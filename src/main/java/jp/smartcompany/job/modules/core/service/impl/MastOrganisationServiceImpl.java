package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionOrganisationBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.mapper.MastOrganisationMapper;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

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
}
