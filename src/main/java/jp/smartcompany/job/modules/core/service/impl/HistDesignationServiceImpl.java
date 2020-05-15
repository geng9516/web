package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.mapper.HistDesignationMapper;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.SectionGroupId;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 異動歴 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class HistDesignationServiceImpl extends ServiceImpl<HistDesignationMapper, HistDesignationDO> implements IHistDesignationService {



        /**
         * 在籍部署・グループを検索
         *
         * @param customerId 　顧客コード
         * @param companyId  　法人コード
         * @param employeeId  　社員番号
         * @param yyyymmdd 　勤務日
         * @return
         */
        @Override
        public SectionGroupId selectSecGroupId(String customerId, String companyId, String employeeId ,Date yyyymmdd){
                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("employeeId", employeeId);
                map.put("yyyymmdd", yyyymmdd);
                // パターン
                SectionGroupId sgId = baseMapper.selectSecGroupId(map);


                return sgId;
        }

        @Override
        public List<HistDesignationDO> selectCompanyId(String userId, Date date) {
                QueryWrapper<HistDesignationDO> qw = SysUtil.query();
                qw.eq("HD_CUSERID",userId)
                  .lt("HD_DSTARTDATE_CK",date)
                   .gt("HD_DENDDATE",date);
                return list(qw);
        }

}
