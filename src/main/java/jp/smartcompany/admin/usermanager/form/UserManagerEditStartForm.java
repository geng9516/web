package jp.smartcompany.admin.usermanager.form;

import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserManagerEditStartForm {

  private String password;
  /**利用開始日*/
  private Date startDate;
  /**入社日利用*/
  private Boolean useEntranceDate;
  /*パスワード発行区分 1: 自定义密码  2 系统自动生成*/
  private Integer passwordType;
  /**ユーザID*/
  private List<String> userIds;
  private List<UserManagerDTO> originalUserList;
  /**次回ログインパスワード変更フラグ*/
  private Boolean forceChangePassword;

}
