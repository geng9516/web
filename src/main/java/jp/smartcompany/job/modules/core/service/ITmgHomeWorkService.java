package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TmgHomeWorkDataDO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminListVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkMonthVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkVO;

import java.util.List;

/**
 * @author 顧成斌
 * @description 在宅勤務登録
 * @date 2020/11/25
 **/
public interface ITmgHomeWorkService extends IService<TmgHomeWorkDataDO> {
    /**
     * 在宅勤務登録情報を取得します
     *
     * @param employeeId
     * @param baseDate
     * @return
     */
    List<HomeWorkVO> selectHomeWork(String employeeId, String baseDate);

    /**
     * Admin在宅勤務登録情報を取得します
     *
     * @param employeeId
     * @param baseDate
     * @return
     */
    List<HomeWorkAdminVO> selectAdminHomeWork(String employeeId, String baseDate);

    /**
     * Admin在宅勤務编辑情報を取得します
     *
     * @param employeeId
     * @param baseDate
     * @return
     */
    List<HomeWorkAdminVO> selectAdminHomeWorkUpdateList(String employeeId, String baseDate);

    /**
     * Admin在宅勤務Monthを取得します
     *
     * @param employeeId
     * @param baseDate
     * @return
     */
    List<HomeWorkMonthVO> selectAdminHomeWorkMonthList(String employeeId, String baseDate);

}
