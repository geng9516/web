package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;

import java.text.DateFormat;
import java.util.*;

public class JSONArrayGenerator {

    /**
     * dataArrayについて、指定されたカラムの内容が、指定されたデータと一致するレコードの集合を、Vectorで返します。
     * この操作は、[ SELECT ... FROM ... WHERE AnyColumn = AnyData ]というSQLを実行することと、ほぼ同義となります。
     * TODO Filterクラスを用意して、完全一致以外の複雑な条件も扱えるようにすると便利か？
     * @param dataArray 元のデータ配列
     * @param keyIndex 条件のカラム
     * @param keyDataArray 条件の値
     * @return Vector 「条件のカラム=条件の値[0] and 条件のカラム=条件の値[1] ... 条件のカラム=条件の値[n]」となるレコードの配列
     * @throws Exception
     */
    public static List<String> selectDataArray(List<String> dataArray, int keyIndex, String[] keyDataArray) throws Exception{
        List<String> newDataArray = CollUtil.newArrayList();
        for (String s : dataArray) {
            for (int i = 0; i < keyDataArray.length; i++) {
                if (StrUtil.equals(s,keyDataArray[i])) {
                    newDataArray.add(s);
                    break;
                }
            }
        }
        return newDataArray;
    }

    /**
     * dataArrayについて、指定されたカラムの内容が、指定されたデータと一致するレコードの集合を、Vectorで返します。
     * この操作は、[ SELECT ... FROM ... WHERE AnyColumn = AnyData ]というSQLを実行することと、ほぼ同義となります。
     * TODO Filterクラスを用意して、完全一致以外の複雑な条件も扱えるようにすると便利か？
     * @param dataArray 元のデータ配列
     * @param keyIndex 条件のカラム
     * @param keyDataArray 条件の値
     * @return Vector 「条件のカラム[i]=条件の値[i][0] and 条件のカラム[i]=条件の値[i][2] ... 条件のカラム[i]=条件の値[i][n]」となるレコードの配列
     * @throws Exception
     */
    public static List<List<String>> selectDataArray(List<List<String>> dataArray, int[] keyIndex, String[][] keyDataArray) throws Exception{
        List<List<String>> newDataArray = CollUtil.newArrayList();
        for(Iterator<List<String>> i = dataArray.iterator(); i.hasNext();){
            List<String> data = i.next();
            for(int k = 0; k < keyIndex.length; k++){
                for(int j = 0; j < keyDataArray[k].length; j++){
                    if( StrUtil.equals(data.get(keyIndex[k]),keyDataArray[k][j])){
                        newDataArray.add(data);
                        break;
                    }
                }
            }
        }
        return newDataArray;
    }

    /**
     * startDate～endDateの範囲内について、一部でも歴がかかっているレコードの集合を、Vectorで返します。
     * レコードの歴は、startDateIndex,endDateIndexで指定したカラムのデータをformatでパースした日付で表されます。
     * 「一部でも歴がかかっている」とは、以下の条件に合致することを意味します。
     *    ( レコードの開始日 <= 条件の終了日 ) && ( レコードの終了日 >= 条件の開始日 )
     * なお、startDate > endDate の場合、元のdataArrayを返します
     * TODO TmgGroupListとTmgMemberListで全く同一のメソッドが存在していたので、汎用メソッドとして括り出したものです。
     * TODO 本来、このクラスに作成されるべきメソッドではないかもしれません。
     * @param dataArray
     * @param startDate
     * @param endDate
     * @param starDateIndex
     * @param endDateIndex
     * @param format
     * @return List
     * @throws Exception
     */
    public static List<List<String>> selectDataArrayBetween(
            List<List<String>> dataArray,
            Date startDate,
            Date endDate,
            int starDateIndex,
            int endDateIndex,
            DateFormat format
    ) throws Exception {
        if (startDate.after(endDate)) {
            return dataArray;
        }
        List<List<String>> newDataArray = CollUtil.newArrayList();
        for(Iterator<List<String>> i = dataArray.iterator(); i.hasNext();){
            List<String> data = i.next();
            Date dstart = format.parse(data.get(starDateIndex) );
            Date dend   = format.parse(data.get(endDateIndex) );
            // 元のデータのうち、指定された開始日～終了日の範囲にかかっているデータを拾っていく
            // 以上・以下(<= && >=)という条件で拾い上げるので、equalsメソッドによる完全一致も拾う必要がある
            if( (dstart.before(endDate)||dstart.equals(endDate)) &&
                    (dend.after(startDate) ||dend.equals(startDate))) {
                newDataArray.add(data);
            }
        }
        return newDataArray;
    }

    /**
     * dataArrayについて、指定したキーのカラムだけを抽出したレコードを取得する、擬似クエリを構築して返します。
     * TODO TmgEmpListとTmgMemberListでほぼ同一内容のメソッドを定義していたので、汎用メソッドとして括り出したものです。
     * TODO 本来、このクラスに作成するメソッドではありません。
     * @param dataArray
     * @param keyArray
     * @param selectKeyArray
     * @return String SQL文(擬似クエリ)
     * @throws Exception
     */
    public static String buildSQLForSelectFromDual(
            List dataArray,
            String[] keyArray,
            int[] selectKeyArray
    ) throws Exception{

        StringBuffer sSQL = new StringBuffer("");

        for(Iterator i = dataArray.iterator(); i.hasNext();){
            List data = (List)i.next();

            sSQL.append(" SELECT ");
            for(int col = 0; col < selectKeyArray.length; col++){
                if (col > 0) {
                    sSQL.append(",");
                }
                if("seq".equals(keyArray[selectKeyArray[col]])){
                    sSQL.append((String)data.get(selectKeyArray[col]));
                }else{
                    sSQL.append("'"+escDBSingleQuartString((String)data.get(selectKeyArray[col]))+"'");
                }
                sSQL.append(" as " + keyArray[selectKeyArray[col]]);
            }
            sSQL.append(" FROM DUAL ");

            if(i.hasNext()){
                sSQL.append(" UNION ALL ");
            }
        }

        if(sSQL.length() == 0){
            sSQL.append(" SELECT ");
            for(int col = 0; col < selectKeyArray.length; col++){
                if (col > 0) {
                    sSQL.append(",");
                }
                sSQL.append(" 0 as " + keyArray[selectKeyArray[col]]);
            }
            sSQL.append(" FROM DUAL WHERE 1 = 0 "); // 有り得ない条件を追加しておく
        }

        return sSQL.toString();
    }

    /**
     * #1567
     * 引数の文字列中にシングルクォートがある場合、DB検索可能な様にエスケープ文字を追加編集する
     */
    private static String escDBSingleQuartString(String param) {

        // パラメータがnullの場合を考慮し、null以外の場合のみ置換処理を行う。
        if (param != null) {

            // シングルクォートがある場合、エスケープ文字付きシングルクォートに変換する。
            return param.replace("'", "''");
        }

        return param;
    }

    /**
     * #1567
     * 引数の文字列中にシングルクォートがある場合、画面表示可能な様にエスケープ文字を追加編集する
     */
    private static String escJSSingleQuartString(String param) {

        // パラメータがnullの場合を考慮し、null以外の場合のみ置換処理を行う。
        if (param != null) {

            // シングルクォートがある場合、エスケープ文字付きシングルクォートに変換する。
            return param.replace("'", "\\'");
        }

        return param;
    }

    /**
     * dataArrayの各レコードについて、指定されたカラムの内容が重複するレコードを除去したVectorを返します。
     * 実際には、dataArrayの1レコード目から順に走査していき、重複する2件目以降のレコードが除去されます。
     * distinctKeyArrayで指定されていないカラムの内容は、重複レコードを除去する際には考慮されません。
     * このメソッドは、あるレコードの集合について、各レコードの一部のカラムだけを取り出してdisitinctを行います。
     * OracleなどのRDBMSに備わっているdistinctとは、動作が異なることに注意してください。
     * @param dataArray
     * @param distinctKeyArray
     * @return distinctDataArray dataArrayから重複データを除去したVector
     */
    public static List distinctDataArray(List dataArray, int[] distinctKeyArray) throws Exception{
        List distinctDataArray = new ArrayList();
        Hashtable hash = new Hashtable();

        for (Iterator i = dataArray.iterator(); i.hasNext();){
            List data = (List)i.next();

            StringBuffer key = new StringBuffer();
            for(int j = 0; j < distinctKeyArray.length; j++){
                key.append((String)data.get(distinctKeyArray[j]));
            }

            // 既に登録されたデータの中に、重複するデータが存在しない場合
            if(!hash.containsKey(key.toString())){
                distinctDataArray.add(data);
                hash.put(key.toString(),"");
            }
        }

        return distinctDataArray;
    }

    /**
     * dataRecordとkeyArrayから、JSON書式のオブジェクトを作成します。
     * 生成されたオブジェクトは、JavaScriptでは連想配列として扱われます。
     * 例えば、1番目の要素のキーを"level"とした場合、JavaScriptでは以下の方法で"level"要素にアクセスできます。
     *     var level = obj.level;
     *     var level = obj["level"];
     * @param dataRecord JSON書式のオブジェクトを作成するためのデータ
     * @param keyArray JSON書式のオブジェクトを作成するためのキー
     * @return String JSON書式のオブジェクト
     * @throws Exception
     */
    public static String buildJSONForObject(String[] dataRecord, String[] keyArray) throws Exception{
        StringBuffer sJSON = new StringBuffer("");

        for(int col = 0; col < keyArray.length; col++){
            if(col > 0){
                sJSON.append(",");
            }
            String data = dataRecord[col];
            if(data != null){
                data = escJSSingleQuartString(data);
                sJSON.append(keyArray[col] + ":'" + data + "'");
            }else{
                sJSON.append(keyArray[col] + ":null");
            }
        }

        return "{" + sJSON.toString() + "}";
    }

    /**
     * dataArrayの先頭レコードから順に走査していき、次のレコードが下位階層だった場合、再帰呼出を使用して子どもツリーを生成します。
     * dataArrayの並び順は、ツリーを全て展開した状態と同一である必要があります。
     * @param startRow
     * @param buf
     * @return
     * @throws Exception
     */
    private static int buildJSONArrayForTreeView(String[][] dataArray, String[] keyArray, int levelIndex, int defaultOpenLevel, int startRow, StringBuffer buf) throws Exception{
        int row = startRow;
        int targetLevel = Integer.valueOf(dataArray[row][levelIndex]).intValue();
        int nextLevel = targetLevel;

        buf.append("[");

        do{
            if(row > startRow){
                buf.append(",");
            }

            buf.append("{data:");
            buf.append(buildJSONForObject(dataArray[row], keyArray) );

            row++;
            if(row < dataArray.length){
                nextLevel = Integer.valueOf(dataArray[row][levelIndex]).intValue();
                if(nextLevel <= defaultOpenLevel){
                    buf.append(",open:true");
                }
                if(nextLevel > targetLevel){
                    buf.append(",child:");
                    row = buildJSONArrayForTreeView(dataArray, keyArray, levelIndex, defaultOpenLevel, row, buf);
                }
            }

            buf.append("}");

        }while(row < dataArray.length && Integer.valueOf(dataArray[row][levelIndex]).intValue() >= targetLevel);

        buf.append("]");

        return row;
    }

    /**
     * キー配列から、指定されたキー項目が格納されているインデックス値を返します。
     * 指定されたキー項目がキー配列に存在しなかった場合、-1を返します。
     * なお、キー項目の文字列は、大文字小文字を区別しません。
     * @param keyArray
     * @return
     */
    public static int getKeyIndex(String[] keyArray,String key){
        for(int i = 0; i < keyArray.length; i++){
            if(keyArray[i].toLowerCase().equals(key)){
                return i;
            }
        }
        return -1;
    }

    /**
     * データ配列の階層レベルに基いて、ツリービュー変換用のJSON配列を生成して返します。
     * データ配列は、ツリービューを展開した際の見た目の順序に従って、データが格納されている必要があります。
     * また、1行のデータについて、必ず"LEVEL"要素と"LABEL"要素が含まれている必要があります。
     * @param dataArray データ配列
     * @param keyArray キー配列
     * @return String JSON配列
     * @throws Exception
     */
    public static String getJSONArrayForTreeView(List dataArray, String[] keyArray, int defaultOpenLevel) throws Exception{
        if(dataArray == null || dataArray.size() == 0){
            return null;
        }
        if(keyArray == null || keyArray.length == 0){
            return null;
        }

        int levelIndex = getKeyIndex(keyArray,"level");
        if(levelIndex == -1){
            return null;
        }

        int rowNum = dataArray.size();
        int colNum = ((List)dataArray.get(0)).size();
        String[][] strArray = new String[rowNum][colNum];

        for(int row = 0; row < rowNum; row++){
            for(int col = 0; col < colNum; col++){
                strArray[row][col] = (String)((List)dataArray.get(row)).get(col);
            }
        }

        StringBuffer sJSON = new StringBuffer("");
        buildJSONArrayForTreeView(strArray, keyArray, levelIndex, defaultOpenLevel, 0, sJSON);
        return sJSON.toString();
    }

    /**
     * データ配列の階層レベルに基いて、ツリービュー変換用のJSON配列を生成して返します。
     * データ配列は、ツリービューを展開した際の見た目の順序に従って、データが格納されている必要があります。
     * また、1行のデータについて、必ず"LEVEL"と"LABEL"要素が含まれている必要があります。
     * @param dataArray データ配列
     * @param keyArray キー配列
     * @return String JSON配列
     * @throws Exception
     */
    public static String getJSONArrayForTreeView(List dataArray, String[] keyArray) throws Exception{
        if(dataArray == null || dataArray.size() == 0){
            return null;
        }
        if(keyArray == null || keyArray.length == 0){
            return null;
        }

        int levelIndex = getKeyIndex(keyArray,"level");
        if(levelIndex == -1){
            return null;
        }

        int rowNum = dataArray.size();
        int colNum = ((List)dataArray.get(0)).size();
        String[][] strArray = new String[rowNum][colNum];

        for(int row = 0; row < rowNum; row++){
            for(int col = 0; col < colNum; col++){
                strArray[row][col] = (String)((List)dataArray.get(row)).get(col);
            }
        }

        StringBuffer sJSON = new StringBuffer("");
        buildJSONArrayForTreeView(strArray, keyArray, levelIndex, 0, 0, sJSON);
        return sJSON.toString();
    }

    private static String buildJSONArrayForTreeViewGroupBy(
            List dataArray,
            String[] keyArray,
            int[] groupKey,
            int[] groupLabel,
            int targetIndex,
            String[][] initOpenNodeArray
    ) throws Exception {

        // HashMapのキーにグループキーの値を、要素にグループキーの値を持っているレコードを格納していく
        HashMap hash = new HashMap();
        HashMap label = new HashMap();
        TreeSet treeSet = new TreeSet();
        int groupKeyIndex = groupKey[targetIndex];
        int groupLabelIndex = groupLabel[targetIndex];

        for(int row = 0; row < dataArray.size(); row++){
            // グループ化のキーを取り出す
            String data = (String)((List)dataArray.get(row)).get(groupKeyIndex);

            // 既に同一の値を持つグループが存在する場合、末尾にレコードを追加
               if(hash.containsKey(data)){
                ((List)hash.get(data)).add(dataArray.get(row));
            }
            // そうでない場合、Vectorを作成してHashMapにマッピングしておく
               else{
                   List vecData = new ArrayList();
                   vecData.add(dataArray.get(row));
                   hash.put(data,vecData);
                   label.put(data, ((List)dataArray.get(row)).get(groupLabelIndex));
                   treeSet.add(data);
               }
        }

        StringBuffer buf = new StringBuffer("");
        buf.append("[");
        // TreeSetに格納されている順序でグループ要素を表示する
        for(Iterator i = treeSet.iterator(); i.hasNext();){
            String keyStr = (String)i.next();
            String labelStr = (String)label.get(keyStr);
            Vector groupDataArray = (Vector)hash.get(keyStr);

            String[] data = {keyStr,labelStr,labelStr};
            String[] key  = {keyArray[groupKeyIndex], keyArray[groupLabelIndex], "label"};
            buf.append("{data:");
            buf.append(buildJSONForObject(data,key));
            if(initOpenNodeArray != null){
                for(int k = 0; k < initOpenNodeArray[targetIndex].length; k++){
                    if(initOpenNodeArray[targetIndex][k].equals(keyStr)){
                        buf.append(",open:true");
                        break;
                    }
                }
            }
            buf.append(",child:");

            // グループ化のキーが他にも指定されている場合、再帰呼出を使用して更にグループ化を進める
            if(targetIndex+1 < groupKey.length){
                buf.append(buildJSONArrayForTreeViewGroupBy(groupDataArray, keyArray, groupKey, groupLabel, targetIndex+1, initOpenNodeArray));
            }
            // そうでない場合、切り出された要素を並べていく
            else{
                buf.append("[");
                for(int row = 0; row < groupDataArray.size(); row++){
                    if(row > 0){
                        buf.append(",");
                    }
                    buf.append("{data:");
                    buf.append(buildJSONForObject((List)groupDataArray.get(row),keyArray));
                    buf.append("}");
                }
                buf.append("]");
            }

            buf.append("}");

            if(i.hasNext()){
                buf.append(",");
            }
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * Vector型のdataRecordとkeyArrayから、JSON書式のオブジェクトを作成します。
     * 引数の型以外は、String[]のdataRecordを要求する同名のメソッドと同じ動作です。
     * @param dataRecord JSON書式のオブジェクトを作成するためのデータ
     * @param keyArray JSON書式のオブジェクトを作成するためのキー
     * @return String JSON書式のオブジェクト
     * @throws Exception
     */
    public static String buildJSONForObject(List dataRecord, String[] keyArray) throws Exception{
        StringBuffer sJSON = new StringBuffer("");

        for(int col = 0; col < keyArray.length; col++){
            if(col > 0){
                sJSON.append(",");
            }
            String data = (String)dataRecord.get(col);
            if(data != null){
                data = escJSSingleQuartString(data);
                sJSON.append(keyArray[col] + ":'" + data + "'");
            }else{
                sJSON.append(keyArray[col] + ":null");
            }
        }

        return "{" + sJSON.toString() + "}";
    }

    /**
     * groupKeyで指定された要素に基いて、dataArrayの内容をグループ化してJSON配列を生成します。
     * groupLabelは、グループ化されたデータを階層表示する際の、上位階層のデータのラベルを指定します。
     * このラベルは、グループ化されたデータのうち、1番目に表示されるデータの要素を抽出することに注意してください。
     * 通常、groupKeyはグループ化のためのコード、groupLabelはコードに対応する名称を指定します。
     * なお、ソート順は以下の通りになります。
     *   ・グループ要素：groupKeyの昇順
     *   ・グループ化された要素：dataArrayの順
     * @param dataArray
     * @param keyArray
     * @param groupKey
     * @param groupLabel
     * @return
     * @throws Exception
     */
    public static String getJSONArrayForTreeViewGroupBy(List dataArray, String[] keyArray, int[] groupKey, int[] groupLabel, String[][] initOpenNodeArray) throws Exception{
        if(dataArray == null || dataArray.size() == 0){
            return null;
        }

        if(keyArray == null || keyArray.length == 0){
            return null;
        }

        if(groupKey == null || groupKey.length == 0){
            return null;
        }

        if(groupLabel == null || groupLabel.length == 0){
            return null;
        }

        return buildJSONArrayForTreeViewGroupBy(dataArray,keyArray,groupKey,groupLabel,0,initOpenNodeArray);
    }

    /**
     * groupKeyで指定された要素に基いて、dataArrayの内容をグループ化してJSON配列を生成します。
     * groupLabelは、グループ化されたデータを階層表示する際の、上位階層のデータのラベルを指定します。
     * このラベルは、グループ化されたデータのうち、1番目に表示されるデータの要素を抽出することに注意してください。
     * 通常、groupKeyはグループ化のためのコード、groupLabelはコードに対応する名称を指定します。
     * なお、ソート順は以下の通りになります。
     *   ・グループ要素：groupKeyの昇順
     *   ・グループ化された要素：dataArrayの順
     * @param dataArray
     * @param keyArray
     * @param groupKey
     * @param groupLabel
     * @return
     * @throws Exception
     */
    public static String getJSONArrayForTreeViewGroupBy(List dataArray, String[] keyArray, int[] groupKey, int[] groupLabel) throws Exception{
        if(dataArray == null || dataArray.size() == 0){
            return null;
        }

        if(keyArray == null || keyArray.length == 0){
            return null;
        }

        if(groupKey == null || groupKey.length == 0){
            return null;
        }

        if(groupLabel == null || groupLabel.length == 0){
            return null;
        }

        return buildJSONArrayForTreeViewGroupBy(dataArray,keyArray,groupKey,groupLabel,0,null);
    }

    /**
     * dataArrayについて、指定したキーのカラムだけを抽出したレコードを取得する、擬似クエリを構築して返します。
     * Oracleテーブルオブジェクトの作成版
     * TODO TmgEmpListとTmgMemberListでほぼ同一内容のメソッドを定義していたので、汎用メソッドとして括り出したものです。
     * TODO 本来、このクラスに作成するメソッドではありません。
     * @param dataArray
     * @param keyArray
     * @param selectKeyArray
     * @return String SQL文(擬似クエリ)
     * @throws Exception
     */
    public static String buildSQLForSelectFromDualTableObject(
            List dataArray,
            String[] keyArray,
            int[] selectKeyArray
    ) throws Exception{

        StringBuffer sSQL = new StringBuffer("");

        for(Iterator i = dataArray.iterator(); i.hasNext();){
            List data = (List)i.next();

            //sSQL.append(" SELECT ");
            sSQL.append(" TMG_EMPLIST( ");

            for(int col = 0; col < selectKeyArray.length; col++){
                if (col > 0) {
                    sSQL.append(",");
                }
                if("seq".equals(keyArray[selectKeyArray[col]])){	//2008/08/20 #437 seqのときは数値 order byでのソート順指定用
                    sSQL.append((String)data.get(selectKeyArray[col]));
                }else{
                    sSQL.append("'" + escDBSingleQuartString((String)data.get(selectKeyArray[col])) + "'");
                }
                //sSQL.append(" as " + keyArray[selectKeyArray[col]]);
                sSQL.append(" /* " + keyArray[selectKeyArray[col]] + " */ "  );
            }
            //sSQL.append(" FROM DUAL ");
            sSQL.append(" ) ");

            if(i.hasNext()){
                //sSQL.append(" UNION ALL ");
                sSQL.append(" , ");
            }
        }

        return sSQL.toString();
    }


    /**
     * List<Entity></>からList<List<String>へ変換する。そうすると既存共通方法をそのまま利用できる。
     * @param dataArray
     * @return List
     * @throws Exception
     */
    public static List entityListTowardList(List<Entity> dataArray) throws Exception{

        List<List<String>> dataList = new ArrayList<List<String>>();

         for (Entity data: dataArray) {

            List<String> record = new ArrayList<String>();
            for (Object v: data.values()){
                if (v != null){
                    record.add(v.toString());
                } else if (null == v){
                    record.add("");
                }
            }
             dataList.add(record);
        }
        return dataList;
    }

}
