package jp.smartcompany.admin.component.logic.impl;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.admin.component.logic.QueryConditionLogic;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdefinitionsDO;
import jp.smartcompany.job.modules.core.service.IMastDatadictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueryConditionLogicImpl implements QueryConditionLogic {

    private final IMastDatadictionaryService iMastDatadictionaryService;

    /** 異動歴 顧客コード */
    private static final String COL_HD_CUSTOMERID   = "HD_CCUSTOMERID_CK";
    /** 異動歴 法人コード */
    private static final String COL_HD_COMPANYID    = "HD_CCOMPANYID_CK";
    /** 異動歴 社員番号 */
    private static final String COL_HD_EMPLOYEEID   = "HD_CEMPLOYEEID_CK";
    /** 異動歴 開始日 */
    private static final String COL_HD_STARTDATE    = "HD_DSTARTDATE_CK";
    /** 異動歴 終了日 */
    private static final String COL_HD_ENDDATE      = "HD_DENDDATE";
    /** 異動歴 ユーザID */
    private static final String COL_HD_USERID		= "HD_CUSERID";


    /** 異動歴 テーブル */
    private static final String TBL_HIST_DESIGNATION = "HIST_DESIGNATION";

    /** クエリ組み立てパーツ イコール */
    private static final String PT_EQUAL        = " = ";
    /** クエリ組み立てパーツ アンド */
    private static final String PT_AND          = " AND ";
    /** クエリ組み立てパーツ カンマ(空白あり) */
    private static final String PT_COMMA        = " , ";
    /** クエリ組み立てパーツ カンマ(空白なし) */
    /** クエリ組み立てパーツ 空白 */
    private static final String PT_SPACE        = " ";
    /** クエリ組み立てパーツ 右カッコ */
    private static final String PT_OPEN_PAR     = " ( ";
    /** クエリ組み立てパーツ 左カッコ */
    private static final String PT_CLOSE_PAR    = " ) ";
    /** クエリ組み立てパーツ 記号(小なり) */
    private static final String PT_LESSER       = "<=";
    /** クエリ組み立てパーツ 記号(大なり) */
    private static final String PT_GREATER      = ">=";
    /** クエリ組み立てパーツ 記号(小なり) */
    private static final String PT_LESS         = "<";
    /** クエリ組み立てパーツ 記号(大なり) */
    private static final String PT_GREAT        = ">";
    /** クエリ組み立てパーツ システム日付 */
    private static final String PT_SYSDATE      = " TRUNC(SYSDATE) ";
    /** クエリ組み立てパーツ IS NULL */
    private static final String PT_IS_NULL      = "IS NULL";
    /** クエリ組み立てパーツ IS NOT NULL */
    private static final String PT_IS_NOT_NULL  = "IS NOT NULL";
    /** クエリ組み立てパーツ 縦棒 */
    private static final String PT_BAR          = "\\|";

    /** 比較用 IS NULL */
    private static final String COMPARE_IS_NULL      = "ISNULL";
    /** 比較用 IS NOT NULL */
    private static final String COMPARE_IS_NOT_NULL  = "ISNOTNULL";
    /** 組織マスタ区分 **/
    private static final String MASTER_QSECTION = "QSECTION";
    /** 役職マスタ区分 **/
    private static final String MASTER_QPOST = "QPOST";
    /** 役職順位マスタ区分 **/
    private static final String MASTER_QPOSTNUM = "QPOSTNUM";
    /** 全社区分 **/
    private static final String ALL_COMPANIES = "*";

    @Override
    public HistGroupdefinitionsDO insertGroupDefinitions(String psCustomerId,
                                                         String psSystemId, String psGroupId, Date ptStartDate,
                                                         Date ptEndDate, QueryConditionRowDTO poDto, int pnCnt, String psCompanyid) {
        // 入れ替え用Entity
        HistGroupdefinitionsDO oInsert = new HistGroupdefinitionsDO();
        oInsert.setHgdNseq((long)pnCnt);  // シーケンスは現在の表示順に採番し直す
        oInsert.setHgdCandor(poDto.getAndor());
        oInsert.setHgdCclosedparenthsis(poDto.getClosedparenthsis());
        oInsert.setHgdCcolumnid(poDto.getColumnid());
        oInsert.setHgdCcolumnname(poDto.getColumnname());
        oInsert.setHgdCdisplayvalue(poDto.getDisplayvalue());
        oInsert.setHgdCopenedparenthsis(poDto.getOpenedparenthsis());
        oInsert.setHgdCoperator(poDto.getOperator());
        oInsert.setHgdCtableid(poDto.getTableid());
        oInsert.setHgdCtypeofcolumn(poDto.getTypeofcolumn());
        oInsert.setHgdCvalue(poDto.getValue());

        // 引数より渡された値を格納する
        oInsert.setHgdCcustomerid(psCustomerId);
        oInsert.setHgdCsystemid(psSystemId);
        oInsert.setHgdCgroupid(psGroupId);
        oInsert.setHgdDstartdate(ptStartDate);
        if (ptEndDate==null) {
            oInsert.setHgdDenddate(SysUtil.getMaxDateObject());
        } else {
            oInsert.setHgdDenddate(ptEndDate);
        }
        oInsert.setHgdCcompanyid(psCompanyid);
        // IDの有るもの(DBに存在するデータ)は、IDとVersionNoを格納
        if (poDto.getId() != null) {
            oInsert.setHgdId(poDto.getId());
            oInsert.setVersionno(1L);
        }
        return oInsert;
    }

    @Override
    public String createQueryCondition(String psCustomerId, String psCompanyId,
                                String psSystemId, String psGroupId, Date pdSearchDate,List<QueryConditionRowDTO> oInfo) {
        StringBuilder sbQuery = new StringBuilder();
        // 現在、定義されている条件定義情報を取得する
        List <QueryConditionRowDTO> oJoinQuery = CollUtil.newArrayList();

        // 結合式とテーブル取得
        for (QueryConditionRowDTO queryConditionRowDTO : oInfo) {
            String sTableId = queryConditionRowDTO.getTableid();
            List<QueryConditionRowDTO> oResult = CollUtil.newArrayList();

            // 異動歴以外の場合のみ、結合式作成
            if (sTableId != null) {
                // 重複しているテーブルは除く
                if (!sTableId.equals(TBL_HIST_DESIGNATION) && checkDuplicate(oJoinQuery, sTableId)) {
                    oResult = iMastDatadictionaryService.selectGroupJoinQuery(psCustomerId, sTableId);
                }
            }

            if (oResult.size() > 0) {
                oResult.get(0).setTableid(sTableId);
                oJoinQuery.add(oResult.get(0));
            }
        }

        // グループ判定用クエリのヘッダ部を作成する
        // (SELECT句 + 取得カラム + FROM句 + テーブル + WHERE句)
        sbQuery.append(" SELECT ");
        sbQuery.append("   DISTINCT").append(PT_OPEN_PAR);
        sbQuery.append(COL_HD_EMPLOYEEID);
        sbQuery.append(PT_CLOSE_PAR);
        sbQuery.append(PT_COMMA);
        sbQuery.append(COL_HD_CUSTOMERID);
        sbQuery.append(PT_COMMA);
        sbQuery.append(COL_HD_COMPANYID);
        sbQuery.append(PT_COMMA);
        sbQuery.append(COL_HD_USERID);
        sbQuery.append(" FROM ");
        sbQuery.append(TBL_HIST_DESIGNATION);

        // 指定されているテーブル分指定する
        for (QueryConditionRowDTO queryConditionRowDTO : oJoinQuery) {
            sbQuery.append(PT_COMMA);
            sbQuery.append(queryConditionRowDTO.getTableid());
        }

        sbQuery.append(" WHERE ");
        sbQuery.append(COL_HD_CUSTOMERID);
        sbQuery.append(PT_EQUAL);
        sbQuery.append(escDBString(psCustomerId));

        // 指定された法人が全社区分の場合は、法人を条件式に含めない
        if (!psCompanyId.equalsIgnoreCase(ALL_COMPANIES)) {
            sbQuery.append(PT_AND);
            sbQuery.append(COL_HD_COMPANYID);
            sbQuery.append(PT_EQUAL);
            sbQuery.append(escDBString(psCompanyId));
        }

        // グループ判定用クエリの結合式を作成する
        if (oJoinQuery.size() > 0) {
            for (int i = 0; i < oJoinQuery.size(); i++) {
                sbQuery.append(oJoinQuery.get(i).getJoinquery());
            }
        } else {
            sbQuery.append(PT_AND);
            sbQuery.append(COL_HD_STARTDATE);
            sbQuery.append(PT_SPACE);
            sbQuery.append(PT_LESSER);
            sbQuery.append(PT_SPACE);
            sbQuery.append(PT_SYSDATE);
            sbQuery.append(PT_AND);
            sbQuery.append(COL_HD_ENDDATE);
            sbQuery.append(PT_SPACE);
            sbQuery.append(PT_GREATER);
            sbQuery.append(PT_SPACE);
            sbQuery.append(PT_SYSDATE);
        }

        // 条件式が設定されている場合
        if (oInfo.size() > 0) {
            sbQuery.append(PT_AND);
            sbQuery.append(PT_OPEN_PAR);
        }

        // 取得した設定値より、条件式を作成する
        for (QueryConditionRowDTO queryConditionRowDto : oInfo) {

            String sOperator = toBlank(queryConditionRowDto.getOperator());      // 比較演算子
            String sType = toBlank(queryConditionRowDto.getTypeofcolumn());  // データ型
            String sValue = toBlank(queryConditionRowDto.getValue());         // 値
            String sMaster = toBlank(queryConditionRowDto.getMastertablename()); // マスタ項目

            // 比較演算子が特定値以外
            if (!sOperator.equals("")) {

                sbQuery.append(toBlank(queryConditionRowDto.getAndor())).append(PT_SPACE);
                sbQuery.append(toBlank(queryConditionRowDto.getOpenedparenthsis())).append(PT_SPACE);

                if (sOperator.equals(COMPARE_IS_NULL)) {
                    sbQuery.append(queryConditionRowDto.getColumnid()).append(PT_SPACE).append(PT_IS_NULL);
                } else if (sOperator.equals(COMPARE_IS_NOT_NULL)) {
                    sbQuery.append(queryConditionRowDto.getColumnid()).append(PT_SPACE).append(PT_IS_NOT_NULL);
                } else if (sValue.equals("")) {
                    // 値が指定されていない場合は、このクエリは組み立てない
                } else if (sMaster.equals("")) {
                    // マスタ区分未使用
                    sbQuery.append(queryConditionRowDto.getColumnid()
                            + PT_SPACE);
                    sbQuery.append(sOperator + PT_SPACE);
                    // データ型で判定する
                    if (sType.equals("NUMBER")) {
                        sbQuery.append(this.toDBNumber(sValue));
                    } else if (sType.equals("DATE")) {
                        sbQuery.append(this.toDBDate(sValue));
                    } else {
                        sbQuery.append(this.escDBString(sValue));
                    }
                } else if (sMaster.equals(MASTER_QPOSTNUM)) {
                    // 役職順位マスタ区分
                    Pattern pattern = Pattern.compile(PT_BAR);
                    String[] stValue = pattern.split(sValue);
                    sValue = stValue[1];

                    sbQuery.append("HD_CPOSTID_FK IN ");
                    sbQuery.append("( ");
                    sbQuery.append("SELECT MAP_CPOSTID_CK ");
                    sbQuery.append("FROM MAST_POST ");
                    sbQuery.append("WHERE MAP_CCUSTOMERID_CK_FK = "
                            + COL_HD_CUSTOMERID
                            + PT_SPACE);
                    sbQuery.append("AND MAP_CCOMPANYID_CK_FK = "
                            + COL_HD_COMPANYID
                            + PT_SPACE);
                    sbQuery.append("AND MAP_NWEIGHTAGE " + getWeightageOperator(sOperator)
                            + PT_SPACE
                            + this.toDBNumber(sValue)
                            + PT_SPACE);
                    sbQuery.append("AND MAP_CLANGUAGE = 'ja' ");
                    sbQuery.append("AND MAP_DSTART <= TRUNC(SYSDATE) ");
                    sbQuery.append("AND MAP_DEND >= TRUNC(SYSDATE) ");
                    sbQuery.append(")");
                } else if (sMaster.equals(MASTER_QSECTION)
                        || sMaster.equals(MASTER_QPOST)) {
                    // 在職・退職マスタ区分 or 本務・兼務マスタ区分
                    Pattern pattern = Pattern.compile(PT_BAR);
                    String[] stValue = pattern.split(sValue);
                    sValue = stValue[1];

                    sbQuery.append(queryConditionRowDto.getColumnid()
                            + PT_SPACE);
                    sbQuery.append(sOperator + PT_SPACE);
                    // データ型で判定する
                    if (sType.equals("NUMBER")) {
                        sbQuery.append(this.toDBNumber(sValue));
                    } else if (sType.equals("DATE")) {
                        sbQuery.append(this.toDBDate(sValue));
                    } else {
                        sbQuery.append(this.escDBString(sValue));
                    }
                } else {
                    // 一般的なマスタ区分
                    sbQuery.append(queryConditionRowDto.getColumnid()).append(PT_SPACE);
                    sbQuery.append(sOperator).append(PT_SPACE);
                    // データ型で判定する
                    if (sType.equals("NUMBER")) {
                        sbQuery.append(this.toDBNumber(sValue));
                    } else if (sType.equals("DATE")) {
                        sbQuery.append(this.toDBDate(sValue));
                    } else {
                        sbQuery.append(this.escDBString(sValue));
                    }
                }
                sbQuery.append(PT_SPACE);
                sbQuery.append(this.toBlank(queryConditionRowDto.getClosedparenthsis()));
            }
        }

        // 条件式が設定されている場合
        if (oInfo.size() > 0) {
            sbQuery.append(PT_CLOSE_PAR);
        }
        return sbQuery.toString();
    }

    /**
     * 役職順位用比較演算子チェック
     *
     * 役職順位の場合、以下・以上・より下・より上の場合は
     * 演算子の不等号を逆にすること。(順位の場合、数値が少ないほうが上となる)
     *
     * @param   psOperator  比較演算子
     * @return  String 役職順位用比較演算子
     */
    private String getWeightageOperator(String psOperator) {

        String sResult = psOperator;

        // 設定済の比較演算子と比較
        switch (sResult) {
            case PT_GREATER:
                // より上
                sResult = PT_LESSER;
                break;
            case PT_LESSER:
                // より下
                sResult = PT_GREATER;
                break;
            case PT_GREAT:
                // 以上
                sResult = PT_LESS;
                break;
            case PT_LESS:
                // 以下
                sResult = PT_GREAT;
                break;
        }

        return sResult;
    }

    /**
     * 取得した値がNULLの場合は、空白で返却します
     *
     * @param   psString    変換元文字列
     * @return  String      変換結果文字列
     */
    protected String toBlank(String psString) {
        return SysUtil.transNull(psString);
    }

    /**
     * DB更新用に数値であればそのままを、nullであれば0をします。
     *
     * @param   psNumber    変換元文字列
     * @return  String      変換結果文字列
     */
    protected String toDBNumber(String psNumber) {
        return SysUtil.transNumberNullToDB(psNumber);
    }

    /**
     * DB更新用に日付「to_date形式に変換して返します。
     * nullであれば「NULL」の文字列を返します。
     * @param   psDate  変換元文字列
     * @return  String  変換結果文字列
     */
    protected String toDBDate(String psDate) {
        return SysUtil.transDateNullToDB(psDate);
    }

    /**
     * DB更新用に文字列を「'」で囲んで返します。 nullであれば「NULL」の文字列を返します。
     *
     * @param   psString    変換元文字列
     * @return  String      変換結果文字列
     */
    protected String escDBString(String psString) {
        return SysUtil.transStringNullToDB(SysUtil.escapeQuote(psString));
    }

    /**
     * テーブル重複チェック
     *
     * 条件式のテーブル句リストに、既に
     * 同じテーブルが登録されているかどうかチェックします。
     *
     * @param   plFrom  条件式テーブルリスト
     * @param   psTable 対象テーブル
     * @return  boolean チェック結果
     */
    private boolean checkDuplicate(List<QueryConditionRowDTO> plFrom, String psTable) {
        boolean bResult = true;
        // テーブル重複チェック
        for (QueryConditionRowDTO queryConditionRowDTO : plFrom) {
            // 同じテーブルが存在する場合は追加しない
            if (queryConditionRowDTO.getTableid().equals(psTable)) {
                bResult = false;
                break;
            }
        }
        return bResult;
    }

}
