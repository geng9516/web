package jp.smartcompany.job.modules.tmg_inp.noticeboard.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper.HistBulletinBoardTempMapper;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.DraftNoticeVO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardTempService;
import org.springframework.stereotype.Service;

@Service
public class HistBulletinBoardTempServiceImpl extends ServiceImpl<HistBulletinBoardTempMapper, HistBulletinBoardTempDO> implements IHistBulletinBoardTempService {

    @Override
    public IPage<DraftNoticeVO> listBulletinBoardTempByPublisherId(IPage<DraftNoticeVO> pageQuery,String loginUserId) {
        return baseMapper.selectBulletinBoardTempByPublisherId(pageQuery,loginUserId);
    }

}
