package jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.DraftNoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HistBulletinBoardTempMapper extends BaseMapper<HistBulletinBoardTempDO> {

    IPage<DraftNoticeVO> selectBulletinBoardTempByPublisherId(IPage<DraftNoticeVO> pageQuery,@Param("empId") String loginUserId);

}
