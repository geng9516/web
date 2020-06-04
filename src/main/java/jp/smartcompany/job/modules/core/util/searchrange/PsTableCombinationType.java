package jp.smartcompany.job.modules.core.util.searchrange;

import jp.smartcompany.job.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PsTableCombinationType {

    /** HttpServletRequest **/
    private final HttpServletRequest gRequest;
    private final TableCombinationTypeCache tableCombinationTypeCache;

    private final String PT_AND = " AND ";
    private final String PT_EQUAL = " = ";
    private final String PT_LESSER = " <= ";
    private final String PT_GREATER = " >= ";
    private final String PT_DOT = ".";
    private final String PT_SYSDATE = " TRUNC(SYSDATE) ";
    private final String PT_SECURITYDATE = "psSecurityDate";

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定なし)
     *
     * @param psTableName1
     * @param psTableName2
     * @return
     */
    public String assemble(String psTableName1, String psTableName2) {
        return this.assemble(psTableName1, "", psTableName2, "", null, true);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定なし)
     *
     * @param psTableName1
     * @param psTableName2
     * @return
     */
    public String assemble(String psTableName1, String psTableName2, Timestamp pTimestamp) {
        return this.assemble(psTableName1, "", psTableName2, "", pTimestamp, true);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定あり)
     *
     * @param psTableName1
     * @param psTableBraceFolder1
     * @param psTableName2
     * @param psTableBraceFolder2
     * @return
     */
    public String assemble(String psTableName1, String psTableBraceFolder1, String psTableName2, String psTableBraceFolder2) {
        return this.assemble(psTableName1, psTableBraceFolder1, psTableName2, psTableBraceFolder2, null, true);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定あり)
     *
     * @param psTableName1
     * @param psTableBraceFolder1
     * @param psTableName2
     * @param psTableBraceFolder2
     * @return
     */
    public String assemble(String psTableName1, String psTableBraceFolder1, String psTableName2, String psTableBraceFolder2, Timestamp pTimestamp) {
        return this.assemble(psTableName1, psTableBraceFolder1, psTableName2, psTableBraceFolder2, pTimestamp, true);
    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定なし)
     *
     * @param psTableName1
     * @param psTableName2
     * @return
     */
    public String assemble(String psTableName1, String psTableName2, boolean pbUserID) {
        return this.assemble(psTableName1, "", psTableName2, "", null, pbUserID);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定なし)
     *
     * @param psTableName1
     * @param psTableName2
     * @return
     */
    public String assemble(String psTableName1, String psTableName2, Timestamp pTimestamp, boolean pbUserID) {
        return this.assemble(psTableName1, "", psTableName2, "", pTimestamp, pbUserID);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定あり)
     *
     * @param psTableName1
     * @param psTableBraceFolder1
     * @param psTableName2
     * @param psTableBraceFolder2
     * @return
     */
    public String assemble(String psTableName1, String psTableBraceFolder1, String psTableName2, String psTableBraceFolder2, boolean pbUserID) {
        return this.assemble(psTableName1, psTableBraceFolder1, psTableName2, psTableBraceFolder2, null, pbUserID);

    }

    /**
     * 指定した2つのテーブルの結合条件式を作成(ブレースフォルダ指定あり)
     *
     * @param psTableName1
     * @param psTableBraceFolder1
     * @param psTableName2
     * @param psTableBraceFolder2
     * @return
     */
    public String assemble(String psTableName1, String psTableBraceFolder1, String psTableName2, String psTableBraceFolder2, Timestamp pTimestamp, boolean pbUserID) {

        /* テーブル1とテーブル2の結合条件式情報を取得 */
        TableCombinationType tableComb1 = tableCombinationTypeCache.getTableCombinationType(psTableName1);
        TableCombinationType tableComb2 = tableCombinationTypeCache.getTableCombinationType(psTableName2);

        /* テーブル1とテーブル2のブレースフォルダを成型 */
        String sBraceFolder1 = null;
        if (!isEmpty(psTableBraceFolder1)) {
            sBraceFolder1 = psTableBraceFolder1 + this.PT_DOT;
        }
        String sBraceFolder2 = null;
        if (!isEmpty(psTableBraceFolder2)) {
            sBraceFolder2 = psTableBraceFolder2 + this.PT_DOT;
        }

        /* 参照日付を成型 */
        String sDate = null;
        if (pTimestamp == null) {
            // 基準日取得
            String sSecurityDate = gRequest.getParameter(PT_SECURITYDATE);
            // 基準日が取得出来ない場合はシステム日付
            if (sSecurityDate != null && !sSecurityDate.equals("")){
                sDate = " \'" + SysUtil.escapeQuote(sSecurityDate) + "\' ";
            }else{
                sDate = this.PT_SYSDATE;
            }
        } else {
            sDate = "TO_DATE('" + SysUtil.transTimestampToString(pTimestamp) + "','yyyy/MM/dd')";
        }

        StringBuilder sbQuery = new StringBuilder();

        if (!isEmpty(tableComb1) && !isEmpty(tableComb2)) {
            if (psTableName1.equals(psTableName2)) {

                /* 同一テーブル */
                sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getIdColumnName()
                        + this.PT_EQUAL + sBraceFolder2 +tableComb2.getIdColumnName());

            } else  {

                if (!isEmpty(tableComb1.getCustomerIdColumnName())
                        && !isEmpty(tableComb2.getCustomerIdColumnName())) {

                    /* 顧客コード */
                    sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getCustomerIdColumnName()
                            + this.PT_EQUAL + sBraceFolder2 +tableComb2.getCustomerIdColumnName());

                }

                if (!isEmpty(tableComb1.getUserIdColumnName())
                        && !isEmpty(tableComb2.getUserIdColumnName())
                        && pbUserID) {

                    /* ユーザID */
                    sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getUserIdColumnName()
                            + this.PT_EQUAL + sBraceFolder2 + tableComb2.getUserIdColumnName());

                } else if (!isEmpty(tableComb1.getCompanyIdColumnName())
                        && !isEmpty(tableComb2.getCompanyIdColumnName())) {

                    /* 法人の結合条件式を作成 */
                    sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getCompanyIdColumnName()
                            + this.PT_EQUAL + sBraceFolder2 + tableComb2.getCompanyIdColumnName());

                    /* 下記は法人の結合条件が成立する場合のみ */

                    if (!isEmpty(tableComb1.getEmployeeIdColumnName())
                            && !isEmpty(tableComb2.getEmployeeIdColumnName())) {

                        sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getEmployeeIdColumnName()
                                + this.PT_EQUAL + sBraceFolder2 + tableComb2.getEmployeeIdColumnName());

                    }

                    /* 所属コード */
                    if (!isEmpty(tableComb1.getSectionIdColumnName())
                            && !isEmpty(tableComb2.getSectionIdColumnName())) {

                        sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getSectionIdColumnName()
                                + this.PT_EQUAL + sBraceFolder2 + tableComb2.getSectionIdColumnName());

                    }
                    /* 役職コード */
                    if (!isEmpty(tableComb1.getPostIdColumnName())
                            && !isEmpty(tableComb2.getPostIdColumnName())) {

                        sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getPostIdColumnName()
                                + this.PT_EQUAL + sBraceFolder2 + tableComb2.getPostIdColumnName());

                    }
                    /* 所属内部階層コード */
                    if (!isEmpty(tableComb1.getLayeredSectionIdColumnName())
                            && !isEmpty(tableComb2.getLayeredSectionIdColumnName())) {

                        sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getLayeredSectionIdColumnName()
                                + this.PT_EQUAL + sBraceFolder2 + tableComb2.getLayeredSectionIdColumnName());

                    }

                }

                /* 本務兼務区分 */
                if (!isEmpty(tableComb1.getIfKeyOrAdditionalRoleColumnName())
                        && !isEmpty(tableComb2.getIfKeyOrAdditionalRoleColumnName())) {
                    sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getIfKeyOrAdditionalRoleColumnName()
                            + this.PT_EQUAL + sBraceFolder2 + tableComb2.getIfKeyOrAdditionalRoleColumnName());
                }

            }
        }

        /* 開始日＆終了日の結合 */
        if (!isEmpty(tableComb1) && !isEmpty(tableComb1.getStartDateColumnName()) && !isEmpty(tableComb1.getEndDateColumnName())) {
            sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getStartDateColumnName() + this.PT_LESSER + sDate);
            sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getEndDateColumnName() + this.PT_GREATER + sDate);
        }
        if (!isEmpty(tableComb2) && !isEmpty(tableComb2.getStartDateColumnName()) && !isEmpty(tableComb2.getEndDateColumnName())) {
            sbQuery.append(this.PT_AND + sBraceFolder2 + tableComb2.getStartDateColumnName() + this.PT_LESSER + sDate);
            sbQuery.append(this.PT_AND + sBraceFolder2 + tableComb2.getEndDateColumnName() + this.PT_GREATER + sDate);
        }

        /* レコード言語区分の限定 */
        if (!isEmpty(tableComb1) && !isEmpty(tableComb1.getLanguageColumnName())) {
            sbQuery.append(this.PT_AND + sBraceFolder1 + tableComb1.getLanguageColumnName() + this.PT_EQUAL + "'ja'");
        }
        if (!isEmpty(tableComb2) && !isEmpty(tableComb2.getLanguageColumnName())) {
            sbQuery.append(this.PT_AND + sBraceFolder2 + tableComb2.getLanguageColumnName() + this.PT_EQUAL + "'ja'");
        }

        /* 先頭がANDから始まっている場合はこれを除く */
        String sQuery = sbQuery.toString();

        if (sQuery.indexOf(this.PT_AND) == 0) {
            sQuery = sQuery.substring(this.PT_AND.length());
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
