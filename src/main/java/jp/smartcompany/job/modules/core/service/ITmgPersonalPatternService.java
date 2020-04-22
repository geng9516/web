package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPersonalPatternDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPersonalPatternRow;

import java.util.Date;

/**
 * <p>
 * [勤怠]個人別勤務パターン 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPersonalPatternService extends IService<TmgPersonalPatternDO> {

        /**
         * 個人別勤務パターンテーブルを検索し、週平均勤務時間を取得
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    社員番号
         * @param yyyymmdd      基準日
         * @return int 週平均勤務時間
         */
        int selectAvgWorkTime(String customerId, String companyId, String employeeId
                , Date yyyymmdd);



        /**
         * 契約情報勤務パターン取得処理
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    社員番号
         * @param yyyymmdd      基準日
         * @return TmgPersonalPatternRow 契約情報勤務パターン
         */
        TmgPersonalPatternRow selectPersonalPatternRow(String customerId, String companyId, String employeeId
                , Date yyyymmdd);


        }
