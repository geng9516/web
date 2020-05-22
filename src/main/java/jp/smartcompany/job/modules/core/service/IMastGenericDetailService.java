package jp.smartcompany.job.modules.core.service;


import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.GenericDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdAttributeVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdCsparechar4VO;

import java.util.Date;
import java.util.List;

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
     * 非常勤通常付与年休ルールを取得
     *
     * @param customerId    顧客コード
     * @param companyId     法人コード
     * @param employeeId    社員番号
     * @param yyyymmdd      基準日
     * @param beginDateWork 　開始日
     * @return int 年休ルール
     */
    int selectNenkyuRuleT(String customerId, String companyId, String employeeId
            , Date yyyymmdd, Date beginDateWork);


    /**
     * 2つの歴の引き算
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param startDate  検索期間開始日
     * @param endDate    検索期間開始日
     * @param checkCtype 　差異値
     * @param csTypeNull 　既定値
     * @return List<TmgTermRow> 除外期間
     */
    List<TmgTermRow> tmgFExcludeTerm(String customerId, String companyId, Date startDate, Date endDate, String checkCtype, String csTypeNull);

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
}
