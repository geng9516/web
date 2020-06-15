package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyOutputLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * [勤怠]月次集計処理ログ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgMonthlyOutputLogService extends IService<TmgMonthlyOutputLogDO> {

    /**
     * 月次集計データ作成処理のジョブ番号を検索するSQL文を生成し返します。
     */
    String selectSeq();
}
