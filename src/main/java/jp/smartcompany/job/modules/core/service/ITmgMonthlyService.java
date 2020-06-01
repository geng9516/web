package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.paidHolidayThisMonthInfoVo;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
         * 月別情報を取得するSQLを返す
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param yyyymmdd      基準日
         * @param employeeId 　社員ID
         * @param params 　select sql
         * @return String パターン
         */

        Map<String,Object> selectMonthlyDisp(String customerId, String companyId, Date yyyymmdd, String employeeId,String[] params);

        /**
         * 今月の月中有給付与に関する情報を返すSQL
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId 　社員ID
         * @return List<paidHolidayThisMonthInfoVo> パターン
         */

        List<paidHolidayThisMonthInfoVo>  selectPaidHolidayThisMonthInfo(String customerId, String companyId, String employeeId);



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

    /**
     * 月別情報を確定/確定解除する
     *
     * @param custId            顧客コード
     * @param compCode          法人コード
     * @param targetUser        対象者
     * @param month             対象月
     * @param userCode          更新者
     * @param modifierProgramId 　更新プログラムID
     * @param statusApproved    　　状態
     * @return 件数
     */
    int buildSQLForUpdateMonthly(String custId, String compCode, String targetUser, String month, String userCode, String modifierProgramId, String statusApproved);

}
