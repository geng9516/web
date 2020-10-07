package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import jp.smartcompany.job.modules.core.pojo.entity.TmgNtfAttachedfileDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class NotificationDispDetailVo {


    /**0 職員番号*/
    private String tntfCemployeeid;
    /**1 漢字氏名*/
    private String tntfCemployeeidName;
    /**2 申請内容*/
    private String tntfCtype;
    /**3 開始日*/
    private String tntfDbegin;
    /**4 終了日*/
    private String tntfDend;
    /**5 解除日*/
    private String tntfDcancel;
    /**6 申請日*/
    private String tntfDnotification;
    /**7 更新日*/
    private  String tntfDmodifieddate;
    /**8 ステータス*/
    private String tntfCstatusflg;
    /**9 ステータス名称*/
    private String tntfCstatusflgName;
    /**10 申請者*/
    private String tntfCalteremployeeid;
    /**11 代理申請者氏名*/
    private String tntfCalteremployeeidName;
    /**12 申請番号*/
    private String tntfCntfNo;
    /**13 解除日当日(遡り判定用)*/
    private String tntfDcancel2;
    /**14 再申請可能な申請種類*/
    private String remakeApply;
    /**16site 显示*/
    private String tntfCtypeChar5;
//    private String cApprovalLevel;
    /**18 typeId*/
    private String tntfCtypeCode;
    /**20 次の承認者*/
    private String[] ntfapprover;
    /**21 0:全取消、1:部分取消*/
    private String allCancellation;
    /**22 開始時刻*/
    private String tntfNtimezoneOpen;
    /**23 終了時刻*/
    private String tntfNtimezoneClose;
    /**24 曜日文言*/
    private String dayOfWeek;
    /**25*/
    private String finalApprovelLevel;
    /**26 申請事由等*/
    private String tntfCowncomment;


    // 承認者コメント
    private String tntfCbosscomment;
    // 承認日
    private String tntfDboss;
    // 28 解除者コメント
    private String tntfCcancelcomment;
    //file　path
    private String filePath;
    //  申請区分略称を取得
    private String ntfName;
    //  添付ファイル
    private List<TmgNtfAttachedfileDO> tmgNtfAttachedfileDoList = new ArrayList<TmgNtfAttachedfileDO>();
    //  申請ログ
    private List<NtfActionLogVo> tmgNtfactionlogDOList = new ArrayList<NtfActionLogVo>();
    //振替flag
    private boolean isSubstituted;
}
