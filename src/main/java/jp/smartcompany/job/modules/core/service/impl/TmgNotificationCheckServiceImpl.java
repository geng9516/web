package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNotificationCheckDO;
import jp.smartcompany.job.modules.core.mapper.TmgNotificationCheckMapper;
import jp.smartcompany.job.modules.core.service.ITmgNotificationCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.notificationListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [勤怠]エラーチェック用・申請情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgNotificationCheckServiceImpl extends ServiceImpl<TmgNotificationCheckMapper, TmgNotificationCheckDO> implements ITmgNotificationCheckService {


        /**TMG_F_CHECK_NOTIFICATION*/
        @Override
        public String  tmgFCheckNotification(String ntfNo,String custId,String compId,String siteId){
                return baseMapper.tmgFCheckNotification(ntfNo,custId,compId,siteId);
        }
        }
