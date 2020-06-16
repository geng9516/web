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
import java.util.*;

/**
 * @author Xiao Wenpeng
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbControllerLogicImpl implements DbControllerLogic {

    private final DbAccessLogic dbAccessLogic;
    private final DataSource dataSource;

    @Override
    public Vector<Vector<Object>> executeQuery(String sSql) throws SQLException {
        if (StrUtil.isBlank(sSql)) {
            throw new GlobalException("SEARCH_SQL");
        }
        Connection connection = dataSource.getConnection();
        Object oSqlResult = dbAccessLogic.executeQuery(connection,sSql);
        if (connection!=null){
            connection.close();
        }
        return convertData(oSqlResult);
    }

    @Override
    public Vector<Vector<Object>> executeQuery(String sSql, Vector vecParam) throws SQLException {
        if (StrUtil.isBlank(sSql)) {
            throw new GlobalException("SEARCH_SQL");
        }
        Connection connection = dataSource.getConnection();
        Object oSqlResult = dbAccessLogic.executeQuery(connection,
                sSql, vecParam);
        if (connection!=null){
            connection.close();
        }
        return convertData(oSqlResult);
    }

    @Override
    public Vector<Integer> executeUpdate(Vector vecQuery) throws SQLException {
        if (CollUtil.isEmpty(vecQuery)) {
            throw new GlobalException("UPDATE_SQL");
        }
        Vector<Integer> vecResult = new Vector<>();
        Connection connection = dataSource.getConnection();
        for (Object sql : vecQuery) {
            int nCount = dbAccessLogic.executeUpdate(connection, (String)sql);
            vecResult.add(nCount);
        }
        if (connection!=null){
            connection.close();
        }
        return vecResult;
    }

    @Override
    public Vector<Integer> executeUpdate(Vector vecQuery, Vector vecParam) throws SQLException {
        if (CollUtil.isEmpty(vecQuery)) {
            throw new GlobalException("UPDATE_SQL");
        }
        Vector<Integer> vecResult = new Vector<>();
        Connection connection = dataSource.getConnection();
        for (Object sql : vecQuery) {
            int nCount = dbAccessLogic.executeUpdate(connection, (String)sql,vecParam);
            vecResult.add(nCount);
        }
        if (connection!=null){
            connection.close();
        }
        return vecResult;
    }

    @Override
    public boolean executeProcedure(Vector vecQuery, Vector vecParam) throws SQLException {
        if (CollUtil.isEmpty(vecQuery)) {
            throw new GlobalException("PROCEDUR_QUERY");
        }
        Connection connection = dataSource.getConnection();
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
        if (connection!=null){
            connection.close();
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
        log.info("【convertData:{}】",vecResult);
        return vecResult;
    }

}
