package jp.smartcompany.job.modules.core.service;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmployMentWithMEVo;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基本情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastEmployeesService extends IService<MastEmployeesDO> {

    /**
     * MAST_EMPLOYEESの採用日を取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd   基準日
     * @return Date 採用日
     */
    Date selectBegindateWork(String customerId, String companyId, String employeeId, Date yyyymmdd);

    List<PaidHolidayInitVO> listPaidHolidayInit(String empSql);

    List<String> selectUserIdList(String psCustid,
                                  String psCompid,
                                  String psLoginUserId,
                                  Date psDate);

    List<MastEmployeesDO> selectEmployByLoginUserId(String psCustid,
                                  String psCompid,
                                  String psLoginUserId,
                                  Date psDate);

    /**
     * サイトIDを判定し更新対象の職員番号
     *
     * @param siteId   サイトID
     * @param yyyymmdd 　対象日
     * @param empsql   　対象職員取得SQL
     * @param empIds   　画面チェックした職員番号リスト
     * @return
     */
    List<String> selectEmpIdListForTmgDaily(String siteId, String yyyymmdd, String empsql, String[] empIds);


    /**
    * 発令上の勤務開始日取得用SQL取得メソッド
    */
    EmployMentWithMEVo selectDateofemploymentWithME(String custId,String compId,String empId);
}
