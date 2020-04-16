package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupCheckDO;
import jp.smartcompany.job.modules.core.mapper.TmgGroupCheckMapper;
import jp.smartcompany.job.modules.core.service.ITmgGroupCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]エラーチェック用・グループ             データ開始日、終了日は親となる部署のデータ開始日、終了日とす 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgGroupCheckServiceImpl extends ServiceImpl<TmgGroupCheckMapper, TmgGroupCheckDO> implements ITmgGroupCheckService {

        }
