package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPersonalPatternDO;
import jp.smartcompany.job.modules.core.mapper.TmgPersonalPatternMapper;
import jp.smartcompany.job.modules.core.service.ITmgPersonalPatternService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPersonalPatternRow;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * [勤怠]個人別勤務パターン 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgPersonalPatternServiceImpl extends ServiceImpl<TmgPersonalPatternMapper, TmgPersonalPatternDO> implements ITmgPersonalPatternService {

        /**
         * 個人別勤務パターンテーブルを検索し、週平均勤務時間を取得
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    社員番号
         * @param yyyymmdd      基準日
         * @return int 週平均勤務時間
         */
        @Override
        public int selectAvgWorkTime(String customerId, String companyId, String employeeId
                , Date yyyymmdd) {

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("yyyymmdd", yyyymmdd);
                map.put("employeeId", employeeId);

                // 週平均勤務時間を取得
                Integer workTime = baseMapper.selectAvgWorkTime(map);

                return workTime;
        }

        /**
         * 契約情報勤務パターン取得処理
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    社員番号
         * @param yyyymmdd      基準日
         * @return TmgPersonalPatternRow 契約情報勤務パターン
         */
        @Override
        public TmgPersonalPatternRow selectPersonalPatternRow(String customerId, String companyId, String employeeId
                , Date yyyymmdd) {

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("yyyymmdd", yyyymmdd);
                map.put("employeeId", employeeId);

                // 週平均勤務時間を取得
                TmgPersonalPatternRow tppr = baseMapper.selectPersonalPatternRow(map);

                return tppr;
        }
        }
