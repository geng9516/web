package jp.smartcompany.framework.component.logic;

import jp.smartcompany.framework.component.entity.EmployeeInfoSearchEntity;

import java.util.List;

public interface EmployeeInfoSearchLogic {

    List<EmployeeInfoSearchEntity> searchEmpList(String searchWord,
                                                 String searchWordConve,
                                                 String searchWordEnglish,
                                                 String searchRange,
                                                 String searchFlg,
                                                 String companyId,
                                                 String targetComp,
                                                 // hidden⇔requestとして保持し続けるオブジェクト (本務兼務区分)
                                                 String ifKeyorAdditionalRole,
                                                 String targetDept,
                                                 Integer type);

}
