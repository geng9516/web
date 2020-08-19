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
class TmgEmpListTest {

    @Autowired
    PsDBBean psDBBean;

    @Test
    void createEmpList001() throws Exception {

        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createEmpList("'"+"01"+"'", "'"+"01"+"'","'"+"201000000000"+"'","to_date('2010/05/18', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')","'"+"ja"+"'",true,true,true) ;
    }

    @Test
    void createEmpList002() throws Exception {
        psDBBean.setCustID("01");
        psDBBean.setCompCode("01");
        psDBBean.setUserCode("46402406");
        psDBBean.setLanguage("ja");

        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createEmpList("'"+"01"+"'", "'"+"01"+"'","'"+"201000000000"+"'","to_date('2010/05/18', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')","'"+"ja"+"'",true,true,true,"KANANAME","BROADMATCH","ｱｲ") ;
    }

    @Test
    void createEmpList003() throws Exception {
        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createEmpList("'"+"01"+"'", "'"+"01"+"'","'"+"201000000000"+"'","to_date('2010/05/18', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')","'"+"ja"+"'",true,true,true) ;
        tmgEmpList.getDataArrayBetween("2020/05/03", "2020/05/01");
    }

    @Test
    void createEmpList004() throws Exception {
        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createWardEmpList("'"+"01"+"'", "'"+"01"+"'","'"+"TMG_ITEMS|ScheduleCheck"+"'","to_date('2019/11/10', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')",true) ;
    }

    @Test
    void createEmpList005() throws Exception {
        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createEmpList("'"+"01"+"'", "'"+"01"+"'","'"+"201000000000"+"'","to_date('2010/05/18', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')","'"+"ja"+"'",true,true,true) ;
        tmgEmpList.buildSQLForSelectEmpListFromDualTableObject(false);
    }

    @Test
    void createEmpList001_1() throws Exception {

        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createEmpList("'"+"02"+"'", "'"+"02"+"'","'"+"201000000000"+"'","to_date('2010/05/18', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')","'"+"ja"+"'",true,true,true) ;
    }

    @Test
    void createEmpList002_1() throws Exception {
        psDBBean.setCustID("02");
        psDBBean.setCompCode("02");
        psDBBean.setUserCode("46402406");
        psDBBean.setLanguage("ja");

        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createEmpList("'"+"02"+"'", "'"+"02"+"'","'"+"201000000000"+"'","to_date('2010/05/18', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')","'"+"ja"+"'",true,true,true,"KANANAME","BROADMATCH","ｱｲ") ;
    }

    @Test
    void createEmpList003_1() throws Exception {
        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createEmpList("'"+"02"+"'", "'"+"02"+"'","'"+"201000000000"+"'","to_date('2010/05/18', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')","'"+"ja"+"'",true,true,true) ;
        tmgEmpList.getDataArrayBetween("2020/05/03", "2020/05/01");
    }

    @Test
    void createEmpList004_1() throws Exception {
        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createWardEmpList("'"+"02"+"'", "'"+"02"+"'","'"+"TMG_ITEMS|ScheduleCheck"+"'","to_date('2019/11/10', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')",true) ;
    }

    @Test
    void createEmpList005_1() throws Exception {
        TmgEmpList tmgEmpList = new TmgEmpList( psDBBean);
        tmgEmpList.createEmpList("'"+"02"+"'", "'"+"02"+"'","'"+"201000000000"+"'","to_date('2010/05/18', 'yyyy/mm/dd')","to_date('2020/05/20', 'yyyy/mm/dd')","'"+"ja"+"'",true,true,true) ;
        tmgEmpList.buildSQLForSelectEmpListFromDualTableObject(false);
    }
}