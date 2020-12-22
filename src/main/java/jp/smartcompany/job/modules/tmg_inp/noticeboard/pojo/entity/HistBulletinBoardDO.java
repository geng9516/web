package jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity;

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
@TableName("hist_bulletinboard")
@KeySequence("HIST_BULLETINBOARD_SEQ")
public class HistBulletinBoardDO implements Serializable {

    private static final long serialVersionUID = 7930041822403037380L;
    @TableId
    private Long hbId;
    // 顧客コード
    private String hbCcustomerid;
    // 法人コード
    private String hbCcompanyid;
    // 掲示開始日
    private String hbDdateofannouncement;
    // 掲示終了日
    private String hbDdateofexpire;
    // タイトル
    private String hbCtitle;
    // 掲示内容
    private String hbCcontents;
    // 掲示者ユーザID
    private String hbCmnuser;
    // 掲示者氏名
    private String hbCmnusername;
    // 先頭表示フラグ
    private String hbCheaddisp;
    // 確定フラグ
    private String hbCfix;
    // 最終更新者
    private String hbCmodifieruserid;
    // 最終更新日
    private Date hbDmodifieddate;
    @Version
    private Long versionno;

}
