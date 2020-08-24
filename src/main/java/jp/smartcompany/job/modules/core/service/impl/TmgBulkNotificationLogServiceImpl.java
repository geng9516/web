package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationLogDO;
import jp.smartcompany.job.modules.core.mapper.TmgBulkNotificationLogMapper;
import jp.smartcompany.job.modules.core.service.ITmgBulkNotificationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.ErrorDetailVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 休暇休業一括登録 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgBulkNotificationLogServiceImpl extends ServiceImpl<TmgBulkNotificationLogMapper, TmgBulkNotificationLogDO> implements ITmgBulkNotificationLogService {

        @Override
        public List<ErrorDetailVo> selectErrorList(String seq){
               return baseMapper.selectErrorList(seq);
        }
        }
