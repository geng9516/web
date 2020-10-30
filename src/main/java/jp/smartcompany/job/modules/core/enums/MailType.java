package jp.smartcompany.job.modules.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MailType {

    /**
     *　アカウント発行通知メール(利用者向け)
     */
    CREATE_ACCOUNT_FOR_USER("AccountEntry"),
    /**
     *　アカウント発行通知メール(管理者向け)
     */
    CREATE_ACCOUNT_FOR_ADMIN("AccountEntryAdmin"),
    /**
     * グループチェック
     */
    GROUP_CHECK("GROUPCHECK"),
    /**
     * 汎用インターフェース
     */
    PS_GENERAL_INTERFACE("PSGENERALINTERFACE"),
    /**
     * パスワード変更
     */
    PASSWORD_CHANGED("PasswordChanged"),
    /**
     * 未承認通知
     */
    UN_APPROVAL("Unapproval"),
    /**
     * 休暇休業申請承認依頼
     */
    TMG_NTF_AWAIT_APPROVAL("TmgNtfAwaitApproval"),
    /**
     * 休暇休業申請完了通知
     */
    TMG_NTF_APPROVED("TmgNtfApproved"),
    /**
     * 休暇休業申請差戻通知
     */
    TMG_NTF_REJECTED("TmgNtfRejected"),
    /**
     * 休暇休業申請取消通知
     */
    TMG_NTF_CANCELED("TmgNtfCanceled"),
    /**
     * 日次実績未承認者通知(週)
     */
    TMG_TDA_UNAPPR_WEEKLY("TmgTdaUnapprWeekly"),
    /**
     * 日次実績未承認者通知(月)
     */
    TMG_TDA_UNAPPR_MONTHLY("TmgTdaUnapprMonthly"),
    /**
     * 月次実績未承認者通知
     */
    TMG_TMO_UNAPPR_MONTHLY("TmgTmoUnapprMonthly"),
    /**
     * 月間超過勤務時間警告(本人宛 レベル1)
     */
    TMG_OT_MONTHLY_EMP_01("TmgOtMonthlyEmp01"),
    /**
     * 月間超過勤務時間警告(本人宛 レベル2)
     */
    TMG_OT_MONTHLY_EMP_02("TmgOtMonthlyEmp02"),
    /**
     * 月間超過勤務時間警告(本人宛 レベル3)
     */
    TMG_OT_MONTHLY_EMP_03("TmgOtMonthlyEmp03"),
    /**
     * 月間超過勤務時間警告(本人宛 レベル4)
     */
    TMG_OT_MONTHLY_EMP_04("TmgOtMonthlyEmp04"),
    /**
     * 月間超過勤務時間警告(本人宛 レベル5)
     */
    TMG_OT_MONTHLY_EMP_05("TmgOtMonthlyEmp05"),
    /**
     * 月間超過勤務時間警告(承認者宛 レベル1)
     */
    TMG_OT_MONTHLY_MGR_01("TmgOtMonthlyMgr01"),
    /**
     * 月間超過勤務時間警告(承認者宛 レベル2)
     */
    TMG_OT_MONTHLY_MGR_02("TmgOtMonthlyMgr02"),
    /**
     * 月間超過勤務時間警告(承認者宛 レベル3)
     */
    TMG_OT_MONTHLY_MGR_03("TmgOtMonthlyMgr03"),
    /**
     * 月間超過勤務時間警告(承認者宛 レベル4)
     */
    TMG_OT_MONTHLY_MGR_04("TmgOtMonthlyMgr04"),
    /**
     * 月間超過勤務時間警告(承認者宛 レベル5)
     */
    TMG_OT_MONTHLY_MGR_05("TmgOtMonthlyMgr05"),
    /**
     * 超勤申請登録／変更通知
     */
    TMG_RESULTS_INPUT_OT("TmgResultsInputOT");

    @EnumValue
    @JsonValue
    private final String desc;

    MailType(String desc) {
        this.desc = desc;
    }

}
