package jp.smartcompany.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.NextJobApplication;
import jp.smartcompany.job.modules.core.util.PsDBBean;

import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest(classes = NextJobApplication.class)
@RunWith(SpringRunner.class)
public class DeptStatListControllerTest {

    private DeptStatListController deptStatListController;

    @Before
    public void before() {
        deptStatListController = SpringUtil.getBean(DeptStatListController.class);
    }

    @Test
    public void testExecuteDispStatList() throws Exception {
        PsDBBean psDBBean = new PsDBBean();

        psDBBean.setRequestHash(MapUtil.newHashMap());

        psDBBean.setCustID("01");
        psDBBean.setCompCode("01");
        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
        psDBBean.setLanguage("ja");
        psDBBean.setUserCode("46402406");

        psDBBean.getRequestHash().put("txtTDA_DYYYYMM","2020/04/01");


        //Map map= deptStatListController.executeDispStatList(psDBBean);
       // System.out.println(map);

    }

}
