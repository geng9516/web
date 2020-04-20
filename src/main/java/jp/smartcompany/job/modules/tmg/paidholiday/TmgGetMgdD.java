package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Nie Wanqun
 * 汎用マスタの予備日付取得処理
 * TMG_F_GET_MGD_D
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetMgdD extends BaseExecute {

    /**
     * MastGenericDetailManager
     */
    private final IMastGenericDetailService iMastGenericDetailService;

    /**
     * CS_PIPE
     */
    private final static char CS_PIPE ='|';

    /**
     *プラガブル用メイン処理
     *
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }

    /**
     * 汎用マスタの予備日付取得処理
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param kiJun 基準日
     * @param masterCode マスタコード
     * @param columnNum カラム番号
     * @param language 言語コード
     * @return Date
     */
    public Date init (String customerId,String companyId, Date kiJun ,String masterCode, int columnNum,String language){

        // 予備日付初期化
        Date wdData = null;

        // 基準日を'DD'で取得
        Date wdKijun = DateUtil.endOfDay(kiJun) ;

        // グループIDを取得
        String wsGroupId = StrUtil.subWithLength(masterCode,1,StrUtil.indexOf(masterCode,CS_PIPE,1)-1);

        // 名称マスタIDを取得
        String wsDetailId = StrUtil.subWithLength(masterCode,StrUtil.indexOf(masterCode,CS_PIPE,1) + 1,StrUtil.length(masterCode));

        // 汎用マスタから予備日付を取得
        MastGenericDetailDO mastGenericDetailDO = iMastGenericDetailService.selectMastGenericDetailDO(customerId, companyId, wsGroupId, wsDetailId, language, wdKijun);

        // パラメータ数値をもとに予備日付の値を代入
        if (columnNum == 1){
            wdData = mastGenericDetailDO.getMgdDsparedate1();
        } else if (columnNum == 2) {
            wdData = mastGenericDetailDO.getMgdDsparedate2();
        } else if (columnNum == 3) {
            wdData = mastGenericDetailDO.getMgdDsparedate3();
        } else if (columnNum == 4) {
            wdData = mastGenericDetailDO.getMgdDsparedate4();
        } else if (columnNum == 5) {
            wdData = mastGenericDetailDO.getMgdDsparedate5();
        }

        return wdData;
    }
}
