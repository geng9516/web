package jp.smartcompany.job.modules.tmg.paidholiday;


import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import java.util.Date;
import java.util.List;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import org.springframework.stereotype.Service;

//@Service
class TmgFExcludeTerm extends BaseExecute {
    private IMastGenericDetailService iMastGenericDetailService;
    /**
     * 2つの歴の引き算
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param startDate 検索期間開始日
     * @param endDate 検索期間終了日
     * @param checkCtype 差異(0のときはタイプの差異を無視する)
     * @return list<tmgtermrow>
     */
    public List<TmgTermRow> init(String customerId,String companyId, Date startDate,Date endDate, Integer checkCtype){
        if (checkCtype !=null){
            checkCtype=1;
        }
        String csTypeNull ="@@@@@@@@@@";
        List<TmgTermRow> selectList;
        selectList=iMastGenericDetailService.tmgFExcludeTerm(customerId,companyId,startDate,endDate,checkCtype.toString(),csTypeNull);

        return selectList;
    }


    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }
}
