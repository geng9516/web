package jp.smartcompany.job.modules.tmg.util;

import jp.smartcompany.job.NextJobApplication;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NextJobApplication.class)
class TmgMemberListTest {

    @Autowired
    TmgMemberList tmgMemberList;

    @Autowired
    PsDBBean psDBBean;

    @Test
    void createMemberList001() throws Exception {

        psDBBean.setCustID("01");
        psDBBean.setCompCode("01");
        psDBBean.setUserCode("46402406");
        psDBBean.setLanguage("ja");

        tmgMemberList.createMemberList("to_date('2020/05/15', 'yyyy/mm/dd')", true);


    }

    @Test
    void createMemberList002() throws Exception {

        psDBBean.setCustID("01");
        psDBBean.setCompCode("01");
        psDBBean.setLanguage("ja");

        tmgMemberList.createMemberList("to_date('2020/05/15', 'yyyy/mm/dd')", true, "KANANAME", "BROADMATCH", "ｱｲ");
    }

    @Test
    void createMemberList003() throws Exception {

        tmgMemberList.isThereEmployeesInSection(new String[]{"306000306110", "306000306120"}, "to_date('2020/05/15', 'yyyy/mm/dd')", "to_date('2020/05/15', 'yyyy/mm/dd')");
    }
}