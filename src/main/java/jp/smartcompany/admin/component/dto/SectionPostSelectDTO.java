package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 該当条件編集画面(組織・役職定義(選択リスト))用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostSelectDTO implements Serializable {

    private static final long serialVersionUID = 2477534855736288116L;
    /** 各情報のコード */
    private String codeinfo;
    /** 各情報の名称 */
    private String nameinfo;

}
