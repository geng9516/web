package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNotificationDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.HistoryDto;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.DetailDataVo;

import java.util.List;

/**
 * <p>
 * 休暇休業一括登録 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgBulkNotificationService extends IService<TmgBulkNotificationDO> {

    List<HistoryDto> selectHistoryList(String custID, String compCode, String language);


    String selectNextSeq();

    DetailDataVo selectDetail(String seq, String language);
}
