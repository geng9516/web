package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastInputmappingDO;
import jp.smartcompany.job.modules.core.mapper.TmgMastInputmappingMapper;
import jp.smartcompany.job.modules.core.service.ITmgMastInputmappingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 入力項目マッピングマスタ                  日次集計処理において、tmg_dailyおよびtmg_dai 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgMastInputmappingServiceImpl extends ServiceImpl<TmgMastInputmappingMapper, TmgMastInputmappingDO> implements ITmgMastInputmappingService {

        }
