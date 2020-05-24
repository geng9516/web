package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgCompanyDO;
import jp.smartcompany.job.modules.core.mapper.TmgCompanyMapper;
import jp.smartcompany.job.modules.core.service.ITmgCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.CompanyVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * [勤怠]法人情報                      2007/01/31項目追加｢年度の開始月」 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgCompanyServiceImpl extends ServiceImpl<TmgCompanyMapper, TmgCompanyDO> implements ITmgCompanyService {

    /**
     * [勤怠]TMG_COMPANYから予定出社・退社時間の基準値を取得。
     *
     * @param custCode   顧客コード
     * @param compCode   法人コード
     * @param targetDate 基準日
     * @return CompanyVO
     */
    @Override
    public CompanyVO buildSQLSelectCompany(String custCode, String compCode, String targetDate) {

        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("custCode", custCode);
        map.put("compCode", compCode);
        map.put("targetDate", targetDate);

        return baseMapper.buildSQLSelectCompany(map);
    }

}
