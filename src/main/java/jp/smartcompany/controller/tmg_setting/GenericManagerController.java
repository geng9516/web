package jp.smartcompany.controller.tmg_setting;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.service.IGenericManagerService;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.vo.CategoryGenericDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("sys/genericmanager")
@RequiredArgsConstructor
public class GenericManagerController {

  private final IGenericManagerService genericManagerService;

  @GetMapping("cates")
  public List<CategoryGenericDetailVO> listCategoryDetailList(@RequestParam(value="searchDate",required = false) Date searchDate,@RequestParam(value="categoryId",required = false,defaultValue = "TMG") String categoryId) {
      if (searchDate == null) {
          searchDate = DateUtil.date();
      }
      return genericManagerService.listCategoryGenericDetail(searchDate,categoryId);
  }

}
