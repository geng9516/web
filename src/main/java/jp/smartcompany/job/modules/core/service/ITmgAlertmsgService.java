package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAlertmsgDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.AlertVo;

import java.util.List;

/**
 * <p>
 * [勤怠]アラートメッセージ格納テーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgAlertmsgService extends IService<TmgAlertmsgDO> {

        /**
         * 集計時の問題(アラート)の表示内容を取得するクエリを返却します
         */
        List<AlertVo> selectAlert(String cust, String comp, String secid, String dyyyymm, String lang, int numStart, int numEnd);
        }
