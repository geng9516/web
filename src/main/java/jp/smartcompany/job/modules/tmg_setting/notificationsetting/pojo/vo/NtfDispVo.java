package jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo;

import jp.smartcompany.job.modules.tmg.tmgnotification.vo.TypeChildrenVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class NtfDispVo {

    private String ntfType;//申請区分
    private String ntfTypeId;

    private String startDate;//開始日
    private String endDate;//終了日

    private String ntfGroup;//親区分
    private String ntfGroupId;

    private String siteId;//可能サイト
    private String site;

    private String work;//反映先
    private String workId;

    private String typeGroup;//申請種類
    private String typeGroupId;
    private String timeRange;//申請期間タイプ
    private String timeRangeId;

    private String dispTypeId;
    private TypeChildrenVo dispType;

    private List<RELVo> workTypeInfo = new ArrayList<>();
    private String workType;
    private String limitCheck;//使用有無
    private String limitNum;//取得上限値
    private String limitCount;//取得可能回数
    private String limitRange; //取得可能期

    private String timeTypeId;//種類
    private String timeType;//種類
    private String appLevel;//決裁レベル
    private String necessaryFile;//ファイル必須
    private String necessaryComment;//事由必須
    private String commentD;//説明文
    private String rangeType;//期間区分
    private String imart;//IMART

}
