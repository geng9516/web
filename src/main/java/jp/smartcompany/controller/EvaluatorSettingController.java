package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.tmg.evaluatersetting.EvaluatorSettingBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/evaluatorsetting")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluatorSettingController {

    private final EvaluatorSettingBean evaluatorSettingBean;

    // 権限設定状況表示
    // 测试用url:http://localhost:6879/sys/evaluatorsetting/disp?psSite=TMG_PERM&psApp=EvaluaterSetting&txtTmgReferListTreeViewRecordDate=2020/07/30
    // &txtTmgReferListTreeViewPermSelectedView=section&txtTmgReferListTreeViewPermTargetSection=201000000000&txtYYYYMMDD=2020/07/01
    // 原有数据：
    // [[[100000000000|000000, 役員等, 40010001 氏名, 役員等, 理事長, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, 1, 2017/04/01, 2018/12/01, 0役員等, null, null, 1, 40010001, 2017-04-01 00:00:00.0], [100000000000|000001, abc, 40010001 氏名, 役員等, 理事長, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, 1, 2013/04/01, 2222/12/31, 1abc, TMG_ONOFF|1, TMG_ONOFF|1, 1, 40010001, 2013-04-01 00:00:00.0], [100000000000|000002, gggc1, null, null, null, null, null, null, null, null, null, null, null, TMG_ONOFF|0, null, null, null, 1gggc1, null, null, null, null, null]], [[100000000000|000000, 1], [100000000000|000001, 1], [100000000000|000002, 1]], [[2019/04/01, null]], [[100000000000|000002, null, 0], [100000000000|000000, 40010001, 1], [100000000000|000001, 40010001, 1]]]
    // [[[200000000000|000000, （本部）, null, null, null, null, null, null, null, null, null, null, null, TMG_ONOFF|0, null, null, null, 0（本部）, null, null, null, null, null]], [[200000000000|000000, 1]], [[2018/10/25, null]], [[200000000000|000000, null, 0]]]
    @PutMapping("disp")
    public Map<String,Object> disp(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return evaluatorSettingBean.dispHandler(psDBBean);
    }

    @GetMapping("makegroup")
    public PsResult makeGroup(@RequestAttribute("BeanName") PsDBBean psDBBean,
                              @RequestParam(value="targetSectionId",required = false) String targetSectionId,
                              @RequestParam(value="targetGroupId",required = false) String targetGroupId,
                              @RequestParam(value="lastTargetGroupId",required = false) String lastTargetGroupId,
                              @RequestParam(value="groupName",required = false) String groupName,
                              @RequestParam(value="empId",required = false) String empId) {
        return evaluatorSettingBean.makeGroupHandler(psDBBean,targetSectionId,targetGroupId,lastTargetGroupId,groupName,empId);
    }

}
