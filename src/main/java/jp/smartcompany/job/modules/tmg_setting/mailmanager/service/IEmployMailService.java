package jp.smartcompany.job.modules.tmg_setting.mailmanager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.entity.EmployMailDO;

public interface IEmployMailService extends IService<EmployMailDO> {

    IPage<MastEmployeesDO> selectInvalidEmailEmpList(IPage<MastEmployeesDO> page);

    IPage<UserManagerListDTO> searchEmpForUpdateMail(IPage<UserManagerListDTO> page, String keyword);

}
