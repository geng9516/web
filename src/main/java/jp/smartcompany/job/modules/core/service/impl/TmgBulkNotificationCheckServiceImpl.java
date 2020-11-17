package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationCheckDO;
import jp.smartcompany.job.modules.core.mapper.TmgBulkNotificationCheck.TmgBulkNotificationCheckMapper;
import jp.smartcompany.job.modules.core.service.ITmgBulkNotificationCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 休暇休業一括登録 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgBulkNotificationCheckServiceImpl extends ServiceImpl<TmgBulkNotificationCheckMapper, TmgBulkNotificationCheckDO> implements ITmgBulkNotificationCheckService {
        @Override
        public String checkBulkNtf(String seq, String custID, String compCode){
                return baseMapper.checkBulkNtf( seq,  custID,  compCode);
        }
        }
