package jp.smartcompany.controller.personalinformation;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionCoopLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/conditionsearch")
@RequiredArgsConstructor
public class ConditionCoopController {

  private final IConditionCoopLogic conditionCoopLogic;

  @PostMapping("coop/save")
  public String saveCoop(@RequestBody ConditionSettingDTO settingDTO) {
     conditionCoopLogic.saveCoop(settingDTO);
     return "横断自由条件検索連携データ保存成功";
  }

}
