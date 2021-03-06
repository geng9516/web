package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.form.ShowLimitDateForm;
import jp.smartcompany.admin.usermanager.form.UserManagerEditStartForm;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditStartLogic;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.util.PsConst;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userManagerEditStartLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerEditStartLogicImpl implements UserManagerEditStartLogic {

   private final ScCacheUtil cacheUtil;
   private final IMastAccountService mastAccountService;
   private final UserManagerEditCommonLogic userManagerEditCommonLogic;
   private final PsSearchCompanyUtil searchCompanyUtil;
   private final IMastEmployeesService mastEmployeesService;

    @Override
    public Map<String,Object> showChangeStartDate(ShowLimitDateForm form) {
        List<UserManagerDTO> list = searchForStartDate("01",form.getUserIds(),"ja",form.getSearchType());
        return MapUtil.<String,Object>builder()
                .put("list",list)
                .put(UserManagerEditCommonLogic.PROP_PW_MAX_LEN,cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MAX_LEN))
                .put(UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT,cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_CHANGE_PW_LIMITATION_COUNT))
                .put(UserManagerEditCommonLogic.PROP_PW_MIN_LEN,cacheUtil.getSystemProperty(UserManagerEditCommonLogic.PROP_PW_MIN_LEN))
                .put("defaultStartDate", SysUtil.transDateToString(DateUtil.date())).build();
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public Map<String,Object> changeStartDate(UserManagerEditStartForm form) {
        Date startDate;
        Date dateNow = DateUtil.date();
        Date endDate = SysUtil.transStringToDate(PsConst.MAXDATE);
        String customerId = "01";
        List<UserManagerDTO> checkListOld;
        String currentUserId = SecurityUtil.getUserId();
        //------------------------------------------------------------------------------
        // ?????????????????????(??????????????????????????????????????????????????????????????????????????????????????????)
        //------------------------------------------------------------------------------
        List<UserManagerDTO> originalList = form.getOriginalUserList();
        for (UserManagerDTO userManagerDTO : originalList) {
            String sUserid  =  userManagerDTO.getMeCuserid();
            String sAccount =  userManagerDTO.getMeCcompanyid() + "_" +  userManagerDTO.getMeCemployeeidCk();
            //========================================================
            //?????????????????????????????????(1)
            //========================================================
            //??????????????????????????? == true ??????????????????????????????
            if (userManagerDTO.getMaId() == null) {
                List<UserManagerDTO> checkList =
                        mastAccountService.selectStartCheckAccount(
                                customerId,
                                sUserid,
                                sAccount);
                if (CollUtil.isNotEmpty(checkList)) {
                    String name = userManagerDTO.getMeCname();
                    throw new GlobalException(UserManagerEditCommonLogic.MESSAGE_ID_DUPLICATE_AUTO+"???"+name+":"+sAccount+"???");
                }
            }
            //=========================================================
            //?????????????????????????????????(1)
            //=========================================================
            checkListOld = mastAccountService.selectPersonalCheckAccountOld(customerId, sAccount);
            //========================================================
            //???????????????????????????(1)-2
            //========================================================
            if (form.getUseEntranceDate()) {
                //??????????????????????????????????????????????????????????????????
                if (userManagerDTO.getMeDdateofemployement() == null) {
                    //????????????null?????????????????????????????????????????????
                    startDate = userManagerDTO.getMeDstartdate();
                } else {
                    startDate = userManagerDTO.getMeDdateofemployement();
                }
            } else {
                //?????????????????????????????????????????????????????????
                startDate = form.getStartDate();
            }
            //=========================================================
            //????????????????????????????????????(2)
            //=========================================================
            UserManagerDTO dto = mastAccountService.selectPersonalCheckUserid(sUserid);
            //------------------------------------------------------
            //?????????????????????
            //------------------------------------------------------
            MastAccountDO accountDto = new MastAccountDO();
            accountDto.setMaId(userManagerDTO.getMaId());
            accountDto.setMaDstart(startDate);
            accountDto.setMaDend(endDate);
            accountDto.setMaNretrycounter(0);
            accountDto.setMaNpasswordlock(0);
            accountDto.setMaCadminuser("0");
            accountDto.setMaCmodifieruserid(currentUserId);
            accountDto.setMaDmodifieddate(dateNow);
            // ??????????????????????????????????????????????????????????????????
            if (userManagerDTO.getMaId() == null || CollUtil.isNotEmpty(checkListOld)) {
                // ???????????????????????????(?????????????????????????????????)
                if (dto == null){
                    // Insert??????
                    userManagerEditCommonLogic.accountInsert(accountDto, checkListOld, customerId, sAccount, sUserid,dateNow);
                } else {
                    //??????????????????????????????????????????????????????
                    if (dto.getMaId() == null){
                        // Insert??????
                        userManagerEditCommonLogic.accountInsert(accountDto, checkListOld, customerId, sAccount, sUserid,dateNow);
                    } else {
                        mastAccountService.updateById(accountDto);
                    }
                }
            } else {
                mastAccountService.updateById(accountDto);
            }
        }
        //------------------------------------------------------
        //?????????????????????
        //------------------------------------------------------
        Map<String, Object> passwordMap = MapUtil.newHashMap();
        Integer passwordType = form.getPasswordType();
        if (passwordType != null
                && ( passwordType.equals(2)
                || ( passwordType.equals(1)
                &&  StrUtil.isNotBlank(form.getPassword())))) {
            passwordMap = userManagerEditCommonLogic.updatePassword(
                    customerId,
                    currentUserId, "ja"
                    , form.getUserIds()
                    , passwordType
                    , form.getPassword()
                    , form.getForceChangePassword());
        }
        return passwordMap;
    }

    /**
     * ??????.
     * @param sCustomerid ???????????????
     * @param userIds ?????????ID
     * @param sLanguage ??????
     * @param searchType ??????????????????
     * @return  UserManagerEditStartDto
     */
    private List<UserManagerDTO> searchForStartDate(
            String sCustomerid, List<String> userIds, String sLanguage, int searchType) {
        // ??????????????????????????????
        List<String> lComps = searchCompanyUtil.getCompList(DateUtil.date());
        List<UserManagerDTO> userManagerDtoList = mastEmployeesService.selectStartList(
                sCustomerid,
                userIds,
                sLanguage,
                searchType,
                lComps);

        for (UserManagerDTO dto : userManagerDtoList) {
            // ????????????MAXDATE???????????????
            if (dto.getMaDend() != null && StrUtil.equals(PsConst.MAXDATE,SysUtil.transDateToString(dto.getMaDend()))) {
                dto.setMaDend(null);
            }
            String sStatus = userManagerEditCommonLogic.getStatus(dto);
            dto.setStatus(sStatus);
        }
        return userManagerDtoList;
    }

}
