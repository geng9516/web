package jp.smartcompany.job.modules.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
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
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ExcludecondCtlDto;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationMasterDto;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerSheetVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.WorkTypeDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.DateDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdNtfPropVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdNtfTypeDispAppVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.MgdTmgNtfTypeVo;
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


    /**年度を取得するSQLを返す*/
    int selectYear(String custId,String compId);

    /**
     * 年度開始終了日を取得するSQLを返す
     */
    DateDto selectDate(String custId, String compId, int year, String baseDate);


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
     * 個人属性一覧_表示処理での一括編集用項目の制御情報を取得するクエリを返します。
     */
    List<EmpAttsetDispVo> selectEmpAttsetDisp(String custId, String compId, String baseDate, String lang);


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
     * 帳票種別リストボックスのデータを取得するクエリ文を生成します。
     */
    List<LedgerSheetVo> selectLedgerSheetList(String custID, String compCode, String language);


    /**
     * 职种名获取
     * @return
     */
    String selectWorkerTypeName(String custId,String compId,String empid, String baseDate);


    String selectMasterCode(String custId, String compId,String baseDate,String masterCode);

    String selectBulkTimeRange(String custID, String compCode, String baseDate);

    List<NewBulkdropDownVo> selectBulkdropDown(String custID, String compCode, String baseDate);

    List<WorkTypeDto> selectWorkerType4Variational(String custID, String compCode, String baseDate);

    List<WorkTypeDto> selectWorkerType4Flex(String custID, String compCode, String baseDate);

    Date getMaxEndDate(String groupId,String detailId,String searchDate);

    MastGenericDetailDO getGenericDetail(String groupId,String detailId,String searchDate);

    List<GenericDetailItemDTO> listItemsByDetailGroupId(String detailGroupId);

    String getHolFlg(String kubunid);
}
