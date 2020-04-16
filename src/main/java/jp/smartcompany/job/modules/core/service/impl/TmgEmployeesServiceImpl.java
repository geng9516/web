package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgEmployeesDO;
import jp.smartcompany.job.modules.core.mapper.TmgEmployeesMapper;
import jp.smartcompany.job.modules.core.service.ITmgEmployeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * [勤怠]基本情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgEmployeesServiceImpl extends ServiceImpl<TmgEmployeesMapper, TmgEmployeesDO> implements ITmgEmployeesService {

    /**
     * 就業管理対象外の最終日を求める
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd   基準日
     * @return Date 就業管理対象外の最終日
     */
    @Override
    public Date selectEndDate(String customerId, String companyId, String employeeId, Date yyyymmdd) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("employeeId", employeeId);
        map.put("yyyymmdd", yyyymmdd);

        return baseMapper.selectEndDate(map);
    }

    /**
     * 　就業管理対象の開始日を求める
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd   基準日
     * @param endDate    　最終日
     * @return Date 就業管理対象の開始日
     */
    @Override
    public Date selectStartDate(String customerId, String companyId, String employeeId, Date yyyymmdd, Date endDate) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("employeeId", employeeId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("endDate", endDate);

        return baseMapper.selectStartDate(map);
    }
}
