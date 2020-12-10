package jp.smartcompany.job.modules.tmg_setting.mailmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.entity.EmployMailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployMailMapper extends BaseMapper<EmployMailDO> {


    IPage<MastEmployeesDO> selectInvalidEmailEmpList(IPage<MastEmployeesDO> page);

    IPage<UserManagerListDTO> searchEmpForUpdateMail(IPage<UserManagerListDTO> page, @Param("keyword") String keyword);

}
