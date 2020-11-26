package jp.smartcompany.job.modules.tmg_setting.genericmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.dto.GenericHistoryDetailDTO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.vo.CategoryGenericDetailItemVO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.vo.CategoryGenericDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface GenericManagerMapper extends BaseMapper<MastGenericDO> {

    List<CategoryGenericDetailVO> listCategoryGenericDetail(String categoryId);

    IPage<CategoryGenericDetailItemVO> listGenericDetailByGroupId(IPage<CategoryGenericDetailItemVO> page, @Param("searchDate") String searchDate, @Param("groupId") String groupId);

    List<GenericHistoryDetailDTO> selectPastDetailList(@Param("groupId") String groupId, @Param("searchDate") String searchDate);

    List<GenericHistoryDetailDTO> selectFutureDetailList(@Param("groupId") String groupId, @Param("searchDate") String searchDate);

    Date selectRevisionDate(@Param("groupId") String groupId,@Param("searchDate") String searchDate);

    Date selectPrevRevisionDate(@Param("groupId") String groupId,@Param("searchDate") String searchDate);

    Date selectNextRevisionDate(@Param("groupId") String groupId,@Param("searchDate") String searchDate,@Param("maxDate") String maxDate);

}
