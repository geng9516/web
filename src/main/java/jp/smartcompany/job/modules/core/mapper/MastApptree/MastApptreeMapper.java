package jp.smartcompany.job.modules.core.mapper.MastApptree;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.admin.appmanager.dto.MastAppDTO;
import jp.smartcompany.framework.auth.entity.AppAuthJudgmentEntity;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
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

  List<MastApptreeDO> selectSiteOrAppListByType(@Param("systemId") String psSystemId,
                                                @Param("language") String psLanguage,
                                                @Param("type") String psType,
                                                @Param("siteId") String psSiteId);

  List<MastAppDTO> selectMastAppList();

}
