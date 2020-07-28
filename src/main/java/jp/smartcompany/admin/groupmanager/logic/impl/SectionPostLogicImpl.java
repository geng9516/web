package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.admin.component.dto.SectionPostCompanyRowListDTO;
import jp.smartcompany.admin.component.dto.SectionPostRowDTO;
import jp.smartcompany.admin.component.dto.SectionPostRowListDTO;
import jp.smartcompany.admin.groupmanager.logic.SectionPostLogic;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupsectionpostmappingDO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SectionPostLogicImpl implements SectionPostLogic {

    /** 処理区分(法人) */
    public static final String FG_COMP             = "01";
    /** 処理区分(法人＆組織指定リスト) */
    public static final String FG_COMP_SEC         = "02";
    /** 処理区分(組織ごとの定義情報取得(法人＆組織＆役職リスト)) */
    public static final String FG_COMP_SEC_POST    = "03";
    /** 処理区分(組織ごとの定義情報取得(法人＆組織＆社員番号リスト)) */
    public static final String FG_COMP_SEC_EMP     = "04";
    /** 処理区分(法人＆組織＆所属長リスト) */
    public static final String FG_COMP_SEC_BOSS    = "05";
    /** 処理区分(法人＆役職リスト) */
    public static final String FG_COMP_POST        = "06";
    /** 処理区分(法人＆社員リスト) */
    public static final String FG_COMP_EMP         = "07";

    /** 所属長フラグ */
    private static final String BOSS_FLG = "1";

    /** クエリ組み立てパーツ イコール */
    private static final String PT_EQUAL        = " = ";
    /** クエリ組み立てパーツ アンド */
    private static final String PT_AND          = " AND ";
    /** クエリ組み立てパーツ オア */
    private static final String PT_OR           = " OR ";
    /** クエリ組み立てパーツ カンマ(空白あり) */
    private static final String PT_COMMA        = " , ";
    /** クエリ組み立てパーツ 右カッコ */
    private static final String PT_OPEN_PAR     = " ( ";
    /** クエリ組み立てパーツ 左カッコ */
    private static final String PT_CLOSE_PAR    = " ) ";
    /** クエリ組み立てパーツ 記号(小なり) */
    private static final String PT_LESSER       = " <= ";
    /** クエリ組み立てパーツ 記号(大なり) */
    private static final String PT_GREATER      = " >= ";
    /** クエリ組み立てパーツ システム日付 */
    private static final String PT_SYSDATE      = " TRUNC(SYSDATE) ";
    /** ORの文字数 */
    private static final int CNT_OR     = 4;
    /** ANDの文字数 */
    private static final int CNT_AND    = 5;
    /** 全社区分 **/
    private static final String ALL_COMPANIES = "*";

    /** 異動歴 テーブル */
    private static final String TBL_HIST_DESIGNATION = "HIST_DESIGNATION";
    /** グループ定義条件マスタ（組織、役職）テーブル  */
    private static final String TBL_MAST_GROUPSECTIONPOSTMAPPING
            = "MAST_GROUPSECTIONPOSTMAPPING";

    /** 異動歴 顧客コード */
    private static final String COL_HD_CUSTOMERID   = "HD_CCUSTOMERID_CK";
    /** 異動歴 法人コード */
    private static final String COL_HD_COMPANYID    = "HD_CCOMPANYID_CK";
    /** 異動歴 所属コード */
    private static final String COL_HD_SECTIONID    = "HD_CSECTIONID_FK";
    /** 異動歴 役職コード */
    private static final String COL_HD_POSTID       = "HD_CPOSTID_FK";
    /** 異動歴 社員番号 */
    private static final String COL_HD_EMPLOYEEID   = "HD_CEMPLOYEEID_CK";
    /** 異動歴 開始日 */
    private static final String COL_HD_STARTDATE    = "HD_DSTARTDATE_CK";
    /** 異動歴 終了日 */
    private static final String COL_HD_ENDDATE      = "HD_DENDDATE";
    /** 異動歴 所属長フラグ*/
    private static final String COL_HD_BOSSORNOT    = "HD_CBOSSORNOT";
    /** 異動歴 ユーザID*/
    private static final String COL_HD_USERID       = "HD_CUSERID";

    /** グループ定義条件マスタ（組織、役職） 顧客コード */
    private static final String COL_MG_CUSTOMERID   = "MAG_CCUSTOMERID_CK_FK ";
    /** グループ定義条件マスタ（組織、役職） 法人コード */
    private static final String COL_MG_COMPANYID    = "MAG_CCOMPANYID";
    /** グループ定義条件マスタ（組織、役職） 所属コード */
    private static final String COL_MG_SECTIONID    = "MAG_CSECTIONID";
    /** グループ定義条件マスタ（組織、役職） 役職コード */
    private static final String COL_MG_POSTID       = "MAG_CPOSTID";
    /** グループ定義条件マスタ（組織、役職） 社員番号 */
    private static final String COL_MG_EMPLOYEEID   = "MAG_CEMPLOYEEID";
    /** グループ定義条件マスタ（組織、役職） 開始日 */
    private static final String COL_MG_STARTDATE    = "MAG_DSTARTDATE_CK";
    /** グループ定義条件マスタ（組織、役職） 終了日 */
    private static final String COL_MG_ENDDATE      = "MAG_DENDDATE";
    /** グループ定義条件マスタ（組織、役職） システムコード */
    private static final String COL_MG_SYSTEMID     = "MAG_CSYSTEMID_CK";
    /** グループ定義条件マスタ（組織、役職） グループコード */
    private static final String COL_MG_GROUPID      = "MAG_CGROUPID_FK";
    /** グループ定義条件マスタ（組織、役職） 定義区分 */
    private static final String COL_MG_TYPEID       = "MAG_CTYPEID";

    @Override
    public MastGroupsectionpostmappingDO insertSection(List<SectionPostRowListDTO> poList, String psCustomerId, String psSystemId, String psGroupId, Date ptStartDate, Date ptEndDate, int pnCnt) {
        // 入れ替え用Entity
        MastGroupsectionpostmappingDO oInsert = new MastGroupsectionpostmappingDO();

        oInsert.setMagCcustomeridCkFk(psCustomerId);
        oInsert.setMagCsystemidCk(psSystemId);
        oInsert.setMagCgroupidFk(psGroupId);
        oInsert.setMagDstartdateCk(ptStartDate);
        if (ptEndDate==null) {
            oInsert.setMagDenddate(SysUtil.getMaxDateObject());
        } else {
            oInsert.setMagDenddate(ptEndDate);
        }
        oInsert.setMagCtypeid(SectionPostLogicImpl.FG_COMP_SEC);
        oInsert.setMagCcompanyid(poList.get(pnCnt).getCompanyId());
        oInsert.setMagCsectionid(poList.get(pnCnt).getSectionId());
        oInsert.setMagCpostid(poList.get(pnCnt).getPostId());
        oInsert.setMagCemployeeid(poList.get(pnCnt).getEmployeeId());
        return null;
    }

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ条件定義マスタ(組織・役職))<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。
     *
     * @param poList        更新対象リスト
     * @param psTypeId      定義ID
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムID
     * @param psGroupId     グループID
     * @param ptStartDate   開始日
     * @param ptEndDate     終了日
     * @param pnCnt         シーケンス番号
     * @return MastGroupSectionPostMappingDO  更新用Entity
     */
    @Override
    public MastGroupsectionpostmappingDO insertSectionPost(
            List<SectionPostRowDTO> poList, String psTypeId,
            String psCustomerId, String psSystemId, String psGroupId,
            Date ptStartDate, Date ptEndDate, int pnCnt) {

        // 入れ替え用Entity
        MastGroupsectionpostmappingDO oInsert = new MastGroupsectionpostmappingDO();
        oInsert.setMagCcustomeridCkFk(psCustomerId);
        oInsert.setMagCsystemidCk(psSystemId);
        oInsert.setMagCgroupidFk(psGroupId);
        oInsert.setMagDstartdateCk(ptStartDate);
        if (ptEndDate==null) {
            oInsert.setMagDenddate(SysUtil.getMaxDateObject());
        } else {
            oInsert.setMagDenddate(ptEndDate);
        }
        oInsert.setMagCtypeid(psTypeId);
        oInsert.setMagCcompanyid(poList.get(pnCnt).getCompanyId());
        oInsert.setMagCsectionid(poList.get(pnCnt).getSectionId());
        oInsert.setMagCpostid(poList.get(pnCnt).getPostId());
        oInsert.setMagCemployeeid(poList.get(pnCnt).getEmployeeId());
        return oInsert;
    }

    @Override
    public String createQuery(String psCustomerId, String psSystemId,
                       String psGroupId, Date ptStartDate,Date ptEndDate,List<SectionPostCompanyRowListDTO> sectionPostCompanyList,String companyId) {
        /** グループ判定クエリ(組織・役職定義) */
        StringBuilder querySecPost = new StringBuilder();

        SectionPostCompanyRowListDTO companyList = sectionPostCompanyList.get(0);
        // 組織リスト
        List<SectionPostRowListDTO> lSectionList = companyList.getGlSectionList();
        // 組織分ループさせる
        if (lSectionList != null) {
            for (SectionPostRowListDTO sectionPostRowListDTO : lSectionList) {
                // グループ判定結果クエリ組み立て処理(法人＆組織＆役職)
                int nPostListCnt = sectionPostRowListDTO.getGlPostCompList().size();
                getQueryData(querySecPost, sectionPostRowListDTO.getGlPostCompList(), FG_COMP_SEC_POST,companyId);
                // グループ判定結果クエリ組み立て処理(法人＆組織＆社員番号)
                int nEmpListCnt = sectionPostRowListDTO.getGlEmpoyeesCompList().size();
                getQueryData(querySecPost, sectionPostRowListDTO.getGlEmpoyeesCompList(), FG_COMP_SEC_EMP,companyId);
                // グループ判定結果クエリ組み立て処理(組織配下データ)
                if (nPostListCnt + nEmpListCnt < 1) {
                    // グループ判定結果クエリ組み立て処理(法人＆組織)
                    querySecPost.append(
                            createQuerySectionPost(FG_COMP_SEC, sectionPostRowListDTO.getSectionId(),companyId));
                }
            }
        }
        // グループ判定結果クエリ組み立て処理(法人＆組織＆所属長リスト)
        getQueryData(querySecPost,companyList.getGlBossCompSectionList(), FG_COMP_SEC_BOSS,companyId);
        // グループ判定結果クエリ組み立て処理(法人＆役職リスト)
        getQueryData(querySecPost,companyList.getGlPostCompList(), FG_COMP_POST,companyId);
        // グループ判定結果クエリ組み立て処理(法人＆社員リスト)
        getQueryData(querySecPost,companyList.getGlEmpoyeesCompList(), FG_COMP_EMP,companyId);
        // 設定が未定義の場合
        if (querySecPost.length() == 0) {
            // グループ判定結果クエリ組み立て処理(法人)
            querySecPost.append(createQuerySectionPost(FG_COMP, null,companyId));
        } else {
            // 内部変数に一旦格納
            String sTempQuery = checkWhereList(querySecPost.toString());
            querySecPost = new StringBuilder();
            querySecPost.append(sTempQuery);
            // 組み立てたクエリをAND結合する
            querySecPost.insert(0, PT_OPEN_PAR);
            querySecPost.append(PT_CLOSE_PAR);
            querySecPost.append(PT_AND);
        }
        // グループ判定結果クエリ組み立て処理(ヘッダ部)
        querySecPost.insert(0, createQueryHeader());
        // グループ判定結果クエリ組み立て処理(フッタ部)
        querySecPost.append(createQueryFooter(psCustomerId, psSystemId, psGroupId));
        return querySecPost.toString();
    }

    /**
     * クエリ作成処理(ヘッダ部)<br>
     * 組織・役職定義での設定内容を元に、検索クエリを作成する。
     *
     * @return  String  グループ判定用クエリ(SELECT句 + 取得カラム + FROM句 + テーブル + WHERE句)
     */
    private String createQueryHeader() {
        // グループ判定用クエリのヘッダ部を作成する
        return " SELECT " +
                "   DISTINCT" + PT_OPEN_PAR +
                COL_HD_EMPLOYEEID +
                PT_CLOSE_PAR + PT_COMMA +
                COL_HD_CUSTOMERID +
                PT_COMMA +
                COL_HD_COMPANYID +
                PT_COMMA +
                COL_HD_USERID +
                " FROM " +
                TBL_HIST_DESIGNATION +
                PT_COMMA +
                TBL_MAST_GROUPSECTIONPOSTMAPPING +
                " WHERE ";
    }

    /**
     * 結合Where句の妥当性チェック
     *
     * @param   psWhere 結合Where句
     * @return  String  結合Where句
     */
    private String checkWhereList(String psWhere) {

        // 結合Where句がなかったら空白で返却
        if (SysUtil.transNull(psWhere).equals("")) {
            return psWhere;
        }

        // 最後がORで終わっていたら削除する
        if (psWhere.lastIndexOf(PT_OR)
                == (psWhere.length() - CNT_OR)
                && (psWhere.length() - CNT_OR) > 0) {

            psWhere = psWhere.substring(0, psWhere.length() - CNT_OR);
        }

        // 最後がANDで終わっていたら削除する
        if (psWhere.lastIndexOf(PT_AND)
                == (psWhere.length() - CNT_AND)
                && (psWhere.length() - CNT_AND) > 0) {

            psWhere = psWhere.substring(0, psWhere.length() - CNT_AND);
        }

        return psWhere;
    }

    /**
     * DB新規登録処理<br>
     * 定義情報をDBへ反映させる(汎用 - 物理更新)
     *
     * @param poList        更新対象リスト
     * @param psTypeId      定義ID
     */
    private void getQueryData(StringBuilder querySecPost, List<SectionPostRowDTO> poList, String psTypeId,String companyId) {
        // データが存在するときのみ
        if (CollUtil.isNotEmpty(poList)) {
            // グループ判定結果クエリ組み立て処理(組織・役職 定義区分ごと結合式)
            querySecPost.append(createQuerySectionPost(psTypeId, null,companyId));
        }
    }

    /**
     * クエリ作成処理(組織・役職結合式)<br>
     * 組織・役職定義での設定内容を元に、検索クエリを作成する。
     *
     * @param psTypeId      定義ID
     * @param psSectionId   組織ID
     * @return  String      グループ判定用クエリ(組織・役職結合式)
     */
    private String createQuerySectionPost(String psTypeId, String psSectionId,String companyId) {

        StringBuilder sbQuery = new StringBuilder();

        // 共通の条件を追加する
        sbQuery.append(PT_OPEN_PAR);

        // 取得した定義区分より、結合式を作成する
        // 定義区分＝"01"(法人指定)
        if (psTypeId.equals(FG_COMP)) {
            // テーブル：グループ定義条件マスタ(組織、役職)，異動歴
            // カラムID：法人コード
            sbQuery.append(COL_MG_COMPANYID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_COMPANYID);
            if (companyId.equalsIgnoreCase(ALL_COMPANIES)) {
                sbQuery.append(PT_AND);
                sbQuery.append(COL_MG_COMPANYID);
                sbQuery.append(PT_EQUAL);
                sbQuery.append(escDBString(companyId));
            }
            // 定義区分＝"02"(法人＆組織指定)
        } else if (psTypeId.equals(FG_COMP_SEC) && psSectionId != null) {
            // テーブル：グループ定義条件マスタ(組織、役職)，異動歴
            // カラムID：法人コード，組織コード(所属コード)
            sbQuery.append(COL_MG_COMPANYID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_COMPANYID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_SECTIONID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_SECTIONID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_SECTIONID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(escDBString(psSectionId));

            // 定義区分＝"03"(法人＆組織＆役職指定)
        } else if (psTypeId.equals(FG_COMP_SEC_POST)) {
            // テーブル：グループ定義条件マスタ(組織、役職)，異動歴
            // カラムID：法人コード，組織コード(所属コード)，役職コード
            sbQuery.append(COL_MG_COMPANYID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_COMPANYID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_SECTIONID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_SECTIONID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_POSTID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_POSTID);

            // 定義区分＝"04"(法人＆組織＆社員指定)
        } else if (psTypeId.equals(SectionPostLogicImpl.FG_COMP_SEC_EMP)) {
            // テーブル：グループ定義条件マスタ(組織、役職)，異動歴
            // カラムID：法人コード，組織コード(所属コード)，社員番号
            sbQuery.append(COL_MG_COMPANYID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_COMPANYID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_SECTIONID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_SECTIONID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_EMPLOYEEID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_EMPLOYEEID);

            // 定義区分＝"05"(法人＆所属長指定)
        } else if (psTypeId.equals(SectionPostLogicImpl.FG_COMP_SEC_BOSS)) {
            // テーブル：グループ定義条件マスタ(組織、役職)，異動歴
            // カラムID：法人コード，組織コード(所属コード)，所属長フラグ("1":所属長)
            sbQuery.append(COL_MG_COMPANYID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_COMPANYID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_SECTIONID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_SECTIONID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_HD_BOSSORNOT);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(BOSS_FLG);

            // 定義区分＝"06"(法人＆役職指定)
        } else if (psTypeId.equals(SectionPostLogicImpl.FG_COMP_POST)) {
            // テーブル：グループ定義条件マスタ(組織、役職)，異動歴
            // カラムID：法人コード，役職コード
            sbQuery.append(COL_MG_COMPANYID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_COMPANYID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_POSTID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_POSTID);

            // 定義区分＝"07"(法人＆社員指定)
        } else if (psTypeId.equals(SectionPostLogicImpl.FG_COMP_EMP)) {
            // テーブル：グループ定義条件マスタ(組織、役職)，異動歴
            // カラムID：法人コード，社員番号
            sbQuery.append(COL_MG_COMPANYID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_COMPANYID);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_EMPLOYEEID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(COL_HD_EMPLOYEEID);

        }

        // 共通の条件を追加する
        // グループ定義の場合、法人のデータを作成しないので飛ばす
        if (!psTypeId.equals(FG_COMP)) {
            sbQuery.append(PT_AND);
            sbQuery.append(COL_MG_TYPEID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(escDBString(psTypeId));
        }

        sbQuery.append(PT_CLOSE_PAR);
        sbQuery.append(PT_OR);

        return sbQuery.toString();

    }

    /**
     * クエリ作成処理(フッタ部)<br>
     * 組織・役職定義での設定内容を元に、検索クエリを作成する。
     *
     * @param psCustomerId  顧客コード
     * @param psSystemId    システムID
     * @param psGroupId     グループID
     * @return  String      グループ判定用クエリ(条件式)
     */
    private String createQueryFooter(String psCustomerId, String psSystemId, String psGroupId) {
        // グループ判定用クエリのフッタ部を作成する
        return COL_MG_CUSTOMERID + // 顧客ID
               PT_EQUAL +
               escDBString(psCustomerId) +
               PT_AND +
               COL_MG_SYSTEMID +   // システムID
               PT_EQUAL +
               escDBString(psSystemId) +
               PT_AND +
               COL_MG_GROUPID +    // グループID
               PT_EQUAL +
               escDBString(psGroupId) +
               PT_AND +
               COL_HD_STARTDATE +  // 開始日(異動歴)
                PT_LESSER +
                PT_SYSDATE +
                PT_AND +
                COL_HD_ENDDATE +    // 終了日(異動歴)
                PT_GREATER +
                PT_SYSDATE + PT_AND +
                COL_MG_STARTDATE +  // 開始日(グループ定義条件マスタ)
                PT_LESSER +
                PT_SYSDATE +
                PT_AND +
                COL_MG_ENDDATE +    // 終了日(グループ定義条件マスタ)
                PT_GREATER +
                PT_SYSDATE;
    }

    /**
     * DB更新用に文字列を「'」で囲んで返します。 nullであれば「NULL」の文字列を返します。
     *
     * @param   psString    変換元文字列
     * @return  String      変換結果文字列
     */
    protected String escDBString(String psString) {
        return SysUtil.transStringNullToDB(SysUtil.escapeQuote(psString));
    }

}
