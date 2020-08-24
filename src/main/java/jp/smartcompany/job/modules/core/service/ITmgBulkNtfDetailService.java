package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNtfDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.SectionDetailVo;

import java.util.List;

/**
 * <p>
 * [勤怠]休暇休業一括登録・対象組織情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgBulkNtfDetailService extends IService<TmgBulkNtfDetailDO> {

    List<SectionDetailVo> selectSectionList(String seq,String custID,String compID,String lang);
}
