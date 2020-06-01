package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * Vo for  ntfActionLog
 */
@Getter
@Setter
@ToString
public class ntfActionLogVo {
    private String empname;//更新者
    private String ntfactionname;// 操作
    private String statusname;// 状態
    private String capprovalLevel;// 状態用
    private String tnalDmodifieddate;// 更新日時
    private String tnalCupdateccomment; // 更新時コメント
}
