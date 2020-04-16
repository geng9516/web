package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgEmployeesDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * [勤怠]基本情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgEmployeesService extends IService<TmgEmployeesDO> {
    /**
     * 就業管理対象外の最終日を求める
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd   基準日
     * @return Date 就業管理対象外の最終日
     */
    Date selectEndDate(String customerId, String companyId, String employeeId, Date yyyymmdd);

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
    Date selectStartDate(String customerId, String companyId, String employeeId, Date yyyymmdd, Date endDate);
}
