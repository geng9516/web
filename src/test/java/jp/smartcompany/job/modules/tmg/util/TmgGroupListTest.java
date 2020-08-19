package jp.smartcompany.job.modules.tmg.util;

import jp.smartcompany.NextJobApplication;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NextJobApplication.class)
class TmgGroupListTest {

    @Autowired
    PsDBBean psDBBean;

    @Test
    void createGroupList001() throws Exception {

        psDBBean.setCustID("01");
        psDBBean.setCompCode("01");
        psDBBean.setUserCode("46402406");
        psDBBean.setLanguage("ja");
        TmgGroupList tmgGroupList = new TmgGroupList( psDBBean);
        tmgGroupList.createGroupList("to_date('2020/05/18', 'yyyy/mm/dd')","to_date('2020/05/18', 'yyyy/mm/dd')");
    }

    @Test
    void createGroupList001_1() throws Exception {

        psDBBean.setCustID("02");
        psDBBean.setCompCode("02");
        psDBBean.setUserCode("46402406");
        psDBBean.setLanguage("ja");
        TmgGroupList tmgGroupList = new TmgGroupList( psDBBean);
        tmgGroupList.createGroupList("to_date('2020/05/18', 'yyyy/mm/dd')","to_date('2020/05/18', 'yyyy/mm/dd')");
    }

}