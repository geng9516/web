package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyInfoDO;
import jp.smartcompany.job.modules.core.mapper.TmgMonthlyInfoMapper;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

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

        }
