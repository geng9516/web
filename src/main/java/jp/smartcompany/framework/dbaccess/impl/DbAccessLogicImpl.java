package jp.smartcompany.framework.dbaccess.impl;

import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.framework.dbaccess.DbAccessLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.Vector;

/**
 * SqlExecutor封装类
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbAccessLogicImpl implements DbAccessLogic {

    @Override
    public Object executeQuery(Connection connection,String sSql) throws SQLException {
        return SqlExecutor.query(connection,sSql ,new EntityListHandler());
    }

    @Override
    public Object executeQuery(Connection connection,String sSql, Vector vecParam) throws SQLException {
        return SqlExecutor.query(connection,sSql ,new EntityListHandler(),vecParam);
    }

    @Override
    public int executeUpdate(Connection connection,String sSql) throws SQLException {
        return SqlExecutor.execute(connection,sSql ,new EntityListHandler());
    }

    @Override
    public int executeUpdate(Connection connection,String sSql,Vector vecParam) throws SQLException {
        return SqlExecutor.execute(connection,sSql ,new EntityListHandler(),vecParam);
    }

    @Override
    public boolean executeProcedure(Connection connection, Vector vecQuery, Vector vecParam) throws SQLException {
        for (int i = 0; i < vecQuery.size(); i++) {
            Vector vParam = (Vector) vecParam.get(i);
            CallableStatement csStatement = connection.prepareCall("{" + vecQuery.get(i) + "}");
            if (!vParam.isEmpty()) {
                for (int nVcnt = 0; nVcnt < vParam.size(); nVcnt++) {
                    setProcedureParam(vParam.get(nVcnt), csStatement, nVcnt);
                }
            }
            csStatement.execute();
        }
        return true;
    }

    private void setProcedureParam(Object oPram, CallableStatement csStatement,
                                  int nVcnt) throws SQLException {
        if ((oPram instanceof byte[])) {
            ByteArrayInputStream isParam = new ByteArrayInputStream(
                    (byte[]) oPram);
            csStatement.setBinaryStream(nVcnt + 1, isParam,
                    ((byte[]) oPram).length);
        } else if ((oPram instanceof Long)) {
            csStatement.setLong(nVcnt + 1, ((Long) oPram).longValue());
        } else if ((oPram instanceof Boolean)) {
            csStatement.setBoolean(nVcnt + 1, ((Boolean) oPram).booleanValue());
        } else if ((oPram instanceof String)) {
            csStatement.setString(nVcnt + 1, (String) oPram);
        } else if ((oPram instanceof BigDecimal)) {
            csStatement.setBigDecimal(nVcnt + 1, (BigDecimal) oPram);
        } else if ((oPram instanceof Date)) {
            csStatement.setDate(nVcnt + 1, (Date) oPram);
        } else if ((oPram instanceof Time)) {
            csStatement.setTime(nVcnt + 1, (Time) oPram);
        } else if ((oPram instanceof Timestamp)) {
            csStatement.setTimestamp(nVcnt + 1, (Timestamp) oPram);
        } else if ((oPram instanceof Double)) {
            csStatement.setDouble(nVcnt + 1, ((Double) oPram).doubleValue());
        } else if ((oPram instanceof Integer)) {
            csStatement.setInt(nVcnt + 1, ((Integer) oPram).intValue());
        } else if ((oPram instanceof Float)) {
            csStatement.setFloat(nVcnt + 1, ((Float) oPram).floatValue());
        } else if ((oPram instanceof Object)) {
            csStatement.setObject(nVcnt + 1, oPram);
        } else {
            throw new SQLException();
        }
    }

}
