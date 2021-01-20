package jp.smartcompany.job.modules.tmg.tmgnotification;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.*;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsDBBeanUtil;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ErrJsonDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ErrNtfDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationCheckOverhoursListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 休暇・休出申請bean -> 对应旧就业的ps.c01.tmg.PaidHoliday.TmgNotificationBean
 *
 * @author Wang Ziyue
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgNotificationBean {

    public TmgReferList referList = null; // 汎用参照リスト;
    public final IMastGenericDetailService iMastGenericDetailService;
    public final ITmgNotificationService iTmgNotificationService;
    public final ITmgPaidHolidayService iTmgPaidHolidayService;
    public final ITmgMonthlyService iTmgMonthlyService;
    public final ITmgErrmsgService iTmgErrmsgService;
    public final ITmgNotificationCheckService iTmgNotificationCheckService;
    public final ITmgTriggerService iTmgTriggerService;
    private final IHistDesignationService iHistDesignationService;
    private final ITmgNtfAttachedfileService iTmgNtfAttachedfileService;
    private final ITmgNtfactionlogService iTmgNtfactionlogService;
    private final ITmgEmployeesService iTmgEmployeesService;
    private final HttpSession httpSession;
    private final PsDBBeanUtil psDBBeanUtil;

    // アクション
    public static final String ACT_DISPINP_RLIST = "ACT_DispInp_RList";            // 一覧表示(本人)
    public static final String ACT_MAKEAPPLY_RDETAIL = "ACT_MakeApply_RDetail";        // 新規申請表示
    public static final String ACT_MAKEAPPLY_CAPPLY = "ACT_MakeApply_CAppl";          // 新規申請
    public static final String ACT_EDITAPPLY_RDETAIL = "ACT_EditApply_RDetail";        // 取下表示(本人)
    public static final String ACT_EDITPERMAPPLY_RDETAIL = "ACT_EditPermApply_RDetail";    // 取下表示(承認者)
    public static final String ACT_EDITAPPLY_UWITHDRAW = "ACT_EditApply_UWithdraw";      // 申請取下(本人)
    public static final String ACT_EDITPERMAPPLY_UWITHDRAW = "ACT_EditPermApply_UWithdraw";  // 申請取下(承認者)
    public static final String ACT_EDITAPPLY_UDEL = "ACT_EditApply_UDel";           // 申請全取消
    public static final String ACT_DISPPERM_RLIST = "ACT_DispPerm_RList";           // 一覧表示(承認者)
    public static final String ACT_ALTERAPPLY_RDETAIL = "ACT_AlterApply_RDetail";       // 代理申請表示
    public static final String ACT_ALTERAPPLY_CAPPLY = "ACT_AlterApply_CAppl";         // 代理申請
    public static final String ACT_EDITPERM_RDETAIL = "ACT_EditPerm_RDetail";         // 承認却下表示
    public static final String ACT_EDITPERM_UPERMIT = "ACT_EditPerm_UPermit";         // 承認
    public static final String ACT_EDITPERM_UREJECT = "ACT_EditPerm_UReject";         // 申請却下
    public static final String ACT_EDITCANCEL_RDETAIL = "ACT_EditCancel_RDetail";       // 申請解除表示
    public static final String ACT_EDITCANCEL_UCANCEL = "ACT_EditCancel_UCancel";       // 申請解除
    public static final String ACT_DISPDETAIL_RDETAIL = "ACT_DispDetail_RDetail";       // 詳細表示
    public static final String ACT_DISPDETAIL_RDETAILCANCEL = "ACT_DispDetail_RDetailCancel"; // 申請解除(本人)
    public static final String ACT_REMAKEAPPLY_CAPPLY = "ACT_ReMakeApply_CAppl";        // 再申請
    public static final String ACT_ALTERLISTAPPLY_CAPPLY = "ACT_ALTERLISTAPPLY_CAPPLY";    // 一括
    public static final String ACT_IM_REDIRECT = "ACT_IM_REDIRECT";              // イントラマート側へのリダイレクト用JSP
    public static final String ACT_EDITPERM_USENDBACK = "ACT_EditPerm_USendBack";       // 差戻し
    //public static final String ACT_CHANGE_DISASTER          = "ACT_CHANGE_DISASTER";          // 労災申請ステータス変更
    public static final String ACT_UPDATE_ITEM = "ACT_UPDATE_ITEM";              // 承認後更新

    /**
     * 職種/申請リレーションが有効な状態
     */
    public static final String FLG_NTF_RELATION_ENABLE = "1";

    public static final String BEAN_DESC = "TmgNotification"; // LOG出力用ディスクリプタ
    private static final String DATA_SEPARATOR01 = "_";               // データ区切り文字(_)
    // 日付フォーマット
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATETIME_FORMAT = "yyyy/MM/dd hh24:mi";
    public static final String DATE_FORMAT_DISP = "yyyy\"年\"MM\"月\"dd\"日\"";

    // ステータス
    public static final String STATUS_WITHDRAW = "TMG_NTFSTATUS|0";  // 取下
    public static final String STATUS_WAIT = "TMG_NTFSTATUS|2";  // 承認待ち
    public static final String STATUS_REJECT = "TMG_NTFSTATUS|3";  // 差戻
    public static final String STATUS_PERM = "TMG_NTFSTATUS|5";  // 承認済




    /**
     * 申請ステータスマスタ
     * @return
     */
    public List<StutasFlgVo>  getStutas(PsDBBean psDBBean){
        ParamNotificationListDto param=new ParamNotificationListDto();
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());

        param.setSiteId(psDBBean.getSiteId());

        param.setLang(psDBBean.getLanguage());



        param.setToday(TmgUtil.getSysdate());
        param.setTodayD(DateUtil.parse(param.getToday()));


        List<StutasFlgVo> stutasFlgVos = new ArrayList<StutasFlgVo>();
        List<Map<String, Object>> mgdList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFSTATUS, "asc",param));

        for(Map<String, Object> map:mgdList){
            StutasFlgVo stutasFlgVo=new StutasFlgVo();
            stutasFlgVo.setStutasId(String.valueOf(map.get("MGD_CMASTERCODE")));
            stutasFlgVo.setStutasName(String.valueOf(map.get("MGD_CGENERICDETAILDESC")));
            stutasFlgVos.add(stutasFlgVo);
        }
        return stutasFlgVos;
    }

    //履歴データ一覧
    public NotificationDispVo getNotificationList(String statusFlg,String ntfTypeId,String serEmpId,String year,int page,PsDBBean psDBBean,String filePath) throws Exception {
        ParamNotificationListDto param=new ParamNotificationListDto();
        //基本信息
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());
        param.setSiteId(psDBBean.getSiteId());
        param.setLang(psDBBean.getLanguage());
        param.setPage(page);

        //初始基准日取得
        param.setToday(TmgUtil.getSysdate());

        param.setYear(param.getToday().substring(0, 4));

        param.setGsStartDate(iMastGenericDetailService.selectDate(param.getCustId(), param.getCompId(), Integer.parseInt(param.getYear()), param.getToday()).getStartDate());
        //referlist 新规
        if(StrUtil.equals(psDBBean.getSiteId(),(TmgUtil.Cs_SITE_ID_TMG_INP))){
            referList = new TmgReferList(psDBBean, "TmgNotification", param.getGsStartDate(), TmgReferList.TREEVIEW_TYPE_EMP, true);
        }else{
            referList = new TmgReferList(psDBBean, "TmgNotification", param.getGsStartDate(), TmgReferList.TREEVIEW_TYPE_LIST, true);
        }
        if(!StrUtil.hasEmpty(year)){
            param.setYear(year);
        }
        //基准日取得 入力site为系统日期
        if(!StrUtil.hasEmpty(referList.getRecordDate())&&StrUtil.equals(param.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_INP)){
            param.setToday(referList.getRecordDate());
        }else{
            param.setToday(TmgUtil.getSysdate());
        }
        param.setTodayD(DateUtil.parse(param.getToday()));
        //アクション
        //param.setAction(psDBBean.getReqParam("txtAction"));

        //getParam(0,Integer.valueOf(year),param,psDBBean);

        // 検索条件・申請内
        param.setType(ntfTypeId);
        param.setSearchEmp(serEmpId);
        // 申請一覧（本人）用検索パラメータを取得するメソッド(返すものは常に承認済・取下・却下(選択不可))
        if (StrUtil.equals(param.getAction() ,ACT_DISPINP_RLIST) && statusFlg == null) {
            statusFlg = STATUS_WAIT;
        } else if (statusFlg == null) {
            statusFlg = STATUS_WAIT;
        }
        //申请状态取得
        param.setStatus(statusFlg);
        //申请typesql取得
        param.setMgdSql(buildSQLForSelectGenericDetail("TMG_NTFTYPE", null, "MGD_CMASTERCODE", param));
        //默认page
        if(StrUtil.hasEmpty(String.valueOf(param.getPage()))){
            param.setPage(1);
        }
        if(!StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_INP)){
            param.setEmployeeListSql(referList.buildSQLForSelectEmployees());
        }
        //if(!isNtfTermUseCond(psDBBean)){
        param.setIsNtfTermUseCond(1);
        //}
        //数据取得
        List<NotificationListVo> notificationListVoList = iTmgNotificationService.selectNotificationList(param);
        //数据处理
        List<NotificationDispDetailVo> dispVo=new ArrayList<NotificationDispDetailVo>();
        for(NotificationListVo nlVo:notificationListVoList){
            NotificationDispDetailVo nddVo = new NotificationDispDetailVo();
            nddVo.setTntfCemployeeid(nlVo.getTntfCemployeeid());
            nddVo.setTntfCemployeeidName(nlVo.getTntfCemployeeidName());
            nddVo.setTntfCtype(nlVo.getTntfCtype());
            nddVo.setTntfDbegin(nlVo.getTntfDbegin());
            nddVo.setTntfDend(nlVo.getTntfDend());
            nddVo.setTntfDcancel(nlVo.getTntfDcancel());
            nddVo.setTntfDnotification(nlVo.getTntfDnotification());
            nddVo.setTntfDmodifieddate(nlVo.getTntfDmodifieddate());
            nddVo.setTntfCstatusflg(nlVo.getTntfCstatusflg());
            nddVo.setTntfCalteremployeeid(nlVo.getTntfCalteremployeeid());
            nddVo.setTntfCalteremployeeidName(nlVo.getTntfCalteremployeeidName());
            nddVo.setTntfCntfNo(nlVo.getTntfCntfNo());
            nddVo.setTntfDcancel2(nlVo.getTntfDcancel2());
            nddVo.setRemakeApply(nlVo.getRemakeApply());
            nddVo.setTntfCtypeChar5(nlVo.getTntfCtypeChar5());
            nddVo.setTntfCtypeCode(nlVo.getTntfCtypeCode());
//            nddVo.setCApprovalLevel(nlVo.getCapprovalLevel());
            if(!StrUtil.hasEmpty(nlVo.getNtfapprover()) && nlVo.getNtfapprover().indexOf(",")>-1){
                nddVo.setNtfapprover(nlVo.getNtfapprover().split(","));
            }else if(!StrUtil.hasEmpty(nlVo.getNtfapprover())){
                String[] str = {nlVo.getNtfapprover()};
                nddVo.setNtfapprover(str);
            }
            nddVo.setAllCancellation(nlVo.getAllCancellation());
            nddVo.setTntfNtimezoneOpen(nlVo.getTntfNtimezoneOpen());
            nddVo.setTntfNtimezoneClose(nlVo.getTntfNtimezoneClose());
            nddVo.setDayOfWeek(nlVo.getDayOfWeek());
            nddVo.setFinalApprovelLevel(nlVo.getFinalApprovelLevel());
            nddVo.setTntfCowncomment(nlVo.getTntfCowncomment());
//            //数据取消文本处理
//            if(nddVo.getTntfCstatusflg().equals(STATUS_WITHDRAW)||nddVo.getTntfCstatusflg().equals(STATUS_REJECT)){
//
//            }else if(nddVo.getTntfDbegin().equals(nddVo.getTntfDend())){
//                if(!StrUtil.hasEmpty(nddVo.getTntfDcancel2())&&nddVo.getTntfDcancel2().equals(nddVo.getTntfDbegin())){
//                    nddVo.setTntfCstatusflg(STATUS_REJECT);//全取消
//                }
//            }else{
//                if(!StrUtil.hasEmpty(nddVo.getTntfDcancel()) && DateUtil.parse(nddVo.getTntfDbegin())
//                        .after(DateUtil.parse(nddVo.getTntfDcancel()))){
//                    nddVo.setTntfCstatusflg(STATUS_REJECT);//全取消
//                }else if(!StrUtil.hasEmpty(nddVo.getTntfDcancel()) && DateUtil.parse(nddVo.getTntfDend())
//                        .after(DateUtil.parse(nddVo.getTntfDcancel()))){
//                    nddVo.setTntfDend(nddVo.getTntfDcancel());//部分取消
//                }
//            }
            if(!StrUtil.hasEmpty(nlVo.getTntfCntfNo())&&param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)){
                param.setNtfNo(nlVo.getTntfCntfNo());
                param.setTypeNew(nlVo.getTntfCtypeCode());
                //详细数据取得
                NotificationDetailVo notificationDetailVo = iTmgNotificationService.selectNotificationDetail(param);
                // 承認者コメント
                nddVo.setTntfCbosscomment(notificationDetailVo.getTntfCbosscomment());
                // 承認日
                nddVo.setTntfDboss(notificationDetailVo.getTntfDboss());
                //  解除者コメント
                nddVo.setTntfCcancelcomment(notificationDetailVo.getTntfCcancelcomment());
                nddVo.setFilePath(filePath+"//"+notificationDetailVo.getTntfCntfno()+"//");
                // 4 申請区分略称を取得
                nddVo.setNtfName(iTmgNotificationService.selectNtfName(param.getCustId(), param.getCompId(), param.getNtfNo()));
                // 5 添付ファイル
                nddVo.setTmgNtfAttachedfileDoList(iTmgNtfAttachedfileService.selectFileDisp(param.getCustId(), param.getCompId(), param.getNtfNo()));
                // 7 申請ログ
                nddVo.setTmgNtfactionlogDOList(iTmgNtfactionlogService.selectNtfActionLog(param.getTodayD(), param.getLang(), param.getCustId(), param.getCompId(), param.getNtfNo()));
                //振替
                nddVo.setSubstituted(isSubstituted(param));
            }

            dispVo.add(nddVo);

        }
        NotificationDispVo notificationDispVo= new NotificationDispVo();
        notificationDispVo.setPageSize(50);
        notificationDispVo.setTotalCount(iTmgNotificationService.selectNotificationListCount(param));
        notificationDispVo.setTotalPage(notificationDispVo.getTotalCount()/50+1);
        notificationDispVo.setCurrPage(page);
        notificationDispVo.setList(dispVo);
        return notificationDispVo;
    }


    /**
     * 休暇休業承認の権限判定で代理申請の使用可否を決める
     * @param psDBBean
     * @return
     * @throws Exception
     */
    public boolean hasAuthority(PsDBBean psDBBean) throws Exception {
        String today = TmgUtil.getSysdate();
        String year=today.substring(0, 4);
        String startDate=iMastGenericDetailService.selectDate(psDBBean.getCustID(),psDBBean.getCompCode(), Integer.parseInt(year), today).getStartDate();

        //referlist 新规
        referList = new TmgReferList(psDBBean, "TmgNotification", startDate, TmgReferList.TREEVIEW_TYPE_LIST, true);
        return referList.hasAuthority(referList.getRecordDate(), referList.getRecordDate(), TmgUtil.Cs_AUTHORITY_NOTIFICATION);
    }



    //再申請/详细用　list
    public NotificationDetailVo getNotificationDetail(String ntfNo ,PsDBBean psDBBean){
        ParamNotificationListDto param=new ParamNotificationListDto();
        //基本信息
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setLang(psDBBean.getLanguage());
        param.setNtfNo(ntfNo);
        //基准日取得 入力site为系统日期
        param.setToday(TmgUtil.getSysdate());
        param.setTodayD(DateUtil.parse(param.getToday()));
        //详细数据取得

        NotificationDetailVo notificationDetailVo = iTmgNotificationService.selectNotificationDetail(param);
        // 4 申請区分略称を取得
        notificationDetailVo.setNtfName(iTmgNotificationService.selectNtfName(param.getCustId(), param.getCompId(), param.getNtfNo()));
        // 5 添付ファイル
        notificationDetailVo.setTmgNtfAttachedfileDoList(iTmgNtfAttachedfileService.selectFileDisp(param.getCustId(), param.getCompId(), param.getNtfNo()));
        // 7 申請ログ
        notificationDetailVo.setTmgNtfactionlogDOList(iTmgNtfactionlogService.selectNtfActionLog(param.getTodayD(), param.getLang(), param.getCustId(), param.getCompId(), param.getNtfNo()));


        //遡り期限
        String selectBackLimit = iTmgNotificationService.selectBackLimit(param.getCustId(), param.getCompId(), psDBBean.getUserCode());
        notificationDetailVo.setFixed(isFixed(notificationDetailVo.getTntfCstatusflg(),notificationDetailVo.getTntfDbegin(),notificationDetailVo.getTntfDend(),
                notificationDetailVo.getTntfDcancel(), selectBackLimit));

        notificationDetailVo.setCheckApprovelLevel(hasCheckApprovelLevel(TmgUtil.getSysdate(),TmgUtil.getSysdate(),notificationDetailVo.getTntfCemployeeid()
                ,notificationDetailVo.getCapprovalLevel(),psDBBean.getSiteId()));

        Boolean match =false;
        if (psDBBean.getUserCode() == null || notificationDetailVo.getTntfCalteremployeeid() == null) {
            match=false;
        }else if(psDBBean.getUserCode().equals(notificationDetailVo.getTntfCalteremployeeid())){
            match=true;
        }

        notificationDetailVo.setMatchUserId(match);
        TypeChildrenVo tc =new TypeChildrenVo();
        String viewflg="";
        int viewType=0;
        viewType = Integer.valueOf(notificationDetailVo.getTntfCtypeN2());
        for(int i=0;i<14;i++){
            viewflg  += (viewType%2);
            viewType = viewType/2;
        }
        if (viewflg.length()==14){
            byte[] bytes;
            bytes=viewflg.getBytes();
            if (bytes[0]=='1'){ tc.setTransfer(true); }//振替先・元
            if (bytes[1]=='1'){ tc.setTimeZone(true); }//時間帯
            if (bytes[2]=='1'){ tc.setWorkTime(true); }//始業・終業
            if (bytes[3]=='1'){ tc.setSickName(true); }//傷病名
            if (bytes[4]=='1'){ tc.setSickApply(true); }//労災申請有無
            if (bytes[5]=='1'){ tc.setPeriod(true); }//起算日
            if (bytes[6]=='1'){ tc.setAddDate(true); }//加算日数
            if (bytes[7]=='1'){ tc.setLabel(true); }//勤務時間ラベル
            if (bytes[8]=='1'){ tc.setRestTime(true); }//休憩時間
            if (bytes[9]=='1'){ tc.setName(true); }//氏名
            if (bytes[10]=='1'){ tc.setRelation(true); }//続柄
            if (bytes[11]=='1'){ tc.setBirthday(true); }//生年月日
            if (bytes[12]=='1'){ tc.setDaysOfWeek(true); }//曜日
            if (bytes[13]=='1'){ tc.setTargetNumber(true); }//対象者の人数
        }

        notificationDetailVo.setNtfTypeValue(tc);

        return notificationDetailVo;
    }


    /**
     * 確定済かどうか
     *
     * @param  sNtfStatus 申請ステータス
     * @param  sBegin     開始日
     * @param  sEnd       終了日
     * @param  sCancel    申請解除日
     * @param  sBackLimit 遡り期限
     * @return boolean    true:確定済 / false:未確定
     */
    public boolean isFixed( String sNtfStatus, String sBegin, String sEnd, String sCancel, String sBackLimit ) {

        // 承認待ち
        if ( sNtfStatus.equals( TmgUtil.Cs_MGD_NTFSTATUS_2 ) ) {
            return ( sBegin.compareTo(sBackLimit) < 0 ) ;
        }
        // 承認済
        else if ( sNtfStatus.equals( TmgUtil.Cs_MGD_NTFSTATUS_5 ) ) {

            if ( sCancel == null || sCancel.equals("")) {
                return ( sEnd.compareTo(sBackLimit) < 0 ) ;
            } else {
                return ( sCancel.compareTo(sBackLimit) < 0 ) ;
            }

            /* 却下した申請の修正を可能にする */
            // 却下
        } else if (sNtfStatus.equals( TmgUtil.Cs_MGD_NTFSTATUS_3 ) ) {
            return false;

            // 取下げ
        } else if (sNtfStatus.equals( TmgUtil.Cs_MGD_NTFSTATUS_0 )) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 申請区分マスタ　全て　新規画面用
     * @param psDBBean
     * @return
     */
    public List<TypeGroupVo> getMgdNtfTypeList(PsDBBean psDBBean){
        String date=psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)? TmgUtil.getSysdate(): this.referList.getRecordDate();
        String workType = iTmgEmployeesService.selectWorkerType(psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getTargetUser(),DateUtil.parse(date));


        List<MgdTmgNtfTypeVo> MgdTmgNtfTypeVos = iMastGenericDetailService.selectMasterTmgNtfType(psDBBean.getCustID(),
                psDBBean.getCompCode(),date, psDBBean.getTargetUser(), psDBBean.getLanguage(), psDBBean.getSiteId(),workType);

        List<TypeGroupVo> typeGroupVoList=new ArrayList<TypeGroupVo>();
        //显示type处理
        int viewType;
        String viewflg = null;
        for(MgdTmgNtfTypeVo vo: MgdTmgNtfTypeVos){
            TypeGroupVo typeGroupVo=new TypeGroupVo();
            typeGroupVo.setGroupId(vo.getGMgdCmastercode());// 0 グループの区分
            typeGroupVo.setGroupName(vo.getGMgdCgenericdetaildesc());// 1 グループの名称
            typeGroupVoList.add(typeGroupVo);
        }

        typeGroupVoList = CollUtil.distinct(typeGroupVoList);
        for(MgdTmgNtfTypeVo voChild: MgdTmgNtfTypeVos){
            for(TypeGroupVo voGroup:typeGroupVoList){
                if(voChild.getGMgdCmastercode().equals(voGroup.getGroupId())){
                    TypeChildrenVo tc =new TypeChildrenVo();
                    tc.setNtfId(voChild.getT1MgdCmastercode());
                    tc.setNtfName(voChild.getT1MgdCgenericdetaildesc());
                    tc.setViewType(voChild.getT1MgdNsparenum2());
                    tc.setConfirmComment(voChild.getT1MgdCsparechar3());
                    tc.setBiko(voChild.getT2MgdCsparechar3());
                    tc.setConfirmFile(voChild.getT2MgdNsparenum2());
                    tc.setSparechar2(voChild.getSparechar2());
                    viewflg="";
                    viewType=0;
                    if(!StrUtil.hasEmpty(voChild.getT1MgdNsparenum2())){
                        viewType = Integer.valueOf(voChild.getT1MgdNsparenum2());
                        for(int i=0;i<14;i++){
                            viewflg  += (viewType%2);
                            viewType = viewType/2;
                        }
                        if (viewflg.length()==14){
                            byte[] bytes;
                            bytes=viewflg.getBytes();
                            if (bytes[0]=='1'){ tc.setTransfer(true); }//振替先・元
                            if (bytes[1]=='1'){ tc.setTimeZone(true); }//時間帯
                            if (bytes[2]=='1'){ tc.setWorkTime(true); }//始業・終業
                            if (bytes[3]=='1'){ tc.setSickName(true); }//傷病名
                            if (bytes[4]=='1'){ tc.setSickApply(true); }//労災申請有無
                            if (bytes[5]=='1'){ tc.setPeriod(true); }//起算日
                            if (bytes[6]=='1'){ tc.setAddDate(true); }//加算日数
                            if (bytes[7]=='1'){ tc.setLabel(true); }//勤務時間ラベル
                            if (bytes[8]=='1'){ tc.setRestTime(true); }//休憩時間
                            if (bytes[9]=='1'){ tc.setName(true); }//氏名
                            if (bytes[10]=='1'){ tc.setRelation(true); }//続柄
                            if (bytes[11]=='1'){ tc.setBirthday(true); }//生年月日
                            if (bytes[12]=='1'){ tc.setDaysOfWeek(true); }//曜日
                            if (bytes[13]=='1'){ tc.setTargetNumber(true); }//対象者の人数
                        }
                    }
                    voGroup.getNtfTypeValue().add(tc);
                }
            }
        }
        return typeGroupVoList;
    }

    /**
     * 申請区分マスタ　全て　一覧画面用
     * @param psDBBean
     * @return
     */
    public List<TypeGroupVo> getMgdNtfTypeDispAppList(PsDBBean psDBBean){
        List<MgdNtfTypeDispAppVo> mgdNtfTypeDispAppVoList = iMastGenericDetailService.selectMasterTmgNtfTypeDispAppList(psDBBean.getCustID(),
                psDBBean.getCompCode(), DateTime.now(), psDBBean.getLanguage());
        List<TypeGroupVo> typeGroupVoList=new ArrayList<TypeGroupVo>();
        for(MgdNtfTypeDispAppVo vo:mgdNtfTypeDispAppVoList){
            TypeGroupVo typeGroupVo=new TypeGroupVo();
            typeGroupVo.setGroupId(vo.getGroupId());// 0 グループの区分
            typeGroupVo.setGroupName(vo.getGroupName());// 1 グループの名称
            typeGroupVoList.add(typeGroupVo);
        }
        typeGroupVoList = CollUtil.distinct(typeGroupVoList);
        List<TypeChildrenVo> typeChildrenVos = new ArrayList<TypeChildrenVo>();
        for(MgdNtfTypeDispAppVo voChild:mgdNtfTypeDispAppVoList){
            for(TypeGroupVo voGroup:typeGroupVoList){
                if(voChild.getGroupId().equals(voGroup.getGroupId())){
                    TypeChildrenVo tc =new TypeChildrenVo();
                    tc.setNtfId(voChild.getNtfId());/**2 申請区分*/
                    tc.setNtfName(voChild.getNtfName()); /**3 申請区分名称*/
                    //tc.setViewType(voChild.getViewType());/**4 表示項目タイプ*/
                    //tc.setConfirmComment(voChild.getConfirmComment());/**5 申請事由必須有無*/
                    //tc.setBiko(voChild.getBiko());/**6 注釈*/
                    //tc.setConfirmFile(voChild.getConfirmFile());/**7 添付ファイル必須有無*/
                    voGroup.getNtfTypeValue().add(tc);
                }
            }
        }
        return typeGroupVoList;
    }


    /**
     * 职员列表
     * @param psDBBean
     * @return
     * @throws Exception
     */
    public List<EmployeeListVo> getEmployeeList(PsDBBean psDBBean) throws Exception {
        //初始基准日取得
//        String startDate = iMastGenericDetailService.selectDate(psDBBean.getCustID(), psDBBean.getCompCode(), Integer.parseInt(TmgUtil.getSysdate().substring(0, 4)), TmgUtil.getSysdate()).getStartDate();
        //referlist 新规
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_INP)){
            referList = new TmgReferList(psDBBean, "TmgNotification", psDBBean.getReqParam(TmgReferList.TREEVIEW_KEY_RECORD_DATE), TmgReferList.TREEVIEW_TYPE_EMP, true,
                    true, false, true, false);;
        }else{
            referList = new TmgReferList(psDBBean, "TmgNotification", psDBBean.getReqParam(TmgReferList.TREEVIEW_KEY_RECORD_DATE), TmgReferList.TREEVIEW_TYPE_LIST, true,
                    true, false, true, false);
        }
        String basedate=referList.getRecordDate();
        if(StrUtil.isBlank(basedate)){
            basedate=TmgUtil.getSysdate();
        }
        return  iHistDesignationService
                .selectemployeeList(psDBBean.getCustID(),psDBBean.getCompCode(),basedate,referList.buildSQLForSelectEmployees());
    }
    /**
     * 年次休暇残日数及び時間
     * @param psDBBean
     * @return
     */
    public List<RestYearVo> getRestYear(PsDBBean psDBBean){
        ParamNotificationListDto param=new ParamNotificationListDto();
        param.setCustId(psDBBean.getCustID());
        param.setCompId(psDBBean.getCompCode());
        //无

        param.setNtfNo(null);
        //当前登陆用
        param.setTargetUser(psDBBean.getTargetUser());
        //今日の日付
        param.setTodayD(DateTime.now());
        List<RestYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
        List<RestYearVo> restYearVoList = new ArrayList<RestYearVo>();
        for(RestYearPaidHolidayVo ryphVo:restYearPaidHolidayVoList){
            RestYearVo restYearVo=new RestYearVo();
            restYearVo.setTypeName(ryphVo.getCdesc());
            restYearVo.setTypeId(ryphVo.getCtype());
            restYearVoList.add(restYearVo);
        }
        //去重
        restYearVoList = CollUtil.distinct(restYearVoList);
        for(RestYearPaidHolidayVo ryphVo:restYearPaidHolidayVoList){
            for(RestYearVo restYearVo:restYearVoList){
                if(ryphVo.getCtype().equals(restYearVo.getTypeId())){
                    restYearVo.getTimeList().add(TmgUtil.Mintue2HHmi(ryphVo.getNrestHours()));
                    restYearVo.getDayList().add(ryphVo.getNrestDays()+"日");
                    restYearVo.getTimeRange().add(getTxtTImeScope(ryphVo.getDbegin(), ryphVo.getDend()));
                }
            }
        }

        return restYearVoList;
    }


    /**
     * 文言処理
     * @param begin
     * @param end
     * @return
     */
    private String getTxtTImeScope(String begin,String end){
        if(DateUtil.parse(end).before(DateTime.now())){
            return end+"まで";
        }else{
            return begin+"から";
        }
    }

    public EmployeeDetailVo getEmpInfo(PsDBBean psDBbean) throws Exception {
        //年开始日
        String GsStartDate =iMastGenericDetailService.selectDate(psDBbean.getCustID(), psDBbean.getCompCode(), Integer.parseInt(((String)psDBbean.getRequestHash().get(TmgReferList.TREEVIEW_KEY_RECORD_DATE)).substring(0, 4)),TmgUtil.getSysdate()).getStartDate();
        referList = new TmgReferList(psDBbean, "TmgNotification",GsStartDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                false, false, false, false);
        String sApprovalLevelName=referList.getApprovalLevelName(referList.getRecordDate(), referList.getRecordDate(), psDBbean.getTargetUser());
        EmployeeDetailVo employeeDetailVo = iHistDesignationService.selectemployee(psDBbean.getCustID(), psDBbean.getCompCode(), psDBbean.getTargetUser(), psDBbean.getLanguage(), referList.getTargetSec(),referList.getRecordDate());
        if(sApprovalLevelName!=null){
            employeeDetailVo.setSApprovalLevelName(sApprovalLevelName);
        }else{
            employeeDetailVo.setSApprovalLevelName("");
        }

        HttpSession httpSession = ContextUtil.getSession();
        if(psDBbean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION , employeeDetailVo.getSectionid());
            httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION_NAME , employeeDetailVo.getSection());
            httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP, employeeDetailVo.getGroupid());
            httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP, employeeDetailVo.getCemployeeid());
        } else if (psDBbean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION , employeeDetailVo.getSectionid());
            httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION_NAME , employeeDetailVo.getSection());
            httpSession.setAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP, employeeDetailVo.getCemployeeid());
        }

        return employeeDetailVo;
    }


    /**
     * 一括承認
     * @param ntfNoList
     * @param psDBBean
     * @return
     */
    public GlobalResponse bulkPermit(List<String> ntfNoList,PsDBBean psDBBean){
        for(String ntfNo:ntfNoList){

            ParamNotificationListDto param = new ParamNotificationListDto();
            param.setCompId(psDBBean.getCompCode());
            param.setCustId(psDBBean.getCustID());
            param.setAction(ACT_EDITPERM_UPERMIT);
            param.setUserCode(psDBBean.getUserCode());
            param.setNtfNo(ntfNo);
            param.setSiteId(psDBBean.getSiteId());
            try{
                // TMG_ERRMSGテーブルを使用する前に一度きれいに削除する
                int deleteErrMsg = deleteErrMsg(param);
                int deleteNotificationCheck = deleteNotificationnCheck(param);
                permit(ntfNo,psDBBean);
                int insertErrmsg = insertErrMsg(param);
                String selectErrMsg = selectErrCode(param);
                if(!selectErrMsg.equals("0") ){
                    //errMSG
                    ErrNtfDto tnfDto = iTmgNotificationService.getNtfErrMsg(ntfNo);
                    String ntfDate;
                    if(tnfDto.getStartDate().equals(tnfDto.getEndDate())){
                        ntfDate=tnfDto.getStartDate();
                    }else{
                        ntfDate=tnfDto.getStartDate() + '-' + tnfDto.getEndDate();
                    }
                    ErrJsonDto result = JSONUtil.toBean(selectErrMsg, ErrJsonDto.class);
                    return GlobalResponse.ok(tnfDto.getEmpName() + ',' +ntfDate + ','+result.getERRTYPE_10().get(0).getERRMSG());
                }else{
                    int insertTrigger = insertTrigger(param);
                }
            }catch (GlobalException e){
                return GlobalResponse.error(e.getMessage());
            }finally {
                deleteTrigger(param);
                deleteErrMsg(param);
                deleteNotificationnCheck(param);
            }

        }
        return GlobalResponse.ok();
    }




    private int permit(String ntfNo,PsDBBean psDBBean){
        //元データを取り
        QueryWrapper<TmgNotificationDO> queryWrapper = new QueryWrapper<TmgNotificationDO>();
        queryWrapper.eq("TNTF_CCUSTOMERID", psDBBean.getCustID());
        queryWrapper.eq("TNTF_CCOMPANYID", psDBBean.getCompCode());
        queryWrapper.eq("TNTF_CNTFNO", ntfNo);
        TmgNotificationDO tnDo = iTmgNotificationService.getBaseMapper().selectOne(queryWrapper);

        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        tncDo.setTntfCcustomerid(tnDo.getTntfCcustomerid());
        tncDo.setTntfCcompanyid(tnDo.getTntfCcompanyid());
        tncDo.setTntfCemployeeid(tnDo.getTntfCemployeeid());
        tncDo.setTntfDstartdate(tnDo.getTntfDstartdate());
        tncDo.setTntfDenddate(tnDo.getTntfDenddate());

        tncDo.setTntfCmodifieruserid(psDBBean.getUserCode());
        tncDo.setTntfDmodifieddate(DateTime.now());

        tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_EDITPERM_UPERMIT);

        tncDo.setTntfNseq(tnDo.getTntfNseq());
        tncDo.setTntfCntfno(tnDo.getTntfCntfno());

        tncDo.setTntfCstatusflg(STATUS_PERM);

        tncDo.setTntfCalteremployeeid(tnDo.getTntfCalteremployeeid());
        tncDo.setTntfDnotification(tnDo.getTntfDnotification());
        tncDo.setTntfDbegin(tnDo.getTntfDbegin());
        tncDo.setTntfDend(tnDo.getTntfDend());

        tncDo.setTntfDcancel(tnDo.getTntfDcancel());
        tncDo.setTntfDcancelend(tnDo.getTntfDcancelend());

        tncDo.setTntfNtimeOpen(tnDo.getTntfNtimeOpen());
        tncDo.setTntfNtimeClose(tnDo.getTntfNtimeClose());
        tncDo.setTntfNtimezoneOpen(tnDo.getTntfNtimezoneOpen());
        tncDo.setTntfNtimezoneClose(tnDo.getTntfNtimezoneClose());
        tncDo.setTntfNnoreserved(tnDo.getTntfNnoreserved());
        tncDo.setTntfNmon(tnDo.getTntfNmon());
        tncDo.setTntfNtue(tnDo.getTntfNtue());
        tncDo.setTntfNwed(tnDo.getTntfNwed());
        tncDo.setTntfNthu(tnDo.getTntfNthu());
        tncDo.setTntfNfri(tnDo.getTntfNfri());
        tncDo.setTntfNsat(tnDo.getTntfNsat());
        tncDo.setTntfNsun(tnDo.getTntfNsun());
        tncDo.setTntfNdayofweek(tnDo.getTntfNdayofweek());
        tncDo.setTntfCtype(tnDo.getTntfCtype());
        tncDo.setTntfCowncomment(tnDo.getTntfCowncomment());

        tncDo.setTntfCboss(psDBBean.getUserCode());
        tncDo.setTntfDboss(DateTime.now());
        tncDo.setTntfCcancel(tnDo.getTntfCcancel());
        tncDo.setTntfCcancelcomment(tnDo.getTntfCcancelcomment());

        tncDo.setTntfDdaikyu(tnDo.getTntfDdaikyu());
        tncDo.setTntfCsickName(tnDo.getTntfCsickName());
        tncDo.setTntfCdisaster(tnDo.getTntfCdisaster());
        tncDo.setTntfDperiodDate(tnDo.getTntfDperiodDate());
        tncDo.setTntfNuapperAddition(tnDo.getTntfNuapperAddition());
        tncDo.setTntfCntfnoIm(tnDo.getTntfCntfnoIm());
        tncDo.setTntfNrestopen(tnDo.getTntfNrestopen());
        tncDo.setTntfNrestclose(tnDo.getTntfNrestclose());
        tncDo.setTntfCkanjiname(tnDo.getTntfCkanjiname());
        tncDo.setTntfCrelation(tnDo.getTntfCrelation());
        tncDo.setTntfDdateofbirth(tnDo.getTntfDdateofbirth());
        tncDo.setTntfNnumberOfTarget(tnDo.getTntfNnumberOfTarget());
        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());

        tncDo.setTntfCapprovalLevel(tnDo.getTntfCapprovalLevel());

        tncDo.setTntfCsiteid(psDBBean.getSiteId());
        tncDo.setTntfCntfaction(TmgUtil.Cs_MGD_NTFACTION_6);

        return iTmgNotificationCheckService.getBaseMapper().insert(tncDo);

    }
    /**
     * 新規申請・再申請・代理申請
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionMakeApply(PsDBBean psDBBean, ParamNotificationListDto param, MultipartFile[] uploadFiles,
                                          String[]deleteFiles) throws Exception {

        //基本信息
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());
        param.setSiteId(psDBBean.getSiteId());
        param.setLang(psDBBean.getLanguage());

        //申请日期
        param.setToday(TmgUtil.getSysdate());

        //年开始日
        param.setYear(param.getToday().substring(0, 4));
        param.setGsStartDate(iMastGenericDetailService.selectDate(param.getCustId(), param.getCompId(), Integer.parseInt(param.getYear()), param.getToday()).getStartDate());
        //referlist 新规
        if(psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)){
            referList = new TmgReferList(psDBBean, "TmgNotification", param.getGsStartDate(), TmgReferList.TREEVIEW_TYPE_EMP, true,
                    false, false, false, false);;
        }else{
            referList = new TmgReferList(psDBBean, "TmgNotification", param.getGsStartDate(), TmgReferList.TREEVIEW_TYPE_LIST, true,
                    false, false, false, false);
        }

        param.setTodayD(DateUtil.parse(param.getToday()));
        //再申請の場合、元番号を使用する
        if(param.getAction().equals(ACT_ALTERAPPLY_CAPPLY) || param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)){
            // 3 シーケンス採番
            String seq = iTmgNotificationService.selectNotificationSeq();
            param.setSeq(seq);
            param.setNtfNo(param.getTargetUser()+"|"+seq);
        }

        //決裁レベル返却
        param.setApprovalLevel(getLoginApprovelLevel(TmgUtil.getSysdate(),TmgUtil.getSysdate(),psDBBean.getTargetUser(),referList));

        String path = psDBBeanUtil.getSystemProperty("TMG_NOTIFICATION_UPLOADFILE_PATH");
        if (deleteFiles!=null&&param.getAction().equals(ACT_REMAKEAPPLY_CAPPLY)) {
            //file upload
            deleteFiles(param.getNtfNo(),deleteFiles,path);
            //ファイル保存SQL
            deleteNtfAttachdFile(param,deleteFiles);
        }
        //file upload
        if(uploadFiles!=null){
            uploadFiles(param.getNtfNo(),uploadFiles,path);
            //ファイル保存SQL
            insertNtfAttachdFile(param, uploadFiles,path);
        }
        //ntfAction
        if (param.getAction().equals(ACT_ALTERAPPLY_CAPPLY)) {
            // 代理申請
            param.setNtfAction(TmgUtil.Cs_MGD_NTFACTION_3);
        } else if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {
            //新規申請
            param.setTargetUser(param.getUserCode());
            param.setNtfAction(TmgUtil.Cs_MGD_NTFACTION_1);
        }else if (param.getAction().equals(ACT_REMAKEAPPLY_CAPPLY)){
            //再申請
            param.setNtfAction(TmgUtil.Cs_MGD_NTFACTION_2);
        }else if(param.getAction().equals(ACT_EDITPERM_UPERMIT)){
            //承認
            param.setNtfAction(TmgUtil.Cs_MGD_NTFACTION_6);

        }else if(param.getAction().equals(ACT_EDITAPPLY_UDEL)){
            //全取消
            param.setNtfAction(TmgUtil.Cs_MGD_NTFACTION_7);
//            PsSession psSession=(PsSession)httpSession.getAttribute(Constant.PS_SESSION);
//            //全取消のときに、コメントを自動添加する
//            param.setCancelcomment(psSession.getLoginKanjiName()+"("+TmgUtil.getSysdate()+")");
        }
        try{
            // TMG_ERRMSGテーブルを使用する前に一度きれいに削除する
            int deleteErrMsg = deleteErrMsg(param);
            int deleteNotificationCheck = deleteNotificationnCheck(param);

            if (!(param.getAction().equals(ACT_MAKEAPPLY_CAPPLY) || param.getAction().equals(ACT_ALTERAPPLY_CAPPLY))) {
                // 取消　再申請　承認　申請の場合は、申请番号非空
                if(!StrUtil.hasEmpty(param.getNtfNo())){
                    int insertNotificationCheckUpdate = insertNotificationCheckUpdate(param);
                }else{
                    return  GlobalResponse.error("申請番号がありません。");
                }
            }
            if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY) || param.getAction().equals(ACT_ALTERAPPLY_CAPPLY)) {
                // 新規申請の場合は、新規申請用
                int insertNotificationCheckUpdate = insertNotificationCheckNew(param);
            }
            int insertErrmsg = insertErrMsg(param);
            String selectErrMsg = selectErrCode(param);
            if(!selectErrMsg.equals("0")&&!param.getAction().equals(ACT_EDITAPPLY_UDEL) ){

//                ErrNtfDto tnfDto = iTmgNotificationService.getNtfErrMsg(param.getNtfNo());
//                String ntfDate;
//                if(tnfDto.getStartDate().equals(tnfDto.getEndDate())){
//                    ntfDate=tnfDto.getStartDate();
//                }else{
//                    ntfDate=tnfDto.getStartDate() + '-' + tnfDto.getEndDate();
//                }
//                ErrJsonDto result = JSONUtil.toBean(selectErrMsg, ErrJsonDto.class);
                return GlobalResponse.ok(selectErrMsg);
            }else{
                int insertTrigger = insertTrigger(param);
                return GlobalResponse.ok();
            }
        }catch (GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }finally {
            deleteTrigger(param);
            deleteErrMsg(param);
            deleteNotificationnCheck(param);
        }
    }

    private void deleteNtfAttachdFile(ParamNotificationListDto param, String[] deleteFiles) {
        if(deleteFiles.length<1){
            return;
        }
        for(String name:deleteFiles){
            QueryWrapper<TmgNtfAttachedfileDO> tnafDo = new QueryWrapper<TmgNtfAttachedfileDO>();
            tnafDo.eq("TNAF_CCUSTOMERID", param.getCustId());
            tnafDo.eq("TNAF_CCOMPANYID", param.getCompId());
            tnafDo.eq("TNAF_CNTFNO", param.getNtfNo());
            tnafDo.eq("TNAF_CFILENAME", name);
            iTmgNtfAttachedfileService.getBaseMapper().delete(tnafDo);
        }
    }

    private void deleteFiles(String ntfNo, String[] deleteFiles, String path) {
        if (deleteFiles.length > 0) {
            for (int i = 0; i < deleteFiles.length; i++) {
                File file = new File(path+ntfNo.replace('|','-')+"\\"+deleteFiles[i].replace('|','-'));
                // 判断目录或文件是否存在
                if (file.exists()) {  // 不存在返回 false
                    file.delete();
                }
            }
        }
    }


    /**
     * 申請差戻の更新をするSQLを返す
     *
     * @return String SQL文
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionUpdateReject(PsDBBean psDBBean, ParamNotificationListDto param){
        //基本信息
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());
        param.setSiteId(psDBBean.getSiteId());
        param.setLang(psDBBean.getLanguage());

        int updateNotificationReject = updateNotificationReject(param);
        int insertTrigger = insertTrigger(param);
        int deleteTrigger = deleteTrigger(param);

        if(updateNotificationReject==1&&insertTrigger==1&&deleteTrigger==1){
            return GlobalResponse.ok();
        }else{
            return GlobalResponse.error();
        }
    }

    /**
     * 申請差戻の更新をするSQL
     * @param param
     * @return
     */
    private int updateNotificationReject(ParamNotificationListDto param) {
        TmgNotificationDO tmgNotificationDO = new TmgNotificationDO();
        tmgNotificationDO.setTntfCmodifieruserid(param.getUserCode());
        tmgNotificationDO.setTntfDmodifieddate(DateTime.now());
        tmgNotificationDO.setTntfCmodifierprogramid(BEAN_DESC + "_" + param.getAction());
        tmgNotificationDO.setTntfCstatusflg(STATUS_REJECT);
        tmgNotificationDO.setTntfCboss(param.getUserCode());
        tmgNotificationDO.setTntfCbosscomment(param.getBosscomment());
        tmgNotificationDO.setTntfDboss(DateTime.now());
        tmgNotificationDO.setTntfCapprovalLevel("");
        tmgNotificationDO.setTntfCsiteid(param.getSiteId());
        tmgNotificationDO.setTntfCntfaction(TmgUtil.Cs_MGD_NTFACTION_5);

        QueryWrapper<TmgNotificationDO> tnDo = new QueryWrapper<TmgNotificationDO>();
        tnDo.eq("TNTF_CCUSTOMERID", param.getCustId());
        tnDo.eq("TNTF_CCOMPANYID", param.getCompId());
        tnDo.eq("TNTF_CNTFNO", param.getNtfNo());

        return iTmgNotificationService.getBaseMapper().update(tmgNotificationDO, tnDo);
    }

    /**
     * 申請取下の処理をするメソッド
     *
     * @return なし
     * editWithdraw
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse actionEditWithdrop(String action,String ntfNo,PsDBBean psDBBean) throws Exception{
        ParamNotificationListDto param=new ParamNotificationListDto();
        param.setAction(action);
        param.setCustId(psDBBean.getCustID());
        param.setCompId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());
        param.setNtfNo(ntfNo);
        param.setSiteId(psDBBean.getSiteId());
        param.setNtfAction(TmgUtil.Cs_MGD_NTFACTION_4);
        // 申請取下
        int updateNotificationWithdraw = updateNotificationWithdraw(param);
        int insertTrigger = insertTrigger(param);
        int deleteTrigger = deleteTrigger(param);

        if(updateNotificationWithdraw==1&&insertTrigger==1&&deleteTrigger==1){
            return GlobalResponse.ok();
        }else{
            return GlobalResponse.error();
        }
    }

    /**
     * 申請取消時、データ更新処理を行います。
     *
     * @return なし
     * updateApply
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse cancelApply(ParamNotificationListDto param,PsDBBean psDBBean) throws Exception {


        //基本信息
        param.setCompId(psDBBean.getCustID());
        param.setCustId(psDBBean.getCompCode());
        param.setTargetUser(psDBBean.getTargetUser());
        param.setUserCode(psDBBean.getUserCode());
        param.setSiteId(psDBBean.getSiteId());
        param.setLang(psDBBean.getLanguage());

        //申请日期
        param.setToday(TmgUtil.getSysdate());

        //年开始日
        param.setYear(param.getToday().substring(0, 4));
        param.setGsStartDate(iMastGenericDetailService.selectDate(param.getCustId(), param.getCompId(), Integer.parseInt(param.getYear()), param.getToday()).getStartDate());
        //referlist 新规
        if(psDBBean.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)){
            referList = new TmgReferList(psDBBean, "TmgNotification", param.getGsStartDate(), TmgReferList.TREEVIEW_TYPE_EMP, true,
                    false, false, false, false);;
        }else{
            referList = new TmgReferList(psDBBean, "TmgNotification", param.getGsStartDate(), TmgReferList.TREEVIEW_TYPE_LIST, true,
                    false, false, false, false);
        }

        param.setTodayD(DateUtil.parse(param.getToday()));

        // TMG_ERRMSGテーブルにゴミが残っているといつまでも正常に処理されなくなる可能性があるため
        // TMG_ERRMSGテーブルを使用する前に一度きれいに削除する
        int deleteErrMsg = deleteErrMsg(param);
        int deleteNotificationCheck = deleteNotificationnCheck(param);
        int insertNotificationCheckUpdate = insertNotificationCheckUpdate(param);
        int insertErrmsg = insertErrMsg(param);
        int insertTrigger = insertTrigger(param);

        String selectErrMsg = selectErrCode(param);
        if(!selectErrMsg.equals("0")){
            int deleteErrMsgAfter = deleteErrMsg(param);
            return GlobalResponse.error(selectErrMsg);
        }else{
            int deleteTrigger = deleteTrigger(param);
            int deleteErrMsgAfter = deleteErrMsg(param);
            int deleteNotificationCheckAfter = deleteNotificationnCheck(param);
            return GlobalResponse.ok();
        }

        //部分取消 废弃
/*        if (!isPartOfReserveApplication(param)) {

            //requestHash.put(PARAMERTER_KEY_ACTION, ACT_MAKEAPPLY_CAPPLY);
            //TODO
            // setTargetUser(getReqParm("empid"));
            //TODO
            // setUserCode(getReqParm("empid"));
            // 開始日を登録
            //requestHash.put("begin", doCalcForTypeStringOfDate(getReqParm("txtDCancelEnd"), 1, Calendar.DATE));
            // end は部分解除した元レコードの終了日をendとして持っているので設定しない
            deleteErrMsg = deleteErrMsg(param);
            deleteNotificationCheck = deleteNotificationnCheck(param);

            int insertNotificationCheckPartOfReApp = insertNotificationCheckPartOfReApp(tempUserCode,param);
            int insertErrMsgNew = insertErrMsgNew(param);

            insertTrigger = insertTrigger(param);
            selectErrMsg = selectErrCode(param);
            deleteTrigger = deleteTrigger(param);
            deleteErrMsgAfter = deleteErrMsg(param);
            deleteNotificationCheckAfter = deleteNotificationnCheck(param);

        }
        param.setTargetUser(tempTargetUser);
        param.setUserCode(tempUserCode);*/
    }


    /**
     * 承認後更新
     * updateItem
     *
     * @throws
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void actionUpdateItem(ParamNotificationListDto param) {

        int updateNotificationItem = iTmgNotificationService.updateNotificationItem(param);
    }


    /**
     * file upload
     */
    private boolean uploadFiles(String ntfNo,MultipartFile[] uploadFiles,String path) throws IOException{
        if (uploadFiles.length > 0) {
            for (int i = 0; i < uploadFiles.length; i++) {
                MultipartFile uploadFile = uploadFiles[i];
                //根据上传的文件番号，进行分类保存
                ntfNo=ntfNo.replace('|','-');
                File folder = new File(path + ntfNo);

                try {
                    if (!folder.isDirectory()) {
                        folder.mkdirs();
                    }
                    //保存文件
                    uploadFile.transferTo(new File(folder, uploadFile.getOriginalFilename()));
                    //生成上传文件的访问路径
                    //String filePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + "/uploadFile" + format + newName;
                    //list.add(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else if (uploadFiles.length == 0) {
            return false;
        }
        return false;
    }


    /**
     * ファイル保存SQL
     */
    private void insertNtfAttachdFile(ParamNotificationListDto param, MultipartFile[] uploadFiles,String path) throws IOException {

        String seq= iTmgNtfAttachedfileService.selectSeq(param.getCustId(),param.getCompId(),param.getNtfNo());
        if (StrUtil.hasEmpty(seq)){
            seq="1";
        }
        for (int i = 0; i < uploadFiles.length; i++) {
            TmgNtfAttachedfileDO tnafDo = new  TmgNtfAttachedfileDO();
            tnafDo.setTnafCcustomerid(param.getCustId());
            tnafDo.setTnafCcompanyid(param.getCompId());
            tnafDo.setTnafCemployeeid(param.getTargetUser());
            tnafDo.setTnafCntfno(param.getNtfNo());
            tnafDo.setTnafCfilename(uploadFiles[i].getOriginalFilename());
            tnafDo.setTnafNseq(Long.valueOf(seq)+i);
            tnafDo.setTnafFilepath(path+param.getNtfNo().replace('|','-')+"\\"+uploadFiles[i].getOriginalFilename());
            tnafDo.setTnafCmodifieruserid(param.getUserCode());
            tnafDo.setTnafDmodifieddate(DateTime.now());
            iTmgNtfAttachedfileService.getBaseMapper().insert(tnafDo);
        }
    }



    /**
     * ログインユーザー決裁レベル返却
     *
     * @param   psStartDate
     * @param   psEndDate
     * @param   psEmpId
     * @return  int true/false
     */
    public String getLoginApprovelLevel(String psStartDate, String psEndDate, String psEmpId,TmgReferList referList) {
        return String.valueOf(referList.getApprovalLevel(psStartDate, psEndDate, psEmpId));
    }


    /**
     * 申請状態の更新をするSQLを返す
     * <p>
     * buildSQLForUpdateNotificationWithdraw
     *
     * @return String      SQL文
     */
    private int updateNotificationWithdraw(ParamNotificationListDto param) {

        TmgNotificationDO tmgNotificationDO = new TmgNotificationDO();
        tmgNotificationDO.setTntfCmodifieruserid(param.getUserCode());
        tmgNotificationDO.setTntfDmodifieddate(DateTime.now());
        tmgNotificationDO.setTntfCmodifierprogramid(BEAN_DESC + "_" + param.getAction());
        tmgNotificationDO.setTntfCstatusflg(STATUS_WITHDRAW);
        tmgNotificationDO.setTntfCapprovalLevel("");
        tmgNotificationDO.setTntfCsiteid(param.getSiteId());
        tmgNotificationDO.setTntfCntfaction(param.getNtfAction());


        QueryWrapper<TmgNotificationDO> tnDo = new QueryWrapper<TmgNotificationDO>();
        tnDo.eq("TNTF_CCUSTOMERID", param.getCustId());
        tnDo.eq("TNTF_CCOMPANYID", param.getCompId());
        tnDo.eq("TNTF_CNTFNO", param.getNtfNo());

        return iTmgNotificationService.getBaseMapper().update(tmgNotificationDO, tnDo);
    }


    /**
     * エラーメッセージを削除するSQLを返す
     * buildSQLForDeleteNotificationnCheck
     *
     * @return String SQL文
     */
    private int deleteNotificationnCheck(ParamNotificationListDto param) {
        // アクション識別子の取得
        String sAction = param.getAction();
        // 起動時
        if (sAction == null) {
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
                sAction = ACT_DISPINP_RLIST;    // 一覧表示(本人)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            }
        }

        Map errMsg = new HashMap();
        errMsg.put("TNTF_CMODIFIERUSERID", param.getUserCode());
        errMsg.put("TNTF_CMODIFIERPROGRAMID", BEAN_DESC + "_" + sAction);

        return iTmgNotificationCheckService.getBaseMapper().deleteByMap(errMsg);
    }

    /**
     * エラーメッセージを削除するSQLを返す
     * buildSQLForDeleteErrMsg
     *
     * @return String SQL文
     */
    private int deleteErrMsg(ParamNotificationListDto param) {
        // アクション識別子の取得
        String sAction = param.getAction();
        // 起動時
        if (sAction == null) {
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
                sAction = ACT_DISPINP_RLIST;    // 一覧表示(本人)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            }
        }
        Map errMsg = new HashMap();
        errMsg.put("TER_CCUSTOMERID", param.getCustId());
        errMsg.put("TER_CCOMPANYID", param.getCompId());
        errMsg.put("TER_CMODIFIERUSERID", param.getUserCode());
        errMsg.put("TER_CMODIFIERPROGRAMID", BEAN_DESC + "_" + sAction);

        return iTmgErrmsgService.getBaseMapper().deleteByMap(errMsg);
    }


    /**
     * エラーチェックに追加するSQLを返す
     * buildSQLForInsertNotificationCheckNew
     *
     * @return int   SQL文
     */
    private int insertNotificationCheckNew(ParamNotificationListDto param) {
        // 初期化
        boolean bLevelCheckFlg = false;
        boolean bSubstitute = isSubstituted(param); // 申請が振替タイプか判定

        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        //基本情報
        tncDo.setTntfCcustomerid(param.getCustId());
        tncDo.setTntfCcompanyid(param.getCompId());
        tncDo.setTntfCemployeeid(param.getTargetUser());
        tncDo.setTntfDstartdate(TmgUtil.minDate);
        tncDo.setTntfDenddate(TmgUtil.maxDate);
        //申請について
        tncDo.setTntfCmodifieruserid(param.getUserCode());
        tncDo.setTntfDmodifieddate(DateTime.now());
        //action
        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {    // 新規申請
            tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_MAKEAPPLY_CAPPLY);
        } else {    // 代理申請
            tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_ALTERAPPLY_CAPPLY);
        }
        //シーケンス
        tncDo.setTntfNseq(Long.parseLong(param.getSeq()));
        tncDo.setTntfCntfno(param.getTargetUser() + "|" + param.getSeq());


        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {// 新規申請
            tncDo.setTntfCstatusflg(STATUS_WAIT);
        } else {// 代理申請
            // 決裁レベル判定用
            bLevelCheckFlg = hasCheckApprovelLevel(param.getFinalApprovalLevel(), param.getApprovalLevel(), param.getSiteId());
            // 承認済判定 管理サイトからの承認は即承認済に
            if (bLevelCheckFlg) {
                tncDo.setTntfCstatusflg(STATUS_PERM); // 承認済
            } else {
                tncDo.setTntfCstatusflg(STATUS_WAIT);// 承認待ち
            }
        }
        tncDo.setTntfCalteremployeeid(param.getUserCode());
        tncDo.setTntfDnotification(DateTime.now());

        tncDo.setTntfDbegin(param.getBegin());
        // 代休の場合は休出日(開始)を終了日にセットし、代休(終了日)を代休カラムにセットする
        if (bSubstitute) {
            tncDo.setTntfDend(param.getBegin());
        } else {
            tncDo.setTntfDend(param.getEnd());
        }

        //非取り消しの場合
        tncDo.setTntfDcancel(null);

        if (!StrUtil.hasEmpty(param.getTimeOpen())) {
            tncDo.setTntfNtimeOpen(Long.parseLong(param.getTimeOpen()));
        }
        if (!StrUtil.hasEmpty(param.getTimeClose())) {
            tncDo.setTntfNtimeClose(Long.parseLong(param.getTimeClose()));
        }
        if (!StrUtil.hasEmpty(param.getTimezoneOpen())) {
            tncDo.setTntfNtimezoneOpen(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTimezoneOpen()));
        }
        if (!StrUtil.hasEmpty(param.getTimezoneClose())) {
            tncDo.setTntfNtimezoneClose(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTimezoneClose()));
        }


        tncDo.setTntfNnoreserved(Long.parseLong(param.getNoreserved()));
        tncDo.setTntfNmon(Long.parseLong(param.getMon()));
        tncDo.setTntfNtue(Long.parseLong(param.getTue()));
        tncDo.setTntfNwed(Long.parseLong(param.getWed()));
        tncDo.setTntfNthu(Long.parseLong(param.getThu()));
        tncDo.setTntfNfri(Long.parseLong(param.getFri()));
        tncDo.setTntfNsat(Long.parseLong(param.getSat()));
        tncDo.setTntfNsun(Long.parseLong(param.getSun()));
        if (!StrUtil.hasEmpty(param.getNoreserved()) && param.getNoreserved().equals("0")) {
            tncDo.setTntfNdayofweek(iMastGenericDetailService.toDayofWeek(param.getMon(),
                    param.getTue(),
                    param.getWed(),
                    param.getThu(),
                    param.getFri(),
                    param.getSat(),
                    param.getSun()));
        } else {
            tncDo.setTntfNdayofweek(iMastGenericDetailService.toDayofWeek(null, null, null, null, null, null, null));
        }

        tncDo.setTntfCtype(param.getTypeNew());
        tncDo.setTntfCowncomment(param.getOwncomment());

        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {// 新規申請

        } else {// 代理申請
            tncDo.setTntfCboss(param.getUserCode());
            tncDo.setTntfCbosscomment(param.getBosscomment());
            tncDo.setTntfDboss(DateTime.now());
        }

        tncDo.setTntfCcancel(null);
        tncDo.setTntfCcancelcomment(null);

        // 代休の場合は休出日(開始)を終了日にセットし、代休(終了日)を代休カラムにセットする
        if (bSubstitute) {
            tncDo.setTntfDdaikyu(param.getEnd());
        }

        // 傷病名
        if (!StrUtil.hasEmpty(param.getTxtSickName())) {
            tncDo.setTntfCsickName(param.getTxtSickName());
        }
        // 労災申請
        if (!StrUtil.hasEmpty(param.getSickApply())) {
            tncDo.setTntfCdisaster(param.getSickApply());
        }
        //起算日
        if (param.getTxtPeriod() != null) {
            tncDo.setTntfDperiodDate(param.getTxtPeriod());
        }
        //加算日数
        if (!StrUtil.hasEmpty(param.getTxtAddDate())) {
            tncDo.setTntfNuapperAddition(Long.parseLong(param.getTxtAddDate()));
        }


        tncDo.setTntfCntfnoIm(null);
        // 休憩時間From
        if (!StrUtil.hasEmpty(param.getTxtRestOpen())) {
            tncDo.setTntfNrestopen(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTxtRestOpen()));
        }
        // 休憩時間To
        if (!StrUtil.hasEmpty(param.getTxtRestClose())) {
            tncDo.setTntfNrestclose(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTxtRestClose()));
        }
        // 氏名
        if (!StrUtil.hasEmpty(param.getTxtName())) {
            tncDo.setTntfCkanjiname(param.getTxtName());
        }
        // 続柄
        if (!StrUtil.hasEmpty(param.getTxtRelation())) {
            tncDo.setTntfCrelation(param.getTxtRelation());
        }
        // 生年月日
        if (!StrUtil.hasEmpty(param.getTxtAddDate())) {
            tncDo.setTntfDdateofbirth(param.getTxtBirthday());
        }
        // 対象の人数
        if (!StrUtil.hasEmpty(param.getTxtTargetNumber())) {
            tncDo.setTntfNnumberOfTarget(Long.parseLong(StrUtil.nullToDefault(param.getTxtTargetNumber(), "NULL")));
        }
        tncDo.setTntfCntfnoMoto(null);


        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {// 新規申請
            tncDo.setTntfCapprovalLevel(getApprovelLevel(param,1));
        } else {// 代理申請
            // 承認済判定 管理サイトからの承認は即承認済に
            if (bLevelCheckFlg) {
                tncDo.setTntfCapprovalLevel(null);
            } else {
                tncDo.setTntfCapprovalLevel(getApprovelLevel(param,0));
            }
        }

        tncDo.setTntfCsiteid(param.getSiteId());
        tncDo.setTntfCntfaction(param.getNtfAction());

        //更新処理
        return iTmgNotificationCheckService.getBaseMapper().insert(tncDo);
    }


    /**
     * エラーチェックに追加するSQLを返す
     * buildSQLForInsertNotificationCheckUpdate
     *
     * @return int   SQL文
     */
    private int insertNotificationCheckUpdate(ParamNotificationListDto param) {

        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        //元データを取り
        QueryWrapper<TmgNotificationDO> queryWrapper = new QueryWrapper<TmgNotificationDO>();
        queryWrapper.eq("TNTF_CCUSTOMERID", param.getCustId());
        queryWrapper.eq("TNTF_CCOMPANYID", param.getCompId());
        queryWrapper.eq("TNTF_CNTFNO", param.getNtfNo());
        TmgNotificationDO tnDo = iTmgNotificationService.getBaseMapper().selectOne(queryWrapper);
        //更新データ
        // 再申請
        if (param.getAction().equals(ACT_REMAKEAPPLY_CAPPLY)) {
            tncDo = reApplySetDo(tnDo,param);
            //全取消
        } else if (param.getAction().equals(ACT_EDITAPPLY_UDEL)) {
            tncDo = delApplu(tnDo,param);
            // 承認・他
        } else {
            tncDo = applyOrConfirm(tnDo,param);
        }

        // 承認か解除(部分取り消し、全取り消し)か？
        if (!(param.getAction().equals(ACT_EDITPERM_UPERMIT) ||
                param.getAction().equals(ACT_EDITCANCEL_UCANCEL) ||
                param.getAction().equals(ACT_EDITAPPLY_UDEL))) {
            tncDo.setTntfDcancel(null);
            tncDo.setTntfDcancelend(null);
        }
        // 承認・管理からの再申請に対応する為
        if (!(param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN) || param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM))) {
            // 承認か解除であれば、承認者コメント、取消コメントのカラムをINSERTに指定する
            tncDo.setTntfCboss(null);
            tncDo.setTntfCbosscomment(null);
            tncDo.setTntfDboss(null);
            tncDo.setTntfCcancel(null);
            tncDo.setTntfCcancelcomment(null);
        }
        return iTmgNotificationCheckService.getBaseMapper().insert(tncDo);
    }


    /**
     * 再申請
     * update用entity処理
     */
    public TmgNotificationCheckDO reApplySetDo(TmgNotificationDO tnDo, ParamNotificationListDto param) {

        boolean bSubstitute = isSubstituted(param); // 申請が振替タイプか判定

        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        tncDo.setTntfCcustomerid(tnDo.getTntfCcustomerid());
        tncDo.setTntfCcompanyid(tnDo.getTntfCcompanyid());
        tncDo.setTntfCemployeeid(tnDo.getTntfCemployeeid());
        tncDo.setTntfDstartdate(tnDo.getTntfDstartdate());
        tncDo.setTntfDenddate(tnDo.getTntfDenddate());

        tncDo.setTntfCmodifieruserid(param.getUserCode());

        tncDo.setTntfDmodifieddate(DateTime.now());
        tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_REMAKEAPPLY_CAPPLY);

        tncDo.setTntfNseq(tnDo.getTntfNseq());
        tncDo.setTntfCntfno(tnDo.getTntfCntfno());

        // 入力サイトは承認済みにする
        if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
            tncDo.setTntfCstatusflg(STATUS_WAIT);//承認待ち
        } else {
            // レベル判定
            if (Boolean.valueOf(param.getFinalApprovalLevel())) {
                tncDo.setTntfCstatusflg(STATUS_PERM);
            } else {
                tncDo.setTntfCstatusflg(STATUS_WAIT); //承認待ち
            }
        }

        tncDo.setTntfCalteremployeeid(tnDo.getTntfCalteremployeeid());
        tncDo.setTntfDnotification(tnDo.getTntfDnotification());

        tncDo.setTntfDbegin(param.getBegin());


        // 代休の場合は休出日(開始)を終了日にセットし、代休(終了日)を代休カラムにセットする
        if (bSubstitute) {
            tncDo.setTntfDend(param.getBegin());
        } else {
            tncDo.setTntfDend(param.getEnd());
        }

        //tncDo.setTntfDcancel();

        if (!StrUtil.hasEmpty(param.getTimeOpen())) {
            tncDo.setTntfNtimeOpen(Long.parseLong(param.getTimeOpen()));
        }
        if (!StrUtil.hasEmpty(param.getTimeClose())) {
            tncDo.setTntfNtimeOpen(Long.parseLong(param.getTimeClose()));
        }
        if (!StrUtil.hasEmpty(param.getTimezoneOpen())) {
            tncDo.setTntfNtimezoneOpen(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTimezoneOpen()));
        }
        if (!StrUtil.hasEmpty(param.getTimezoneClose())) {
            tncDo.setTntfNtimezoneClose(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTimezoneClose()));
        }

        tncDo.setTntfNnoreserved(Long.parseLong(param.getNoreserved()));
        tncDo.setTntfNmon(Long.parseLong(param.getMon()));
        tncDo.setTntfNtue(Long.parseLong(param.getTue()));
        tncDo.setTntfNwed(Long.parseLong(param.getWed()));
        tncDo.setTntfNthu(Long.parseLong(param.getThu()));
        tncDo.setTntfNfri(Long.parseLong(param.getFri()));
        tncDo.setTntfNsat(Long.parseLong(param.getSat()));
        tncDo.setTntfNsun(Long.parseLong(param.getSun()));
        if (StrUtil.hasEmpty(param.getNoreserved())) {
            tncDo.setTntfNdayofweek(iMastGenericDetailService.toDayofWeek(param.getMon(),
                    param.getTue(),
                    param.getWed(),
                    param.getThu(),
                    param.getFri(),
                    param.getSat(),
                    param.getSun()));
        } else {
            tncDo.setTntfNdayofweek(iMastGenericDetailService.toDayofWeek(null, null, null, null, null, null, null));
        }


        tncDo.setTntfCtype(param.getTypeNew());
        tncDo.setTntfCowncomment(param.getOwncomment());

        // 承認・管理からの再申請に対応する為
        if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN) || param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
            //画面情報
            tncDo.setTntfCboss(param.getUserCode());
            tncDo.setTntfCbosscomment(param.getBosscomment());
            tncDo.setTntfDboss(DateTime.now());
            tncDo.setTntfCcancel(null);
            tncDo.setTntfCcancelcomment(null);
        }
        // 代休の場合は休出日(開始)を終了日にセットし、代休(終了日)を代休カラムにセットする
        if (bSubstitute) {
            tncDo.setTntfDdaikyu(param.getEnd());
        } else {
            tncDo.setTntfDdaikyu(null);
        }

        tncDo.setTntfCsickName(param.getTxtSickName());
        tncDo.setTntfCdisaster(param.getSickApply());
        tncDo.setTntfDperiodDate(param.getTxtPeriod());
        if(!StrUtil.hasEmpty(param.getTxtAddDate())){
            tncDo.setTntfNuapperAddition(Long.parseLong(param.getTxtAddDate()));
        }
        tncDo.setTntfCntfnoIm(param.getNtfNo());// 申請番号

        // 休憩時間From
        if(!StrUtil.hasEmpty(param.getTxtRestOpen())){
            tncDo.setTntfNrestopen(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTxtRestOpen()));
        }
        // 休憩時間To
        if(!StrUtil.hasEmpty(param.getTxtRestClose())){
            tncDo.setTntfNrestclose(iMastGenericDetailService.tmgFConvHhmi2Min(param.getTxtRestClose()));
        }
        // 氏名
        tncDo.setTntfCkanjiname(param.getTxtName());
        // 続柄
        tncDo.setTntfCrelation(param.getTxtRelation());
        // 生年月日
        tncDo.setTntfDdateofbirth(param.getTxtBirthday());
        // 対象の人数
        if(!StrUtil.hasEmpty(param.getTxtTargetNumber())){
            tncDo.setTntfNnumberOfTarget(Long.parseLong(StrUtil.nullToDefault(param.getTxtTargetNumber(), "NULL")));
        }
        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());// 分割前申請番号


        // レベル判定
        if (Boolean.valueOf(param.getFinalApprovalLevel())) {
            tncDo.setTntfCapprovalLevel(null);
        } else {
            // 承認・管理からの再申請に対応する為
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN) || param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                tncDo.setTntfCapprovalLevel(getApprovelLevel(param,0));
            } else {
                tncDo.setTntfCapprovalLevel(getApprovelLevel(param,1));
            }
        }

        tncDo.setTntfCsiteid(param.getSiteId());
        tncDo.setTntfCntfaction(param.getNtfAction());

        return tncDo;
    }

    /**
     * 全取消
     * update用entity処理
     */
    public TmgNotificationCheckDO delApplu(TmgNotificationDO tnDo, ParamNotificationListDto param) {
        //update用entity処理
        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        tncDo.setTntfCcustomerid(tnDo.getTntfCcustomerid());
        tncDo.setTntfCcompanyid(tnDo.getTntfCcompanyid());
        tncDo.setTntfCemployeeid(tnDo.getTntfCemployeeid());
        tncDo.setTntfDstartdate(tnDo.getTntfDstartdate());
        tncDo.setTntfDenddate(tnDo.getTntfDenddate());

        tncDo.setTntfCmodifieruserid(param.getUserCode());
        tncDo.setTntfDmodifieddate(DateTime.now());
        tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_EDITAPPLY_UDEL);

        tncDo.setTntfNseq(tnDo.getTntfNseq());
        tncDo.setTntfCntfno(tnDo.getTntfCntfno());
        tncDo.setTntfCstatusflg(STATUS_PERM);
        tncDo.setTntfCalteremployeeid(tnDo.getTntfCalteremployeeid());
        tncDo.setTntfDnotification(tnDo.getTntfDnotification());
        tncDo.setTntfDbegin(tnDo.getTntfDbegin());
        tncDo.setTntfDend(tnDo.getTntfDend());

        //tncDo.setTntfDcancel(param.getCancel());　仅有全取消
        tncDo.setTntfDcancel(tnDo.getTntfDbegin());
        tncDo.setTntfDcancelend(tnDo.getTntfDend());

        tncDo.setTntfNtimeOpen(tnDo.getTntfNtimeOpen());
        tncDo.setTntfNtimeClose(tnDo.getTntfNtimeClose());
        tncDo.setTntfNtimezoneOpen(tnDo.getTntfNtimezoneOpen());
        tncDo.setTntfNtimezoneClose(tnDo.getTntfNtimezoneClose());
        tncDo.setTntfNnoreserved(tnDo.getTntfNnoreserved());
        tncDo.setTntfNmon(tnDo.getTntfNmon());
        tncDo.setTntfNtue(tnDo.getTntfNtue());
        tncDo.setTntfNwed(tnDo.getTntfNwed());
        tncDo.setTntfNthu(tnDo.getTntfNthu());
        tncDo.setTntfNfri(tnDo.getTntfNfri());
        tncDo.setTntfNsat(tnDo.getTntfNsat());
        tncDo.setTntfNsun(tnDo.getTntfNsun());
        tncDo.setTntfNdayofweek(tnDo.getTntfNdayofweek());
        tncDo.setTntfCtype(tnDo.getTntfCtype());
        tncDo.setTntfCowncomment(tnDo.getTntfCowncomment());
        tncDo.setTntfCboss(tnDo.getTntfCboss());
        tncDo.setTntfCbosscomment(tnDo.getTntfCbosscomment());
        tncDo.setTntfDboss(tnDo.getTntfDboss());

        tncDo.setTntfCcancel(param.getUserCode());
        tncDo.setTntfCcancelcomment(param.getCancelcomment());

        tncDo.setTntfDdaikyu(tnDo.getTntfDdaikyu());
        // 傷病種類は不要の為除去 //
        tncDo.setTntfCsickName(tnDo.getTntfCsickName());
        tncDo.setTntfCdisaster(tnDo.getTntfCdisaster());
        tncDo.setTntfDperiodDate(tnDo.getTntfDperiodDate());
        tncDo.setTntfNuapperAddition(tnDo.getTntfNuapperAddition());
        tncDo.setTntfCntfnoIm(tnDo.getTntfCntfnoIm());
        tncDo.setTntfNrestopen(tnDo.getTntfNrestopen());
        tncDo.setTntfNrestclose(tnDo.getTntfNrestclose());
        tncDo.setTntfCkanjiname(tnDo.getTntfCkanjiname());
        tncDo.setTntfCrelation(tnDo.getTntfCrelation());
        tncDo.setTntfDdateofbirth(tnDo.getTntfDdateofbirth());
        tncDo.setTntfNnumberOfTarget(tnDo.getTntfNnumberOfTarget());
        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());

        tncDo.setTntfCapprovalLevel(null);
        tncDo.setTntfCsiteid(param.getSiteId());
        tncDo.setTntfCntfaction(param.getNtfAction());

        return tncDo;
    }

    /**
     * 承認・他
     * update用entity処理
     */
    public TmgNotificationCheckDO applyOrConfirm(TmgNotificationDO tnDo, ParamNotificationListDto param) {
        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();

        tncDo.setTntfCcustomerid(tnDo.getTntfCcustomerid());
        tncDo.setTntfCcompanyid(tnDo.getTntfCcompanyid());
        tncDo.setTntfCemployeeid(tnDo.getTntfCemployeeid());
        tncDo.setTntfDstartdate(tnDo.getTntfDstartdate());
        tncDo.setTntfDenddate(tnDo.getTntfDenddate());

        tncDo.setTntfCmodifieruserid(param.getUserCode());
        tncDo.setTntfDmodifieddate(DateTime.now());

        if (param.getAction().equals(ACT_EDITCANCEL_UCANCEL)) {    // 差戻し
            tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_EDITCANCEL_UCANCEL);
        } else {    // 承認
            tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_EDITPERM_UPERMIT);
        }

        tncDo.setTntfNseq(tnDo.getTntfNseq());
        tncDo.setTntfCntfno(tnDo.getTntfCntfno());

        //ステータス
        if (param.getAction().equals(ACT_EDITCANCEL_UCANCEL)) { // 解除日=開始日の場合は取消となる
            tncDo.setTntfCstatusflg(tnDo.getTntfCstatusflg());
        } else {
            // レベル判定
            if (hasCheckApprovelLevel(TmgUtil.getSysdate(),TmgUtil.getSysdate(),param.getTargetUser(),
                    param.getApprovalLevel(),param.getSiteId())) {
                tncDo.setTntfCstatusflg(STATUS_PERM);
            } else {
                tncDo.setTntfCstatusflg(tnDo.getTntfCstatusflg());
            }
        }

        tncDo.setTntfCalteremployeeid(tnDo.getTntfCalteremployeeid());
        tncDo.setTntfDnotification(tnDo.getTntfDnotification());
        tncDo.setTntfDbegin(tnDo.getTntfDbegin());
        tncDo.setTntfDend(tnDo.getTntfDend());

        if (param.getAction().equals(ACT_EDITPERM_UPERMIT)) {
            tncDo.setTntfDcancel(tnDo.getTntfDcancel());
            tncDo.setTntfDcancelend(tnDo.getTntfDcancelend());
        }
        if (param.getAction().equals(ACT_EDITCANCEL_UCANCEL)) {
            tncDo.setTntfDcancel(param.getCancel());
            tncDo.setTntfDcancelend(param.getTxtDCancelEnd());
        }

        tncDo.setTntfNtimeOpen(tnDo.getTntfNtimeOpen());
        tncDo.setTntfNtimeClose(tnDo.getTntfNtimeClose());
        tncDo.setTntfNtimezoneOpen(tnDo.getTntfNtimezoneOpen());
        tncDo.setTntfNtimezoneClose(tnDo.getTntfNtimezoneClose());
        tncDo.setTntfNnoreserved(tnDo.getTntfNnoreserved());
        tncDo.setTntfNmon(tnDo.getTntfNmon());
        tncDo.setTntfNtue(tnDo.getTntfNtue());
        tncDo.setTntfNwed(tnDo.getTntfNwed());
        tncDo.setTntfNthu(tnDo.getTntfNthu());
        tncDo.setTntfNfri(tnDo.getTntfNfri());
        tncDo.setTntfNsat(tnDo.getTntfNsat());
        tncDo.setTntfNsun(tnDo.getTntfNsun());
        tncDo.setTntfNdayofweek(tnDo.getTntfNdayofweek());
        tncDo.setTntfCtype(tnDo.getTntfCtype());
        tncDo.setTntfCowncomment(tnDo.getTntfCowncomment());


        if (param.getAction().equals(ACT_EDITPERM_UPERMIT)) {
            tncDo.setTntfCboss(param.getUserCode());
            tncDo.setTntfCbosscomment(param.getBosscomment());
            tncDo.setTntfDboss(DateTime.now());
            tncDo.setTntfCcancel(tnDo.getTntfCcancel());
            tncDo.setTntfCcancelcomment(tnDo.getTntfCcancelcomment());
        }
        if (param.getAction().equals(ACT_EDITCANCEL_UCANCEL)) {
            tncDo.setTntfCboss(tnDo.getTntfCboss());
            tncDo.setTntfCbosscomment(tnDo.getTntfCbosscomment());
            tncDo.setTntfDboss(tnDo.getTntfDboss());
            tncDo.setTntfCcancel(param.getUserCode());
            tncDo.setTntfCcancelcomment(param.getCancelcomment());
        }


        tncDo.setTntfDdaikyu(tnDo.getTntfDdaikyu());
        tncDo.setTntfCsickName(tnDo.getTntfCsickName());
        tncDo.setTntfCdisaster(tnDo.getTntfCdisaster());
        tncDo.setTntfDperiodDate(tnDo.getTntfDperiodDate());
        tncDo.setTntfNuapperAddition(tnDo.getTntfNuapperAddition());
        tncDo.setTntfCntfnoIm(tnDo.getTntfCntfnoIm());
        tncDo.setTntfNrestopen(tnDo.getTntfNrestopen());
        tncDo.setTntfNrestclose(tnDo.getTntfNrestclose());
        tncDo.setTntfCkanjiname(tnDo.getTntfCkanjiname());
        tncDo.setTntfCrelation(tnDo.getTntfCrelation());
        tncDo.setTntfDdateofbirth(tnDo.getTntfDdateofbirth());
        tncDo.setTntfNnumberOfTarget(tnDo.getTntfNnumberOfTarget());
        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());

        if (param.getAction().equals(ACT_EDITPERM_UPERMIT)) {   // 承認
            // レベル判定
            if (Boolean.valueOf(hasCheckApprovelLevel(TmgUtil.getSysdate(),TmgUtil.getSysdate(),tnDo.getTntfCemployeeid(),param.getFinalApprovalLevel(),param.getSiteId()))) {
                tncDo.setTntfCapprovalLevel(null);
            } else {
                tncDo.setTntfCapprovalLevel(getApprovelLevel(param,0));
            }
        } else {
            tncDo.setTntfCapprovalLevel(null);
        }

        tncDo.setTntfCsiteid(param.getSiteId());
        tncDo.setTntfCntfaction(param.getNtfAction());

        return tncDo;
    }

    /**
     * 指定された申請種類の表示タイプを取得する
     * buildSQLForNtfDispType
     *
     * @return int inputCtl
     */
    private int selectNtfDispType(ParamNotificationListDto param) {
        return iMastGenericDetailService.selectNtfDispType(param.getCustId(), param.getCompId(), param.getLang(), param.getTypeNew());
    }

    /**
     * 「振替」タイプの申請か判定を行う。
     *
     * @return boolean 振替タイプの場合、trueを返却、以外の場合、falseを返却
     */
    public boolean isSubstituted(ParamNotificationListDto param) {

        int sDispType = selectNtfDispType(param);

        // 取得した表示タイプを２進数変換
        String sBinaryDispType = Integer.toBinaryString(sDispType);

        // １桁目に１が立っていたら「振替」タイプと判定する。
        if ("1".equals(sBinaryDispType.substring(sBinaryDispType.length() - 1))) {

            // 振替タイプの場合、trueを返却
            return true;
        } else {

            // 振替タイプ以外の場合、falseを返却
            return false;
        }
    }


    /**
     * エラーメッセージテーブルへ登録する
     * buildSQLForInsertErrMsg
     *
     * @return int
     */
    private int insertErrMsg(ParamNotificationListDto param) {

        String sProgramId = BEAN_DESC + "_" + param.getAction();

        TmgErrmsgDO tmgErrmsgDO = new TmgErrmsgDO();
        tmgErrmsgDO.setTerCcustomerid(param.getCustId());
        tmgErrmsgDO.setTerCcompanyid(param.getCompId());
        tmgErrmsgDO.setTerDstartdate(TmgUtil.minDate);
        tmgErrmsgDO.setTerDenddate(TmgUtil.maxDate);
        tmgErrmsgDO.setTerCmodifieruserid(param.getUserCode());
        tmgErrmsgDO.setTerDmodifieddate(DateTime.now());
        tmgErrmsgDO.setTerCmodifierprogramid(sProgramId);
        tmgErrmsgDO.setTerCerrcode(iTmgNotificationCheckService.tmgFCheckNotification(param.getNtfNo(),
                param.getCustId(), param.getCompId(), param.getSiteId()));
        tmgErrmsgDO.setTerClanguage(param.getLang());

        return iTmgErrmsgService.getBaseMapper().insert(tmgErrmsgDO);
    }

    /**
     * エラーメッセージを取得する
     * buildSQLForSelectErrMsg
     *
     * @return String SQL文
     */
    private String selectErrCode(ParamNotificationListDto param) {
        // アクション識別子の取得
        String sAction = param.getAction();
        // 起動時
        if (sAction == null) {
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
                sAction = ACT_DISPINP_RLIST;    // 一覧表示(本人)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            }
        }
        QueryWrapper<TmgErrmsgDO> teDo = new QueryWrapper<TmgErrmsgDO>();
        teDo.eq("TER_CMODIFIERPROGRAMID", BEAN_DESC + "_" + sAction);
        teDo.eq("TER_CMODIFIERUSERID", param.getUserCode());
        return iTmgErrmsgService.getBaseMapper().selectOne(teDo).getTerCerrcode();
    }

    /**
     * トリガーを削除する
     * buildSQLForDeleteTrigger
     *
     * @return String SQL文
     */
    private int deleteTrigger(ParamNotificationListDto param) {
        QueryWrapper<TmgTriggerDO> ttDo = new QueryWrapper<TmgTriggerDO>();
        ttDo.eq("TTR_CMODIFIERPROGRAMID", BEAN_DESC + "_" + param.getAction());
        ttDo.eq("TTR_CMODIFIERUSERID", param.getUserCode());
        return iTmgTriggerService.getBaseMapper().delete(ttDo);
    }

    /**
     * トリガー用テーブルへInsertする
     * buildSQLForInsertTmgTrigger
     *
     * @return String SQL文
     */
    private int insertTrigger(ParamNotificationListDto param) {
        String paramNftNo;
        //アクション識別子の取得
        String sAction = param.getAction();
        // 起動時
        if (sAction == null) {
            if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)) {
                sAction = ACT_DISPINP_RLIST;    // 一覧表示(本人)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            } else if (param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                sAction = ACT_DISPPERM_RLIST;    // 一覧表示(承認者)
            }
        }

        if (isPartOfReserveApplication(param)) {
            paramNftNo = "NULL";
        } else {
            paramNftNo = param.getNtfNo();
        }

        TmgTriggerDO tmgTriggerDO = new TmgTriggerDO();
        tmgTriggerDO.setTtrCcustomerid(param.getCustId());
        tmgTriggerDO.setTtrCcompanyid(param.getCompId());
        tmgTriggerDO.setTtrCemployeeid(param.getTargetUser());
        tmgTriggerDO.setTtrDstartdate(TmgUtil.minDate);
        tmgTriggerDO.setTtrDenddate(TmgUtil.maxDate);

        tmgTriggerDO.setTtrCmodifieruserid(param.getUserCode());
        tmgTriggerDO.setTtrDmodifieddate(DateTime.now());
        tmgTriggerDO.setTtrCmodifierprogramid(BEAN_DESC + DATA_SEPARATOR01 + sAction);
        tmgTriggerDO.setTtrCprogramid(BEAN_DESC + DATA_SEPARATOR01 + sAction);
        tmgTriggerDO.setTtrCparameter1(sAction);
        tmgTriggerDO.setTtrCparameter2(paramNftNo);

        return iTmgTriggerService.getBaseMapper().insert(tmgTriggerDO);
    }

    /**
     * 長期間有効な申請の部分解除か判定し真偽を返します。
     *废弃
     * @return 長期間有効な申請の部分解除であればtrue、それ以外はfalse
     */
    private boolean isPartOfReserveApplication(ParamNotificationListDto param) {
        if (ACT_EDITCANCEL_UCANCEL.equals(param.getAction())
                && param.getTxtDCancelEnd() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * マスタを取得するSQLを返す
     * <p>
     * グループIDが「申請種類」かつ、アクションが「新規申請表示」か「代理申請表示」の場合は、
     * 抽出条件として検索対象職員の職種/申請リレーションを指定する。
     * 「職種/申請リレーション」は職種別に申請可能な「申請区分」を定義したマスタです。
     * </p>
     *
     * @param sGroupID :グループID
     * @param sOrder   :ソート順
     * @param sColum   :取得項目
     * @return String          :SQL
     */
    private String buildSQLForSelectGenericDetail(String sGroupID, String sOrder, String sColum, ParamNotificationListDto param) {

        StringBuffer sSQL = new StringBuffer();
        sSQL.append(" SELECT  ");
        if (sColum == null) {
            sSQL.append("     D1.MGD_CMASTERCODE, ");
            sSQL.append("     D1.MGD_CGENERICDETAILDESC, ");
            sSQL.append("     D1.MGD_NSPARENUM2, ");
            sSQL.append("     D1.MGD_CSPARECHAR3 ");
        } else {
            sSQL.append(sColum);
        }
        sSQL.append(" FROM  ");
        sSQL.append("     MAST_GENERIC_DETAIL D1");
        sSQL.append(" WHERE  ");
        sSQL.append("     D1.MGD_CCUSTOMERID          = '" + param.getCustId() + "'");
        sSQL.append(" AND D1.MGD_CCOMPANYID_CK_FK     = '" + param.getCompId() + "'");
        sSQL.append(" AND D1.MGD_CLANGUAGE_CK         = '" + param.getLang() + "'");
        sSQL.append(" AND D1.MGD_CGENERICGROUPID      = '" + sGroupID + "'");
        sSQL.append(" AND D1.MGD_DSTART_CK           <= TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "') ");
        sSQL.append(" AND D1.MGD_DEND                >= TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "') ");

        String sAction = "";//getReqParm("txtAction");
        if (sGroupID.equals(TmgUtil.Cs_MGD_NTFTYPE) &&
                (ACT_MAKEAPPLY_RDETAIL.equals(sAction) || ACT_ALTERAPPLY_RDETAIL.equals(sAction) || ACT_DISPDETAIL_RDETAIL.equals(sAction)) || ACT_ALTERLISTAPPLY_CAPPLY.equals(sAction)) {
            sSQL.append(" AND MGD_CMASTERCODE IN ( ");
            sSQL.append("         SELECT ");
            sSQL.append("             D2.MGD_CSPARECHAR2 ");
            sSQL.append("         FROM ");
            sSQL.append("             MAST_GENERIC_DETAIL D2");
            sSQL.append("         WHERE ");
            sSQL.append("             D2.MGD_CCUSTOMERID          = '" + param.getCustId() + "'");
            sSQL.append("         AND D2.MGD_CCOMPANYID_CK_FK     = '" + param.getCompId() + "'");
            sSQL.append("         AND D2.MGD_CGENERICGROUPID      = '" + TmgUtil.Cs_MGD_NTF_RELATION + "'");
            sSQL.append("         AND D2.MGD_CLANGUAGE_CK         = '" + param.getLang() + "'");
            sSQL.append("         AND D2.MGD_DSTART_CK           <= TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "') ");
            sSQL.append("         AND D2.MGD_DEND                >= TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "') ");
            sSQL.append("         AND D2.MGD_CSPARECHAR1 = TMG_F_GET_WORKERTYPE( ");
            sSQL.append("                                 '" + param.getTargetUser() + "', TO_DATE('" + param.getToday() + "', '" + DATE_FORMAT + "'), '"
                    + param.getCustId() + "' , '" + param.getCompId() + "'");
            sSQL.append("                             )  "); //-- 対象職員の勤怠職種
            sSQL.append("         AND D2.MGD_NSPARENUM1 = " + FLG_NTF_RELATION_ENABLE); //-- 職種/申請リレーションフラグ
            sSQL.append(" ) ");
        }
        // 現在のサイトで申請可能な休暇を取得する(一覧は除く)
        if (sGroupID.equals(TmgUtil.Cs_MGD_NTFTYPE)
                && sAction != null
                && !sAction.equals(ACT_DISPINP_RLIST)
        ) {
            sSQL.append(" AND instr(D1.MGD_CSPARECHAR5,'" + param.getSiteId() + "') > 0 ");
        }
        if (sOrder != null) {
            sSQL.append(" ORDER BY  ");
            sSQL.append("     D1.MGD_CGENERICDETAILID_CK " + sOrder);
        }

        return new String(sSQL);
    }

    /**
     * マスタを取得するSQLを返す
     * <p>
     * グループIDが「申請種類」かつ、アクションが「新規申請表示」か「代理申請表示」の場合は、
     * 抽出条件として検索対象職員の職種/申請リレーションを指定する。
     * 「職種/申請リレーション」は職種別に申請可能な「申請区分」を定義したマスタです。
     * </p>
     *
     * @param sGroupID :グループID
     * @param sOrder   :ソート順
     * @return String          :SQL
     */
    private String buildSQLForSelectGenericDetail(String sGroupID, String sOrder, ParamNotificationListDto param) {
        StringBuffer sSQL = new StringBuffer();
        sSQL.append(buildSQLForSelectGenericDetail(sGroupID, sOrder, null,param));
        return new String(sSQL);
    }


    /**
     * 決裁レベル取得SQL返却メソッド
     * getApprovelLevelSQL
     *
     * @param piMode 0:次の決裁レベル取得用、1:一番小さい決裁レベル取得用
     * @return String SQL文
     */
    private String getApprovelLevel(ParamNotificationListDto param, int piMode) {

        return iMastGenericDetailService.selectApprovelLevel(param.getCustId(), param.getCompId(), param.getLang(), param.getToday(), param.getApprovalLevel(), piMode);

    }

    /**
     * 決裁レベルチェック
     *
     * @param psFinalApprovalLevel
     * @param psApprovalLevel
     * @return boolean true/false
     */
    public boolean hasCheckApprovelLevel(String psFinalApprovalLevel, String psApprovalLevel, String siteId) {

        if (siteId.equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
            return true;
        }

        if (Integer.valueOf(psFinalApprovalLevel).intValue() <= Integer.valueOf(psApprovalLevel).intValue()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 一覧表示用決裁レベルチェックメソッド
     * @param  psStartDate     開始日,
     * @param  psEndDate       終了日,
     * @param  psEmpId         職員番号,
     * @param  psApprovalLevel 決裁レベル
     * @return boolean         表示可能:true、表示不可能:false
     * @exception
     */
    public boolean hasCheckApprovelLevel(String psStartDate, String psEndDate, String psEmpId, String psApprovalLevel, String siteId){

        // 決裁レベルが未設定の場合は表示
        if (psApprovalLevel == null){
            return true;
        }

        // 管理サイトの場合、無条件に決裁権限があるものとする
        if(siteId.equals(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }

        // 職員の決裁レベルが、申請の決裁レベル以上の場合、決裁権限を有すると判定する
        if (Integer.valueOf(psApprovalLevel).intValue() <= referList.getApprovalLevel(psStartDate, psEndDate, psEmpId)){
            return true;  // 決裁権限がある
        } else {
            return false; // 決裁権限が無い
        }

    }


    /**
     * 権限があるかどうか判定します。
     *
     * @param  sStart  開始日
     * @param  sEnd    終了日
     * @return boolean true:権限有り/false:なし
     */
    public boolean isNotification(String empId,PsDBBean psDBbean, String sStart, String sEnd ) throws Exception {
        //年开始日
        try {
            return referList.hasAuthorityAtEmployee( sStart, sEnd, empId, TmgUtil.Cs_AUTHORITY_NOTIFICATION );
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 新規申請・再申請・代理申請
     */
    @Transactional(rollbackFor = GlobalException.class)
    public List<NotificationCheckOvertimeVo> actionCheckExistOverhours(PsDBBean psDBBean, ParamNotificationListDto param) throws Exception {

        ParamNotificationCheckOverhoursListDto paramCheck = new ParamNotificationCheckOverhoursListDto();

        //基本信息
        paramCheck.setCompId(psDBBean.getCustID());
        paramCheck.setCustId(psDBBean.getCompCode());
        //paramCheck.setTargetUser(psDBBean.getTargetUser());
        paramCheck.setUserCode(psDBBean.getUserCode());

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        paramCheck.setBegin(formatter.format(param.getBegin()));
        paramCheck.setEnd(formatter.format(param.getEnd()));

        paramCheck.setNoreserved(param.getNoreserved());

        paramCheck.setMon(param.getMon());
        paramCheck.setTue(param.getTue());
        paramCheck.setWed(param.getWed());
        paramCheck.setThu(param.getThu());
        paramCheck.setFri(param.getFri());
        paramCheck.setSat(param.getSat());
        paramCheck.setSun(param.getSun());

        //ntfAction
        // 代理申請 新規申請 再申請
        List<NotificationCheckOvertimeVo> notificationListVoList= null;
        if (param.getAction().equals(ACT_ALTERAPPLY_CAPPLY) || param.getAction().equals(ACT_MAKEAPPLY_CAPPLY) || param.getAction().equals(ACT_REMAKEAPPLY_CAPPLY)) {

            notificationListVoList = iTmgNotificationService.selectNotificationCheckList(paramCheck);
        }

        return notificationListVoList;
    }

//
//    /**
//     * 廃棄
//     * 承認一覧画面表示対象となる申請範囲が条件指定かどうかを判定します。
//     * @return boolean 判定結果（true：条件指定、false：条件指定以外（年度毎））
//     */
//    public boolean isNtfTermUseCond(PsDBBean psDBBean) {
//        Boolean bNtfTermCondition = null;
//        // 申請期間指定範囲の表示設定の場合、trueを返す。
//        if (bNtfTermCondition == null) {
//            bNtfTermCondition = "direct".equals(psDBBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_NTF_TERM_CONDITION).toLowerCase());
//        }
//
//        return bNtfTermCondition;
//    }



//    /**
//     * エラーメッセージ(更新)に追加する
//     * buildSQLForInsertErrMsgUpdate
//     *
//     * @return int
//     */
//    private int insertErrMsgUpdate(ParamNotificationListDto param) {
//
//        // 処理モードに合致したプログラムＩＤを設定する
//        String sProgramId = "";
//
//        // 申請解除、または申請取消
//        if (ACT_EDITCANCEL_UCANCEL.equals(param.getAction()) || ACT_EDITAPPLY_UDEL.equals(param.getAction())) {
//            sProgramId = BEAN_DESC + "_" + param.getAction();
//
//            // 再申請
//        } else if (ACT_REMAKEAPPLY_CAPPLY.equals(param.getAction())) {
//            sProgramId = BEAN_DESC + "_" + ACT_REMAKEAPPLY_CAPPLY;
//
//            // 承認
//        } else {
//            sProgramId = BEAN_DESC + "_" + ACT_EDITPERM_UPERMIT;
//        }
//
//        TmgErrmsgDO tmgErrmsgDO = new TmgErrmsgDO();
//        tmgErrmsgDO.setTerCcustomerid(param.getCustId());
//        tmgErrmsgDO.setTerCcompanyid(param.getCompId());
//        tmgErrmsgDO.setTerDstartdate(TmgUtil.minDate);
//        tmgErrmsgDO.setTerDenddate(TmgUtil.maxDate);
//        tmgErrmsgDO.setTerCmodifieruserid(param.getUserCode());
//        tmgErrmsgDO.setTerDmodifieddate(DateTime.now());
//        tmgErrmsgDO.setTerCmodifierprogramid(sProgramId);
//        tmgErrmsgDO.setTerCerrcode(iTmgNotificationCheckService.tmgFCheckNotification(param.getNtfNo(),
//                param.getCustId(), param.getCompId(), param.getSiteId()));
//        tmgErrmsgDO.setTerClanguage(param.getLang());
//        return iTmgErrmsgService.getBaseMapper().insert(tmgErrmsgDO);
//    }

//
//    /**
//     * エラーメッセージ(新規)に追加するS
//     * buildSQLForInsertErrMsgNe
//     *
//     * @return int
//     */
//    private int insertErrMsgNew(ParamNotificationListDto param) {
//
//        // 処理モードに合致したプログラムＩＤを設定する
//        String sProgramId = "";
//
//        if (ACT_MAKEAPPLY_CAPPLY.equals(param.getAction())) {
//            // 本人申請
//            sProgramId = BEAN_DESC + "_" + ACT_MAKEAPPLY_CAPPLY;
//        } else {
//            // 代理申請
//            sProgramId = BEAN_DESC + "_" + ACT_ALTERAPPLY_CAPPLY;
//        }
//
//        TmgErrmsgDO tmgErrmsgDO = new TmgErrmsgDO();
//        tmgErrmsgDO.setTerCcustomerid(param.getCustId());
//        tmgErrmsgDO.setTerCcompanyid(param.getCompId());
//        tmgErrmsgDO.setTerDstartdate(TmgUtil.minDate);
//        tmgErrmsgDO.setTerDenddate(TmgUtil.maxDate);
//        tmgErrmsgDO.setTerCmodifieruserid(param.getUserCode());
//        tmgErrmsgDO.setTerDmodifieddate(DateTime.now());
//        tmgErrmsgDO.setTerCmodifierprogramid(sProgramId);
//
//        QueryWrapper<TmgNotificationCheckDO> tncDo = new QueryWrapper<TmgNotificationCheckDO>();
//        tncDo.eq("TNTF_CMODIFIERUSERID", param.getUserCode());
//        tncDo.eq("TNTF_CMODIFIERPROGRAMID", sProgramId);
//        TmgNotificationCheckDO tmgNotificationCheckDO = iTmgNotificationCheckService.getBaseMapper().selectOne(tncDo);
//
//        tmgErrmsgDO.setTerCerrcode(iTmgNotificationCheckService.tmgFCheckNotification(tmgNotificationCheckDO.getTntfCntfno(),
//                tmgNotificationCheckDO.getTntfCcustomerid(),
//                tmgNotificationCheckDO.getTntfCcompanyid(),
//                param.getSiteId()));
//
//        tmgErrmsgDO.setTerClanguage(param.getLang());
//
//        return iTmgErrmsgService.getBaseMapper().insert(tmgErrmsgDO);
//    }
//    /**
//     * 部分申請解除.エラーチェックに追加するSQLを返す
//     *
//     * @param employeeId 承認者職員番号
//     * @return int       SQL文
//     */
//    private int insertNotificationCheckPartOfReApp(String employeeId, ParamNotificationListDto param) {
//
//        //元データを取り
//        QueryWrapper<TmgNotificationDO> queryWrapper = new QueryWrapper<TmgNotificationDO>();
//        queryWrapper.eq("TNTF_CCUSTOMERID", param.getCustId());
//        queryWrapper.eq("TNTF_CCOMPANYID", param.getCompId());
//        queryWrapper.eq("TNTF_CNTFNO", param.getNtfNo());
//        TmgNotificationDO tnDo = iTmgNotificationService.getBaseMapper().selectOne(queryWrapper);
//
//        int seq = Integer.parseInt(iTmgNotificationService.selectNotificationSeq());
//        TmgNotificationCheckDO tncDo = new TmgNotificationCheckDO();
//
//        tncDo.setTntfCcustomerid(param.getCustId());
//        tncDo.setTntfCcompanyid(param.getCompId());
//        tncDo.setTntfCemployeeid(param.getTargetUser());
//        tncDo.setTntfDstartdate(TmgUtil.minDate);
//        tncDo.setTntfDenddate(TmgUtil.maxDate);
//        tncDo.setTntfCmodifieruserid(param.getUserCode());
//        tncDo.setTntfDmodifieddate(DateTime.now());
//        tncDo.setTntfCmodifierprogramid(BEAN_DESC + "_" + ACT_MAKEAPPLY_CAPPLY);
//        tncDo.setTntfNseq((long) seq);
//        tncDo.setTntfCntfno(param.getUserCode() + "|" + seq);
//        tncDo.setTntfCstatusflg(STATUS_PERM);
//        tncDo.setTntfCalteremployeeid(param.getUserCode());
//        tncDo.setTntfDnotification(DateTime.now());
//        tncDo.setTntfDbegin(param.getBegin());
//        tncDo.setTntfDend(param.getEnd());
//        tncDo.setTntfDcancel(null);
//
//        tncDo.setTntfNtimeOpen(tnDo.getTntfNtimeOpen());
//        tncDo.setTntfNtimeClose(tnDo.getTntfNtimeClose());
//        tncDo.setTntfNtimezoneOpen(tnDo.getTntfNtimezoneOpen());
//        tncDo.setTntfNtimezoneClose(tnDo.getTntfNtimezoneClose());
//        tncDo.setTntfNnoreserved(tnDo.getTntfNnoreserved());
//        tncDo.setTntfNmon(tnDo.getTntfNmon());
//        tncDo.setTntfNtue(tnDo.getTntfNtue());
//        tncDo.setTntfNwed(tnDo.getTntfNwed());
//        tncDo.setTntfNthu(tnDo.getTntfNthu());
//        tncDo.setTntfNfri(tnDo.getTntfNfri());
//        tncDo.setTntfNsat(tnDo.getTntfNsat());
//        tncDo.setTntfNsun(tnDo.getTntfNsun());
//        tncDo.setTntfNdayofweek(tnDo.getTntfNdayofweek());
//        tncDo.setTntfCtype(tnDo.getTntfCtype());
//
//        tncDo.setTntfCowncomment(param.getOwncomment());
//
//        tncDo.setTntfCboss(employeeId);
//
//        tncDo.setTntfCbosscomment(null);
//        tncDo.setTntfDboss(DateTime.now());
//        tncDo.setTntfCcancel(null);
//        tncDo.setTntfCcancelcomment(null);
//        //tncDo.setTntfDdaikyu();
//        tncDo.setTntfCsickName(tnDo.getTntfCsickName());
//        tncDo.setTntfCdisaster(tnDo.getTntfCdisaster());
//        tncDo.setTntfDperiodDate(tnDo.getTntfDperiodDate());
//        tncDo.setTntfNuapperAddition(tnDo.getTntfNuapperAddition());
//        tncDo.setTntfCntfnoIm(tnDo.getTntfCntfnoIm());
//        tncDo.setTntfNrestopen(tnDo.getTntfNrestopen());
//        tncDo.setTntfNrestclose(tnDo.getTntfNrestclose());
//        tncDo.setTntfCkanjiname(tnDo.getTntfCkanjiname());
//        tncDo.setTntfCrelation(tnDo.getTntfCrelation());
//        tncDo.setTntfDdateofbirth(tnDo.getTntfDdateofbirth());
//        tncDo.setTntfNnumberOfTarget(tnDo.getTntfNnumberOfTarget());
//        tncDo.setTntfCntfnoMoto(tnDo.getTntfCntfnoMoto());
//        tncDo.setTntfCapprovalLevel(tnDo.getTntfCapprovalLevel());
//        tncDo.setTntfCsiteid(tnDo.getTntfCsiteid());
//        tncDo.setTntfCntfaction(tnDo.getTntfCntfaction());
//
//        return iTmgNotificationCheckService.getBaseMapper().insert(tncDo);
//    }

//    /**
//     * 新規申請時にチェック処理を行うSQL文を返す
//     * 登録時は、イントラマート側の更新が必要になるが、チェック結果によって更新の可否が分かれるため
//     * 内容は、就業側のチェック処理のみ
//     *
//     * @return Vector    SQL
//     */
//    public String getSQLVecForAjaxNewCheck(ParamNotificationListDto param) throws IOException {
//
//        if (param.getAction().equals(ACT_ALTERAPPLY_CAPPLY)) {
//            // 代理申請
//            param.setTargetUser(param.getSearchEmp());
//        } else if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY)) {
//            param.setTargetUser(param.getUserCode());
//        }
//
//        // TMG_ERRMSGテーブルを使用する前に一度きれいに削除する
//        int deleteErrMsg = deleteErrMsg(param);
//        int deleteNotificationCheck = deleteNotificationnCheck(param);
//
//        if (param.getAction().equals(ACT_REMAKEAPPLY_CAPPLY)) {
//            // 再申請の場合は、再申請用
//            int insertNotificationCheckUpdate = insertNotificationCheckUpdate(param);
//            int insertErrMsgUpdate = insertErrMsgUpdate(param);
//        }
//
//        if (param.getAction().equals(ACT_MAKEAPPLY_CAPPLY) || param.getAction().equals(ACT_ALTERAPPLY_CAPPLY)) {
//            // 新規申請の場合は、新規申請用
//            int insertNotificationCheckUpdate = insertNotificationCheckNew(param);
//            int insertErrMsgNew = insertErrMsgNew(param);
//        }
//
//        int insertErrmsg = insertErrMsg(param);
//
//        String selectErrMsg = selectErrCode(param);
//
//        int deleteErrMsgAfter = deleteErrMsg(param);
//        int deleteNotificationCheckAfter = deleteNotificationnCheck(param);
//
//        return selectErrMsg;
//    }


//    /**
//     * 職員リストにEmpの職員番号があるかをチェックし、なければEmpをクリアする。
//     */
//    private void checkEmp(ParamNotificationListDto param) {
//        if (param.getSearchEmp() == null || param.getSearchEmp().equals("")) {
//            return;
//        }
//        // 検索
//        List<EmployeeListVo> EmployeeListVos = iHistDesignationService.selectemployeeList(param.getCustId(), param.getCompId(), param.getToday(), param.getEmployeeListSql());
//        for (EmployeeListVo vo : EmployeeListVos) {
//            if (param.getSearchEmp().equals(vo.getEmpid())) {
//                return;
//            }
//        }
//        param.setSearchEmp("");
//        //requestHash.remove("search_emp");
//    }

//    /**
//     * 一覧表示(本人)
//     *
//     */
//    public void actionDispInp(ParamNotificationListDto param) {
//
//        //4 今月の月中有給付与の情報
//        List<PaidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCompId(), param.getCustId(), param.getTargetUser());
//
//    }

//    /**
//     * 詳細表示の処理をするメソッド
//     *
//     * @return なし
//     * showDetail
//     */
//    public void actionShowDetail(ParamNotificationListDto param) {
//
//        param.setNtfNo("46402406|12967");
//        if (StrUtil.hasEmpty(param.getNtfNo())) {
//            return;
//        }
//        // 0 一覧
//        NotificationDetailVo notificationDetailVo = iTmgNotificationService.selectNotificationDetail(param);
//
//        // 1 申請区分マスタ
//        //List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVos = iMastGenericDetailService.selectMasterTmgNtfType(param.getCustId(),
//                //param.getCompId(), param.getToday(), param.getTargetUser(), param.getLang(), param.getSiteId());
//        //modelMap.addAttribute("mgdTmgNtfTypeVos", mgdTmgNtfTypeVos);
//        // 2 年次休暇残日数及び時間
//        List<RestYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 1);
//
//        // 3 今月の月中有給付与に関する情報を返すSQL
//        List<PaidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCustId(), param.getCompId(), param.getTargetUser());
//
//        // 4 申請区分略称を取得
//        String ntfName = iTmgNotificationService.selectNtfName(param.getCustId(), param.getCompId(), param.getNtfNo());
//
//        // 5 添付ファイル
//        List<TmgNtfAttachedfileDO> tmgNtfAttachedfileDoList = iTmgNtfAttachedfileService.selectFileDisp(param.getCustId(), param.getCompId(), param.getNtfNo());
//
//        // 6 SYSDATE取得
//        String sysdate = iMastGenericDetailService.selectSysdate();
//
//        // 7 申請ログ
//        List<NtfActionLogVo> tmgNtfactionlogDOList = iTmgNtfactionlogService.selectNtfActionLog(param.getTodayD(), param.getLang(), param.getCustId(), param.getCompId(), param.getNtfNo());
//
//        // 8 労災申請更新アクション
//        // 9 画面項目名称の設定マスタ
//        MgdNtfPropVo mgdNtfPropVo = iMastGenericDetailService.selectMasterNtfProp(param.getCustId(), param.getCompId(), param.getLang());
//
//    }

//    /**
//     * 新規申請表示の処理をするメソッド
//     *
//     * @return なし
//     * showMakeApply
//     */
//    public void actionShowMakeApply(ParamNotificationListDto param) {
//
//        // 0 申請区分マスタ
//        //List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVoS = iMastGenericDetailService.selectMasterTmgNtfType(param.getCustId(),
//                //param.getCompId(), param.getToday(), param.getTargetUser(), param.getLang(), param.getSiteId());
//        //modelMap.addAttribute("mgdTmgNtfTypeVoS", mgdTmgNtfTypeVoS);
//        //1 年次休暇残日数及び時間
//        List<RestYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
//
//        //2 今月の月中有給付与に関する情報を返すSQL
//        List<PaidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCompId(), param.getCustId(), param.getTargetUser());
//
//        // 3 シーケンス採番
//        String seq = iTmgNotificationService.selectNotificationSeq();
//
//        // 4 ヘッダ情報(新規申請用)
//        //employeeDetailVo employeeDetailVo = iHistDesignationService.selectemployeeDetail(param.getCustId(), param.getCompId(), param.getTargetUser(), param.getLang());
//
//        // 5 画面項目名称の設定マスタ
//        MgdNtfPropVo mgdNtfPropVo = iMastGenericDetailService.selectMasterNtfProp(param.getCustId(), param.getCompId(), param.getLang());
//
//    }

//    /**
//     * 代理申請(再申請)表示の処理をするメソッド
//     *
//     * @return なし
//     * showAlterReApply
//     */
//    public void actionShowAlterReApply(ParamNotificationListDto param) {
//
//        // 組織が選択されているか
//        if (referList.getTargetSec() == null) {
//            return;
//        }
//        // 参照できる職員が存在するかチェックする
//        if (param.getTargetUser() == null) {
//            return;
//        }
//        // 0 申請詳細
//        NotificationDetailVo notificationDetailVo = iTmgNotificationService.selectNotificationDetail(param);
//
//        // 1 申請区分マスタ
//        //List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVoS = iMastGenericDetailService.selectMasterTmgNtfType(param.getCustId(),
//                //param.getCompId(), param.getToday(), param.getTargetUser(), param.getLang(), param.getSiteId());
//        //modelMap.addAttribute("mgdTmgNtfTypeVoS", mgdTmgNtfTypeVoS);
//        //2 年次休暇残日数及び時間]]
//        List<RestYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
//
//        // 3 今月の月中有給付与に関する情報を返すSQL
//        List<PaidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCompId(), param.getCustId(), param.getTargetUser());
//
//        // 4 職員情報
//        EmployeeDetailVo employeeDetailVo = iHistDesignationService.selectemployee(param.getCustId(), param.getCompId(), param.getTargetUser(), param.getLang(), referList.getTargetSec());
//
//        // 5 シーケンス採番
//        String seq = iTmgNotificationService.selectNotificationSeq();
//
//        // 6 添付ファイル
//        List<TmgNtfAttachedfileDO> tmgNtfAttachedfileDoList = iTmgNtfAttachedfileService.selectFileDisp(param.getCustId(), param.getCompId(), param.getNtfNo());
//
//        // 7 SYSDATE取得(yyyy/mm/dd)
//        String sysdate = iMastGenericDetailService.selectSysdate();
//
//        // 8 申請ログ
//        List<NtfActionLogVo> tmgNtfactionlogDOList = iTmgNtfactionlogService.selectNtfActionLog(param.getTodayD(), param.getLang(), param.getCustId(), param.getCompId(), param.getNtfNo());
//
//        // 9 画面項目名称の設定マスタ
//        MgdNtfPropVo mgdNtfPropVo = iMastGenericDetailService.selectMasterNtfProp(param.getCustId(), param.getCompId(), param.getLang());
//
//    }
//    /**
//     * 初期処理判定フラグ
//     */
//    private static final int INITIAL_TREATMENT = 0;
//
//    /**
//     * リクエストキー - 再表示ボタン使用判定用
//     */
//    private static final String TREEVIEW_KEY_REFRESH_FLG = "txtTmgReferListTreeViewRefreshFlg";

//
//    /**
//     * 全局参数使用
//     */
    //paramNotificationListDto param = new paramNotificationListDto();

//    /**
//     * 参数设置
//     * setSysControl
//     * setParamSearch
//     * setTargetUser
//     * setSearchNtfTerm
//     * execute部分
//     */
//    private ParamNotificationListDto paramSetting(ParamNotificationListDto param, PsDBBean psDBBean, TmgReferList referList) throws Exception {
//
//        //param.setEmployeeListSql(null);
//        //param.setSiteId(TmgUtil.Cs_SITE_ID_TMG_INP);
//        //
//        // todo
//        //param.setNtfNo(psDBBean.getReqParam("ntfNo"));
//        //param.setNtfNo("46402406|11631");
//        return param;
//    }

//    public CalendarButtonVo getLinkDisp(String siteId,String action ,TmgReferList referList,PsDBBean psDBBean) throws Exception {
//        ParamNotificationListDto param =new ParamNotificationListDto();
//
//
//        //getParam()
//        // 参照権限チェック仕様変更対応
//        // ■初期表示時：
//        //   　選択した組織、(もしくはグループ)の対象年月(デフォルトでは現在日付時点の年月)時点での
//        //   休暇・休出申請コンテンツの参照権限をチェックする。
//        //   参照権限がある場合は、問題なく休暇・休出申請を表示する。
//        //   (決裁レベルをピンポイントで引き当てるたい為に過去日、未来日での参照権限判定は行わない)
//        //   メッセージを画面へ表示する。
//        // ■初期表示以外：
//        //   選択した組織、(もしくはグループ)の対象年月時点での休暇・休出申請コンテンツの参照権限をチェックする。
//        //   権限があれば問題なく休暇・休出申請を表示する。
//        //   権限が無い場合は画面に「参照できる職員が存在しません」(文言変更有り)
//        //   メッセージを画面へ表示する。
//        //   ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
//        //     権限が無いのと同じ扱いとする。
//        // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
//        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(siteId) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(siteId)) {
//            String sAction = action;
//            String sTargetSec = referList.getTargetSec();
//            String sStartDateSysdate = TmgUtil.getSysdate();
//
//            // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
//            // ※勤怠管理サイトの場合、初期表示時でも組織が選択されていない状態なら権限チェックを行わない
//            if ((TmgUtil.Cs_SITE_ID_TMG_PERM.equals(siteId) && (sAction == null || sAction.length() == 0))
//                    || (TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(siteId) && !(sTargetSec == null || sTargetSec.length() == 0) && (sAction == null || sAction.length() == 0))) {
//
//                // 参照権限チェック(現在時点での年度)
//                if (referList.existsAnyone(sStartDateSysdate) && referList.isThereSomeEmployees(sStartDateSysdate)) {
//                    //param.setAuthorityYear(true);
//                    param.setAuthorityNextYear(true);
//                    // 参照権限が無い場合
//                } else {
//                    //param.setAuthorityYear(false);
//                    param.setAuthorityNextYear(false);
//                }
//                // 初期表示時以外
//            } else {
//                // 組織未選択時は権限チェックを行わない
//                if(!(sTargetSec == null || sTargetSec.length() == 0)) {
//                    if((referList.existsAnyone(sStartDateSysdate) && referList.isThereSomeEmployees(sStartDateSysdate))) {
//                        //param.setAuthorityYear(true);
//                        param.setAuthorityNextYear(true);
//                    } else {
//                        //param.setAuthorityYear(false);
//                        param.setAuthorityNextYear(false);
//                    }
//                }
//            }
//        }
//        //カレンダー関連情報を取得するメソッド
//        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//        // 検索
//        // 年度開始・終了日
//        DateDto dateDto = iMastGenericDetailService.selectDate(param.getCustId(), param.getCompId(), Integer.parseInt(param.getYear()), referList.getRecordDate());
//        // 前翌年度有無判定
//        CalendarDto calendarDto = iTmgCalendarService.selectCalendar(param.getCustId(), param.getCompId(), Integer.parseInt(param.getYear()), referList.getRecordDate());
//        try {
//
//            // 年度開始日・終了日
//            param.setGsStartDate(dateDto.getStartDate());
//            param.setGsEndDate(dateDto.getEndDate());
//            // 前翌年度
//            Date dMin = sdf.parse(calendarDto.getMinMonth());
//            Date dMax = sdf.parse(calendarDto.getMaxMonth());
//            Date dStart = sdf.parse(calendarDto.getStartYearDate());
//            Date dEnd = sdf.parse(calendarDto.getEndYearDate());
//
//            if (dMin.before(dStart)) {
//                param.setGbPreviousYear(true);
//            }
//            if (dMax.after(dEnd)) {
//                param.setGbNextYear(true);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        CalendarButtonVo buttonVo = new CalendarButtonVo();
//        if(param.getSiteId().equals(TmgUtil.Cs_SITE_ID_TMG_INP)){
//            buttonVo.setPreviousYear(param.isGbPreviousYear());
//            buttonVo.setNextYear(param.isGbNextYear());
//        }else{
//            buttonVo.setPreviousYear(param.isGbPreviousYear());
//            buttonVo.setNextYear(param.isAuthorityNextYear()&&getCheckToDayFlg(referList,param.getThisYear(),param.getYear()));
//        }
//
//        return buttonVo;
//    }


//    /**
//     * 廃棄
//     * 表示対象期間パラメータを設定する。
//     * @return なし
//     */
//    private void setSearchNtfTerm() {
//        ParamNotificationListDto param=new ParamNotificationListDto();
//        // 検索条件・申請期間（申請期間検索機能が使用できる場合）
//        //todo
//        //if (isNtfTermUseCond()) {
//        if (true) {
//            String sBegin = psDBBean.getReqParam("txtNtfTermBegin");
//            String sEnd = psDBBean.getReqParam("txtNtfTermEnd");
//            /*
//             * 初期表示時、または組織ツリー再検索を行った場合は、
//             * 検索条件として、基準日年度の年度開始日と年度終了日を条件として指定する。
//             * ※初期表示はリクエストパラメータがNULLかどうかで判断する。（開始日、終了日が空は検索条件として認めているので、空（""）では判定しない。）
//             * 　また、再表示ボタン押下時は「getReqParm(TREEVIEW_KEY_REFRESH_FLG)」にtrueが設定（再表示以外は空（""））されるので、リクエストパラメータで再表示処理の判定
//             */
//            if ((sBegin == null && sEnd == null)
//                    || StringUtils.isNotEmpty(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG))) {
//
//                try {
//                    sBegin = param.getGsStartDate();
//                    sEnd = param.getGsEndDate();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            // 申請期間検索条件を設定
//            param.setNtfTermBegin(sBegin);
//            param.setNtfTermEnd(sEnd);
//        }
//
//    }


//    /**
//     * 検索パラメータを取得するメソッド
//     *廃棄
//     * @return なし
//     */
//    private void setParamSearch() {
//        ParamNotificationListDto param=new ParamNotificationListDto();
//        // 検索条件・申請内容
//        String sType = psDBBean.getReqParam("search_type");
//        if (sType == null) {
//            sType = "";
//        }
//        param.setType(sType);
//
//        // 検索条件・申請選択状態
//        String sSearchTypeIdx = psDBBean.getReqParam("searchTypeIdx");
//        if (sSearchTypeIdx == null) {
//            sSearchTypeIdx = "";
//        }
//        param.setSearchTypeIdx(sSearchTypeIdx);
//
//        // 検索条件・状態
//        String sStatus = psDBBean.getReqParam("search_status");
//
//        // 申請一覧（本人）用検索パラメータを取得するメソッド(返すものは常に承認済・取下・却下(選択不可))
//        if (param.getAction() == ACT_DISPINP_RLIST && sStatus == null) {
//            sStatus = STATUS_WAIT;
//        } else if (sStatus == null) {
//            sStatus = STATUS_WAIT;
//        }
//        param.setStatus(sStatus);
//
//        // 検索条件・氏名
//        String sEmp = psDBBean.getReqParam("search_emp");
//        if (sEmp == null) {
//            sEmp = "";
//        }
//        param.setSearchEmp(sEmp);
//
//        // 承認者
//        String sEvaluater = psDBBean.getReqParam("search_evaluater");
//        if (sEvaluater == null) {
//            sEvaluater = "";
//        }
//        param.setEvaluator(sEvaluater);
//
//        // ページ
//        int iPage = 1;
//        String sPage = psDBBean.getReqParam("page");
//        if (sPage != null) {
//            try {
//                iPage = Integer.parseInt(sPage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        param.setPage(iPage);
//
//        String sTargetEmpId = psDBBean.getReqParam("targetEmpId");
//        param.setSTargetEmpId(sTargetEmpId);
//
//    }



//    /**
//     * パラメータを取得するメソッド
//     *廃棄
//     * @param piPBranchingProcess 0:初期処理、1:組織ツリー情報使用処理
//     * @return なし
//     */
//    private ParamNotificationListDto getParam(int piPBranchingProcess, int yearNow, ParamNotificationListDto param,PsDBBean psDBBean) {
//        int year = iMastGenericDetailService.selectYear(param.getCustId(), param.getCompId());
//        // 初期処理
//        if (piPBranchingProcess == INITIAL_TREATMENT) {
//            // 今年度
//            param.setThisYear(String.valueOf(year));
//            // 年度
//            try {
//                param.setYear(String.valueOf(yearNow));
//            } catch (Exception e) {
//                // 取得出来なかったらDBより取得
//                param.setYear(String.valueOf(year));
//            }
//        }
//        // 組織ツリー情報取得後再構築を行う
//        else {
//            // 組織ツリー基準日情報チェック
//            if (referList.getRecordDate() != null) {
//                param.setThisYear(referList.getRecordDate().substring(0, 4));
//                //TODO 再表示ボタン使用判定
//                if (psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG) != null && !"".equals(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG))) {
//                    param.setYear(referList.getRecordDate().substring(0, 4));
//                }
//            }
//        }
//        return param;
//    }
//psEmpId    /**
//     * 基準日が過去日付か判定して値を返却
//     *
//     * @return boolean
//     */
//    public boolean getCheckToDayFlg(TmgReferList referList,String thisyear,String year){
//        if (referList != null) {
//            if (referList.isCheckToDayFlg() == false){
//                if (thisyear.equals(year)){
//                    return referList.isCheckToDayFlg();
//                }
//            }
//        }
//        return true;
//    }


//    /**
//     * 申請区分略称を取得するSQLを返す
//     *
//     * @return String
//     */
//    private String selectNtfName(ParamNotificationListDto param) {
//        return iTmgNotificationService.selectNtfName(param.getCustId(), param.getCompId(), param.getNtfNo());
//    }

//    /**
//     * 指定された日付、計算値をjava.util.Calendarで算出し文字列で返します。
//     *
//     * @param date   指定された日付
//     * @param mvVal  計算値
//     * @param mvMode {@link java.util.Calendar}の定数値
//     * @return 「yyyy/MM/dd」形式の日付を表す文字列
//     */
//    private String doCalcForTypeStringOfDate(String date, int mvVal, int mvMode) {
//        if (date == null) {
//            return null;
//        }
//        GregorianCalendar cal = (GregorianCalendar) getCalendarOfTypeStringOfDate(date);
//        cal.add(mvMode, mvVal);
//        return new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
//    }


//    /**
//     * 「yyyy/MM/dd」形式の日付文字列を{@link java.util.Calendar}型に設定して返します。
//     *
//     * @param date 「yyyy/MM/dd」形式の日付文字列
//     * @return {@link java.util.Calendar}オブジェクトに変換された「yyyy/MM/dd」形式の日付文字列
//     */
//    private Calendar getCalendarOfTypeStringOfDate(String date) {
//        int[] dates = new int[3];
//        GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
//        dates = divideDate(date);
//        // 指定された日付でカレンダーオブジェクトを生成
//        cal.set(dates[0], (dates[1] - 1), dates[2]);
//        return cal;
//    }

//    /**
//     * yyyy/MM/dd形式の文字列を「yyyy」,「MM」,「dd」分割し数値型配列に格納します。
//     */
//    private int[] divideDate(String date) throws NumberFormatException {
//        int[] dates = new int[3];
//        try {
//            StringTokenizer st = new StringTokenizer(date, "/");
//            for (int i = 0; st.hasMoreTokens(); i++) {
//                dates[i] = Integer.parseInt(st.nextToken());
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return dates;
//    }

//    /**
//     * 本人取下処理の後画面表示処理をするメソッド
//     *
//     * @return なし
//     * showWithdraw
//     */
//    private void actionShowWithdraw(ParamNotificationListDto param) {
//
//        //0 一覧
//        param.setMgdSql(buildSQLForSelectGenericDetail("TMG_NTFTYPE", null, "MGD_CMASTERCODE",param));
//        List<NotificationListVo> notificationListVoList = iTmgNotificationService.selectNotificationList(param);
//
//        //2 申請区分マスタ
//        List<Map<String, Object>> mgdTypeList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFTYPE, "asc",param));
//
//        //2 申請ステータスマスタ
//        List<Map<String, Object>> mgdStatusList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFSTATUS, "asc",param));
//
//        //3 年次休暇残日数及び時間
//        List<RestYearPaidHolidayVo> restYearPaidHolidayVoList = iTmgPaidHolidayService.selectNenjikyukazannissu(param, 0);
//
//        //4 今月の月中有給付与の情報
//        List<PaidHolidayThisMonthInfoVo> paidHolidayThisMonthInfoVoList = iTmgMonthlyService.selectPaidHolidayThisMonthInfo(param.getCompId(), param.getCustId(), param.getTargetUser());
//
//
//    }
//    /**
//     * 一覧表示(承認者)の処理をするメソッド
//     *
//     * @return なし
//     * showDispPerm
//     */
//    public void actionShowDispPerm(ParamNotificationListDto param) {
//        //todo
//       /* if(StrUtil.hasEmpty(param.getEmployeeListSql())){
//            return;
//        }
//        if(StrUtil.hasEmpty(referList.getTargetSec())){
//            return;
//        }
//        */
//        checkEmp(param);
//        //0 一覧
//        param.setMgdSql(buildSQLForSelectGenericDetail("TMG_NTFTYPE", null, "MGD_CMASTERCODE",param));
//        List<NotificationListVo> notificationListVoList = iTmgNotificationService.selectNotificationList(param);
//
//        // 1 申請区分マスタ
//        //List<mgdTmgNtfTypeVo> mgdTmgNtfTypeVoS = iMastGenericDetailService.selectMasterTmgNtfType(param.getCustId(),
//                //param.getCompId(), param.getToday(), param.getTargetUser(), param.getLang(), param.getSiteId());
//        //modelMap.addAttribute("mgdTmgNtfTypeVoS", mgdTmgNtfTypeVoS);
//        //2 申請ステータスマスタ
//        List<Map<String, Object>> mgdList = iMastGenericDetailService.selectGenericDetail(buildSQLForSelectGenericDetail(TmgUtil.Cs_MGD_NTFSTATUS, "asc",param));
//
//        // TODO 3 職員一覧
//        //List<employeeListVo> employeeListVos=iHistDesignationService.selectemployeeList(param.getCustId(),param.getCompId(),param.getToday(),param.getEmployeeListSql());
//        //modelMap.addAttribute("employeeListVos", employeeListVos);
//        // TODO 4 所属名
//        //String sectionNAme=iHistDesignationService.selectSectionNAme(param.getCustId(),param.getCompId(),param.getTodayD(),referList.getTargetSec());
//        //modelMap.addAttribute("sectionNAme", sectionNAme);
//        // TODO 5 件数
//        //int selectNotificationCount=iTmgNotificationService.selectNotificationCount(param);;
//        //modelMap.addAttribute("selectNotificationCount", selectNotificationCount);
//        // 6 遡り期限
//        String selectBackLimit = iTmgNotificationService.selectBackLimit(param.getCustId(), param.getCompId(), param.getTargetUser());
//        //modelMap.addAttribute("selectBackLimit", selectBackLimit);
//        // 7 SYSDATE取得
//        String nowDate = iMastGenericDetailService.selectSysdate();
//        //modelMap.addAttribute("nowDate", nowDate);
//
//    }


}
