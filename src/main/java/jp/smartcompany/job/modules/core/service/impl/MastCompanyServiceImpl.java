package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.util.SysDateUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.mapper.MastCompany.MastCompanyMapper;
import jp.smartcompany.job.modules.core.service.IMastCompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 法人ツリーマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastCompanyServiceImpl extends ServiceImpl<MastCompanyMapper, MastCompanyDO> implements IMastCompanyService {

        @Override
        public List<MastCompanyDO> getCompanyInfo(String sCustid, String sLanguage, String sDate) {
                return baseMapper.selectCompanyInfo(sCustid,sLanguage,sDate);
        }

        @Override
        public  List<HistDesignationDO> selectTargetCompany(String existsSql, Date searchDate) {
                return baseMapper.selectTargetCompany(existsSql,SysUtil.transDateToString(searchDate));
        }

        @Override
        public  List<HistDesignationDO> selectAllCompany(String custId,
                                                         Date searchDate) {
                if (StrUtil.isBlank(custId)){
                        custId = "01";
                }
                return baseMapper.selectAllCompany(custId, SysUtil.transDateToString(searchDate));
        }

        @Override
        public List<MastCompanyDO> selectCompanyList(String customerId,String language,Date date,
                                                     List<String> companyList) {
               if (StrUtil.isBlank(language)){
                       language="ja";
               }
               if (StrUtil.isBlank(customerId)){
                       customerId = "01";
               }
               if (date ==null){
                       date = SysDateUtil.of(1900,1,1);
               }
               String strDate = SysUtil.transDateToString(date);
               if (CollUtil.isEmpty(companyList)){
                       companyList = CollUtil.newArrayList("01");
               }
               return baseMapper.selectCompanyList(customerId,language,strDate, companyList);
        }

        @Override
        public String getCompanyName(String searchDate) {
           return baseMapper.getCompanyName(searchDate);
        }
}
