package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyInfoDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.permStatList.dto.ColNameDto;
import jp.smartcompany.job.modules.tmg.permStatList.vo.TmgMonthlyInfoVO;

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

}
