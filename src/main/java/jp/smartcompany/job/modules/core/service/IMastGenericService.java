package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDO;

public interface IMastGenericService extends IService<MastGenericDO> {

  MastGenericDO getByGroupId(String groupId);

}
