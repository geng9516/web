package jp.smartcompany.job.modules.base.pojo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Slf4j
@Component
public class BaseBeanFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 设置操作时间
        strictInsertFill(metaObject, "createTime", Date.class, new Date());
        strictUpdateFill(metaObject, "updateTime", Date.class,new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 设置更新时间
        strictUpdateFill(metaObject, "updateTime", Date.class,new Date());
    }

}
