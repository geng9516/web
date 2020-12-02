package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgHomeWork.TmgHomeWorkMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgHomeWorkDataDO;
import jp.smartcompany.job.modules.core.service.ITmgHomeWorkService;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminListVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkMonthVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


/**
 * @author 顧成斌
 * @description 在宅勤務登録
 * @date 2020/11/25
 **/
@Repository
public class TmgHomeWorkServiceImpl extends ServiceImpl<TmgHomeWorkMapper, TmgHomeWorkDataDO> implements ITmgHomeWorkService {


    @Override
    public List<HomeWorkVO> selectHomeWork(String employeeId, String baseDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        return baseMapper.selectHomeWork(params);
    }

    @Override
    public List<HomeWorkAdminVO> selectAdminHomeWork(String empSql, String baseDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", empSql);
        params.put("baseDate", baseDate);
        return baseMapper.selectAdminHomeWork(params);
    }

    @Override
    public List<HomeWorkAdminVO> selectAdminHomeWorkUpdateList(String empSql, String baseDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", empSql);
        params.put("baseDate", baseDate);
        return baseMapper.selectAdminHomeWorkUpdateList(params);
    }

    @Override
    public List<HomeWorkMonthVO> selectAdminHomeWorkMonthList(String empSql, String baseDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", empSql);
        params.put("baseDate", baseDate);
        return baseMapper.selectAdminHomeWorkMonthList(params);
    }
}
