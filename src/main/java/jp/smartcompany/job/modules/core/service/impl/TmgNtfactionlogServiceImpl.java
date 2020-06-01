package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgNtfactionlogDO;
import jp.smartcompany.job.modules.core.mapper.TmgNtfactionlogMapper;
import jp.smartcompany.job.modules.core.service.ITmgNtfactionlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.ntfActionLogVo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]申請ログ情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgNtfactionlogServiceImpl extends ServiceImpl<TmgNtfactionlogMapper, TmgNtfactionlogDO> implements ITmgNtfactionlogService {
        /**
         * 申請ログを取得するクエリ文を生成します
         *
         * @param  psDate     基準日
         * @param  psLanguage 言語区分
         * @param  psCustId   顧客コード
         * @param  psCompCode 法人コード
         * @param  psNtfNo    申請番号
         * @return List<ntfActionLogVo>
         */
        @Override
        public List<ntfActionLogVo> selectNtfActionLog(Date psDate, String psLanguage, String psCustId, String psCompCode, String psNtfNo){
                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("psDate", psDate);
                map.put("psLanguage", psLanguage);
                map.put("psCustId", psCustId);
                map.put("psCompCode", psCompCode);
                map.put("psNtfNo", psNtfNo);
                //
                List<ntfActionLogVo> ntfActionLogVoList = baseMapper.selectNtfActionLog(map);

                return ntfActionLogVoList;
        }
        }
