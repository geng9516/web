package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.framework.jsf.orgtree.dto.OrgTreeDTO;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionOrganisationBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastOrganisationDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.monthlyoutput.dto.TargetFiscalYearDto;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotApprovalVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotFixedDeptListVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.LimitOfBasedateVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 組織ツリーマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastOrganisationService extends IService<MastOrganisationDO> {


    /**
     * 指定組織の検索
     *
     * @param customerId
     * @param companyId
     * @param sectionId
     * @param yyyymmdd
     * @return
     */
    MastOrganisationDO selectOrganisation(String customerId, String companyId, String sectionId, Date yyyymmdd);


    /**
     * 指定組織の検索（パタンが存在する）
     *
     * @param customerId
     * @param companyId
     * @param yyyymmdd
     * @return
     */
    List<MastOrganisationDO> selectPatternOrganisation(String customerId, String companyId, Date yyyymmdd);

    /**
     * 根据层级选择基点组织下的组织列表
     *
     * @param customerId
     * @param conds
     * @param date
     * @return
     */
    List<BaseSectionOrganisationBO> selectOrganisationByLevel(String customerId, String conds, Date date);

    /**
     * <p>
     * <b>上位組織</b>情報取得（組織指定）
     * </p>
     * <div>指定した組織の上位組織リストを返却する。</div>
     *
     * @author t-abe
     * @param customerId 顧客コード
     * @param compnyId 法人コード
     * @param sectionId 組織コード
     * @param searchDate 検索基準日
     * @return 上位組織情報
     */
    List<String> selectHighSection(String psCustID, String psCompCode, String psTargetDept, Date pdSearchDate);


    /**
     * <p>
     * <b>下位組織</b>情報取得（組織指定）
     * </p>
     * <div>指定した組織の下位組織リストを返却する。 </div>
     *
     * @param psCustID  顧客コード
     * @param psCompID  法人コード
     * @param psSection 組織コード
     * @param date      検索基準日
     * @return 下位組織情報
     * @throws
     * @author t-abe
     */
    List<String> selectLowerSection(String psCustID, String psCompID, String psSection, Date date);


    /**
     * 基準日時点の超勤限度時間取得用
     *
     * @param custID    顧客コード
     * @param compCode  法人コード
     * @param targetSec 部署コード
     * @param day       　基準日
     * @return LimitOfBasedateVO
     */
    LimitOfBasedateVO buildSQLForLimitOfBasedate(String custID, String compCode, String targetSec, String day);

    /**
     * 職員情報を取得する
     *
     * @param sectionId 部署コード
     * @param today     基準日
     * @param custId    顧客コード
     * @param compCode  法人コード
     * @return 所属マスタの値
     */
    String buildSQLForSelectEmployeeDetail(String sectionId, String today, String custId, String compCode);

    /**
     * <p>
     * <b>下位組織</b>情報取得（組織指定）
     * </p>
     * <div>指定した組織の下位組織リストを返却する。 </div>
     *
     * @author t-abe
     * @param customerId 顧客コード
     * @param compnyId 法人コード
     * @param sectionId 組織コード
     * @param searchDate 検索基準日
     * @return 下位組織情報
     * @exception
     */
    List < String > getSubSection(String customerId, String compnyId, String sectionId,
                                  Date searchDate);




    /**
     * 選択された組織の表示権限があるかを判定するSQLを返すメソッド
     * */
    int selectHasAuth(String customerId, String compnyId, String sectionId,
                      String searchDate,String language,String exists);


    /**
     * 対象日の年度開始日および年度終了日を取得するSQLを構築して返します
     */
    TargetFiscalYearDto selectTargetFiscalYear(String cust,
                                               String comp,
                                               String section,
                                               String lang,
                                               String targetDate,
                                               String targetStartDate,
                                               String targetEneDate,
                                               String psBaseDate);


    /**
     * 対象組織と組織配下の部署について対象年月の未承認者情報を検索するSQL文を生成し返します。
     *
     * @param custId 対象顧客コード
     * @param compId 対象法人コード
     * @param secId 対象組織コード
     * @param dyyyymm 該当年月
     * @param lang 言語区分
     * @param numStart 検索番号スタート
     * @param numEnd 検索番号エンド
     * @return SQL文
     */
    List<NotApprovalVo> selectNotApproval(String custId,
                                          String compId,
                                          String secId,
                                          String dyyyymm,
                                          String lang,
                                          int numStart,
                                          int numEnd);


    /**
     * 対象年月の締め未完了部局の一覧を検索するSQL文を生成し返します。
     *
     * @param custId 対象顧客コード
     * @param compId 対象法人コード
     * @param secId 対象組織コード
     * @param dyyyymm 該当年月
     * @param lang 言語区分
     * @param numStart 検索番号スタート
     * @param numEnd 検索番号エンド
     * @return SQL文
     */
    List<NotFixedDeptListVo> selectNotFixedDeptList(String custId,
                                                    String compId,
                                                    String secId,
                                                    String dyyyymm,
                                                    String lang,
                                                    int numStart,
                                                    int numEnd);


    /**
     * <p>
     * <b>下位組織</b>情報取得（社員指定）
     * </p>
     * <div>指定した社員の下位組織情報を返却する。</div>
     *
     * @author t-abe
     * @param userId ユーザID
     * @param searchDate 検索基準日
     * @param virtualSection 仮想組織判定<br>
     *            true : 組織リストに仮想組織を含ませる。<br>
     *            false : 仮想リストに仮想組織を含ませない。
     * @return 下位組織情報
     * @exception
     */
    Map<String,List<String>> getSubSectionEmp(String userId,Date searchDate, boolean virtualSection);

    /**
     * 取得結果を返却.
     * @param psCustomerId 顧客コード
     * @param psLanguage 言語区分
     * @param psCompanyId 法人コード
     * @param pdSearchDate 検索基準日
     * @return String
     **/
    List<OrgTreeDTO> selectOrgList(
      String customerId,
      String language,
      String companyId,
      String searchDate,
      String startDate,
      String endDate,
      String companyCode,
      String sectionCode,
      String sExists
    );

    /**
     * 選択中の法人の取得結果を返却.
     * @param psCustomerId 顧客コード
     * @param psLanguage 言語区分
     * @param psCompanyId 法人コード
     * @param searchDate 検索基準日
     * @param exists 検索条件範囲
     * @return String
     **/
    List<OrgTreeDTO> getSelCompOrgTreeList(
            String psCustomerId,
            String psLanguage,
            String psCompanyId,
            String startDate,
            String endDate,
            String exists);

}
