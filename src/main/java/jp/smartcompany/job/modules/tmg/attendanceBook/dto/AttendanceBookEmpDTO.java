package jp.smartcompany.job.modules.tmg.attendanceBook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 氏名所属等情報
 * @objectSource
 * @date 2020/05/22
 **/
@Getter
@Setter
@ToString
public class AttendanceBookEmpDTO {
    private String employeeId;
    private String empname;
    private String secname;
    private String worktypename;
    private String postname;
    private String dyyyymmdd;

}
