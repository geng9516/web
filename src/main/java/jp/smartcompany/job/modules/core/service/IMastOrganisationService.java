package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionOrganisationBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.LimitOfBasedateVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 組織ツリーマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastOrganisationService extends IService<MastOrganisationDO> {


    /**
     * 指定組織の検索
     *
     * @param customerId
     * @param companyId
     * @param sectionId
     * @param yyyymmdd
     * @return
     */
    MastOrganisationDO selectOrganisation(String customerId, String companyId, String sectionId, Date yyyymmdd);


    /**
     * 指定組織の検索（パタンが存在する）
     *
     * @param customerId
     * @param companyId
     * @param yyyymmdd
     * @return
     */
    List<MastOrganisationDO> selectPatternOrganisation(String customerId, String companyId, Date yyyymmdd);

    /**
     * 根据层级选择基点组织下的组织列表
     *
     * @param customerId
     * @param conds
     * @param date
     * @return
     */
    List<BaseSectionOrganisationBO> selectOrganisationByLevel(String customerId, String conds, String date);

    /**
     * 获取父级组织的id
     *
     * @param psCustID
     * @param psCompCode
     * @param psTargetDept
     * @param pdSearchDate
     * @return
     */
    List<String> selectHighSection(String psCustID, String psCompCode, String psTargetDept, Date pdSearchDate);


    /**
     * <p>
     * <b>下位組織</b>情報取得（組織指定）
     * </p>
     * <div>指定した組織の下位組織リストを返却する。 </div>
     *
     * @param psCustID  顧客コード
     * @param psCompID  法人コード
     * @param psSection 組織コード
     * @param date      検索基準日
     * @return 下位組織情報
     * @throws
     * @author t-abe
     */
    List<String> selectLowerSection(String psCustID, String psCompID, String psSection, Date date);


    /**
     * 基準日時点の超勤限度時間取得用
     *
     * @param custID    顧客コード
     * @param compCode  法人コード
     * @param targetSec 部署コード
     * @param day       　基準日
     * @return LimitOfBasedateVO
     */
    LimitOfBasedateVO buildSQLForLimitOfBasedate(String custID, String compCode, String targetSec, String day);

    /**
     * 職員情報を取得する
     *
     * @param sectionId 部署コード
     * @param today     基準日
     * @param custId    顧客コード
     * @param compCode  法人コード
     * @return 所属マスタの値
     */
    String buildSQLForSelectEmployeeDetail(String sectionId, String today, String custId, String compCode);

}
