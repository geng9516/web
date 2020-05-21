package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAttendanceBookDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceBookDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceEndueTimeInfoDTO;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * [勤怠]出勤簿情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgAttendanceBookService extends IService<TmgAttendanceBookDO> {

    /**
     * ディフォルト表示時間を取得する
     *
     * @param dyyyymmdd      2020/05/14
     * @param firstDayOfYear 2020/01/01
     * @param employeeId     34370889
     * @return
     */
    AttendanceDateInfoDTO selectDateInfo(String dyyyymmdd, String firstDayOfYear, String employeeId);


    /**
     * 年次休暇付与日数と付与時間
     *
     * @param dyyyymmdd   2020/05/14
     * @param employeeId  34370889
     * @param preMonthDay 2020/04/01
     * @param nextYearDay 2021/04/01
     * @return
     */
    AttendanceEndueTimeInfoDTO selectEndueTimeInfo(String dyyyymmdd, String employeeId, String preMonthDay, String nextYearDay);


    /**
     * 出勤簿リスト
     *
     * @param employeeId  34370889
     * @param curMonthDay 2020/05/01
     * @param nextYearDay 2021/04/01
     * @return
     */
    List<AttendanceBookDTO> selectAttendanceBookList(String employeeId, String curMonthDay, String nextYearDay);


    /**
     * 摘要編集
     *
     * @param employeeId     34370889
     * @param modifieruserId 46402406
     * @param yearLastDay    2020/12/31
     * @param comment        contentmsg
     * @return true:success    false:fail
     */
    boolean updateComment(String employeeId, String modifieruserId, String yearLastDay, String comment);


    /**
     * コメント　検索
     *
     * @param employeeId
     * @param yearLastDay
     * @return
     */
    HashMap<String, String> selectComment(String employeeId, String yearLastDay);

}
