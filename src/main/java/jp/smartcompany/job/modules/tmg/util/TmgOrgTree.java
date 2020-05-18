package jp.smartcompany.job.modules.tmg.util;


import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.job.modules.core.pojo.bo.OrganisationBO;
import jp.smartcompany.job.modules.core.pojo.handler.OrganisationEntityListHandler;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@Component
@Slf4j
public class TmgOrgTree {

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

    private PsDBBean psDBBean = null;
    private String beanDesc = null;
    private ArrayList dataArray = null;
    private ArrayList<OrganisationBO> dataArray1 = null;
    private List dataArray2 = null;
    private String[] keyArray = null;
    /** 検索対象範囲設定を考慮するかどうか */
    private boolean withTarget = true;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public TmgOrgTree() {
        // 下記のコンストラクタのエラー回避のためです。
    }

    public TmgOrgTree(PsDBBean psDBBean, String beanDesc) {
        this.psDBBean = psDBBean;
        this.beanDesc = beanDesc;
        keyArray = DEFAULT_KEY_ARRAY;
    }

    public void createOrgTree(String custId, String compCode, String language, String baseDate) throws Exception{

        String sSQL = buildSQLForSelectOrgTree(custId, compCode, language, baseDate);
        //String sSQL = buildSQLForSelectOrgTree(custId, compCode, language, baseDate, psDBBean.requestHash, psDBBean.session);

        Connection connection;
        ArrayList<OrganisationBO> entityList = null;
        log.info("実行SQL文：｛｝",sSQL);
        try {
            connection = dataSource.getConnection();
            entityList = SqlExecutor.query(connection,sSQL ,new OrganisationEntityListHandler());
            log.info("{}",entityList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //dataArray1 = entityList.get(0);
        dataArray1 = entityList;
    }

    public String buildSQLForSelectOrgTree(String cust, String comp, String language, String baseDate){

        StringBuffer sSQL = new StringBuffer();

        // TODO とりあえず検索対象範囲を見ないとして対応すること。
        // 検索対象範囲設定を見るかどうかのフラグを参照し、見るときだけ条件に加える
        String sExists = "";
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
        sSQL.append("			and d.HD_DSTARTDATE_CK		<= " + baseDate);
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
        sSQL.append("			and d.HD_DSTARTDATE_CK		<= " + baseDate);
        sSQL.append("			and d.HD_DENDDATE			>= " + baseDate);
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
        sSQL.append("     o.MO_CCUSTOMERID_CK_FK = " + cust);
        sSQL.append(" AND o.MO_CCOMPANYID_CK_FK  = " + comp);
        sSQL.append(" AND o.MO_DSTART           <= " + baseDate);
        sSQL.append(" AND o.MO_DEND             >= " + baseDate);
        sSQL.append(" AND o.MO_CLANGUAGE         = " + language);
        sSQL.append(" ) m ");

        sSQL.append(" START WITH ");
        sSQL.append("     m.MO_CSECTIONID_CK in ");
        sSQL.append(" (SELECT ");
        sSQL.append("		o.MO_CSECTIONID_CK　");
        sSQL.append(" FROM	MAST_ORGANISATION o ");
        sSQL.append(" WHERE ");
        sSQL.append("     o.MO_CCUSTOMERID_CK_FK = " + cust);
        sSQL.append(" AND o.MO_CCOMPANYID_CK_FK  = " + comp);
        sSQL.append(" AND o.MO_CSECTIONID_CK <> '999999'");  // 「退職済」を表す部署は除く
        sSQL.append(" AND o.MO_DSTART           <= " + baseDate);
        sSQL.append(" AND o.MO_DEND             >= " + baseDate);
        sSQL.append(" AND o.MO_CLANGUAGE         = " + language);
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

        String sRet = sSQL.toString();
        return sRet;
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
     * 指定された社員の指定されたデータを返します
     * dataArrayに指定された社員が存在しない場合、nullを返します
     * @param targetSectionId 対象社員の社員番号
     * @param keyIndex 取得したいデータのカラム番号(TmgMemberList.DEFAULT_KEY_～を指定)
     * @return String 社員のデータ(指定された社員が存在しない場合、NULL)
     */
    public String getTargetSectionData(String targetSectionId,int keyIndex){
        try{
            for(Iterator i = dataArray.iterator(); i.hasNext();){
                Vector data = (Vector)i.next();
                if(data.elementAt(DEFAULT_KEY_SECID).equals(targetSectionId)){
                    return (String)data.elementAt(keyIndex);
                }
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
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
            if(result.lastIndexOf('(') > 0){
                return result.substring(0, result.lastIndexOf('('));
            }else{
                return result;
            }
        } else {
            return null;
        }
    }

    public ArrayList getDataArray() {
        return dataArray;
    }

    public void setDataArray(List dataArray2) {
        this.dataArray2 = dataArray2;
    }

    public String[] getKeyArray() {
        return keyArray;
    }

}
