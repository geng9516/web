package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchOrder.HistSearchOrderMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchOrderDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchOrderService;
import org.springframework.stereotype.Service;

@Service
public class HistSearchOrderServiceImpl extends ServiceImpl<HistSearchOrderMapper, HistSearchOrderDO> implements IHistSearchOrderService {


}
