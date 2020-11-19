package jp.smartcompany.controller.tmg_setting;

import jp.smartcompany.job.modules.tmg_setting.genericmanager.service.IGenericManagerService;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.vo.CategoryGenericDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/genericmanager")
@RequiredArgsConstructor
public class GenericManagerController {

  private final IGenericManagerService genericManagerService;

  // http://localhost:6879/sys/genericmanager/cates
  @GetMapping("cates")
  public List<CategoryGenericDetailVO> listCategoryDetailList(@RequestParam(value="categoryId",required = false,defaultValue = "TMG") String categoryId) {
      return genericManagerService.listCategoryGenericDetail(categoryId);
  }

  // http://localhost:6879/sys/genericmanager/detail?groupId=CP_SUSP_CATEGORY&page=1&size=10&historyType=1
  @GetMapping("detail")
  public Map<String,Object> getGenericDetailList(@RequestParam Map<String,Object> conditions) {
      return genericManagerService.getGenericDetailList(conditions);
  }

}
