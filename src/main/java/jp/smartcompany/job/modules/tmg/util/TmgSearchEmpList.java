package jp.smartcompany.job.modules.tmg.util;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.evaluatersetting.EvaluatorSettingBean;

public class TmgSearchEmpList {

    private PsDBBean bean;
    private String beanDesc;

    // リスト作成のための各種オブジェクト
    private TmgOrgTree orgTree;
    private TmgEmpList empList;

    private String targetSection;
    private boolean isViewAll;

    // TmgSearchEmpListオブジェクトをリクエストパラメータへ一時退避する際のキー
    public static final String REQUEST_KEY_TMG_SEARCH_EMP_LIST_OBJECT = "TmgSearchEmpListObject";
    /**
     *  管理サイトにおいて、社員一覧の検索に管理対象フラグを使用するかどうかをセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_USEMANAGEFLG  = "UseManageFlg";

    // ツリービュー用の検索結果をセッションに登録する際のキー
    public static final String SESSION_KEY_ORGTREE_RESULT    = "TmgOrgTreeResult";
    public static final String SESSION_KEY_EMPLIST_RESULT    = "TmgEmpListResultForTmgSearchEmpList";
    public static final String SESSION_KEY_EMPLIST_CONDITION = "TmgEmpListConditionForTmgSearchEmpList";

    // 勤怠管理サイト：組織ツリー作成時の条件
    public static final String SESSION_KEY_ORGTREE_CONDITION = "TmgOrgTreeConditionTargetSection";

    // ツリービューを使用する際の、HTMLオブジェクトのIDなどに対する接頭辞
    public static final String TREEVIEW_OBJ_HEADER = "TmgSearchEmpListTreeView";

    // リクエストパラメータorセッションに登録する際のキー
    public static final String TREEVIEW_KEY_TARGET_SECTION = "txt"+TREEVIEW_OBJ_HEADER+"TargetSection";

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します<br>
     * @param  bean     呼出元のBean
     * @param  beanDesc 呼出元のBeanの名称
     * @param  initSec  初期選択組織の組織コード
     */
    public TmgSearchEmpList(
            PsDBBean bean,
            String beanDesc,
            String initSec
    ) {
        this.bean = bean;
        this.beanDesc = beanDesc;
        init(initSec, null, true, false);
    }

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します<br>
     * @param  bean     呼出元のBean
     * @param  beanDesc 呼出元のBeanの名称
     * @param  initSec  初期選択組織の組織コード
     * @param  psDate   検索基準日
     */
    public TmgSearchEmpList(
            PsDBBean bean,
            String beanDesc,
            String initSec,
            String psDate
    ) {
        this.bean = bean;
        this.beanDesc = beanDesc;
        init(initSec, psDate, true, false);
    }

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します<br>
     * @param  bean      呼出元のBean
     * @param  beanDesc  呼出元のBeanの名称
     * @param  initSec   初期選択組織の組織コード
     * @param  psDate    検索基準日
     */
    public TmgSearchEmpList(
            PsDBBean bean,
            String beanDesc,
            String initSec,
            String psDate,
            boolean pbViewAll
    ) {
        this.bean = bean;
        this.beanDesc = beanDesc;
        init(initSec, psDate, pbViewAll, false);
    }

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します<br>
     * @param  bean          呼出元のBean
     * @param  beanDesc      呼出元のBeanの名称
     * @param  initSec       初期選択組織の組織コード
     * @param  psDate        検索基準日
     * @param  pbViewAll     検索対象範囲設定を条件に加えるかどうか,
     * @param  useManageFLG  管理対象外職員を表示するかの判定フラグ（true：含める、false：含めない）
     */
    public TmgSearchEmpList(
            PsDBBean bean,
            String beanDesc,
            String initSec,
            String psDate,
            boolean pbViewAll,
            boolean useManageFLG
    ) {
        this.bean = bean;
        this.beanDesc = beanDesc;
        init(initSec, psDate, pbViewAll, useManageFLG);
    }

    /**
     * 初期処理
     *
     * @param  initSection  初期選択組織の組織コード
     * @param  psDate       検索基準日
     * @param  pbViewAll    検索対象範囲設定を条件に加えるかどうか,
     * @param  useManageFLG 管理対象外職員を表示するかの判定フラグ（true：含める、false：含めない）
     */
    private void init(String initSection, String psDate, boolean pbViewAll, boolean useManageFLG) {
        // 対象組織の組織コードをリクエストパラメータから取得
        targetSection = bean.getReqParam(TREEVIEW_KEY_TARGET_SECTION);
        // リクエストパラメータに存在しない場合、引数の値を使用
        if(targetSection == null || targetSection.equals("")){
            targetSection = initSection;
        }

        // 管理サイトの場合→引数に応じて、組織ツリーを初期化
        // bWithTarget : 検索対象範囲設定を適用するかどうか
        boolean bWithTarget = !"ON".equalsIgnoreCase(bean.getSystemProperty(EvaluatorSettingBean.TMG_EVASET_VIEWALL));
        if (pbViewAll) {
            createOrgTree(psDate, bWithTarget);
        }
        // 社員一覧を初期化
        if(targetSection != null && !targetSection.equals("")){
            createEmpList(psDate, bWithTarget, useManageFLG);
        }
        this.isViewAll = pbViewAll;
    }

    /**
     * 勤怠管理サイト用に、組織ツリーを作成します
     * @param psDate	基準日
     * @param pbWithTarget	true: 検索対象範囲設定を使用、false: 全学から選択
     */
    private void createOrgTree(String psDate, boolean pbWithTarget){
        String sDate;
        if (psDate != null && !psDate.equals("")) {
            sDate = "TO_DATE('" + psDate + "', 'yyyy/MM/dd')";
        } else {
            sDate = "SYSDATE";
        }
        orgTree = new TmgOrgTree(bean,beanDesc, pbWithTarget);
        /* セッションのデータを使うには、検索対象範囲設定が同じで、基準日も同じで、管理対象者フラグを制御も同じ必要があるので、いっそのことセッションを使わないようにする */
        try{
            orgTree.createOrgTree("'"+bean.getCustID()+"'", "'"+bean.getCompCode()+"'", "'"+bean.getLanguage()+"'", sDate);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 対象部署について社員一覧を作成します。
     * SYSDATE時点で、指定した部署に所属している社員の一覧を作成します。
     * なお、作成される社員一覧には、兼務の社員も含まれます。
     * @param psDate	基準日
     * @param pbWithTarget	true: 検索対象範囲設定を使用、false: 全学から選択
     * @param useManageFLG 管理対象外職員を表示するかの判定フラグ（true：含める、false：含めない）
     */
    private void createEmpList(String psDate, boolean pbWithTarget, boolean useManageFLG) {
        String sDate;
        if (psDate != null && !psDate.equals("")) {
            sDate = "TO_DATE('" + psDate + "', 'yyyy/MM/dd')";
        } else {
            sDate = "SYSDATE";
        }
        empList = new TmgEmpList(bean, beanDesc, pbWithTarget);
        try{
            empList.createEmpList(
                    "'"+bean.getCustID()+"'",
                    "'"+bean.getCompCode()+"'",
                    "'"+targetSection+"'",
                    sDate,
                    sDate,
                    "'"+bean.getLanguage()+"'",
                    false, // 兼務を含める
                    false,
                    useManageFLG); // 管理対象外職員を検索結果に含むかどうかの設定
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 作成された社員一覧の内容に従って、ツリービュー作成用のJSON配列を生成して返します。
     * 勤怠管理サイトの個人データを表示するタイプのコンテンツで使用します。
     */
    public String getJSONArrayForEmpList(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN) || isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(empList == null){
                return null;
            }
            return empList.getJSONArrayForTreeViewGroupBySection(targetSection);
        }else{
            return null;
        }
    }


    /**
     * 作成された組織ツリーの内容に従って、ツリービュー作成用のJSON配列を生成して返します。
     * 勤怠管理サイトの各種コンテンツで使用します。
     * @return String JSON配列
     */
    public String getJSONArrayForOrgTree(){
        if (isViewAll) {
            if(orgTree == null){
                return null;
            }
            return orgTree.getJSONArrayForTreeView();
        }else{
            return null;
        }
    }

    /**
     * 現在のサイトが、指定したサイトかどうかを判断します
     * @param siteId
     * @return boolean true:指定したサイトである false:指定したサイトではない
     */
    private boolean isSite(String siteId){
        return bean.getSiteId().equals(siteId);
    }


}
