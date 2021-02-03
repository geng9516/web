package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity;

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
@TableName("MAST_SEARCH_WHERE")
@KeySequence("HIST_SEARCH_WHERE_SEQ")
public class MastSearchWhereDO implements Serializable {

    private static final long serialVersionUID = -2568207071401194143L;

    /**
     * IDカラム
     */
    @TableId
    private Long mswId;
    /**
     * 顧客コード
     */
    private String mswCcsutomerid;
    /**
     * テーブルID
     */
    private String mswCtableid;
    /**
     * カラムID
     */
    private String mswCcolumnid;
    /**
     *  並び順
     */
    private Integer mswNseq;
    /**
     * 社員情報フラグ'
     */
    private String mswCemployee;
    /**
     *　最終更新者
     */
    private String mswCmodifieruserid;
    /**
     *　最終更新日
     */
    private Date mswDmodifieddate;
    /**
     * バージョンNo
     */
    @Version
    private Long versionno;

}
