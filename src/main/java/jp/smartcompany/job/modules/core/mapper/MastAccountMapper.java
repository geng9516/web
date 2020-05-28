package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * アカウントマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastAccountMapper extends BaseMapper<MastAccountDO> {

        List<LoginAccountBO> selectAccountInfo(String username);

}
