package jp.smartcompany.framework.util;

import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
public class BuildTargetSql {

    private static final String BEAN_DESC        = "BuildTargetSql";	//LOG出力用ディスクリプタ

    //HIST_TARGETDEFINITIONS検索用カラムインデックス
    public static final int COL_HTD_CANDOR				= 0;	//AND/OR
    public static final int COL_HTD_COPENEDPARENTHSIS	= 1;	//開始カッコ
    public static final int COL_HTD_CCOLUMNNAME			= 2;	//カラム名
    public static final int COL_HTD_COPERATOR			= 3;	//比較演算子
    public static final int COL_HTD_CDISPLAYVALUE		= 4;	//表示値
    public static final int COL_HTD_CCLOSEDPARENTHSIS	= 5;	//終了カッコ
    public static final int COL_HTD_CTABLEID				= 6;	//テーブルID
    public static final int COL_HTD_CCOLUMNID			= 7;	//カラムID
    public static final int COL_HTD_CTYPEOFCOLUMN		= 8;	//カラムタイプ
    public static final int COL_MD_CMASTERTBLNAME		= 9;	//参照マスタ識別
    public static final int COL_HTD_CVALUE				= 10;	//値
    public static final int COL_HTD_CMYFLAG				= 11;	//検索者自身の属性と比較フラグ

    // MAST_GROUPDEFINITIONS検索用カラムインデックス
    public static final int COL_MGP_BASEFLAG				= 0;	// 定義種別
    public static final int COL_MGP_CQUERY				= 1;	// 定義SQL

    // HIST_DESIGNATION検索用カラムインデックス
    public static final int COL_HD_CSECTIONID_FK			= 0;	// 組織コード
    public static final int COL_HD_CPOSTID_FK			= 1;	// 役職コード

    private Vector gvecTargetSqlFroms = new Vector();		// From句保存用
    private Vector gvecTargetSqlWheres = new Vector();	// Where（結合式）句保存用
    private String gsTargetSqlWhere;						// Where（条件式）句保存用

    private PsDBBean psDBBean;
    private PsResult psResult;

    public BuildTargetSql() {
        this.psResult = new PsResult();
    }

    public BuildTargetSql(PsDBBean psDBBean) {
        this.psDBBean = psDBBean;
        this.psResult = new PsResult();
    }

    /**
     * メイン処理
     * @return  なし
     * @throws  Exception
     */
    public void execute() throws Exception {
        // 基準日を変換する
        String sCreterialDate = psDBBean.getCreterialDate1();
        if(sCreterialDate.indexOf("-") > -1) {
            sCreterialDate = sCreterialDate.replace('-', '/');
            sCreterialDate = sCreterialDate.substring(0, sCreterialDate.indexOf(" "));
        }

        // HIST_TARGETDEFINITIONSを取り出す
        String sWhere = "";
        Vector vecQuery = new Vector();
        vecQuery.add(buildSQLForSelectCondition());
        PsResult rsResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
        if(rsResult.getException().size() > 0 && !rsResult.getException().get(0).equals("")) {
            throw new Exception();
        }


        // 先読みして組織と役職の関係を取得する
        Vector vecSecPost = new Vector();
        int nSeq = 0;
        boolean bSecPost = false;

        int nSectionLineSeq = 0;
        int nPostLineSeq = 0;
        String sSectionTableID = "";
        String sPostTableID = "";
        String sSectionOperator = "";
        String sSectionOpenedParenthsis = "";
        String sSectionClosedParenthsis = "";
        String sSectionColumnID = "";
        String sPostColumnID = "";
        String sPostOperator = "";
        String sPostOpenedParenthsis = "";
        String sPostClosedParenthsis = "";
        String sSecPostAndOr = "";

        for(int i = 0; i < ((Vector)rsResult.getResult().get(0)).size(); i++) {
            Hashtable hshRow = new Hashtable();
            String sAndOr = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CANDOR, i);
            String sOpenedParenthsis = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_COPENEDPARENTHSIS, i);
            String sOperator = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_COPERATOR, i);
            String sClosedParenthsis = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CCLOSEDPARENTHSIS, i);
            String sTableID = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CTABLEID, i);
            String sColumnID = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CCOLUMNID, i);
            String sMasterTableName = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_MD_CMASTERTBLNAME, i);
            String sMyFlag = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CMYFLAG, i);

            // 途中でORが出たら組織と役職の間につながりはなし
            if(bSecPost && sAndOr.equals("OR")) {
                bSecPost = false;
            }
            // 組織または役職が検索者自身の属性と比較されているとき
            if(sMasterTableName != null && (sMasterTableName.equals("QSECTION") || sMasterTableName.equals("QPOST")) && sMyFlag.equals("1")) {
                // まだ保存データができていないとき
                if(!bSecPost) {
                    bSecPost = true;
                    sSecPostAndOr = sAndOr;
                    if(sMasterTableName.equals("QSECTION")) {
                        nSectionLineSeq = i;
                        sSectionTableID = sTableID;
                        sSectionColumnID = sColumnID;
                        sSectionOperator = sOperator;
                        sSectionOpenedParenthsis = sOpenedParenthsis;
                        sSectionClosedParenthsis = sClosedParenthsis;
                    }
                    else {
                        nPostLineSeq = i;
                        sPostTableID = sTableID;
                        sPostColumnID = sColumnID;
                        sPostOperator = sOperator;
                        sPostOpenedParenthsis = sOpenedParenthsis;
                        sPostClosedParenthsis = sClosedParenthsis;
                    }
                }
                // 対応する組織または役職が見つかったとき
                else {
                    if(sMasterTableName.equals("QSECTION")) {
                        nSectionLineSeq = i;
                        sSectionTableID = sTableID;
                        sSectionColumnID = sColumnID;
                        sSectionOperator = sOperator;
                        sSectionOpenedParenthsis = sOpenedParenthsis;
                        sSectionClosedParenthsis = sClosedParenthsis;
                    }
                    else {
                        nPostLineSeq = i;
                        sPostTableID = sTableID;
                        sPostColumnID = sColumnID;
                        sPostOperator = sOperator;
                        sPostOpenedParenthsis = sOpenedParenthsis;
                        sPostClosedParenthsis = sClosedParenthsis;
                    }
                    Vector vecRow = new Vector();
                    vecRow.add(String.valueOf(nSectionLineSeq));
                    vecRow.add(sSectionTableID);
                    vecRow.add(sSectionColumnID);
                    vecRow.add(sSectionOperator);
                    vecRow.add(sSectionOpenedParenthsis);
                    vecRow.add(sSectionClosedParenthsis);
                    vecRow.add(String.valueOf(nPostLineSeq));
                    vecRow.add(sPostTableID);
                    vecRow.add(sPostColumnID);
                    vecRow.add(sPostOperator);
                    vecRow.add(sPostOpenedParenthsis);
                    vecRow.add(sPostClosedParenthsis);
                    vecRow.add(sSecPostAndOr);
                    vecSecPost.add(vecRow);
                    bSecPost = false;

                    nSeq++;
                }
            }
        }

        // １行ずつ読んでSQLを組み立てる
        for(int i = 0; i < ((Vector)rsResult.getResult().get(0)).size(); i++) {
            String sAndOr = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CANDOR, i);
            if(sAndOr == null) {
                sAndOr = "";
            }
            String sOpenedParenthsis = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_COPENEDPARENTHSIS, i);
            if(sOpenedParenthsis == null) {
                sOpenedParenthsis = "";
            }
            String sColumnName = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CCOLUMNNAME, i);
            String sOperator = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_COPERATOR, i);
            String sDisplayValue = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CDISPLAYVALUE, i);
            String sClosedParenthsis = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CCLOSEDPARENTHSIS, i);
            if(sClosedParenthsis == null) {
                sClosedParenthsis = "";
            }
            String sTableID = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CTABLEID, i);
            String sColumnID = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CCOLUMNID, i);
            String sTypeOfColumn = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CTYPEOFCOLUMN, i);
            String sMasterTableName = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_MD_CMASTERTBLNAME, i);
            String sValue = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CVALUE, i);
            String sMyFlag = (String)valueAtColumnRow((Vector)rsResult.getResult().get(0), COL_HTD_CMYFLAG, i);

            // マスタ参照カラムの場合
            if(sMasterTableName != null) {
                // 役職カラムの場合の処理
                if(sMasterTableName.equals("QPOST")) {
                    // 現在のFrom句リストにMAST_POSTがなければ追加
                    int j = 0;
                    for(j = 0; j < gvecTargetSqlFroms.size(); j++) {
                        if(gvecTargetSqlFroms.elementAt(j).equals("MAST_POST")) {
                            break;
                        }
                    }
                    if(j == gvecTargetSqlFroms.size()) {
                        gvecTargetSqlFroms.add("MAST_POST");
                        gvecTargetSqlWheres.add(createJoinSql("MAST_POST", sCreterialDate, gvecTargetSqlFroms.size()));
                    }
                    sWhere += createPostSQL(sAndOr, sOpenedParenthsis, sOperator, sValue, sClosedParenthsis, sMyFlag, sCreterialDate, i, vecSecPost);
                }
                // 組織カラムの場合の処理
                else if(sMasterTableName.equals("QSECTION")) {
                    sWhere += createOrganisationSQL(sAndOr, sOpenedParenthsis, sTableID, sColumnID, sOperator, sValue, sClosedParenthsis, sMyFlag, sCreterialDate, i, vecSecPost);
                    Vector vecColumnIDs = getColumnIDs(sTableID);
                    int j = 0;
                    for(j = 0; j < gvecTargetSqlFroms.size(); j++) {
                        if(gvecTargetSqlFroms.elementAt(j).equals(sTableID)) {
                            break;
                        }
                    }
                    if(j == gvecTargetSqlFroms.size()) {
                        gvecTargetSqlFroms.add(sTableID);
                        gvecTargetSqlWheres.add(createJoinSql(vecColumnIDs, sCreterialDate, gvecTargetSqlFroms.size()));
                    }
                }
                // 一般カラムの場合の処理
                else  {
                    Vector vecColumnIDs = getColumnIDs(sTableID);
                    int j = 0;
                    for(j = 0; j < gvecTargetSqlFroms.size(); j++) {
                        if(gvecTargetSqlFroms.elementAt(j).equals(sTableID)) {
                            break;
                        }
                    }
                    if(j == gvecTargetSqlFroms.size()) {
                        gvecTargetSqlFroms.add(sTableID);
                        gvecTargetSqlWheres.add(createJoinSql(vecColumnIDs, sCreterialDate, gvecTargetSqlFroms.size()));
                    }
                    sWhere += createNormalSQL(vecColumnIDs, sAndOr, sOpenedParenthsis, sTableID, sColumnID, sTypeOfColumn, sOperator, sValue, sClosedParenthsis, sMyFlag, sCreterialDate);
                }
            }
            // その他のカラムの場合
            else {
                Vector vecColumnIDs = getColumnIDs(sTableID);
                int j = 0;
                for(j = 0; j < gvecTargetSqlFroms.size(); j++) {
                    if(gvecTargetSqlFroms.elementAt(j).equals(sTableID)) {
                        break;
                    }
                }
                if(j == gvecTargetSqlFroms.size()) {
                    gvecTargetSqlFroms.add(sTableID);
                    gvecTargetSqlWheres.add(createJoinSql(vecColumnIDs, sCreterialDate, gvecTargetSqlFroms.size()));
                }
                sWhere += createNormalSQL(vecColumnIDs, sAndOr, sOpenedParenthsis, sTableID, sColumnID, sTypeOfColumn, sOperator, sValue, sClosedParenthsis, sMyFlag, sCreterialDate);
            }
        }
        // 最後がORで終わっていたら削除する
        if(sWhere.lastIndexOf(" OR ") == sWhere.length() - 5 && sWhere.length() - 5 > 0) {
            sWhere = sWhere.substring(0, sWhere.length() - 5);
        }
        // 最後がANDで終わっていたら削除する
        if(sWhere.lastIndexOf(" AND ") == sWhere.length() - 6 && sWhere.length() - 6 > 0) {
            sWhere = sWhere.substring(0, sWhere.length() - 6);
        }
        gsTargetSqlWhere = "(" + sWhere + ")";
    }

    /**
     * 役職を検索するSQLを組み立てます
     * @param psAndOr
     * @param psOpenedParenthsis
     * @param psOperator
     * @param psValue
     * @param psClosedParenthsis
     * @param psMyFlag
     * @param psCreterialDate
     * @param pnLineSeq
     * @param pvecSecPost
     * @return
     */
    private String createPostSQL (
            String psAndOr,
            String psOpenedParenthsis,
            String psOperator,
            String psValue,
            String psClosedParenthsis,
            String psMyFlag,
            String psCreterialDate,
            int pnLineSeq,
            Vector pvecSecPost) throws Exception {

        String sWhere = "";
        // 通常の比較の場合
        if(psMyFlag.equals("0")) {
            sWhere +=
                    psAndOr + " " + psOpenedParenthsis + " ##MAST_POST##.MAP_NWEIGHTAGE " + psOperator + " ";
            sWhere +=
                    "(SELECT "	+
                            "a.MAP_NWEIGHTAGE "	+
                            "FROM "	+
                            "MAST_POST a "	+
                            "WHERE "	+
                            "a.MAP_CCUSTOMERID_CK_FK = " + psDBBean.escDBString(psDBBean.getCustID()) + " "	+
                            "AND a.MAP_CCOMPANYID_CK_FK = " + psDBBean.escDBString(psDBBean.getCompCode()) + " "	+
                            "AND a.MAP_CPOSTID_CK = " + psDBBean.escDBString(psValue) + " "	+
                            "AND a.MAP_CLANGUAGE = 'ja' "	+
                            "AND a.MAP_DSTART <= " + SysUtil.transDateNullToDB(psCreterialDate) + " "	+
                            "AND a.MAP_DEND >= " + SysUtil.transDateNullToDB(psCreterialDate)	+
                            ")";
            sWhere += psClosedParenthsis;
        }
        // 検索者自身の属性と比較の場合
        else {
            // 組織と役職が組み合わせて使われている場合のみ組み立を行う
            if(checkSecPost(pvecSecPost, pnLineSeq)) {
                // 検索者がグループに該当する時にヒットした組織、役職の組み合わせを取得する
                Vector vecSecPost = getGroupSecPost();

                // 対応するテーブルID、カラムIDを取得する
                String sSectionTableID = "";
                String sSectionColumnID = "";
                String sSectionOperator = "";
                String sSectionOpenedParenthsis = "";
                String sSectionClosedParenthsis = "";
                String sPostTableID = "";
                String sPostColumnID = "";
                String sPostOperator = "";
                String sPostOpenedParenthsis = "";
                String sPostClosedParenthsis = "";
                String sAndOr = "";

                for(int i = 0; i < pvecSecPost.size(); i++) {
                    int nPostLineSeq = Integer.parseInt((String)((Vector)pvecSecPost.elementAt(i)).elementAt(6));
                    if(nPostLineSeq == pnLineSeq) {
                        sSectionTableID = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(1);
                        sSectionColumnID = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(2);
                        sSectionOperator = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(3);
                        sSectionOpenedParenthsis = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(4);
                        if(sSectionOpenedParenthsis == null) {
                            sSectionOpenedParenthsis = "";
                        }
                        sSectionClosedParenthsis = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(5);
                        if(sSectionClosedParenthsis == null) {
                            sSectionClosedParenthsis = "";
                        }
                        sPostTableID = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(7);
                        sPostColumnID = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(8);
                        sPostOperator = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(9);
                        sPostOpenedParenthsis = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(10);
                        if(sPostOpenedParenthsis == null) {
                            sPostOpenedParenthsis = "";
                        }
                        sPostClosedParenthsis = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(11);
                        if(sPostClosedParenthsis == null) {
                            sPostClosedParenthsis = "";
                        }
                        sAndOr = (String)((Vector)pvecSecPost.elementAt(i)).elementAt(12);
                        if(sAndOr == null) {
                            sAndOr = "";
                        }
                    }
                }

                sWhere += sAndOr + " (";
                for(int i = 0; i < vecSecPost.size(); i++) {
                    boolean bOrFlag = false;
                    String sSectionCode = (String)((Vector)vecSecPost.get(i)).get(0);
                    String sPostCode = (String)((Vector)vecSecPost.get(i)).get(1);
                    String sWhereTemp = "";
                    sWhereTemp += "(";
                    if(sSectionOperator.equals("=")) {
                        /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                        sWhereTemp += SectionsSeparateWhere( "##" + sSectionTableID + "##." + sSectionColumnID, psDBBean.escDBString(sSectionCode), 1000, true );
                        /* ▲ 2005/11/02 A.SUZUKI */
                        bOrFlag = true;
                    }
                    else if(sSectionOperator.equals("<=")) {
                        /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                        // 親組織があれば追加する
                        String sUpperSectionList = psDBBean.getV3Logic().getUpperSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), sSectionCode, psCreterialDate);
                        if(!sUpperSectionList.equals("")) {
                            sUpperSectionList = psDBBean.escDBString(sSectionCode) + "," + sUpperSectionList;
                        } else {
                            sUpperSectionList = psDBBean.escDBString(sSectionCode);
                        }
                        sWhereTemp += SectionsSeparateWhere( "##" + sSectionTableID + "##." + sSectionColumnID, sUpperSectionList, 1000, true );
                        /* ▲ 2005/11/02 A.SUZUKI */
                        bOrFlag = true;
                    }
                    else if(sSectionOperator.equals("<")) {
                        String sUpperSectionList = psDBBean.getV3Logic().getUpperSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), sSectionCode, psCreterialDate);
                        // 親組織がある時だけ作成する
                        if(!sUpperSectionList.equals("")) {
                            /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                            sWhereTemp += SectionsSeparateWhere( "##" + sSectionTableID + "##." + sSectionColumnID, sUpperSectionList, 1000, true );
                            /* ▲ 2005/11/02 A.SUZUKI */
                            bOrFlag = true;
                        }
                    }
                    else if(sSectionOperator.equals(">=")) {
                        /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                        // 子組織があれば追加する
                        String sLowerSectionList = psDBBean.getV3Logic().getLowerSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), sSectionCode, psCreterialDate);
                        if(!sLowerSectionList.equals("")) {
                            sLowerSectionList = psDBBean.escDBString(sSectionCode) + "," + sLowerSectionList;
                        } else {
                            sLowerSectionList = psDBBean.escDBString(sSectionCode);
                        }
                        sWhereTemp += SectionsSeparateWhere( "##" + sSectionTableID + "##." + sSectionColumnID, sLowerSectionList, 1000, true );
                        /* ▲ 2005/11/02 A.SUZUKI */
                        bOrFlag = true;
                    }
                    else if(sSectionOperator.equals(">")) {
                        String sLowerSectionList = psDBBean.getV3Logic().getLowerSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), sSectionCode, psCreterialDate);
                        // 子組織があるときだけ作成する
                        if(!sLowerSectionList.equals("")) {
                            /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                            sWhereTemp += SectionsSeparateWhere( "##" + sSectionTableID + "##." + sSectionColumnID, sLowerSectionList, 1000, true );
                            /* ▲ 2005/11/02 A.SUZUKI */
                            bOrFlag = true;
                        }
                    }
                    // 組織を追加したときだけ役職も追加する
                    if(bOrFlag) {
                        sWhereTemp += " AND ";
                        sWhereTemp +=
                                "##MAST_POST##.MAP_NWEIGHTAGE "	+
                                        sPostOperator		+
                                        " (SELECT "	+
                                        "a.MAP_NWEIGHTAGE "	+
                                        "FROM "	+
                                        "MAST_POST a,"	+
                                        "HIST_DESIGNATION b "	+
                                        "WHERE "	+
                                        "b.HD_CCUSTOMERID_CK = " + psDBBean.escDBString(psDBBean.getCustID()) + " "	+
                                        "AND b.HD_CCOMPANYID_CK = " + psDBBean.escDBString(psDBBean.getCompCode()) + " "	+
                                        "AND b.HD_CEMPLOYEEID_CK = " + psDBBean.escDBString(psDBBean.getUserCode())	+ " "	+
                                        "AND b.HD_CSECTIONID_FK = " + psDBBean.escDBString(sSectionCode) + " "	+
                                        // 2005/09/12 Saito
                                        "AND b.HD_CPOSTID_FK = " + psDBBean.escDBString(sPostCode) + " "			+
                                        "AND b.HD_DSTARTDATE_CK <= " + SysUtil.transDateNullToDB(psCreterialDate) + " "	+
                                        "AND b.HD_DENDDATE >= " + SysUtil.transDateNullToDB(psCreterialDate) + " "	+
                                        "AND a.MAP_CCUSTOMERID_CK_FK = b.HD_CCUSTOMERID_CK "	+
                                        "AND a.MAP_CCOMPANYID_CK_FK = b.HD_CCOMPANYID_CK "	+
                                        "AND a.MAP_CPOSTID_CK = b.HD_CPOSTID_FK "	+
                                        "AND a.MAP_CLANGUAGE = 'ja' "	+
                                        "AND a.MAP_DSTART <= " + SysUtil.transDateNullToDB(psCreterialDate) + " "	+
                                        "AND a.MAP_DEND >= " + SysUtil.transDateNullToDB(psCreterialDate)	+
                                        ")";
                        sWhereTemp += ")";
                    }
                    // 最後以外で追加があったときはORで結合する
                    if(vecSecPost.size() - 1 != i && bOrFlag) {
                        sWhereTemp += " OR ";
                    }
                    // 2005/11/09 Saito
                    if(!sWhereTemp.equals("(")) {
                        sWhere += sWhereTemp;
                    }
                }
                // 最後がORで終わっていたら削除する
                if(sWhere.lastIndexOf(" OR ") == sWhere.length() - 4 && sWhere.length() - 4 > 0) {
                    sWhere = sWhere.substring(0, sWhere.length() - 4);
                }
                // 最後がOR (で終わっていたら削除する
                if(sWhere.lastIndexOf(" OR (") == sWhere.length() - 5 && sWhere.length() - 5 > 0) {
                    sWhere = sWhere.substring(0, sWhere.length() - 5);
                }
                // 何も追加されていないときは何も検索させないために成り立たない式を追加する
                if(sWhere.lastIndexOf("(") == sWhere.length() - 1) {
                    sWhere += "1 = 2)";
                }
                else {
                    sWhere += ")";
                }
            }
        }
        return sWhere;
    }

    /**
     * 組織を検索するSQLを組み立てます
     * @param psAndOr
     * @param psOpenedParenthsis
     * @param psColumnID
     * @param psOperator
     * @param psValue
     * @param psClosedParenthsis
     * @param psMyFlag
     * @param psCreterialDate
     * @param pnLineSeq
     * @param pvecSecPost
     * @return
     * @throws Exception
     */
    private String createOrganisationSQL(
            String psAndOr,
            String psOpenedParenthsis,
            String psTableID,
            String psColumnID,
            String psOperator,
            String psValue,
            String psClosedParenthsis,
            String psMyFlag,
            String psCreterialDate,
            int pnLineSeq,
            Vector pvecSecPost) throws Exception {

        String sWhere = "";
        sWhere +=
                psAndOr + " " + psOpenedParenthsis + " ";
        // 通常の比較の場合
        if(psMyFlag.equals("0")) {
            if(psOperator.equals("=")) {
                /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, psDBBean.escDBString(psValue), 1000, true );
                /* ▲ 2005/11/02 A.SUZUKI */
            }
            else if(psOperator.equals("<=")) {
                /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                // 親組織があれば追加する
                String sSectionList = psDBBean.getV3Logic().getUpperSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), psValue, psCreterialDate);
                if(!sSectionList.equals("")) {
                    sSectionList = psDBBean.escDBString(psValue) + "," + sSectionList;
                } else {
                    sSectionList = psDBBean.escDBString(psValue);
                }
                sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, sSectionList, 1000, true );
                /* ▲ 2005/11/02 A.SUZUKI */
            }
            else if(psOperator.equals("<")) {
                String sSectionList = psDBBean.getV3Logic().getUpperSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), psValue, psCreterialDate);
                // 親組織がある時だけ追加する
                if(!sSectionList.equals("")) {
                    /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                    sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, sSectionList, 1000, true );
                    /* ▲ 2005/11/02 A.SUZUKI */
                }
            }
            else if(psOperator.equals(">=")) {
                /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                // 子組織があれば追加する
                String sSectionList = psDBBean.getV3Logic().getLowerSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), psValue, psCreterialDate);
                if(!sSectionList.equals("")) {
                    sSectionList = psDBBean.escDBString(psValue) + "," + sSectionList;
                } else {
                    sSectionList = psDBBean.escDBString(psValue);
                }
                sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, sSectionList, 1000, true );
                /* ▲ 2005/11/02 A.SUZUKI */
            }
            else if(psOperator.equals(">")) {
                String sSectionList = psDBBean.getV3Logic().getLowerSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), psValue, psCreterialDate);
                // 子組織がある時だけ追加する
                if(!sSectionList.equals("")) {
                    /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                    sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, sSectionList, 1000, true );
                    /* ▲ 2005/11/02 A.SUZUKI */
                }
            }
            else if(psOperator.equals("<>")) {
                /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, psDBBean.escDBString(psValue), 1000, false );
                /* ▲ 2005/11/02 A.SUZUKI */
            }
            // ▼▼▼ 2005/11/30 Saito
            // もし結果が空なら検索させないために成り立たない式を挿入する
            if(trimSpace(sWhere).equals("")) {
                sWhere += "1 = 2";
            }
            // ▲▲▲ 2005/11/30 Saito
        }
        // 検索者自身の属性と比較の場合
        else {
            // もし組織と役職が組み合わせて使われている場合はここでは組み立てないので空文字を返す
            if(checkSecPost(pvecSecPost, pnLineSeq)) {
                return "";
            }
            // 検索者がグループに該当する時にヒットした組織、役職の組み合わせを取得する
            Vector vecSecPost = getGroupSecPost();

            sWhere += "(";
            for(int i = 0; i < vecSecPost.size(); i++) {
                boolean bOrFlag = false;
                String sSectionCode = (String)((Vector)vecSecPost.get(i)).get(0);
                if(psOperator.equals("=")) {
                    /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                    sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, psDBBean.escDBString(sSectionCode), 1000, true );
                    /* ▲ 2005/11/02 A.SUZUKI */
                    bOrFlag = true;
                }
                else if(psOperator.equals("<=")) {
                    /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                    // 親組織があれば追加する
                    String sUpperSectionList = psDBBean.getV3Logic().getUpperSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), sSectionCode, psCreterialDate);
                    if(!sUpperSectionList.equals("")) {
                        sUpperSectionList = psDBBean.escDBString(sSectionCode) + "," + sUpperSectionList;
                    } else {
                        sUpperSectionList = psDBBean.escDBString(sSectionCode);
                    }
                    sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, sUpperSectionList, 1000, true );
                    /* ▲ 2005/11/02 A.SUZUKI */
                    bOrFlag = true;
                }
                else if(psOperator.equals("<")) {
                    String sUpperSectionList = psDBBean.getV3Logic().getUpperSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), sSectionCode, psCreterialDate);
                    // 親組織がある時だけ作成する
                    if(!sUpperSectionList.equals("")) {
                        /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                        sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, sUpperSectionList, 1000, true );
                        /* ▲ 2005/11/02 A.SUZUKI */
                        bOrFlag = true;
                    }
                }
                else if(psOperator.equals(">=")) {
                    /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                    // 子組織があれば追加する
                    String sLowerSectionList = psDBBean.getV3Logic().getLowerSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), sSectionCode, psCreterialDate);
                    if(!sLowerSectionList.equals("")) {
                        sLowerSectionList = psDBBean.escDBString(sSectionCode) + "," + sLowerSectionList;
                    } else {
                        sLowerSectionList = psDBBean.escDBString(sSectionCode);
                    }
                    sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, sLowerSectionList, 1000, true );
                    /* ▲ 2005/11/02 A.SUZUKI */
                    bOrFlag = true;
                }
                else if(psOperator.equals(">")) {
                    String sLowerSectionList = psDBBean.getV3Logic().getLowerSectionListForSQL(psDBBean.getCustID(), psDBBean.getCompCode(), sSectionCode, psCreterialDate);
                    // 子組織があるときだけ作成する
                    if(!sLowerSectionList.equals("")) {
                        /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
                        sWhere += SectionsSeparateWhere( "##" + psTableID + "##." + psColumnID, sLowerSectionList, 1000, true );
                        /* ▲ 2005/11/02 A.SUZUKI */
                        bOrFlag = true;
                    }
                }
                // 最後以外で追加があったときはORで結合する
                if(vecSecPost.size() - 1 != i && bOrFlag) {
                    sWhere += " OR ";
                }
            }
            // 最後がORで終わっていたら削除する
            if(sWhere.lastIndexOf(" OR ") == sWhere.length() - 4 && sWhere.length() - 4 > 0) {
                sWhere = sWhere.substring(0, sWhere.length() - 4);
            }
            // 何も追加されていないときは何も検索させないために成り立たない式を追加する
            if(sWhere.lastIndexOf("(") == sWhere.length() - 1) {
                sWhere += "1 = 2)";
            }
            else {
                sWhere += ")";
            }
        }
        sWhere += psClosedParenthsis;
        return sWhere;
    }

    /**
     * 一般カラムの比較条件SQLを生成します
     * @param pvecColumnIDs
     * @param psAndOr
     * @param psOpenedParenthsis
     * @param psTableID
     * @param psColumnID
     * @param psOperator
     * @param psValue
     * @param psClosedParenthsis
     * @param psMyFlag
     * @param psCreterialDate
     * @return
     * @throws Exception
     */
    private String createNormalSQL(
            Vector pvecColumnIDs,
            String psAndOr,
            String psOpenedParenthsis,
            String psTableID,
            String psColumnID,
            String psTypeOfColumn,
            String psOperator,
            String psValue,
            String psClosedParenthsis,
            String psMyFlag,
            String psCreterialDate) throws Exception {

        String sWhere = "";
        sWhere +=
                psAndOr + " " + psOpenedParenthsis + " ##" + psTableID + "##." + psColumnID + " " + psOperator + " ";

        // 通常の比較の場合
        if(psMyFlag.equals("0")) {
            // 2005/09/30 Saito
            if(!psOperator.equalsIgnoreCase("IS NULL") && !psOperator.equalsIgnoreCase("IS NOT NULL")) {
                if(psTypeOfColumn.equalsIgnoreCase("VARCHAR2")) {
                    sWhere += psDBBean.escDBString(psValue);
                }
                else if(psTypeOfColumn.equalsIgnoreCase("NUMBER")) {
                    sWhere += SysUtil.transNumberNullToDB(psValue);
                }
                else if(psTypeOfColumn.equalsIgnoreCase("DATE")) {
                    sWhere += SysUtil.transDateNullToDB(psValue);
                }
                else {
                    sWhere += psDBBean.escDBString(psValue);
                }
            }
        }
        // 検索者自身の属性と比較の場合
        else {
            sWhere +=
                    "(SELECT "	+
                            psColumnID + " "	+
                            "FROM "	+
                            psTableID + " a "	+
                            "WHERE "	+
                            "a." + pvecColumnIDs.get(0) + " = " + psDBBean.escDBString(psDBBean.getCustID()) + " "	+
                            "AND a." + pvecColumnIDs.get(1) + " = " + psDBBean.escDBString(psDBBean.getCompCode()) + " "	+
                            "AND a." + pvecColumnIDs.get(2) + " = " + psDBBean.escDBString(psDBBean.getUserCode()) + " "	+
                            "AND a." + pvecColumnIDs.get(3) + " <= " + SysUtil.transDateNullToDB(psCreterialDate) + " "	+
                            "AND a." + pvecColumnIDs.get(4) + " >= " + SysUtil.transDateNullToDB(psCreterialDate) + " ";
            // 2006/03/15 Saito
            if(psTableID.equalsIgnoreCase("HIST_DESIGNATION")) {
                sWhere += "AND a.HD_CIFKEYORADDITIONALROLE = '0' ";
            }
            sWhere += ")";
        }
        sWhere += psClosedParenthsis;

        return sWhere;
    }

    /**
     * テーブルの結合条件SQLを生成します（汎用）
     * @param pvecColumnIDs
     * @return
     * @throws Exception
     */
    private String createJoinSql(Vector pvecColumnIDs, String psCreterialDate, int pnIndex) throws Exception {

        String sWhere = "";
        String sIndex = String.valueOf(pnIndex);
        sWhere +=
                "##HD##.HD_CCUSTOMERID_CK = ##" + sIndex + "##." + pvecColumnIDs.get(0) + " "	+
                        "AND ##HD##.HD_CCOMPANYID_CK = ##"  + sIndex + "##." + pvecColumnIDs.get(1) + " "	+
                        "AND ##HD##.HD_CEMPLOYEEID_CK = ##"  + sIndex + "##." + pvecColumnIDs.get(2) + " "	+
                        "AND ##"  + sIndex + "##." + pvecColumnIDs.get(3) + " <= " + SysUtil.transDateNullToDB(psCreterialDate) + " "	+
                        "AND ##"  + sIndex + "##." + pvecColumnIDs.get(4) + " >= " + SysUtil.transDateNullToDB(psCreterialDate);

        return sWhere;
    }

    /**
     * テーブルの結合条件SQLを生成します（特定テーブル用）
     * @param psTableID
     * @return
     * @throws Exception
     */
    private String createJoinSql(String psTableID, String psCreterialDate, int pnIndex) throws Exception {

        String sWhere = "";
        String sIndex = String.valueOf(pnIndex);
        if(psTableID.equals("MAST_POST")) {
            sWhere +=
                    "##HD2##.HD_CCUSTOMERID_CK = ##" + sIndex + "##.MAP_CCUSTOMERID_CK_FK "	+
                            "AND ##HD2##.HD_CCOMPANYID_CK = ##"  + sIndex + "##.MAP_CCOMPANYID_CK_FK "	+
                            "AND ##HD2##.HD_CPOSTID_FK = ##"  + sIndex + "##.MAP_CPOSTID_CK "	+
                            "AND MAP_CLANGUAGE = 'ja' "		+
                            "AND ##"  + sIndex + "##.MAP_DSTART <= " + SysUtil.transDateNullToDB(psCreterialDate) + " "	+
                            "AND ##"  + sIndex + "##.MAP_DEND >= " + SysUtil.transDateNullToDB(psCreterialDate);
        }

        return sWhere;
    }

    /**
     * 指定行が組織と役職の対応関係のある行か確認します
     * @param pvecSecPost
     * @param pnLineSeq
     * @return
     */
    private boolean checkSecPost(Vector pvecSecPost, int pnLineSeq) {
        for(int i = 0; i < pvecSecPost.size(); i++) {
            int nSectionLineSeq = Integer.parseInt((String)((Vector)pvecSecPost.elementAt(i)).elementAt(0));
            int nPostLineSeq = Integer.parseInt((String)((Vector)pvecSecPost.elementAt(i)).elementAt(6));

            if(pnLineSeq == nSectionLineSeq || pnLineSeq == nPostLineSeq) {
                return true;
            }
        }
        return false;
    }

    /**
     * 保存された条件式を読み込むためのSQL文を準備
     */
    private String buildSQLForSelectCondition() {
        // SQLを組み立てる
        return "SELECT " +
                "HTD_CANDOR, "				+
                "HTD_COPENEDPARENTHSIS, "	+
                "HTD_CCOLUMNNAME, "			+
                "HTD_COPERATOR, "			+
                "HTD_CDISPLAYVALUE, "		+
                "HTD_CCLOSEDPARENTHSIS, "	+
                "HTD_CTABLEID, "			+
                "HTD_CCOLUMNID, "			+
                "HTD_CTYPEOFCOLUMN, "		+
                "MD_CMASTERTBLNAME, "		+
                "HTD_CVALUE, "				+
                "HTD_CMYFLAG "				+
                "FROM " +
                "HIST_TARGETDEFINITIONS, "	+
                "MAST_DATADICTIONARY "		+
                "WHERE " +
                "HTD_CCUSTOMERID = '" + psDBBean.getCustID() + "' "	+
                "AND HTD_CCOMPANYID = '" + psDBBean.getCompCode() + "' "		+
                "AND HTD_CSYSTEMID = '" + psDBBean.getSystemCode() + "' "		+
                "AND HTD_CGROUPID = '" + psDBBean.getGroupID() + "' "			+
                "AND HTD_DSTARTDATE <= TRUNC(SYSDATE) "		+
                "AND HTD_DENDDATE >= TRUNC(SYSDATE) "		+
                "AND MD_CCUSTOMERID = HTD_CCUSTOMERID "					+
                "AND MD_CCOMPANYID = HTD_CCOMPANYID "					+
                "AND MD_CCOLUMNNAME = HTD_CCOLUMNID "					+
                "ORDER BY "	+
                "HTD_NSEQ";
    }

    /**
     * 検索者がグループに該当した時の所属、役職を取得します
     * @return
     */
    private Vector getGroupSecPost() throws Exception {
        return getGroupSecPost("TRUNC(SYSDATE)");
    }

    /**
     * 検索者がグループに該当した時の所属、役職を取得します
     * @return
     */
    public Vector getGroupSecPost(String psCreterialDate) throws Exception {
        Vector vecGroupSecPost = new Vector();
        // MAST_GROUPDEFINITIONSを読み込む
        Vector vecQuery = new Vector();
        vecQuery.add(buildSQLForSelectGroupDefinitions());
        psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
        if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
            throw new Exception();
        }

        // 該当するグループは１件のみの前提なので内側でpsResultを使い回しても大丈夫
        for(int i = 0; i < ((Vector)psResult.getResult().get(0)).size(); i++) {
            String sBaseFlag = valueAtColumnRow(0, COL_MGP_BASEFLAG, i);
            String sQuery = valueAtColumnRow(0, COL_MGP_CQUERY, i);
            // MAST_GROUPSECTIONPOSTMAPPINGで定義の場合
            if(sBaseFlag.equals("0")) {
                // 該当する所属・役職の組み合わせを取得する
                vecQuery.clear();
                vecQuery.add(buildSQLForSelectGroupSecPost(psCreterialDate));
                psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
                if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
                    throw new Exception();
                }
                for(int j = 0; j < ((Vector)psResult.getResult().get(0)).size(); j++) {
                    Vector vecRow = new Vector();
                    String sSectionID = valueAtColumnRow(0, COL_HD_CSECTIONID_FK, j);
                    String sPostID = valueAtColumnRow(0, COL_HD_CPOSTID_FK, j);
                    vecRow.add(sSectionID);
                    vecRow.add(sPostID);
                    vecGroupSecPost.add(vecRow);
                }
                // 間違って２件以上あったときにエラーにならないようにループを終了する
                break;
            }
            // MAST_GROUPDEFINITIONSで定義の場合
            else {
                // 該当する所属・役職の組み合わせを取得する
                vecQuery.clear();
                vecQuery.add(buildSQLForSelectGroupDefinitionsQuery(sQuery, psCreterialDate));
                psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
                if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
                    throw new Exception();
                }
                for(int j = 0; j < ((Vector)psResult.getResult().get(0)).size(); j++) {
                    Vector vecRow = new Vector();
                    String sSectionID = valueAtColumnRow(0, COL_HD_CSECTIONID_FK, j);
                    String sPostID = valueAtColumnRow(0, COL_HD_CPOSTID_FK, j);
                    vecRow.add(sSectionID);
                    vecRow.add(sPostID);
                    vecGroupSecPost.add(vecRow);
                }
                // 間違って２件以上あったときにエラーにならないようにループを終了する
                break;
            }
        }
        return vecGroupSecPost;
    }

    /**
     * グループ定義を読み込むためのSQL文を準備
     */
    private String buildSQLForSelectGroupDefinitions() {
        // SQLを組み立てる
        return "SELECT " +
                "MGP_BASEFLAG, "				+
                "MGP_CQUERY "	+
                "FROM " +
                "MAST_GROUPDEFINITIONS "	+
                "WHERE " +
                "MGP_CCUSTOMERID_CK_FK = " + psDBBean.escDBString(psDBBean.getCustID()) + " "		+
                "AND MGP_CCOMPANYID_CK_FK = " + psDBBean.escDBString(psDBBean.getCompCode()) + " "	+
                "AND MGP_CSYSTEMID_CK = " + psDBBean.escDBString(psDBBean.getSystemCode()) + " "		+
                "AND MGP_CGROUPID_CK_FK = " + psDBBean.escDBString(psDBBean.getGroupID()) + " "		+
                "AND MGP_DSTARTDATE <= TRUNC(SYSDATE) "								+
                "AND MGP_DENDDATE >= TRUNC(SYSDATE) ";
    }

    /**
     * 該当する所属、役職を検索するためのSQL文を準備(MAST_GROUPSECTIONPOSTMAPPING)
     */
    private String buildSQLForSelectGroupSecPost(String psCreterialDate) {
        if(!psCreterialDate.equals("TRUNC(SYSDATE)")) {
            psCreterialDate = SysUtil.transDateNullToDB(psCreterialDate);
        }
        // SQLを組み立てる
        String sSql =
                "SELECT DISTINCT " +
                        "HD_CSECTIONID_FK, "	+
                        "HD_CPOSTID_FK "		+
                        "FROM " +
                        "MAST_GROUPSECTIONPOSTMAPPING, "	+
                        "HIST_DESIGNATION "					+
                        "WHERE " +
                        "MAG_CCUSTOMERID_CK_FK = " + psDBBean.escDBString(psDBBean.getCustID()) + " "		+
                        "AND MAG_CCOMPANYID_CK_FK = " + psDBBean.escDBString(psDBBean.getCompCode()) + " "	+
                        "AND MAG_CSYSTEMID_CK = " + psDBBean.escDBString(psDBBean.getSystemCode()) + " "		+
                        "AND MAG_CGROUPID_FK = " + psDBBean.escDBString(psDBBean.getGroupID()) + " "			+
                        "AND MAG_DSTARTDATE_CK <= TRUNC(SYSDATE) "							+
                        "AND MAG_DENDDATE >= TRUNC(SYSDATE) "								+
                        "AND HD_CCUSTOMERID_CK = MAG_CCUSTOMERID_CK_FK "					+
                        "AND HD_CCOMPANYID_CK = MAG_CCOMPANYID_CK_FK "						+
                        "AND HD_CEMPLOYEEID_CK = " + psDBBean.escDBString(psDBBean.getUserCode())	+ " "		+
                        "AND HD_DSTARTDATE_CK <= " + psCreterialDate + " "		+
                        "AND HD_DENDDATE >= " + psCreterialDate + " ";
        return sSql;
    }

    /**
     * 該当する所属、役職を検索するためのSQL文を準備(MAST_GROUPDEFINITIONS)
     */
    private String buildSQLForSelectGroupDefinitionsQuery(String psQuery, String psCreterialDate) {
        // SQLを組み立てる
        String sSql = psQuery;
        int nFromPoint = sSql.toUpperCase().indexOf("FROM");
        sSql = "SELECT " +
                "HD_CSECTIONID_FK, "	+
                "HD_CPOSTID_FK "		+
                sSql.substring(nFromPoint)	+
                " AND HD_CEMPLOYEEID_CK = " + psDBBean.escDBString(
                        psDBBean.getUserCode()) +
                // 2005/09/22 Saito 仮想異動歴は検索対象としない
                " AND HD_NOFFCIALORNOT = '0'";
        if(!psCreterialDate.equals("TRUNC(SYSDATE)")) {
            sSql = SysUtil.replaceString(sSql, "TRUNC(SYSDATE)", SysUtil.transDateNullToDB(psCreterialDate));
        }
        return sSql;
    }

    // テーブル結合用カラム名情報の取得
    private Vector getColumnIDs(String psTableID) throws Exception {
        Vector vecColumnIDs = new Vector();

        // 検索を実行し、結果を取得
        Vector vecQuery = new Vector();

        // 顧客コードカラムIDの取得
        vecQuery.add(buildSQLForSelectColumnIDs(psTableID, "CCUSTOMERID"));
        psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
        if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
            throw new Exception();
        }
        if(((Vector)psResult.getResult().get(0)).size() > 0) {
            vecColumnIDs.add(valueAtColumnRow(0, 0, 0));
        }
        else {
            vecColumnIDs.add(null);
        }

        // 会社コードカラムIDの取得
        vecQuery.clear();
        vecQuery.add(buildSQLForSelectColumnIDs(psTableID, "CCOMPANYID"));
        psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
        if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
            throw new Exception();
        }
        if(((Vector)psResult.getResult().get(0)).size() > 0) {
            vecColumnIDs.add(valueAtColumnRow(0, 0, 0));
        }
        else {
            vecColumnIDs.add(null);
        }

        // 社員番号カラムIDの取得
        vecQuery.clear();
        vecQuery.add(buildSQLForSelectColumnIDs(psTableID, "CEMPLOYEEID"));
        psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
        if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
            throw new Exception();
        }
        if(((Vector)psResult.getResult().get(0)).size() > 0) {
            vecColumnIDs.add(valueAtColumnRow(0, 0, 0));
        }
        else {
            vecColumnIDs.add(null);
        }

        // 開始日カラムIDの取得
        // DSTARTDATEを含むカラムがあればそれを使う
        vecQuery.clear();
        vecQuery.add(buildSQLForSelectColumnIDs(psTableID, "DSTARTDATE"));
        psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
        if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
            throw new Exception();
        }
        if(((Vector)psResult.getResult().get(0)).size() > 0) {
            vecColumnIDs.add(valueAtColumnRow(0, 0, 0));
        }
        // DSTARTDATEを含むカラムがなければDSTARTを含むカラムを使う
        else {
            vecQuery.clear();
            vecQuery.add(buildSQLForSelectColumnIDs(psTableID, "DSTART"));
            psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
            if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
                throw new Exception();
            }
            if(((Vector)psResult.getResult().get(0)).size() > 0) {
                vecColumnIDs.add(valueAtColumnRow(0, 0, 0));
            }
            else {
                vecColumnIDs.add(null);
            }
        }
        // 終了日カラムIDの取得
        // DENDDATEを含むカラムがあればそれを使う
        vecQuery.clear();
        vecQuery.add(buildSQLForSelectColumnIDs(psTableID, "DENDDATE"));
        psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
        if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
            throw new Exception();
        }
        if(((Vector)psResult.getResult().get(0)).size() > 0) {
            vecColumnIDs.add(valueAtColumnRow(0, 0, 0));
        }
        // DENDDATEを含むカラムがなければDENDを含むカラムを使う
        else {
            vecQuery.clear();
            vecQuery.add(buildSQLForSelectColumnIDs(psTableID, "DEND"));
            psResult = getValuesforMultiquery(vecQuery, BEAN_DESC);
            if(psResult.getException().size() > 0 && !psResult.getException().get(0).equals("")) {
                throw new Exception();
            }
            if(((Vector)psResult.getResult().get(0)).size() > 0) {
                vecColumnIDs.add(valueAtColumnRow(0, 0, 0));
            }
            else {
                vecColumnIDs.add(null);
            }
        }

        return vecColumnIDs;
    }

    // テーブル結合用カラム名情報を検索するSQLを組み立てます
    private String buildSQLForSelectColumnIDs(String psTableID, String psKeyWord) {
        return "SELECT " +
                "MD_CCOLUMNNAME "	+
                "FROM " +
                "MAST_DATADICTIONARY " +
                "WHERE " +
                "MD_CCUSTOMERID = " + psDBBean.escDBString(psDBBean.getCustID()) + " "	+
                "AND MD_CCOMPANYID = " + psDBBean.escDBString(psDBBean.getCompCode()) + " "	+
                "AND MD_CTABLENAME = '" + psTableID + "' "			+
                "AND MD_CCOLUMNNAME LIKE '%"	+ psKeyWord + "%' ";
    }

    /**
     * 検索対象範囲設定SQL組み立て処理
     * @return 検索対象範囲設定SQL(From句)
     */
/*
	public String getTargetSqlFrom() {
		return gsTargetSqlFrom;
	}
*/
    /**
     * 検索対象範囲設定SQL組み立て処理
     * @return 検索対象範囲設定SQL(From句)
     */
    public Vector getTargetSqlFroms() {
        return gvecTargetSqlFroms;
    }

    /**
     * 検索対象範囲設定SQL組み立て処理
     * @return 検索対象範囲設定SQL(Where<結合式用>句)
     */
    public Vector getTargetSqlWheres() {
        return gvecTargetSqlWheres;
    }

    /**
     * 検索対象範囲設定SQL組み立て処理
     * @return 検索対象範囲設定SQL(Where<条件式用>句)
     */
    public String getTargetSqlWhere() {
        return gsTargetSqlWhere;
    }
    /* ▼ 2005/11/02 A.SUZUKI 検索対象範囲設定・部門人事にて参照所属が1000を超える場合にOracle制限でエラー発生を回避(Q&A1173,1226,1303) */
    /**
     * 指定カラムに対する条件値をINの形式で表すSQLの一部を返します<BR>
     * Oracleの制約によりINに1001以上の値を指定するとエラーが発生する現象の対応<BR>
     * @return	String	SQLのWHERE句IN
     */
    private String SectionsSeparateWhere(String psSQLColumn, String psSections, int pnSeparatePartCount, boolean pbEqual ) {

        /* Error Check */
        if( psSQLColumn.getBytes().length == 0 ) {
            return "";
        }
        if( psSections.getBytes().length == 0 ) {
            return "";
        }

        /* Value Separate ＆ Unique　*/
        CSVTokenizer	SeparatePart	= new CSVTokenizer( psSections );
        HashMap mapSeparatePart	= new HashMap();
        Vector			vecSeparatePart	= new Vector();
        int				nItemCount		= 0;

        while ( SeparatePart.hasMoreTokens() ) {
            String	sSeparatePart	=	SeparatePart.nextToken();
            if ( mapSeparatePart.containsKey( sSeparatePart ) == false ) {
                vecSeparatePart.add( sSeparatePart );
                mapSeparatePart.put( sSeparatePart, sSeparatePart );
                nItemCount++;
            }
        }

        /* SQL Separate */
        StringBuffer	sbWhere	= new StringBuffer();
        int				i		= 0;
        while ( i < nItemCount ) {
            String	sSeparatePart	=	new String();
            int		j				=	0;
            while ( i < nItemCount && j < pnSeparatePartCount ) {
                if ( j == 0 ) {
                    sSeparatePart += (String)vecSeparatePart.get( i );
                } else {
                    sSeparatePart += "," + (String)vecSeparatePart.get( i );
                }
                i++;
                j++;
            }

            /* SQL Molding */
            if ( pbEqual ) {
                if ( sbWhere.length() == 0 ) {
                    sbWhere.append(          psSQLColumn + " IN (" + sSeparatePart + ") " );
                }else {
                    sbWhere.append( " OR " + psSQLColumn + " IN (" + sSeparatePart + ") " );
                }
            } else {
                if ( sbWhere.length() == 0 ) {
                    sbWhere.append(          psSQLColumn + " NOT IN (" + sSeparatePart + ") " );
                }else {
                    sbWhere.append( " OR " + psSQLColumn + " NOT IN (" + sSeparatePart + ") " );
                }
            }
        }
        return " ( " + sbWhere.toString() + " ) ";
    }
    /* ▲ 2005/11/02 A.SUZUKI */

    // ▼▼▼ 2005/11/30 Saito
    private String trimSpace(String psString) {
        String sString = "";
        for(int i = 0; i < psString.length(); i++) {
            if(psString.charAt(i) != ' ') {
                // 2006/03/15 Saito
                sString = psString.substring(i);
                break;
            }
        }
        return sString;
    }
    // ▲▲▲ 2005/11/30 Saito



    /**
     * ------------------------------------------
     * ============== DB操作公共方法 ==============
     * ------------------------------------------
     */
    public String valueAtColumnRow(int queryindex,int column,int row )
            throws ArrayIndexOutOfBoundsException,Exception
    {
        Vector retvec = null;
        retvec = psResult.getException();
        try
        {
            if (retvec.size() != 0 && !retvec.get(queryindex).equals(""))
            {
                String errmsg = (String)retvec.get(queryindex);
                throw new Exception(errmsg);
            }
            retvec = psResult.getResult();
            return (String)((Vector)((Vector)retvec.get(queryindex)).get(row)).get(column);
        }
        catch(Exception e)
        {
        }
        return null;
    }

    /**
     * １つ以上のSELECT文をセキュリティ判定なしで実行します。
     * @param vecQuery	   SELECT文のVector
     * @param strBeanDesc ログ出力用Bean識別子
     * @return PsResult   SQLの実行結果
     * @throws Exception  システム例外
     */
    public PsResult getValuesforMultiquery(Vector vecQuery, String strBeanDesc)
            throws Exception {
        try {
            if (vecQuery != null && strBeanDesc != null) {
                return psDBBean.getV3Logic().executeMultiQuery(
                        vecQuery, psDBBean.getCustID(),
                        psDBBean.getCompCode(), psDBBean.getUserCode(),
                        psDBBean.getGroupID(), 	strBeanDesc,
                        psDBBean.getSystemCode(), psDBBean.getStrGUID(),
                        false);
            } else {
                throw new Exception(
                        SysUtil.getpropertyvalue(
                                psDBBean.getLanguage(), "ErrorCode_25"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public Object valueAtColumnRow(Vector result,int column,int row ) throws ArrayIndexOutOfBoundsException
    {
        int vnest = 0;
        Object odummy = result;
        while(true){
            if(odummy instanceof Vector == true){
                vnest++;
                if(((Vector)odummy).size() > 0){
                    odummy = ((Vector)odummy).get(0);
                }else{
                    break;
                }
            }else{
                break;
            }
        }
        System.out.println("odummy:" + vnest);
        if(vnest == 3){
            //result = (Vector)result.get(0);
        }
        int realRow = row + 1;
        if (result == null) {
            throw new ArrayIndexOutOfBoundsException ("Result set is empty.");
        }

        if (realRow > result.size()) {
            throw new ArrayIndexOutOfBoundsException ("Row is out of bounds.");
        }
        try
        {
            return ((Vector)result.get(row)).get(column);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }//end of valueAtColumnRow()................

}
