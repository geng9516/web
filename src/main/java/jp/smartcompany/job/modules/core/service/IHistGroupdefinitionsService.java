package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;
import jp.smartcompany.job.modules.core.pojo.entity.HistGroupdefinitionsDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ定義条件式データ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IHistGroupdefinitionsService extends IService<HistGroupdefinitionsDO> {

    /**
     * グループ条件定義マスタ(条件式)の各定義情報を取得
     *
     * @author  isolyamada
     * @param   customerId    顧客コード
     * @param   companyId     法人コード
     * @param   systemId      システムID
     * @param   groupId       グループID
     * @param   searchDate    今回改定日
     * @param   seq           データ格納番号
     * @return  List < QueryConditionRowDto >  定義情報リスト
     */
    List<QueryConditionRowDTO> selectGroupDefinitions(
            String customerId,
            String companyId,
            String systemId,
            String groupId,
            Date searchDate,
            Long seq
    );
}
