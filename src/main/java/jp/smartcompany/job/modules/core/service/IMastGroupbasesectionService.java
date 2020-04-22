package jp.smartcompany.job.modules.core.service;

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

}
