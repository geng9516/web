package jp.smartcompany.job.modules.tmg_setting.genericmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.mapper.GenericManagerMapper;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.service.IGenericManagerService;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.vo.CategoryGenericDetailVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GenericManagerServiceImpl extends ServiceImpl<GenericManagerMapper, MastGenericDO>
implements IGenericManagerService {

    @Override
    public List<CategoryGenericDetailVO> listCategoryGenericDetail(Date searchDate,String categoryId) {
        String date = SysUtil.transDateToString(searchDate);
        return baseMapper.listCategoryGenericDetail(date,categoryId);
    }

}
