package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastPostDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MastPostMapper extends BaseMapper<MastPostDO> {

    List<MastPostDO> select(
            @Param("companyId") String companyId,
            @Param("baseDate") String baseDate,
            @Param("language") String language,
            @Param("customerId") String customerId,
            @Param("sExists") String sExists
    );

}
