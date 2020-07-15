package jp.smartcompany.admin.component.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class SelectEmployeesEditDTO implements Serializable {

    private static final long serialVersionUID = -3521897157523355663L;

    /** 選択法人コード */
    private String gsPsSelectedComp;
    /** 固定法人フラグ */
    private String gsPsFixedComp;

    private List<SectionPostRowDTO> empList;

}
