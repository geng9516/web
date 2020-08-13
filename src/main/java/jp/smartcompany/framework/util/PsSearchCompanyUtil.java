package jp.smartcompany.framework.util;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.service.IMastCompanyService;
import jp.smartcompany.job.modules.core.util.PsConst;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 法人取得クラス
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PsSearchCompanyUtil {

    private final IMastCompanyService iMastCompanyService;
    private final PsBuildTargetSql psBuildTargetSql;

    /** 全社区分判定フラグ */
    private boolean gbAllCompaniesFlg;

    /**
     * システム情報取得-検索対象範囲設定情報取得機能を用い<br>
     * 「検索対象範囲設定情報」を取得<br>
     * その後取得した検索対象範囲設定情報を適用し<br>
     * 異動歴より特定された人が属する法人のリストを返却します。
     *
     * @return 法人コードが格納されたリスト
     */
    public List<String> getCompList(Date searchDate) {
        // Exists句を取得した際に余計な識別子を削除します。
        String sExists = psBuildTargetSql.getExistsQueryHdId("HIST_DESIGNATION.HD_CUSERID", "HIST_DESIGNATION.HD_ID");
        // 法人取得取得
        List<HistDesignationDO> lHistDesignationEntityList
                = iMastCompanyService.selectTargetCompany(sExists, getPsSearchDate(
                        SysUtil.transDateToString(searchDate)
        ));
        List<String> lResultList = CollUtil.newArrayList();
        String sCust = null;
        for (HistDesignationDO histDesignationDO : lHistDesignationEntityList) {
            lResultList.add(histDesignationDO.getHdCcompanyidCk());
            sCust = histDesignationDO.getHdCcustomeridCk();
        }

        // 全社区分に対する権限を判定
        if (sCust != null && !lResultList.isEmpty()) {
            List <HistDesignationDO>
                    comp = iMastCompanyService.selectAllCompany(
                    sCust, searchDate);
            // 「権限を持つ法人リスト」の内容が、「有効な法人リスト」の要素と同じ場合、
            // 全社区分に対する権限も持つと判定し、リストに追加
            if (lResultList.size() == comp.size()) {
                boolean bEquals = true;
                for (String sComp : lResultList) {
                    boolean bCodeCheck = false;
                    for (HistDesignationDO mac : comp) {
                        if (sComp.equalsIgnoreCase(mac.getHdCcompanyidCk())) {
                            bCodeCheck = true;
                            break;
                        }
                    }
                    if (!bCodeCheck) {
                        bEquals = false;
                        break;
                    }
                }
                if (bEquals) {
                    lResultList.add(PsConst.CODE_ALL_COMPANIES);
                }
                // 全社区分判定フラグをセットする
                this.gbAllCompaniesFlg = bEquals;
            }
        }
        return lResultList;
    }

    /**
     * <B>【基準日（データ検索基準日）返却】</B><BR>
     * @return  String  データ検索基準日
     */
    public Date getPsSearchDate(String gsPsSearchDate) {
        Date dSearchDate;
        if (gsPsSearchDate == null) {
            Date dSysDate = new Date();
            // 時分秒を削除
            String sSysDate = SysUtil.transDateToString(dSysDate);
            try {
                dSysDate = SysUtil.transStringToDate(sSysDate);
            }
            catch(ParseException e) {
                e.printStackTrace();
                return null;
            }
            return dSysDate;
        }
        try {
            dSearchDate = SysUtil.transStringToDate(gsPsSearchDate);
        } catch (ParseException e) {
            throw new GlobalException(PsConst.PARAM_KEY_SEARCHDATE+"-"+"yyyy/MM/dd");
        }
        return dSearchDate;
    }

    public boolean isAllCompaniesFlg() {
        return this.gbAllCompaniesFlg;
    }

}
