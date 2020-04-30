package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.mapper.MastEmployeesMapper;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基本情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastEmployeesServiceImpl extends ServiceImpl<MastEmployeesMapper, MastEmployeesDO> implements IMastEmployeesService {

    /**
     * MAST_EMPLOYEESの採用日を取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd   基準日
     * @return Date 採用日
     */
    @Override
    public Date selectBegindateWork(String customerId, String companyId, String employeeId, Date yyyymmdd) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("employeeId", employeeId);
        map.put("yyyymmdd", yyyymmdd);

        return baseMapper.selectBegindateWork(map);
    }

    @Override
    public List<PaidHolidayInitVO> listPaidHolidayInit() {
        return baseMapper.selectPaidHolidayInit();
    }
}
