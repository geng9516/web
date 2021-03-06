package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNotificationCheckDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * [勤怠]エラーチェック用・申請情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgNotificationCheckService extends IService<TmgNotificationCheckDO> {

        /**TMG_F_CHECK_NOTIFICATION*/
        String  tmgFCheckNotification(String ntfNo,String custId,String compId,String siteId);
        }
