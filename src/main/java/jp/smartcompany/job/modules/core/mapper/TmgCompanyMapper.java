package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCompanyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.CompanyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * [勤怠]法人情報                      2007/01/31項目追加｢年度の開始月」 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgCompanyMapper extends BaseMapper<TmgCompanyDO> {

    /**
     * [勤怠]TMG_COMPANYから予定出社・退社時間の基準値を取得。
     *
     * @param map
     * @return 基準値
     */
    CompanyVO buildSQLSelectCompany(Map<String, Object> map);
}
