package jp.smartcompany.job.modules.tmg.paidholiday;




import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.service.ITmgPaidHolidayService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author:Wang Ziyue
 * */
@Service
class TmgSelectNykLoseDate extends BaseExecute {
    private ITmgPaidHolidayService iTmgPaidHolidayService;

    /**
     * 年休の喪失日をリストで取得
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 検索期間開始日
     * @param startDate 検索期間開始日
     * @param endDate 検索期間終了日
     * @return list<Date></> 年休付与区分
     */
    public List<Date> init(String customerId, String companyId, String employeeId, Date startDate, Date endDate){

        TmgFExcludeTerm selectList=new  TmgFExcludeTerm();
        List<TmgTermRow> ttRList =selectList.init(customerId,companyId,startDate,endDate,0);
        List<Date> resultDateList=new ArrayList();
        List<Date> dateList;
        for(TmgTermRow ttr:ttRList){
            dateList=iTmgPaidHolidayService.selectNykLoseDate(customerId,companyId,ttr.getDOpen(),ttr.getDClose(),employeeId);
            if(dateList.size()>0){
                CollUtil.addAllIfNotContains(resultDateList,dateList);
            }
        }
        return resultDateList;
    }

    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }
}
