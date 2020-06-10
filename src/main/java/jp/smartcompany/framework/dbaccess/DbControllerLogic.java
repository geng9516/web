package jp.smartcompany.framework.dbaccess;

import java.sql.SQLException;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 */
public interface DbControllerLogic {

    Vector<Vector<Object>> executeQuery(String sSql) throws SQLException;

}
