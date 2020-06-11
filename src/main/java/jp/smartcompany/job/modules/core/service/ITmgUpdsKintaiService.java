package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgUpdsKintaiDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]月次集計出力イメージ(upds連携用、過去データ退避 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgUpdsKintaiService extends IService<TmgUpdsKintaiDO> {

        /**
         * CSVデータ取得用SQLを構築し返す
         * @param columnList：CSVレイアウトカラムリスト
         * @param functionID：ファンクション名
         * @param sectionID：部署コード
         * @param dlTypeID：DL種別コード
         * @param date：対象年月
         * @param custID：顧客コード
         * @param compID：法人コード
         * @param lang：言語区分
         * @return
         */
        Map<String,Object>  selectMoUpds(List<String> columnList, String functionID, String sectionID,
                                         String dlTypeID, String date, String custID, String compID, String lang);
        }
