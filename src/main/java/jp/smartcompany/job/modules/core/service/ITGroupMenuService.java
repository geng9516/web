package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TGroupMenuDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TMenuDO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-24
 */
public interface ITGroupMenuService extends IService<TGroupMenuDO> {

/**
 * 根据用户组获取该用户可以访问的系统顶级菜单
 * @param groupCodes
 * @param systemCode
 * @param customerId
 * @return
 */
  List<TMenuDO> listTopMenuByGroupCode(List<String> groupCodes,String systemCode,String customerId);

  /**
   * 根据用户组获取该用户可以访问的系统二级菜单
   * @param groupCodes
   * @param systemCode
   * @param customerId
   * @return
   */
  List<TMenuDO> listSecondMenuByGroupCode(List<String> groupCodes,String systemCode,String customerId);

  /**
   * 根据用户组和父级菜单id获取该用户可以访问的系统菜单
   * @param menuId
   * @param systemCode
   * @param customerId
   * @return
   */
  List<TMenuDO> listMenuByGroupCodeAndParentId(Long menuId,String systemCode,String customerId);

}
