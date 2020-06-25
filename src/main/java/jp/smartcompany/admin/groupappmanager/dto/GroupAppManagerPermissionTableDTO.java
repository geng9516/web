package jp.smartcompany.admin.groupappmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 起動権限設定 権限一覧DTOクラス
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class GroupAppManagerPermissionTableDTO implements Serializable {

    private static final long serialVersionUID = 2371710810836561779L;
    /** 権限設定 */
    private List<GroupAppManagerPermissionDTO> list;
    /** サブアプリ配下フラグ(true:サブアプリ配下である, false:サブアプリ配下でない) */
    private Boolean subApp;
    /** オブジェクト名称 */
    private String objectName;
    /** 種別 */
    private String type;

}
