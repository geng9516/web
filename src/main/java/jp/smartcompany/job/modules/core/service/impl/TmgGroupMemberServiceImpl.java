package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupMemberDO;
import jp.smartcompany.job.modules.core.mapper.TmgGroupMemberMapper;
import jp.smartcompany.job.modules.core.service.ITmgGroupMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [勤怠]グループ割付情報                  データ開始日、終了日は親となる異動歴のデータ開始日、終了日と 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgGroupMemberServiceImpl extends ServiceImpl<TmgGroupMemberMapper, TmgGroupMemberDO> implements ITmgGroupMemberService {

        @Override
        public List<TmgGroupMemberDO> getTmgGroupMemberDOList(String customerId, String companyId, String employeeId) {
                return null;
        }
}
