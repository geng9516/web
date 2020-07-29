package jp.smartcompany.job.modules.tmg.evaluatersetting;

import jp.smartcompany.boot.util.SysUtil;

public class EvaluatorSettingBean {

    /**
     * 画面遷移リンク用基準日取得クエリ
     *
     * @param params
     * @return String SQL
     */
    public static String buildSQLForTransitionDate(EvaluatorSettingParam params) {

        // 検索条件に使用するパラメータを準備
        String sDBCustId   = escDBString(params.getCustomerId()); // 顧客コード
        String sDBCompId   = escDBString(params.getCompanyId());  // 法人コード
        String sDBSecId    = escDBString(params.getSection());    // 組織コード
        String sDBBaseDate = SysUtil.transDateNullToDB(params.getYYYYMMDD());      // 基準日

        StringBuilder sbSQL = new StringBuilder();

        sbSQL.append(" SELECT ");
        sbSQL.append("     ( "); // 指定日付より前の最大の開始日
        sbSQL.append("         SELECT TO_CHAR(MAX(STARTDATE), " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE ");
        sbSQL.append("         FROM ( " );
        sbSQL.append("               SELECT TGR_DSTARTDATE AS STARTDATE ");
        sbSQL.append("               FROM   TMG_GROUP");
        sbSQL.append("               WHERE  TGR_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("               AND    TGR_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("               AND    TGR_CSECTIONID  = " + sDBSecId);
        sbSQL.append("               UNION ");
        sbSQL.append("               SELECT TGRM_DSTARTDATE AS STARTDATE ");
        sbSQL.append("               FROM   TMG_GROUP_MEMBER " );
        sbSQL.append("               WHERE  TGRM_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("               AND    TGRM_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("               AND    TGRM_CSECTIONID  = " + sDBSecId);
        sbSQL.append("               UNION ");
        sbSQL.append("               SELECT TEV_DSTARTDATE AS STARTDATE ");
        sbSQL.append("               FROM   TMG_EVALUATER ");
        sbSQL.append("               WHERE  TEV_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("               AND    TEV_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("               AND    TEV_CSECTIONID  = " + sDBSecId);
        sbSQL.append("              ) ");
        sbSQL.append("         WHERE ");
        sbSQL.append("             STARTDATE = ");
        sbSQL.append("                 ( "); // TRUNC(SYSDATE)時点で有効な歴の開始日
        sbSQL.append("                  SELECT MAX(STARTDATE) AS STARTDATE ");
        sbSQL.append("                  FROM (");
        sbSQL.append("                        SELECT TGR_DSTARTDATE AS STARTDATE ");
        sbSQL.append("                        FROM   TMG_GROUP ");
        sbSQL.append("                        WHERE  TGR_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("                        AND    TGR_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("                        AND    TGR_CSECTIONID  = " + sDBSecId);
        sbSQL.append("                        UNION ");
        sbSQL.append("                        SELECT TGRM_DSTARTDATE AS STARTDATE ");
        sbSQL.append("                        FROM   TMG_GROUP_MEMBER ");
        sbSQL.append("                        WHERE  TGRM_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("                        AND    TGRM_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("                        AND    TGRM_CSECTIONID  = " + sDBSecId);
        sbSQL.append("                        UNION ");
        sbSQL.append("                        SELECT TEV_DSTARTDATE AS STARTDATE ");
        sbSQL.append("                        FROM   TMG_EVALUATER ");
        sbSQL.append("                        WHERE  TEV_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("                        AND    TEV_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("                        AND    TEV_CSECTIONID  = " + sDBSecId);
        sbSQL.append("                       ) ");
        sbSQL.append("                  WHERE ");
        sbSQL.append("                      STARTDATE < " + sDBBaseDate);
        sbSQL.append("                 )  ");
        sbSQL.append("     ), ");

        sbSQL.append("     ( "); // TRUNC(SYSDATE)時点で有効な歴の開始日
        sbSQL.append("         SELECT TO_CHAR(MAX(STARTDATE), " + SysUtil.transStringNullToDB(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE ");
        sbSQL.append("         FROM ( ");
        sbSQL.append("               SELECT TGR_DSTARTDATE AS STARTDATE ");
        sbSQL.append("               FROM   TMG_GROUP ");
        sbSQL.append("               WHERE  TGR_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("               AND    TGR_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("               AND    TGR_CSECTIONID  = " + sDBSecId);
        sbSQL.append("               UNION ");
        sbSQL.append("               SELECT TGRM_DSTARTDATE AS STARTDATE ");
        sbSQL.append("               FROM   TMG_GROUP_MEMBER ");
        sbSQL.append("               WHERE  TGRM_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("               AND    TGRM_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("               AND    TGRM_CSECTIONID  = " + sDBSecId);
        sbSQL.append("               UNION ");
        sbSQL.append("               SELECT TEV_DSTARTDATE AS STARTDATE ");
        sbSQL.append("               FROM   TMG_EVALUATER ");
        sbSQL.append("               WHERE  TEV_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("               AND    TEV_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("               AND    TEV_CSECTIONID  = " + sDBSecId);
        sbSQL.append("              ) ");
        sbSQL.append("          WHERE ");
        sbSQL.append("              STARTDATE <= TRUNC(SYSDATE) ");
        sbSQL.append("     ), ");

        sbSQL.append("     ( "); // 指定日付より後の最小の開始日
        sbSQL.append("         SELECT TO_CHAR(MIN(STARTDATE), " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE ");
        sbSQL.append("         FROM ( ");
        sbSQL.append("                SELECT TGR_DSTARTDATE AS STARTDATE ");
        sbSQL.append("                FROM   TMG_GROUP ");
        sbSQL.append("                WHERE  TGR_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("                AND    TGR_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("                AND    TGR_CSECTIONID  = " + sDBSecId);
        sbSQL.append("                UNION ");
        sbSQL.append("                SELECT TGRM_DSTARTDATE AS STARTDATE ");
        sbSQL.append("                FROM   TMG_GROUP_MEMBER ");
        sbSQL.append("                WHERE  TGRM_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("                AND    TGRM_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("                AND    TGRM_CSECTIONID  = " + sDBSecId);
        sbSQL.append("                UNION ");
        sbSQL.append("                SELECT TEV_DSTARTDATE AS STARTDATE ");
        sbSQL.append("                FROM   TMG_EVALUATER ");
        sbSQL.append("                WHERE  TEV_CCUSTOMERID = " + sDBCustId);
        sbSQL.append("                AND    TEV_CCOMPANYID  = " + sDBCompId);
        sbSQL.append("                AND    TEV_CSECTIONID  = " + sDBSecId);
        sbSQL.append("              ) ");
        sbSQL.append("         WHERE ");
        sbSQL.append("             STARTDATE > " + sDBBaseDate);
        sbSQL.append("     ) ");
        sbSQL.append(" FROM ");
        sbSQL.append("     DUAL ");

        return sbSQL.toString();
    }

    public static String escDBString(String sString) {
        return SysUtil.transStringNullToDB(SysUtil.escapeQuote(sString));
    }

}
