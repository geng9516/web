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
     *  ??????????????????
     */
    @Transactional(rollbackFor = GlobalException.class)
    @Override
    public GlobalResponse editSettings(ConditionSettingDTO settingDTO) {
        PsSession psSession = (PsSession) ContextUtil.getSession().getAttribute(Constant.PS_SESSION);
        // ??????ID
        Long oHssNsettingid;
        String loginUserId = psSession.getLoginUser();
        // ??????????????????????????????????????????????????????
//        String sJkPermitOverwrite = cacheUtil.getSystemProperty(PROP_JK_PERMIT_OVERWRITE);
        Long searchSettingId = settingDTO.getHseId();
        if (Objects.isNull(searchSettingId)) {
            // ????????????
            int sameNameCount = histSearchSettingService.selectSameSettingName(
                    settingDTO.getHseCsettingname()
            );
            // ?????????????????????????????????????????????
            if (sameNameCount>0){
                throw new GlobalException("??????????????????????????????");
            }
            oHssNsettingid = histSearchSettingService.selectSeq();
            settingDTO.setHseNsettingid(oHssNsettingid);
        }
//        else {
//            Boolean overwrite = settingDTO.getOverwrite();
//            // ID?????????
            oHssNsettingid = settingDTO.getHseNsettingid();
//            // ???????????????
//            String owner = histSearchSettingService.selectSettingOwner(oHssNsettingid);
//            // ????????????????????????????????????????????????????????????????????????
//            if(StrUtil.equalsIgnoreCase(owner,loginUserId)){
//                if(!overwrite) {
//                    return GlobalResponse.error(30004,"??????????????????????????????????????????????????????????????????????????????");
//                }
//            } else if (!StrUtil.equalsIgnoreCase(owner,loginUserId)
//                    && StrUtil.equalsIgnoreCase(sJkPermitOverwrite,"yes")) {
//                if(!overwrite) {
//                    return GlobalResponse.error(30005,"????????????????????????????????????????????????????????????????????????????????????");
//                }
//            } else {
//                return GlobalResponse.error(30006,"????????????????????????????????????????????????????????????????????????");
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
            // ????????????Select???Dto?????????????????????
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
                // Select???Dto?????????
                histSearchSelectService.save(selectDO);
                selectSeq++;
            }
        }
        // ?????????
        // ?????????
        if (Objects.isNull(searchSettingId)) {
            Map<String,Object> whereParams = MapUtil.<String,Object>builder().put("HSW_NSETTINGID",searchSettingId).build();
            histSearchWhereService.removeByMap(whereParams);
            whereParams = MapUtil.<String,Object>builder().put("HSD_NSETTINGID",searchSettingId).build();
            histSearchDefinitionsService.removeByMap(whereParams);
        }
        Boolean useQueryCondition = settingDTO.getUseQueryDefinition();
        // ??????????????????????????????(?????????????????????)
        List<QueryConditionRowDTO> queryConditionRowDTOList = settingDTO.getQueryConditionDtoList();
        if (useQueryCondition && CollUtil.isNotEmpty(queryConditionRowDTOList)) {
            int querySeq = 0;
            for (QueryConditionRowDTO queryConditionRowDTO : queryConditionRowDTOList) {
                // ???????????????
                String sOperator         = queryConditionRowDTO.getOperator();
                // ???
                String sValue            = queryConditionRowDTO.getValue();
                // ????????????
                String sDispValue        = queryConditionRowDTO.getDisplayvalue();
                // ????????????
                String sOpenedparenthsis = queryConditionRowDTO.getOpenedparenthsis();
                // ????????????
                String sClosedparenthsis = queryConditionRowDTO.getClosedparenthsis();

                if (SysUtil.isAnyBlank(sOperator,sValue,sDispValue,sOpenedparenthsis,sClosedparenthsis)) {
                    HistSearchDefinitionsDO definitionsDO = new HistSearchDefinitionsDO();
                    // ??????ID
                    definitionsDO.setHsdNsettingid(oHssNsettingid);
                    // ?????????
                    definitionsDO.setHsdNseq(querySeq);
                    // ???????????????
                    definitionsDO.setHsdCandor(queryConditionRowDTO.getAndor());
                    // ??????
                    definitionsDO.setHsdCopenedparenthsis(queryConditionRowDTO.getOpenedparenthsis());
                    // ????????????ID
                    definitionsDO.setHsdCtableid(queryConditionRowDTO.getTableid());
                    // ?????????ID
                    definitionsDO.setHsdCcolumnid(queryConditionRowDTO.getColumnid());
                    // ????????????
                    definitionsDO.setHsdCcolumnname(queryConditionRowDTO.getColumnname());
                    // ????????????
                    definitionsDO.setHsdCtypeofcolumn(queryConditionRowDTO.getTypeofcolumn());
                    // ?????????
                    definitionsDO.setHsdCoperator(queryConditionRowDTO.getOperator());
                    // ?????????
                    definitionsDO.setHsdCvalue(queryConditionRowDTO.getValue());
                    // ???????????????
                    definitionsDO.setHsdCdisplayvalue(queryConditionRowDTO.getDisplayvalue());
                    // ????????????
                    definitionsDO.setHsdCclosedparenthsis(queryConditionRowDTO.getClosedparenthsis());
                    // ???????????????
                    definitionsDO.setHsdCmodifieruserid(loginUserId);
                    definitionsDO.setHsdDmodifieddate(now);
                    histSearchDefinitionsService.save(definitionsDO);
                    // ????????????
                    querySeq++;
                }
            }
        } else {
            // ???????????????????????????
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
            // ????????????Order by???Dto?????????????????????
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

        // ????????????????????????
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
        return GlobalResponse.ok("??????????????????????????????");
    }

    @Override
    public List<CommonConditionVO> getConditionVoList() {
        List<String> companyList = searchCompanyUtil.getCompList(DateUtil.date());
        String loginUserId = SecurityUtil.getUserId();
        // ??????????????????????????????
        Map<String, List<LoginGroupBO>> hmGroups = SecurityUtil.getLoginUser().getLoginGroups();
        // ???????????????????????????????????????????????????
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
            throw new GlobalException("?????????????????????????????????");
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
                        // ????????????
                    if (StrUtil.containsIgnoreCase(column, "employeeid_ck")) {
                        String employName = SqlExecutor.query(conn, "SELECT DISTINCT ME_CKANJINAME FROM MAST_EMPLOYEES WHERE ME_CEMPLOYEEID_CK = ? ", new StringHandler(),value);
                        whereItem.setDisplayName(employName);
                        // ??????
                    } else if (StrUtil.containsIgnoreCase(column, "sectionid_fk")) {
                        String sectionName = SqlExecutor.query(conn,"SELECT DISTINCT MO_CSECTIONNAME FROM MAST_ORGANISATION WHERE MO_CSECTIONID_CK = ? ",new StringHandler(),value);
                        whereItem.setDisplayName(sectionName);
                        // ??????
                    } else if (StrUtil.containsIgnoreCase(column, "postid_fk")) {
                        String postName = SqlExecutor.query(conn,"SELECT DISTINCT MAP_CPOSTNICK FROM MAST_POST WHERE MAP_CPOSTID_CK = ? ",new StringHandler(),value);
                        whereItem.setDisplayName(postName);
                        // ?????????????????? ??????:1 ??????:0
                    } else if (StrUtil.containsIgnoreCase(column, "HVHD_CIFKEYORADDITIONALROLE")) {
                        whereItem.setDisplayName(StrUtil.equals(value,"1")?"??????":"??????");
                        // ???????????? ??????:1 ??????:0
                    } else {
                        whereItem.setDisplayName(StrUtil.equals(value,"1")?"??????":"??????");
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
     * ??????????????????
     */
    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void deleteSettings(Long settingId) {
        // ???????????????????????????????????????????????????
//        String permitDelete = cacheUtil.getSystemProperty(PROP_JK_PERMIT_DELETE);
        // ???????????????
//        String owner = conditionSearchService.selectSettingOwner(settingId);
//        String loginUserId = SecurityUtil.getUserId();
//        if (StrUtil.equalsIgnoreCase(loginUserId,owner)) {
//            if (!delFlag) {
//                return "??????????????????????????????????????????????????????????????????";
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
