package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDateofempLogDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.TmgDateOfEmpLogVo;

import java.util.List;

/**
 * <p>
 * [勤怠]勤務開始日編集ログ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgDateofempLogService extends IService<TmgDateofempLogDO> {


        /**
         * 勤務開始日更新履歴取得用SQL取得メソッド
         */
        List<TmgDateOfEmpLogVo> selectTmgDateofempLog(String custId, String compId, String empId, String baseDate);
        }
