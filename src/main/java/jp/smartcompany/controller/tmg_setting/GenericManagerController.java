package jp.smartcompany.controller.tmg_setting;

import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.dto.UpdateDetailDTO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.service.IGenericManagerService;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.vo.CategoryGenericDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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

  // http://localhost:6879/sys/genericmanager/detail?groupId=CP_SUSP_CATEGORY&page=1&limit=10&historyType=1
  @GetMapping("detail")
  public Map<String,Object> getGenericDetailList(@RequestParam Map<String,Object> conditions) {
      return genericManagerService.getGenericDetailList(conditions);
  }

  @PostMapping("delete")
  public String removeSelectedDetails(@RequestBody List<Long> ids) {
      return genericManagerService.deleteSelectedDetails(ids);
  }

  // http://localhost:6879/sys/genericmanager/detail/item?groupId=HS_SUSP&detailId=800
  @GetMapping("detail/item")
  public Map<String,Object> getGenericDetail(@RequestParam(defaultValue = "TMG") String categoryId, String groupId, Date searchDate, String detailId) {
      return genericManagerService.getGenericDetail(categoryId,groupId,searchDate,detailId);
  }

    /**
     * {
     *    endDate: '2020/11/25',
     *    mgdId: 1, 传入表示修改，不传表示新增，
     *    newHistory: true,
     *    info: {
     *            通过上述detail/item接口获得的detail对象
     *    }
     * }
     *
     */
  @PostMapping("detail")
  public String execute(@RequestBody @Valid UpdateDetailDTO info) {
      return "変更成功しました";
  }

}
