package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAttendanceBookDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.*;

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
     * @param compCode       01
     * @param custId         01
     * @param language       ja
     * @return
     */
    AttendanceDateInfoDTO selectDateInfo(String dyyyymmdd, String firstDayOfYear, String employeeId, String compCode, String custId, String language);

    /**
     * 表示時間を取得する
     *
     * @param dyyyymmdd      2020/05/14
     * @param month          05
     * @param firstDayOfYear 2020/01/01
     * @param employeeId     34370889
     * @param compCode       01
     * @param custId         01
     * @param language       ja
     * @return
     */
    AttendanceDateInfoDTO selectTargetDateInfo(String dyyyymmdd,String month, String firstDayOfYear, String employeeId, String compCode, String custId, String language);


    /**
     * 年次休暇付与日数と付与時間
     *
     * @param dyyyymmdd   2020/05/14
     * @param employeeId  34370889
     * @param preMonthDay 2020/04/01
     * @param nextYearDay 2021/04/01
     * @param compCode    01
     * @param custId      01
     * @return
     */
    AttendanceEndueTimeInfoDTO selectEndueTimeInfo(String dyyyymmdd, String employeeId, String preMonthDay, String nextYearDay, String compCode, String custId);


    /**
     * 出勤簿リスト
     *
     * @param employeeId  34370889
     * @param curMonthDay 2020/05/01
     * @param nextYearDay 2021/04/01
     * @param compCode    01
     * @param custId      01
     * @param list        出勤簿月単位集計項目
     * @return
     */
    List<AttendanceBookDTO> selectAttendanceBookList(String employeeId, String curMonthDay, String nextYearDay, String compCode, String custId, List<String> list);


    /**
     * 摘要編集
     *
     * @param employeeId     34370889
     * @param modifieruserId 46402406
     * @param yearLastDay    2020/12/31
     * @param comment        contentmsg
     * @param compCode       01
     * @param custId         01
     */
    void updateComment(String employeeId, String modifieruserId, String yearLastDay, String comment, String compCode, String custId);


    /**
     * コメント　検索
     *
     * @param employeeId
     * @param yearLastDay
     * @param compCode    01
     * @param custId      01
     * @return
     */
    AttendanceBookCommentDTO selectComment(String employeeId, String yearLastDay, String compCode, String custId);


    /**
     * 出勤簿月単位集計項目 データ部取得クエリ構築
     *
     * @param items       TMG_ATTENDANCEITEMS
     * @param displayLine 5
     * @return
     */
    List<MastGenericDetailDTO> selectTotalDataQueryList(String items, int displayLine);


    /**
     * 出勤簿のヘッダ部情報（氏名、所属）等
     *
     * @param employeeId
     * @param queryMonthDay
     * @param nextYearDay
     * @param compCode
     * @param custId
     * @return
     */
    AttendanceBookEmpDTO selectEmployeesBasicInfo(String employeeId, String queryMonthDay, String nextYearDay, String compCode, String custId);

}
