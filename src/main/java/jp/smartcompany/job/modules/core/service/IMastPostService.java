package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastPostDO;

import java.util.List;

public interface IMastPostService extends IService<MastPostDO> {

    List<MastPostDO> select(
            String companyId,
            String baseDate,
            String language,
            String customerId,
            String sExists
    );

}
