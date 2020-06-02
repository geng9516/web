package jp.smartcompany.job.controller;

import jp.smartcompany.job.modules.tmg.attendanceBook.AttendanceBookBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.vo.AttendanceBookHolidayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description test controller
 * @objectSource
 * @date 2020/05/18
 **/
@RestController
@RequestMapping("sys/attendanceBook")
public class AttendanceBookController {

    @Autowired
    private AttendanceBookBean attendanceBookBean;


    /**
     * ディフォルト表示時間を取得する
     *
     * @param employeeId
     * @param year
     * @return {"msg":"リクエスト成功","code":0,"data":{"mgd_ndefault_month":"4","dispterm_start":"2020/04/01","dispterm_end":"2021/03/01"}}
     */
    @GetMapping("queryDate")
    public AttendanceDateInfoDTO queryDate(@RequestParam("employeeId") String employeeId, @RequestParam("year") String year) {
        return attendanceBookBean.selectDateInfo(employeeId, year);

    }

    /**
     * ディフォルト表示時間を取得する
     *
     * @param employeeId
     * @return {"msg":"リクエスト成功","code":0,"data":{"mgd_ndefault_month":"4","dispterm_start":"2020/04/01","dispterm_end":"2021/03/01"}}
     */
    @GetMapping("defaultDate")
    public AttendanceDateInfoDTO defaultDate(@RequestParam("employeeId") String employeeId) {
        return attendanceBookBean.selectDateInfo(employeeId);

    }

    /**
     * 年次休暇付与日数, 年次休暇付与日, 摘要info
     *
     * @param employeeId
     * @param year
     * @param month
     * @return {"msg":"リクエスト成功","code":0,"data":{"employeeId":"34370889","endueDate":"01月01日","endueDaysHours":"33 5","tmy_comment":"this is a test 20200519","dataTime":"2020/04","queryTime":"2020-05-22 16:21:21"}}
     */
    @GetMapping("queryHolidayInfo")
    @ResponseBody
    public AttendanceBookHolidayInfoVO queryHolidayInfo(@RequestParam("employeeId") String employeeId,
                                                        @RequestParam("year") String year,
                                                        @RequestParam("month") String month) {

        return attendanceBookBean.queryHolidayInfo(employeeId, year, month);
    }

    /**
     * 出勤簿リスト
     *
     * @param employeeId
     * @param year
     * @param month
     * @return
     */
    @GetMapping("attendanceBookList")
    @ResponseBody
    public List<LinkedHashMap<String, String>> attendanceBookList(@RequestParam("employeeId") String employeeId,
                                                                  @RequestParam("year") String year,
                                                                  @RequestParam("month") String month) {

        return attendanceBookBean.selectAttendanceBookList(employeeId, year, month);
    }


    /**
     * 摘要編集
     *
     * @param employeeId
     * @param modifieruserId
     * @param year
     * @param comment
     * @return {"msg":"リクエスト成功","code":0,"data":true}
     */
    @GetMapping("updateComment")
    @ResponseBody
    public boolean updateComment(@RequestParam("employeeId") String employeeId,
                                 @RequestParam("modifieruserId") String modifieruserId,
                                 @RequestParam("year") String year,
                                 @RequestParam("comment") String comment) {
        return attendanceBookBean.updateComment(employeeId, modifieruserId, year, comment);
    }


}
