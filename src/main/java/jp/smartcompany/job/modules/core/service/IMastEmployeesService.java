package jp.smartcompany.job.modules.core.service;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.framework.auth.entity.LoginControlEntity;
import jp.smartcompany.framework.compatible.entity.V3CompatiblePostEntity;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import com.baomidou.mybatisplus.extension.service.IService;
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
                                  String psDate);

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

    int selectRelation(String sCust,String sLoginUser,String sTargetUser,String sSystem,String sDesig,
                       String sDate);

    int selectRelationEx(String sCust,String sLoginUser,String sTargetUser,String sSystem,String sDesig,
                                String sDate, String psBase1, String psBase2, String psBase3,
                                String psBase4, String psBase5, String psBase6,
                                String psBase7, String psBase8);

    List<LoginControlEntity> selectUserInfoByDate(String loginUser,String language,String psDate);

    int selectOrgRelation(String sCust, String sLoginUser,String sTargetComp,
                          String sTargetSec,String sSystem,String non,String sDate);

    List<V3CompatiblePostEntity> getVersion3SectionChief(
            String sCustid,
            String sCompid,
            String sDeptid,
            String sDate,
            String sPostid,
            boolean bIncludeactual
    );

}
