package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityHandler;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class TmgMemberList {

    /**
     * 検索結果をJSONへマッピングする際のキー文字列(この文字列をキーとした連想配列が作成される)
     */
    public static final String[] DEFAULT_KEY_ARRAY = {
            "level","label","grouplabel","groupid","groupname","empid","empname","secid","secnic",
            "postid","postname","workertypeid","wokertypename","dstart","dend","seq","cust","comp",
            "kananame"
    };

    public static final int DEFAULT_KEY_LEVEL          = 0;
    public static final int DEFAULT_KEY_LABEL          = 1;
    public static final int DEFAULT_KEY_GROUPLABEL     = 2;
    public static final int DEFAULT_KEY_GROUPID        = 3;
    public static final int DEFAULT_KEY_GROUPNAME      = 4;
    public static final int DEFAULT_KEY_EMPID          = 5;
    public static final int DEFAULT_KEY_EMPNAME        = 6;
    public static final int DEFAULT_KEY_SECID          = 7;
    public static final int DEFAULT_KEY_SECNIC         = 8;
    public static final int DEFAULT_KEY_POSTID         = 9;
    public static final int DEFAULT_KEY_POSTNAME       = 10;
    public static final int DEFAULT_KEY_WORKERTYPEID   = 11;
    public static final int DEFAULT_KEY_WORKERTYPENAME = 12;
    public static final int DEFAULT_KEY_DSTART         = 13;
    public static final int DEFAULT_KEY_DEND           = 14;
    public static final int DEFAULT_KEY_SEQ            = 15;
    public static final int DEFAULT_KEY_CUST           = 16;
    public static final int DEFAULT_KEY_COMP           = 17;

    private PsDBBean bean;
    private String beanDesc = null;
    private List dataArray = null;
    private List gvSearchDataArray = null;
    private String[] keyArray;

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
    private String dateFormat;

    private final DataSource dataSource = (DataSource) SpringUtil.getBean("dataSource");

    /**
     * コンストラクタ
     * @param bean
     */
    public TmgMemberList(PsDBBean bean) {
        // 下記のコンストラクタのエラー回避のためです。
        this.bean = bean;
        keyArray = DEFAULT_KEY_ARRAY;
        dateFormat = DEFAULT_DATE_FORMAT;
    }

    /**
     * コンストラクタ
     * @param bean
     * @param beanDesc
     */
    public TmgMemberList(PsDBBean bean, String beanDesc) {
        this.bean = bean;
        this.beanDesc = beanDesc;
        keyArray = DEFAULT_KEY_ARRAY;
        dateFormat = DEFAULT_DATE_FORMAT;
    }

    /**
     * 組織ツリータブ用
     * 指定された条件で、参照可能な社員一覧を作成します。
     * 検索結果は、二次元Vectorの形式で保持されます。
     * ※このメソッドは、セキュリティ判定無しの検索を行います。
     * @param baseDate
     * @param useManageFLG
     * @throws Exception
     */
    public void createMemberList(String baseDate, boolean useManageFLG) throws Exception{

        String paramUseManageFlg = (useManageFLG) ? "1" : "0" ;

        createTreeMemberList(baseDate, paramUseManageFlg);

    }

    /**
     * 組織ツリー検索タブ用
     * 指定された条件で、参照可能な社員一覧を作成します。
     * 検索結果は、二次元Vectorの形式で保持されます。
     * ※このメソッドは、セキュリティ判定無しの検索を行います。
     * @param baseDate
     * @param useManageFLG
     * @param psSearchItems
     * @param psSearchCondition
     * @param psSearchData
     * @throws Exception
     */
    public void createMemberList(String baseDate, boolean useManageFLG,
                                 String psSearchItems, String psSearchCondition, String psSearchData) throws Exception{

        String paramUseManageFlg = (useManageFLG) ? "1" : "0" ;

        createSearchMemberList(baseDate, paramUseManageFlg,
                psSearchItems, psSearchCondition, psSearchData
        );

        setDispLimit4Tree(getMsgDispLimit4Tree(baseDate));

    }

    /**
     * 組織ツリータブ用
     * 指定された条件で、参照可能な社員一覧を作成します。
     * 検索結果は、二次元Vectorの形式で保持されます。
     * ※このメソッドは、セキュリティ判定無しの検索を行います。
     * @param baseDate
     * @param psParamUseManageFlg
     * @throws Exception
     */
    private void createTreeMemberList(String baseDate, String psParamUseManageFlg) throws Exception{

        String sSQL =   buildSQLForSelectMemberList(
                        bean.escDBString(bean.getCustID()),
                        bean.escDBString(bean.getCompCode()),
                        bean.escDBString(bean.getUserCode()),
                        baseDate,
                        psParamUseManageFlg,
                        bean.escDBString(bean.getLanguage()),
                        bean.escDBString(DEFAULT_DATE_FORMAT),
                        null,
                        null,
                        null);

        Connection connection = null;
        List entityList = null;
        log.info("createTreeMemberList_SQL1：{}",sSQL);
        try {
            connection = dataSource.getConnection();
            entityList = SqlExecutor.query(connection,sSQL ,new EntityListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        dataArray = JSONArrayGenerator.entityListTowardList(entityList);

    }

    /**
     * 組織ツリー検索タブ用
     * 指定された条件で、参照可能な社員一覧を作成します。
     * 検索結果は、二次元Vectorの形式で保持されます。
     * ※このメソッドは、セキュリティ判定無しの検索を行います。
     * @param baseDate
     * @param psParamUseManageFlg
     * @param psSearchItems
     * @param psSearchCondition
     * @param psSearchData
     * @throws Exception
     */
    private void createSearchMemberList(String baseDate, String psParamUseManageFlg, String psSearchItems,
                                        String psSearchCondition, String psSearchData
    ) throws Exception{

        String sSQL =   buildSQLForSelectMemberList(bean.escDBString(bean.getCustID()), bean.escDBString(bean.getCompCode()),
                        bean.escDBString(bean.getUserCode()), baseDate, psParamUseManageFlg, bean.escDBString(bean.getLanguage()),
                        bean.escDBString(DEFAULT_DATE_FORMAT), psSearchItems, psSearchCondition,
                        psSearchData);

        Connection connection = null;
        List entityList = null;
        log.info("createSearchMemberList_SQL2：{}",sSQL);
        try {
            connection = dataSource.getConnection();
            entityList = SqlExecutor.query(connection,sSQL ,new EntityListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        gvSearchDataArray = JSONArrayGenerator.entityListTowardList(entityList);
    }

    private String gsDispLimi4Tree = null;

    /**
     * 検索上限値を返却する。
     * @param psBaseDate
     * @throws Exception
     */
    private String getMsgDispLimit4Tree(String psBaseDate) throws Exception{

        String sSQL =   buildSQLForSelectTmgDispLimit4Tree(bean.escDBString(bean.getCustID()),
                        bean.escDBString(bean.getCompCode()), psBaseDate, bean.escDBString(bean.getLanguage()));

        Connection connection = null;
        Entity entity = null;
        log.info("getMsgDispLimit4Tree_SQL3：{}",sSQL);
        try {
            connection = dataSource.getConnection();
            entity = SqlExecutor.query(connection,sSQL ,new EntityHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
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
     * 指定された条件で、DBから社員一覧を検索するSQLを構築して返します。
     * @param cust
     * @param comp
     * @param baseDate
     * @param useManageFLG
     * @param lang
     * @param dateFormat
     * @param psSearchItems
     * @param psSearchCondition
     * @param psSearchData
     * @return
     */
    public String buildSQLForSelectMemberList(String cust, String comp,String emp, String baseDate,
                                              String useManageFLG, String lang, String dateFormat,
                                              String psSearchItems, String psSearchCondition, String psSearchData
    ){

        StringBuffer sSQL = new StringBuffer("");

        if (psSearchData != null){
            sSQL.append(   " SELECT ");
            sSQL.append(       " * ");
            sSQL.append(   " FROM ");
            sSQL.append(       " ( ");
        }

        sSQL.append("SELECT * FROM TABLE(");
        sSQL.append(    "TMG_SELECT_MEMBERLIST(");
        sSQL.append(        cust);
        sSQL.append(        ","+comp);
        sSQL.append(        ","+emp);
        sSQL.append(        ","+baseDate);
        sSQL.append(        ","+useManageFLG);
        sSQL.append(        ","+lang);
        sSQL.append(        ","+dateFormat);
        sSQL.append("))");

        if (psSearchData != null){
            sSQL.append(buildSQLForSelectMemberListWhere(psSearchItems, psSearchCondition, psSearchData));
            sSQL.append(buildSQLForSelectMemberListOrder("CKANANAME"));

            sSQL.append(")");

            sSQL.append(buildSQLForSelectEmpListWhereRowLimit(cust, comp, baseDate, lang));
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
    private String buildSQLForSelectMemberListWhere(String psSearchItems, String psSearchCondition, String psSearchData){

        StringBuffer sbSQL = new StringBuffer("");

        sbSQL.append(" WHERE ");

        String sColumnName = new String();

        if (TmgUtil.Cs_TREE_VIEW_ITEMS_KANJINAME.equals(psSearchItems)){
            sColumnName = "CKANJINAME";
        } else if (TmgUtil.Cs_TREE_VIEW_ITEMS_EMPLOYEEID.equals(psSearchItems)){
            sColumnName = "CEMPLOYEEID";
        } else {
            sColumnName = "CKANANAME";
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

        StringBuffer sbSQL = new StringBuffer("");
        String sReplaceSearchData = psSearchData.replaceAll("_", "__").replaceAll("%", "_%");

        if (TmgUtil.Cs_TREE_VIEW_CONDITION_PREFIXSEARCH.equals(psSearchCondition)){
            sbSQL.append(psSearchItems).append(" LIKE ").append(bean.escDBString(sReplaceSearchData + "%"));
        } else if (TmgUtil.Cs_TREE_VIEW_CONDITION_BACKWARDMATCH.equals(psSearchCondition)){
            sbSQL.append(psSearchItems).append(" LIKE ").append(bean.escDBString("%" + sReplaceSearchData));
        } else {
            sbSQL.append(psSearchItems).append(" LIKE ").append(bean.escDBString("%" + sReplaceSearchData + "%"));
        }

        sbSQL.append(" ESCAPE '_' ");

        return sbSQL.toString();

    }

    /**
     * 指定された条件で、SQL(ORDER BY句)を構築して返します。
     * @param psColumnName
     * @return String SQL
     */
    private String buildSQLForSelectMemberListOrder(String psColumnName){

        StringBuffer sbSQL = new StringBuffer("");

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

        StringBuffer sbSQL = new StringBuffer("");

        sbSQL.append(" WHERE ");
        sbSQL.append(     " ROWNUM <= NVL(( ").append(buildSQLForSelectTmgDispLimit4Tree(psCustId, psCompId, psDate, psLanguage));
        sbSQL.append(                "), ").append(TmgUtil.Cs_TmgDispLimit4TreeDefault).append(")");

        return sbSQL.toString();

    }

    /**
     * 指定された条件で、DBから検索上限値を検索するSQLを構築して返します。
     * @return String SQL
     */
    private String buildSQLForSelectTmgDispLimit4Tree(String psCustId, String psCompId,
                                                      String psDate, String psLanguage
    ){

        StringBuffer sbSQL = new StringBuffer("");

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
     * dataArrayのデータについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、全てのデータをフラットに扱います。
     * @return String ツリービュー用のJSON配列
     */
    public String getJSONArrayForTreeView(){
        if(dataArray == null){
            return null;
        }
        try{
            // 社員番号でdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = { DEFAULT_KEY_EMPID };
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
            // 社員番号でdistinctをかけてから、JSON配列を生成する
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
            // 社員番号と部署コードでdistinctをかけてから、JSON配列を生成する
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
            // 社員番号と部署コードでdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = {
                    DEFAULT_KEY_EMPID,
                    DEFAULT_KEY_SECID
            };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);

            int[] groupKey   = { DEFAULT_KEY_SECID };
            int[] groupLabel = { DEFAULT_KEY_SECNIC };

            if(targetSec != null){
                String[][] initOpenNodeArray = { {targetSec} };
                return JSONArrayGenerator.getJSONArrayForTreeViewGroupBy(distinctDataArray,keyArray,groupKey,groupLabel,initOpenNodeArray);
            }else{
                return JSONArrayGenerator.getJSONArrayForTreeViewGroupBy(distinctDataArray,keyArray,groupKey,groupLabel);
            }

        }catch(Exception e){
            return null;
        }
    }

    /**
     * dataArrayのレコードについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、各レコードについて、groupidカラムをキーにグループ化します。
     * @return
     */
    public String getJSONArrayForTreeViewGroupByGroup(){
        if(dataArray == null){
            return null;
        }
        try{
            // 社員番号とグループIDでdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = {
                    DEFAULT_KEY_EMPID,
                    DEFAULT_KEY_GROUPID
            };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);

            int[] groupKey   = { DEFAULT_KEY_GROUPID };
            int[] groupLabel = { DEFAULT_KEY_GROUPLABEL };

            return JSONArrayGenerator.getJSONArrayForTreeViewGroupBy(distinctDataArray,keyArray,groupKey,groupLabel);
        }catch(Exception e){
            return null;
        }
    }

    /**
     * dataArrayのレコードについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、各レコードについて、groupidカラムをキーにグループ化します。
     * @return
     */
    public String getJSONArrayForTreeViewGroupByGroup(String targetGroup){
        if(dataArray == null){
            return null;
        }
        try{
            // 社員番号とグループIDでdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = {
                    DEFAULT_KEY_EMPID,
                    DEFAULT_KEY_GROUPID
            };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);

            int[] groupKey   = { DEFAULT_KEY_GROUPID };
            int[] groupLabel = { DEFAULT_KEY_GROUPLABEL };

            if(targetGroup != null)	{
                String[][] initOpenNodeArray = { {targetGroup} };
                return JSONArrayGenerator.getJSONArrayForTreeViewGroupBy(distinctDataArray,keyArray,groupKey,groupLabel,initOpenNodeArray);
            }else{
                return JSONArrayGenerator.getJSONArrayForTreeViewGroupBy(distinctDataArray,keyArray,groupKey,groupLabel);
            }
        }catch(Exception e){
            return null;
        }
    }

    /**
     * dataArrayのデータから、
     * 社員番号・社員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します。
     * @return
     */
    public String buildSQLForSelectMemberListFromDualAll() throws Exception{
        return buildSQLForSelectMemberListFromDualByArray(dataArray);
    }

    /**
     * dataArrayのデータから、指定した部署に所属している社員について、
     * 社員番号・社員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します。
     * @return
     */
    public String buildSQLForSelectMemberListFromDualInSection(String[] targetSectionArray) throws Exception{
        // まずは部署コードで絞り込む
        List targetDataArray = getDataArrayInSection(targetSectionArray);

        return buildSQLForSelectMemberListFromDualByArray(targetDataArray);
    }

    /**
     * dataArrayのデータから、指定した部署に所属している社員について、
     * 社員番号・社員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します。
     * @return
     */
    public String buildSQLForSelectMemberListFromDualInGroup(String[] targetGroupArray) throws Exception{
        // まずは部署コードで絞り込む
        List targetDataArray = getDataArrayInGroup(targetGroupArray);

        return buildSQLForSelectMemberListFromDualByArray(targetDataArray);
    }

    // todo:targetDataArray ← この引数使用されてないみたいだが・・・
    /**
     * dataArrayのデータから、社員番号・社員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します
     * Oracleテーブルオブジェクトの作成版
     * @return
     */
    private String buildSQLForSelectMemberListFromDualByArray(List targetDataArray) throws Exception{
        // 社員番号でdistinctをかける
        int[] distinctKeyArray = { DEFAULT_KEY_EMPID };

        List distinctDataArray = JSONArrayGenerator.distinctDataArray(targetDataArray, distinctKeyArray);

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
     * searchDataArrayのデータから、
     * 社員番号・社員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します。
     * @return
     */
    public String buildearchDataArraySQLForSelectMemberListFromDual() throws Exception{
        return buildSearchDataArraySQLForSelectMemberListFromDualByArray();
    }

    /**
     * searchDataArrayのデータから、社員番号・社員氏名・ソート順を取得する擬似インラインビューのSQLを構築して返します
     * Oracleテーブルオブジェクトの作成版
     * @return
     */
    private String buildSearchDataArraySQLForSelectMemberListFromDualByArray() throws Exception{
        // 社員番号でdistinctをかける
        int[] distinctKeyArray = { DEFAULT_KEY_EMPID };

        List distinctDataArray = JSONArrayGenerator.distinctDataArray(getSearchDataArray(), distinctKeyArray);

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
     * baseDate(基準日)-targetDate(遡り基準日)の範囲内について一部でも歴がかかっているメンバー一覧を返します。
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
     * baseDate(基準日)-targetDate(遡り基準日)の範囲内について一部でも歴がかかっているメンバー一覧を返します。
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
     * 指定した部署に所属しているメンバー一覧を返します。
     * @param targetSectionArray
     * @return
     * @throws Exception
     */
    public List getDataArrayInSection(String[] targetSectionArray) throws Exception{
        return JSONArrayGenerator.selectDataArray(dataArray, DEFAULT_KEY_SECID, targetSectionArray);
    }

    /**
     * 指定したグループに所属しているメンバー一覧を返します。
     * @param targetGroupArray
     * @return
     * @throws Exception
     */
    public List getDataArrayInGroup(String[] targetGroupArray) throws Exception{
        return JSONArrayGenerator.selectDataArray(dataArray, DEFAULT_KEY_GROUPID, targetGroupArray);
    }

    /**
     * 指定された社員の指定されたデータを返します
     * dataArrayに指定された社員が存在しない場合、nullを返します
     * @param targetMemberId 対象社員の社員番号
     * @param keyIndex 取得したいデータのカラム番号(TmgMemberList.DEFAULT_KEY_-を指定)
     * @return String 社員のデータ(指定された社員が存在しない場合、NULL)
     */
    public String getTargetMemberData(String targetMemberId, int keyIndex){
        try{
            for(Iterator i = dataArray.iterator(); i.hasNext();){
                List data = (List)i.next();
                if(data.get(DEFAULT_KEY_EMPID).equals(targetMemberId)){
                    return (String)data.get(keyIndex);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定された社員の氏名を返します
     * dataArrayに指定された社員が存在しない場合、NULLを返します
     * @param targetMemberId 対象社員の社員番号
     * @return String 社員氏名(指定された社員が存在しない場合、NULL)
     */
    public String getTargetMemberName(String targetMemberId){
        try{
            return getTargetMemberData(targetMemberId, DEFAULT_KEY_EMPNAME);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定された社員の勤怠種別IDを返します
     * 指定された社員が存在しない場合、nullを返します
     * @param targetMemberId 対象社員の社員番号
     * @return String 勤怠種別ID(指定された社員が存在しない場合)
     */
    public String getTargetMemberWorkerTypeId(String targetMemberId){
        try{
            return getTargetMemberData(targetMemberId, DEFAULT_KEY_WORKERTYPEID);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定された社員の勤怠種別名称を返します
     * 指定された社員が存在しない場合、nullを返します
     * @param targetMemberId 対象社員の社員番号
     * @return String 勤怠種別名称(指定された社員が存在しない場合)
     */
    public String getTargetMemberWorkerTypeName(String targetMemberId){
        try{
            return getTargetMemberData(targetMemberId, DEFAULT_KEY_WORKERTYPENAME);
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
