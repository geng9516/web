package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDO;
import jp.smartcompany.job.modules.core.mapper.TmgDailyMapper;
import jp.smartcompany.job.modules.core.service.ITmgDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyEditVO;
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
     * @param language　元号
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
     * @param day 対象非
     * @param today　今日
     * @param siteId　サイトID
     * @return DailyEditVO
     */
    @Override
    public DailyEditVO buildSQLForSelectDailyEdit(String custId, String compCode, String targetUser, String day, String today, String siteId){

        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("custId", custId);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("day", day);
        map.put("today", today);
        map.put("siteId", siteId);

        return baseMapper.buildSQLForSelectDailyEdit(map);
    }

}
