package jp.smartcompany.controller.personalinformation;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchCsvLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.csv.ConditionSearchCsvDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 自由条件检索controller
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/conditionsearch")
@RequiredArgsConstructor
public class ConditionSearchCsvController {

    private final IConditionSearchCsvLogic conditionSearchCsvLogic;

    @PostMapping("csv/show")
    public List<ConditionSearchCsvDTO> showCsvDownload(@RequestBody ConditionSettingDTO settingDTO) {
        return conditionSearchCsvLogic.showCsvDownload(settingDTO);
    }

}
