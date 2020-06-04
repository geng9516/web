package jp.smartcompany.job.interceptor;

import cn.hutool.core.util.StrUtil;
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

import java.sql.Statement;

@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "query",
        args = {Statement.class, ResultHandler.class}
)})
public class SqlParameter2NullInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        boolean isInterceptor = (
                SqlCommandType.SELECT == mappedStatement.getSqlCommandType()
                && StatementType.STATEMENT == mappedStatement.getStatementType()
        ) || StatementType.CALLABLE == mappedStatement.getStatementType();
        if (isInterceptor) {
            BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
            String originalSql = boundSql.getSql();
            String parsedSql=StrUtil.replace(originalSql,"''","null");
            metaObject.setValue("delegate.boundSql.sql", parsedSql);
        }
        return invocation.proceed();
    }

}
