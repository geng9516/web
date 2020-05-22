package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class TmgGroupList {

    /**
     * 検索結果をJSONへマッピングする際のキー文字列(この文字列をキーとした連想配列が作成される)
     */
    public static final String[] DEFAULT_KEY_ARRAY = {
            "level","label","groupid","groupname","secid","secnic",
            "result","notification","overtime","authority","dstart","dend","cust","comp",
            "formsecid","monthlyapproval","approvallevel","approvallevelname"
    };

    /**
     * 部署ごとに表示する場合に、検索結果をJSONへマッピングするキー文字列
     */
    public static final String[] SEC_KEY_ARRAY = {
            "level","grouplabel","groupid","groupname","secid","label",
            "result","notification","overtime","authority","dstart","dend","cust","comp",
            "formsecid","monthlyapproval","approvallevel","approvallevelname"
    };

    public static final int DEFAULT_KEY_LEVEL        = 0;
    public static final int DEFAULT_KEY_LABEL        = 1;
    public static final int DEFAULT_KEY_GROUPID      = 2;
    public static final int DEFAULT_KEY_GROUPNAME    = 3;
    public static final int DEFAULT_KEY_SECID        = 4;
    public static final int DEFAULT_KEY_SECNIC       = 5;
    public static final int DEFAULT_KEY_RESULT       = 6;
    public static final int DEFAULT_KEY_NOTIFICATION = 7;
    public static final int DEFAULT_KEY_OVERTIME     = 8;
    public static final int DEFAULT_KEY_AUTHORITY    = 9;
    public static final int DEFAULT_KEY_DSTART       = 10;
    public static final int DEFAULT_KEY_DEND         = 11;
    public static final int DEFAULT_KEY_CUST         = 12;
    public static final int DEFAULT_KEY_COMP         = 13;
    public static final int DEFAULT_KEY_SCHEDULE     = 14;
    public static final int DEFAULT_KEY_CFROMSECID      = 15;
    public static final int DEFAULT_KEY_MONTHLYAPPROVAL = 16;
    public static final int DEFAULT_KEY_APPROVALLEVEL   = 17;
    public static final int DEFAULT_KEY_APPROVALLEVELNAME   = 18;

    private PsDBBean psDBBean = null;
    private String beanDesc = null;
    private List dataArray = null;
    private List dataArray1 = null;
    private String[] keyArray = null;

    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
    private String dateFormat = null;

    @Autowired
    private DataSource dataSource;

    /**
     * コンストラクタ
     * @param psDBBean
     */
    @Autowired
    public TmgGroupList(PsDBBean psDBBean) {
        // 下記のコンストラクタのエラー回避のためです。
        this.psDBBean = psDBBean;
        keyArray = DEFAULT_KEY_ARRAY;
        dateFormat = DEFAULT_DATE_FORMAT;
    }

    /**
     * コンストラクタ
     * @param psDBBean
     * @param beanDesc
     */
    public TmgGroupList(PsDBBean psDBBean, String beanDesc) {
        this.psDBBean = psDBBean;
        this.beanDesc = beanDesc;
        keyArray = DEFAULT_KEY_ARRAY;
        dateFormat = DEFAULT_DATE_FORMAT;
    }

    public void createGroupList(String baseDate, String targetDate) throws Exception{
        String sSQL =   buildSQLForSelectGroupList(
                psDBBean.escDBString(psDBBean.getCustID()),
                psDBBean.escDBString(psDBBean.getCompCode()),
                psDBBean.escDBString(psDBBean.getUserCode()),
                baseDate,
                psDBBean.escDBString(psDBBean.getLanguage()),
                psDBBean.escDBString(DEFAULT_DATE_FORMAT)
                );

        Connection connection = null;
        List entityList = null;
        log.info("createGroupList_SQL1：{}",sSQL);
        try {
            connection = dataSource.getConnection();
            entityList = SqlExecutor.query(connection,sSQL ,new EntityListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        dataArray = entityList;
    }

    public String buildSQLForSelectGroupList(
            String cust,
            String comp,
            String emp,
            String baseDate,
            String lang,
            String dateFormat
    ){
        StringBuffer sSQL = new StringBuffer("");

        sSQL.append("SELECT * FROM TABLE(");
        sSQL.append(    "TMG_SELECT_GROUPLIST(");
        sSQL.append(        cust);
        sSQL.append(        ","+comp);
        sSQL.append(        ","+emp);
        sSQL.append(        ","+baseDate);
        sSQL.append(        ","+lang);
        sSQL.append(        ","+dateFormat);
        sSQL.append("))");

        return sSQL.toString();
    }

    /**
     * dataArrayのデータについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、全てのデータをフラットに扱います。
     * @return String ツリービュー用のJSON配列
     * @return
     */
    public String getJSONArrayForTreeView(){
        if(dataArray == null){
            return null;
        }
        try{
            // グループIDでdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = {
                    DEFAULT_KEY_GROUPID
            };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);

            return JSONArrayGenerator.getJSONArrayForTreeView(distinctDataArray,keyArray);
        }catch(Exception e){
            return null;
        }
    }

    /**
     * dataArrayのデータについて、ツリービュー用のJSON配列を生成して返します。
     * このメソッドが返すJSON配列は、全てのデータをフラットに扱います。
     * @return String ツリービュー用のJSON配列
     * @return
     */
    public String getJSONArrayForTreeViewSection(){
        if(dataArray == null){
            return null;
        }
        try{
            // 部署コードでdistinctをかけてから、JSON配列を生成する
            int[] distinctKeyArray = {
                    DEFAULT_KEY_SECID
            };
            List distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);

            return JSONArrayGenerator.getJSONArrayForTreeView(distinctDataArray,SEC_KEY_ARRAY);
        }catch(Exception e){
            return null;
        }
    }

    /**
     * baseDate-targetDateの範囲内について一部でも歴がかかっているグループ一覧を返します。
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
     * dataArrayに格納されているグループについて、重複を排除したグループIDの一覧をIN関数用に整形した文字列として返します
     * @return　String シングルクォートで括られたグループID（カンマ区切り）
     */
    public String getGroupListToDBString(){
        List distinctDataArray = null;
        // グループIDでdistinctをかけてから、JSON配列を生成する
        int[] distinctKeyArray = {
                DEFAULT_KEY_GROUPID
        };
        try{
            distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);
        }catch(Exception e){
            distinctDataArray = dataArray;
        }

        StringBuffer buf = new StringBuffer();
        for(int row = 0; row < distinctDataArray.size(); row++){
            if (row > 0) {
                buf.append(",");
            }
            buf.append("'"+((List)distinctDataArray.get(row)).get(DEFAULT_KEY_GROUPID)+"'");
        }
        return buf.toString();
    }

    /**
     * dataArrayに格納されているグループの親部署ついて、重複を排除した部署コードの一覧をIN関数用に整形した文字列として返します
     * @return　String シングルクォートで括られた部署コード（カンマ区切り）
     */
    public String getSectionListToDBString(){
        List distinctDataArray = null;
        // 部署コードでdistinctをかけてから、JSON配列を生成する
        int[] distinctKeyArray = {
                DEFAULT_KEY_SECID
        };
        try{
            distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);
        }catch(Exception e){
            distinctDataArray = dataArray;
        }

        StringBuffer buf = new StringBuffer();
        for(int row = 0; row < distinctDataArray.size(); row++){
            if (row > 0) {
                buf.append(",");
            }
            buf.append("'"+((List)distinctDataArray.get(row)).get(DEFAULT_KEY_SECID)+"'");
        }
        return buf.toString();
    }

    /**
     * dataAraryに格納されているグループについて、重複を排除したグループIDの一覧をString配列にして返します。
     * @return
     */
    public String[] getGroupListArray(){
        List distinctDataArray = null;
        // グループIDでdistinctをかけてから、JSON配列を生成する
        int[] distinctKeyArray = {
                DEFAULT_KEY_GROUPID
        };
        try{
            distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);
        }catch(Exception e){
            distinctDataArray = dataArray;
        }

        String[] groupListArray = new String[distinctDataArray.size()];

        for(int i = 0; i < distinctDataArray.size(); i++){
            groupListArray[i] = (String)((List)distinctDataArray.get(i)).get(DEFAULT_KEY_GROUPID);
        }

        return groupListArray;
    }

    /**
     * dataAraryに格納されているグループの親部署ついて、重複を排除した部署コードの一覧をString配列にして返します。
     * @return
     */
    public String[] getSectionListArray(){
        List distinctDataArray = null;
        // 部署コードでdistinctをかけてから、JSON配列を生成する
        int[] distinctKeyArray = {
                DEFAULT_KEY_SECID
        };
        try{
            distinctDataArray = JSONArrayGenerator.distinctDataArray(dataArray, distinctKeyArray);
        }catch(Exception e){
            distinctDataArray = dataArray;
        }

        String[] sectionListArray = new String[distinctDataArray.size()];

        for(int i = 0; i < distinctDataArray.size(); i++){
            sectionListArray[i] = (String)((List)distinctDataArray.get(i)).get(DEFAULT_KEY_SECID);
        }

        return sectionListArray;
    }

    /**
     * 指定されたグループの指定されたデータを返します
     * dataArrayに指定されたグループが存在しない場合、nullを返します
     * @param targetGroupId 対象グループのグループID
     * @param keyIndex 取得したいデータのカラム番号(TmgGroupList.DEFAULT_KEY_-を指定)
     * @return String 指定されたグループのデータ(指定されたグループが存在しない場合、NULL)
     */
    public String getTargetGroupData(String targetGroupId, int keyIndex){
        try{
            for(Iterator i = dataArray.iterator(); i.hasNext();){
                List data = (List)i.next();
                if(data.get(DEFAULT_KEY_GROUPID).equals(targetGroupId)){
                    return (String)data.get(keyIndex);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定されたグループのグループ名を返します
     * dataArrayに指定されたグループIDが存在しない場合、NULLを返します
     * @param targetGroupId
     * @return String グループ名(指定されたグループが存在しない場合、NULL)
     */
    public String getTargetGroupName(String targetGroupId){
        return getTargetGroupData(targetGroupId,DEFAULT_KEY_GROUPNAME);
    }

    /**
     * 指定された部署の略称を返します
     * dataArrayに指定された部署が存在しない場合、NULLを返します
     * @param targetSectionId
     * @return String 部署(指定された部署が存在しない場合、NULL)
     */
    public String getTargetSectionName(String targetSectionId){
        for(Iterator i = dataArray.iterator(); i.hasNext();){
            List data = (List)i.next();
            if(data.get(DEFAULT_KEY_SECID).equals(targetSectionId)){
                return (String)data.get(DEFAULT_KEY_SECNIC);
            }
        }
        return null;
    }

    public List getDataArray() {
        return dataArray;
    }

    public void setDataArray(List dataArray) {
        this.dataArray1 = dataArray;
    }

    public String[] getKeyArray() {
        return keyArray;
    }

}
