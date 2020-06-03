package jp.smartcompany.job.modules.core.util;

import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.util.appcontrol.TopPageInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PsSession implements Serializable {

    private static final long serialVersionUID = 1L;
    // 当前登录用户账号
    private String loginAccount;
    // 法人code
    private String loginCustomer;
    // 公司code
    private String loginCompany;
    // 当前登录用户名
    private String loginUser;
    // 账号对应的员工表里的员工id
    private String loginEmployee;
    // 语言
    private String language;
    // 汉字名称
    private String loginKanjiName;
    // 当前用户所属部门
    private List<Designation> loginDesignation;
    // 当前用户所属用户组
    private Map<String, List<LoginGroupBO>> loginGroups;
    //  ログインユーザの基点組織情報
    private Map<String, Map<String, String>> loginBaseSection;
    // 基点組織情報キャッシュ(基準日毎)
    private Map<String, Map<String, String>> loginGroupBaseSection;
    // ログインユーザのアプリケーション起動権限判定結果 ※本番
    private TopPageInfo loginAppPermission;

}
