package jp.smartcompany.job.modules.tmg.attendanceBook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 名称マスタ明細データ
 * @objectSource mast_generic_detail
 * @date 2020/05/21
 **/
@Getter
@Setter
@ToString
public class MastGenericDetailDTO {

    private String mgd_csparechar1;
    private String mgd_csparechar2;
    private String col_name;


}
