package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDO;

import java.util.List;

public interface IHistSearchCoopService extends IService<HistSearchCoopDO> {

    Boolean selectSameDataName(String hscCdataname);

    Long selectSeq();

    List<HistSearchCoopDO> selectCoopList(String userId);

    void deleteCoop(Long dataId);
}
