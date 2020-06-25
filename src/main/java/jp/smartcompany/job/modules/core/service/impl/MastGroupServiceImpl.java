package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.bo.DBMastGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupDO;
import jp.smartcompany.job.modules.core.mapper.MastGroupMapper;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ定義マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastGroupServiceImpl extends ServiceImpl<MastGroupMapper, MastGroupDO> implements IMastGroupService {

        @Override
        public List<DBMastGroupBO> getUserGroupByLanguage(String language, String systemCode) {
           return baseMapper.getUserGroupByLanguage(language,systemCode);
        }

        @Override
        public List<DBMastGroupBO> getPretreatGroupByLanguageUserId(String language, String userId) {
            return baseMapper.getPretreatGroupByLanguageUserId(language,userId);
        }

        @Override
        public List<GroupAppManagerGroupDTO> selectAppManagerGroup(String customerId, String systemId, String language,
                                                                   Date searchDate, String companyId,List<String> companyIds) {
            if (StrUtil.isBlank(language)){
                language = "ja";
            }
            if (StrUtil.isBlank(customerId)){
                customerId="01";
            }
            if (StrUtil.isBlank(systemId)){
                systemId = "01";
            }
            String strSearchDate = SysUtil.transDateToString(searchDate);
            if (searchDate == null){
                strSearchDate = "2007/08/13";
            }
            return baseMapper.selectAppManagerGroup(customerId, systemId, language, strSearchDate, companyId,companyIds);
        }

}
