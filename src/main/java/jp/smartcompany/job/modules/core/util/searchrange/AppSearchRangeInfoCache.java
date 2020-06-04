package jp.smartcompany.job.modules.core.util.searchrange;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * アプリケーション別検索範囲常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
@Component
public class AppSearchRangeInfoCache {

    @Resource
    private IMastSystemService iMastSystemService;

    /** 常駐変数用HashMap */
    private HashMap< String, List<AppSearchRangeInfoEntity> > gAppSearchRangeInfo;

    /**
     * 情報取得.
     * @param psKey1 キー名1
     * @return アプリケーション設定Bean
     */
    public List<AppSearchRangeInfoEntity> getAppSearchRangeInfoCache(String psKey1) {

        if (psKey1 == null) {
            return null;
        }
        return gAppSearchRangeInfo.get(psKey1);
    }

    public void loadAppSearchRangeInfo (){

        // 初期化
        StringBuilder sb = new StringBuilder();
        gAppSearchRangeInfo = new HashMap <>();
        List < AppSearchRangeInfoEntity > lKeyList = iMastSystemService.selectSearchRangeInfo();

        for (AppSearchRangeInfoEntity appSearchRangeInfoEntity : lKeyList) {
            // 初期化
            sb.delete(0, sb.length());
            // next
            AppSearchRangeInfoEntity appSearchRangeInfoDto = appSearchRangeInfoEntity;
            // 顧客
            sb.append(appSearchRangeInfoDto.getHgpCcustomerid());
            sb.append("_");
            // システム
            sb.append(appSearchRangeInfoDto.getHgpCsystemid());
            sb.append("_");
            // グループID
            sb.append(appSearchRangeInfoDto.getHgpCgroupid());
            sb.append("_");
            // サイトID
            sb.append(appSearchRangeInfoDto.getHgpCsiteid());
            if (appSearchRangeInfoDto.getHgpCappid() != null) {
                sb.append("_");
                // アプリケーションID
                sb.append(appSearchRangeInfoDto.getHgpCappid());
            }

            String key = sb.toString();

            List<AppSearchRangeInfoEntity> lAppSearchRangeInfoDtoList = gAppSearchRangeInfo.get(key);
            if (lAppSearchRangeInfoDtoList == null) {
                lAppSearchRangeInfoDtoList = CollUtil.newArrayList();
                gAppSearchRangeInfo.put(key, lAppSearchRangeInfoDtoList);
            }

            appSearchRangeInfoDto.setHgpCcustomerid(null);
            appSearchRangeInfoDto.setHgpCsystemid(null);
            appSearchRangeInfoDto.setHgpCgroupid(null);
            appSearchRangeInfoDto.setHgpCsiteid(null);
            appSearchRangeInfoDto.setHgpCappid(null);

            lAppSearchRangeInfoDtoList.add(appSearchRangeInfoDto);

        }

    }
}
