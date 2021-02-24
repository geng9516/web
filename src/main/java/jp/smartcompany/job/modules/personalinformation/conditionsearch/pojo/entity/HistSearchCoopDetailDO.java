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

/**
 * コンテンツ横断自由条件検索連携詳細テーブル
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HIST_SEARCH_COOP_DETAIL")
@KeySequence("HIST_SEARCH_COOP_DETAIL_SEQ")
public class HistSearchCoopDetailDO implements Serializable {

    /**
     * 　IDカラム
     */
    @TableId
    private Long hsdId;
    /**
     *　データID
     */
    private Long hsdNdataId;
    /**
     *　検索条件顧客コード
     */
    private String hsdCcondCustomerid;
    /**
     *　検索条件ユーザID
     */
    private String hsdCcondUserid;
    /**
     *　最終更新者
     */
    private String hsdCmodifieruserid;
    /**
     *　最終更新日
     */
    private Date hsdDmodifieddate;
    /**
     *　バージョンNo
     */
    @Version
    private Long versionno;

}
