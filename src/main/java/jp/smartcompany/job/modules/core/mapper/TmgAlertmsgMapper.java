package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAlertmsgDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.AlertVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [勤怠]アラートメッセージ格納テーブル Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgAlertmsgMapper extends BaseMapper<TmgAlertmsgDO> {

    List<AlertVo> selectAlert(@Param("cust") String cust,
                              @Param("comp")String comp,
                              @Param("secid")String secid,
                              @Param("dyyyymm")String dyyyymm,
                              @Param("lang")String lang,
                              @Param("numStart")int numStart,
                              @Param("numEnd")int numEnd);
}
