package jp.smartcompany.framework.dbaccess.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.framework.dbaccess.DbAccessLogic;
import jp.smartcompany.framework.dbaccess.DbControllerLogic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 不支持事务操作
 * @author Xiao Wenpeng
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbControllerLogicImpl implements DbControllerLogic {

    private final DbAccessLogic dbAccessLogic;
    private final DataSource dataSource;

    @Override
    public Vector<Vector<Object>> executeQuery(String sSql,Connection connection) throws SQLException {
        if (StrUtil.isBlank(sSql)) {
            throw new GlobalException("SEARCH_SQL");
        }
        log.info("【执行的sql:{}】",sSql);
        Object oSqlResult = dbAccessLogic.executeQuery(connection,sSql);
        return convertData(oSqlResult);
    }


    @Override
    public Vector<Integer> executeUpdate(Vector vecQuery){
        if (CollUtil.isEmpty(vecQuery)) {
            throw new GlobalException("UPDATE_SQL");
        }
        Vector<Integer> vecResult = new Vector<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                for (Object sql : vecQuery) {
                    log.info("【执行sql(executeUpdate):{}】", sql);
                    int count = statement.executeUpdate((String) sql);
                    vecResult.add(count);
                }
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return vecResult;
    }

    @Override
    public Vector<Integer> executeUpdate(Vector vecQuery, Vector vecParam) {
        if (CollUtil.isEmpty(vecQuery)) {
            throw new GlobalException("UPDATE_SQL");
        }
        Vector<Integer> vecResult = new Vector<>();
        try(Connection connection = dataSource.getConnection()) {
            for (Object sql : vecQuery) {
                int nCount = dbAccessLogic.executeUpdate(connection, (String)sql,vecParam);
                vecResult.add(nCount);
            }
        } catch(SQLException e) {
            throw new GlobalException(e.getMessage());
        }
        return vecResult;
    }

    @Override
    public boolean executeProcedure(Vector vecQuery, Vector vecParam) {
        if (CollUtil.isEmpty(vecQuery)) {
            throw new GlobalException("PROCEDUR_QUERY");
        }
        try (Connection connection = dataSource.getConnection()) {
            Vector<Vector<Object>> vParam = new Vector();
            for (int i = 0; i < vecQuery.size(); i++) {
                if ((vecParam == null) || (vecParam.size() == 0)) {
                    vParam.add(new Vector());
                } else if ((i >= vecParam.size()) || (vecParam.get(i) == null)) {
                    vParam.add(new Vector());
                } else {
                    vParam.add((Vector) vecParam.get(i));
                }
            }
            dbAccessLogic.executeProcedure(connection,vecQuery,vecParam);
        } catch (SQLException e) {
            throw new GlobalException(e.getMessage());
        }
        return true;
    }

    private Vector<Vector<Object>> convertData(Object oSqlResult) {
        Vector<Vector<Object>> vecResult = new Vector<>();
        List<Entity> lSqlResult = (List<Entity>) oSqlResult;
        if (CollUtil.isNotEmpty(lSqlResult)) {
            Vector<Object> vColuName = new Vector();
            Entity entity = lSqlResult.get(0);
            vColuName.addAll(entity.getFieldNames());
            vecResult.add(vColuName);
            for (Entity en : lSqlResult) {
                Vector<Object> vColuData = new Vector();
                en.values().forEach(value->vColuData.add(value));
                vecResult.add(vColuData);
            }
        }
//        log.info("【convertData:{}】",vecResult);
        return vecResult;
    }

}
