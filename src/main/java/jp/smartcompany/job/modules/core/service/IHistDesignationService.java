package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.bo.EvaluatorBO;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.SectionGroupId;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.EmployeeDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.EmployeeListVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.ItemVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 異動歴 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IHistDesignationService extends IService<HistDesignationDO> {


        /**
         * 在籍部署・グループを検索
         *
         * @param customerId 　顧客コード
         * @param companyId  　法人コード
         * @param employeeId  　社員番号
         * @param yyyymmdd 　勤務日
         * @return
         */
        SectionGroupId selectSecGroupId(String customerId, String companyId, String employeeId , Date yyyymmdd);


        /**
         * 基準日時点の法人コードを取得する
         * @param userId
         * @param date
         * @return
         */
        List<HistDesignationDO> selectCompanyId(String userId,Date date);

        List<EvaluatorBO> selectEvaluator(String customerId,String systemId,String userId,
                                          Date pdSearchDate,int evaluation,
                                          String reportType,String sLanguage);

        List<EvaluatorBO> selectAllEvaluator(String psCustomerId,String psUserId,Date pdSearchDate,String psLanguage);

        List<EvaluatorBO> selectWithSection(
                String psCustId,String psUserId,String psSectionId,Date pdSearchDate,String sLanguage);

        List<EvaluatorBO> selectSectionChief(
                        String psCustId,
                        String compId,
                        String sectionId,
                        Date pdSearchDate,
                        String sLanguage);


        /**ヘッダ情報(新規申請用)を取得するSQLを生成します。*/
        EmployeeDetailVo selectemployeeDetail(String custId, String compId, String employeeId, String language);

        EmployeeDetailVo selectemployee(String custId, String compId, String employeeId, String language, String sectionId);

        /**
         * 職員一覧を取得するSQLを返す
         * */
        List<EmployeeListVo> selectemployeeList(String custId, String compId, String date, String sql);



        /**
         * 所属名
         * */
        String selectSectionNAme(String custId,String compId,Date date,String sectionId);

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
        List <HistDesignationDO> selectByEmpId(String psCustomerId, String psCompanyId, String psUserId, String psDate);


        /**
         * CSV出力用データを取得
         *
         * @param targetDate   対象日
         * @param headerList 　検索項目
         * @param empsql     　対象者取得sql
         * @return List<Map>
         */
        List<Map> buildSQLForSelectCSVOutputImage(String targetDate, List<ItemVO> headerList, String empsql);
}
