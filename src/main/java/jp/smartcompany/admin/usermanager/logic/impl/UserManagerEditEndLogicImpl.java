package jp.smartcompany.admin.usermanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.admin.usermanager.form.ShowLimitDateForm;
import jp.smartcompany.admin.usermanager.form.UserManagerEditEndForm;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditCommonLogic;
import jp.smartcompany.admin.usermanager.logic.UserManagerEditEndLogic;
import jp.smartcompany.boot.common.GlobalException;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userManagerEditEndLogic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserManagerEditEndLogicImpl implements UserManagerEditEndLogic {

    private final IMastAccountService mastAccountService;
    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastEmployeesService mastEmployeesService;
    private final UserManagerEditCommonLogic userManagerEditCommonLogic;

    @Override
    public Map<String,Object> showChangeEndDate(ShowLimitDateForm form) {
        List<UserManagerDTO> list = searchForEndDate("01",form.getUserIds(),"ja",form.getSearchType());
        return MapUtil.<String,Object>builder()
                .put("defaultEndDate",getDefaultDate())
                .put("list",list)
                .build();
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void changeEndDate(UserManagerEditEndForm dto) {
        List<UserManagerListDTO> accountDTOList = dto.getList();
        String userId = SecurityUtil.getUserId();
        Date date = DateUtil.date();
        List<MastAccountDO> accountList = CollUtil.newArrayList();
        accountDTOList.forEach(accountDTO -> {
            MastAccountDO account = new MastAccountDO();
            // ??????ID????????????
            account.setMaId(accountDTO.getMaId());
            account.setMaCmodifieruserid(userId);
            account.setMaDmodifieddate(date);

            if (StrUtil.isNotBlank(accountDTO.getMaCuserid())) {
                //???????????????????????????????????????
                if (dto.getUseRetireDate()) {
                    Date retirementDate = accountDTO.getMeDdateofretirement();
                    if (retirementDate == null) {
                        // ????????????NULL????????????????????????
                        if (dto.getEndDate() != null) {
                            account.setMaDend(dto.getEndDate());
                        } else {
                            // ?????????????????????????????????????????????
                            account.setMaDend(date);
                        }
                    } else {
                        // ??????????????????
                        account.setMaDend(retirementDate);
                    }
                } else {
                    if (dto.getEndDate() != null) {
                        account.setMaDend(dto.getEndDate());
                    } else {
                        // ?????????????????????????????????????????????
                        account.setMaDend(date);
                    }
                }
                accountList.add(account);
            }
        });
        if (CollUtil.isNotEmpty(accountList)) {
            mastAccountService.updateBatchById(accountList);
        }
    }

    private List<UserManagerDTO> searchForEndDate(
            String sCustomerid, List<String> userIds, String sLanguage, int searchType) {
        // ??????????????????????????????
        List<String> lComps = searchCompanyUtil.getCompList(DateUtil.date());
        List<UserManagerDTO> userManagerDtoList = mastEmployeesService.selectEndList(
                sCustomerid,
                userIds,
                sLanguage,
                searchType,
                lComps);
        for (UserManagerDTO dto : userManagerDtoList) {
            // ????????????MAXDATE???????????????
            if (dto.getMaDend() != null && StrUtil.equals(PsConst.MAXDATE, SysUtil.transDateToString(dto.getMaDend()))) {
                dto.setMaDend(null);
            }
            String sStatus = userManagerEditCommonLogic.getStatus(dto);
            dto.setStatus(sStatus);
        }
        return userManagerDtoList;
    }


    /**
     * ????????????????????????????????????????????????
     * ???????????????????????????
     */
    private Date getDefaultDate() {
        // ???????????????(???????????????)
        Calendar cal = (Calendar) Calendar.getInstance().clone();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new Date(cal.getTime().getTime());
    }

}
