package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IConditionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConditionSearchLogicImpl implements IConditionSearchLogic {

    /** CSV最大行 */
    private static final String PROP_JK_MAX_COLS = "JkMaxCols";
    private final ScCacheUtil cacheUtil;

    private final IConditionSearchService conditionSearchService;

    /**
     * 自由条件検索画面表示処理
     */
    @Override
    public Map<String,Object> getConditionOptions() {
        Map<String,Object> result = MapUtil.newHashMap();
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
        List<TableOptionDTO> tableOptionList = conditionSearchService.selectTable();
        result.put("tableOptions",tableOptionList);
        return result;
    }

}
