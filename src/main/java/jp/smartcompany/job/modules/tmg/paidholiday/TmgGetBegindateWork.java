package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Nie Wanqun
 * 勤務開始日を求める
 * TMG_F_GET_BEGINDATE_WORK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetBegindateWork extends BaseExecute {

    /**
     * IMastEmployeesService
     */
    private final IMastEmployeesService iMastEmployeesService;

    /**
     * TmgGetBegindateEmp
     */
    private final TmgGetBegindateEmp tmgGetBegindateEmp;


    /**
     * TmgGetMgdD
     */
    private final TmgGetMgdD tmgGetMgdD;

    /**
     *プラガブル用メイン処理
     *
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }

    /**
     * 勤務開始日を求める
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @return Date 勤務開始日
     */
    public Date init(String customerId, String companyId, String employeeId, Date yyyymmdd){
        // マスタコード
        String masterCode ="TMG_DATEOFEMPLOYMENT" + "|"+ employeeId;

        // 勤務開始日を取得
        Date begindateWork = tmgGetMgdD.init(customerId,companyId,yyyymmdd,masterCode,1,"ja");

        if (begindateWork != null){
            return begindateWork;
        }

        // MAST_EMPLOYEESの採用日を取得
        begindateWork = iMastEmployeesService.selectBegindateWork(customerId,companyId,employeeId,yyyymmdd);

        if (begindateWork != null){
            return begindateWork;
        }

        // 存在しない場合、システム最小日付をセットする
        begindateWork = tmgGetBegindateEmp.init(customerId,companyId,employeeId,yyyymmdd);
        if (begindateWork != null){
            return begindateWork;
        }

        // デフォルトの勤務開始日
        begindateWork = DateUtil.parseDate("1900/01/01");

        // 勤務開始日を返す
        return begindateWork;
    }
}
