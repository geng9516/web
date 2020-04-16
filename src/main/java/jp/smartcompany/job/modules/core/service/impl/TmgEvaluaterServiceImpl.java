package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgEvaluaterDO;
import jp.smartcompany.job.modules.core.mapper.TmgEvaluaterMapper;
import jp.smartcompany.job.modules.core.service.ITmgEvaluaterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]権限設定                      データ開始日、終了日は親となる部署のデータ開始日、終了日とす 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgEvaluaterServiceImpl extends ServiceImpl<TmgEvaluaterMapper, TmgEvaluaterDO> implements ITmgEvaluaterService {

        }
