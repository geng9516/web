package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupAttributeDO;
import jp.smartcompany.job.modules.core.mapper.TmgGroupAttribute.TmgGroupAttributeMapper;
import jp.smartcompany.job.modules.core.service.ITmgGroupAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.HolidayTimeLimitDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.dto.OverTimeLimitDto;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.LimitOfBaseDate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * グループ属性テーブル 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgGroupAttributeServiceImpl extends ServiceImpl<TmgGroupAttributeMapper, TmgGroupAttributeDO> implements ITmgGroupAttributeService {


        /**
         * 基準日時点の超勤限度時間取得用クエリを返す
         * */
        @Override
        public List<LimitOfBaseDate> selectLimitOfBaseDate(String custId, String compId, String sectionId, String baseDate){
                return baseMapper.selectLimitOfBaseDate( custId,  compId,  sectionId,  baseDate);
        }


        /**
         * 閾値（超過勤務）の取得
         * */
        @Override
        public OverTimeLimitDto selectOverTimeLimit(String custId, String compId, String sectionId, String groupId){
                if(StrUtil.hasEmpty(sectionId)){
                        sectionId="null";
                }else{
                        sectionId="'"+sectionId+"'";
                }
                if(StrUtil.hasEmpty(groupId)){
                        groupId="null";
                }else{
                        groupId="'"+groupId+"'";
                }
                return baseMapper.selectOverTimeLimit( custId,  compId,  sectionId,  groupId);
        }


        /**
         * 閾値（休日出勤）の取得
         * */
        @Override
        public HolidayTimeLimitDto selectHolidayTimeLimit(String custId, String compId, String sectionId, String groupId){
                if(StrUtil.hasEmpty(sectionId)){
                        sectionId="null";
                }else{
                        sectionId="'"+sectionId+"'";
                }
                if(StrUtil.hasEmpty(groupId)){
                        groupId="null";
                }else{
                        groupId="'"+groupId+"'";
                }
                return baseMapper.selectHolidayTimeLimit( custId,  compId,  sectionId,  groupId);
        }

        }
