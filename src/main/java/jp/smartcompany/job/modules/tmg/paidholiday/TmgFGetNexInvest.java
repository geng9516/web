package jp.smartcompany.job.modules.tmg.paidholiday;


import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.base.service.PluggableService;

import java.util.Date;

/**
 *
 * @author:Wang Ziyue
 * */
class TmgFGetNexInvest extends BaseExecute {


    private TmgGetInvestType tmgGetInvestType;
    /**
     * 次回付与日取得
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @param searchdate 検索期限
     * @return String 年休付与区分
     */
    public Date init(String customerId, String companyId, String employeeId
            ,Date yyyymmdd, Date searchdate){
        int nDIRECTION =0;

        if(yyyymmdd.compareTo(searchdate)<0){
            nDIRECTION=1;
        }else{
            nDIRECTION=-1;
        }

        while(DateUtil.betweenDay(yyyymmdd,searchdate,true)==1)
        {
            yyyymmdd=DateUtil.offsetDay(yyyymmdd,nDIRECTION);
            if(tmgGetInvestType.init(customerId,companyId,employeeId,yyyymmdd)!= null){
                return yyyymmdd;
            }
        }

        return null;
    }

    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }
}
