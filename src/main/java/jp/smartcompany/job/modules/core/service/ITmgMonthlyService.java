package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MonthlyLinkVO;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * [勤怠]月別情報                      2007/2/23 年休調整日数のカラムを追加。年休(時間) 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgMonthlyService extends IService<TmgMonthlyDO> {


    /**
     * 該当の職員・年月の超過勤務時間数の合計を取得する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param targetUser 対象者
     * @param month      対象月
     * @return List<String>
     */
    List<String> buildSQLForSelectMonthlyOverTime(String custID, String compCode, String targetUser, String month);

    /**
     * 表示月遷移リスト情報を取得する]
     *
     * @param custId   顧客コード
     * @param compId   法人コード
     * @param empId    対象者
     * @param baseDate 対象日
     * @return List<DispMonthlyVO>
     */
    List<DispMonthlyVO> buildSQLForSelectDispMonthlyList(String custId, String compId, String empId, String baseDate);


    /**
     * 前月・翌月の月別情報を取得する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param targetUser 対象者
     * @param month      対象月
     * @return MonthlyLinkVO
     */
    MonthlyLinkVO buildSQLForSelectMonthlyLink(String custID, String compCode, String targetUser, String month);


    /**
     * カレンダーを取得する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param targetUser 対象者
     * @param sectionid  組織コード
     * @param groupid    グループコード
     * @param year       年度
     * @param month      対象月
     * @return HashMap
     */
    HashMap buildSQLForSelectCalendar(String custID, String compCode, String targetUser, String sectionid, String groupid, String year, String month);


    /**
     * 月別情報を取得する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param targetUser 対象者
     * @param month      　対象月
     * @param list       動態項目
     * @return 月別情報
     */
    HashMap buildSQLForSelectMonthly(String custID, String compCode, String targetUser, String month, List<String> list);

}
