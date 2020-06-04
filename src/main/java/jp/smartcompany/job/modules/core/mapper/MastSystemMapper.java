package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.dto.TableCombinationTypeDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.util.searchrange.AppSearchRangeInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * <p>
 * システムマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastSystemMapper extends BaseMapper<MastSystemDO> {

        List<TableCombinationTypeDTO> selectTableInfo();

        List<AppSearchRangeInfoEntity> selectSearchRangeInfo();
}
