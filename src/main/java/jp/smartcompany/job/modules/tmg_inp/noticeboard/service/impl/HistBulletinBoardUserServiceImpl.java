package jp.smartcompany.job.modules.tmg_inp.noticeboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper.HistBulletinBoardUserMapper;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardUserDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardUserService;
import org.springframework.stereotype.Service;

@Service
public class HistBulletinBoardUserServiceImpl extends ServiceImpl<HistBulletinBoardUserMapper, HistBulletinBoardUserDO>
implements IHistBulletinBoardUserService {

    @Override
    public HistBulletinBoardUserDO getByNoticeId(Long noticeId) {
        QueryWrapper<HistBulletinBoardUserDO> qw = SysUtil.query();
        return getOne(qw.eq("HBG_CARTICLEID",noticeId));
    }

}
