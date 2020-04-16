package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * [ﾏｽﾀ]出納員コードマスタ
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dt_umstoin")
public class DtUmstoinDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 会社区分
         */
                @TableId(value = "ccompkb", type = IdType.AUTO)
                private String ccompkb;

        /**
         * 給与体系区分                        00固定
         */
    @TableField("cqtaikeikb")
        private String cqtaikeikb;

        /**
         * 適用開始年月                        yyyy/mm/01
         */
    @TableField("dstart")
        private Date dstart;

        /**
         * 適用終了年月                        yyyy/mm/末日
         */
    @TableField("dend")
        private Date dend;

        /**
         * 更新ﾕｰｻﾞ)端末id
         */
    @TableField("cmnclient")
        private String cmnclient;

        /**
         * 更新ﾕｰｻﾞ)会社区分
         */
    @TableField("cmncomp")
        private String cmncomp;

        /**
         * 更新ﾕｰｻﾞ)ﾕｰｻﾞid
         */
    @TableField("cmnuser")
        private String cmnuser;

        /**
         * 更新ﾕｰｻﾞ)更新日時
         */
    @TableField("dmndate")
        private Date dmndate;

        /**
         * 区分
         */
    @TableField("code")
        private String code;

        /**
         * 名称
         */
    @TableField("name")
        private String name;

        /**
         * 部局コード
         */
    @TableField("bkyk_cde")
        private String bkykCde;


        }