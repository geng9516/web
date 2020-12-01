package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 名称マスタ明細データ
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
@TableName("mast_generic_detail")
@KeySequence("MAST_GENERIC_DETAIL_SEQ")
public class MastGenericDetailDO implements Serializable {

    private static final long serialVersionUID = -5905220289990712995L;

    /**
         * idカラム
         */
                @TableId(value = "mgd_id")
                private Long mgdId;

        /**
         * 顧客コード
         */
    @TableField("mgd_ccustomerid")
        private String mgdCcustomerid;

        /**
         * 法人コード
         */
    @TableField("mgd_ccompanyid_ck_fk")
        private String mgdCcompanyidCkFk;

        /**
         * 名称マスタコード
         */
    @TableField("mgd_cgenericgroupid")
        private String mgdCgenericgroupid;

        /**
         * 明細データコード
         */
    @TableField("mgd_cgenericdetailid_ck")
        private String mgdCgenericdetailidCk;

        /**
         * マスタコード
         */
    @TableField("mgd_cmastercode")
        private String mgdCmastercode;

        /**
         * 言語区分
         */
    @TableField("mgd_clanguage_ck")
        private String mgdClanguageCk;

        /**
         * 開始日
         */
    @TableField("mgd_dstart_ck")
        private Date mgdDstartCk;

        /**
         * 終了日
         */
    @TableField("mgd_dend")
        private Date mgdDend;

        /**
         * 最終更新者
         */
    @TableField("mgd_cmodifieruserid")
        private String mgdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mgd_dmodifieddate")
        private Date mgdDmodifieddate;

        /**
         * 明細データ内容
         */
    @TableField("mgd_cgenericdetaildesc")
        private String mgdCgenericdetaildesc;

        /**
         * 明細データ内容（日本語）
         */
    @TableField("mgd_cgenericdetaildescja")
        private String mgdCgenericdetaildescja;

        /**
         * 明細データ内容（英語）
         */
    @TableField("mgd_cgenericdetaildescen")
        private String mgdCgenericdetaildescen;

        /**
         * 明細データ内容（中国語）
         */
    @TableField("mgd_cgenericdetaildescch")
        private String mgdCgenericdetaildescch;

        /**
         * 明細データ内容（予備１）
         */
    @TableField("mgd_cgenericdetaildesc01")
        private String mgdCgenericdetaildesc01;

        /**
         * 明細データ内容（予備２）
         */
    @TableField("mgd_cgenericdetaildesc02")
        private String mgdCgenericdetaildesc02;

        /**
         * 明細データ内容予備1（日本語）
         */
    @TableField("mgd_csparedesc1ja")
        private String mgdCsparedesc1ja;

        /**
         * 明細データ内容予備1（別名用ダミー）
         */
    @TableField("mgd_csparedesc1")
        private String mgdCsparedesc1;

        /**
         * 明細データ内容予備1（英語）
         */
    @TableField("mgd_csparedesc1en")
        private String mgdCsparedesc1en;

        /**
         * 明細データ内容予備1（中国語）
         */
    @TableField("mgd_csparedesc1ch")
        private String mgdCsparedesc1ch;

        /**
         * 明細データ内容予備1（予備１）
         */
    @TableField("mgd_csparedesc101")
        private String mgdCsparedesc101;

        /**
         * 明細データ内容予備1（予備２）
         */
    @TableField("mgd_csparedesc102")
        private String mgdCsparedesc102;

        /**
         * 明細データ内容予備2（別名用ダミー）
         */
    @TableField("mgd_csparedesc2")
        private String mgdCsparedesc2;

        /**
         * 明細データ内容予備2（日本語）
         */
    @TableField("mgd_csparedesc2ja")
        private String mgdCsparedesc2ja;

        /**
         * 明細データ内容予備2（英語）
         */
    @TableField("mgd_csparedesc2en")
        private String mgdCsparedesc2en;

        /**
         * 明細データ内容予備2（中国語）
         */
    @TableField("mgd_csparedesc2ch")
        private String mgdCsparedesc2ch;

        /**
         * 明細データ内容予備2（予備１）
         */
    @TableField("mgd_csparedesc201")
        private String mgdCsparedesc201;

        /**
         * 明細データ内容予備2（予備２）
         */
    @TableField("mgd_csparedesc202")
        private String mgdCsparedesc202;

        /**
         * 明細データ内容予備3（別名用ダミー）
         */
    @TableField("mgd_csparedesc3")
        private String mgdCsparedesc3;

        /**
         * 明細データ内容予備3（日本語）
         */
    @TableField("mgd_csparedesc3ja")
        private String mgdCsparedesc3ja;

        /**
         * 明細データ内容予備3（英語）
         */
    @TableField("mgd_csparedesc3en")
        private String mgdCsparedesc3en;

        /**
         * 明細データ内容予備3（中国語）
         */
    @TableField("mgd_csparedesc3ch")
        private String mgdCsparedesc3ch;

        /**
         * 明細データ内容予備3（予備１）
         */
    @TableField("mgd_csparedesc301")
        private String mgdCsparedesc301;

        /**
         * 明細データ内容予備3（予備２）
         */
    @TableField("mgd_csparedesc302")
        private String mgdCsparedesc302;

        /**
         * 明細データ内容予備4（別名用ダミー）
         */
    @TableField("mgd_csparedesc4")
        private String mgdCsparedesc4;

        /**
         * 明細データ内容予備4（日本語）
         */
    @TableField("mgd_csparedesc4ja")
        private String mgdCsparedesc4ja;

        /**
         * 明細データ内容予備4（英語）
         */
    @TableField("mgd_csparedesc4en")
        private String mgdCsparedesc4en;

        /**
         * 明細データ内容予備4（中国語）
         */
    @TableField("mgd_csparedesc4ch")
        private String mgdCsparedesc4ch;

        /**
         * 明細データ内容予備4（予備１）
         */
    @TableField("mgd_csparedesc401")
        private String mgdCsparedesc401;

        /**
         * 明細データ内容予備4（予備２）
         */
    @TableField("mgd_csparedesc402")
        private String mgdCsparedesc402;

        /**
         * 明細データ内容予備5（別名用ダミー）
         */
    @TableField("mgd_csparedesc5")
        private String mgdCsparedesc5;

        /**
         * 明細データ内容予備5（日本語）
         */
    @TableField("mgd_csparedesc5ja")
        private String mgdCsparedesc5ja;

        /**
         * 明細データ内容予備5（英語）
         */
    @TableField("mgd_csparedesc5en")
        private String mgdCsparedesc5en;

        /**
         * 明細データ内容予備5（中国語）
         */
    @TableField("mgd_csparedesc5ch")
        private String mgdCsparedesc5ch;

        /**
         * 明細データ内容予備5（予備１）
         */
    @TableField("mgd_csparedesc501")
        private String mgdCsparedesc501;

        /**
         * 明細データ内容予備5（予備２）
         */
    @TableField("mgd_csparedesc502")
        private String mgdCsparedesc502;

        /**
         * 予備文字１
         */
    @TableField("mgd_csparechar1")
        private String mgdCsparechar1;

        /**
         * 予備文字2
         */
    @TableField("mgd_csparechar2")
        private String mgdCsparechar2;

        /**
         * 予備文字3
         */
    @TableField("mgd_csparechar3")
        private String mgdCsparechar3;

        /**
         * 予備文字4
         */
    @TableField("mgd_csparechar4")
        private String mgdCsparechar4;

        /**
         * 予備文字5
         */
    @TableField("mgd_csparechar5")
        private String mgdCsparechar5;

        /**
         * 予備数字1
         */
    @TableField("mgd_nsparenum1")
        private Long mgdNsparenum1;

        /**
         * 予備数字2
         */
    @TableField("mgd_nsparenum2")
        private Long mgdNsparenum2;

        /**
         * 予備数字3
         */
    @TableField("mgd_nsparenum3")
        private Long mgdNsparenum3;

        /**
         * 予備数字4
         */
    @TableField("mgd_nsparenum4")
        private Long mgdNsparenum4;

        /**
         * 予備数字5
         */
    @TableField("mgd_nsparenum5")
        private Long mgdNsparenum5;

        /**
         * 予備日付1
         */
    @TableField("mgd_dsparedate1")
        private Date mgdDsparedate1;

        /**
         * 予備日付2
         */
    @TableField("mgd_dsparedate2")
        private Date mgdDsparedate2;

        /**
         * 予備日付3
         */
    @TableField("mgd_dsparedate3")
        private Date mgdDsparedate3;

        /**
         * 予備日付4
         */
    @TableField("mgd_dsparedate4")
        private Date mgdDsparedate4;

        /**
         * 予備日付5
         */
    @TableField("mgd_dsparedate5")
        private Date mgdDsparedate5;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;

}