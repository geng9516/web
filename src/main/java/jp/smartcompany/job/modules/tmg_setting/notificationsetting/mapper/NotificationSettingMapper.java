package jp.smartcompany.job.modules.tmg_setting.notificationsetting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.entity.TmgNtfCheckDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationSettingMapper extends BaseMapper<TmgNtfCheckDo> {

    //新规时查找check
    List<TmgNtfCheckDo> getNewCheckList(@Param("group") String group,
                                        @Param("typeGroup") String typeGroup,
                                        @Param("sysdate") String sysdate,
                                        @Param("timeType") String timeType);

    List<TmgNtfCheckDo> getCheckFunc(@Param("ntfType")String ntfType,
                                     @Param("sysdate")String sysdate);
}
