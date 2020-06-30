package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.framework.sysboot.dto.AppSearchRangeInfoDTO;
import jp.smartcompany.framework.sysboot.dto.TableCombinationTypeDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

        List<AppSearchRangeInfoDTO> selectSearchRangeInfo();

        List<MastSystemDO> selectSystemList(String language);

}
