package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmpAttsetDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmploymentWithMgdVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.MgdTimeLimitVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetDateLimit;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.MoDLTypeVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoTableFunctionVo;
import jp.smartcompany.job.modules.tmg.deptstatlist.dto.DispItemsDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.DispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.NewBulkdropDownVo;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerSheetVo;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ExcludecondCtlDto;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationMasterDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.DateDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdNtfPropVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdNtfTypeDispAppVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdTmgNtfTypeVo;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgDispItemsDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.GenericDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdAttributeVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdCsparechar4VO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 非常勤年休ルールを取得
     *
     * @param map 検索条件
     * @return int 年休ルール
     */
    int selectNenkyuRuleH(Map<String, Object> map);

    /**
     * 非常勤年休ルールを取得
     *
     * @param map 検索条件
     * @return int 年休ルール
     */
    int selectNenkyuRuleH2(Map<String, Object> map);

    /**
     * 常勤年休ルールを取得
     *
     * @param map 検索条件
     * @return int 年休ルール
     */
    int selectNenkyuRuleT(Map<String, Object> map);


    /**
     * 名称マスタを取得
     *
     * @param map 検索条件
     * @return MastGenericDetailDO 名称マスタ
     */
    MastGenericDetailDO selectMastGenericDetailDO(Map<String, Object> map);

    /**
     * ワークタイプのデフォルトパターンを検索
     */
    String selectWorkPattern(Map<String, Object> map);

        /**
         * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
         */
        List<TmgDispItemsDto> selectDispDailyItems(Map<String, Object> map);


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
     * 名称マスタから属性コードを取得(エフォート対象者判定用)
     */
    List<MgdAttributeVO> buildSQLForSelectgetMgdAttributeEffort(Map<String, Object> map);

    /**
     * 予備項目4を取得「0:出勤日,それ以外は出勤日ではない」
     */
    List<MgdCsparechar4VO> buildSQLSelectGetMgdCsparechar4(Map<String, Object> map);

    /**
     * 就業区分マスタを取得する
     */
    List<GenericDetailVO> buildSQLForSelectGenericDetail(Map<String, Object> map);

    /**
     * 各コメント欄の最大値を名称マスタ詳細より取得
     */
    String buildSQLForSelectTmgVMgdMaxLengthCheck(Map<String, Object> map);


    /**
     * 名称マスタから属性コードを取得
     */
    List<GenericDetailVO> buildSQLForSelectgetMgdDescriptions(Map<String, Object> map);

    /**
     * 申請画面用 申請区分マスタ取得SQL
     */
    List<MgdTmgNtfTypeVo> selectMasterTmgNtfType(Map<String, Object> map);

    /**
     * 画面項目名称の設定マスタ
     */
    MgdNtfPropVo selectMasterNtfProp(Map<String, Object> map);

    /**
     * 現在日付を取得するクエリ文を生成します
     */
    String selectSysdate();

    /**
     * 年度を取得する
     */
    int selectYear(@Param("custId")String custId,
                   @Param("compId")String compId);


    DateDto selectDate(@Param("custId")String custId,
                       @Param("compId")String compId,
                       @Param("year")int year,
                       @Param("baseDate")String baseDate);

    /**
     * 日付関連情報を取得
     */
    TodayThisMonthVO buildSQLForSelectDate();

    /**
     * 36協定における月の超勤限度時間表示用名称取得
    */
    String selectLimit(@Param("custId")String custId,
                       @Param("compId")String compId,
                       @Param("baseDate")String baseDate,
                       @Param("sLang")String sLang,
                       @Param("masterCode")String masterCode);


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


    List<SimulationMasterDto> buildSQLForSelectSimulationMaster(@Param("custID")String custID,
                                                                @Param("compCode")String compCode,
                                                                @Param("language")String language,
                                                                @Param("groupId")String groupId);

    List<Integer> buildSQLForSelectSumSimulationMaster(@Param("custID")String custID,
                                                 @Param("compCode")String compCode,
                                                 @Param("language")String language,
                                                 @Param("groupId")String groupId);

    List<ExcludecondCtlDto> buildSQLForSelectExcludecondCtl(@Param("custID")String custID,
                                                            @Param("compCode")String compCode,
                                                            @Param("language")String language);

    /**
     * 名称マスタ詳細情報に登録する
     */
    void buildSQLForInsertTmgDailyDetail(@Param("psCustId") String psCustId,
                                         @Param("psCompId") String psCompId,
                                         @Param("psLanguage") String psLanguage,
                                         @Param("psGroupCode") String psGroupCode,
                                         @Param("psDetailId") String psDetailId,
                                         @Param("psMasterCode") String psMasterCode,
                                         @Param("psStartDate") String psStartDate,
                                         @Param("psEndDate") String psEndDate,
                                         @Param("psUpdateUser") String psUpdateUser,
                                         @Param("psExuludecondType") String psExuludecondType,
                                         @Param("psExuludecondForm") String psExuludecondForm,
                                         @Param("psExuludecondTo") String psExuludecondTo);

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

    NewBulkdropDownVo selectBulkdropDown(@Param("custID")String custID,
                                         @Param("compCode")String compCode,
                                         @Param("baseDate")String baseDate);
}

