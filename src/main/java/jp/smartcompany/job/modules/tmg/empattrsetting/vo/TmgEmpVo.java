package jp.smartcompany.job.modules.tmg.empattrsetting.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TmgEmpVo {

    private String meCemployeeidCk;
    private String meCkanjiname;
    private String hdCpostname;

    private String temCworktypeid;
    private String temCworktypename;

    private String tmgItemsManageflg;
    private String tmgItemsExcludeOvertime;
    private String tphaNavgworktime;

    private String tphaNworkingdaysWeek;
    private String tmgItemsWorkplaceCode;
    private String tmgItemsWorkplaceName;
    private String begindate;


    private boolean manageFlg;
    private boolean excludeOvertime;
}
