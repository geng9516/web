package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.NextJobApplication;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NextJobApplication.class)
class TmgGroupListTest {

    @Autowired
    TmgGroupList tmgGroupList;

    @Autowired
    PsDBBean psDBBean;

    @Test
    void createGroupList() throws Exception {

        psDBBean.setCustID("01");
        psDBBean.setCompCode("01");
        psDBBean.setUserCode("46402406");
        psDBBean.setLanguage("ja");
        tmgGroupList.createGroupList("to_date('2020/05/18', 'yyyy/mm/dd')","to_date('2020/05/18', 'yyyy/mm/dd')");
    }
}