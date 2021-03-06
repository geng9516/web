package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.pojo.handler.OrganisationEntityListHandler;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TmgDivisionTree {

    /**
     * 検索結果をJSONへマッピングする際のデフォルトキー配列
     */
    public static final String[] DEFAULT_KEY_ARRAY = {
            "level","label","secid","secname","secnic","cust","comp"
    };

    public static final int DEFAULT_KEY_LEVEL   = 0;
    public static final int DEFAULT_KEY_LABEL   = 1;
    public static final int DEFAULT_KEY_SECID   = 2;
    public static final int DEFAULT_KEY_SECNAME = 3;
    public static final int DEFAULT_KEY_SECNIC  = 4;
    public static final int DEFAULT_KEY_CUST    = 5;
    public static final int DEFAULT_KEY_COMP    = 6;

    private PsDBBean psDBBean;
    private List dataArray = null;
    private String[] keyArray;

    private Boolean gbAllDivision;
    private String gsRootSection;

    private final DataSource dataSource = SpringUtil.getBean("dataSource");
    private final TmgSearchRangeUtil searchRangeUtil = SpringUtil.getBean(TmgSearchRangeUtil.class);

    /**
     * コンストラクタ
     * @param psDBBean
     */
    public TmgDivisionTree(PsDBBean psDBBean) {
        this.psDBBean = psDBBean;
        keyArray = DEFAULT_KEY_ARRAY;
    }

    /**
     * コンストラクタ
     * @param psDBBean
     * @param beanDesc
     */
    public TmgDivisionTree(PsDBBean psDBBean, String beanDesc) {
        this.psDBBean = psDBBean;
        keyArray = DEFAULT_KEY_ARRAY;
    }

    public void createDivisionTree(String custId, String compCode, String language, String baseDate) throws Exception{
        String sExists = getDivTreeSearchRange(psDBBean, ContextUtil.getSession());
        String sql1=  buildSQLForSelectOrgTree(custId, compCode, language, baseDate, sExists);
        //最上位組織コードを取得する
        String sql2 =  buildSQLForSelectRootSection(custId, compCode, language, baseDate);
        List entityList;
        Entity rootSection = null;
        log.info("createDivisionTree_SQL1：{}",sql1);
        log.info("createDivisionTree_SQL2：{}",sql2);
        try (Connection connection = dataSource.getConnection()) {
            entityList = SqlExecutor.query(connection,sql1 ,new OrganisationEntityListHandler());
            dataArray = JSONArrayGenerator.entityListTowardList(entityList);
            rootSection= SqlExecutor.query(connection,sql2 ,new EntityHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //rootSectionがnullの場合、上位組織コード：""で返却する
        gsRootSection = rootSection == null ? "":rootSection.getStr("MO_CSECTIONID_CK");
        gbAllDivision = true;
    }

    public String buildSQLForSelectOrgTree(String cust, String comp, String language, String baseDate, String sExists){
        StringBuilder sSQL = new StringBuilder();
        sSQL.append(" SELECT ");
        sSQL.append(    " org.MO_NLEVEL");
        sSQL.append(    ",org.MO_CSECTIONNICK");
        sSQL.append("	|| '(' ||");
        sSQL.append("	(");
        sSQL.append("		select");
        sSQL.append("			count(*)");
        sSQL.append("		from");
        sSQL.append("			HIST_DESIGNATION d");
        sSQL.append("		where");
        sSQL.append("				d.HD_CCUSTOMERID_CK		= org.MO_CCUSTOMERID_CK_FK");
        sSQL.append("			and d.HD_CCOMPANYID_CK		= org.MO_CCOMPANYID_CK_FK");
        sSQL.append("			and d.HD_DSTARTDATE_CK		<= ").append(baseDate);
        sSQL.append("			and d.HD_DENDDATE			>= " + baseDate);
        sSQL.append("			and d.HD_CIFKEYORADDITIONALROLE = 0");
        sSQL.append("			and d.HD_CSECTIONID_FK = org.MO_CSECTIONID_CK ");

        sSQL.append("	)");
        sSQL.append("	|| ')'");

        sSQL.append(    ",org.MO_CSECTIONID_CK");
        sSQL.append(    ",org.MO_CSECTIONNAME");

        sSQL.append("	|| '(' ||");
        sSQL.append("	(");
        sSQL.append("		select");
        sSQL.append("			count(*)");
        sSQL.append("		from");
        sSQL.append("			HIST_DESIGNATION d");
        sSQL.append("		where");
        sSQL.append("				d.HD_CCUSTOMERID_CK		= org.MO_CCUSTOMERID_CK_FK");
        sSQL.append("			and d.HD_CCOMPANYID_CK		= org.MO_CCOMPANYID_CK_FK");
        sSQL.append("			and d.HD_DSTARTDATE_CK		<= ").append(baseDate);
        sSQL.append("			and d.HD_DENDDATE			>= ").append(baseDate);
        sSQL.append("			and d.HD_CIFKEYORADDITIONALROLE = 0");
        sSQL.append("			and d.HD_CSECTIONID_FK = org.MO_CSECTIONID_CK ");

        sSQL.append("	)");
        sSQL.append("	|| ')'");

        sSQL.append(    ",org.MO_CSECTIONNICK");
        sSQL.append(    ",org.MO_CCUSTOMERID_CK_FK");
        sSQL.append(    ",org.MO_CCOMPANYID_CK_FK");
        sSQL.append(    ",org.MO_NSEQ");
        sSQL.append(" FROM ");

        // 閲覧可能組織の上位組織を取得する
        // 重複の可能性があるのでDISTINCTをかける(親が同じ場合そこがその数だけ出てしまう)
        sSQL.append(" ( ");

        sSQL.append(" SELECT DISTINCT ");
        sSQL.append("	m.MO_NLEVEL,　");
        sSQL.append("	m.MO_NSEQ,");
        sSQL.append("	m.MO_CSECTIONNICK,　");
        sSQL.append("	m.MO_CSECTIONID_CK,　");
        sSQL.append("	m.MO_CSECTIONNAME,　");
        sSQL.append("	m.MO_CCUSTOMERID_CK_FK,　");
        sSQL.append("	m.MO_CCOMPANYID_CK_FK,　");
        sSQL.append("	m.MO_CPARENTID　");
        sSQL.append(" FROM ");

        sSQL.append(" ( ");
        sSQL.append(" SELECT ");
        sSQL.append("	o.MO_NLEVEL,　");
        sSQL.append("	o.MO_NSEQ,");
        sSQL.append("	o.MO_CSECTIONNICK,　");
        sSQL.append("	o.MO_CSECTIONID_CK,　");
        sSQL.append("	o.MO_CSECTIONNAME,　");
        sSQL.append("	o.MO_CCUSTOMERID_CK_FK,　");
        sSQL.append("	o.MO_CCOMPANYID_CK_FK,　");
        sSQL.append("	o.MO_CPARENTID　");
        sSQL.append(" FROM ");
        sSQL.append(" 	MAST_ORGANISATION o ");
        sSQL.append(" WHERE ");
        sSQL.append("     o.MO_CCUSTOMERID_CK_FK = ").append(cust);
        sSQL.append(" AND o.MO_CCOMPANYID_CK_FK  = ").append(comp);
        sSQL.append(" AND o.MO_DSTART           <= ").append(baseDate);
        sSQL.append(" AND o.MO_DEND             >= ").append(baseDate);
        sSQL.append(" AND o.MO_CLANGUAGE         = " + language);
        sSQL.append(" ) m ");

        sSQL.append(" START WITH ");
        sSQL.append("     m.MO_CSECTIONID_CK in ");
        sSQL.append(" (SELECT ");
        sSQL.append("		o.MO_CSECTIONID_CK　");
        sSQL.append(" FROM	MAST_ORGANISATION o ");
        sSQL.append(" WHERE ");
        sSQL.append("     o.MO_CCUSTOMERID_CK_FK = ").append(cust);
        sSQL.append(" AND o.MO_CCOMPANYID_CK_FK  = ").append(comp);
        sSQL.append(" AND o.MO_DSTART           <= ").append(baseDate);
        sSQL.append(" AND o.MO_DEND             >= ").append(baseDate);
        sSQL.append(" AND o.MO_CLANGUAGE         = ").append(language);
        sSQL.append(" AND EXISTS(");
        sSQL.append(    " SELECT 1 FROM MAST_GROUPBASESECTION g");
        sSQL.append(    " WHERE");
        sSQL.append(        " g.MGBS_CCUSTOMERID  = o.MO_CCUSTOMERID_CK_FK");
        sSQL.append(    " AND g.MGBS_CCOMPANYID   = o.MO_CCOMPANYID_CK_FK");
        sSQL.append(    " AND g.MGBS_CSECTIONID   = o.MO_CSECTIONID_CK");
        sSQL.append(" AND g.MGBS_DSTARTDATE  <= ").append(baseDate);
        sSQL.append(" AND g.MGBS_DENDDATE    >= ").append(baseDate);
        sSQL.append(" )");
        sSQL.append(sExists);
        sSQL.append(" ) ");
        sSQL.append(" CONNECT BY ");
        sSQL.append("     m.MO_CSECTIONID_CK = PRIOR m.MO_CPARENTID ");
        sSQL.append(" ) org ");

        // 上位組織取得でDISTINCTをかけるため階層を保ったORDER BYができないので再度階層検索をする
        sSQL.append(" START WITH ");
        sSQL.append("     org.MO_CPARENTID is null ");
        sSQL.append(" CONNECT BY ");
        sSQL.append("     org.MO_CPARENTID = PRIOR org.MO_CSECTIONID_CK ");
        sSQL.append(" ORDER SIBLINGS BY  ");
        sSQL.append("     org.MO_NSEQ, ");
        sSQL.append("     org.MO_CSECTIONID_CK ");

        return sSQL.toString();
    }


    private String buildSQLForSelectRootSection(String cust, String comp, String language, String baseDate){
        StringBuilder sSQL = new StringBuilder();

        sSQL.append(" SELECT ");
        sSQL.append("	o.MO_CSECTIONID_CK　");
        sSQL.append(" FROM ");
        sSQL.append(" 	MAST_ORGANISATION o ");
        sSQL.append(" WHERE ");
        sSQL.append("     o.MO_CCUSTOMERID_CK_FK = ").append(cust);
        sSQL.append(" AND o.MO_CCOMPANYID_CK_FK  = ").append(comp);
        sSQL.append(" AND o.MO_DSTART           <= ").append(baseDate);
        sSQL.append(" AND o.MO_DEND             >= ").append(baseDate);
        sSQL.append(" AND o.MO_CLANGUAGE         = ").append(language);
        sSQL.append(" AND o.MO_CPARENTID IS NULL ");

        return sSQL.toString();
    }

    public String getJSONArrayForTreeView(){
        if(dataArray == null){
            return null;
        }
        try{
            return JSONArrayGenerator.getJSONArrayForTreeView(dataArray,keyArray,1);
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 指定された職員の指定されたデータを返します
     * dataArrayに指定された職員が存在しない場合、nullを返します
     * @param targetSectionId 対象職員の職員番号
     * @param keyIndex 取得したいデータのカラム番号(TmgMemberList.DEFAULT_KEY_～を指定)
     * @return String 職員のデータ(指定された職員が存在しない場合、NULL)
     */
    public String getTargetSectionData(String targetSectionId,int keyIndex){
        try{
            for (Object o : dataArray) {
                ArrayList data = (ArrayList) o;
                if (data.get(DEFAULT_KEY_SECID).equals(targetSectionId)) {
                    return (String) data.get(keyIndex);
                }
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 検索対象範囲条件の取得(職員に対する検索対象範囲とは別に分ける。Treeでは上位所属を利用するが職員リストでは出てはいけないため)
     * @param pSession
     * @return
     */
    public String getDivTreeSearchRange(PsDBBean psDBBean, HttpSession pSession) {
        // 検索対象範囲の適用
        String sExists;
        try {
            sExists = searchRangeUtil.getExistsQueryBaseSection(psDBBean, pSession, "o.MO_CSECTIONID_CK");
        }
        catch(Exception e) {
            sExists = "";
        }
        return sExists;
    }

    /**
     * 指定された部署の正式名称を返します
     * dataArrayに指定された部署が存在しない場合、NULLを返します
     * @param targetSectionId
     * @return String 部署正式名称(指定された部署が存在しない場合、NULL)
     */
    public String getTargetSectionName(String targetSectionId){
        return getTargetSectionData(targetSectionId, DEFAULT_KEY_SECNAME);
    }

    /**
     * 指定された部署の略称を返します
     * dataArrayに指定された部署が存在しない場合、NULLを返します
     * @param targetSectionId
     * @return String 部署略称(指定された部署が存在しない場合、NULL)
     */
    public String getTargetSectionNick(String targetSectionId){
        return getTargetSectionData(targetSectionId, DEFAULT_KEY_SECNIC);
    }

    /**
     * 指定された部署の正式名称を返します.
     * {@link #getTargetSectionName(String)}とは異なり、名称末尾の所属人数表記がありません.
     * dataArrayに指定された部署が存在しない場合、NULLを返します
     * @param targetSectionId
     * @return String 部署正式名称(指定された部署が存在しない場合、NULL)
     */
    public String getTargetSectionNameNoCounts(String targetSectionId){
        String result = getTargetSectionData(targetSectionId, DEFAULT_KEY_SECNAME);
        if (result != null) {
            return result.substring(0, result.lastIndexOf('('));
        } else {
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

    public Boolean isAllDivision() {
        return gbAllDivision;
    }

    public String getRootSection() {
        return gsRootSection;
    }

}
