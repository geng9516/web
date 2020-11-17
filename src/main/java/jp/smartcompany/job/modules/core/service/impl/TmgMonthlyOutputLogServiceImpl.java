package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyOutputLogDO;
import jp.smartcompany.job.modules.core.mapper.TmgMonthlyOutputLog.TmgMonthlyOutputLogMapper;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyOutputLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]月次集計処理ログ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgMonthlyOutputLogServiceImpl extends ServiceImpl<TmgMonthlyOutputLogMapper, TmgMonthlyOutputLogDO> implements ITmgMonthlyOutputLogService {

        /**
         * 月次集計データ作成処理のジョブ番号を検索するSQL文を生成し返します。
         */
        @Override
        public String selectSeq(){
                return baseMapper.selectSeq();
        }
        }
