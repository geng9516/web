package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.component.dto.QueryConditionSelectDTO;
import jp.smartcompany.framework.sysboot.dto.MastDatadicSeclevelDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.core.mapper.MastDatadictionary.MastDatadictionaryMapper;
import jp.smartcompany.job.modules.core.service.IMastDatadictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * データディクショナリマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastDatadictionaryServiceImpl extends ServiceImpl<MastDatadictionaryMapper, MastDatadictionaryDO> implements IMastDatadictionaryService {

        @Override
        public List<MastDatadictionaryDO> selectAllDicts() {
           return baseMapper.selectAllDicts();
        }

        @Override
        public List<MastDatadicSeclevelDTO> selectAllDataDicSecLevel() {
           return baseMapper.selectAllDataDicSecLevel();
        }

        @Override
        public List<QueryConditionRowDTO> selectGroupJoinQuery(String customerId, String tableId) {
          return baseMapper.selectGroupJoinQuery(customerId,tableId);
        }

        @Override
        public List<QueryConditionSelectDTO> selectTableInfo(
                                                  String customerId,
                                                  String language,
                                                  String mdCavlforckStart) {
          return baseMapper.selectTableInfo(customerId,language,mdCavlforckStart);
        }

        @Override
        public List<QueryConditionSelectDTO> selectColumnInfo(String customerId,String language,String tableId,String mdCavlforckStart) {
          return baseMapper.selectColumnInfo(customerId,language,tableId,mdCavlforckStart);
        }
}
