package jp.smartcompany.job.modules.personalinformation.conditionsearch.util;

import cn.hutool.core.map.MapUtil;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
public class SqlBuilder {

    /**
     * FROM句リスト
     */
    private final Map<String,String> fromMap = MapUtil.newHashMap(true);
    /**
     * テーブル別名リスト
     */
    private final Map<String,String> tableAliasMap = MapUtil.newHashMap(true);


    /**
     * クエリ組み立てパーツ イコール
     */
    private static final String PT_EQUAL = " = ";
    /**
     * クエリ組み立てパーツ アンド
     */
    private static final String PT_AND  = " AND ";
    /**
     * クエリ組み立てパーツ オア
     */
    private static final String PT_OR  = " OR ";
    /**
     * クエリ組み立てパーツ カンマ(空白あり)
     */
    private static final String PT_COMMA  = " , ";
    /**
     * クエリ組み立てパーツ イン
     */
    private static final String PT_IN  = " IN ";
    /**
     * クエリ組み立てパーツ 空白
     */
    private static final String PT_SPACE  = " ";
    /**
      * クエリ組み立てパーツ 右カッコ
      */
    private static final String PT_OPEN_PAR  = " ( ";
    /**
     * クエリ組み立てパーツ 左カッコ
     */
    private static final String PT_CLOSE_PAR  = " ) ";
    /**
     * クエリ組み立てパーツ 記号(小なり)
     */
    private static final String PT_LESSER  = "<=";
    /**
     * クエリ組み立てパーツ 記号(大なり)
     */
    private static final String PT_GREATER  = ">=";
    /**
     * クエリ組み立てパーツ 記号(小なり)
     */
    private static final String PT_LESS  = "<";
    /**
     * クエリ組み立てパーツ 記号(大なり)
     */
    private static final String PT_GREAT  = ">";
    /**
     * クエリ組み立てパーツ システム日付
     */
    private static final String PT_SYSDATE  = " TRUNC(SYSDATE) ";
    /**
     * クエリ組み立てパーツ NULL
     */
    private static final String PT_NULL = "NULL";
    /**
     * クエリ組み立てパーツ IS NULL
     */
    private static final String PT_IS_NULL = "IS NULL";
    /**
     * クエリ組み立てパーツ IS NOT NULL
     */
    private static final String PT_IS_NOT_NULL  = "IS NOT NULL";
    /**
     * クエリ組み立てパーツ LIKE
     */
    private static final String PT_LIKE = " LIKE ";
    /**
     * クエリ組み立てパーツ %
     */
    private static final String PT_PERCENT = "%";
    /**
     * クエリ組み立てパーツ %
     */
    private static final String PT_SINGLE  = "'";
    /**
     * クエリ組み立てパーツ 縦棒
     */
    private static final String PT_BAR  = "\\|";
    /**
     * 比較用 IS NULL
     */
    private static final String COMPARE_IS_NULL = "ISNULL";
    /**
     * 比較用 IS NOT NULL
     */
    private static final String COMPARE_IS_NOT_NULL = "ISNOTNULL";
    /**
     * 組織マスタ区分
     */
    private static final String MASTER_QSECTION  = "QSECTION";
    /**
     * 役職マスタ区分
     */
    private static final String MASTER_QPOST = "QPOST";
    /**
      * 役職順位マスタ区分
      */
    private static final String MASTER_QPOSTNUM = "QPOSTNUM";
    /**
      * マスタテーブルID 法人マスタ
      */
    private static final String MASTER_QCOMPANY = "QCOMPANY";
    /**
     * ～を含む
     */
    private static final String WRD_PT_IS_CONTAINS_VALUE = "CONTAINS";
    /**
     * ～から始まる
     */
    private static final String WRD_PT_IS_STARTS_WITH_VALUE = "STARTS_WITH";
    /**
     * ～で終わる
     */
    private static final String WRD_PT_IS_ENDS_WITH_VALUE = "ENDS_WITH";

}
