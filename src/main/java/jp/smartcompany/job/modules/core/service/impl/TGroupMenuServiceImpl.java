package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TGroupMenuDO;
import jp.smartcompany.job.modules.core.mapper.TGroupMenuMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TMenuDO;
import jp.smartcompany.job.modules.core.service.ITGroupMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-24
 */
@Repository
public class TGroupMenuServiceImpl extends ServiceImpl<TGroupMenuMapper, TGroupMenuDO> implements ITGroupMenuService {

  @Override
  public List<TMenuDO> listTopMenuByGroupCode(List<String> groupCodes, String systemCode, String customerId) {
     if (CollUtil.isEmpty(groupCodes)) {
         return CollUtil.newArrayList();
     }
     return baseMapper.listTopMenuByGroupCode(groupCodes,systemCode,customerId);
  }

}
