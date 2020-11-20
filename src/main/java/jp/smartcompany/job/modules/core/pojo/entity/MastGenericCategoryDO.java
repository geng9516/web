package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mast_generic_category")
@KeySequence("MAST_GENERIC_CATEGORY_SEQ")
public class MastGenericCategoryDO implements Serializable {

    private static final long serialVersionUID = -887293596444178096L;
    @TableId
    private Long mgcId;

    private String mgcCcustomerid;

    private String mgcCcategoryid;

    private String mccCcategoryname;

    private String mgcCmodifieruserid;

    private Date mgcDmodifieddate;
    @Version
    private Long versionno;

}
