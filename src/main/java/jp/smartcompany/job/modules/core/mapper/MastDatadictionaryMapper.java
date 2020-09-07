package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.framework.component.dto.QueryConditionSelectDTO;
import jp.smartcompany.framework.sysboot.dto.MastDatadicSeclevelDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * データディクショナリマスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastDatadictionaryMapper extends BaseMapper<MastDatadictionaryDO> {

        List<MastDatadictionaryDO> selectAllDicts();

        List<MastDatadicSeclevelDTO> selectAllDataDicSecLevel();

        List<QueryConditionRowDTO> selectGroupJoinQuery(@Param("customerId") String customerId,@Param("tableId") String tableId);

        /**
         * データディクショナリマスタより、登録されているテーブル情報を取得
         *
         * @author  isolyamada
         * @param   customerId    顧客コード
         * @param   language      言語区分
         * @param   mdCavlforckStart  自由条件検索の場合使用
         * @return  List < QueryConditionSelectDto >   テーブル情報リスト
         * @exception
         */
        List<QueryConditionSelectDTO> selectTableInfo(@Param("customerId") String customerId,
                                                      @Param("language") String language,
                                                      @Param("mdCavlforckStart") String mdCavlforckStart);
        /**
         * データディクショナリマスタより、登録されているカラム情報を取得
         *
         * @author  isolyamada
         * @param   customerId        顧客コード
         * @param   language          言語区分
         * @param   tableId           対象テーブル
         * @param   mdCavlforckStart  自由条件検索の場合使用
         */
        List<QueryConditionSelectDTO> selectColumnInfo(
                @Param("customerId") String customerId,
                @Param("language") String language,
                @Param("tableId") String tableId,
                @Param("mdCavlforckStart") String mdCavlforckStart
        );
}
