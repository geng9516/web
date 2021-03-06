package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.ResultRest40tVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyDetailVO;

import java.util.List;

/**
 * <p>
 * [勤怠]日別情報詳細                    2007/02/23 予定実績を再統合。また、申請番号のカラ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgDailyDetailService extends IService<TmgDailyDetailDO> {

    /**
     * 日別詳細情報を取得する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param targetUser 対象者
     * @param day        対象日
     * @param language   言語
     * @param iMode      　Mode
     * @param bDel       　削除フラグ
     * @return List<DailyDetailVO>
     */
    List<DailyDetailVO> buildSQLForSelectDetail(String custID, String compCode, String targetUser, String day, String language, int iMode, boolean bDel);


    /**日別詳細情報より勤務予定時間外の休憩開始・終了時間を取得*/
    List<ResultRest40tVo> selectResultRest40t(String custId, String compId, String baseDate, String sql);
    /**
     * 一括承認データを更新する
     *
     * @param custID        顧客コード
     * @param compCode      法人コード
     * @param loginUserCode 更新者
     * @param programId     　更新プログラムID
     * @param yyyymmdd      　対象日
     * @param empIdList     　更新職員リスト
     * @param notWorkId     　超過勤務
     * @return 更新件数
     */
    int buildSQLForUpdateTmgDailyDetail(String custID, String compCode, String loginUserCode, String programId, String yyyymmdd, List<String> empIdList, String notWorkId);

}
