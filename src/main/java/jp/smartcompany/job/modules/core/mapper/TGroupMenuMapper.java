package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TGroupMenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TMenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-24
 */

@Mapper
public interface TGroupMenuMapper extends BaseMapper<TGroupMenuDO> {

   List<TMenuDO> listTopMenuByGroupCode(@Param("groupCodes") List<String> groupCodes, @Param("systemCode") String systemCode, @Param("customerId") String customerId);

}
