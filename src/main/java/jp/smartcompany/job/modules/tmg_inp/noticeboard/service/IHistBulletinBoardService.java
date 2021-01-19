package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.NoticeVO;


public interface IHistBulletinBoardService extends IService<HistBulletinBoardDO> {

    IPage<HistBulletinBoardDO> listBulletinBoardByPublisherId(IPage<HistBulletinBoardDO> pageQuery,String loginUserId);

    IPage<NoticeVO> listRubbishByPublisherId(Page<NoticeVO> pageQuery, String loginUserId);

}
