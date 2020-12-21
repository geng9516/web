package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerChangeDateDTO;
import jp.smartcompany.admin.searchrangemanager.dto.SearchRangeManagerDataDTO;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdatapermissionDO;
import jp.smartcompany.job.modules.core.mapper.HistGroupdatapermission.HistGroupdatapermissionMapper;
import jp.smartcompany.job.modules.core.service.IHistGroupdatapermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ別アプリケーション検索対象範囲設定テーブル 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class HistGroupdatapermissionServiceImpl extends ServiceImpl<HistGroupdatapermissionMapper, HistGroupdatapermissionDO> implements IHistGroupdatapermissionService {

   @Override
   public List<SearchRangeManagerDataDTO> getSearchRangeTableData(String systemId, String siteId, String appId, Date searchDate, String language, List<String> groupIds) {
      String sSearchDate = SysUtil.transDateToString(searchDate);
      return baseMapper.getSearchRangeTableData(systemId,siteId, appId, sSearchDate, language, groupIds);
   }

   @Override
   public SearchRangeManagerChangeDateDTO selectHistoryDate(String custId, String systemId, String groupId, Date searchDate) {
      String sSearchDate = SysUtil.transDateToString(searchDate);
      return baseMapper.selectHistoryDate(custId,systemId,groupId,sSearchDate);
   }

   @Override
   public void updateFinishHistory(Long hgpId, Date startDate) {
      baseMapper.updateFinishHistory(hgpId,startDate);
   }

   @Override
   public String getSearchRangeBySiteIdAndAppIdAndGroupCodeListStr(String sSiteId,String sAppId,String userGroupCodeListString,String useBaseSection) {
      return baseMapper.selectSearchRangeBySiteIdAndAppIdAndGroupCodeListStr(sSiteId, sAppId, userGroupCodeListString,useBaseSection);
   }

}
