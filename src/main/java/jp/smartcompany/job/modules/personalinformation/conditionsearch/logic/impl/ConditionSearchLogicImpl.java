package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.impl;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConditionSearchLogicImpl implements IConditionSearchLogic {

    /** CSV最大行 */
    private static final String PROP_JK_MAX_COLS = "JkMaxCols";
    private final ScCacheUtil cacheUtil;

    /**
     * 自由条件検索画面表示処理
     */
    public void getConditionOptions() {
        String sJkMaxCols = cacheUtil.getSystemProperty(PROP_JK_MAX_COLS);
        if (StrUtil.isBlank(sJkMaxCols)) {
            throw new GlobalException("MAX行は設定していません");
        }
        int maxCols = 0;
        try {
            maxCols = Integer.parseInt(sJkMaxCols);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }

    }

}
