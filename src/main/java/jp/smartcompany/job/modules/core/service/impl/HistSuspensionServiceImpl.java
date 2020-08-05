package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.HistSuspensionDO;
import jp.smartcompany.job.modules.core.mapper.HistSuspensionMapper;
import jp.smartcompany.job.modules.core.service.IHistSuspensionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.HatuReiDto;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 休職歴 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class HistSuspensionServiceImpl extends ServiceImpl<HistSuspensionMapper, HistSuspensionDO> implements IHistSuspensionService {


        @Override
        public HatuReiDto getHatuRei(String custID, String compCode, String targetUser, String baseDate){
                return getBaseMapper().getHatuRei( custID,  compCode,  targetUser,  baseDate);
        }
        }
