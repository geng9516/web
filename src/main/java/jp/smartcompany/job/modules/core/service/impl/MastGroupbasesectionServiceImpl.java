package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.component.dto.BaseSectionRowDTO;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.bo.GroupBaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupbasesectionDO;
import jp.smartcompany.job.modules.core.mapper.MastGroupbasesectionMapper;
import jp.smartcompany.job.modules.core.service.IMastGroupbasesectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ別基点組織マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastGroupbasesectionServiceImpl extends ServiceImpl<MastGroupbasesectionMapper, MastGroupbasesectionDO> implements IMastGroupbasesectionService {

  @Override
  public List<GroupBaseSectionBO> getBaseSectionByGroupCode(String customerId, String systemCode, String groupCode, Date date) {
       return baseMapper.getBaseSectionByGroupCode(customerId, systemCode, groupCode, SysUtil.transDateToString(date));
  }

  @Override
  public List<BaseSectionRowDTO> selectGroupBaseSectionCompanyList(String psCustomerId,
                                                                     String psSystemId, String psGroupId, String psLanguage, Date pdSearchDate,
                                                                     List<String> plValidCompany) {
      if (StrUtil.isBlank(psLanguage)){
          psLanguage = "ja";
      }
      String strSearchDate;
      if (pdSearchDate==null) {
          strSearchDate = "2020/07/07";
      }else {
          strSearchDate = SysUtil.transDateToString(pdSearchDate);
      }
      if (CollUtil.isEmpty(plValidCompany)) {
          plValidCompany = CollUtil.newArrayList("01","02");
      }
      if (StrUtil.isBlank(psGroupId)){
          psGroupId = "1";
      }
      return baseMapper.selectGroupBaseSectionCompanyList(psCustomerId, psSystemId,
              psGroupId, psLanguage, strSearchDate, plValidCompany);
  }

}
