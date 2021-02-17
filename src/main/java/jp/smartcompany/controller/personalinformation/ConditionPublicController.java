package jp.smartcompany.controller.personalinformation;

import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IConditionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 自由条件检索编辑共有接口
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/conditionsearch")
@RequiredArgsConstructor
public class ConditionPublicController {

    private final IConditionSearchService conditionSearchService;
    private final IConditionSearchLogic conditionSearchLogic;

    /**
     * 获取编辑共有前端显示需要的参数
     */
    @GetMapping("show/addOrUpdate")
    public Map<String,Object> showEdit(@RequestParam(required = false) Long settingId) {
        return conditionSearchService.showAddOrUpdate(settingId);
    }

    /**
     * 编辑共有
     */
    @PostMapping("addOrUpdate")
    public GlobalResponse editSettings(@RequestBody ConditionSettingDTO settingDTO) {
        return conditionSearchLogic.editSettings(settingDTO);
    }

    /**
     * 自由条件設定読込画面表示処理
     */
    @GetMapping("show/read")
    public List<CommonConditionVO> getConditionVoList() {
        return conditionSearchLogic.getConditionVoList();
    }

}
