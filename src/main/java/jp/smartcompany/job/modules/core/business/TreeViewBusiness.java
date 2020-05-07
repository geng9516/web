package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.entity.TmgMgdMsgSearchTreeView;
import jp.smartcompany.job.modules.core.service.ITmgMgdMsgSearchTreeViewService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.util.TmgEmpList;
import jp.smartcompany.job.modules.tmg.util.TmgMemberList;
import jp.smartcompany.job.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xiao Wenpeng
 * コンテンツ埋め込み型の組織ツリー＆社員リストを提供するクラスです。 -> 对应旧就业的ps.c01.tmg.util.TmgReferList
 *
 * 勤怠承認サイトの場合は、現在日付-targetDateの範囲で参照可能なグループ/社員の一覧を作成します。
 * 勤怠管理サイトの場合は、現在日付時点の組織ツリーと、選択された部署に所属している社員の一覧を作成します。
 *
 * 使用可能なツリービューのタイプは、以下の3種類があります
 * 個人系(個人を選択して表示するタイプ)
 * 一覧系(部署/グループを選択して、所属している社員を一覧表示するタイプ)
 * 一覧系/部署(一覧系で、部署ごとに表示するタイプ)
 *
 * 引数を指定する際は、TmgReferListに定義されている以下の定数を使用します
 * TREEVIEW_TYPE_EMP
 * TREEVIEW_TYPE_LIST
 * TREEVIEW_TYPE_LIST_SEC
 * 例）承認状況一覧は「一覧系」なので、treeViewTypeにTREEVIEW_TYPE_LISTを指定する
 * 勤怠管理サイトの場合、表示される社員一覧について、TMG_EMPLOYEESと結合するかどうか指定します。
 * この値は、常にtrueを指定してください。
 *
 * TmgReferListオブジェクトの各メソッドを使用することで、検索対象社員のデータを取得することができます。
 * 個人系：getTargetEmployee()
 * 一覧系：buildSQLForSelectEmployees()
 * ビュータイプで個人系を指定していた場合、getTargetEmployeeメソッドを使用することができます。
 * ビュータイプで一覧系もしくは一覧系/部署を指定していた場合、buildSQLForSelectEmployeesメソッドを使用することができます。
 *
 */
@Service(CoreBean.Business.TREE_VIEW)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TreeViewBusiness {

    private TmgMemberList memberList;
    private TmgEmpList empList;

    private final PsDBBean psDBBean;
    private final ITmgMgdMsgSearchTreeViewService iTmgMgdMsgSearchTreeViewService;
    private final HttpSession session;


    /**
     *  検索対象日付をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_TARGETDATE       = "TargetDate";

    /**
     * 「個人系」を表すツリービュータイプ定数です。
     */
    public static final int TREEVIEW_TYPE_EMP      = 11;
    /**
     * 「一覧系」を表すツリービュータイプ定数です。
     */
    public static final int TREEVIEW_TYPE_LIST     = 21;
    /**
     * 「一覧系/部署ごと」を表すツリービュータイプ定数です。
     */
    public static final int TREEVIEW_TYPE_LIST_SEC = 31;
    /**
     * 「一覧系(部局)」を表すツリービュータイプ定数です。
     */
    public static final int TREEVIEW_TYPE_DIVLIST     = 41;

    /**
     * 「一覧系/病棟ごと」を表すツリービュータイプ定数です。
     */
    public static final int TREEVIEW_TYPE_LIST_WARD = 51;

    /**
     * 「一覧系/個人ごと」を表すツリービュータイプ定数です。
     */
    public static final int TREEVIEW_TYPE_EMP_WARD = 61;


    // ツリービューを使用する際の、HTMLオブジェクトのIDなどに対する接頭辞
    public static final String TREEVIEW_OBJ_HEADER_ADMIN       = "TmgReferListTreeViewAdmin";
    public static final String TREEVIEW_OBJ_HEADER_PERM        = "TmgReferListTreeViewPerm";
    public static final String TREEVIEW_OBJ_HEADER             = "TmgReferListTreeView";
    public static final String TREEVIEW_OBJ_HIDSELECT          = "hidSelectTab";
    public static final String TREEVIEW_OBJ_HIDSEARCHITEMES    = "hidSearchItems";
    public static final String TREEVIEW_OBJ_HIDSEARCHCONDITION = "hidSearchCondition";
    public static final String TREEVIEW_OBJ_HIDSEARCHDATA      = "hidSearchData";

    // リクエストパラメータorセッションに登録する際のキー(共通)
    public static final String TREEVIEW_KEY_RECORD_DATE    = "txt" + TREEVIEW_OBJ_HEADER + "RecordDate";
    // リクエストキー - 再表示ボタン使用判定用
    public static final String TREEVIEW_KEY_REFRESH_FLG  = "txt" + TREEVIEW_OBJ_HEADER + "RefreshFlg";

    // リクエストパラメータorセッションに登録する際のキー(勤怠管理サイト用)
    public static final String TREEVIEW_KEY_ADMIN_TARGET_SECTION = "txt"+TREEVIEW_OBJ_HEADER_ADMIN+"TargetSection";
    public static final String TREEVIEW_KEY_ADMIN_TARGET_EMP     = "txt"+TREEVIEW_OBJ_HEADER_ADMIN+"TargetEmp";
    public static final String TREEVIEW_KEY_ADMIN_TARGET_WARD    = "txt"+TREEVIEW_OBJ_HEADER_ADMIN+"TargetWard";

    // リクエストパラメータorセッションに登録する際のキー(勤怠承認サイト用)
    public static final String TREEVIEW_KEY_PERM_TARGET_SECTION  = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetSection";
    public static final String TREEVIEW_KEY_PERM_TARGET_GROUP    = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetGroup";
    public static final String TREEVIEW_KEY_PERM_TARGET_EMP      = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetEmp";
    public static final String TREEVIEW_KEY_PERM_SELECTED_VIEW   = "txt"+TREEVIEW_OBJ_HEADER_PERM+"SelectedView";

    // 勤怠承認サイトにおいて、選択されているビューのタイプを表す
    public static final String PERM_SELECTEDVIEW_SECTION = "section";
    public static final String PERM_SELECTEDVIEW_GROUP   = "group";
    public static final String PERM_SELECTEDVIEW_ALL     = "all";


    // 勤怠管理サイト：組織ツリー作成時の条件
    public static final String SESSION_KEY_ORGTREE_CONDITION = "TmgOrgTreeConditionTargetSection";
    // 勤怠管理サイト：部局ツリー作成時の条件
    public static final String SESSION_KEY_DIVTREE_CONDITION = "TmgDivTreeConditionTargetSection";
    // 勤怠管理サイト：全部局参照可否
    public static final String SESSION_KEY_DIVTREE_ALL = "TmgDivTreeConditionAllDivision";
    // 勤怠管理サイト：ルート組織
    public static final String SESSION_KEY_DIVTREE_ROOT = "TmgDivTreeConditionRootSection";
    // 勤怠管理サイト：検索対象範囲EXISTS句
    public static final String SESSION_KEY_SEARCHRANGE = "TmgSearchRange";

    // 勤怠管理サイト：病棟ツリー作成時の条件
    public static final String SESSION_KEY_WARDTREE_CONDITION = "TmgWardTreeConditionTargetSection";

    // 勤怠管理サイト：社員一覧作成時の条件
    public static final String SESSION_KEY_EMPLIST_CONDITION = "TmgEmpListConditionTargetSection";

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

    //編集担当者選択用のマスタグループID
    public static final String GROUPID_DAYNIGHT_EDITOR	= "TMG_DAYNIGHT_EDITOR";	//宿日直割振編集担当者
    public static final String GROUPID_DUTYHOURS_EDITOR	= "TMG_DUTYHOURS_EDITOR";	//交替制勤務割振編集担当者

    // 勤怠管理サイトにおいて、画面上で選択された部署・社員を保持する
    private String targetSec_admin = null;
    private String targetEmp_admin = null;

    // 勤怠管理サイトにおいて、画面上で選択された勤務先グループを保持する
    private String targetWard_admin = null;

    // 勤怠承認サイトにおいて、画面上で選択された部署・グループ・社員・ビューを保持する
    private String targetSec_perm = null;
    private String targetGroup_perm = null;
    private String targetMember_perm = null;
    private String selectedView_perm = null;

    // 勤怠承認サイトにおいて、画面上で選択された勤務先グループを保持する
    private String targetWard_perm = null;

    // オブジェクト生成時の「SYSDATE」をGrecorianCalendarで保持する（クエリ作成などに使用する）
    private Date gcSysdate = null;
    // グループ一覧作成時に使用される「前月初日」の日付です
    private Date gcPreMonthDate = null;
    // メンバー一覧作成時に使用される「前年度初日」の日付です
    private Date gcPreYearDate = null;
    // 基準日保持用
    private String gsRecordDate = null;
    // 基準日使用判定用
    private boolean gbUseRecordDate = true;
    // セッションに保持してある日付用
    private String gsSessionDate = null;

    // 現在日付よりも過去かチェックする
    private boolean gbCheckToDayFlg = true;

    // 日付フォーマット
    private final String DATE_FORMAT = "yyyy/MM/dd";

    // 最大決裁レベル
    private final int giApprovalLevelMax = 2147483647;
    // 最小決裁レベル
    private final int giApprovalLevelMin = -2147483647;

    /**
     * TmgReferListオブジェクトをリクエストパラメータへ一時退避する際のキーです。
     */
    public static final String REQUEST_KEY_TMG_REFER_LIST_OBJECT = "TmgReferListObject";

    /**
     * 管理サイトにおいて、組織ツリーの検索結果をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_ORGTREE_RESULT    = "TmgOrgTreeResult";
    /**
     * 管理サイトにおいて、部局ツリーの検索結果をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_DIVTREE_RESULT    = "TmgDivTreeResult";

    /**
     * 管理サイトにおいて、病棟ツリー（リスト）の検索結果をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_WARDTREE_RESULT    = "TmgWardTreeResult";

    /**
     * 管理サイトにおいて、社員一覧の検索結果をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_EMPLIST_RESULT    = "TmgEmpListResult";
    /**
     * 管理サイトにおいて、グループ一覧の検索結果をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_GROUPLIST_RESULT  = "TmgGroupListResult";
    /**
     * 管理サイトにおいて、メンバー一覧の検索結果をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_MEMBERLIST_RESULT = "TmgMemberListResult";

    /**
     * 現在日付をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_SYSDATE = "TmgReferListSYSDATE";
    /**
     * 前月初日をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_PRE_MONTH_DATE = "TmgReferListPRE_MONTH_DATE";
    /**
     * 前年度初日をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_PRE_YEAR_DATE = "TmgReferListPRE_YEAR_DATE";
    /**
     *  管理サイトにおいて、社員一覧の検索に管理対象フラグを使用するかどうかをセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_USEMANAGEFLG  = "UseManageFlg";
    /**
     *  組織ツリー検索タブで使用する値をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_SEARCHITEMS      = "SearchItems";
    public static final String SESSION_KEY_SEARCHCONDITION  = "SearchCondition";
    public static final String SESSION_KEY_SEARCHDATA       = "SearchData";
    public static final String SESSION_KEY_SEARCHDATAARRAY  = "SearchDataArray";
    public static final String SESSION_KEY_DISPLIMIT4TREE   = "DispLimi4Tree";


    /**
     * 現在入力されている検索内容を返却する
     * @param なし
     */
    private String searchData = null;
    public String getSearchData() {
        if (searchData == null){
            searchData = "";
//            if (bean.getReqParm(TREEVIEW_OBJ_HIDSEARCHDATA) != null){
//                gsSearchData = bean.getReqParm(TREEVIEW_OBJ_HIDSEARCHDATA);
//            } else if (session.getAttribute(SESSION_KEY_SEARCHDATA) != null){
//                gsSearchData = (String)bean.session.getAttribute(SESSION_KEY_SEARCHDATA);
//            }
        }
        return searchData;
    }

    /**
     * 検索上限値を返却する。(对应旧代码3468-3485)
     */
    private String dispLimit4Tree = null;
    public String getDispLimit4Tree(){
        if (StrUtil.isNotBlank(dispLimit4Tree)) {
            return dispLimit4Tree;
        }
        if (memberList!=null){
            dispLimit4Tree = memberList.getDispLimit4Tree();
        }
        if (empList!=null) {
            dispLimit4Tree = empList.getDispLimit4Tree();
        }
        return dispLimit4Tree;
    }

    /**
     * 検索タブで表示するメッセージを返却する。(对应旧代码3487-3546)
     */
    private String msgSearchTree = null;
    public String getMsgSearchTree(){
      if (StrUtil.isNotBlank(msgSearchTree)){
          return msgSearchTree;
      }
      List<TmgMgdMsgSearchTreeView> searchTreeViewList = buildSQLForSelectMsgSearchTree(psDBBean.getCustID(),psDBBean.getCompCode(), psDBBean.getLanguage(),DateUtil.date());
      if (CollUtil.isNotEmpty(searchTreeViewList)) {
        msgSearchTree = searchTreeViewList.get(0).getMsg();
      }
      return msgSearchTree;
    }

    /**
     * 名称マスタから検索タブで表示するメッセージを取得するSQLを返却する。
     * @param custId
     * @param compId
     * @param language
     * @param date
     * @return String SQL
     */
    private List<TmgMgdMsgSearchTreeView> buildSQLForSelectMsgSearchTree(String custId, String compId, String language, Date date){
        QueryWrapper<TmgMgdMsgSearchTreeView> qw = SysUtil.query();
        qw.eq("MGD_CCUSTOMERID",custId)
          .eq("MGD_CCOMPANYID_CK_FK",compId)
          .lt("MGD_DSTART_CK",date)
          .gt("MGD_DEND",date)
          .eq("MGD_CLANGUAGE_CK",language)
          .select("MGD_CMSG");
        return iTmgMgdMsgSearchTreeViewService.list(
          qw
       );
    }

}
