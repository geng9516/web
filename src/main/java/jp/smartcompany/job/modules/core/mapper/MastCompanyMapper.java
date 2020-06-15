package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 法人ツリーマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastCompanyMapper extends BaseMapper<MastCompanyDO> {

       List<MastCompanyDO> selectCompanyInfo(@Param("custId") String sCustid,@Param("language") String sLanguage,@Param("date") String sDate);
}
