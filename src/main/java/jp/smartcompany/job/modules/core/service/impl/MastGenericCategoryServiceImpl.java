package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.mapper.MastGenericCategory.MastGenericCategoryMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericCategoryDO;
import jp.smartcompany.job.modules.core.service.IMastGenericCategoryService;
import org.springframework.stereotype.Service;

@Service
public class MastGenericCategoryServiceImpl extends ServiceImpl<MastGenericCategoryMapper,MastGenericCategoryDO>
implements IMastGenericCategoryService {

    @Override
    public String getNameByCateId(String cateId) {
        QueryWrapper<MastGenericCategoryDO> qw = SysUtil.query();
        return getOne(qw.eq("MGC_CCUSTOMERID","01")
          .eq("MGC_CCATEGORYID",cateId)
          .select("MGC_CCATEGORYNAME")).getMccCcategoryname();
    }

}
