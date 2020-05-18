package jp.smartcompany.job.modules.core.util;

public class TmgUtil {

    /** 組織ツリー検索タブで使用している検索項目・検索条件 */
    public static final String Cs_TREE_VIEW_ITEMS_KANANAME = "KANANAME";               // カナ氏名
    public static final String Cs_TREE_VIEW_ITEMS_KANJINAME = "KANJIMAME";             // 漢字氏名
    public static final String Cs_TREE_VIEW_ITEMS_EMPLOYEEID = "EMPLOYEEID";           // 職員番号
    public static final String Cs_TREE_VIEW_CONDITION_BROADMATCH = "BROADMATCH";       // 部分一致
    public static final String Cs_TREE_VIEW_CONDITION_PREFIXSEARCH = "PREFIXSEARCH";   // 前方一致
    public static final String Cs_TREE_VIEW_CONDITION_BACKWARDMATCH = "BACKWARDMATCH"; // 後方一致

    public static final String Cs_TmgDispLimit4TreeDefault = "100";


    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>管理対象外</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MANAGEFLG_0 = "TMG_MANAGEFLG|0";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>??</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORKERTYPE_00 = "TMG_WORKERTYPE|00";

}
