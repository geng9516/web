package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
     * @return Vector
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

}
