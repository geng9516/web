package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgBulkNtfDetailDO;
import jp.smartcompany.job.modules.core.mapper.TmgBulkNtfDetail.TmgBulkNtfDetailMapper;
import jp.smartcompany.job.modules.core.service.ITmgBulkNtfDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgbulknotification.vo.SectionDetailVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [勤怠]休暇休業一括登録・対象組織情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgBulkNtfDetailServiceImpl extends ServiceImpl<TmgBulkNtfDetailMapper, TmgBulkNtfDetailDO> implements ITmgBulkNtfDetailService {

        @Override
        public List<SectionDetailVo> selectSectionList(String seq,String custID,String compID,String lang){
                return baseMapper.selectSectionList(seq,custID, compID, lang);
        }
        }
