package jp.smartcompany.job.modules.core.service.impl;

        import cn.hutool.core.map.MapUtil;
        import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyDO;
        import jp.smartcompany.job.modules.core.mapper.TmgMonthlyMapper;
        import jp.smartcompany.job.modules.core.service.ITmgMonthlyService;
        import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
        import jp.smartcompany.job.modules.tmg.tmgnotification.vo.paidHolidayThisMonthInfoVo;
        import org.springframework.stereotype.Repository;
import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyDO;
import jp.smartcompany.job.modules.core.mapper.TmgMonthlyMapper;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MonthlyLinkVO;
import org.springframework.stereotype.Repository;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;
        import java.util.Map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]月別情報                      2007/2/23 年休調整日数のカラムを追加。年休(時間) 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgMonthlyServiceImpl extends ServiceImpl<TmgMonthlyMapper, TmgMonthlyDO> implements ITmgMonthlyService {

    /**
     * 該当の職員・年月の超過勤務時間数の合計を取得する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param targetUser 対象者
     * @param month      対象月
     * @return List<String>
     */
    @Override
    public List<String> buildSQLForSelectMonthlyOverTime(String custID, String compCode, String targetUser, String month) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("month", month);

        return baseMapper.buildSQLForSelectMonthlyOverTime(map);

    }

    /**
     * 表示月遷移リスト情報を取得する]
     *
     * @param custId   顧客コード
     * @param compId    法人コード
     * @param empId    対象者
     * @param baseDate 対象日
     * @return List<DispMonthlyVO>
     */
    @Override
    public List<DispMonthlyVO> buildSQLForSelectDispMonthlyList(String custId, String compId, String empId, String baseDate) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("empId", empId);
        map.put("baseDate", baseDate);

        return baseMapper.buildSQLForSelectDispMonthlyList(map);

    }

    /**
     * 前月・翌月の月別情報を取得する
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param targetUser 対象者
     * @param month 対象月
     * @return MonthlyLinkVO
     */
    @Override
    public MonthlyLinkVO buildSQLForSelectMonthlyLink(String custID, String compCode, String targetUser, String month){

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("month", month);

        return baseMapper.buildSQLForSelectMonthlyLink(map);
    }

    /**
     * カレンダーを取得する
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param targetUser 対象者
     * @param sectionid 組織コード
     * @param groupid グループコード
     * @param year 年度
     * @param month 対象月
     * @return HashMap
     */
    @Override
    public HashMap buildSQLForSelectCalendar(String custID, String compCode, String targetUser, String sectionid, String groupid, String year, String month){

        Map<String, Object> map = MapUtil.newHashMap(7);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("sectionid", sectionid);
        map.put("groupid", groupid);
        map.put("year", year);
        map.put("month", month);
        return baseMapper.buildSQLForSelectCalendar(map);
    }

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
    @Override
    public HashMap buildSQLForSelectMonthly(String custID, String compCode, String targetUser, String month, List<String> list) {

        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("month", month);
        map.put("list", list);

        return baseMapper.buildSQLForSelectMonthly(map);
    }


        /**
         * 月別情報を取得するSQLを返すs
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param yyyymmdd      基準日
         * @param employeeId 　社員ID
         * @param params 　select sql
         * @return String パターン
         */
        @Override
        public Map<String,Object> selectMonthlyDisp(String customerId, String companyId, Date yyyymmdd, String employeeId,String[] params){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(yyyymmdd);
                Map<String,Object> selectMonthlyDispList = baseMapper.selectMonthlyDisp("'"+customerId+"'","'"+companyId+"'", "'" +dateString+"'","'"+employeeId+"'",params);

                return selectMonthlyDispList;
        }


        /**
         * 今月の月中有給付与に関する情報を返すSQL
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId 　社員ID
         * @return List<paidHolidayThisMonthInfoVo> パターン
         */

        @Override
        public List<paidHolidayThisMonthInfoVo> selectPaidHolidayThisMonthInfo(String customerId, String companyId, String employeeId){
                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("employeeId", employeeId);

                List<paidHolidayThisMonthInfoVo> list = baseMapper.selectPaidHolidayThisMonthInfo(map);

                return list;
        }
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
    @Override
    public int buildSQLForUpdateMonthly(String custId, String compCode, String targetUser, String month, String userCode, String modifierProgramId, String statusApproved){

        Map<String, Object> map = MapUtil.newHashMap(7);
        map.put("custId", custId);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("month", month);
        map.put("userCode", userCode);
        map.put("modifierProgramId", modifierProgramId);
        map.put("statusApproved", statusApproved);

        return baseMapper.buildSQLForUpdateMonthly(map);
    }

}
