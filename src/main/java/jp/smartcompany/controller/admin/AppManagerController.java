package jp.smartcompany.controller.admin;

import jp.smartcompany.admin.appmanager.logic.AppManagerMainLogic;
import jp.smartcompany.admin.appmanager.dto.MastAppTreeDTO;
import jp.smartcompany.admin.appmanager.dto.MastTemplateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sys/appmanager")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppManagerController {

  private final AppManagerMainLogic appManagerMainLogic;

  // http://localhost:6879/sys/appmanager/tree
  @GetMapping("tree")
  public List<MastAppTreeDTO> getAppTree() {
      return appManagerMainLogic.getAppTree();
  }

  // http://localhost:6879/sys/appmanager/templates
  @GetMapping("templates")
  public List<MastTemplateDTO> getTemplateList() {
     return appManagerMainLogic.getTemplateList();
  }

}
