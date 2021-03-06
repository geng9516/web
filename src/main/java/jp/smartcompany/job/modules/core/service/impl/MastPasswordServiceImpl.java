package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import jp.smartcompany.job.modules.core.mapper.MastPassword.MastPasswordMapper;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * パスワードマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastPasswordServiceImpl extends ServiceImpl<MastPasswordMapper, MastPasswordDO> implements IMastPasswordService {

        @Override
        public List<MastPasswordDO> getUpdateDateByUsernamePassword(String userId, String password) {
                QueryWrapper<MastPasswordDO> qw = SysUtil.query();
                qw.eq("map_cuserid", userId).eq("map_cpassword", password)
                .eq("map_nhistory",0).select("map_dpwddate");
                return list(qw);
        }

        @Override
        public List<MastPasswordDO> selectSinglePassword(String username, String passwordHisCnt) {
                return baseMapper.selectSinglePassword(username,passwordHisCnt);
        }

        @Override
        public int updateHistory(String username) {
               return baseMapper.updateHistory(username);
        }

        @Override
        public int updateHistoryNo(List<String> userIds) {
                return baseMapper.updateHistoryNo(userIds);
        }

}
