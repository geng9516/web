package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastBalanceitem4salaryDO;
import jp.smartcompany.job.modules.core.mapper.TmgMastBalanceitem4salaryMapper;
import jp.smartcompany.job.modules.core.service.ITmgMastBalanceitem4salaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 差引優先項目マスタ                     給与データ出力時に超勤項目から時間を差引く際の、差し引く対象 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgMastBalanceitem4salaryServiceImpl extends ServiceImpl<TmgMastBalanceitem4salaryMapper, TmgMastBalanceitem4salaryDO> implements ITmgMastBalanceitem4salaryService {

        }
