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
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 名称マスタ明細データ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGenericDetailMapper extends BaseMapper<MastGenericDetailDO> {

        /**
         * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
         */
        List<MgdNtfTypeDispAppVo> selectMasterTmgNtfTypeDispAppList(Map<String, Object> map);

        /**
         * mast_generic_detail 内容取り
         */
        List<Map<String,Object>> selectGenericDetail(@Param("sql")String sql);


        /**
         * 指定された申請種類の表示タイプを取得する
         */
        int selectNtfDispType(Map<String, Object> map);

        /**
         * 「HH:MI60」形式の文字列を分(Minute)情報に変換
         */
        Long tmgFConvHhmi2Min (Map<String, Object> map);


        /**
         * 処理用の型に変換
         * 引数は月曜～日曜を0または1で渡す
         */
        Long toDayofWeek (@Param("sql")String sql);




        /**
         * 決裁レベル取得SQL返却メソッド
         */
        String selectApprovelLevel(Map<String, Object> map);



    /**
     * 月次情報表示項目を検索
     */
    List<ItemVO> buildSQLForSelectTmgDispMonthlyItems(Map<String, Object> map);


    /**
     * 日次情報表示項目を検索
     */
    List<ItemVO> buildSQLForSelectTmgDispDailyItems(Map<String, Object> map);

    /**
     * 名称マスタから属性コードを取得
     */
    List<MgdAttributeVO> buildSQLForSelectgetMgdAttribute(Map<String, Object> map);

    /**
     * 予備項目4を取得「0:出勤日,それ以外は出勤日ではない」
     */
    List<MgdCsparechar4VO> buildSQLSelectGetMgdCsparechar4(Map<String, Object> map);

    /**
     * 就業区分マスタを取得する
     */
    List<GenericDetailVO> buildSQLForSelectGenericDetail(Map<String, Object> map);

    /**
     * 名称マスタから属性コードを取得
     */
    List<GenericDetailVO> buildSQLForSelectgetMgdDescriptions(Map<String, Object> map);

    /**
     * 申請画面用 申請区分マスタ取得SQL
     */
    List<MgdTmgNtfTypeVo> selectMasterTmgNtfType(Map<String, Object> map);

    /**
     * 年度を取得する
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
     * 承認状況欄へ表示するヘッダー名称・select句・表示順をTMG_DISPPERMSTATLISTマスタより取得
     */
    List<ItemVO> buildSQLForSelectTmgDisppermstatlist(@Param("custID") String custID,
                                                      @Param("compID") String compID,
                                                      @Param("lang") String lang);

    /**
     * CSV出力ヘッダー・項目取得
     */
    List<ItemVO> buildSQLForSelectTmgDeptstatcsvitems(@Param("custID") String custID,
                                                      @Param("compID") String compID,
                                                      @Param("lang") String lang,
                                                      @Param("targetDate") String targetDate);
    /**
     * 表示項目のヘッダー・職員毎select句・部署別合計用select句・テーブルセル幅の項目取得
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
}

