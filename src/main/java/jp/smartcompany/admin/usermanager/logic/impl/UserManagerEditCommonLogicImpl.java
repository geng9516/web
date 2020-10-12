package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerUpdateParamDTO;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ldap.Rdn;
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
  public Map<String,Object> updatePassword(
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
    Map<String,Object> passwordMap = MapUtil.newHashMap();

    List<MastPasswordDO> updatePasswordList = CollUtil.newArrayList();
    System.out.println(dtoParam);
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
//        passwordMap.put(dto.getMapCuserid(), encrypt(dto.getOriginalPassword()));
        passwordMap.put(dto.getMapCuserid(), dto.getOriginalPassword());
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

      }
    }
    List<MastPasswordDO> updatePasswords = CollUtil.newArrayList();
    List<MastPasswordDO> insertPasswords = CollUtil.newArrayList();
    updatePasswordList.forEach(item -> {
       List<MastPasswordDO> dbPasswords = passwordService.list(SysUtil.<MastPasswordDO>query().eq("MAP_DPWDDATE",SysUtil.transDateToString(now)).eq("MAP_CUSERID",item.getMapCuserid()));
       if (CollUtil.isNotEmpty(dbPasswords)){
         item.setMapId(dbPasswords.get(0).getMapId());
         updatePasswords.add(item);
       }else{
         item.setMapId(null);
         insertPasswords.add(item);
       }
    });
    if (CollUtil.isNotEmpty(updatePasswords)){
      passwordService.updateBatchById(updatePasswords);
    }
    if (CollUtil.isNotEmpty(insertPasswords)){
      passwordService.saveBatch(insertPasswords);
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
    String sRetPassword = sPassword;
    if (sPassType == null || (sPassType.equals(1) && sPassword == null)) {
      return sRetPassword;
    }
    if (sPassType.equals(1)) {
      //入力されたパスワード
//      sRetPassword = decrypt(sPassword);
    } else {
      int maxLen = Integer.parseInt(cacheUtil.getSystemProperty(PROP_PW_MAX_LEN));
      //自動生成パスワード
      sRetPassword = RandomUtil.randomString(maxLen);
    }
    return sRetPassword;
  }

  /**
   * ステータス取得
   * @param poUserManagerDto ユーザDtoデータ
   * @return sValue
   */
  @Override
  public String getStatus(UserManagerListDTO poUserManagerDto) {
    String sStatus = "";
    Calendar calSys = (Calendar) Calendar.getInstance().clone();
    calSys.set(Calendar.HOUR, 0);
    calSys.set(Calendar.MINUTE, 0);
    calSys.set(Calendar.SECOND, 0);
    Calendar calResult = (Calendar) Calendar.getInstance().clone();

    // 入社年月日がnullの時は基本情報のデータ開始日付を使用
    if (poUserManagerDto.getMeDdateofemployement() == null) {
      calResult.setTime(poUserManagerDto.getMeDstartdate());
    } else {
      calResult.setTime(poUserManagerDto.getMeDdateofemployement());
    }

    calResult.set(Calendar.HOUR, 0);
    calResult.set(Calendar.MINUTE, 0);
    calResult.set(Calendar.SECOND, 0);

    if (poUserManagerDto.getMaId() == null) {
      // アカウント情報なし
      if (calResult.compareTo(calSys) <= 0) {
        // 入社年月日<=システム日付のとき"入社後未登録"
        sStatus = STATUS_AFTER_ENTRANCE;
      } else if (calResult.compareTo(calSys) > 0) {
        // 入社年月日>システム日付のとき"入社前"(アカウント情報有無は無関係)
        sStatus = STATUS_BEFORE_ENTRANCE;
      }
    } else {
      // アカウント情報あり
      if (poUserManagerDto.getMeDdateofretirement() != null
              && poUserManagerDto.getMeDdateofretirement().before(calSys.getTime())
              && poUserManagerDto.getMaDend() != null
              && poUserManagerDto.getMaDend().compareTo(calSys.getTime()) >= 0) {
        // 退職後未削除
        sStatus = STATUS_AFTER_RETIRE;
      } else if (poUserManagerDto.getMaDend() != null
              && poUserManagerDto.getMaDend().before(calSys.getTime())) {
        // 無効
        sStatus = STATUS_INVALID;
      } else if (poUserManagerDto.getMaNpasswordlock() != null
              && poUserManagerDto.getMaNpasswordlock() == 1) {
        // ロックアウト
        sStatus = STATUS_LOCKOUT;
      } else if (calResult.compareTo(calSys) > 0) {
        // 入社年月日>システム日付のとき"入社前"(アカウント情報有無は無関係)
        sStatus = STATUS_BEFORE_ENTRANCE;
      }
    }
    //スタータスがnullの場合
    if (StrUtil.isBlank(sStatus)) {
      return "";
    }
    return getStatusText(sStatus);
  }

  private String getStatusText(String status) {
    if(StrUtil.equals(STATUS_BEFORE_ENTRANCE,status)) {
      return STATUS_BEFORE_ENTRANCE_TEXT;
    }
    if (StrUtil.equals(STATUS_AFTER_ENTRANCE,status)) {
      return STATUS_AFTER_ENTRANCE_TEXT;
    }
    if (StrUtil.equals(STATUS_LOCKOUT,status)) {
      return STATUS_LOCKOUT_TEXT;
    }
    if (StrUtil.equals(STATUS_INVALID,status)) {
      return STATUS_INVALID_TEXT;
    }
    if (StrUtil.equals(STATUS_AFTER_RETIRE,status)) {
      return STATUS_AFTER_RETIRE_TEXT;
    }
    return "";
  }

  /**
   * Account登録処理
   * @param accountDto
   * @param checkListOld
   * @param psCustomerid
   * @param psAccount
   * @param psUserid
   * @param now
   */
  @Override
  public void accountInsert(
          MastAccountDO accountDto,
          List<UserManagerDTO> checkListOld,
          String psCustomerid,
          String psAccount,
          String psUserid,
          Date now){

    //=========================================================
    //過去アカウント削除(1)
    //=========================================================
    if(CollUtil.isNotEmpty(checkListOld)){
      // アカウント削除
      Map<String,Object> accountParams = MapUtil.newHashMap();
      accountParams.put("MA_CCUSTOMERID",psCustomerid);
      accountParams.put("MA_CACCOUNT",psAccount);
      mastAccountService.removeByMap(accountParams);
      // パスワード削除
      Map<String,Object> passwordParams = MapUtil.newHashMap();
      passwordParams.put("accountInsert",checkListOld.get(0).getMaCuserid());
      passwordService.removeByMap(passwordParams);
    }
    //=========================================================
    //登録前処理
    //=========================================================
    accountDto.setMaCcustomerid(psCustomerid);
    accountDto.setMaCaccount(psAccount);
    accountDto.setMaCuserid(psUserid);
    accountDto.setMaDcreate(now);
    //=========================================================
    //登録処理
    //=========================================================
    mastAccountService.save(accountDto);
  }

}
