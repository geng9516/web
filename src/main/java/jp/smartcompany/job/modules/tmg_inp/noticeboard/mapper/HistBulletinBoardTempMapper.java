package jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.DraftNoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HistBulletinBoardTempMapper extends BaseMapper<HistBulletinBoardTempDO> {

    @Select("SELECT HBT_ID,HBT_DDATEOFANNOUNCEMENT,HBT_DDATEOFEXPIRE,HBT_CTITLE,HBT_CHEADDISP FROM HIST_BULLETINBOARD_TEMP WHERE " +
            "HBT_CCUSTOMERID = '01' AND HBT_CCOMPANYID = '01' AND HBT_CMNUSER = #{empId} ORDER BY HBT_CHEADDISP DESC,HBT_DMODIFIEDDATE DESC")
    IPage<DraftNoticeVO> selectBulletinBoardTempByPublisherId(IPage<DraftNoticeVO> pageQuery,@Param("empId") String loginUserId);

}
