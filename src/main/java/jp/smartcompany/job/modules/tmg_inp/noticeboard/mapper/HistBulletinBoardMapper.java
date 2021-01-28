package jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HistBulletinBoardMapper extends BaseMapper<HistBulletinBoardDO> {

    IPage<NoticeVO> selectByPublisherId(Page<NoticeVO> pageQuery,@Param("userId") String loginUserId);

    IPage<NoticeVO> selectVisibleList(@Param("page") IPage<NoticeVO> pageQuery,@Param("userId") String userId);

}
