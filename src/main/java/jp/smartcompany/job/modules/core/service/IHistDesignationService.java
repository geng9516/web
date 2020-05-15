package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.SectionGroupId;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 異動歴 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IHistDesignationService extends IService<HistDesignationDO> {


        /**
         * 在籍部署・グループを検索
         *
         * @param customerId 　顧客コード
         * @param companyId  　法人コード
         * @param employeeId  　社員番号
         * @param yyyymmdd 　勤務日
         * @return
         */
        SectionGroupId selectSecGroupId(String customerId, String companyId, String employeeId , Date yyyymmdd);


        List<HistDesignationDO> selectCompanyId(String userId,Date date);

}
