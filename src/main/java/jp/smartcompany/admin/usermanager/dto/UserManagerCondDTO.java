package jp.smartcompany.admin.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ユーザ管理検索条件用DTO
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class UserManagerCondDTO {

    /**
     * サイトID
     */
    private String siteId;
    /**
     * 法人コード
      */
    private String companyId;
    /**
     * 表示するページ
     */
    private Integer page;
    /**
     * 表示件数
     */
    private Integer limit;
    /**
     * 排序字段
     */
    public String sidx;
    /**
     * 排序方式
     */
    public String order;
    /**
     * 升序
     */
    public String asc;
    /**
     * 是否需要返回数据总数
     */
    public String sum;
    /**
     * 部署ID
     */
    private String sectionId;
    /**
     * 社員番号
     */
    private String empId;
    /**
     * 氏名条件
     */
    private String empName;
    /**
     * 部署条件(法人コード)
     */
    private String sectionCompanyId;
    /**
     * 絞り込み区分
     */
    private Integer searchType;
}
