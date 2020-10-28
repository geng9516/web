package jp.smartcompany.job.modules.core.mapper.TmgUpdsKintai;

import jp.smartcompany.job.modules.core.pojo.entity.TmgUpdsKintaiDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]月次集計出力イメージ(upds連携用、過去データ退避 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgUpdsKintaiMapper extends BaseMapper<TmgUpdsKintaiDO> {

    List<Map<String,Object>> selectMoUpds(@Param("columnList")List<String> columnList,
                                     @Param("functionID")String functionID,
                                     @Param("sectionID")String sectionID,
                                     @Param("dlTypeID")String dlTypeID,
                                     @Param("date")String date,
                                     @Param("custID")String custID,
                                     @Param("compID")String compID,
                                     @Param("lang")String lang);
}
