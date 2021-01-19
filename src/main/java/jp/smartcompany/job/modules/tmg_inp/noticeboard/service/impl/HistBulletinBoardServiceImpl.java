package jp.smartcompany.job.modules.tmg_inp.noticeboard.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper.HistBulletinBoardMapper;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.NoticeVO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardService;
import org.springframework.stereotype.Service;

@Service
public class HistBulletinBoardServiceImpl extends ServiceImpl<HistBulletinBoardMapper,HistBulletinBoardDO>
implements IHistBulletinBoardService {

    @Override
    public IPage<HistBulletinBoardDO> listBulletinBoardByPublisherId(IPage<HistBulletinBoardDO> pageQuery, String loginUserId) {
        return baseMapper.selectBulletinBoardByPublisherId(pageQuery,loginUserId);
    }

    @Override
    public IPage<NoticeVO> listRubbishByPublisherId(Page<NoticeVO> pageQuery, String loginUserId) {
        return baseMapper.selectRubbishByPublisherId(pageQuery,loginUserId);
    }

}
