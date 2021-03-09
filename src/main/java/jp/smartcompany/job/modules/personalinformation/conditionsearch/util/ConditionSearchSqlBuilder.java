package jp.smartcompany.job.modules.personalinformation.conditionsearch.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.sysboot.dto.TableCombinationTypeDTO;
import jp.smartcompany.framework.sysinfo.logic.SysInfoLogic;
import jp.smartcompany.framework.util.PsBuildTargetSql;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.bo.SqlBO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor
public class ConditionSearchSqlBuilder {

    private final ScCacheUtil cacheUtil;
    private final SysInfoLogic sysInfoLogic;
    private final PsBuildTargetSql buildTargetSql;

    /**
     * 別名記号
     * */
    private static final String ALIAS_MARK = "_F";

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

    private static final String DATE_FORMAT = PsConst.DATE_YYYYMMDD;

    private static final String TABLE_MAIN = "MAIN_TABLE";
    private static final String TABLE_MAST_EMPLOYEES = "MAST_EMPLOYEES";
    private static final String PROP_JK_SYSDATE_MODE = "JkSysdateMode";
    private static final String COLUMN_ME_CCUSTOMERID = "ME_CCUSTOMERID_CK";
    /**
     * 異動歴の開始日
     */
    private static final String COLUMN_ME_DSTARTDATE = "ME_DSTARTDATE";
    /**
     * 異動歴の終了日
     */
    private static final String COLUMN_ME_DENDDATE = "ME_DENDDATE";

    /**
     * 異動歴のユーザID
     */
    private static final String COLUMN_ME_CUSERID = "ME_CUSERID";


    /**
     * 基準日時点のデータを検索
     */
    private static final int FLAG_CRITERIALDATE = 1;

    /**
     * 異動歴の法人コード
     * */
    private static final String COLUMN_ME_COMPANYID = "ME_CCOMPANYID";


    private int tableAliasCounter = 0;
    private int nAliasCounter = 0;

    /** テーブル別名記号 **/
    static final String TABLE_ALIAS_MARK = "T";

    /** 自分のフラグ 通常の比較 */
    private static final String MY_FLG0  = "0";
    /** 自分のフラグ 自分自身との比較 */
    private static final String MY_FLG1  = "1";

    private PsSession psSession;
    private String searchRangeWhere;

    /** 2021/02/03 Xiao Wenpeng 基準日指定フラグ */
    private static final int DATEFLAG = 1;

    /** DATE型 **/
    private static final String TYPE_DATE = "DATE";
    /** NUMBER型 **/
    private static final String TYPE_NUMBER = "NUMBER";

    /** 本務兼務区分 **/
    private static final String MASTER_HONKEN = "QHONKEN";
    /** 組織マスタ区分 **/
    private static final String MASTER_SECTION = "QSECTION";
    /** 役職マスタ区分 **/
    private static final String MASTER_POST = "QPOST";
    /** 在職退職区分 **/
    private static final String MASTER_ZAITAI = "QZAITAI";

    /** 基準日（置換文字列） **/
    private static final String REPSTR_DATE = "##DATE##";
    /** 日付フォーマット（置換文字列） **/
    private static final String REPSTR_DATEFORMAT = "##DATE_FORMAT##";

    /**
     * SQL組立処理d
     * @param settingDto 検索条件
     * @return sql语句拼装对象
     */
    public SqlBO createSql(ConditionSettingDTO settingDto) {
        psSession = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
        SqlBO sqlBO = new SqlBO();

        List<ConditionSelectDTO> selectDtoList = settingDto.getSelectDtoList();
        List<ConditionWhereDTO> whereDtoList = settingDto.getWhereDtoList();
        List<QueryConditionRowDTO> whereQueryDtoList = settingDto.getQueryConditionDtoList();
        List<ConditionOrderDTO> orderDtoList = settingDto.getOrderDtoList();
        List<String> joinWhereList;
        String customerId = "01";
        String companyId = settingDto.getCompanyId();
        String allCompany = settingDto.getHseCcompanyselect();
        Integer standardDateType = settingDto.getStandardDateType();
        Date standardDate = settingDto.getStandardDate();
        String useCoop = settingDto.getHseCusecooperation();
        Long coopDataId = settingDto.getHseNdataId();

        if (StrUtil.equals(allCompany,"1")) {
            companyId = PsConst.CODE_ALL_COMPANIES;
        }
        Boolean mastCodeFlag = settingDto.getShowMastCode();

        // SELECT句の組立
        StringBuilder selectSql = new StringBuilder();
        for (ConditionSelectDTO conditionSelectDTO : selectDtoList) {
            selectSql.append(
                    createSelectSql(conditionSelectDTO,customerId,SysUtil.transDateToString(standardDate),mastCodeFlag,standardDateType)
                    ).append(",");
        }
        // 末尾の,を取り除く
        int lastCommaIndex = selectSql.lastIndexOf(",");
        String selectStatement = selectSql.substring(0,lastCommaIndex);
        sqlBO.setSelectStatement(selectStatement);

        // WHERE句（検索対象範囲）の組立
        String searchRangeWhere = getSearchRangeWhere();
        String searchCoopWhere = getSearchCoopWhere(useCoop , coopDataId);
        String conditionWhere = null;
        // チェック
        if (CollUtil.isNotEmpty(whereDtoList)) {
            // WHERE句（絞込条件）の組立(簡易版)
            conditionWhere = getConditionWhereSql(whereDtoList);
        } else if (CollUtil.isNotEmpty(whereQueryDtoList)){
            // WHERE句（絞込条件）の組立(定義式)
            conditionWhere = createQueryCondition(companyId,whereQueryDtoList);
        }
        if (StrUtil.isNotBlank(conditionWhere)) {
            sqlBO.setWhereConditionStatement(conditionWhere + searchRangeWhere + searchCoopWhere);
        }

// ----- feats: 老代码出现了这段，新代码觉得这段会产生问题，在此注释 修改日期： 2021/02/24 18:24 ----
//        else {
//            sqlBO.setWhereConditionStatement(searchRangeWhere + searchCoopWhere);
//        }
        if (CollUtil.isNotEmpty(orderDtoList)) {
            StringBuilder orderSql = new StringBuilder();
            for (ConditionOrderDTO conditionOrderDTO : orderDtoList) {
                orderSql.append(getOrderSql(conditionOrderDTO)).append(",");
            }
            // 末尾の,を取り除く
            int lastOrderCommaIndex = orderSql.lastIndexOf(",");
            String orderStatement = orderSql.substring(0,lastOrderCommaIndex);
            sqlBO.setOrderStatement(orderStatement);
        }

        // メインテーブルの追加
        fromMap.put(TABLE_MAIN,TABLE_MAST_EMPLOYEES+" M1");

        // WHERE句（結合条件）の組立
        joinWhereList = getJoinWhereSqlList(standardDateType, customerId, companyId, standardDate);
        sqlBO.setJoinWhereStatementList(joinWhereList);

        // FROM句リストの更新
        sqlBO.setFromStatementList(getFromList());

        return sqlBO;
    }

    /**
     * カラムID情報からSELECT句を組み立てます
     *
     * @param pConditionSelectDto SELECT条件
     * @param psCustomerId 顧客コード
     * @param psCriterialDate 基準日
     * @param pbMasterCodeFlag コード表示フラグ
     * @return SELECT句
     */
    private String createSelectSql(ConditionSelectDTO pConditionSelectDto,
                                  String psCustomerId,
                                  String psCriterialDate,
                                  boolean pbMasterCodeFlag,
                                  int psDateFlag) {
        // 表示対象カラムIDの取得
        String sColumnId = pConditionSelectDto.getHssCcolumn();

        // データディクショナリ情報の参照
        MastDatadictionaryDO dataDictionary = cacheUtil.getDataDictionary(
                "01" + "_" + sColumnId);
        String sTableId = dataDictionary.getMdCtablename();
        String sColumnType = dataDictionary.getMdCtypeofcolumn();
        String sCalcExp = dataDictionary.getMdCcalculatecolumn();
        String sMasterType = dataDictionary.getMdCmastertblname();

        // from句Mapに追加
        if (fromMap.get(sTableId.toUpperCase()) == null) {
            setTableAlias(sTableId);
            fromMap.put(sTableId.toUpperCase(), sTableId.toUpperCase() + " " + getTableAlias(sTableId.toUpperCase()));
        }
        // 計算式の場合
        if (sCalcExp != null) {
            return getCalcExpSql(sCalcExp, psCriterialDate, sColumnId);
        }
        // マスタ参照の場合
        if (sMasterType != null) {
            String sMasterSelect = "";
            String sSearchDate;
            // 2010/04/06 Sakaguchi 「指定日付時点のデータ検索」か「履歴も全て検索」の判定
            if(psDateFlag == DATEFLAG){
                sSearchDate = "TO_DATE('" + psCriterialDate + "', 'yyyy/MM/DD')" ;
            } else {
                sSearchDate = getTableAlias(sTableId) + "." + getEndDate(sTableId);
            }

            // マスタコード表示ありの時
            if (pbMasterCodeFlag) {
                sMasterSelect =
                        getTableAlias(sTableId) + "." + sColumnId + " AS " + getAlias(sColumnId) + ",";
            }
            // 組織マスタの時
            if (sMasterType.equals(MASTER_SECTION)) {
                sMasterSelect += getSectionMasterSql(
                        sColumnId, getTableAlias(sTableId) + ".", psCustomerId, getCompanyId(sTableId), sSearchDate);
                // 役職マスタの時
            } else if (sMasterType.equals(MASTER_POST)) {
                sMasterSelect += getPostMasterSql(
                        sColumnId, getTableAlias(sTableId) + ".", psCustomerId, getCompanyId(sTableId), sSearchDate);
                // 名称マスタの時
            } else {
                sMasterSelect += getGenericMasterSql(
                        sMasterType, sColumnId, getTableAlias(sTableId) + ".", psCustomerId, getCompanyId(sTableId), sSearchDate);
            }
            return sMasterSelect;
        }
        // マスタ以外の時
        // 日付型の時
        if (sColumnType.equals(TYPE_DATE)) {
            return getDateTypeSql(getTableAlias(sTableId) + ".", sColumnId);
            // 数値型の時
        } else if (sColumnType.equals(TYPE_NUMBER)) {
            return getNumberTypeSql(getTableAlias(sTableId) + ".", sColumnId);
            // その他の時
        } else {
            return getOtherTypeSql(getTableAlias(sTableId) + ".", sColumnId);
        }
    }

    /**
     * その他カラム用SQL作成
     * @param psColumnId カラムID
     */
    protected String getOtherTypeSql(String psTableAlias, String psColumnId) {
        return psTableAlias + psColumnId;
    }

    /**
     * 数値カラム用SQL作成
     * @param psColumnId カラムID
     */
    private String getNumberTypeSql(String psTableAlias, String psColumnId) {
        return "TO_CHAR(" + psTableAlias + psColumnId + ") AS " + psColumnId;
    }

    /**
     * 日付型カラム用SQL作成
     * @param psColumnId カラムID
     * @return SQL
     */
    private String getDateTypeSql(String psTableAlias, String psColumnId) {
        return "TO_CHAR(" + psTableAlias + psColumnId + ",'" + DATE_FORMAT + "') AS " +
                        psColumnId;
    }

    /**
     * 名称マスタ参照カラム用SQL作成
     * @param psColumnId カラムID
     * @param psCustomerId 顧客コード
     * @param psCompanyId 法人コード
     * @param psCriterialDate 基準日
     */
    private StringBuilder getGenericMasterSql(
            String psMasterType,
            String psColumnId,
            String psTableAlias,
            String psCustomerId,
            String psCompanyId,
            String psCriterialDate) {
        StringBuilder sbSQL = new StringBuilder();
        // 本務兼務区分、在職退職区分の時は区分とコードを分けて渡す
        if (StrUtil.equalsAnyIgnoreCase(psMasterType,MASTER_HONKEN,MASTER_ZAITAI)) {
            sbSQL.append("PSMASTER.FUNC_GET_GENERICDETAIL_DESC_EX(");
            sbSQL.append("'").append(psCustomerId).append("',");	// 顧客
            sbSQL.append(psTableAlias).append(psCompanyId).append(",");		// 法人
            sbSQL.append("'").append(psMasterType).append("',");	// 名称マスタID
            sbSQL.append(psTableAlias).append(psColumnId).append(","); 			// 明細データコード
        } else {
            sbSQL.append("PSMASTER.FUNC_GET_GENERICDETAIL_DESC(");
            sbSQL.append("'").append(psCustomerId).append("',");	// 顧客
            sbSQL.append(psTableAlias).append(psCompanyId).append(",");		// 法人
            sbSQL.append(psTableAlias).append(psColumnId).append(","); 			// マスタコード
        }
        sbSQL.append(psCriterialDate)
                .append(" ,"); // 基準日
        sbSQL.append("'ja') AS ")
                .append(getAlias(psColumnId));
        return sbSQL;
    }

    /**
     * 役職マスタ参照カラム用SQL作成
     * @param psColumnId カラムID
     * @param psCustomerId 顧客コード
     * @param psCompanyId 法人コード
     * @param psCriterialDate 基準日
     * @return SQL
     */
    private String getPostMasterSql(String psColumnId,
                                      String psTableAlias,
                                      String psCustomerId,
                                      String psCompanyId,
                                      String psCriterialDate) {
        return "PSMASTER.FUNC_GET_POST_NAME(" +
                        "'" + psCustomerId + "'," + // 顧客
                        psTableAlias + psCompanyId + "," + // 法人
                        psTableAlias + psColumnId + "," +  // 役職
                        psCriterialDate + "," + // 基準日
                        "'ja') AS " + getAlias(psColumnId);
    }

    /**
     * 組織マスタ参照カラム用SQL作成
     * @param psColumnId カラムID
     * @param psCustomerId 顧客コード
     * @param psCompanyId 法人コード
     * @param psCriterialDate 基準日
     * @return SQL
     */
    private String getSectionMasterSql(String psColumnId,
                                         String psTableAlias,
                                         String psCustomerId,
                                         String psCompanyId,
                                         String psCriterialDate) {
        return "PSMASTER.FUNC_GET_SECTION_NAME(" +
                        "'" + psCustomerId + "'," + // 顧客
                        psTableAlias + psCompanyId + "," + // 法人
                        psTableAlias + psColumnId + "," +  // 組織
                        psCriterialDate + "," + // 基準日
                        "'js') AS " + getAlias(psColumnId);
    }

    /**
     * 計算式設定カラム用SQL組立処理
     * @param psCalcExp 計算式
     * @param psCriterialDate 基準日
     * @param psColumnId カラムID
     * @return SQL
     */
    private String getCalcExpSql(String psCalcExp,
                                   String psCriterialDate,
                                   String psColumnId) {
        String sCalcExp = psCalcExp;
        // 計算式内で使用されているテーブル名を別名に置換する
        Set<String> aliasSet = tableAliasMap.keySet();
        for (String sKey : aliasSet) {
            String sAlias = tableAliasMap.get(sKey);
            if (sCalcExp.contains(sKey)) {
                sCalcExp = sCalcExp.replaceAll(sKey, sAlias);
            }
        }
        // 計算式の置換文字列を置き換えて返す
        String pResult = SysUtil.replaceStringIgnoreCase(
                sCalcExp, REPSTR_DATE, psCriterialDate);
        pResult = SysUtil.replaceStringIgnoreCase(
                pResult, REPSTR_DATEFORMAT, DATE_FORMAT);
        pResult += " AS " + psColumnId;
        return pResult;
    }

    private String getSearchRangeWhere() {
        searchRangeWhere = buildTargetSql.getExistsQuery("M1.ME_CUSERID");
        return searchRangeWhere;
    }

    private String getSearchCoopWhere(String psUseCoop, Long pnCoopDataId) {
        StringBuilder sbSql = new StringBuilder();
        if(StrUtil.equals(psUseCoop,"1")) {
            String sCoopDataId = pnCoopDataId.toString();
            sbSql.append(" AND EXISTS ( SELECT 1 FROM HIST_SEARCH_COOP_DETAIL WHERE HSD_NDATA_ID = ")
                 .append("'")
                 .append(sCoopDataId)
                 .append("' AND ")
                 .append("M1.ME_CCUSTOMERID_CK = HSD_CCOND_CUSTOMERID and M1.ME_CUSERID = HSD_CCOND_USERID)");
        }
        return sbSql.toString();
    }

    /**
     * 設定条件からWHERE句（絞り込み条件）を組み立てます
     *
     * @param conditionWhereDtoList WHERE条件
     * @return WHERE句
     */
    private String getConditionWhereSql(List<ConditionWhereDTO> conditionWhereDtoList) {
        StringBuilder sbWhereResult = new StringBuilder();
        int nCond = 0;
        // 利用される中で何番目の条件か
        for (ConditionWhereDTO conditionWhereDto : conditionWhereDtoList) {
            String sColumnId = conditionWhereDto.getMswCcolumnid();
            String sTableId = conditionWhereDto.getMswCtableid();
            List<ConditionWhereValueDTO> valueList = conditionWhereDto.getSelectValue();
            StringBuilder sbWhere = new StringBuilder();
            //使用しない場合は条件式に加えない
            if(!conditionWhereDto.getUse()){
                continue;
            } else {
                nCond++;
            }
            // from句Mapに追加
            if (fromMap.get(sTableId.toUpperCase()) == null) {
                setTableAlias(sTableId);
                fromMap.put(sTableId.toUpperCase(), sTableId.toUpperCase() + " " + getTableAlias(sTableId));
            }
            for (ConditionWhereValueDTO conditionWhereValueDto : valueList) {
                sbWhere.append("'")
                        .append(SysUtil.escapeQuote(conditionWhereValueDto.getHswCvalue()))
                        .append("',");
            }
            // 末尾の,を取り除いてin句を作成
            if (sbWhere.length() > 0) {
                if(nCond != 0){
                    sbWhereResult.append(" and ");
                }
                sbWhere.replace(0, sbWhere.length(), sbWhere.substring(0, sbWhere.length() - 1));
                sbWhereResult
                        .append(getTableAlias(sTableId))
                        .append(".")
                        .append(sColumnId)
                        .append(" in (");
                sbWhereResult.append(sbWhere);
                //andは頭につける
                //sbWhereResult.append(") and ");
                sbWhereResult.append(") ");
            }
        }
        // 末尾のANDを取り除く→あたまにandをつける形式へ変える
        //sbWhereResult.replace(0, sbWhereResult.length(), sbWhereResult.substring(0, sbWhereResult.length() - " and ".length()));

        return sbWhereResult.toString();
    }


    /**
     * クエリ作成処理(条件式定義)<br>
     * 条件式定義での設定内容を元に、検索クエリを作成する。
     * @param psCompanyId   法人コード
     * @return  String      グループ判定用クエリ(条件式定義)
     */
    private String createQueryCondition(
            String psCompanyId,
            List<QueryConditionRowDTO> conditionWhereDtoList) {
        // 初期化
        StringBuilder sbQuery = new StringBuilder();
        boolean tableCheck = false;
        // テーブル取得
        for (QueryConditionRowDTO queryConditionRowDTO : conditionWhereDtoList) {
            // テーブルID待避
            String tableId = queryConditionRowDTO.getTableid();
            if (StrUtil.isNotBlank(tableId)){
                if (fromMap.get(tableId.toUpperCase()) == null) {
                    // 別名取得用
                    setTableAlias(tableId);
                    fromMap.put(tableId.toUpperCase(), tableId.toUpperCase() + " " + getTableAlias(tableId));
                }
                // 取得できた場合はSQL組立
                tableCheck = true;
            }
        }
        // 組立チェック
        if (!tableCheck){
            return "";
        }
        // カウンタ
        int iIndex = 0;
        int subIndex = 0;
        // 取得した設定値より、条件式を作成する
        for (QueryConditionRowDTO queryConditionRowDTO : conditionWhereDtoList) {
            String sTableId	= toBlank(queryConditionRowDTO.getTableid());
            // 比較演算子
            String sOperator = toBlank(queryConditionRowDTO.getOperator());
            // データ型
            String sType = toBlank(queryConditionRowDTO.getTypeofcolumn());
            // 値
            String sValue = toBlank(queryConditionRowDTO.getValue());
            // マスタ項目
            String sMaster = toBlank(queryConditionRowDTO.getMastertablename());
            // 表示名称
            String sDispValue = toBlank(queryConditionRowDTO.getDisplayvalue());
            // 左カッコ
            String sOpenedparenthsis = toBlank(queryConditionRowDTO.getOpenedparenthsis());
            // 右カッコ
            String sClosedparenthsis = toBlank(queryConditionRowDTO.getClosedparenthsis());
            // カウント(空白を含めた全ての行用)
            subIndex++;
            // すべて""の場合チェックを行わない
            if (StrUtil.isNotBlank(sOperator) || StrUtil.isNotBlank(sValue) || StrUtil.isNotBlank(sDispValue)
            || StrUtil.isNotBlank(sOpenedparenthsis) || StrUtil.isNotBlank(sClosedparenthsis)) {
                // 開始括弧
                if(iIndex == 0){
                    sbQuery.append(PT_AND);
                    sbQuery.append(PT_OPEN_PAR);
                }
                // 比較演算子が特定値以外
                if (StrUtil.isNotBlank(sOperator)) {
                    sbQuery.append(toBlank(queryConditionRowDTO.getAndor()))
                            .append(PT_SPACE);
                    sbQuery.append(toBlank(queryConditionRowDTO.getOpenedparenthsis()))
                            .append(PT_SPACE);
                    if (StrUtil.equals(sOperator,COMPARE_IS_NULL)) {
                        sbQuery.append(getTableAlias(sTableId))
                                .append(".")
                                .append(queryConditionRowDTO.getColumnid())
                                .append(PT_SPACE)
                                .append(PT_IS_NULL);
                    } else if (StrUtil.equals(sOperator,COMPARE_IS_NOT_NULL)) {
                        sbQuery.append(getTableAlias(sTableId))
                                .append(".")
                                .append(queryConditionRowDTO.getColumnid())
                                .append(PT_SPACE)
                                .append(PT_IS_NOT_NULL);
                    } else if (StrUtil.isBlank(sValue)) {
                        // 値が指定されていない場合は、このクエリは組み立てない
                    } else if (StrUtil.isBlank(sMaster)) {
                        // マスタ区分未使用
                        sbQuery.append(getTableAlias(sTableId))
                                .append(".")
                                .append(queryConditionRowDTO.getColumnid())
                                .append(PT_SPACE);
                        // LIKE検索の場合
                        if(StrUtil.equals(sOperator,WRD_PT_IS_CONTAINS_VALUE)){
                            // LIKE '% ～ %'
                            sbQuery.append(PT_LIKE + PT_SPACE);
                            // LIKE文は文字型のみ
                            sbQuery.append(transStringContainsNullToDB(sValue));
                        }else if(StrUtil.equals(sOperator,WRD_PT_IS_STARTS_WITH_VALUE)){
                            // LIKE ' ～ %'
                            sbQuery.append(PT_LIKE).append(PT_SPACE);
                            // LIKE文は文字型のみ
                            sbQuery.append(escDBStartsWithString(sValue));
                        }else if(StrUtil.equals(sOperator,WRD_PT_IS_ENDS_WITH_VALUE)){
                            // LIKE '% ～ '
                            sbQuery.append(PT_LIKE)
                                    .append(PT_SPACE);
                            // LIKE文は文字型のみ
                            sbQuery.append(transStringEndsWithNullToDB(sValue));
                        }else{
                            sbQuery.append(sOperator)
                                    .append(PT_SPACE);
                            // データ型で判定する
                            if (StrUtil.equals(sType,"NUMBER")) {
                                sbQuery.append(toDBNumber(sValue));
                            } else if (StrUtil.equals(sType,"DATE")) {
                                sbQuery.append(toDBDate(sValue));
                            } else {
                                sbQuery.append(this.escDBString(sValue));
                            }
                        }
                    } else if (StrUtil.equalsAny(sMaster,MASTER_QCOMPANY,MASTER_QSECTION,MASTER_QPOST,MASTER_QPOSTNUM)){
                        // 法人コード待避
                        if (StrUtil.isBlank(queryConditionRowDTO.getCompanyid())){
                            queryConditionRowDTO.setCompanyid(psCompanyId);
                        }
                        // Myフラグの設定
                        if(StrUtil.isBlank(queryConditionRowDTO.getMyflag())){
                            queryConditionRowDTO.setMyflag("0");
                        }
                        // 行番号
                        if(queryConditionRowDTO.getSeq() == null){
                            queryConditionRowDTO.setSeq(iIndex);
                        }
                        // 特殊マスタ系(自由条件検索はドメイン「01」固定)
                        sbQuery.append(createMasterSpecialMeaningSQL(queryConditionRowDTO));
// ↓上記特殊マスタ系内の処理で実施
//                    } else if (sMaster.equals(BuildSQLLogicImpl.MASTER_QPOSTNUM)) {
//                        // 役職順位マスタ区分
//                        Pattern pattern = Pattern.compile(BuildSQLLogicImpl.PT_BAR);
//                        String[] stValue = pattern.split(sValue);
//                        sValue = stValue[1];
//
//                        sbQuery.append("HD_CPOSTID_FK IN ");
//                        sbQuery.append("( ");
//                        sbQuery.append("SELECT MAP_CPOSTID_CK ");
//                        sbQuery.append("FROM MAST_POST ");
//                        sbQuery.append("WHERE MAP_CCUSTOMERID_CK_FK = "
//                            + BuildSQLLogicImpl.COL_HD_CUSTOMERID
//                            + BuildSQLLogicImpl.PT_SPACE);
//                        sbQuery.append("AND MAP_CCOMPANYID_CK_FK = "
//                            + BuildSQLLogicImpl.COL_HD_COMPANYID
//                            + BuildSQLLogicImpl.PT_SPACE);
//                        sbQuery.append("AND MAP_NWEIGHTAGE " + this.getWeightageOperator(sOperator)
//                            + BuildSQLLogicImpl.PT_SPACE
//                            + this.toDBNumber(sValue)
//                            + BuildSQLLogicImpl.PT_SPACE);
//                        sbQuery.append("AND MAP_CLANGUAGE = 'ja' ");
//                        sbQuery.append("AND MAP_DSTART <= TRUNC(SYSDATE) ");
//                        sbQuery.append("AND MAP_DEND >= TRUNC(SYSDATE) ");
//                        sbQuery.append(")");
//                    } else if (sMaster.equals(BuildSQLLogicImpl.MASTER_QSECTION)
//                                    || sMaster.equals(BuildSQLLogicImpl.MASTER_QPOST)) {
//                        // 在職・退職マスタ区分 or 本務・兼務マスタ区分
//                        Pattern pattern = Pattern.compile(BuildSQLLogicImpl.PT_BAR);
//                        String[] stValue = pattern.split(sValue);
//                        sValue = stValue[1];
//
//                        sbQuery.append(queryConditionRowDto.getColumnid()
//                            + BuildSQLLogicImpl.PT_SPACE);
//                        sbQuery.append(sOperator + BuildSQLLogicImpl.PT_SPACE);
//                        // データ型で判定する
//                        if (sType.equals("NUMBER")) {
//                            sbQuery.append(this.toDBNumber(sValue));
//                        } else if (sType.equals("DATE")) {
//                            sbQuery.append(this.toDBDate(sValue));
//                        } else {
//                            sbQuery.append(this.escDBString(sValue));
//                        }
                    } else {
                        // 一般的なマスタ区分
                        sbQuery.append(getTableAlias(sTableId))
                                .append(".")
                                .append(queryConditionRowDTO.getColumnid())
                                .append(PT_SPACE)
                                .append(sOperator)
                                .append(PT_SPACE);
                        // データ型で判定する
                        if (StrUtil.equals(sType,"NUMBER")) {
                            sbQuery.append(toDBNumber(sValue));
                        } else if (StrUtil.equals(sType,"DATE")) {
                            sbQuery.append(toDBDate(sValue));
                        } else {
                            sbQuery.append(escDBString(sValue));
                        }
                    }
                    sbQuery.append(PT_SPACE);
                    sbQuery.append(toBlank(queryConditionRowDTO.getClosedparenthsis()));
                }
                // カウント(行番号用)
                iIndex++;
            }
            // 閉じ括弧
            if (subIndex == conditionWhereDtoList.size()){
                sbQuery.append(PT_CLOSE_PAR);
            }
        }
        return sbQuery.toString();
    }

    /**
     * カラムID情報からORDER BY句を組み立てます
     * @param orderDto ORDER BY条件
     * @return ORDER BY句
     */
    private String getOrderSql(ConditionOrderDTO orderDto) {
        // 表示対象カラムIDの取得
        String columnId = orderDto.getHsoCcolumn();
        String sortMethod = orderDto.getHsoCorder();
        if(StrUtil.isNotBlank(columnId)){
            // データディクショナリ情報の参照
            MastDatadictionaryDO dataDictionary = cacheUtil.getDataDictionary("01" + "_" + columnId);
            // テーブルID格納
            String tableId = dataDictionary.getMdCtablename();
            // from句Mapに追加
            if (fromMap.get(tableId.toUpperCase()) == null) {
                // 別名取得用
                setTableAlias(tableId);
                fromMap.put(tableId.toUpperCase(), tableId.toUpperCase() + " " + getTableAlias(tableId));
            }
            return getTableAlias(tableId) + "." + orderDto.getHsoCcolumnId();
        }else{
            return "";
        }
    }

    /**
     * 使用テーブル一覧からWHERE句（結合条件）を組み立てます
     *
     * @param standardDateType 基準日使用有無
     * @param customerId 顧客コード
     * @param standardDate 基準日
     * @param companyId 法人コード
     * @return WHERE句リスト
     */
    private List<String> getJoinWhereSqlList(Integer standardDateType, String customerId, String companyId, Date standardDate) {
        List<String> joinWhereList = CollUtil.newArrayList();
        Set<String> fromSet = fromMap.keySet();
        String sysdateMode = cacheUtil.getSystemProperty(PROP_JK_SYSDATE_MODE);
        // 登録チェック
        if (!StrUtil.equalsAnyIgnoreCase(sysdateMode,"yes","no")) {
            throw new GlobalException(PROP_JK_SYSDATE_MODE+"は設定していません");
        }
        String strStandardDate = SysUtil.transDateToString(standardDate);
        for (String tableId : fromSet) {
           if (StrUtil.equals(TABLE_MAIN,tableId)) {
               StringBuilder joinWhereSql = new StringBuilder();
               joinWhereSql
                       .append("M1.")
                       .append(COLUMN_ME_CCUSTOMERID)
                       .append(" = '")
                       .append(customerId)
                       .append("' ");
               if (standardDateType.equals(FLAG_CRITERIALDATE)) {
                   // 基準日指定の時
                   joinWhereSql
                           .append(" AND ")
                           .append("M1.")
                           .append(COLUMN_ME_DSTARTDATE)
                           .append(" <= TO_DATE('")
                           .append(strStandardDate)
                           .append("','yyyy/mm/dd') AND ")
                           .append("M1.")
                           .append(COLUMN_ME_DENDDATE)
                           .append(" >= TO_DATE('")
                           .append(strStandardDate)
                           .append("','yyyy/mm/dd')");
               } else {
                   // 基準日無視の時
                   // システム日付モードであればシステム日付時点で絞り込み
                   if (StrUtil.equalsIgnoreCase(sysdateMode,"yes")) {
                       joinWhereSql.append(" AND ")
                                   .append("M1.")
                                   .append(COLUMN_ME_DSTARTDATE)
                                   .append(" <= TRUNC(SYSDATE) AND ")
                                   .append("M1.")
                                   .append(COLUMN_ME_DENDDATE)
                                   .append(" >= TRUNC(SYSDATE)");
                   }
               }
               // 全法人共通検索でなければ法人で絞り込み
               if (!StrUtil.equals(companyId,PsConst.CODE_ALL_COMPANIES)) {
                   joinWhereSql.append(" and ")
                           .append("M1.")
                           .append(COLUMN_ME_COMPANYID)
                           .append(" = '")
                           .append(companyId)
                           .append("' ");
               }
               joinWhereList.add(joinWhereSql.toString());
               continue;
           }
            // 顧客、ユーザID、開始日、終了日カラムを取得
            String sCustomerId = getCustomerId(tableId);
            String sCompanyId = getCompanyId(tableId);
            String sUserId = getUserId(tableId);
            String sStartDate = getStartDate(tableId);
            String sEndDate = getEndDate(tableId);
            // SQLの組立
            StringBuilder joinWhereSql = new StringBuilder();
            joinWhereSql
                    .append("M1.")
                    .append(COLUMN_ME_CCUSTOMERID)
                    .append(" = ")
                    .append(getTableAlias(tableId))
                    .append(".")
                    .append(sCustomerId)
                    .append("(+) and ")
                    .append("M1.")
                    .append(COLUMN_ME_CUSERID)
                    .append(" = ")
                    .append(getTableAlias(tableId))
                    .append(".")
                    .append(sUserId)
                    .append("(+) ");
            if (standardDateType.equals(FLAG_CRITERIALDATE)) {
                joinWhereSql.append(" and ")
                        .append(getTableAlias(tableId))
                        .append(".")
                        .append(sStartDate)
                        .append("(+) <= TO_DATE('")
                        .append(strStandardDate)
                        .append("','yyyy/mm/dd') and ")
                        .append(getTableAlias(tableId))
                        .append(".")
                        .append(sEndDate)
                        .append("(+) >= TO_DATE('")
                        .append(strStandardDate)
                        .append("','yyyy/mm/dd')");
            }
            // 全法人共通検索でなければ法人で絞り込み
            if (!StrUtil.equals(companyId,PsConst.CODE_ALL_COMPANIES)) {
                joinWhereSql.append(" and ")
                        .append(getTableAlias(tableId))
                        .append(".")
                        .append(sCompanyId)
                        .append("(+) = '")
                        .append(companyId)
                        .append("' ");
            }
            joinWhereList.add(joinWhereSql.toString());
        }
        return joinWhereList;
    }


    /**
     * 名称マスタ区分が特別な意味を持つ場合のSQLを組み立てます。
     * @param queryConditionRowDto 検索範囲取得DTOクラス
     */
    private String createMasterSpecialMeaningSQL(QueryConditionRowDTO queryConditionRowDto) {
        // 関連テーブルの情報を取得
        String sMdpdCtableid = queryConditionRowDto.getTableid();
        String sMdCmastertblname = queryConditionRowDto.getMastertablename();
        TableCombinationTypeDTO tableCombinationType = cacheUtil.getTableCombinationType(sMdpdCtableid);
        // 返却値用
        StringBuilder sbQuery = new StringBuilder();
        // [AND/OR] [NOT] [(]
        sbQuery.append(PT_SPACE);
        // 行数チェック
        if (queryConditionRowDto.getSeq() == 0){
            sbQuery.append(toBlank(queryConditionRowDto.getAndor()));
        }
        String operator = queryConditionRowDto.getOperator();
        if (StrUtil.equalsAny(operator,"!=","<>")) {
            sbQuery.append(" NOT ");
        }
        // 処理対象のデータを準備
        List<Designation> designationList = createMasterSpecialMeaningData(queryConditionRowDto);

        sbQuery.append(PT_OPEN_PAR);
        for (Iterator<Designation> designationIte = designationList.iterator(); designationIte.hasNext();) {
            Designation designation = designationIte.next();
            sbQuery.append(PT_OPEN_PAR);

            if (StrUtil.equals(sMdCmastertblname,MASTER_QCOMPANY)) {
                // 法人：MAC_CCOMPANYID_CK [=] ''
                sbQuery.append(tableCombinationType.getCompanyIdColumnName());
                sbQuery.append(PT_SPACE);
                sbQuery.append(queryConditionRowDto.getOperator());
                sbQuery.append(PT_SPACE);
                sbQuery.append(escDBString(toBlank(designation.getCompanyCode())));
            } else if (StrUtil.equals(sMdCmastertblname,MASTER_QSECTION)) {
                // 法人：MO_CCOMPANYID_CK_FK = '' AND
                sbQuery.append(tableCombinationType.getCompanyIdColumnName());
                sbQuery.append(PT_EQUAL);
                sbQuery.append(this.escDBString(this.toBlank(designation.getCompanyCode())));
                sbQuery.append(PT_AND);
                // 組織：MO_CSECTIONID_CK = ''
                sbQuery.append(tableCombinationType.getSectionIdColumnName());
                sbQuery.append(PT_EQUAL);
                sbQuery.append(escDBString(toBlank(designation.getSection())));
            } else if (StrUtil.equals(sMdCmastertblname,MASTER_QPOST)) {
                // 法人：MO_CCOMPANYID_CK_FK = '' AND
                sbQuery.append(tableCombinationType.getCompanyIdColumnName());
                sbQuery.append(PT_EQUAL);
                sbQuery.append(this.escDBString(this.toBlank(designation.getCompanyCode())));
                sbQuery.append(PT_AND);
                // 役職：MAP_NWEIGHTAGE [=] ''
                if (StrUtil.equals(queryConditionRowDto.getOperator(),"=")) {
                    // 役職に対して[=][!=]の比較に対しては役職コードにて行う
                    sbQuery.append(tableCombinationType.getPostIdColumnName())
                            .append(PT_SPACE);
                    sbQuery.append(queryConditionRowDto.getOperator())
                            .append(PT_SPACE);
                    sbQuery.append(this.escDBString(this.toBlank(designation.getPostCode())))
                            .append(PT_SPACE);
                } else if (StrUtil.equalsAny(queryConditionRowDto.getOperator(),"!=","<>")) {
                    // "!="か"<>"の場合は条件が反転するので"="に直す
                    sbQuery.append(tableCombinationType.getPostIdColumnName()).append(PT_SPACE);
                    sbQuery.append("=" + PT_SPACE);
                    sbQuery.append(escDBString(toBlank(designation.getPostCode())))
                            .append(PT_SPACE);
                } else if (StrUtil.equalsAny(queryConditionRowDto.getOperator(),">",">=","<","<=")) {
                    // 役職に対して[>][>=][<=][<]の比較に対しては役職順位にて行う
                    sbQuery.append(tableCombinationType.getPostIdColumnName());
                    sbQuery.append(PT_IN);
                    sbQuery.append(" ( SELECT MP1.MAP_CPOSTID_CK ");
                    sbQuery.append("FROM MAST_POST MP1, MAST_POST MP2 ");
                    sbQuery.append("WHERE MP1.MAP_CCUSTOMERID_CK_FK = ")
                            .append(escDBString(toBlank(designation.getCustomerCode())))
                            .append(PT_SPACE);
                    sbQuery.append("AND MP1.MAP_CCOMPANYID_CK_FK = ")
                            .append(escDBString(toBlank(designation.getCompanyCode())))
                            .append(PT_SPACE);
                    sbQuery.append("AND MP1.MAP_CLANGUAGE = 'ja' ");
                    sbQuery.append("AND MP1.MAP_DSTART <= TRUNC(SYSDATE) ");
                    sbQuery.append("AND MP1.MAP_DEND >= TRUNC(SYSDATE) ");
                    // 注意：「○○職以上の人」との表現にて、数値的な「50以上」と意味が反転するので右辺と左辺を反対に記述
                    sbQuery.append("AND MP2.MAP_NWEIGHTAGE ")
                            .append(queryConditionRowDto.getOperator())
                            .append(" MP1.MAP_NWEIGHTAGE ");
                    sbQuery.append("AND MP2.MAP_CCUSTOMERID_CK_FK = MP1.MAP_CCUSTOMERID_CK_FK ");
                    sbQuery.append("AND MP2.MAP_CCOMPANYID_CK_FK = MP1.MAP_CCOMPANYID_CK_FK ");
                    sbQuery.append("AND MP2.MAP_CPOSTID_CK = ")
                            .append(escDBString(this.toBlank(designation.getPostCode())))
                            .append(PT_SPACE);
                    sbQuery.append("AND MP2.MAP_CLANGUAGE = MP1.MAP_CLANGUAGE ");
                    sbQuery.append("AND MP2.MAP_DSTART <= TRUNC(SYSDATE) ");
                    sbQuery.append("AND MP2.MAP_DEND >= TRUNC(SYSDATE) ");
                    sbQuery.append(") ");
                }
            } else if (StrUtil.equals(sMdCmastertblname,MASTER_QPOSTNUM)) {
                // 役職順位：MAP_NWEIGHTAGE [=] [1]
                sbQuery.append(tableCombinationType.getPostIdColumnName())
                        .append(PT_IN)
                        .append(" ( SELECT MAP_CPOSTID_CK ")
                        .append("FROM MAST_POST ")
                        .append("WHERE MAP_CCUSTOMERID_CK_FK = ")
                        .append(escDBString(toBlank(designation.getCustomerCode())))
                        .append(PT_SPACE).append("AND MAP_CCOMPANYID_CK_FK = ")
                        .append(escDBString(toBlank(designation.getCompanyCode())))
                        .append(PT_SPACE)
                        .append("AND MAP_CLANGUAGE = 'ja' ")
                        .append("AND MAP_DSTART <= TRUNC(SYSDATE) ")
                        .append("AND MAP_DEND >= TRUNC(SYSDATE) ");
                // 注意：「○○職以上の人」との表現にて、数値的な「50以上」と意味が反転するので右辺と左辺を反対に記述
                sbQuery.append("AND ").append(designation.getPostRank())
                        .append(PT_SPACE)
                        .append(queryConditionRowDto.getOperator())
                        .append(" MAP_NWEIGHTAGE ").append(") ");
            }
            sbQuery.append(PT_CLOSE_PAR);
            if (designationIte.hasNext()) {
                sbQuery.append(PT_OR);
            }
        }
        // )
        sbQuery.append(PT_CLOSE_PAR);

        return sbQuery.toString();
    }

    /**
     * 名称マスタ区分が特別な意味を持つ場合の検索対象データの取得
     * @param queryConditionRowDto 検索範囲取得DTOクラス
     */
    private List<Designation> createMasterSpecialMeaningData(QueryConditionRowDTO queryConditionRowDto) {
        List<Designation>designationList = CollUtil.newArrayList();
        String sMdCmastertblname = queryConditionRowDto.getMastertablename();
        if (queryConditionRowDto.getMyflag().equals(MY_FLG0)) {
            // 指定値
            if (StrUtil.equals(sMdCmastertblname,MASTER_QCOMPANY)) {
                Designation designation = new Designation();
                // 顧客コード
                designation.setCustomerCode(psSession.getLoginCustomer());
                // 法人コード
                designation.setCompanyCode(queryConditionRowDto.getCompanyid());
                designationList.add(designation);
            } else if (StrUtil.equals(sMdCmastertblname,MASTER_QSECTION)) {
                Designation designation = new Designation();
                // 顧客コード
                designation.setCustomerCode(psSession.getLoginCustomer());
                // 法人コード
                designation.setCompanyCode(queryConditionRowDto.getCompanyid());
                // 組織コード
                designation.setSection(getDetailId(queryConditionRowDto.getValue()));
                designationList.add(designation);
            } else if (StrUtil.equals(sMdCmastertblname,MASTER_QPOST)) {
                // 指定法人に対する役職コードが指定される
                for (Designation designation : psSession.getLoginDesignation()) {
                    Designation newDesignation = new Designation();
                    // 顧客コード
                    newDesignation.setCustomerCode(psSession.getLoginCustomer());
                    // 法人コード
                    newDesignation.setCompanyCode(queryConditionRowDto.getCompanyid());
                    // 役職コード
                    newDesignation.setPostCode(getDetailId(queryConditionRowDto.getValue()));
                    designationList.add(newDesignation);
                }
            } else if (StrUtil.equals(sMdCmastertblname,MASTER_QPOSTNUM)) {
                Designation designation = new Designation();
                designation.setCustomerCode(psSession.getLoginCustomer());    // 顧客コード
                designation.setCompanyCode(queryConditionRowDto.getCompanyid());    // 法人コード
                designation.setPostRank(Integer.parseInt(getDetailId(queryConditionRowDto.getValue())));   // 役職順位
                designationList.add(designation);
            }
        } else if (StrUtil.equals(queryConditionRowDto.getMyflag(),MY_FLG1)) {
            /** 検索者の値：異動歴 */
            designationList = psSession.getLoginDesignation();
        }
        // 条件式での指定値の情報および異動歴に対して「上位」「下位」の表現に対する対象を求める
        // （クエリで回避出来ない法人および組織の上位取得部分はここで吸収しておく）
        if (StrUtil.equals(sMdCmastertblname,MASTER_QCOMPANY)) {
            /** 法人 */
        } else if (StrUtil.equals(sMdCmastertblname,MASTER_QSECTION)) {
            /** 組織 */
            List<Designation> newDesignationList = CollUtil.newArrayList();
            // 対象リスト(指定値および異動歴)毎のLOOP
            for (Designation designation : designationList) {
                List<String> targetSectionList = CollUtil.newArrayList();
                String operator = queryConditionRowDto.getOperator();
                if (StrUtil.equalsAny(operator,">",">=")) {
                    // 上位情報
                    targetSectionList = sysInfoLogic.getUpperSectionListDeptForSQL(designation.getCustomerCode(), designation.getCompanyCode(), designation.getSection(), new Date());
                } else if (StrUtil.equalsAny(operator,"<","<=")) {
                    // 下位情報
                    targetSectionList = sysInfoLogic.getLowerSectionListDeptForSQL(designation.getCustomerCode(), designation.getCompanyCode(), designation.getSection(), new Date());
                }
                // 取得した上位組織を処理対象のリストに反映
                if (CollUtil.isNotEmpty(targetSectionList)) {
                    for (String sSection : targetSectionList) {
                        if (
                                !(
                                        StrUtil.equalsAny(operator,">","<") && StrUtil.equals(sSection,designation.getSection())
                                )
                        ){
                            // 元になった異動歴情報に対象組織を反映
                            Designation targetDesignation = new Designation();
                            targetDesignation.setCustomerCode(designation.getCustomerCode());
                            targetDesignation.setCompanyCode(designation.getCompanyCode());
                            targetDesignation.setSection(sSection);
                            // 新たな対象組織リストを作成
                            newDesignationList.add(targetDesignation);
                        }
                    }
                }
                // [>=][<=][=][<>]の場合は基点部分も対象に含める
                if (StrUtil.equalsAny(operator,">=","<=","=","<>","IN")) {
                    newDesignationList.add(designation);
                }
            }
            designationList = newDesignationList;
        } else if (StrUtil.equals(sMdCmastertblname,MASTER_QPOST)) {
            /** 役職 */
        } else if (StrUtil.equals(sMdCmastertblname,MASTER_QPOSTNUM)) {
            /** 役職順位 */
        }
        return designationList;
    }


    /**
     * 明細データコード取得処理<br>
     * 指定された値がマスタコードであった場合、明細データコードを返却します。
     *
     * @param psValue   値(マスタコード)
     * @return  String  明細データコード
     */
    private String getDetailId(String psValue) {
        String sDetailId = "";
        if (StrUtil.isNotBlank(psValue)) {
            Pattern pattern = Pattern.compile(PT_BAR);
            String[] stValue = pattern.split(psValue);
            // 分割された場合は、明細データコードを返却
            if (StrUtil.equals(psValue,stValue[0])) {
                sDetailId = psValue;
            } else {
                sDetailId = stValue[1];
            }
        }
        return sDetailId;
    }

    /**
     * 指定テーブルの終了日カラムを取得します
     * @param tableId テーブルID
     * @return String
     */
    private String getEndDate(String tableId) {
        TableCombinationTypeDTO tableCombinationType =
                cacheUtil.getTableCombinationType(tableId);
        return tableCombinationType.getEndDateColumnName();
    }

    /**
     * FROM句のMAPをLISTに変換します
     * @return FROM句のList
     */
    private List<String> getFromList() {
        List<String> fromList = CollUtil.newArrayList();
        Set<String> fromSet = fromMap.keySet();
        for (String key : fromSet) {
            fromList.add(fromMap.get(key));
        }
        return fromList;
    }

    /**
     * 指定テーブルの顧客コードカラムを取得します
     * @param tableId テーブルID
     * @return String
     */
    private String getCustomerId(String tableId) {
        TableCombinationTypeDTO tableCombinationType =cacheUtil.getTableCombinationType(tableId);
        return tableCombinationType.getCustomerIdColumnName();
    }

    /**
     * 指定テーブルの開始日カラムを取得します
     * @param tableId テーブルID
     * @return String
     */
    private String getStartDate(String tableId) {
        TableCombinationTypeDTO tableCombinationType = cacheUtil.getTableCombinationType(tableId);
        return tableCombinationType.getStartDateColumnName();
    }

    /**
     * 指定テーブルの法人コードカラムを取得します
     * @param tableId テーブルID
     * @return String
     */
    private String getCompanyId(String tableId) {
        TableCombinationTypeDTO tableCombinationType = cacheUtil.getTableCombinationType(tableId);
        return tableCombinationType.getCompanyIdColumnName();
    }

    /**
     * 指定テーブルのユーザIDカラムを取得します
     * @param psTableId テーブルID
     * @return String
     */
    private String getUserId(String psTableId) {
        TableCombinationTypeDTO tableCombinationType =
                cacheUtil.getTableCombinationType(psTableId);
        return tableCombinationType.getUserIdColumnName();
    }

    private void setTableAlias(String tableName) {
        int i = 0;
        Set<String> tableSet = fromMap.keySet();
        for (String table : tableSet) {
            if (StrUtil.equalsIgnoreCase(tableName,table)) {
                break;
            }
            i++;
        }
        // まだ存在しないテーブルの場合Mapに追加
        if (i == fromMap.size()) {
            tableAliasCounter++;
            tableAliasMap.put(tableName.toUpperCase(), TABLE_ALIAS_MARK + tableAliasCounter+"");
        }
    }

    private String getTableAlias(String psTableName) {
        // 一致するテーブルを返す
        return tableAliasMap.get(psTableName.toUpperCase());
    }

    /**
     * 取得した値がNULLの場合は、空白で返却します
     *
     * @param   psString    変換元文字列
     * @return  String      変換結果文字列
     */
    private String toBlank(String psString) {
        return SysUtil.transNull(psString);
    }

    /**
     * DB更新用に数値であればそのままを、nullであれば0をします。
     * @param   psNumber    変換元文字列
     * @return  String      変換結果文字列
     */
    private String toDBNumber(String psNumber) {
        return SysUtil.transNumberNullToDB(psNumber);
    }

    /**
     * DB更新用に日付「to_date形式に変換して返します。
     * nullであれば「NULL」の文字列を返します。
     * @param   psDate  変換元文字列
     * @return  String  変換結果文字列
     */
    private String toDBDate(String psDate) {
        return SysUtil.transDateNullToDB(psDate);
    }

    /**
     * DB更新用に文字列を「'」で囲んで返します。 nullであれば「NULL」の文字列を返します。
     *
     * @param   psString    変換元文字列
     * @return  String      変換結果文字列
     */
    private String escDBString(String psString) {
        return SysUtil.transStringNullToDB(SysUtil.escapeQuote(psString));
    }

    /**
     * DB更新用に文字列を「'」で囲んで返します。
     * Null以外は前方一致(Like文)で且つシングルクォーテーションで囲みます。
     *
     * @param   psString    変換元文字列
     * @return  String      変換結果文字列
     */
    private String escDBStartsWithString(String psString) {
        return transStringStartsWithNullToDB(SysUtil.escapeQuote(psString));
    }

    /**
        * 文字列をnull → "NULL" にします。
        * null以外は前方一致(Like文)で且つシングルクォーテーションで囲みます。
        * @param psString 変換する文字列。
        * @return String 変換の文字列
     */
    private String transStringStartsWithNullToDB(String psString) {
        if (StrUtil.isBlank(PT_NULL)) {
            return PT_NULL;
        } else {
            return PT_SINGLE + psString + PT_PERCENT + PT_SINGLE;
        }
    }

    /**
     * 文字列をnull → "NULL" にします。
     * null以外は後方一致(Like文)で且つシングルクォーテーションで囲みます。
     *
     * @param psString 変換する文字列。
     * @return String 変換後の文字列
     */
    private String transStringEndsWithNullToDB(String psString) {
        if (StrUtil.isBlank(psString)) {
            return PT_NULL;
        } else {
            return PT_SINGLE + PT_PERCENT + psString + PT_SINGLE;
        }
    }

    /**
     * 文字列をnull → "NULL" にします。
     * null以外は部分一致(Like文)で且つシングルクォーテーションで囲みます。
     *
     * @param psString 変換する文字列。
     */
    private String transStringContainsNullToDB(String psString) {
        if (StrUtil.isBlank(psString)) {
            return PT_NULL;
        } else {
            return PT_SINGLE + PT_PERCENT + psString + PT_PERCENT + PT_SINGLE;
        }
    }

    private String getAlias(String psColumnName) {
        nAliasCounter++;
        String sAlias = psColumnName + ALIAS_MARK + nAliasCounter;
        if (sAlias.length() > 30) {
            String[] saMessage = new String[1];
            saMessage[0] = psColumnName;
            throw new GlobalException(saMessage[0]);
        }
        return psColumnName + ALIAS_MARK + nAliasCounter;
    }

}
