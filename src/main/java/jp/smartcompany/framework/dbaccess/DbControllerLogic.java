package jp.smartcompany.framework.dbaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
public interface DbControllerLogic {

    Vector<Vector<Object>> executeQuery(String sSql, Connection connection) throws SQLException;

    Vector<Vector<Object>> executeQuery(String sSql, Vector vecParam,Connection connection) throws SQLException;

    Vector<Integer> executeUpdate(Vector vecQuery) throws SQLException;

    Vector<Integer> executeUpdate(Vector vecQuery, Vector vecParam) throws SQLException;

    boolean executeProcedure(Vector vecQuery, Vector vecParam) throws SQLException;
}
