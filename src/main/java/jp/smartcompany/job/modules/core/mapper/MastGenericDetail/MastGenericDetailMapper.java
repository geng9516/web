package jp.smartcompany.job.modules.core.mapper.MastGenericDetail;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.dto.GenericDetailItemDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.tmg.deptstatlist.dto.DispItemsDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmpAttsetDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmploymentWithMgdVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.MgdTimeLimitVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetDateLimit;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.MoDLTypeVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoTableFunctionVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.DispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.NewBulkdropDownVo;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerSheetVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.WorkTypeDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.DateDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdNtfTypeDispAppVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdTmgNtfTypeVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.GenericDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdAttributeVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdCsparechar4VO;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.entity.TmgNtfCheckDo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.GroupVo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.NtfDispVo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.TypeGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ?????????????????????????????? Mapper ??????
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGenericDetailMapper extends BaseMapper<MastGenericDetailDO> {

        /**
         * ??????/???????????????]????????????/?????????????????????????????????
         */
        List<MgdNtfTypeDispAppVo> selectMasterTmgNtfTypeDispAppList(Map<String, Object> map);

        /**
         * mast_generic_detail ????????????
         */
        List<Map<String,Object>> selectGenericDetail(@Param("sql")String sql);


        /**
         * ????????????????????????????????????????????????????????????
         */
        int selectNtfDispType(Map<String, Object> map);

        /**
         * ???HH:MI60???????????????????????????(Minute)???????????????
         */
        Long tmgFConvHhmi2Min (Map<String, Object> map);


        /**
         * ????????????????????????
         * ???????????????????????????0?????????1?????????
         */
        Long toDayofWeek (@Param("sql")String sql);




        /**
         * ?????????????????????SQL??????????????????
         */
        String selectApprovelLevel(Map<String, Object> map);



    /**
     * ?????????????????????????????????
     */
    List<ItemVO> buildSQLForSelectTmgDispMonthlyItems(Map<String, Object> map);


    /**
     * ?????????????????????????????????
     */
    List<ItemVO> buildSQLForSelectTmgDispDailyItems(Map<String, Object> map);

    /**
     * ?????????????????????????????????????????????
     */
    List<MgdAttributeVO> buildSQLForSelectgetMgdAttribute(Map<String, Object> map);

    /**
     * ????????????4????????????0:?????????,???????????????????????????????????????
     */
    List<MgdCsparechar4VO> buildSQLSelectGetMgdCsparechar4(Map<String, Object> map);

    /**
     * ????????????????????????????????????
     */
    List<GenericDetailVO> buildSQLForSelectGenericDetail(Map<String, Object> map);

    /**
     * ?????????????????????????????????????????????
     */
    List<GenericDetailVO> buildSQLForSelectgetMgdDescriptions(Map<String, Object> map);

    /**
     * ??????????????? ???????????????????????????SQL
     */
    List<MgdTmgNtfTypeVo> selectMasterTmgNtfType(Map<String, Object> map);

    /**
     * ?????????????????????
     */
    int selectYear(@Param("custId")String custId,
                   @Param("compId")String compId);


    DateDto selectDate(@Param("custId")String custId,
                       @Param("compId")String compId,
                       @Param("year")int year,
                       @Param("baseDate")String baseDate);

    List<DispOverTimeItemsDto> selectDispOverTimeItems(@Param("custId")String custID,
                                                       @Param("compID")String compID,
                                                       @Param("baseDate")String baseDate,
                                                       @Param("language")String language);

    /**
     * ???????????????????????????????????????????????????select??????????????????TMG_DISPPERMSTATLIST?????????????????????
     */
    List<ItemVO> buildSQLForSelectTmgDisppermstatlist(@Param("custID") String custID,
                                                      @Param("compID") String compID,
                                                      @Param("lang") String lang);

    /**
     * CSV?????????????????????????????????
     */
    List<ItemVO> buildSQLForSelectTmgDeptstatcsvitems(@Param("custID") String custID,
                                                      @Param("compID") String compID,
                                                      @Param("lang") String lang,
                                                      @Param("targetDate") String targetDate);
    /**
     * ???????????????????????????????????????select????????????????????????select??????????????????????????????????????????
     */
    List<DispItemsDto> buildSQLForSelectTmgDispdeptStatlist(@Param("custID") String custID,
                                                            @Param("compID") String compID,
                                                            @Param("lang") String lang,
                                                            @Param("targetDate") String targetDate);


    TargetDateLimit selectTargetFiscalYear(@Param("custID") String custID,
                                           @Param("compID") String compID,
                                           @Param("baseDate") String baseDate);


    List<MoDLTypeVo> selectTmgMoDLType(@Param("custId") String custId,
                                       @Param("compId") String compId,
                                       @Param("lang") String lang,
                                       @Param("date") String date);


    List<TmgMoTableFunctionVo> selectTmgMoTableFunction(@Param("custID")String custID,
                                                        @Param("compID")String compID,
                                                        @Param("lang")String lang,
                                                        @Param("date")String date,
                                                        @Param("masterCD")String masterCD);

    List<String> selectTmgMoRetroLayout(@Param("custId")String custId,
                                        @Param("compId")String compId,
                                        @Param("lang")String lang,
                                        @Param("targetDate")String targetDate,
                                        @Param("dlTypeID")String dlTypeID);

    String selectTmgMoCsvFileName(@Param("custId")String custId,
                                  @Param("compId")String compId,
                                  @Param("empId")String empId,
                                  @Param("targetDate")String targetDate,
                                  @Param("dlTypeID")String dlTypeID);

    List<EmpAttsetDispVo> selectEmpAttsetDisp(@Param("custId")String custId,
                                              @Param("compId")String compId,
                                              @Param("baseDate")String baseDate,
                                              @Param("lang")String lang);

    MgdTimeLimitVo selectMgdTimeLimit();

    String selectWeekDaysCom(@Param("custId") String custId,
                             @Param("compId")String compId,
                             @Param("baseDate")String baseDate,
                             @Param("daysOfWeeks")int daysOfWeeks,
                             @Param("allMinutes")int allMinutes);

    List<EmploymentWithMgdVo> selectDateOfEmploymentWithMGD(@Param("custId")String custId,
                                                      @Param("compId")String compId,
                                                      @Param("lang")String lang,
                                                      @Param("empId")String empId,
                                                      @Param("groupId")String groupId);

    int insertMgdKinmuStart(@Param("custId")String custId,
                            @Param("compId")String compId,
                            @Param("targetUser")String targetUser,
                            @Param("userCode")String userCode,
                            @Param("baseDate")String baseDate,
                            @Param("startDate")String startDate,
                            @Param("endDate")String endDate,
                            @Param("beginDate")String beginDate);

    List<LedgerSheetVo> selectLedgerSheetList(@Param("custID")String custID,
                                              @Param("compCode")String compCode,
                                              @Param("language")String language);

    String selectWorkerTypeName(@Param("custId")String custId,
                                @Param("compId")String compId,
                                @Param("empid")String empid,
                                @Param("baseDate")String baseDate);

    String selectMasterCode(@Param("custId")String custId,
                            @Param("compId")String compId,
                            @Param("baseDate")String baseDate,
                            @Param("masterCode")String masterCode);

    String selectBulkTimeRange(@Param("custID")String custID,
                               @Param("compCode")String compCode,
                               @Param("baseDate")String baseDate);

    List<NewBulkdropDownVo> selectBulkdropDown(@Param("custID")String custID,
                                         @Param("compCode")String compCode,
                                         @Param("baseDate")String baseDate);

    List<WorkTypeDto> selectWorkerType4Variational(@Param("custID")String custID,
                                                   @Param("compCode")String compCode,
                                                   @Param("baseDate")String baseDate);

    List<WorkTypeDto> selectWorkerType4Flex(@Param("custID")String custID,
                                            @Param("compCode")String compCode,
                                            @Param("baseDate")String baseDate);

    @Select("SELECT CASE WHEN MIN(MGD_DSTART_CK) IS NULL THEN null ELSE MIN(MGD_DSTART_CK) - 1 END MaxEndDate FROM MAST_GENERIC_DETAIL WHERE MGD_CCUSTOMERID = '01' AND MGD_CCOMPANYID_CK_FK = '01' AND MGD_CGENERICGROUPID = #{groupId} AND MGD_CGENERICDETAILID_CK = #{detailId} AND MGD_CLANGUAGE_CK = 'ja' AND MGD_DSTART_CK > #{searchDate}")
    Date selectMaxEndDate(@Param("groupId") String groupId,@Param("detailId") String detailId,@Param("searchDate") String searchDate);

    @Select("SELECT MGD_CMASTERCODE,MGD_CGENERICDETAILDESC,MGD_CSPARECHAR1,MGD_CSPARECHAR2,MGD_CSPARECHAR3,MGD_CSPARECHAR4,MGD_CSPARECHAR5,MGD_NSPARENUM1,MGD_NSPARENUM2," +
            "MGD_NSPARENUM3,MGD_NSPARENUM4,MGD_NSPARENUM5,MGD_DSPAREDATE1,MGD_DSPAREDATE2,MGD_DSPAREDATE3,MGD_DSPAREDATE4,MGD_DSPAREDATE5 FROM MAST_GENERIC_DETAIL " +
            "WHERE MGD_DSTART_CK <= TRUNC(SYSDATE) " +
            "AND MGD_DEND >= TRUNC(SYSDATE) " +
            "AND MGD_CCUSTOMERID = '01' " +
            "AND MGD_CCOMPANYID_CK_FK = '01' " +
            "AND MGD_CLANGUAGE_CK = 'ja' " +
            "AND MGD_CGENERICGROUPID = #{value} " +
            "ORDER BY MGD_CMASTERCODE")
    List<GenericDetailItemDTO> selectByDetailGroupId(String detailGroupId);

    @Select("select MGD_CWORKID FROM TMG_V_MGD_HOLFLG WHERE MGD_CHOLFLG = #{value}")
    String getHolFlg(String kubunid);

    @Select("SELECT mast_generic_detail_seq.NEXTVAL FROM dual")
    String getMgdSeq();

    @Select("SELECT MAX(MGD_CGENERICDETAILID_CK) + 10 FROM SPWM.MAST_GENERIC_DETAIL WHERE MGD_CGENERICGROUPID = #{value}")
    String getMgdDetailId(String groupId);

    @Select("SELECT count(*) FROM MAST_GENERIC_DETAIL WHERE MGD_CMASTERCODE = #{value}")
    int existMgdMastCode(String mastCode);

    @Select("SELECT count(*) FROM MAST_GENERIC_DETAIL WHERE MGD_CGENERICDETAILDESC = #{desc} and (#{start}  between MGD_DSTART_CK and   MGD_DEND  or  #{end} between MGD_DSTART_CK and   MGD_DEND)")
    int existMgdDesc(@Param("desc")String desc, @Param("start")String start, @Param("end")String end);

    @Select("SELECT MGD_CGENERICDETAILDESC name,MGD_CMASTERCODE id,MGD_NSPARENUM1 sort " +
            "FROM MAST_GENERIC_DETAIL " +
            "WHERE MGD_CGENERICGROUPID = 'TMG_NTFGROUP' " +
            "AND #{value} BETWEEN MGD_DSTART_CK AND MGD_DEND ")
    List<GroupVo> getNTFGroup(String sysdate);


    List<NtfDispVo> getNtfTypeDetail(@Param("ntfGroup")String ntfGroup, @Param("sysdate")String sysdate);

    List<TypeGroupVo> getNTFTypeGroup(@Param("sysdate") String sysdate);

    @Select("SELECT " +
            " MGD_CMASTERCODE workTypeId," +
            " MGD_CGENERICDETAILDESC workType" +
            " FROM MAST_GENERIC_DETAIL " +
            " WHERE MGD_CGENERICGROUPID = 'TMG_WORKERTYPE' " +
            " and #{value}  between MGD_DSTART_CK and  MGD_DEND ")
    List<Map<String, Object>> getTotalWorkType(String sysdate);

    @Select("select" +
            "    MGD_CMASTERCODE  timeTypeId ," +
            "    MGD_CGENERICDETAILDESC timeType" +
            " from" +
            "     MAST_GENERIC_DETAIL tmgwork" +
            " where" +
            "      tmgwork.MGD_CGENERICGROUPID = 'TMG_WORK'" +
            "  AND tmgwork.MGD_CSPARECHAR5 ='0'" +
            " AND #{value}  between MGD_DSTART_CK AND  MGD_DEND ")
    List<Map<String, Object>> getDayTimeType(String sysdate);


    @Select("select" +
            "    MGD_CMASTERCODE timeTypeId," +
            "    MGD_CGENERICDETAILDESC timeType" +
            " from " +
            "    MAST_GENERIC_DETAIL notwork" +
            " where " +
            "      notwork.MGD_CGENERICGROUPID = 'TMG_NOTWORK'" +
            "  AND notwork.MGD_NSPARENUM2 = 1 " +
            "  AND #{value}  between MGD_DSTART_CK AND  MGD_DEND ")
    List<Map<String, Object>> getHourTimeType(String sysdate);


    @Select("select" +
            "    count(*) " +
            " from " +
            "    MAST_GENERIC_DETAIL " +
            " where " +
            "      MGD_CGENERICDETAILDESC = #{ntfName} " +
            "  AND MGD_CGENERICGROUPID =#{type} "+
            "  AND #{sysdate}  between MGD_DSTART_CK AND  MGD_DEND ")
    int checkNtfName(@Param("ntfName") String ntfName,@Param("sysdate") String sysdate,@Param("type") String type);
}

