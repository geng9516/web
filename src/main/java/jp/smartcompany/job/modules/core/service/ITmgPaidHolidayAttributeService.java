package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayAttributeDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.empattrsetting.dto.EmpAttrSettingDto;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.AvgWorkTimeHistoryVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.AvgWorkTimeVo;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.TmgEmpVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * [勤怠]年次休暇付与属性テーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPaidHolidayAttributeService extends IService<TmgPaidHolidayAttributeDO> {

        /**
         * 年次休暇付与属性テーブルを検索し、週平均勤務時間を取得
         *
         * @param customerId    顧客コード
         * @param companyId     法人コード
         * @param employeeId    職員番号
         * @param yyyymmdd      基準日
         * @return int 週平均勤務時間
         */
        int selectAvgWorkTime(String customerId, String companyId, String employeeId
                , Date yyyymmdd);


        /**
         * 個人属性一覧_表示処理の一覧部分を取得するクエリを返します
         */
        List<TmgEmpVo> selectTmgEmp(EmpAttrSettingDto param,int start,int end);

        /**
         * 個人属性一覧_表示処理での件数を取得するクエリを返します。ページ数の計算に利用します
         * (職員一覧の検索条件と表示ページによる取得範囲制限以外の条件が同じ)
         */
        int selectTmgEmpCount(EmpAttrSettingDto param);

        /**
         * 平均勤務時間を取得するSQLを返す
         */
        AvgWorkTimeVo selectAvgWorkTime60(String customerId, String companyId, String employeeId
                , String  yyyymmdd);


        /**
         * 平均勤務時間のデフォルト値を取得するSQLを返す
         */
        AvgWorkTimeVo selectDefaultAvgWorkTime(String customerId, String companyId, String employeeId
                , String  yyyymmdd);

        /**
         * 平均勤務時間設定状況を取得するSQLを返す
         */
        List<AvgWorkTimeHistoryVo> selectAvgWorkTimeHistory(String customerId, String companyId, String employeeId);
        }
