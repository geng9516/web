package jp.smartcompany.admin.appmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MastAppDTO implements Serializable {

    private static final long serialVersionUID = 5489976098618866643L;

    private Long id;
    private String objectId;
    private String siteId;
    private String appId;
    private String subAppId;
    private String screenId;
    private String buttonId;
    private String name;
    private String type;
    private String templateId;
    private String url;
    private String icon;
    private String remark;
    private Long sort;
    private String systemId;
    private String domainId;
    private String helpDocUrl;
    private String permissionType;
    private String autoUpload;
    private String defaultTargetUser;
    private String baseDateType;
    private String parentId;
    @JsonIgnore
    private String appLevel;

}
