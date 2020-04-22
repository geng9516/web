package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.bo.DBMastGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * グループ定義マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastGroupService extends IService<MastGroupDO> {

        /**
         * 根据语言标识获取所有角色组
         * @param language
         * @return
         */
        List<DBMastGroupBO> getUserGroupByLanguage(String language, String systemCode);

        /**
         * 根据语言标识和用户id获取角色组
         * @param language
         * @param userId
         * @return
         */
        List<DBMastGroupBO> getPretreatGroupByLanguageUserId(String language, String userId);

}
