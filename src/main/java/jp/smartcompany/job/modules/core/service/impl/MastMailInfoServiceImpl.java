package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.MastMailInfoMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastMailInfoDO;
import jp.smartcompany.job.modules.core.service.IMastMailInfoService;
import org.springframework.stereotype.Service;

@Service
public class MastMailInfoServiceImpl extends ServiceImpl<MastMailInfoMapper, MastMailInfoDO> implements IMastMailInfoService {
}
