package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.mapper.MastGenericDetailMapper;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.deptstatlist.dto.DispItemsDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.DispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationMasterDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.dateDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.mgdNtfPropVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.mgdNtfTypeDispAppVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.mgdTmgNtfTypeVo;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgDispItemsDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.GenericDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdAttributeVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdCsparechar4VO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.*;
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
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param startDate  検索期間開始日
     * @param endDate    検索期間開始日
     * @param checkCtype 　差異値
     * @param csTypeNull 　既定値
     * @return List<TmgTermRow> 除外期間
     */
    @Override
    public List<TmgTermRow> tmgFExcludeTerm(String customerId, String companyId, Date startDate, Date endDate, String checkCtype, String csTypeNull) {
        List<TmgTermRow> ttRList = new ArrayList<TmgTermRow>();
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("checkCtype", checkCtype);
        map.put("csTypeNull", csTypeNull);


        ttRList = baseMapper.tmgFExcludeTerm(map);

        return ttRList;
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
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param yyyymmdd   基準日
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
     * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param yyyymmdd   基準日
     * @param language   　言語
     * @return String パターン
     */
    @Override
    public List<TmgDispItemsDto> selectDispMonthlyItems(String customerId, String companyId, Date yyyymmdd, String language) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("language", language);
        //
        List<TmgDispItemsDto> tmgDispMonthlyItemsDtoList = baseMapper.selectDispMonthlyItems(map);


        return tmgDispMonthlyItemsDtoList;
    }


    /**
     * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param yyyymmdd   基準日
     * @param language   　言語
     * @return String パターン
     */
    @Override
    public List<TmgDispItemsDto> selectDispDailyItems(String customerId, String companyId, Date yyyymmdd, String language) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("language", language);
        //
        List<TmgDispItemsDto> tmgDispDailyItemsDtoList = baseMapper.selectDispDailyItems(map);


        return tmgDispDailyItemsDtoList;
    }


    /**
     * 申請一覧画面用 申請区分マスタ取得SQL
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param yyyymmdd   基準日
     * @param language   　言語
     * @return String パターン
     */
    @Override
    public List<mgdNtfTypeDispAppVo> selectMasterTmgNtfTypeDispAppList(String customerId, String companyId, Date yyyymmdd, String language) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("language", language);
        //
        List<mgdNtfTypeDispAppVo> mgdNtfTypeDispAppVoList = baseMapper.selectMasterTmgNtfTypeDispAppList(map);

        return mgdNtfTypeDispAppVoList;
    }


    /**
     * マスタを取得するSQL
     *
     * @param sql 　sql
     * @return map
     */

    @Override
    public List<Map<String, Object>> selectGenericDetail(String sql) {

        List<Map<String, Object>> mgdList = baseMapper.selectGenericDetail(sql);
        return mgdList;
    }


    /**
     * 指定された申請種類の表示タイプを取得する
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param ntfId      申請種類
     * @param language   　言語
     * @return int
     */
    @Override
    public int selectNtfDispType(String customerId, String companyId, String language, String ntfId) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("ntfId", ntfId);
        map.put("language", language);
        //
        int inputCtl = baseMapper.selectNtfDispType(map);

        return inputCtl;
    }


    /**
     * 「HH:MI60」形式の文字列を分(Minute)情報に変換
     *
     * @param hhmi 「HH:MI60」形式の文字列
     * @return int
     */
    @Override
    public Long tmgFConvHhmi2Min(String hhmi) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("hhmi", hhmi);
        //
        Long min = baseMapper.tmgFConvHhmi2Min(map);

        return min;
    }


    @Override
    public Long toDayofWeek(String mon, String tue, String wed, String thu, String fri, String sat, String sun) {
        String sql = null;
        if (!StrUtil.hasEmpty(mon) ||
                !StrUtil.hasEmpty(tue) ||
                !StrUtil.hasEmpty(wed) ||
                !StrUtil.hasEmpty(thu) ||
                !StrUtil.hasEmpty(fri) ||
                !StrUtil.hasEmpty(sat) ||
                !StrUtil.hasEmpty(sun)) {

            if (StrUtil.hasEmpty(mon)) {
                mon = "null";
            }
            if (StrUtil.hasEmpty(mon)) {
                tue = "null";
            }
            if (StrUtil.hasEmpty(mon)) {
                wed = "null";
            }
            if (StrUtil.hasEmpty(mon)) {
                thu = "null";
            }
            if (StrUtil.hasEmpty(mon)) {
                fri = "null";
            }
            if (StrUtil.hasEmpty(mon)) {
                sat = "null";
            }
            if (StrUtil.hasEmpty(mon)) {
                sun = "null";
            }
            sql = mon + "," + tue + "," + wed + "," + thu + "," + fri + "," + sat + "," + sun;
        } else {
            sql = "";
        }
        Long num = baseMapper.toDayofWeek(sql);
        return num;
    }

    /**
     * 決裁レベル取得SQL返却メソッド
     *
     * @param custId
     * @param compId
     * @param language
     * @param today
     * @param approvelLevel
     * @param piMode
     * @return String
     */
    @Override
    public String selectApprovelLevel(String custId, String compId, String language, String today, String approvelLevel, int piMode) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("language", language);
        map.put("today", today);
        map.put("approvelLevel", approvelLevel);
        map.put("piMode", piMode);
        //
        String Level = baseMapper.selectApprovelLevel(map);

        return Level;
    }

    /**
     * TMG_DISPMONTHLYITEMSマスタより取得した月次情報のヘッダー・SQLを取得する
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 　対象日
     * @return List<MonthlyItemVO>
     */
    @Override
    public List<ItemVO> buildSQLForSelectTmgDispMonthlyItems(String custID, String compID, String lang, String targetDate) {

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
    public List<ItemVO> buildSQLForSelectTmgDispDailyItems(String custID, String compID, String lang, String targetDate) {

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
                                                                 String siteId, String day, String attribute, String categoryCode) {

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
     * @param custID       顧客コード
     * @param compCode     法人コード
     * @param targetUser   対象者
     * @param language     言語
     * @param siteId       サイトID
     * @param day          対象日
     * @param month        対象月
     * @param type         　種別
     * @param onOff        　onOff
     * @param attribute    　使用可否
     * @param categoryCode 　カテゴリーID
     * @return List<MgdAttributeVO>
     */
    @Override
    public List<MgdAttributeVO> buildSQLForSelectgetMgdAttributeEffort(String custID, String compCode, String targetUser, String language,
                                                                       String siteId, String day, String month, String type, String onOff,
                                                                       String attribute, String categoryCode) {
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
    public List<MgdCsparechar4VO> buildSQLSelectGetMgdCsparechar4(String custCode, String compCode) {
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
    public List<GenericDetailVO> buildSQLForSelectGenericDetail(String custID, String targetComp, String targetUser, String day, String language) {

        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custID", custID);
        map.put("targetComp", targetComp);
        map.put("targetUser", targetUser);
        map.put("day", day);
        map.put("language", language);

        return baseMapper.buildSQLForSelectGenericDetail(map);
    }

    /**
     * 各コメント欄の最大値を名称マスタ詳細より取得
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 対象日
     * @param masterCode マスタコード
     * @return String
     */
    @Override
    public String buildSQLForSelectTmgVMgdMaxLengthCheck(String custID, String compID, String lang, String targetDate, String masterCode) {
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
     * @param custID   顧客コード
     * @param compCode 法人コード
     * @param day      基準日
     * @param groupId  　グループID
     * @return List<GenericDetailVO>
     */
    @Override
    public List<GenericDetailVO> buildSQLForSelectgetMgdDescriptions(String custID, String compCode, String day, String groupId) {
        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("day", day);
        map.put("groupId", groupId);

        return baseMapper.buildSQLForSelectgetMgdDescriptions(map);
    }

    /**
     * 申請画面用 申請区分マスタ取得SQL
     *
     * @param custId
     * @param compId
     * @param baseDate
     * @param employeeId
     * @param language
     * @param siteId
     * @return String
     */
    @Override
    public List<mgdTmgNtfTypeVo> selectMasterTmgNtfType(String custId, String compId, String baseDate, String employeeId, String language, String siteId) {
        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("baseDate", baseDate);
        map.put("employeeId", employeeId);
        map.put("language", language);
        map.put("siteId", siteId);

        return baseMapper.selectMasterTmgNtfType(map);
    }

    /**
     * 画面項目名称の設定マスタ
     *
     * @param custId
     * @param compId
     * @param language
     * @return String
     */
    @Override
    public mgdNtfPropVo selectMasterNtfProp(String custId, String compId, String language) {
        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("language", language);

        return baseMapper.selectMasterNtfProp(map);
    }

    /**
     * 現在日付を取得するクエリ文を生成します
     */
    @Override
    public String selectSysdate() {
        return baseMapper.selectSysdate();
    }

    /**
     * 年度を取得するSQLを返す
     */
    @Override
    public int selectYear(String custId, String compId) {
        return baseMapper.selectYear(custId, compId);
    }

    /**
     * 年度開始終了日を取得するSQLを返す
     */
    @Override
    public dateDto selectDate(String custId, String compId, int year, String baseDate) {
        return baseMapper.selectDate(custId, compId, year, baseDate);
    }

    /**
     * 日付関連情報を取得
     *
     * @return TodayThisMonthVO
     */
    @Override
    public TodayThisMonthVO buildSQLForSelectDate() {
        return baseMapper.buildSQLForSelectDate();
    }


    /**
     * 36協定における月の超勤限度時間表示用名称取得
     */
    @Override
    public String selectLimit(String custId, String compId, String baseDate, String sLang, String masterCode) {
        return baseMapper.selectLimit(custId, compId, baseDate, sLang, masterCode);
    }


    /**
     * 超過勤務命令情報表示項目ヘッダー・select句・表示順をTMG_DISPOVERTIMEINSTマスタより取得
     */
    @Override
    public List<DispOverTimeItemsDto> selectDispOverTimeItems(String custID, String compID, String baseDate, String language){
        return baseMapper.selectDispOverTimeItems( custID,  compID,  baseDate, language);
    }

    /**
     * 承認状況欄へ表示するヘッダー名称・select句・表示順をTMG_DISPPERMSTATLISTマスタより取得
     *
     * @param custID 顧客コード
     * @param compID 法人コード
     * @param lang   言語
     * @return List<ItemVO>
     */
    @Override
    public List<ItemVO> buildSQLForSelectTmgDisppermstatlist(String custID, String compID, String lang){
        return baseMapper.buildSQLForSelectTmgDisppermstatlist( custID,  compID,  lang);

    }


    /**
     * CSV出力ヘッダー・項目取得
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 対処日
     * @return List<ItemVO>
     */
    @Override
    public List<ItemVO> buildSQLForSelectTmgDeptstatcsvitems(String custID, String compID, String lang, String targetDate) {
        return baseMapper.buildSQLForSelectTmgDeptstatcsvitems(custID, compID, lang, targetDate);
    }

    /**
     * 表示項目のヘッダー・職員毎select句・部署別合計用select句・テーブルセル幅の項目取得
     *
     * @param custID     顧客コード
     * @param compID     法人コード
     * @param lang       言語
     * @param targetDate 対処日
     * @return List<DispItemsDto>
     */
    @Override
    public List<DispItemsDto> buildSQLForSelectTmgDispdeptStatlist(String custID, String compID, String lang, String targetDate) {
        return baseMapper.buildSQLForSelectTmgDispdeptStatlist(custID, compID, lang, targetDate);
    }

    @Override
    public List<MastGenericDetailDO> selectPermissionString() {
        return list(SysUtil.<MastGenericDetailDO>query()
                .eq("MGD_CGENERICGROUPID", "PERMSTR")
                .eq("MGD_CLANGUAGE_CK", "ja")
                .le("MGD_DSTART_CK", "trunc(sysdate)")
                .ge("MGD_DEND", "trunc(sysdate)")
                .orderByAsc("MGD_CCUSTOMERID", "MGD_CCOMPANYID_CK_FK"));
    }

    /**
     * HR連携除外条件マスタ情報を取得する
     *
     * @param custID   顧客コード
     * @param compCode 　法人コード
     * @param language 　言語
     * @param groupId  グループID
     * @return 　List<SimulationMasterDto>
     */
    @Override
    public List<SimulationMasterDto> buildSQLForSelectSimulationMaster(String custID, String compCode, String language, String groupId){
        return null;
    }
}
