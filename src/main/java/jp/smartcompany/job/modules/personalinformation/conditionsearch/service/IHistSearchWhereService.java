package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchWhereDO;

import java.util.List;

public interface IHistSearchWhereService extends IService<HistSearchWhereDO> {
    List<HistSearchWhereDO> selectBySettingId(Long settingId);
}
