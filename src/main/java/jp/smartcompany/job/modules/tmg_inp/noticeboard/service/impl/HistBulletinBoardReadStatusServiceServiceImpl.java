package jp.smartcompany.job.modules.tmg_inp.noticeboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper.HistBulletinBoardReadStatusMapper;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardReadStatusDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardReadStatusService;
import org.springframework.stereotype.Service;

@Service
public class HistBulletinBoardReadStatusServiceServiceImpl extends ServiceImpl<HistBulletinBoardReadStatusMapper, HistBulletinBoardReadStatusDO>
implements IHistBulletinBoardReadStatusService {

    @Override
    public boolean checkNoticeHasRead(String userId, Long articleId) {
        QueryWrapper<HistBulletinBoardReadStatusDO> qw = SysUtil.query();
        qw.eq("hb_id_fk",articleId)
                .eq("hbrs_emp_id_fk",userId);
        return count(qw)>0;
    }

}
