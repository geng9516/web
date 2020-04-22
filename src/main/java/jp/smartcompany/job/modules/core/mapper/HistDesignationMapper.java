package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.SectionGroupId;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * 異動歴 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface HistDesignationMapper extends BaseMapper<HistDesignationDO> {


        /**
         * 在籍部署・グループを検索
         */
        SectionGroupId selectSecGroupId(Map<String, Object> map);
        }
