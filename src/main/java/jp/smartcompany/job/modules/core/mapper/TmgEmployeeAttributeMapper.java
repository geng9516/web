package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgEmployeeAttributeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.TmgEmployeeAttributeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * public.tmg_employee_attribute Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgEmployeeAttributeMapper extends BaseMapper<TmgEmployeeAttributeDO> {

    /**
     * 基本情報属性を検索
     */
    List<TmgEmployeeAttributeVO> buildSQLForSelectTmgEmployeeAttribute(Map<String, Object> map);

    /**
     * 基本情報属性をインサート
     */
    int buildSQLForInsertTmgEmployeeAttributeActDisp(Map<String, Object> map);


    /**
     * 基本情報属性を更新
     */
    int buildSQLForUpdateTmgEmployeeAttribute(Map<String, Object> map);

}
