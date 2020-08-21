package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerModifiedDateDTO;
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

        @Override
        public List<GroupManagerGroupListDTO> selectValidGroup(
                String customerCode,
                String systemId,
                String language,
                String searchDate,
                List<String> companyList
        ) {
            if (StrUtil.isBlank(customerCode)){
                customerCode = "01";
            }
            if (StrUtil.isBlank(systemId)){
                systemId = "01";
            }
            if (StrUtil.isBlank(language)){
                language = "ja";
            }
            if (CollUtil.isEmpty(companyList)){
                companyList = CollUtil.newArrayList("01","02");
            }
            if (StrUtil.isBlank(searchDate)){
                searchDate = "2007/07/07";
            }
            //
            companyList.add("*");
           return baseMapper.selectValidGroup(customerCode,systemId,language,searchDate,companyList);
        }

        @Override
        public List<GroupManagerGroupListDTO> selectInvalidGroupList(
                String customerCode,
                String systemId,
                String language,
                List<String> validGroup,
                List<String> validCompany
        ) {
            if (StrUtil.isBlank(customerCode)){
                customerCode = "01";
            }
            if (StrUtil.isBlank(systemId)){
                systemId = "01";
            }
            if (StrUtil.isBlank(language)){
                language = "ja";
            }
            if (CollUtil.isEmpty(validCompany)){
                validCompany = CollUtil.newArrayList("01","02");
            }
            if (CollUtil.isEmpty(validGroup)){
                validGroup = CollUtil.newArrayList("1","2","3","4","5","6","7");
            }
           return baseMapper.selectInvalidGroup(customerCode,systemId,language,validCompany,validGroup);
        }

        @Override
        public List<GroupManagerModifiedDateDTO> selectHistoryDate(
            String customerCode,
            String systemId,
            List<String> companyList,
            String searchDate
        ) {
            if (StrUtil.isBlank(customerCode)){
                customerCode = "01";
            }
            if (StrUtil.isBlank(systemId)){
                systemId = "01";
            }
            if (CollUtil.isEmpty(companyList)){
                companyList = CollUtil.newArrayList("01","02");
            }
            if (StrUtil.isBlank(searchDate)){
                searchDate = "2007/07/07";
            }
            return baseMapper.selectHistoryDate(customerCode,systemId,companyList,searchDate);
        }

        @Override
        public List<GroupManagerGroupListDTO> selectGroupHistoryList(
                String customerCode, String systemId, String language, String groupId, Date searchDate, List<String> companyList
        ) {
            String strSearchDate;
            if (searchDate==null){
                strSearchDate = "2007/07/07";
            } else {
                strSearchDate = SysUtil.transDateToString(searchDate);
            }

            if (StrUtil.isBlank(customerCode)){
                customerCode = "01";
            }
            if (StrUtil.isBlank(systemId)){
                systemId = "02";
            }
            if (StrUtil.isBlank(language)){
                language = "ja";
            }
            if (CollUtil.isEmpty(companyList)){
                companyList = CollUtil.newArrayList("01","02");
            }
            if (StrUtil.isBlank(groupId)){
                groupId = "1";
            }
           return baseMapper.selectGroupHistoryList(customerCode,systemId,language,groupId,strSearchDate,companyList);
        }

        @Override
        public int selectGroupExists(String customerId,String systemId,String groupId) {
            QueryWrapper<MastGroupDO> qw = SysUtil.query();
            qw.eq("MG_CCUSTOMERID",customerId).eq("MG_CSYSTEMID_CK_FK",systemId)
                    .eq("MG_CGROUPID_PK",groupId);
            return count(qw);
        }

        /**
         * グループ全体の優先順位を更新
         * (グループが削除された場合のみ)
         */
        @Override
        public int updateGroupPrionityLevel (String searchDate,
                                      String custId,
                                      String systemId) {
            return baseMapper.updateGroupPrionityLevel(searchDate,custId,systemId);
        }
}
