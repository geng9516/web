package jp.smartcompany.job.modules.core.mapper.HistGroupdatapermission;

import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerChangeDateDTO;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerDataDTO;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdatapermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ別アプリケーション検索対象範囲設定テーブル Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface HistGroupdatapermissionMapper extends BaseMapper<HistGroupdatapermissionDO> {

        List<SearchRangeManagerDataDTO> getSearchRangeTableData(@Param("systemId") String systemId,
                                                                @Param("siteId") String siteId,
                                                                @Param("appId") String appId,
                                                                @Param("searchDate") String sSearchDate,
                                                                @Param("language") String language,
                                                                @Param("groupIds") List<String> groupIds);

        SearchRangeManagerChangeDateDTO selectHistoryDate(@Param("custId") String custId,@Param("systemId") String systemId,
                                                          @Param("groupId") String groupId,@Param("searchDate") String searchDate);

        void updateFinishHistory(@Param("hgpId") Long hgpId,@Param("searchDate") Date startDate);

        @Select("SELECT DISTINCT HGP_CPERMNECESSITY FROM HIST_GROUPDATAPERMISSION WHERE" +
                " HGP_CSITEID = #{siteId}" +
                " AND HGP_CAPPID = #{appId}" +
                " AND HGP_CCUSTOMERID = '01'" +
                " AND HGP_CSYSTEMID = '01'" +
                " AND HGP_CGROUPID in (${groupIdArr})" +
                " AND HGP_DSTARTDATE <= TRUNC(SYSDATE)" +
                " AND HGP_DENDDATE >= TRUNC(SYSDATE)" +
                " AND HGP_CBASESECTION_FLAG_NEED = #{useBaseSection}")
        String selectSearchRangeBySiteIdAndAppIdAndGroupCodeListStr(@Param("siteId") String sSiteId,
                                                                    @Param("appId") String sAppId,
                                                                    @Param("groupIdArr") String userGroupCodeListString,
                                                                    @Param("useBaseSection") String useBaseSection);
}
