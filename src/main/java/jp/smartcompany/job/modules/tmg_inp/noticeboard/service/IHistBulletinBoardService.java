package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardDO;


public interface IHistBulletinBoardService extends IService<HistBulletinBoardDO> {

    IPage<HistBulletinBoardDO> listBulletinBoardByPublisherId(IPage<HistBulletinBoardDO> pageQuery,String loginUserId);

}
