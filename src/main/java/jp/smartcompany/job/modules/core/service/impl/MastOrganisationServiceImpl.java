package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.framework.jsf.orgtree.dto.OrgTreeDTO;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionOrganisationBO;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.mapper.MastOrganisation.MastOrganisationMapper;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetFiscalYearDto;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotApprovalVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotFixedDeptListVo;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.dto.SectionRankDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.LimitOfBasedateVO;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MastOrganisationServiceImpl extends ServiceImpl<MastOrganisationMapper, MastOrganisationDO> implements IMastOrganisationService {

    private final IHistDesignationService iHistDesignationService;


    @Override
    public List<Map<String,String>> getSearchRangeSection(String custId,String compId, String sql){
        return baseMapper.getSearchRangeSection( custId,  compId,  sql);
    }

    @Override
    public MastOrganisationDO selectOrganisation(String customerId, String companyId, String sectionId, Date yyyymmdd) {
        QueryWrapper<MastOrganisationDO> qw = SysUtil.query();
        qw.eq("mo_ccustomerid_ck_fk", customerId)
                .eq("mo_ccompanyid_ck_fk", companyId)
                .eq("mo_csectionid_ck", sectionId)
                .eq("mo_clanguage", "ja")
                .le("mo_dstart", yyyymmdd)
                .ge("mo_dend", yyyymmdd)
                .orderByAsc("mo_nseq");
        MastOrganisationDO mastOrganisationDO = getOne(qw);
        if (mastOrganisationDO != null) {
            return mastOrganisationDO;
        }
        return null;
    }

    @Override
    public List<BaseSectionOrganisationBO> selectOrganisationByLevel(String customerId, String conds, Date date) {
        QueryWrapper<MastOrganisationDO> qw = SysUtil.query();
        qw.eq("mo_ccustomerid_ck_fk", customerId)
                .le("mo_dstart", SysUtil.transDateToString(date)).ge("mo_dend", SysUtil.transDateToString(date))
                .eq("mo_clanguage", "ja")
                .orderByDesc("mo_nseq");
        List<MastOrganisationDO> organisationDOList = list(qw);
        List<BaseSectionOrganisationBO> organisationBOList = organisationDOList.stream().map(item -> {
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
                .le("mo_dstart", SysUtil.transDateToString(pdSearchDate))
                .ge("mo_dend", SysUtil.transDateToString(pdSearchDate))
                .select("MO_CLAYEREDSECTIONID");
        List<MastOrganisationDO> mastOrganisationList = list(qw);
        if (CollUtil.isNotEmpty(mastOrganisationList)) {
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
    public List<String> selectLowerSection(String psCustID, String psCompID, String psSection, Date date) {
        return baseMapper.selectLowerSection(psCustID, psCompID, psSection, date);
    }

    /**
     * 基準日時点の超勤限度時間取得用
     *
     * @param custID    顧客コード
     * @param compCode  法人コード
     * @param targetSec 部署コード
     * @param day       　基準日
     * @return LimitOfBasedateVO
     */
    @Override
    public LimitOfBasedateVO buildSQLForLimitOfBasedate(String custID, String compCode, String targetSec, String day) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetSec", targetSec);
        map.put("day", day);

        return baseMapper.buildSQLForLimitOfBasedate(map);
    }

    /**
     * 職員情報を取得する
     *
     * @param sectionId 部署コード
     * @param today     基準日
     * @param custId    顧客コード
     * @param compCode  法人コード
     * @return 所属マスタの値
     */
    @Override
    public String buildSQLForSelectEmployeeDetail(String sectionId, String today, String custId, String compCode) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("sectionId", sectionId);
        map.put("today", today);
        map.put("custId", custId);
        map.put("compCode", compCode);

        return baseMapper.buildSQLForSelectEmployeeDetail(map);
    }

    /**
     * <p>
     * <b>下位組織</b>情報取得（組織指定）
     * </p>
     * <div>指定した組織の下位組織リストを返却する。 </div>
     *
     * @author t-abe
     * @param customerId 顧客コード
     * @param compnyId 法人コード
     * @param sectionId 組織コード
     * @param searchDate 検索基準日
     * @return 下位組織情報
     * @exception
     */
    @Override
    public List < String > getSubSection(String customerId, String compnyId, String sectionId,
                                         Date searchDate) {

        List <String> highLowSectionSysInfoDtolist = selectLowerSection(customerId, compnyId, sectionId, searchDate);
        if (highLowSectionSysInfoDtolist == null || highLowSectionSysInfoDtolist.size() == 0) {
            return null;
        }
        return highLowSectionSysInfoDtolist;
    }

    /**
     * <p>
     * <b>下位組織</b>情報取得（職員指定）
     * </p>
     * <div>指定した職員の下位組織情報を返却する。</div>
     *
     * @author t-abe
     * @param userId ユーザID
     * @param searchDate 検索基準日
     * @param virtualSection 仮想組織判定<br>
     *            true : 組織リストに仮想組織を含ませる。<br>
     *            false : 仮想リストに仮想組織を含ませない。
     * @return 下位組織情報
     * @exception
     */
    @Override
    public Map<String,List<String>> getSubSectionEmp(String userId,Date searchDate, boolean virtualSection) {
        if (searchDate==null) {
            throw new GlobalException("searchDate");
        }
        List<HistDesignationDO> desinationDtoList = iHistDesignationService.selectCompanyId(userId,searchDate);
        if (CollUtil.isEmpty(desinationDtoList)) {
            return null;
        }
        Map<String, List<String>> subSectionMap =MapUtil.newHashMap();
        for (HistDesignationDO histDesignationDO : desinationDtoList) {
            String companyidCk = histDesignationDO.getHdCcompanyidCk();
            List<MastOrganisationDO> highLowSectionSysInfoDtolist = baseMapper.selectLowEmp(histDesignationDO.getHdCcustomeridCk(),
                            companyidCk,
                            histDesignationDO.getHdCsectionidFk(),
                            SysUtil.transDateToString(searchDate),
                            virtualSection);
            List<String> sectionidList = CollUtil.newArrayList();
            for (MastOrganisationDO o : highLowSectionSysInfoDtolist) {
                sectionidList.add(o.getMoCsectionidCk());
            }
            subSectionMap.put(companyidCk, sectionidList);
        }
        return subSectionMap;
    }



    /**
     * 選択された組織の表示権限があるかを判定するSQLを返すメソッド
     * */
    @Override
    public int selectHasAuth(String customerId, String compnyId, String sectionId,
                             String searchDate,String language,String exists){
        return baseMapper.selectHasAuth( customerId,  compnyId,  sectionId,
                 searchDate, language, exists);
    }



    /**
     * 対象日の年度開始日および年度終了日を取得するSQLを構築して返します
     */
    @Override
    public TargetFiscalYearDto selectTargetFiscalYear(String cust,
                                                      String comp,
                                                      String section,
                                                      String lang,
                                                      String targetDate,
                                                      String targetStartDate,
                                                      String targetEneDate,
                                                      String psBaseDate){

        return baseMapper.selectTargetFiscalYear(cust, comp, section, lang, targetDate, targetStartDate,
                 targetEneDate, psBaseDate);
    }


    /**
     * 対象組織と組織配下の部署について対象年月の未承認者情報を検索するSQL文を生成し返します。
     *
     * @param custId 対象顧客コード
     * @param compId 対象法人コード
     * @param secId 対象組織コード
     * @param dyyyymm 該当年月
     * @param lang 言語区分
     * @param numStart 検索番号スタート
     * @param numEnd 検索番号エンド
     * @return SQL文
     */
    @Override
    public List<NotApprovalVo> selectNotApproval(String custId,
                                          String compId,
                                          String secId,
                                          String dyyyymm,
                                          String lang,
                                          int numStart,
                                          int numEnd){

        return baseMapper.selectNotApproval(custId, compId, secId, dyyyymm, lang, numStart,numEnd);
    }


    /**
     * 対象年月の締め未完了部局の一覧を検索するSQL文を生成し返します。
     *
     * @param custId 対象顧客コード
     * @param compId 対象法人コード
     * @param secId 対象組織コード
     * @param dyyyymm 該当年月
     * @param lang 言語区分
     * @param numStart 検索番号スタート
     * @param numEnd 検索番号エンド
     * @return SQL文
     */
    @Override
    public List<NotFixedDeptListVo> selectNotFixedDeptList(String custId,
                                                           String compId,
                                                           String secId,
                                                           String dyyyymm,
                                                           String lang,
                                                           int numStart,
                                                           int numEnd){
        return baseMapper.selectNotFixedDeptList(custId, compId, secId, dyyyymm, lang, numStart,numEnd);
    }

    @Override
    public List<OrgTreeDTO> selectOrgList(
            String customerId,
            String language,
            String companyId,
            String searchDate,
            String startDate,
            String endDate,
            String companyCode,
            String sectionCode,
            String sExists
    ) {
        return baseMapper.selectOrgList(customerId,
                language,
               companyId,
                searchDate,
               startDate,
                 endDate,
              companyCode,
                sectionCode,
               sExists);
    }

    @Override
    public List<OrgTreeDTO> getSelCompOrgTreeList(
            String psCustomerId,
            String psLanguage,
            String psCompanyId,
            String startDate,
            String endDate,
            String exists) {
        return baseMapper.selectSelCompOrgTreeList(psCustomerId,psLanguage,psCompanyId,startDate,endDate,exists);
    }

    @Override
    public List<SectionRankDto> selectSectionRankDto(String custID, String compCode, String language,String sectionId){
        return baseMapper.selectSectionRankDto( custID,  compCode,  language, sectionId);
    }
}
