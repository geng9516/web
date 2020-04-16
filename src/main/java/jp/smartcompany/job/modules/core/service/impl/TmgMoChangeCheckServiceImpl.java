package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMoChangeCheckDO;
import jp.smartcompany.job.modules.core.mapper.TmgMoChangeCheckMapper;
import jp.smartcompany.job.modules.core.service.ITmgMoChangeCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]月次集計データ差異チェックテーブル 月次集計実行より後に勤務実績が修正されている職員・年月を登録 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgMoChangeCheckServiceImpl extends ServiceImpl<TmgMoChangeCheckMapper, TmgMoChangeCheckDO> implements ITmgMoChangeCheckService {

        }
