package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.MastPermissionMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastPermissionDO;
import jp.smartcompany.job.modules.core.service.IMastPermissionService;
import org.springframework.stereotype.Service;

@Service
public class MastPermissionServiceImpl extends ServiceImpl<MastPermissionMapper, MastPermissionDO> implements IMastPermissionService {
}
