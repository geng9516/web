package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationDO;
import jp.smartcompany.job.modules.core.mapper.TmgBulkNotificationMapper;
import jp.smartcompany.job.modules.core.service.ITmgBulkNotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.HistoryDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.DetailDataVo;
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
        public class TmgBulkNotificationServiceImpl extends ServiceImpl<TmgBulkNotificationMapper, TmgBulkNotificationDO> implements ITmgBulkNotificationService {

        @Override
        public List<HistoryDto> selectHistoryList(String custID, String compCode, String language){
                return baseMapper.selectHistoryList(custID,compCode,language);
        }

        @Override
        public  String selectNextSeq(){
                return baseMapper.selectNextSeq();
        }

        @Override
        public DetailDataVo selectDetail(String seq, String language){
                return baseMapper.selectDetail( seq, language);
        }
        }
