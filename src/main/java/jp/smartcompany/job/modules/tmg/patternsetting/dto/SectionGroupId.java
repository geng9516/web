package jp.smartcompany.job.modules.tmg.patternsetting.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 */
@Getter
@Setter
@ToString
public class SectionGroupId {

    /**
     *部署コード
     * */
    @TableField("SECTIONID")
    private String sectionId;

    /**
     *グループコード
     * */
    @TableField("GROUPID")
    private String groupId;
}
