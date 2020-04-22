package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupMemberDO;
import jp.smartcompany.job.modules.core.service.ITmgGroupMemberService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgCalendarRow;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nie Wanqun
 * <p>
 * カレンダー情報の返却
 * TMG_F_GET_CALENDAR_LIST
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetCalendarList extends BaseExecute {

    /**
     * ITmgGroupMemberService
     */
    private final ITmgGroupMemberService iTmgGroupMemberService;

    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }

    /**
     * カレンダー情報の返却
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param sectionId  組織コード
     * @param groupId    グループコード
     * @param date       基準日
     * @return カレンダー情報
     */
    public List<TmgCalendarRow> init(String customerId, String companyId, String employeeId, String sectionId, String groupId, Date date) {
        // 引数を変数に待避
        String wsCustomerId = customerId;
        String wsCompanyId = companyId;
        String wSectionId = sectionId;
        String wsGroupId = groupId;
        Date wsDate = date;

        if (wsDate == null) {
            wsDate = DateUtil.date();
        }

        boolean flg = !StrUtil.hasEmpty(employeeId) && StrUtil.hasEmpty(wSectionId) && StrUtil.hasEmpty(wsGroupId);
        if (flg) {
            List<TmgGroupMemberDO> tmgGroupMemberDOList = this.cursorEmployees(customerId, companyId, employeeId);
            if (tmgGroupMemberDOList.size() > 0) {
                wSectionId = tmgGroupMemberDOList.get(0).getTgrmCsectionid();
                wsGroupId = tmgGroupMemberDOList.get(0).getTgrmCgroupid();
            }

        }

        List<TmgCalendarRow> tmgCalendarRowList = new ArrayList<TmgCalendarRow>();


        return tmgCalendarRowList;

    }


    /**
     * レコード呼出(人)
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @return
     */
    private List<TmgGroupMemberDO> cursorEmployees(String customerId, String companyId, String employeeId) {

        // TODO 未完
        List<TmgGroupMemberDO> tmgGroupMemberDOList = iTmgGroupMemberService.getTmgGroupMemberDOList(customerId, companyId, employeeId);

        return tmgGroupMemberDOList;
    }

}
