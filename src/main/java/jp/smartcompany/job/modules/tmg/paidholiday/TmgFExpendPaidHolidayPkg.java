package jp.smartcompany.job.modules.tmg.paidholiday;


import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgExpendPaidHoliday;

import java.util.Date;

class TmgFExpendPaidHolidayPkg extends BaseExecute {

    /**
     * プラガブル用メイン処理
     *
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {

        // init()を呼び出す
        return this.init(pluggableDTO.getCustomerId(),pluggableDTO.getCompanyId(),pluggableDTO.getEmployeeId(),pluggableDTO.getStartDate(),
                pluggableDTO.getEndDate(),pluggableDTO.getRestDays(),pluggableDTO.getRestHours(),pluggableDTO.getNtfNo(),pluggableDTO.getSingle());


    }


    protected Object init(String customerId, String companyId, String employeeId, Date beginDate, Date endDate ,
                          int paidRestDays, int paidRestHours, String ntfNo,int single) {


        String csNtfTypeGroupNyk="TMG_NTFTYPEGROUP|NYK";

        //平均勤務時間(当日)
        int workTime ;
        //平均勤務時間(前日)
        int workTimeBk;

        //初期設定
        //年休消化計算情報(累計)
        TmgExpendPaidHoliday teph=new TmgExpendPaidHoliday();

        teph.setNRestDays(paidRestDays);
        teph.setNRestHours(paidRestHours);


        return null;
    }
}