package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.component.dto.SectionPostRowDTO;
import jp.smartcompany.admin.component.dto.SectionPostRowListDTO;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupsectionpostmappingDO;
import jp.smartcompany.job.modules.core.mapper.MastGroupsectionpostmappingMapper;
import jp.smartcompany.job.modules.core.service.IMastGroupsectionpostmappingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ定義条件マスタ（組織、役職） 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastGroupsectionpostmappingServiceImpl extends ServiceImpl<MastGroupsectionpostmappingMapper, MastGroupsectionpostmappingDO> implements IMastGroupsectionpostmappingService {

    @Override
    public List<SectionPostRowDTO> selectGroupSectionPost(
            String customerId,
            String companyId,
            String systemId,
            String groupId,
            Date searchDate,
            String permissionId,
            String language) {
        String strSearchDate;
        if (searchDate==null) {
            strSearchDate="2007/07/07";
        }else {
            strSearchDate = SysUtil.transDateToString(searchDate);
        }
        if (StrUtil.isBlank(customerId)){
            customerId = "01";
        }
        if (StrUtil.isBlank(companyId)){
            companyId="01";
        }
        if(StrUtil.isBlank(systemId)){
            systemId = "999";
        }
        if (StrUtil.isBlank(language)){
            language = "ja";
        }
        if (StrUtil.isBlank(groupId)){
            groupId = "1";
        }
        if (StrUtil.isBlank(permissionId)){
            permissionId = "001";
        }
       return baseMapper.selectGroupSectionPost(customerId,companyId,systemId,groupId,strSearchDate,permissionId,language);
    }

    @Override
    public List<SectionPostRowDTO> selectWholeSectionInfo(
            String customerId,
            String companyId,
            String systemId,
            String groupId,
            Date searchDate,
            String permissionId,
            String sectionId,
            String language) {
        String strSearchDate;
        if (searchDate==null) {
            strSearchDate="2007/07/07";
        }else {
            strSearchDate = SysUtil.transDateToString(searchDate);
        }
        if (StrUtil.isBlank(customerId)){
            customerId = "01";
        }
        if (StrUtil.isBlank(companyId)){
            companyId="01";
        }
        if(StrUtil.isBlank(systemId)){
            systemId = "999";
        }
        if (StrUtil.isBlank(language)){
            language = "ja";
        }
        if (StrUtil.isBlank(groupId)){
            groupId = "1";
        }
        if (StrUtil.isBlank(permissionId)){
            permissionId = "001";
        }
        if (StrUtil.isBlank(sectionId)){
            permissionId = "110";
        }
        return baseMapper.selectWholeSectionInfo(customerId,companyId,systemId,groupId,strSearchDate,permissionId,sectionId,language);
    }

    @Override
    public List<SectionPostRowListDTO> selectGroupSection(
            String customerId,
            String companyId,
            String systemId,
            String groupId,
            Date searchDate,
            String permissionId,
            String language) {
        String strSearchDate;
        if (searchDate==null) {
            strSearchDate="2007/07/07";
        }else {
            strSearchDate = SysUtil.transDateToString(searchDate);
        }
        if (StrUtil.isBlank(customerId)){
            customerId = "01";
        }
        if (StrUtil.isBlank(companyId)){
            companyId="01";
        }
        if(StrUtil.isBlank(systemId)){
            systemId = "999";
        }
        if (StrUtil.isBlank(groupId)){
            groupId = "1";
        }
        if (StrUtil.isBlank(permissionId)){
            permissionId = "001";
        }
        if (StrUtil.isBlank(language)){
            language = "ja";
        }
        return baseMapper.selectGroupSection(customerId,companyId,systemId,groupId,strSearchDate,permissionId,language);
    }

}
