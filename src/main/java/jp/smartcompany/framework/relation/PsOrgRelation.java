package jp.smartcompany.framework.relation;

import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PsOrgRelation {

    private final IMastEmployeesService iMastEmployeesService;

    public int getRelationId(String sCust, String sLoginUser,
                             String sTargetComp, String sTargetSec, String sSystem) {
        return getRelationId(sCust, sLoginUser, sTargetComp, sTargetSec,
                sSystem, getSysdate());
    }

    public int getRelationId(String sCust, String sLoginUser,
                             String sTargetComp, String sTargetSec, String sSystem, String sDate) {
        int nRelId;
        nRelId = iMastEmployeesService.selectOrgRelation(sCust,
                sLoginUser, sTargetComp, sTargetSec, sSystem, null, sDate);
        return nRelId;
    }

    private String getSysdate() {
        Date dateToday = new Date();
        SimpleDateFormat fFormat = new SimpleDateFormat("yyyy/MM/dd");
        return fFormat.format(dateToday);
    }
}
