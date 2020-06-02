package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.permStatList.vo.TmgMonthlyInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]月単位日別情報                   tmg_dailyのビュー代わり。承認状況一覧、超過勤務指示 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMonthlyInfoMapper extends BaseMapper<TmgMonthlyInfoDO> {

    /**
     * 勤怠データ件数
     */
    int buildSQLForSelectTmgMonthlyInfoCount(Map<String, Object> map);
    /**
     * 表示する職員氏名と、承認ステータス状態を取得する
     */
    List<TmgMonthlyInfoVO> buildSQLForSelectTmgMonthlyInfo(Map<String, Object> map);
}
