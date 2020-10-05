package jp.smartcompany.admin.usermanager.logic;

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

  Map<String,String> updatePassword(
          String customerId,
          String currentUserId,
          String language,
          List<String> userIds,
          Integer passwordType,
          String password,
          Boolean forceChangePassword);

}
