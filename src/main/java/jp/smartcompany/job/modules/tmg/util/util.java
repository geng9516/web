package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.core.service.ITmgPaidHolidayAttributeService;
import jp.smartcompany.job.modules.core.service.ITmgPersonalPatternService;
import jp.smartcompany.job.modules.tmg.PatternSetting.TmgFGetPatternDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class util {

    private final ITmgPaidHolidayAttributeService iTmgPaidHolidayAttributeService;
    private final ITmgPersonalPatternService iTmgPersonalPatternService;
    private final IConfSyscontrolService iConfSyscontrolService;
    private TmgFGetPatternDefault tmgFGetPatternDefault;


    /**
     *平均勤務時間の換算処理
     * @param customerId 法人コード
     * @param companyId 顧客コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @param ntfTypeGroupId  申請種類グルーピングコード
     * @return int 平均勤務時間
     * */
    public int tmgFConvDay2Time (String customerId, String companyId, String employeeId, Date yyyymmdd,String ntfTypeGroupId){

        int returnValue;
        //平均勤務時間を取得する
        returnValue=tmgFgGetAvgWorkTime(customerId,companyId,employeeId,yyyymmdd);
        //換算処理
        //returnValue=tmgConvFraction4Nyk(customerId,companyId,employeeId,yyyymmdd,returnValue);

        return returnValue;
}



    /**
     *週平均勤務時間の取得
     * @param customerId 法人コード
     * @param companyId 顧客コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @return int 週平均勤務時間
     * */
    public int tmgFgGetAvgWorkTime (String customerId, String companyId, String employeeId, Date yyyymmdd){

        int workTime;
        Date sysIntroduction;
        //年次休暇付与属性テーブルを検索し、週平均勤務時間を取得
        workTime=iTmgPaidHolidayAttributeService.selectAvgWorkTime(customerId,companyId,employeeId,yyyymmdd);

        if (workTime!=0){
            return workTime;
        }else {
            //個人別勤務パターンテーブルを検索し、週平均勤務時間を取得
            workTime=iTmgPersonalPatternService.selectAvgWorkTime(customerId,companyId,employeeId,yyyymmdd);
            if(workTime!=0){
                return workTime;
            }else{
                //[勤怠]HR連携用(勤怠種別)の勤怠種別に応じ、勤務パターンを学内標準か再雇用者か判断
                sysIntroduction= Convert.toDate(tmgFGetSysProp(customerId,"TMG_SYSTEM_INTRODUCTION_DATE"));
                List<Object> dateList= Arrays.asList(yyyymmdd,Convert.toDate(sqlUtil.nvlSql(sysIntroduction,yyyymmdd)));
                yyyymmdd=Convert.toDate(sqlUtil.greatestSql(dateList));
                return tmgFGetPatternDefault.init(customerId,companyId,employeeId,yyyymmdd).getNTime();
            }
        }
    }



    /**
     *システムプロパティを取得する
     * @param customerId 顧客コード
     * @param propertyName システムプロパティ名
     * @return String システムプロパティ値
     * */
    public String tmgFGetSysProp(String customerId,String propertyName){
        String propertyValue;
        propertyValue=iConfSyscontrolService.selectPropertyValue(customerId,propertyName);
        if (StrUtil.hasEmpty(propertyValue)){
            propertyValue=iConfSyscontrolService.selectPropertyValueNotFound(customerId,propertyName);
            if(StrUtil.hasEmpty(propertyValue)){
                return null;
            }else{
                return propertyValue;
            }
        }else{
            return propertyValue;
        }

    }
}
