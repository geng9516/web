package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupMemberCheckDO;
import jp.smartcompany.job.modules.core.mapper.TmgGroupMemberCheckMapper;
import jp.smartcompany.job.modules.core.service.ITmgGroupMemberCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]エラーチェック用グループ割付情報          データ開始日、終了日は親となる異動歴のデータ開始日、終了日と 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgGroupMemberCheckServiceImpl extends ServiceImpl<TmgGroupMemberCheckMapper, TmgGroupMemberCheckDO> implements ITmgGroupMemberCheckService {

        }
