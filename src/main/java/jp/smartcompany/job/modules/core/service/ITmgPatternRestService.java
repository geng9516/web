package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternRestDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDetailRow;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 勤務パターン休憩情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPatternRestService extends IService<TmgPatternRestDO> {


        /**
         * パターン休憩情報
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param sectionId    部局コード
         * @param groupId      グループコード
         * @param patternId    勤務パターンID
         * @param statDate      基準日
         * @param upperSectionId    上位組織
         * @return List<TmgPatternRestDO> パターン休憩情報
         */
        List<TmgPatternRestDO> selectPatternRestTime(String customerId, String companyId, String sectionId
                , String groupId, String patternId, Date statDate, String upperSectionId);
        }
