package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.dbaccess.DbControllerLogic;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.bo.SqlBO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.ColumnQueryDefinitionOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.TableOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.TableQueryDefinitionOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.util.ConditionSearchSqlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Service
@RequiredArgsConstructor
public class ConditionSearchLogicImpl implements IConditionSearchLogic {

    private final IConditionSearchService conditionSearchService;
    private final ConditionSearchSqlBuilder sqlBuilder;
    private final DbControllerLogic dbControllerLogic;
    private final DataSource dataSource;
    private final ScCacheUtil cacheUtil;

    /** 異動歴のユーザID **/
    private final String COLUMN_ME_CUSERID_AS = "ME_CUSERID AS MC";

    // 获取数据库表选择列表
    @Override
    public List<TableOptionDTO> getTableOptions() {
        return conditionSearchService.selectTable();
    }

    // 根据数据库表名获取数据库表里的所有列名
    @Override
    public List<ColumnOptionDTO> getColumnOptions(String table) {
        return conditionSearchService.selectColumns(table);
    }

    // 获取条件式定义里可选择的数据库表
    @Override
    public List<TableQueryDefinitionOptionDTO> getTableForQueryDefinition() {
        return conditionSearchService.selectTableForQueryDefinition();
    }

    // 获取条件定义里可选择的表的列名
    @Override
    public List<ColumnQueryDefinitionOptionDTO> getColumnForQueryDefinition(String tableName) {
        return conditionSearchService.selectColumnForQueryDefinition(tableName);
    }

    @Override
    public Map<String,Object> search(ConditionSettingDTO settingDTO) {
        SqlBO sqlBO = sqlBuilder.createSql(settingDTO);
        String mode = settingDTO.getMode();
        if (!StrUtil.equalsIgnoreCase(MODE_TABLE,mode)) {
            // 組み立てたSQLを実行して検索結果を取得する
            int nTotalCount = getTotalCount(sqlBO);
            // 開始行、終了行を取得する
            if (StrUtil.equalsIgnoreCase(MODE_SCREEN,mode)) {
                calcStartEnd(nTotalCount,settingDTO);
            }
        }
        // 検索条件Dtoを解析してSQLを組み立てる
        String sSearchSql = getSearchSql(settingDTO, sqlBO);
        // 組み立てたSQLを実行して検索結果を取得する
        Vector <Vector<Object>> vSearchResult = executeSql(sSearchSql);
        // できあがった検索結果をListに変換する
        List<List<Object>> lstSearchResult = convertDBResult(vSearchResult,mode);
        // 検索結果からデータディクショナリ情報を取得し、タイトル列を取得する
        // 常量代表 custId language
        List<String> lstTitle = getTitle(lstSearchResult, "01", "ja", mode);
        // １行目はカラム情報なので除去する
        lstSearchResult.remove(0);

        Map<String,Object> result = MapUtil.newHashMap();
        result.put("title",lstTitle);
        result.put("result",lstSearchResult);

        return result;
    }

    /**
     * 設定に沿って検索を実行するSQLを組み立てます
     *
     * @param settingDto 検索条件
     * @return String
     */
    @Override
    public String getSearchSql(ConditionSettingDTO settingDto, SqlBO sqlBO) {
        String psLoginUserId = SecurityUtil.getUserId();
        String psMode = settingDto.getMode();
        int nStart = 0;
        int nEnd = 0;
        // SQL部品作成処理
        String sSelect;
        if (StrUtil.equalsIgnoreCase(psMode,MODE_CSV)) {
            // CSVダウンロードモードは指定通りに出力
            sSelect = sqlBO.getSelectStatement();
        } else if (StrUtil.equalsIgnoreCase(MODE_SCREEN,psMode)) {
            // 検索モードは先頭にユーザIDを追加
            sSelect = "M1." + COLUMN_ME_CUSERID_AS + "," + sqlBO.getSelectStatement();
            PagerLinkDTO pagerLinkDto = settingDto.getPagerLinkDTO();
            nStart = pagerLinkDto.getPageFrom();
            nEnd = pagerLinkDto.getPageTo();
        } else {
            // 連携データ出力モードは顧客コードとユーザIDのみ
            Long nDataId = settingDto.getHseNdataId();
            sSelect = "HIST_SEARCH_COOP_DETAIL_SEQ.nextval, ";
            sSelect += nDataId + ", ";
            sSelect += "M1.ME_CCUSTOMERID_CK, M1.ME_CUSERID, ";
            sSelect += "'" + psLoginUserId + "', ";
            sSelect += "SYSDATE, 0";
        }
        List<String> fromList = sqlBO.getFromStatementList();
        List<String> joinWhereList = sqlBO.getJoinWhereStatementList();
        String conditionWhereSql = sqlBO.getWhereConditionStatement();
        String orderSql = sqlBO.getOrderStatement();

        // SQL組立処理
        StringBuilder sbSql = new StringBuilder();
        // 検索モードは１ページ分のデータを検索
        if (StrUtil.equalsIgnoreCase(MODE_SCREEN,psMode)) {
            sbSql.append("SELECT * FROM (SELECT ROWNUM AS ROW_NUM, RN.* FROM (SELECT ").append(sSelect).append(" FROM ");
        } else {
            sbSql.append("SELECT ").append(sSelect).append(" FROM ");
        }
        // FROM句の組立
        for (String from : fromList) {
            sbSql.append(from).append(",");
        }
        // 末尾の,を取り除く
        sbSql.deleteCharAt(sbSql.length() - 1);
        // WHERE句の組立
        sbSql.append(" WHERE ");
        for (int i = 0; i < joinWhereList.size(); i++) {
            if (i != 0) {
                sbSql.append(" AND ");
            }
            sbSql.append(joinWhereList.get(i));
        }
        // 条件WHEREが続くとき
        if (StrUtil.isNotBlank(conditionWhereSql)) {
            sbSql.append(conditionWhereSql);
        }
        // ORDER BY句の組立
        if (StrUtil.isNotBlank(orderSql)){
            sbSql.append(" ORDER BY ").append(orderSql);
        }
        // 末尾の条件
        if (StrUtil.equalsIgnoreCase(psMode,MODE_SCREEN)) {
            sbSql.append(" ) RN ) WHERE ROW_NUM >= ").append(nStart).append(" AND ROW_NUM <= ").append(nEnd);
        }
        return sbSql.toString();
    }

    /**
     * 検索結果の総行数を取得するSQLを組み立てます
     */
    private int getTotalCount(SqlBO sqlBO) {
        // SQL取得
        String sTotalSql = getTotalCountSql(sqlBO);
        // SQL実行
        Vector<Vector<Object>> vTotalResult = executeSql(sTotalSql);
        // 総件数を取得する
        return ((BigDecimal) vTotalResult.get(1).get(0)).intValue();
    }

    /**
     * 検索結果から列タイトルを取得します
     *
     * @param pSearchResult 検索結果
     * @param psCustomer 顧客コード
     * @param psLanguage 言語区分
     * @return List
     */
    private List<String> getTitle(
            List<List<Object>> pSearchResult, String psCustomer, String psLanguage, String psMode) {
        // 先頭行のカラム情報を取得
        List <Object> lstColumnInfo = pSearchResult.get(0);
        List <String> lstColumnName = CollUtil.newArrayList();
        // １カラムずつ取り出してデータディクショナリからカラム名称を取得する
        for (int i = 0; i < lstColumnInfo.size(); i++) {
            // 検索モードの先頭カラム（ユーザID）はタイトル不要なので空文字列を返す
            if (StrUtil.equals(psMode,MODE_SCREEN) && i == 0) {
                lstColumnName.add("");
                continue;
            }
            String sKey = psCustomer + UNDER_SCORE + lstColumnInfo.get(i);
            MastDatadictionaryDO dataDictionary =
                    cacheUtil.getDataDictionary(sKey);
            // 別名を書き換えていてヒットしない場合
            if (dataDictionary == null) {
                if (sKey.lastIndexOf(ALIAS_MARK) > 0) {
                    sKey = sKey.substring(0, sKey.lastIndexOf(ALIAS_MARK));
                    dataDictionary = cacheUtil.getDataDictionary(sKey);
                }
            }

            String sColumnName = null;
            if (dataDictionary!=null) {
                if (StrUtil.equalsIgnoreCase(psLanguage,"ja")) {
                    sColumnName = dataDictionary.getMdCcolumndescja();
                } else if (StrUtil.equalsIgnoreCase(psLanguage,"en")) {
                    sColumnName = dataDictionary.getMdCcolumndescen();
                } else if (StrUtil.equalsIgnoreCase(psLanguage,"ch")) {
                    sColumnName = dataDictionary.getMdCcolumndescch();
                } else if (StrUtil.equalsIgnoreCase(psLanguage,"01")) {
                    sColumnName = dataDictionary.getMdCcolumndesc01();
                } else if (StrUtil.equalsIgnoreCase(psLanguage,"02")) {
                    sColumnName = dataDictionary.getMdCcolumndesc02();
                }
                // カラム名称が設定されていないときはカラムIDを返す
                if (sColumnName == null) {
                    sColumnName = dataDictionary.getMdCcolumnname();
                }
            }
            lstColumnName.add(sColumnName);
        }
        return lstColumnName;
    }

    /**
     * 検索結果の総行数を取得するSQLを組み立てます
     *
     * @param sqlBO 検索条件
     */
    private String getTotalCountSql(SqlBO sqlBO) {
        // SQL部品作成処理
        List<String> fromList = sqlBO.getFromStatementList();
        List<String> joinWhereList = sqlBO.getJoinWhereStatementList();
        String sConditionWhere = sqlBO.getWhereConditionStatement();
        // TODO:ここ以下はgetSearchSqlとほとんど一緒なので共通化する V4.1R1では対応見送り
        // SQL組立処理
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT COUNT(*) FROM ");
        // FROM句の組立
        for (String s : fromList) {
            sbSql.append(s).append(",");
        }
        // 末尾の,を取り除く
        sbSql.deleteCharAt(sbSql.length() - 1);
        // WHERE句の組立
        sbSql.append(" WHERE ");
        for (int i = 0; i < joinWhereList.size(); i++) {
            if (i != 0) {
                sbSql.append(" AND ");
            }
            sbSql.append(joinWhereList.get(i));
        }
        // 条件WHEREが続くとき
        if (StrUtil.isNotBlank(sConditionWhere)) {
            sbSql.append(sConditionWhere);
        }
        return sbSql.toString();
    }

    /**
     * Vectorで返ってきた検索結果をListに変換します
     * @param pvResult 検索結果
     * @return List
     */
    private List<List<Object>> convertDBResult(Vector <Vector<Object>> pvResult, String psMode) {
        List <List<Object>> lstResult = CollUtil.newArrayList();
        for (Vector<Object> objects : pvResult) {
            List<Object> lstObject = CollUtil.newArrayList();
            // SCREENモード時、１列目はROW_NUMなので除外する
            if (StrUtil.equalsIgnoreCase(psMode,MODE_SCREEN)) {
                for (int j = 1; j < objects.size(); j++) {
                    lstObject.add(objects.get(j));
                }
                // その他のモード時はすべて出力
            } else {
                lstObject.addAll(objects);
            }
            lstResult.add(lstObject);
        }
        return lstResult;
    }

    /**
     * 汎用DBアクセスを使用して組み立てた検索SQLを実行します
     *
     * @param psSql 検索SQL
     * @return Vector
     */
    private Vector<Vector<Object>> executeSql(String psSql) {
        Vector<Vector<Object>> vResult;
        try (Connection conn = dataSource.getConnection()){
            vResult = dbControllerLogic.executeQuery(psSql,conn);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return vResult;
    }

    /**
     * 検索条件と総件数とページサイズ、ページ番号から開始行、終了行を算出します
     *
     * @param psTotalCount 総件数
     */
    private void calcStartEnd(int psTotalCount,ConditionSettingDTO settingDTO) {
        PagerLinkDTO pagerLinkDto = settingDTO.getPagerLinkDTO();
        String sEvent = settingDTO.getPagingEvent();

        // 現在の設定のページ当たりの件数、ページ番号を取得する
        int nLinePerPage = pagerLinkDto.getPagerCondition();
        int nPageNo = pagerLinkDto.getCurrentPage();

        int nPageCount = 1;

        int nStart;
        int nEnd;
        int nTotalPage;

        // １ページ当たりの行数が変更されていたらリセット
        if (StrUtil.equalsIgnoreCase(sEvent,CHANGE_PAGE_CONDITION)) {
            nStart = 1;
            nEnd = nLinePerPage;
            // 開始行、終了行の再計算
        } else {
            // ページ数の算出
            nPageCount = psTotalCount / nLinePerPage;
            if (psTotalCount % nLinePerPage != 0) {
                nPageCount++;
            }
            // 開始行は１ページ前の末尾＋１
            nStart = (nPageNo - 1) * nLinePerPage + 1;
            // 終了行はそのページの末尾
            nEnd = nPageNo * nLinePerPage;
        }
        // 総ページ数を算出
        nTotalPage = psTotalCount / nLinePerPage;
        if (psTotalCount % nLinePerPage > 0) {
            nTotalPage++;
        }
        if (psTotalCount == 0) {
            nTotalPage = 1;
        }
        // 開始行が総行数を超えていたら総行数が開始行（0件の時）
        if (nStart > psTotalCount) {
            nStart = psTotalCount;
        }
        // 終了行が総行数を超えていたら総行数が終了行
        if (nEnd > psTotalCount) {
            nEnd = psTotalCount;
        }
        // ページ範囲を算出
        int nStartPage;
        int nEndPage;
        int nPreviousStartPage;
        int nNextStartPage;
        // 現在ページの変更
        if (StrUtil.equalsIgnoreCase(sEvent,CHANGE_CURRENT_PAGE)) {
            // 開始ページを求める。1未満だった場合はに1を格納
            nStartPage = Math.max(nPageNo - MOVE_PAGE_COUNT, 1);
            // 終了ページを求める。総ページ数を超えていた場合は最終ページを格納
            nEndPage = Math.min(nTotalPage, nPageNo + MOVE_PAGE_COUNT);
            // １ページの行数変更または初期検索
        } else {
            nStartPage = 1;
            nEndPage = Math.min(nTotalPage, nPageNo + MOVE_PAGE_COUNT);
            nPageNo = nStartPage;
        }
        // 前ページグループ、次ページグループ
        nPreviousStartPage = nStartPage;
        // 次ページグループの開始ページを求める
        nNextStartPage = Math.min(nEndPage, nTotalPage);

        // ページリンク設定
        List<SelectItemDTO> pageList = CollUtil.newArrayList();
        for (int i = nStartPage; i <= nEndPage; i++) {
            SelectItemDTO si = new SelectItemDTO();
            si.setLabel(String.valueOf(i));
            si.setValue(i);
            si.setDisabled(i == nPageNo);
            pageList.add(si);
        }
        pagerLinkDto.setPageList(pageList);

        if (nTotalPage > MOVE_PAGE_COUNT) {
            // 前ページグループへ
            pagerLinkDto.setPreviousPage(nPreviousStartPage+"");
            // 次ページグループへ
            pagerLinkDto.setNextPage("" + nNextStartPage);
        }

        // ページ数を格納
        pagerLinkDto.setTotalPage(nPageCount);

        // 総件数を格納
        pagerLinkDto.setTotalCount(psTotalCount);

        // 総ページ数を格納
        pagerLinkDto.setTotalPage(nTotalPage);

        // 新しい現在のページを格納
        pagerLinkDto.setCurrentPage(nPageNo);

        // Dtoに開始行、終了行を設定
        pagerLinkDto.setPageFrom(nStart);
        pagerLinkDto.setPageTo(nEnd);

        // ConditionSettingDtoに格納
        settingDTO.setPagerLinkDTO(pagerLinkDto);
    }

}
