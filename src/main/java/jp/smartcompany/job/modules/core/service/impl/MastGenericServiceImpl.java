package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.mapper.MastGeneric.MastGenericMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDO;
import jp.smartcompany.job.modules.core.service.IMastGenericService;
import org.springframework.stereotype.Service;

@Service
public class MastGenericServiceImpl extends ServiceImpl<MastGenericMapper, MastGenericDO> implements IMastGenericService {

    @Override
    public MastGenericDO getByGroupId(String groupId) {
        QueryWrapper<MastGenericDO> qw = SysUtil.query();
        qw.eq("MG_CCUSTOMERID_CK_FK","01")
          .eq("MG_CGENERICGROUPID_CK",groupId)
          .eq("MG_CLANGUAGE_CK","ja");
        return getOne(qw);
    }

}
