package jp.smartcompany.boot.event;

import jp.smartcompany.framework.sysboot.SearchRangeInfoCache;
import jp.smartcompany.framework.sysboot.SystemPropertyCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppStartEvent implements ApplicationRunner {

    /**
     * 系统启动后触发事件
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        loadGlobalData();
    }

    private void loadGlobalData() {
        // システムプロパティ情報取得処理
        SystemPropertyCache systemPropertyCache = new SystemPropertyCache();
        systemPropertyCache.loadSystemProperty();
        // 検索範囲情報取得処理
        SearchRangeInfoCache searchRangeInfoCache = new SearchRangeInfoCache();
        searchRangeInfoCache.loadSearchRangeInfo();
    }

}
