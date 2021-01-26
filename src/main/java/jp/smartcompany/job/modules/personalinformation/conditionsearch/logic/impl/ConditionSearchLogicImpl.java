package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysDateUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.ColumnOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.CompanyDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IConditionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConditionSearchLogicImpl implements IConditionSearchLogic {

    private final IConditionSearchService conditionSearchService;
    private final ScCacheUtil cacheUtil;
    private final PsSearchCompanyUtil searchCompanyUtil;

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

    @Override
    public Map<String,Object> getQueryConditions(Long settingId,Date sDate) {
        Map<String,Object> result = MapUtil.newHashMap();
        DateTime now = DateUtil.date(sDate);
        Date searchDate = SysDateUtil.of(now.year(),now.month()+1,now.dayOfMonth());
        List <String> companyIds = searchCompanyUtil.getCompList(searchDate);
        List<CompanyDTO> companyList = conditionSearchService.selectCompanyList(companyIds);
        result.put("companyList",companyList);
        result.put("defaultTab",cacheUtil.getSystemProperty("DefaultUseWhere"));
        result.put("defaultWhereOptions",conditionSearchService.selectDefaultWhereConditions(settingId));
        result.put("queryWhereOptions",conditionSearchService.selectQueryWhereConditions(settingId,searchDate));
        result.put("orderOptions",conditionSearchService.selectOrderConditions(settingId));
        return result;
    }

    /** CSV最大行 */
//    private static final String PROP_JK_MAX_COLS = "JkMaxCols";
//    private final ScCacheUtil cacheUtil;

    //        String sJkMaxCols = cacheUtil.getSystemProperty(PROP_JK_MAX_COLS);
//        if (StrUtil.isBlank(sJkMaxCols)) {
//            throw new GlobalException("MAX行は設定していません");
//        }
//        int maxCols = 0;
//        try {
//            maxCols = Integer.parseInt(sJkMaxCols);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            throw new GlobalException(e.getMessage());
//        }

}
