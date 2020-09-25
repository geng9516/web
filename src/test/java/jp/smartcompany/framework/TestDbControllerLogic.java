package jp.smartcompany.framework;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.framework.dbaccess.DbControllerLogic;
import jp.smartcompany.NextJobApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

@SpringBootTest(classes = NextJobApplication.class)
@RunWith(SpringRunner.class)
public class TestDbControllerLogic {

    private DbControllerLogic dbControllerLogic;
    private DataSource dataSource;

    @Before
    public void before() {
        dataSource = SpringUtil.getBean(DataSource.class);
        dbControllerLogic = SpringUtil.getBean(DbControllerLogic.class);
    }

    @Test
    public void testExecuteQuery() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "SELECT MGP_ID, MGP_CCOMPANYID,MGP_CSYSTEMID,MGP_CGROUPID,MGP_COBJECTID,MGP_CSITE,MGP_CAPP,MGP_CSUBAPP,MGP_CBUTTON,MGP_CSCREEN,MGP_CPERMISSION,MGP_CREJECT,MGP_DSTARTDATE,MGP_DENDDATE FROM" +
                " MAST_GROUPAPPPERMISSION WHERE MGP_CSYSTEMID = ? AND MGP_DSTARTDATE <= ? AND MGP_DENDDATE >= ? AND MGP_CGROUPID = ? AND MGP_COBJECTID = ? ORDER BY MGP_COBJECTID,MGP_DSTARTDATE";
        List<Entity> list = SqlExecutor.query(connection,sql,new EntityListHandler(),"01","2020/07/10","2020/07/10","11","TopPage_substitutelogin");
        System.out.println(list);
//        System.out.println(dbControllerLogic.executeQuery("select * from mast_apptree order by mtr_nseq"));
    }

    @Test
    public void testExecuteProcedure() throws SQLException {
        Vector sql = new Vector();
        Vector<Vector<Object>> vParam = new Vector<>();
        sql.add("call TMG_P_IF_PERSONAL_PATTERN(?,?,?,?,?,?)");
        Vector<Object> p1 = new Vector<>();
        p1.add("'46402406'");
        p1.add("null");
        p1.add("'TMG_P_IF_PERSONAL_PATTERN'");
        p1.add("'01'");
        p1.add("'01'");
        p1.add("2020/06/10");
        vParam.add(p1);
        dbControllerLogic.executeProcedure(sql,vParam);
    }


}
