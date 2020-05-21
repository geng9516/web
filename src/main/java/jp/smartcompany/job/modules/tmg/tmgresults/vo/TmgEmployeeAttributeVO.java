package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * [勤怠]基本情報属性格納用VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TmgEmployeeAttributeVO {

    /**
     * 項目属性
     */
    private String tesCattribute;
    /**
     * 予備文字１
     */
    private String csparechar1;
    /**
     * コード01
     */
    private String ccode01;
    /**
     * コード01名称
     */
    private String ccode1name;
    /**
     * 予備数値1
     */
    private int nsparenum1;
    /**
     * 予備文字２
     */
    private String csparechar2;
}
