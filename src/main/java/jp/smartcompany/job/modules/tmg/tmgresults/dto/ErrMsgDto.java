package jp.smartcompany.job.modules.tmg.tmgresults.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Nie Wanqun
 * @description エラーチェック結果情報
 * @objectSource
 * @date 2020/06/12
 **/
@Getter
@Setter
@ToString
public class ErrMsgDto {
    /**
     * エラーコード
     */
    private String terCerrcode;

    /**
     * エラーメッセージ
     */
    private String message;
}
