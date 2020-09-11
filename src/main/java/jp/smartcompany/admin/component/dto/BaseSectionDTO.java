package jp.smartcompany.admin.component.dto;

import jp.smartcompany.job.modules.core.pojo.entity.MastGroupbasesectionDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *
 * 該当条件編集画面(基点組織定義)用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class BaseSectionDTO {

    /** 基点組織設定情報一覧 */
    private List <BaseSectionRowDTO> rowList;
    /** 基点組織設定情報 削除用一覧 */
    private List<MastGroupbasesectionDO> deleteBaseSection;

}
