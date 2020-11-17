package jp.smartcompany.job.modules.core.mapper.TmgNotificationCheck;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNotificationCheckDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [勤怠]エラーチェック用・申請情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgNotificationCheckMapper extends BaseMapper<TmgNotificationCheckDO> {


        /**
         * TMG_F_CHECK_NOTIFICATION
         */

        String tmgFCheckNotification(@Param("ntfNo") String ntfNo,
                                     @Param("custId") String custId,
                                     @Param("compId") String compId,
                                     @Param("siteId") String siteId);
        }
