package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.admin.component.dto.BaseSectionRowDTO;
import jp.smartcompany.admin.component.dto.BaseSectionRowListDTO;
import jp.smartcompany.job.modules.core.pojo.bo.GroupBaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupbasesectionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ別基点組織マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastGroupbasesectionService extends IService<MastGroupbasesectionDO> {

        /**
         * 根据用户组code和
         * @param customerId
         * @param systemCode
         * @param groupCode
         * @param date
         * @return
         */
        List<GroupBaseSectionBO>  getBaseSectionByGroupCode(String customerId, String systemCode, String groupCode, Date date);

        /**
         * グループ基点組織マスタ(基点組織)の定義情報を取得
         *
         * @param   psCustomerId    顧客コード
         * @param   psSystemId      システムID
         * @param   psGroupId       グループID
         * @param   psLanguage      言語区分
         * @param   pdSearchDate    今回改定日
         * @param   plValidCompany  参照可能な法人一覧
         * @return  List <QueryConditionRowDto>  基点組織定義情報リスト
         */
        List<BaseSectionRowDTO> selectGroupBaseSectionCompanyList(String psCustomerId,
                                                                   String psSystemId, String psGroupId, String psLanguage, Date pdSearchDate,
                                                                   List<String> plValidCompany);

        /**
         * グループ基点組織マスタ(基点組織)の定義情報を取得
         *
         * @author  isolyamada
         * @param   psCustomerId    顧客コード
         * @param   psCompanyId     法人コード
         * @param   psSystemId      システムID
         * @param   psGroupId       グループID
         * @param   psLanguage      言語区分
         * @param   pdSearchDate    今回改定日
         * @return  List < BaseSectionRowListDTO>  基点組織定義情報リスト
         */
        List<BaseSectionRowListDTO> selectGroupBaseSectionList(String psCustomerId,
                                                               String psCompanyId, String psSystemId, String psLanguage,
                                                               String psGroupId, Date pdSearchDate);
}
