package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.MastRelationshipDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * リレーションシップマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastRelationshipService extends IService<MastRelationshipDO> {

    List<MastRelationshipDO> selectEvaluationLevel(String psSystemId, int pnEvaluation, Date pdSearchDate);

}
