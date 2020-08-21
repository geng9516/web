package jp.smartcompany.controller;


import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.TmgLedgeDispVo;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.TmgBulkNotificationBean;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.UploadDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.BulkDispVo;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.DetailDataVo;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.NewBulkdropDownVo;
import jp.smartcompany.job.modules.tmg.tmgledger.TmgLedgerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/tmgbulknotification")
public class TmgBulkNotificationController {

    @Autowired
    private TmgBulkNotificationBean tmgBulkNotificationBean;
    /**
     * 休暇・休業登録履歴一覧
     * @return BulkDispVo
     */
    @GetMapping("Disp")
    public BulkDispVo execRList(@RequestParam("baseDate")String baseDate,
                                @RequestAttribute("BeanName") PsDBBean psDBBean) {

        return tmgBulkNotificationBean.execRList(baseDate,psDBBean);
    }

    /**
     * 一括登録区分取得
     * @return NewBulkdropDownVo
     */
    @GetMapping("getNtfType")
    public List<NewBulkdropDownVo> getNtfType(@RequestParam("baseDate")String baseDate,
                                              @RequestAttribute("BeanName") PsDBBean psDBBean) {

        return tmgBulkNotificationBean.execRMakeBulkNtf(baseDate,psDBBean);
    }

    /**
     * 詳細画面表示処理
     * @return DetailDataVo
     */
    @GetMapping("getDetail")
    public DetailDataVo getDetail(@RequestParam("ntfId")String ntfId,
                                  @RequestAttribute("BeanName") PsDBBean psDBBean) {

        return tmgBulkNotificationBean.execRDetail(ntfId,psDBBean);
    }

    /**
     * 登録処理を実行します
     */
    @PostMapping("makeBulkNtf")
    @ResponseBody
    public GlobalResponse execCMakeBulkNtf( @RequestBody UploadDto uploadDto,
                                            @RequestAttribute("BeanName") PsDBBean psDBBean){
        return tmgBulkNotificationBean.execCMakeBulkNtf(uploadDto,psDBBean);
    }

    /**
     * 取消処理を実行します
     */
    @PostMapping("cancelBulkNtf")
    @ResponseBody
    public GlobalResponse execUCancel(  @RequestParam("seq") String seq,
                                            @RequestAttribute("BeanName") PsDBBean psDBBean){
        return tmgBulkNotificationBean.execUCancel(seq,psDBBean);
    }

}
