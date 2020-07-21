package jp.smartcompany.framework.jsf.orgtree.logic.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.framework.jsf.orgtree.dto.OrgTreeDTO;
import jp.smartcompany.framework.jsf.orgtree.logic.OrgTreeListLogic;
import jp.smartcompany.framework.util.PsBuildTargetSql;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 組織情報コンポーネントの法人ツリーLogicクラス
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrgTreeListLogicImpl implements OrgTreeListLogic {

    private final PsBuildTargetSql psBuildTargetSql;
    private final IMastOrganisationService iMastOrganisationService;

    @Override
    public List<OrgTreeDTO> getOrgTreeList(
            String customerId,
            String language,
            String companyId,
            String searchDate,
            String companyCode,
            String sectionCode,
            Boolean useSearchRange,
            String searchRange
    ) {
        String sExists = "";
        // 検索対象範囲を掛けるかどうか (false以外は掛ける)
        if (useSearchRange) {
            // 検索対象範囲種別の判定
            if (StrUtil.equalsIgnoreCase(searchRange,"1")) {
                // 検索対象範囲種別が"1"の場合は、法人の絞込みを行う
                sExists = psBuildTargetSql.getExistsQueryCompany("MO.MO_CCOMPANYID_CK_FK");
            } else {
                // 検索対象範囲種別が"1"以外の場合は、検索対象範囲設定を行う
                sExists = psBuildTargetSql.getExistsQueryOrganisation("MO.MO_CCOMPANYID_CK_FK","MO.MO_CSECTIONID_CK");
            }
        }

//        //StaticTreeを使用するか？
//        String sSysPropertyCache = SystemPropertyCache.getValue("UseStaticTree");
//        //検索対象範囲の設定適用時にはStaticTreeは使えない
//        if((sExists == null || sExists.equals("")) && (sSysPropertyCache != null && sSysPropertyCache.equalsIgnoreCase("yes"))){
//            List < OrgTreeDto > lorgtreedto = new ArrayList< OrgTreeDto >();
//            OrgTreeDto o = new OrgTreeDto();
//            o.setMoCmodifieruserid("usecache");
//            o.setMoCcompanyidCkFk(psCompanyId);
//            o.setMoCcustomeridCkFk(psCustomerId);
//            o.setMoClanguage(psLanguage);
//
//            // ファイル取得用日付取得処理開始
//            List <HistOrganisationTreeFilesEntity> orgTreeFilesList = orgTreeFilesDao.selectOrgTreeFileList(psCustomerId, psCompanyId, psLanguage, pdSearchDate);
//            // 情報格納
//            if(orgTreeFilesList.size() != 0){
//                o.setStaticDstart(orgTreeFilesList.get(0).getHotDstartdateCk().toString());
//                o.setStaticDend(orgTreeFilesList.get(0).getHotDenddate().toString());
//                o.setStaticName(orgTreeFilesList.get(0).getHotCfilename());
//            }
//            lorgtreedto.add(o);
//            return lorgtreedto;
//        }

        // 全ての時
        if (sExists.contains("'A' = 'A'")) {
            // 検索対象範囲を空にして検索
            return iMastOrganisationService.selectOrgList(
                    customerId,
                    language,
                    companyId,
                    searchDate,
                    searchDate,
                    searchDate,
                    companyCode,
                    sectionCode,
                    ""
            );
        }
        // 設定された検索対象範囲で組織を検索
        List<OrgTreeDTO> lOrgTreeList = iMastOrganisationService.selectOrgList(
            customerId,
            language,
            companyId,
            searchDate,
            searchDate,
            searchDate,
            companyCode,
            sectionCode,
            sExists
        );
        // 検索結果から検索対象組織のリストを取得（IN句形式）
        String sSectionList = " AND " + getOrgListFromLayeredSection(lOrgTreeList);

        // 対象組織のリストをもとに組織を検索し直し
        return iMastOrganisationService.selectOrgList(
               customerId,
               language,
               companyId,
               searchDate,
               searchDate,
               searchDate,
               companyCode,
               sectionCode,
               sSectionList
        );
    }

    /**
     * 選択中の法人の取得結果を返却.
     * @param psCustomerId 顧客コード
     * @param psLanguage 言語区分
     * @param psCompanyId 法人コード
     * @param pdSearchDate 検索基準日
     * @param exists 検索条件範囲
     * @return String
     **/
    @Override
    public List<OrgTreeDTO> getSelCompOrgTreeList(
            String psCustomerId,
            String psLanguage,
            String psCompanyId,
            String pdSearchDate,
            String exists){
        return iMastOrganisationService.getSelCompOrgTreeList(psCustomerId, psLanguage, psCompanyId, pdSearchDate, pdSearchDate, exists);
    }

    /**
     * 組織リストから内部階層コードを取り出して組織リストを作成する
     * @param plOrgTreeList
     * @return
     */
    private String getOrgListFromLayeredSection(List<OrgTreeDTO> plOrgTreeList) {
        Map<String,String> mSectionList = MapUtil.newHashMap();
        Map<String,String> mNewSectionList = MapUtil.newHashMap();
        // 検索した組織リストから組織コードをMapに追加
        for (OrgTreeDTO orgTreeDto : plOrgTreeList) {
            String sSection = orgTreeDto.getMoCsectionidCk();
            mSectionList.put(sSection, sSection);
        }
        // 検索した組織リストから内部階層コードを取得して組織コードMapと比較
        for (OrgTreeDTO orgTreeDto : plOrgTreeList) {
            String sLayeredSection = orgTreeDto.getMoClayeredsectionid();
            List<String> lLayeredSection = getSectionList(sLayeredSection);
            boolean bAddFlag = false;
            for (String s : lLayeredSection) {
                String sSection = (String) mSectionList.get(s);
                // 組織リストに存在する組織コードは追加する
                if (mSectionList.get(sSection) != null) {
                    mNewSectionList.put(sSection, sSection);
                    bAddFlag = true;
                }
                // 組織リストに存在しない場合でも既に追加済み以降は追加する
                else if (bAddFlag) {
                    mNewSectionList.put(s, s);
                }
            }
        }

        // 内部階層コードに含まれる組織リストを順次読み込み、IN句を組み立て
        StringBuilder sbSectionList = new StringBuilder();
        Set<String> setNewSectionList = mNewSectionList.keySet();
        Iterator<String> iNewSectionList = setNewSectionList.iterator();
        int nCount = 0;
        StringBuilder sSectionList = new StringBuilder();
        while(iNewSectionList.hasNext()) {
            sbSectionList.append("'").append(iNewSectionList.next()).append("',");
            nCount++;
            // IN句の要素が1000になったら一度閉じる
            if(nCount == 1000) {
                // 最初
                if(sSectionList.toString().equals("")) {
                    sSectionList.append(" ( ");
                }
                // 2回目からはORを追加
                else {
                    sSectionList.append(" OR ");
                }
                String sWork = sbSectionList.toString();
                sSectionList.append(" MO.MO_CSECTIONID_CK IN (").append(sWork.substring(0, sWork.length() - 1)).append(") ");
                nCount = 0;
                sbSectionList = new StringBuilder();
            }
        }

        String sWork = sbSectionList.toString();
        // ぴったりで終わったときはカッコで閉じるだけ
        if(!sSectionList.toString().equals("") && sWork.equals("")) {
            sSectionList.append(")");
        }
        // 残りの要素があるときはORで追加
        else if(!sSectionList.toString().equals("")) {
            sSectionList.append(" OR MO.MO_CSECTIONID_CK IN (").append(sWork.substring(0, sWork.length() - 1)).append(")) ");
        }
        // 初めての書き出しで対象データがある時
        else if(!sWork.equals("")) {
            sSectionList = new StringBuilder(" (MO.MO_CSECTIONID_CK IN (" + sWork.substring(0, sWork.length() - 1) + ")) ");
        }
        // 対象データがないとき
        else {
            sSectionList = new StringBuilder(" (1 = 2) ");
        }

        return sSectionList.toString();
    }

    /**
     * 内部階層コードから組織リストを取得する。
     * @param psLayeredSection
     * @return
     */
    private List <String> getSectionList(String psLayeredSection) {
        String[] saLayeredSection = psLayeredSection.split("\\|");
        String[] sSections = saLayeredSection[1].split(",");
        ArrayList <String> lSections = new ArrayList<String>();
        for(int i = 0; i < sSections.length; i++) {
            if(sSections[i] != null && !sSections[i].equals("")) {
                lSections.add(sSections[i]);
            }
        }
        return lSections;
    }


}
