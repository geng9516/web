package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgErrmsgDO;
import jp.smartcompany.job.modules.core.mapper.TmgErrmsg.TmgErrmsgMapper;
import jp.smartcompany.job.modules.core.service.ITmgErrmsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.ErrMsgDto;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * [勤怠]エラーメッセージ格納テーブル 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgErrmsgServiceImpl extends ServiceImpl<TmgErrmsgMapper, TmgErrmsgDO> implements ITmgErrmsgService {

    /**
     * エラーメッセージを取得する
     *
     * @param custID   顧客コード
     * @param compCode  法人コード
     * @param userCode ユーザコード
     */
    @Override
    public ErrMsgDto buildSQLForSelectErrMsg(String custID, String compCode, String userCode) {

        Map<String, Object> map = MapUtil.newHashMap(3);

        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("userCode", userCode);

        return baseMapper.buildSQLForSelectErrMsg(map);
    }

    /**
     * エラーメッセージをインサートする
     *
     * @param custId    顧客コード
     * @param compId    法人コード
     * @param userId    ユーザコード
     * @param programId プログラムID
     * @param lang      　元号
     * @return 件数
     */
    @Override
    public int buildSQLForInsertNoErrMsg(String custId, String compId, String userId, String programId, String lang) {


        Map<String, Object> map = MapUtil.newHashMap(5);

        map.put("custId", custId);
        map.put("compId", compId);
        map.put("userId", userId);
        map.put("programId", programId);
        map.put("lang", lang);

        return baseMapper.buildSQLForInsertNoErrMsg(map);
    }

    /**
     * 月次承認処理時、エラーチェックを行った結果をエラーメッセージテーブルへ登録する
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param userCode ユーザコード
     * @param programId　プログラムID
     * @param language　　言語
     * @param txtcCmployeeid　職員番号
     * @param txtDyyyymm　該当月
     * @return
     */
    @Override
    public int buildSQLForInsertErrMsgForMonthlyApproval(String custID, String compCode, String userCode, String programId, String language, String txtcCmployeeid, String txtDyyyymm){
        Map<String, Object> map = MapUtil.newHashMap(7);

        map.put("custID,", custID);
        map.put("compCode", compCode);
        map.put("userCode", userCode);
        map.put("programId", programId);
        map.put("language", language);
        map.put("txtcCmployeeid", txtcCmployeeid);
        map.put("txtDyyyymm", txtDyyyymm);

        return baseMapper.buildSQLForInsertErrMsgForMonthlyApproval(map);
    }

    /**
     * エラーメッセージに追加する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param userCode   ユーザコード
     * @param programId  　プログラムID
     * @param language   　　言語
     * @param targetUser 職員番号
     * @param day        該当日
     * @return
     */
    @Override
    public int buildSQLForInsertErrMsg(String custID, String compCode, String userCode, String programId, String language, String targetUser, String day){

        return baseMapper.buildSQLForInsertErrMsg(custID, compCode, userCode, programId, language, targetUser, day);
    }

}
