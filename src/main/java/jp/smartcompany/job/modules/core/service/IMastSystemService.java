package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.framework.sysboot.dto.AppSearchRangeInfoDTO;
import jp.smartcompany.job.modules.core.pojo.dto.TableCombinationTypeDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * システムマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastSystemService extends IService<MastSystemDO> {

        /**
         * 根据语言获取当前使用的系统信息
         * @param language
         * @return String
         */
        List<MastSystemDO> getByLang(String language);

        List<TableCombinationTypeDTO> getTableInfo();

        List<AppSearchRangeInfoDTO> selectSearchRangeInfo();

}
