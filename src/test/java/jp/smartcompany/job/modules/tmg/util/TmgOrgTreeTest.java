package jp.smartcompany.job.modules.tmg.util;

import jp.smartcompany.job.NextJobApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NextJobApplication.class)
class TmgOrgTreeTest {

    @Autowired
    TmgOrgTree tmgOrgTree;

    @Test
    void createOrgTree() throws Exception {

        tmgOrgTree.createOrgTree("'"+"01"+"'", "'"+"01"+"'", "'"+"ja"+"'", "to_date('2020/05/15', 'yyyy/mm/dd')");

    }
}