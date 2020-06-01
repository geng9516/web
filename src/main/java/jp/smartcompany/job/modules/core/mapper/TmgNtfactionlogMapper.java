package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNtfactionlogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.ntfActionLogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]申請ログ情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgNtfactionlogMapper extends BaseMapper<TmgNtfactionlogDO> {



        /**
         * 申請ログを取得するクエリ文を生成します
         * @param map Map
         * @return Map
         */
        List<ntfActionLogVo> selectNtfActionLog(Map<String, Object> map);
        }
