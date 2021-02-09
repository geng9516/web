package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class PagerLinkDTO {

    public PagerLinkDTO() {
        this.totalCount = 0;
        this.totalPage = 1;
        this.pageFrom = 0;
        this.pageTo = 0;
        this.currentPage = 1;
        this.pageForPaging = 10;
        this.pagerCondition = 20;
    }

    /**
     * 総件数
     */
    private Integer totalCount;
    /**
     * 総ページ数
     */
    private Integer totalPage;
    /**
     * ページ内件数From
     */
    private Integer pageFrom;
    /**
     * ページ内件数To
     */
    private Integer pageTo;
    /**
     * 表示ページ
     */
    private Integer currentPage;
    /**
     * ページング単位
     */
    private Integer pageForPaging;
    /**
     * 表示件数：設定値
     */
    private Integer pagerCondition;
    /**
     * 表示件数設定リスト
     */
//    private List<SelectItem> condList;
    /**
     * ページリスト
     */
    private List<SelectItemDTO> pageList;
    /**
     * ページリンク：前へボタン
     */
    private String previousPage;
    /**
     * ページリンク：次へボタン
     */
    private String nextPage;
    /**
     * ページ件数表示 TOTAL文言
     */
    private String pageCountTotalStr;

}
