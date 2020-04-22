package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.bo.DBMastGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestGroupBusiness {

   private GroupBusiness groupBusiness;

   @Before
   public void beforeTest(){
       groupBusiness = SpringUtil.getBean(CoreBean.Business.GROUP);
   }

    /**
     * 测试获取某用户是否拥有用户组
     */
   @Test
   public void  testGetAssembleSql() {
       String[] sqls = {
               "SELECT    DISTINCT ( HD_CEMPLOYEEID_CK )  , HD_CCUSTOMERID_CK , HD_CCOMPANYID_CK , HD_CUSERID FROM HIST_DESIGNATION , MAST_GROUPSECTIONPOSTMAPPING WHERE  (  ( MAG_CCOMPANYID = HD_CCOMPANYID_CK AND MAG_CEMPLOYEEID = HD_CEMPLOYEEID_CK AND MAG_CTYPEID = '07' )  )  AND MAG_CCUSTOMERID_CK_FK  = '01' AND MAG_CSYSTEMID_CK = '01' AND MAG_CGROUPID_FK = '12' AND HD_DSTARTDATE_CK <=  current_date  AND HD_DENDDATE >=  current_date  AND MAG_DSTARTDATE_CK <= current_date  AND MAG_DENDDATE >=  current_date ",
               " SELECT    DISTINCT ( HD_CEMPLOYEEID_CK )  , HD_CCUSTOMERID_CK , HD_CCOMPANYID_CK , HD_CUSERID FROM HIST_DESIGNATION , MAST_EMPLOYEES WHERE HD_CCUSTOMERID_CK = '01' AND HD_CCOMPANYID_CK = '01' AND HD_CCUSTOMERID_CK  = ME_CCUSTOMERID_CK AND HD_CUSERID         = ME_CUSERID AND HD_DSTARTDATE_CK  <= current_date AND HD_DENDDATE       >= current_date AND ME_DSTARTDATE <= current_date AND ME_DENDDATE >= current_date AND  (   ME_CIFSTILLEMPLOYEDID = '0'  ) ",
               " SELECT    DISTINCT ( HD_CEMPLOYEEID_CK )  , HD_CCUSTOMERID_CK , HD_CCOMPANYID_CK , HD_CUSERID FROM HIST_DESIGNATION , MAST_GROUPSECTIONPOSTMAPPING WHERE  (  ( MAG_CCOMPANYID = HD_CCOMPANYID_CK AND MAG_CSECTIONID = HD_CSECTIONID_FK AND MAG_CSECTIONID = '010000000000' AND MAG_CTYPEID = '02' )  OR  ( MAG_CCOMPANYID = HD_CCOMPANYID_CK AND MAG_CPOSTID = HD_CPOSTID_FK AND MAG_CTYPEID = '06' )  )  AND MAG_CCUSTOMERID_CK_FK  = '01' AND MAG_CSYSTEMID_CK = '01' AND MAG_CGROUPID_FK = '010000' AND HD_DSTARTDATE_CK <=  current_date  AND HD_DENDDATE >=  current_date  AND MAG_DSTARTDATE_CK <=  current_date  AND MAG_DENDDATE >=  current_date "
       };
       for (String sql : sqls) {
           DBMastGroupBO groupBO = new DBMastGroupBO();
           groupBO.setPQuery(sql);
           int count = groupBusiness.getAssembleSql(groupBO,"46402406");
           System.out.println("正常输出相应查询记录数:" + count);
       }
    }

    /**
     * 测试获取特定用户组（注意：运行时需要把设计ShiroUtil的地方注释掉换上自己的测试数据）
     */
    @Test
    public void testSetGroupInfo() {
       MastSystemDO systemDO = new MastSystemDO();
       systemDO.setMsCsystemidPk("01");
        List<MastSystemDO> systemList = CollUtil.newArrayList(systemDO);
        groupBusiness.setGroupInfo("multiple","ja",systemList);
    }

}
