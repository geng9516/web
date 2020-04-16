package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyCheckLogDO;
import jp.smartcompany.job.modules.core.mapper.TmgDailyCheckLogMapper;
import jp.smartcompany.job.modules.core.service.ITmgDailyCheckLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        public class TmgDailyCheckLogServiceImpl extends ServiceImpl<TmgDailyCheckLogMapper, TmgDailyCheckLogDO> implements ITmgDailyCheckLogService {

        }
