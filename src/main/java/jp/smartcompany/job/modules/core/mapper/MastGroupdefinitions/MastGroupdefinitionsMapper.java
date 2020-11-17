package jp.smartcompany.job.modules.core.mapper.MastGroupdefinitions;

import jp.smartcompany.job.modules.core.pojo.entity.MastGroupdefinitionsDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * グループ定義条件マスタ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface MastGroupdefinitionsMapper extends BaseMapper<MastGroupdefinitionsDO> {

        /**
         * 更新先データ取得処理
         *
         * @param startDate      開始日
         * @param customerId 顧客コード
         * @param systemId   システムコード
         * @param groupId    グループID
         * @return  グループ条件定義マスタ
         * @exception
         */
        List<MastGroupdefinitionsDO> selectGroupDefinitions(
                @Param("startDate") String startDate,
                @Param("customerId") String customerId,
                @Param("systemId") String systemId,
                @Param("groupId") String groupId);

}
