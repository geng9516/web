package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternDO;
import jp.smartcompany.job.modules.core.mapper.TmgPatternMapper;
import jp.smartcompany.job.modules.core.service.ITmgPatternService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDetailRow;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがonの行は同 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgPatternServiceImpl extends ServiceImpl<TmgPatternMapper, TmgPatternDO> implements ITmgPatternService {


        /**
         * パターン情報
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param sectionId    部局コード
         * @param groupId      グループコード
         * @param patternId    勤務パターンID
         * @param statDate      基準日
         * @param upperSectionId    上位組織
         * @return List<TmgPatternDetailRow> パターン情報
         */
        @Override
        public List<TmgPatternDetailRow> selectPatternDetail(String customerId, String companyId, String sectionId
                , String groupId, String patternId, Date statDate, String upperSectionId){

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("sectionId", sectionId);
                map.put("groupId", groupId);
                map.put("patternId", patternId);
                map.put("statDate", statDate);
                map.put("upperSectionId", upperSectionId);

                // パターン情報を取得
                List<TmgPatternDetailRow> tpdrList= baseMapper.selectPatternDetail(map);

                return tpdrList;
        }


        /**
         * 日付変更時刻はルート組織から取得
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param yyyymmdd      基準日
         * @return int  日付変更時刻
         */
        @Override
        public int selectChangeTime(String customerId, String companyId,Date yyyymmdd){
                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("yyyymmdd", yyyymmdd);

                // パターン情報を取得
                int changeTime= baseMapper.selectChangeTime(map);

                return changeTime;
        }


        /**
         * 標準の勤務時間数取得用クエリを返す
         *
         * @param  customerId   顧客コード
         * @param  companyId 法人コード
         * @param  yyyymmdd 対象年月日
         * @return SQL文
         */
        @Override
        public int selectStandardWorkTime(String customerId, String companyId,String yyyymmdd){
                return baseMapper.selectStandardWorkTime(customerId,companyId,yyyymmdd);
        }
}
