package jp.smartcompany.controller.admin;

import jp.smartcompany.admin.appmanager.dto.AppSortDTO;
import jp.smartcompany.admin.appmanager.dto.MastAppTreeDTO;
import jp.smartcompany.admin.appmanager.logic.AppManagerMainLogic;
import jp.smartcompany.admin.appmanager.dto.MastAppDTO;
import jp.smartcompany.admin.appmanager.dto.MastTemplateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/appmanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppManagerController {

  private final AppManagerMainLogic appManagerMainLogic;

  // http://localhost:6879/sys/appmanager/applist
  @GetMapping("applist")
  public List<MastAppTreeDTO> getAppList() {
      return appManagerMainLogic.getAppList();
  }

  // http://localhost:6879/sys/appmanager/templates
  @GetMapping("templates")
  public List<MastTemplateDTO> getTemplateList() {
     return appManagerMainLogic.getTemplateList();
  }

  @PostMapping("update")
  public String updateMenuList(@RequestBody MastAppDTO dto) {
    return appManagerMainLogic.updateMenuList(dto);
  }

  @PostMapping("sort")
  public String sort(@RequestBody List<AppSortDTO> list) {
    appManagerMainLogic.updateMenuSort(list);
    return "変更成功";
  }

}
