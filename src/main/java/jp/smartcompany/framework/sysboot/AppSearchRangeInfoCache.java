package jp.smartcompany.framework.sysboot;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.framework.sysboot.dto.AppSearchRangeInfoDTO;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;

/**
 * アプリケーション別検索範囲常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
@Slf4j
public class AppSearchRangeInfoCache {

    /** 常駐変数用HashMap */
    private final Map<String, List<AppSearchRangeInfoDTO>> gAppSearchRangeInfo = MapUtil.newConcurrentHashMap();

    /**
     * 情報取得.
     * @param psKey1 キー名1
     * @return アプリケーション設定Bean
     */
    public List<AppSearchRangeInfoDTO> getAppSearchRangeInfoCache(String psKey1) {
        if (psKey1 == null) {
            return null;
        }
        return gAppSearchRangeInfo.get(psKey1);
    }

    public void loadAppSearchRangeInfo(){
        LRUCache<Object,Object> lruCache = SpringUtil.getBean("scCache");
        // 初期化
        StringBuilder sb = new StringBuilder();
        IMastSystemService iMastSystemService = SpringUtil.getBean("mastSystemServiceImpl");
        List <AppSearchRangeInfoDTO> lKeyList = iMastSystemService.selectSearchRangeInfo();
        for (AppSearchRangeInfoDTO appSearchRangeInfoEntity : lKeyList) {
            // 初期化
            sb.delete(0, sb.length());
            // next
            // 顧客
            sb.append(appSearchRangeInfoEntity.getHgpCcustomerid());
            sb.append("_");
            // システム
            sb.append(appSearchRangeInfoEntity.getHgpCsystemid());
            sb.append("_");
            // グループID
            sb.append(appSearchRangeInfoEntity.getHgpCgroupid());
            sb.append("_");
            // サイトID
            sb.append(appSearchRangeInfoEntity.getHgpCsiteid());
            if (appSearchRangeInfoEntity.getHgpCappid() != null) {
                sb.append("_");
                // アプリケーションID
                sb.append(appSearchRangeInfoEntity.getHgpCappid());
            }
            String key = sb.toString();
            List<AppSearchRangeInfoDTO> lAppSearchRangeInfoDtoList = gAppSearchRangeInfo.get(key);
            if (lAppSearchRangeInfoDtoList == null) {
                lAppSearchRangeInfoDtoList = CollUtil.newArrayList();
                gAppSearchRangeInfo.put(key, lAppSearchRangeInfoDtoList);
            }

            appSearchRangeInfoEntity.setHgpCcustomerid(null);
            appSearchRangeInfoEntity.setHgpCsystemid(null);
            appSearchRangeInfoEntity.setHgpCgroupid(null);
            appSearchRangeInfoEntity.setHgpCsiteid(null);
            appSearchRangeInfoEntity.setHgpCappid(null);

            lAppSearchRangeInfoDtoList.add(appSearchRangeInfoEntity);

        }
        lruCache.put(ScCacheUtil.APP_SEARCH_RANGE_INFO,gAppSearchRangeInfo);
//        log.info("【アプリケーション別検索範囲:{}】",gAppSearchRangeInfo);
    }
}
