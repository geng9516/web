package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNotificationDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.NotificationDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.NotificationListVo;

import java.util.List;

/**
 * <p>
 * [勤怠]申請情報                      2007/01/31項目追加「申請日」「対象曜日：指定なし」 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgNotificationService extends IService<TmgNotificationDO> {

        /**
         * 休暇・休業申請を一覧を取得するSQLを返す
         *
         * @param params 　params
         * @return String パターン
         */
        List<NotificationListVo> selectNotificationList(ParamNotificationListDto params);

        /**
         * 休暇・休業申請を一覧COUNT
         *
         * @param params 　params
         * @return String パターン
         */
        int selectNotificationListCount(ParamNotificationListDto params);

        /**
         * 申請詳細を取得するSQLを返す
         *buildSQLForSelectNotificationDetail
         * @param params 　params
         * @return String パターン
         */
        NotificationDetailVo selectNotificationDetail(ParamNotificationListDto params);

        /**
         * シーケンス採番
         * buildSQLForSelectNotificationSeq
         * @return String
         */
        String selectNotificationSeq();

        /**
         * 申請区分略称を取得する
         * buildSQLForSelectNtfName
         * @return String
         */
        String selectNtfName(String custId,String compId,String ntfNo);

        /**
         * 一覧の対象件数を取得するSQLを返す
         * buildSQLForSelectNotificationCount
         */
        int selectNotificationCount(ParamNotificationListDto params);


        /**
         * 遡り期限を取得する
         * buildSQLForSelectBackLimit
         */
        String selectBackLimit(String custId,String compId,String employeeId);


        /**承認後更新のSEQ
         * buildSQLForUpdateNotificationItem
         * */
        int updateNotificationItem(ParamNotificationListDto params);
        }
