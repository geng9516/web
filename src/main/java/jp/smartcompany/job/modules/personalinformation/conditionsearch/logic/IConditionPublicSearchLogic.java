package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic;

import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;

import java.util.List;
import java.util.Map;

public interface IConditionPublicSearchLogic {

    // 他者作成の設定を上書きできるかどうか
    String PROP_JK_PERMIT_OVERWRITE = "JkPermitOverwrite";

    GlobalResponse editSettings(ConditionSettingDTO settingDTO);

    List<CommonConditionVO> getConditionVoList();

    Map<String,Object> showAddOrUpdate(Long settingId);

}
