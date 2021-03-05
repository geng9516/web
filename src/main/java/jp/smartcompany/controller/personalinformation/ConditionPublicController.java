package jp.smartcompany.controller.personalinformation;

import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionPublicSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;
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

    private final IConditionPublicSearchLogic conditionPublicSearchLogic;

    /**
     * 获取编辑共有前端显示需要的参数
     */
    @GetMapping("show/addOrUpdate")
    public Map<String,Object> showEdit(@RequestParam(required = false) Long settingId) {
        return conditionPublicSearchLogic.showAddOrUpdate(settingId);
    }

    /**
     * 获取保存的设定详情
     */
    @GetMapping("setting/detail")
    public Map<String,Object> settingDetail(@RequestParam Long settingId) {
        return conditionPublicSearchLogic.getSettingDetail(settingId);
    }

    /**
     * 编辑共有
     * 参数示例（新增动作）：
     * 搜索条件让某些group共有：
     * {
     *    hseCpublic 共有有無 1=共有 0=不共有
     *    hseCcomment 備考
     *    hseCusecooperation 連携使用有無
     *    hseCmastercodeflg マスタコード表示フラグ
     *    hseCcompanyidCk 法人コード，保持为01即可
     *    hseCcompanyselect 法人選択区分，保持为0即可
     *    hseCsettingname 設定名称
     *    orderDtoList: [
     *      {
     *         "hsoNseq": 0,
     *         "hsoCcolumn":"TV2904_CSECTIONID",
     *         "hsoCcolumId":"TV2904_CSECTIONID DESC",
     *         "hsoCorder":"1"
     *      }
     *    ],
     *    selectDtoList: [
     *      {
     *        hssNseq:'0',
     *        hssCcolumn:'TV2904_CSECTIONID'
     *       }
     *    ],
     *    targetDtoList: [ 如果是全group共有则不需要传targetDtoList
     *      {hstCtargetsystem:'01',hstCtargetgroup:'4'},
     *      {hstCtargetsystem:'01',hstCtargetgroup:'2'}
     *    ]
     * }
     */
    @PostMapping("addOrUpdate")
    public GlobalResponse editSettings(@RequestBody ConditionSettingDTO settingDTO) {
        return conditionPublicSearchLogic.editSettings(settingDTO);
    }

    /**
     * 自由条件設定読込画面表示処理
     */
    @GetMapping("show/read")
    public List<CommonConditionVO> getConditionVoList() {
        return conditionPublicSearchLogic.getConditionVoList();
    }

    @GetMapping("delete")
    public String deleteSettings(@RequestParam Long settingId) {
        conditionPublicSearchLogic.deleteSettings(settingId);
        return "削除成功";
    }

}
