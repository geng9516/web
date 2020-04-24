package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Nie Wanqun
 *
 * 年休付与区分判定(常勤通常付与)
 * TMG_F_GET_INVEST_TYPE_H_IYAKU
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetInvestTypeHIyaku extends BaseExecute {

    /**
     * IMastGenericDetailService
     */
    private final IMastGenericDetailService iMastGenericDetailService;

    /**
     *プラガブル用メイン処理
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {

        // init()を呼び出す
        return this.init(pluggableDTO.getCustomerId(),pluggableDTO.getCompanyId(),pluggableDTO.getEmployeeId(),pluggableDTO.getYyyymmdd(),pluggableDTO.getBeginDateWork());
    }

    /**
     * 年休付与区分判定(常勤通常付与)
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @param beginDateWork 開始日
     * @return String 年休付与区分
     */
    protected Object init(String customerId, String companyId, String employeeId, Date yyyymmdd, Date beginDateWork) {


        // 常勤通常付与年休ルールを取得
        int  nenkyu =  iMastGenericDetailService.selectNenkyuRuleH(customerId,  companyId
                ,  employeeId, yyyymmdd, beginDateWork);

        // 入社半年後
        boolean flg  = (nenkyu == 9 && DateUtil.compare(yyyymmdd , beginDateWork) >= 0
                && DateUtil.compare(DateUtil.endOfDay(yyyymmdd), DateUtil.endOfDay(DateUtil.offset(beginDateWork, DateField.MONTH, 6))) == 0);
        if (flg){
            return "TMG_INVEST_TYPE|20";
        }

        // 入社一年後毎
        flg  = (nenkyu == 9 && DateUtil.compare(yyyymmdd , beginDateWork) > 0
                && DateUtil.format(yyyymmdd,"MM/DD")
                .equals(DateUtil.format(DateUtil.offset(beginDateWork, DateField.MONTH, 6),"MM/DD")) );
        if (flg){
            return "TMG_INVEST_TYPE|21";
        }

        // 指定月初日付与
        flg  = (nenkyu > 0 && nenkyu < 13 && nenkyu != 9
                && DateUtil.compare(yyyymmdd , beginDateWork) > 0
                && DateUtil.format(yyyymmdd,"MM/DD")
                .equals(DateUtil.format(DateUtil.offset(DateUtil.parseDate("2019/12/01"), DateField.MONTH, nenkyu),"MM/DD")) );
        if (flg){
            return "TMG_INVEST_TYPE|22";
        }

        // 入社日
        flg  = (nenkyu > 0 && nenkyu < 13 && nenkyu != 9
                && DateUtil.compare(yyyymmdd , beginDateWork) == 0);
        if (flg){
            return "TMG_INVEST_TYPE|23";
        }

        return null;
    }

}
