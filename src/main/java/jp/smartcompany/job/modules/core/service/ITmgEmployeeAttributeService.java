package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgEmployeeAttributeDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.TmgEmployeeAttributeVO;

import java.util.List;

/**
 * <p>
 * public.tmg_employee_attribute 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgEmployeeAttributeService extends IService<TmgEmployeeAttributeDO> {

    /**
     * [勤怠]基本情報属性を検索
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param date 今日
     * @param targetUser　対象者
     * @param month　対象月
     * @param psType　種別
     * @param overHoursReasonType　比較対象種別
     * @return List<TmgEmployeeAttributeVO>
     */
    List<TmgEmployeeAttributeVO> buildSQLForSelectTmgEmployeeAttribute(String custID, String compCode, String date, String targetUser, String month, String psType, String overHoursReasonType);


    /**
     * 画面初期表示時に月45時間の申請事由の存在チェックを行い、
     * システム年月が表示対象年月を過ぎているため遅延理由の登録が必要な場合に、
     * 遅延理由の入力欄＝有効、かつ、メール送信＝未送信、のレコードを作成します
     *
     * @param psType 種別
     * @param modifierProgramId 更新プログラムID
     * @param custId 顧客コード
     * @param compCode 法人コード
     * @param targetUser 対象者
     * @param month 対象月
     * @param userCode 変更者
     * @param onOff ONOFF
     * @param view view
     * @param mailUnsend mailUnsend
     */
    void buildSQLForInsertTmgEmployeeAttributeActDisp(String psType, String modifierProgramId, String custId, String compCode, String targetUser, String month, String userCode,String onOff,String view,String mailUnsend);

    /**
     * 画面初期表示時に月45時間の申請事由の存在チェックを行い、
     * システム年月が表示対象年月を過ぎているため遅延理由の登録が必要な場合に、
     * 遅延理由の入力欄＝有効、かつ、メール送信＝未送信、のレコードを作成します
     *
     * なお、
     * 　　月45時間のレコードが既に存在する　＆　データが無効、
     * の場合はこのUPDATE文を返します。
     * レコードが存在しない場合は、buildSQLForInsertTmgEmployeeAttributeActDispがコールされ、INSERT文が返されます。
     *
     * なお、「データが無効」な状態になるのは、下記2つの場合です
     * ・画面表示時の自動生成によりレコードが作成され、その後、画面から変更をしていない場合
     * ・一旦申請を上げた後、取り下げを行った場合
     *
     * ※このメソッド自体は、単にUPDATE文を返すだけであり、↑の諸々の判定は親メソッドmonthSumOverWorkで実施します
     * @param userCode　更新者
     * @param custId　顧客コード
     * @param compCode　法人コード
     * @param targetUser　対象者
     * @param month　対象月
     * @param type　種別
     * @param modifierProgramId　更新プログラムID
     * @param view　view
     * @param mailUnsend　mailUnsend
     */
    void buildSQLForUpdateTmgEmployeeAttribute(String userCode, String custId, String compCode , String targetUser, String month,String type,String modifierProgramId,String view,String mailUnsend);

    /**
     * 超過勤務対象有無取得用
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param targetUser 対象者
     * @param day        基準日
     * @return String
     */
    String buildSQLForSelectTargetForOverTime(String custID, String compCode, String targetUser, String day);
}
