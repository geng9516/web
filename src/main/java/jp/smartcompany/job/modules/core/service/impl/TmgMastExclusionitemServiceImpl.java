package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastExclusionitemDO;
import jp.smartcompany.job.modules.core.mapper.TmgMastExclusionitemMapper;
import jp.smartcompany.job.modules.core.service.ITmgMastExclusionitemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 除外項目マスタ                       集計処理カテゴリごとに、重複している場合に重複部分を除外する 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgMastExclusionitemServiceImpl extends ServiceImpl<TmgMastExclusionitemMapper, TmgMastExclusionitemDO> implements ITmgMastExclusionitemService {

        }
