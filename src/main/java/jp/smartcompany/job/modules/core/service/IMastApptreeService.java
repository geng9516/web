package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.admin.appmanager.dto.MastAppDTO;
import jp.smartcompany.admin.appmanager.dto.MastAppTreeDTO;
import jp.smartcompany.framework.auth.entity.AppAuthJudgmentEntity;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * アプリケーション設定マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastApptreeService extends IService<MastApptreeDO> {

        List<AppAuthJudgmentEntity> selectAppTreePermission();

        List<AppAuthJudgmentEntity> selectGroupPermission(String systemCode,String groupCode);

        List<MastApptreeDO> selectSiteOrAppListByType(String psSystemId,
                                                      String psLanguage,
                                                      String psType,
                                                      String psSiteId);

        List<MastAppTreeDTO> selectMastAppList();

        int removeAll();

}
