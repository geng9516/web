package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import jp.smartcompany.job.modules.core.mapper.TmgTimepunchMapper;
import jp.smartcompany.job.modules.core.service.ITmgTimepunchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * [勤怠]打刻
 * @author X02461
 */
@Repository
public class TmgTimepunchServiceImpl extends ServiceImpl<TmgTimepunchMapper, TmgTimepunchDO> implements ITmgTimepunchService {

    @Override
    public void insertTmgTimePunch(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid, String ctpTypeid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("modifierprogramid", modifierprogramid);
        params.put("minDate", minDate);
        params.put("maxDate", maxDate);
        params.put("ctpTypeid", ctpTypeid);
        baseMapper.insertTmgTimePunch(params);
    }

    @Override
    public void insertTmgTrgger(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("modifierprogramid", modifierprogramid);
        params.put("minDate", minDate);
        params.put("maxDate", maxDate);
        baseMapper.insertTmgTrgger(params);
    }

    @Override
    public void deleteTmgTrgger(String custId, String compCode, String employeeId, String modifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("modifierprogramid", modifierprogramid);
        baseMapper.deleteTmgTrgger(params);
    }

    @Override
    public String selectIsTimePunchTarget(String custId, String compCode, String employeeId, String targetDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("targetDate", targetDate);
        return baseMapper.selectIsTimePunchTarget(params);
    }

    @Override
    public BaseTimesDTO selectBaseTimes(String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectBaseTimes(params);
    }

    @Override
    public String selectBaseTimesWithPattern(String custId, String compCode, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        return baseMapper.selectBaseTimesWithPattern(params);
    }
}
