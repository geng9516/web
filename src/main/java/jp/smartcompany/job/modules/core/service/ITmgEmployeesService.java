package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgEmployeesDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.IsWorkHealthChkVO;

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
     * @param employeeId 職員番号
     * @param yyyymmdd   基準日
     * @return Date 就業管理対象外の最終日
     */
    Date selectEndDate(String customerId, String companyId, String employeeId, Date yyyymmdd);

    /**
     * 　就業管理対象の開始日を求める
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 職員番号
     * @param yyyymmdd   基準日
     * @param endDate    　最終日
     * @return Date 就業管理対象の開始日
     */
    Date selectStartDate(String customerId, String companyId, String employeeId, Date yyyymmdd, Date endDate);

    /**
     * 　勤怠種別の取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 職員番号
     * @param yyyymmdd   基準日
     * @return String 勤怠種別
     */
    String selectWorkerType(String customerId, String companyId, String employeeId, Date yyyymmdd);


    /**
     * 勤務状況確認欄、健康状態確認欄の使用可否設定の取得
     *
     * @param custId 顧客コード
     * @param compId 法人コード
     * @param empId 職員番号
     * @param lang 言語
     * @param month　対象月
     * @return IsWorkHealthChkVO
     */
    IsWorkHealthChkVO buildIsWorkHealthChk(String custId, String compId, String empId, String lang, String month);
}
