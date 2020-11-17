package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternRestDO;
import jp.smartcompany.job.modules.core.mapper.TmgPatternRest.TmgPatternRestMapper;
import jp.smartcompany.job.modules.core.service.ITmgPatternRestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 勤務パターン休憩情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgPatternRestServiceImpl extends ServiceImpl<TmgPatternRestMapper, TmgPatternRestDO> implements ITmgPatternRestService {


        /**
         * パターン休憩情報
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param sectionId    部局コード
         * @param groupId      グループコード
         * @param patternId    勤務パターンID
         * @param statDate      基準日
         * @param upperSectionId    上位組織
         * @return List<TmgPatternRestDO> パターン休憩情報
         */
        @Override
        public List<TmgPatternRestDO> selectPatternRestTime(String customerId, String companyId, String sectionId
                , String groupId, String patternId, Date statDate, String upperSectionId){

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("sectionId", sectionId);
                map.put("groupId", groupId);
                map.put("patternId", patternId);
                map.put("statDate", statDate);
                map.put("upperSectionId", upperSectionId);

                // パターン情報を取得
                List<TmgPatternRestDO> tprList= baseMapper.selectPatternRestTime(map);

                return tprList;
        }
        }
