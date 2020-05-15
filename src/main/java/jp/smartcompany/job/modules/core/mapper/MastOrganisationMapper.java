package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 組織ツリーマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastOrganisationMapper extends BaseMapper<MastOrganisationDO> {

     List<String> selectLowerSection(@Param("custId") String custId,@Param("compId") String compId,
                                            @Param("sectionId") String psSection,@Param("date") Date date);

}
