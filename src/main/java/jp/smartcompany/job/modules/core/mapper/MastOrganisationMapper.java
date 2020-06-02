package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.LimitOfBasedateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 組織ツリーマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastOrganisationMapper extends BaseMapper<MastOrganisationDO> {

     List<String> selectLowerSection(@Param("custId") String custId,@Param("compId") String compId,
                                            @Param("sectionId") String psSection,@Param("date") Date date);


     /**
      * 基準日時点の超勤限度時間取得用
      */
     LimitOfBasedateVO buildSQLForLimitOfBasedate(Map<String, Object> map);

     /**
      * 職員情報を取得する
      */
     String buildSQLForSelectEmployeeDetail(Map<String, Object> map);

}
