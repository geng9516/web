package jp.smartcompany.framework.dbaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
public interface DbControllerLogic {

    Vector<Vector<Object>> executeQuery(String sSql, Connection connection) throws SQLException;

    Vector<Integer> executeUpdate(Vector vecQuery) ;

    Vector<Integer> executeUpdate(Vector vecQuery, Vector vecParam) ;

    boolean executeProcedure(Vector vecQuery, Vector vecParam) ;
}
