package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgErrmsgDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.ErrMsgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * [勤怠]エラーメッセージ格納テーブル Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgErrmsgMapper extends BaseMapper<TmgErrmsgDO> {

    /**
     * エラーメッセージを取得する
     */
    ErrMsgDto buildSQLForSelectErrMsg(Map<String, Object> map);

    /**
     * エラーメッセージをインサートする
     */
    int buildSQLForInsertNoErrMsg(Map<String, Object> map);

    /**
     * 月次承認処理時、エラーチェックを行った結果をエラーメッセージテーブルへ登録する
     */
    int buildSQLForInsertErrMsgForMonthlyApproval(Map<String, Object> map);

    /**
     * エラーメッセージをインサートする
     */
    int buildSQLForInsertErrMsg(@Param("custID") String custID,
                                @Param("compCode")String compCode,
                                @Param("userCode")String userCode,
                                @Param("programId")String programId,
                                @Param("language")String language,
                                @Param("targetUser")String targetUser,
                                @Param("day")String day);
}
