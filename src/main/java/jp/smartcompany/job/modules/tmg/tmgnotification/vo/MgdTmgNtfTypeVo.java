package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 * Vo for  SelectMasterTmgNtfType
 */
@Getter
@Setter
@ToString
public class MgdTmgNtfTypeVo {
    // 0 グループの区分
    private String gMgdCmastercode;
    // 1 グループの名称
    private String gMgdCgenericdetaildesc;
    // 2 申請区分
    private String t1MgdCmastercode;
    // 3 申請区分名称
    private String t1MgdCgenericdetaildesc;
    // 4 表示項目タイプ
    private String t1MgdNsparenum2;
    // 5 申請事由必須有無
    private String t1MgdCsparechar3;
    // 6 注釈
    private String t2MgdCsparechar3;
    // 7 添付ファイル必須有無
    private String t2MgdNsparenum2;
    // 8 決裁レベル
    private String sparechar2;
    //決裁レベル(名称)
    private String genericdetaildesc;

}
