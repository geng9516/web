package jp.smartcompany.job.modules.tmg.tmgnotification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Wang Ziyue
 * Vo for  MasterTmgNtfTypeDispAppList
 */
@Getter
@Setter
@ToString
public class MgdNtfTypeDispAppVo {
    /**0 グループの区分*/
    private String groupId;
    /**1 グループの名称*/
    private String groupName;
    /**2 申請区分*/
    private String ntfId;
    /**3 申請区分名称*/
    private String ntfName;
    /**4 表示項目タイプ*/
    private String viewType;
    /**5 申請事由必須有無*/
    private String confirmComment;
    /**6 注釈*/
    private String biko;
    /**7 添付ファイル必須有無*/
    private String confirmFile;



}
