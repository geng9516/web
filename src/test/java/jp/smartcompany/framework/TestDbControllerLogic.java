package jp.smartcompany.framework;

import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.framework.dbaccess.DbControllerLogic;
import jp.smartcompany.job.NextJobApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@SpringBootTest(classes = NextJobApplication.class)
@RunWith(SpringRunner.class)
public class TestDbControllerLogic {

    private DbControllerLogic dbControllerLogic;

    @Before
    public void before() {
        dbControllerLogic = SpringUtil.getBean(DbControllerLogic.class);
    }

    @Test
    public void testExecuteQuery() throws SQLException {
        System.out.println(dbControllerLogic.executeQuery("select * from mast_apptree order by mtr_nseq"));
    }


}
