package jp.smartcompany.job.modules.tmg.deptstatlist;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.deptstatlist.dto.DispItemsDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.util.CommonUI;
import jp.smartcompany.job.modules.tmg.util.CusomCsvUtil;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final CusomCsvUtil csvUtil;
    /**
     * IMastGenericDetailService
     */
    private final IMastGenericDetailService iMastGenericDetailService;

    /**
     * ITmgMonthlyService
     */
    private final ITmgMonthlyService iTmgMonthlyService;

    /**
     * ITmgTriggerService
     */
    private final ITmgTriggerService iTmgTriggerService;

    /**
     * IHistDesignationService
     */
    private final IHistDesignationService iHistDesignationService;

    /**
     * 汎用参照オブジェクト
     */
    public TmgReferList referList = null;

    /**
     * ドメイン
     */
    private String beanDesc = "DeptStatListBean";

    /**
     * 更新プログラムIDを表すコードです。
     */
    public static final String PROGRAMID = "DeptStatList_ACT_Disp_RList";

    /**
     * 1ページ辺りの行数
     */
    public static final int LINE_PER_PAGE = 50;
    /**
     * ダウンロードコンテンツタイプ
     */
    public static final String DOWNLOAD_CONTENT_TYPE = "application/octetstream;charset=Shift_JIS";

    /**
     * OS改行文字
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 勤怠トリガーテーブルへのインサート処理を実行します。
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void executeInsertTmgTrigger(String txtDYYYYMM, PsDBBean psDBBean) {

        // ログインユーザコード
        String pLoginUserCode = psDBBean.getUserCode();

        // 基準日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String pBaseDate = sdf.format(new Date());

        //トリガーの挿入
        iTmgTriggerService.buildSQLInsertTrigger(pLoginUserCode, pBaseDate, referList.buildSQLForSelectEmployees(), txtDYYYYMM);

        //トリガーの削除
        iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query()
                .eq("TTR_CMODIFIERUSERID", pLoginUserCode)
                .eq("TTR_CMODIFIERPROGRAMID", PROGRAMID)
                .eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", (psDBBean.getCompCode())));
    }

    /**
     * 表示月遷移リスト情報を取得する
     *
     * @return List<DispMonthlyVO>
     */
    public List<DispMonthlyVO> getWorkDateList() {
        // 表示月遷移リスト情報取得
        return iTmgMonthlyService.buildSQLForSelectDispTmgMonthlyList(referList.getRecordDate(), referList.buildSQLForSelectEmployees());
    }

    /**
     * 参照画面表示処理を実行します。
     */
    public Map executeDispStatList(String txtDYYYYMM, int page, PsDBBean psDBBean) {

        Map resultMap = new HashMap();
        if (referList.getTargetSec() == null) {
            return resultMap;
        }

        // 表示項目のヘッダー・職員毎select句・部署別合計用select句・テーブルセル幅をTMG_DISPDEPTSTATLISTマスタから取得し、dispItemMapにセットする。
        List<DispItemsDto> dispItemsDtoList = this.iMastGenericDetailService.buildSQLForSelectTmgDispdeptStatlist(psDBBean.getCustID(),
                psDBBean.getTargetComp(),
                psDBBean.getLanguage(),
                txtDYYYYMM);

        List<Map> dispItemsList = new ArrayList<>();
        Map title = new HashMap();
        title.put(CommonUI.TITLE, "氏名");
        title.put(CommonUI.WIDTH, CommonUI.WIDTH_100);
        title.put(CommonUI.KEY, "EMPNAME");
        dispItemsList.add(title);
        for (DispItemsDto dispItemsDto : dispItemsDtoList) {
            title = new HashMap();
            title.put(CommonUI.TITLE, dispItemsDto.getMgdCitemname());
            title.put(CommonUI.KEY, dispItemsDto.getTempColumnid());
            title.put(CommonUI.WIDTH, CommonUI.WIDTH_36);
            dispItemsList.add(title);
        }

        // タイトルを保存する
        resultMap.put("dispItemsList", dispItemsList);

        //対象部署に属する社員全ての合計値を取得
        Map sectionMap = iTmgMonthlyService.buildSQLSelectSection(dispItemsDtoList, referList.buildSQLForSelectEmployees(), txtDYYYYMM);
        sectionMap.put("EMPNAME", "合計");

        // 部署別統計を保存する
        resultMap.put("sectionMap", sectionMap);

        //ページ
        //社員別のデータを取得
        int startSeq = (page - 1) * LINE_PER_PAGE + 1;
        int endSeq = page * LINE_PER_PAGE;

        List<Map> employyesMap = iTmgMonthlyService.buildSQLSelectEmployyes(dispItemsDtoList, referList.buildSQLForSelectEmployees(), txtDYYYYMM, startSeq, endSeq);

        // 個人別情報 を保存する
        resultMap.put("employyesMap", employyesMap);

        return resultMap;
    }

    /**
     * 部署別統計情報データCSV出力処理
     */
    public void executeDownloadDownload(String txtDYYYYMM, PsDBBean psDBBean) throws Exception {

        // CSV出力ヘッダー・項目取得
        List<ItemVO> headerList = iMastGenericDetailService.buildSQLForSelectTmgDeptstatcsvitems(
                psDBBean.getCustID(),
                psDBBean.getTargetComp(),
                psDBBean.getLanguage(),
                txtDYYYYMM);

        List<Map> mapList = iHistDesignationService.buildSQLForSelectCSVOutputImage(txtDYYYYMM, headerList, referList.buildSQLForSelectEmployees());

        // ファイル名セット(フォーマット：yyyymm_部署名.csv)
        String downloadFileName = new SimpleDateFormat("yyyymm").format(new SimpleDateFormat("yyyy/mm/dd").parse(txtDYYYYMM)) + "_"
                + referList.getTargetSecName() + ".csv";

        List<List<Object>> rowData = CollUtil.newArrayList();
        List<Object> rowTilte = CollUtil.newArrayList();
        for (ItemVO header : headerList) {
            rowTilte.add(StrUtil.nullToEmpty(header.getMgdCheader()));
        }
        rowData.add(rowTilte);

        // 明細行
        for (Map mapRow : mapList) {
            List<Object> rowDate = CollUtil.newArrayList();

            for (int col = 0; col < headerList.size(); col++) {
                rowDate.add(StrUtil.nullToEmpty(String.valueOf( mapRow.get(headerList.get(col).getTempColumnid()))));
            }

            rowData.add(rowDate);

        }

        csvUtil.writeCsv(downloadFileName,rowData);

    }

    /**
     * メイン処理
     *
     * @throws Exception
     */
    public void execute(PsDBBean psDBBean) throws Exception {

        //リクエストからパラメータを取得
        //基準日の取得
        String baseDate = (String) psDBBean.getRequestHash().get(TmgReferList.TREEVIEW_KEY_RECORD_DATE);

        referList = new TmgReferList(psDBBean, beanDesc, baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
    }


//    /**
//     * 前翌月リンクを取得
//     */
//    public LinkOfMonthVO buildSQLSelectLinkOfMonth(){
//        LinkOfMonthVO linkOfMonthVO = new LinkOfMonthVO();
//            if (referList.getTargetSec() == null) {
//            return linkOfMonthVO;
//        }
//
//        //前月リンクを取得
//        preMonth = iTmgMonthlyService.buildSQLSelectLinkOfPreMonth(referList.buildSQLForSelectEmployees(), baseDate);
//        //翌月リンクを取得
//        nextMonth = iTmgMonthlyService.buildSQLSelectLinkOfNextMonth(referList.buildSQLForSelectEmployees(), baseDate);
//
//        linkOfMonthVO.setPreMonth(preMonth);
//        linkOfMonthVO.setNextMonth(nextMonth);
//
//        return linkOfMonthVO;
//    }

//
//    /**
//     * 表示中のページ
//     */
//    private int giPage = 1;
//
//    /**
//     * 表示中のページ
//     */
//    public void setPage(int i) {
//        giPage = i;
//    }
//
//    public int getPage() {
//        return giPage;
//    }


//    /**
//     * 勤怠シートの参照権限(基準日月)
//     */
//    boolean authorityMonth = false;
//
//    /**
//     * 勤怠シートの参照権限(基準日)設定メソッド
//     */
//    public void setAuthorityMonth(boolean bValue) {
//        authorityMonth = bValue;
//    }
//
//    /**
//     * 勤怠シートの参照権限(基準日)取得メソッド
//     */
//    public boolean getAuthorityMonth() {
//        return authorityMonth;
//    }
//
//    /**
//     * 参照権限フラグ：参照可能
//     */
//    private static final boolean CB_CAN_REFER = true;
//
//    /**
//     * 引数で指定されたyyyy/mm/dd形式の日付文字列を「/」で分割しint型配列に格納します。
//     *
//     * @param date 基準日 (「yyyy/mm/dd」形式の文字列)
//     * @throws NumberFormatException
//     * @throws ArrayIndexOutOfBoundsException
//     */
//    public int[] devideDateFormatString(String date) {
//
//        int[] dates = new int[3]; //日付値格納配列
//
//        StringTokenizer st = new StringTokenizer(date, "/");
//        for (int i = 0; st.hasMoreTokens(); i++) {
//            dates[i] = Integer.parseInt(st.nextToken());
//        }
//
//        return dates;
//    }

//    /**
//     * 引数で指定された値分だけ基準日の月を追加し、その月の月初めの日付を返却します。
//     *
//     * @param date    基準日 (「yyyy/mm/dd」形式の文字列)
//     * @param mvValue 追加したい月の値
//     * @return String  移動した年月日 - ※日は月初(1日)になります。
//     */
//    public String addMonthOfDateFromatString(String date, int mvValue) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        Date objDate = null;
//
//        // 日付文字列を分割
//        int dates[] = devideDateFormatString(date);
//        Calendar calendar = Calendar.getInstance();
//        // カレンダーに設定
//        calendar.set(dates[0], dates[1], dates[2]);
//
//        // 月の追加処理
//        calendar.add(Calendar.MONTH, mvValue - 1);
//        calendar.set(Calendar.DATE, 1);
//
//        objDate = calendar.getTime();
//
//        return df.format(objDate);
//
//    }

//    /**
//     * 勤怠シートの参照権限(基準日の翌月)
//     */
//    boolean authorityNextMonth = false;
//    /**
//     * 基準日の前月
//     */
//    private String preMonth = null;
//    /**
//     * 基準日の翌月
//     */
//    private String nextMonth = null;
//    /**
//     * 参照権限フラグ：参照不可能
//     */
//    private static final boolean CB_CANT_REFER = false;

//    /**
//     * 勤怠シートの参照権限(基準日の翌月)設定メソッド
//     */
//    public void setAuthorityNextMonth(boolean bValue) {
//        authorityNextMonth = bValue;
//    }
//
//    /**
//     * 勤怠シートの参照権限(基準日の翌月)取得メソッド
//     */
//    public boolean getAuthorityNextMonth() {
//        return authorityNextMonth;
//    }



//        String txtDYYYYMM,String txtPage,

//        //基準日から勤務年月を設定
//        setObjWorkDate(baseDate);

//        // 当日・当月日付の情報を再格納する
//        if (referList.getRecordDate() != null) {
//            // 当月情報更新
//            setThisMonth(TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), TmgUtil.Cs_PARAM_THIS_MONTH));
//        }
//
//        // 2007/10/03  H.Kawabata  #248 勤怠系コンテンツの参照権限の統一対応
//        // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
//        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId())) {
//            String sAction = getRequestString(txtAction);
//            String sTargetSec = getReferList().getTargetSec();
//
//            // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
//            if ((TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) && (sAction == null || sAction.length() == 0))
//                    || (TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()) && !(sTargetSec == null || sTargetSec.length() == 0) && (sAction == null || sAction.length() == 0))) {
//
//                // 参照権限チェック(現在時点での年月)
//                if (getReferList().existsAnyone(getSysdate()) &&
//                        getReferList().isThereSomeEmployees(getSysdate())) {
//                    setAuthorityMonth(CB_CAN_REFER);
//
//                    // 参照権限が無い場合は、1ヶ月過去のシートの権限をチェックする。
//                } else {
//                    String sPrevMonth = addMonthOfDateFromatString(getSysdate(), -1);
//                    // 汎用参照コンポーネントの基準日を基準日の前月(過去)に設定しなおす
//                    referList = new TmgReferList(psDBBean, beanDesc, sPrevMonth, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
//
//                    // 参照権限チェック(現在時点より1年度過去の年度)
//                    if (getReferList().existsAnyone(sPrevMonth) &&
//                            getReferList().isThereSomeEmployees(sPrevMonth)) {
//
//                        // 対象年月が現在の年月の場合、1ヶ月過去の年月を対象年月に設定します
//                        // このif文は、現在「部署A」を選択していて対象年月が変更された状態で「組織B」を選択しなおすと
//                        // 「組織B」の現在日付時点の年月と、その年月-1ヶ月時点での参照権限をチェックします。
//                        // その際に、変更後対象年月が現在年月でない場合にも現在年月-1ヶ月を設定されるのを防ぐ為
//                        // 「対象年月が現在の年月の場合」という条件を実装しています。
//                        if (getSysdate().equals(baseDate)) {
//                            // 対象年月を1ヶ月過去に設定します
//                            baseDate = sPrevMonth;
//                            //基準日から勤務年月を設定
//                            setObjWorkDate(baseDate);
//
//                            // 検索対象年月の前月
//                            preMonth = addMonthOfDateFromatString(baseDate, -1);
//                            // 検索対象年月の翌月
//                            nextMonth = addMonthOfDateFromatString(baseDate, 1);
//                        }
//                        setAuthorityMonth(CB_CAN_REFER);
//                    } else {
//                        // 対象年月を元に戻します
//                        referList = new TmgReferList(psDBBean, beanDesc, baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
//
//                        setAuthorityMonth(CB_CANT_REFER);
//                    }
//                }
//
//                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
//                // 翌月の権限があればリンク「>」を画面に表示する。
//                // 権限が無い場合は「>」を表示しない。
//                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
//                //   権限が無いのと同じ扱いとする。
//                String sNextMonth = addMonthOfDateFromatString(baseDate, 1);
//                if (getReferList().existsAnyone(sNextMonth) && getReferList().isThereSomeEmployees(sNextMonth)) {
//                    setAuthorityNextMonth(CB_CAN_REFER);
//                } else {
//                    setAuthorityNextMonth(CB_CANT_REFER);
//                }
//
//                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
//                // 翌月の権限があればリンク「>」を画面に表示する。
//                // 権限が無い場合は「>」を表示しない。
//                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
//                //   権限が無いのと同じ扱いとする。
//            } else if (!(sTargetSec == null || sTargetSec.length() == 0)) {
//
//                // 参照権限の判定：設定(当月、当月-1ヶ月)
//                // 当月もしくは、先月どちらかの権限が有効な場合は過去に関しては常に表示する(シートがある限り)
//                String sPrevMonthSysdate = addMonthOfDateFromatString(getSysdate(), -1);
//                if ((getReferList().existsAnyone(getSysdate()) && getReferList().isThereSomeEmployees(getSysdate())) ||
//                        (getReferList().existsAnyone(sPrevMonthSysdate) && getReferList().isThereSomeEmployees(sPrevMonthSysdate))) {
//                    setAuthorityMonth(CB_CAN_REFER);
//                } else {
//                    setAuthorityMonth(CB_CANT_REFER);
//                }
//
//                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
//                // 翌月の権限があればリンク「>」を画面に表示する。
//                // 権限が無い場合は「>」を表示しない。
//                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
//                //   権限が無いのと同じ扱いとする。
//                String sNextMonthSysdate = addMonthOfDateFromatString(baseDate, 1);
//                if (getReferList().existsAnyone(sNextMonthSysdate) && getReferList().isThereSomeEmployees(sNextMonthSysdate)) {
//                    setAuthorityNextMonth(CB_CAN_REFER);
//                } else {
//                    setAuthorityNextMonth(CB_CANT_REFER);
//                }
//            }
//            // その他のサイトの場合
//        } else {
//            setAuthorityMonth(CB_CAN_REFER);
//        }

//    }

//    /**
//     * 基準日
//     */
//    public String baseDate = null;
//
//    public String getRequestString(String txtDYYYYMM) {
//
//        String data = txtDYYYYMM;
//        if (data == null) {
//            data = "";
//        }
//
//        return data;
//    }
//
//    /**
//     * 今月日付取得処理
//     * SYSDATE時点の年月の1日の日付を返します。
//     * ex)現在「2007年9月23日」の場合は「2007年9月1日」の情報が返却されます。
//     * また、返却される際の日付書式はは「YYYY/MM/DD」です。
//     *
//     * @return 今月の月初めの日付(YYYY / MM / DD)形式
//     */
//    private String getSysdate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
//        return sdf.format(new Date()) + "/01";
//    }
//
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
//     * 対象勤務年月
//     */
//    private String objWorkDate = null;
//    /**
//     * システム年月日(今月)
//     */
//    private String thisMonth = "";

//    /**
//     * 基準日から勤務年月を設定します。
//     */
//    private void setObjWorkDate(String baseDate) {
//        int[] date = divideDate(baseDate);
//
//        String labelYear = "年";
//        String labelDay = "月";
//        StringBuffer wrkDate = new StringBuffer();
//        wrkDate.append(String.valueOf(date[0]));
//        wrkDate.append(labelYear);
//        wrkDate.append(String.valueOf(date[1]));
//        wrkDate.append(labelDay);
//        objWorkDate = wrkDate.toString();
//    }
//    /** 勤務年月を返却します。 */
//    public String getObjWorkDate() {
//        return objWorkDate;
//    }
//
//
//    public void setThisMonth(String thisMonth) {
//        this.thisMonth = thisMonth;
//    }


}
