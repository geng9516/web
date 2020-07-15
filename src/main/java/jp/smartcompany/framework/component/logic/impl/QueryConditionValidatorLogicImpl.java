package jp.smartcompany.framework.component.logic.impl;

import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.dto.QueryConditionDTO;
import jp.smartcompany.framework.component.logic.QueryConditionValidatorLogic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class QueryConditionValidatorLogicImpl implements QueryConditionValidatorLogic {

    private ResourceBundle goResourceBundle;

    @PostConstruct
    public void init() {
        goResourceBundle = ResourceBundle.getBundle("jp/smartcompany/framework/component/QueryCondition",
                Locale.JAPAN);
    }

    @Override
    public int checkQueryCondition() {
        return 0;
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
