package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
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

        List<HistDesignationDO> selectTargetCompany(String existsSql, Date searchDate);

        List<HistDesignationDO> selectAllCompany(String custId, Date searchDate);

        List<MastCompanyDO> selectCompanyList(String customerId,String language,Date date,
                                              List<String> companyList);

        String getCompanyName(String searchDate);
}
