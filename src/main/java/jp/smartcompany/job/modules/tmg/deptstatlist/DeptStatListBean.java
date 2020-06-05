package jp.smartcompany.job.modules.tmg.deptstatlist;

import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsUtil;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 部署別統計情
 * ps.c01.tmg.DeptStatList.DeptStatListBean
 *
 * @author Nie Wanqun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeptStatListBean {


    /**
     * PsDBBean
     */
    private final PsDBBean psDBBean;

    /**
     * IMastGenericDetailService
     */
    private final IMastGenericDetailService iMastGenericDetailService;

    /**
     * ITmgMonthlyService
     */
    private final ITmgMonthlyService iTmgMonthlyService;

    /**
     * ITmgDailyService
     */
    private final ITmgDailyService iTmgDailyService;

    /**
     * ITmgDailyDetailService
     */
    private final ITmgDailyDetailService iTmgDailyDetailService;

    /**
     * IMastOrganisationService
     */
    private final IMastOrganisationService iMastOrganisationService;

    /**
     * ITmgTriggerService
     */
    private final ITmgTriggerService iTmgTriggerService;

    /**
     * ITmgMonthlyInfoService
     */
    private final ITmgMonthlyInfoService iTmgMonthlyInfoService;

    /**
     * ITmgMonthlyInfoService
     */
    private final ITmgCalendarService iTmgCalendarService;

    /**
     * IMastEmployeesService
     */
    private final IMastEmployeesService iMastEmployeesService;

    /**
     * 承認サイト
     * 部署別統計情
     * <p>
     * ACT_DISP_RLIST
     */
    public void actDispRlist(ModelMap modelMap) throws Exception {




    }
    /**
     * 承認サイト・管理サイト
     * 部署別統計情
     * <p>
     * ACT_DISP_CDOWNLOAD
     */
    public void actDispCdownload(ModelMap modelMap) throws Exception {
        executeDownload_CDownload();

    }


    /**
     * 部署別統計情報データCSV出力処理
     */
    private void executeDownload_CDownload() throws Exception{

        List <String> headerList= new ArrayList<>();
//                List <String> headerList= this.buildSQLForSelectTMG_DEPTSTATCSVITEMS(
//                psDBBean.getCustID(),
//                psDBBean.getTargetComp(),
//                psDBBean.getLanguage(),
//                baseDate);


        List<String> column = new ArrayList<String>();

        List<Map> mapList = new ArrayList<>();
//        List<Map> mapList = buildSQLForSelectCSVOutputImage(baseDate, column);


        // ファイル名セット(フォーマット：yyyymm_部署名.csv)
        psDBBean.setDownloadFileName(new SimpleDateFormat("yyyymm").format(new SimpleDateFormat("yyyy/mm/dd").parse(baseDate)) + "_"
                + referList.getTargetSecName() + ".csv");


        // ヘッダ行
        StringBuffer csvData = new StringBuffer(StringUtils.join(headerList.toArray(), ','));
        csvData.append(LINE_SEPARATOR);

        // 明細行
        for (Map mapRow:mapList) {

            for (int col = 0; col < headerList.size(); col++) {
                csvData.append(StringUtils.defaultString((String)mapRow.get(headerList.get(col)))).append(',');
            }
            csvData.deleteCharAt(csvData.lastIndexOf(","));
            csvData.append(LINE_SEPARATOR);
        }

        psDBBean.setDownloadStream(csvData.toString().getBytes());
        psDBBean.setDownloadContentType(DOWNLOAD_CONTENT_TYPE);
        psDBBean.setDownload(true);


    }


    /** ダウンロードコンテンツタイプ */
    public static final String DOWNLOAD_CONTENT_TYPE = "application/octetstream;charset=Shift_JIS";

    /** OS改行文字 */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");



    public TmgReferList getReferList(){
        return referList;
    }

    /** 勤怠シートの参照権限(基準日月) */
    boolean authorityMonth            = false;

    /** 勤怠シートの参照権限(基準日)設定メソッド */
    public void setAuthorityMonth(boolean bValue) {
        authorityMonth = bValue;
    }
    /** 勤怠シートの参照権限(基準日)取得メソッド */
    public boolean getAuthorityMonth() {
        return authorityMonth;
    }

    /** 参照権限フラグ：参照可能 */
    private static final boolean CB_CAN_REFER = true;

    /**
     * 引数で指定されたyyyy/mm/dd形式の日付文字列を「/」で分割しint型配列に格納します。
     * @param date 基準日 (「yyyy/mm/dd」形式の文字列)
     * @exception NumberFormatException
     * @exception ArrayIndexOutOfBoundsException
     */
    public int[] devideDateFormatString(String date)
            throws NumberFormatException, ArrayIndexOutOfBoundsException {

        int[] dates = new int[3]; //日付値格納配列

        try {
            StringTokenizer st = new StringTokenizer(date, "/");
            for(int i = 0; st.hasMoreTokens(); i++) {
                dates[i] = Integer.parseInt(st.nextToken());
            }
        } catch (NumberFormatException e) {

        } catch (ArrayIndexOutOfBoundsException e) {

        }
        return dates;
    }

    /**
     * 引数で指定された値分だけ基準日の月を追加し、その月の月初めの日付を返却します。
     * @param  date    基準日 (「yyyy/mm/dd」形式の文字列)
     * @param  mvValue 追加したい月の値
     * @return String  移動した年月日 - ※日は月初(1日)になります。
     */
    public String addMonthOfDateFromatString(String date, int mvValue) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date objDate        = null;

        try {
            // 日付文字列を分割
            int dates[]      = devideDateFormatString(date);
            Calendar calendar = Calendar.getInstance();
            // カレンダーに設定
            calendar.set(dates[0], dates[1], dates[2]);

            // 月の追加処理
            calendar.add(Calendar.MONTH, mvValue - 1);
            calendar.set(Calendar.DATE, 1);

            objDate = calendar.getTime();

        } catch (Exception e) {

        }
        return df.format(objDate);

    }
    /** 勤怠シートの参照権限(基準日の翌月) */
    boolean authorityNextMonth        = false;
    /** 基準日の前月 */
    private String preMonth			 = null;
    /** 基準日の翌月 */
    private String nextMonth			 = null;
    /** 参照権限フラグ：参照不可能  */
    private static final boolean CB_CANT_REFER = false;

    /** 勤怠シートの参照権限(基準日の翌月)設定メソッド */
    public void setAuthorityNextMonth(boolean bValue) {
        authorityNextMonth = bValue;
    }
    /** 勤怠シートの参照権限(基準日の翌月)取得メソッド */
    public boolean getAuthorityNextMonth() {
        return authorityNextMonth;
    }
    /**
     * メイン処理
     * @throws Exception
     */
    public void execute(ModelMap modelMap) throws Exception{
        //リクエストからパラメータを取得
        setReferList(modelMap);

        // 2007/10/03  H.Kawabata  #248 勤怠系コンテンツの参照権限の統一対応
        // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId())) {
            String sAction     = getRequestString("txtAction");
            String sTargetSec  = getReferList().getTargetSec();

            // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
            if((TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) && (sAction == null || sAction.length() == 0))
                    || (TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()) && !(sTargetSec == null || sTargetSec.length() == 0) && (sAction == null || sAction.length() == 0))) {

                // 参照権限チェック(現在時点での年月)
                if (getReferList().existsAnyone(getSysdate()) &&
                        getReferList().isThereSomeEmployees(getSysdate())) {
                    setAuthorityMonth(CB_CAN_REFER);

                    // 参照権限が無い場合は、1ヶ月過去のシートの権限をチェックする。
                } else {
                    String sPrevMonth = addMonthOfDateFromatString(getSysdate(), -1);
                    // 汎用参照コンポーネントの基準日を基準日の前月(過去)に設定しなおす
                    referList = new TmgReferList(psDBBean, beanDesc, sPrevMonth, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
                    referList.putReferList(modelMap);

                    // 参照権限チェック(現在時点より1年度過去の年度)
                    if (getReferList().existsAnyone(sPrevMonth) &&
                            getReferList().isThereSomeEmployees(sPrevMonth)) {

                        // 対象年月が現在の年月の場合、1ヶ月過去の年月を対象年月に設定します
                        // このif文は、現在「部署A」を選択していて対象年月が変更された状態で「組織B」を選択しなおすと
                        // 「組織B」の現在日付時点の年月と、その年月-1ヶ月時点での参照権限をチェックします。
                        // その際に、変更後対象年月が現在年月でない場合にも現在年月-1ヶ月を設定されるのを防ぐ為
                        // 「対象年月が現在の年月の場合」という条件を実装しています。
                        if(getSysdate().equals(baseDate)) {
                            // 対象年月を1ヶ月過去に設定します
                            baseDate = sPrevMonth;
                            //基準日から勤務年月を設定
                            setObjWorkDate(baseDate);

                            // 検索対象年月の前月
                            preMonth  = addMonthOfDateFromatString(baseDate, -1);
                            // 検索対象年月の翌月
                            nextMonth = addMonthOfDateFromatString(baseDate, 1);
                        }
                        setAuthorityMonth(CB_CAN_REFER);
                    } else {
                        // 対象年月を元に戻します
                        referList = new TmgReferList(psDBBean, beanDesc, baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
                        referList.putReferList(modelMap);
                        setAuthorityMonth(CB_CANT_REFER);
                    }
                }

                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                // 翌月の権限があればリンク「>」を画面に表示する。
                // 権限が無い場合は「>」を表示しない。
                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
                //   権限が無いのと同じ扱いとする。
                String sNextMonth = addMonthOfDateFromatString(baseDate, 1);
                if(getReferList().existsAnyone(sNextMonth) && getReferList().isThereSomeEmployees(sNextMonth)) {
                    setAuthorityNextMonth(CB_CAN_REFER);
                } else {
                    setAuthorityNextMonth(CB_CANT_REFER);
                }

                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                // 翌月の権限があればリンク「>」を画面に表示する。
                // 権限が無い場合は「>」を表示しない。
                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
                //   権限が無いのと同じ扱いとする。
            } else if(!(sTargetSec == null || sTargetSec.length() == 0)) {

                // 参照権限の判定：設定(当月、当月-1ヶ月)
                // 当月もしくは、先月どちらかの権限が有効な場合は過去に関しては常に表示する(シートがある限り)
                String sPrevMonthSysdate = addMonthOfDateFromatString(getSysdate(), -1);
                if((getReferList().existsAnyone(getSysdate()) && getReferList().isThereSomeEmployees(getSysdate())) ||
                        (getReferList().existsAnyone(sPrevMonthSysdate) && getReferList().isThereSomeEmployees(sPrevMonthSysdate))) {
                    setAuthorityMonth(CB_CAN_REFER);
                } else {
                    setAuthorityMonth(CB_CANT_REFER);
                }

                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                // 翌月の権限があればリンク「>」を画面に表示する。
                // 権限が無い場合は「>」を表示しない。
                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
                //   権限が無いのと同じ扱いとする。
                String sNextMonthSysdate = addMonthOfDateFromatString(baseDate, 1);
                if(getReferList().existsAnyone(sNextMonthSysdate) && getReferList().isThereSomeEmployees(sNextMonthSysdate)) {
                    setAuthorityNextMonth(CB_CAN_REFER);
                } else {
                    setAuthorityNextMonth(CB_CANT_REFER);
                }
            }
            // その他のサイトの場合
        } else {
            setAuthorityMonth(CB_CAN_REFER);
        }


    }
    /** 基準日 */
    public String baseDate = null;

    public String getRequestString(String key){
        String data = "";
        try{
            data = (String)psDBBean.getReqParam(key);
            if(data==null) {
                data = "";
            }
        }catch(Exception e){
            data = "";
        }

        return data;
    }

    /**
     * 今月日付取得処理
     * SYSDATE時点の年月の1日の日付を返します。
     * ex)現在「2007年9月23日」の場合は「2007年9月1日」の情報が返却されます。
     * また、返却される際の日付書式はは「YYYY/MM/DD」です。
     *
     * @return 今月の月初めの日付(YYYY/MM/DD)形式
     */
    private String getSysdate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        return sdf.format(new Date())+"/01";
    }

    /**
     * yyyy/MM/dd形式の文字列を「yyyy」,「MM」,「dd」分割し数値型配列に格納します。
     */
    private int[] divideDate(String date) throws NumberFormatException {
        int[] dates = new int[3];
        try {
            StringTokenizer st = new StringTokenizer(date, "/");
            for(int i = 0; st.hasMoreTokens(); i++) {
                dates[i] = Integer.parseInt(st.nextToken());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dates;
    }
    /** 対象勤務年月 */
    private String objWorkDate         = null;
    /** システム年月日(今月) */
    private String thisMonth           = "";
    /**
     * 基準日から勤務年月を設定します。
     */
    private void setObjWorkDate(String baseDate) {
        int[] date = divideDate(baseDate);

        String labelYear = "年";
        String labelDay = "月";
        StringBuffer wrkDate = new StringBuffer();
        wrkDate.append(String.valueOf(date[0]));
        wrkDate.append(labelYear);
        wrkDate.append(String.valueOf(date[1]));
        wrkDate.append(labelDay);
        objWorkDate = wrkDate.toString();
    }

    /** 汎用参照オブジェクト */
    public TmgReferList referList      = null;
    /**ドメイン*/
    private String beanDesc  = "DeptStatListBean";
    public void setThisMonth(String thisMonth) {
        this.thisMonth = thisMonth;
    }

    /** リクエストキー - 再表示ボタン使用判定用 */
    private static final String TREEVIEW_KEY_REFRESH_FLG  = "txtTmgReferListTreeViewRefreshFlg";
    /**
     * 汎用コンポーネント設定処理
     * 基準日時点の汎用コンポーネントをnewするメソッドです。
     */
    private void setReferList(ModelMap modelMap){
        //基準日の取得
        baseDate = getRequestString("txtTDA_DYYYYMM");
        if (baseDate == null || baseDate.length() == 0) {
            baseDate = getSysdate();
        }
        //基準日から勤務年月を設定
        setObjWorkDate(baseDate);

        try{
            referList = new TmgReferList(psDBBean, beanDesc, baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
            referList.putReferList(modelMap);
        }catch(Exception e){
            e.printStackTrace();
        }

        // 当日・当月日付の情報を再格納する
        if (referList.getRecordDate() != null){
            // 当月情報更新
            setThisMonth(TmgUtil.getFirstDayOfMonth(referList.getRecordDate(),TmgUtil.Cs_PARAM_THIS_MONTH));
            // 再表示ボタンを使用したか判定
            if (!(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG) == null || "".equals(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG)))){
                // 退避
                baseDate = TmgUtil.getFirstDayOfMonth(referList.getRecordDate(),TmgUtil.Cs_PARAM_THIS_MONTH);
                // 表示用
                setObjWorkDate(TmgUtil.getFirstDayOfMonth(referList.getRecordDate(),TmgUtil.Cs_PARAM_THIS_MONTH));
            }
        }
    }

}
