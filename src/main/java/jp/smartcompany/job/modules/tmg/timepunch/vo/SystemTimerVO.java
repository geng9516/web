package jp.smartcompany.job.modules.tmg.timepunch.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description サーバーの時間
 * @objectSource
 * @date 2020/06/26
 **/
@Getter
@Setter
@ToString
public class SystemTimerVO {
    /**
     * サーバーの時間
     */
    private String sysdate;
}
