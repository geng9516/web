package jp.smartcompany.framework.sysinfo.logic.impl;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.framework.sysinfo.logic.SysInfoLogic;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor
public class SysInfoLogicImpl implements SysInfoLogic {

    private final IMastOrganisationService mastOrganisationService;

    /**
     * <p>
     * <b>上位組織</b>情報取得（組織指定）
     * </p>
     * <div>指定した組織の上位組織リストを返却する。</div>
     *
     * @author t-abe
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param targetDept 組織コード
     * @param searchDate 検索基準日
     * @return 上位組織情報
     */
    @Override
    public List<String> getUpperSectionListDeptForSQL(String custID, String compCode, String targetDept, Date searchDate) {
        return mastOrganisationService.selectHighSection(custID,compCode,targetDept,searchDate);
    }

    @Override
    public List<String> getLowerSectionListDeptForSQL(String custID, String compCode, String targetDept, Date searchDate) {
        return mastOrganisationService.selectLowerSection(custID,compCode,targetDept,searchDate);
    }


    /**
     * <p>
     * 組織階層コードをリストに変換する。
     * </p>
     * <div> カンマ毎、リストに作成する。 <br>
     * "DD|AAA,BBB,CCC" → List[0] = AAA, [1] = BBB, [2] = CCC ／ DDは法人コードなので、カットする。<br>
     * "AAA,BBB,CCC" → List[0] = AAA, [1] = BBB ／ CCCは自組織なので、カットする。<br>
     * </div>
     *
     * @author t-abe
     * @param layeredSection 組織階層コード
     * @return 組織コードList
     * @exception
     */
    private List<String> makeSectionList(String layeredSection) {
        List <String> sectionList = CollUtil.newArrayList();
        int pipe = layeredSection.indexOf("|");
        String[] array;
        if (pipe != -1) {
            // パイプがある
            layeredSection = layeredSection.substring(pipe + 1);
            array = layeredSection.split(",");
        } else {
            // パイプがない
            array = layeredSection.split(",");
        }
        Collections.addAll(sectionList, array);
        if (pipe == -1) {
            // パイプがない
            sectionList.remove(array.length - 1);
        }
        return sectionList;
    }

}
