package jp.smartcompany.controller.personalinformation;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * http://localhost:6879/sys/conditionsearch/options/tbl
     */
    @GetMapping("options/tbl")
    public List<TableOptionDTO> getTableOptions() {
      return conditionSearchLogic.getTableOptions();
    }

    /**
     * 获取数据库中对应表的字段名选择列表
     * http://localhost:6879/sys/conditionsearch/options/col?table=TMG_V_CDS_EMPLOYEES
     */
    @GetMapping("options/col")
    public List<ColumnOptionDTO> getColumnOptions(@RequestParam String table) {
      return conditionSearchLogic.getColumnOptions(table);
    }

}
