package jp.smartcompany.job.modules.tmg_setting.genericmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.vo.CategoryGenericDetailVO;

import java.util.Date;
import java.util.List;

public interface IGenericManagerService extends IService<MastGenericDO> {

    List<CategoryGenericDetailVO> listCategoryGenericDetail(Date searchDate,String categoryId);

}
