package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TitleVo {
    private String title;
    private String key;
    private String align;
    private List<TitleVo> children;
}
