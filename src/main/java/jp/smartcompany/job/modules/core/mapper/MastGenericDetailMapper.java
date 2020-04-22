package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 名称マスタ明細データ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGenericDetailMapper extends BaseMapper<MastGenericDetailDO> {


        /**
         * 非常勤年休ルールを取得
         *
         * @param map 検索条件
         * @return int 年休ルール
         */
        int selectNenkyuRuleH(Map<String, Object> map);

        /**
         * 非常勤年休ルールを取得
         *
         * @param map 検索条件
         * @return int 年休ルール
         */
        int selectNenkyuRuleH2(Map<String, Object> map);

        /**
         * 常勤年休ルールを取得
         *
         * @param map 検索条件
         * @return int 年休ルール
         */
        int selectNenkyuRuleT(Map<String, Object> map);

        /**
         * 常勤年休ルールを取得
         *
         * @param map 検索条件
         * @return int 年休ルール
         */
        int selectNenkyuRuleT2(Map<String, Object> map);

        /**
         * 名称マスタを取得
         *
         * @param map 検索条件
         * @return MastGenericDetailDO 名称マスタ
         */
        MastGenericDetailDO selectMastGenericDetailDO(Map<String, Object> map);

        /**
         * 2つの歴の引き算
         *
         * @param map 検索条件
         * @return TmgTermRow 除外期間
         */
        List<TmgTermRow> tmgFExcludeTerm(Map<String, Object> map);


        /**
         * ワークタイプのデフォルトパターンを検索
         */
        String selectWorkPattern(Map<String, Object> map);
        }
