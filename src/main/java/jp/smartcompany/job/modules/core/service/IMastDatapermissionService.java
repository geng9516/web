package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.MastDatapermissionDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.util.searchrange.SearchRangeInfo;
import java.util.List;

/**
 * <p>
 * 検索対象範囲条件定義マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastDatapermissionService extends IService<MastDatapermissionDO> {

        List<SearchRangeInfo> selectDataPermissionDefs();

        List<SearchRangeInfo> selectDataSectionPost();

}
