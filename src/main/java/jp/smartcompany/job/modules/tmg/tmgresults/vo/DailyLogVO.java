package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 更新履歴VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DailyLogVO {
    /**
     * 更新者
     */
    private String cmodifierusernm;
    /**
     * 更新日時
     */
    private String dmodifieddate;
    /**
     * 画面
     */
    private String cmodifierprogramnm;
    /**
     * 操作
     */
    private String cactionnm;
    /**
     * 更新内容
     */
    private String cchangelogstr;
}
