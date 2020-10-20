package jp.smartcompany.framework.sysboot;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.service.IMastDatapermissionService;
import jp.smartcompany.framework.sysboot.dto.SearchRangeInfoDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 *  検索範囲情報常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
@Slf4j
public class SearchRangeInfoCache {

    /** 検索範囲情報(組織、役職)MAP */
    private Map<String, List<SearchRangeInfoDTO>> ghmDataSectionPost;

    /** 検索範囲情報(条件式)MAP */
    private Map<String, List<SearchRangeInfoDTO>> ghmDataPermissionDefs;

    /**
     * 検索対象範囲条件取得（組織、役職）
     * @param   psPermissionid  キー(必要条件定義ID)
     * @return  検索範囲情報Bean
     */
    public List<SearchRangeInfoDTO> getDataSectionPost(String psPermissionid) {
        List<SearchRangeInfoDTO> searchRangeInfoDTO = null;
        if (psPermissionid != null
                && ghmDataSectionPost.containsKey(psPermissionid)) {
            searchRangeInfoDTO = ghmDataSectionPost.get(psPermissionid);
        }
        return searchRangeInfoDTO;
    }

    /**
     * 検索対象範囲条件取得（条件式）
     * @param   psPermissionid  キー(必須条件定義ID)
     * @return  検索範囲情報Bean
     */
    public List<SearchRangeInfoDTO> getDataPermissionDefs(String psPermissionid) {
        List <SearchRangeInfoDTO> searchRangeInfoDTO = null;
        if (psPermissionid != null
                && ghmDataPermissionDefs.containsKey(psPermissionid)) {
            searchRangeInfoDTO = ghmDataPermissionDefs.get(psPermissionid);
        }
        return searchRangeInfoDTO;
    }

    /**
     * 検索対象範囲条件作成
     */
    public void loadSearchRangeInfo() {
        LRUCache<Object,Object> lruCache = SpringUtil.getBean("scCache");
        ghmDataSectionPost = (Map<String, List<SearchRangeInfoDTO>>)lruCache.get(ScCacheUtil.GHM_DATA_SECTION_POST);
        List<SearchRangeInfoDTO> lSecPostList;
        List<SearchRangeInfoDTO> lPermDefsList;
        if (ghmDataSectionPost == null) {
            IMastDatapermissionService iMastDatapermissionService = SpringUtil.getBean("mastDatapermissionServiceImpl");
            lSecPostList = iMastDatapermissionService.selectDataSectionPost();
            lPermDefsList = iMastDatapermissionService.selectDataPermissionDefs();
            // 検索範囲情報を常駐変数に保存する
            ghmDataSectionPost = createSearchRangeInfo(lSecPostList,  true);
            ghmDataPermissionDefs = createSearchRangeInfo(lPermDefsList, false);
            lruCache.put(ScCacheUtil.GHM_DATA_SECTION_POST,ghmDataSectionPost);
            lruCache.put(ScCacheUtil.GHM_DATA_PERMISSION_DEFS,ghmDataPermissionDefs);
        }
//        log.info("【検索対象範囲条件取得（組織、役職）】:{}",ghmDataSectionPost);
//        log.info("【検索対象範囲条件取得（条件式）】:{}",ghmDataPermissionDefs);
    }

    /**
     * 検索対象範囲条件作成(必要条件)
     * @param   plDatasList 検索範囲定義情報
     * @param   pbFlg	   判定フラグ(true:組織・役職／false:条件式)
     * @return  HashMap <String, List <SearchRangeInfo>> 検索対象範囲条件
     */
    public Map<String, List<SearchRangeInfoDTO>>
    createSearchRangeInfo(List<SearchRangeInfoDTO> plDatasList, boolean pbFlg) {
        Map<String, List<SearchRangeInfoDTO>> hmDataList = MapUtil.newHashMap();
        List<SearchRangeInfoDTO>lsTempList = CollUtil.newArrayList();
        // 内部処理用カウンタ
        int nTempSeq = 0;
        // 内部処理用定義ID
        String sTempPerid = "";
        // 定義ID
        String sKey = "";
        // データがない場合は、処理をしない
        if (CollUtil.isEmpty(plDatasList)) {
            return hmDataList;
        }
        // 定義IDをキーとして、取得したデータ内容を保存する
        for (int i = 0; i < plDatasList.size(); i++) {
            SearchRangeInfoDTO searchRangeList = plDatasList.get(i);
            // 検索対象範囲条件定義マスタ(組織・役職／条件式)
            if (pbFlg) {
                sKey = searchRangeList.getMdspCpermissionid();
            } else {
                sKey = searchRangeList.getMdpdCpermissionid();
            }
            // １件目以外の場合
            if (i != 0) {
                // 定義IDが一致の場合
                if (StrUtil.equals(sKey,sTempPerid)) {
                    // 内部カウンタを増やす
                    nTempSeq++;
                } else {
                    // 定義IDをキーとし、検索範囲情報を保存する
                    hmDataList.put(sTempPerid, lsTempList);
                    // カウンタを0に戻す
                    nTempSeq = 0;
                    // 内部処理用リストをクリア
                    lsTempList.clear();
                }
            }
            // 対象データを内部処理用リストに追加
            lsTempList.add(nTempSeq, searchRangeList);
            // 比較用の定義IDを格納
            sTempPerid = sKey;
        }
        // 定義IDをキーとし、検索範囲情報を保存する
        hmDataList.put(sKey, lsTempList);
        // 値を返却する
        return hmDataList;
    }


}
