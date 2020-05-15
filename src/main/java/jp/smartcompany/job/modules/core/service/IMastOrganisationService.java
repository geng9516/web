package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionOrganisationBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import com.baomidou.mybatisplus.extension.service.IService;

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
         * @param customerId
         * @param companyId
         * @param sectionId
         * @param yyyymmdd
         * @return
         */
        MastOrganisationDO selectOrganisation(String customerId, String companyId,String sectionId,Date yyyymmdd);


        /**
         * 指定組織の検索（パタンが存在する）
         * @param customerId
         * @param companyId
         * @param yyyymmdd
         * @return
         */
        List<MastOrganisationDO> selectPatternOrganisation(String customerId, String companyId,Date yyyymmdd);

        /**
         * 根据层级选择基点组织下的组织列表
         * @param customerId
         * @param conds
         * @param date
         * @return
         */
        List<BaseSectionOrganisationBO> selectOrganisationByLevel(String customerId,String conds,Date date);

        /**
         * 获取父级组织的id
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
         * @author t-abe
         * @param customerId 顧客コード
         * @param compnyId 法人コード
         * @param sectionId 組織コード
         * @param searchDate 検索基準日
         * @return 下位組織情報
         * @exception
         */
        List<String> selectLowerSection(String psCustID, String psCompID, String psSection, Date date);

}
