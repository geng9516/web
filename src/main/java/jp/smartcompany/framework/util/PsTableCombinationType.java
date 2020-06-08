package jp.smartcompany.framework.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.framework.sysboot.dto.TableCombinationTypeDTO;
import jp.smartcompany.boot.util.SysUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * @author Xiao Wenpeng
 */
public class PsTableCombinationType {


    private final static String PT_AND = " AND ";
    private final static String PT_EQUAL = " = ";
    private final static String PT_LESSER = " <= ";
    private final static String PT_GREATER = " >= ";
    private final static String PT_DOT = ".";
    private final static String PT_SYSDATE = " TRUNC(SYSDATE) ";
    private final static String PT_SECURITYDATE = "psSecurityDate";

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定なし)
     */
    public String assemble(String psTableName1, String psTableName2) {
        return this.assemble(psTableName1, "", psTableName2, "", null, true);
    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定なし)
     */
    public String assemble(String psTableName1, String psTableName2, Timestamp pTimestamp) {
        return this.assemble(psTableName1, "", psTableName2, "", pTimestamp, true);
    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定あり)
     */
    public String assemble(String psTableName1, String psTableBraceFolder1, String psTableName2, String psTableBraceFolder2) {
        return this.assemble(psTableName1, psTableBraceFolder1, psTableName2, psTableBraceFolder2, null, true);
    }
    public String assemble(String psTableName1, String psTableBraceFolder1, String psTableName2, String psTableBraceFolder2, Timestamp pTimestamp) {
        return this.assemble(psTableName1, psTableBraceFolder1, psTableName2, psTableBraceFolder2, pTimestamp, true);
    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定なし)
     */
    public String assemble(String psTableName1, String psTableName2, boolean pbUserID) {
        return this.assemble(psTableName1, "", psTableName2, "", null, pbUserID);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定なし)
     */
    public String assemble(String psTableName1, String psTableName2, Timestamp pTimestamp, boolean pbUserID) {
        return this.assemble(psTableName1, "", psTableName2, "", pTimestamp, pbUserID);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定あり)
     */
    public String assemble(String psTableName1, String psTableBraceFolder1, String psTableName2, String psTableBraceFolder2, boolean pbUserID) {
        return this.assemble(psTableName1, psTableBraceFolder1, psTableName2, psTableBraceFolder2, null, pbUserID);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定あり)
     */
    public String assemble(String psTableName1, String psTableBraceFolder1, String psTableName2, String psTableBraceFolder2, Timestamp pTimestamp, boolean pbUserID) {
        ScCacheUtil scCacheUtil = SpringUtil.getBean("scCacheUtil");
        /* テーブル1とテーブル2の結合条件式情報を取得 */
        TableCombinationTypeDTO tableComb1 = scCacheUtil.getTableCombinationType(psTableName1);
        TableCombinationTypeDTO tableComb2 = scCacheUtil.getTableCombinationType(psTableName2);

        /* テーブル1とテーブル2のブレースフォルダを成型 */
        String sBraceFolder1 = null;
        if (!isEmpty(psTableBraceFolder1)) {
            sBraceFolder1 = psTableBraceFolder1 + PT_DOT;
        }
        String sBraceFolder2 = null;
        if (!isEmpty(psTableBraceFolder2)) {
            sBraceFolder2 = psTableBraceFolder2 + PT_DOT;
        }

        /* 参照日付を成型 */
        String sDate;
        if (pTimestamp == null) {
            // 基準日取得
            HttpServletRequest gRequest = ContextUtil.getHttpRequest();
            String sSecurityDate = gRequest.getParameter(PT_SECURITYDATE);
            // 基準日が取得出来ない場合はシステム日付
            if (StrUtil.isNotBlank(sSecurityDate)){
                sDate = " '" + SysUtil.escapeQuote(sSecurityDate) + "' ";
            }else{
                sDate = PT_SYSDATE;
            }
        } else {
            sDate = "TO_DATE('" + SysUtil.transTimestampToString(pTimestamp) + "','yyyy/MM/dd')";
        }

        StringBuilder sbQuery = new StringBuilder();

        if (!isEmpty(tableComb1) && !isEmpty(tableComb2)) {
            if (psTableName1.equals(psTableName2)) {

                /* 同一テーブル */
                sbQuery.append(PT_AND).append(sBraceFolder1)
                        .append(tableComb1.getIdColumnName()).append(PT_EQUAL).append(sBraceFolder2)
                        .append(tableComb2.getIdColumnName());

            } else  {

                if (!isEmpty(tableComb1.getCustomerIdColumnName())
                        && !isEmpty(tableComb2.getCustomerIdColumnName())) {

                    /* 顧客コード */
                    sbQuery.append(PT_AND).append(sBraceFolder1)
                            .append(tableComb1.getCustomerIdColumnName())
                            .append(PT_EQUAL).append(sBraceFolder2)
                            .append(tableComb2.getCustomerIdColumnName());

                }

                if (!isEmpty(tableComb1.getUserIdColumnName())
                        && !isEmpty(tableComb2.getUserIdColumnName())
                        && pbUserID) {

                    /* ユーザID */
                    sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getUserIdColumnName())
                            .append(PT_EQUAL).append(sBraceFolder2).append(tableComb2.getUserIdColumnName());

                } else if (!isEmpty(tableComb1.getCompanyIdColumnName())
                        && !isEmpty(tableComb2.getCompanyIdColumnName())) {

                    /* 法人の結合条件式を作成 */
                    sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getCompanyIdColumnName())
                            .append(PT_EQUAL).append(sBraceFolder2).append(tableComb2.getCompanyIdColumnName());

                    /* 下記は法人の結合条件が成立する場合のみ */

                    if (!isEmpty(tableComb1.getEmployeeIdColumnName())
                            && !isEmpty(tableComb2.getEmployeeIdColumnName())) {

                        sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getEmployeeIdColumnName())
                                .append(PT_EQUAL).append(sBraceFolder2).append(tableComb2.getEmployeeIdColumnName());

                    }

                    /* 所属コード */
                    if (!isEmpty(tableComb1.getSectionIdColumnName())
                            && !isEmpty(tableComb2.getSectionIdColumnName())) {
                        sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getSectionIdColumnName())
                                .append(PT_EQUAL).append(sBraceFolder2).append(tableComb2.getSectionIdColumnName());

                    }
                    /* 役職コード */
                    if (!isEmpty(tableComb1.getPostIdColumnName())
                            && !isEmpty(tableComb2.getPostIdColumnName())) {

                        sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getPostIdColumnName())
                                .append(PT_EQUAL).append(sBraceFolder2).append(tableComb2.getPostIdColumnName());

                    }
                    /* 所属内部階層コード */
                    if (!isEmpty(tableComb1.getLayeredSectionIdColumnName())
                            && !isEmpty(tableComb2.getLayeredSectionIdColumnName())) {

                        sbQuery.append(PT_AND).append(sBraceFolder1)
                                .append(tableComb1.getLayeredSectionIdColumnName())
                                .append(PT_EQUAL).append(sBraceFolder2).append(tableComb2.getLayeredSectionIdColumnName());

                    }

                }

                /* 本務兼務区分 */
                if (!isEmpty(tableComb1.getIfKeyOrAdditionalRoleColumnName())
                        && !isEmpty(tableComb2.getIfKeyOrAdditionalRoleColumnName())) {
                    sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getIfKeyOrAdditionalRoleColumnName())
                            .append(PT_EQUAL).append(sBraceFolder2).append(tableComb2.getIfKeyOrAdditionalRoleColumnName());
                }

            }
        }

        /* 開始日＆終了日の結合 */
        if (!isEmpty(tableComb1) && !isEmpty(tableComb1.getStartDateColumnName()) && !isEmpty(tableComb1.getEndDateColumnName())) {
            sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getStartDateColumnName()).append(PT_LESSER).append(sDate);
            sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getEndDateColumnName()).append(PT_GREATER).append(sDate);
        }
        if (!isEmpty(tableComb2) && !isEmpty(tableComb2.getStartDateColumnName()) && !isEmpty(tableComb2.getEndDateColumnName())) {
            sbQuery.append(PT_AND).append(sBraceFolder2).append(tableComb2.getStartDateColumnName()).append(PT_LESSER).append(sDate);
            sbQuery.append(PT_AND).append(sBraceFolder2).append(tableComb2.getEndDateColumnName()).append(PT_GREATER).append(sDate);
        }

        /* レコード言語区分の限定 */
        if (!isEmpty(tableComb1) && !isEmpty(tableComb1.getLanguageColumnName())) {
            sbQuery.append(PT_AND).append(sBraceFolder1).append(tableComb1.getLanguageColumnName()).append(PT_EQUAL).append("'ja'");
        }
        if (!isEmpty(tableComb2) && !isEmpty(tableComb2.getLanguageColumnName())) {
            sbQuery.append(PT_AND).append(sBraceFolder2).append(tableComb2.getLanguageColumnName()).append(PT_EQUAL).append("'ja'");
        }

        /* 先頭がANDから始まっている場合はこれを除く */
        String sQuery = sbQuery.toString();

        if (sQuery.indexOf(PT_AND) == 0) {
            sQuery = sQuery.substring(PT_AND.length());
        }

        return sQuery;
    }

    /**
     * 空文字判断
     *
     * @param psValue 文字列
     * @return 判断結果 true:空 / false:何らかの文字列
     */
    private boolean isEmpty(String psValue) {
        if (psValue != null && !psValue.equals("")) {
            return false;
        }
        return true;
    }

    private boolean isEmpty(Object poValue) {
        if (poValue != null) {
            return false;
        }
        return true;
    }

}
