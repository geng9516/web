package jp.smartcompany.controller.personalinformation;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
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

    /**
     * 组装查询语句所需要的where，order条件
     * http://localhost:6879/sys/conditionsearch/options
     */
    @GetMapping("options")
    public Map<String,Object> getQueryConditions(@RequestParam(required = false,defaultValue = "1") Long settingId,@RequestParam(required = false) Date searchDate) {
        if (searchDate == null) {
            searchDate = DateUtil.date();
        }
        return conditionSearchLogic.getQueryConditions(settingId,searchDate);
    }

}
