package jp.smartcompany.job.controller;

import jp.smartcompany.job.modules.tmg.attendanceBook.AttendanceBookBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceBookDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceEndueTimeInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description test controller
 * @objectSource
 * @date 2020/05/18
 **/
@RestController
@RequestMapping("sys/test")
public class SelfController {

    @Autowired
    private AttendanceBookBean attendanceBookBean;


    /**
     * ディフォルト表示時間を取得する
     *
     * @param dyyyymmdd
     * @param employeeId
     * @return
     */
    @GetMapping("defaultDate")
    public AttendanceDateInfoDTO defaultDate(@RequestParam("dyyyymmdd") String dyyyymmdd, @RequestParam("employeeId") String employeeId) {
        return attendanceBookBean.selectDateInfo(dyyyymmdd, employeeId);

    }


    /**
     * 年次休暇付与日数と付与時間
     *
     * @param dyyyymmdd
     * @param employeeId
     * @return
     */
    @GetMapping("endueTimeInfo")
    @ResponseBody
    public AttendanceEndueTimeInfoDTO endueTimeInfo(@RequestParam("dyyyymmdd") String dyyyymmdd,
                                                    @RequestParam("employeeId") String employeeId,
                                                    @RequestParam("year") String year,
                                                    @RequestParam("month") String month) {

        return attendanceBookBean.selectEndueTimeInfo(dyyyymmdd, employeeId, year, month);
    }

    /**
     * 出勤簿リスト
     *
     * @param dyyyymmdd
     * @param employeeId
     * @return
     */
    @GetMapping("attendanceBookList")
    @ResponseBody
    public List<AttendanceBookDTO> attendanceBookList(@RequestParam("dyyyymmdd") String dyyyymmdd,
                                                      @RequestParam("employeeId") String employeeId,
                                                      @RequestParam("year") String year,
                                                      @RequestParam("month") String month) {

        return attendanceBookBean.selectAttendanceBookList(dyyyymmdd, employeeId, year, month);
    }


    /**
     * 摘要編集
     *
     * @param employeeId
     * @param modifieruserId
     * @param year
     * @param comment
     * @return
     */
    @GetMapping("updateComment")
    @ResponseBody
    public boolean updateComment(@RequestParam("employeeId") String employeeId,
                                 @RequestParam("modifieruserId") String modifieruserId,
                                 @RequestParam("year") String year,
                                 @RequestParam("comment") String comment) {
        return attendanceBookBean.updateComment(employeeId, modifieruserId, year, comment);
    }


    /**
     * コメント　検索
     *
     * @param employeeId
     * @param year
     * @return
     */
    @GetMapping("selectComment")
    @ResponseBody
    public HashMap<String, String> selectComment(@RequestParam("employeeId") String employeeId, @RequestParam("year") String year) {
        return attendanceBookBean.selectComment(employeeId, year);
    }

}
