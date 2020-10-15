package jp.smartcompany.job.modules.tmg.timepunch.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 打刻の結果メッセージを返却する
 * @objectSource
 * @date 2020/07/14
 **/
@Setter
@Getter
@ToString
public class ClockResultVO {

    /**
     * 職員番号
     */
    private String employeeId;

    /**
     * 客様番号
     */
    private String customerId;

    /**
     * 会社コード
     */
    private String companyId;

    /**
     * 打刻時間
     */
    private String clockTime;

    /**
     * コード
     * ERRTYPE_0:成功
     * ERRTYPE_10: 入力禁止：登録は不可能
     * ERRTYPE_20：　警告：登録は可能
     * ERRTYPE_30：　警告：OK時に登録が可能
     */
    private String resultCode;

    /**
     * 結果メッセージ
     */
    private String resultMsg;


}
