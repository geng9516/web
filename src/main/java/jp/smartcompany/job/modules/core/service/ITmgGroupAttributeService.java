package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupAttributeDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.HolidayTimeLimitDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.OverTimeLimitDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.LimitOfBaseDate;

import java.util.List;

/**
 * <p>
 * グループ属性テーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgGroupAttributeService extends IService<TmgGroupAttributeDO> {

        /**
         * 基準日時点の超勤限度時間取得用クエリを返す
         * */
        List<LimitOfBaseDate> selectLimitOfBaseDate(String custId, String compId, String sectionId, String baseDate);

        /**
         * 閾値（超過勤務）の取得
         * */
        OverTimeLimitDto selectOverTimeLimit(String custId, String compId, String sectionId, String groupId);


        /**
         * 閾値（休日出勤）の取得
         * */
        HolidayTimeLimitDto selectHolidayTimeLimit(String custId, String compId, String sectionId, String groupId);
        }
