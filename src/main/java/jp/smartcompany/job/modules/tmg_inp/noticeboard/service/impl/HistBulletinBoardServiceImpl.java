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
    public IPage<NoticeVO> listByPublisherId(Page<NoticeVO> pageQuery, String loginUserId) {
        return baseMapper.selectByPublisherId(pageQuery,loginUserId);
    }

    @Override
    public IPage<NoticeVO> selectVisibleList(IPage<NoticeVO> pageQuery,String userId) {
        return baseMapper.selectVisibleList(pageQuery,userId);
    }

}
