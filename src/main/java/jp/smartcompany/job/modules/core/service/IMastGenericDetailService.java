package jp.smartcompany.job.modules.core.service;


import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;

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
     * @param customerId    顧客コード
     * @param companyId     法人コード
     * @param startDate    検索期間開始日
     * @param endDate      検索期間開始日
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
     * @param customerId    顧客コード
     * @param companyId     法人コード
     * @param yyyymmdd      基準日
     * @param workerType 　ワークタイプ
     * @return String パターン
     */
    String selectWorkPattern(String customerId, String companyId, Date yyyymmdd, String workerType);

}
