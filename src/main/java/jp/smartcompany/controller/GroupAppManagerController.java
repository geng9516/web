package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.admin.groupappmanager.logic.GroupAppManagerMainLogic;
import jp.smartcompany.admin.groupappmanager.vo.GroupAppManagerTableLayout;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 *  起動権限設定Controller
 *  @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/groupappmanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupAppManagerController {

  private final GroupAppManagerMainLogic groupAppManagerMainLogic;

  @GetMapping
  public GroupAppManagerTableLayout list(@RequestAttribute("BeanName") PsDBBean psDBBean,
//                                                      @RequestParam GroupAppManagerPermissionTableForm form
                                         @RequestParam(value="date",required = false) Date date,
                                         @RequestParam(value="groupId",required = false) String groupId,
                                         @RequestParam(value="isAll",required = false,defaultValue = "false") Boolean isAll
                                                      ) {
    if (date == null){
      date = DateUtil.date();
    }
    return groupAppManagerMainLogic.listPermsTable(
            psDBBean.getSystemCode(),
            date,
            groupId,
            psDBBean.getSiteId(),
            psDBBean.getAppId(),
            psDBBean.getLanguage(),
            psDBBean.getCustID(),
            psDBBean.getCompCode(),
            isAll
    );
  }

}
