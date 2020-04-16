package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgWeekPatternDO;
import jp.smartcompany.job.modules.core.mapper.TmgWeekPatternMapper;
import jp.smartcompany.job.modules.core.service.ITmgWeekPatternService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]週次勤務パターンテーブル(エラーチェック用) 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgWeekPatternServiceImpl extends ServiceImpl<TmgWeekPatternMapper, TmgWeekPatternDO> implements ITmgWeekPatternService {

        }
