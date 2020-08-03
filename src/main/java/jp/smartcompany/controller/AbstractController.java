package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.util.SysUtil;

import java.lang.reflect.Method;

/**
 * @author Xiao
 */
public abstract class AbstractController {

    public String getSysDate() {
        return SysUtil.transDateToString(DateUtil.date());
    }

}
