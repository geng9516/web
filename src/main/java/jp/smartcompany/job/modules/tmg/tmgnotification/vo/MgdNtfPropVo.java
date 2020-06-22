package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * Vo for  employee detail
 */
@Getter
@Setter
@ToString
public class MgdNtfPropVo {
    /**0 申請区分*/
    private String mgdNtftypeid;
    /**1 画面キー*/
    private String mgdPropid;
    /**2 表示名称*/
    private String mgdDispname;
}
