package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.framework.sysboot.dto.MastDatadicSeclevelDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * データディクショナリマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastDatadictionaryService extends IService<MastDatadictionaryDO> {

        List<MastDatadictionaryDO> selectAllDicts();

        List<MastDatadicSeclevelDTO> selectAllDataDicSecLevel();
}
