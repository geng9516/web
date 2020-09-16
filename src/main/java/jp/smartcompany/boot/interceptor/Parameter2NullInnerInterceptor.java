package jp.smartcompany.boot.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * Oracle SQL参数null填充器
 * @author Xiao Wenpeng
 */
public class Parameter2NullInnerInterceptor implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        String buildSql = boundSql.getSql();
        buildSql=buildSql.replaceAll("(,\\s*,)",",null,");
        buildSql=buildSql.replaceAll("(\\(\\s*,)","(null,");
        buildSql=buildSql.replaceAll("(,\\s*\\))",",null)");
        PluginUtils.mpBoundSql(boundSql).sql(buildSql);
    }

}
