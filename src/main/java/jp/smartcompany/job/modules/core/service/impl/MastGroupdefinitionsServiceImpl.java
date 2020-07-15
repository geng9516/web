package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupdefinitionsDO;
import jp.smartcompany.job.modules.core.mapper.MastGroupdefinitionsMapper;
import jp.smartcompany.job.modules.core.service.IMastGroupdefinitionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ定義条件マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastGroupdefinitionsServiceImpl extends ServiceImpl<MastGroupdefinitionsMapper, MastGroupdefinitionsDO> implements IMastGroupdefinitionsService {

        @Override
        public List<MastGroupdefinitionsDO> selectGroupDefinitions(
                Date startDate,
                String customerId,
                String systemId,
                String groupId) {
                if (StrUtil.isBlank(customerId)) {
                        customerId = "01";
                }
                if (StrUtil.isBlank(systemId)) {
                        systemId = "01";
                }
                if (StrUtil.isBlank(groupId)) {
                        groupId = "1004";
                }
                String strStartDate;
                if (startDate==null) {
                        strStartDate="2007/07/07";
                }else {
                        strStartDate = SysUtil.transDateToString(startDate);
                }
                return baseMapper.selectGroupDefinitions(strStartDate,customerId,systemId,groupId);
        }
}
