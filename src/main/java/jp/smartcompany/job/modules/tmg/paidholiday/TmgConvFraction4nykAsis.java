package jp.smartcompany.job.modules.tmg.paidholiday;

import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import org.springframework.stereotype.Service;

/**
 * @author Nie Wanqun
 * 端数処理（端数処理なし　１分未満は切り上げ）
 * TMG_F_CONV_FRACTION4NYK_ASIS
 */
@Service
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
     * 換算処理区分が「端数処理なし(1分未満は切り上げ)」の場合
     *
     * @param pnTime 分単位の数値
     * @return 分単位の数値
     */
    public int init(int pnTime) {

        // 換算処理区分が「端数処理なし(1分未満は切り上げ)」の場合
        return pnTime;
    }
}
