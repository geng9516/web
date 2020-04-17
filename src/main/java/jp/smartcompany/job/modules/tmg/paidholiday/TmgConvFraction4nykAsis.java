package jp.smartcompany.job.modules.tmg.paidholiday;

import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;

public class TmgConvFraction4nykAsis  extends BaseExecute {

    /**
     *プラガブル用メイン処理
     *
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return init(pluggableDTO.getPnTime());
    }

    /**
     *換算処理区分が「端数処理なし(1分未満は切り上げ)」の場合
     *
     * @param pnTime 分単位の数値
     * @return 分単位の数値
     */
    public int init(Double pnTime) {
        double tmp = 0;
        if(pnTime == null){
            pnTime = tmp ;
        }
        // 換算処理区分が「端数処理なし(1分未満は切り上げ)」の場合
        return (int)Math.ceil(pnTime);
    }
}
