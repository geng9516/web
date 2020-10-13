package jp.smartcompany.admin.usermanager.dto;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UpdatePersonalDTO {

    /**
     * idカラム
     */
    private Long maId;

    /**
     * 顧客コード
     */
    private String maCcustomerid;

    /**
     * ユーザid
     */
    private String maCuserid;

    /**
     * アカウント
     */
    private String maCaccount;

    /**
     * 有効期間開始日
     */
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date maDstart;

    /**
     * 有効期間終了日
     */
    @JsonFormat(pattern="yyyy/MM/dd")
    private Date maDend;

    /**
     * パスワード間違い回数
     */
    private Integer maNretrycounter;

    /**
     * ロックアウトフラグ
     */
    private Integer maNpasswordlock;



}
