package jp.smartcompany.framework;

import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.framework.compatible.business.Version3CompatibleLogic;
import jp.smartcompany.job.NextJobApplication;
import jp.smartcompany.job.modules.core.util.PsResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = NextJobApplication.class)
@RunWith(SpringRunner.class)
public class TestVersion3CompatibleLogic {

    private Version3CompatibleLogic version3CompatibleLogic;

    @Before
    public void before() {
        version3CompatibleLogic = SpringUtil.getBean(Version3CompatibleLogic.class);
    }

    @Test
    public void testGetLookAndFeelSettings() {
        System.out.println(version3CompatibleLogic.getLookAndFeelSettings());
    }

    @Test
    public void testGetChiefPostOfDesignation() {
        PsResult result =version3CompatibleLogic.getChiefPostOfDesignation("01", "01", "222", "2020/06/10");
        System.out.println(result);
    }

    @Test
    public void testGetUpperSectionListForSQL() {
        String result = version3CompatibleLogic.getUpperSectionListForSQL("01","01", "201000000000", "2020/06/10");
        System.out.println(result);
    }

    @Test
    public void testGetLowerSectionListUserForSQL() {
        String result = version3CompatibleLogic.getLowerSectionListForSQL("01","01", "201000000000", "2020/06/10");
        System.out.println(result);
    }

    @Test
    public void testGetUseridForV4() {
        String result = version3CompatibleLogic.getUseridForV4("01","01","46402406","2020/06/11");
        System.out.println(result);
    }

}
