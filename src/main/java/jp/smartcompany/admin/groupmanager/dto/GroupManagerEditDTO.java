package jp.smartcompany.admin.groupmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jp.smartcompany.admin.component.dto.BaseSectionRowDTO;
import jp.smartcompany.admin.component.dto.SectionPostListDTO;
import jp.smartcompany.admin.component.dto.SectionPostRowDTO;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class GroupManagerEditDTO {

    // グループ定義条件マスタ ID
    private Long mgpId;
    // groupId 如果是新增则不传，修改时才传入
    private Long mgId;
    @NotBlank
    private String groupId;
    private Boolean mgPublishing;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date endDate;
    private String baseFlag;
    private Long weightAge;
    private String groupName;
    private Long peopleCount;
    private String remark;
    private String belowSingle;
    // 組織・役職条件選択情報 格納リスト 法人一覧
    private SectionPostListDTO sectionPostList;
    // 条件式定義コンポーネント用
    private List<QueryConditionRowDTO> queryConditionList;
    // 該当条件編集 - 定義情報取得(法人＆職員リスト)
    private List<SectionPostRowDTO> employList;
    // 該当条件編集画面(基点組織定義(行単位))用Dtoクラス
    private List<BaseSectionRowDTO> baseSectionList;

}
