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
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConditionSearchLogicImpl implements IConditionSearchLogic {

    private final IConditionSearchService conditionSearchService;
    private final IHistSearchSettingService histSearchSettingService;
    private final IHistSearchSelectService histSearchSelectService;
    private final IHistSearchDefinitionsService histSearchDefinitionsService;
    private final IHistSearchWhereService histSearchWhereService;
    private final IHistSearchOrderService histSearchOrderService;
    private final IHistSearchSettingTargetService histSearchSettingTargetService;
    private final PsSearchCompanyUtil searchCompanyUtil;

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
        System.out.println("++===searchSql===+++");
        System.out.println(sSearchSql);
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
     *  設定保存処理
     */
    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public GlobalResponse editSettings(ConditionSettingDTO settingDTO) {
        PsSession psSession = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
        // 設定ID
        Long oHssNsettingid;
        String loginUserId = psSession.getLoginUser();
        // 他者作成の設定を上書きできるかどうか
        String sJkPermitOverwrite = cacheUtil.getSystemProperty(PROP_JK_PERMIT_OVERWRITE);
        Long searchSettingId = settingDTO.getHseId();
        if (Objects.isNull(searchSettingId)) {
           // 名称取得
            int sameNameCount = histSearchSettingService.selectSameSettingName(
                    settingDTO.getHseCsettingname()
            );
            // 名称が重複している場合は返す。
            if (sameNameCount>0){
                throw new GlobalException("名称が重複しています");
            }
            oHssNsettingid = histSearchSettingService.selectSeq();
            settingDTO.setHseNsettingid(oHssNsettingid);
        } else {
            Boolean overwrite = settingDTO.getOverwrite();
            // IDを取得
            oHssNsettingid = settingDTO.getHseNsettingid();
            // 作成者取得
            String owner = histSearchSettingService.selectSettingOwner(oHssNsettingid);
            // ログインユーザが作者の場合は上書保存確認を返す。
            if(StrUtil.equalsIgnoreCase(owner,loginUserId)){
                if(!overwrite) {
                    return GlobalResponse.error(30004,"現在読み込み中の設定に上書きします。よろしいですか？");
                }
            } else if (!StrUtil.equalsIgnoreCase(owner,loginUserId)
                    && StrUtil.equalsIgnoreCase(sJkPermitOverwrite,"yes")) {
                if(!overwrite) {
                    return GlobalResponse.error(30005,"現在他の読み込み中の設定に上書きします。よろしいですか？");
                }
            } else {
                return GlobalResponse.error(30006,"設定名が重複しています。別名を使用してください。");
            }
        }
        histSearchSettingService.removeById(oHssNsettingid);

        HistSearchSettingDO settingDO = new HistSearchSettingDO();
        settingDO.setHseCuserid(loginUserId);
        settingDO.setHseCcustomerid(psSession.getLoginCustomer());
        settingDO.setHseCmodifieruserid(loginUserId);
        BeanUtil.copyProperties(settingDTO,settingDO);

        if (Objects.nonNull(searchSettingId)){
            // 更新時はSelect句Dtoを削除しておく
            Map<String,Object> map = MapUtil.<String,Object>builder()
                    .put("HSE_NSETTINGID",oHssNsettingid).build();
            histSearchSettingService.removeByMap(map);
        }

        List<ConditionSelectDTO> selectDTOList = settingDTO.getSelectDtoList();
        int selectSeq = 0;
        for (ConditionSelectDTO selectDTO : selectDTOList) {
            if (Objects.isNull(selectDTO.getHssCcolumn())) {
                break;
            }
            HistSearchSelectDO selectDO = new HistSearchSelectDO();
            BeanUtil.copyProperties(selectDO,selectDO);
            selectDO.setHssNsettingid(oHssNsettingid);
            selectDO.setHssCmodifieruserid(loginUserId);
            selectDO.setHssNseq(selectSeq);
            // Select句Dtoを挿入
            histSearchSelectService.save(selectDO);
            selectSeq++;
        }

        // 簡易版
        // 条件式
        if (Objects.isNull(searchSettingId)) {
          Map<String,Object> whereParams = MapUtil.<String,Object>builder().put("HSW_NSETTINGID",searchSettingId).build();
          histSearchWhereService.removeByMap(whereParams);
          whereParams = MapUtil.<String,Object>builder().put("HSD_NSETTINGID",searchSettingId).build();
          histSearchDefinitionsService.removeByMap(whereParams);
        }
        Boolean useQueryCondition = settingDTO.getUseQueryDefinition();
        // 条件式妥当性チェック(条件式設定のみ)
        if (useQueryCondition) {
            List<QueryConditionRowDTO> queryConditionRowDTOList = settingDTO.getQueryConditionDtoList();
            int querySeq = 0;
            for (QueryConditionRowDTO queryConditionRowDTO : queryConditionRowDTOList) {
                // 比較演算子
                String sOperator         = queryConditionRowDTO.getOperator();
                // 値
                String sValue            = queryConditionRowDTO.getValue();
                // 表示名称
                String sDispValue        = queryConditionRowDTO.getDisplayvalue();
                // 左カッコ
                String sOpenedparenthsis = queryConditionRowDTO.getOpenedparenthsis();
                // 右カッコ
                String sClosedparenthsis = queryConditionRowDTO.getClosedparenthsis();

                if (SysUtil.isAnyBlank(sOperator,sValue,sDispValue,sOpenedparenthsis,sClosedparenthsis)) {
                    HistSearchDefinitionsDO definitionsDO = new HistSearchDefinitionsDO();
                    // 設定ID
                    definitionsDO.setHsdNsettingid(oHssNsettingid);
                    // 行番号
                    definitionsDO.setHsdNseq(querySeq);
                    // 論理演算子
                    definitionsDO.setHsdCandor(queryConditionRowDTO.getAndor());
                    // 括弧
                    definitionsDO.setHsdCopenedparenthsis(queryConditionRowDTO.getOpenedparenthsis());
                    // テーブルID
                    definitionsDO.setHsdCtableid(queryConditionRowDTO.getTableid());
                    // カラムID
                    definitionsDO.setHsdCcolumnid(queryConditionRowDTO.getColumnid());
                    // カラム名
                    definitionsDO.setHsdCcolumnname(queryConditionRowDTO.getColumnname());
                    // データ型
                    definitionsDO.setHsdCtypeofcolumn(queryConditionRowDTO.getTypeofcolumn());
                    // 演算子
                    definitionsDO.setHsdCoperator(queryConditionRowDTO.getOperator());
                    // 比較値
                    definitionsDO.setHsdCvalue(queryConditionRowDTO.getValue());
                    // 表示文字列
                    definitionsDO.setHsdCdisplayvalue(queryConditionRowDTO.getDisplayvalue());
                    // 閉じ括弧
                    definitionsDO.setHsdCclosedparenthsis(queryConditionRowDTO.getClosedparenthsis());
                    // 最終更新者
                    definitionsDO.setHsdCmodifieruserid(loginUserId);
                    histSearchDefinitionsService.save(definitionsDO);
                    // カウント
                    querySeq++;
                }
            }
        } else {
            // 簡易版登録処理開始
            List<ConditionWhereDTO> whereDTOList = settingDTO.getWhereDtoList();
            for (ConditionWhereDTO conditionWhereDTO : whereDTOList) {
                for (ConditionWhereValueDTO conditionWhereValueDTO : conditionWhereDTO.getSelectValue()) {
                    HistSearchWhereDO whereDO = new HistSearchWhereDO();
                    BeanUtil.copyProperties(conditionWhereValueDTO,whereDO);
                    if (conditionWhereDTO.getUse()) {
                        whereDO.setHswCuse("1");
                    }
                    whereDO.setHswNsettingid(oHssNsettingid);
                    whereDO.setHswCmodifieruserid(loginUserId);
                    histSearchWhereService.save(whereDO);
                }
            }
        }

        if (Objects.isNull(searchSettingId)) {
            // 更新時はOrder by句Dtoを削除しておく
            Map<String,Object> map = MapUtil.<String,Object>builder()
                    .put("HSO_NSETTINGID",oHssNsettingid).build();
            histSearchOrderService.removeByMap(map);
        }

        List<ConditionOrderDTO> orderDtoList = settingDTO.getOrderDtoList();
        for (ConditionOrderDTO orderDTO : orderDtoList) {
            HistSearchOrderDO orderDO = new HistSearchOrderDO();
            BeanUtil.copyProperties(orderDTO,orderDO);
            if (StrUtil.isBlank(orderDO.getHsoCcolumn())) {
                break;
            }
            orderDO.setHsoNsettingid(oHssNsettingid);
            histSearchOrderService.save(orderDO);
        }

        // 共有範囲登録処理
        List<ConditionSettingTargetDTO> targetDtoList = settingDTO.getTargetDtoList();
        if (CollUtil.isNotEmpty(targetDtoList)) {
            if (Objects.nonNull(searchSettingId)){
                Map<String,Object> map = MapUtil.<String,Object>builder()
                        .put("HST_NSETTINGID",oHssNsettingid).build();
                histSearchSettingTargetService.removeByMap(map);
            }
            for (ConditionSettingTargetDTO targetDTO : targetDtoList) {
                HistSearchSettingTargetDO targetDO = new HistSearchSettingTargetDO();
                BeanUtil.copyProperties(targetDTO,targetDO);
                targetDO.setHstNsettingid(oHssNsettingid);
                histSearchSettingTargetService.save(targetDO);
            }
        }
        return GlobalResponse.ok("設定を保存しました。");
    }

    @Override
    public List<CommonConditionVO> getConditionVoList() {
        List<String> companyList = searchCompanyUtil.getCompList(DateUtil.date());
        String loginUserId = SecurityUtil.getUserId();
        // 属するグループを取得
        Map<String, List<LoginGroupBO>> hmGroups = SecurityUtil.getLoginUser().getLoginGroups();
        // 自由条件検索のシステムコードを取得
        List<LoginGroupBO> loginGroups = hmGroups.get("01");
        List<String> groupIds = loginGroups.stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
        return histSearchSettingService.selectList("01",loginUserId,companyList,groupIds);
    }

    /**
     * 設定に沿って検索を実行するSQLを組み立てます
     *
     * @param settingDto 検索条件
     * @return String
     */
    private String getSearchSql(ConditionSettingDTO settingDto, SqlBO sqlBO) {
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
