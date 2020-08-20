package jp.smartcompany.admin.groupmanager.logic.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerGroupListDTO;
import jp.smartcompany.admin.groupmanager.dto.GroupManagerModifiedDateDTO;
import jp.smartcompany.admin.groupmanager.logic.GroupManagerDateEditLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupManagerDateEditLogicImpl implements GroupManagerDateEditLogic {

    private final ScCacheUtil cacheUtil;
    private final PsSearchCompanyUtil searchCompanyUtil;
    private final IMastGroupService iMastGroupService;

    /** システムプロパティ(グループ判定モード) */
    private static final String KEY_GROUP_CHECK_MODE = "GroupCheckMode";

    @Override
    public Map<String,Object> editListHandler(String searchDate,String systemId) {
        if (searchDate == null) {
            searchDate = SysUtil.transDateToString(DateUtil.date());
        }
        // システムプロパティより、グループ判定モードを取得
        String checkMode = cacheUtil.getSystemProperty(KEY_GROUP_CHECK_MODE);
        // システムプロパティに設定されていなかった場合
        if (StrUtil.isBlank(checkMode)) {
            // 必須常駐変数未設定例外
            throw new GlobalException(KEY_GROUP_CHECK_MODE);
        }
        // 法人検索対象範囲情報取得(参照可能な法人のリストを取得)
        List<String> companyValidList = searchCompanyUtil.getCompList(SysUtil.transStringToDate(searchDate));
        PsSession session = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
        // 获取当前系统正在被使用的grouplist
        List<GroupManagerGroupListDTO> validGroupList= iMastGroupService.selectValidGroup(session.getLoginCustomer(),systemId,session.getLanguage(),searchDate,companyValidList);
        validGroupList.forEach(item->{
            if (StrUtil.equals(item.getMgCcompanyid(), PsConst.CODE_ALL_COMPANIES)){
                item.setCompanyName("全社区分");
            }
        });
        // 歴日付情報取得
        List<GroupManagerModifiedDateDTO> historyDate =
                iMastGroupService.selectHistoryDate(session.getLoginCustomer(), systemId, companyValidList,searchDate);

        Map<String,Object> map = MapUtil.newHashMap();
        map.put("historyDate",historyDate);
        map.put("groupList",validGroupList);

        // 現在有効な日付(複数件数が、返却されることは想定していない)
        GroupManagerModifiedDateDTO dateDTO = historyDate.get(0);
        String sValidDate = transStringFormat(dateDTO.getValiddate());
        // 今回改定日，改定日を指定フォーマットに変換して詰め替え
        String applyDate = SysUtil.transDateToString(dateDTO.getMgDstartdate());
        String previousDate = transStringFormat(dateDTO.getPreviousdate());
        String nextDate = transStringFormat(dateDTO.getNextdate());

//        // 最新改定日よりシステム日付が小さい場合
//        if (pdModifiedDate != null && pdSysdate.compareTo(pdModifiedDate) < 0) {
//            // 「現在有効な歴の開始日」を今回改定日に設定
//            this.groupManagerModifiedDateDto.setModifiedDate(
//                    PsV4Util.transDateToString(
//                            lGroupManagerModifiedDateDtoDate.get(0).getMgDstartdate()));
//        } else {
//            // 「システム日付」を今回改定日に設定
//            this.groupManagerModifiedDateDto.setModifiedDate(
//                    PsV4Util.transDateToString(pdSysdate));
//        }
//
//        // 最新改定日を指定
//        HashMap<String, Object> hmVariables = new HashMap<String, Object>();
//        hmVariables.put(GroupManagerModifiedDateLogicImpl.MAXDATE, sValidDate);
//        this.groupManagerModifiedDateDto.setJsVariables(hmVariables);
//        this.groupManagerModifiedDateDto.setMindate(sValidDate);

        return map;
    }

    /**
     * 日付文字列を指定フォーマット(yyyy/MM/dd)に変換する。
     * nullまたはフォーマットの文字数に満たない場合は場合は、nullを返す
     *
     * @param   psDate 日付文字列
     * @return  String(yyyy/MM/dd)
     */
    public String transStringFormat(String psDate)  {
        String sFormat = "yyyy/MM/dd";

        if (psDate == null
                || sFormat.length() > psDate.length()) {
            return null;
        }

        return psDate.substring(0, sFormat.length()).replace("-", "/");
    }

}
