package jp.smartcompany.job.modules.tmg.monthlyoutput;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyOutputLogSecDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetDateLimit;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetFiscalYearDto;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.*;
import jp.smartcompany.job.modules.tmg.util.CusomCsvUtil;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgSearchRangeUtil;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 月次集計データ
 * ps.c01.tmg.MonthlyOutput
 *
 * @author Wang Ziyue
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MonthlyOutputBean {


    /** 汎用参照リスト */
    private final IMastOrganisationService iMastOrganisationService;
    private final IMastGenericDetailService iMastGenericDetailService;
    private final ITmgWorkMonthlyoutputlistService iTmgWorkMonthlyoutputlistService;
    private final ITmgWorkMoYearlistService iTmgWorkMoYearlistService;
    private final ITmgTriggerService iTmgTriggerService;
    private final ITmgUpdsKintaiService iTmgUpdsKintaiService;
    private final ITmgMonthlyOutputLogService iTmgMonthlyOutputLogService;
    private final ITmgMonthlyOutputLogSecService iTmgMonthlyOutputLogSecService;
    private final ITmgAlertmsgService iTmgAlertmsgService;
    private final CusomCsvUtil csvUtil;
    private final TmgSearchRangeUtil tmgSearchRangeUtil;
    /** 表示中のページ */
    private int _currentPage = 1;
    /** 1ページ内の最大表示件数 */
    public static final int MAX_PAGE_OF_INFO = 50;
    /** 未承認者数 */
    private int _notAppCount = 0;
    /** 更新プログラムID.一覧画面表示時対象者一覧ワークテーブルデータ作成処理 */
    public static final String MOD_ID_DISPRMONTHLYOUTPUT = "MonthlyOutput_ACT_Disp_RMonthlyOutput";
    /** 締め未完了部局数 */
    private int _notAppSecCount = 0;
    /** ダブルクオートをつけるかどうかのチェックボックス */
    public static final String CB_USE_DOUBLE_QUOT = "cbDoubleQuot";
    /** CSVデータカンマ連結用文字列1 */
    public String CSV_CONCAT1 = ",\"";

    /** CSVデータカンマ連結用文字列2 */
    public String CSV_CONCAT2 = "\",";

    /** ダブルクォーテーション(CSVデータ閉じる用) */
    public String DOUBLE_QUOT = "\"";

    /** 月次集計データ作成処理成功 */
    public static final String STAT_CALC_FIXED_SUCCESS = "1";

    /** 月次集計データ作成処理失敗 */
    public static final String STAT_CALC_FIXED_FAILER = "9";
    /** 月次集計データ作成完了フラグ */
    private String _flgFixedCalc = "0";

    /** 更新プログラムID.月次集計処理 */
    public static final String MOD_ID_CCALC = "MonthlyOutput_ACT_CDATA";
    /** 更新プログラムID.勤怠月次締め処理 */
    public static final String MOD_ID_UFIXESMONTHLY = "MonthlyOutput_ACT_UFIXESMONTHLY";
    /** 更新プログラムID.勤怠月次締め解除処理 */
    public static final String MOD_ID_DFIXESMONTHLY = "MonthlyOutput_ACT_DFIXESMONTHLY";

    /** OS改行文字 */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 月次集計データ一覧画面表示処理プロセスを実行します。 以下は主な処理プロセスです。
     * リクエストパラメータから検索値を取得し、以下のSQLクエリを取得し実行します。
     * <li>月別情報検索対象年度を検索するSQL文を取得します。</li>
     * <li>対象組織と組織配下の職員について対象年度の月単位での 未承認者数を集計するSQL文を取得します。</li>
     * <li>対象組織と組織配下の部署について対象年度の月単位で、[勤怠]月次集計処理 ログ(部局別)を参照し、ステータスが[処理前]のレコード件数を検索するSQL文を取得します。</li>
     * <li>ダウンロードデータ集計検索</li>
     * <li>対象年-1年の12月～対象年の11月までの月単位での 未承認者数を集計するSQL文を取得します。</li>
     * <li>対象年-1年の12月～対象年の11月までの月単位での ダウンロードデータ集計検索</li>
     * executeDisp_RMonthlyOutput
     */
    public List<TmgMoYearListVo> actionExecuteDispRMonthlyOutput(String year,PsDBBean psDBBean,TmgReferList referList){
        String targetYear;
        String baseDate;
        if(StrUtil.hasEmpty(referList.getRecordDate())){
            baseDate=TmgUtil.getSysdate();
        }else{
            baseDate=referList.getRecordDate();
        }
        if(StrUtil.hasEmpty(year)){
            targetYear=baseDate;
        }else{
            targetYear=year;
        }

        List<TmgMoYearListVo> tmgMoYearListVoList = new ArrayList<>();
        // 組織を選択、かつ選択組織の表示権限を有していない場合は何もしない。
        if(!StrUtil.hasEmpty(getSectionId("",referList)) && hasAuthOfSect(getSectionId("",referList),psDBBean,referList)){
            TargetDateLimit targetDateLimit=iMastGenericDetailService.selectTargetFiscalYear(psDBBean.getCustID(), psDBBean.getCompCode(),targetYear);

            if(!StrUtil.hasEmpty(targetDateLimit.getTargetStartDate())&& !StrUtil.hasEmpty(targetDateLimit.getTargetStartDate())) {
                TargetFiscalYearDto targetFiscalYear = iMastOrganisationService.selectTargetFiscalYear(psDBBean.getCustID(), psDBBean.getCompCode()
                        , referList.getTargetSec(), psDBBean.getLanguage(), targetYear, targetDateLimit.getTargetStartDate(), targetDateLimit.getTargetEndDate(), baseDate);


                if (!StrUtil.hasEmpty(targetFiscalYear.getTargetYearDate())) {
                    createWorkMonthlyOutPutListData(psDBBean.getCustID(), psDBBean.getCompCode(), targetFiscalYear.getTargetYearDate(), psDBBean);
                }

                tmgMoYearListVoList = iTmgWorkMoYearlistService.selectMoYearList(psDBBean.getCustID(), psDBBean.getCompCode(),
                        referList.getTargetSec(), targetYear, psDBBean.getLanguage(), baseDate);

                return tmgMoYearListVoList;
            }
        }
        return null;
    }

    /**
     * 未承認状況照会画面表示処理プロセスを実行します。 以下は主な処理プロセスです。
     * 未承認者一覧画面表示処理
     * リクエストパラメータから検索値を取得し、以下のSQLクエリを取得し実行します。
     *executeNotAppList_RNotAppList
     */
    public NotApprovalListVo actionExecuteNotAppListRNotAppList(String baseDate,String sPage,PsDBBean psDBBean,TmgReferList referList) {
        if (sPage == null || sPage.length() == 0) {
            sPage = "1";
        }
        int pageInfo[] = getPageOfSearchNumber(sPage);

        List<NotApprovalVo> notApprovalVoList= iMastOrganisationService.selectNotApproval(psDBBean.getCustID(),psDBBean.getCompCode(),
                referList.getTargetSec(),baseDate,psDBBean.getLanguage(),pageInfo[0],pageInfo[1]);

        NotApprovalListVo vo = new NotApprovalListVo();
        if(notApprovalVoList.size() > 0){

            vo.setTotalCount(Integer.valueOf(notApprovalVoList.get(0).getNotApprovalCount()));
            vo.setCurrPage(Integer.valueOf(sPage));
            vo.setPageSize(50);
            vo.setTotalPage(Integer.valueOf(vo.getTotalCount())/50+1);
            vo.setList(notApprovalVoList);
        }else{
            return null;
        }
        return vo;
    }

    /**
     * 締め未完了部局表示処理プロセスを実行します。 以下は主な処理プロセスです。
     * リクエストパラメータから検索値を取得し、以下のSQLクエリを取得し実行します。
     *executeNotAppList_RNotAppSectionList
     */
    public NotAppSectionListVo actionExecuteNotAppListRNotAppSectionList(String baseDate,String sPage,PsDBBean psDBBean,TmgReferList referList) {
        if (sPage == null || sPage.length() == 0) {
            sPage = "1";
        }
        int pageInfo[] = getPageOfSearchNumber(sPage);

        List<NotFixedDeptListVo> notFixedDeptListVoList = iMastOrganisationService.selectNotFixedDeptList(psDBBean.getCustID(),psDBBean.getCompCode(),
                referList.getTargetSec(),baseDate,psDBBean.getLanguage(),pageInfo[0],pageInfo[1]);

        NotAppSectionListVo vo = new NotAppSectionListVo();

        if (notFixedDeptListVoList.size() > 0) {
            vo.setTotalCount(Integer.parseInt(notFixedDeptListVoList.get(0).getNotMonthlyfixCount()));
            vo.setCurrPage(Integer.valueOf(sPage));
            vo.setPageSize(50);
            vo.setTotalPage(Integer.valueOf(vo.getTotalCount())/50+1);
            vo.setList(notFixedDeptListVoList);
        }else{
            return null;
        }
        return vo;
    }

    /**
     * 月次集計データ作成画面表示処理プロセスを実行します。 以下は処理プロセスです。
     * executeDisp_RCalc
     */
    public List<SectionAdminMailVo> actionExecuteDispRCalc(String baseDate,PsDBBean psDBBean,TmgReferList referList) {
        List<MastGenericDetailDO> mgdDtoList=iMastGenericDetailService.selectTmgSectionAdmin(psDBBean.getCustID(), psDBBean.getCompCode(),referList.getTargetSec()
                ,psDBBean.getLanguage(),baseDate);
        List<SectionAdminMailVo> Vos=new ArrayList<>();
        for(MastGenericDetailDO Do:mgdDtoList){
            if(!StrUtil.hasEmpty(Do.getMgdCsparechar3())){
                SectionAdminMailVo mailVo=new SectionAdminMailVo();
                mailVo.setName(Do.getMgdCsparechar4());
                mailVo.setMailAddress(Do.getMgdCsparechar3());
            }
        }
        return   Vos;
    }

    /**
     * 月次集計データCSV出力処理プロセスを実行します。 以下は処理プロセスです。
     * <li>月次集計出力イメージを検索するSQL文を取得します。</li>
     * <li>CSVファイル名を生成するSQL文を取得します。</li>
     * <li>SQLを実行します。</li>
     * <li>親クラスにダウンロードファイル情報を設定します。</li>
     * sRetroFlg  遡及フラグ
     * dlTypeId   DL種別コード
     * executeDownload_CDownload
     */
    public void actionexecuteDownloadCDownload(boolean checked,String baseDate,String sRetroFlg,String dlTypeId,PsDBBean psDBBean,TmgReferList referList) throws Exception {
        // ダブルクオートで囲む場合
        if (checked) {
            CSV_CONCAT1 = "\"";

        } else {
            CSV_CONCAT1 = "";
        }
        List<TmgMoTableFunctionVo> tmgMoTableFunctionVoList = new ArrayList<TmgMoTableFunctionVo>();
        List<String>   tmgMoRetroLayout = new ArrayList<String>();
        // 遡及の場合
        if ("on".equals( sRetroFlg)) {
            // ファンクション名取得
            tmgMoTableFunctionVoList=iMastGenericDetailService.selectTmgMoTableFunction(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getLanguage()
                    ,baseDate,TmgUtil.Cs_MGD_TMG_MOTABLEFUNCTION_2);
            // カラム名リスト取得
            tmgMoRetroLayout=iMastGenericDetailService.selectTmgMoRetroLayout(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getLanguage()
                    ,baseDate,dlTypeId);
        // 月例の場合
        } else {
            // ファンクション名取得
            tmgMoTableFunctionVoList=iMastGenericDetailService.selectTmgMoTableFunction(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getLanguage()
                    ,baseDate,TmgUtil.Cs_MGD_TMG_MOTABLEFUNCTION_1);

            // カラム名リスト取得
            tmgMoRetroLayout=iMastGenericDetailService.selectTmgMoRetroLayout(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getLanguage()
                    ,baseDate,dlTypeId);
        }


        // CSVデータ取得用SQL構築
        List<Map<String,Object>> moUpds = iTmgUpdsKintaiService.selectMoUpds(tmgMoRetroLayout,tmgMoTableFunctionVoList.get(0).getMgdCfunctionid()
        ,referList.getTargetSec(),dlTypeId,baseDate, psDBBean.getCompCode(), psDBBean.getCompCode(),psDBBean.getLanguage());


        // CSVファイル名
        String TmgMoCsvFileName = iMastGenericDetailService.selectTmgMoCsvFileName(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getUserCode(),baseDate,dlTypeId);

        List<List<Object>> rowData =getCsvOfBody(moUpds,tmgMoRetroLayout);
        if(rowData.size() == 0){
            rowData.add(Collections.singletonList(""));
        }
        csvUtil.writeCsv(TmgMoCsvFileName,rowData);
    }



    /**
     * CSV出力ファイル作成
     * @param moUpds：CSVレイアウトカラムリスト
     * @return CSV出力文字列
     */
    private List<List<Object>> getCsvOfBody(List<Map<String,Object>> moUpds,List<String> tmgMoRetroLayout) throws Exception {

        List<List<Object>> rowData = CollUtil.newArrayList();

        for(Map<String,Object> map:moUpds){
            List<Object> rowTilte = CollUtil.newArrayList();
            for (String col:tmgMoRetroLayout) {
                String value;
                if(map.get(col) != null){
                    value= CSV_CONCAT1+map.get(col)+CSV_CONCAT1;
                }else{
                    value=CSV_CONCAT1+StrUtil.nullToEmpty("")+CSV_CONCAT1;
                }
                rowTilte.add(value);
            }
            rowData.add(rowTilte);
        }
        return rowData;
    }

    /**
     * CSVダウンロード画面表示処理プロセスを実行します。 リクエストパラメータから検索対象年月を取得し、このクラスオブジェクトに保持します。
     * executeDisp_RDownloadView
     */
    public List<MoDLTypeVo> actionExecuteDispRDownloadView(String baseDate,PsDBBean psDBBean){

        List<MoDLTypeVo> moDLTypeVoList = iMastGenericDetailService.selectTmgMoDLType(psDBBean.getCustID(), psDBBean.getCompCode()
                                                                                            ,psDBBean.getLanguage(),baseDate);

        return moDLTypeVoList;
    }

    /**
     * 月次集計データ作成処理プロセスを実行します。<br>
     * 以下は処理プロセスです。
     * <p>
     * <ol>
     * <li>SQLクエリ格納変数(Vector)を宣言します。</li>
     * <li>月次集計データ作成処理のジョブ番号を検索するSQL文を取得します。</li>
     * <li>SQLを実行し結果セットを取得します。</li>
     * <li>結果セットからカレントジョブ番号を取得しこのクラスのフィールド変数に保持します。<br>
     * ジョブ番号取得処理中に例外などで取得失敗した場合は、フィールド変数.月次集計データ作成完了フラグに 取得失敗({@link MonthlyOutputBean#STAT_CALC_FIXED_FAILER}
     * )を設定し、処理を終了します。</li>
     * <li>[勤怠]月次集計処理ログ(部局別)に対象年月の対象組織と配下組織のレコードを挿入するSQL文を取得します。</li>
     * <li>SQLを実行します。</li>
     * <li>勤怠トリガーテーブルにカレントジョブ番号の対象データを削除するSQL文を取得します。</li>
     * <li>勤怠トリガーテーブルにカレントジョブ番号の対象データを挿入するSQL文を取得します。</li>
     * <li>勤怠トリガーテーブルにカレントジョブ番号の対象データを削除するSQL文を取得します。</li>
     * <li>SQLを実行します。</li>
     * executeCALC_CCALC
     * </p>
     *
     */
    public synchronized GlobalResponse actionExecuteCALCCCALC(String baseDate, PsDBBean psDBBean, TmgReferList referList) {
        String seq =null;
        try {
            seq= iTmgMonthlyOutputLogService.selectSeq();
        }catch (Exception e){
            _flgFixedCalc = STAT_CALC_FIXED_FAILER; // failer
            return GlobalResponse.error(" ジョブ番号取得失敗"); // ジョブ番号取得失敗したときの対応
        }
        // q0.ログ挿入
        int insertMonthlyOutputLogSec=insertMonthlyOutputLogSec(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getUserCode(),baseDate,
                referList.getTargetSec(),seq);

        // q1.トリガー削除
        int deleteTmgTriggerbef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID",MOD_ID_CCALC)
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        // q2.トリガー挿入
        int insertTmgTrigger = insertTmgTrigger(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getUserCode(),baseDate
        ,MOD_ID_CCALC,referList.getTargetSec(),seq);

        // q3.トリガー削除
        int deleteTmgTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID",MOD_ID_CCALC)
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        if (insertMonthlyOutputLogSec <= 0||insertTmgTrigger<=0||deleteTmgTriggerAft<=0) {
            _flgFixedCalc = STAT_CALC_FIXED_FAILER; // failer
            return GlobalResponse.error(); // ジョブ番号取得失敗したときの対応
        } else {
            _flgFixedCalc = STAT_CALC_FIXED_SUCCESS; // success
            return GlobalResponse.ok(seq);
        }
    }

    /**
     * 勤怠月次締めプロセスを実行します。
     * executeDisp_UFIXESMONTHLY
     */
    public GlobalResponse actionExecuteDispUFIXESMONTHLY(String baseDate,PsDBBean psDBBean,TmgReferList referList) {
        // q1.トリガー削除
        int deleteTmgTriggerbef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID",MOD_ID_UFIXESMONTHLY)
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        // q2.トリガー挿入
        int insertTmgTrigger = insertTmgTrigger(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getUserCode(),baseDate
                ,MOD_ID_UFIXESMONTHLY,referList.getTargetSec(),null);

        // q3.トリガー削除
        int deleteTmgTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID",MOD_ID_UFIXESMONTHLY)
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        if(insertTmgTrigger==1&&insertTmgTrigger==1){
            return GlobalResponse.ok();
        }else{
            return GlobalResponse.error();
        }
    }

    /**
     * 勤怠月次締め解除プロセスを実行します。
     * executeDisp_DFIXESMONTHLY
     */
    public GlobalResponse actionExecuteDisp_DFIXESMONTHLY(String baseDate,PsDBBean psDBBean,TmgReferList referList){
        // q1.トリガー削除
        int deleteTmgTriggerbef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID",MOD_ID_DFIXESMONTHLY)
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        // q2.トリガー挿入
        int insertTmgTrigger = insertTmgTrigger(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getUserCode(),baseDate
                ,MOD_ID_DFIXESMONTHLY,referList.getTargetSec(),null);

        // q3.トリガー削除
        int deleteTmgTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID",MOD_ID_DFIXESMONTHLY)
                .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

        if(insertTmgTrigger==1&&insertTmgTrigger==1){
            return GlobalResponse.ok();
        }else{
            return GlobalResponse.error();
        }
    }


    /**
     * 確定処理プロセスを実行します。
     * executeChangeFix
     */
    public GlobalResponse actionExecuteChangeFix(String baseDate, String action, PsDBBean psDBBean, TmgReferList referList) {
        String targetYear;
        String date;
        if(StrUtil.hasEmpty(referList.getRecordDate())){
            date=TmgUtil.getSysdate();
        }else{
            date=referList.getRecordDate();
        }
        if(StrUtil.hasEmpty(date)){
            targetYear=baseDate;
        }else{
            targetYear=baseDate;
        }
        // 確定可能かどうかをチェックする
        int  salaryFix = iTmgWorkMoYearlistService.selectMoYearListColumn(psDBBean.getCustID(), psDBBean.getCompCode(),
                referList.getTargetSec(),baseDate,
                psDBBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_SYSTEM_INTRODUCTION_DATE).replace('-', '/')
                ,psDBBean.getLanguage(), targetYear);


        // SALARY_FIX="1"なら確定可能、"3"なら確定解除可能
        if(salaryFix == 1 || salaryFix == 3){

            // 更新プログラムID組み立て
            String modifierProgID="";

            if(action.equals("C")){
                modifierProgID = "MonthlyOutput_ACT_Disp_CFixesSalary";
            }else if(action.equals("D")){
                modifierProgID = "MonthlyOutput_ACT_Disp_DFixesSalary";
            }

            if(StrUtil.hasEmpty(modifierProgID)){
                return GlobalResponse.error("action is null");
            }
            // q1.トリガー削除
            int deleteTmgTriggerbef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                    .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                    .eq("TTR_CMODIFIERPROGRAMID",modifierProgID)
                    .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                    .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));

            // q2.トリガー挿入
            int insertTmgTrigger = insertTmgTrigger(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getUserCode(),baseDate
                    ,modifierProgID,referList.getTargetSec(),null);

            // q3.トリガー削除
            int deleteTmgTriggerAft=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                    .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                    .eq("TTR_CMODIFIERPROGRAMID",modifierProgID)
                    .eq("TTR_CCUSTOMERID",psDBBean.getCustID())
                    .eq("TTR_CCOMPANYID", psDBBean.getCompCode()));
        }
            // SALARY_FIX="2"の場合、集計処理が必要なのでアラートを仕込んでおく（画面再表示時にアラートを出す）
        else if(salaryFix == 2){
            //集計処理を行ってから確定をしてください。
            return GlobalResponse.ok("集計処理を行ってから確定をしてください。");
        }
            // SALARY_FIX="0"は、そもそもこの処理をコールできないはずなので、想定外のエラーとしておく
        else{
            //確定処理で例外が発生しました。システム管理者へ連絡してください。。
            return GlobalResponse.ok("確定処理で例外が発生しました、システム管理者へ連絡してください");
        }

        return GlobalResponse.ok();
    }


    /**
     * 集計時の問題(アラート)ダイアログの表示プロセスを実行します。
     */
    public NotAppSectionListVo executeNotAppList_RAlertList(String baseDate, String sPage, PsDBBean psDBBean, TmgReferList referList) {
        if (sPage == null || sPage.length() == 0) {
            sPage = "1"; // default select Page
        } else {
        }
        int pageInfo[] = getPageOfSearchNumber(sPage);


        List<AlertVo> alertVo=iTmgAlertmsgService.selectAlert(psDBBean.getCustID(), psDBBean.getCompCode(),referList.getTargetSec(),baseDate,
                psDBBean.getLanguage(),pageInfo[0],pageInfo[1]);

        NotAppSectionListVo vo =new NotAppSectionListVo();
        if (alertVo.size() > 0) {
            vo.setTotalCount(Integer.parseInt(alertVo.get(0).getAlertCount()));
            vo.setCurrPage(Integer.valueOf(sPage));
            vo.setPageSize(50);
            vo.setTotalPage(Integer.valueOf(vo.getTotalCount())/50+1);
            vo.setList2(alertVo);
        }else{
            return null;
        }

        return vo;
    }

    /**
     * 勤怠トリガーテーブルにカレントジョブ番号の対象データを挿入するSQL文を返却します。
     *
     * @param custId 対象顧客コード
     * @param compId 対象法人コード
     * @param empId ユーザーコード
     * @param targetDate 対象年月
     * @param modPgId 更新プログラムID
     * @param secId 組織コード
     * @param jobNo ジョブ番号
     */
    private int insertTmgTrigger(String custId, String compId, String empId, String targetDate,
                                                String modPgId, String secId, String jobNo) {

        TmgTriggerDO ttDo=new TmgTriggerDO();
        ttDo.setTtrCcustomerid(custId);
        ttDo.setTtrCcompanyid(compId);
        ttDo.setTtrCemployeeid(empId);
        ttDo.setTtrDstartdate(TmgUtil.minDate);
        ttDo.setTtrDenddate(TmgUtil.maxDate);
        ttDo.setTtrCmodifieruserid(empId);
        ttDo.setTtrDmodifieddate(DateTime.now());
        ttDo.setTtrCmodifierprogramid(modPgId);
        ttDo.setTtrCprogramid("MonthlyOutput");
        ttDo.setTtrDparameter1(DateUtil.parse(targetDate));
        ttDo.setTtrCparameter1(secId);
        if(!StrUtil.hasEmpty(jobNo)){
            ttDo.setTtrNparameter1(Double.valueOf(jobNo));
        }
        return iTmgTriggerService.getBaseMapper().insert(ttDo);

    }

    /**
     * [勤怠]月次集計処理ログ(部局別)に対象年月の対象組織と配下組織のレコードを挿入する SQL文を生成し返します。
     *
     * @param custId 対象顧客コード
     * @param compId 対象法人コード
     * @param modUser 更新対象ユーザーコード
     * @param targetDate 対象年月
     * @param secId 組織コード
     * @param jobNo ジョブ番号
     * @return
     */
    private int insertMonthlyOutputLogSec(String custId, String compId, String modUser,
                                                        String targetDate, String secId, String jobNo) {
        StringBuffer sql = new StringBuffer();
        TmgMonthlyOutputLogSecDO tmgMonthlyOutputLogSecDO=new TmgMonthlyOutputLogSecDO();
        tmgMonthlyOutputLogSecDO.setTmolsCcustomerid(custId);
        tmgMonthlyOutputLogSecDO.setTmolsCcompanyid(compId);
        tmgMonthlyOutputLogSecDO.setTmolsDstartdate(TmgUtil.minDate);
        tmgMonthlyOutputLogSecDO.setTmolsDenddate(TmgUtil.maxDate);
        tmgMonthlyOutputLogSecDO.setTmolsCmodifieruserid(modUser);
        tmgMonthlyOutputLogSecDO.setTmolsDmodifieddate(DateTime.now());
        tmgMonthlyOutputLogSecDO.setTmolsCmodifierprogramid("MonthlyOutput_ACT_CDATA");
        tmgMonthlyOutputLogSecDO.setTmolsDyyyymm(DateUtil.parse(targetDate));
        tmgMonthlyOutputLogSecDO.setTmolsCsectionid(secId);
        tmgMonthlyOutputLogSecDO.setTmolsNjobno(Long.parseLong(jobNo));
        tmgMonthlyOutputLogSecDO.setTmolsCstatusflg(TmgUtil.Cs_MGD_CALCSTATUS_0);
        return iTmgMonthlyOutputLogSecService.getBaseMapper().insert(tmgMonthlyOutputLogSecDO);
    }


    /**
     * ページ情報からページング処理を実行し開始件～終了件を int型配列[0=開始件, 1=終了件]に格納し返します。
     *
     * @param sPage ページ情報
     */
    public int[] getPageOfSearchNumber(String sPage) {
        int iPage = 1;
        if (sPage != null) {
            iPage = Integer.parseInt(sPage);
        }

        int[] pageInfo = new int[2];

        if (iPage > 1) {
            pageInfo[0] = (iPage - 1) * MAX_PAGE_OF_INFO + 1;
            pageInfo[1] = iPage * MAX_PAGE_OF_INFO;
        } else {
            pageInfo[0] = 1;
            pageInfo[1] = MAX_PAGE_OF_INFO;
        }

        return pageInfo;
    }

    /**
     * 対象者一覧ワークテーブルデータの作成処理を行う
     *
     * @param sCsustId   顧客ＩＤ
     * @param sCompId    法人ＩＤ
     * @param targetYear 表示対象年度
     */
    private void createWorkMonthlyOutPutListData(String sCsustId, String sCompId, String targetYear,PsDBBean psDBBean) {

        // 処理対象年を取得
        String targetYYYY = targetYear.substring(0, 4);

        /*
         * 対象者一覧ワークテーブルデータを作成する必要があるか判定し、
         * 必要であれば、ワークテーブルデータを作成する。、
         */
        if (!isExistentWorkMonthlyOutputListData(sCsustId, sCompId, targetYear)) {

            try {
                // トリガー削除
                int deleteTmgTriggerBef=iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                                                            .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                                                            .eq("TTR_CMODIFIERPROGRAMID", MOD_ID_DISPRMONTHLYOUTPUT)
                                                            .eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                                                            .eq("TTR_CCOMPANYID",  psDBBean.getCompCode()));

                // トリガー登録
                int insertTmgTrigger = insertTmgTrigger(psDBBean.getCustID(), psDBBean.getCompCode(),psDBBean.getUserCode(),targetYYYY,MOD_ID_DISPRMONTHLYOUTPUT);

                // トリガー削除
                int deleteTmgTriggerAfr = iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                                            .eq("TTR_CMODIFIERUSERID",psDBBean.getUserCode())
                                            .eq("TTR_CMODIFIERPROGRAMID", MOD_ID_DISPRMONTHLYOUTPUT)
                                            .eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                                            .eq("TTR_CCOMPANYID",  psDBBean.getCompCode()));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    /**
     * 勤怠トリガーテーブルに対象者一覧ワークテーブル作成処理に必要なパラメータデータを挿入するSQL文を返却します。
     *
     * @param  custId     対象顧客コード
     * @param  compId     対象法人コード
     * @param  empId      ユーザーコード
     * @param  targetYear 対象年(YYYY)
     * @param  modPgId    更新プログラムID
     * @return SQL文
     */
    private int insertTmgTrigger(String custId, String compId, String empId,
                                                String targetYear, String modPgId) {

        TmgTriggerDO ttDo = new TmgTriggerDO();

        ttDo.setTtrCcustomerid(custId);
        ttDo.setTtrCcompanyid(compId);
        ttDo.setTtrCemployeeid(empId);
        ttDo.setTtrDstartdate(TmgUtil.minDate);
        ttDo.setTtrDenddate(TmgUtil.maxDate);
        ttDo.setTtrCmodifieruserid(empId);
        ttDo.setTtrDmodifieddate(DateTime.now());
        ttDo.setTtrCmodifierprogramid(modPgId);
        ttDo.setTtrCprogramid(modPgId);
        ttDo.setTtrNparameter1(Double.valueOf(targetYear));
        ttDo.setTtrDparameter1(DateTime.now());

        return iTmgTriggerService.getBaseMapper().insert(ttDo);
    }




    /**
     * 本日、対象者一覧ワークテーブルが作成されているか判定します。
     * 作成済の場合、真を返し、未作成の場合、偽を返します。
     *
     * @param  sCsustId    顧客ＩＤ
     * @param  sCompId     法人ＩＤ
     * @param  sTargetYear 処理対象年
     * @return 判定結果
     */
    private boolean isExistentWorkMonthlyOutputListData(String sCsustId, String sCompId, String sTargetYear) {

        // 判定結果フラグ
        boolean bFlg = false;
        // システム導入年月日
        //todo
        String sIntroductionData = "";//getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_SYSTEM_INTRODUCTION_DATE);
        int count = iTmgWorkMonthlyoutputlistService.selectWorkMonthlyOutputList(sCsustId,sCompId,sTargetYear,sIntroductionData);
        if(count>0){
            bFlg=true;
        }
        return bFlg;
    }



    /**
     * 選択された組織コードを返します
     * リクエスト中の組織変更で意図しない組織が処理対象とならない様、処理実行時に表示されている組織を優先する
     * @return 組織コード
     */
    public String getSectionId(String action,TmgReferList referList) {
        if(referList != null){
            // 初期表示や組織変更の場合、選択された組織を返す
            if (StrUtil.isBlank(StrUtil.nullToEmpty(action))
                    || StrUtil.isBlank(StrUtil.nullToEmpty(referList.getTargetSec()))) {
                return referList.getTargetSec();
                // それ以外は表示中組織を返す
            } else {
                return (StrUtil.equals(StrUtil.nullToEmpty(referList.getTargetSec()),referList.getTargetSec()))
                        ? referList.getTargetSec()
                        : referList.getTargetSec();
            }
        }else{
            return null;
        }
    }
    /**
     * 選択された組織の表示権限があるかを判定するメソッド
     * 表示画面 MonthlyOutput_DispRcheck.jsp の表示判定で使用します
     *
     * @param  section:組織
     * @return 権限有無(有:true、無:false)
     */
    public boolean hasAuthOfSect(String section,PsDBBean psDBBean,TmgReferList referList) {
        String sExists = getDivTreeSearchRange(psDBBean, ContextUtil.getSession());
        if(StrUtil.isBlank(sExists)){
            return true;
        }
        // 検索
        int organisationCount=iMastOrganisationService.selectHasAuth(psDBBean.getCustID(),psDBBean.getCompCode()
        ,section,referList.getRecordDate(),psDBBean.getLanguage(),sExists);

        if(organisationCount > 0){
            return true;
        }
        return false;
    }

    /**
     * 検索対象範囲条件の取得(職員に対する検索対象範囲とは別に分ける。Treeでは上位所属を利用するが社員リストでは出てはいけないため)
     * @param psDBBean
     * @param session
     * @return
     */
    public String getDivTreeSearchRange(PsDBBean psDBBean,HttpSession session) {
        try {
            return  tmgSearchRangeUtil.getExistsQueryBaseSection(psDBBean,session,"o.MO_CSECTIONID_CK");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



}
