package jp.smartcompany.job.modules.core.util.searchrange;

import jp.smartcompany.job.modules.core.service.IMastDatapermissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  検索範囲情報常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
@Component
public class SearchRangeInfoCache {

    /** 検索範囲情報DAOインターフェース */
    @Resource
    private IMastDatapermissionService iMastDatapermissionService;

    /** 検索範囲情報(組織、役職)MAP */
    private HashMap < String, List<SearchRangeInfo> > ghmDataSectionPost =
            new HashMap< String, List<SearchRangeInfo> >();

    /** 検索範囲情報(条件式)MAP */
    private HashMap < String, List<SearchRangeInfo>> ghmDataPermissionDefs =
            new HashMap < String, List<SearchRangeInfo> >();

    /**
     * コンストラクタ
     */
    private SearchRangeInfoCache() {
    }

    /**
     * 検索対象範囲条件取得（組織、役職）
     * @param   psPermissionid  キー(必要条件定義ID)
     * @return  検索範囲情報Bean
     */
    public List<SearchRangeInfo> getDataSectionPost(String psPermissionid) {
        List < SearchRangeInfo > searchRangeInfo = null;

        if (psPermissionid != null
                && ghmDataSectionPost.containsKey(psPermissionid)) {
            searchRangeInfo = ghmDataSectionPost.get(psPermissionid);
        }
        return searchRangeInfo;
    }

    /**
     * 検索対象範囲条件取得（条件式）
     * @param   psPermissionid  キー(必須条件定義ID)
     * @return  検索範囲情報Bean
     */
    public List<SearchRangeInfo> getDataPermissionDefs(String psPermissionid) {
        List < SearchRangeInfo > searchRangeInfo = null;

        if (psPermissionid != null
                && ghmDataPermissionDefs.containsKey(psPermissionid)) {
            searchRangeInfo = ghmDataPermissionDefs.get(psPermissionid);
        }
        return searchRangeInfo;
    }


    /**
     * 検索対象範囲条件作成
     */
    public void loadSearchRangeInfo() {

        List < SearchRangeInfo > lSecPostList = iMastDatapermissionService.selectDataSectionPost();
        List < SearchRangeInfo > lPermDefsList =iMastDatapermissionService.selectDataPermissionDefs();

        ghmDataSectionPost = new HashMap <String, List<SearchRangeInfo>>();
        ghmDataPermissionDefs = new HashMap <String, List<SearchRangeInfo>>();

        // 検索範囲情報を常駐変数に保存する
        ghmDataSectionPost =
                createSearchRangeInfo(lSecPostList,  true);
        ghmDataPermissionDefs =
                createSearchRangeInfo(lPermDefsList, false);

    }

    /**
     * 検索対象範囲条件作成(必要条件)
     *
     * @param   plDatasList 検索範囲定義情報
     * @param   pbFlg	   判定フラグ(true:組織・役職／false:条件式)
     * @return  HashMap < String, List < SearchRangeInfo > > 検索対象範囲条件
     */
    public HashMap < String, List < SearchRangeInfo >  >
    createSearchRangeInfo(List < SearchRangeInfo > plDatasList, boolean pbFlg) {
        HashMap < String, List < SearchRangeInfo > > hmDataList =
                new HashMap<>();

        List < SearchRangeInfo> lsTempList = new ArrayList< SearchRangeInfo >();

        int nTempSeq     = 0;       // 内部処理用カウンタ
        String sTempPerid = "";     // 内部処理用定義ID
        String sKey = "";           // 定義ID

        // データがない場合は、処理をしない
        if (plDatasList.size() < 1) {
            return hmDataList;
        }

        // 定義IDをキーとして、取得したデータ内容を保存する
        for (int i = 0; i < plDatasList.size(); i++) {

            SearchRangeInfo searchRangeList = plDatasList.get(i);

            // 検索対象範囲条件定義マスタ(組織・役職／条件式)
            if (pbFlg) {
                sKey = searchRangeList.getMdspCpermissionid();
            } else {
                sKey = searchRangeList.getMdpdCpermissionid();
            }

            // １件目以外の場合
            if (i != 0) {
                // 定義IDが一致の場合
                if (sKey.equals(sTempPerid)) {
                    // 内部カウンタを増やす
                    nTempSeq++;
                } else {
                    // 定義IDをキーとし、検索範囲情報を保存する
                    hmDataList.put(sTempPerid, lsTempList);

                    // カウンタを0に戻す
                    nTempSeq = 0;

                    // 内部処理用リストをクリア
                    lsTempList = new  ArrayList < SearchRangeInfo >();
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
