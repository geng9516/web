package jp.smartcompany.framework;

import jp.smartcompany.NextJobApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


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
