package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.mapper.MastEmployeesMapper;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public  List<String> selectUserIdList(String psCustid,
                                          String psCompid,
                                          String psLoginUserId,
                                          Date psDate) {
        QueryWrapper<MastEmployeesDO> qw = SysUtil.query();
        qw.eq("ME_CCUSTOMERID_CK",psCustid)
          .eq("ME_CCOMPANYID",psCompid)
          .eq("ME_CEMPLOYEEID_CK",psLoginUserId)
                .lt("ME_DSTARTDATE",psDate)
                .gt("ME_DENDDATE",psDate);
        return list(qw).stream().map(MastEmployeesDO::getMeCuserid).collect(Collectors.toList());
    }

    @Override
    public List<MastEmployeesDO> selectEmployByLoginUserId(String psCustid,
                                                           String psCompid,
                                                           String psLoginUserId,
                                                           Date psDate) {
        QueryWrapper<MastEmployeesDO> qw = SysUtil.query();
        qw.eq("ME_CCUSTOMERID_CK",psCustid)
                .eq("ME_CCOMPANYID",psCompid)
                .eq("ME_CEMPLOYEEID_CK",psLoginUserId)
                .lt("ME_DSTARTDATE",psDate)
                .gt("ME_DENDDATE",psDate);
        return list(qw);
    }
}
