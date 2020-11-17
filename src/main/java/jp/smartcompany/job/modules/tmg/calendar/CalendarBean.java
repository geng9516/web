package jp.smartcompany.job.modules.tmg.calendar;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarSectionDO;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import jp.smartcompany.job.modules.core.service.ITmgCalendarSectionService;
import jp.smartcompany.job.modules.core.service.ITmgCalendarService;
import jp.smartcompany.job.modules.core.service.ITmgDailyService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarColumnDto;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarMonthDto;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarYearDto;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarDispVo;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarVo;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 個人属性設定
 * ps.c01.tmg.CalendarBean
 *
 * @author Wang Ziyue
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CalendarBean {


    private final IMastOrganisationService iMastOrganisationService;
    private final ITmgCalendarSectionService iTmgCalendarSectionService;
    private final ITmgCalendarService iTmgCalendarService;
    private final ITmgDailyService iTmgDailyService;

    /**
     * カレンダー一覧表示の為のプロセスを実行します。<br>
     *   自所属(又は自グループ)の情報が存在しない場合は上位階層に登録している<br>
     *   情報を参照します。
     */
    public CalendarVo getCalendar(String year, PsDBBean psDBBean, TmgReferList referList){
        CalendarVo calendarVo=new CalendarVo();
        MastOrganisationDO MoDo=selectOrganization(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec());
        if(MoDo==null){
            return null;
        }


        List<CalendarDispVo> calendarDispVoList=new ArrayList<CalendarDispVo>();
        String groupId;
        if(StrUtil.hasEmpty(referList.getTargetGroup())){
            groupId="'"+referList.getTargetSec() +  "|000000"+"'";
        }else{
            groupId="'"+referList.getTargetGroup()+"'";
        }
        if(!StrUtil.hasEmpty(MoDo.getMoCsectionidCk())){
            calendarDispVoList=iTmgCalendarSectionService.selectCalenderDisp(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec(),
                    groupId,getYear(year),"","0");
        }else{
            calendarDispVoList=iTmgCalendarService.selectCalenderDisp(psDBBean.getCustID(),psDBBean.getCompCode(),getYear(year));
        }

        // 全学かそれ以外か判定
        if (!StrUtil.hasEmpty(MoDo.getMoCsectionidCk())){
            // 結果をチェック(ゼロ件の場合最上位階層まで検索する)
            if (calendarDispVoList.isEmpty()){
                // 開始組織検索
                int iCount = MoDo.getMoNlevel().intValue();
                // レベル分チェック
                for (int i = 0; i < iCount ; i++) {
                    // 上位組織がnullの場合は終了
                    if ( MoDo.getMoCparentid()== null){
                        break;
                    }
                    // 部署カレンダー検索
                    calendarDispVoList=iTmgCalendarSectionService.selectCalenderDisp(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec(),
                            groupId,getYear(year),MoDo.getMoCsectionidCk(),"1");
                    if (!calendarDispVoList.isEmpty()){
                        break;
                    } else {
                        // 上位組織コードで再検索
                        MoDo = selectOrganization(psDBBean.getCustID(),psDBBean.getCompCode(),MoDo.getMoCparentid());
                    }
                }
                // 結果をチェック
                if (calendarDispVoList.isEmpty()){
                    // 全学で検索
                    calendarDispVoList=iTmgCalendarService.selectCalenderDisp(psDBBean.getCustID(),psDBBean.getCompCode(),getYear(year));
                    // 結果をチェック
                    if (calendarDispVoList.isEmpty()){
                        // 全学も存在しない場合はエラーメッセージを出力
                        // エラーフラグON
                        calendarVo.setGErrorFlag(true);
                    }
                }
            } else {
                // 自分自身のデータの場合は更新可能
                calendarVo.setGUpdateFlag(true);
            }
        } else {
            // 全学で検索
            if (calendarDispVoList.isEmpty()){
                // 全学も存在しない場合はエラーメッセージを出力
                // エラーフラグON
                calendarVo.setGErrorFlag(true);
            } else {
                // 自分自身のデータの場合は更新可能
                calendarVo.setGUpdateFlag(true);
                // 全学の場合は更新のみ使用可能
                calendarVo.setGTopLevelOrganization(false);
            }
        }
        for(CalendarDispVo dispVo: calendarDispVoList){
            dispVo.setDataFlg(JSONUtil.parseObj(dispVo.getMonthPara()));
        }
        calendarVo.setCalendarDispVoList(calendarDispVoList);
        calendarVo.setSelectMaxDaily(selectMaxDaily());
        return calendarVo;
    }


    /**
     * 新規登録処理
     */
    public GlobalResponse insertCalendar(CalendarYearDto yearDto, PsDBBean psDBBean, TmgReferList referList){
        MastOrganisationDO MoDo=selectOrganization(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec());
        String parentId = MoDo.getMoCparentid();

        try{

            for(CalendarMonthDto monthDto:yearDto.getMonthlist()){
                if(monthDto.getHolFlgList().size()<31){
                    for(int i= monthDto.getHolFlgList().size();i<31;i++){
                        monthDto.getHolFlgList().add("");
                    }
                }
                int insertCalendarSecton = iTmgCalendarSectionService.insertCalendarSecton(psDBBean.getCustID(),psDBBean.getCompCode()
                        ,referList.getTargetSec(),psDBBean.getUserCode(),referList.getTargetGroup(),monthDto.getMonth(),monthDto.getHolFlgList());
            }
        }catch (GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }
        return GlobalResponse.ok();
    }


    /**
     * 更新処理
     */
    public GlobalResponse updateCalendar(CalendarYearDto yearDto,PsDBBean psDBBean, TmgReferList referList){
        MastOrganisationDO MoDo=selectOrganization(psDBBean.getCustID(),psDBBean.getCompCode(),referList.getTargetSec());
        String parentId = MoDo.getMoCparentid();

        int updateCalendar;

        try{
            for(CalendarMonthDto monthDto:yearDto.getMonthlist()){

                List<CalendarColumnDto> calendarColumnDtoList=new ArrayList<CalendarColumnDto>();

                    // 全学判定
                    if (parentId == null){
                        // UPDATE     Calendar
                        for(int i=0;i<monthDto.getHolFlgList().size();i++){
                            CalendarColumnDto dto=new CalendarColumnDto();
                            if (i < 9){
                                dto.setColumnName("TCA_CHOLFLG0"+(i+1));
                            } else {
                                dto.setColumnName("TCA_CHOLFLG"+(i+1));
                            }
                            dto.setColumnValue(monthDto.getHolFlgList().get(i));
                            calendarColumnDtoList.add(dto);
                        }
                        updateCalendar = iTmgCalendarService.updateCalendar(psDBBean.getCustID(),psDBBean.getCompCode()
                                ,psDBBean.getUserCode(),monthDto.getMonth(),calendarColumnDtoList);
                    } else {
                        // UPDATE     CalendarOrganization
                        for(int i=0;i<monthDto.getHolFlgList().size();i++){
                            CalendarColumnDto dto=new CalendarColumnDto();
                            if (i < 9){
                                dto.setColumnName("TCAS_CHOLFLG0"+(i+1));
                            } else {
                                dto.setColumnName("TCAS_CHOLFLG"+(i+1));
                            }
                            dto.setColumnValue(monthDto.getHolFlgList().get(i));
                            calendarColumnDtoList.add(dto);
                        }
                        updateCalendar = iTmgCalendarSectionService.updateCalendar(psDBBean.getCustID(),psDBBean.getCompCode()
                                ,referList.getTargetSec(),referList.getTargetGroup(),psDBBean.getUserCode(),monthDto.getMonth(),calendarColumnDtoList);
                    }
            }
        }catch(GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }

        return GlobalResponse.ok();

    }








    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse deleteCalendar(String year,PsDBBean psDBBean,TmgReferList referList){
        String groupId;
        if(StrUtil.hasEmpty(referList.getTargetGroup())){
            groupId=referList.getTargetSec()+"|000000";
        }else{
            groupId=referList.getTargetGroup();
        }
        try{
            int deleteCalendar=iTmgCalendarSectionService.getBaseMapper().delete(SysUtil.<TmgCalendarSectionDO>query()
                    .eq("TCAS_CCUSTOMERID",psDBBean.getCustID())
                    .eq("TCAS_CCOMPANYID",psDBBean.getCompCode())
                    .eq("TCAS_CSECTIONID",referList.getTargetSec())
                    .eq("TCAS_CGROUPID",groupId)
                    .le("TCAS_DYYYYMM",year+"/12/31")
                    .ge("TCAS_DYYYYMM",year+"/01/01"));
        }catch (GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }
        return GlobalResponse.ok();
    }
    /**
     * 予定入力可能情報を取得
     * @return String SQL (予定入力可能年月取得用クエリ)
     */
    public String selectMaxDaily(){
        return iTmgDailyService.selectMaxDaily();
    }


    /**
     * 当年度情報を取得します。
     * @return String 当年度
     */
    public String getYear(String year) {
        // 年度の取得
        String sYear=year;
        if (StrUtil.hasEmpty(sYear)){
            sYear = getYearOfSysdate();
        } else if(sYear.equals("")){
            sYear = getYearOfSysdate();
        }
        return sYear;
    }
    /**
     * 当年度を返却します。
     * @return Stirng 基準日
     */
    public String getYearOfSysdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,0);
        calendar.set(Calendar.DATE, 1);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 組織情報を取得します
     * @param sectionId 組織コード
     * @return MastOrganisationDO (組織情報取得用クエリ)
     */
    private MastOrganisationDO selectOrganization(String custId, String compId, String sectionId){
        return iMastOrganisationService.selectOrganisation(custId,compId,sectionId, DateTime.now());
    }




}
