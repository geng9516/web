package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.DigestUtil;
import jp.smartcompany.admin.usermanager.dto.UserManagerUpdateParamDTO;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ldap.Rdn;
import java.sql.Timestamp;
import java.util.*;

@Service("userManagerEditCommonLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerEditCommonLogicImpl implements UserManagerEditCommonLogic {

  private final ScCacheUtil cacheUtil;
  private final PsSearchCompanyUtil searchCompanyUtil;
  private final IMastAccountService mastAccountService;
  private final IMastPasswordService passwordService;

  /**
   * パスワード更新.
   * @param customerId 顧客コード
   * @param currentUserId ログインユーザID
   * @param language 言語区分
   * @param userIds ユーザーID
   * @param passwordType パスワード区分
   * @param psPassword パスワード
   * @param forceChangePassword 「次回ログイン時にパスワードを変更」チェック
   * @return 更新パスワードMap
   */
  @Override
  public Map<String,String> updatePassword(
          String customerId,
          String currentUserId,
          String language,
          List<String> userIds,
          Integer passwordType,
          String psPassword,
          Boolean forceChangePassword) {
    Date now = DateUtil.date();
    String userId = SecurityUtil.getUserId();
    //===================================================
    //アカウント情報＋メアド取得処理(2)
    //===================================================
    String sUpdatePassword = "";
    String sCryptPassword  = "";
    if (passwordType.equals(1)) {
      sUpdatePassword = createPassword(passwordType,psPassword);
      sCryptPassword  = DigestUtil.md5Hex(sUpdatePassword);
    }
    //パスワード設定日の設定
    String pwdDate = SysUtil.transDateToString(now);
    //「次回ログイン時にパスワードを変更する」のときは1900/01/01を設定する
    if (forceChangePassword) {
      pwdDate = "1900/01/01";
    }
    // 法人検索対象範囲取得
    List<String> lComps = searchCompanyUtil.getCompList(now);
    List<UserManagerUpdateParamDTO> dtoParam =
            mastAccountService.selectPasswordForUpdateInfo(
                    pwdDate, sCryptPassword
                    , userId
                    , sUpdatePassword
                    , customerId
                    , userIds
                    , lComps);
    //===================================================
    //履歴のインクリメント(3)-1
    //===================================================
    passwordService.updateHistoryNo(userIds);
    // メール送信モードの取得
//    String sMailSendMode = cacheUtil.getSystemProperty(USER_MANAGER_MAIL_SEND_MODE);
    Map<String,String> passwordMap = MapUtil.newHashMap();

    List<MastPasswordDO> updatePasswordList = CollUtil.newArrayList();
    for (UserManagerUpdateParamDTO dto : dtoParam) {
      String account = dto.getMaCaccount();
      if (account != null) {
        String password = dto.getOriginalPassword();
        //ユーザごとにパスワードを発行する
        if (passwordType.equals(2)) {
          password = createPassword(passwordType, password);
          dto.setOriginalPassword(password);
          dto.setMapCpassword(DigestUtil.md5Hex(password));
        }
        // 更新者情報の設定
        dto.setMapCmodifieruserid(userId);
        dto.setMapDmodifieddate(now);

        MastPasswordDO passwordDO = new MastPasswordDO();
        BeanUtil.copyProperties(dto,passwordDO);
        updatePasswordList.add(passwordDO);
        //===================================================
        //パスワード更新(4)
        //===================================================

        // Map設定時に暗号化
        passwordMap.put(dto.getMapCuserid(), encrypt(dto.getOriginalPassword()));

        // 暂时不要发送邮件
        //===================================================
        //パスワード変更メール送信(8)
        //===================================================
        // メール送信モードがyesで、パスワードを変更したときはパスワード変更メールを、
        // アカウントの利用開始処理を行ったときはアカウント発行メールを送信する
//        if ((sMailSendMode == null || sMailSendMode.equalsIgnoreCase("yes")) && PASSWORD_CHANGED_MAIL_ID.equals(getMailId())) {
//          this.sendPasswordChangeMail(dto, psLanguage);
//        } else if ((sMailSendMode == null || (sMailSendMode != null && sMailSendMode.equalsIgnoreCase("yes")))
//                && ADD_ACCOUNT_MAIL_ID.equals(getMailId())) {
//          this.sendAddAccountMail(dto, psLanguage, psBaseURL);
//        }
        passwordService.saveBatch(updatePasswordList);

      }
    }
    return passwordMap;
  }

  /**
   * パスワード作成.
   * @param sPassType パスワード区分
   * @param sPassword パスワード
   * @return パスワード
   */
  public String createPassword(Integer sPassType, String sPassword) {

    String sRetPassword = "";
    if (sPassType == null || (sPassType.equals(1) && sPassword == null)) {
      return sRetPassword;
    }
    if (sPassType.equals(1)) {
      //入力されたパスワード
      sRetPassword = decrypt(sPassword);
    } else {
      int maxLen = Integer.parseInt(cacheUtil.getSystemProperty(PROP_PW_MAX_LEN));
      //自動生成パスワード
      sRetPassword = getRandomPassword(maxLen);
    }
    return sRetPassword;
  }

  /**
   * リクエスト渡し用復号化.
   * @param str 暗号化文字列
   * @return 復号結果
   */
  private String decrypt(String str) {
    String decryptStr = "";

    for (int i = 0; i < str.length(); i++) {
      // コードをずらす
      int cd = str.charAt(i) - 1;

      if (cd <= 0x1f) {
        cd = cd + 0x5f;
      }
      char charCd = (char) cd;
      decryptStr = decryptStr + String.valueOf(charCd);
    }
    decryptStr = (String) Rdn.unescapeValue(decryptStr);

    return decryptStr;
  }

  /**
   * ランダムパスワード作成.
   * @param length パスワード長
   * @return パスワード
   */
  private String getRandomPassword(int length) {
    // 初期化
    Random rnd = new Random();
    int ran = rnd.nextInt(128);
    // パスワード同一化を防ぐためメモリ使用量とランダム値も加算
    long seed = System.currentTimeMillis() + Runtime.getRuntime().freeMemory() + ran;
    Random rand = new Random(seed);

    String text = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ_";
    int roof = text.length();
    char [] character = new char[roof];
    for (int i = 0; i < roof; i++) {
      character[i] = text.charAt(i);
    }
    StringBuilder pass = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      pass.append(character[rand.nextInt(roof)]);
    }

    return pass.toString();
  }

  /**
   * リクエスト渡し用暗号化
   * @param str 暗号化対照文字列
   * @return 暗号結果
   */
  private String encrypt(String str) {
    StringBuilder encryptStr = new StringBuilder();

    // コード化
    str = (String) Rdn.unescapeValue(str);
    for (int i = 0; i < str.length(); i++) {
      // コードをずらす
      int cd = str.charAt(i) + 1;

      if(cd >= 0x7f) {
        cd = cd - 0x5f;
      }
      char charCd = (char) cd;
      encryptStr.append(charCd);
    }
    return encryptStr.toString();
  }

}
