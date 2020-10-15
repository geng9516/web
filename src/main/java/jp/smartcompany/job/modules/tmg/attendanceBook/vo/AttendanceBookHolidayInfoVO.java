package jp.smartcompany.job.modules.tmg.attendanceBook.vo;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceBookCommentDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceEndueTimeInfoDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 年次休暇付与日数, 年次休暇付与日, 摘要
 * @objectSource
 * @date 2020/05/22
 **/
@Getter
@Setter
@ToString
public class AttendanceBookHolidayInfoVO {

    /**
     * 職員ID
     */
    private String employeeId;

    /**
     * 　年次休暇付与日
     */
    private String endueDate;

    /**
     * 年次休暇付与日数
     */
    private String endueDaysHours;

    /**
     * 摘要
     */
    private String tmy_comment;

    /**
     * 　データ時間
     */
    private String dataTime;

    /**
     * 　検索時間
     */
    private String queryTime;

    /**
     * データを初期化
     *
     * @param attendanceEndueTimeInfoDTO
     * @param attendanceBookCommentDTO
     */
    public AttendanceBookHolidayInfoVO(AttendanceEndueTimeInfoDTO attendanceEndueTimeInfoDTO, AttendanceBookCommentDTO attendanceBookCommentDTO) {
        if (null != attendanceEndueTimeInfoDTO) {
            this.endueDate = attendanceEndueTimeInfoDTO.getEndueDate();
            this.endueDaysHours = attendanceEndueTimeInfoDTO.getEndueDays() + "日 " + attendanceEndueTimeInfoDTO.getEndueHours() + "時間";
        }
        if (null != attendanceBookCommentDTO) {
            this.tmy_comment = attendanceBookCommentDTO.getTmy_comment();
        }
        this.queryTime = DateUtil.now();
    }

}
