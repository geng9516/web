package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.HistJinjishokumeiDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 人事職名歴                         companyから連携した人事発令データに基く職名歴です Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface HistJinjishokumeiMapper extends BaseMapper<HistJinjishokumeiDO> {

        }
