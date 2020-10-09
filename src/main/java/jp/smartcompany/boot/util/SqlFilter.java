package jp.smartcompany.boot.util;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalException;

/**
 * SQL过滤
 *
 * @author 肖文彭
 */
public class SqlFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StrUtil.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StrUtil.replace(str, "'", "");
        str = StrUtil.replace(str, "\"", "");
        str = StrUtil.replace(str, ";", "");
        str = StrUtil.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.contains(keyword)){
                throw new GlobalException(StrUtil.format("入力内容【{}】に不正な文字が含まれている",keyword));
            }
        }

        return str;
    }

}
