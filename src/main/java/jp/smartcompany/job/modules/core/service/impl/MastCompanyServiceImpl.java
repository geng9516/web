package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.mapper.MastCompanyMapper;
import jp.smartcompany.job.modules.core.service.IMastCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 法人ツリーマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastCompanyServiceImpl extends ServiceImpl<MastCompanyMapper, MastCompanyDO> implements IMastCompanyService {

        @Override
        public List<MastCompanyDO> getCompanyInfo(String sCustid, String sLanguage, String sDate) {
                return baseMapper.selectCompanyInfo(sCustid,sLanguage,sDate);
        }
}
