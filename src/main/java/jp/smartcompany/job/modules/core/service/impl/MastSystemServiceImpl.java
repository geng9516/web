package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.framework.sysboot.dto.AppSearchRangeInfoDTO;
import jp.smartcompany.framework.sysboot.dto.TableCombinationTypeDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.mapper.MastSystemMapper;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * システムマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastSystemServiceImpl extends ServiceImpl<MastSystemMapper, MastSystemDO> implements IMastSystemService {

        @Override
        public List<MastSystemDO> getByLang(String language) {
           QueryWrapper<MastSystemDO> qw = SysUtil.query();
           qw.eq("ms_clanguage",language);
           return list(qw);
        }

        @Override
        public List<TableCombinationTypeDTO> getTableInfo() {
            return baseMapper.selectTableInfo();
        }

        @Override
        public List<AppSearchRangeInfoDTO> selectSearchRangeInfo() {
            return baseMapper.selectSearchRangeInfo();
        }

        @Override
        public List<MastSystemDO> selectSystemList(String language) {
            if (StrUtil.isBlank(language)){
                language = "ja";
            }
            return baseMapper.selectSystemList(language);
        }

}
