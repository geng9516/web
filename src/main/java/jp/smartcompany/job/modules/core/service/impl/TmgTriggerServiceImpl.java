package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgTriggerMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.ITmgTriggerService;
import org.springframework.stereotype.Repository;

/**
 * @author Xiao Wenpeng
 */
@Repository
public class TmgTriggerServiceImpl extends ServiceImpl<TmgTriggerMapper, TmgTriggerDO> implements ITmgTriggerService {
}
