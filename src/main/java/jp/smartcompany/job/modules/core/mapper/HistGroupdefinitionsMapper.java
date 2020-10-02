package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdefinitionsDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * グループ定義条件式データ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface HistGroupdefinitionsMapper extends BaseMapper<HistGroupdefinitionsDO> {

        /**
         * グループ条件定義マスタ(条件式)の各定義情報を取得
         *
         * @author  Xiao Wenpeng
         * @param   customerId    顧客コード
         * @param   companyId     法人コード
         * @param   systemId      システムID
         * @param   groupId       グループID
         * @param   searchDate    今回改定日
         * @param   seq           データ格納番号
         * @return  List<QueryConditionRowDTO>  定義情報リスト
         * @exception
         */
        List<QueryConditionRowDTO> selectGroupDefinitions(
                @Param("customerId") String customerId,
                @Param("companyId") String companyId,
                @Param("systemId") String systemId,
                @Param("groupId") String groupId,
                @Param("searchDate") String searchDate,
                @Param("seq") Long seq);

        List<HistGroupdefinitionsDO> selectHistGroupDefinitions(
                @Param("searchDate") String searchDate,
                @Param("custId") String custId,
                @Param("systemId") String systemId,
                @Param("groupId") String groupId);

}
