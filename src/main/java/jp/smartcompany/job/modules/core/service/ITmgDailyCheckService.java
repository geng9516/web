package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyCheckDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.DailyCheckDto;

/**
 * <p>
 * [勤怠]エラーチェック用・日別情報             2007/02/23元テーブルのレイアウト変更に伴い修正 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgDailyCheckService extends IService<TmgDailyCheckDO> {

    /**
     * 日別情報チェックを追加する
     *
     * @param dailyCheckDto 　DailyCheckDto
     * @return 件数
     */
    int buildSQLForInsertDailyCheck(DailyCheckDto dailyCheckDto);

}
