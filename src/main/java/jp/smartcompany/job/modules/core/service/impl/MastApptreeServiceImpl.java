package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.framework.auth.entity.AppAuthJudgmentEntity;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import jp.smartcompany.job.modules.core.mapper.MastApptreeMapper;
import jp.smartcompany.job.modules.core.service.IMastApptreeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * アプリケーション設定マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastApptreeServiceImpl extends ServiceImpl<MastApptreeMapper, MastApptreeDO> implements IMastApptreeService {

        @Override
        public  List<AppAuthJudgmentEntity> selectAppTreePermission() {
                return baseMapper.selectAppTreePermission();
        }

        @Override
        public List<AppAuthJudgmentEntity> selectGroupPermission(String systemCode,String groupCode) {
                return baseMapper.selectGroupPermission(systemCode,groupCode);
        }

        @Override
        public List<MastApptreeDO> selectSiteOrAppListByType(@Param("psSystemId") String psSystemId,
                                                             @Param("psLanguage") String psLanguage,
                                                             @Param("psType") String psType,
                                                             @Param("psSiteId") String psSiteId) {
                return baseMapper.selectSiteOrAppListByType(psSystemId,psLanguage,psType,psSiteId);
        }
}
