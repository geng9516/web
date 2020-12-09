package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.admin.usermanager.dto.PersonalInfoDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.auth.entity.LoginControlEntity;
import jp.smartcompany.framework.compatible.entity.V3CompatiblePostEntity;
import jp.smartcompany.framework.component.dto.EmployInfoSearchDTO;
import jp.smartcompany.framework.component.entity.EmployeeInfoSearchEntity;
import jp.smartcompany.job.modules.core.mapper.MastEmployees.MastEmployeesMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmployMentWithMEVo;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 基本情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastEmployeesServiceImpl extends ServiceImpl<MastEmployeesMapper, MastEmployeesDO> implements IMastEmployeesService {

    /**
     * MAST_EMPLOYEESの採用日を取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 職員番号
     * @param yyyymmdd   基準日
     * @return Date 採用日
     */
    @Override
    public Date selectBegindateWork(String customerId, String companyId, String employeeId, Date yyyymmdd) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("employeeId", employeeId);
        map.put("yyyymmdd", yyyymmdd);

        return baseMapper.selectBegindateWork(map);
    }

    @Override
    public List<PaidHolidayInitVO> listPaidHolidayInit(String empSql) {
        return baseMapper.selectPaidHolidayInit(empSql);
    }

    @Override
    public  List<String> selectUserIdList(String psCustid,
                                          String psCompid,
                                          String psLoginUserId,
                                          Date psDate) {
        QueryWrapper<MastEmployeesDO> qw = SysUtil.query();
        qw.eq("ME_CCUSTOMERID_CK",psCustid)
          .eq("ME_CCOMPANYID",psCompid)
          .eq("ME_CEMPLOYEEID_CK",psLoginUserId)
                .le("ME_DSTARTDATE",psDate)
                .ge("ME_DENDDATE",psDate);
        return list(qw).stream().map(MastEmployeesDO::getMeCuserid).collect(Collectors.toList());
    }

    @Override
    public List<MastEmployeesDO> selectEmployByLoginUserId(String psCustid,
                                                           String psCompid,
                                                           String psLoginUserId,
                                                           String psDate) {
        QueryWrapper<MastEmployeesDO> qw = SysUtil.query();
        qw.eq("ME_CCUSTOMERID_CK",psCustid)
                .eq("ME_CCOMPANYID",psCompid)
                .eq("ME_CEMPLOYEEID_CK",psLoginUserId)
                .le("ME_DSTARTDATE",psDate)
                .ge("ME_DENDDATE",psDate);
        return list(qw);
    }

    /**
     * サイトIDを判定し更新対象の職員番号
     *
     * @param siteId   サイトID
     * @param yyyymmdd 　対象日
     * @param empsql   　対象職員取得SQL
     * @param empIds   　画面チェックした職員番号リスト
     * @return
     */
    @Override
    public List<String> selectEmpIdListForTmgDaily(String siteId, String yyyymmdd, String empsql, String[] empIds) {
        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("siteId", siteId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("empsql", empsql);
        map.put("empIds", empIds);
        return baseMapper.selectEmpIdListForTmgDaily(map);
    }

    @Override
    public int selectRelationEx(String sCust,String sLoginUser,String sTargetUser,String sSystem,String sDesig,
                                       String sDate, String psBase1, String psBase2, String psBase3,
                                       String psBase4, String psBase5, String psBase6,
                                       String psBase7, String psBase8) {
        if (StrUtil.isBlank(sCust)){
            sCust = "01";
        }
        if (StrUtil.isBlank(sLoginUser)){
            sLoginUser = "111";
        }
        if (StrUtil.isBlank(sTargetUser)) {
            sTargetUser = "222";
        }
        if (StrUtil.isBlank(sSystem)){
            sSystem = "system";
        }
        return baseMapper.selectRelationEx(sCust,sLoginUser,sTargetUser,sSystem,sDesig,sDate,psBase1,psBase2,psBase3,
                psBase4,psBase5,psBase6,psBase7,psBase8);
    }

    @Override
    public int selectRelation(String sCust,String sLoginUser,String sTargetUser,String sSystem,String sDesig,
                                String sDate) {
        if (StrUtil.isBlank(sCust)){
            sCust = "01";
        }
        if (StrUtil.isBlank(sLoginUser)){
            sLoginUser = "111";
        }
        if (StrUtil.isBlank(sTargetUser)) {
            sTargetUser = "222";
        }
        if (StrUtil.isBlank(sSystem)){
            sSystem = "system";
        }
        return baseMapper.selectRelation(sCust,sLoginUser,sTargetUser,sSystem,sDesig,sDate);
    }

    @Override
    public List<LoginControlEntity> selectUserInfoByDate(String loginUser, String language, String psDate) {
        return baseMapper.selectUserInfoByDate(loginUser,language,psDate);
    }

    @Override
    public int selectOrgRelation(String sCust, String sLoginUser,String sTargetComp,
                                 String sTargetSec,String sSystem,String non,String sDate) {
        if (StrUtil.isBlank(sCust)){
            sCust="01";
        }
        if(StrUtil.isBlank(sLoginUser)){
            sLoginUser = "111";
        }
        if (StrUtil.isBlank(sTargetComp)){
            sTargetComp = "222";
        }
        if (StrUtil.isBlank(sTargetSec)){
            sTargetSec = "222";
        }
        if (StrUtil.isBlank(sSystem)){
            sSystem = "system";
        }
        return baseMapper.selectOrgRelation(sCust,
                sLoginUser, sTargetComp, sTargetSec, sSystem, null, sDate);
    }

    @Override
    public List<V3CompatiblePostEntity> getVersion3SectionChief(
            String sCustid,
            String sCompid,
            String sDeptid,
            String sDate,
            String sPostid,
            boolean bIncludeactual
    ) {
        return baseMapper.getVersion3SectionChief(sCustid,sCompid,sDeptid,sDate,sPostid,bIncludeactual);
    }

    /**
     * 発令上の勤務開始日取得用SQL取得メソッド
     */
    @Override
    public EmployMentWithMEVo selectDateofemploymentWithME(String custId, String compId, String empId){
        return baseMapper.selectDateofemploymentWithME( custId,  compId,  empId);
    }


    /**
     * ================社员检索(Group定义中社员检索弹窗用)===========
     */
    @Override
    public List<EmployeeInfoSearchEntity> selectEmployeeInfoUserIDList(EmployInfoSearchDTO searchDTO) {
        return baseMapper.selectEmployeeInfoUserIDList(searchDTO);
    }

    @Override
    public  List<EmployeeInfoSearchEntity> selectEmployeeInfoUserIDListAdd(EmployInfoSearchDTO searchDTO) {
        return baseMapper.selectEmployeeInfoUserIDListAdd(searchDTO);
    }

    @Override
    public List<EmployeeInfoSearchEntity> selectEmployeeInfoList(
            String searchDate,
            String language,
            String designation,
            String sEmpInfoUserIDList,
            String sCompNick,
            String sSectionNick,
            String sPostNick,
            String loginUser,
            String systemId
    ) {
        return baseMapper.selectEmployeeInfoList(searchDate,language,designation,sEmpInfoUserIDList,sCompNick,sSectionNick,sPostNick,loginUser,systemId);
    }


    // -=========================== 用户管理相关sql开始============================//
    @Override
    public IPage<UserManagerListDTO> selectMainAllList(IPage<UserManagerListDTO> page, String custId, String language, String companyId, List<String> companyList) {
        return baseMapper.selectMainAllList(page, custId, language, companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainLockoutList(IPage<UserManagerListDTO> page, String custId, String language, String companyId, List<String> companyList) {
        return baseMapper.selectMainLockoutList(page, custId, language, companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainAdminList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainAdminList(page,custId,language,companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainEmpNameList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList,String empName) {
        return baseMapper.selectMainEmpNameList(page,custId,language,companyId,companyList,empName);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainSectionList(IPage<UserManagerListDTO> page,String custId,String language,List<String> companyList,
                                                           String sectionCompanyId,String sectionId) {
        return baseMapper.selectMainSectionList(page,custId,language,companyList,sectionCompanyId,sectionId);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainEmpIdList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList,String empId) {
        return baseMapper.selectMainEmpIdList(page,custId,language,companyId,companyList,empId);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainAfterRetireList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainAfterRetireList(page,custId,language, companyId, companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainAfterJoinList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainAfterJoinList(page, custId,language, companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainBeforeJoinList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainBeforeJoinList(page,custId,language,companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainValidList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainValidList(page,custId,language,companyId,companyList);
    }

    @Override
    public List<UserManagerDTO> selectStartList(String custId, List<String> userIds,

                                                String language, Integer searchType,
                                                List<String> companyList) {
        return baseMapper.selectStartList(custId,userIds,language,searchType,companyList);
    }

    @Override
    public List<UserManagerDTO> selectEndList(String custId, List<String> userIds,
                                              String language, Integer searchType,
                                              List<String> companyList) {
         return baseMapper.selectEndList(custId,userIds, language, searchType, companyList);
    }

    @Override
    public UserManagerDTO selectPersonalName(String custId, String userId, String language,  List<String> companyList) {
         return baseMapper.selectPersonalName(custId,userId,language, companyList);
    }

    @Override
    public PersonalInfoDTO selectPersonalInfo(Date baseDate, String custId, String language, String userId) {
         String sBaseDate = SysUtil.transDateToString(baseDate);
         return baseMapper.selectPersonalInfo(custId,userId,language,sBaseDate);
    }
    /*========================= 用户管理相关sql结束 ==================================*/

    @Override
    public IPage<MastEmployeesDO> selectInvalidEmailEmpList(IPage<MastEmployeesDO> page) {
        return baseMapper.selectInvalidEmailEmpList(page);
    }

    @Override
    public IPage<UserManagerListDTO> searchEmpForUpdateMail(IPage<UserManagerListDTO> page,String keyword) {
        return baseMapper.searchEmpForUpdateMail(page,keyword);
    }

    @Override
    public Optional<MastEmployeesDO> getEmployInfo(String empId) {
        QueryWrapper<MastEmployeesDO> qw = SysUtil.query();
        List<MastEmployeesDO> employList =  list(qw.eq("ME_CUSERID",empId)
                .eq("ME_CCUSTOMERID_CK","01")
                .eq("ME_CCUSTOMERID_CK","01")
                 .select("ME_CKANJINAME","ME_CMAIL"));
        if (CollUtil.isEmpty(employList)) {
            return Optional.empty();
        }
        return Optional.of(employList.get(0));
    }
}
