package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.DraftNoticeVO;

public interface IHistBulletinBoardTempService extends IService<HistBulletinBoardTempDO> {

    IPage<DraftNoticeVO> listBulletinBoardTempByPublisherId(IPage<DraftNoticeVO> pageQuery,String loginUserId);

}
