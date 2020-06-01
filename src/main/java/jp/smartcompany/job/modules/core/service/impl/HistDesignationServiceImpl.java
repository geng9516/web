package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.bo.EvaluatorBO;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.mapper.HistDesignationMapper;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.SectionGroupId;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.employeeDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.employeeListVo;
import jp.smartcompany.job.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 異動歴 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class HistDesignationServiceImpl extends ServiceImpl<HistDesignationMapper, HistDesignationDO> implements IHistDesignationService {



        /**
         * 在籍部署・グループを検索
         *
         * @param customerId 　顧客コード
         * @param companyId  　法人コード
         * @param employeeId  　社員番号
         * @param yyyymmdd 　勤務日
         * @return
         */
        @Override
        public SectionGroupId selectSecGroupId(String customerId, String companyId, String employeeId ,Date yyyymmdd){
                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", customerId);
                map.put("companyId", companyId);
                map.put("employeeId", employeeId);
                map.put("yyyymmdd", yyyymmdd);
                // パターン
                SectionGroupId sgId = baseMapper.selectSecGroupId(map);

                return sgId;
        }

        @Override
        public List<HistDesignationDO> selectCompanyId(String userId, Date date) {
                QueryWrapper<HistDesignationDO> qw = SysUtil.query();
                qw.eq("HD_CUSERID",userId)
                  .lt("HD_DSTARTDATE_CK",date)
                   .gt("HD_DENDDATE",date);
                return list(qw);
        }

        @Override
        public List<EvaluatorBO> selectEvaluator(String customerId, String systemId, String userId,
                                                 Date pdSearchDate, int evaluation,
                                                 String reportType, String sLanguage) {
              return baseMapper.selectEvaluator(customerId,systemId, userId, pdSearchDate, evaluation, reportType, sLanguage);
        }

        @Override
        public List<EvaluatorBO> selectAllEvaluator(String psCustomerId,String psUserId,Date pdSearchDate,String psLanguage) {
              return baseMapper.selectAllEvaluator(psCustomerId,psUserId, pdSearchDate, psLanguage);
        }

        @Override
        public List<EvaluatorBO> selectWithSection(
                String psCustId,String psUserId,String psSectionId,Date pdSearchDate,String sLanguage) {
                return baseMapper.selectWithSection(
                        psCustId,psUserId,psSectionId,pdSearchDate,sLanguage);
        }

        @Override
        public List<EvaluatorBO> selectSectionChief(
                String psCustId,
                String compId,
                String sectionId,
                Date pdSearchDate,
                String sLanguage) {
                return baseMapper.selectSectionChief(
                        psCustId,compId,sectionId,pdSearchDate,sLanguage);
        }


        /**ヘッダ情報(新規申請用)を取得するSQLを生成します。*/
        @Override
        public employeeDetailVo selectemployeeDetail(String custId, String compId, String employeeId, String language){
                Map<String, Object> map = MapUtil.newHashMap(5);
                map.put("custId", custId);
                map.put("compId", compId);
                map.put("employeeId", employeeId);
                map.put("language", language);

                return baseMapper.selectemployeeDetail(map);
        }


        @Override
        public employeeDetailVo selectemployee(String custId, String compId, String employeeId, String language,String sectionId){
                Map<String, Object> map = MapUtil.newHashMap(5);
                map.put("custId", custId);
                map.put("compId", compId);
                map.put("employeeId", employeeId);
                map.put("language", language);
                map.put("sectionId", sectionId);
                return baseMapper.selectemployee(map);
        }

        /**
         * 職員一覧を取得するSQLを返す
         * */
        @Override
        public List<employeeListVo> selectemployeeList(String custId,String compId,String date,String sql){
                return baseMapper.selectemployeeList(custId,compId,date,sql);
        }


        /**
         * 所属名
         * */
        @Override
        public String selectSectionNAme(String custId,String compId,Date date,String sectionId){
                return baseMapper.selectSectionNAme(custId,compId,date,sectionId);
        }
}
