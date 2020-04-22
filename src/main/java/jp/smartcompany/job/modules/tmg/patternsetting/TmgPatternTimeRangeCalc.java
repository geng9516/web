package jp.smartcompany.job.modules.tmg.patternsetting;

import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTimeRange;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternRow;

import java.util.Date;
import java.util.List;

public class TmgPatternTimeRangeCalc extends BaseExecute {

    /**
     *プラガブル用メイン処理
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }

    /**
     * 予定から休憩を差引いた予定勤務時間をNUMBER型で返却する
     *
     * @param List<TmgTimeRange>

     * @return int
     */
    protected int init(List<TmgTimeRange> ttrList) {
        return 0;
    }
}
