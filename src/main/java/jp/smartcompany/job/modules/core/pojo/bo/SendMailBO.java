package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class SendMailBO {
    /**
     * 接收人邮箱地址
      */
    private String toAddress;
    /**
     * 接收人姓名
     */
    private String empName;
    /**
     * 基准日 没有可不填
     */
    private Date standardDate;
    /**
     * 接收人的员工编号
     */
    private String empId;
    /**
     * 额外的邮件内容
     */
    private Map<String,Object> extraContent;

}
