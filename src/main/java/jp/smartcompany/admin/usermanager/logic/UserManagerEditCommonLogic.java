package jp.smartcompany.admin.usermanager.logic;

import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;

import java.util.Date;
import java.util.Map;
import java.util.List;

public interface UserManagerEditCommonLogic {

  String MESSAGE_ID_DUPLICATE_AUTO = "自動アカウント重複";
  /** 定数:メールID */
  String PASSWORD_CHANGED_MAIL_ID = "PasswordChanged";
  /** 定数:メールID **/
  String ADD_ACCOUNT_MAIL_ID = "AddAccount";

  // 定数：有効期間（日数）
  String PROP_PW_VALID_PERIOD = "PasswordValidPeriod";
  // 定数：最小文字数
  String PROP_PW_MIN_LEN = "PASSWORD_MIN_LEN";
  // 定数：最大文字数
  String PROP_PW_MAX_LEN = "PASSWORD_MAX_LEN";
  // 定数：最大リトライ回数
  String PROP_LOGIN_RETRY = "LoginRetry";
  // 定数：再利用禁止回数
  String PROP_CHANGE_PW_LIMITATION_COUNT = "ChangePasswordLimitationCount";
  /** システムプロパティ：メール送信モード */
  String USER_MANAGER_MAIL_SEND_MODE = "UserManagerMailSendMode";

  /**定数：ステータス　入社前*/
  String STATUS_BEFORE_ENTRANCE = "STATUS_BEFORE_ENTRANCE";
  /**定数：ステータス　入社後未登録*/
  String STATUS_AFTER_ENTRANCE = "STATUS_AFTER_ENTRANCE";
  /**定数：ステータス　ロックアウト*/
  String STATUS_LOCKOUT = "STATUS_LOCKOUT";
  /**定数：ステータス　無効*/
  String STATUS_INVALID = "STATUS_INVALID";
  /**定数：ステータス　退職後未削除*/
  String STATUS_AFTER_RETIRE = "STATUS_AFTER_RETIRE";


  String STATUS_AFTER_ENTRANCE_TEXT="入社後未登録";
  String STATUS_AFTER_RETIRE_TEXT="退職後未削除";
  String STATUS_BEFORE_ENTRANCE_TEXT="入社前";
  String STATUS_INVALID_TEXT="無効";
  String STATUS_LOCKOUT_TEXT="ロックアウト";

  Map<String,Object> updatePassword(
          String customerId,
          String currentUserId,
          String language,
          List<String> userIds,
          Integer passwordType,
          String password,
          Boolean forceChangePassword);

  String getStatus(UserManagerListDTO poUserManagerDto);

  void accountInsert(
          MastAccountDO accountDto,
          List<UserManagerDTO> checkListOld,
          String psCustomerid,
          String psAccount,
          String psUserid,
          Date now);
}
