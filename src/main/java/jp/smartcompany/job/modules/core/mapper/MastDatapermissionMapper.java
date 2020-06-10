package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.MastDatapermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.framework.sysboot.dto.SearchRangeInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 検索対象範囲条件定義マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastDatapermissionMapper extends BaseMapper<MastDatapermissionDO> {

        List<SearchRangeInfoDTO> selectDataPermissionDefs();

        List<SearchRangeInfoDTO> selectDataSectionPost();

}
