package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgTermRow;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.dateDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.mgdNtfPropVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.mgdNtfTypeDispAppVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.mgdTmgNtfTypeVo;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgDispItemsDto;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.GenericDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdAttributeVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdCsparechar4VO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 名称マスタ明細データ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGenericDetailMapper extends BaseMapper<MastGenericDetailDO> {


        /**
         * 非常勤年休ルールを取得
         *
         * @param map 検索条件
         * @return int 年休ルール
         */
        int selectNenkyuRuleH(Map<String, Object> map);

        /**
         * 非常勤年休ルールを取得
         *
         * @param map 検索条件
         * @return int 年休ルール
         */
        int selectNenkyuRuleH2(Map<String, Object> map);

        /**
         * 常勤年休ルールを取得
         *
         * @param map 検索条件
         * @return int 年休ルール
         */
        int selectNenkyuRuleT(Map<String, Object> map);

        /**
         * 常勤年休ルールを取得
         *
         * @param map 検索条件
         * @return int 年休ルール
         */
        int selectNenkyuRuleT2(Map<String, Object> map);

        /**
         * 名称マスタを取得
         *
         * @param map 検索条件
         * @return MastGenericDetailDO 名称マスタ
         */
        MastGenericDetailDO selectMastGenericDetailDO(Map<String, Object> map);

        /**
         * 2つの歴の引き算
         *
         * @param map 検索条件
         * @return TmgTermRow 除外期間
         */
        List<TmgTermRow> tmgFExcludeTerm(Map<String, Object> map);


        /**
         * ワークタイプのデフォルトパターンを検索
         */
        String selectWorkPattern(Map<String, Object> map);

        /**
         * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
         */
        List<TmgDispItemsDto> selectDispMonthlyItems(Map<String, Object> map);

        /**
         * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
         */
        List<TmgDispItemsDto> selectDispDailyItems(Map<String, Object> map);


        /**
         * 勤怠/名称マスタ]就業登録/承認・月次情報表示項目
         */
        List<mgdNtfTypeDispAppVo> selectMasterTmgNtfTypeDispAppList(Map<String, Object> map);

        /**
         * mast_generic_detail 内容取り
         */
        List<Map<String,Object>> selectGenericDetail(@Param("sql")String sql);


        /**
         * 指定された申請種類の表示タイプを取得する
         */
        int selectNtfDispType(Map<String, Object> map);

        /**
         * 「HH:MI60」形式の文字列を分(Minute)情報に変換
         */
        Long tmgFConvHhmi2Min (Map<String, Object> map);


        /**
         * 処理用の型に変換
         * 引数は月曜～日曜を0または1で渡す
         */
        Long toDayofWeek (@Param("sql")String sql);




        /**
         * 決裁レベル取得SQL返却メソッド
         */
        String selectApprovelLevel(Map<String, Object> map);



    /**
     * 月次情報表示項目を検索
     */
    List<ItemVO> buildSQLForSelectTmgDispMonthlyItems(Map<String, Object> map);


    /**
     * 日次情報表示項目を検索
     */
    List<ItemVO> buildSQLForSelectTmgDispDailyItems(Map<String, Object> map);

    /**
     * 名称マスタから属性コードを取得
     */
    List<MgdAttributeVO> buildSQLForSelectgetMgdAttribute(Map<String, Object> map);

    /**
     * 名称マスタから属性コードを取得(エフォート対象者判定用)
     */
    List<MgdAttributeVO> buildSQLForSelectgetMgdAttributeEffort(Map<String, Object> map);

    /**
     * 予備項目4を取得「0:出勤日,それ以外は出勤日ではない」
     */
    List<MgdCsparechar4VO> buildSQLSelectGetMgdCsparechar4(Map<String, Object> map);

    /**
     * 就業区分マスタを取得する
     */
    List<GenericDetailVO> buildSQLForSelectGenericDetail(Map<String, Object> map);

    /**
     * 各コメント欄の最大値を名称マスタ詳細より取得
     */
    String buildSQLForSelectTmgVMgdMaxLengthCheck(Map<String, Object> map);


    /**
     * 名称マスタから属性コードを取得
     */
    List<GenericDetailVO> buildSQLForSelectgetMgdDescriptions(Map<String, Object> map);

    /**
     * 申請画面用 申請区分マスタ取得SQL
     */
    List<mgdTmgNtfTypeVo> selectMasterTmgNtfType(Map<String, Object> map);

    /**
     * 画面項目名称の設定マスタ
     */
    mgdNtfPropVo selectMasterNtfProp(Map<String, Object> map);

    /**
     * 現在日付を取得するクエリ文を生成します
     */
    String selectSysdate();

    /**
     * 年度を取得する
     */
    int selectYear(@Param("custId")String custId,
                   @Param("compId")String compId);


    dateDto selectDate(@Param("custId")String custId,
                       @Param("compId")String compId,
                       @Param("year")int year,
                       @Param("baseDate")String baseDate);
}
