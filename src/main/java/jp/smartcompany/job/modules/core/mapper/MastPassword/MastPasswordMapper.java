package jp.smartcompany.job.modules.core.mapper.MastPassword;

import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * パスワードマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastPasswordMapper extends BaseMapper<MastPasswordDO> {

   List<MastPasswordDO> selectSinglePassword(@Param("username") String username,@Param("hisCnt") String passwordHisCnt);

   int updateHistory(String username);

   int updateHistoryNo(@Param("userIds") List<String> userIds);
}
