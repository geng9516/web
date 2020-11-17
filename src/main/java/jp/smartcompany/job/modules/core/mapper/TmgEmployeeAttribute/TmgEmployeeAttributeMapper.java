package jp.smartcompany.job.modules.core.mapper.TmgEmployeeAttribute;

import jp.smartcompany.job.modules.core.pojo.entity.TmgEmployeeAttributeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.TmgEmployeeAttributeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 超過勤務対象有無を取得
     */
    String buildSQLForSelectTargetForOverTime(Map<String, Object> map);

    /**
     * 個人属性の指定タイプレコードの内、基準日以降に始まるレコードを削除
     */
    int buildSQLForDeleteTmgEmployeeAttribute(@Param("custID") String custID,
                                              @Param("compCode") String compCode,
                                              @Param("targetUser") String targetUser,
                                              @Param("cycleDay") String cycleDay,
                                              @Param("typeItemWorkStatus") String typeItemWorkStatus);

    /**
     * 個人属性の指定タイプレコードの、基準日で始まるレコードを追加
     */
    int buildSQLForInsertTmgEmployeeAttribute(@Param("custID") String custID,
                                              @Param("compCode") String compCode,
                                              @Param("targetUser") String targetUser,
                                              @Param("cycleDay1") String cycleDay1,
                                              @Param("cycleDay2") String cycleDay2,
                                              @Param("userCode") String userCode,
                                              @Param("modifierProgramId") String modifierProgramId,
                                              @Param("status") String status,
                                              @Param("action") String action,
                                              @Param("selHealthStatus") String selHealthStatus);
}
