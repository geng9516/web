package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgUpdsKintaiDO;
import jp.smartcompany.job.modules.core.mapper.TmgUpdsKintaiMapper;
import jp.smartcompany.job.modules.core.service.ITmgUpdsKintaiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]月次集計出力イメージ(upds連携用、過去データ退避 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgUpdsKintaiServiceImpl extends ServiceImpl<TmgUpdsKintaiMapper, TmgUpdsKintaiDO> implements ITmgUpdsKintaiService {



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
        @Override
        public Map<String,Object> selectMoUpds(List<String> columnList, String functionID, String sectionID,
                                        String dlTypeID, String date, String custID, String compID, String lang){
                return baseMapper.selectMoUpds(columnList,functionID,sectionID, dlTypeID, date, custID, compID, lang);
        }
        }
