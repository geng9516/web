package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgEmployeesDO;
import jp.smartcompany.job.modules.core.mapper.TmgEmployeesMapper;
import jp.smartcompany.job.modules.core.service.ITmgEmployeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.IsWorkHealthChkVO;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
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


    /**
     * 　勤怠種別の取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd   基準日
     * @return String 勤怠種別
     */
    @Override
    public String selectWorkerType(String customerId, String companyId, String employeeId, Date yyyymmdd){
        QueryWrapper<TmgEmployeesDO> qw = SysUtil.query();
        qw.eq("tem_ccustomerid", customerId)
                .eq("tem_ccompanyid", companyId)
                .eq("tem_cemployeeid", employeeId)
                ///基準日を'DD'で取得
                .le("tem_dstartdate", DateUtil.formatDate(yyyymmdd))
                .ge("tem_denddate",DateUtil.formatDate(yyyymmdd))
                .select("tem_cworktypeid");
        TmgEmployeesDO teDo= getOne(qw);
        if (teDo!=null){
            return teDo.getTemCworktypeid();
        }
        return null;
    }

    /**
     * 勤務状況確認欄、健康状態確認欄の使用可否設定の取得
     *
     * @param custId 顧客コード
     * @param compId 法人コード
     * @param empId 社員番号
     * @param lang 言語
     * @param month　対象月
     * @return IsWorkHealthChkVO
     */
    @Override
    public IsWorkHealthChkVO buildIsWorkHealthChk(String custId, String compId, String empId, String lang, String month){

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("empId", empId);
        map.put("lang", lang);
        map.put("month", month);

        return baseMapper.buildIsWorkHealthChk(map);
    }
}
