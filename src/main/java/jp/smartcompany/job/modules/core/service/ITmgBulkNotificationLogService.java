package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationLogDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.ErrorDetailVo;

import java.util.List;

/**
 * <p>
 * 休暇休業一括登録 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgBulkNotificationLogService extends IService<TmgBulkNotificationLogDO> {

    List<ErrorDetailVo> selectErrorList(String seq);
}
