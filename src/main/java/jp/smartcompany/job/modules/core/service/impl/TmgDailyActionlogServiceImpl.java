package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyActionlogDO;
import jp.smartcompany.job.modules.core.mapper.TmgDailyActionlog.TmgDailyActionlogMapper;
import jp.smartcompany.job.modules.core.service.ITmgDailyActionlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyLogVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [勤怠]日別情報操作ログ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgDailyActionlogServiceImpl extends ServiceImpl<TmgDailyActionlogMapper, TmgDailyActionlogDO> implements ITmgDailyActionlogService {
    /**
     * 実績情報の変更ログ表示用表関数から値を取得する
     *
     * @param custID     顧客コード
     * @param compCode   　法人コード
     * @param targetUser 　対象者
     * @param day        　対象日
     * @param language   　言語
     * @return
     */
    @Override
    public List<DailyLogVO> buildSQLForSelectTmgSelectDailyActionLog(String custID, String compCode, String targetUser, String day, String language) {
        return baseMapper.buildSQLForSelectTmgSelectDailyActionLog(custID, compCode, targetUser, day, language);

    }

}
