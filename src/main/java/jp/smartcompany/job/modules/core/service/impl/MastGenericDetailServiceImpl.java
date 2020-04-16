package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.mapper.MastGenericDetailMapper;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 名称マスタ明細データ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastGenericDetailServiceImpl extends ServiceImpl<MastGenericDetailMapper, MastGenericDetailDO> implements IMastGenericDetailService {

    /**
     * 常勤年休ルールを取得
     *
     * @param customerId    顧客コード
     * @param companyId     法人コード
     * @param employeeId    社員番号
     * @param yyyymmdd      基準日
     * @param beginDateWork 　開始日
     * @return int 年休ルール
     */
    @Override
    public int selectNenkyuRuleH(String customerId, String companyId, String employeeId
            , Date yyyymmdd, Date beginDateWork) {

        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("companyId", companyId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("employeeId", employeeId);

        // 年休ルールを取得
        Integer nenkyu = baseMapper.selectNenkyuRuleH(map);

        // データが無い場合、名称マスタを参照する様に設定
        if (nenkyu == null) {
            nenkyu = baseMapper.selectNenkyuRuleH2(map);
        }
        return nenkyu;
    }

    /**
     * 非常勤年休ルールを取得
     *
     * @param customerId    顧客コード
     * @param companyId     法人コード
     * @param employeeId    社員番号
     * @param yyyymmdd      基準日
     * @param beginDateWork 　開始日
     * @return int 年休ルール
     */
    @Override
    public int selectNenkyuRuleT(String customerId, String companyId, String employeeId
            , Date yyyymmdd, Date beginDateWork) {

        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("companyId", companyId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("employeeId", employeeId);

        // 年休ルールを取得
        Integer nenkyu = baseMapper.selectNenkyuRuleT(map);

        // データが無い場合、名称マスタを参照する様に設定
        if (nenkyu == null) {
            nenkyu = baseMapper.selectNenkyuRuleT2(map);
        }
        return nenkyu;
    }

    /**
     * 汎用マスタから予備日付を取得
     *
     * @param customerId 　顧客コード
     * @param companyId  　法人コード
     * @param wsGroupId  　グループコード
     * @param wsDetailId 　名称コード
     * @param language   　言語
     * @param wdKijun    　基準日
     * @return MastGenericDetailDO 名称マスタD0
     */
    @Override
    public MastGenericDetailDO selectMastGenericDetailDO(String customerId, String companyId, String wsGroupId, String wsDetailId, String language, Date wdKijun) {

        Map<String, Object> map = MapUtil.newHashMap(6);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("wsGroupId", wsGroupId);
        map.put("wsDetailId", wsDetailId);
        map.put("language", language);
        map.put("wdKijun", wdKijun);

        return baseMapper.selectMastGenericDetailDO(map);
    }
}
