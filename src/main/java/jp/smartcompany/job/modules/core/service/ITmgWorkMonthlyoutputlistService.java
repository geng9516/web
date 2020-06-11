package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgWorkMonthlyoutputlistDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * [勤怠]月次集計データ作成・対象者一覧ワークテーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgWorkMonthlyoutputlistService extends IService<TmgWorkMonthlyoutputlistDO> {

        /**
         * 処理対象年度の対象者一覧ワークテーブルデータの件数を取得するＳＱＬ文を生成する。
         */
        int selectWorkMonthlyOutputList(String custId,String compId,String targetYear,String introductionData);
        }
