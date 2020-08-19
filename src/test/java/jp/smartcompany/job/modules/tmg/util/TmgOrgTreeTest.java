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
class TmgOrgTreeTest {

    @Autowired
    PsDBBean psDBBean;

    @Test
    void createOrgTree001() throws Exception {
        TmgOrgTree tmgOrgTree = new TmgOrgTree( psDBBean);
        tmgOrgTree.createOrgTree("'"+"01"+"'", "'"+"01"+"'", "'"+"ja"+"'", "to_date('2020/05/15', 'yyyy/mm/dd')");

    }

    @Test
    void createOrgTree001_1() throws Exception {
        TmgOrgTree tmgOrgTree = new TmgOrgTree( psDBBean);
        tmgOrgTree.createOrgTree("'"+"02"+"'", "'"+"02"+"'", "'"+"ja"+"'", "to_date('2020/05/15', 'yyyy/mm/dd')");

    }
}