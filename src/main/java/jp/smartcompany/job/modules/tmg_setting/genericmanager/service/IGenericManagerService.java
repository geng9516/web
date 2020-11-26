package jp.smartcompany.job.modules.tmg_setting.genericmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.dto.UpdateDetailDTO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.vo.CategoryGenericDetailVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IGenericManagerService extends IService<MastGenericDO> {

    List<CategoryGenericDetailVO> listCategoryGenericDetail(String categoryId);

    Map<String,Object> getGenericDetailList(Map<String,Object> conditions);

    String deleteSelectedDetails(List<Long> ids);

    Map<String,Object> getGenericDetail(String categoryId, String groupId, Date searchDate, String detailId);

    void execute(UpdateDetailDTO info);

}
