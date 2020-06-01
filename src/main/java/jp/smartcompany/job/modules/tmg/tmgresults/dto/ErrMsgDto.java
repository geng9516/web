package jp.smartcompany.job.modules.tmg.tmgresults.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * パラメータDTO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
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
