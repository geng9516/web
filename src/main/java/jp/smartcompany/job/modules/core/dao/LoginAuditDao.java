package jp.smartcompany.job.modules.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Xiao Wenpeng
 */
@Mapper
public interface LoginAuditDao extends BaseMapper<LoginAuditDO> {
}
