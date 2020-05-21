package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 月次情報のヘッダー・SQLを格納用VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ItemVO {

    /**
     * ヘッダ名称
     */
    private String mgdCheader;
    /**
     * Select句
     */
    private String mgdCsql;
    /**
     * カラム名
     */
    private String mgdCcolumnid;

    /**
     * カラムKEY
     */
    private String mgdCcolumnkey;

    /**
     * width
     */
    private int mgdNwidth;
}
