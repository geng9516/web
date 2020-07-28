package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.sysboot.dto.MastDatadicSeclevelDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * データディクショナリマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastDatadictionaryMapper extends BaseMapper<MastDatadictionaryDO> {

        List<MastDatadictionaryDO> selectAllDicts();

        List<MastDatadicSeclevelDTO> selectAllDataDicSecLevel();

        List<QueryConditionRowDTO> selectGroupJoinQuery(@Param("customerId") String customerId,@Param("tableId") String tableId);

}
