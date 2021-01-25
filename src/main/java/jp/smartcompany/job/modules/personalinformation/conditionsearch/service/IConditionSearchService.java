package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;

import java.util.List;

public interface IConditionSearchService extends IService<MastDatadictionaryDO> {

    List<TableOptionDTO> selectTable();

}
