package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityHandler;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class TmgEmpList {

    /**
     * 検索結果をJSONへマッピングする際のキー文字列(この文字列をキーとした連想配列が作成される)
     */
    public static final String[] DEFAULT_KEY_ARRAY = {
            "level","label","cust","comp","empid","empname","secid","secnic",
            "postid","postname","workertypeid","wokertypename","dstart","dend","seq",
            "defaultapplevel"
    };

    public static final int DEFAULT_KEY_LEVEL          = 0;
    public static final int DEFAULT_KEY_LABEL          = 1;
    public static final int DEFAULT_KEY_CUST           = 2;
    public static final int DEFAULT_KEY_COMP           = 3;
    public static final int DEFAULT_KEY_EMPID          = 4;
    public static final int DEFAULT_KEY_EMPNAME        = 5;
    public static final int DEFAULT_KEY_SECID          = 6;
    public static final int DEFAULT_KEY_SECNIC         = 7;
    public static final int DEFAULT_KEY_POSTID         = 8;
    public static final int DEFAULT_KEY_POSTNAME       = 9;
    public static final int DEFAULT_KEY_WORKERTYPEID   = 10;
    public static final int DEFAULT_KEY_WORKERTYPENAME = 11;
    public static final int DEFAULT_KEY_DSTART         = 12;
    public static final int DEFAULT_KEY_DEND           = 13;
    public static final int DEFAULT_KEY_SEQ            = 14;
    public static final int DEFAULT_KEY_DEFAULT_APPLEVEL = 15;

    private PsDBBean bean;
    private String beanDesc = null;
    private List dataArray = null;
    private List gvSearchDataArray = null;
    private String[] keyArray;

    /** 検索対象範囲設定を考慮するかどうか */
    private boolean withTarget = true;

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
    private String dateFormat;

    private final DataSource dataSource = SpringUtil.getBean("dataSource");
    private final TmgSearchRangeUtil tmgSearchRangeUtil = SpringUtil.getBean(TmgSearchRangeUtil.class);
    /**
     * コンストラクタ
     * @param bean
     */
    public TmgEmpList(PsDBBean bean) {
        this.bean = bean;
        this.keyArray = DEFAULT_KEY_ARRAY;
        this.dateFormat = DEFAULT_DATE_FORMAT;
    }

    /**
     * コンストラクタ
     * @param bean
     * @param beanDesc
     */
    public TmgEmpList(PsDBBean bean, String beanDesc) {
        this.bean = bean;
        this.beanDesc = beanDesc;
        this.keyArray = DEFAULT_KEY_ARRAY;
        this.dateFormat = DEFAULT_DATE_FORMAT;
    }
    /**
     * コンストラクタ
     * @param bean
     * @param beanDesc
     * @param bWithTarget	検索対象範囲設定を考慮するかどうか（true: 条件に含む、false: 条件に含まない→全件）
     */
    public TmgEmpList(PsDBBean bean, String beanDesc, boolean bWithTarget) {
        this.bean = bean;
        this.beanDesc = beanDesc;
        this.keyArray = DEFAULT_KEY_ARRAY;
        dateFormat = DEFAULT_DATE_FORMAT;
        this.withTarget = bWithTarget;
    }

    /**
     * 組織ツリータブ用
     * 指定された条件で、参照可能な職員一覧を作成します。
     * 検索結果は、二次元ArrayListの形式で保持されます。
     * ※このメソッドは、セキュリティ判定無しの検索を行います。
     * @param cust
     * @param comp
     * @param section
     * @param targetStartDate
     * @param targetEndDate
     * @param language
     * @throws Exception
     */
    public void createEmpList(String cust, String comp, String section, String targetStartDate,
                              String targetEndDate, String language, boolean ifKyeOrAdditionalRole,
                              boolean isJoinTmgEmployees, boolean useManageFLG //管理対象外を表示するか
    ) throws Exception{

        createTreeEmpList(cust, comp, section, targetStartDate, targetEndDate,
                language, ifKyeOrAdditionalRole, isJoinTmgEmployees, useManageFLG
        );

    }

    /**
     * 組織ツリー検索タブ用
     * 指定された条件で、参照可能な職員一覧を作成します。
     * 検索結果は、二次元ArrayListの形式で保持されます。
     * ※このメソッドは、セキュリティ判定無しの検索を行います。
     * @param cust
     * @param comp
     * @param section
     * @param targetStartDate
     * @param targetEndDate
     * @param language
     * @throws Exception
     */
    public void createEmpList(String cust, String comp, String section, String targetStartDate,
                              String targetEndDate, String language, boolean ifKyeOrAdditionalRole,
                              boolean isJoinTmgEmployees, boolean useManageFLG, String psSearchItems,
                              String psSearchCondition, String psSearchData
    ) throws Exception{

        createSearchEmpList(cust, comp, section, targetStartDate, targetEndDate,
                language, ifKyeOrAdditionalRole, isJoinTmgEmployees, useManageFLG,
                psSearchItems, psSearchCondition, psSearchData
        );

        setDispLimit4Tree(getMsgDispLimit4Tree(targetStartDate));

    }

    /**
     * 組織ツリータブ用
     * 指定された条件で、参照可能な職員一覧を作成します。
     * 検索結果は、二次元ArrayListの形式で保持されます。
     * ※このメソッドは、セキュリティ判定無しの検索を行います。
     * @param cust
     * @param comp
     * @param section
     * @param targetStartDate
     * @param targetEndDate
     * @param language
     * @throws Exception
     */
    private void createTreeEmpList(String cust, String comp, String section, String targetStartDate,
                                   String targetEndDate, String language, boolean ifKyeOrAdditionalRole, boolean isJoinTmgEmployees,
                                   boolean useManageFLG
    ) throws Exception{

        String sSQL = buildSQLForSelectEmpList(cust, comp, section, targetStartDate, targetEndDate,
                language, ifKyeOrAdditionalRole, isJoinTmgEmployees, useManageFLG, null, null, null);
        log.info("【createTreeEmpList_SQL1：{}】",sSQL);
        try (Connection connection = dataSource.getConnection()) {
            List entityList = SqlExecutor.query(connection,sSQL ,new EntityListHandler());
            dataArray = JSONArrayGenerator.entityListTowardList(entityList);
//            log.debug("empList结果:{}",entityList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        log.debug("【empList的dataArray：{}】",dataArray);
     }

    /**
     * 組織ツリー検索タブ用
     * 指定された条件で、参照可能な職員一覧を作成します。
     * 検索結果は、二次元ArrayListの形式で保持されます。
     * ※このメソッドは、セキュリティ判定無しの検索を行います。
     * @param cust
     * @param comp
     * @param section
     * @param targetStartDate
     * @param targetEndDate
     * @param language
     * @throws Exception
     */
    private void createSearchEmpList(String cust, String comp, String section, String targetStartDate,
                                     String targetEndDate, String language, boolean ifKyeOrAdditionalRole, boolean isJoinTmgEmployees,
                                     boolean useManageFLG, String psSearchItems, String psSearchCondition, String psSearchData
    ) throws Exception{
        String sSQL = buildSQLForSelectEmpList(cust, comp, section, targetStartDate, targetEndDate,
                language, ifKyeOrAdditionalRole, isJoinTmgEmployees, useManageFLG, psSearchItems,psSearchCondition, psSearchData);
        log.info("createSearchEmpList_SQL2：{}",sSQL);
        try (Connection connection = dataSource.getConnection()){
            List entityList  = SqlExecutor.query(connection,sSQL ,new EntityListHandler());
            setSearchDataArray(JSONArrayGenerator.entityListTowardList(entityList));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    /**
     *
     * @param cust
     * @param comp
     * @param ward
     * @param targetDate
     * @param language
     * @throws Exception
     */
    public void createWardEmpList(
            String cust,
            String comp,
            String ward,
            String targetDate,
            String language,
            boolean ifKeyOrAdditionalRole
    ) throws Exception{
        String sSQL = buildSQLForSelectWardEmpList(cust, comp, ward, targetDate, language, ifKeyOrAdditionalRole);
        log.info("createWardEmpList_SQL3：{}",sSQL);
        try ( Connection connection = dataSource.getConnection()){
            List entityList  = SqlExecutor.query(connection,sSQL ,new EntityListHandler());
            dataArray = JSONArrayGenerator.entityListTowardList(entityList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定された条件で、DBから職員一覧を検索するSQLを構築して返します。
     * @param cust
     * @param comp
     * @param section
     * @param targetStartDate
     * @param targetEndDate
     * @param language
     * @return
     */
    public String buildSQLForSelectEmpList(String cust, String comp, String section,
                                           String targetStartDate, String targetEndDate, String language, boolean ifKyeOrAdditionalRole,
                                           boolean isJoinTmgEmployees, boolean useManageFLG, String psSearchItems, String psSearchCondition,
                                           String psSearchData
    ){
        StringBuilder sSQL = new StringBuilder();
        String layeredSection = "";

//        検索対象範囲の適用
        if (this.withTarget) {
            try {
                layeredSection = tmgSearchRangeUtil.getExistsQueryOrganisation(bean, Objects.requireNonNull(ContextUtil.getHttpRequest()).getSession(), "o.MO_CLAYEREDSECTIONID");
            }
            catch(Exception e) {
                layeredSection = "";
                e.printStackTrace(); // 念の為
            }
        }
        sSQL.append(" SELECT  ");
        sSQL.append(    " 0 as MO_NLEVEL");

        // SYSDATE時点で所属している職員については、「(部署名/役職名)」を表示する
        sSQL.append(    ",CASE ");
        sSQL.append(        " WHEN HD_DENDDATE >= " + targetEndDate + " THEN ");
        sSQL.append(            " ME_CKANJINAME || ' (' || MO_CSECTIONNICK || '/' || MAP_CPOSTNAME || ')' ");
        sSQL.append(        " ELSE ");
        sSQL.append(            " ME_CKANJINAME || '( * )' ");
        sSQL.append(    " END");

        sSQL.append(    ",HD_CCUSTOMERID_CK");
        sSQL.append(    ",HD_CCOMPANYID_CK");
        sSQL.append(    ",HD_CEMPLOYEEID_CK");
        sSQL.append(    ",ME_CKANJINAME");
        sSQL.append(    ",HD_CSECTIONID_FK");
        sSQL.append(    ",MO_CSECTIONNICK");
        sSQL.append(    ",HD_CPOSTID_FK");
        sSQL.append(    ",MAP_CPOSTNAME");
        sSQL.append(    ",TEM_CWORKTYPEID");
        sSQL.append(    ",TEM_CWORKTYPENAME");
        sSQL.append(    ",to_char(HD_DSTARTDATE_CK,'YYYY/MM/DD')");
        sSQL.append(    ",to_char(HD_DENDDATE,'YYYY/MM/DD')");
        sSQL.append(    ",ROWNUM AS SEQ ");
        sSQL.append(    ",TMG_F_GET_DEFAULT_APPLEVEL( ");
        sSQL.append(    "                             HD_CEMPLOYEEID_CK, ");
        sSQL.append(    "                             " + targetEndDate + ",");
        sSQL.append(    "                             HD_CCUSTOMERID_CK, ");
        sSQL.append(    "                             HD_CCOMPANYID_CK, ");
        sSQL.append(    "                             " + language);
        sSQL.append(    "                           ) AS DEFAULT_APPLEVEL ");
        sSQL.append(" FROM ");

        if (psSearchData != null){
            sSQL.append(" (");
            sSQL.append(   " SELECT ");
            sSQL.append(       " * ");
            sSQL.append(   " FROM ");
        }

        sSQL.append(" (");

        sSQL.append(" SELECT ");
        sSQL.append(    " d.HD_CCUSTOMERID_CK");
        sSQL.append(    ",d.HD_CCOMPANYID_CK");
        sSQL.append(    ",d.HD_CEMPLOYEEID_CK");
        sSQL.append(    ",TMG_F_GET_ME_NAME(d.HD_CEMPLOYEEID_CK, " + targetEndDate + ", 0, d.HD_CCUSTOMERID_CK, d.HD_CCOMPANYID_CK) as ME_CKANJINAME");
        sSQL.append(    ",d.HD_CSECTIONID_FK");
        sSQL.append(    ",TMG_F_GET_MO(d.HD_CSECTIONID_FK, " + targetEndDate + ",1,d.HD_CCUSTOMERID_CK,d.HD_CCOMPANYID_CK,"+language+") as MO_CSECTIONNICK");
        sSQL.append(    ",d.HD_CPOSTID_FK");
        sSQL.append(    ",TMG_F_GET_MP(d.HD_CPOSTID_FK, " + targetEndDate + ",d.HD_CCUSTOMERID_CK,d.HD_CCOMPANYID_CK,"+language+") as MAP_CPOSTNAME");
        sSQL.append(    ",e.TEM_CWORKTYPEID");
        sSQL.append(    ",TMG_F_GET_MGD(e.TEM_CWORKTYPEID, " + targetEndDate + ",e.TEM_CCUSTOMERID,e.TEM_CCOMPANYID,"+language+") as TEM_CWORKTYPENAME");
        sSQL.append(    ",trunc(d.HD_DSTARTDATE_CK) as HD_DSTARTDATE_CK");
        sSQL.append(    ",trunc(d.HD_DENDDATE) as HD_DENDDATE ");
        sSQL.append(    ",TMG_F_GET_ME_NAME(d.HD_CEMPLOYEEID_CK, " + targetEndDate + ", 1, d.HD_CCUSTOMERID_CK, d.HD_CCOMPANYID_CK) as ME_CKANANAME ");

        sSQL.append(" FROM ");
        sSQL.append(    " HIST_DESIGNATION d");
        sSQL.append(    ",TMG_EMPLOYEES e ");
        if (StrUtil.isNotBlank(layeredSection)) {
            sSQL.append(", MAST_ORGANISATION o ");
        }

        sSQL.append(" WHERE ");
        sSQL.append("     d.HD_CCUSTOMERID_CK = "+cust);
        sSQL.append(" AND d.HD_CCOMPANYID_CK  = "+comp);
        // 検索タブで一覧を作成する場合は組織にまたがる
        if (psSearchData == null){
            // 2020-12-04 加入为null的限制，为null则不加入此查询条件
            if (!StrUtil.equals(section,"'null'") || StrUtil.isNotBlank(section)) {
                sSQL.append(" AND d.HD_CSECTIONID_FK IN (" + section + ") ");
            }
        }

        sSQL.append(" AND d.HD_DSTARTDATE_CK <= "+targetEndDate);
        sSQL.append(" AND d.HD_DENDDATE      >= "+targetEndDate);
        if(ifKyeOrAdditionalRole){
            sSQL.append(" AND d.HD_CIFKEYORADDITIONALROLE = '0' ");
        }
        sSQL.append(" AND d.HD_NOFFCIALORNOT  = 0 ");
        sSQL.append(" AND d.HD_NOFFICIALORNOT = 0 ");

        sSQL.append(" AND e.TEM_CEMPLOYEEID(+) = d.HD_CEMPLOYEEID_CK ");
        sSQL.append(" AND e.TEM_DSTARTDATE(+)  <= " + targetEndDate);
        sSQL.append(" AND e.TEM_CCOMPANYID(+)  = d.HD_CCOMPANYID_CK ");
        sSQL.append(" AND e.TEM_CCUSTOMERID(+) = d.HD_CCUSTOMERID_CK ");
        sSQL.append(" AND e.TEM_DENDDATE(+)    >= " + targetEndDate);

        if(!useManageFLG){
            // 管理対象外を判定
            sSQL.append("  AND '"+ TmgUtil.Cs_MGD_MANAGEFLG_0+"' <> ");
            sSQL.append("        TMG_F_IS_MANAGEFLG(e.TEM_CEMPLOYEEID,TRUNC(" + targetEndDate+ "),LAST_DAY("+ targetEndDate +"),e.TEM_CCUSTOMERID,e.TEM_CCOMPANYID)");
        }

        if (StrUtil.isNotBlank(layeredSection)) {
            // 検索対象範囲の適用
            sSQL.append("    AND o.MO_CCUSTOMERID_CK_FK = D.HD_CCUSTOMERID_CK ");
            sSQL.append("    AND o.MO_CCOMPANYID_CK_FK = D.HD_CCOMPANYID_CK ");
            sSQL.append("    AND o.MO_CSECTIONID_CK = D.HD_CSECTIONID_FK ");
            sSQL.append("    AND o.MO_CLANGUAGE = " + language);
            sSQL.append("    AND o.MO_DSTART <= " + targetEndDate);
            sSQL.append("    AND o.MO_DEND >= " + targetEndDate + " ");
            sSQL.append(layeredSection);
        }

        if(isJoinTmgEmployees){
            // 管理対象身分か判定
            sSQL.append(" AND e.TEM_CWORKTYPEID NOT IN ('"+ TmgUtil.Cs_MGD_WORKERTYPE_00+"') ");
        }

        // 組織コード、役職ウェイト、カナ氏名、職員番号、でソート
        sSQL.append(" ORDER BY ");
        sSQL.append("     d.HD_CSECTIONID_FK, ");
        sSQL.append("     TMG_F_GET_MP_WEIGHT(d.HD_CPOSTID_FK," + targetEndDate + ",d.HD_CCUSTOMERID_CK,d.HD_CCOMPANYID_CK,"+language+"), ");
        sSQL.append("     TMG_F_GET_ME_NAME(d.HD_CEMPLOYEEID_CK, " + targetEndDate + ", 1, d.HD_CCUSTOMERID_CK, d.HD_CCOMPANYID_CK), ");
        sSQL.append("     d.HD_CEMPLOYEEID_CK ");

        sSQL.append(")");

        if (psSearchData != null){

            sSQL.append(buildSQLForSelectEmpListWhere(psSearchItems, psSearchCondition, psSearchData));
            sSQL.append(buildSQLForSelectEmpListOrder("ME_CKANANAME"));

            sSQL.append(")");

            sSQL.append(buildSQLForSelectEmpListWhereRowLimit(cust, comp, targetEndDate, language));

        }

        return sSQL.toString();
    }

    /**
     * 指定された条件で、SQL(WHERE句)を構築して返します。
     * @param psSearchItems
     * @param psSearchCondition
     * @param psSearchData
     * @return String SQL
     */
    private String buildSQLForSelectEmpListWhere(String psSearchItems, String psSearchCondition, String psSearchData){

        StringBuilder sbSQL = new StringBuilder();

        sbSQL.append(" WHERE ");

        String sColumnName = new String();

        if (TmgUtil.Cs_TREE_VIEW_ITEMS_KANJINAME.equals(psSearchItems)){
            sColumnName = "ME_CKANJINAME";
        } else if (TmgUtil.Cs_TREE_VIEW_ITEMS_EMPLOYEEID.equals(psSearchItems)){
            sColumnName = "HD_CEMPLOYEEID_CK";
        } else {
            sColumnName = "ME_CKANANAME";
        }

        sbSQL.append(buildSQLForLikeColumn(sColumnName, psSearchCondition, psSearchData));

        return sbSQL.toString();

    }

    /**
     * 指定された条件で、SQL(LIKE文)を構築して返します。
     * @param psSearchItems
     * @param psSearchCondition
     * @param psSearchData
     * @return String SQL
     */
    private String buildSQLForLikeColumn(String psSearchItems, String psSearchCondition, String psSearchData){

        if (psSearchData == null){return "";}

        StringBuilder sbSQL = new StringBuilder();
        String sReplaceSearchData = psSearchData.replaceAll("_", "__").replaceAll("%", "_%");

        if (TmgUtil.Cs_TREE_VIEW_CONDITION_PREFIXSEARCH.equals(psSearchCondition)){
            sbSQL.append(psSearchItems).append(" LIKE ").append(SysUtil.escDBString(sReplaceSearchData + "%"));
        } else if (TmgUtil.Cs_TREE_VIEW_CONDITION_BACKWARDMATCH.equals(psSearchCondition)){
            sbSQL.append(psSearchItems).append(" LIKE ").append(SysUtil.escDBString("%" + sReplaceSearchData));
        } else {
            sbSQL.append(psSearchItems).append(" LIKE ").append(SysUtil.escDBString("%" + sReplaceSearchData + "%"));
        }

        sbSQL.append(" ESCAPE '_' ");

        return sbSQL.toString();

    }

    /**
     * 指定された条件で、SQL(ORDER BY句)を構築して返します。
     * @return String SQL
     */
    private String buildSQLForSelectEmpListOrder(String psColumnName){

        StringBuilder sbSQL = new StringBuilder("");

        sbSQL.append(" ORDER BY ");
        sbSQL.append(psColumnName);
        sbSQL.append(" ");

        return sbSQL.toString();

    }

    /**
     * 指定された条件で、SQL(WHERE句)を構築して返します。
     * @return String SQL
     */
    private String buildSQLForSelectEmpListWhereRowLimit(String psCustId, String psCompId,
                                                         String psDate, String psLanguage
    ){

        StringBuilder sbSQL = new StringBuilder("");

        sbSQL.append(" WHERE ");
        sbSQL.append(     " ROWNUM <= NVL(( ").append(buildSQLForSelectTmgDispLimit4Tree(psCustId, psCompId, psDate, psLanguage));
        sbSQL.append(                "), ").append(TmgUtil.Cs_TmgDispLimit4TreeDefault).append(")");

        return sbSQL.toString();

    }

    private String gsDispLimi4Tree = null;

    /**
     * 検索上限値を返却する。
     * @param psBaseDate
     * @throws Exception
     */
    private String getMsgDispLimit4Tree(String psBaseDate) throws Exception{

        String sSQL =buildSQLForSelectTmgDispLimit4Tree( SysUtil.escDBString(bean.getCustID()),
                        SysUtil.escDBString(bean.getCompCode()),psBaseDate,  SysUtil.escDBString(bean.getLanguage()));
        Entity entity;
        log.info("getMsgDispLimit4Tree_SQL4："+ sSQL);
        try (Connection connection = dataSource.getConnection()){
            entity = SqlExecutor.query(connection,sSQL ,new EntityHandler());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        //TMG_V_MGD_DISP_LIMIT4TREEから最大件数を取得できるなら、取得した最大件数を返却する。取得できないなら、固定値：100を返却する
        return entity == null ? TmgUtil.Cs_TmgDispLimit4TreeDefault : entity.getStr("MGD_NLIMIT");

    }

    public void setDispLimit4Tree(String psDispLimi4Tree) throws Exception{
        gsDispLimi4Tree = psDispLimi4Tree;
    }

    public String getDispLimit4Tree(){
        return gsDispLimi4Tree;
    }

    /**
     * 指定された条件で、DBから検索上限値を検索するSQLを構築して返します。
     * @return String SQL
     */
    private String buildSQLForSelectTmgDispLimit4Tree(String psCustId, String psCompId,
                                                      String psDate, String psLanguage
    ){

        StringBuilder sbSQL = new StringBuilder("");

        sbSQL.append(" SELECT ");
        sbSQL.append(     " MGD_NLIMIT ");
        sbSQL.append(" FROM ");
        sbSQL.append(     " TMG_V_MGD_DISP_LIMIT4TREE ");
        sbSQL.append(" WHERE ");
        sbSQL.append(     " MGD_CCUSTOMERID       = ").append(psCustId);
        sbSQL.append(" AND  MGD_CCOMPANYID_CK_FK  = ").append(psCompId);
        sbSQL.append(" AND  MGD_DSTART_CK        <= ").append(psDate);
        sbSQL.append(" AND  MGD_DEND             >= ").append(psDate);
        sbSQL.append(" AND  MGD_CLANGUAGE_CK      = ").append(psLanguage);

        return sbSQL.toString();

    }

    /**
     *
     * @param cust
     * @param comp
     * @param ward
     * @param targetDate
     * @param language
     * @return
     */
    public String buildSQLForSelectWardEmpList(
            String cust,
            String comp,
            String ward,
            String targetDate,
            String language,
            boolean ifKyeOrAdditionalRole
    ){
        StringBuilder sSQL = new StringBuilder();

        sSQL.append(" SELECT  ");
        sSQL.append(    " 0 as MO_NLEVEL");

        // SYSDATE時点で所属している職員については、「(部署名/役職名)」を表示する
        sSQL.append(    ",CASE ");
        sSQL.append(        " WHEN HD_DENDDATE >= " + targetDate + " THEN ");
        sSQL.append(            " ME_CKANJINAME || ' (' || MO_CSECTIONNICK || '/' || MAP_CPOSTNAME || ')' ");
        sSQL.append(        " ELSE ");
        sSQL.append(            " ME_CKANJINAME || '( * )' ");
        sSQL.append(    " END");

        sSQL.append(    ",HD_CCUSTOMERID_CK");
        sSQL.append(    ",HD_CCOMPANYID_CK");
        sSQL.append(    ",HD_CEMPLOYEEID_CK");
        sSQL.append(    ",ME_CKANJINAME");
        sSQL.append(    ",HD_CSECTIONID_FK");
        sSQL.append(    ",MO_CSECTIONNICK");
        sSQL.append(    ",HD_CPOSTID_FK");
        sSQL.append(    ",MAP_CPOSTNAME");
        sSQL.append(    ",TEM_CWORKTYPEID");
        sSQL.append(    ",TEM_CWORKTYPENAME");
        sSQL.append(    ",to_char(HD_DSTARTDATE_CK,'YYYY/MM/DD')");
        sSQL.append(    ",to_char(HD_DENDDATE,'YYYY/MM/DD')");
        sSQL.append(    ",ROWNUM AS SEQ ");
        sSQL.append(" FROM (");

        sSQL.append(" SELECT ");
        sSQL.append(    " d.HD_CCUSTOMERID_CK");
        sSQL.append(    ",d.HD_CCOMPANYID_CK");
        sSQL.append(    ",d.HD_CEMPLOYEEID_CK");
        sSQL.append(    ",TMG_F_GET_ME_NAME(d.HD_CEMPLOYEEID_CK, " + targetDate + ", 0, d.HD_CCUSTOMERID_CK, d.HD_CCOMPANYID_CK) as ME_CKANJINAME");
        sSQL.append(    ",d.HD_CSECTIONID_FK");
        sSQL.append(    ",TMG_F_GET_MO(d.HD_CSECTIONID_FK, " + targetDate + ",1,d.HD_CCUSTOMERID_CK,d.HD_CCOMPANYID_CK,"+language+") as MO_CSECTIONNICK");
        sSQL.append(    ",d.HD_CPOSTID_FK");
        sSQL.append(    ",TMG_F_GET_MP(d.HD_CPOSTID_FK, " + targetDate + ",d.HD_CCUSTOMERID_CK,d.HD_CCOMPANYID_CK,"+language+") as MAP_CPOSTNAME");
        sSQL.append(    ",e.TEM_CWORKTYPEID");
        sSQL.append(    ",TMG_F_GET_MGD(e.TEM_CWORKTYPEID, " + targetDate + ",e.TEM_CCUSTOMERID,e.TEM_CCOMPANYID,"+language+") as TEM_CWORKTYPENAME");
        sSQL.append(    ",trunc(d.HD_DSTARTDATE_CK) as HD_DSTARTDATE_CK");
        sSQL.append(    ",trunc(d.HD_DENDDATE) as HD_DENDDATE ");

        sSQL.append(" FROM ");
        sSQL.append(    " HIST_DESIGNATION d");
        sSQL.append(    ",TMG_EMPLOYEES e ");

        sSQL.append(" WHERE ");
        sSQL.append("     d.HD_CCUSTOMERID_CK = "+cust);
        sSQL.append(" AND d.HD_CCOMPANYID_CK  = "+comp);

        //sSQL.append(" AND d.HD_CSECTIONID_FK IN ("+section+") ");
        sSQL.append(" 	and exists ( ");
        sSQL.append(" 		select ");
        sSQL.append(" 				1 ");
        sSQL.append(" 		from ");
        sSQL.append(" 			TMG_EMPLOYEE_ATTRIBUTE e ");
        sSQL.append(" 		where ");
        sSQL.append(" 				e.TES_CCUSTOMERID = d.HD_CCUSTOMERID_CK ");
        sSQL.append(" 			and e.TES_CCOMPANYID = d.HD_CCOMPANYID_CK ");
        sSQL.append(" 			and e.TES_CTYPE = " + ward);
        sSQL.append(" 			and e.TES_CEMPLOYEEID = d.HD_CEMPLOYEEID_CK ");
        sSQL.append(" 			and " + targetDate);
        sSQL.append(" 				between e.TES_DSTARTDATE and e.TES_DENDDATE ");
        sSQL.append(" 	) ");


        sSQL.append(" AND d.HD_DSTARTDATE_CK <= "+targetDate);
        sSQL.append(" AND d.HD_DENDDATE      >= "+targetDate);
        if(ifKyeOrAdditionalRole){
            sSQL.append(" AND d.HD_CIFKEYORADDITIONALROLE = '0' ");
        }
        sSQL.append(" AND d.HD_NOFFCIALORNOT  = 0 ");
        sSQL.append(" AND d.HD_NOFFICIALORNOT = 0 ");

        sSQL.append(" AND e.TEM_CEMPLOYEEID(+) = d.HD_CEMPLOYEEID_CK ");
        sSQL.append(" AND e.TEM_DSTARTDATE(+)  <= " + targetDate);
        sSQL.append(" AND e.TEM_CCOMPANYID(+)  = d.HD_CCOMPANYID_CK ");
        sSQL.append(" AND e.TEM_CCUSTOMERID(+) = d.HD_CCUSTOMERID_CK ");
        sSQL.append(" AND e.TEM_DENDDATE(+)    >= " + targetDate);


        // TODO: こっちはまだ this.withTarget による分岐を適用していません。いまのところここは関係ないので・・・。
        // 検索対象範囲の適用
          String sExists = "";
         try {
          sExists = tmgSearchRangeUtil.getExistsQuery(bean, Objects.requireNonNull(ContextUtil.getHttpRequest()).getSession(), "d.HD_CCOMPANYID_CK", "d.HD_CEMPLOYEEID_CK");
           }
          catch(Exception e) {
              sExists = "";
          }
        sSQL.append(sExists);

        // 終了日(降順)、部署コード、役職ID、開始日(昇順)、職員番号、でソート
        sSQL.append(" ORDER BY ");
        sSQL.append("     LEAST(d.HD_DENDDATE, " + targetDate + ") desc, ");
        sSQL.append("     d.HD_CSECTIONID_FK, ");
        sSQL.append("     TMG_F_GET_MP_WEIGHT(d.HD_CPOSTID_FK," + targetDate + ",d.HD_CCUSTOMERID_CK,d.HD_CCOMPANYID_CK,"+language+"), ");
        sSQL.append("     d.HD_DSTARTDATE_CK, ");
        sSQL.append("     d.HD_CEMPLOYEEID_CK ");

        sSQL.append(")");

        return sSQL.toString();
    }

    /**
     * dataArrayのデータについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、全てのデータをフラットに扱います。
     * 部署ごとにグループ化して作成したい場合は、getJSONArrayForTreeViewGroupBySectionメソッドを使用してください。
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForTreeView(){
        if(dataArray == null){
            return null;
        }
        try{
            // 職員番号でdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = {
                    DEFAULT_KEY_EMPID
            };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);

            return JSONArrayGenerator.getJSONArrayForTreeView(distinctDataArray,keyArray);

        }catch(Exception e){
            return null;
        }
    }


    /**
     * gvSearchDataArrayのデータについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、全てのデータをフラットに扱います。
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForSearchView(){
        if(gvSearchDataArray == null){
            return null;
        }
        try{
            // 職員番号でdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = { DEFAULT_KEY_EMPID };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(gvSearchDataArray, distinctKeyArray);
            return JSONArrayGenerator.getJSONArrayForTreeView(distinctDataArray,keyArray);

        }catch(Exception e){
            return null;
        }
    }

    /**
     * dataArrayのデータについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、各データについてsecidカラムをキーにグループ化します。
     * @return
     */
    public String getJSONArrayForTreeViewGroupBySection(){
        if(dataArray == null){
            return null;
        }
        try{
            // 職員番号と部署コードでdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = {
                    DEFAULT_KEY_EMPID,
                    DEFAULT_KEY_SECID
            };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);

            int[] groupKey   = { DEFAULT_KEY_SECID };
            int[] groupLabel = { DEFAULT_KEY_SECNIC };

            return JSONArrayGenerator.getJSONArrayForTreeViewGroupBy(distinctDataArray,keyArray,groupKey,groupLabel);

        }catch(Exception e){
            return null;
        }
    }

    /**
     * dataArrayのデータについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、各データについてsecidカラムをキーにグループ化します。
     * @return
     */
    public String getJSONArrayForTreeViewGroupBySection(String targetSec){
        if(dataArray == null){
            return null;
        }
        try{
            // 職員番号と部署コードでdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = {
                    DEFAULT_KEY_EMPID,
                    DEFAULT_KEY_SECID
            };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);
            int[] groupKey   = { DEFAULT_KEY_SECID };
            int[] groupLabel = { DEFAULT_KEY_SECNIC };
            if(StrUtil.isNotBlank(targetSec)){
                String[][] initOpenNodeArray = { {targetSec} };
                String string = JSONArrayGenerator.getJSONArrayForTreeViewGroupBy(distinctDataArray,keyArray,groupKey,groupLabel,initOpenNodeArray);
                return string;
            }else{
                return JSONArrayGenerator.getJSONArrayForTreeViewGroupBy(distinctDataArray,keyArray,groupKey,groupLabel);
            }

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

      /**
     * baseDate(基準日)～targetDate(遡り基準日)の範囲内について一部でも歴がかかっているメンバー一覧を返します。
     * @param baseDate
     * @param targetDate
     * @return
     * @throws Exception
     */
    public List getDataArrayBetween(String baseDate, String targetDate) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date base = sdf.parse(baseDate);
        Date target = sdf.parse(targetDate);
        // baseDate(基準日)がtargetDate(遡り基準日)より以前の場合、baseDate時点のデータだけを対象とする
        if(base.before(target)){
            target = sdf.parse(baseDate);
        }
        return JSONArrayGenerator.selectDataArrayBetween(dataArray, target, base, DEFAULT_KEY_DSTART, DEFAULT_KEY_DEND, sdf);
    }

    /**
     * baseDate(基準日)～targetDate(遡り基準日)の範囲内について一部でも歴がかかっているメンバー一覧を返します。
     * @param baseDate
     * @param targetDate
     * @return
     * @throws Exception
     */
    public List getSearchDataArrayBetween(String baseDate, String targetDate) throws Exception{

        if (gvSearchDataArray == null){return null;}

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date base = sdf.parse(baseDate);
        Date target = sdf.parse(targetDate);
        // baseDate(基準日)がtargetDate(遡り基準日)より以前の場合、baseDate時点のデータだけを対象とする
        if(base.before(target)){
            target = sdf.parse(baseDate);
        }

        return JSONArrayGenerator.selectDataArrayBetween(gvSearchDataArray, target, base, DEFAULT_KEY_DSTART, DEFAULT_KEY_DEND, sdf);
    }

    /**
     * dataArrayのデータから、職員番号・職員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します
     * @return
     */
    public String buildSQLForSelectEmpListFromDual() throws Exception{

        // 職員番号でdistinctをかけてから、SQLを構築するcreateSearchEmpList
        int[] distinctKeyArray = { DEFAULT_KEY_EMPID };

        List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray
        );

        // 抽出するカラムを定義
        int[] selectKeyArray = {
                DEFAULT_KEY_EMPID,
                DEFAULT_KEY_EMPNAME,
                DEFAULT_KEY_SEQ,
                DEFAULT_KEY_CUST,
                DEFAULT_KEY_COMP
        };

        // 擬似インラインビューを構築して返す
        return JSONArrayGenerator.buildSQLForSelectFromDual(distinctDataArray, keyArray, selectKeyArray);
    }

    /**
     * gvSearchDataArrayのデータから、職員番号・職員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します
     * @return
     */
    public String buildSearchDataArraySQLForSelectEmpListFromDual() throws Exception{
        // 職員番号でdistinctをかけてから、SQLを構築する
        int[] distinctKeyArray = { DEFAULT_KEY_EMPID };

        List distinctDataArray = JSONArrayGenerator.distinctDataArray(getSearchDataArray(), distinctKeyArray
        );

        // 抽出するカラムを定義
        int[] selectKeyArray = {
                DEFAULT_KEY_EMPID,
                DEFAULT_KEY_EMPNAME,
                DEFAULT_KEY_SEQ,
                DEFAULT_KEY_CUST,
                DEFAULT_KEY_COMP
        };

        // 擬似インラインビューを構築して返す
        return JSONArrayGenerator.buildSQLForSelectFromDual(distinctDataArray, keyArray, selectKeyArray);
    }

    /**
     * dataArrayのデータから、職員番号・職員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します
     * Oracleテーブルオブジェクトの作成版
     * @return
     */
    public String buildSQLForSelectEmpListFromDualTableObject(boolean pbSelectedSearchTab) throws Exception{
        // TODO: 呼び出し先はTmgDutyHoursBean　交替制勤務割振表
        // 職員番号でdistinctをかけてから、SQLを構築する
        int[] distinctKeyArray = { DEFAULT_KEY_EMPID };

        List distinctDataArray = JSONArrayGenerator.distinctDataArray((pbSelectedSearchTab) ? getSearchDataArray() : dataArray, distinctKeyArray
        );

        // 抽出するカラムを定義
        int[] selectKeyArray = {
                DEFAULT_KEY_EMPID,
                DEFAULT_KEY_EMPNAME,
                DEFAULT_KEY_SEQ,
                DEFAULT_KEY_CUST,
                DEFAULT_KEY_COMP
        };

        // 擬似インラインビューを構築して返す
        return JSONArrayGenerator.buildSQLForSelectFromDualTableObject(distinctDataArray, keyArray, selectKeyArray);
    }

    public boolean existsEmp(String empid){
        for(Iterator i = dataArray.iterator(); i.hasNext();){
            List data = (List)i.next();
            if(empid.equals(data.get(DEFAULT_KEY_EMPID)+"")){
                return true;
            }
        }
        return false;
    }

    /**
     * 指定された職員の指定されたデータを返します
     * dataArrayに指定された職員が存在しない場合、nullを返します
     * @param targetMemberId 対象職員の職員番号
     * @param keyIndex 取得したいデータのカラム番号(TmgMemberList.DEFAULT_KEY_～を指定)
     * @return String 職員のデータ(指定された職員が存在しない場合、NULL)
     */
    public String getTargetEmpData(String targetMemberId, int keyIndex){
        try{
            for(Iterator i = dataArray.iterator(); i.hasNext();){
                List data = (List)i.next();
                if(data.get(DEFAULT_KEY_EMPID).equals(targetMemberId)){
                    return (String)data.get(keyIndex);
                }
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定された職員の氏名を返します
     * dataArrayに指定された職員が存在しない場合、NULLを返します
     * @param targetMemberId 対象職員の職員番号
     * @return String 職員氏名(指定された職員が存在しない場合、NULL)
     */
    public String getTargetEmpName(String targetMemberId){
        try{
            return getTargetEmpData(targetMemberId, DEFAULT_KEY_EMPNAME);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定された職員の勤怠種別IDを返します
     * 指定された職員が存在しない場合、nullを返します
     * @param targetMemberId 対象職員の職員番号
     * @return String 勤怠種別ID(指定された職員が存在しない場合)
     */
    public String getTargetEmpWorkerTypeId(String targetMemberId){
        try{
            return getTargetEmpData(targetMemberId, DEFAULT_KEY_WORKERTYPEID);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定された職員の勤怠種別名称を返します
     * 指定された職員が存在しない場合、nullを返します
     * @param targetMemberId 対象職員の職員番号
     * @return String 勤怠種別名称(指定された職員が存在しない場合)
     */
    public String getTargetEmpWorkerTypeName(String targetMemberId){
        try{
            return getTargetEmpData(targetMemberId, DEFAULT_KEY_WORKERTYPENAME);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List getDataArray() {
        return dataArray;
    }

    public void setDataArray(List dataArray) {
        this.dataArray = dataArray;
    }

    public String[] getKeyArray() {
        return keyArray;
    }

    public List getSearchDataArray() {
        return this.gvSearchDataArray;
    }

    public void setSearchDataArray(List pvSearchDataArray) {
        this.gvSearchDataArray = pvSearchDataArray;
    }

}
