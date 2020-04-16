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
 * 掲示板データ
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
@TableName("hist_bulletinboard")
public class HistBulletinboardDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * id
         */
                @TableId(value = "hb_id", type = IdType.AUTO)
                private Long hbId;

        /**
         * 顧客コード
         */
    @TableField("hb_ccustomerid")
        private String hbCcustomerid;

        /**
         * 法人コード
         */
    @TableField("hb_ccompanyid")
        private String hbCcompanyid;

        /**
         * 掲示開始日
         */
    @TableField("hb_ddateofannouncement")
        private Date hbDdateofannouncement;

        /**
         * 掲示終了日
         */
    @TableField("hb_ddateofexpire")
        private Date hbDdateofexpire;

        /**
         * タイトル
         */
    @TableField("hb_ctitle")
        private String hbCtitle;

        /**
         * 掲示内容
         */
    @TableField("hb_ccontents")
        private String hbCcontents;

        /**
         * 添付ファイル名
         */
    @TableField("hb_cfilename")
        private String hbCfilename;

        /**
         * 関連情報リンク
         */
    @TableField("hb_clink")
        private String hbClink;

        /**
         * 掲示者ユーザid
         */
    @TableField("hb_cmnuser")
        private String hbCmnuser;

        /**
         * 掲示者氏名
         */
    @TableField("hb_cmnusername")
        private String hbCmnusername;

        /**
         * 添付ファイル
         */
    @TableField("hb_battach")
        private String hbBattach;

        /**
         * 先頭表示フラグ
         */
    @TableField("hb_cheaddisp")
        private String hbCheaddisp;

        /**
         * 確定フラグ
         */
    @TableField("hb_cfix")
        private String hbCfix;

        /**
         * 最終更新者
         */
    @TableField("hb_cmodifieruserid")
        private String hbCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hb_dmodifieddate")
        private Date hbDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }