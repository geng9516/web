package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.HistSuspensionDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.HatuReiDto;

/**
 * <p>
 * 休職歴 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IHistSuspensionService extends IService<HistSuspensionDO> {

    HatuReiDto getHatuRei(String custID, String compCode, String targetUser, String baseDate);
}
