package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.MastGroupdefinitionsDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ定義条件マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastGroupdefinitionsService extends IService<MastGroupdefinitionsDO> {

        /**
         * 更新先データ取得処理
         *
         * @param startDate      開始日
         * @param customerId 顧客コード
         * @param systemId   システムコード
         * @param groupId    グループID
         * @return  グループ条件定義マスタ
         */
        List<MastGroupdefinitionsDO> selectGroupDefinitions(
                Date startDate,
                String customerId,
                String systemId,
                String groupId
        );

}
