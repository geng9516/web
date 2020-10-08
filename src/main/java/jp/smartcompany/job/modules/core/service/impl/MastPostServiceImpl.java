package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.MastPost.MastPostMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastPostDO;
import jp.smartcompany.job.modules.core.service.IMastPostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MastPostServiceImpl extends ServiceImpl<MastPostMapper, MastPostDO> implements IMastPostService {

    @Override
    public List<MastPostDO> select(
            String companyId,
            String baseDate,
            String language,
            String customerId,
            String sExists
    ) {
        return baseMapper.select(companyId,baseDate,language,customerId,sExists);
    }

}
