package jp.smartcompany.job.modules.core.mapper.TmgHomeWork;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    /**
     * [勤怠]日別情報より予定データを取得します
     *
     * @param params
     * @return
     */
    List<HomeWorkVO> selectHomeWork(HashMap<String, Object> params);

}
