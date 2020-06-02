package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyInfoDO;
import jp.smartcompany.job.modules.core.mapper.TmgMonthlyInfoMapper;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.permStatList.dto.ColNameDto;
import jp.smartcompany.job.modules.tmg.permStatList.vo.TmgMonthlyInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]月単位日別情報                   tmg_dailyのビュー代わり。承認状況一覧、超過勤務指示 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgMonthlyInfoServiceImpl extends ServiceImpl<TmgMonthlyInfoMapper, TmgMonthlyInfoDO> implements ITmgMonthlyInfoService {

    /**
     * 勤怠データ件数
     *
     * @param empSql 対象者取得SQL
     * @param date   　対象月
     * @return
     */
    @Override
    public int buildSQLForSelectTmgMonthlyInfoCount(String empSql, String date) {
        Map<String, Object> map = MapUtil.newHashMap(2);
        map.put("empSql", empSql);
        map.put("date", date);

        return baseMapper.buildSQLForSelectTmgMonthlyInfoCount(map);
    }


    /**
     * 表示する職員氏名と、承認ステータス状態を取得する
     *
     * @param custId   顧客コード
     * @param compId   法人コード
     * @param baseDate 　対象年月
     * @param lang     　言語
     * @param today    　今日
     * @param empSql   　対象者取得ｓql
     * @param list     　承認ステータス状態項目
     */
    @Override
    public List<TmgMonthlyInfoVO> buildSQLForSelectTmgMonthlyInfo(String custId, String compId, String baseDate, String lang, String today, String empSql, List<ColNameDto> list){

        Map<String, Object> map = MapUtil.newHashMap(2);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("baseDate", baseDate);
        map.put("lang", lang);
        map.put("today", today);
        map.put("empSql", empSql);
        map.put("list", list);
        return baseMapper.buildSQLForSelectTmgMonthlyInfo(map);
    }

}
