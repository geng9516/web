package jp.smartcompany.job.modules.core.mapper.HistGroupdatapermission;

import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerChangeDateDTO;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerDataDTO;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdatapermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
