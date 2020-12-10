package jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employ_mail")
@KeySequence("T_EMPLOY_MAIL_SEQ")
public class EmployMailDO {

   @TableId
   private Long tmaId;
   private String tmaEmpId;
   private String tmaEmpName;
   private String tmaEmail;
   private Date tmaStartDate;
   private Date tmaEndDate;
   private String tmaCreatedBy;
   private String tmaUpdatedBy;
   private Date tmaCreateTime;
   private Date tmaUpdateTime;
   @Version
   private Long version;

}
