package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarSectionDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupMemberDO;
import jp.smartcompany.job.modules.core.service.ITmgCalendarSectionService;
import jp.smartcompany.job.modules.core.service.ITmgCalendarService;
import jp.smartcompany.job.modules.core.service.ITmgGroupMemberService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgCalendarRow;
import jp.smartcompany.job.modules.tmg.util.Util;
import jp.smartcompany.job.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nie Wanqun
 * <p>
 * カレンダー情報の返却
 * TMG_F_GET_CALENDAR_LIST
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetCalendarList extends BaseExecute {

    /**
     * ITmgGroupMemberService
     */
    private final ITmgGroupMemberService iTmgGroupMemberService;

    /**
     * ITmgCalendarSectionService
     */
    private final ITmgCalendarSectionService iTmgCalendarSectionService;

    /**
     * ITmgCalendarService
     */
    private final ITmgCalendarService iTmgCalendarService;

    /**
     * Util
     */
    private final Util util;

    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }

    /**
     * カレンダー情報の返却
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param sectionId  組織コード
     * @param groupId    グループコード
     * @param date       基準日
     * @return カレンダー情報
     */
    public List<TmgCalendarRow> init(String customerId, String companyId, String employeeId, String sectionId, String groupId, Date date) {

        List<TmgCalendarRow> tmgCalendarRowList = new ArrayList<>();

        // 引数を変数に待避
        // 組織コード
        String wSectionId = sectionId;
        // グループコード
        String wsGroupId = groupId;
        // 基準日
        Date wsDate = date;

        if (wsDate == null) {
            wsDate = DateUtil.date();
        }

        // システム日付
        Date sysDate = DateUtil.date();

        // 社員番号指定の場合
        boolean flg = !StrUtil.hasEmpty(employeeId) && StrUtil.hasEmpty(wSectionId) && StrUtil.hasEmpty(wsGroupId);
        if (flg) {

            // レコード呼出(人)
            List<TmgGroupMemberDO> tmgGroupMemberDOList = iTmgGroupMemberService.getBaseMapper().selectList(
                    SysUtil.<TmgGroupMemberDO>query()
                            .select("tgrm_csectionid", "tgrm_cgroupid")
                            .eq("tgrm_ccustomerid", customerId)
                            .eq("tgrm_ccompanyid", companyId)
                            .eq("tgrm_cemployeeid", employeeId)
                            .le("tgrm_dstartdate", sysDate)
                            .ge("tgrm_denddate", sysDate)
            );
            if (tmgGroupMemberDOList.size() > 0) {
                wSectionId = tmgGroupMemberDOList.get(0).getTgrmCsectionid();
                wsGroupId = tmgGroupMemberDOList.get(0).getTgrmCgroupid();
            }
        }

        // 変数初期化
        // 顧客コード
        String gsCustomerId = "";
        // 法人コード
        String gsCompanyId = "";
        // 組織コード
        String gSectionId = "";
        // グループコード
        String gsGroupId = "";

        // 該当組織が存在しない場合を判定
        boolean wbFlg = false;
        // 組織カレンダーが存在しない場合を判定
        boolean wbAllFlg = false;

        // 件数取得
        int wnCount = this.tmgCheck(customerId, companyId, wSectionId, wsGroupId);
        if (wnCount > 0) {
            gsCustomerId = customerId;
            gsCompanyId = companyId;
            gSectionId = wSectionId;
            gsGroupId = wsGroupId;
            wbFlg = true;
        } else {
            // 上位階層を走査
            List<String> strList = util.tmgGetUpperSection(customerId, companyId, wSectionId, sysDate);
            for (String str : strList) {
                // 件数取得
                wnCount = this.tmgCheck(customerId, companyId, str, str + "||000000");
                if (wnCount > 0) {
                    gsCustomerId = customerId;
                    gsCompanyId = companyId;
                    gSectionId = str;
                    gsGroupId = str + "||000000";
                    wbFlg = true;
                    break;
                }
            }
        }

        // 組織を検索
        if (wbFlg) {

            // レコード呼出(組織)
            List<TmgCalendarSectionDO> tmgCalendarSectionDOList = iTmgCalendarSectionService.getBaseMapper().selectList(SysUtil.<TmgCalendarSectionDO>query()
                    .eq("tcas_ccustomerid", gsCustomerId)
                    .eq("tcas_ccompanyid", gsCompanyId)
                    .le("tcas_dstartdate", sysDate)
                    .ge("tcas_denddate", sysDate)
                    .eq("tcas_csectionid", gSectionId)
                    .eq("tcas_cgroupid", gsGroupId)
                    .le("tcas_dyyyymm", DateUtil.beginOfYear(wsDate))
                    .ge("tcas_dyyyymm", DateUtil.endOfYear(wsDate))
            );

            for (TmgCalendarSectionDO tmgCalendarSectionDO : tmgCalendarSectionDOList) {

                TmgCalendarRow tmgCalendarRow = this.setTmgCalendarRowByTmgCalendarSection(tmgCalendarSectionDO);
                tmgCalendarRowList.add(tmgCalendarRow);
                wbAllFlg = true;
            }
        }

        // 存在しない場合は全学を返却
        if (!wbAllFlg) {

            // レコード呼出(全学)
            List<TmgCalendarDO> tmgCalendarDOList = iTmgCalendarService.getBaseMapper().selectList(SysUtil.<TmgCalendarDO>query()
                    .eq("tca_ccustomerid", gsCustomerId)
                    .eq("tca_ccompanyid", gsCompanyId)
                    .le("tca_dstartdate", sysDate)
                    .ge("tca_denddate", sysDate)
                    .le("tca_dyyyymm", DateUtil.beginOfYear(wsDate))
                    .ge("tca_dyyyymm", DateUtil.endOfYear(wsDate))
            );

            for (TmgCalendarDO tmgCalendarDO : tmgCalendarDOList) {
                TmgCalendarRow tmgCalendarRow = this.setTmgCalendarRowByTmgCalendar(tmgCalendarDO);
                tmgCalendarRowList.add(tmgCalendarRow);
            }
        }

        return tmgCalendarRowList;
    }

    /**
     * 存在判定用ファンクション
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param sectionId  組織コード
     * @param groupId    グループコード
     * @return int
     */
    private int tmgCheck(String customerId, String companyId, String sectionId, String groupId) {

        // システム日付
        Date sysDate = DateUtil.date();

        return iTmgCalendarSectionService.getBaseMapper().selectCount(SysUtil.<TmgCalendarSectionDO>query()
                .eq("tcas_ccustomerid", customerId)
                .eq("tcas_ccompanyid", companyId)
                .le("tcas_dstartdate", sysDate)
                .ge("tcas_denddate", sysDate)
                .eq("tcas_csectionid", sectionId)
                .eq("tcas_cgroupid", groupId)
        );
    }

    /**
     * TmgCalendarSectionDOでTmgCalendarRowを設定する
     *
     * @param tmgCalendarSectionDO TmgCalendarSectionDO
     * @return TmgCalendarRow
     */
    private TmgCalendarRow setTmgCalendarRowByTmgCalendarSection(TmgCalendarSectionDO tmgCalendarSectionDO) {

        TmgCalendarRow tmgCalendarRow = new TmgCalendarRow();
        tmgCalendarRow.setTcaCustomerId(tmgCalendarSectionDO.getTcasCcustomerid());
        tmgCalendarRow.setTcaCompanyId(tmgCalendarSectionDO.getTcasCcompanyid());
        tmgCalendarRow.setTcaStartDate(tmgCalendarSectionDO.getTcasDstartdate());
        tmgCalendarRow.setTcaEndDate(tmgCalendarSectionDO.getTcasDenddate());
        tmgCalendarRow.setTcaModifierUserId(tmgCalendarSectionDO.getTcasCmodifieruserid());
        tmgCalendarRow.setTcaModifiedDate(tmgCalendarSectionDO.getTcasDmodifieddate());
        tmgCalendarRow.setTcaModifiedProgramId(tmgCalendarSectionDO.getTcasCmodifierprogramid());
        tmgCalendarRow.setTcaYyyyMm(tmgCalendarSectionDO.getTcasDyyyymm());
        tmgCalendarRow.setTcaHolFlg01(tmgCalendarSectionDO.getTcasCholflg01());
        tmgCalendarRow.setTcaHolFlg02(tmgCalendarSectionDO.getTcasCholflg02());
        tmgCalendarRow.setTcaHolFlg03(tmgCalendarSectionDO.getTcasCholflg03());
        tmgCalendarRow.setTcaHolFlg04(tmgCalendarSectionDO.getTcasCholflg04());
        tmgCalendarRow.setTcaHolFlg05(tmgCalendarSectionDO.getTcasCholflg05());
        tmgCalendarRow.setTcaHolFlg06(tmgCalendarSectionDO.getTcasCholflg06());
        tmgCalendarRow.setTcaHolFlg07(tmgCalendarSectionDO.getTcasCholflg07());
        tmgCalendarRow.setTcaHolFlg08(tmgCalendarSectionDO.getTcasCholflg08());
        tmgCalendarRow.setTcaHolFlg09(tmgCalendarSectionDO.getTcasCholflg09());
        tmgCalendarRow.setTcaHolFlg10(tmgCalendarSectionDO.getTcasCholflg10());
        tmgCalendarRow.setTcaHolFlg11(tmgCalendarSectionDO.getTcasCholflg11());
        tmgCalendarRow.setTcaHolFlg12(tmgCalendarSectionDO.getTcasCholflg12());
        tmgCalendarRow.setTcaHolFlg13(tmgCalendarSectionDO.getTcasCholflg13());
        tmgCalendarRow.setTcaHolFlg14(tmgCalendarSectionDO.getTcasCholflg14());
        tmgCalendarRow.setTcaHolFlg15(tmgCalendarSectionDO.getTcasCholflg15());
        tmgCalendarRow.setTcaHolFlg16(tmgCalendarSectionDO.getTcasCholflg16());
        tmgCalendarRow.setTcaHolFlg17(tmgCalendarSectionDO.getTcasCholflg17());
        tmgCalendarRow.setTcaHolFlg18(tmgCalendarSectionDO.getTcasCholflg18());
        tmgCalendarRow.setTcaHolFlg19(tmgCalendarSectionDO.getTcasCholflg19());
        tmgCalendarRow.setTcaHolFlg20(tmgCalendarSectionDO.getTcasCholflg20());
        tmgCalendarRow.setTcaHolFlg21(tmgCalendarSectionDO.getTcasCholflg21());
        tmgCalendarRow.setTcaHolFlg22(tmgCalendarSectionDO.getTcasCholflg22());
        tmgCalendarRow.setTcaHolFlg23(tmgCalendarSectionDO.getTcasCholflg23());
        tmgCalendarRow.setTcaHolFlg24(tmgCalendarSectionDO.getTcasCholflg24());
        tmgCalendarRow.setTcaHolFlg25(tmgCalendarSectionDO.getTcasCholflg25());
        tmgCalendarRow.setTcaHolFlg26(tmgCalendarSectionDO.getTcasCholflg26());
        tmgCalendarRow.setTcaHolFlg27(tmgCalendarSectionDO.getTcasCholflg27());
        tmgCalendarRow.setTcaHolFlg28(tmgCalendarSectionDO.getTcasCholflg28());
        tmgCalendarRow.setTcaHolFlg29(tmgCalendarSectionDO.getTcasCholflg29());
        tmgCalendarRow.setTcaHolFlg30(tmgCalendarSectionDO.getTcasCholflg30());
        tmgCalendarRow.setTcaHolFlg31(tmgCalendarSectionDO.getTcasCholflg31());
        return tmgCalendarRow;
    }

    /**
     * TmgCalendarDOでTmgCalendarRowを設定する
     *
     * @param tmgCalendarDO TmgCalendarDO
     * @return TmgCalendarRow
     */
    private TmgCalendarRow setTmgCalendarRowByTmgCalendar(TmgCalendarDO tmgCalendarDO) {

        TmgCalendarRow tmgCalendarRow = new TmgCalendarRow();
        tmgCalendarRow.setTcaCustomerId(tmgCalendarDO.getTcaCcustomerid());
        tmgCalendarRow.setTcaCompanyId(tmgCalendarDO.getTcaCcompanyid());
        tmgCalendarRow.setTcaStartDate(tmgCalendarDO.getTcaDstartdate());
        tmgCalendarRow.setTcaEndDate(tmgCalendarDO.getTcaDenddate());
        tmgCalendarRow.setTcaModifierUserId(tmgCalendarDO.getTcaCmodifieruserid());
        tmgCalendarRow.setTcaModifiedDate(tmgCalendarDO.getTcaDmodifieddate());
        tmgCalendarRow.setTcaModifiedProgramId(tmgCalendarDO.getTcaCmodifierprogramid());
        tmgCalendarRow.setTcaYyyyMm(tmgCalendarDO.getTcaDyyyymm());
        tmgCalendarRow.setTcaHolFlg01(tmgCalendarDO.getTcaCholflg01());
        tmgCalendarRow.setTcaHolFlg02(tmgCalendarDO.getTcaCholflg02());
        tmgCalendarRow.setTcaHolFlg03(tmgCalendarDO.getTcaCholflg03());
        tmgCalendarRow.setTcaHolFlg04(tmgCalendarDO.getTcaCholflg04());
        tmgCalendarRow.setTcaHolFlg05(tmgCalendarDO.getTcaCholflg05());
        tmgCalendarRow.setTcaHolFlg06(tmgCalendarDO.getTcaCholflg06());
        tmgCalendarRow.setTcaHolFlg07(tmgCalendarDO.getTcaCholflg07());
        tmgCalendarRow.setTcaHolFlg08(tmgCalendarDO.getTcaCholflg08());
        tmgCalendarRow.setTcaHolFlg09(tmgCalendarDO.getTcaCholflg09());
        tmgCalendarRow.setTcaHolFlg10(tmgCalendarDO.getTcaCholflg10());
        tmgCalendarRow.setTcaHolFlg11(tmgCalendarDO.getTcaCholflg11());
        tmgCalendarRow.setTcaHolFlg12(tmgCalendarDO.getTcaCholflg12());
        tmgCalendarRow.setTcaHolFlg13(tmgCalendarDO.getTcaCholflg13());
        tmgCalendarRow.setTcaHolFlg14(tmgCalendarDO.getTcaCholflg14());
        tmgCalendarRow.setTcaHolFlg15(tmgCalendarDO.getTcaCholflg15());
        tmgCalendarRow.setTcaHolFlg16(tmgCalendarDO.getTcaCholflg16());
        tmgCalendarRow.setTcaHolFlg17(tmgCalendarDO.getTcaCholflg17());
        tmgCalendarRow.setTcaHolFlg18(tmgCalendarDO.getTcaCholflg18());
        tmgCalendarRow.setTcaHolFlg19(tmgCalendarDO.getTcaCholflg19());
        tmgCalendarRow.setTcaHolFlg20(tmgCalendarDO.getTcaCholflg20());
        tmgCalendarRow.setTcaHolFlg21(tmgCalendarDO.getTcaCholflg21());
        tmgCalendarRow.setTcaHolFlg22(tmgCalendarDO.getTcaCholflg22());
        tmgCalendarRow.setTcaHolFlg23(tmgCalendarDO.getTcaCholflg23());
        tmgCalendarRow.setTcaHolFlg24(tmgCalendarDO.getTcaCholflg24());
        tmgCalendarRow.setTcaHolFlg25(tmgCalendarDO.getTcaCholflg25());
        tmgCalendarRow.setTcaHolFlg26(tmgCalendarDO.getTcaCholflg26());
        tmgCalendarRow.setTcaHolFlg27(tmgCalendarDO.getTcaCholflg27());
        tmgCalendarRow.setTcaHolFlg28(tmgCalendarDO.getTcaCholflg28());
        tmgCalendarRow.setTcaHolFlg29(tmgCalendarDO.getTcaCholflg29());
        tmgCalendarRow.setTcaHolFlg30(tmgCalendarDO.getTcaCholflg30());
        tmgCalendarRow.setTcaHolFlg31(tmgCalendarDO.getTcaCholflg31());
        return tmgCalendarRow;
    }

}
