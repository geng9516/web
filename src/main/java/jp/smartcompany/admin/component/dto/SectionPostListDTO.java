package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 該当条件編集画面(組織・役職定義(法人報配下のデータ))用Dtoクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class SectionPostListDTO {

    private String companyId;
    private String companyName;
    /** 組織・役職条件選択情報 格納リスト 組織一覧(指定法人) */
    private List<SectionPostRowListDTO> sectionList;
    /** 組織・役職条件選択情報 格納リスト 役職一覧(指定法人) */
    private List<SectionPostRowDTO> postList;
    /** 組織・役職条件選択情報 格納リスト 社員一覧(指定法人) */
    private List<SectionPostRowDTO> employList;
    /** 組織・役職条件選択情報 格納リスト 所属長一覧(指定法人＆指定組織) */
    private List<SectionPostRowDTO> bossSectionList;

}
