package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternAppliesDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * [就業]勤務パターン適用情報(部署) 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPatternAppliesService extends IService<TmgPatternAppliesDO> {


        /**
         * パターン割付情報を検索
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    社員番号
         * @param yyyymmdd      基準日
         * @return int 週平均勤務時間
         */
        String selectPatternId(String customerId, String companyId, String employeeId
                , Date yyyymmdd);
        }
