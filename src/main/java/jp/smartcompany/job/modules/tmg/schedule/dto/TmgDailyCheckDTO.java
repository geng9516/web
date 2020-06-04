package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author 陳毅力
 * @description 日別情報DTO
 * @objectSource
 * @Date 2020/06/02
 **/
@Getter
@Setter
@ToString
public class TmgDailyCheckDTO {

    private String tda_ccustomerid;
    private String tda_ccompanyid;
    private String tda_cemployeeid;
    private String tda_cmodifieruserid;
    private String tda_cmodifierprogramid;
    private Date tda_dyyyymmdd;
    private String tda_cworkingid_p;
    private int tda_nopen_p;
    private int tda_nclose_p;
    private int tda_nrestopen_p;
    private int tda_nrestclose_p;
    private int tda_nopen_n;
    private int tda_nclose_n;
    private int tda_nrestopen_n;
    private int tda_nrestclose_n;
    private int tda_cmodifieruserid_n;
    private Date tda_dmodifiedDate_n;
    private String tda_cworkingid_r;
    private String tda_cbusinesstripid_r;
    private int tda_nopen_r;
    private int tda_nclose_r;
    private int tda_nrestopen_r;
    private int tda_nrestclose_r;
    private String tda_cmodifieruserid_r;
    private Date tda_dmodifiedDate_r;
    private String tda_cbusinesstripid_p;
    private String tda_ccomment_p;
    private String tda_cpatternid;

}
