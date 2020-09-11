package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * 該当条件編集画面(基点組織定義(選択リスト))用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class BaseSectionSelectDTO {

    /** 各情報のコード */
    private String codeInfo;
    /** 各情報の名称 */
    private String nameInfo;
    /** 各情報のその他(階層コード) */
    private String genericInfo;

}
