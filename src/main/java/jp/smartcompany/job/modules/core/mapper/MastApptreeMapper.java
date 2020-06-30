package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.framework.auth.entity.AppAuthJudgmentEntity;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * アプリケーション設定マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastApptreeMapper extends BaseMapper<MastApptreeDO> {

  List<AppAuthJudgmentEntity> selectAppTreePermission();

  List<AppAuthJudgmentEntity> selectGroupPermission(@Param("systemCode") String systemCode,
                                                    @Param("groupCode") String groupCode);

  List<MastApptreeDO> selectSiteOrAppListByType(@Param("psSystemId") String psSystemId,
                                                @Param("psLanguage") String psLanguage,
                                                @Param("psType") String psType,
                                                @Param("psSiteId") String psSiteId);


}
