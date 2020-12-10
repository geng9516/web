package jp.smartcompany.job.modules.tmg_setting.mailmanager.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.mapper.EmployMailMapper;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.entity.EmployMailDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.service.IEmployMailService;
import org.springframework.stereotype.Service;

@Service
public class EmployMailServiceImpl extends ServiceImpl<EmployMailMapper, EmployMailDO> implements IEmployMailService {

    @Override
    public IPage<MastEmployeesDO> selectInvalidEmailEmpList(IPage<MastEmployeesDO> page) {
        return baseMapper.selectInvalidEmailEmpList(page);
    }

    @Override
    public IPage<UserManagerListDTO> searchEmpForUpdateMail(IPage<UserManagerListDTO> page, String keyword) {
        return baseMapper.searchEmpForUpdateMail(page,keyword);
    }

}
