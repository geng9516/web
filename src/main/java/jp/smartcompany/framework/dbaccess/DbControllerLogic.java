package jp.smartcompany.framework.dbaccess;

import java.sql.SQLException;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
public interface DbControllerLogic {

    Vector<Vector<Object>> executeQuery(String sSql) throws SQLException;

    Vector<Vector<Object>> executeQuery(String sSql, Vector vecParam) throws SQLException;

    Vector<Integer> executeUpdate(Vector vecQuery) throws SQLException;

    Vector<Integer> executeUpdate(Vector vecQuery, Vector vecParam) throws SQLException;

    boolean executeProcedure(Vector vecQuery, Vector vecParam) throws SQLException;
}
