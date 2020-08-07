package jp.smartcompany.job.modules.core.service;


import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmpAttsetDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmploymentWithMgdVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.MgdTimeLimitVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetDateLimit;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.MoDLTypeVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoTableFunctionVo;
import jp.smartcompany.job.modules.tmg.deptstatlist.dto.DispItemsDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.DispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ExcludecondCtlDto;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationMasterDto;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerSheetVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.DateDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdNtfPropVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdNtfTypeDispAppVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdTmgNtfTypeVo;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgDispItemsDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 名称マスタ明細データ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 20200416
 */
public interface IMastGenericDetailService extends IService<MastGenericDetailDO> {

    /**
     * 常勤通常付与年休ルールを取得
     *
     * @param customerId    顧客コード
     * @param companyId     法人コード
     * @param employeeId    社員番号
     * @param yyyymmdd      基準日
     * @param beginDateWork 　開始日
     * @return int 年休ルール
     */
    int selectNenkyuRuleH(String customerId, String companyId, String employeeId
            , Date yyyymmdd, Date beginDateWork);


    /**
     * 汎用マスタから予備日付を取得
     *
     * @param customerId 　顧客コード
     * @param companyId  　法人コード
     * @param wsGroupId  　グループコード
     * @param wsDetailId 　名称コード
     * @param language   　言語
     * @param wdKijun    　基準日
     * @return MastGenericDetailDO 名称マスタD0
     */
    MastGenericDetailDO selectMastGenericDetailDO(String customerId, String companyId, String wsGroupId, String wsDetailId, String language, Date wdKijun);

    /**
     * ワークタイプのデフォルトパターンを検索
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param yyyymmdd   基準日
     * @param workerType 　ワークタイプ
     * @return String パターン
     */
    String selectWorkPattern(String customerId, String companyId, Date yyyymmdd, String workerType);

    // ＊＊＊＊＊＊ここから＊＊＊＊＊＊＊

    /**
     * TMG_DISPMONTHLYITEMSマスタより取得した月次情報のヘッダー・SQLを取得する
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 　対象日
     * @return List
     */
    List<ItemVO> buildSQLForSelectTmgDispMonthlyItems(String custID, String compID, String lang, String targetDate);


    /**
     * TMG_DISPDAILYITEMSマスタより取得した日次情報のヘッダー・SQL・表示幅を取得する
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 　対象日
     * @return List
     */
    List<ItemVO> buildSQLForSelectTmgDispDailyItems(String custID, String compID, String lang, String targetDate);

    /**
     * 　名称マスタから属性コードを取得
     *
     * @param custID       顧客コード
     * @param compCode     法人コード
     * @param targetUser   対象者
     * @param language     言語
     * @param siteId       　サイトID
     * @param day          　対象日
     * @param attribute    　属性コードの使用可否
     * @param categoryCode 　検索対象のカテゴリコード
     * @return List<MgdAttributeVO>
     */
    List<MgdAttributeVO> buildSQLForSelectgetMgdAttribute(String custID, String compCode, String targetUser, String language,
                                                          String siteId, String day, String attribute, String categoryCode);

    /**
     * 名称マスタから属性コードを取得(エフォート対象者判定用)
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param targetUser 対象者
     * @param language 言語
     * @param siteId サイトID
     * @param day 対象日
     * @param month 対象月
     * @param type　種別
     * @param onOff　onOff
     * @param attribute　使用可否
     * @param categoryCode　カテゴリーID
     * @return List<MgdAttributeVO>
     */
    List<MgdAttributeVO> buildSQLForSelectgetMgdAttributeEffort(String custID, String compCode, String targetUser, String language,
                                                               String siteId, String day, String month, String type, String onOff,
                                                               String attribute, String categoryCode);

    /**
     * 予備項目4を取得「0:出勤日,それ以外は出勤日ではない」
     *
     * @param custCode 顧客コード
     * @param compCode 法人コード
     * @return List<MgdCsparechar4VO>
     */
    List<MgdCsparechar4VO> buildSQLSelectGetMgdCsparechar4(String custCode, String compCode);

    /**
     * 就業区分マスタを取得する
     *
     * @param custID     顧客コード
     * @param targetComp 法人コード
     * @param targetUser 対象者
     * @param day        　対象日
     * @param language   　言語
     * @return List<GenericDetailVO>
     */
    List<GenericDetailVO> buildSQLForSelectGenericDetail(String custID, String targetComp, String targetUser, String day, String language);

    /**
     * 各コメント欄の最大値を名称マスタ詳細より取得
     * @param custID 顧客コード
     * @param compID 法人コード
     * @param lang 言語
     * @param targetDate 対象日
     * @param masterCode マスタコード
     * @return String
     */
    String buildSQLForSelectTmgVMgdMaxLengthCheck(String custID, String compID, String lang, String targetDate, String masterCode);

    /**
     * 名称マスタから属性コードを取得
     *
     * @param custID
     * @param compCode
     * @param day
     * @param groupId
     * @return List<GenericDetailVO>
     */
    List<GenericDetailVO> buildSQLForSelectgetMgdDescriptions(String custID, String compCode, String day, String groupId);


    /**
    * 勤怠/名称マスタ]就業登録/承認・日次情報表示項目
    *
    * @param customerId    顧客コード
    * @param companyId     法人コード
    * @param yyyymmdd      基準日
    * @param language 　言語
    * @return String パターン
    */
    List<TmgDispItemsDto> selectDispDailyItems(String customerId, String companyId, Date yyyymmdd, String language);

    /**
    * 申請一覧画面用 申請区分マスタ取得SQL
    *
    * @param customerId    顧客コード
    * @param companyId     法人コード
    * @param yyyymmdd      基準日
    * @param language 　言語
    * @return String パターン
    */

    List<MgdNtfTypeDispAppVo> selectMasterTmgNtfTypeDispAppList(String customerId, String companyId, Date yyyymmdd, String language);

    /**
     * マスタを取得するSQL
     *

     * @param sql 　sql
     * @return map
     */

    List<Map<String,Object>> selectGenericDetail(String sql);



    /**
     * 指定された申請種類の表示タイプを取得する
     * @param customerId    顧客コード
     * @param companyId     法人コード
     * @param ntfId      申請種類
     * @param language 　言語
     * @return int
     */

   int selectNtfDispType(String customerId, String companyId, String language,String ntfId);

    /**
      * 「HH:MI60」形式の文字列を分(Minute)情報に変換
      * @param hhmi     「HH:MI60」形式の文字列
      * @return int
     */
   Long tmgFConvHhmi2Min(String hhmi);

     /**
      * 「HH:MI60」形式の文字列を分(Minute)情報に変換
      * @param mon
      * @param tue
      * @param wed
      * @param thu
      * @param fri
      * @param sat
      * @param sun
      * @return int
      */
     Long toDayofWeek(String mon,String tue,String wed,String thu,String fri,String sat,String sun);

    /**
     * 決裁レベル取得SQL返却メソッド
     * @param custId
     * @param compId
     * @param language
     * @param today
     * @param approvelLevel
     * @param piMode
     * @return String
     */
    String selectApprovelLevel(String custId,String compId,String language,String today,String approvelLevel,int piMode);

    /**
     * 申請画面用 申請区分マスタ取得SQL
     * @param custId
     * @param compId
     * @param baseDate
     * @param employeeId
     * @param language
     * @param siteId
     * @return String
     */
    List<MgdTmgNtfTypeVo> selectMasterTmgNtfType(String custId, String compId, String baseDate, String employeeId, String language, String siteId, String workType);

    /**
     * 画面項目名称の設定マスタ
     * @param custId
     * @param compId
     * @param language
     * @return String
     */
    MgdNtfPropVo selectMasterNtfProp(String custId, String compId, String language);

    /**現在日付を取得するクエリ文を生成します*/
    String selectSysdate();

    /**年度を取得するSQLを返す*/
    int selectYear(String custId,String compId);

    /**
     * 年度開始終了日を取得するSQLを返す
     */
    DateDto selectDate(String custId, String compId, int year, String baseDate);

    /**
     * 日付関連情報を取得
     *
     * @return TodayThisMonthVO
     */
    TodayThisMonthVO buildSQLForSelectDate();

    /**
     * 36協定における月の超勤限度時間表示用名称取得
     * */
    String selectLimit(String custId,String compId,String baseDate,String sLang,String masterCode);

    /**
     * 超過勤務命令情報表示項目ヘッダー・select句・表示順をTMG_DISPOVERTIMEINSTマスタより取得
     */
    List<DispOverTimeItemsDto> selectDispOverTimeItems(String custID, String compID, String baseDate,String language);

    /**
     * 承認状況欄へ表示するヘッダー名称・select句・表示順をTMG_DISPPERMSTATLISTマスタより取得
     *
     * @param custID 顧客コード
     * @param compID 法人コード
     * @param lang   言語
     * @return List<ItemVO>
     */
    List<ItemVO> buildSQLForSelectTmgDisppermstatlist(String custID, String compID, String lang);



    /**
     * 対象日の年度開始日および年度終了日を取得するSQLを構築して返します
     */
    TargetDateLimit selectTargetFiscalYear(String custID, String compID, String baseDate);


    /**
     * 月例/遡及データダウンロード画面用 DL種別・リンク名称を取得するクエリを返す
     * @param custId：顧客コード
     * @param compId：法人コード
     * @param lang：言語区分
     * @param date：基準日
     * @return
     */
    List<MoDLTypeVo> selectTmgMoDLType(String custId, String compId, String lang, String date);

    /**
     * 部局管理者を検索するSQL文を生成し返します。
     */
    List<MastGenericDetailDO> selectTmgSectionAdmin(String custId, String compId, String sectionId, String lang, String baseDate);

    /**
     * CSVダウンロード用 ファイルタイプ名・表関数名を取得するクエリを返す
     * @param custID：顧客コード
     * @param compID：法人コード
     * @param lang：言語区分
     * @param date：基準日
     * @param masterCD：マスタコード
     * @return
     */
    List<TmgMoTableFunctionVo> selectTmgMoTableFunction(String custID, String compID, String lang, String date, String masterCD);

    /**
     * (遡及)CSVダウンロード用 CSVレイアウトを取得するクエリを返す
     * @param custId：顧客コード
     * @param compId：法人コード
     * @param lang：言語区分
     * @param targetDate：基準日
     * @param dlTypeID：DL種別コード
     * @return
     */
    List<String> selectTmgMoRetroLayout(String custId, String compId, String lang, String targetDate, String dlTypeID);


    /**
     * CSVファイル名取得するクエリを返す
     */
    String selectTmgMoCsvFileName(String custId, String compId, String empId, String targetDate, String dlTypeID);

    /**
     * CSV出力ヘッダー・項目取得
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 対処日
     * @return List<ItemVO>
     */
    List<ItemVO> buildSQLForSelectTmgDeptstatcsvitems(String custID, String compID, String lang, String targetDate);

    /**
     * 表示項目のヘッダー・職員毎select句・部署別合計用select句・テーブルセル幅の項目取得
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 対処日
     * @return List<DispItemsDto>
     */
    List<DispItemsDto> buildSQLForSelectTmgDispdeptStatlist(String custID, String compID, String lang, String targetDate);


    List<MastGenericDetailDO> selectPermissionString();

    /**
     * HR連携除外条件マスタ情報を取得する
     *
     * @param custID   顧客コード
     * @param compCode 　法人コード
     * @param language 　言語
     * @param groupId  グループID
     * @return 　List<SimulationMasterDto>
     */
    List<SimulationMasterDto> buildSQLForSelectSimulationMaster(String custID, String compCode, String language, String groupId);

    /**
     * 個人属性一覧_表示処理での一括編集用項目の制御情報を取得するクエリを返します。
     */
    List<EmpAttsetDispVo> selectEmpAttsetDisp(String custId, String compId, String baseDate, String lang);


    /**
     * マスタ検索用SQL取得メソッド
     */
    List<MastGenericDetailDO> selectWorkPlace(String custId,String compId,String lang,String groupId,String baseDate);

    /**
     * 平均勤務時間の上限取得クエリを返す
     * 1日の上限を「分」で返す
     * @return
     */
    MgdTimeLimitVo selectMgdTimeLimit();


    /**
     * 週予定勤務パターン取得処理
     */
    String selectWeekDaysCom(String custId,String compId,String baseDate,int daysOfWeeks,int allMinutes);


    List<EmploymentWithMgdVo> selectDateOfEmploymentWithMGD(String custId, String compId, String lang, String empId, String groupId);


    /**
     * 名称マスタに勤務開始日を追加
     */
    int insertMgdKinmuStart(String custId, String compId, String targetUser, String userCode, String baseDate,String startDate,String endDate,String beginDate);

    /**
     * HR連携除外条件マスタ情報の件数を取得する
     *
     * @param custID   顧客コード
     * @param compCode 　法人コード
     * @param language 　言語
     * @param groupId groupId
     * @return
     */
    List<Integer> buildSQLForSelectSumSimulationMaster(String custID, String compCode, String language, String groupId);
    /**
     * HR連携除外条件区分マスタ情報の件数を取得する
     *
     * @param custID   顧客コード
     * @param compCode 　法人コード
     * @param language 　言語
     * @return
     */
    List<ExcludecondCtlDto> buildSQLForSelectExcludecondCtl(String custID, String compCode, String language);

    /**
     * 名称マスタ詳細情報に登録する
     *
     * @param psCustId          顧客コード
     * @param psCompId          法人コード
     * @param psLanguage        言語
     * @param psGroupCode       グループコード
     * @param psDetailId        明細データコード
     * @param psMasterCode      マスタコード
     * @param psStartDate       データ開始日
     * @param psEndDate         データ終了日
     * @param psUpdateUser      更新者ID
     * @param psExuludecondType 絞込み項目区分
     * @param psExuludecondForm 範囲(FROM)
     * @param psExuludecondTo   範囲(TO)
     */
    void buildSQLForInsertTmgDailyDetail(String psCustId, String psCompId,
                                         String psLanguage, String psGroupCode, String psDetailId,
                                         String psMasterCode, String psStartDate, String psEndDate,
                                         String psUpdateUser, String psExuludecondType, String psExuludecondForm,
                                         String psExuludecondTo);

    /**
     * 帳票種別リストボックスのデータを取得するクエリ文を生成します。
     */
    List<LedgerSheetVo> selectLedgerSheetList(String custID, String compCode, String language);


    /**
     * 职种名获取
     * @return
     */
    String selectWorkerTypeName(String custId,String compId,String empid, String baseDate);


    String selectMasterCode(String custId, String compId,String baseDate,String masterCode);
}
