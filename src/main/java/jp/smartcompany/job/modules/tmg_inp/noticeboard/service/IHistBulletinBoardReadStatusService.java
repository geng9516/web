package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardReadStatusDO;

public interface IHistBulletinBoardReadStatusService extends IService<HistBulletinBoardReadStatusDO> {

    boolean checkNoticeHasRead(String userId, Long articleId);

    HistBulletinBoardReadStatusDO getStatusByUserIdArticleId(String userId, Long articleId);

}
