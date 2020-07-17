package jp.smartcompany.framework;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.controller.PermStatListController;
import jp.smartcompany.job.NextJobApplication;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@SpringBootTest(classes = NextJobApplication.class)
@RunWith(SpringRunner.class)
public class TestPermStatListBean {
//
//    private PermStatListController permStatListController;
//
//    @Autowired
//    PsDBBean psDBBean;
//
//    @Before
//    public void before() {
//        permStatListController = SpringUtil.getBean(PermStatListController.class);
//    }
//
//    @Test
//    public void testDispMonthlyList() throws Exception {
//
//        psDBBean.setRequestHash(MapUtil.newHashMap());
//        psDBBean.setCustID("01");
//        psDBBean.setCompCode("01");
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_PERM);
//        psDBBean.setLanguage("ja");
//        psDBBean.setUserCode("46402406");
//        psDBBean.getRequestHash().put("txtAction","ACT_DISP_MONTHLY");
//        psDBBean.getRequestHash().put("txtDYYYYMM","2020/04/01");
//        psDBBean.getRequestHash().put("txtSectionId","");
//
//        List<DispMonthlyVO> dispMonthlyList = permStatListController.dispMonthlyList(psDBBean);
//
//    }
//
//    @Test
//    public void testDispMonthlyPrevAndNext() throws Exception {
//
//        psDBBean.setRequestHash(MapUtil.newHashMap());
//        psDBBean.setCustID("01");
//        psDBBean.setCompCode("01");
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_PERM);
//        psDBBean.setLanguage("ja");
//        psDBBean.setUserCode("46402406");
//        psDBBean.getRequestHash().put("txtAction","ACT_DISP_MONTHLY");
//        psDBBean.getRequestHash().put("txtDYYYYMM","2020/03/01");
//        psDBBean.getRequestHash().put("txtSectionId","");
//
//        int dispMonthlyPrev = permStatListController.dispMonthlyPrev(psDBBean);
//        System.out.println(dispMonthlyPrev);
//        int dispMonthlyNext = permStatListController.dispMonthlyNext(psDBBean);
//        System.out.println(dispMonthlyNext);
//
//    }
//    @Test
//    public void testGetTmgMonthlyInfoVOList() throws Exception {
//
//        psDBBean.setRequestHash(MapUtil.newHashMap());
//        psDBBean.setCustID("01");
//        psDBBean.setCompCode("01");
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_PERM);
//        psDBBean.setLanguage("ja");
//        psDBBean.setUserCode("46402406");
//        psDBBean.getRequestHash().put("txtAction","ACT_DISP_MONTHLY");
//        psDBBean.getRequestHash().put("txtDYYYYMM","2020/03/01");
//        psDBBean.getRequestHash().put("txtSectionId","");
//
//        Map tmgmgMonthlyInfoVO = permStatListController.getTmgMonthlyInfoVOList(psDBBean);
//
//        System.out.println(tmgmgMonthlyInfoVO);
//
//    }
//    @Test
//    public void testCalenderVo() throws Exception {
//
//        psDBBean.setRequestHash(MapUtil.newHashMap());
//        psDBBean.setCustID("01");
//        psDBBean.setCompCode("01");
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_PERM);
//        psDBBean.setLanguage("ja");
//        psDBBean.setUserCode("46402406");
//        psDBBean.getRequestHash().put("txtAction","ACT_DISP_MONTHLY");
//        psDBBean.getRequestHash().put("txtDYYYYMM","2020/03/01");
//        psDBBean.getRequestHash().put("txtSectionId","");
//
//        CalenderVo selectGetCalendarList = permStatListController.selectGetCalendarList(psDBBean);
//
//        System.out.println(selectGetCalendarList);
//
//    }
//
//    @Test
//    public void testOneMonthDetailVo() throws Exception {
//
//        psDBBean.setRequestHash(MapUtil.newHashMap());
//        psDBBean.setCustID("01");
//        psDBBean.setCompCode("01");
//        psDBBean.getRequestHash().put("SiteId", TmgUtil.Cs_SITE_ID_TMG_PERM);
//        psDBBean.setLanguage("ja");
//        psDBBean.setUserCode("46402406");
//        psDBBean.getRequestHash().put("txtAction","ACT_DISP_MONTHLY");
//        psDBBean.getRequestHash().put("txtDYYYYMM","2020/03/01");
//        psDBBean.getRequestHash().put("txtSectionId","");

//        ArrayList<String> oneMonthDetailVo = permStatListController.selectDayCount(psDBBean);

//        System.out.println(oneMonthDetailVo);

//    }
}
