package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDO;
import jp.smartcompany.job.modules.core.mapper.TmgDailyMapper;
import jp.smartcompany.job.modules.core.service.ITmgDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.DispOverTimeItemsDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.DailyDetailOverHoursVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.DailyVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.MonthlyInfoOverSumVo;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.PaidHolidayVO;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgStatus;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyEditVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailNonDutyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DetailOverhoursVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]日別情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgDailyServiceImpl extends ServiceImpl<TmgDailyMapper, TmgDailyDO> implements ITmgDailyService {

    /**
     * 対象社員について対象月の未承認日数を集計する
     *
     * @param targetDate  対象日
     * @param compId      　対象法人コード
     * @param custId      　対象顧客コード
     * @param empId       　対象社員
     * @param dataStatus9 　確定済
     * @param dataStatus5 　承認済
     * @return 未承認日数
     */
    @Override
    public String buildSQLForSelectCountNotApprovalDay(String targetDate, String compId, String custId, String empId, String dataStatus9, String dataStatus5) {

        Map<String, Object> map = MapUtil.newHashMap(6);
        map.put("targetDate", targetDate);
        map.put("compId", compId);
        map.put("custId", custId);
        map.put("empId", empId);
        map.put("dataStatus9", dataStatus9);
        map.put("dataStatus5", dataStatus5);

        return baseMapper.buildSQLForSelectCountNotApprovalDay(map);
    }

    /**
     * 日別情報を取得する
     *
     * @param custID 法人コード
     * @param compCode 顧客コード
     * @param targetUser　対象者
     * @param language　言語
     * @param month　対象月
     * @param manageFlg　管理者フラグ
     * @param list　変動項目リスト
     * @return List<HashMap>
     */
    @Override
    public List<HashMap> buildSQLForSelectDaily(String custID, String compCode, String targetUser, String language, String month, String manageFlg, List<String> list){

        Map<String, Object> map = MapUtil.newHashMap(6);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("language", language);
        map.put("month", month);
        map.put("manageFlg", manageFlg);
        map.put("list", list);

        return baseMapper.buildSQLForSelectDaily(map);
    }

    /**
     *日別情報(編集用)を取得する
     *
     * @param custId 法人コード
     * @param compCode 顧客コード
     * @param targetUser 対象者
     * @param day 対象日
     * @param today　今日
     * @param siteId　サイトID
     * @param language　言語
     * @return DailyEditVO
     */
    @Override
    public DailyEditVO buildSQLForSelectDailyEdit(String custId, String compCode, String targetUser, String day, String today, String siteId, String language){

        Map<String, Object> map = MapUtil.newHashMap(6);
        map.put("custId", custId);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("day", day);
        map.put("today", today);
        map.put("siteId", siteId);
        map.put("language", language);

        return baseMapper.buildSQLForSelectDailyEdit(map);
    }

    /**
     * 日別詳細情報（非勤務）を取得する
     *
     * @param custId 法人コード
     * @param compCode 顧客コード
     * @param targetUser 対象者
     * @param siteId　サイトID
     * @param day　対象日
     * @param language　言語
     * @return
     */
    @Override
    public List<DetailNonDutyVO> buildSQLForSelectDetailNonDuty(String custId, String compCode, String targetUser, String siteId, String day, String language){

        Map<String, Object> map = MapUtil.newHashMap(6);
        map.put("custId", custId);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("siteId", siteId);
        map.put("day", day);
        map.put("language", language);

        return baseMapper.buildSQLForSelectDetailNonDuty(map);
    }

    /**
     * 日別詳細情報（超過勤務）を取得する
     *
     * @param custId                 法人コード
     * @param compCode               顧客コード
     * @param targetUser             対象者
     * @param siteId                 　サイトID
     * @param day                    　対象日
     * @param language               　言語
     * @param isOvertimeNotification 表示制御
     * @return
     */
    @Override
    public List<DetailOverhoursVO> buildSQLForSelectDetailOverhours(String custId, String compCode, String targetUser, String siteId, String day, String language, boolean isOvertimeNotification){

        Map<String, Object> map = MapUtil.newHashMap(7);
        map.put("custId", custId);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("siteId", siteId);
        map.put("day", day);
        map.put("language", language);
        map.put("isOvertimeNotification", isOvertimeNotification);

        return baseMapper.buildSQLForSelectDetailOverhours(map);
    }



    /**
     * [勤怠]日別集計情報より、表示対象社員の指定期間内の時間外労働時間、休日労働時間、超勤時間合計時間を集計して取得する
     * 引数には事前にescDBStringを使用しクエリにて使用出来る様にエスケープをかけて下さい。
     *
     * @param  custId      顧客ID
     * @param  compId      法人ID
     * @param  userID      職員ID
     * @param  sBaseDBDate 基準日(日付型変換)
     * @param  sMonthsNum  遡り月数
     * @return SQL文
     */
    @Override
    public List<MonthlyInfoOverSumVo> selectMonthlyOverSum(String custId, String compId, String userID, String sBaseDBDate,
                                                           String sMonthsNum){
        return baseMapper.selectMonthlyOverSum( custId,  compId,  userID,  sBaseDBDate,
                 sMonthsNum);
    }

    /**
     * 日別一覧データを取得する
     *
     * @param targetDate 表示対象日
     * @param empSql     対象者取得SQL
     * @param list       　動態項目
     * @return
     */
    @Override
    public List<HashMap> buildSQLForSelectTmgDaily(String targetDate, String empSql, List<String> list){
        return baseMapper.buildSQLForSelectTmgDaily(targetDate, empSql, list);
    }


    /**
     * 更新対象職員のROWIDを取得する
     *
     * @param empIdList 職員リスト
     * @param yyyymmdd  　対象日
     * @return 更新対象rowidリスト
     */
    @Override
    public List<String> buildSQLForSelectObjEmpForUpdate(List<String> empIdList, String yyyymmdd){


        Map<String, Object> map = MapUtil.newHashMap(2);
        map.put("empIdList", empIdList);
        map.put("yyyymmdd", yyyymmdd);

        return baseMapper.buildSQLForSelectObjEmpForUpdate(map);
    }

    /**
     *  一括承認データを更新する
     * @param loginUserCode 更新者
     * @param programId　更新プログラムID
     * @param yyyymmdd　対象日
     * @param empIdList　対象者リスト
     * @return 更新件数
     */
    @Override
    public int buildSQLForUpdateTmgDaily(String loginUserCode, String programId, String yyyymmdd, List<String> empIdList) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("loginUserCode", loginUserCode);
        map.put("programId", programId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("empIdList", empIdList);

        return baseMapper.buildSQLForUpdateTmgDaily(map);
    }


    /**
     * 日別情報より予定出社・退社時間、超過勤務命令開始・終了時間を取得
     * */
    @Override
    public List<DailyVo> selectDaily(String pCustCode, String pCompCode, String sectionCode, String pYYYYMM,
                                     String pYYYYMMDD, String pLangage, String empListSql, List<DispOverTimeItemsDto> itemsSql) {
        return baseMapper.selectDaily( pCustCode,  pCompCode,  sectionCode,  pYYYYMM,
                 pYYYYMMDD,  pLangage,  empListSql, itemsSql);
    }

    /**
     * 日別詳細情報（超過勤務）を取得するSQLを返す
     *
     s* @param  custId       顧客コード
     * @param  compId       法人コード
     * @param  baseDate  対象年月日（ＤＢ検索用にエスケープ済(')）
     * @return SQL文
     */
    @Override
    public List<DailyDetailOverHoursVo> selectDailyDetailOverHours(String custId, String compId,  String baseDate,  String empListSql){
        return baseMapper.selectDailyDetailOverHours( custId,  compId,  baseDate,empListSql);
    }


    /**
     * [勤怠]年次休暇情報より、年次休暇付与状況一覧を取得する
     *
     * @param custID       顧客コード
     * @param compCode     法人コード
     * @param dispUserCode 対象者
     * @param searchStart  　開始日
     * @param searchEnd    　終了日
     * @return 年次有給休暇
     */
    @Override
    public List<PaidHolidayVO> buildSQLForSelectPaidHoliday(String custID, String compCode, String dispUserCode, String searchStart, String searchEnd) {
        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("userCode", dispUserCode);
        map.put("searchStart", searchStart);
        map.put("searchEnd", searchEnd);

        return baseMapper.buildSQLForSelectPaidHoliday(map);
    }


    /**
     * 対象職員・年月日の日別、月別ステータスおよび勤怠締め有無、給与確定有無、システム日付との比較結果を返します。
     *
     * @param custID   顧客コード
     * @param compCode 法人コード
     * @param userCode 対象者
     * @param day      対象者
     * @return
     */
    @Override
    public TmgStatus buildSQLForSelectTmgStatus(String custID, String compCode, String userCode, String day) {
        return baseMapper.buildSQLForSelectTmgStatus(custID, compCode, userCode, day);
    }

    /**
     * 予定入力可能情報を取得
     * @return String SQL (予定入力可能年月取得用クエリ)
     */
    @Override
    public String selectMaxDaily(){
        return baseMapper.selectMaxDaily();
    }

}
