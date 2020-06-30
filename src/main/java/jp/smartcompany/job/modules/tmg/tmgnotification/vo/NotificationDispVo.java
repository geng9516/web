package jp.smartcompany.job.modules.tmg.tmgnotification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NotificationDispVo {

    private int totalCount;//总记录数
    private int pageSize;//表示当前页的总记录数
    private int totalPage;//总页数
    private int currPage;//当前页
    private List<NotificationDispDetailVo> list;
}
