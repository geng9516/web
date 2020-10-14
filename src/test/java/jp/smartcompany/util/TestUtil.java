package jp.smartcompany.util;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.boot.util.SysDateUtil;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsSession;
import org.springframework.mock.web.MockHttpSession;

import java.util.List;

public class TestUtil {

    /**
     * 模拟登录后返回的用户信息session
     */
    public static MockHttpSession mockLoginInfoSession() {
        MockHttpSession session = new MockHttpSession();
        PsSession psSession = new PsSession();

        List<Designation> designations = CollUtil.newArrayList();
        Designation designation = new Designation();
        designation.setAttachRole("0")
                .setBossOrNot("0")
                .setCompanyCode("01")
                .setCompanyHierarchy(",01,")
                .setCompanyName("国立研究開発法人　医薬基盤・健康・栄養研究所")
                .setCompanyOrder("1")
                .setCustomerCode("01")
                .setEmployee("A0000001")
                .setName("A0000001 氏名")
                .setName("A0000001 ｶﾅｼﾒｲ")
                .setPersonnelChangesBigin(SysDateUtil.of(2016,4,1))
                .setPostCode("405")
                .setPostName("係長")
                .setPostRank(22)
                .setSection("201000201010")
                .setSectionHierarchy(",01,,|,000000,200000000000,201000000000,201000201010,")
                .setSectionName("総務課")
                .setSectionOrder("3")
                .setUserid("A0000001");
        designations.add(designation);
        psSession.setLoginDesignation(designations);

        psSession.setLoginCustomer("01");

        psSession.setLanguage("ja");

        session.setAttribute("psSession",psSession);
        return session;
    }

}
