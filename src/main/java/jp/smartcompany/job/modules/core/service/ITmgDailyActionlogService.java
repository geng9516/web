package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyActionlogDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyLogVO;

import java.util.List;

/**
 * <p>
 * [勤怠]日別情報操作ログ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgDailyActionlogService extends IService<TmgDailyActionlogDO> {

    /**
     * 実績情報の変更ログ表示用表関数から値を取得する
     *
     * @param custID     顧客コード
     * @param compCode   　法人コード
     * @param targetUser 　対象者
     * @param day        　対象日
     * @param language   　言語
     * @return
     */
    List<DailyLogVO> buildSQLForSelectTmgSelectDailyActionLog(String custID, String compCode, String targetUser, String day, String language);
}
