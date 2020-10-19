package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EditDispVo {
    private String yyyymm;//年月
    private String standard;//標準時間
    private String result;//調整時間
    private String adjust;//調整数値
}
