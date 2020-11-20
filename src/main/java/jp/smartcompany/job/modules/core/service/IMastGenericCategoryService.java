package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericCategoryDO;

public interface IMastGenericCategoryService extends IService<MastGenericCategoryDO> {

    String getNameByCateId(String cateId);

}
