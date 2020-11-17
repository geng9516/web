package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyCheckDO;
import jp.smartcompany.job.modules.core.mapper.TmgDailyCheck.TmgDailyCheckMapper;
import jp.smartcompany.job.modules.core.service.ITmgDailyCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.DailyCheckDto;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]エラーチェック用・日別情報             2007/02/23元テーブルのレイアウト変更に伴い修正 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgDailyCheckServiceImpl extends ServiceImpl<TmgDailyCheckMapper, TmgDailyCheckDO> implements ITmgDailyCheckService {

    /**
     * 日別情報チェックを追加する
     *
     * @param dailyCheckDto 　DailyCheckDto
     * @return 件数
     */
    @Override
    public int buildSQLForInsertDailyCheck(DailyCheckDto dailyCheckDto) {

        return baseMapper.buildSQLForInsertDailyCheck(dailyCheckDto);
    }
}
