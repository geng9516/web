package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 法人ツリーマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastCompanyService extends IService<MastCompanyDO> {

        List<MastCompanyDO> getCompanyInfo(String sCustid,String sLanguage,String sDate);
}
