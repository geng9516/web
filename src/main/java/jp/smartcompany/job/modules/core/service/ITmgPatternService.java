package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDetailRow;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがonの行は同 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPatternService extends IService<TmgPatternDO> {


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
        List<TmgPatternDetailRow> selectPatternDetail(String customerId, String companyId, String sectionId
                , String groupId, String patternId, Date statDate, String upperSectionId);


        /**
         * 日付変更時刻はルート組織から取得
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param yyyymmdd      基準日
         * @return int  日付変更時刻
         */
        int selectChangeTime(String customerId, String companyId,Date yyyymmdd);
        }
