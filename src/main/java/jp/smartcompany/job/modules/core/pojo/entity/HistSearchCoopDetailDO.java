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
 * コンテンツ横断自由条件検索連携詳細テーブル
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
@TableName("hist_search_coop_detail")
public class HistSearchCoopDetailDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hsd_id", type = IdType.AUTO)
                private Long hsdId;

        /**
         * データid
         */
    @TableField("hsd_ndata_id")
        private Long hsdNdataId;

        /**
         * 検索条件顧客コード
         */
    @TableField("hsd_ccond_customerid")
        private String hsdCcondCustomerid;

        /**
         * 検索条件ユーザid
         */
    @TableField("hsd_ccond_userid")
        private String hsdCcondUserid;

        /**
         * 最終更新者
         */
    @TableField("hsd_cmodifieruserid")
        private String hsdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hsd_dmodifieddate")
        private Date hsdDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }