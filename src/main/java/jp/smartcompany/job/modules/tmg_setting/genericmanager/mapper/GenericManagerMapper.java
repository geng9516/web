package jp.smartcompany.job.modules.tmg_setting.genericmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.vo.CategoryGenericDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenericManagerMapper extends BaseMapper<MastGenericDO> {

    List<CategoryGenericDetailVO> listCategoryGenericDetail(@Param("searchDate") String searchDate,
                                                            @Param("categoryId") String categoryId);

}
