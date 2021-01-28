package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.NoticeVO;
import org.apache.ibatis.annotations.Param;


public interface IHistBulletinBoardService extends IService<HistBulletinBoardDO> {

    IPage<NoticeVO> listByPublisherId(Page<NoticeVO> pageQuery, String loginUserId);

    IPage<NoticeVO> selectVisibleList(@Param("page") IPage<NoticeVO> pageQuery, @Param("userId") String userId);

}
