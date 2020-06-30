package jp.smartcompany.boot.interceptor;

import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.Statement;

@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "query",
        args = {Statement.class, ResultHandler.class}
),@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class SqlParameter2NullInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        boolean isInterceptor = SqlCommandType.SELECT == mappedStatement.getSqlCommandType() || StatementType.CALLABLE == mappedStatement.getStatementType();
        if (isInterceptor) {
            BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
            String originalSql = boundSql.getSql();
            originalSql=originalSql.replaceAll("(,\\s*,)",",null,");
            originalSql=originalSql.replaceAll("\\(\\)","(null)");
            originalSql=originalSql.replaceAll("(\\(\\s*,)","(null,");
            originalSql=originalSql.replaceAll("(,\\s*\\))",",null)");
            // 防止系统函数也被填充null值
            originalSql=originalSql.replaceAll("ROWNUM\\s*\\(\\s*NULL\\s*\\)","ROWNUM()");
            originalSql=originalSql.replaceAll("ROWNUM\\s*\\(\\s*null\\s*\\)","ROWNUM()");
            metaObject.setValue("delegate.boundSql.sql", originalSql);
        }
        return invocation.proceed();
    }

}
