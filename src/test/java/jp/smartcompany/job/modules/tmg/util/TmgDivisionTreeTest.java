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
class TmgDivisionTreeTest {

    @Autowired
    PsDBBean psDBBean;

    /**
     *正常系テスト（結果データセット：あり）
     */
    @Test
    void createDivisionTree001() throws Exception {

        TmgDivisionTree tmgDivisionTree = new TmgDivisionTree( psDBBean);

        tmgDivisionTree.createDivisionTree("'"+"01"+"'", "'"+"01"+"'", "'"+"ja"+"'", "to_date('2020/05/18', 'yyyy/mm/dd')");
    }

    /**
     *正常系テスト（結果データセット：null）
     */
    @Test
    void createDivisionTree002() throws Exception {

        TmgDivisionTree tmgDivisionTree = new TmgDivisionTree( psDBBean);
        tmgDivisionTree.createDivisionTree("'"+"02"+"'", "'"+"02"+"'", "'"+"ja"+"'", "to_date('2020/05/18', 'yyyy/mm/dd')");
    }
}