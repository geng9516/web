package jp.smartcompany.framework.dbaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
public interface DbAccessLogic {

    Object executeQuery(Connection connection,String sSql) throws SQLException;

    Object executeQuery(Connection connection,String sSql, Vector vecParam) throws SQLException;

    int executeUpdate(Statement statement, String sSql) throws SQLException;

    int executeUpdate(Connection connection,String sSql,Vector vecParam) throws SQLException;

    boolean executeProcedure(Connection connection, Vector vecQuery, Vector vecParam) throws SQLException;
}
