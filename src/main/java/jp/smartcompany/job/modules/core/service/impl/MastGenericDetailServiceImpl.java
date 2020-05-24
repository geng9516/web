package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.mapper.MastGenericDetailMapper;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.GenericDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdAttributeVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdCsparechar4VO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 名称マスタ明細データ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 20200416
 */
@Repository
public class MastGenericDetailServiceImpl extends ServiceImpl<MastGenericDetailMapper, MastGenericDetailDO> implements IMastGenericDetailService {

     /**
      * 常勤年休ルールを取得
      *
      * @param customerId    顧客コード
      * @param companyId     法人コード
      * @param employeeId    社員番号
      * @param yyyymmdd      基準日
      * @param beginDateWork 　開始日
      * @return int 年休ルール
      */
    @Override
    public int selectNenkyuRuleH(String customerId, String companyId, String employeeId
            , Date yyyymmdd, Date beginDateWork) {
            Map<String, Object> map = MapUtil.newHashMap(3);
            map.put("companyId", companyId);
            map.put("yyyymmdd", yyyymmdd);
            map.put("employeeId", employeeId);

            // 年休ルールを取得
            Integer nenkyu = baseMapper.selectNenkyuRuleH(map);

            // データが無い場合、名称マスタを参照する様に設定
            if (nenkyu == null) {
                    nenkyu = baseMapper.selectNenkyuRuleH2(map);

             }
            return nenkyu;
     }


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
     @Override
     public List<TmgTermRow> tmgFExcludeTerm(String customerId, String companyId, Date startDate, Date endDate, String checkCtype, String csTypeNull){
            List<TmgTermRow> ttRList=new ArrayList<TmgTermRow>() ;
            Map<String, Object> map = MapUtil.newHashMap(3);
            map.put("customerId", customerId);
            map.put("companyId", companyId);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            map.put("checkCtype", checkCtype);
            map.put("csTypeNull", csTypeNull);


            ttRList =baseMapper.tmgFExcludeTerm(map);

            return  ttRList;
      }


      /**
       * 非常勤年休ルールを取得
       *
       * @param customerId    顧客コード
       * @param companyId     法人コード
       * @param employeeId    社員番号
       * @param yyyymmdd      基準日
       * @param beginDateWork 　開始日
       * @return int 年休ルール
       */
       @Override
       public int selectNenkyuRuleT(String customerId, String companyId, String employeeId
               , Date yyyymmdd, Date beginDateWork) {

               Map<String, Object> map = MapUtil.newHashMap(3);
               map.put("companyId", companyId);
               map.put("yyyymmdd", yyyymmdd);
               map.put("employeeId", employeeId);

               // 年休ルールを取得
               Integer nenkyu = baseMapper.selectNenkyuRuleT(map);

               // データが無い場合、名称マスタを参照する様に設定
               if (nenkyu == null) {
                       nenkyu = baseMapper.selectNenkyuRuleT2(map);
               }
               return nenkyu;
       }

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
       @Override
       public MastGenericDetailDO selectMastGenericDetailDO(String customerId, String companyId, String wsGroupId, String wsDetailId, String language, Date wdKijun) {

               Map<String, Object> map = MapUtil.newHashMap(6);
               map.put("customerId", customerId);
               map.put("companyId", companyId);
               map.put("wsGroupId", wsGroupId);
               map.put("wsDetailId", wsDetailId);
               map.put("language", language);
               map.put("wdKijun", wdKijun);

               return baseMapper.selectMastGenericDetailDO(map);
       }




    /**
     * ワークタイプのデフォルトパターンを検索
     *
     * @param customerId    顧客コード
     * @param companyId     法人コード
     * @param yyyymmdd      基準日
     * @param workerType 　ワークタイプ
     * @return String パターン
     */
    @Override
    public String selectWorkPattern(String customerId, String companyId, Date yyyymmdd, String workerType) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("workerType", workerType);
        // パターン
        String workPattern = baseMapper.selectWorkPattern(map);


        return workPattern;
    }

    /**
     * TMG_DISPMONTHLYITEMSマスタより取得した月次情報のヘッダー・SQLを取得する
     *
     * @param custID    顧客コード
     * @param compID     法人コード
     * @param lang      言語
     * @param targetDate 　対象日
     * @return List<MonthlyItemVO>
     */
    @Override
    public List<ItemVO> buildSQLForSelectTmgDispMonthlyItems(String custID, String compID, String lang, String targetDate){

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("custID", custID);
        map.put("compID", compID);
        map.put("lang", lang);
        map.put("targetDate", targetDate);
        return baseMapper.buildSQLForSelectTmgDispMonthlyItems(map);
    }
    /**
     * TMG_DISPDAILYITEMSマスタより取得した日次情報のヘッダー・SQL・表示幅を取得する
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 　対象日
     * @return List
     */
    @Override
    public List<ItemVO> buildSQLForSelectTmgDispDailyItems(String custID, String compID, String lang, String targetDate){

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("custID", custID);
        map.put("compID", compID);
        map.put("lang", lang);
        map.put("targetDate", targetDate);
        return baseMapper.buildSQLForSelectTmgDispDailyItems(map);
    }

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
    @Override
    public List<MgdAttributeVO> buildSQLForSelectgetMgdAttribute(String custID, String compCode, String targetUser, String language,
                                                          String siteId, String day, String attribute, String categoryCode){

        Map<String, Object> map = MapUtil.newHashMap(8);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("language", language);
        map.put("siteId", siteId);
        map.put("day", day);
        map.put("attribute", attribute);
        map.put("categoryCode", categoryCode);
        return baseMapper.buildSQLForSelectgetMgdAttribute(map);
    }

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
    @Override
    public List<MgdAttributeVO> buildSQLForSelectgetMgdAttributeEffort(String custID, String compCode, String targetUser, String language,
                                                                String siteId, String day, String month, String type, String onOff,
                                                                String attribute, String categoryCode){
        Map<String, Object> map = MapUtil.newHashMap(11);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("language", language);
        map.put("siteId", siteId);
        map.put("day", day);
        map.put("month", month);
        map.put("type", type);
        map.put("onOff", onOff);
        map.put("attribute", attribute);
        map.put("categoryCode", categoryCode);
        return baseMapper.buildSQLForSelectgetMgdAttributeEffort(map);
    }

    /**
     * 予備項目4を取得「0:出勤日,それ以外は出勤日ではない」
     *
     * @param custCode 顧客コード
     * @param compCode 法人コード
     * @return List<MgdCsparechar4VO>
     */
    @Override
    public List<MgdCsparechar4VO> buildSQLSelectGetMgdCsparechar4(String custCode, String compCode){
        Map<String, Object> map = MapUtil.newHashMap(2);
        map.put("custCode", custCode);
        map.put("compCode", compCode);

        return baseMapper.buildSQLSelectGetMgdCsparechar4(map);
    }

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
    @Override
    public List<GenericDetailVO> buildSQLForSelectGenericDetail(String custID, String targetComp, String targetUser, String day, String language){

        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custID", custID);
        map.put("targetComp", targetComp);
        map.put("targetUser", targetUser);
        map.put("day", day);
        map.put("language", language);

        return baseMapper.buildSQLForSelectGenericDetail(map);
    }

    /**
     *各コメント欄の最大値を名称マスタ詳細より取得
     * @param custID 顧客コード
     * @param compID 法人コード
     * @param lang 言語
     * @param targetDate 対象日
     * @param masterCode マスタコード
     * @return String
     */
    @Override
    public String buildSQLForSelectTmgVMgdMaxLengthCheck(String custID, String compID, String lang, String targetDate, String masterCode){
        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custID", custID);
        map.put("compID", compID);
        map.put("lang", lang);
        map.put("targetDate", targetDate);
        map.put("masterCode", masterCode);

        return baseMapper.buildSQLForSelectTmgVMgdMaxLengthCheck(map);
    }

    /**
     * 名称マスタから属性コードを取得
     *
     * @param custID
     * @param compCode
     * @param day
     * @param groupId
     * @return List<GenericDetailVO>
     */
    @Override
    public List<GenericDetailVO> buildSQLForSelectgetMgdDescriptions(String custID, String compCode, String day, String groupId){
        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("day", day);
        map.put("groupId", groupId);

        return baseMapper.buildSQLForSelectgetMgdDescriptions(map);
    }
}
