package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @author Wu chenjun
 * @update Xiao Wenpeng
 *      添加 TmgSearchRangeUtil类
 */
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

    private Connection connection;
    private PsDBBean psDBBean;
    private String beanDesc = null;
    private List dataArray = null;
    private String[] keyArray = null;
    /** 検索対象範囲設定を考慮するかどうか */
    private boolean withTarget = true;

    private final DataSource dataSource = SpringUtil.getBean("dataSource");
    private final TmgSearchRangeUtil tmgSearchRangeUtil = SpringUtil.getBean(TmgSearchRangeUtil.class);

    /**
     * コンストラクタ
     * @param psDBBean
     */
    public TmgOrgTree(PsDBBean psDBBean) {
        this.psDBBean = psDBBean;
        keyArray = DEFAULT_KEY_ARRAY;
    }

    /**
     * コンストラクタ
     * @param psDBBean
     * @param beanDesc
     */
    public TmgOrgTree(PsDBBean psDBBean, String beanDesc) {
        this.psDBBean = psDBBean;
        this.beanDesc = beanDesc;
        keyArray = DEFAULT_KEY_ARRAY;
    }

    /**
     *
     * @param bean
     * @param beanDesc
     * @param bWithTarget	検索対象範囲設定を考慮するかどうか（true: 条件に含む、false: 条件に含まない→全件）
     */
    public TmgOrgTree(PsDBBean bean,String beanDesc, boolean bWithTarget) {
        this.psDBBean =  bean;
        this.beanDesc = beanDesc;
        this.beanDesc = beanDesc;
        keyArray = DEFAULT_KEY_ARRAY;
        this.withTarget = bWithTarget;
    }


    public void createOrgTree(String custId, String compCode, String language, String baseDate) throws Exception{
        String sql = buildSQLForSelectOrgTree(custId, compCode, language, baseDate);
        log.info("生成部门sql:{}",sql);
        try (Connection connection = dataSource.getConnection()) {
            List entityList = SqlExecutor.query(connection,sql,new EntityListHandler());
            dataArray = JSONArrayGenerator.entityListTowardList(entityList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    /**
     * 検索対象範囲条件の取得
     * @param psDBBean
     * @return
     */
    public String getOrgTreeSearchRange(PsDBBean psDBBean) {
        // 検索対象範囲の適用
        String sExists;
        try {
            sExists = tmgSearchRangeUtil.getExistsQuery(psDBBean, ContextUtil.getHttpRequest().getSession(),
                    "d.HD_CCOMPANYID_CK", "d.HD_CEMPLOYEEID_CK");
        }
        catch(Exception e) {
            sExists = "";
        }
        return sExists;
    }

    /**
     * 検索対象範囲条件の取得(職員に対する検索対象範囲とは別に分ける。Treeでは上位所属を利用するが職員リストでは出てはいけないため)
     * @param pSession
     * @return
     */
    public String getOrgTreeSearchRangeForTreeBuild(HttpSession pSession) {
        String sExists;
        try {
            //sExists = tmgSearchRangeUtil.getExistsQuery(pRequestHash, pSession, "d.HD_CCOMPANYID_CK", "d.HD_CEMPLOYEEID_CK");
            sExists = tmgSearchRangeUtil.getExistsQueryOrganisation(psDBBean, pSession, "o.MO_CLAYEREDSECTIONID");
        } catch(Exception e) {
            sExists = "";
        }
        return sExists;
    }

    public String buildSQLForSelectOrgTree(String cust, String comp, String language, String baseDate){
        StringBuilder sSQL = new StringBuilder();
        // 検索対象範囲設定を見るかどうかのフラグを参照し、見るときだけ条件に加える
        String sExists = "";
        if (this.withTarget) {
            sExists = getOrgTreeSearchRangeForTreeBuild(Objects.requireNonNull(ContextUtil.getHttpRequest()).getSession());
        }
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
        sSQL.append("			and d.HD_DENDDATE			>= ").append(baseDate);
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
     * 指定された職員の指定されたデータを返します
     * dataArrayに指定された職員が存在しない場合、nullを返します
     * @param targetSectionId 対象職員の職員番号
     * @param keyIndex 取得したいデータのカラム番号(TmgMemberList.DEFAULT_KEY_～を指定)
     * @return String 職員のデータ(指定された職員が存在しない場合、NULL)
     */
    public String getTargetSectionData(String targetSectionId,int keyIndex){
        try{
            for (Object o : dataArray) {
                List data = (List) o;
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

    public List getDataArray() {
        return dataArray;
    }

    public void setDataArray(List dataArray) {
        this.dataArray = dataArray;
    }

    public String[] getKeyArray() {
        return keyArray;
    }

}
