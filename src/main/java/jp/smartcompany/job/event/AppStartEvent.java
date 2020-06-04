package jp.smartcompany.job.event;

import jp.smartcompany.job.modules.core.util.searchrange.AppSearchRangeInfoCache;
import jp.smartcompany.job.modules.core.util.searchrange.SearchRangeInfoCache;
import jp.smartcompany.job.modules.core.util.searchrange.TableCombinationTypeCache;
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

    private final TableCombinationTypeCache tableCombinationTypeCache;
    private final AppSearchRangeInfoCache appSearchRangeInfoCache;
    private final SearchRangeInfoCache searchRangeInfoCache;

    /**
     * 系统启动后触发事件
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        appSearchRangeInfoCache.loadAppSearchRangeInfo();
        searchRangeInfoCache.loadSearchRangeInfo();
        tableCombinationTypeCache.loadTableCombinationType();
    }
}
