package jp.smartcompany.job.modules.tmg_inp.noticeboard.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
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
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.EditNoticeDTO;
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
    // ????????????service
    private final IHistBulletinBoardTempFileService histBulletinBoardTempFileService;
    private final IHistBulletinBoardTempService histBulletinBoardTempService;
    // ??????????????????service
    private final IHistBulletinBoardService histBulletinBoardService;
    private final IHistBulletinBoardFileService histBulletinBoardFileService;
    private final IHistBulletinBoardUserService histBulletinBoardUserService;
    private final IHistBulletinBoardReadStatusService histBulletinBoardReadStatusService;

    /** ????????????(??????????????????????????????) */
    public static final String FG_COMP_SEC         = "02";
    /** ????????????(?????????????????????????????????(?????????????????????????????????)) */
    public static final String FG_COMP_SEC_POST    = "03";
    /** ????????????(????????????????????????????????????) */
    public static final String FG_COMP_SEC_BOSS    = "05";
    /** ????????????(????????????????????????) */
    public static final String FG_COMP_POST        = "06";
    /** ????????????(????????????????????????) */
    public static final String FG_COMP_EMP         = "07";

    public static final String BASE_ONLY = "09";
    public static final String BASE_UNDER = "10";

    @Override
    public PageUtil getRangeNoticeList(Map<String,Object> params) {
        String userId = SecurityUtil.getUserId();
        Page<NoticeVO> pageQuery = new PageQuery<NoticeVO>().getPage(params);
        IPage<NoticeVO> page = histBulletinBoardService.selectVisibleList(pageQuery,userId);
        page.getRecords().forEach(notice -> notice.setEnableEdit(StrUtil.equals(notice.getHbCmnuser(),userId)));
        return new PageUtil(page);
    }

    @Override
    public List<NoticeRangeDTO> getSendNoticeRangeList(HttpSession session) {
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        if (!psSession.getHasPublishPermission()) {
            throw new GlobalException("??????????????????");
        }
        List<LoginGroupBO> noticeGroups = getPublishGroupList();
        List<String> noticeGroupIds = noticeGroups.stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
        List<NoticeRangeDTO> rangeList = CollUtil.newArrayList();
        List<String> typeIdList = groupsectionpostmappingService.selectGroupRange(noticeGroupIds);
        for (String typeId : typeIdList) {
            NoticeRangeDTO rangeDTO = new NoticeRangeDTO();
            rangeDTO.setTypeId(typeId);
            if (StrUtil.equals(FG_COMP_SEC,typeId)) {
                rangeDTO.setTypeName("???????????????");
            } else if (StrUtil.equals(FG_COMP_SEC_POST,typeId)) {
                rangeDTO.setTypeName("?????????????????????");
            }else if (StrUtil.equals(FG_COMP_SEC_BOSS ,typeId)) {
                rangeDTO.setTypeName("????????????????????????");
            }else if (StrUtil.equals(FG_COMP_POST,typeId)) {
                rangeDTO.setTypeName("???????????????");
            }else if (StrUtil.equals(FG_COMP_EMP,typeId)) {
                rangeDTO.setTypeName("???????????????");
            }
            rangeList.add(rangeDTO);
        }
        NoticeRangeDTO rangeDTO = new NoticeRangeDTO();
        rangeDTO.setTypeId(BASE_ONLY);
        rangeDTO.setTypeName("???????????????");
        rangeList.add(rangeDTO);
        NoticeRangeDTO underRangeDTO = new NoticeRangeDTO();
        underRangeDTO.setTypeId(BASE_UNDER);
        underRangeDTO.setTypeName("?????????????????????");
        rangeList.add(underRangeDTO);
        return rangeList;
    }

    @Override
    public List<Map<String,String>> getValidReadEmpList(List<String> typeIds, HttpSession session) {
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        if (!psSession.getHasPublishPermission()) {
            throw new GlobalException("??????????????????");
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

    /**
     * ?????????????????????????????????????????????????????????
     * @param file ????????????
     * @return ????????????????????????
     */
    @Override
    public String uploadImageUrl(MultipartFile file) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil();
        return uploadFileUtil.uploadRichTextImage(file,"TMG_RICH_TEXT_NOTICE_BOARD_UPLOAD_PATH");
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void editNoticeContent(EditNoticeDTO dto) {
        Long id = dto.getHbId();
        String loginUserId = SecurityUtil.getUserId();
        Date now = DateUtil.date();
        HistBulletinBoardDO originalNotice = histBulletinBoardService.getById(id);
        if (originalNotice == null) {
            throw new GlobalException("????????????????????????????????????");
        }
        if (!StrUtil.equals(loginUserId,originalNotice.getHbCmnuser())) {
            throw new GlobalException("??????????????????????????????????????????????????????");
        }
        if (SysDateUtil.isLess(originalNotice.getHbDdateofexpire(),now)) {
            throw new GlobalException("????????????????????????????????????????????????");
        }
        if (StrUtil.equals("0",originalNotice.getHbCfix())) {
            throw new GlobalException("????????????????????????????????????????????????");
        }
        HistBulletinBoardUserDO userRangeDO = histBulletinBoardUserService.getByNoticeId(id);
        if (userRangeDO == null) {
            throw new GlobalException("?????????????????????????????????????????????");
        }
        userRangeDO.setHbgCrangeTypes(dto.getRangeTypes());
        userRangeDO.setHbgCuserids(dto.getEmpRangeIds());
        histBulletinBoardUserService.updateById(userRangeDO);

        if (Objects.isNull(dto.getHbDdateofexpire())) {
            dto.setHbDdateofexpire(SysUtil.getMaxDateObject());
        }
        DateTime sDate = DateUtil.date(dto.getHbDdateofannouncement());
        DateTime eDate = DateUtil.date(dto.getHbDdateofexpire());
        Date startDate = SysDateUtil.of(sDate.year(), sDate.month()+1, sDate.dayOfMonth());
        Date endDate = SysDateUtil.of(eDate.year(), eDate.month()+1, eDate.dayOfMonth());

        BeanUtil.copyProperties(dto,originalNotice);
        originalNotice.setHbDmodifieddate(now);
        originalNotice.setHbDdateofannouncement(startDate);
        originalNotice.setHbDdateofexpire(endDate);

        // ???????????????????????????
        HistBulletinBoardReadStatusDO statusDO = histBulletinBoardReadStatusService.getStatusByUserIdArticleId(loginUserId,id);
        if (statusDO !=null){
            statusDO.setHbrsStatus(false);
            histBulletinBoardReadStatusService.updateById(statusDO);
        }
        histBulletinBoardService.updateById(originalNotice);
    }

    @Override
    public PageUtil getSelfNoticeList(Map<String,Object> params) {
        Page<NoticeVO> pageQuery = new PageQuery<NoticeVO>().getPage(params);
        String loginUserId = SecurityUtil.getUserId();
        IPage<NoticeVO> page = histBulletinBoardService.listByPublisherId(pageQuery,loginUserId);
        Date now = DateUtil.date();
        page.getRecords().forEach(notice -> {
              //0?????? 1?????? 2?????? 3?????????????????????
              if (StrUtil.equals(notice.getHbCfix(),"0")){
                  notice.setStatus(1);
              } else if (SysDateUtil.isLess(notice.getHbDdateofexpire(),now)) {
                  notice.setStatus(2);
              } else if (SysDateUtil.isGreater(notice.getHbDdateofannouncement(),now)){
                  notice.setStatus(3);
              } else {
                  notice.setStatus(0);
              }
        });
        return new PageUtil(page);
    }

    /**
     * ???????????????????????????
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void addOrUpdateDraft(DraftNoticeDTO dto) {
        Long hbtId = dto.getHbtId();
        DateTime sDate = DateUtil.date(dto.getHbtDdateofannouncement());
        DateTime eDate = DateUtil.date(dto.getHbtDdateofexpire());
        Date startDate = SysDateUtil.of(sDate.year(), sDate.month()+1, sDate.dayOfMonth());
        Date endDate = SysDateUtil.of(eDate.year(), eDate.month()+1, eDate.dayOfMonth());
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
        // ??????????????????????????????
        if (isDraft && !isPublish) {
            // ??????????????????
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
            setTopDisplay(top,loginUserId,isDraft);
            // ??????????????????
            if (isUpdate) {
                tempDO.setHbtId(hbtId);
                histBulletinBoardTempService.updateById(tempDO);
            // ??????????????????
            } else {
               tempDO.setHbtId(1L);
               histBulletinBoardTempService.save(tempDO);
               // ????????????????????????id??????
                hbtId = tempDO.getHbtId();
            }
            // ???????????????????????????????????????????????????
            uploadTempAttachments(hbtId,uploadFiles,isUpdate,dto.getDeleteAttachmentIdList(),false);
        }
        // ?????????????????????????????????????????????
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
            setTopDisplay(top,loginUserId,isDraft);
            // ?????????????????????????????????idList
            HistBulletinBoardUserDO boardUserDO = new HistBulletinBoardUserDO();
            boardUserDO.setHbgCuserids(dto.getEmpRangeIds());
            boardUserDO.setHbgCarticleid(articleId);
            boardUserDO.setHbgCmodifieruserid(loginUserId);
            boardUserDO.setHbgDmodifieddate(now);
            boardUserDO.setHbgCrangeTypes(dto.getRangeTypes());
            histBulletinBoardUserService.save(boardUserDO);

            // ???????????????????????????????????????
            if (!isUpdate) {
                uploadOfficialAttachments(articleId,uploadFiles,false,dto.getDeleteAttachmentIdList(),false,hbtId);
            // ???????????????????????????????????????
            } else {
                uploadOfficialAttachments(articleId,uploadFiles,true,dto.getDeleteAttachmentIdList(),false,hbtId);
                // ?????????????????????????????????????????????
                histBulletinBoardTempService.removeById(hbtId);
            }
        }
    }

    private void setTopDisplay(String top, String loginUserId, boolean isDraft) {
        if (StrUtil.equals(top,"1")) {
           // ??????????????????????????????????????????????????????????????????????????????????????????????????????display??????
           if (isDraft) {
               QueryWrapper<HistBulletinBoardTempDO> qw = SysUtil.query();
               qw.eq("HBT_CMODIFIERUSERID",loginUserId);
               List<HistBulletinBoardTempDO> tempNoticeList = histBulletinBoardTempService.list(qw);
               if (CollUtil.isNotEmpty(tempNoticeList)){
                   tempNoticeList.forEach(item -> item.setHbtCheaddisp("0"));
                   histBulletinBoardTempService.updateBatchById(tempNoticeList);
               }
           } else {
               QueryWrapper<HistBulletinBoardDO> qw = SysUtil.query();
               qw.eq("HB_CMODIFIERUSERID",loginUserId);
               List<HistBulletinBoardDO> noticeList = histBulletinBoardService.list(qw);
               if (CollUtil.isNotEmpty(noticeList)){
                   noticeList.forEach(item -> item.setHbCheaddisp("0"));
                   histBulletinBoardService.updateBatchById(noticeList);
               }
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
    @Transactional(rollbackFor = GlobalException.class)
    public NoticeVO getNoticeDetail(Long id) {
        HistBulletinBoardDO boardDO = histBulletinBoardService.getById(id);
        if (boardDO == null) {
            throw new GlobalException("???????????????????????????????????????");
        }
        String userId = SecurityUtil.getUserId();
        QueryWrapper<HistBulletinBoardUserDO> qw = new QueryWrapper<>();
        qw.eq("HBG_CARTICLEID",id);
        HistBulletinBoardUserDO userRange = histBulletinBoardUserService.getOne(qw);
        if (userRange == null) {
            throw new GlobalException("??????????????????????????????????????????");
        }
        // ????????????????????????????????????
        if (!StrUtil.contains(userRange.getHbgCuserids(),userId)) {
            throw new GlobalException("??????????????????????????????????????????");
        }
        String rangeTypesStr = userRange.getHbgCrangeTypes();
        if (StrUtil.isBlank(rangeTypesStr)){
            throw new GlobalException("?????????????????????????????????????????????");
        }
        List<String> rangeTypes = Arrays.asList(rangeTypesStr.split(","));
        List<NoticeRangeDTO> rangeTypeList = CollUtil.newArrayList();
        assembleTypeRangeItems(rangeTypes, rangeTypeList);

        NoticeVO noticeVO = new NoticeVO();
        BeanUtil.copyProperties(boardDO,noticeVO);
        noticeVO.setTypeRangeList(rangeTypeList);
        noticeVO.setUpdateDate(boardDO.getHbDmodifieddate());
        List<HistBulletinBoardFileDO> attachments = histBulletinBoardFileService.listFileById(id);
        noticeVO.setAttachmentList(attachments);
        List<Map<String,String>> tempEmpRangeList = CollUtil.newArrayList();

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
        // ??????????????????
        boolean hasRead = histBulletinBoardReadStatusService.checkNoticeHasRead(userId,id);
        if (!hasRead){
            HistBulletinBoardReadStatusDO readStatusDO = new HistBulletinBoardReadStatusDO();
            readStatusDO.setHbIdFk(id);
            readStatusDO.setHbrsEmpIdFk(userId);
            readStatusDO.setHbrsStatus(true);
            histBulletinBoardReadStatusService.save(readStatusDO);
        }
        return noticeVO;
    }

    @Override
    public DraftNoticeVO getDraftNoticeDetail(Long id) {
        HistBulletinBoardTempDO tempDO = histBulletinBoardTempService.getById(id);
        if (tempDO == null) {
            throw new GlobalException("??????????????????????????????");
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
            throw new GlobalException("?????????????????????????????????????????????????????????");
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
            throw new GlobalException("?????????????????????????????????????????????");
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

    // ?????????????????????????????????????????????????????????????????????
    public void uploadOfficialAttachments(Long articleId,List<MultipartFile> uploadFiles,boolean isUpdate,List<Long> deleteAttachmentIdList,boolean randomName,Long draftId) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil(randomName);
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (!isUpdate) {
            if (CollUtil.isNotEmpty(uploadFiles)) {
                addNewAttachments(articleId, uploadFiles, uploadFileUtil,false);
            }
        } else {
            // ????????????????????????????????????
            List<HistBulletinBoardTempFileDO> tempFileList = histBulletinBoardTempFileService.listFileById(draftId);
            // ??????????????????????????????????????????????????????????????????????????????????????????
            if (CollUtil.isEmpty(tempFileList)) {
                if (CollUtil.isNotEmpty(uploadFiles)) {
                    addNewAttachments(articleId, uploadFiles, uploadFileUtil,false);
                }
            } else {
                List<Long> historyFileIdList = tempFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfId).collect(Collectors.toList());
                // ??????????????????????????????????????????????????????id
                List<Long> deletedTempFileIdList = CollUtil.newArrayList(CollUtil.intersection(historyFileIdList, deleteAttachmentIdList));
                if (CollUtil.isNotEmpty(deletedTempFileIdList)) {
                    // ??????id????????????????????????,????????????????????????????????????
                    List<HistBulletinBoardTempFileDO> deletedTempFileList =  histBulletinBoardTempFileService.listByIds(deletedTempFileIdList);
                    if (CollUtil.isNotEmpty(deletedTempFileList)) {
                        List<String> realPathList = deletedTempFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfFileRealPath).collect(Collectors.toList());
                        realPathList.forEach(uploadFileUtil::removePreFile);
                        histBulletinBoardTempFileService.removeByIds(deletedTempFileIdList);
                    }
                }
                // ????????????????????????????????????????????????????????????tempFile??????????????????????????????
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
                // ????????????????????????????????????
                List<Long> deleteTempStorageFileIdList = tempStorageFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfId).collect(Collectors.toList());
                histBulletinBoardTempFileService.removeByIds(deleteTempStorageFileIdList);
                // ??????????????????????????????????????????????????????????????????
                if (CollUtil.isNotEmpty(uploadFiles)) {
                    addNewAttachments(articleId, uploadFiles, uploadFileUtil,false);
                }
            }
        }
    }

    /**
     * ??????????????????(??????)
     * @param articleId ???????????????id
     * @param uploadFiles ????????????
     * @param isUpdate ??????????????????????????????
     */
    public void uploadTempAttachments(Long articleId, List<MultipartFile> uploadFiles, boolean isUpdate, List<Long> deleteAttachmentIdList,boolean randomName) {
        UploadFileUtil uploadFileUtil = new UploadFileUtil(randomName);
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (!isUpdate) {
            if (CollUtil.isNotEmpty(uploadFiles)) {
                addNewAttachments(articleId, uploadFiles, uploadFileUtil,true);
            }
        }
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        else {
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
            List<HistBulletinBoardTempFileDO> oldFileList = histBulletinBoardTempFileService.listFileById(articleId);
            if (CollUtil.isEmpty(oldFileList)) {
                if (CollUtil.isNotEmpty(uploadFiles)) {
                    addNewAttachments(articleId, uploadFiles, uploadFileUtil,true);
                }
            }
            // ????????????????????????????????????,?????????????????????????????????????????????????????????????????????deleteAttachmentIdStr???keepAttachmentIdStr????????????????????????????????????????????????
            else {
               List<Long> historyFileIdList = oldFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfId).collect(Collectors.toList());
               // ??????????????????????????????????????????????????????id
               List<Long> deletedTempFileIdList = CollUtil.newArrayList(CollUtil.intersection(historyFileIdList, deleteAttachmentIdList));
               if (CollUtil.isNotEmpty(deletedTempFileIdList)) {
                   // ??????id????????????????????????,????????????????????????????????????
                   List<HistBulletinBoardTempFileDO> deletedTempFileList =  histBulletinBoardTempFileService.listByIds(deletedTempFileIdList);
                   if (CollUtil.isNotEmpty(deletedTempFileList)) {
                       List<String> realPathList = deletedTempFileList.stream().map(HistBulletinBoardTempFileDO::getHbtfFileRealPath).collect(Collectors.toList());
                        realPathList.forEach(uploadFileUtil::removePreFile);
                        histBulletinBoardTempFileService.removeByIds(deletedTempFileIdList);
                   }
               }
               // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
               if (CollUtil.isNotEmpty(uploadFiles)) {
                   addNewAttachments(articleId, uploadFiles, uploadFileUtil,true);
               }
            }
        }
    }

    // ????????????
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
                fileDO.setHbtfValid(true);
                uploadBoardTempFileList.add(fileDO);
            } else {
                HistBulletinBoardFileDO fileDO = new HistBulletinBoardFileDO();
                fileDO.setHbIdFk(articleId);
                fileDO.setHbfValid(true);
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
     * ???????????????????????????
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
                rangeDTO.setTypeName("???????????????");
            } else if (StrUtil.equals(FG_COMP_SEC_POST, rangeTypeId)) {
                rangeDTO.setTypeName("?????????????????????");
            } else if (StrUtil.equals(FG_COMP_SEC_BOSS, rangeTypeId)) {
                rangeDTO.setTypeName("????????????????????????");
            } else if (StrUtil.equals(FG_COMP_POST, rangeTypeId)) {
                rangeDTO.setTypeName("???????????????");
            } else if (StrUtil.equals(FG_COMP_EMP, rangeTypeId)) {
                rangeDTO.setTypeName("???????????????");
            }
            typeRangeList.add(rangeDTO);
        }
    }

    // ?????????????????????????????????group
    private List<LoginGroupBO> getPublishGroupList() {
        HttpSession session = ContextUtil.getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        return psSession.getLoginGroups().get("01").stream().filter(LoginGroupBO::getPublishing).collect(Collectors.toList());
    }

    /**
     * ??????group?????????typeId?????????????????????
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
                    // ??????????????????
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
                              log.info("???????????????{}",partEmpIds);
                    }
                }
                if (CollUtil.isNotEmpty(partEmpIds)){
                    CollUtil.addAllIfNotContains(empIdResult,partEmpIds);
                }
            }
        }
    }

}
