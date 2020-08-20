package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.mapper.MastGenericDetailMapper;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.deptstatlist.dto.DispItemsDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmpAttsetDispVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmploymentWithMgdVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.MgdTimeLimitVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetDateLimit;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.MoDLTypeVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoTableFunctionVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.DispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.NewBulkdropDownVo;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ExcludecondCtlDto;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationMasterDto;
import jp.smartcompany.job.modules.tmg.tmgledger.vo.LedgerSheetVo;
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
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
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
    public List<MgdNtfTypeDispAppVo> selectMasterTmgNtfTypeDispAppList(String customerId, String companyId, Date yyyymmdd, String language) {
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("language", language);
        //
        List<MgdNtfTypeDispAppVo> mgdNtfTypeDispAppVoList = baseMapper.selectMasterTmgNtfTypeDispAppList(map);

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
            sql = " ";
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
    public List<MgdTmgNtfTypeVo> selectMasterTmgNtfType(String custId, String compId, String baseDate, String employeeId, String language, String siteId, String workType) {
        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("baseDate", baseDate);
        map.put("employeeId", employeeId);
        map.put("language", language);
        map.put("siteId", siteId);
        map.put("workType", workType);

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
    public MgdNtfPropVo selectMasterNtfProp(String custId, String compId, String language) {
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
    public DateDto selectDate(String custId, String compId, int year, String baseDate) {
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
     * 対象日の年度開始日および年度終了日を取得するSQLを構築して返します
     */
    @Override
    public TargetDateLimit selectTargetFiscalYear(String custID, String compID, String baseDate){
        return baseMapper.selectTargetFiscalYear( custID,  compID,  baseDate);
    }


    /**
     * 月例/遡及データダウンロード画面用 DL種別・リンク名称を取得するクエリを返す
     * @param custId：顧客コード
     * @param compId：法人コード
     * @param lang：言語区分
     * @param date：基準日
     * @return
     */
    @Override
    public List<MoDLTypeVo> selectTmgMoDLType(String custId, String compId, String lang, String date){
        return baseMapper.selectTmgMoDLType( custId, compId, lang, date);
    }


    /**
     * 部局管理者を検索するSQL文を生成し返します。
     */
    @Override
    public List<MastGenericDetailDO> selectTmgSectionAdmin(String custId, String compId, String sectionId, String lang, String baseDate){
        QueryWrapper<MastGenericDetailDO> qw = SysUtil.query();
        qw.eq("MGD_CCUSTOMERID", custId)
                .eq("MGD_CCOMPANYID_CK_FK", compId)
                .eq("MGD_CLANGUAGE_CK", lang)
                .eq("MGD_CGENERICGROUPID",  TmgUtil.Cs_MGD_TMG_SECTION_ADMIN )
                .le("MGD_DSTART_CK", baseDate)
                .ge("MGD_DEND", baseDate)
                .eq("MGD_CSPARECHAR1",sectionId)
                .select("MGD_CSPARECHAR4","MGD_CSPARECHAR3");
        List<MastGenericDetailDO> mastGenericDetailDOList = list(qw);
        if (mastGenericDetailDOList != null) {
            return mastGenericDetailDOList;
        }
        return null;
    }


    /**
     * CSVダウンロード用 ファイルタイプ名・表関数名を取得するクエリを返す
     * @param custID：顧客コード
     * @param compID：法人コード
     * @param lang：言語区分
     * @param date：基準日
     * @param masterCD：マスタコード
     * @return
     */
    @Override
    public List<TmgMoTableFunctionVo> selectTmgMoTableFunction(String custID, String compID, String lang, String date, String masterCD){
        return baseMapper.selectTmgMoTableFunction( custID,  compID,  lang,  date, masterCD);
    }


    /**
     * (遡及)CSVダウンロード用 CSVレイアウトを取得するクエリを返す
     * @param custId：顧客コード
     * @param compId：法人コード
     * @param lang：言語区分
     * @param targetDate：基準日
     * @param dlTypeID：DL種別コード
     * @return
     */
    @Override
    public List<String> selectTmgMoRetroLayout(String custId, String compId, String lang, String targetDate, String dlTypeID){
        return baseMapper.selectTmgMoRetroLayout( custId,  compId,  lang,targetDate, dlTypeID);
    }


    /**
     * CSVファイル名取得するクエリを返す
     */
    @Override
    public String selectTmgMoCsvFileName(String custId, String compId, String empId, String targetDate, String dlTypeID){
        return baseMapper.selectTmgMoCsvFileName(  custId, compId, empId, targetDate, dlTypeID);
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
        Date now = DateUtil.date();
        return list(SysUtil.<MastGenericDetailDO>query()
                .eq("MGD_CGENERICGROUPID", "PERMSTR")
                .eq("MGD_CLANGUAGE_CK", "ja")
                .le("MGD_DSTART_CK", SysUtil.transDateToString(now))
                .ge("MGD_DEND", SysUtil.transDateToString(now))
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
        return baseMapper.buildSQLForSelectSimulationMaster( custID, compCode, language, groupId);
    }



    /**
     * 個人属性一覧_表示処理での一括編集用項目の制御情報を取得するクエリを返します。
     */
    @Override
    public List<EmpAttsetDispVo> selectEmpAttsetDisp(String custId, String compId, String baseDate, String lang){
        return baseMapper.selectEmpAttsetDisp( custId, compId, baseDate, lang);
    }


    /**
     * マスタ検索用SQL取得メソッド
     */
    @Override
    public List<MastGenericDetailDO> selectWorkPlace(String custId,String compId,String lang,String groupId,
                                                                        String baseDate){
        QueryWrapper<MastGenericDetailDO> qw = SysUtil.query();
        qw.eq("MGD_CCUSTOMERID", custId)
                .eq("MGD_CCOMPANYID_CK_FK", compId)
                .eq("MGD_CLANGUAGE_CK", lang)
                .eq("MGD_CGENERICGROUPID",  groupId )
                .le("MGD_DSTART_CK", baseDate)
                .ge("MGD_DEND", baseDate)
                .orderByAsc("MGD_CGENERICDETAILID_CK")
                .select("MGD_CMASTERCODE","MGD_CGENERICDETAILDESC");
        List<MastGenericDetailDO> mastGenericDetailDOList = list(qw);
        if (mastGenericDetailDOList != null) {
            return mastGenericDetailDOList;
        }
        return null;
    }

    /**
     * 平均勤務時間の上限取得クエリを返す
     * 1日の上限を「分」で返す
     * @return
     */
    @Override
    public MgdTimeLimitVo selectMgdTimeLimit(){
        return baseMapper.selectMgdTimeLimit();
    }


    /**
     * 週予定勤務パターン取得処理
     */
    @Override
    public String selectWeekDaysCom(String custId,String compId,String baseDate,int daysOfWeeks,int allMinutes){
        return baseMapper.selectWeekDaysCom( custId, compId, baseDate, daysOfWeeks, allMinutes);
    }



    @Override
    public List<EmploymentWithMgdVo> selectDateOfEmploymentWithMGD(String custId, String compId, String lang, String empId, String groupId){
        return baseMapper.selectDateOfEmploymentWithMGD( custId,  compId,  lang, empId,  groupId);
    }


    /**
     * 名称マスタに勤務開始日を追加
     */
    @Override
    public int insertMgdKinmuStart(String custId, String compId, String targetUser, String userCode, String baseDate,String startDate,String endDate,String beginDate){
        return baseMapper.insertMgdKinmuStart( custId,  compId,  targetUser, userCode,  baseDate,startDate,endDate,beginDate);
    }

    /**
     * HR連携除外条件マスタ情報の件数を取得するSQL文を返却します。
     *
     * @param custID   顧客コード
     * @param compCode 　法人コード
     * @param language 　言語
     * @param groupId groupId
     * @return
     */
    @Override
    public List<Integer> buildSQLForSelectSumSimulationMaster(String custID, String compCode, String language, String groupId) {
        return baseMapper.buildSQLForSelectSumSimulationMaster(custID, compCode, language, groupId);
    }

    /**
     * HR連携除外条件区分マスタ情報の件数を取得する
     *
     * @param custID   顧客コード
     * @param compCode 　法人コード
     * @param language 　言語
     * @return
     */
    @Override
    public List<ExcludecondCtlDto> buildSQLForSelectExcludecondCtl(String custID, String compCode, String language){
        return baseMapper.buildSQLForSelectExcludecondCtl(custID, compCode, language);
    }

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
    @Override
    public void buildSQLForInsertTmgDailyDetail(String psCustId, String psCompId,
                                         String psLanguage, String psGroupCode, String psDetailId,
                                         String psMasterCode, String psStartDate, String psEndDate,
                                         String psUpdateUser, String psExuludecondType, String psExuludecondForm,
                                         String psExuludecondTo){
         baseMapper.buildSQLForInsertTmgDailyDetail(psCustId, psCompId,
                psLanguage, psGroupCode, psDetailId,
                psMasterCode, psStartDate, psEndDate,
                psUpdateUser, psExuludecondType, psExuludecondForm,
                psExuludecondTo);
    }

    /**
     * 帳票種別リストボックスのデータを取得するクエリ文を生成します。
     */
    @Override
    public List<LedgerSheetVo> selectLedgerSheetList(String custID, String compCode, String language){
        return baseMapper.selectLedgerSheetList( custID, compCode, language);
    }


    /**
     * 职种名获取
     * @return
     */
    @Override
    public String selectWorkerTypeName(String custId,String compId,String empid, String baseDate){
        return baseMapper.selectWorkerTypeName( custId, compId, empid, baseDate);
    }

    @Override
    public  String selectMasterCode(String custId, String compId,String baseDate,String masterCode){
        return baseMapper.selectMasterCode( custId, compId, baseDate, masterCode);
    }

    @Override
    public String selectBulkTimeRange(String custID, String compCode, String baseDate){
        return baseMapper.selectBulkTimeRange( custID, compCode, baseDate);
    }

    @Override
    public NewBulkdropDownVo selectBulkdropDown(String custID, String compCode, String baseDate){
        return baseMapper.selectBulkdropDown( custID, compCode, baseDate);
    }
}
