package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternAppliesDO;
import jp.smartcompany.job.modules.core.mapper.TmgPatternAppliesMapper;
import jp.smartcompany.job.modules.core.service.ITmgPatternAppliesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * [就業]勤務パターン適用情報(部署) 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgPatternAppliesServiceImpl extends ServiceImpl<TmgPatternAppliesMapper, TmgPatternAppliesDO> implements ITmgPatternAppliesService {


        /**
         * パターン割付情報を検索
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    社員番号
         * @param yyyymmdd      基準日
         * @return int 週平均勤務時間
         */
        @Override
        public String selectPatternId(String customerId, String companyId, String employeeId
                , Date yyyymmdd) {

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("yyyymmdd", yyyymmdd);
                map.put("employeeId", employeeId);

                // 週平均勤務時間を取得
                String patternId = baseMapper.selectPatternId(map);

                return patternId;
        }
        }
