package jp.smartcompany.admin.appmanager.logic;

import jp.smartcompany.admin.appmanager.dto.AppSortDTO;
import jp.smartcompany.admin.appmanager.dto.MastAppDTO;
import jp.smartcompany.admin.appmanager.dto.MastAppTreeDTO;
import jp.smartcompany.admin.appmanager.dto.MastTemplateDTO;
import java.util.List;

public interface AppManagerMainLogic {

    List<MastAppTreeDTO> getAppList();

    List<MastTemplateDTO> getTemplateList();

    String updateMenuList(MastAppDTO paramList);

    void updateMenuSort(List<AppSortDTO> list);

}
