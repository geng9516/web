package jp.smartcompany.job.modules.core.mapper.MastGroupapppermission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerChangeDateDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupapppermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ別アプリケーション権限マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGroupapppermissionMapper extends BaseMapper<MastGroupapppermissionDO> {

        /**
         * 起動権限一覧取得
         *
         * @param systemId システムID
         * @param date 検索日付
         * @param groupIds グループID(表示対象のみをIN句で取得する)
         * @param siteId サイトID
         * @param appId アプリケーションID
         * @param language 言語区分
         * @return List<GroupAppManagerPermissionDTO>
         */
        List<GroupAppManagerPermissionDTO> selectPermissionList(
                @Param("systemId") String systemId,
                @Param("date") String date,
                @Param("groupIds") List<String> groupIds,
                @Param("siteId") String siteId,
                @Param("appId") String appId,
                @Param("language") String language);

        List<GroupAppManagerPermissionDTO> selectPermissionListBySiteList(
                @Param("systemId") String systemId,
                @Param("date") String date,
                @Param("groupIds") List<String> groupIds,
                @Param("siteList") List<String> siteList,
                @Param("language") String language
        );

        GroupAppManagerChangeDateDTO selectDate(@Param("systemId") String systemId,
                                                @Param("systemDate") String pdDate,
                                                @Param("groupId") String groupId);


        // 以下三个方法废弃使用
        int deleteAfter(@Param("systemId") String systemId,
                        @Param("date") String date,
                        @Param("groupId") String groupId,
                        @Param("objectId") String objectId);

        List<MastGroupapppermissionDO> selectValidPermissions(@Param("systemId") String systemId,
                                                              @Param("date") String date,
                                                              @Param("groupId") String groupId,
                                                              @Param("objectId") String objectId);

        int deleteOtherSysObj(@Param("systemId") String systemId,@Param("objectId") String objectId);

        List<String> selectSiteList(Boolean isApprover);

        List<String> selectUrlPermFlags(@Param("groupIds") List<String> groupIds,@Param("objectId") String objectId);

}
