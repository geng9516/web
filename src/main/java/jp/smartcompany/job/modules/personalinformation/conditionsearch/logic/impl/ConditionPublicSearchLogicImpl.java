package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.handler.StringHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionPublicSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConditionPublicSearchLogicImpl implements IConditionPublicSearchLogic {

    private final IHistSearchSettingService histSearchSettingService;
    private final IHistSearchSelectService histSearchSelectService;
    private final IHistSearchWhereService histSearchWhereService;
    private final IHistSearchDefinitionsService histSearchDefinitionsService;
    private final IHistSearchOrderService histSearchOrderService;
    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IHistSearchSettingTargetService histSearchSettingTargetService;
    private final IConditionSearchService conditionSearchService;
    private final ScCacheUtil cacheUtil;
    private final DataSource dataSource;

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
//        String sJkPermitOverwrite = cacheUtil.getSystemProperty(PROP_JK_PERMIT_OVERWRITE);
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
        }
//        else {
//            Boolean overwrite = settingDTO.getOverwrite();
//            // IDを取得
            oHssNsettingid = settingDTO.getHseNsettingid();
//            // 作成者取得
//            String owner = histSearchSettingService.selectSettingOwner(oHssNsettingid);
//            // ログインユーザが作者の場合は上書保存確認を返す。
//            if(StrUtil.equalsIgnoreCase(owner,loginUserId)){
//                if(!overwrite) {
//                    return GlobalResponse.error(30004,"現在読み込み中の設定に上書きします。よろしいですか？");
//                }
//            } else if (!StrUtil.equalsIgnoreCase(owner,loginUserId)
//                    && StrUtil.equalsIgnoreCase(sJkPermitOverwrite,"yes")) {
//                if(!overwrite) {
//                    return GlobalResponse.error(30005,"現在他の読み込み中の設定に上書きします。よろしいですか？");
//                }
//            } else {
//                return GlobalResponse.error(30006,"設定名が重複しています。別名を使用してください。");
//            }
//        }
        histSearchSettingService.removeById(oHssNsettingid);

        HistSearchSettingDO settingDO = new HistSearchSettingDO();
        BeanUtil.copyProperties(settingDTO,settingDO);
        settingDO.setHseCuserid(loginUserId);
        if (settingDTO.getShowMastCode()){
            settingDO.setHseCmastercodeflg("1");
        } else {
            settingDO.setHseCmastercodeflg("0");
        }
        settingDO.setHseCcustomerid(psSession.getLoginCustomer());
        settingDO.setHseCmodifieruserid(loginUserId);
        if (settingDTO.getStandardDateType()!=null && settingDTO.getStandardDateType().equals(1)) {
            settingDO.setHseCusedate("1");
        } else {
            settingDO.setHseCusedate("0");
        }

        histSearchSettingService.save(settingDO);

        if (Objects.nonNull(searchSettingId)){
            // 更新時はSelect句Dtoを削除しておく
            Map<String,Object> map = MapUtil.<String,Object>builder()
                    .put("HSE_NSETTINGID",oHssNsettingid).build();
            histSearchSettingService.removeByMap(map);
        }

        List<ConditionSelectDTO> selectDTOList = settingDTO.getSelectDtoList();
        int selectSeq = 0;

        Date now = DateUtil.date();

        if (CollUtil.isNotEmpty(selectDTOList)) {
            for (ConditionSelectDTO selectDTO : selectDTOList) {
                if (Objects.isNull(selectDTO.getHssCcolumn())) {
                    break;
                }
                HistSearchSelectDO selectDO = new HistSearchSelectDO();
                BeanUtil.copyProperties(selectDTO, selectDO);
                selectDO.setHssNsettingid(oHssNsettingid);
                selectDO.setHssCmodifieruserid(loginUserId);
                selectDO.setHssNseq(selectSeq);
                selectDO.setHssDmodifieddate(now);
                // Select句Dtoを挿入
                histSearchSelectService.save(selectDO);
                selectSeq++;
            }
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
        List<QueryConditionRowDTO> queryConditionRowDTOList = settingDTO.getQueryConditionDtoList();
        if (useQueryCondition && CollUtil.isNotEmpty(queryConditionRowDTOList)) {
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
                    definitionsDO.setHsdDmodifieddate(now);
                    histSearchDefinitionsService.save(definitionsDO);
                    // カウント
                    querySeq++;
                }
            }
        } else {
            // 簡易版登録処理開始
            List<ConditionWhereDTO> whereDTOList = settingDTO.getWhereDtoList();
            if (CollUtil.isNotEmpty(whereDTOList)) {
                for (ConditionWhereDTO conditionWhereDTO : whereDTOList) {
                    for (ConditionWhereValueDTO conditionWhereValueDTO : conditionWhereDTO.getSelectValue()) {
                        HistSearchWhereDO whereDO = new HistSearchWhereDO();
                        BeanUtil.copyProperties(conditionWhereValueDTO, whereDO);
                        if (conditionWhereDTO.getUse()) {
                            whereDO.setHswCuse("1");
                        }
                        whereDO.setHswCtable(conditionWhereDTO.getMswCtableid());
                        whereDO.setHswCcolumn(conditionWhereDTO.getMswCcolumnid());
                        whereDO.setHswNsettingid(oHssNsettingid);
                        whereDO.setHswCmodifieruserid(loginUserId);
                        whereDO.setHswDmodifieddate(now);
                        histSearchWhereService.save(whereDO);
                    }
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
        if (CollUtil.isNotEmpty(orderDtoList)) {
            for (ConditionOrderDTO orderDTO : orderDtoList) {
                HistSearchOrderDO orderDO = new HistSearchOrderDO();
                BeanUtil.copyProperties(orderDTO, orderDO);
                if (StrUtil.isBlank(orderDO.getHsoCcolumn())) {
                    break;
                }
                String sortMethod = orderDTO.getHsoCorder();
                orderDO.setHsoCorder(sortMethod);
                orderDO.setHsoNsettingid(oHssNsettingid);
                orderDO.setHsoCmodifieruserid(loginUserId);
                orderDO.setHsoDmodifieddate(now);
                histSearchOrderService.save(orderDO);
            }
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
                targetDO.setHstDmodifieddate(now);
                targetDO.setHstCmodifieruserid(loginUserId);
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

    @Override
    public Map<String,Object> showAddOrUpdate(Long settingId) {
        Map<String,Object> result = MapUtil.newHashMap();
        HistSearchSettingDO settings;
        long searchSettingId = 1L;
        if (settingId != null) {
            searchSettingId = settingId;
            settings = conditionSearchService.selectHistSearchSettingBySettingId(settingId);
            result.put("settings",settings);
        }
        List<ConditionSettingTargetDTO> groupOptions = conditionSearchService.selectConditionSettingTargetList(searchSettingId);
        result.put("groups",groupOptions);
        return result;
    }

    @Override
    public Map<String, Object> getSettingDetail(Long settingId) {
        HistSearchSettingDO histSearchSettingDO = histSearchSettingService.selectSettingInfo(settingId);
        if (histSearchSettingDO==null) {
            throw new GlobalException("設定内容は存在しません");
        }
        Map<String,Object> result = MapUtil.newHashMap();

        List<HistSearchSelectDO> histSelectList = histSearchSelectService.selectBySettingId(settingId);

        histSelectList.forEach(selectItem -> {
            String column = selectItem.getHssCcolumn();
            MastDatadictionaryDO dataDictionary = cacheUtil.getDataDictionary(
                    "01" + "_" + column);
            String sTableId = dataDictionary.getMdCtablename();
            ColumnOptionDTO columnInfo = conditionSearchService.selectColumnByTableAndColumn(sTableId,column);
            selectItem.setColumnName(columnInfo.getColumnFieldName());
        });

        List<HistSearchWhereDO> histWhereList = histSearchWhereService.selectBySettingId(settingId);
        if (CollUtil.isNotEmpty(histWhereList)) {
            try (Connection conn = dataSource.getConnection()) {
                conn.setReadOnly(true);
                for (HistSearchWhereDO whereItem : histWhereList) {
                    String column = whereItem.getHswCcolumn();
                    String value = whereItem.getHswCvalue();
                        // 職員番号
                    if (StrUtil.containsIgnoreCase(column, "employeeid_ck")) {
                        String employName = SqlExecutor.query(conn, "SELECT DISTINCT ME_CKANJINAME FROM MAST_EMPLOYEES WHERE ME_CEMPLOYEEID_CK = ? ", new StringHandler(),value);
                        whereItem.setDisplayName(employName);
                        // 所属
                    } else if (StrUtil.containsIgnoreCase(column, "sectionid_fk")) {
                        String sectionName = SqlExecutor.query(conn,"SELECT DISTINCT MO_CSECTIONNAME FROM MAST_ORGANISATION WHERE MO_CSECTIONID_CK = ? ",new StringHandler(),value);
                        whereItem.setDisplayName(sectionName);
                        // 役職
                    } else if (StrUtil.containsIgnoreCase(column, "postid_fk")) {
                        String postName = SqlExecutor.query(conn,"SELECT DISTINCT MAP_CPOSTNICK FROM MAST_POST WHERE MAP_CPOSTID_CK = ? ",new StringHandler(),value);
                        whereItem.setDisplayName(postName);
                        // 本務兼務区分 本務:1 兼務:0
                    } else if (StrUtil.containsIgnoreCase(column, "HVHD_CIFKEYORADDITIONALROLE")) {
                        whereItem.setDisplayName(StrUtil.equals(value,"1")?"本務":"兼務");
                        // 在職区分 在職:1 退職:0
                    } else {
                        whereItem.setDisplayName(StrUtil.equals(value,"1")?"在職":"退職");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        List<HistSearchDefinitionsDO> histQueryWhereList = histSearchDefinitionsService.selectBySettingId(settingId);
        List<HistSearchOrderDO> histOrderList = histSearchOrderService.selectBySettingId(settingId);
        histOrderList.forEach(orderItem -> {
            String column = orderItem.getHsoCcolumn();
            MastDatadictionaryDO dataDictionary = cacheUtil.getDataDictionary(
                    "01" + "_" + column);
            String sTableId = dataDictionary.getMdCtablename();
            ColumnOptionDTO columnInfo = conditionSearchService.selectColumnByTableAndColumn(sTableId,column);
            orderItem.setColumnName(columnInfo.getColumnFieldName());
        });

        List<HistSearchSettingTargetDO> histTargetList = histSearchSettingTargetService.selectBySettingId(settingId);

        result.put("mainInfo",histSearchSettingDO);
        result.put("selectInfo",histSelectList);
        result.put("whereInfo",histWhereList);
        result.put("queryWhereInfo",histQueryWhereList);
        result.put("orderInfo",histOrderList);
        result.put("targetInfo",histTargetList);
        return result;
    }

    /**
     * 設定削除処理
     */
    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void deleteSettings(Long settingId) {
        // 他者作成の設定を削除できるかどうか
//        String permitDelete = cacheUtil.getSystemProperty(PROP_JK_PERMIT_DELETE);
        // 作成者取得
//        String owner = conditionSearchService.selectSettingOwner(settingId);
//        String loginUserId = SecurityUtil.getUserId();
//        if (StrUtil.equalsIgnoreCase(loginUserId,owner)) {
//            if (!delFlag) {
//                return "選択された設定を削除します。よろしいですか？";
//            }
//        } else if (!StrUtil.equalsIgnoreCase(loginUserId,owner)
//           && StrUtil.equalsIgnoreCase(permitDelete,"yes")) {
//            if (!delFlag) {
//                return ""
//            }
//        }
        conditionSearchService.deleteSetting(settingId);
        conditionSearchService.deleteSelectSetting(settingId);
        conditionSearchService.deleteWhereSetting(settingId);
        conditionSearchService.deleteDefinitionSetting(settingId);
        conditionSearchService.deleteOrderSetting(settingId);
        conditionSearchService.deleteTargetSetting(settingId);

    }

}
