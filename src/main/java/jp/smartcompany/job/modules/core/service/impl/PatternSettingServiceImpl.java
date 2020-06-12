package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.PatternSettingMapper;
import jp.smartcompany.job.modules.core.service.IPatternSettingService;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.TmgPatternAppliesDTO;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.TmgPatternDTO;
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
    public TmgPatternDTO selectTmgPatternOwn(String custId, String compCode, String groupId, String sectionId) {
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
}
