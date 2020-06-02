package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyInfoDO;
import jp.smartcompany.job.modules.core.mapper.TmgMonthlyInfoMapper;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.monthlyInfoOtVo;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.yearlyInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [勤怠]月単位日別情報                   tmg_dailyのビュー代わり。承認状況一覧、超過勤務指示 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgMonthlyInfoServiceImpl extends ServiceImpl<TmgMonthlyInfoMapper, TmgMonthlyInfoDO> implements ITmgMonthlyInfoService {

        /**
         * [勤怠]月単位日別情報より、表示対象社員の超過勤務命令・実績時間を取得。
         * @param  custId      顧客ID
         * @param  compId      法人ID
         * @param  sectionId   組織ID
         * @param  sContentId  コンテンツID 超過勤務命令用:「TMG_CONTENTID|OTI」    超過勤務実績用：「TMG_CONTENTID|OTR」
         * @param  sBaseDate   基準日
         * @param  slanguage   言語区分
         * */
        @Override
        public List<monthlyInfoOtVo> selectMonthlyInfoOtr(String custId, String compId, String sectionId,
                                                          String sContentId, String sBaseDate, String slanguage, String sql){
                return baseMapper.selectMonthlyInfoOtr( custId,  compId,  sectionId,
                         sContentId,  sBaseDate,  slanguage, sql);

        }


        /**
         * [勤怠]月単位日別情報より、表示対象社員の超過勤務命令・実績時間を取得。
         * @param  custId      顧客ID
         * @param  compId      法人ID
         * @param  sBaseDate   基準日
         * */
        @Override
        public String selectAftBefBaseDate(String custId, String compId,
                                    String sBaseDate,String sql,int AftBef){
                return baseMapper.selectAftBefBaseDate( custId,  compId,
                           sBaseDate, sql, AftBef);
        }



        /**
         * [勤怠]月単位日別情報より、該当年度の合計超過時間と月超過回数を取得
         * 引数には事前にescDBStringを使用しクエリにて使用出来る様にエスケープをかけて下さい。
         *
         * @param  custID      顧客ID
         * @param  compID      法人ID
         * @param  sectionID   組織コード
         * @param  sContentId  コンテンツID 超過勤務命令用:「TMG_CONTENTID|OTI」    超過勤務実績用：「TMG_CONTENTID|OTR」
         * @param  sBaseDate   基準日(月初日)
         * @param  sLang       言語区分
         * @return SQL文
         */
        @Override
        public List<yearlyInfoVo> selectYearlyInfo(String custID, String compID, String sectionID,
                                                   String sContentId, String sBaseDate, String toDay,String sLang,String sql){
                return baseMapper.selectYearlyInfo( custID,  compID,  sectionID,
                         sContentId,  sBaseDate,toDay,  sLang, sql);
        }


        }
