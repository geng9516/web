package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNtfactionlogDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.NtfActionLogVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * [勤怠]申請ログ情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgNtfactionlogService extends IService<TmgNtfactionlogDO> {

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
        List<NtfActionLogVo> selectNtfActionLog(Date psDate, String psLanguage, String psCustId, String psCompCode, String psNtfNo);

        }
