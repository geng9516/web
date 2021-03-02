package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDO;
import java.util.List;

public interface IConditionCoopLogic {

    void saveCoop(ConditionSettingDTO settingDTO);

    List<HistSearchCoopDO> selectCoopList();

    void delCoop(Long dataId);

}
