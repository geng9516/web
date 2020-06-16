package jp.smartcompany.job.modules.tmg.tmgnotification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NotificationDispVo {

    private int count;
    private List<NotificationDispDetailVo> list;
}
