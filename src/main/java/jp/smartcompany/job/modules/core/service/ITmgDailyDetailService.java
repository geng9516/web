package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;
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

}
