package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.IMastOrganisationService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;

public class TmgSearchRangeUtil  {

    private PsDBBean psDbBean = SpringUtil.getBean("psDBBean");

    /**
     * 社員リストデータのセッションキーを作成
     * @param psDate
     * @param psSection
     * @param pRequestHash
     * @return
     */
    public String getEmpListCondition(String psDate, String psSection, boolean pbEmployee, Hashtable pRequestHash) {
        // 初期化
        psDbBean.setSysControl(pRequestHash);

        return psDate + "_" + psSection + "_" + pbEmployee + "_" + getCurrentApp(pRequestHash);
    }

    /**
     * 現在実行中のアプリケーションIDの取得
     * @param pRequestHash
     * @return
     */
    public String getCurrentApp(Hashtable pRequestHash) {
        return (String)pRequestHash.get("PageName");
    }


}
