package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAcquired5daysholidayDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.Acquired5DaysListVO;

import java.util.List;

/**
 * <p>
 * 年5日時季指定取得情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgAcquired5daysholidayService extends IService<TmgAcquired5daysholidayDO> {

    /**
     * 一覧/編集画面検索用
     *
     * @param baseDate 基準日
     * @param empsql   　組織内職員取得sql
     * @param userCode 　対象sql
     * @return
     */
    List<Acquired5DaysListVO> buildSQLforList(String baseDate, String empsql, String userCode);

    /**
     *修正基準日チェック期間再計算
     * @param txtUserCode 対象者
     * @param kijunbi　基準日
     * @param txtYear　基準日修正年
     * @param kijunbiEdit　修正基準日
     * @param userCode　更新者
     * @param programId　更新プログラムID
     * @param custID　顧客コード
     * @param compCode　法人コード
     */
    void buildSQLTmgAcquired5daykikanbi(String txtUserCode, String kijunbi, String txtYear, String kijunbiEdit, String userCode, String programId, String custID, String compCode);
}
