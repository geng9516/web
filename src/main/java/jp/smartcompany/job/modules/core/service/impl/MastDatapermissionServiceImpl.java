package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.MastDatapermissionDO;
import jp.smartcompany.job.modules.core.mapper.MastDatapermission.MastDatapermissionMapper;
import jp.smartcompany.job.modules.core.service.IMastDatapermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.framework.sysboot.dto.SearchRangeInfoDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 検索対象範囲条件定義マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastDatapermissionServiceImpl extends ServiceImpl<MastDatapermissionMapper, MastDatapermissionDO> implements IMastDatapermissionService {

        @Override
        public List<SearchRangeInfoDTO> selectDataPermissionDefs() {
                return baseMapper.selectDataPermissionDefs();
        }

        @Override
        public List<SearchRangeInfoDTO> selectDataSectionPost() {
                return baseMapper.selectDataSectionPost();
        }

}
