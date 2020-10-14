package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyInfoDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.MonthlyInfoOtVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.YearlyInfoVo;

import java.util.List;
import jp.smartcompany.job.modules.tmg.permStatList.dto.ColNameDto;
import jp.smartcompany.job.modules.tmg.permStatList.vo.TmgMonthlyInfoVO;

/**
 * <p>
 * [勤怠]月単位日別情報                   tmg_dailyのビュー代わり。承認状況一覧、超過勤務指示 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgMonthlyInfoService extends IService<TmgMonthlyInfoDO> {

    /**
     * 勤怠データ件数
     *
     * @param empSql 対象者取得SQL
     * @param date   　対象月
     * @return
     */
    int buildSQLForSelectTmgMonthlyInfoCount(String empSql, String date);

    /**
     * 表示する職員氏名と、承認ステータス状態を取得する
     *
     * @param custId   顧客コード
     * @param compId   法人コード
     * @param baseDate 　対象年月
     * @param lang     　言語
     * @param today    　今日
     * @param empSql   　対象者取得ｓql
     * @param list     　承認ステータス状態項目
     */
    List<TmgMonthlyInfoVO> buildSQLForSelectTmgMonthlyInfo(String custId, String compId, String baseDate, String lang, String today, String empSql, List<ColNameDto> list);


        /**
         * [勤怠]月単位日別情報より、表示対象職員の超過勤務命令・実績時間を取得。
         * @param  custId      顧客ID
         * @param  compId      法人ID
         * @param  sectionId   組織ID
         * @param  sContentId  コンテンツID 超過勤務命令用:「TMG_CONTENTID|OTI」    超過勤務実績用：「TMG_CONTENTID|OTR」
         * @param  sBaseDate   基準日
         * @param  slanguage   言語区分
         * */
        List<MonthlyInfoOtVo> selectMonthlyInfoOtr(String custId, String compId, String sectionId,
                                                   String sContentId, String sBaseDate, String slanguage, String sql);




        /**
         * [勤怠]月単位日別情報より、表示対象職員の超過勤務命令・実績時間を取得。
         * @param  custId      顧客ID
         * @param  compId      法人ID
         * @param  sBaseDate   基準日
         * */
        String selectAftBefBaseDate(String custId, String compId,
                                                    String sBaseDate,String sql,int AftBef);



        /**
         * [勤怠]月単位日別情報より、該当年度の合計超過時間と月超過回数を取得
         * 引数には事前にescDBStringを使用しクエリにて使用出来る様にエスケープをかけて下さい。
         *
         * @param  custID      顧客ID
         * @param  compID      法人ID
         * @param  sContentId  コンテンツID 超過勤務命令用:「TMG_CONTENTID|OTI」    超過勤務実績用：「TMG_CONTENTID|OTR」
         * @param  sBaseDBDate 基準日(日付型変換)
         * @param  sLang       言語区分
         * @return SQL文
         */
        List<YearlyInfoVo> selectYearlyInfo(String custID, String compID,
                                            String sContentId, String sBaseDBDate, String toDay, String sLang, String sql);

    /**
     * 月次情報(ステータス)を更新する
     *
     * @param custId            顧客ID
     * @param compId            法人ID
     * @param empId             職員ID
     * @param yyyyMm            　対象月
     * @param loginUserCode     　更新者
     * @param modifierProgramId 　更新プログラムID
     * @return 件数
     */
    int buildSQLForUpdateTmgMonthly(String custId, String compId, String empId, String yyyyMm, String loginUserCode, String modifierProgramId);

}
