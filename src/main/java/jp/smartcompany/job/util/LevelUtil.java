package jp.smartcompany.job.util;

import cn.hutool.core.util.StrUtil;

/**
 * @author Xiao Wenpeng
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel, Long parentId) {
        if (StrUtil.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StrUtil.join(SEPARATOR,parentLevel, parentId);
        }
    }

}

