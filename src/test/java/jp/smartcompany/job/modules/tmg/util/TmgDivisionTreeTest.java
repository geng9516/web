package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.NextJobApplication;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NextJobApplication.class)
class TmgDivisionTreeTest {

    @Autowired
    TmgDivisionTree tmgDivisionTree;

    @Test
    void createDivisionTree() throws Exception {

        tmgDivisionTree.createDivisionTree("'"+"01"+"'", "'"+"01"+"'", "'"+"ja"+"'", "to_date('2020/05/18', 'yyyy/mm/dd')");
    }
}