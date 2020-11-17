package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgTrigger.TmgTriggerMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.ITmgTriggerService;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Repository
public class TmgTriggerServiceImpl extends ServiceImpl<TmgTriggerMapper, TmgTriggerDO> implements ITmgTriggerService {
    /**
     * 月次一覧、また日次登録（承認）画面表示時打刻反映処理　トリガーに追加する
     * 　月次一覧画面表示時：表示対象月で運用日以前の打刻反映対象となる全ての日のレコード分登録します。
     * 　日次登録（承認画面）表示時：表示対象日の打刻反映対象となる日のレコードを登録します。
     * TMG_DAILYから打刻未反映のデータを対象に処理するので、打刻反映対象のデータが無い場合はトリガーへ登録されないので打刻反映処理は実施されません。
     * ※月次一覧、日次登録（承認）表示時の打刻反映処理以外では使用を想定していないので使用しないで下さい。
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param targetUser 対象者
     * @param userCode　ユーザID
     * @param action　アクション
     * @param stDate　開始
     * @param endDate　終了
     * @return
     */
    @Override
    public int buildSQLForInsertTriggerByTimePunch(String custID, String compCode, String targetUser, String userCode, String action, String stDate, String endDate){

        Map<String, Object> map = MapUtil.newHashMap(7);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("userCode", userCode);
        map.put("action", action);
        map.put("stDate", stDate);
        map.put("endDate", endDate);

        return baseMapper.buildSQLForInsertTriggerByTimePunch(map);
    }

    /**
     * トリガーに追加する
     *
     * @param custID     顧客コード
     * @param compCode    法人コード
     * @param targetUser 対象者
     * @param day        基準日
     * @param userCode   ユーザコード
     * @param txtAction  アクション
     * @return int
     */
    @Override
    public int buildSQLForInsertTrigger(String custID, String compCode, String targetUser, String day, String userCode, String txtAction){


        Map<String, Object> map = MapUtil.newHashMap(6);

        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("day", day);
        map.put("userCode", userCode);
        map.put("txtAction", txtAction);

        return baseMapper.buildSQLForInsertTrigger(map);
    }


    /**
     * 月次一覧、また日次承認画面表示時打刻反映処理　トリガーに追加する
     * 　月次一覧画面表示時：表示対象月で運用日以前の打刻反映対象となる全ての日のレコード分登録します。
     * 　日次承認画面表示時：表示対象日の打刻反映対象となる日のレコードを登録します。
     * <p>
     * TMG_DAILYから打刻未反映のデータを対象に処理するので、打刻反映対象のデータが無い場合はトリガーへ登録されないので打刻反映処理は実施されません。
     * ※月次一覧、日次承認表示時の打刻反映処理以外では使用を想定していないので使用しないで下さい。
     *
     * @param userCode ユーザコード
     * @param action   アクション
     * @param stDate   打刻反映処理対象開始日
     * @param endDate  打刻反映処理対象終了日
     * @param empSql   選択した組織のすべて職員取得SQL
     * @return int
     */
    @Override
    public int buildSQLForInsertTmgTriggerByTimePunch(String userCode, String action, String stDate, String endDate, String empSql) {
        Map<String, Object> map = MapUtil.newHashMap(5);

        map.put("userCode", userCode);
        map.put("action", action);
        map.put("stDate", stDate);
        map.put("endDate", endDate);
        map.put("empSql", empSql);

        return baseMapper.buildSQLForInsertTmgTriggerByTimePunch(map);
    }

    /**
     * 勤怠トリガーテーブルに挿入するSQL文を返却します。
     * <p>
     * 日別テーブルから更新対象者のみ検出し、勤怠トリガーテーブルにレコードを
     * 挿入します。
     * </P>
     *
     * @param loginUserCode 登録者
     * @param programId     　登録プログラムID
     * @param action        　アクション
     * @param yyyymmdd      　基準日
     * @param rowIdList     　rowidリスト
     * @return int
     */
    @Override
    public int buildSQLForInsertTmgTrigger(String loginUserCode, String programId, String action, String yyyymmdd, List<String> rowIdList){

        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("loginUserCode", loginUserCode);
        map.put("programId", programId);
        map.put("action", action);
        map.put("yyyymmdd", yyyymmdd);
        map.put("rowIdList", rowIdList);

        return baseMapper.buildSQLForInsertTmgTrigger(map);
    }

    /**
     *[勤怠]トリガーの挿入
     * @param loginUserCode 登録者
     * @param pWorkDate　基準日（今日）
     * @param empsql　組織内すべて職員取得sqｌ
     * @param baseDate　基準日（対象日）
     * @return 件数
     */
    @Override
    public int buildSQLInsertTrigger(String loginUserCode,  String pWorkDate,String empsql, String baseDate){
        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("loginUserCode", loginUserCode);
        map.put("pWorkDate", pWorkDate);
        map.put("empsql", empsql);
        map.put("baseDate", baseDate);

        return baseMapper.buildSQLInsertTrigger(map);
    }

    /**
     * トリガーテーブルにデータを入れる
     *
     * @param custID       顧客コード
     * @param compCode     　法人コード
     * @param dispUserCode 　対象者
     * @param userCode     　更新者
     * @param baseDate     　対象日
     */
    @Override
    public void buildSQLForInsertTriggerTable(String custID, String compCode, String dispUserCode, String userCode, String baseDate) {
        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("dispUserCode", dispUserCode);
        map.put("userCode", userCode);
        map.put("baseDate", baseDate);

        baseMapper.buildSQLForInsertTriggerTable(map);
    }

    /**
     * TMG_TRIGGERへINSERTする
     *
     * @param custID 顧客コード
     * @param compCode  法人コード
     * @param userCode 更新者
     * @param programId プログラムID
     * @return
     */
    @Override
    public int buildSQLForInsertTmgTrgger(String custID, String compCode, String userCode, String programId){
       return baseMapper.buildSQLForInsertTmgTrgger(custID, compCode, userCode, programId);
    }

    @Override
    public int stamp(String ctpTypeid, String employeeId, String modifierprogramid, Date minDate, Date maxDate) {
       return baseMapper.stamp(ctpTypeid,employeeId,modifierprogramid, minDate,maxDate);
    }

}
