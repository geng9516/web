package jp.smartcompany.job.modules.tmg_inp.noticeboard.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.handler.StringHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.boot.util.UploadFileUtil;
import jp.smartcompany.job.modules.core.pojo.bo.GroupBaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.service.IMastGroupbasesectionService;
import jp.smartcompany.job.modules.core.service.IMastGroupsectionpostmappingService;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.logic.INoticeBoardLogic;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.bo.UploadFileInfo;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.DraftNoticeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempFileDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardTempFileService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardTempService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardLogicImpl implements INoticeBoardLogic {

    private final IMastGroupsectionpostmappingService groupsectionpostmappingService;
    private final IMastGroupbasesectionService baseSectionService;
    private final DataSource dataSource;
    private final IHistBulletinBoardTempFileService histBulletinBoardTempFileService;
    private final IHistBulletinBoardTempService histBulletinBoardTempService;

    /** 処理区分(法人＆組織指定リスト) */
    public static final String FG_COMP_SEC         = "02";
    /** 処理区分(組織ごとの定義情報取得(法人＆組織＆役職リスト)) */
    public static final String FG_COMP_SEC_POST    = "03";
    /** 処理区分(法人＆組織＆所属長リスト) */
    public static final String FG_COMP_SEC_BOSS    = "05";
    /** 処理区分(法人＆役職リスト) */
    public static final String FG_COMP_POST        = "06";
    /** 処理区分(法人＆職員リスト) */
    public static final String FG_COMP_EMP         = "07";

    public static final String BASE_ONLY = "09";
    public static final String BASE_UNDER = "10";

    // 获取能进行揭示板发布的group
    private List<LoginGroupBO> getPublishGroupList() {
        HttpSession session = ContextUtil.getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        return psSession.getLoginGroups().get("01").stream().filter(LoginGroupBO::getPublishing).collect(Collectors.toList());
    }

    @Override
    public List<NoticeRangeDTO> getSendNoticeRangeList(HttpSession session) {
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        if (!psSession.getHasPublishPermission()) {
            throw new GlobalException("発表権限なし");
        }
        List<LoginGroupBO> noticeGroups = getPublishGroupList();
        List<String> noticeGroupIds = noticeGroups.stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
        List<NoticeRangeDTO> rangeList = CollUtil.newArrayList();
        List<String> typeIdList = groupsectionpostmappingService.selectGroupRange(noticeGroupIds);
        for (String typeId : typeIdList) {
            NoticeRangeDTO rangeDTO = new NoticeRangeDTO();
            rangeDTO.setTypeId(typeId);
            if (StrUtil.equals(FG_COMP_SEC,typeId)) {
                rangeDTO.setTypeName("自所属以下");
            } else if (StrUtil.equals(FG_COMP_SEC_POST,typeId)) {
                rangeDTO.setTypeName("所属以下の役職");
            }else if (StrUtil.equals(FG_COMP_SEC_BOSS ,typeId)) {
                rangeDTO.setTypeName("所属以下の所属長");
            }else if (StrUtil.equals(FG_COMP_POST,typeId)) {
                rangeDTO.setTypeName("法人の役職");
            }else if (StrUtil.equals(FG_COMP_EMP,typeId)) {
                rangeDTO.setTypeName("法人の職員");
            }
            rangeList.add(rangeDTO);
        }
        NoticeRangeDTO rangeDTO = new NoticeRangeDTO();
        rangeDTO.setTypeId(BASE_ONLY);
        rangeDTO.setTypeName("自所属のみ");
        rangeList.add(rangeDTO);
        NoticeRangeDTO underRangeDTO = new NoticeRangeDTO();
        underRangeDTO.setTypeId(BASE_UNDER);
        underRangeDTO.setTypeName("照会できる所属");
        rangeList.add(underRangeDTO);
        return rangeList;
    }

    @Override
    public List<Map<String,String>> getValidReadEmpList(List<String> typeIds, HttpSession session) {
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        if (!psSession.getHasPublishPermission()) {
            throw new GlobalException("発表権限なし");
        }
        List<Map<String,String>> employs = CollUtil.newArrayList();
        Date date = DateUtil.date();
        List<String> groupIds = psSession.getLoginGroups().get("01").stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
        List<String> sectionIds = groupsectionpostmappingService.selectCompanySectionIdList(groupIds,FG_COMP_SEC);
        List<String> empIdResult = CollUtil.newArrayList();
        try(Connection conn = dataSource.getConnection()) {
            conn.setReadOnly(true);
            getEmpIdRanges(typeIds, psSession, date, groupIds, sectionIds, empIdResult, conn);
            for (String empId : empIdResult) {
                String empName = SqlExecutor.query(conn,"select me_ckanjiname from mast_employees  where me_cemployeeid_ck = ? and me_dstartdate <= trunc(sysdate) and me_denddate >= trunc(sysdate)",new StringHandler(),empId);
                Map<String,String> empMap = MapUtil.<String,String>builder().put(empId,empName).build();
                employs.add(empMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return employs;
    }

    private static final String IS_DRAFT = "0";
    private static final String COMPANY_ID = "01";
    private static final String CUSTOMER_ID = "01";

    @Override
    public String uploadImageUrl(MultipartFile file) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil();
        return "/upload"+uploadFileUtil.uploadRichTextImage(file,"TMG_RICH_TEXT_NOTICE_BOARD_UPLOAD_PATH");
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void addOrUpdateDraft(DraftNoticeDTO dto) {
        Long id = dto.getHbtId();
        Date startDate = dto.getHbtDdateofannouncement();
        Date endDate = dto.getHbtDdateofexpire();
        if (Objects.isNull(endDate)) {
            endDate = SysUtil.getMaxDateObject();
        }
        String loginUserId = SecurityUtil.getUserId();
        String loginEmpName = SecurityUtil.getLoginUser().getMeCemployeename();
        boolean isDraft = StrUtil.equals(dto.getHbtCfix(),IS_DRAFT);
        String content = dto.getHbtCcontents();
        String title = dto.getHbtCtitle();
        String top = dto.getHbtCheaddisp();
        Date now = DateUtil.date();
        boolean isUpdate = Objects.nonNull(id);
        List<MultipartFile> uploadFiles = dto.getAttachments();
        // 保存草稿
        if (isDraft) {
            // 设置基础属性
            HistBulletinBoardTempDO tempDO = new HistBulletinBoardTempDO();
            tempDO.setHbtCcompanyid(COMPANY_ID);
            tempDO.setHbtCcustomerid(CUSTOMER_ID);
            tempDO.setHbtCcontents(content);
            tempDO.setHbtCtitle(title);
            tempDO.setHbtCheaddisp(top);
            tempDO.setHbtCmnuser(loginUserId);
            tempDO.setHbtCmnusername(loginEmpName);
            tempDO.setHbtCmodifieruserid(loginUserId);
            tempDO.setHbtDmodifieddate(now);
            tempDO.setHbtDdateofannouncement(startDate);
            tempDO.setHbtDdateofexpire(endDate);
            tempDO.setHbtCrangeTypes(dto.getRangeTypes());
            tempDO.setHbtCempRange(dto.getEmpRangeIds());
            // 修改草稿动作
            if (isUpdate) {
                tempDO.setHbtId(id);
                histBulletinBoardTempService.updateById(tempDO);
            // 新增草案动作
            } else {
               tempDO.setHbtId(1L);
               histBulletinBoardTempService.save(tempDO);
               // 保存草稿后将草稿id赋给
               id = tempDO.getHbtId();
            }
            // 如果有附件则需要保存用户上传的附件
            uploadAttachments(id,uploadFiles,isUpdate,isDraft);
        }
    }

    /**
     * 上传公告附件
     * @param articleId 草稿或公告id
     * @param uploadFiles 附件个数
     * @param isUpdate 是否是草稿的更新动作
     * @param isDraft 是否保存为正式公告
     */
    public void uploadAttachments(Long articleId,List<MultipartFile> uploadFiles,boolean isUpdate,boolean isDraft) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil();
        // 如果是编辑草稿
        if (isDraft) {
            // 如果上传了新的附件，则要把原来已经上传的附件删除
            if (CollUtil.isNotEmpty(uploadFiles)) {
                // 如果是修改操作，则需要删除之前相对应的附件
                if (!isUpdate) {
                    List<HistBulletinBoardTempFileDO> oldFileList = histBulletinBoardTempFileService.listFileById(articleId);
                    if (CollUtil.isNotEmpty(oldFileList)) {
                        List<String> realPathList = oldFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfFileRealPath).collect(Collectors.toList());
                        realPathList.forEach(uploadFileUtil::removePreFile);
                        histBulletinBoardTempFileService.removeByIds(oldFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfId).collect(Collectors.toList()));
                    }
                }
                // 将新的附件保存
                List<UploadFileInfo> uploadFileInfoList = uploadFileUtil.uploadAttachment(uploadFiles, "notice-board", "TMG_NOTICE_BOARD_UPLOAD_PATH");
                if (CollUtil.isNotEmpty(uploadFileInfoList)) {
                    List<HistBulletinBoardTempFileDO> uploadBoardFileList = CollUtil.newArrayList();
                    uploadFileInfoList.forEach(item -> {
                        HistBulletinBoardTempFileDO fileDO = new HistBulletinBoardTempFileDO();
                        fileDO.setHbtIdFk(articleId);
                        fileDO.setHbtfFileUrl(item.getFileUrl());
                        fileDO.setHbtfFilename(item.getFilename());
                        fileDO.setHbtfFileRealPath(item.getRealPath());
                        uploadBoardFileList.add(fileDO);
                    });
                    histBulletinBoardTempFileService.saveBatch(uploadBoardFileList);
                }
            }
        }
    }

    /**
     * 根据group定义的typeId获取相应的用户
     * @param typeIds
     * @param psSession
     * @param date
     * @param groupIds
     * @param sectionIds
     * @param empIdResult
     * @param conn
     * @throws SQLException
     */
    private void getEmpIdRanges(List<String> typeIds, PsSession psSession, Date date, List<String> groupIds, List<String> sectionIds, List<String> empIdResult, Connection conn) throws SQLException {
        for (String groupId : groupIds) {
             for (String typeId : typeIds) {
                List<String> partEmpIds = CollUtil.newArrayList();
                if (StrUtil.equals(FG_COMP_SEC,typeId)) {
                    if (CollUtil.isNotEmpty(sectionIds)) {
                        partEmpIds = groupsectionpostmappingService.selectEmpIdByCompanySectionIdList(sectionIds, groupId, FG_COMP_SEC);
                    }
                } else if (StrUtil.equals(FG_COMP_SEC_POST,typeId)) {
                     partEmpIds = groupsectionpostmappingService.selectEmpIdByCompanySectionPostIdList(groupId);
                } else if (StrUtil.equals(FG_COMP_SEC_BOSS,typeId)) {
                     partEmpIds = groupsectionpostmappingService.selectEmpIdByCompanySectionBossList(groupId);
                } else if (StrUtil.equals(FG_COMP_POST,typeId)) {
                     partEmpIds = groupsectionpostmappingService.selectEmpIdByCompanyPostList(groupId);
                } else if (StrUtil.equals(FG_COMP_EMP,typeId)) {
                     partEmpIds = groupsectionpostmappingService.selectEmpIdByCompanyEmpList(groupId);
                } else if (StrUtil.equals(BASE_ONLY,typeId)) {
                    // 获取当前部门
                    List<Designation> loginDesignations = psSession.getLoginDesignation();
                    Designation designation = loginDesignations.get(0);
                    String sectionId = designation.getSection();
                    partEmpIds = baseSectionService.selectSelfSectionEmpIds(sectionId);
                } else if (StrUtil.equals(BASE_UNDER,typeId)) {
                    List<GroupBaseSectionBO> baseSectionList = baseSectionService.getBaseSectionByGroupCode("01", "01", groupId, date);
                    List<String> layerSectionIds = baseSectionList.stream().map(GroupBaseSectionBO::getMgbsClayeredsectionid).collect(Collectors.toList());
                    if (CollUtil.isNotEmpty(layerSectionIds)) {
                        List<String> layerSqlList = CollUtil.newArrayList();
                        for (String layerSectionId : layerSectionIds) {
                            layerSqlList.add(" o.MO_CLAYEREDSECTIONID LIKE '" + layerSectionId+"' ");
                        }
                        StringBuilder sql = new StringBuilder();
                        int size = layerSqlList.size();
                        for (int i = 0; i < size; i++) {
                            String layerSql = layerSqlList.get(i);
                            if (i == size - 1) {
                                sql.append(layerSql);
                            }else {
                                sql.append(layerSql).append(" OR");
                            }
                        }
                        String executeSql = "SELECT" +
                                " HD_CEMPLOYEEID_CK" +
                                " FROM" +
                                " HIST_DESIGNATION d,TMG_EMPLOYEES e,MAST_ORGANISATION o" +
                                " WHERE" +
                                " d.HD_CCUSTOMERID_CK = '01'" +
                                " AND d.HD_CCOMPANYID_CK = '01'" +
                                " AND d.HD_DSTARTDATE_CK <= TRUNC(SYSDATE)" +
                                " AND d.HD_DENDDATE >= TRUNC(SYSDATE)" +
                                " AND d.HD_CIFKEYORADDITIONALROLE = '0'" +
                                " AND d.HD_NOFFCIALORNOT = 0" +
                                " AND d.HD_NOFFICIALORNOT = 0" +
                                " AND e.TEM_CEMPLOYEEID(+) = d.HD_CEMPLOYEEID_CK" +
                                " AND e.TEM_DSTARTDATE(+) <= TRUNC(SYSDATE)" +
                                " AND e.TEM_CCOMPANYID(+) = d.HD_CCOMPANYID_CK" +
                                " AND e.TEM_CCUSTOMERID(+) = d.HD_CCUSTOMERID_CK" +
                                " AND e.TEM_DENDDATE(+) >= TRUNC(SYSDATE)" +
                                " AND 'TMG_MANAGEFLG|0' <> TMG_F_IS_MANAGEFLG(e.TEM_CEMPLOYEEID,TRUNC(SYSDATE),LAST_DAY(TRUNC(SYSDATE)), e.TEM_CCUSTOMERID,e.TEM_CCOMPANYID)" +
                                " AND o.MO_CCUSTOMERID_CK_FK = D.HD_CCUSTOMERID_CK" +
                                " AND o.MO_CCOMPANYID_CK_FK = D.HD_CCOMPANYID_CK" +
                                " AND o.MO_CSECTIONID_CK = D.HD_CSECTIONID_FK" +
                                " AND o.MO_CLANGUAGE = 'ja'" +
                                " AND o.MO_DSTART <= TRUNC(SYSDATE)" +
                                " AND o.MO_DEND >= TRUNC(SYSDATE)" +
                                " AND e.TEM_CWORKTYPEID NOT IN ('TMG_WORKERTYPE|00')" +
                                " AND (" +
                                   sql.toString()+
                                ")";
                              List<Entity> entityList = SqlExecutor.query(conn,executeSql,new EntityListHandler());
                              List<String> tmpEmpIds = CollUtil.newArrayList();
                              entityList.forEach(entity -> tmpEmpIds.add((String)entity.get("HD_CEMPLOYEEID_CK")));
                              partEmpIds = tmpEmpIds;
                              log.info("处理结果：{}",partEmpIds);
                    }
                }
                if (CollUtil.isNotEmpty(partEmpIds)){
                    CollUtil.addAllIfNotContains(empIdResult,partEmpIds);
                }
            }
        }
    }

}
