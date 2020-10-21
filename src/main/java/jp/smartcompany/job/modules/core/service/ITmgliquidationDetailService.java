package jp.smartcompany.job.modules.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDetailDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.EditDispVo;

import java.util.List;

public interface ITmgliquidationDetailService extends IService<TmgLiquidationDetailDO> {
    List<EditDispVo> getLiquidationDetail(String custID, String compCode, String tlpId, String startDate, String endDate);

    List<EditDispVo> getLiquidationDetailNew(String startDate, String months);
}
