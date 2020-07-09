package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailCheckDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.DetailCheckDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * [勤怠]エラーチェック用・日別情報詳細           2007/02/23元テーブルのレイアウト変更に伴い修正 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgDailyDetailCheckMapper extends BaseMapper<TmgDailyDetailCheckDO> {

    /**
     * 日別詳細情報チェック(申請関係)を追加する
     */
    int buildSQLForInsertDetailCheckEtc(Map<String, Object> map);

    /**
     * 日別詳細情報チェック(画面非表示項目)を追加する
     */
    int buildSQLForInsertDetailCheckNotDisp(Map<String, Object> map);

    /**
     * 日別詳細情報チェックを追加する
     *
     * @param detailCheckDto DetailCheckDto
     * @return 件数
     */
    int buildSQLForInsertDetailCheck(DetailCheckDto detailCheckDto);
}

