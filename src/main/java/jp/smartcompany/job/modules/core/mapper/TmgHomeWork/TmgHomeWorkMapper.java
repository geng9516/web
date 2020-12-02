package jp.smartcompany.job.modules.core.mapper.TmgHomeWork;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminListVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkMonthVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author 顧成斌
 * @description 在宅勤務登録
 * @objectSource null
 * @date 2020/11/20
 **/
@Mapper
public interface TmgHomeWorkMapper extends BaseMapper<HomeWorkVO> {

    List<HomeWorkVO> selectHomeWork(HashMap<String, Object> params);

    List<HomeWorkAdminVO> selectAdminHomeWork(HashMap<String, Object> params);

    List<HomeWorkAdminVO> selectAdminHomeWorkUpdateList(HashMap<String, Object> params);

    List<HomeWorkMonthVO> selectAdminHomeWorkMonthList(HashMap<String, Object> params);
}
