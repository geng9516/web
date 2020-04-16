package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.WorkTmgMonthlyOutputLogDO;
import jp.smartcompany.job.modules.core.mapper.WorkTmgMonthlyOutputLogMapper;
import jp.smartcompany.job.modules.core.service.IWorkTmgMonthlyOutputLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]月次集計処理ログ(ユーザー自動作成スクリプト用、一時退避テーブル) 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class WorkTmgMonthlyOutputLogServiceImpl extends ServiceImpl<WorkTmgMonthlyOutputLogMapper, WorkTmgMonthlyOutputLogDO> implements IWorkTmgMonthlyOutputLogService {

        }
