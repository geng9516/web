package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayAttributeDO;
import jp.smartcompany.job.modules.core.mapper.TmgPaidHolidayAttributeMapper;
import jp.smartcompany.job.modules.core.service.ITmgPaidHolidayAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.EmpAttrSettingDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.AvgWorkTimeHistoryVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.AvgWorkTimeVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.TmgEmpVo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]年次休暇付与属性テーブル 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgPaidHolidayAttributeServiceImpl extends ServiceImpl<TmgPaidHolidayAttributeMapper, TmgPaidHolidayAttributeDO> implements ITmgPaidHolidayAttributeService {


        /**
         * 年次休暇付与属性テーブルを検索し、週平均勤務時間を取得
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    職員番号
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
         * 個人属性一覧_表示処理の一覧部分を取得するクエリを返します
         */
        @Override
        public List<TmgEmpVo> selectTmgEmp(EmpAttrSettingDto param,int start,int end){
                return baseMapper.selectTmgEmp(param,start,end);
        }

        /**
         * 個人属性一覧_表示処理での件数を取得するクエリを返します。ページ数の計算に利用します
         * (職員一覧の検索条件と表示ページによる取得範囲制限以外の条件が同じ)
         */
        @Override
        public int selectTmgEmpCount(EmpAttrSettingDto param){
                return baseMapper.selectTmgEmpCount(param);
        }


        /**
         * 平均勤務時間を取得するSQLを返す
         */
        @Override
        public AvgWorkTimeVo selectAvgWorkTime60(String customerId, String companyId, String employeeId
                , String  yyyymmdd){
                return baseMapper.selectAvgWorkTime60(customerId, companyId, employeeId, yyyymmdd);
        }


        /**
         * 平均勤務時間のデフォルト値を取得するSQLを返す
         */
        @Override
        public AvgWorkTimeVo selectDefaultAvgWorkTime(String customerId, String companyId, String employeeId
                , String  yyyymmdd){
                return baseMapper.selectDefaultAvgWorkTime(customerId, companyId, employeeId, yyyymmdd);
        }

        /**
         * 平均勤務時間設定状況を取得するSQLを返す
         */
        @Override
        public List<AvgWorkTimeHistoryVo> selectAvgWorkTimeHistory(String customerId, String companyId, String employeeId){
                return baseMapper.selectAvgWorkTimeHistory(customerId, companyId, employeeId);
        }
}
