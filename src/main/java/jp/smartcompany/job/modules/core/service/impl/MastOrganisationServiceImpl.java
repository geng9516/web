package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionOrganisationBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.mapper.MastOrganisationMapper;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 組織ツリーマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastOrganisationServiceImpl extends ServiceImpl<MastOrganisationMapper, MastOrganisationDO> implements IMastOrganisationService {

        @Override
        public MastOrganisationDO selectOrganisation(String customerId, String companyId,String sectionId,Date yyyymmdd) {
                QueryWrapper<MastOrganisationDO> qw = SysUtil.query();
                qw.eq("mo_ccustomerid_ck_fk", customerId)
                        .eq("mo_ccompanyid_ck_fk", companyId)
                        .eq("mo_csectionid_ck", sectionId)
                        .eq("mo_clanguage", "ja")
                        .le("mo_dstart",yyyymmdd)
                        .ge("mo_dend",yyyymmdd)
                        .orderByAsc("mo_nseq");
                MastOrganisationDO mastOrganisationDO = getOne(qw);
                if (mastOrganisationDO!=null){
                        return mastOrganisationDO;
                }
                return null;
        }

        @Override
        public List<MastOrganisationDO> selectPatternOrganisation(String customerId, String companyId,Date yyyymmdd) {
                QueryWrapper<MastOrganisationDO> qw = SysUtil.query();
                qw.eq("mo_ccustomerid_ck_fk", customerId)
                        .eq("mo_ccompanyid_ck_fk", companyId)
                        .eq("mo_clanguage", "ja")
                        .isNull("mo_cparentid")
                        .le("mo_dstart",yyyymmdd)
                        .ge("mo_dend",yyyymmdd)

                        .orderByAsc( "mo_nseq");
                List<MastOrganisationDO> mastOrganisationDOList = list(qw);
                if (mastOrganisationDOList!=null){
                        return mastOrganisationDOList;
                }
                return null;
        }

        @Override
        public List<BaseSectionOrganisationBO> selectOrganisationByLevel(String customerId, String conds, Date date) {
                QueryWrapper<MastOrganisationDO> qw = SysUtil.query();
                qw.eq("mo_ccustomerid_ck_fk",customerId)
                  .lt("mo_dstart",date).gt("mo_dend",date)
                  .eq("mo_clanguage","ja")
                  .orderByDesc("mo_nseq");
                List<MastOrganisationDO> organisationDOList = list(qw);
                List<BaseSectionOrganisationBO> organisationBOList = organisationDOList.stream().map(item ->{
                   BaseSectionOrganisationBO organisationBO = new BaseSectionOrganisationBO()
                           .setMoClayeredsectionid(item.getMoClayeredsectionid())
                           .setMoCsectionidCk(item.getMoCsectionidCk())
                           .setMoNseq(item.getMoNseq());
                   return organisationBO;
                }).collect(Collectors.toList());
                return organisationBOList;
        }

        @Override
        public List<String> selectHighSection(String psCustID, String psCompCode, String psTargetDept, Date pdSearchDate) {
                QueryWrapper<MastOrganisationDO> qw = SysUtil.query();
                qw.eq("mo_ccustomerid_ck_fk", psCustID)
                        .eq("mo_ccompanyid_ck_fk", psCompCode)
                        .eq("mo_csectionid_ck", psTargetDept)
                        .eq("mo_clanguage", "ja")
                        .le("mo_dstart",pdSearchDate)
                        .ge("mo_dend",pdSearchDate)
                        .orderByAsc("mo_nseq");
                List<MastOrganisationDO> mastOrganisationList = list(qw);
                if (CollUtil.isNotEmpty(mastOrganisationList)){
                        List<String> layeredsectionid = mastOrganisationList.stream().map(MastOrganisationDO::getMoClayeredsectionid).collect(Collectors.toList());
                        return makeSectionList(layeredsectionid.get(0));
                }
                return null;
        }

        private List<String> makeSectionList(String layeredSection) {
                List<String> sectionList = CollUtil.newArrayList();

                int pipe = layeredSection.indexOf("|");
                String[] array;
                if (pipe != -1) {
                        layeredSection = layeredSection.substring(pipe + 1);
                        array = layeredSection.split(",");
                } else {
                        array = layeredSection.split(",");
                }
                sectionList.addAll(Arrays.asList(array));
                if (pipe == -1) {
                        sectionList.remove(array.length - 1);
                }
                return sectionList;
        }

        @Override
        public  List<String> selectLowerSection(String psCustID, String psCompID, String psSection, Date date) {
                return baseMapper.selectLowerSection(psCustID,psCompID,psSection,date);
        }
}
