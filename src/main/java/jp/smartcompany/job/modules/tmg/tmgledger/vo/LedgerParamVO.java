package jp.smartcompany.job.modules.tmg.tmgledger.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LedgerParamVO {

    /**
     * アクション
     */
    private  String txtAction;
    /**
     * 選択帳票種別ID
     */
    private  String txtLedgerSheetId;
    /**
     * 選択対象職員名
     */
    private  String txtEmp;
    /**
     * 選択対象職員ID
     */
    private  String hidEmpId;
    /**
     * 選択対象部署名
     */
    private  String txtOrg;
    /**
     * 選択対象部署ID
     */
    private  String hidOrgId;
    /**
     * 選択対象年
     */
    private  String txtYYYY;
    /**
     * 選択対象年月
     */
    private  String txtYYYYMM;
    /**
     * 選択対象期間・開始
     */
    private  String txtTermFrom;
    /**
     * 選択対象期間・終了
     */
    private  String txtTermTo;
    /**
     * 表示開始月：出勤簿（年）
     */
    private  String txtAtdBookTermFrom;
    /**
     * 法定内超勤を含める
     */
    private  String txtIncludeOt100Flg;
}
