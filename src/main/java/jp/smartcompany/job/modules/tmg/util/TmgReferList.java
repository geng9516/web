package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgMgdMsgSearchTreeView;
import jp.smartcompany.job.modules.core.pojo.handler.StringListHandler;
import jp.smartcompany.job.modules.core.service.ITmgMgdMsgSearchTreeViewService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.boot.util.SysDateUtil;
import jp.smartcompany.boot.util.SysUtil;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
@NoArgsConstructor
@Slf4j
@ToString
public class TmgReferList {

    // 返回给前端使用的树状参数键名，前端用来作为公共参数的建明
    public static final String ATTR_TREEVIEW_RECORD_DATE = "TREEVIEW_KEY_RECORD_DATE";
    public static final String ATTR_TREEVIEW_REFRESH_FLG = "TREEVIEW_KEY_REFRESH_FLG";

    public static final String ATTR_TREEVIEW_ADMIN_TARGET_EMP = "TREEVIEW_KEY_ADMIN_TARGET_EMP";
    public static final String ATTR_TREEVIEW_ADMIN_TARGET_SECTION = "TREEVIEW_KEY_ADMIN_TARGET_SECTION";

    public static final String ATTR_TREEVIEW_PERM_TARGET_EMP = "TREEVIEW_KEY_PERM_TARGET_EMP";
    public static final String ATTR_TREEVIEW_PERM_TARGET_SECTION = "TREEVIEW_KEY_PERM_TARGET_SECTION";
    public static final String ATTR_TREEVIEW_PERM_TARGET_GROUP = "TREEVIEW_KEY_PERM_TARGET_GROUP";

    private PsDBBean psDBBean;
    private final ITmgMgdMsgSearchTreeViewService iTmgMgdMsgSearchTreeViewService = (ITmgMgdMsgSearchTreeViewService)SpringUtil.getBean("tmgMgdMsgSearchTreeViewServiceImpl");
    private final DataSource dataSource = (DataSource) SpringUtil.getBean("dataSource");
    private final TmgSearchRangeUtil tmgSearchRangeUtil = (TmgSearchRangeUtil) SpringUtil.getBean("tmgSearchRangeUtil");

    // psApp参数
    private String beanDesc;
    // 目标日期
    private String targetDate;
    // 树状菜单类型
    private int treeViewType = -1;
    // TMG_EMPLOYEEと結合するかどうか
    private boolean isJoinTmgEmployees = true;
    private boolean gbAllDivision = false;
    // 管理対象者外を表示するかどうか（ture：表示する false：表示しない）
    private boolean gbUseManageFLG = false;
    // 引数として渡したtargetDateを基準日として扱うか(true：基準日に設定する false：設定しない）
    private boolean gbIsSetTargetDate = false;
    // 編集担当者のグループコード
    private String gsEditorGroupCode;
    // 組織ツリー検索機能を使用判定用(true:使用、false:使用しない)
    private boolean gbUseSearcjEmp = false;

    /**
      *リスト作成のための各種オブジェクト
      */
    // 勤怠管理サイト用:組織ツリー
    private TmgOrgTree orgTree  = null;
    // 勤怠管理サイト用:部局ツリー
    private TmgDivisionTree  divTree    = null;
    // 勤怠管理サイト用:社員一覧
    private TmgEmpList  empList    = null;

    // 勤怠承認サイト用:グループ一覧
    private TmgGroupList groupList  = null;
    // 勤怠承認サイト用:メンバー一覧
    private TmgMemberList memberList = null;

    /**
     *  検索対象日付をセッションに登録する際のキーです。
     */
    public static final String SESSION_KEY_TARGETDATE   = "TargetDate";

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
    public static final String TREEVIEW_KEY_ADMIN_TARGET_EMP_NAME      = "txt"+TREEVIEW_OBJ_HEADER_ADMIN+"TargetEmpName";
    // xin追加：选中部门名称
    public static final String TREEVIEW_KEY_ADMIN_TARGET_SECTION_NAME = "txt"+TREEVIEW_OBJ_HEADER_ADMIN+"TargetSectionName";

    // リクエストパラメータorセッションに登録する際のキー(勤怠承認サイト用)
    public static final String TREEVIEW_KEY_PERM_TARGET_SECTION  = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetSection";
    public static final String TREEVIEW_KEY_PERM_TARGET_SECTION_NAME  = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetSectionName";
    public static final String TREEVIEW_KEY_PERM_TARGET_GROUP    = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetGroup";
    public static final String TREEVIEW_KEY_PERM_TARGET_GROUP_NAME  = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetGroupName";
    public static final String TREEVIEW_KEY_PERM_TARGET_EMP      = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetEmp";
    public static final String TREEVIEW_KEY_PERM_TARGET_EMP_NAME      = "txt"+TREEVIEW_OBJ_HEADER_PERM+"TargetEmpName";
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

    // 勤怠承認サイトにおいて、画面上で選択された部署・グループ・社員・ビューを保持する
    private String targetSec_perm = null;
    private String targetGroup_perm = null;
    private String targetMember_perm = null;
    private String selectedView_perm = null;


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
     *  # 肖文彭追加 往session里添加当前系统时间
     */
    public static final String SESSION_KEY_CURRENT_DATE = "TmgReferListCurrentDate";
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

    private static final int csSessionControl4SearchTreeInitialization = 1;
    private static final int csSessionControl4SearchTreeSave = 2;

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します。<br>
     * 現在日付-targetDateの範囲で、参照可能な社員の一覧を作成します。<br>
     * @param bean 呼出元のBean
     * @param beanDesc 呼出元のBeanの名称
     * @param targetDate 基準日(YYYY/MM/DD形式)
     * @param treeViewType コンテンツが使用するツリービューのタイプ
     * @param isJoinTmgEmployees 勤怠管理サイトの場合、TMG_EMPLOYEESテーブルと結合するかどうか
     * @throws Exception
     */
    public TmgReferList(
            PsDBBean bean,
            String beanDesc,
            String targetDate,
            int treeViewType,
            boolean isJoinTmgEmployees
    ) throws Exception{
        this.psDBBean = bean;
        this.beanDesc = beanDesc;
        this.targetDate = targetDate;
        this.treeViewType = treeViewType;
        this.isJoinTmgEmployees = isJoinTmgEmployees;
        init();
    }

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します。<br>
     * 現在日付-targetDateの範囲で、参照可能な社員の一覧を作成します。<br>
     * @param bean 呼出元のBean
     * @param beanDesc 呼出元のBeanの名称
     * @param targetDate 基準日(YYYY/MM/DD形式)
     * @param treeViewType コンテンツが使用するツリービューのタイプ
     * @param isJoinTmgEmployees 勤怠管理サイトの場合、TMG_EMPLOYEESテーブルと結合するかどうか
     * @param pbUseRecordDate 組織ツリーの基準日を使用するかどうか
     * @throws Exception
     */
    public TmgReferList(
            PsDBBean bean,
            String beanDesc,
            String targetDate,
            int treeViewType,
            boolean isJoinTmgEmployees,
            boolean pbUseRecordDate
    ) throws Exception{
        this.psDBBean = bean;
        this.beanDesc = beanDesc;
        this.targetDate = targetDate;
        this.treeViewType = treeViewType;
        this.isJoinTmgEmployees = isJoinTmgEmployees;
        this.gbUseRecordDate = pbUseRecordDate;
        init();
    }

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します。<br>
     * 現在日付-targetDateの範囲で、参照可能な社員の一覧を作成します。<br>
     * @param bean 呼出元のBean
     * @param beanDesc 呼出元のBeanの名称
     * @param targetDate 基準日(YYYY/MM/DD形式)
     * @param treeViewType コンテンツが使用するツリービューのタイプ
     * @param isJoinTmgEmployees 勤怠管理サイトの場合、TMG_EMPLOYEESテーブルと結合するかどうか
     * @param pbUseRecordDate 組織ツリーの基準日を使用するかどうか
     * @param pbUseManageFLG 管理対象外を表示するか(true：表示する false：表示しない)
     * @throws Exception
     */
    public TmgReferList(
            PsDBBean bean,
            String beanDesc,
            String targetDate,
            int treeViewType,
            boolean isJoinTmgEmployees,
            boolean pbUseRecordDate,
            boolean pbUseManageFLG
    ) throws Exception{
        this.psDBBean = bean;
        this.beanDesc = beanDesc;
        this.targetDate = targetDate;
        this.treeViewType = treeViewType;
        this.isJoinTmgEmployees = isJoinTmgEmployees;
        this.gbUseRecordDate = pbUseRecordDate;
        this.gbUseManageFLG = pbUseManageFLG;
        init();
    }

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します。<br>
     * 現在日付-targetDateの範囲で、参照可能な社員の一覧を作成します。<br>
     * @param bean 呼出元のBean
     * @param beanDesc 呼出元のBeanの名称
     * @param targetDate 基準日(YYYY/MM/DD形式)
     * @param treeViewType コンテンツが使用するツリービューのタイプ
     * @param isJoinTmgEmployees 勤怠管理サイトの場合、TMG_EMPLOYEESテーブルと結合するかどうか
     * @param pbUseRecordDate 組織ツリーの基準日を使用するかどうか
     * @param pbUseManageFLG 管理対象外を表示するか(true：表示する false：表示しない)
     * @paeam isSetTargetDate 引数として渡したtargetDateを基準日として扱うか(true：基準日に設定する false：設定しない）
     * @throws Exception
     */
    public TmgReferList(
            PsDBBean bean,
            String beanDesc,
            String targetDate,
            int treeViewType,
            boolean isJoinTmgEmployees,
            boolean pbUseRecordDate,
            boolean pbUseManageFLG,
            boolean isSetTargetDate
    ) throws Exception{
        this.psDBBean = bean;
        this.beanDesc = beanDesc;
        this.targetDate = targetDate;
        this.treeViewType = treeViewType;
        this.isJoinTmgEmployees = isJoinTmgEmployees;
        this.gbUseRecordDate = pbUseRecordDate;
        this.gbUseManageFLG = pbUseManageFLG;
        this.gbIsSetTargetDate = isSetTargetDate;
        init();
    }

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します。<br>
     * 現在日付-targetDateの範囲で、参照可能な社員の一覧を作成します。<br>
     * @param bean 呼出元のBean
     * @param beanDesc 呼出元のBeanの名称
     * @param targetDate 基準日(YYYY/MM/DD形式)
     * @param treeViewType コンテンツが使用するツリービューのタイプ
     * @param isJoinTmgEmployees 勤怠管理サイトの場合、TMG_EMPLOYEESテーブルと結合するかどうか
     * @param pbUseRecordDate 組織ツリーの基準日を使用するかどうか
     * @param editorGroupCode 編集担当者を示すグループコード
     * @throws Exception
     */
    public TmgReferList(
            PsDBBean bean,
            String beanDesc,
            String targetDate,
            int treeViewType,
            boolean isJoinTmgEmployees,
            boolean pbUseRecordDate,
            String editorGroupCode
    ) throws Exception{
        this.psDBBean = bean;
        this.beanDesc = beanDesc;
        this.targetDate = targetDate;
        this.treeViewType = treeViewType;
        this.isJoinTmgEmployees = isJoinTmgEmployees;
        this.gbUseRecordDate = pbUseRecordDate;
        this.gsEditorGroupCode = editorGroupCode;
        init();
    }

    /**
     * 汎用参照リストクラスのオブジェクトを生成し、必要なデータを初期化します。<br>
     * 現在日付-targetDateの範囲で、参照可能な社員の一覧を作成します。<br>
     * @param bean 呼出元のBean
     * @param beanDesc 呼出元のBeanの名称
     * @param targetDate 基準日(YYYY/MM/DD形式)
     * @param treeViewType コンテンツが使用するツリービューのタイプ
     * @param isJoinTmgEmployees 勤怠管理サイトの場合、TMG_EMPLOYEESテーブルと結合するかどうか
     * @param pbUseRecordDate 組織ツリーの基準日を使用するかどうか
     * @param pbUseManageFLG 管理対象外を表示するか(true：表示する false：表示しない)
     * @paeam isSetTargetDate 引数として渡したtargetDateを基準日として扱うか(true：基準日に設定する false：設定しない）
     * @param pbUseSearcjEmp 組織ツリー検索機能を使用判定用(true:使用、false:使用しない)
     * @throws Exception
     */
    public TmgReferList(
            PsDBBean bean,
            String beanDesc,
            String targetDate,
            int treeViewType,
            boolean isJoinTmgEmployees,
            boolean pbUseRecordDate,
            boolean pbUseManageFLG,
            boolean isSetTargetDate,
            boolean pbUseSearcjEmp
    ) throws Exception{
        this.psDBBean = bean;
        this.beanDesc = beanDesc;
        this.targetDate = targetDate;
        this.treeViewType = treeViewType;
        this.isJoinTmgEmployees = isJoinTmgEmployees;
        this.gbUseRecordDate = pbUseRecordDate;
        this.gbUseManageFLG = pbUseManageFLG;
        this.gbIsSetTargetDate = isSetTargetDate;
        this.gbUseSearcjEmp = pbUseSearcjEmp;
        init();
    }

    /**
     * TmgReferListオブジェクトを、BeanのrequestHashに格納します。<br>
     * このメソッドは、TmgTreeView.jspを使用する場合に実行しなければいけません。<br>
     * このメソッドを実行しなかった場合、TmgTreeView.jspをインクルードしてもツリービューは表示されません。
     */
    public void putReferList(ModelMap modelMap){
        modelMap.addAttribute(REQUEST_KEY_TMG_REFER_LIST_OBJECT, this);
    }

    public String getTargetDate() {
      return this.targetDate;
    }

    private void init() throws Exception{
        // 日付の処理
        setSysdate();
        // targetDate(遡り基準日)がSYSDATEより後の日付だった場合、targetDate = SYSDATE とする
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        String recordDate = psDBBean.getReqParam(TREEVIEW_KEY_RECORD_DATE);
        Date date = sdf.parse(targetDate);
        if(SysDateUtil.isGreater(date,gcSysdate)) {
            targetDate = sdf.format(gcSysdate.getTime());
        }
        // SYSDATEをYYYY/MM/DD形式の文字列にしておく
        String baseDate = sdf.format(gcSysdate.getTime());
        // 基準日を格納
        if (StrUtil.isBlank(recordDate)){
            setRecordDate(baseDate);
        }
        sessionControl4SearchTree(csSessionControl4SearchTreeInitialization, null, null);
        //病棟のリスト表示のケース
//        if(this.treeViewType == TREEVIEW_TYPE_LIST_WARD){
//
//            createTreeViewListWard();
        // 勤怠管理サイト } else
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
//               原： createTreeViewSiteAdmin();
            createTreeViewSiteAdmin(baseDate);
                // 勤怠承認サイト
        }else if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            createTreeViewSitePerm(baseDate);
        }
        // 基準日が過去か判定用フラグを格納
        setCheckDay(getRecordDate());
    }

    private void setSysdate(){
        HttpSession httpSession = psDBBean.getSession();
        // 初期化
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date dDate = new Date();

        String useManage = (String)httpSession.getAttribute(SESSION_KEY_USEMANAGEFLG);
        // 組織ツリー上の再表示ボタンが押下フラグ（押された場合はtrue)
//        String sRefersh = (String)psDBBean.requestHash.get(TREEVIEW_KEY_REFRESH_FLG);
        gcSysdate = SysUtil.transStringToDate((String)httpSession.getAttribute(SESSION_KEY_SYSDATE));
        gcPreMonthDate = SysUtil.transStringToDate((String)httpSession.getAttribute(SESSION_KEY_PRE_MONTH_DATE));
        gcPreYearDate  = SysUtil.transStringToDate((String)httpSession.getAttribute(SESSION_KEY_PRE_YEAR_DATE));

        Boolean isInit = (Boolean)httpSession.getAttribute("INIT_APPLICATION");
        //対象日付を格納
        if(gbIsSetTargetDate){	// gbIsSetTargetDate→true：渡ってきたtargetDateを基準日に設定   false：設定しない
            if (isInit == null) {
                setRecordDate(SysUtil.transDateToString(DateUtil.date()));
            } else {
                setRecordDate(this.targetDate);
            }
        }else if (psDBBean.getReqParam(TREEVIEW_KEY_RECORD_DATE) != null){ // 基準日を格納
            if (isInit == null) {
                setRecordDate(SysUtil.transDateToString(DateUtil.date()));
            } else {
                setRecordDate(psDBBean.getReqParam(TREEVIEW_KEY_RECORD_DATE));
            }
        } else {
            if (isInit == null) {
                setRecordDate(SysUtil.transDateToString(DateUtil.date()));
            } else {
                if (gcSysdate != null) {
                    // セッションが格納されている場合
                    setRecordDate(sdf.format(gcSysdate.getTime()));
                }
            }
        }
        httpSession.setAttribute("INIT_APPLICATION",true);
        // セッション情報の日付を格納
        if (gcSysdate != null){
            gsSessionDate = sdf.format(gcSysdate.getTime());
        }

        // セッションに登録されていなかった場合、基準日が変更された場合、基準日を使用せず基準日として設定されている値が現在日付ではない場合にSYSDATE他諸々をDBから取得する
        if((gcSysdate == null || gcPreMonthDate == null || gcPreYearDate == null) ||
                !getRecordDate().equals(sdf.format(gcSysdate.getTime())) ||
                (!isUseRecordDate() && !getRecordDate().equals(sdf.format(dDate)))
                || String.valueOf(this.gbUseManageFLG).equals(useManage)
        ){
            StringBuilder sSQL = new StringBuilder();
            if (getRecordDate() != null && isUseRecordDate()){
                sSQL.append(" SELECT ");
                sSQL.append(    " TO_CHAR(TO_DATE('" + getRecordDate() + "'),'"+DEFAULT_DATE_FORMAT+"') as systemDate, ");
                sSQL.append(    " TO_CHAR(ADD_MONTHS(TRUNC(TO_DATE('" + getRecordDate() + "'),'MM'),-1),'"+DEFAULT_DATE_FORMAT+"') as preMonthDate, ");
                sSQL.append(    " TO_CHAR(TMG_F_GET_THE_YEARZONE( TMG_F_GET_THE_YEAR(ADD_MONTHS(TO_DATE('" + getRecordDate() + "'),-12)), 0, ADD_MONTHS(TO_DATE('" + getRecordDate() + "'),-12)),'"+DEFAULT_DATE_FORMAT+"') preYearDate ");
                sSQL.append(" FROM DUAL ");
            } else {
                sSQL.append(" SELECT ");
                sSQL.append(    " TO_CHAR(SYSDATE,'"+DEFAULT_DATE_FORMAT+"') as systemDate, ");
                sSQL.append(    " TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE,'MM'),-1),'"+DEFAULT_DATE_FORMAT+"') as preMonthDate, ");
                sSQL.append(    " TO_CHAR(TMG_F_GET_THE_YEARZONE( TMG_F_GET_THE_YEAR(ADD_MONTHS(SYSDATE,-12)), 0, ADD_MONTHS(SYSDATE,-12)),'"+DEFAULT_DATE_FORMAT+"') preYearDate ");
                sSQL.append(" FROM DUAL ");
            }
            Connection conn = null;
            try {
                conn = dataSource.getConnection();
                log.info("日期SQL语句：{}",sSQL);
                /* 执行查询语句，返回实体列表，一个Entity对象表示一行的数据，Entity对象是一个继承自HashMap的对象，存储的key为字段名，value为字段值 */
                List<Entity> entityList = SqlExecutor.query(conn, sSQL.toString(), new EntityListHandler());
                Entity entity = entityList.get(0);
                Date sysdate      = sdf.parse((String)entity.get("SYSTEMDATE"));
                Date preMonthDate = sdf.parse((String)entity.get("PREMONTHDATE"));
                Date preYearDate  = sdf.parse((String)entity.get("PREYEARDATE"));

                gcSysdate = DateUtil.date(sysdate);
                gcPreMonthDate = DateUtil.date(preMonthDate);
                gcPreYearDate = DateUtil.date(preYearDate);

            } catch (SQLException | ParseException e) {
                e.printStackTrace();
                // 例外発生時の緊急対応
                Calendar calendar = CalendarUtil.calendar();
                calendar.set(Calendar.AM_PM, Calendar.AM);  // 午前にする
                calendar.set(Calendar.HOUR, 0);             // 0時にする
                calendar.set(Calendar.MINUTE, 0);           // 0分にする
                calendar.set(Calendar.SECOND, 0);           // 0秒にする
                calendar.set(Calendar.MILLISECOND, 0);      // 0ミリ秒にする
                gcSysdate = DateUtil.date(calendar);

                gcPreMonthDate = ObjectUtil.clone(gcSysdate); // SYSDATEにする
                Calendar cgcPreMonthDate = CalendarUtil.calendar(gcPreMonthDate);
                cgcPreMonthDate.set(Calendar.DATE, 1);                  // 日付を1日にする
                cgcPreMonthDate.add(Calendar.MONTH, -1);                // 前月にする
                gcPreMonthDate = DateUtil.date(cgcPreMonthDate);

                gcPreYearDate = ObjectUtil.clone(gcSysdate); // SYSDATEにする
                Calendar cgcPreYearDate = CalendarUtil.calendar(gcPreYearDate);
                cgcPreYearDate.set(Calendar.DATE, 1);                  // 日付を1日にする
                cgcPreYearDate.set(Calendar.MONTH, 0);                 // 月を1月にする(※Javaの場合、1月は'0'になる)
                cgcPreYearDate.add(Calendar.YEAR, -1);                 // 前年にする
                gcPreYearDate = DateUtil.date(cgcPreYearDate);
            } finally {
                DbUtil.close(conn);
            }
            httpSession.setAttribute(SESSION_KEY_SYSDATE, DateUtil.format(gcSysdate,DEFAULT_DATE_FORMAT));
            httpSession.setAttribute(SESSION_KEY_PRE_MONTH_DATE, DateUtil.format(gcPreMonthDate,DEFAULT_DATE_FORMAT));
            httpSession.setAttribute(SESSION_KEY_PRE_YEAR_DATE, DateUtil.format(gcPreYearDate,DEFAULT_DATE_FORMAT));
        }
    }

    /**
     * 組織ツリー検索タブのセッション情報の格納を行う
     * @param piParam
     * @param pvSearchDataArray ※piParamが「csSessionControl4SearchTreeSave」の時のみ使用
     * @return なし
     */
    private void sessionControl4SearchTree(int piParam, List pvSearchDataArray, String psDispLimit4Tree){
        HttpSession httpSession = psDBBean.getSession();
        switch(piParam){
            case csSessionControl4SearchTreeInitialization:

                if (isUseSearcjEmp()){
                    httpSession.setAttribute(TREEVIEW_OBJ_HIDSELECT, getHidSelectTab());
                    // fix:与老代码不同的是，接口化搜索接口后每次调用都要重新设置搜索条件和搜索结果
                    httpSession.setAttribute(SESSION_KEY_SEARCHDATAARRAY, pvSearchDataArray);
                    httpSession.setAttribute(SESSION_KEY_SEARCHITEMS, String.valueOf(getSearchItems()));
                    httpSession.setAttribute(SESSION_KEY_SEARCHCONDITION, String.valueOf(getSearchCondition()));
                    httpSession.setAttribute(SESSION_KEY_SEARCHDATA, String.valueOf(getSearchData()));
                    httpSession.setAttribute(SESSION_KEY_DISPLIMIT4TREE, String.valueOf(psDispLimit4Tree));
                } else {
                    // 組織ツリー検索タブを使わない場合は初期化
                    httpSession.setAttribute(TREEVIEW_OBJ_HIDSELECT, ciSelectTreeTab);
                    httpSession.setAttribute(SESSION_KEY_SEARCHDATAARRAY, null);
                    httpSession.setAttribute(SESSION_KEY_SEARCHITEMS, null);
                    httpSession.setAttribute(SESSION_KEY_SEARCHCONDITION, null);
                    httpSession.setAttribute(SESSION_KEY_SEARCHDATA, null);
                    httpSession.setAttribute(SESSION_KEY_DISPLIMIT4TREE, null);
                }
                break;
            case csSessionControl4SearchTreeSave:
                httpSession.setAttribute(SESSION_KEY_SEARCHDATAARRAY, pvSearchDataArray);
                httpSession.setAttribute(SESSION_KEY_SEARCHITEMS, String.valueOf(getSearchItems()));
//                httpSession.setAttribute(SESSION_KEY_SEARCHITEMS, null);
                httpSession.setAttribute(SESSION_KEY_SEARCHCONDITION, String.valueOf(getSearchCondition()));
                httpSession.setAttribute(SESSION_KEY_SEARCHDATA, String.valueOf(getSearchData()));
                httpSession.setAttribute(SESSION_KEY_DISPLIMIT4TREE, String.valueOf(psDispLimit4Tree));
                break;
        }
    }

    /**
     * init()での勤怠管理サイト用の処理
     * @return なし
     */
    private void createTreeViewSiteAdmin(String baseDate) throws Exception {

        HttpSession session = psDBBean.getSession();

        // 対象社員の社員コードをリクエストパラメータから取得
        targetEmp_admin = psDBBean.getReqParam(TREEVIEW_KEY_ADMIN_TARGET_EMP);
        // リクエストパラメータに存在しない場合、セッションから取得
        if(targetEmp_admin == null || targetEmp_admin.equals("")){
            targetEmp_admin = (String)psDBBean.getSession().getAttribute(TREEVIEW_KEY_ADMIN_TARGET_EMP);
            // セッションにも存在しない場合、NULLをセット
            if(targetEmp_admin == null || targetEmp_admin.equals("")){
                targetEmp_admin = null;
            }
        }

        // 対象組織の組織コードをリクエストパラメータから取得
        targetSec_admin = psDBBean.getReqParam(TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        // リクエストパラメータに存在しない場合、セッションから取得
        if(StrUtil.isBlank(targetSec_admin)){
            targetSec_admin = (String)psDBBean.getSession().getAttribute(TREEVIEW_KEY_ADMIN_TARGET_SECTION);
            // セッションにも存在しない場合、NULLをセット(対象社員もNULLにしておく)
            if(StrUtil.isBlank(targetSec_admin)){
                targetSec_admin = null;
                targetEmp_admin = null;
            }
        }

        // 組織ツリーを初期化
        if (this.treeViewType == TREEVIEW_TYPE_DIVLIST) {
            createDivTree();
        }
        else {
            createOrgTree();
        }

        // 組織が選択されている場合、社員一覧を初期化
//        if(StrUtil.isNotBlank(targetSec_admin)){
            createEmpList(targetSec_admin, targetDate, getHidSelectTab());
            // 対象社員の値が存在しない場合、デフォルト値をセットする
//            if(targetEmp_admin == null ||!empList.existsEmp(targetEmp_admin)){
//                if(CollUtil.isNotEmpty(empList.getDataArray())){
//                    targetEmp_admin = (String)((List)empList.getDataArray().get(0)).get(TmgEmpList.DEFAULT_KEY_EMPID);
//                }else{
//                    targetEmp_admin = null;
//                }
//            }
//        }

        // 改めてセッションに登録する
        session.setAttribute(TREEVIEW_KEY_ADMIN_TARGET_SECTION, targetSec_admin);
        session.setAttribute(TREEVIEW_KEY_ADMIN_TARGET_EMP, targetEmp_admin);

    }

    /**
     * init()での勤怠承認サイト用の処理
     * @param psDaseDate
     * @return なし
     */
    private void createTreeViewSitePerm(String psDaseDate) throws Exception {
        // まずは初期化
        createGroupList();
        createTreeViewNoTargetSection(psDaseDate);
    }

    private void createTreeViewNoTargetSection(String psDaseDate) throws Exception {

        HttpSession session = psDBBean.getSession();

        createMemberList(targetDate, getHidSelectTab());
        // 対象組織の組織コードをリクエストパラメータから取得
        targetSec_perm    = psDBBean.getReqParam(TREEVIEW_KEY_PERM_TARGET_SECTION);
        // リクエストパラメータに存在しない場合、セッションから取得
        if(targetSec_perm == null){
            targetSec_perm = (String)session.getAttribute(TREEVIEW_KEY_PERM_TARGET_SECTION);
        }

        // 対象グループのグループIDをリクエストパラメータから取得
        targetGroup_perm  = psDBBean.getReqParam(TREEVIEW_KEY_PERM_TARGET_GROUP);
        // リクエストパラメータに存在しない場合、セッションから取得
        if(targetGroup_perm == null){
            targetGroup_perm = (String)psDBBean.getSession().getAttribute(TREEVIEW_KEY_PERM_TARGET_GROUP);
        }

        // 対象社員の社員コードをリクエストパラメータから取得
        targetMember_perm = psDBBean.getReqParam(TREEVIEW_KEY_PERM_TARGET_EMP);
        // リクエストパラメータに存在しない場合、セッションから取得
        if(targetMember_perm == null){
            targetMember_perm = (String)psDBBean.getSession().getAttribute(TREEVIEW_KEY_PERM_TARGET_EMP);
        }
        // 2007/10/02 Shishido 2007/08/30の修正を元に戻します
        // 承認サイトで社員の存在しない部署を選択しても、自動的に社員が存在する部署を対象になるため、
        // 一見すると、部署選択のロジックがバグってるように見えてしまう→これを改善
        List dataList = memberList.getDataArray();
        // 対象組織の値が存在しない場合、デフォルト値をセットする
        if(targetSec_perm == null || targetSec_perm.equals("") || !existsSection(targetDate, psDaseDate, targetSec_perm)){
            if(dataList.size() > 0){
                targetSec_perm = (String)((List)dataList.get(0)).get(TmgMemberList.DEFAULT_KEY_SECID);
            }else{
                targetSec_perm = null;
            }
            targetGroup_perm = null;
            targetMember_perm = null;
        }

        // 対象グループの値が存在しない場合、デフォルト値をセットする
        if(targetGroup_perm == null || "".equals(targetGroup_perm)|| !existsGroup(targetDate, psDaseDate, targetGroup_perm)){
            String[] keyData = {targetSec_perm};
            dataList = JSONArrayGenerator.selectDataArray(dataList, TmgMemberList.DEFAULT_KEY_SECID, keyData);
            if(dataList.size() > 0){
                targetGroup_perm = (String)((List)dataList.get(0)).get(TmgMemberList.DEFAULT_KEY_GROUPID);
            }else{
                targetGroup_perm = null;
            }
            targetMember_perm = null;
        }

        // 対象社員の値が存在しない場合、デフォルト値をセットする
        if(targetMember_perm == null || "".equals(targetMember_perm)|| !existsEmployee(targetDate, psDaseDate, targetMember_perm)){
            String[] keyData = {targetGroup_perm};
            dataList = JSONArrayGenerator.selectDataArray(dataList, TmgMemberList.DEFAULT_KEY_GROUPID, keyData);
            if(dataList.size() > 0){
                targetMember_perm = (String)((List)dataList.get(0)).get(TmgMemberList.DEFAULT_KEY_EMPID);
            }else{
                targetMember_perm = null;
            }
        }


        // 選択されているビューをリクエストパラメータから取得
        selectedView_perm = psDBBean.getReqParam(TREEVIEW_KEY_PERM_SELECTED_VIEW);
        // リクエストパラメータに存在しない場合、セッションから取得
        if(selectedView_perm == null){
            selectedView_perm = (String)session.getAttribute(TREEVIEW_KEY_PERM_SELECTED_VIEW);
        }
        // 選択されているビューが存在しない場合、デフォルト値をセットする
        if(selectedView_perm == null || selectedView_perm.equals("")){
            selectedView_perm = PERM_SELECTEDVIEW_SECTION;
        }

        // 2007.07.05  H.kawabata 選択されているコンテンツのツリービュータイプが「一覧系/部署ごと」の場合強制的に"section"を設定する
        if(TREEVIEW_TYPE_LIST_SEC == getTreeViewType()) {
            selectedView_perm = PERM_SELECTEDVIEW_SECTION;
        }

        // 改めてセッションに登録する
        session.setAttribute(TREEVIEW_KEY_PERM_TARGET_SECTION, targetSec_perm);
        session.setAttribute(TREEVIEW_KEY_PERM_TARGET_GROUP, targetGroup_perm);
        session.setAttribute(TREEVIEW_KEY_PERM_TARGET_EMP, targetMember_perm);
        session.setAttribute(TREEVIEW_KEY_PERM_SELECTED_VIEW, selectedView_perm);

        log.debug("勤怠承認サイトsectionId:{}",session.getAttribute(TREEVIEW_KEY_PERM_TARGET_SECTION));
        log.debug("勤怠承認サイトempId:{}",session.getAttribute(TREEVIEW_KEY_PERM_TARGET_EMP));
        log.debug("勤怠承認サイトgroupId:{}",session.getAttribute(TREEVIEW_KEY_PERM_TARGET_GROUP));
    }

    /**
     * Calendarが表す日付を、指定されたフォーマットに変換して返します。<br>
     * フォーマットの指定が正しくない場合、nullを返します。
     * @param date
     * @param format
     * @return
     */
    private String getDateStringFor(Date date, String format){
        return DateUtil.format(date,format);
    }

    /**
     * 勤怠管理サイト用に、組織ツリーを作成します。
     */
    private void createOrgTree(){
        // 勤怠管理サイトの場合、SYSDATE時点の組織ツリーを作成する
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            // 検索対象範囲の適用
            orgTree = new TmgOrgTree(psDBBean,beanDesc);
            // 検索対象範囲条件を取得（暂时不需要）
            String sExists = orgTree.getOrgTreeSearchRange(psDBBean);
            // セッションに組織ツリーのデータが格納されていれば、セッションのデータを使用する
            List obj = (List)psDBBean.getSession().getAttribute(SESSION_KEY_ORGTREE_RESULT);
            String sCondition = (String)psDBBean.getSession().getAttribute(SESSION_KEY_ORGTREE_CONDITION);
            if(obj != null && sCondition != null && sCondition.equalsIgnoreCase(sExists) && sessionSameCheck()) {
                orgTree.setDataArray(obj);
            }
            // そうでなければ、新たに組織ツリーを作成する(SYSDATE時点のツリーを作成する)
            else{
                try{
                    String baseDate = SysUtil.transDateNullToDB(getDateStringFor(gcSysdate, DEFAULT_DATE_FORMAT));
                    log.debug("【调用TmgOrgTree的createOrgTree方法，custId:{},compCode:{},language:{},baseDate:{}】",psDBBean.getCustID(),psDBBean.getCompCode(),psDBBean.getLanguage(),baseDate);
                    orgTree.createOrgTree("'"+psDBBean.getCustID()+"'", "'"+psDBBean.getCompCode()+"'", "'"+psDBBean.getLanguage()+"'", baseDate);
//                    psDBBean.getSession().setAttribute(SESSION_KEY_ORGTREE_RESULT, orgTree.getDataArray());
//                    psDBBean.getSession().setAttribute(SESSION_KEY_ORGTREE_CONDITION, sExists);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 勤怠管理サイト用に、部局ツリーを作成します。
     */
    private void createDivTree(){
        HttpSession session = psDBBean.getSession();
        // 勤怠管理サイトの場合、SYSDATE時点の組織ツリーを作成する
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            divTree = new TmgDivisionTree(psDBBean,beanDesc);
            // 検索対象範囲条件を取得(暂时不需要)
             String sExists = divTree.getDivTreeSearchRange(psDBBean, session);
            // セッションに組織ツリーのデータが格納されていれば、セッションのデータを使用する
            List obj = (List)session.getAttribute(SESSION_KEY_DIVTREE_RESULT);
            String sCondition = (String)session.getAttribute(SESSION_KEY_DIVTREE_CONDITION);
            if(obj != null && sCondition.equalsIgnoreCase(sExists)  && sessionSameCheck()) {
                divTree.setDataArray(obj);
            }
            // そうでなければ、新たに組織ツリーを作成する(SYSDATE時点のツリーを作成する)
            else{
                try{
                    String baseDate = SysUtil.transDateNullToDB(getDateStringFor(gcSysdate, DEFAULT_DATE_FORMAT));
                    divTree.createDivisionTree("'"+psDBBean.getCustID()+"'", "'"+psDBBean.getCompCode()+"'", "'"+psDBBean.getLanguage()+"'", baseDate);
                    session.setAttribute(SESSION_KEY_DIVTREE_RESULT, divTree.getDataArray());
                    session.setAttribute(SESSION_KEY_DIVTREE_CONDITION, sExists);
                    session.setAttribute(SESSION_KEY_DIVTREE_ALL, divTree.isAllDivision());
                    session.setAttribute(SESSION_KEY_DIVTREE_ROOT, divTree.getRootSection());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * システム管理者かどうかを判定する。
     * @return
     */
    public boolean isAdmin(){
        boolean retval = false;
        Connection connection;
        try{
            connection = dataSource.getConnection();
            List<String> result = SqlExecutor.query(connection,buildSQLforCheckAdmin(), new StringListHandler());
            if(CollUtil.isNotEmpty(result)) {
                retval = true;
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            retval = false;
        }
        return retval;
    }

    /**
     * システム管理者か判定するSQL
     * @return
     */
    private String buildSQLforCheckAdmin(){
        return   " select "
                + " 	MGD_CGENERICDETAILID_CK "
                + " from "
                + " 	MAST_GENERIC_DETAIL d "
                + " where "
                + " 		d.MGD_CCUSTOMERID		= " + psDBBean.escDBString(psDBBean.getCustID())
                + " 	and d.MGD_CCOMPANYID_CK_FK	= " + psDBBean.escDBString(psDBBean.getCompCode())
                + " 	and d.MGD_CGENERICGROUPID	= 'TMG_ADMIN_GROUP' "
                + " 	and " + SysUtil.transDateNullToDB(getDateStringFor(gcSysdate, DEFAULT_DATE_FORMAT))
                + " 		between d.MGD_DSTART_CK and d.MGD_DEND "
                + " 	and d.MGD_CLANGUAGE_CK		= " + psDBBean.escDBString(psDBBean.getLanguage())
                + " 	and d.MGD_CGENERICDETAILID_CK = " + psDBBean.escDBString(psDBBean.getGroupID())
                + "";
    }

    /**
     * 勤怠管理サイト用に、社員一覧を作成します。
     * SYSDATE-targetDate(遡り基準日)の範囲で、指定した部署に所属していた社員の一覧を作成します。
     * 勤怠承認サイトのメンバー一覧と異なり、リクエストの都度、検索処理を実行します。
     * @param targetSection
     * @param targetDate
     */
    private void createEmpList(String targetSection, String targetDate, int piHidSelectTab) throws Exception{
        // 勤怠管理サイトの場合、対象部署について、SYSDATE-targetDateの範囲で歴を持っている社員一覧を作成する
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            empList = new TmgEmpList(psDBBean, beanDesc);
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            Date date = sdf.parse(targetDate);
            String base = SysUtil.transDateNullToDB(getDateStringFor(gcSysdate, DEFAULT_DATE_FORMAT));
            String target;

            log.info("【createEmpList的参数:sectionId:{},targetDate:{},dateCompare:{}】",targetSection,targetDate,SysDateUtil.isLess(date,gcPreYearDate));
            // 前年度初日より以前の日付が指定された場合、新しい範囲について社員一覧の検索処理を実行します
            // ===== fix: 从数据库查出的gcPreYearDate在比当前年份仅小于一年时数量和查询出的员工数量不正确的问题
            if(SysDateUtil.isLess(date,gcPreYearDate) || ( DateUtil.date().year()-DateUtil.date(date).year()) == 1){
//                if(SysDateUtil.isLess(date,gcPreYearDate)){
                target = SysUtil.transDateNullToDB(targetDate);
                empList.createEmpList(
                        "'"+psDBBean.getCustID()+"'",
                        "'"+psDBBean.getCompCode()+"'",
                        SysUtil.transStringNullToDB(targetSection),
                        target, //現状未使用
// fix: 老代码以basedate查询，现在统一用target查询
//                       base,
                        target,
                        "'"+psDBBean.getLanguage()+"'",
                        true, // 本務のみとする
                        isJoinTmgEmployees,
                        this.gbUseManageFLG
                );
                if (target.equals(psDBBean.getSession().getAttribute(SESSION_KEY_TARGETDATE)) && isSession4SearchTabItem()){
                    empList.setSearchDataArray((List)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATAARRAY));
                    empList.setDispLimit4Tree((String)psDBBean.getSession().getAttribute(SESSION_KEY_DISPLIMIT4TREE));
                } else {
                    if (isSelectedSearchTab()){
                        empList.createEmpList("'"+psDBBean.getCustID()+"'", "'"+psDBBean.getCompCode()+"'",
                                "'"+targetSection+"'", target, base, "'"+psDBBean.getLanguage()+"'", true,
                                isJoinTmgEmployees, this.gbUseManageFLG, getSearchItems(),
                                getSearchCondition(), getSearchData()
                        );
                    } else {
                        empList.setSearchDataArray(null);
                    }
                    psDBBean.getSession().setAttribute(SESSION_KEY_TARGETDATE, target);
                    sessionControl4SearchTree(csSessionControl4SearchTreeSave, empList.getSearchDataArray(), empList.getDispLimit4Tree());
                }
            }
            // そうでない場合、SYSDATE-targetDateの範囲のレコードを使用します
            else{
                target = SysUtil.transDateNullToDB(getDateStringFor(gcPreYearDate, DEFAULT_DATE_FORMAT));
                try{
//                    createEmpList4TreeView(target, target, targetSection, sdf);
//                    createEmpList4SearchView(target, target, targetSection, sdf);
                    createEmpList4TreeView(base, target, targetSection, sdf);
                    createEmpList4SearchView(base, target, targetSection, sdf);
                    psDBBean.getSession().setAttribute(SESSION_KEY_TARGETDATE, target);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 勤怠管理サイト用に、組織ツリータブ情報を作成します。
     * SYSDATE-targetDate(遡り基準日)の範囲で、指定した部署に所属していた社員の一覧を作成します。
     * 勤怠承認サイトのメンバー一覧と異なり、リクエストの都度、検索処理を実行します。
     * @param psBase
     * @param psTarget
     * @param psTargetSection
     * @param pSdf
     */
    private void createEmpList4TreeView(String psBase, String psTarget, String psTargetSection,
                                        SimpleDateFormat pSdf) throws Exception{

        List dataArray = null;
        String condition = (String)psDBBean.getSession().getAttribute(SESSION_KEY_EMPLIST_CONDITION);
        // セッションに登録されているデータと、検索条件(対象部署＆結合条件)が同じ場合、そちらのデータを取りに行く
        if(condition != null && condition.equalsIgnoreCase(
                tmgSearchRangeUtil.getEmpListCondition("", psTargetSection, isJoinTmgEmployees, psDBBean))){
            dataArray = (List)psDBBean.getSession().getAttribute(SESSION_KEY_EMPLIST_RESULT);
        }
        log.info("【createEmpList4TreeView-before-dataArray:{}】",dataArray);
        // セッションに社員一覧のデータが格納されていて管理対象者条件使用フラグが同じ場合は、セッションのデータを使用する
        if(dataArray != null  && getRecordDate().equals(gsSessionDate) && sessionSameCheck()){
            empList.setDataArray(dataArray);
        }
        // そうでなければ「SYSDATE-前年度初日」の範囲で社員一覧を作成しておく
        else{

            empList.createEmpList(
                    "'"+psDBBean.getCustID()+"'",
                    "'"+psDBBean.getCompCode()+"'",
                    "'"+psTargetSection+"'",
                    psTarget, //現状未使用
                    psBase,
                    "'"+psDBBean.getLanguage()+"'",
                    true, // 本務のみとする
                    isJoinTmgEmployees,
                    this.gbUseManageFLG //管理対象外を表示するか
            );
            psDBBean.getSession().setAttribute(SESSION_KEY_EMPLIST_CONDITION, tmgSearchRangeUtil.getEmpListCondition("", psTargetSection, isJoinTmgEmployees, psDBBean));
            psDBBean.getSession().setAttribute(SESSION_KEY_EMPLIST_CONDITION, null);
            psDBBean.getSession().setAttribute(SESSION_KEY_EMPLIST_RESULT, empList.getDataArray());
            psDBBean.getSession().setAttribute(SESSION_KEY_USEMANAGEFLG,String.valueOf(this.gbUseManageFLG));
        }
        // 使用するデータを、SYSDATE-targetDateの範囲に絞り込みます
        empList.setDataArray(empList.getDataArrayBetween(pSdf.format(gcSysdate.getTime()),targetDate));
    }

    /**
     * 勤怠管理サイト用に、検索タブ情報を作成します。
     * SYSDATE-targetDate(遡り基準日)の範囲で、指定した部署に所属していた社員の一覧を作成します。
     * 勤怠承認サイトのメンバー一覧と異なり、リクエストの都度、検索処理を実行します。
     * @param psBase
     * @param psTarget
     * @param psTargetSection
     * @param pSdf
     */
    private void createEmpList4SearchView(String psBase, String psTarget, String psTargetSection,
                                          SimpleDateFormat pSdf) throws Exception{
        // セッションに社員一覧のデータが格納されていて管理対象者条件使用フラグが同じ場合は、セッションのデータを使用する
        if(psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATAARRAY) != null  &&
                getRecordDate().equals(gsSessionDate) && isSession4SearchTabItem()
        ){
            empList.setSearchDataArray((List)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATAARRAY));
            empList.setDispLimit4Tree((String)psDBBean.getSession().getAttribute(SESSION_KEY_DISPLIMIT4TREE));
        }
        // そうでなければ「SYSDATE-前年度初日」の範囲で社員一覧を作成しておく
        else{
            if (isSelectedSearchTab()){
                empList.createEmpList("'"+psDBBean.getCustID()+"'", "'"+psDBBean.getCompCode()+"'",
                        "'"+psTargetSection+"'", psTarget, psBase, "'"+psDBBean.getLanguage()+"'", true,
                        isJoinTmgEmployees, this.gbUseManageFLG, getSearchItems(), getSearchCondition(), getSearchData()
                );
            } else {
                empList.setSearchDataArray(null);
            }
            sessionControl4SearchTree(csSessionControl4SearchTreeSave, empList.getSearchDataArray(), empList.getDispLimit4Tree());
        }
        // 使用するデータを、SYSDATE-targetDateの範囲に絞り込みます
        empList.setSearchDataArray(empList.getSearchDataArrayBetween(pSdf.format(gcSysdate.getTime()),targetDate));
    }

    /**
     * セッション情報と選択・入力された情報が一致しているか判定し返却する
     * 選択・入力された情報がなくセッションに保持されている場合も判定し返却する
     * @return boolean
     */
    private boolean isSession4SearchTabItem(){

        if (psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHITEMES) != null){
            String searchItems = (String)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHITEMS);
            String searchCondition =  (String)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHCONDITION);
            String searchData =  (String)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATA);
            if(!StrUtil.equals(searchItems,psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHITEMES)) &&
                    StrUtil.equals(searchCondition,psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHCONDITION)) &&
                    StrUtil.equals(searchData,psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHDATA))
            ) {
                return false;
            }
            return true;
        } else if (psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATAARRAY) != null){
            return true;
        }

        return false;

    }

    /**
     * 勤怠承認サイト用に、グループ一覧を作成します。
     */
    private void createGroupList(){
        // 勤怠承認サイトの場合、SYSDATE-前月初日の間で権限を持っているグループの一覧を作成する
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            groupList = new TmgGroupList(psDBBean, beanDesc);
            //基準日時点で有効なグループの一覧を作成するため実質不要
            String targetDate = SysUtil.transDateNullToDB(getDateStringFor(gcPreMonthDate, DEFAULT_DATE_FORMAT));	//前月初日
            // セッションにグループ一覧のデータが格納されていれば、セッションのデータを使用する
            List<String> dataArray = (List<String>)psDBBean.getSession().getAttribute(SESSION_KEY_GROUPLIST_RESULT);
            if(dataArray != null && getRecordDate().equals(gsSessionDate)  && sessionSameCheck()){
                groupList.setDataArray(dataArray);
            }
            // そうでなければ、新たにグループ一覧を作成する
            else{
                try{
                    String baseDate = SysUtil.transDateNullToDB(getDateStringFor(gcSysdate, DEFAULT_DATE_FORMAT));
                    groupList.createGroupList(baseDate, targetDate);
                    psDBBean.getSession().setAttribute(SESSION_KEY_GROUPLIST_RESULT, groupList.getDataArray());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 勤怠承認サイト用に、メンバー(部下)一覧を作成します。
     * 現在日付-targetDateの範囲で、参照可能なグループに所属していた社員の一覧を作成します。
     * targetDateの書式が「YYYY/MM/DD」ではない場合、例外が発生します。
     * @param targetDate
     */
    private void createMemberList(String targetDate, int piHidSelectTab) throws Exception{

        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){

            memberList = new TmgMemberList(psDBBean, beanDesc);

            String base = SysUtil.transDateNullToDB(getDateStringFor(gcSysdate, DEFAULT_DATE_FORMAT));

            try{
                createMemberList4TreeView(memberList, base);
                createMemberList4SearchView(memberList, base);
            }catch(Exception e){
                e.printStackTrace();
            }

            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            Date date = sdf.parse(targetDate);

            // 前月初日より後の日付が指定された場合、グループ一覧を指定された日付の範囲で絞り込みます
            // また、メンバー一覧を、上記のグループに絞り込みます
            if(SysDateUtil.isLess(gcPreMonthDate,date)){
                groupList.setDataArray(groupList.getDataArrayBetween(sdf.format(gcSysdate.getTime()),targetDate));
                memberList.setDataArray(memberList.getDataArrayInGroup(groupList.getGroupListArray()));
                // todo：グループの絞込をする必要がないのでここには追加しない
            }

            // 前年度初日より以前の日付が指定された場合、新しい範囲について社員一覧の検索処理を実行します
            if(SysDateUtil.isLess(date,gcPreYearDate)){
                memberList.createMemberList(base, gbUseManageFLG);

                if (isSelectedSearchTab()){
                    memberList.createMemberList(base, gbUseManageFLG,
                            getSearchItems(), getSearchCondition(), getSearchData()
                    );
                } else {
                    memberList.setSearchDataArray(null);
                }
            }
            // そうでない場合、SYSDATE-targetDateの範囲のレコードを使用します
            else{
                // 使用するデータレコードを、SYSDATE-targetDateの範囲に絞り込みます
                memberList.setDataArray(memberList.getDataArrayBetween(sdf.format(gcSysdate.getTime()),targetDate));
                memberList.setSearchDataArray(memberList.getSearchDataArrayBetween(sdf.format(gcSysdate.getTime()),targetDate));
            }

        }

    }

    /**
     * 勤怠承認サイト用の組織ツリータブ情報を作成します。
     * @param pTemberList
     * @param psBase
     */
    private void createMemberList4TreeView(TmgMemberList pTemberList, String psBase) throws Exception{

        // セッションにメンバー一覧のデータが登録されていない場合、SYSDATE-前年度初日の範囲でメンバー一覧を作成して登録しておきます
        List dataArray = (List)psDBBean.getSession().getAttribute(SESSION_KEY_MEMBERLIST_RESULT);

        if(dataArray != null && sessionSameCheck()){
            // セッションに登録されていて管理対象者条件使用フラグが同じ場合、セッションのデータをそのまま使用します
            pTemberList.setDataArray(dataArray);
        } else {
            pTemberList.createMemberList(psBase, gbUseManageFLG);
            psDBBean.getSession().setAttribute(SESSION_KEY_MEMBERLIST_RESULT, pTemberList.getDataArray());
            psDBBean.getSession().setAttribute(SESSION_KEY_USEMANAGEFLG,String.valueOf(this.gbUseManageFLG));
        }

    }

    /**
     * 勤怠承認サイト用の検索タブ情報を作成します。
     * @param psBase
     */
    private void createMemberList4SearchView(TmgMemberList pTmgMemberList, String psBase) throws Exception{

        log.info("【createMemberList4SearchView参数：searchTab:{},searchItems:{},searchCondition:{},searchData:{}】",isSelectedSearchTab(),getSearchItems(),
                getSearchCondition(),getSearchData());
//        if (isSelectedSearchTab() && StrUtil.isNotBlank(getSearchData())){
//            pTmgMemberList.createMemberList(psBase, gbUseManageFLG,
//                    getSearchItems(), getSearchCondition(), getSearchData()
//            );
//        } else {
//            pTmgMemberList.setSearchDataArray(null);
//        }
        if (isSelectedSearchTab() && StrUtil.isNotBlank(getSearchData())) {
            pTmgMemberList.createMemberList(psBase, gbUseManageFLG,
                    getSearchItems(), getSearchCondition(), getSearchData()
            );
        } else {
            pTmgMemberList.setSearchDataArray(null);
        }
        sessionControl4SearchTree(csSessionControl4SearchTreeSave, pTmgMemberList.getSearchDataArray(),
                pTmgMemberList.getDispLimit4Tree()
        );

//        if(psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATAARRAY) != null && isSession4SearchTabItem()){
//            // セッションに登録されていて管理対象者条件使用フラグが同じ場合、セッションのデータをそのまま使用します
//            pTmgMemberList.setSearchDataArray((List)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATAARRAY));
//            pTmgMemberList.setDispLimit4Tree((String)psDBBean.getSession().getAttribute(SESSION_KEY_DISPLIMIT4TREE));
//        } else {
//            log.info("【createMemberList4SearchView参数：searchTab:{},searchItems:{},searchCondition:{},searchData:{}】",isSelectedSearchTab(),getSearchItems(),
//                    getSearchCondition(),getSearchData());
//            if (isSelectedSearchTab() && StrUtil.isNotBlank(getSearchData())){
//                pTmgMemberList.createMemberList(psBase, gbUseManageFLG,
//                        getSearchItems(), getSearchCondition(), getSearchData()
//                );
//            } else {
//                pTmgMemberList.setSearchDataArray(null);
//            }
//            sessionControl4SearchTree(csSessionControl4SearchTreeSave, pTmgMemberList.getSearchDataArray(),
//                    pTmgMemberList.getDispLimit4Tree()
//            );
//
//        }

    }

    /**
     * 選択されたビューに従って、リスト表示される社員の一覧を取得するSQLを構築して返します。<br>
     * 勤怠承認サイトおよび勤怠管理サイトにおいて、一覧系コンテンツの場合、
     * SYSDATE-基準日の範囲で参照可能な社員のデータをつなげて、<br>
     *   SELECT ... FROM DUAL UNION ALL ...<br>
     * という形式の擬似クエリを構築して返します。<br>
     * この擬似クエリは、以下のカラムを持ちます。
     * <ul>
     *   <li>CUST    : 顧客コード
     *   <li>COMP    : 法人コード
     *   <li>EMPID   : 社員番号
     *   <li>EMPNAME : 社員氏名(漢字氏名)
     *   <li>SEQ     : ソート順(異動歴のDEND降順、DSTART昇順、部署コード昇順、役職ウェイト昇順、社員番号昇順)
     * </ul>
     * @return String SQL文(勤怠承認or管理サイトの一覧系コンテンツではない場合、NULLを返す)
     */
    public String buildSQLForSelectEmployees(){
        String sSQL = null;
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(treeViewType == TREEVIEW_TYPE_LIST || treeViewType == TREEVIEW_TYPE_LIST_SEC || treeViewType == TREEVIEW_TYPE_LIST_WARD){
                sSQL = (isSelectedSearchTab()) ? buildSearchDataArraySQLForSelectEmpList() : buildSQLForSelectEmpList();
            }
        }else if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(treeViewType == TREEVIEW_TYPE_LIST || treeViewType == TREEVIEW_TYPE_LIST_SEC){
                if (PERM_SELECTEDVIEW_ALL.equals(getSelectedView())){
                    sSQL = (isSelectedSearchTab()) ? buildSearchDataArraySQLForSelectMemberList() : buildSQLForSelectMemberListAll();
                } else if(PERM_SELECTEDVIEW_SECTION.equals(getSelectedView())){
                    sSQL = (isSelectedSearchTab()) ? buildSearchDataArraySQLForSelectMemberList() : buildSQLForSelectMemberListInSection(targetSec_perm);
                } else if(PERM_SELECTEDVIEW_GROUP.equals(getSelectedView())){
                    sSQL = (isSelectedSearchTab()) ? buildSearchDataArraySQLForSelectMemberList() : buildSQLForSelectMemberListInGroup(targetGroup_perm);
                } else{
                    sSQL = (isSelectedSearchTab()) ? buildSearchDataArraySQLForSelectMemberList() : buildSQLForSelectMemberListAll();
                }
            }
        }
        if (sSQL == null) {
            // 返すクエリがnullの場合はダミークエリを返す(SQLエラー予防)
            sSQL = "SELECT NULL AS CUST, NULL AS COMP, NULL AS EMPID, NULL AS EMPNAME, NULL AS SEQ FROM DUAL WHERE DUMMY IS NULL";
        }
        return sSQL;
    }

    /**
     * 選択されたビューに従って、リスト表示される社員の一覧を取得するSQLを構築して返します。<br>
     * Oracleテーブルオブジェクトの作成版
     * 勤怠承認サイトおよび勤怠管理サイトにおいて、一覧系コンテンツの場合、
     * SYSDATE-基準日の範囲で参照可能な社員のデータをつなげて、<br>
     *   SELECT ... FROM DUAL UNION ALL ...<br>
     * という形式の擬似クエリを構築して返します。<br>
     * この擬似クエリは、以下のカラムを持ちます。
     * <ul>
     *   <li>CUST    : 顧客コード
     *   <li>COMP    : 法人コード
     *   <li>EMPID   : 社員番号
     *   <li>EMPNAME : 社員氏名(漢字氏名)
     *   <li>SEQ     : ソート順(異動歴のDEND降順、DSTART昇順、部署コード昇順、役職ウェイト昇順、社員番号昇順)
     * </ul>
     * @return String SQL文(勤怠承認or管理サイトの一覧系コンテンツではない場合、NULLを返す)
     */
    public String buildSQLForSelectEmployeesTableObject(){
        String sSQL = null;
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(treeViewType == TREEVIEW_TYPE_LIST || treeViewType == TREEVIEW_TYPE_LIST_SEC || treeViewType == TREEVIEW_TYPE_LIST_WARD){
                sSQL = buildSQLForSelectEmpListTableObject(isSelectedSearchTab());
            }
        }else if(sSQL == null){
            sSQL = " TMG_EMPLIST_TABLE() ";
        }

        return sSQL;
    }

    /**
     * 勤怠管理サイトにおいて、選択された部署に所属している社員の一覧を取得するSQLを構築して返します。<br>
     * @return String SQL文
     */
    private String buildSQLForSelectEmpList(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(empList == null) {
                return null;
            }
            try{
                return empList.buildSQLForSelectEmpListFromDual();
            }catch(Exception e){
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 勤怠管理サイトにおいて、検索された社員の一覧を取得するSQLを構築して返します。<br>
     * @return String SQL文
     */
    private String buildSearchDataArraySQLForSelectEmpList(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            log.debug("buildSearchDataArraySQLForSelectEmpList执行");
            if (empList == null) {
                return null;
            }
            try{
                return empList.buildSearchDataArraySQLForSelectEmpListFromDual();
            }catch(Exception e){
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 勤怠管理サイトにおいて、選択された部署に所属している社員の一覧を取得するSQLを構築して返します。<br>
     * Oracleテーブルオブジェクトの作成版
     * @return String SQL文
     */
    private String buildSQLForSelectEmpListTableObject(boolean pbSelectedSearchTab){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if (empList == null) {
                return null;
            }
            try{
                return empList.buildSQLForSelectEmpListFromDualTableObject(pbSelectedSearchTab);
            }catch(Exception e){
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 勤怠承認サイトにおいて、検索されたメンバーの一覧を取得するSQLを構築して返します。<br>
     * @return String SQL文
     */
    private String buildSearchDataArraySQLForSelectMemberList(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if (memberList == null) {
                return null;
            }
            try{
                return memberList.buildearchDataArraySQLForSelectMemberListFromDual();
            }catch(Exception e){
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 勤怠承認サイトにおいて、参照可能なメンバーの一覧を取得するSQLを構築して返します。<br>
     * @return String SQL文
     */
    private String buildSQLForSelectMemberListAll(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if (memberList == null) {
                return null;
            }
            try{
                return memberList.buildSQLForSelectMemberListFromDualAll();
            }catch(Exception e){
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 勤怠承認サイトにおいて、指定された部署に所属している社員のうち、参照可能なメンバーの一覧を取得するSQLを構築します。<br>
     * @param targetSection 対象部署コード（選択された部署コードを取得するgetTargetSecメソッドと併用する）
     * @return SQL文
     */
    private String buildSQLForSelectMemberListInSection(String targetSection){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if (memberList == null) {
                return null;
            }

            try{
                String[] targetSectionArray = {targetSection};
                return memberList.buildSQLForSelectMemberListFromDualInSection(targetSectionArray);
            }catch(Exception e){
                return null;
            }
        }
        else{
            return null;
        }
    }

    /**
     * 勤怠承認サイトにおいて、指定されたグループに所属している社員のうち、参照可能なメンバーの一覧を取得するSQLを構築します。<br>
     * @param targetGroup 対象グループID
     * @return
     */
    private String buildSQLForSelectMemberListInGroup(String targetGroup){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if (memberList == null) {
                return null;
            }
            try{
                String[] targetGroupArray = {targetGroup};
                return memberList.buildSQLForSelectMemberListFromDualInGroup(targetGroupArray);
            }catch(Exception e){
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 作成された組織ツリーの内容に従って、ツリービュー作成用のJSON配列を生成して返します。<br>
     * 勤怠管理サイトの各種コンテンツで使用します。
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForOrgTree(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)
                || this.treeViewType == TREEVIEW_TYPE_LIST_WARD
        ){
            if(orgTree == null) {
                return null;
            }
            //            if (StrUtil.isNotBlank(targetSec_admin)) {
//               String result =ReUtil.get("\\{[^{]*'"+targetSec_admin+"'[^}]*\\}",orgTreeList,0);
//               JSONObject obj = JSONUtil.parseObj(result);
//               psDBBean.getSession().setAttribute(TREEVIEW_KEY_ADMIN_TARGET_SECTION_NAME,obj.get("secnic"));
//            }
            return orgTree.getJSONArrayForTreeView();
        }else{
            return null;
        }
    }


    /**
     * 作成された部局ツリーの内容に従って、ツリービュー作成用のJSON配列を生成して返します。<br>
     * 勤怠管理サイトの各種コンテンツで使用します。
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForDivisionTree(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(divTree == null){
                return null;
            }
            //            if (StrUtil.isNotBlank(targetSec_admin)) {
//                String result = ReUtil.get("\\{[^{]*'" + targetSec_admin + "'[^}]*\\}", divTreeList, 0);
//                JSONObject obj = JSONUtil.parseObj(result);
//                psDBBean.getSession().setAttribute(TREEVIEW_KEY_ADMIN_TARGET_SECTION_NAME, obj.get("secnic"));
//            }
            return divTree.getJSONArrayForTreeView();
        }else{
            return null;
        }
    }

    /**
     * 作成された社員一覧の内容に従って、ツリービュー作成用のJSON配列を生成して返します。<br>
     * 勤怠管理サイトの個人系コンテンツで使用します。
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForEmpList(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(empList == null){
                return null;
            }
            return empList.getJSONArrayForTreeViewGroupBySection(targetSec_admin);
        }else{
            return null;
        }
    }

    /**
     * 作成されたグループ一覧の内容に従って、ツリービュー作成用のJSON配列を生成して返します。<br>
     * 勤怠承認サイトの一覧系コンテンツで使用します
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForGroupList(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(groupList == null){
                return null;
            }
            //            if (StrUtil.isNotBlank(targetGroup_perm)) {
//                String regexGroupPerm =  targetGroup_perm.replace("|","\\|");
//                String result = ReUtil.get("\\{[^{]*'" + regexGroupPerm+ "'[^}]*\\}", treeViewGroup, 0);
//                JSONObject obj = JSONUtil.parseObj(result);
//                psDBBean.getSession().setAttribute(TREEVIEW_KEY_PERM_TARGET_GROUP_NAME, obj.get("label"));
//            }
            return groupList.getJSONArrayForTreeView();
        }else{
            return null;
        }
    }

    /**
     * 作成されたグループ一覧の親部署の一覧について、ツリービュー作成用のJSON配列を生成して返します。<br>
     * 勤怠承認サイトの一覧系コンテンツで使用します
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForSectionList(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(groupList == null){
                return null;
            }
            String treeViewSection = groupList.getJSONArrayForTreeViewSection();
//            if (StrUtil.isNotBlank(targetSec_perm)) {
//                String result = ReUtil.get("\\{[^{]*'" + targetSec_perm + "'[^}]*\\}", treeViewSection, 0);
//                JSONObject obj = JSONUtil.parseObj(result);
//                psDBBean.getSession().setAttribute(TREEVIEW_KEY_PERM_TARGET_SECTION_NAME, obj.get("label"));
//            }
            return treeViewSection;
        }else{
            return null;
        }
    }

    /**
     * 作成されたメンバー一覧の内容に従って、ツリービュー作成用のJSON配列を生成して返します。<br>
     * 勤怠承認サイトの個人系コンテンツで使用します。
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForMemberList(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(memberList ==  null){
                return null;
            }

            return memberList.getJSONArrayForTreeView();
        }else{
            return null;
        }
    }

    /**
     * 作成されたメンバー一覧の内容に従って、ツリービュー作成用のJSON配列を生成して返します。
     * 勤怠承認サイトの一覧系コンテンツで使用します
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForMemberListGroupBySection(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(memberList ==  null){
                return null;
            }
            return memberList.getJSONArrayForTreeViewGroupBySection(targetSec_perm);
        }else{
            return null;
        }
    }

    /**
     * 作成されたメンバー一覧の内容に従って、ツリービュー作成用のJSON配列を生成して返します。
     * 勤怠承認サイトの個人系コンテンツで使用します
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForMemberListGroupByGroup(){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(memberList ==  null){
                return null;
            }
            //            if (StrUtil.isNotBlank(targetMember_perm)) {
//                String c = ReUtil.get(targetMember_perm+"[^\\(]*\\("  ,     rs, 0);
//                String empName = c.substring(0, c.lastIndexOf(" "));
//                psDBBean.getSession().setAttribute(TREEVIEW_KEY_PERM_TARGET_EMP_NAME,empName);
//                System.out.println(empName);
//            }
            return memberList.getJSONArrayForTreeViewGroupByGroup(targetGroup_perm);
        }else{
            return null;
        }
    }

    /**
     * 作成された一覧の内容に従って、ツリービュー作成用のJSON配列を生成して返します。<br>
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForMemberListSearch(){
        if(memberList !=  null){
            return memberList.getJSONArrayForSearchView();
        }
        if(empList != null){
            return empList.getJSONArrayForSearchView();
        }
        return null;
    }

    /**
     * 検索タブで検索結果が1件以上あるか判別し返却する
     * 1件以上:true/0件:false
     * ※検索タブが選択されていない場合はfalseが返却される
     *
     * @return boolean
     */
    public boolean isEmployeeList(){
        if(!isSelectedSearchTab()){return false;}
        if(memberList !=  null){
            if (memberList.getSearchDataArray() != null){
                if (memberList.getSearchDataArray().size() != 0){return true;}
            }
        }

        if(empList != null){
            if(empList.getSearchDataArray() != null){
                return empList.getSearchDataArray().size() != 0;
            }
        }
        return false;
    }

    /**
     * 現在のサイトが、指定したサイトかどうかを判断します。
     * @param siteId
     * @return boolean treu:指定したサイトである false:指定したサイトではない
     */
    private boolean isSite(String siteId){
        return StrUtil.equals(psDBBean.getSiteId(),siteId);
    }

    /**
     * 基準日時点において、指定した社員に対して指定した権限を持っているかどうかを判定します。
     * @param sDate 基準日
     * @param sEmp 社員番号
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean hasAuthorityAtEmployee(String sDate, String sEmp, String sAuthority) throws Exception {
        return hasAuthorityAtEmployee(sDate, sDate, sEmp, sAuthority);
    }

    /**
     * 開始日-終了日の範囲において、指定した社員に対して指定した権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param sEmp 社員番号
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean hasAuthorityAtEmployee(String sStart, String sEnd, String sEmp, String sAuthority) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        // リストがnullの場合
        if( memberList == null || groupList == null ) {
            return false;
        }

        // メンバーの取得
        List<List<String>> vMemberList = getMemberList(sEmp, sStart, sEnd);
        if( vMemberList.size() <= 0 ) {	// 社員が見つからない
            return false;
        }
        // グループの取得
        List<String> vGroupID = CollUtil.newArrayList();
        for (List<String> vMember : vMemberList) {
            vGroupID.add(vMember.get(TmgMemberList.DEFAULT_KEY_GROUPID));
        }
        // グループの権限
        return hasAuthorityAtGroup(sStart, sEnd, vGroupID, sAuthority);

    }

    /**
     * 基準日時点において、指定したグループに対して指定した権限を持っているかどうかを判定します。
     * @param sDate 基準日
     * @param sGroup グループID
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean hasAuthorityAtGroup(String sDate, String sGroup, String sAuthority) throws Exception {
        return hasAuthorityAtGroup(sDate, sDate, sGroup, sAuthority);
    }

    /**
     * 開始日-終了日の範囲において、指定したグループに対して指定した権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param sGroup グループID
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean hasAuthorityAtGroup(String sStart, String sEnd, String sGroup, String sAuthority) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        // リストがnullの場合
        if( groupList == null ) {
            return false;
        }
        List<String> vGroupID = CollUtil.newArrayList();
        vGroupID.add(sGroup);
        return hasAuthorityAtGroup(sStart, sEnd, vGroupID, sAuthority);
    }

    /**
     * 開始日-終了日の範囲において、指定したグループ(複数)に対して指定した権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param vGroupID グループID
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    private boolean hasAuthorityAtGroup(String sStart, String sEnd, List<String> vGroupID, String sAuthority) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }
        // グループの取得
        List<List<String>> vGroupList = getGroupList(vGroupID, sStart, sEnd);

        // 指定の権限があるかどうか
        int iAuthority	= getAuthorityIndex(sAuthority);
        for (List<String> vGroup : vGroupList) {
            if (StrUtil.equals(vGroup.get(iAuthority), TmgUtil.Cs_MGD_ONOFF_1)) {    // ONなら
                return true;
            }
        }
        return false;
    }

    /**
     * 基準日時点において、指定した部署に対して指定した権限を持っているかどうかを判定します。
     * @param sDate 基準日
     * @param sSection 部署コード
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean hasAuthorityAtSection(String sDate, String sSection, String sAuthority) throws Exception {
        return hasAuthorityAtSection(sDate, sDate, sSection, sAuthority);
    }

    /**
     * 開始日-終了日の範囲において、指定した部署に対して指定した権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param sSection 部署コード
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean hasAuthorityAtSection(String sStart, String sEnd, String sSection, String sAuthority) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        // リストがnullの場合
        if( groupList == null ) {
            return false;
        }

        List<String> vSectionID = CollUtil.newArrayList();
        vSectionID.add(sSection);
        return hasAuthorityAtSection(sStart, sEnd, vSectionID, sAuthority);
    }

    /**
     * 開始日-終了日の範囲において、指定した部署(複数)に対して指定した権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param vSectionID 部署コード
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    private boolean hasAuthorityAtSection(String sStart, String sEnd,List<String> vSectionID, String sAuthority) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        // 部署の取得
        List<List<String>> vGroupList = getGroupListForSection(vSectionID, sStart, sEnd);

        // 指定の権限があるかどうか
        int iAuthority	= getAuthorityIndex(sAuthority);
        for(Iterator<List<String>> i = vGroupList.iterator(); i.hasNext();){
            List<String> vGroup = i.next();
            if(StrUtil.equals(vGroup.get(iAuthority),TmgUtil.Cs_MGD_ONOFF_1)) {	// ONなら
                return true;
            }
        }
        return false;
    }

    /**
     * 基準日時点において、指定した権限を持っているかどうかを判定します。<br>
     * 勤怠承認サイトにおいて、画面上に表示されている社員のいずれかに対して、指定した権限を持っているかどうかを返します。<br>
     * 個人系コンテンツでは、選択中の社員に対して、指定した権限を持っているかどうかを返します。<br>
     * 一覧系コンテンツでは、選択中のビューに応じて、指定した権限を持っているかどうかを返します。<br>
     * <ul>
     * <li>グループ毎：選択中のグループに所属している社員のいずれかに対して権限を持っているかどうか
     * <li>部署ごと：選択中の部署に所属している社員のいずれかに対して権限を持っているかどうか
     * <li>全て：表示されている社員のいずれかに対して権限を持っているかどうか
     * </ul>
     * @param sDate 基準日
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean hasAuthority(String sDate, String sAuthority) throws Exception {
        return hasAuthority(sDate, sDate, sAuthority);
    }

    /**
     * 開始日-終了日の範囲において、指定した権限を持っているかどうかを判定します。<br>
     * 勤怠承認サイトにおいて、画面上に表示されている社員のいずれかに対して、指定した権限を持っているかどうかを返します。<br>
     * 個人系コンテンツでは、選択中の社員に対して、指定した権限を持っているかどうかを返します。<br>
     * 一覧系コンテンツでは、選択中のビューに応じて、指定した権限を持っているかどうかを返します。<br>
     * <ul>
     * <li>グループ毎：選択中のグループに所属している社員のいずれかに対して権限を持っているかどうか
     * <li>部署ごと：選択中の部署に所属している社員のいずれかに対して権限を持っているかどうか
     * <li>全て：表示されている社員のいずれかに対して権限を持っているかどうか
     * </ul>
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param sAuthority 権限コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean hasAuthority(String sStart, String sEnd, String sAuthority) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        if(treeViewType == TREEVIEW_TYPE_EMP){
            // 個人系コンテンツであれば、選択中の社員に対して権限を持っているかどうかを判定
            return hasAuthorityAtEmployee(sStart, sEnd, targetMember_perm, sAuthority);
        }else{
            if(PERM_SELECTEDVIEW_GROUP.equals(getSelectedView())){
                // 一覧系(グループ毎)であれば、選択中のグループに対して権限を持っているかどうかを判定
                return hasAuthorityAtGroup(sStart, sEnd, targetGroup_perm, sAuthority);
            }else if(PERM_SELECTEDVIEW_SECTION.equals(getSelectedView())){
                // 一覧系(部署毎)であれば、選択中の部署に対して権限を持っているかどうかを判定
                return hasAuthorityAtSection(sStart, sEnd, targetSec_perm, sAuthority);
            }else{
                // 指定された日付の範囲内で参照可能な全てのグループについて、指定された権限を持っているかどうかを判定する
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                Date dStart	= sdf.parse(sStart);
                Date dEnd	= sdf.parse(sEnd);

                // 日付の絞込
                List<List<String>> vGroupList = JSONArrayGenerator.selectDataArrayBetween(
                        groupList.getDataArray(), dStart, dEnd, TmgGroupList.DEFAULT_KEY_DSTART, TmgGroupList.DEFAULT_KEY_DEND, sdf );
                int iAuthority	= getAuthorityIndex(sAuthority);
                for (List<String> vGroup : vGroupList) {
                    // 権限があったら
                    if (StrUtil.equals(vGroup.get(iAuthority), TmgUtil.Cs_MGD_ONOFF_1)) {
                        return true;
                    }
                }
                return false;	// 権限がないので
            }
        }
    }

    /**
     * 開始日-終了日の範囲において、指定した社員に対して何らかの権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param sEmp 社員番号
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean existsEmployee(String sStart, String sEnd, String sEmp) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        // リストがnullの場合
        if( memberList == null || groupList == null ) {
            return false;
        }

        // メンバーの取得
        List<List<String>> vMemberList = getMemberList(sEmp, sStart, sEnd);
        if( vMemberList.size() <= 0 ) {	// 社員が見つからない
            return false;
        }

        // グループの取得
        List<String> vGroupID = CollUtil.newArrayList();
        for (List<String> vMember : vMemberList) {
            vGroupID.add(vMember.get(TmgMemberList.DEFAULT_KEY_GROUPID));
        }
        // グループの権限
        return existsGroup(sStart, sEnd, vGroupID);

    }

    /**
     * sDate-システム日付の範囲において、指定したグループに対して何らかの権限を持っているかどうかを判定します。
     * @param sDate 基準日
     * @param sGroup グループID
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean existsGroup(String sDate, String sGroup) throws Exception {
        // 2007/10/02 Shishido #305 基準日-システム日付の範囲についてチェックするよう修正
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return existsGroup(sDate, sdf.format(gcSysdate.getTime()), sGroup);
//		return existsGroup(sDate, sDate, sGroup);
    }

    /**
     * 開始日-終了日の範囲において、指定したグループに対して何らかの権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param sGroup グループID
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean existsGroup(String sStart, String sEnd, String sGroup) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        // リストがnullの場合
        if( groupList == null ) {
            return false;
        }
        List<String> vGroupID = CollUtil.newArrayList();
        vGroupID.add(sGroup);

        return existsGroup(sStart, sEnd, vGroupID);
    }

    /**
     * 開始日-終了日の範囲において、指定したグループ(複数)に対して何らかの権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param vGroupID グループID
     * @return	boolean	true:持っている false:持っていない
     */
    private boolean existsGroup(String sStart, String sEnd, List<String> vGroupID) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }
        // 部署の取得
        List<List<String>> vGroupList = getGroupList(vGroupID, sStart, sEnd);

        // 2007.08.07  H.kawabata  TmgReferList内のメソッドに以下の処理を書き込むと現在正常に稼動している処理への影響があるので
        //                        「isThereSomeEmployees()」は各コンテンツで実装し、判断させるように変更します。
        // /* 2007.08.07  H.kawabata      */
        // // 現在選択しているグループに、対象年月時点で1人も社員が存在していない場合はfalseを返す
        // if(!isThereSomeEmployees()) {
        //		return false;
        // }
        // 何らかの権限があるかどうか
        for(Iterator<List<String>> i = vGroupList.iterator(); i.hasNext();){
            List<String> vGroup = i.next();
            if(existsAuthority(vGroup)) {	// ONなら
                return true;
            }
        }
        return false;
    }

    /**
     * 開始日-終了日の範囲において、指定した職員に対して決裁レベルを返します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @return String 決裁レベル
     */
    private int getCheckApprovalLevel(String sStart, String sEnd, String psEmpId) throws Exception {
        // 初期化
        List<String> vGroupID = CollUtil.newArrayList();
        // 勤怠管理サイトであれば、常に最大値
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return giApprovalLevelMax;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常に最小値を返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return giApprovalLevelMin;
            }
        // リストがnullの場合
        if(groupList == null || memberList == null){
            return giApprovalLevelMin;
        }
        // メンバーの取得
        List<List<String>> vMemberList = getMemberList(psEmpId, sStart, sEnd);
        if( vMemberList.size() <= 0 ) {
            return giApprovalLevelMin;
        }
        for (List<String> vMember : vMemberList) {
            vGroupID.add(vMember.get(TmgMemberList.DEFAULT_KEY_GROUPID));
        }
        // 部署の取得
        List<List<String>> vGroupList = getGroupList(vGroupID, sStart, sEnd);
        if( vGroupList.size() <= 0 ) {
            return giApprovalLevelMin;
        }
        for (List<String> vEmpGroupList : vGroupList) {
            return getCheckApprovalLevel(vEmpGroupList);
        }
        return giApprovalLevelMin;
    }

    /**
     * 開始日-終了日の範囲において、指定した職員に対して決裁レベル(名称)を返します。
     * @param sStart 開始日
     * @param sEnd 終了日
     */
    private String getCheckApprovalLevelName(String sStart, String sEnd, String psEmpId) throws Exception {

        // 初期化
        List<String> vGroupID = CollUtil.newArrayList();

        // 勤怠管理サイトでも勤怠承認サイトでもない場合、常に最小値を返す
        if(!isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN) && !isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return null;
        }
        // リストがnullの場合
        if(groupList == null || memberList == null){
            return null;
        }
        // メンバーの取得
        List<List<String>> vMemberList = getMemberList(psEmpId, sStart, sEnd);
        if( vMemberList.size() <= 0 ) {
            return null;
        }
        for (List<String> vMember : vMemberList) {
            vGroupID.add(vMember.get(TmgMemberList.DEFAULT_KEY_GROUPID));
        }
        // 部署の取得
        List<List<String>> vGroupList = getGroupList(vGroupID, sStart, sEnd);
        if( vGroupList.size() <= 0 ) {
            return null;
        }
        for (List<String> vEmpGroupList : vGroupList) {
            return getCheckApprovalLevelName(vEmpGroupList);
        }
        return null;
    }

    /**
     * sDate-システム日付の範囲において、指定した部署に対して何らかの権限を持っているかどうかを判定します。
     * @param sDate 基準日
     * @param sSection 部署コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean existsSection(String sDate, String sSection) throws Exception {
        // 2007/10/02 Shishido #305 基準日-システム日付の範囲についてチェックするよう修正
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return existsSection(sDate, sdf.format(gcSysdate.getTime()), sSection);
    }

    /**
     * 開始日-終了日の範囲において、指定した部署に対して何らかの権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param sSection 部署コード
     * @return	boolean	true:持っている false:持っていない
     */
    public boolean existsSection(String sStart, String sEnd, String sSection) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        // リストがnullの場合
        if( groupList == null ) {
            return false;
        }
        List<String> vSectionID = CollUtil.newArrayList();
        vSectionID.add(sSection);
        return existsSection(sStart, sEnd, vSectionID);

    }

    /**
     * 開始日-終了日の範囲において、指定した部署(複数)に対して何らかの権限を持っているかどうかを判定します。
     * @param sStart 開始日
     * @param sEnd 終了日
     * @param vSectionID 部署コード
     * @return	boolean	true:持っている false:持っていない
     */
    private boolean existsSection(String sStart, String sEnd, List<String> vSectionID) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        // 部署の取得
        List<List<String>> vGroupList = getGroupListForSection(vSectionID, sStart, sEnd);
        // 2007.08.07  H.kawabata  TmgReferList内のメソッドに以下の処理を書き込むと現在正常に稼動している処理への影響があるので
        //                         「isThereSomeEmployees()」は各コンテンツで実装し、判断させるように変更します。
        // /* 2007.08.07  H.kawabata */
        // // 現在選択している部署に、対象年月時点で1人も社員が存在していない場合はfalseを返す
        // if(!isThereSomeEmployees()) {
        // 		return false;
        // }

        // 何らかの権限があるかどうか
        for (List<String> vGroup : vGroupList) {
            if (existsAuthority(vGroup)) {    // ONなら
                return true;
            }
        }
        return false;
    }

    /**
     * 指定した基準日時点において、何らかの権限を持っているかどうか(即ち、参照権限があるかどうか)を判定します。<br>
     * 勤怠承認サイトにおいて、画面上に表示されている社員のいずれかに対して、参照権限を持っているかどうかを返します。<br>
     * 個人系コンテンツでは、選択中の社員に対して、参照権限を持っているかどうかを返します。<br>
     * 一覧系コンテンツでは、選択中のビューに応じて、参照権限を持っているかどうかを返します。<br>
     * @param sDate 基準日
     * @return	boolean true:持っている false:持っていない
     */
    public boolean existsAnyone(String sDate) throws Exception {
        // 2007/09/27 Shishido #305 基準日-システム日付の範囲についてチェックする
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return existsAnyone(sDate, sdf.format(gcSysdate.getTime()));

    }

    /**
     * 開始日-終了日の範囲において、何らかの権限を持っているかどうか(即ち、参照権限があるかどうか)を判定します。<br>
     * 勤怠承認サイトにおいて、画面上に表示されている社員のいずれかに対して、参照権限を持っているかどうかを返します。<br>
     * 個人系コンテンツでは、選択中の社員に対して、参照権限を持っているかどうかを返します。<br>
     * 一覧系コンテンツでは、選択中のビューに応じて、参照権限を持っているかどうかを返します。<br>
     * <ul>
     * <li>グループ毎：選択中のグループに所属している社員のいずれかに対して参照権限を持っているかどうか
     * <li>部署ごと：選択中の部署に所属している社員のいずれかに対して権限を持っているかどうか
     * <li>全て：表示されている社員のいずれかに対して権限を持っているかどうか
     * </ul>
     * @param sStart 開始日
     * @param sEnd 終了日
     * @return	boolean true:持っている false:持っていない
     */
    public boolean existsAnyone(String sStart, String sEnd) throws Exception {
        // 勤怠管理サイトであれば、常にtrue
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return true;
        }else
            // 勤怠管理サイトでも勤怠承認サイトでもない場合、常にfalseを返す
            if(!isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
                return false;
            }

        if(treeViewType == TREEVIEW_TYPE_EMP){
            // 個人系コンテンツであれば、選択中の社員に対して参照権限を持っているかどうかを判定
            return existsEmployee(sStart, sEnd, targetMember_perm);
        }else{
            if(memberList == null || groupList == null){
                return false;
            }

            if(PERM_SELECTEDVIEW_GROUP.equals(getSelectedView())){
                // 一覧系(グループ毎)であれば、選択中のグループに対して参照権限を持っているかどうかを判定
                return existsGroup(sStart, sEnd, targetGroup_perm);
            }else if(PERM_SELECTEDVIEW_SECTION.equals(getSelectedView())){
                // 一覧系(部署毎)であれば、選択中の部署に対して参照権限を持っているかどうかを判定
                return existsSection(sStart, sEnd, targetSec_perm);
            }else{
                // 指定された日付の範囲内で参照可能なグループが存在するかどうかを判定
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
                Date dStart	= sdf.parse(sStart);
                Date dEnd	= sdf.parse(sEnd);

                // 2007.08.07  H.kawabata  TmgReferList内のメソッドに以下の処理を書き込むと現在正常に稼動している処理への影響があるので
                //                         「isThereSomeEmployees()」は各コンテンツで実装し、判断させるように変更します。
                // /* 2007.08.07  H.kawabata */
                // // 現在選択している部署(もしくはグループ)に、対象年月時点で1人も社員が存在していない場合はfalseを返す
                // if(!isThereSomeEmployees()) {
                // 		return false;
                // }

                // 日付の絞込
                List<List<String>> vGroupList = JSONArrayGenerator.selectDataArrayBetween(
                        groupList.getDataArray(), dStart, dEnd, TmgGroupList.DEFAULT_KEY_DSTART, TmgGroupList.DEFAULT_KEY_DEND, sdf );

                for (List<String> vGroup : vGroupList) {
                    // 権限があったら
                    if (existsAuthority(vGroup)) {
                        return true;
                    }
                }

                return false;	// 権限がないので
            }
        }
    }

    /**
     * 開始日-終了日の範囲において、決裁レベルを返します。<br>
     * 勤怠承認サイトにおいて、画面上に表示されているグループの決裁レベルを返します。<br>
     * 勤怠承認サイトにおいて、決裁レベルの最大値を返します。<br>
     * 権限がない場合は、決裁レベルの最小値を返します。<br>
     * @param psStart 開始日
     * @param psEnd   終了日
     * @return String 決裁レベル
     */
    public int getApprovalLevel(String psStart, String psEnd, String psEmpId) {

        // 勤怠管理サイトでも勤怠承認サイトでもない場合、常に最小値を返す
        if(!isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN) && !isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return giApprovalLevelMin;
        }
        try {
            return getCheckApprovalLevel(psStart, psEnd, psEmpId);
        } catch(Exception e){
            e.printStackTrace();
            return giApprovalLevelMin;
        }
    }

    /**
     * 開始日-終了日の範囲において、決裁レベル(名称)を返します。<br>
     * 権限がない場合は、nullを返します。<br>
     * @param psStart 開始日
     * @param psEnd   終了日
     * @return String 決裁レベル
     */
    public String getApprovalLevelName(String psStart, String psEnd, String psEmpId) {
        // 勤怠管理サイトでも勤怠承認サイトでもない場合、常に最小値を返す
        if(!isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN) && !isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return null;
        }
        try {
            return getCheckApprovalLevelName(psStart, psEnd, psEmpId);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 基準日時点において、指定された権限を持っている社員番号の一覧を返します。
     * @param sDate 基準日
     * @param sAuthority 権限コード
     * @return	Vector 社員番号の一覧
     */
    public List<String> getEmpArrayHasAuthority(String sDate, String sAuthority) throws Exception {
        List<String> vEmpID = CollUtil.newArrayList();
        if(groupList == null || memberList == null){
            // 初期化されていない場合、空のVectorを返す
            return vEmpID;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date dDate	= sdf.parse(sDate);
        // 指定日付時点で指定された権限を持っているグループの一覧を取得
        List<String> vGroupID = getGroupArrayHasAuthority( sDate, sAuthority );
        // 日付の絞込
        List<List<String>> vMemberList = JSONArrayGenerator.selectDataArrayBetween(
                memberList.getDataArray(), dDate, dDate, TmgMemberList.DEFAULT_KEY_DSTART, TmgMemberList.DEFAULT_KEY_DEND, sdf );
        // 社員の絞込
        for (List<String> vMember : vMemberList) {
            for (String s : vGroupID) {
                if (StrUtil.equals(vMember.get(TmgMemberList.DEFAULT_KEY_GROUPID), s)) {
                    vEmpID.add(vMember.get(TmgMemberList.DEFAULT_KEY_EMPID));
                }
            }
        }
        return vEmpID;
    }

    /**
     * 基準日時点において、指定された権限を持っているグループIDの一覧を返します。
     * @param sDate 基準日
     * @param sAuthority 権限コード
     * @return	Vector グループIDの一覧
     */
    private List<String> getGroupArrayHasAuthority(String sDate, String sAuthority) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date dDate	= sdf.parse(sDate);
        // 日付の絞込
        List<List<String>> vGroupList = JSONArrayGenerator.selectDataArrayBetween(
                groupList.getDataArray(), dDate, dDate, TmgGroupList.DEFAULT_KEY_DSTART, TmgGroupList.DEFAULT_KEY_DEND, sdf );
        // グループの検索
        List<String> vGroupID = CollUtil.newArrayList();
        int iAuthority	= getAuthorityIndex(sAuthority);
        for(Iterator<List<String>> i = vGroupList.iterator(); i.hasNext();){
            List<String> vGroup = i.next();
            if(StrUtil.equals(vGroup.get(iAuthority),TmgUtil.Cs_MGD_ONOFF_1)) {	// ONなら
                vGroupID.add(vGroup.get(TmgGroupList.DEFAULT_KEY_GROUPID));
            }
        }
        return vGroupID;
    }

    /**
     * 基準日時点において、指定された権限を持っている部署コードの一覧を返します。
     * @param sDate 基準日
     * @param sAuthority 権限コード
     * @return Vector 部署コードの一覧
     */
    private List<String> getSectionArrayHasAuthority(String sDate, String sAuthority) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date dDate	= sdf.parse(sDate);
        // 日付の絞込
        List<List<String>> vGroupList = JSONArrayGenerator.selectDataArrayBetween(
                groupList.getDataArray(), dDate, dDate, TmgGroupList.DEFAULT_KEY_DSTART, TmgGroupList.DEFAULT_KEY_DEND, sdf );
        // 部署の検索
        List<String> vSectionID = CollUtil.newArrayList();
        int iAuthority	= getAuthorityIndex(sAuthority);
        for(Iterator<List<String>> i = vGroupList.iterator(); i.hasNext();){
            List<String> vGroup = i.next();
            if(StrUtil.equals(vGroup.get(iAuthority),TmgUtil.Cs_MGD_ONOFF_1)) {	// ONなら
                vSectionID.add(vGroup.get(TmgGroupList.DEFAULT_KEY_SECID));
            }
        }
        return vSectionID;
    }

    /**
     * 権限コードより、グループ一覧のインデックスを返します。
     * @param sAuthority 権限コード
     * @return int インデックス
     */
    private int getAuthorityIndex(String sAuthority) {
        if(StrUtil.equals(sAuthority,TmgUtil.Cs_AUTHORITY_RESULT)) {
            return TmgGroupList.DEFAULT_KEY_RESULT;
        }
        if(StrUtil.equals(sAuthority,TmgUtil.Cs_AUTHORITY_NOTIFICATION)) {
            return TmgGroupList.DEFAULT_KEY_NOTIFICATION;
        }
        if(StrUtil.equals(sAuthority,TmgUtil.Cs_AUTHORITY_OVERTIME)) {
            return TmgGroupList.DEFAULT_KEY_OVERTIME;
        }
        if(StrUtil.equals(sAuthority,TmgUtil.Cs_AUTHORITY_AUTHORITY)) {
            return TmgGroupList.DEFAULT_KEY_AUTHORITY;
        }
        //#427
        if(StrUtil.equals(sAuthority,TmgUtil.Cs_AUTHORITY_SCHEDULE)) {
            return TmgGroupList.DEFAULT_KEY_SCHEDULE;
        }
        // #1160
        if(StrUtil.equals(sAuthority,TmgUtil.Cs_AUTHORITY_MONTHLYAPPROVAL)) {
            return TmgGroupList.DEFAULT_KEY_MONTHLYAPPROVAL;
        }
        return -1;	// 見つからなかった場合
    }

    /**
     * 何らかの権限があるかどうかを返します
     * @param vGroup groupListの1件
     * @return boolean true:ある false:ない
     */
    private boolean existsAuthority(List<String> vGroup) {
        if((StrUtil.equals(vGroup.get(TmgGroupList.DEFAULT_KEY_RESULT),TmgUtil.Cs_MGD_ONOFF_1))) {
            return true;
        }
        if (StrUtil.equals(vGroup.get(TmgGroupList.DEFAULT_KEY_NOTIFICATION),TmgUtil.Cs_MGD_ONOFF_1) ) {
            return true;
        }
        if (StrUtil.equals(vGroup.get(TmgGroupList.DEFAULT_KEY_OVERTIME),TmgUtil.Cs_MGD_ONOFF_1)) {
            return true;
        }
        if (StrUtil.equals(vGroup.get(TmgGroupList.DEFAULT_KEY_AUTHORITY),TmgUtil.Cs_MGD_ONOFF_1)) {
            return true;
        }
        //#427
        if (StrUtil.equals(vGroup.get(TmgGroupList.DEFAULT_KEY_SCHEDULE),TmgUtil.Cs_MGD_ONOFF_1)) {
            return true;
        }
        // #1160
        if(StrUtil.equals(vGroup.get(TmgGroupList.DEFAULT_KEY_MONTHLYAPPROVAL),TmgUtil.Cs_MGD_ONOFF_1)) {
            return true;
        }
        return false;
    }

    /**
     * 決裁レベルを返します
     * @param vGroup groupListの1件
     * @return String 決裁レベル
     */
    private int getCheckApprovalLevel(List<String> vGroup) {
        String sApprovalLevel = vGroup.get(TmgGroupList.DEFAULT_KEY_APPROVALLEVEL);
        if (sApprovalLevel == null){
            return giApprovalLevelMin;
        } else {
            return Integer.parseInt(sApprovalLevel);
        }
    }

    /**
     * 決裁レベル(名称)を返します
     * @param vGroup groupListの1件
     * @return String 決裁レベル
     */
    private String getCheckApprovalLevelName(List<String> vGroup) {
        String sApprovalLevelName = vGroup.get(TmgGroupList.DEFAULT_KEY_APPROVALLEVELNAME);
        if (sApprovalLevelName == null){
            return null;
        } else {
            return sApprovalLevelName;
        }
    }

    /**
     * 指定した社員の一覧を返します。
     * @param sEmp 社員番号
     * @param sStart 開始日
     * @param sEnd 終了日
     * @return Vector memberListから抽出したもの
     */
    private List<List<String>> getMemberList(String sEmp, String sStart, String sEnd) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date dStart	= sdf.parse(sStart);
        Date dEnd	= sdf.parse(sEnd);
        List<List<String>> vData = CollUtil.newArrayList();
        // 日付の絞込
        List<List<String>> vMemberList = JSONArrayGenerator.selectDataArrayBetween(
                memberList.getDataArray(), dStart, dEnd, TmgMemberList.DEFAULT_KEY_DSTART, TmgMemberList.DEFAULT_KEY_DEND, sdf );
        // 社員の絞込
        for (List<String> vMember : vMemberList) {
            if (StrUtil.equals(vMember.get(TmgMemberList.DEFAULT_KEY_EMPID), sEmp)) {
                // 社員のデータ
                vData.add(vMember);
            }
        }
        return vData;
    }

    /**
     * 指定したグループの一覧を返します。
     * @param vGroupID グループID
     * @param sStart 開始日
     * @param sEnd 終了日
     * @return Vector groupListから抽出したもの
     */
    private List<List<String>> getGroupList(List<String> vGroupID, String sStart, String sEnd) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date dStart	= sdf.parse(sStart);
        Date dEnd	= sdf.parse(sEnd);
        List<List<String>> vData = CollUtil.newArrayList();
        // 日付の絞込
        List<List<String>> vGroupList = JSONArrayGenerator.selectDataArrayBetween(
                groupList.getDataArray(), dStart, dEnd, TmgGroupList.DEFAULT_KEY_DSTART, TmgGroupList.DEFAULT_KEY_DEND, sdf );
        // グループの絞込
        for (List<String> vGroup : vGroupList) {
            // グループIDのループ
            for (String s : vGroupID) {
                if (StrUtil.equals(vGroup.get(TmgGroupList.DEFAULT_KEY_GROUPID), s)) {
                    // グループのデータ
                    vData.add(vGroup);
                }
            }
        }
        return vData;
    }

    /**
     * 指定した部署に属しているグループの一覧を返します。
     * @param vSectionID 部署コード
     * @param sStart 開始日
     * @param sEnd 終了日
     * @return Vector groupListから抽出したもの
     */
    private List<List<String>> getGroupListForSection(List<String> vSectionID, String sStart, String sEnd) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        Date dStart	= sdf.parse(sStart);
        Date dEnd	= sdf.parse(sEnd);
        List<List<String>> vData = CollUtil.newArrayList();
        // 日付の絞込
        List<List<String>> vGroupList = JSONArrayGenerator.selectDataArrayBetween(
                groupList.getDataArray(), dStart, dEnd, TmgGroupList.DEFAULT_KEY_DSTART, TmgGroupList.DEFAULT_KEY_DEND, sdf );
        // 部署の絞込
        for (List<String> vGroup : vGroupList) {
            // グループIDのループ
            for (String s : vSectionID) {
                if (StrUtil.equals(vGroup.get(TmgGroupList.DEFAULT_KEY_SECID), s)) {
                    // グループのデータ
                    vData.add(vGroup);

                }
            }
        }
        return vData;
    }

    private String getTargetEmpData(int keyIndex) {
        if( StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(treeViewType == TREEVIEW_TYPE_EMP){
                if(targetEmp_admin == null){
                    return null;
                }
                return empList.getTargetEmpData(targetEmp_admin,keyIndex);
            }else{
                return null;
            }
        }else
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(treeViewType == TREEVIEW_TYPE_EMP){
                if(targetMember_perm == null){
                    return null;
                }
                return memberList.getTargetMemberData(targetMember_perm,keyIndex);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 検索対象ユーザの顧客コードを返します。<BR>
     * 勤怠管理サイトおよび勤怠承認サイトにおいて、
     * 個人を表示するタイプのコンテンツの場合は、
     * 選択された社員の社員番号を返します。
     * 勤怠管理サイトにおいて、部署が選択されていない状態の場合、nullを返します。
     * それ以外の場合は、nullを返します。
     * @return String 社員番号
     */
    public String getTargetCust() {
        if( StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return getTargetEmpData(TmgEmpList.DEFAULT_KEY_CUST);
        }else
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return getTargetEmpData(TmgMemberList.DEFAULT_KEY_CUST);
        }else{
            return null;
        }
    }

    /**
     * 検索対象ユーザの法人コードを返します。<BR>
     * 勤怠管理サイトおよび勤怠承認サイトにおいて、
     * 個人を表示するタイプのコンテンツの場合は、
     * 選択された社員の社員番号を返します。
     * 勤怠管理サイトにおいて、部署が選択されていない状態の場合、nullを返します。
     * それ以外の場合は、nullを返します。
     * @return String 社員番号
     */
    public String getTargetComp() {
        if( StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return getTargetEmpData(TmgEmpList.DEFAULT_KEY_COMP);
        }else
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return getTargetEmpData(TmgMemberList.DEFAULT_KEY_COMP);
        }else{
            return null;
        }
    }

    /**
     * 検索対象ユーザの社員番号を返します。<BR>
     * 勤怠管理サイトおよび勤怠承認サイトにおいて、
     * 個人を表示するタイプのコンテンツの場合は、
     * 選択された社員の社員番号を返します。
     * 勤怠管理サイトにおいて、社員が選択されていない状態の場合、nullを返します。
     * それ以外の場合は、nullを返します。
     * @return String 社員番号
     */
    public String getTargetEmployee() {
        if( StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(treeViewType == TREEVIEW_TYPE_EMP){
                return targetEmp_admin;
            }else{
                return null;
            }
        }else
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(treeViewType == TREEVIEW_TYPE_EMP){
                return targetMember_perm;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 勤怠管理サイトおよび勤怠承認サイトにおいて、
     * 個人を表示するタイプのコンテンツの場合は、
     * 選択された社員の氏名を返します。
     * 勤怠管理サイトにおいて、社員が選択されていない状態の場合、nullを返します。
     * それ以外の場合は、nullを返します。
     * @return String 社員番号
     */
    public String getTargetEmployeeName() {
        if( StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            return getTargetEmpData(TmgEmpList.DEFAULT_KEY_EMPNAME);
        }else
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return getTargetEmpData(TmgMemberList.DEFAULT_KEY_EMPNAME);
        }else{
            return null;
        }
    }

    /**
     * 勤怠管理サイトおよび勤怠承認サイトにおいて、
     * 個人を表示するタイプのコンテンツの場合は、
     * 選択された社員の勤怠種別IDを返します。
     * 勤怠管理サイトにおいて、社員が選択されていない状態の場合、nullを返します。
     * それ以外の場合は、nullを返します。
     * @return String 勤怠種別ID
     */
    public String getTargetEmployeeWorkerTypeId() {
        if( StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(treeViewType == TREEVIEW_TYPE_EMP){
                return empList.getTargetEmpWorkerTypeId(targetEmp_admin);
            }else{
                return null;
            }
        }else
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(treeViewType == TREEVIEW_TYPE_EMP){
                return memberList.getTargetMemberWorkerTypeId(targetMember_perm);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 勤怠管理サイトおよび勤怠承認サイトにおいて、
     * 個人を表示するタイプのコンテンツの場合は、
     * 選択された社員の勤怠種別IDを返します。
     * 勤怠管理サイトにおいて、社員が選択されていない状態の場合、nullを返します。
     * それ以外の場合は、nullを返します。
     * @return String 勤怠種別ID
     */
    public String getTargetEmployeeWorkerTypeName() {
        if( StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            if(treeViewType == TREEVIEW_TYPE_EMP){
                return empList.getTargetEmpWorkerTypeName(targetEmp_admin);
            }else{
                return null;
            }
        }else
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            if(treeViewType == TREEVIEW_TYPE_EMP){
                return memberList.getTargetMemberWorkerTypeName(targetMember_perm);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 勤怠承認サイトにおいて、選択されたグループのグループIDを返します。
     * それ以外の場合は、nullを返します。
     * @return String グループID
     */
    public String getTargetGroup() {
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return targetGroup_perm;
        }else{
            return null;
        }
    }

    /**
     * 勤怠承認サイトにおいて、選択されたグループのグループ名を返します。
     * それ以外の場合は、nullを返します。
     * @return String グループ名
     */
    public String getTargetGroupName() {
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return groupList.getTargetGroupName(targetGroup_perm);
        }else{
            return null;
        }
    }

    /**
     * 勤怠管理サイトおよび勤怠承認サイトにおいて、選択された部署の部署コードを返します。
     * 勤怠管理サイトにおいて、部署が選択されていない状態の場合、nullを返します。
     * それ以外の場合は、nullを返します。
     * @return String 部署コード
     */
    public String getTargetSec() {
        if(
                StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)
                        || this.treeViewType == TREEVIEW_TYPE_LIST_WARD
        ){
            return targetSec_admin;
        }else
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)){
            return targetSec_perm;
        }else{
            return null;
        }
    }


    /**
     * 勤怠管理サイトおよび勤怠承認サイトにおいて、選択された部署の部署略称を返します。
     * 勤怠管理サイトにおいて、部署が選択されていない状態の場合、nullを返します。
     * それ以外の場合は、nullを返します。
     * @return String 部署略称
     */
    public String getTargetSecName() {
        // 初期化
        String sSecName = null;
        // サイト判定
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
            // 部局か組織か判定を行う
            if (orgTree != null){
                sSecName = orgTree.getTargetSectionNameNoCounts(targetSec_admin);
            } else {
                sSecName = divTree.getTargetSectionNameNoCounts(targetSec_admin);
            }
            return sSecName;
        } else if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)) {
            return groupList.getTargetSectionName(targetSec_perm);
        } else {
            return null;
        }
    }

    /**
     * 勤怠承認サイトにおいて、選択されたビューを返します。
     * それ以外の場合は、nullを返します。
     * @return String ビュー
     */
    public String getSelectedView() {
        if(StrUtil.equals(psDBBean.getSiteId(),TmgUtil.Cs_SITE_ID_TMG_PERM)) {
            return selectedView_perm;
        }else{
            return null;
        }
    }

    public int getTreeViewType() {
        return treeViewType;
    }

    /**
     * 検索対象社員をセットします。<br>
     * 画面を介さず強制的に検索対象社員を変更したい場合に使用します。<br>
     * 実際には、一覧系コンテンツから個人系コンテンツへリダイレクトする際に使用します。<br>
     * 引数で指定された社員について、所属する部署・グループIDも再セットされます。<br>
     * なお、引数で指定された社員が存在しない場合、検索対象社員の変更は行われません。
     * @param sEmp 検索対象社員の社員番号
     */
    public void setTargetEmployee(String sEmp){
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)){
            String sSec = empList.getTargetEmpData(sEmp,TmgEmpList.DEFAULT_KEY_SECID);
            if(sSec != null){
                targetSec_admin = sSec;
                targetEmp_admin = sEmp;
                // セッションに登録する
                psDBBean.getSession().setAttribute(TREEVIEW_KEY_ADMIN_TARGET_SECTION, targetSec_admin);
                psDBBean.getSession().setAttribute(TREEVIEW_KEY_ADMIN_TARGET_EMP, targetEmp_admin);
            }
        }else if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            String sSec = memberList.getTargetMemberData(sEmp,TmgMemberList.DEFAULT_KEY_SECID);
            String sGroup = memberList.getTargetMemberData(sEmp,TmgMemberList.DEFAULT_KEY_GROUPID);
            if(sSec != null && sGroup != null){
                targetSec_perm = sSec;
                targetGroup_perm = sGroup;
                targetMember_perm = sEmp;
                psDBBean.getSession().setAttribute(TREEVIEW_KEY_PERM_TARGET_SECTION, targetSec_perm);
                psDBBean.getSession().setAttribute(TREEVIEW_KEY_PERM_TARGET_GROUP, targetGroup_perm);
                psDBBean.getSession().setAttribute(TREEVIEW_KEY_PERM_TARGET_EMP, targetMember_perm);
            }
        }
    }

    /**
     * 対象組織(グループ)社員存在チェックメソッド<br>
     * 現在選択している組織(もしくはグループ)に、<Br>
     * 引数で渡された年月日時点で社員が1人でも存在している場合trueを、<br>
     * 1人も存在していない場合はfalseを返す。<br>
     *
     * @author kawabata in 2007/08/06
     * @param  sDate     チェック対象年月
     * @return boolean   社員が存在する:ture, 社員が存在しない:false
     * @throws Exception 何らかの予期せぬ例外が発生した場合
     */
    public boolean isThereSomeEmployees(String sDate) throws Exception {
        String date = DateUtil.format(gcSysdate,DATE_FORMAT);
        // 勤怠管理サイト
        if(isSite(TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
            // 引数で渡された年月時点のDataArray内の情報を存在チェックの対象とする
            List<String> vDate = empList.getDataArrayBetween(date, sDate);
            if(empList == null) {
                return false;
            }
            return !(vDate == null || vDate.size() == 0);
            // 勤怠承認サイト
        }else if(isSite(TmgUtil.Cs_SITE_ID_TMG_PERM)){
            // 引数で渡された年月時点のDataArray内の情報を存在チェックの対象とする
            List<String> vDate = memberList.getDataArrayBetween(date, sDate);
            // ツリービュータイプ：一覧系 or 一覧系部署毎
            if(treeViewType == TREEVIEW_TYPE_LIST || treeViewType == TREEVIEW_TYPE_LIST_SEC){
                // 選択ビュータイプが「全て」の場合
                if(PERM_SELECTEDVIEW_ALL.equals(getSelectedView())){
                    if(memberList == null) {
                        return false;
                    }
                    return !(vDate == null || vDate.size() == 0);

                    // 選択ビュータイプが「部署」の場合
                } else if(PERM_SELECTEDVIEW_SECTION.equals(getSelectedView())) {
                    if(memberList == null) {
                        return false;
                    }
                    String[] targetSectionArray = {targetSec_perm};
                    //return memberList.isThereEmployeesInSection(targetSectionArray, sdf.format(gcSysdate.getTime()), sDate);
                    // 2007/10/02 Shishido パフォーマンス向上（↑メソッド内で日付絞込みを再び行っていて無駄）
                    List<String> targetDataArray = CollUtil.newArrayList();
                    targetDataArray = JSONArrayGenerator.selectDataArray(vDate, TmgMemberList.DEFAULT_KEY_SECID, targetSectionArray);
                    return !(targetDataArray == null || targetDataArray.size() == 0);
                    // 選択ビュータイプが「グループ」の場合
                } else if(PERM_SELECTEDVIEW_GROUP.equals(getSelectedView())) {
                    if(memberList == null) {
                        return false;
                    }
                    String[] targetGroupArray = {targetGroup_perm};
                    //return memberList.isThereEmployeesInGroup(targetGroupArray, sdf.format(gcSysdate.getTime()), sDate);
                    // 2007/10/02 Shishido パフォーマンス向上（↑メソッド内で日付絞込みを再び行っていて無駄）
                    List<String> targetDataArray = CollUtil.newArrayList();
                    targetDataArray = JSONArrayGenerator.selectDataArray(vDate, TmgMemberList.DEFAULT_KEY_GROUPID, targetGroupArray);
                    return !(targetDataArray == null || targetDataArray.size() == 0);
                    // その他のビュータイプを選択した場合
                } else {
                    if(memberList == null) {
                        return false;
                    }
                    return !(vDate == null || vDate.size() == 0);
                }
                // ツリービュータイプ：その他
            } else {
                if(memberList == null) {
                    return false;
                }
                return !(vDate == null || vDate.size() == 0);
            }
        }
        return false;
    }

    /**
     * 対象組織(グループ)社員存在チェックメソッド<br>
     * 現在選択している組織(もしくはグループ)に社員が1人でも存在している場合<br>
     * trueを、1人も存在していない場合はfalseを返す。<br>
     *
     * @author kawabata in 2007/08/06
     * @return boolean   社員存在チェック結果 true:存在する,  false:存在しない
     * @throws Exception 何らかの予期せぬ例外が発生した場合
     */
    public boolean isThereSomeEmployees() throws Exception {
        // 基準日時点の対象組織(グループ)社員存在チェック結果を返します
        return isThereSomeEmployees(targetDate);

    }

    // 基準日よりも過去かチェックする
    public void setCheckDay(String pRecordDate) {
        // 初期化
        Date date1 = DateUtil.date();
        // 現在日付よりも基準日が過去の場合は「false」をセットする
        this.gbCheckToDayFlg = compareDateString(pRecordDate, DateUtil.format(date1, DATE_FORMAT));
    }

    /**
     * 日付比較用
     * @param str1
     * @param str2
     * @return
     */
    public boolean compareDateString(String str1, String str2){
        //nullだったりyyyy/mm/dd以外の時は-1より小さい値を返す
        if(str1 == null || str2 == null){
            return false;
        }
        Date date1 = DateUtil.parse(str1);
        Date date2 = DateUtil.parse(str2);
        return SysDateUtil.isGreater(date1,date2);
    }

    /**
     * セッション同一チェック(管理対象フラグ使用条件、基準日についてセッション情報を確認し同一かチェックする）
     *
     * @return boolean
     */
    public boolean sessionSameCheck(){
        String useManage = (String)psDBBean.getSession().getAttribute(SESSION_KEY_USEMANAGEFLG);
        return String.valueOf(this.gbUseManageFLG).equals(useManage) && getRecordDate().equals(gsSessionDate);
    }

    /**
     * =============旧代码3288~3341 Start===============
     */
    /**
     * 全部局を参照可能かどうかを返します
     * @return
     */
    public boolean isAllDivision() {
        return ((Boolean)psDBBean.getSession().getAttribute(SESSION_KEY_DIVTREE_ALL)).booleanValue();
    }

    /**
     * トップレベルの組織コードを返します
     * @return
     */
    public String getRootSection() {
        return (String)psDBBean.getSession().getAttribute(SESSION_KEY_DIVTREE_ROOT);
    }

    public String getRecordDate() {
        return gsRecordDate;
    }

    public void setRecordDate(String pRecordDate) {
        this.gsRecordDate = pRecordDate;
    }

    public boolean isUseRecordDate() {
        return gbUseRecordDate;
    }

    public void setUseRecordDate(boolean pbUseRecordDate) {
        this.gbUseRecordDate = pbUseRecordDate;
    }

    public boolean isCheckToDayFlg() {
        return gbCheckToDayFlg;
    }

    /**
     * システム日付の翌月月末日をyyyy/mm/ddフォーマットで返す
     * @return
     */
    public String getMaxDate() {
        Date nextMonth = DateUtil.nextMonth();
        Date lastDay = DateUtil.endOfMonth(nextMonth);
        return DateUtil.format(lastDay,DATE_FORMAT);
    }
    /**
     * =============旧代码3288~3341 End===============
     */


    /**
     * 検索タブ機能を使用するか返却する
     * @return boolean true:使用する/false:使用しない
     */
    public boolean isUseSearcjEmp() {
        return this.gbUseSearcjEmp;
    }

    public void setUseSearcjEmp(boolean pbUseSearcjEmp) {
        gbUseSearcjEmp = pbUseSearcjEmp;
    }

    /**
     * ============= 旧代码3354~3391 Start============
     */
    public static final int ciSelectTreeTab = 0;
    public static final int ciSelectSearchTab = 1;

    // 選択されたタブがどちらか(ツリーか検索か)を保持する
    private Integer hidSelectTab = null;

    /**
     * 現在選択されているタブの値を返却する
     */
    public int getHidSelectTab() {
//        if (hidSelectTab == null){
            hidSelectTab = ciSelectTreeTab;
            if (psDBBean.getReqParam(TREEVIEW_OBJ_HIDSELECT) != null){
                hidSelectTab = Integer.parseInt(psDBBean.getReqParam(TREEVIEW_OBJ_HIDSELECT));
            } else if (psDBBean.getSession().getAttribute(TREEVIEW_OBJ_HIDSELECT) != null){
                hidSelectTab = Integer.parseInt(psDBBean.getSession().getAttribute(TREEVIEW_OBJ_HIDSELECT).toString());
            }
//        }
        return hidSelectTab;
    }

    /**
     * 現在選択されているタブが検索タブなのか判定し返却する
     */
    public boolean isSelectedSearchTab(){
        return getHidSelectTab() == ciSelectSearchTab;
    }

    /**
     * 現在選択されている検索項目を返却する(对应旧代码3393~3416)
     */
    private String searchItems = null;
    public String getSearchItems() {
//        if (searchItems == null){
            searchItems = TmgUtil.Cs_TREE_VIEW_ITEMS_KANANAME;
            if (psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHITEMES) != null){
                searchItems = psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHITEMES);
            } else if (psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHITEMS) != null){
                searchItems = (String)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHITEMS);
            }
//        }
        return searchItems;
    }

    /**
     * ============= 旧代码3354~3391 End============
     */

    /**
     * 現在選択されている検索条件を返却する(对应旧代码3420~3442)
     * @param なし
     */
    private String searchCondition = null;

    public String getSearchCondition() {
//        if (searchCondition == null){
            searchCondition = TmgUtil.Cs_TREE_VIEW_CONDITION_BROADMATCH;
            if (psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHCONDITION) != null){
                searchCondition = psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHCONDITION);
            } else if (psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHCONDITION) != null){
                searchCondition = (String)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHCONDITION);
            }
//        }
        return searchCondition;
    }

    /**
     * 現在入力されている検索内容を返却する（对应旧代码3443~3466）
     * @param なし
     */
    private String searchData = null;
    public String getSearchData() {
//        if (searchData == null){
            searchData = "";
            if (psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHDATA) != null){
                searchData =psDBBean.getReqParam(TREEVIEW_OBJ_HIDSEARCHDATA);
            } else if (psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATA) != null){
                // fix：接口化后不再需要后端负责判断使用session还是前端传过来的参数，全部交由前端控制
//                searchData = (String)psDBBean.getSession().getAttribute(SESSION_KEY_SEARCHDATA);
            }
//        }
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
//    private String msgSearchTree = null;
//    public String getMsgSearchTree(){
//      if (StrUtil.isNotBlank(msgSearchTree)){
//          return msgSearchTree;
//      }
//      List<TmgMgdMsgSearchTreeView> searchTreeViewList = buildSQLForSelectMsgSearchTree(psDBBean.getCustID(),psDBBean.getCompCode(), psDBBean.getLanguage(),DateUtil.date());
//      if (CollUtil.isNotEmpty(searchTreeViewList)) {
//        msgSearchTree = searchTreeViewList.get(0).getMsg();
//      }
//      return msgSearchTree;
//    }

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
