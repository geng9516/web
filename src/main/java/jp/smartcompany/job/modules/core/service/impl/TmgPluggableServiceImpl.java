package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPluggableDO;
import jp.smartcompany.job.modules.core.mapper.TmgPluggable.TmgPluggableMapper;
import jp.smartcompany.job.modules.core.service.ITmgPluggableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * プラガブル情報マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgPluggableServiceImpl extends ServiceImpl<TmgPluggableMapper, TmgPluggableDO> implements ITmgPluggableService {

    /**
     * 勤怠種別実行処理取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param yyyymmdd   基準日
     * @param workType   勤務種別
     * @param cphase     勤務種別  CPHASE
     * @return List<TmgPluggableDO>
     */
    @Override
    public List<TmgPluggableDO> listTmgPluggableDO(String customerId, String companyId, Date yyyymmdd, String workType, String cphase) {

        Map<String, Object> map = MapUtil.newHashMap(5);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("workType", workType);
        map.put("cphase", cphase);

        return this.baseMapper.listTmgPluggableDO(map);
    }
}
