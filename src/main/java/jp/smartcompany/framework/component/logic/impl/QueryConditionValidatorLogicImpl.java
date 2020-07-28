package jp.smartcompany.framework.component.logic.impl;

import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.dto.QueryConditionDTO;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.component.logic.QueryConditionValidatorLogic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class QueryConditionValidatorLogicImpl implements QueryConditionValidatorLogic {

    private ResourceBundle goResourceBundle;

    /** クエリ組み立てパーツ 空白 */
    private static final String PT_SPACE        = " ";
    /** クエリ組み立てパーツ 縦棒 */
    private static final String PT_BAR          = "\\|";

    /** 比較用 IS NULL */
    private static final String COMPARE_IS_NULL      = "ISNULL";
    /** 比較用 IS NOT NULL */
    private static final String COMPARE_IS_NOT_NULL  = "ISNOTNULL";

    /** エラーフラグ(表示用名称の入力最大桁数) */
    private static final long LIMITVALUE            = 2000;
    /** エラーチェック用(ダブルカッコの桁数) */
    private static final int DOUBLECOUNT            = 2;
    /** エラーチェック用(トリプルカッコの桁数) */
    private static final int TRIPLECOUNT            = 3;
    /** エラーフラグ(SQL不正) */
    private static final int ERR_INVALID_QUERY      = 2;
    /** エラーフラグ(マスタの入力値不正) */
    private static final int ERR_INVALID_DISPVALUE  = 4;
    /** エラーフラグ(比較値の入力値不正) */
    private static final int ERR_INVALID_OPERATOR   = 5;
    /** エラーフラグ(カッコの入力値不正) */
    private static final int ERR_INVALID_PARENTHSIS = 6;

    @PostConstruct
    public void init() {
        goResourceBundle = ResourceBundle.getBundle("jp/smartcompany/framework/component/QueryCondition",
                Locale.JAPAN);
    }

    @Override
    public int checkQueryCondition(List<QueryConditionRowDTO> oInfo) {

        int nResult = 0;
        int nParenthsisCount = 0; // 左右のカッコの数をチェックするためのカウンタ

        // 現在、定義されている条件定義情報を取得する

        // 取得した設定値より、条件式のチェックを行う
        for (QueryConditionRowDTO queryConditionRowDTO : oInfo) {


            String sOperator = SysUtil.transNull(queryConditionRowDTO.getOperator());  // 比較演算子
            String sValue = SysUtil.transNull(queryConditionRowDTO.getValue());            // 値
            String sDispValue = SysUtil.transNull(queryConditionRowDTO.getDisplayvalue());     // 表示名称
            String sOpenedparenthsis = SysUtil.transNull(queryConditionRowDTO.getOpenedparenthsis()); // 左カッコ
            String sClosedparenthsis = SysUtil.transNull(queryConditionRowDTO.getClosedparenthsis()); // 右カッコ

            // 左カッコ使用度
            if (!sOpenedparenthsis.equals("")) {
                if (sOpenedparenthsis.equals("(")) {
                    nParenthsisCount++;
                } else if (sOpenedparenthsis.equals("((")) {
                    nParenthsisCount += DOUBLECOUNT;
                } else if (sOpenedparenthsis.equals("(((")) {
                    nParenthsisCount += TRIPLECOUNT;
                }
            }
            // 右カッコ使用度
            if (!sClosedparenthsis.equals("")) {
                if (sClosedparenthsis.equals(")")) {
                    nParenthsisCount--;
                } else if (sClosedparenthsis.equals("))")) {
                    nParenthsisCount -= DOUBLECOUNT;
                } else if (sClosedparenthsis.equals(")))")) {
                    nParenthsisCount -= QueryConditionValidatorLogicImpl.TRIPLECOUNT;
                }
            }
            // 入力した値の長さチェック
            // V4.0では、Validatorにてチェック

            // 表示用名称の長さチェック
            if (!sDispValue.equals("")
                    && sDispValue.getBytes().length > LIMITVALUE) {
                return ERR_INVALID_DISPVALUE;
            }

            // 演算子と値の整合チェック
            // IS NULL/IS NOT NULL以外の演算子で値がないのはエラー
            if ((sValue.equals("") && !sOperator.equals(
                    COMPARE_IS_NULL))
                    && !sOperator.equals(COMPARE_IS_NOT_NULL)) {
                return ERR_INVALID_OPERATOR;
            }
            // IS NULL/IS NOT NULLでの演算子が指定されていた場合はエラー
            if ((!sValue.equals("") && sOperator.equals(
                    QueryConditionValidatorLogicImpl.COMPARE_IS_NULL)) || !sValue.equals("")
                    && sOperator.equals(COMPARE_IS_NOT_NULL)) {
                return ERR_INVALID_QUERY;
            }
        }
        // カッコの数整合性チェック
        if (nParenthsisCount != 0) {
            return ERR_INVALID_PARENTHSIS;
        }

        return nResult;
    }

    @Override
    public String getMaseterId(String psValue) {
        return null;
    }

    @Override
    public String getDispOperator(String psMaseterId, String psOperator) {
        String sMaseterId = SysUtil.transNull(psMaseterId);
        String sOperator  = SysUtil.transNull(psOperator);
        ResourceBundle goResourceBundle = ResourceBundle.getBundle("jp/smartcompany/framework/component/QueryCondition",
                Locale.JAPAN);
        // 組織・役職が選択されていた場合
        if (sMaseterId.equals("QSECTION") || sMaseterId.equals("QPOST") || sMaseterId.equals("QPOSTNUM")) {

            // 設定済の比較演算子と比較
            switch (psOperator) {
                case ">=":
                    // より上
                    goResourceBundle.getString("WRD_PT_GREATER_LANG");

                    break;
                case "<=":
                    // より下
                    goResourceBundle.getString("WRD_PT_LESSER_LANG");

                    break;
                case ">":
                    // 以上
                    goResourceBundle.getString("WRD_PT_GREAT_LANG");

                    break;
                case "<":
                    // 以下
                    goResourceBundle.getString("WRD_PT_LESS_LANG");

                    break;
                case "ISNULL":
                    // 空白のみ
                    sOperator = goResourceBundle.getString("WRD_PT_IS_NULL_LANG");

                    break;
                case "ISNOTNULL":
                    // 空白以外
                    sOperator = goResourceBundle.getString("WRD_PT_IS_NOT_NULL_LANG");

                    break;
                default:
                    // それ以外は、設定済の比較演算子をそのまま返却する
                    sOperator = psOperator;

                    break;
            }
        } else {
            // 設定済の比較演算子と比較
            switch (psOperator) {
                case ">=":
                    // より上
                    sOperator = goResourceBundle.getString("WRD_PT_DOUBLET_GREATER_SIGN");

                    break;
                case "<=":
                    // より下
                    sOperator = goResourceBundle.getString("WRD_PT_DOUBLET_LESSER_SIGN");

                    break;
                case ">":
                    // 以上
                    sOperator = goResourceBundle.getString("WRD_PT_DOUBLET_GREAT_SIGN");

                    break;
                case "<":
                    // 以下
                    sOperator = goResourceBundle.getString("WRD_PT_DOUBLET_LESS_SIGN");

                    break;
                case "ISNULL":
                    // 空白のみ
                    sOperator = goResourceBundle.getString("WRD_PT_IS_NULL_LANG");

                    break;
                case "ISNOTNULL":
                    // 空白以外
                    sOperator = goResourceBundle.getString("WRD_PT_IS_NOT_NULL_LANG");

                    break;
                case "CONTAINS":
                    // 次が含まれる
                    sOperator = goResourceBundle.getString("WRD_PT_IS_CONTAINS");

                    break;
                case "STARTS_WITH":
                    // 次で始まる
                    sOperator = goResourceBundle.getString("WRD_PT_IS_STARTS_WITH");

                    break;
                case "ENDS_WITH":
                    // 次で始まる
                    sOperator = goResourceBundle.getString("WRD_PT_IS_ENDS_WITH");

                    break;
                default:
                    // それ以外は、設定済の比較演算子をそのまま返却する
                    sOperator = psOperator;

                    break;
            }
        }

        // 共通
        if (psOperator.equals("=")) {
            // 等号
            sOperator = goResourceBundle.getString("WRD_PT_DOUBLET_EQUAL");

        } else if (psOperator.equals("<>")) {
            // 不等号
            sOperator = goResourceBundle.getString("WRD_PT_DOUBLET_NOT_EQUAL");
        }

        return sOperator;
    }

    @Override
    public void setResourceBundle(ResourceBundle resourceBundle) {

    }

    @Override
    public void setQueryConditionDto(QueryConditionDTO queryConditionDto) {

    }
}
