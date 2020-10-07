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
    @Transactional
    public void changeEndDate(UserManagerEditEndForm dto) {
        List<UserManagerListDTO> accountDTOList = dto.getList();
        String userId = SecurityUtil.getUserId();
        Date date = DateUtil.date();
        List<MastAccountDO> accountList = CollUtil.newArrayList();
        accountDTOList.forEach(accountDTO -> {
            MastAccountDO account = new MastAccountDO();
            account.setMaCmodifieruserid(userId);
            account.setMaDmodifieddate(date);

            if (StrUtil.isNotBlank(account.getMaCuserid())) {
                //「退職日を設定する」のとき
                if (dto.getUseRetireDate()) {
                    Date retirementDate = accountDTO.getMeDdateofretirement();
                    if (retirementDate == null) {
                        // 退職日がNULLのときはそのまま
                        if (accountDTO.getMaDend() != null) {
                            account.setMaDend(accountDTO.getMaDend());
                        } else {
                            // 入力値がなかったらシステム日付
                            account.setMaDend(dto.getEndDate());
                        }
                    } else {
                        // 退職日を設定
                        account.setMaDend(retirementDate);
                    }
                } else {
                    if (accountDTO.getMaDend() != null) {
                        account.setMaDend(accountDTO.getMaDend());
                    } else {
                        // 入力値がなかったらシステム日付
                        account.setMaDend(dto.getEndDate());
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
        // 法人検索対象範囲取得
        List<String> lComps = searchCompanyUtil.getCompList(DateUtil.date());
        List<UserManagerDTO> userManagerDtoList = mastEmployeesService.selectEndList(
                sCustomerid,
                userIds,
                sLanguage,
                searchType,
                lComps);
        for (UserManagerDTO dto : userManagerDtoList) {
            // 表示用：MAXDATEを空にする
            if (dto.getMaDend() != null && StrUtil.equals(PsConst.MAXDATE, SysUtil.transDateToString(dto.getMaDend()))) {
                dto.setMaDend(null);
            }
            String sStatus = userManagerEditCommonLogic.getStatus(dto);
            dto.setStatus(sStatus);
        }
        return userManagerDtoList;
    }


    /**
     * 前端实现，终了日比当前日期小一天
     * 初期値表示日付取得
     */
    private Date getDefaultDate() {
        // 利用終了日(初期値設定)
        Calendar cal = (Calendar) Calendar.getInstance().clone();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new Date(cal.getTime().getTime());
    }

}
