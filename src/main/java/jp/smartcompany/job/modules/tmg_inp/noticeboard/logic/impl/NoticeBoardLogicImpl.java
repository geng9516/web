package jp.smartcompany.job.modules.tmg_inp.noticeboard.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.handler.StringHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.configuration.security.dto.SmartUserDetails;
import jp.smartcompany.boot.util.*;
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
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.*;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.DraftNoticeVO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.NoticeVO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardLogicImpl implements INoticeBoardLogic {

    private final IMastGroupsectionpostmappingService groupsectionpostmappingService;
    private final IMastGroupbasesectionService baseSectionService;
    private final DataSource dataSource;
    // 草稿相关service
    private final IHistBulletinBoardTempFileService histBulletinBoardTempFileService;
    private final IHistBulletinBoardTempService histBulletinBoardTempService;
    // 正式公告相关service
    private final IHistBulletinBoardService histBulletinBoardService;
    private final IHistBulletinBoardFileService histBulletinBoardFileService;
    private final IHistBulletinBoardUserService histBulletinBoardUserService;

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

    @Override
    public PageUtil getRangeNoticeList(Map<String,Object> params) {
        String userId = SecurityUtil.getUserId();
        Page<NoticeVO> pageQuery = new PageQuery<NoticeVO>().getPage(params);
        IPage<NoticeVO> page = histBulletinBoardTempService.listBulletinBoard(pageQuery,userId);
        return new PageUtil(page);
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

    @Override
    public PageUtil getSelfDraftNoticeList(Map<String,Object> params) {
      String loginUserId = SecurityUtil.getUserId();
      IPage<DraftNoticeVO> pageQuery = new PageQuery<DraftNoticeVO>().getPage(params);
      IPage<DraftNoticeVO> page = histBulletinBoardTempService.listBulletinBoardTempByPublisherId(pageQuery,loginUserId);
      return new PageUtil(page);
    }

    @Override
    public PageUtil getSelfNoticeList(Map<String,Object> params) {
        String loginUserId = SecurityUtil.getUserId();
        IPage<HistBulletinBoardDO> pageQuery = new PageQuery<HistBulletinBoardDO>().getPage(params);
        IPage<HistBulletinBoardDO> page = histBulletinBoardService.listBulletinBoardByPublisherId(pageQuery,loginUserId);
        return new PageUtil(page);
    }

    /**
     * 供前端富文本插件图文混排时上传图片使用
     * @param file 上传图片
     * @return 上传后的图片路径
     */
    @Override
    public String uploadImageUrl(MultipartFile file) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil();
        return uploadFileUtil.uploadRichTextImage(file,"TMG_RICH_TEXT_NOTICE_BOARD_UPLOAD_PATH");
    }


    /**
     * 保存或修改公告草稿
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void addOrUpdateDraft(DraftNoticeDTO dto) {
        Long hbtId = dto.getHbtId();
        Date startDate = dto.getHbtDdateofannouncement();
        Date endDate = dto.getHbtDdateofexpire();
        if (Objects.isNull(endDate)) {
            endDate = SysUtil.getMaxDateObject();
        }
        String loginUserId = SecurityUtil.getUserId();
        SmartUserDetails loginUser = SecurityUtil.getLoginUser();
        String loginEmpName = loginUser.getMeCemployeename();
        String deptName = loginUser.getMoCsectionname();
        boolean isDraft = StrUtil.equals(dto.getHbtCfix(),IS_DRAFT);
        boolean isPublish = StrUtil.equals(dto.getIsPublish(),IS_PUBLISH);
        String content = dto.getHbtCcontents();
        String title = dto.getHbtCtitle();
        String top = dto.getHbtCheaddisp();
        Date now = DateUtil.date();
        boolean isUpdate = Objects.nonNull(hbtId);
        List<MultipartFile> uploadFiles = dto.getAttachments();
        // 仅保存为草稿，不发布
        if (isDraft && !isPublish) {
            // 设置基础属性
            HistBulletinBoardTempDO tempDO = new HistBulletinBoardTempDO();
            tempDO.setHbtCcompanyid(COMPANY_ID);
            tempDO.setHbtCcustomerid(CUSTOMER_ID);
            tempDO.setHbtCcontents(content);
            tempDO.setHbtCtitle(title);
            tempDO.setHbtCheaddisp(top);
            tempDO.setHbtCmnuser(loginUserId);
            tempDO.setHbtCmnusername(loginEmpName+"("+deptName+")");
            tempDO.setHbtCmodifieruserid(loginUserId);
            tempDO.setHbtDmodifieddate(now);
            tempDO.setHbtDdateofannouncement(startDate);
            tempDO.setHbtDdateofexpire(endDate);
            tempDO.setHbtCrangeTypes(dto.getRangeTypes());
            tempDO.setHbtCempRange(dto.getEmpRangeIds());
            // 修改草稿动作
            if (isUpdate) {
                tempDO.setHbtId(hbtId);
                histBulletinBoardTempService.updateById(tempDO);
            // 新增草案动作
            } else {
               tempDO.setHbtId(1L);
               histBulletinBoardTempService.save(tempDO);
               // 保存草稿后将草稿id赋给
                hbtId = tempDO.getHbtId();
            }
            // 如果有附件则需要保存用户上传的附件
            uploadTempAttachments(hbtId,uploadFiles,isUpdate,dto.getDeleteAttachmentIdList(),false);
        }
        // 保存成草稿的同时发布为正式公共
        if (isPublish) {
            HistBulletinBoardDO bulletinBoardDO = new HistBulletinBoardDO();
            bulletinBoardDO.setHbCcompanyid(COMPANY_ID);
            bulletinBoardDO.setHbCcustomerid(CUSTOMER_ID);
            bulletinBoardDO.setHbCcontents(content);
            bulletinBoardDO.setHbCtitle(title);
            bulletinBoardDO.setHbCheaddisp(top);
            bulletinBoardDO.setHbCmnuser(loginUserId);
            bulletinBoardDO.setHbCmnusername(loginEmpName+"("+deptName+")");
            bulletinBoardDO.setHbCmodifieruserid(loginUserId);
            bulletinBoardDO.setHbDmodifieddate(now);
            bulletinBoardDO.setHbDdateofannouncement(startDate);
            bulletinBoardDO.setHbDdateofexpire(endDate);
            bulletinBoardDO.setHbCfix(dto.getIsPublish());
            histBulletinBoardService.save(bulletinBoardDO);
            Long articleId = bulletinBoardDO.getHbId();

            // 保存可查看此公告的用户idList
            HistBulletinBoardUserDO boardUserDO = new HistBulletinBoardUserDO();
            boardUserDO.setHbgCuserids(dto.getEmpRangeIds());
            boardUserDO.setHbgCarticleid(articleId);
            boardUserDO.setHbgCmodifieruserid(loginUserId);
            boardUserDO.setHbgDmodifieddate(now);
            histBulletinBoardUserService.save(boardUserDO);

            // 首次创建就直接将此公告发布
            if (!isUpdate) {
                uploadOfficialAttachments(articleId,uploadFiles,false,dto.getDeleteAttachmentIdList(),false,hbtId);
            // 经过编辑后的草稿发布成公告
            } else {
                uploadOfficialAttachments(articleId,uploadFiles,true,dto.getDeleteAttachmentIdList(),false,hbtId);
                // 草稿附件处理完毕后要把草稿删除
                histBulletinBoardTempService.removeById(hbtId);
            }
        }
    }

    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public void deleteDraft(List<Long> draftIds) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil();
        List<HistBulletinBoardTempFileDO> oldFileList = histBulletinBoardTempFileService.listFileByIds(draftIds);
        if (CollUtil.isNotEmpty(oldFileList)) {
            List<String> realPathList = oldFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfFileRealPath).collect(Collectors.toList());
            realPathList.forEach(uploadFileUtil::removePreFile);
            histBulletinBoardTempFileService.removeByIds(oldFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfId).collect(Collectors.toList()));
        }
        histBulletinBoardTempService.removeByIds(draftIds);
    }

    @Override
    public NoticeVO getNoticeDetail(Long id) {
        HistBulletinBoardDO boardDO = histBulletinBoardService.getById(id);
        if (boardDO == null) {
            throw new GlobalException("掲示板データは存在しません");
        }
        NoticeVO noticeVO = new NoticeVO();
        BeanUtil.copyProperties(boardDO,noticeVO);
        noticeVO.setUpdateDate(boardDO.getHbDmodifieddate());
        List<HistBulletinBoardFileDO> attachments = histBulletinBoardFileService.listFileById(id);
        noticeVO.setAttachmentList(attachments);
        List<Map<String,String>> tempEmpRangeList = CollUtil.newArrayList();

        QueryWrapper<HistBulletinBoardUserDO> qw = new QueryWrapper<>();
        qw.eq("HBG_CARTICLEID",id);
        HistBulletinBoardUserDO userRange = histBulletinBoardUserService.getOne(qw);
        if (userRange == null) {
            throw new GlobalException("照会できる人は見つかりません");
        }
        String[] empIds = userRange.getHbgCuserids().split(",");
        try (Connection conn = dataSource.getConnection()) {
            for (String empId : empIds) {
                String empName = SqlExecutor.query(conn, "select me_ckanjiname from mast_employees  where me_cemployeeid_ck = ? and me_dstartdate <= trunc(sysdate) and me_denddate >= trunc(sysdate)", new StringHandler(), empId);
                Map<String, String> empMap = MapUtil.<String, String>builder().put(empId, empName).build();
                tempEmpRangeList.add(empMap);
            }
            noticeVO.setUserRangeList(tempEmpRangeList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return noticeVO;
    }

    @Override
    public DraftNoticeVO getDraftNoticeDetail(Long id) {
        HistBulletinBoardTempDO tempDO = histBulletinBoardTempService.getById(id);
        if (tempDO == null) {
            throw new GlobalException("下書きは存在しません");
        }
        DraftNoticeVO noticeVO = new DraftNoticeVO();
        BeanUtil.copyProperties(tempDO,noticeVO);
        List<HistBulletinBoardTempFileDO> attachments = histBulletinBoardTempFileService.listFileById(id);
        if (CollUtil.isEmpty(attachments)) {
            noticeVO.setAttachments(CollUtil.newArrayList());
        } else {
            noticeVO.setAttachments(attachments);
        }
        List<String> empIds = Arrays.asList(tempDO.getHbtCempRange().split(",").clone());
        if (CollUtil.isEmpty(empIds)) {
            throw new GlobalException("照会できるの社員範囲は設定していません");
        }
        List<Map<String,String>> tempEmpRangeList = CollUtil.newArrayList();
        try (Connection conn = dataSource.getConnection()) {
            for (String empId : empIds) {
                String empName = SqlExecutor.query(conn, "select me_ckanjiname from mast_employees  where me_cemployeeid_ck = ? and me_dstartdate <= trunc(sysdate) and me_denddate >= trunc(sysdate)", new StringHandler(), empId);
                Map<String, String> empMap = MapUtil.<String, String>builder().put(empId, empName).build();
                tempEmpRangeList.add(empMap);
            }
            noticeVO.setEmpRangeList(tempEmpRangeList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        List<String> rangeTypeIds = Arrays.asList(tempDO.getHbtCrangeTypes().split(",").clone());
        if (CollUtil.isEmpty(rangeTypeIds)) {
            throw new GlobalException("照会の範囲は設定されていません");
        }
        List<NoticeRangeDTO> typeRangeList = assembleTypeRangeList(rangeTypeIds);
        noticeVO.setTypeRangeList(typeRangeList);
        return noticeVO;
    }

    @Override
    public void changeNoticeStatus(Long id,String status) {
        HistBulletinBoardDO histBulletinBoardDO = new HistBulletinBoardDO();
        histBulletinBoardDO.setHbId(id);
        histBulletinBoardDO.setHbCfix(status);
        histBulletinBoardDO.setHbDmodifieddate(DateUtil.date());
        histBulletinBoardService.updateById(histBulletinBoardDO);
    }

    // 上传附件到正式公告（直接发布成正式公告的情况）
    public void uploadOfficialAttachments(Long articleId,List<MultipartFile> uploadFiles,boolean isUpdate,List<Long> deleteAttachmentIdList,boolean randomName,Long draftId) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil(randomName);
        // 公告添加操作的话不需要判断是否已经添加过附件，只要将上传的附件保存即可
        if (!isUpdate) {
            if (CollUtil.isNotEmpty(uploadFiles)) {
                addNewAttachments(articleId, uploadFiles, uploadFileUtil,false);
            }
        } else {
            // 查询是否存在此公告的草稿
            List<HistBulletinBoardTempFileDO> tempFileList = histBulletinBoardTempFileService.listFileById(draftId);
            // 如果此次编辑的草稿不存在附件且本次保存为正式公告时上传了附件
            if (CollUtil.isEmpty(tempFileList)) {
                if (CollUtil.isNotEmpty(uploadFiles)) {
                    addNewAttachments(articleId, uploadFiles, uploadFileUtil,false);
                }
            } else {
                List<Long> historyFileIdList = tempFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfId).collect(Collectors.toList());
                // 获取本次编辑草稿时要删除的附件对应的id
                List<Long> deletedTempFileIdList = CollUtil.newArrayList(CollUtil.intersection(historyFileIdList, deleteAttachmentIdList));
                if (CollUtil.isNotEmpty(deletedTempFileIdList)) {
                    // 根据id找到要删除的附件,如果确实存在则删除此附件
                    List<HistBulletinBoardTempFileDO> deletedTempFileList =  histBulletinBoardTempFileService.listByIds(deletedTempFileIdList);
                    if (CollUtil.isNotEmpty(deletedTempFileList)) {
                        List<String> realPathList = deletedTempFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfFileRealPath).collect(Collectors.toList());
                        realPathList.forEach(uploadFileUtil::removePreFile);
                        histBulletinBoardTempFileService.removeByIds(deletedTempFileIdList);
                    }
                }
                // 删除掉本次需要删除的附件后，再把删除后的tempFile的数据赋值给正式公告
                List<HistBulletinBoardTempFileDO> tempStorageFileList = histBulletinBoardTempFileService.listFileById(draftId);
                List<HistBulletinBoardFileDO> nowFileList = CollUtil.newArrayList();
                if (CollUtil.isNotEmpty(tempStorageFileList)) {
                    tempStorageFileList.forEach(tempFile -> {
                        HistBulletinBoardFileDO fileDO = new HistBulletinBoardFileDO();
                        fileDO.setHbfFileRealPath(tempFile.getHbtfFileRealPath());
                        fileDO.setHbfFileName(tempFile.getHbtfFilename());
                        fileDO.setHbfFileUrl(tempFile.getHbtfFileUrl());
                        fileDO.setHbfStartDate(tempFile.getHbtfStartDate());
                        fileDO.setHbfEndDate(tempFile.getHbtfEndDate());
                        fileDO.setHbIdFk(articleId);
                        fileDO.setHbfValid(tempFile.getHbtfValid());
                        nowFileList.add(fileDO);
                    });
                    histBulletinBoardFileService.saveBatch(nowFileList);
                }
                // 保存完毕后将相关草稿删除
                List<Long> deleteTempStorageFileIdList = tempStorageFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfId).collect(Collectors.toList());
                histBulletinBoardTempFileService.removeByIds(deleteTempStorageFileIdList);
                // 处理完原先草稿中的附件后再处理本次上传的附件
                if (CollUtil.isNotEmpty(uploadFiles)) {
                    addNewAttachments(articleId, uploadFiles, uploadFileUtil,false);
                }
            }
        }
    }

    /**
     * 上传公告附件(草稿)
     * @param articleId 草稿或公告id
     * @param uploadFiles 附件个数
     * @param isUpdate 是否是草稿的更新动作
     */
    public void uploadTempAttachments(Long articleId, List<MultipartFile> uploadFiles, boolean isUpdate, List<Long> deleteAttachmentIdList,boolean randomName) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil(randomName);
        // 公告添加操作的话不需要判断是否已经添加过附件，只要将上传的附件保存即可
        if (!isUpdate) {
            if (CollUtil.isNotEmpty(uploadFiles)) {
                addNewAttachments(articleId, uploadFiles, uploadFileUtil,true);
            }
        }
        // 公告修改操作的话则要判断之前一回添加的附件哪些需要删除，哪些需要保留，之后再将本次上传的新的文件与比对后的附件结果进行合并保存
        else {
            // 查询此草稿是否存在附件信息。不存在且本次上传了附件的话则直接将附件添加即可
            List<HistBulletinBoardTempFileDO> oldFileList = histBulletinBoardTempFileService.listFileById(articleId);
            if (CollUtil.isEmpty(oldFileList)) {
                if (CollUtil.isNotEmpty(uploadFiles)) {
                    addNewAttachments(articleId, uploadFiles, uploadFileUtil,true);
                }
            }
            // 如果原先已经存在草稿附件,且本次没有上传新的附件，则需要根据前端传过来的deleteAttachmentIdStr和keepAttachmentIdStr进行筛选，对附件进行有条件地增减
            else {
               List<Long> historyFileIdList = oldFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfId).collect(Collectors.toList());
               // 获取本次编辑草稿时要删除的附件对应的id
               List<Long> deletedTempFileIdList = CollUtil.newArrayList(CollUtil.intersection(historyFileIdList, deleteAttachmentIdList));
               if (CollUtil.isNotEmpty(deletedTempFileIdList)) {
                   // 根据id找到要删除的附件,如果确实存在则删除此附件
                   List<HistBulletinBoardTempFileDO> deletedTempFileList =  histBulletinBoardTempFileService.listByIds(deletedTempFileIdList);
                   if (CollUtil.isNotEmpty(deletedTempFileList)) {
                       List<String> realPathList = deletedTempFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfFileRealPath).collect(Collectors.toList());
                        realPathList.forEach(uploadFileUtil::removePreFile);
                        histBulletinBoardTempFileService.removeByIds(deletedTempFileIdList);
                   }
               }
               // 处理完本次要删除的附件后，还要判断是否有本次新添加的附件，如果有本次新添加的附件则也要进行保存
               if (CollUtil.isNotEmpty(uploadFiles)) {
                   addNewAttachments(articleId, uploadFiles, uploadFileUtil,true);
               }
            }
        }
    }

    // 新增附件
    public void addNewAttachments(Long articleId, List<MultipartFile> uploadFiles, UploadFileUtil uploadFileUtil,boolean isDraft) {
        List<UploadFileInfo> uploadFileInfoList = uploadFileUtil.uploadAttachment(uploadFiles, "notice-board", "TMG_NOTICE_BOARD_UPLOAD_PATH");

        List<HistBulletinBoardTempFileDO> uploadBoardTempFileList = CollUtil.newArrayList();
        List<HistBulletinBoardFileDO> uploadBoardFileList = CollUtil.newArrayList();

        uploadFileInfoList.forEach(item -> {
            if (isDraft) {
                HistBulletinBoardTempFileDO fileDO = new HistBulletinBoardTempFileDO();
                fileDO.setHbtIdFk(articleId);
                fileDO.setHbtfFileUrl(item.getFileUrl());
                fileDO.setHbtfFilename(item.getFilename());
                fileDO.setHbtfFileRealPath(item.getRealPath());
                uploadBoardTempFileList.add(fileDO);
            } else {
                HistBulletinBoardFileDO fileDO = new HistBulletinBoardFileDO();
                fileDO.setHbIdFk(articleId);
                fileDO.setHbfFileUrl(item.getFileUrl());
                fileDO.setHbfFileName(item.getFilename());
                fileDO.setHbfFileRealPath(item.getRealPath());
                uploadBoardFileList.add(fileDO);
            }
        });
        if (CollUtil.isNotEmpty(uploadBoardTempFileList)) {
            histBulletinBoardTempFileService.saveBatch(uploadBoardTempFileList);
        }
        if (CollUtil.isNotEmpty(uploadBoardFileList)) {
            histBulletinBoardFileService.saveBatch(uploadBoardFileList);
        }
    }

    /**
     * ================================
     * 以下为私有使用方法
     * ===============================
     */

    private static final String IS_DRAFT = "0";
    private static final String IS_PUBLISH = "1";
    private static final String COMPANY_ID = "01";
    private static final String CUSTOMER_ID = "01";

    private List<NoticeRangeDTO> assembleTypeRangeList(List<String> rangeTypeIds) {
        List<NoticeRangeDTO> typeRangeList = CollUtil.newArrayList();
        assembleTypeRangeItems(rangeTypeIds, typeRangeList);
        return typeRangeList;
    }

    private void assembleTypeRangeItems(List<String> rangeTypeIds, List<NoticeRangeDTO> typeRangeList) {
        for (String rangeTypeId : rangeTypeIds) {
            NoticeRangeDTO rangeDTO = new NoticeRangeDTO();
            rangeDTO.setTypeId(rangeTypeId);
            if (StrUtil.equals(FG_COMP_SEC, rangeTypeId)) {
                rangeDTO.setTypeName("自所属以下");
            } else if (StrUtil.equals(FG_COMP_SEC_POST, rangeTypeId)) {
                rangeDTO.setTypeName("所属以下の役職");
            } else if (StrUtil.equals(FG_COMP_SEC_BOSS, rangeTypeId)) {
                rangeDTO.setTypeName("所属以下の所属長");
            } else if (StrUtil.equals(FG_COMP_POST, rangeTypeId)) {
                rangeDTO.setTypeName("法人の役職");
            } else if (StrUtil.equals(FG_COMP_EMP, rangeTypeId)) {
                rangeDTO.setTypeName("法人の職員");
            }
            typeRangeList.add(rangeDTO);
        }
    }

    // 获取能进行揭示板发布的group
    private List<LoginGroupBO> getPublishGroupList() {
        HttpSession session = ContextUtil.getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        return psSession.getLoginGroups().get("01").stream().filter(LoginGroupBO::getPublishing).collect(Collectors.toList());
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
