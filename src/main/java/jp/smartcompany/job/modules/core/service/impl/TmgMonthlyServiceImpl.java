package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyDO;
import jp.smartcompany.job.modules.core.mapper.TmgMonthlyMapper;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

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

        }
