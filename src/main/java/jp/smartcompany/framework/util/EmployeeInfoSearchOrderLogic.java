package jp.smartcompany.framework.util;

import cn.hutool.core.map.MapUtil;
import java.util.Map;

public class EmployeeInfoSearchOrderLogic {

    /** ORDER句Map デフォルトキー  */
    public static final String DEFAULT_ORDER_KEY = "empno";

    /** ORDERBY句マップ */
    private static Map<String, String> omOrderBy;
    /** SortPattern */
    private final static String[] sortPattern = {"empno","kana","boss_post_empno","boss_post_kana","post_empno","post_kana","date","date_empno","postcode_kana","ifstillemp_boss_post_empno"};

    public static final String[] ORDER_VALUE = {
            " ORDER BY ME_CIFSTILLEMPLOYEDID,ME_CEMPLOYEEID_CK",
            " ORDER BY ME_CIFSTILLEMPLOYEDID,ME_CKANANAME",
            " ORDER BY ME_CIFSTILLEMPLOYEDID,HD_CBOSSORNOT DESC, MAP_NWEIGHTAGE, ME_CEMPLOYEEID_CK",
            " ORDER BY ME_CIFSTILLEMPLOYEDID,HD_CBOSSORNOT DESC, MAP_NWEIGHTAGE, ME_CKANANAME",
            " ORDER BY ME_CIFSTILLEMPLOYEDID,MAP_NWEIGHTAGE, ME_CEMPLOYEEID_CK",
            " ORDER BY ME_CIFSTILLEMPLOYEDID,MAP_NWEIGHTAGE, ME_CKANANAME",
            " ORDER BY ME_CIFSTILLEMPLOYEDID,MAP_NWEIGHTAGE, HD_DSTARTDATE_CK",
            " ORDER BY ME_CIFSTILLEMPLOYEDID,HD_DSTARTDATE_CK, ME_CEMPLOYEEID_CK",
            " ORDER BY ME_CIFSTILLEMPLOYEDID,HD_CPOSTID_FK, ME_CKANANAME",
            " ORDER BY ME_CIFSTILLEMPLOYEDID, HD_CBOSSORNOT DESC, MAP_NWEIGHTAGE, ME_CEMPLOYEEID_CK"};



    public static final String[] ORDER_KEY = {
            DEFAULT_ORDER_KEY,
            "kana",
            "boss_post_empno",
            "boss_post_kana",
            "post_empno",
            "post_kana",
            "date",
            "date_empno",
            "postcode_kana",
            "ifstillemp_boss_post_empno"};
    /**
     * 引数に指定されたソートパターンキーよりORDER　BY 句を返却
     * @param psSortKey ソートパターンキー
     * @return ORDER　BY 句
     */
    public static String orderBySelect(String psSortKey) {
        omOrderBy = MapUtil.newHashMap();
        // ORDERBY句マップ生成
        for (int i = 0; i < ORDER_KEY.length; i++) {
           omOrderBy.put(
                    ORDER_KEY[i], ORDER_VALUE[i]);
        }
        int count = sortPattern.length;
        boolean existPattern = false;

        if (psSortKey == null || psSortKey.length() <= 0) {
            // キーが取得できない場合、デフォルトキーを使用
            psSortKey = DEFAULT_ORDER_KEY;
        }else{
            for (String s : sortPattern) {
                if (s.equals(psSortKey.toLowerCase())) {
                    existPattern = true;
                    break;
                }
            }
            if (!existPattern) {
                // キーの値が想定外の場合、デフォルトキーを使用
                psSortKey = DEFAULT_ORDER_KEY;
            }
        }
        return omOrderBy.get(psSortKey.toLowerCase());
    }
}
