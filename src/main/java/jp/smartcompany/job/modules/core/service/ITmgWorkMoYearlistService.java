package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgWorkMoYearlistDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoYearListVo;

import java.util.List;

/**
 * <p>
 * [勤怠]月次集計データ作成・年度状況一覧ワークテーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgWorkMoYearlistService extends IService<TmgWorkMoYearlistDO> {


        /**
         * 一覧画面表示用のSELECT文を構築して返します
         *
         * @param cust 顧客区分
         * @param comp 法人区分
         * @param secid 部局
         * @param dyyyymm 該当年月（検索処理自体は、該当年月の年度＋前年度12月の13レコードを返す）
         * @param lang 言語区分
         * @return SQL文
         */
        List<TmgMoYearListVo>  selectMoYearList(String cust,
                                                String comp,
                                                String secid,
                                                String dyyyymm,
                                                String lang,
                                                String psBaseDate);

        /**
         * 確定可能かどうかをチェックする
         *
         */
        int  selectMoYearListColumn(String cust,
                                                String comp,
                                                String secid,
                                                String targetDate,
                                                String introDate,
                                                String lang,
                                                String psBaseDate);
        }
