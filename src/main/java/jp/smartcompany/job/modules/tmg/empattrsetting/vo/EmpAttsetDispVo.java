package jp.smartcompany.job.modules.tmg.empattrsetting.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpAttsetDispVo {


    /**
     * 列名
     */
    private String mgdCitemname;

    /**
     * 显示flg
     */
    private int mgdNdispflg;

    /**
     * 编辑flg
     */
    private int mgdNedithlg;

    /**
     * 排序
     */
    private int mgdNwidth;

}
