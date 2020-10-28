package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgWorkMonthlyoutputlistDO;
import jp.smartcompany.job.modules.core.mapper.TmgWorkMonthlyoutputlist.TmgWorkMonthlyoutputlistMapper;
import jp.smartcompany.job.modules.core.service.ITmgWorkMonthlyoutputlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]月次集計データ作成・対象者一覧ワークテーブル 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgWorkMonthlyoutputlistServiceImpl extends ServiceImpl<TmgWorkMonthlyoutputlistMapper, TmgWorkMonthlyoutputlistDO> implements ITmgWorkMonthlyoutputlistService {


        /**
         * 処理対象年度の対象者一覧ワークテーブルデータの件数を取得するＳＱＬ文を生成する。
         */
        @Override
        public int selectWorkMonthlyOutputList(String custId,String compId,String targetYear,String introductionData){
                return baseMapper.selectWorkMonthlyOutputList( custId, compId, targetYear,introductionData);
        }
        }
