package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.bo.DBMastGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * グループ定義マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGroupMapper extends BaseMapper<MastGroupDO> {

        /**
         *根据语言标识获取所有角色组
         * @param language
         * @return
         */
        List<DBMastGroupBO> getUserGroupByLanguage(@Param("language") String language, @Param("systemCode") String systemCode);

        /**
         *根据语言标识和用户id获取角色组
         * @param language
         * @param userId
         * @return List<LoginUserGroupBO>
         */
        List<DBMastGroupBO> getPretreatGroupByLanguageUserId(@Param("language") String language, @Param("userId") String userId);
}
