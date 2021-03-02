package jp.smartcompany.controller.personalinformation;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionCoopLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("coop/list")
  public List<HistSearchCoopDO> coopList() {
      return conditionCoopLogic.selectCoopList();
  }

  /**
    * 連携削除処理
    */
  @GetMapping("coop/del")
  public String delCoop(Long dataId) {
      conditionCoopLogic.delCoop(dataId);
      return "連携削除成功";
  }

}
