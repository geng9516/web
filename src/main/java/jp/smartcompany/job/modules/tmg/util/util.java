package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.service.IConfSyscontrolService;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import jp.smartcompany.job.modules.core.service.ITmgPaidHolidayAttributeService;
import jp.smartcompany.job.modules.core.service.ITmgPersonalPatternService;
import jp.smartcompany.job.modules.tmg.paidholiday.TmgConvFraction4nyk;
import jp.smartcompany.job.modules.tmg.patternsetting.TmgFGetPatternDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class util {

    private final ITmgPaidHolidayAttributeService iTmgPaidHolidayAttributeService;
    private final ITmgPersonalPatternService iTmgPersonalPatternService;
    private final IConfSyscontrolService iConfSyscontrolService;
    private final TmgFGetPatternDefault tmgFGetPatternDefault;
    private final TmgConvFraction4nyk tmgConvFraction4nyk;
    private final IMastOrganisationService iMastOrganisationService;
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
        returnValue=tmgConvFraction4nyk.init(customerId,companyId,employeeId,yyyymmdd,returnValue);

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


    /**
     *上位組織を取得
     * @param customerId 法人コード
     * @param companyId 顧客コード
     * @param sectionId 組織番号
     * @param yyyymmdd 基準日
     * @return List<String>  上位組織
     * */
    public List<String>  tmgGetUpperSection (String customerId, String companyId, String sectionId, Date yyyymmdd){
        Date baseDate=(Date)sqlUtil.nvlSql(yyyymmdd,DateUtil.date());
        List<String> sectionIdList=new ArrayList<String>();
        //取得フラグ
        boolean loopFlg=false;
        String upperSectionId="";
        if(StrUtil.hasEmpty(sectionId)){
            List<MastOrganisationDO> moDoList=iMastOrganisationService.selectPatternOrganisation(customerId,companyId,baseDate);
            for(MastOrganisationDO mODo:moDoList){
                sectionIdList.add(mODo.getMoCsectionidCk());
            }
        }else{
            sectionIdList.add(sectionId);
            upperSectionId=sectionId;
            //上位組織を取得
            do{
                loopFlg=false;
                MastOrganisationDO moDo=iMastOrganisationService.selectOrganisation(customerId,companyId,upperSectionId,baseDate);
                if(moDo!=null&&!StrUtil.hasEmpty(moDo.getMoCsectionidCk())){
                    //上位組織を取得
                    upperSectionId= moDo.getMoCsectionidCk();
                    //上位組織が取得出来た場合は対象にセット
                    sectionIdList.add(upperSectionId);
                    loopFlg=true;
                }

            }while (loopFlg);
        }
        return sectionIdList;

    }
}
