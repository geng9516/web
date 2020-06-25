package jp.smartcompany.admin.groupappmanager.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of={"mgpCsite","mgpCsubapp"})
public class GroupAppManagerPermissionDTO {

    private Long mtrId;
    private String mgpCcompanyid;
    private String mgpCsystemid;
    private String mgpCgroupid;
    private String mgpCobjectid;
    private String mgpCsite;
    private String mgpCapp;
    private String mgpCsubapp;
    private String mgpCbutton;
    private String mgpCscreen;
    /** 権限 無し(0)・許可(1)・拒否(2)*/
    private String permission;
    /** オブジェクト名称 */
    private String objectName;
    /** タイプ */
    private String type;
    private String mtrNseq;
    private String mgNweightage;
    private String mtrCurl2;
    private String mtrIcon;
    /** セルの背景色 */
    private String bgColor;

}
