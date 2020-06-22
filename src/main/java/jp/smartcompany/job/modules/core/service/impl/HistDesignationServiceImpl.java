package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.bo.EvaluatorBO;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.mapper.HistDesignationMapper;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.SectionGroupId;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.EmployeeDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.EmployeeListVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

        /**
         * 基準日時点の法人コードを取得する
         * @param userId
         * @param date
         * @return
         */
        @Override
        public List<HistDesignationDO> selectCompanyId(String userId, Date date) {
                QueryWrapper<HistDesignationDO> qw = SysUtil.query();
                qw.eq("HD_CUSERID",userId)
                  .le("HD_DSTARTDATE_CK",SysUtil.transDateToString(date))
                   .ge("HD_DENDDATE",SysUtil.transDateToString(date));
                return list(qw);
        }

        @Override
        public List<EvaluatorBO> selectEvaluator(String customerId, String systemId, String userId,
                                                 Date pdSearchDate, int evaluation,
                                                 String reportType, String sLanguage) {
              return baseMapper.selectEvaluator(customerId,systemId, userId, SysUtil.transDateToString(pdSearchDate), evaluation, reportType, sLanguage);
        }

        @Override
        public List<EvaluatorBO> selectAllEvaluator(String psCustomerId,String psUserId,Date pdSearchDate,String psLanguage) {
              return baseMapper.selectAllEvaluator(psCustomerId,psUserId, SysUtil.transDateToString(pdSearchDate), psLanguage);
        }

        @Override
        public List<EvaluatorBO> selectWithSection(
                String psCustId,String psUserId,String psSectionId,Date pdSearchDate,String sLanguage) {
                return baseMapper.selectWithSection(
                        psCustId,psUserId,psSectionId,SysUtil.transDateToString(pdSearchDate),sLanguage);
        }

        @Override
        public List<EvaluatorBO> selectSectionChief(
                String psCustId,
                String compId,
                String sectionId,
                Date pdSearchDate,
                String sLanguage) {
                return baseMapper.selectSectionChief(
                        psCustId,compId,sectionId,SysUtil.transDateToString(pdSearchDate),sLanguage);
        }


        /**ヘッダ情報(新規申請用)を取得するSQLを生成します。*/
        @Override
        public EmployeeDetailVo selectemployeeDetail(String custId, String compId, String employeeId, String language){
                Map<String, Object> map = MapUtil.newHashMap(5);
                map.put("custId", custId);
                map.put("compId", compId);
                map.put("employeeId", employeeId);
                map.put("language", language);

                return baseMapper.selectemployeeDetail(map);
        }


        @Override
        public EmployeeDetailVo selectemployee(String custId, String compId, String employeeId, String language, String sectionId){
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
        public List<EmployeeListVo> selectemployeeList(String custId, String compId, String date, String sql){
                return baseMapper.selectemployeeList(custId,compId,date,sql);
        }


        /**
         * 所属名
         * */
        @Override
        public String selectSectionNAme(String custId,String compId,Date date,String sectionId){
                return baseMapper.selectSectionNAme(custId,compId,date,sectionId);
        }

        /**
         * 異動歴検索処理
         *
         * @param psCustomerId 検索対象の顧客コード
         * @param psCompanyId 検索対象の法コード
         * @param psUserId 検索対象のユーザID
         * @param psDate 対象日
         *
         * @return List < HistDesignationEntity >
         */
        @Override
        public List <HistDesignationDO> selectByEmpId(String psCustomerId, String psCompanyId, String psUserId, String psDate) {
                QueryWrapper<HistDesignationDO> qw = SysUtil.query();
                Date now = DateUtil.date();
                String d = DateUtil.format(now, TmgUtil.Cs_FORMAT_DATE_TYPE1);
                String fDate = SysUtil.transDateNullToDB(d);
                qw.eq("HD_CUSERID",psUserId)
                        .le("HD_DSTARTDATE_CK",fDate)
                        .ge("HD_DENDDATE",fDate);
                return list(qw);
        }

        /**
         * CSV出力用データを取得
         *
         * @param targetDate   対象日
         * @param headerList 　検索項目
         * @param empsql     　対象者取得sql
         * @return List<Map>
         */
        @Override
        public List<Map> buildSQLForSelectCSVOutputImage(String targetDate, List<ItemVO> headerList, String empsql) {

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("targetDate", targetDate);
                map.put("headerList", headerList);
                map.put("empsql", empsql);
                return baseMapper.buildSQLForSelectCSVOutputImage(map);
        }

}
