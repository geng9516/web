package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDetailDO;

public interface IHistSearchCoopDetailService extends IService<HistSearchCoopDetailDO> {

    void saveQuery(String query);

    void deleteDetail(Long oDataId);

}
