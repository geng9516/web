package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.DraftNoticeVO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.NoticeVO;

public interface IHistBulletinBoardTempService extends IService<HistBulletinBoardTempDO> {

    IPage<DraftNoticeVO> listBulletinBoardTempByPublisherId(IPage<DraftNoticeVO> pageQuery,String loginUserId);

    IPage<NoticeVO> listBulletinBoard(IPage<NoticeVO> pageQuery,String userId);

}
