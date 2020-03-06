package jp.smartcompany.job.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
public class Query<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        long curPage = 1;
        long limit = 10;
        boolean sum = true;
        if(params.get(Constant.KEY_PAGE) != null){
            curPage = Long.parseLong((String)params.get(Constant.KEY_PAGE));
        }
        if(params.get(Constant.KEY_LIMIT) != null){
            limit = Long.parseLong((String)params.get(Constant.KEY_LIMIT));
        }
        if(params.get(Constant.KEY_SUM) != null) {
            sum = Boolean.parseBoolean((String) params.get(Constant.KEY_SUM));
        }
        Page<T> page = new Page<>(curPage, limit, sum);
        params.put(Constant.KEY_PAGE, page);
        // 防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = (String)params.get(Constant.KEY_ORDER_FIELD);
        String sortWay = (String)params.get(Constant.KEY_ORDER);
        String[] orders = StrUtil.split(orderField,",");
        if(orders.length>0){
          List<OrderItem> orderFieldList = CollUtil.newArrayList();
          for(String str: orders) {
              if(Constant.KEY_ASC.equalsIgnoreCase(sortWay)) {
                  orderFieldList.add(OrderItem.asc(str));
              }else {
                  orderFieldList.add(OrderItem.desc(str));
              }
          }
          page.setOrders(orderFieldList);
        }
        List<OrderItem> orderFieldList = CollUtil.newArrayList();
        if (StrUtil.isNotBlank(defaultOrderField)) {
            if (isAsc) {
                orderFieldList.add(OrderItem.asc(defaultOrderField));
            } else {
                orderFieldList.add(OrderItem.desc(defaultOrderField));
            }
            page.setOrders(orderFieldList);
        }
        return page;
    }
}
