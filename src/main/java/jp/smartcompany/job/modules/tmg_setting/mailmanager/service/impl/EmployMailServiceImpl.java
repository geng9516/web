package jp.smartcompany.job.modules.tmg_setting.mailmanager.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.mapper.EmployMailMapper;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.entity.EmployMailDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.service.IEmployMailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<EmployMailDO> getEmpMailInfo(String empId) {
        QueryWrapper<EmployMailDO> qw = SysUtil.query();
        List<EmployMailDO> employList =  list(qw.eq("TMA_EMP_ID",empId).select("TMA_EMP_NAME","TMA_EMAIL","TMA_EMP_NAME"));
        if (CollUtil.isEmpty(employList)) {
            return Optional.empty();
        }
        return Optional.of(employList.get(0));
    }

}
