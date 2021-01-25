package jp.smartcompany.controller.personalinformation;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 自由条件检索controller
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/conditionsearch")
@RequiredArgsConstructor
public class ConditionSearchController {

    private final IConditionSearchLogic conditionSearchLogic;

    /**
     * 获取数据库表名选择列表
     * http://localhost:6879/sys/conditionsearch/options/table
     */
    @GetMapping("options/table")
    public Map<String,Object> getSearchConditions() {
      return conditionSearchLogic.getConditionOptions();
    }

}
