package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.PatternSettingMapper;
import jp.smartcompany.job.modules.core.service.IPatternSettingService;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description
 * @objectSource
 * @date 2020/06/12
 **/
@Repository
public class PatternSettingServiceImpl extends ServiceImpl<PatternSettingMapper, Object> implements IPatternSettingService {

    @Override
    public List<TmgPatternDTO> selectTmgPattern(String custId, String compCode, String groupId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectTmgPattern(params);
    }

    @Override
    public List<TmgPatternDTO> selectTmgPatternOwn(String custId, String compCode, String groupId, String sectionId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("sectionId", sectionId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectTmgPatternOwn(params);
    }

    @Override
    public List<TmgPatternAppliesDTO> selectTmgPatternApplies(String baseDate, String targetEmployees, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("baseDate", baseDate);
        params.put("targetEmployees", targetEmployees);
        params.put("language", language);
        return baseMapper.selectTmgPatternApplies(params);
    }

    @Override
    public List<RestTimeLimitDTO> selectRestTimeLimit(String custId, String compCode, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("language", language);
        return baseMapper.selectRestTimeLimit(params);
    }

    @Override
    public TmgPatternDTO selectTmgPatternById(String custId, String compCode, String groupId, String patternId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("patternId", patternId);
        return baseMapper.selectTmgPatternById(params);
    }

    @Override
    public List<TmgPatternDTO> selectPatternSelectList(String custId, String compCode, String groupId, String patternId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("patternId", patternId);
        return baseMapper.selectPatternSelectList(params);
    }

    @Override
    public void deleteTmgPattern(String custId, String compCode, String groupId, String sectionId, String patternId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("sectionId", sectionId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("patternId", patternId);
        baseMapper.deleteTmgPattern(params);
    }

    @Override
    public void deleteTmgPatternRest(String custId, String compCode, String groupId, String sectionId, String patternId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("sectionId", sectionId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("patternId", patternId);
        baseMapper.deleteTmgPatternRest(params);
    }

    @Override
    public void deleteTmgPatternApplies(String custId, String compCode, String groupId, String sectionId, String patternId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("sectionId", sectionId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("patternId", patternId);
        baseMapper.deleteTmgPatternApplies(params);
    }

    @Override
    public void updateTmgPattern(String custId, String compCode, String groupId, String sectionId, String employeeId, String modifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", groupId);
        params.put("sectionId", sectionId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("modifierprogramid", modifierprogramid);
        baseMapper.updateTmgPattern(params);
    }

    @Override
    public void insertTmgPattern(TmgPatternInsertDTO tmgPatternInsertDTO) {
        baseMapper.insertTmgPattern(tmgPatternInsertDTO);
    }

    @Override
    public void insertTmgPatternRestPlural(TmgPatternRestDTO tmgPatternRestDTO) {
        baseMapper.insertTmgPatternRestPlural(tmgPatternRestDTO);
    }

    @Override
    public String selectEditPeriodDate() {
        return baseMapper.selectEditPeriodDate();
    }

}
