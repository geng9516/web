/*==============================================================================
 *  system      Progress@Site HR
 *  version     Ver3.7.3
 *  id          EvaluaterSettingConst
 *  title       �����ݒ�
 *  author      XXXX
 *  create      YYYY/MM/DD(#���Ή����ɗ����R�����g�쐬 �\�[�X�쐬�҂͕s��)
 *              �X�V��      �X�V��          �X�V���e
 *  update      2009/12/07  isolootsuki     #���Ή� �����o�[���t��ʂɒǉ��@�\��ǉ��̂��� �A�N�V�����ǉ�
 *              2009/12/21  isoltakahashi   #���Ή� ��E���ڒǉ��̂��߃A�N�V�����ǉ�
 *              2009/12/30  isolootsuki     #���Ή������̕����E�O���[�vID��ێ�����d�l�ƂȂ�������TGRM_CBASE_SECTIONID��TGRM_CBASE_GROUPID��ǉ�
 *              2010/11/18  scisatoh.a      #1422 �A��Ver1.0�Ɍ����āA�����ݒ�̃O���[�v�쐬/�����o�[���t�@�\�����ɔ����萔�ǉ���
 *              2010/12/01  ogawa           tmd#37 ���i�K���F�̑Ή�
 *              2010/12/21  ogawa           tmd#62 �O���[�v�쐬�E�O���[�v�ҏW������DB��
 *              2012/05/16  ogawa           tmd#635 �����ݒ��ʂ̕\�����b�Z�[�W��DB����擾����p�ɏC��
 *              2012/09/27  yamaguchi.k     tmd#725 �g�D�c���[�����擾����ׂ̃��N�G�X�g�L�[�ǉ�
 *              2014/05/19  yamaguchi.k     tmd#1015 v1.4 �����ݒ�̐ݒ�L�����Ԃ���ʂɕ\���o����l�ɂ���B
 *              2015/06/25  yamaguchi.k     tmd#1162 v1.5 �S�𕪂̐ݒ���e����ʂɈ�x�ɕ\������l�ɂ���B
 *  remark
 =============================================================================*/

package jp.smartcompany.job.modules.tmg.evaluatersetting;

public interface EvaluatorSettingConst {

	// LOG出力用ディスクリプタ
	public static final String BEAN_DESC = "EvaluaterSetting";

	/** リクエスト：組織ツリー基準日 */
    public static final String REQUEST_KEY_TREE_YYYYMMDD = "RecordDateCalendar";

	/** リクエスト：組織ツリー再表示ボタンクリックフラグ */
    public static final String REQUEST_KEY_TREE_REFRESH_FLG = "txtTmgReferListTreeViewRefreshFlg";

	public static final String DATE_FORMAT = "yyyy/MM/dd";

	 String DATE_FORMAT_DISP = "yyyy\\\"年\\\"MM\\\"月\\\"dd\\\"日\\\"(dy)";

	// アクション
	String ACT_DISP_REVALLIST = "ACT_Disp_REvalList";// 権限設定状況表示

	String ACT_MAKEGROUP_RGROUPNEW = "ACT_MakeGroup_RGroupNew"; // グループ作成画面表示

	String ACT_MAKEGROUP_CGROUP = "ACT_MakeGroup_CGroup"; // グループ登録

	String ACT_EDITGROUP_RGROUP = "ACT_EditGroup_RGroup"; // グループ編集画面表示

	String ACT_EDITGROUP_UGROUP = "ACT_EditGroup_UGroup"; // グループ設定値変更

	String ACT_EDITGROUP_IGROUP = "ACT_EditGroup_IGroup"; // グループ属性登録

	/** グループ名編集画面表示 */
	String ACT_EDITGROUP_RGROUPNAME = "ACT_EditGroup_RGrpName"; // グループ名編集画面表示

	/** グループ名変更 */
	String ACT_EDITGROUP_UGROUPNAME = "ACT_EditGroup_UGrpName";  // グループ名変更

	String ACT_EDITGROUP_DGROUP = "ACT_EditGroup_DGroup";  // グループ削除

	String ACT_ADDEVAL_REVALNEW = "ACT_AddEval_REvalNew"; // 承認者追加画面表示

	String ACT_ADDEVAL_CEVAL = "ACT_AddEval_CEval"; // 承認者登録

	String ACT_EDITEVAL_REVAL = "ACT_EditEval_REval";  // 承認者編集画面表示

	String ACT_EDITEVAL_UEVAL = "ACT_EditEval_UEval"; // 権限変更

	String ACT_EDITEVAL_DEVAL = "ACT_EditEval_DEval";  // 承認者削除

	String ACT_EDITMEMBER_RMEMBER = "ACT_EditMember_RMember"; // メンバー割付画面表示

	String ACT_EDITMEMBER_UMEMBER = "ACT_EditMember_UMember"; // メンバー割付変更

	String ACT_SEARCHEMP_REMPLIST = "ACT_SearchEmp_REmpList"; // 社員検索画面表示

	// 2009/12/7 isolootsuki #阪大対応 メンバー割付画面に追加機能を追加のため アクション追加
	String ACT_ADDEMP_REMPLIST = "ACT_AddEmp_REmpList"; // 社員追加画面表示

	String ACT_ADD_POST = "ACT_Add_Post";

	String START_ADD_POST_PART = "'<postpart>'";// 役職追加のタグ:(始)

	String END_ADD_POST_PART = "'</postpart>'"; // 役職追加のタグ:(終)

	String START_ADD_POST_PART_NIGHT = "'<postpartnight>'"; // 宿直のみ役職追加のタグ:(始)

	String END_ADD_POST_PART_NIGHT = "'</postpartnight>'"; // 宿直のみ役職追加のタグ:(終)

	String START_ADD_POST_PART_DAY = "'<postpartday>'"; // 日直のみ役職追加のタグ:(始)

	String END_ADD_POST_PART_DAY = "'</postpartday>'"; // 日直のみ役職追加のタグ:(終)

	// リクエスト：アクション
	String REQUEST_KEY_ACTION = "txtAction";

	// リクエスト：基準日
	String REQUEST_KEY_YYYYMMDD = "txtYYYYMMDD";

	// リクエスト：組織グループ
	String REQUEST_KEY_GROUP = "txtTargetGroupId";

	// リクエスト：削除チェックボックス
	String REQUEST_KEY_DELETE_GROUP = "lstDeleteGroupId";

	// リクエスト：既存データフラグ
	String REQUEST_KEY_EXIST_MEMBER = "lstExistMember";

	// 削除チェックボックス値
	String CHECKBOX_VALUE = "ON";

	// カラム位置
	// グループ一覧及び承認者一覧(権限設定情報表示画面)
	int COL_EVALLIST_GROUPID = 0; // グループID

	int COL_EVALLIST_GROUPNAME = 1;// グループ名

	int COL_EVALLIST_EMPLOYEEID = 2; // 社員番号

	int COL_EVALLIST_EMPLOYEENAME = 3; // 承認者氏名

	int COL_EVALLIST_SECTIONNAME = 4; // 所属部署名

	int COL_EVALLIST_POSTNAME = 5;// 役職名

	int COL_EVALLIST_EDITABLEFLG = 6;// 編集可能フラグ

	int COL_EVALLIST_RESULTS = 7; // 権限：勤怠承認

	int COL_EVALLIST_NOTIFICATION = 8; // 権限：休暇休出承認

	int COL_EVALLIST_OVERTIME = 9; // 権限：超過勤務命令

	int COL_EVALLIST_SCHEDULE = 10; // 権限：予定作成 #427

	int COL_EVALLIST_AUTHORITY = 11; // 権限：権限付与 #427順序変更

	int COL_EVALLIST_ADMINFLG = 12; // 人事管理フラグ

	int COL_EVALLIST_MONTHLYRESULTS = 13; // 権限：月次承認

	int COL_EVALLIST_SECTIONEVALUATER = 14; // // デフォルト承認者フラグ

	int COL_EVALLIST_APPROVALLEVEL = 15; // 決裁レベル

	int COL_EVALLIST_TERM_FROM = 16; // 権限設定有効期間・開始日

	int COL_EVALLIST_TERM_TO   = 17; // 権限設定有効期間・終了日

	// リンク用基準日
	int COL_DATE_PREVMONTH = 0;

	int COL_DATE_THISMONTH = 1;

	int COL_DATE_NEXTMONTH = 2;

	// 画面表示用メッセージ取得
    int COL_EVASET_MESSAGE_MASTERCODE = 0;

    int COL_EVASET_MESSAGE_MSG = 1;

	// 承認者の人数(権限設定情報表示画面)
	int COL_EVALNUM_GROUPID = 0; // グループID

	int COL_EVALNUM_EMPNUM = 1; // 承認者人数

	// グループ名称(グループ編集画面)
	int COL_GROUP_GROUPNAME = 0; // グループ名

	// グループ一覧(承認者追加画面表示画面、メンバー割付画面表示)
	int COL_EVALNEW_GROUPID = 0;// グループID

	int COL_EVALNEW_GROUPNAME = 1; // グループ名

	// 承認者情報(承認者編集画面表示画面)
	int COL_EVAL_EMPLOYEENAME = 0; // 承認者氏名

	int COL_EVAL_SECTIONNAME = 1; // 所属部署名

	int COL_EVAL_POSTNAME = 2; // 役職名

	int COL_EVAL_RESULTS = 3;// 権限：勤怠承認

	int COL_EVAL_NOTIFICATION = 4; // 権限：休暇休出承認

	int COL_EVAL_OVERTIME = 5; // 権限：超過勤務命令

	int COL_EVAL_SCHEDULE = 6; // 権限：予定作成 #427

	int COL_EVAL_AUTHORITY = 7; // 権限：権限付与 #427順序変更

	int COL_EVAL_GROUPNAME = 8; // グループ名

	int COL_EVAL_MONTHLYRESULTS = 9; // 権限：月次承認

	int COL_EVAL_APPROVALLEVEL = 10; // 権限：月次承認

	int COL_EVAL_TERM_FROM = 11;// 権限設定有効期間・開始日

	int COL_EVAL_TERM_TO   = 12; // 権限設定有効期間・終了日

	// 承認者情報(承認者追加画面表示画面)
	int COL_ADD_GROUPID   = 0; // グループコード

	int COL_ADD_GROUPNAME = 1;// グループ名

	int COL_ADD_GROUP_END = 2; // グループ終了日（権限設定・終了日用）

	// メンバー情報(メンバー割付画面表示画面)
	int COL_MEMBER_EMPLOYEEID = 0; // 社員番号

	int COL_MEMBER_EMPLOYEENAME = 1;// 氏名

	int COL_MEMBER_GROUPID = 2; // グループID

	int COL_MEMBER_GROUPNAME = 3; // グループ名

	int COL_MEMBER_BASE_SECTIONID = 4;  // ベース部署ID

	int COL_MEMBER_BASE_GROUPID = 5; // ベースグループID

	// 承認者追加時
	int COL_MGD_DEFAULT_APPLEVEL = 0; // デフォルト決裁レベル

	// 一覧画面のグループ・承認者毎データ件数検索
	int COL_EVALEMPNUM_GROUPID = 0; // グループID

	int COL_EVALEMPNUM_EMPID   = 1; // 職員番号

	int COL_EVALEMPNUM_DATACNT = 2; // データ数

	// 変更画面の異動歴の期間
	int COL_EVAL_DESIG_FROM = 0; // 異動歴・開始日

	int COL_EVAL_DESIG_TO   = 1; // 異動歴・終了日

	// 権限設定用メッセージ取得マスターコード
	String TMG_EVASET_MESSAGE_DISPALERT = "TMG_EVASET_MESSAGE|dispAlert";

}
