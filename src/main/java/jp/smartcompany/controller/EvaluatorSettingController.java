package jp.smartcompany.controller;

import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.evaluatersetting.EvaluatorSettingBean;
import jp.smartcompany.job.modules.tmg.evaluatersetting.EvaluatorSettingParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    // http://localhost:6879/sys/evaluatorsetting/disp?psSite=TMG_PERM&psApp=EvaluaterSetting&txtTmgReferListTreeViewRecordDate=2020/07/30&txtTmgReferListTreeViewPermSelectedView=section&txtTmgReferListTreeViewPermTargetSection=200000000000&txtYYYYMMDD=2020/07/01
    // [[[100000000000|000000, 役員等, 40010001 氏名, 役員等, 理事長, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, 1, 2017/04/01, 2018/12/01, 0役員等, null, null, 1, 40010001, 2017-04-01 00:00:00.0], [100000000000|000001, abc, 40010001 氏名, 役員等, 理事長, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, TMG_ONOFF|0, TMG_ONOFF|1, TMG_ONOFF|1, TMG_ONOFF|0, 1, 2013/04/01, 2222/12/31, 1abc, TMG_ONOFF|1, TMG_ONOFF|1, 1, 40010001, 2013-04-01 00:00:00.0], [100000000000|000002, gggc1, null, null, null, null, null, null, null, null, null, null, null, TMG_ONOFF|0, null, null, null, 1gggc1, null, null, null, null, null]], [[100000000000|000000, 1], [100000000000|000001, 1], [100000000000|000002, 1]], [[2019/04/01, null]], [[100000000000|000002, null, 0], [100000000000|000000, 40010001, 1], [100000000000|000001, 40010001, 1]]]
    // [[[200000000000|000000, （本部）, null, null, null, null, null, null, null, null, null, null, null, TMG_ONOFF|0, null, null, null, 0（本部）, null, null, null, null, null]], [[200000000000|000000, 1]], [[2018/10/25, null]], [[200000000000|000000, null, 0]]]
    @GetMapping("disp")
    public GlobalResponse disp(@RequestAttribute("BeanName")PsDBBean psDBBean) throws Exception {
        return GlobalResponse.ok(evaluatorSettingBean.dispHandler(psDBBean));
    }

}
