package jp.smartcompany.boot.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jp.smartcompany.boot.common.Constant;

import java.util.Map;

/**
 * 分页参数
 */
public class PageQuery<T> {

  public Page<T> getPage(Map<String, Object> params) {
    return this.getPage(params, null, false);
  }

  public Page<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
    //分页参数
    long curPage = 1;
    long limit = 10;

    if(params.get(Constant.KEY_PAGE) != null){
      curPage = Long.parseLong((String)params.get(Constant.KEY_PAGE));
    }
    if(params.get(Constant.KEY_LIMIT) != null){
      limit = Long.parseLong((String)params.get(Constant.KEY_LIMIT));
    }

    //分页对象
    Page<T> page = new Page<>(curPage, limit);

    //分页参数
    params.put(Constant.KEY_PAGE, page);

    //排序字段
    //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
    String orderField = SqlFilter.sqlInject((String)params.get(Constant.KEY_ORDER_FIELD));
    String order = (String)params.get(Constant.KEY_ORDER);

    //前端字段排序
    if(StrUtil.isAllNotEmpty(orderField,order)){
      if(Constant.KEY_ASC.equalsIgnoreCase(order)) {
        return  page.addOrder(OrderItem.asc(orderField));
      }else {
        return page.addOrder(OrderItem.desc(orderField));
      }
    }

    //没有排序字段，则不排序
    if(StrUtil.isBlank(defaultOrderField)){
      return page;
    }

    //默认排序
    if(isAsc) {
      page.addOrder(OrderItem.asc(defaultOrderField));
    }else {
      page.addOrder(OrderItem.desc(defaultOrderField));
    }

    return page;
  }
}