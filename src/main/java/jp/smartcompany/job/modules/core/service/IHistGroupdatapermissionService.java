package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerChangeDateDTO;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerDataDTO;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdatapermissionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ別アプリケーション検索対象範囲設定テーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IHistGroupdatapermissionService extends IService<HistGroupdatapermissionDO> {

        List<SearchRangeManagerDataDTO> getSearchRangeTableData(String systemId, String siteId, String appId, Date searchDate,String language,List<String> groupIds);

        SearchRangeManagerChangeDateDTO selectHistoryDate(String custId,String systemId,String groupId,Date searchDate);

        void updateFinishHistory(Long hgpId, Date startDate);
}
