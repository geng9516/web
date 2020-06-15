package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyInfoDO;
import jp.smartcompany.job.modules.core.mapper.TmgMonthlyInfoMapper;
import jp.smartcompany.job.modules.core.service.ITmgMonthlyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.MonthlyInfoOtVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.YearlyInfoVo;
import jp.smartcompany.job.modules.tmg.permStatList.dto.ColNameDto;
import jp.smartcompany.job.modules.tmg.permStatList.vo.TmgMonthlyInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Map;

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
     * 勤怠データ件数
     *
     * @param empSql 対象者取得SQL
     * @param date   　対象月
     * @return
     */
    @Override
    public int buildSQLForSelectTmgMonthlyInfoCount(String empSql, String date) {
        Map<String, Object> map = MapUtil.newHashMap(2);
        map.put("empSql", empSql);
        map.put("date", date);

        return baseMapper.buildSQLForSelectTmgMonthlyInfoCount(map);
    }


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
    @Override
    public List<TmgMonthlyInfoVO> buildSQLForSelectTmgMonthlyInfo(String custId, String compId, String baseDate, String lang, String today, String empSql, List<ColNameDto> list){

        Map<String, Object> map = MapUtil.newHashMap(7);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("baseDate", baseDate);
        map.put("lang", lang);
        map.put("today", today);
        map.put("empSql", empSql);
        map.put("list", list);
        return baseMapper.buildSQLForSelectTmgMonthlyInfo(map);
    }


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
        public List<MonthlyInfoOtVo> selectMonthlyInfoOtr(String custId, String compId, String sectionId,
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
         * @param  sContentId  コンテンツID 超過勤務命令用:「TMG_CONTENTID|OTI」    超過勤務実績用：「TMG_CONTENTID|OTR」
         * @param  sBaseDate   基準日(月初日)
         * @param  sLang       言語区分
         * @return SQL文
         */
        @Override
        public List<YearlyInfoVo> selectYearlyInfo(String custID, String compID,
                                                   String sContentId, String sBaseDate, String toDay, String sLang, String sql){
                return baseMapper.selectYearlyInfo( custID,  compID,
                         sContentId,  sBaseDate,toDay,  sLang, sql);
        }

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
    @Override
    public int buildSQLForUpdateTmgMonthly(String custId, String compId, String empId, String yyyyMm, String loginUserCode, String modifierProgramId) {

        Map<String, Object> map = MapUtil.newHashMap(6);
        map.put("custId", custId);
        map.put("compId", compId);
        map.put("empId", empId);
        map.put("yyyyMm", yyyyMm);
        map.put("loginUserCode", loginUserCode);
        map.put("modifierProgramId", modifierProgramId);

        return baseMapper.buildSQLForUpdateTmgMonthly(map);
    }
}
