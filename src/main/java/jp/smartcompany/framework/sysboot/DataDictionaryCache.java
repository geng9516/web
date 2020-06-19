package jp.smartcompany.framework.sysboot;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.sysboot.dto.MastDatadicSeclevelDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.core.service.IMastDatadictionaryService;
import jp.smartcompany.job.modules.core.util.PsConst;

import java.util.List;
import java.util.Map;

/**
 * データディクショナリ情報常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
public class DataDictionaryCache {


    /** 例外用メッセージ：データディクショナリマスタ */
    private static final String ERR_MAST_DATADICTIONARY = "MAST_DATADICTIONARY";

    /** 例外用メッセージ：データディクショナリ機密レベル */
    private static final String ERR_MAST_DATADIC_SECLEVEL = "MAST_DATADIC_SECLEVEL";

    /** データディクショナリ情報MAP */
    private final Map<String, MastDatadictionaryDO> ghmDataDictionary = MapUtil.newConcurrentHashMap();

    /** データディクショナリ機密レベル情報MAP */
    private final Map<String, MastDatadicSeclevelDTO> ghmDatadicSeclevel = MapUtil.newConcurrentHashMap();

    /**
     * データディクショナリ情報読込.
     */
    public void loadDataDictionary() {
        LRUCache<Object,Object> lruCache = SpringUtil.getBean("scCache");
        IMastDatadictionaryService iMastDatadictionaryService = SpringUtil.getBean("mastDatadictionaryServiceImpl");
        List<MastDatadictionaryDO> dictionaryList = iMastDatadictionaryService.selectAllDicts();
        if (CollUtil.isEmpty(dictionaryList)) {
            // 必須マスタデータ未登録例外
            throw new GlobalException(ERR_MAST_DATADICTIONARY);
        }
        for (MastDatadictionaryDO dataDictionary : dictionaryList) {
            // キー設定
            String key = dataDictionary.getMdCcustomerid() + "_" + dataDictionary.getMdCcolumnname();
            ghmDataDictionary.put(key, dataDictionary);
        }
        lruCache.put(ScCacheUtil.DATA_DICTIONARY,ghmDataDictionary);
    }

    /**
     * データディクショナリ機密レベル情報読込.
     */
    public  void loadDataDicSeclevel() {
        LRUCache<Object,Object> lruCache = SpringUtil.getBean("scCache");
        IMastDatadictionaryService iMastDatadictionaryService = SpringUtil.getBean("mastDatadictionaryServiceImpl");
        List <MastDatadicSeclevelDTO> dicSeclevelList = iMastDatadictionaryService.selectAllDataDicSecLevel();
        for (MastDatadicSeclevelDTO dataDicSeclevel : dicSeclevelList) {
            // キー設定
            String key = dataDicSeclevel.getMdslCsystemid() + "_" + dataDicSeclevel.getMdslCcolumnname();
            ghmDatadicSeclevel.put(key, dataDicSeclevel);
        }
        lruCache.put(ScCacheUtil.DATA_DIC_SEC_LEVEL,ghmDatadicSeclevel);
    }

    /**
     * データディクショナリ情報取得.
     * @param psSystem システムコード
     * @param psColumn カラムID
     * @return データディクショナリ機密レベル情報Bean
     */
    public MastDatadicSeclevelDTO getDatadicSeclevel(String psSystem, String psColumn) {
        MastDatadicSeclevelDTO datadicSeclevel = new MastDatadicSeclevelDTO();;
        MastDatadictionaryDO dataDictionary;

        if (psSystem == null || psColumn == null) {
            return null;
        }
        String psKeySL = psSystem + SysUtil.PS_DEFAULT_SEPARATOR + psColumn;
        String psKeyDD = PsConst.CUSTOMER_ID + SysUtil.PS_DEFAULT_SEPARATOR + psColumn;
        // データディクショナリ機密レベル情報が存在するとき
        if (psKeySL != null && ghmDatadicSeclevel.containsKey(psKeySL)) {
            datadicSeclevel = ghmDatadicSeclevel.get(psKeySL);
            // データディクショナリ機密レベル情報が存在しないときはデータディクショナリから
        } else {
            dataDictionary = ghmDataDictionary.get(psKeyDD);
            if (dataDictionary==null) {
                return null;
            }
            datadicSeclevel.setMdslCsystemid(psSystem);
            datadicSeclevel.setMdslCcolumnname(psColumn);
            datadicSeclevel.setMdslClevelid(dataDictionary.getMdClevelid());
        }
        return datadicSeclevel;
    }

    /**
     * データディクショナリ情報取得.
     * @param psKey キー名
     * @return データディクショナリ情報Bean
     */
    public MastDatadictionaryDO getDataDictionary(String psKey) {
        MastDatadictionaryDO dataDictionary = null;
        if (psKey != null && ghmDataDictionary.containsKey(psKey)) {
            dataDictionary = ghmDataDictionary.get(psKey);
        }
        return dataDictionary;
    }

    /**
     * データディクショナリ情報取得.
     * @param psKey1 キー名1
     * @param psKey2 キー名2
     * @return データディクショナリ情報Bean
     */
    public MastDatadictionaryDO getDataDictionary(String psKey1, String psKey2) {
        if (psKey1 == null || psKey2 == null) {
            return null;
        }
        return getDataDictionary(
                psKey1 + SysUtil.PS_DEFAULT_SEPARATOR + psKey2);
    }


}
