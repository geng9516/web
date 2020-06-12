package jp.smartcompany.job.modules.tmg.monthlyoutput.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TmgMoYearListVo {

     /**yyyy/mm 形式*/
    private String yyyymm;
    /**直近の集計処理を実行した日時("yyyy/mm/dd hh24:mi:ss"形式)*/
    private String totalTime;
    /**未承認者一覧   0:空白表示, 1:未承認者状況リンク表示*/
    private int notApproval;
    /**勤怠締め       0:空白表示, 1:締めリンク表示,          2:固定文言「締め完了」*/
    private int monthlyFixEmp;
    /**集計処理       0:空白表示, 1:集計処理リンク表示,      2:固定文言「集計中」*/
    private int totalStatus;
    /**集計処理の問題 0:空白表示, 1:集計処理の問題リンク表示*/
    private int totalAlert;
    /**月例データ     0:空白表示, 1:月例データdlリンク表示*/
    private int worksKintai;
    /**遡及データ     0:空白表示, 1:遡及データdlリンク表示*/
    private int worksRetro;
    /**締め未完了部局 0:空白表示, 1:締め未完了部局リンク表示*/
    private int monthlyFixDept;
    /**給与締め       0:空白表示, 1:確定リンク表示,          2:確定リンク表示(アラート), 3:確定解除リンク表示*/
    private int salaryFix;
    /**未承認者の人数*/
    private int  notApprovalCnt;
    /**集計時の問題の件数*/
    private int totaoAlertCnt;
    /**締め未完了部局数*/
    private int notFixdeptCnt;
    /**集計処理実行   0:不可能, 1:可能*/
    private int enableTotal;
    /**締め解除リンク表示制御   0:非表示, 1:表示*/
    private int linkRescission;

}
