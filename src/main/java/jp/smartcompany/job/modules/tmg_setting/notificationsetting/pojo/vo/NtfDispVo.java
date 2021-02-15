package jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo;

import jp.smartcompany.job.modules.tmg.tmgnotification.vo.TypeChildrenVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NtfDispVo {

    private String ntfType;//申請区分
    private String ntfTypeId;

    private String startDate;//開始日
        private String endDate;//終了日

    private String group;//親区分
    private String ntfGroupId;

    private String siteId;
    private String site;//可能サイト

    private String work;//反映先
    private String workId;

    private String typeGroup;//申請種類
    private String typeGroupId;

    private String dispTypeId;
    private TypeChildrenVo dispType;

    private List<RELVo> workTypeInfo;
    private String workType;

    private String timeType;//種類
    private String appLevel;//決裁レベル
    private String necessaryFile;//ファイル必須
    private String necessaryComment;//事由必須
    private String commentD;//説明文
    private String rangeType;//期間区分
    private String imart;//IMART

}
