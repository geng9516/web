package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdefinitionsDO;
import jp.smartcompany.job.modules.core.mapper.HistGroupdefinitionsMapper;
import jp.smartcompany.job.modules.core.service.IHistGroupdefinitionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ定義条件式データ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class HistGroupdefinitionsServiceImpl extends ServiceImpl<HistGroupdefinitionsMapper, HistGroupdefinitionsDO> implements IHistGroupdefinitionsService {

        @Override
        public List<QueryConditionRowDTO> selectGroupDefinitions(
                String customerId,
                String companyId,
                String systemId,
                String groupId,
                Date searchDate,
                Long seq) {
                String strSearchDate;
                if (searchDate==null){
                        strSearchDate = "2007/07/07";
                } else {
                        strSearchDate = SysUtil.transDateToString(searchDate);
                }
                if (StrUtil.isBlank(customerId)){
                        customerId = "01";
                }
                if (StrUtil.isBlank(companyId)){
                        companyId = "01";
                }
                if (StrUtil.isBlank(systemId)){
                        systemId = "01";
                }
//                if (seq==null){
//                        seq = 1L;
//                }
                return baseMapper.selectGroupDefinitions(customerId,companyId,systemId,groupId,strSearchDate,seq);
        }

        @Override
        public List<HistGroupdefinitionsDO> selectHistGroupDefinitions(Date searchDate, String custId, String systemId, String groupId) {
                String startDate = SysUtil.transDateToString(searchDate);
                return baseMapper.selectHistGroupDefinitions(startDate,custId,systemId,groupId);
        }
}
