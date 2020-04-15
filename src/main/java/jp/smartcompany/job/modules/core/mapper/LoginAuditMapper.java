package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Xiao Wenpeng
 */
@Mapper
public interface LoginAuditMapper extends BaseMapper<LoginAuditDO> {
}
