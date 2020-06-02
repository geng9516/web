package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyInfoDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.monthlyInfoOtVo;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.yearlyInfoVo;

import java.util.List;

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
         * [勤怠]月単位日別情報より、表示対象社員の超過勤務命令・実績時間を取得。
         * @param  custId      顧客ID
         * @param  compId      法人ID
         * @param  sectionId   組織ID
         * @param  sContentId  コンテンツID 超過勤務命令用:「TMG_CONTENTID|OTI」    超過勤務実績用：「TMG_CONTENTID|OTR」
         * @param  sBaseDate   基準日
         * @param  slanguage   言語区分
         * */
        List<monthlyInfoOtVo> selectMonthlyInfoOtr(String custId, String compId, String sectionId,
                                                   String sContentId, String sBaseDate, String slanguage, String sql);




        /**
         * [勤怠]月単位日別情報より、表示対象社員の超過勤務命令・実績時間を取得。
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
         * @param  sectionID   組織コード
         * @param  sContentId  コンテンツID 超過勤務命令用:「TMG_CONTENTID|OTI」    超過勤務実績用：「TMG_CONTENTID|OTR」
         * @param  sBaseDBDate 基準日(日付型変換)
         * @param  sLang       言語区分
         * @return SQL文
         */
        List<yearlyInfoVo> selectYearlyInfo(String custID, String compID, String sectionID,
                                            String sContentId, String sBaseDBDate,String toDay, String sLang,String sql);
        }
