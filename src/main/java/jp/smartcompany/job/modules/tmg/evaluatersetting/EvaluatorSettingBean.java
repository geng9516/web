package jp.smartcompany.job.modules.tmg.evaluatersetting;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.util.AjaxBean;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.tmg.evaluatersetting.vo.EvaluatorGroupVO;
import jp.smartcompany.job.modules.tmg.evaluatersetting.vo.EvaluatorMemberRightVO;
import jp.smartcompany.job.modules.tmg.evaluatersetting.vo.EvaluatorMemberVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgSearchRangeUtil;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluatorSettingBean {

    private TmgReferList referList;
    private final TmgSearchRangeUtil tmgSearchRangeUtil;
    private final ScCacheUtil scCacheUtil;
    private final AjaxBean ajaxBean;

    // 結果セット番号
    private final static int IDX_LIST			 = 0;	// グループ一覧及び承認者一覧
    private final static int IDX_EVALNUM		 = 1;	// グループごとの人数
    private final static int IDX_DATE			 = 2;	// リンク用基準日
    private final static int IDX_EVALEMPNUM	 = 3;	// グループ・職員ごとのデータ数

    public Map<String,Object> dispHandler(PsDBBean psDBBean) throws Exception {
        Map<String,Object> result = MapUtil.newHashMap();
        // ログインユーザーが、表示グループに対して権限を持っているか
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        boolean haveAuthority = processParams(psDBBean,params);
        referList = new TmgReferList(psDBBean, EvaluatorSettingConst.BEAN_DESC,params.getYYYYMMDD(),
                TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
        // REQUEST:組織
        params.setSection(referList.getTargetSec());
        params.setSectionName(referList.getTargetSecName());

        // REQUEST:組織グループ
        params.setRootGroup(referList.getTargetSec() + '|' + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);

        // 表示対象部署に対して、「権限付与」の権限を持っているか
        // V1.5対応 tmd#1162 システム日付時点で権限を持っている職員のみ編集を可能とする対応
        //	gbHaveAuthority = referList.hasAuthority(evaluaterSettingParam.getYYYYMMDD(), TmgUtil.Cs_AUTHORITY_AUTHORITY);
        String recordDate = (String)psDBBean.getRequestHash().get(TmgReferList.TREEVIEW_KEY_RECORD_DATE);
        haveAuthority = referList.hasAuthority(recordDate, TmgUtil.Cs_AUTHORITY_AUTHORITY);

        boolean btnEditMember = haveAuthority;
        boolean btnMakeGroup = haveAuthority;
        boolean btnAddEval = haveAuthority;
        result.put("btnEditMember",btnEditMember);
        result.put("btnMakeGroup",btnMakeGroup);
        result.put("btnAddEval",btnAddEval);
        result.put("YYYYMMDD",params.getYYYYMMDD());
        String adminInitMsg = null;
        // 勤怠管理サイトで初期表示の場合はここで終了
        if (!params.isSection()) {
            adminInitMsg = scCacheUtil.getSystemProperty("MSG_NO_SELECT_SECTION");
            result.put("adminInitMsg",adminInitMsg);
        }
        showDisp(params,psDBBean,result,haveAuthority);

        // 返回处理后前端需要的数据
        return result;
    }

    public Object makeGroupHandler(PsDBBean psDBBean,String targetSectionId,String targetGroupId,String lastTargetGroupId,String groupName,String empId) {
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        params.setSite(psDBBean.getSiteId());
        params.setLanguage(psDBBean.getLanguage());
        String txtAction = (String)psDBBean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_ACTION);
        psDBBean.getRequestHash().put("txtGroupName",groupName);
        if (StrUtil.isNotBlank(txtAction)) {
            params.setAction(txtAction);
        } else {
            params.setAction(EvaluatorSettingConst.ACT_MAKEGROUP_CGROUP);
        }
        params.setYYYYMMDD((String)psDBBean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_YYYYMMDD));
        params.setCompanyId(psDBBean.getCompCode());
        params.setCustomerID(psDBBean.getCustID());
        params.setSection(targetSectionId);
        params.setRootGroup(targetSectionId+"|"+TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);
        if (StrUtil.isNotBlank(targetGroupId)){
            params.setGroup(targetGroupId);
        } else if (StrUtil.isNotBlank(lastTargetGroupId)){
            params.setGroup(lastTargetGroupId);
        }
        params.setGroupName(groupName);
        if (StrUtil.isNotBlank(empId)){
            params.setEmployee(empId);
        } else {
            params.setEmployee(psDBBean.getUserCode());
        }
        Vector<String> vQuery = new Vector<>();
        vQuery.add(buildSQLForDeleteGroupErrMsg(params));  // エラーメッセージ削除
        vQuery.add(buildSQLForInsertGroupCheck(params,psDBBean));   // チェックテーブルへグループ名登録
        vQuery.add(buildSQLForInsertGroupErrMsg(params));  // エラーメッセージ追加
        vQuery.add(buildSQLForInsertGroupTrigger(params)); // トリガー追加
        vQuery.add(buildSQLForSelectGroupErrMsg(params));  // エラーメッセージ取得
        vQuery.add(buildSQLForDeleteGroupTrigger(params)); // トリガー削除
        vQuery.add(buildSQLForDeleteGroupErrMsg(params));  // エラーメッセージ削除
        vQuery.add(buildSQLForDeleteGroupCheck(params));   // エラーチェック削除
        Vector<Object> msgList = (Vector<Object>)ajaxBean.exeSQLs(vQuery,psDBBean).getResult().get(0);
        Vector<Object> msg = (Vector<Object>)msgList.get(0);
        if (CollUtil.isNotEmpty(msg)) {
            String errorMsg = (String)msg.get(0);
            if (StrUtil.equals(errorMsg ,"0")) {
               return GlobalResponse.ok("追加成功しました");
            } else {
                return GlobalResponse.error(errorMsg);
            }
        }
        return GlobalResponse.error("追加失敗しました");
    }

    /**
     * 権限設定状況表示の処理をするメソッド
     */
    private void showDisp(EvaluatorSettingParam params,PsDBBean psDBBean,Map<String,Object> result,boolean haveAuthority) {

        /** 勤怠シートの参照権限( */
        String authNotValidMsg;

        // 組織コードが選択されていない場合は終了
        PsResult psResult;
        List<EvaluatorGroupVO> dataList = CollUtil.newArrayList();
        if (params.isSection()) {
             //選択されている組織が検索対象範囲設定されていない場合はreturn
            // 管理サイトの場合のみ判定
            if (params.isSiteTa()) {
                // 没有权限访问直接返回
                if (!hasAuthOfSect(params.getSection(),psDBBean)) {
                    authNotValidMsg = scCacheUtil.getSystemProperty("MSG_NO_SELECT_SECTION");
                    result.put("authNotValidMsg",authNotValidMsg);
                    return;
                }
            }
            // 検索
            Vector <String> vQuery = new Vector <>();
            vQuery.add(buildSQLForSelectGroupAndEvaluater(params));  // 0 グループ一覧及び承認者一覧
            vQuery.add(buildSQLForSelectEvaluaterNum(params));       // 1 グループごとに設定されている社員の人数
            vQuery.add(buildSQLForTransitionDate(params));           // 2 画面遷移リンク用基準日取得(クエリ)
            vQuery.add(buildSQLForSelectEvaluaterEmpNum(params));    // 3 グループの承認者ごとに登録されている権限設定の歴数
            try {
                 psResult = psDBBean.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC);

                 // グループ・承認者毎の権限設定歴データ数マップデータを作成
                 Map<String,Object> groupEmpDataMap = MapUtil.newHashMap();
                 for (int i=0; i <= psDBBean.getCount(psResult,IDX_EVALEMPNUM); i++) {
                     // キー（グループID_承認者職員番号）
                     String key= psDBBean.valueAtColumnRow(psResult,IDX_EVALEMPNUM, EvaluatorSettingConst.COL_EVALEMPNUM_GROUPID, i) +
                                 "_" +
                                 psDBBean.valueAtColumnRow(psResult,IDX_EVALEMPNUM, EvaluatorSettingConst.COL_EVALEMPNUM_EMPID, i);
                     // 値（グループ・承認者が保持する権限設定データ歴数）
                     String value = psDBBean.valueAtColumnRow(psResult,IDX_EVALEMPNUM, EvaluatorSettingConst.COL_EVALEMPNUM_DATACNT, i);
                     if (StrUtil.isNotBlank(value)) {
                         groupEmpDataMap.put(key,value);
                     }
                 }

                int nGroupNum = -1;				// 表示するグループの番号（最初にカウントアップするので、-1開始）
                String sGroupName_Group = "";	// 表示するグループID
                String sGroupName_Eval  = "";	// 承認者の所属するグループID
                String sEvalEmpDataCnt  = "";	// 承認者の権限設定データ歴数
                boolean sEmpNameLinkTagFlg;		// 承認者氏名のリンクタグフラグ
                boolean sEmpNameBoldTagFlg;		// 承認者氏名の太字タグフラグ

                // ▽件数分だけループ
                for (int i=0;i<psDBBean.getCount(psResult, IDX_LIST);i++){

                    EvaluatorGroupVO vo = new EvaluatorGroupVO();

                    sGroupName_Eval = psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i);
                    // ▼ループ中にグループ名が切り替わった場合　かつ　勤怠承認サイトの場合、グループ名を表示
                    /* 2007/06/28  H.Kawabata      権限設定の仕様変更 */
                    if(!sGroupName_Group.equals(sGroupName_Eval)) {
                        sGroupName_Group = sGroupName_Eval;	//新しいグループIDをセット
                        nGroupNum++;	//グループ番号をカウントアップ

                        //グループに所属する人の数だけ、セルを結合
                        if (i == 0) { //2008/10/10
                               vo.setMain(true);
                        } else {
                               vo.setMain(false);
                        }
                        // ▼▼ 勤怠承認サイト かつ、組織直下の部署以外 かつ 権限付与されている 場合はリンクを表示。または管理サイトであれば表示する。
                        if ((params.isSiteTp() && i != 0 && haveAuthority) || params.isSiteTa()) {
                            // ▼▼▼組織直下の部署以外の場合、リンクを表示
                            // ルートグループの場合、組織属性編集画面への遷移リンクを表示する。
                            String groupId = psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i);
                            if (StrUtil.equals(
                                    groupId,
                                    params.getRootGroup())) {
                                vo.setEnableEditGroup(true);
                                vo.setEnableEditGroupName(false);
                            } else {
                                vo.setEnableEditGroup(false);
                                vo.setEnableEditGroupName(true);
                            }
                        } else {
                            vo.setEnableEditGroup(false);
                            vo.setEnableEditGroupName(false);
                        }
                        vo.setGroup(psDBBean.valueAtColumnRow(psResult,IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_GROUPNAME,i));
                        vo.setGroupId(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i));
                    } else {
                        vo.setGroup(psDBBean.valueAtColumnRow(psResult,IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_GROUPNAME,i-1));
                        vo.setGroupId(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i-1));
                    }

                    // ■ 承認者氏名欄の表示制御 ■
                    // 承認者未設定の組織・グループには、"未設定"と表示する。
                    String sEvalEmpId = psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_EMPLOYEEID,i);

                    List<EvaluatorMemberVO> memberList = CollUtil.newArrayList();

                    if (sEvalEmpId == null || StrUtil.equals("null",sEvalEmpId)) {
                        vo.setMemberList(memberList);
                    } else {
                        EvaluatorMemberVO memberVO = new EvaluatorMemberVO();
                        // 承認者氏名欄の縦結合数を取得（ROWPANの値）
                        sEvalEmpDataCnt = (String)groupEmpDataMap.get(sGroupName_Eval + "_" + sEvalEmpId);
                        // 承認者氏名欄の枠は、グループまたは承認者職員番号が変わるタイミングのみで生成する。
                        if (i==0
                                || !psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i-1).equals(sGroupName_Eval)
                                || !psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_EMPLOYEEID,i-1).equals(sEvalEmpId))
                        {
                            /*
                             * 下記の場合のみ、承認者編集画面への画面遷移リンクを表示する。
                             *
                             * 承認権限があり、表示対象の承認者の編集可能フラグがオンで、人事管理フラグがオフの場合（つまり、承認サイトで登録され編集可能な人）。
                             * または、管理サイトで、表示対象の承認者の編集可能フラグがオンで、人事管理フラグがオンの場合（つまり、管理サイトで登録された人）。
                             */
                            sEmpNameLinkTagFlg = false;	// 承認者氏名のリンクフラグ
                            sEmpNameBoldTagFlg = false;	// 承認者氏名の太字フラグ
                            if (isEditLink(
                                    psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_EDITABLEFLG,i),
                                    psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_ADMINFLG,i),
                                    params, haveAuthority
                            )) {
                                sEmpNameLinkTagFlg = true;
                                // デフォルト承認者の場合、表示名を太字にする。
                                if (isSecEvaluater(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_SECTIONEVALUATER,i))) {
                                    sEmpNameBoldTagFlg = true;
                                }
                           // 承認サイトで承認者編集が不能な承認者は、表示名を太字にする。
                            } else {
                            // 表示名を太字にする。
                               sEmpNameBoldTagFlg = true;
                            }
                            memberVO.setEnableEmpEdit(sEmpNameLinkTagFlg);
                            memberVO.setNameBold(sEmpNameBoldTagFlg);

                            memberVO.setGroupId(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i));
                            memberVO.setGroupName(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPNAME,i));
                            memberVO.setPostName(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_POSTNAME,i));

                            memberVO.setEmpId(sEvalEmpId);
                            memberVO.setName(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_EMPLOYEENAME,i));

                        }

                        // ■ 権限欄の表示制御 ■
                        List<EvaluatorMemberRightVO> rightList = CollUtil.newArrayList();
                        EvaluatorMemberRightVO memberRightVO = new EvaluatorMemberRightVO();

                        // 勤怠承認権限
                        memberRightVO.setWorkConfirm(
                          isAuthority(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_RESULTS,i))
                        );
                        // 勤怠承認権限
                        memberRightVO.setMonthConfirm(
                          isAuthority(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_MONTHLYRESULTS,i))
                        );
                        // 超過勤務命令
                        memberRightVO.setOverWorkConfirm(
                          isAuthority(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_OVERTIME,i))
                        );
                        // 予定作成権限を持っていれば○を表示
                        memberRightVO.setScheduleCreate(
                          isAuthority(
                                  psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_SCHEDULE,i)));
                        // 権限付与権限を持っていれば○を表示
                        memberRightVO.setPermissionGive(
                          isAuthority(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_AUTHORITY,i)));
                        // 休暇・休出承認権限を持っていれば○を表示
                        boolean restConfirm = isAuthority(psDBBean.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_NOTIFICATION,i));
                        memberRightVO.setRestConfirm(restConfirm);
                        // 休暇・休出承認権限を持っていれば決裁レベルを表示
                        if (restConfirm) {
                            memberRightVO.setLevel(
                                    Integer.parseInt(psDBBean.valueAtColumnRow(psResult, IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_APPROVALLEVEL, i))
                            );
                        }
                        memberRightVO.setValidStartDate(psDBBean.valueAtColumnRow(psResult, IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_TERM_FROM, i));
                        memberRightVO.setValidEndDate(psDBBean.valueAtColumnRow(psResult, IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_TERM_TO, i));

                        rightList.add(memberRightVO);
                        memberVO.setValidList(rightList);

                        memberList.add(memberVO);

                        vo.setMemberList(memberList);
                    }
                    dataList.add(vo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }

        // 格式化数据为前端想要的格式
        List<EvaluatorGroupVO> formatGroupList = CollUtil.distinct(dataList);

        for (EvaluatorGroupVO groupVO : dataList) {
            for (EvaluatorGroupVO formatGroupVO : formatGroupList) {
                if (StrUtil.equals(groupVO.getGroupId(),formatGroupVO.getGroupId())) {
                   formatGroupVO.getMemberList().addAll(groupVO.getMemberList());
                }
            }
        }

        // 为empId为空的数据加上empId
        for (EvaluatorGroupVO evaluatorGroupVO : formatGroupList) {
               for (int i = 0; i < evaluatorGroupVO.getMemberList().size();i++) {
                   EvaluatorMemberVO memberVO = evaluatorGroupVO.getMemberList().get(i);
                   // 判断长度是否大于1是为了防止发生npe异常
                   if (StrUtil.isBlank(memberVO.getEmpId()) && evaluatorGroupVO.getMemberList().size()>1) {
                       EvaluatorMemberVO preMemberVO = evaluatorGroupVO.getMemberList().get(i-1);
                       memberVO.setEmpId(preMemberVO.getEmpId());
                       memberVO.setName(preMemberVO.getName());
                   }
               }
        }

        // 将存在异动历的权限设定做好标记
        Map<String,List<EvaluatorMemberRightVO>> multiRigtMap = MapUtil.newHashMap();
        for (EvaluatorGroupVO evaluatorGroupVO : formatGroupList) {
            for (int i = 0; i < evaluatorGroupVO.getMemberList().size(); i++) {

                EvaluatorMemberVO memberVO =  evaluatorGroupVO.getMemberList().get(i);
                if (StrUtil.isBlank(memberVO.getGroupName())) {
                    if (!multiRigtMap.containsKey(memberVO.getEmpId())) {
                        multiRigtMap.put(memberVO.getEmpId(),memberVO.getValidList());
                    } else {
                        List<EvaluatorMemberRightVO> rightList = multiRigtMap.get(memberVO.getEmpId());
                        rightList.addAll(memberVO.getValidList());
                    }
                }

            }
        }
        // 去掉memberList里重复的员工项
        for (EvaluatorGroupVO evaluatorGroupVO : formatGroupList) {
            List<EvaluatorMemberVO> distMemberList = CollUtil.newArrayList();
            for (int i = 0; i < evaluatorGroupVO.getMemberList().size();i++) {
                EvaluatorMemberVO memberVO = evaluatorGroupVO.getMemberList().get(i);
                if (!distMemberList.contains(memberVO)) {
                    distMemberList.add(memberVO);
                }
            }
            evaluatorGroupVO.setMemberList(distMemberList);
        }

        // 将前面标记的异动历权限记录合并到最后的结果中
        for (EvaluatorGroupVO evaluatorGroupVO : formatGroupList) {
            for (int i = 0; i < evaluatorGroupVO.getMemberList().size();i++) {
                EvaluatorMemberVO memberVO = evaluatorGroupVO.getMemberList().get(i);
                if (multiRigtMap.containsKey(memberVO.getEmpId())) {
                    List<EvaluatorMemberRightVO> validList = memberVO.getValidList();
                    validList.addAll(multiRigtMap.get(memberVO.getEmpId()));
                    memberVO.setValidList(validList);
                }
            }
        }

        result.put("bottomMessage",getEvasetMessage(psDBBean,params, EvaluatorSettingConst.TMG_EVASET_MESSAGE_DISPALERT));
        result.put("list",formatGroupList);
    }

    /**
     * 设置权限设定模块请求用到的公共参数
     * @param psDBBean
     * @param params
     * @return
     */
    public boolean processParams(PsDBBean psDBBean, EvaluatorSettingParam params) {
        boolean haveAuthority = true;
        // 汎用参照リストを生成する
        try {
            params.setAction((String) psDBBean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_ACTION));
            params.setSite(psDBBean.getSiteId());
            params.setLanguage(psDBBean.getLanguage());
            params.setCustomerID(psDBBean.getCustID());
            params.setCompanyId(psDBBean.getCompCode());
            params.setEmployee(psDBBean.getUserCode());
            /*
             * 初期表示の際に用いる基準日を取得する際に、システム日付時点で有効な歴の開始日を用いる
             * 但し、この際に顧客・法人・組織の値が特定される必要があり、組織は汎用ツリーの挙動に合わせる必要があり、汎用ツリーを用いるには基準日特定する必要がある
             * この為、汎用ツリーが用いる組織の特定方法を用いて汎用ツリーより先に組織を取得する。
             */
            String adminTargetSection = psDBBean.getReqParam(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
            String permTargetSection = psDBBean.getReqParam(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
            if (params.isSiteTa() && StrUtil.isNotBlank(adminTargetSection)) {
                params.setSection(adminTargetSection);
            } else if (params.isSiteTp() && StrUtil.isNotBlank(permTargetSection)) {
                params.setSection(permTargetSection);
            }
            // REQUEST:基準日
            String YYYYMMDD = psDBBean.getReqParam(EvaluatorSettingConst.REQUEST_KEY_YYYYMMDD);
            // 承認サイト、管理サイトで基準日設定を振り分ける。
            if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId())) {
                // 初期表示時、承認サイトの場合はシステム年月初日を設定
                if (StrUtil.isBlank(YYYYMMDD)) {
                    YYYYMMDD = TmgUtil.getSysdate().substring(0, 8).concat("01");
                    params.setYYYYMMDD(YYYYMMDD);
                } else {
                    params.setYYYYMMDD(YYYYMMDD);
                }
            } else {
                if (StrUtil.isBlank(YYYYMMDD)) {
                    params.setYYYYMMDD(TmgUtil.getSysdate());
                    Vector vecSQL = new Vector();
                    vecSQL.add(buildSQLForTransitionDate(params));
                    PsResult psResult = psDBBean.getValuesforMultiquery(vecSQL, EvaluatorSettingConst.BEAN_DESC);
                    String baseDate = psDBBean.valueAtColumnRow(psResult,0, 0, 0);
                    if (StrUtil.isNotBlank(baseDate)) {
                        params.setYYYYMMDD(baseDate);
                    }
                } else {
                    params.setYYYYMMDD(YYYYMMDD);
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
            haveAuthority = false;
        }
        return haveAuthority;
    }

    private String escDBString(String sString) {
        return SysUtil.transStringNullToDB(SysUtil.escapeQuote(sString));
    }

    /**
     * 選択された組織の表示権限があるかを判定するメソッド
     *
     * @return 権限有無(有:true、無:false)
     */
    private boolean hasAuthOfSect(String section,PsDBBean psDBBean) {
        // 検索
        Vector vQuery = new Vector();
        vQuery.add(buildSQLForSelectHasAuth(section,psDBBean));
        String sConut = null;
        int nCount = 0;
        try {
            PsResult psResult = psDBBean.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC);
            // 一件も結果がない場合はfalse
            sConut = psDBBean.valueAtColumnRow(psResult,0, 0, 0);
            nCount = Integer.parseInt(sConut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nCount > 0){
            return true;
        }
        return false;
    }


    /**
     * 選択された組織の表示権限があるかを判定するSQLを返すメソッド
     * @return SQL
     */
    private String buildSQLForSelectHasAuth(String section,PsDBBean psDBBean) {
        StringBuffer sSQL = new StringBuffer();
        String sExists = getOrgTreeSearchRangeForTreeBuild(psDBBean);
        sSQL.append(" SELECT ");
        sSQL.append("     COUNT(1) ");
        sSQL.append(" FROM MAST_ORGANISATION o ");
        sSQL.append(" WHERE ");
        sSQL.append("     o.MO_CCUSTOMERID_CK_FK = " + escDBString(psDBBean.getCustID()));
        sSQL.append(" AND o.MO_CCOMPANYID_CK_FK  = " + escDBString(psDBBean.getCompCode()));
        sSQL.append(" AND o.MO_CSECTIONID_CK     = " + escDBString(section));  // 「退職済」を表す部署は除く
        sSQL.append(" AND o.MO_DSTART           <= TRUNC(SYSDATE) ");
        sSQL.append(" AND o.MO_DEND             >= TRUNC(SYSDATE) ");
        sSQL.append(" AND o.MO_CLANGUAGE         = " + escDBString(psDBBean.getLanguage()));
        sSQL.append(sExists);
        return sSQL.toString();
    }

    /**
     * 検索対象範囲条件の取得(職員に対する検索対象範囲とは別に分ける。Treeでは上位所属を利用するが社員リストでは出てはいけないため)
     * @return
     */
    public String getOrgTreeSearchRangeForTreeBuild(PsDBBean psDBBean) {
        String sExists;
        try {
            //sExists = tmgSearchRangeUtil.getExistsQuery(pRequestHash, pSession, "d.HD_CCOMPANYID_CK", "d.HD_CEMPLOYEEID_CK");
            sExists = tmgSearchRangeUtil.getExistsQueryOrganisation(psDBBean, ContextUtil.getHttpRequest().getSession(), "o.MO_CLAYEREDSECTIONID");
        }
        catch(Exception e) {
            sExists = "";
        }
        return sExists;
    }

    /**
     * 画面遷移リンク用基準日取得クエリ
     *
     * @param params
     * @return String SQL
     */
    public String buildSQLForTransitionDate(EvaluatorSettingParam params) {

        // 検索条件に使用するパラメータを準備
        String sDBCustId   = escDBString(params.getCustomerId()); // 顧客コード
        String sDBCompId   = escDBString(params.getCompanyId());  // 法人コード
        String sDBSecId    = escDBString(params.getSection());    // 組織コード
        String sDBBaseDate = SysUtil.transDateNullToDB(params.getYYYYMMDD());      // 基準日

        String sbSQL = " SELECT " +
                "     ( " + // 指定日付より前の最大の開始日
                "         SELECT TO_CHAR(MAX(STARTDATE), " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE " +
                "         FROM ( " +
                "               SELECT TGR_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_GROUP" +
                "               WHERE  TGR_CCUSTOMERID = " + sDBCustId +
                "               AND    TGR_CCOMPANYID  = " + sDBCompId +
                "               AND    TGR_CSECTIONID  = " + sDBSecId +
                "               UNION " +
                "               SELECT TGRM_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_GROUP_MEMBER " +
                "               WHERE  TGRM_CCUSTOMERID = " + sDBCustId +
                "               AND    TGRM_CCOMPANYID  = " + sDBCompId +
                "               AND    TGRM_CSECTIONID  = " + sDBSecId +
                "               UNION " +
                "               SELECT TEV_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_EVALUATER " +
                "               WHERE  TEV_CCUSTOMERID = " + sDBCustId +
                "               AND    TEV_CCOMPANYID  = " + sDBCompId +
                "               AND    TEV_CSECTIONID  = " + sDBSecId +
                "              ) " +
                "         WHERE " +
                "             STARTDATE = " +
                "                 ( " + // TRUNC(SYSDATE)時点で有効な歴の開始日
                "                  SELECT MAX(STARTDATE) AS STARTDATE " +
                "                  FROM (" +
                "                        SELECT TGR_DSTARTDATE AS STARTDATE " +
                "                        FROM   TMG_GROUP " +
                "                        WHERE  TGR_CCUSTOMERID = " + sDBCustId +
                "                        AND    TGR_CCOMPANYID  = " + sDBCompId +
                "                        AND    TGR_CSECTIONID  = " + sDBSecId +
                "                        UNION " +
                "                        SELECT TGRM_DSTARTDATE AS STARTDATE " +
                "                        FROM   TMG_GROUP_MEMBER " +
                "                        WHERE  TGRM_CCUSTOMERID = " + sDBCustId +
                "                        AND    TGRM_CCOMPANYID  = " + sDBCompId +
                "                        AND    TGRM_CSECTIONID  = " + sDBSecId +
                "                        UNION " +
                "                        SELECT TEV_DSTARTDATE AS STARTDATE " +
                "                        FROM   TMG_EVALUATER " +
                "                        WHERE  TEV_CCUSTOMERID = " + sDBCustId +
                "                        AND    TEV_CCOMPANYID  = " + sDBCompId +
                "                        AND    TEV_CSECTIONID  = " + sDBSecId +
                "                       ) " +
                "                  WHERE " +
                "                      STARTDATE < " + sDBBaseDate +
                "                 )  " +
                "     ), " +
                "     ( " + // TRUNC(SYSDATE)時点で有効な歴の開始日
                "         SELECT TO_CHAR(MAX(STARTDATE), " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE " +
                "         FROM ( " +
                "               SELECT TGR_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_GROUP " +
                "               WHERE  TGR_CCUSTOMERID = " + sDBCustId +
                "               AND    TGR_CCOMPANYID  = " + sDBCompId +
                "               AND    TGR_CSECTIONID  = " + sDBSecId +
                "               UNION " +
                "               SELECT TGRM_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_GROUP_MEMBER " +
                "               WHERE  TGRM_CCUSTOMERID = " + sDBCustId +
                "               AND    TGRM_CCOMPANYID  = " + sDBCompId +
                "               AND    TGRM_CSECTIONID  = " + sDBSecId +
                "               UNION " +
                "               SELECT TEV_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_EVALUATER " +
                "               WHERE  TEV_CCUSTOMERID = " + sDBCustId +
                "               AND    TEV_CCOMPANYID  = " + sDBCompId +
                "               AND    TEV_CSECTIONID  = " + sDBSecId +
                "              ) " +
                "          WHERE " +
                "              STARTDATE <= TRUNC(SYSDATE) " +
                "     ), " +
                "     ( " + // 指定日付より後の最小の開始日
                "         SELECT TO_CHAR(MIN(STARTDATE), " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE " +
                "         FROM ( " +
                "                SELECT TGR_DSTARTDATE AS STARTDATE " +
                "                FROM   TMG_GROUP " +
                "                WHERE  TGR_CCUSTOMERID = " + sDBCustId +
                "                AND    TGR_CCOMPANYID  = " + sDBCompId +
                "                AND    TGR_CSECTIONID  = " + sDBSecId +
                "                UNION " +
                "                SELECT TGRM_DSTARTDATE AS STARTDATE " +
                "                FROM   TMG_GROUP_MEMBER " +
                "                WHERE  TGRM_CCUSTOMERID = " + sDBCustId +
                "                AND    TGRM_CCOMPANYID  = " + sDBCompId +
                "                AND    TGRM_CSECTIONID  = " + sDBSecId +
                "                UNION " +
                "                SELECT TEV_DSTARTDATE AS STARTDATE " +
                "                FROM   TMG_EVALUATER " +
                "                WHERE  TEV_CCUSTOMERID = " + sDBCustId +
                "                AND    TEV_CCOMPANYID  = " + sDBCompId +
                "                AND    TEV_CSECTIONID  = " + sDBSecId +
                "              ) " +
                "         WHERE " +
                "             STARTDATE > " + sDBBaseDate +
                "     ) " +
                " FROM " +
                "     DUAL ";
        return sbSQL;
    }

    /**
     * 対象部署のグループ一覧及び承認者一覧（現在日付時点の）を取得するSQLを返す
     *
     * @return String SQL
     */
    private String buildSQLForSelectGroupAndEvaluater(EvaluatorSettingParam evaluaterSettingParam) {
        StringBuilder sSQL = new StringBuilder();
        String custId   = escDBString(evaluaterSettingParam.getCustomerId());
        String compId   = escDBString(evaluaterSettingParam.getCompanyId());
        String baseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
        String lang     = escDBString(evaluaterSettingParam.getLanguage());

        sSQL.append(" SELECT ");
        sSQL.append("     G.TGR_CGROUPID, ");
        sSQL.append("     G.TGR_CGROUPNAME, ");
        sSQL.append("     E.TEV_CEMPLOYEEID, ");
        sSQL.append("     TMG_F_GET_ME_NAME(E.TEV_CEMPLOYEEID, " + baseDate + ", 0, E.TEV_CCUSTOMERID, E.TEV_CCOMPANYID) as TEV_CEMPLOYEEID, ");
        sSQL.append("     TMG_F_GET_MO(D.HD_CSECTIONID_FK, " + baseDate + ", 1, D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + ") as HD_CSECTIONID_FK, ");
        sSQL.append("     TMG_F_GET_MP(D.HD_CPOSTID_FK, " + baseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + ") as HD_CPOSTID_FK, ");
        /*
         * "編集可能フラグ"は画面表示時の承認者氏名の編集（リンク、太字）判定に使用。
         * また、過去分も修正する為、改定日を指定せず、MAX値をとる。
         */
//		sSQL.append("     E.TEV_CEDITABLEFLG, ");
        sSQL.append("     (");
        sSQL.append("      SELECT MAX(E2.TEV_CEDITABLEFLG) ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("     ) AS TEV_CEDITABLEFLG, ");
        sSQL.append("     E.TEV_CRESULTS, ");
        sSQL.append("     E.TEV_CNOTIFICATION, ");
        sSQL.append("     E.TEV_COVERTIME, ");
        sSQL.append("     E.TEV_CSCHEDULE, ");
        sSQL.append("     E.TEV_CAUTHORITY, ");
        /*
         * "人事管理フラグ"は画面表示時の承認者氏名の編集（リンク、太字）判定に使用。
         * 過去に１度でも承認サイトから登録があれば承認サイトで編集可能なためMIN値をとる。
         */
//		sSQL.append("     E.TEV_CADMINFLG, ");
        sSQL.append("     (");
        sSQL.append("      SELECT MIN(E2.TEV_CADMINFLG) ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("     ) AS TEV_CADMINFLG, ");
        sSQL.append("     E.TEV_CMONTHLYAPPROVAL, ");
        /*
         * "デフォルト承認者フラグ"は画面表示時の承認者氏名の編集（太字）判定に使用。
         * また、判定は改定日時点のデフォルト承認者フラグ値で判定する為、本項目は改定日時点のデータを取得する。
         */
//		sSQL.append("     E.TEV_CSECTIONEVALUATER, ");
        sSQL.append("     NVL((");
        sSQL.append("      SELECT E2.TEV_CSECTIONEVALUATER ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("      AND   E2.TEV_DSTARTDATE <= " + baseDate);
        sSQL.append("      AND   E2.TEV_DENDDATE   >= " + baseDate);
        sSQL.append("     ), " + escDBString(TmgUtil.Cs_MGD_ONOFF_0) + ") AS TEV_CSECTIONEVALUATER, ");
        sSQL.append("     TMG_F_GET_MGD(E.TEV_CAPPROVAL_LEVEL," + baseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + ") AS CSECTIONEVALUATER, ");
        sSQL.append("     TO_CHAR(E.TEV_DSTARTDATE, 'yyyy/mm/dd') AS TEV_DSTARTDATE, ");
        sSQL.append("     TO_CHAR(E.TEV_DENDDATE,   'yyyy/mm/dd') AS TEV_DENDDATE, ");

        // ソート用の項目
        sSQL.append("     DECODE(SUBSTR(G.TGR_CGROUPID, INSTR(G.TGR_CGROUPID,'|') + 1, 6), " + escDBString(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE) + ", '0' || G.TGR_CGROUPNAME, '1' || G.TGR_CGROUPNAME) AS SORT1, ");
        sSQL.append("     (");
        sSQL.append("      SELECT E2.TEV_CEDITABLEFLG ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("      AND   E2.TEV_DSTARTDATE <= " + baseDate);
        sSQL.append("      AND   E2.TEV_DENDDATE   >= " + baseDate);
        sSQL.append("     ) AS SORT2, ");
        sSQL.append("     (");
        sSQL.append("      SELECT E2.TEV_CADMINFLG ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("      AND   E2.TEV_DSTARTDATE <= " + baseDate);
        sSQL.append("      AND   E2.TEV_DENDDATE   >= " + baseDate);
        sSQL.append("     ) AS SORT3, ");
        sSQL.append("     TMG_F_GET_MP_WEIGHT(D.HD_CPOSTID_FK, " + baseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + ") AS SORT4, ");
        sSQL.append("     E.TEV_CEMPLOYEEID AS SORT5, ");
        sSQL.append("     E.TEV_DSTARTDATE  AS SORT6  ");
        sSQL.append(" FROM ");
        sSQL.append("     TMG_GROUP G, ");
        sSQL.append("     TMG_EVALUATER E, ");
        sSQL.append("     HIST_DESIGNATION D");
        sSQL.append(" WHERE ");
        sSQL.append("     G.TGR_CCUSTOMERID      = " + custId);
        sSQL.append(" AND G.TGR_CCOMPANYID       = " + compId);
        sSQL.append(" AND G.TGR_CSECTIONID       = " + escDBString(evaluaterSettingParam.getSection()));
        sSQL.append(" AND G.TGR_DSTARTDATE      <= " + baseDate);
        sSQL.append(" AND G.TGR_DENDDATE        >= " + baseDate);
        sSQL.append(" AND E.TEV_CCUSTOMERID(+)   = G.TGR_CCUSTOMERID");
        sSQL.append(" AND E.TEV_CCOMPANYID(+)    = G.TGR_CCOMPANYID");
        sSQL.append(" AND E.TEV_CSECTIONID(+)    = G.TGR_CSECTIONID");
        sSQL.append(" AND E.TEV_CGROUPID(+)      = G.TGR_CGROUPID");
        sSQL.append(" AND D.HD_CCUSTOMERID_CK(+) = E.TEV_CCUSTOMERID");
        sSQL.append(" AND D.HD_CCOMPANYID_CK(+)  = E.TEV_CCOMPANYID");
        sSQL.append(" AND D.HD_CEMPLOYEEID_CK(+) = E.TEV_CEMPLOYEEID");
        sSQL.append(" AND D.HD_DSTARTDATE_CK(+) <= " + baseDate);
        sSQL.append(" AND D.HD_DENDDATE(+)      >= " + baseDate);
        sSQL.append(" AND D.HD_CIFKEYORADDITIONALROLE(+) = '0'");
        sSQL.append(" ORDER BY ");
        sSQL.append("     SORT1, ");            // グループID
        sSQL.append("     SORT2 nulls last, "); // 権限設定の編集可否フラグ
        sSQL.append("     SORT3 nulls last, "); // 権限設定の人事管理フラグ
        sSQL.append("     SORT4 nulls last, "); // 異動歴の役職の重み
        sSQL.append("     SORT5 nulls last, "); // 権限設定の職員番号
        sSQL.append("     SORT6 nulls last  "); // 権限設定の開始日

        return sSQL.toString();
    }

    /**
     * 対象部署のグループ一覧及び承認者一覧（現在日付時点の）を取得するSQLを返す
     *
     * @return String SQL
     */
    private String buildSQLForSelectEvaluaterNum(EvaluatorSettingParam evaluaterSettingParam) {

        StringBuilder sbSQL = new StringBuilder();

        String sYyyyMmDd      = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
        String sCustId        = escDBString(evaluaterSettingParam.getCustomerId());
        String sCompId        = escDBString(evaluaterSettingParam.getCompanyId());
        String sSecId         = escDBString(evaluaterSettingParam.getSection());
        String sGroupSequence = escDBString(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);

        sbSQL.append(" SELECT ");
        sbSQL.append("     G.TGR_CGROUPID, ");
        sbSQL.append("     ( ");
        sbSQL.append("      SELECT ");
        sbSQL.append("          DECODE(COUNT(E.TEV_CGROUPID),0,1,COUNT(E.TEV_CGROUPID)) ");
        sbSQL.append("      FROM ");
        sbSQL.append("          TMG_EVALUATER E ");
        sbSQL.append("      WHERE ");
        sbSQL.append("          E.TEV_CCUSTOMERID  = G.TGR_CCUSTOMERID ");
        sbSQL.append("      AND E.TEV_CCOMPANYID   = G.TGR_CCOMPANYID ");
        sbSQL.append("      AND E.TEV_CSECTIONID   = G.TGR_CSECTIONID ");
        sbSQL.append("      AND E.TEV_CGROUPID     = G.TGR_CGROUPID ");
        sbSQL.append("     ) AS TEV_CEMPLOYEEID ");
        sbSQL.append(" FROM ");
        sbSQL.append("     TMG_GROUP G ");
        sbSQL.append(" WHERE ");
        sbSQL.append("     G.TGR_CCUSTOMERID   = " + sCustId);
        sbSQL.append(" AND G.TGR_CCOMPANYID    = " + sCompId);
        sbSQL.append(" AND G.TGR_CSECTIONID    = " + sSecId);
        sbSQL.append(" AND G.TGR_DSTARTDATE   <= " + sYyyyMmDd);
        sbSQL.append(" AND G.TGR_DENDDATE     >= " + sYyyyMmDd);
        sbSQL.append(" ORDER BY ");
        sbSQL.append("     DECODE( ");
        sbSQL.append("         SUBSTR(G.TGR_CGROUPID, INSTR(G.TGR_CGROUPID, '|') + 1, 6), "+sGroupSequence + ", '0' || G.TGR_CGROUPNAME, '1' || G.TGR_CGROUPNAME ");
        sbSQL.append("     ) ");

        return sbSQL.toString();

    }

    /**
     * 対象部署の権限設定一覧のグループ・承認者毎の権限設定レコード数を取得する。
     *
     * @return String SQL
     */
    private String buildSQLForSelectEvaluaterEmpNum(EvaluatorSettingParam evaluaterSettingParam) {

        StringBuilder sSQL = new StringBuilder();

        String baseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
        String lang     = escDBString(evaluaterSettingParam.getLanguage());

        sSQL.append(" SELECT ");
        sSQL.append("     R.TGR_CGROUPID, ");
        sSQL.append("     R.TEV_CEMPLOYEEID, ");
        sSQL.append("     COUNT(R.TEV_CEMPLOYEEID) ");
        sSQL.append(" FROM ");
        sSQL.append("     ( ");
        sSQL.append("      SELECT ");
        sSQL.append("          G.TGR_CGROUPID, ");
        sSQL.append("          E.TEV_CEMPLOYEEID ");
        sSQL.append("      FROM ");
        sSQL.append("          TMG_GROUP G, ");
        sSQL.append("          TMG_EVALUATER E, ");
        sSQL.append("          HIST_DESIGNATION D");
        sSQL.append("      WHERE ");
        sSQL.append("          G.TGR_CCUSTOMERID      = " + escDBString(evaluaterSettingParam.getCustomerId()));
        sSQL.append("      AND G.TGR_CCOMPANYID       = " + escDBString(evaluaterSettingParam.getCompanyId()));
        sSQL.append("      AND G.TGR_CSECTIONID       = " + escDBString(evaluaterSettingParam.getSection()));
        sSQL.append("      AND G.TGR_DSTARTDATE      <= " + baseDate);
        sSQL.append("      AND G.TGR_DENDDATE        >= " + baseDate);
        sSQL.append("      AND E.TEV_CCUSTOMERID(+)   = G.TGR_CCUSTOMERID");
        sSQL.append("      AND E.TEV_CCOMPANYID(+)    = G.TGR_CCOMPANYID");
        sSQL.append("      AND E.TEV_CSECTIONID(+)    = G.TGR_CSECTIONID");
        sSQL.append("      AND E.TEV_CGROUPID(+)      = G.TGR_CGROUPID");
        sSQL.append("      AND D.HD_CCUSTOMERID_CK(+) = E.TEV_CCUSTOMERID");
        sSQL.append("      AND D.HD_CCOMPANYID_CK(+)  = E.TEV_CCOMPANYID");
        sSQL.append("      AND D.HD_CEMPLOYEEID_CK(+) = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND D.HD_DSTARTDATE_CK(+) <= " + baseDate);
        sSQL.append("      AND D.HD_DENDDATE(+)      >= " + baseDate);
        sSQL.append("      AND D.HD_CIFKEYORADDITIONALROLE(+) = '0'");
        sSQL.append("      ORDER BY ");
        sSQL.append("          DECODE(SUBSTR(G.TGR_CGROUPID, INSTR(G.TGR_CGROUPID,'|') + 1, 6), " + escDBString(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE) + ", '0' || G.TGR_CGROUPNAME, '1' || G.TGR_CGROUPNAME), ");
        sSQL.append("          E.TEV_CEDITABLEFLG, ");
        sSQL.append("          TMG_F_GET_MP_WEIGHT(D.HD_CPOSTID_FK, " + baseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + "), ");
        sSQL.append("          E.TEV_CEMPLOYEEID, ");
        sSQL.append("          E.TEV_DSTARTDATE   ");
        sSQL.append("     ) R");
        sSQL.append(" GROUP BY ");
        sSQL.append("     R.TGR_CGROUPID, ");
        sSQL.append("     R.TEV_CEMPLOYEEID ");

        return sSQL.toString();
    }

    /**
     * 権限設定一覧画面にて、承認者編集リンク対象かどうか判定する。
     *
     * @param sEditableFlg 編集フラグ
     * @param sAdminFlg    管理対象フラグ
     * @return 判定結果（リンク対象：true、リンク対象外：false）
     */
    public boolean isEditLink(String sEditableFlg, String sAdminFlg,EvaluatorSettingParam params, boolean haveAuthority) {
        // 編集可能かつ管理対象フラグ対象外（承認サイトで登録）かつ表示対象部署に対し、権限付与権限を持つ場合
        // または、管理サイト表示で、編集可能かつ管理対象フラグ対象（管理サイトで登録）の場合、編集リンクを表示可能とする。
        return (isAuthority(sEditableFlg) && !isAuthority(sAdminFlg) && haveAuthority)
                || (params.isSiteTa() && isAuthority(sEditableFlg) && isAuthority(sAdminFlg));
    }

    /**
     * 与えられたグループコードがデフォルトグループかどうかを返します。
     * @param evaluaterSettingParam
     * @param psMasterCode
     * @return String(取得出来ない場合は空白を返却)
     */
    public String getEvasetMessage(PsDBBean psDBBean,EvaluatorSettingParam evaluaterSettingParam, String psMasterCode){
        PsResult psMesResult = null;
        Vector <String> vQuery = new Vector <String>();
        vQuery.add(buildSQL4SelectTmgVMgdEvasetMessage(evaluaterSettingParam));
        try {
            psMesResult = psDBBean.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        Vector vsMessage = ((Vector)psMesResult.getResult().elementAt(0));
        // 一度Hashmapに入れ直してから取得する方がいいかもしれない・・・？
        for (Iterator< Vector > ite = vsMessage.iterator(); ite.hasNext();) {
            Vector <String> vsEvasetMessage = ite.next();
            if(vsEvasetMessage.get(0).equals(psMasterCode)){
                return vsEvasetMessage.get(1);
            }
        }
        return "";

    }

    /**
     * 権限がオンかどうか
     * @return boolean 権限がオンの場合ture
     */
    private boolean isAuthority(String sAuthority) {
        return sAuthority != null && sAuthority.equals(TmgUtil.Cs_MGD_ONOFF_1);
    }

    /**
     * デフォルト承認者か判定する。
     *
     * @return boolean 判定結果
     */
    private boolean isSecEvaluater(String sSecEvalFlg) {
        return TmgUtil.Cs_MGD_ONOFF_1.equals(sSecEvalFlg);
    }

    /**
     * 名称マスタ詳細情報より、権限設定用メッセージを取得
     *
     * @param params
     * @return String SQL
     */
    private String buildSQL4SelectTmgVMgdEvasetMessage(EvaluatorSettingParam params) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     MGD_CMASTERCODE, ");
        sbSql.append("     MGD_CMSG ");
        sbSql.append(" FROM ");
        sbSql.append("     TMG_V_MGD_EVASET_MESSAGE ");
        sbSql.append(" WHERE ");
        sbSql.append("     MGD_CCUSTOMERID       = " + escDBString(params.getCustomerId()));
        sbSql.append(" AND MGD_CCOMPANYID_CK_FK  = " + escDBString(params.getCompanyId()));
        sbSql.append(" AND MGD_DSTART_CK        <= " + escDBString(params.getYYYYMMDD()));
        sbSql.append(" AND MGD_DEND             >= " + escDBString(params.getYYYYMMDD()));
        sbSql.append(" AND MGD_CLANGUAGE_CK      = " + escDBString(params.getLanguage()));
        return sbSql.toString();
    }

    /**
     * エラーメッセージを削除するSQLを返す
     * @return String SQL
     */
    private String buildSQLForDeleteGroupErrMsg(EvaluatorSettingParam params) {
        return " DELETE FROM "
                + "     TMG_ERRMSG E "
                + " WHERE "
                + "     E.TER_CMODIFIERUSERID    = " + escDBString(params.getEmployee())
                + " AND E.TER_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + params.getAction())
                + " AND E.TER_CCUSTOMERID        = " + escDBString(params.getCustomerId())
                + " AND E.TER_CCOMPANYID         = " + escDBString(params.getCompanyId());
    }

    /**
     * チェックテーブルへグループ名を登録するSQLを返す
     * @return String SQL
     */
    private String buildSQLForInsertGroupCheck(EvaluatorSettingParam evaluaterSettingParam,PsDBBean psDBBean) {

        // 検索条件に使用するパラメータを準備
        String sDBCustId   = escDBString(evaluaterSettingParam.getCustomerId()); // 顧客コード
        String sDBCompId   = escDBString(evaluaterSettingParam.getCompanyId());  // 法人コード
        String sDBSecId    = escDBString(evaluaterSettingParam.getSection());    // 組織コード
        String sDBBaseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());      // 基準日



        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("INSERT INTO TMG_GROUP_CHECK ");
        sbSQL.append("(");
        sbSQL.append("TGR_CCUSTOMERID,");
        sbSQL.append("TGR_CCOMPANYID,");
        sbSQL.append("TGR_DSTARTDATE,");
        sbSQL.append("TGR_DENDDATE,");
        sbSQL.append("TGR_CMODIFIERUSERID,");
        sbSQL.append("TGR_DMODIFIEDDATE,");
        sbSQL.append("TGR_CMODIFIERPROGRAMID,");
        sbSQL.append("TGR_CSECTIONID,");
        sbSQL.append("TGR_CGROUPID,");
        sbSQL.append("TGR_CGROUPNAME,");
        sbSQL.append("TGR_OT_MONTLY_01,");
        sbSQL.append("TGR_OT_MONTLY_02,");
        sbSQL.append("TGR_OT_MONTLY_03,");
        sbSQL.append("TGR_OT_MONTLY_04,");
        sbSQL.append("TGR_OT_MONTLY_05,");
        sbSQL.append("TGR_OT_YEARLY_01,");
        sbSQL.append("TGR_OT_YEARLY_02,");
        sbSQL.append("TGR_OT_YEARLY_03,");
        sbSQL.append("TGR_OT_YEARLY_04, ");
        sbSQL.append("TGR_OT_YEARLY_05,");
        sbSQL.append("TGR_OT_MONTHLY_COUNT,");
        sbSQL.append("TGR_HT_MONTLY_01,");
        sbSQL.append("TGR_HT_MONTLY_02,");
        sbSQL.append("TGR_HT_MONTLY_03,");
        sbSQL.append("TGR_HT_MONTLY_04,");
        sbSQL.append("TGR_HT_MONTLY_05,");
        sbSQL.append("TGR_OT_DAILY_01,");
        sbSQL.append("TGR_OT_MONTHLY_AVG");//超勤実績の月平均時間
        sbSQL.append(") ");
        sbSQL.append("SELECT ");
        sbSQL.append("T1.TGR_CCUSTOMERID,");
        sbSQL.append("T1.TGR_CCOMPANYID,");
        if (evaluaterSettingParam.getAction() != null && evaluaterSettingParam.getAction().equals(EvaluatorSettingConst.ACT_MAKEGROUP_CGROUP)) {
            // 引数で渡すSTARTDATEを改訂日に変更
            sbSQL.append("TMG_F_GET_ORG_STARTDATE(T1.TGR_CCUSTOMERID, T1.TGR_CCOMPANYID, T1.TGR_CSECTIONID, " + sDBBaseDate + ") AS STARTDATE, ");
        } else {
            sbSQL.append(sDBBaseDate + "AS STARTDATE,");
        }
        // 引数で渡すSTARTDATEを改訂日に変更
        sbSQL.append("TMG_F_GET_ORG_ENDDATE(T1.TGR_CCUSTOMERID, T1.TGR_CCOMPANYID, T1.TGR_CSECTIONID, " + sDBBaseDate + ") AS ENDDATE,");
        sbSQL.append(escDBString(evaluaterSettingParam.getEmployee()) + ",");
        sbSQL.append("SYSDATE,");
        sbSQL.append(escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ",");
        sbSQL.append("T1.TGR_CSECTIONID,");

        if (StrUtil.equals(evaluaterSettingParam.getAction(),EvaluatorSettingConst.ACT_MAKEGROUP_CGROUP)) {
            // 新規 組織グループ追加(新規 組織グループコード採番)
            sbSQL.append(buildSQL4NextGroupId(sDBSecId, " T1.TGR_CCUSTOMERID ", " T1.TGR_CCOMPANYID ") + ",");
        } else {
            // 既存 組織グループ編集(既存 組織グループコード適用)
            sbSQL.append(escDBString(evaluaterSettingParam.getGroup()) + ",");
        }

        String groupName = (String)psDBBean.getRequestHash().get("txtGroupName");
        if (StrUtil.isNotBlank(groupName)) {
            sbSQL.append(escDBString(groupName)).append(",");
        } else {
            sbSQL.append("T1.TGR_CGROUPNAME,");
        }
        sbSQL.append("T1.TGR_OT_MONTLY_01,");
        sbSQL.append("T1.TGR_OT_MONTLY_02,");
        sbSQL.append("T1.TGR_OT_MONTLY_03,");
        sbSQL.append("T1.TGR_OT_MONTLY_04,");
        sbSQL.append("T1.TGR_OT_MONTLY_05,");
        sbSQL.append("T1.TGR_OT_YEARLY_01,");
        sbSQL.append("T1.TGR_OT_YEARLY_02,");
        sbSQL.append("T1.TGR_OT_YEARLY_03,");
        sbSQL.append("T1.TGR_OT_YEARLY_04,");
        sbSQL.append("T1.TGR_OT_YEARLY_05,");
        sbSQL.append("T1.TGR_OT_MONTHLY_COUNT,");
        sbSQL.append("T1.TGR_HT_MONTLY_01,");
        sbSQL.append("T1.TGR_HT_MONTLY_02,");
        sbSQL.append("T1.TGR_HT_MONTLY_03,");
        sbSQL.append("T1.TGR_HT_MONTLY_04,");
        sbSQL.append("T1.TGR_HT_MONTLY_05,");
        sbSQL.append("T1.TGR_OT_DAILY_01,");
        sbSQL.append("T1.TGR_OT_MONTHLY_AVG ");//超勤実績の月平均時間
        sbSQL.append("FROM TMG_GROUP T1 ");
        sbSQL.append("WHERE T1.TGR_CCUSTOMERID = " + sDBCustId);
        sbSQL.append(" AND T1.TGR_CCOMPANYID = " + sDBCompId);
        sbSQL.append(" AND T1.TGR_CSECTIONID = " + sDBSecId);
        sbSQL.append(" AND T1.TGR_CGROUPID = " + escDBString(evaluaterSettingParam.getSection() + "|" + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE));
        sbSQL.append(" AND T1.TGR_DSTARTDATE <= " + sDBBaseDate);
        sbSQL.append(" AND T1.TGR_DENDDATE >= " + sDBBaseDate);
        sbSQL.append(" AND T1.TGR_CCUSTOMERID = " + sDBCustId);
        sbSQL.append(" AND T1.TGR_CCOMPANYID = " + sDBCompId);
        sbSQL.append(" AND T1.TGR_CSECTIONID = " + sDBSecId);
        if (StrUtil.isBlank(evaluaterSettingParam.getGroup())) {
            sbSQL.append(" AND T1.TGR_CGROUPID = " + escDBString(evaluaterSettingParam.getSection() + "|" + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE));
        } else {
            sbSQL.append(" AND T1.TGR_CGROUPID = " + escDBString(evaluaterSettingParam.getGroup()));
        }
        sbSQL.append(" AND T1.TGR_DSTARTDATE <= " + sDBBaseDate);
        sbSQL.append(" AND T1.TGR_DENDDATE >= " + sDBBaseDate);
        return sbSQL.toString();
    }

    /**
     * カラム「TGR_CGROUPID」に登録する「グループIDの最大値+1」を取得するSQLを返却する
     *
     * @param psSecId  組織コード
     * @param psCustId 顧客コード
     * @param psCompId 法人コード
     * @return String SQL
     */
    private String buildSQL4NextGroupId(String psSecId, String psCustId, String psCompId){
        return " (T1.TGR_CSECTIONID || '|' || ( " +
                " SELECT " +
                " TO_CHAR(NVL(SUBSTR(MAX(TGR_CGROUPID), INSTR(MAX(TGR_CGROUPID),'|') + 1, " +
                " LENGTH(MAX(TGR_CGROUPID))), 0) + 1, 'FM099999') " +
                " FROM " +
                " TMG_GROUP " +
                " WHERE " +
                " TGR_CCUSTOMERID  =  " + psCustId +
                " AND TGR_CCOMPANYID   =  " + psCompId +
                " AND TGR_CSECTIONID   =  " + psSecId +
                " ) " +
                " ) ";
    }

    /**
     * エラーメッセージを追加するSQLを返す
     * @return String SQL
     */
    private String buildSQLForInsertGroupErrMsg(EvaluatorSettingParam evaluaterSettingParam) {
        return " INSERT INTO TMG_ERRMSG ( "
                + " TER_CCUSTOMERID, "
                + " TER_CCOMPANYID, "
                + " TER_DSTARTDATE, "
                + " TER_DENDDATE, "
                + " TER_CMODIFIERUSERID, "
                + " TER_DMODIFIEDDATE, "
                + " TER_CMODIFIERPROGRAMID, "
                + " TER_CERRCODE, "
                + " TER_CLANGUAGE "
                + " ) VALUES ( "
                + escDBString(evaluaterSettingParam.getCustomerId()) + ", "
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + TmgUtil.Cs_MINDATE + ", "
                + TmgUtil.Cs_MAXDATE + ", "
                + escDBString(evaluaterSettingParam.getEmployee()) + ", "
                + "SYSDATE, "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ", "
                + "TMG_F_CHECK_GROUP(" + escDBString(evaluaterSettingParam.getEmployee()) + ", " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + "), "
                + escDBString(evaluaterSettingParam.getLanguage()) + ") ";
    }

    /**
     * トリガーに追加するSQLを返す
     * @return String SQL
     */
    private String buildSQLForInsertGroupTrigger(EvaluatorSettingParam evaluaterSettingParam) {
        return " INSERT INTO TMG_TRIGGER ( "
                + " TTR_CCUSTOMERID, "
                + " TTR_CCOMPANYID, "
                + " TTR_CEMPLOYEEID, "
                + " TTR_DSTARTDATE, "
                + " TTR_DENDDATE, "
                + " TTR_CMODIFIERUSERID, "
                + " TTR_DMODIFIEDDATE, "
                + " TTR_CMODIFIERPROGRAMID, "
                + " TTR_CPROGRAMID, "
                + " TTR_CPARAMETER1 "
                + " ) VALUES ( "
                + escDBString(evaluaterSettingParam.getCustomerId()) + ", "
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + escDBString(evaluaterSettingParam.getEmployee()) + ", "
                + TmgUtil.Cs_MINDATE + ", "
                + TmgUtil.Cs_MAXDATE + ", "
                + escDBString(evaluaterSettingParam.getEmployee()) + ", "
                + " SYSDATE, "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ", "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ", "
                + escDBString(evaluaterSettingParam.getAction()) + ")";
    }

    /**
     * エラーメッセージを取得するSQLを返す
     *
     * @return String SQL
     */
    private String buildSQLForSelectGroupErrMsg(EvaluatorSettingParam evaluaterSettingParam) {
        return " SELECT "
                + "     TMG_F_GET_ERRORMESSAGE(E.TER_CERRCODE,"
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + ", "
                + escDBString(evaluaterSettingParam.getLanguage())
                + "     ) "
                + " FROM "
                + "     TMG_ERRMSG E "
                + " WHERE "
                + "     E.TER_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId()) + " "
                + " AND E.TER_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId()) + " "
                + " AND E.TER_CMODIFIERUSERID    = " + escDBString(evaluaterSettingParam.getEmployee())
                + " AND E.TER_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction());
    }

    /**
     * トリガーを削除するSQLを返す
     * @return String SQL
     */
    private String buildSQLForDeleteGroupTrigger(EvaluatorSettingParam evaluaterSettingParam) {
        return " DELETE FROM "
                + "     TMG_TRIGGER T "
                + " WHERE "
                + "     T.TTR_CMODIFIERUSERID    = " + escDBString(evaluaterSettingParam.getEmployee())
                + " AND T.TTR_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction())
                + " AND T.TTR_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND T.TTR_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId());
    }

    /**
     * エラーチェックを削除するSQLを返す
     * @return String SQL
     */
    private String buildSQLForDeleteGroupCheck(EvaluatorSettingParam evaluaterSettingParam) {
        return " DELETE FROM "
                + "     TMG_GROUP_CHECK G "
                + " WHERE "
                + "     G.TGR_CMODIFIERUSERID    = " + escDBString(evaluaterSettingParam.getEmployee())
                + " AND G.TGR_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction())
                + " AND G.TGR_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND G.TGR_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId());
    }


}
