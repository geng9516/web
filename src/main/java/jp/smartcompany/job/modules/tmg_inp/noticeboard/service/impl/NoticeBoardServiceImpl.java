package jp.smartcompany.job.modules.tmg_inp.noticeboard.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.core.pojo.bo.GroupBaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.service.IMastGroupbasesectionService;
import jp.smartcompany.job.modules.core.service.IMastGroupsectionpostmappingService;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.INoticeBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardServiceImpl implements INoticeBoardService {

    private final IMastGroupsectionpostmappingService groupsectionpostmappingService;
    private final IMastGroupbasesectionService baseSectionService;
    private final DataSource dataSource;

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
    public List<String> getValidReadEmpList(List<String> typeIds,HttpSession session) {
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        if (!psSession.getHasPublishPermission()) {
            throw new GlobalException("発表権限なし");
        }
        Date date = DateUtil.date();
        List<String> groupIds = psSession.getLoginGroups().get("01").stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
        List<String> sectionIds = groupsectionpostmappingService.selectCompanySectionIdList(groupIds,FG_COMP_SEC);
        List<String> empIdResult = CollUtil.newArrayList();
        for (String groupId : groupIds) {
             for (String typeId : typeIds) {
                List<String> partEmpIds = CollUtil.newArrayList();
                if (StrUtil.equals(FG_COMP_SEC,typeId)) {
                     partEmpIds = groupsectionpostmappingService.selectEmpIdByCompanySectionIdList(sectionIds,groupId,FG_COMP_SEC);
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
                    List<GroupBaseSectionBO> baseSectionList = baseSectionService.getBaseSectionByGroupCode("01", "01", groupId,date);
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
                        String executeSql = " SELECT" +
                                "          HD_CEMPLOYEEID_CK" +
                                "        FROM" +
                                "             HIST_DESIGNATION d,TMG_EMPLOYEES e,MAST_ORGANISATION o" +
                                "        WHERE" +
                                "              d.HD_CCUSTOMERID_CK = '01'" +
                                "              AND d.HD_CCOMPANYID_CK = '01'" +
                                "              AND d.HD_DSTARTDATE_CK <= TRUNC(SYSDATE)" +
                                "              AND d.HD_DENDDATE >= TRUNC(SYSDATE)" +
                                "              AND d.HD_CIFKEYORADDITIONALROLE = '0'" +
                                "              AND d.HD_NOFFCIALORNOT = 0" +
                                "              AND d.HD_NOFFICIALORNOT = 0" +
                                "              AND e.TEM_CEMPLOYEEID(+) = d.HD_CEMPLOYEEID_CK" +
                                "              AND e.TEM_DSTARTDATE(+) <= TRUNC(SYSDATE)" +
                                "              AND e.TEM_CCOMPANYID(+) = d.HD_CCOMPANYID_CK" +
                                "              AND e.TEM_CCUSTOMERID(+) = d.HD_CCUSTOMERID_CK" +
                                "              AND e.TEM_DENDDATE(+) >= TRUNC(SYSDATE)" +
                                "              AND 'TMG_MANAGEFLG|0' <> TMG_F_IS_MANAGEFLG(" +
                                "                e.TEM_CEMPLOYEEID," +
                                "                TRUNC(SYSDATE)," +
                                "                LAST_DAY(TRUNC(SYSDATE)), e.TEM_CCUSTOMERID,e.TEM_CCOMPANYID)" +
                                "              AND o.MO_CCUSTOMERID_CK_FK = D.HD_CCUSTOMERID_CK" +
                                "              AND o.MO_CCOMPANYID_CK_FK = D.HD_CCOMPANYID_CK" +
                                "              AND o.MO_CSECTIONID_CK = D.HD_CSECTIONID_FK" +
                                "              AND o.MO_CLANGUAGE = 'ja'" +
                                "              AND o.MO_DSTART <= TRUNC(SYSDATE)" +
                                "              AND o.MO_DEND >= TRUNC(SYSDATE)" +
                                "              AND e.TEM_CWORKTYPEID NOT IN ('TMG_WORKERTYPE|00')" +
                                "              AND (" +
                                sql.toString()+
                                ")";
                          try(Connection conn = dataSource.getConnection()) {
                              List<Entity> entityList = SqlExecutor.query(conn,executeSql,new EntityListHandler());
                              List<String> tmpEmpIds = CollUtil.newArrayList();
                              entityList.forEach(entity -> tmpEmpIds.add((String)entity.get("HD_CEMPLOYEEID_CK")));
                              partEmpIds = tmpEmpIds;
                              log.info("处理结果：{}",partEmpIds);
                          } catch (SQLException e) {
                              e.printStackTrace();
                              throw new GlobalException(e.getMessage());
                          }
                    }
                }
                if (CollUtil.isNotEmpty(partEmpIds)){
                    CollUtil.addAllIfNotContains(empIdResult,partEmpIds);
                }
            }
        }
        return empIdResult;
    }

}
