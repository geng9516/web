package jp.smartcompany.job.modules.core.mapper.TmgEmployees;

import jp.smartcompany.job.modules.core.pojo.entity.TmgEmployeesDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.IsWorkHealthChkVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * [勤怠]基本情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgEmployeesMapper extends BaseMapper<TmgEmployeesDO> {

    /**
     * 就業管理対象外の最終日を求める
     *
     * @param map 検索条件
     * @return Date 就業管理対象外の最終日
     */
    Date selectEndDate(Map<String, Object> map);

    /**
     * 就業管理対象の開始日を求める
     *
     * @param map 検索条件
     * @return Date 就業管理対象の開始日
     */
    Date selectStartDate(Map<String, Object> map);

    /**
     * 勤務状況確認欄、健康状態確認欄の使用可否設定の取得
     *
     * @param map 検索条件
     * @return IsWorkHealthChkVO
     */
    IsWorkHealthChkVO buildIsWorkHealthChk(Map<String, Object> map);
}
