package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNotificationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.paramNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.notificationDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.notificationListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]申請情報                      2007/01/31項目追加「申請日」「対象曜日：指定なし」 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgNotificationMapper extends BaseMapper<TmgNotificationDO> {


        /**
         * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
         */

        List<notificationListVo> selectNotificationList(@Param("paramNotificationList") paramNotificationListDto params);

        /**
         * 休業申請を一覧COUNT
         */
        int selectNotificationListCount(@Param("paramNotificationList") paramNotificationListDto params);

        /**
         * 申請詳細を取得するSQLを返す
         */

        notificationDetailVo selectNotificationDetail(Map<String, Object> map);

        /**
         * シーケンス採番
         * */
        String selectNotificationSeq();


        String selectNtfName(@Param("custId") String custId,
                             @Param("compId") String compId,
                             @Param("ntfNo") String ntfNo);

        /**
         * 一覧の対象件数を取得するSQLを返す
         */

        int selectNotificationCount(@Param("paramNotificationList") paramNotificationListDto params);

        /**
         * 遡り期限を取得するSQLを返す
         */
        String selectBackLimit(@Param("custId") String custId,
                               @Param("compId") String compId,
                               @Param("employeeId") String employeeId);

        /**
         * 承認後更新のSEQ
         */
        int updateNotificationItem(@Param("paramNotificationList") paramNotificationListDto params);
        }
