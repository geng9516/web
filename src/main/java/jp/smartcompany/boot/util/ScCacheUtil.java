package jp.smartcompany.boot.util;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.map.MapUtil;
import jp.smartcompany.framework.sysboot.*;
import jp.smartcompany.framework.sysboot.dto.*;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor
public class ScCacheUtil {

    private final LRUCache<Object,Object> lruCache;

    public static final String APP_SEARCH_RANGE_INFO = "appSearchRangeInfo";
    public static final String TABLE_COMBINATION_TYPE_MAP = "tableCombinationTypeMap";
    public static final String GHM_DATA_SECTION_POST = "ghmDataSectionPost";
    public static final String GHM_DATA_PERMISSION_DEFS = "ghmDataPermissionDefs";
    public static final String SYSTEM_PROPERTY_MAP = "systemPropertyMap";
    public static final String APP_AUTH_JUDGMENT_CACHE = "appAuthJudgmentCache";
    public static final String PERMISSION_STRING = "permissionString";
    public static final String DATA_DICTIONARY = "dataDictionary";
    public static final String DATA_DIC_SEC_LEVEL = "dataDicSecLevel";

    /**
     * アプリケーション別検索範囲用
     * @param key
     * @return
     */
    public List<AppSearchRangeInfoDTO> getAppSearchRange(String key) {
        if (key ==null){
            return null;
        }
        Map<String, List<AppSearchRangeInfoDTO>> gAppSearchRangeInfo =
                (Map<String, List<AppSearchRangeInfoDTO>>)lruCache.get(APP_SEARCH_RANGE_INFO);
        if (MapUtil.isNotEmpty(gAppSearchRangeInfo)) {
            return gAppSearchRangeInfo.get(key);
        }
        AppSearchRangeInfoCache appSearchRangeInfoCache = new AppSearchRangeInfoCache();
        appSearchRangeInfoCache.loadAppSearchRangeInfo();
        return appSearchRangeInfoCache.getAppSearchRangeInfoCache(key);
    }

    /**
     * // テーブル結合条件情報取得処理
     * @param psTableID
     * @return
     */
    public TableCombinationTypeDTO  getTableCombinationType(String psTableID) {
        if (psTableID==null){
            return null;
        }
        Map<String, TableCombinationTypeDTO> tableCombinationTypeMap = (Map<String, TableCombinationTypeDTO>)lruCache.get(TABLE_COMBINATION_TYPE_MAP);
        if (MapUtil.isNotEmpty(tableCombinationTypeMap)){
            return tableCombinationTypeMap.get(psTableID);
        }
        TableCombinationTypeCache tableCombinationTypeCache = new TableCombinationTypeCache();
        tableCombinationTypeCache.loadTableCombinationType();
        return tableCombinationTypeCache.getTableCombinationType(psTableID);
    }

    /**
     * 検索対象範囲条件取得（組織、役職）
     * @param   psPermissionid  キー(必要条件定義ID)
     * @return  検索範囲情報Bean
     */
    public List<SearchRangeInfoDTO> getDataSectionPost(String psPermissionid) {
        if (psPermissionid==null){
            return null;
        }
        Map<String, List<SearchRangeInfoDTO>> ghmDataSectionPost= (Map<String, List<SearchRangeInfoDTO>>)lruCache.get(GHM_DATA_SECTION_POST);
        if (MapUtil.isNotEmpty(ghmDataSectionPost)){
            return ghmDataSectionPost.get(psPermissionid);
        }
        SearchRangeInfoCache searchRangeInfoCache = new SearchRangeInfoCache();
        searchRangeInfoCache.loadSearchRangeInfo();
        return searchRangeInfoCache.getDataSectionPost(psPermissionid);
    }

    /**
     * 検索対象範囲条件取得（条件式）
     * @param   psPermissionid  キー(必須条件定義ID)
     * @return  検索範囲情報Bean
     */
    public List<SearchRangeInfoDTO> getDataPermissionDefs(String psPermissionid) {
        Map<String, List<SearchRangeInfoDTO>> ghmDataPermissionDefs = (Map<String, List<SearchRangeInfoDTO>>)lruCache.get(GHM_DATA_PERMISSION_DEFS);
        if (MapUtil.isNotEmpty(ghmDataPermissionDefs)){
            return ghmDataPermissionDefs.get(psPermissionid);
        }
        SearchRangeInfoCache searchRangeInfoCache = new SearchRangeInfoCache();
        searchRangeInfoCache.loadSearchRangeInfo();
        return searchRangeInfoCache.getDataPermissionDefs(psPermissionid);
    }

    /**
     * システムプロパティ情報取得
     */
    public String getSystemProperty(String psKey) {
       SystemPropertyCache systemPropertyCache = new SystemPropertyCache();
       return systemPropertyCache.getSystemProperty(psKey);
    }

    /**
     * システムプロパティ情報更新.
     */
    public void setSystemProperty(String psKey, String psValue) {
        SystemPropertyCache systemPropertyCache = new SystemPropertyCache();
        systemPropertyCache.setSystemProperty(psKey,psValue);
    }

    /**
     * アプリケーション起動権限判定
     */
    public List<AppAuthJudgmentDTO> getAppAuthJudgmentCache(String psKey1) {
        Map<String, List<AppAuthJudgmentDTO>> appAuthJudgmentList = (Map<String, List<AppAuthJudgmentDTO>>)lruCache.get(APP_AUTH_JUDGMENT_CACHE);
        if (MapUtil.isNotEmpty(appAuthJudgmentList )){
            return appAuthJudgmentList.get(psKey1);
        }
        AppAuthJudgmentCache appAuthJudgmentCache = new AppAuthJudgmentCache();
        appAuthJudgmentCache.loadAppAuthJudgment();
        return appAuthJudgmentCache.getAppAuthJudgmentCache(psKey1);
    }

    public Map<String, SystemPropertyDTO> getAllSystemProperties() {
        SystemPropertyCache systemPropertyCache = new SystemPropertyCache();
        return systemPropertyCache.getAllSystemProperties();
    }

    public String getPermissionString(String psCust,String psComp,String psLang) {
        String sKey = psCust + "_" + psComp + "_" + psLang;
        Map<String, String> gPermissionStringMap = (Map<String, String> )lruCache.get(PERMISSION_STRING);
        if (MapUtil.isNotEmpty(gPermissionStringMap)){
            return gPermissionStringMap.get(sKey);
        }
        PsPermissionStringCache psPermissionStringCache = new PsPermissionStringCache();
        psPermissionStringCache.loadPermissionString();
        return psPermissionStringCache.getValue(sKey);
    }

    public MastDatadictionaryDO getDataDictionary(String psKey){
        Map<String, MastDatadictionaryDO> ghmDataDictionary = (Map<String, MastDatadictionaryDO>)lruCache.get(DATA_DICTIONARY);
        if (MapUtil.isNotEmpty(ghmDataDictionary)){
            return ghmDataDictionary.get(psKey);
        }
        DataDictionaryCache dataDictionaryCache = new DataDictionaryCache();
        dataDictionaryCache.loadDataDictionary();
        return dataDictionaryCache.getDataDictionary(psKey);
    }

    public MastDatadictionaryDO getDataDictionary(String psKey1, String psKey2) {
       return getDataDictionary(psKey1 + SysUtil.PS_DEFAULT_SEPARATOR + psKey2);
    }

    /**
     * データディクショナリ情報取得.
     * @param psSystem システムコード
     * @param psColumn カラムID
     * @return データディクショナリ機密レベル情報Bean
     */
    public MastDatadicSeclevelDTO getDatadicSeclevel(String psSystem, String psColumn) {
        DataDictionaryCache dataDictionaryCache = new DataDictionaryCache();
        dataDictionaryCache.loadDataDictionary();
        dataDictionaryCache.loadDataDicSeclevel();
        return dataDictionaryCache.getDatadicSeclevel(psSystem,psColumn);
    }

}
