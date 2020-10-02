package jp.smartcompany.controller.admin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupappmanager.form.GroupAppManagerUpdatePermsForm;
import jp.smartcompany.admin.groupappmanager.logic.GroupAppManagerMainLogic;
import jp.smartcompany.admin.groupappmanager.vo.GroupAppManagerTableLayout;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  システム管理menu-起動権限設定Controller
 *  @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/groupappmanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupAppManagerController {

  private final GroupAppManagerMainLogic groupAppManagerMainLogic;

  // http://localhost:6879/sys/groupappmanager?isAll=true&psSite=Admin&psSecurityDate=2020/06/29&date=2020/06/29&siteId=TMG_PERM
  @PostMapping
  public GroupAppManagerTableLayout list(
                                         @RequestAttribute("BeanName") PsDBBean psDBBean,
                                         @RequestParam Map<String,Object> params
                                     ) {
    String dateStr = (String)params.get("date");
    Date date;
    if (StrUtil.isBlank(dateStr)){
      date = DateUtil.date();
    }else {
        date = SysUtil.transStringToDate(dateStr);
    }
    String groupId = (String)params.get("groupId");
    String isAllStr = (String)params.get("isALl");
    if (StrUtil.isBlank(isAllStr)) {
        isAllStr = "false";
    }
    boolean isAll = Boolean.parseBoolean(isAllStr);
    String psSiteId = (String)params.get("siteId");
    String psAppId = (String)params.get("appId");
      return groupAppManagerMainLogic.listPermsTable(
            psDBBean.getSystemCode(),
            date,
            groupId,
            psSiteId,
            psAppId,
            psDBBean.getLanguage(),
            psDBBean.getCustID(),
            psDBBean.getCompCode(),
            isAll
    );
  }

  @PostMapping("groups")
  public List<GroupAppManagerGroupDTO> getGroupList(
          @RequestAttribute("BeanName") PsDBBean psDBBean,
          @RequestParam Map<String,Object> params
  ) {
      String isAllStr = (String)params.get("isAll");
      if (StrUtil.isBlank(isAllStr)){
          isAllStr = "false";
      }
      String systemId = (String)params.get("systemId");
      String searchDateStr = (String)params.get("searchDate");
      Date date;
      if (StrUtil.isBlank(searchDateStr)){
          date = DateUtil.date();
      }else {
          date = SysUtil.transStringToDate(searchDateStr);
      }
      String companyId = (String)params.get("companyId");
      boolean isAll =Boolean.parseBoolean(isAllStr);
      if (StrUtil.isBlank(systemId)){
            systemId=psDBBean.getSystemCode();
          }
          if (StrUtil.isBlank(companyId)){
            companyId = psDBBean.getCompCode();
          }
          return groupAppManagerMainLogic.getGroupList(psDBBean.getCustID(),
            systemId,
            psDBBean.getLanguage(),
                  date, companyId, isAll);
  }

  @GetMapping("sites")
  public List<MastApptreeDO> getSiteList(
          @RequestAttribute("BeanName") PsDBBean psDBBean,
          @RequestParam(value="systemId",required = false) String psSystemId) {
    if (StrUtil.isBlank(psSystemId)){
      psSystemId = psDBBean.getSystemCode();
    }
    return groupAppManagerMainLogic.getSiteList(psSystemId,psDBBean.getLanguage());
  }

  @GetMapping("apps")
  public List<MastApptreeDO> getAppList(
          @RequestAttribute("BeanName") PsDBBean psDBBean,
          @RequestParam(value="systemId",required = false) String psSystemId,
          @RequestParam(value="siteId",required = false) String psSiteId) {
      if (StrUtil.isBlank(psSystemId)){
        psSystemId = psDBBean.getSystemCode();
      }
      return groupAppManagerMainLogic.getAppList(psSystemId, psDBBean.getLanguage(), psSiteId);
  }

  @GetMapping("systems")
  public List<MastSystemDO> getSystemList(@RequestAttribute("BeanName") PsDBBean psDBBean) {
     return groupAppManagerMainLogic.getSystemList(psDBBean.getLanguage());
  }

  @GetMapping("companies")
  public List<MastCompanyDO> getCompanyList(
          @RequestAttribute("BeanName") PsDBBean psDBBean,
          @RequestParam(value="searchDate",required = false) Date searchDate){
     return groupAppManagerMainLogic.getCompanyList(psDBBean.getCustID(),searchDate);
  }

  @PostMapping("update")
  public String executeUpdate(@RequestBody GroupAppManagerUpdatePermsForm updatePermForm) throws ParseException, SQLException {
      groupAppManagerMainLogic.executeUpdate(updatePermForm);
      return "変更成功";
  }

}
