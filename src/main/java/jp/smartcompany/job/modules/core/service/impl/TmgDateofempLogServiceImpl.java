package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDateofempLogDO;
import jp.smartcompany.job.modules.core.mapper.TmgDateofempLogMapper;
import jp.smartcompany.job.modules.core.service.ITmgDateofempLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.TmgDateOfEmpLogVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * [勤怠]勤務開始日編集ログ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgDateofempLogServiceImpl extends ServiceImpl<TmgDateofempLogMapper, TmgDateofempLogDO> implements ITmgDateofempLogService {


        /**
         * 勤務開始日更新履歴取得用SQL取得メソッド
         */
        @Override
        public TmgDateOfEmpLogVo selectTmgDateofempLog(String custId, String compId, String empId, String baseDate){
                return baseMapper.selectTmgDateofempLog(custId,compId,empId,baseDate);
        }
        }
