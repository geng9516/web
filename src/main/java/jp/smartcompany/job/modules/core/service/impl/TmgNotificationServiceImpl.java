package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNotificationDO;
import jp.smartcompany.job.modules.core.mapper.TmgNotificationMapper;
import jp.smartcompany.job.modules.core.service.ITmgNotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]申請情報                      2007/01/31項目追加「申請日」「対象曜日：指定なし」 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgNotificationServiceImpl extends ServiceImpl<TmgNotificationMapper, TmgNotificationDO> implements ITmgNotificationService {

        }
