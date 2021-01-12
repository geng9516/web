package jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HistBulletinBoardMapper extends BaseMapper<HistBulletinBoardDO> {

    IPage<HistBulletinBoardDO>  selectBulletinBoardByPublisherId(IPage<HistBulletinBoardDO> pageQuery,@Param("publisherId") String publisherId);

}
