package jp.smartcompany.job.modules.tmg.attendanceBook;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.core.service.ITmgAttendanceBookService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.*;
import jp.smartcompany.job.modules.tmg.attendanceBook.vo.AttendanceBookHolidayInfoVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陳毅力
 * @description 出勤簿クラス
 * @objectSource
 * @date 2020/05/20
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AttendanceBookBean {

    private final Logger logger = LoggerFactory.getLogger(AttendanceBookBean.class);
    private final PsDBBean psDBBean;
    private final ITmgAttendanceBookService iTmgAttendanceBookService;

    public static final String BEAN_DESC = "AttendanceBook";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>出勤簿月単位集計情報表示項目</b>"を表すグループIDです
     */
    public static final String Cs_MGD_DISPATTENDANCEITEMS = "TMG_ATTENDANCEITEMS";
    /**
     * 行番号：5
     */
    private static final int DISP_LINE_5 = 5;

    private final String DYYYYMMDD = "yyyy/MM/dd";
    private final String DYYYYMM = "yyyy/MM";

    /**
     * ディフォルト表示時間を取得する
     *
     * @param dyyyymmdd  2020/05/14
     * @param employeeId 34370889
     * @return
     */
    public AttendanceDateInfoDTO selectDateInfo(String dyyyymmdd, String employeeId) {

        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("社員IDは空です");
            return null;
        }
        if (ObjectUtil.isNull(dyyyymmdd) || ObjectUtil.isEmpty(dyyyymmdd)) {
            dyyyymmdd = DateUtil.format(new java.util.Date(), DYYYYMMDD);
        }

        String firstDayOfYear = DateUtil.format(DateUtil.beginOfYear(DateUtil.parse(dyyyymmdd)), DYYYYMMDD);
        String compCode = psDBBean.getCompCode();
        String custId = psDBBean.getCustID();
        String language = psDBBean.getLanguage();
        AttendanceDateInfoDTO result = iTmgAttendanceBookService.selectDateInfo(dyyyymmdd, firstDayOfYear, employeeId, compCode, custId, language);

        return result;
    }


    /**
     * 年次休暇付与日数と付与時間
     *
     * @param dyyyymmdd   2020/05/14
     * @param employeeId  34370889
     * @param preYearDay  2020
     * @param nextYearDay 04
     * @param compCode    01
     * @param custId      01
     * @return
     */
    private AttendanceEndueTimeInfoDTO selectEndueTimeInfo(String dyyyymmdd, String employeeId, String preYearDay, String nextYearDay, String compCode, String custId) {

        AttendanceEndueTimeInfoDTO results = iTmgAttendanceBookService.selectEndueTimeInfo(dyyyymmdd, employeeId, preYearDay, nextYearDay, compCode, custId);

        return results;
    }


    /**
     * 摘要編集
     *
     * @param employeeId     34370889
     * @param modifieruserId 46402406
     * @param year           2020
     * @param comment        contentmsg
     * @return true:success    false:fail
     */
    @Transactional(rollbackFor = GlobalException.class)
    public boolean updateComment(String employeeId, String modifieruserId, String year, String comment) {

        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId) || ObjectUtil.isNull(modifieruserId) || ObjectUtil.isEmpty(modifieruserId)) {
            logger.error("社員IDまたは更新者IDは空です");
            return false;
        }
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        if (ObjectUtil.isNull(comment) || ObjectUtil.isEmpty(comment)) {
            logger.error("コメントは空です");
            return false;
        }
        if (comment.length() > 600) {
            logger.error("コメントの長さは最大のサイズを超える");
            return false;
        }

        //2020/12/31
        String yearLastDay = year + "/12/31";
        String compCode = psDBBean.getCompCode();
        String custId = psDBBean.getCustID();
        iTmgAttendanceBookService.updateComment(employeeId, modifieruserId, yearLastDay, comment, compCode, custId);
        return true;
    }

    /**
     * コメント　検索
     *
     * @param employeeId 34370889
     * @param year       2020
     * @param compCode   01
     * @param custId     01
     * @return
     */
    private AttendanceBookCommentDTO selectComment(String employeeId, String year, String yearLastDay, String compCode, String custId) {

        AttendanceBookCommentDTO attendanceBookCommentDTO = iTmgAttendanceBookService.selectComment(employeeId, yearLastDay, compCode, custId);
        attendanceBookCommentDTO.setDyyyy(year);
        attendanceBookCommentDTO.setEmployeeId(employeeId);

        return attendanceBookCommentDTO;
    }

    /**
     * 出勤簿月単位集計項目 データ部取得クエリ構築
     *
     * @return
     */
    private List<String> selectTotalDataQueryList() {
        List<MastGenericDetailDTO> mastGenericDetailDTOList = iTmgAttendanceBookService.selectTotalDataQueryList(Cs_MGD_DISPATTENDANCEITEMS, DISP_LINE_5);
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < mastGenericDetailDTOList.size(); i++) {
            MastGenericDetailDTO mastGenericDetailDTO = mastGenericDetailDTOList.get(i);
            result.add(mastGenericDetailDTO.getMgd_csparechar1() + " AS " + mastGenericDetailDTO.getCol_name());
        }
        return result;

    }


    /**
     * 出勤簿リスト
     *
     * @param employeeId 　　34370889
     * @param year       2020
     * @param month      04
     * @return
     */
    public List<AttendanceBookDTO> selectAttendanceBookList(String employeeId, String year, String month) {
        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("社員IDは空です");
            return null;
        }
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        if (ObjectUtil.isNull(month) || ObjectUtil.isEmpty(month)) {
            month = DateUtil.thisMonth() + "";
        }
        if (month.length() == 1) {
            month = "0" + month;
        }

        //2020/05
        String queryMonth = year + "/" + month;
        String queryMonthDay = queryMonth + "/01";
        //2021/04/01
        String nextYearDay = DateUtil.format(DateUtil.parse(queryMonth, DYYYYMM).offset(DateField.MONTH, 12), DYYYYMM) + "/01";
        String compCode = psDBBean.getCompCode();
        String custId = psDBBean.getCustID();
        List<String> results = this.selectTotalDataQueryList();
        List<AttendanceBookDTO> attendanceBookDTOList = iTmgAttendanceBookService.selectAttendanceBookList(employeeId, queryMonthDay, nextYearDay, compCode, custId, results);

        return attendanceBookDTOList;

    }

    /**
     * 出勤簿のヘッダ部情報（氏名、所属）等
     *
     * @param employeeId 34370889
     * @param year       2020
     * @param month      04
     * @return
     */
    public AttendanceBookEmpDTO selectEmployeesBasicInfo(String employeeId, String year, String month) {
        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("社員IDは空です");
            return null;
        }
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        if (ObjectUtil.isNull(month) || ObjectUtil.isEmpty(month)) {
            month = DateUtil.thisMonth() + "";
        }
        if (month.length() == 1) {
            month = "0" + month;
        }

        String queryMonth = year + "/" + month;
        String queryMonthDay = queryMonth + "/01";
        String nextYearDay = DateUtil.format(DateUtil.parse(queryMonth, DYYYYMM).offset(DateField.MONTH, 12), DYYYYMM) + "/01";
        String compCode = psDBBean.getCompCode();
        String custId = psDBBean.getCustID();
        AttendanceBookEmpDTO attendanceBookEmpDTO = iTmgAttendanceBookService.selectEmployeesBasicInfo(employeeId, queryMonthDay, nextYearDay, compCode, custId);
        attendanceBookEmpDTO.setDyyyymmdd(queryMonthDay);
        attendanceBookEmpDTO.setEmployeeId(employeeId);
        return attendanceBookEmpDTO;
    }


    /**
     * 年次休暇付与日数, 年次休暇付与日, 摘要
     *
     * @param employeeId
     * @param year
     * @param month
     * @return
     */
    public AttendanceBookHolidayInfoVO queryHolidayInfo(String employeeId, String year, String month) {

        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("社員IDは空です");
            return null;
        }
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        if (ObjectUtil.isNull(month) || ObjectUtil.isEmpty(month)) {
            month = DateUtil.thisMonth() + "";
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        String dyyyymmdd = DateUtil.format(new java.util.Date(), DYYYYMMDD);
        //2020/05
        String queryMonth = year + "/" + month;
        //2020/04/01
        String preYearDay = DateUtil.format(DateUtil.parse(queryMonth, DYYYYMM).offset(DateField.MONTH, -1), DYYYYMM) + "/01";
        //2021/04/01
        String nextYearDay = DateUtil.format(DateUtil.parse(queryMonth, DYYYYMM).offset(DateField.MONTH, 12), DYYYYMM) + "/01";
        //2020/12/31
        String yearLastDay = year + "/12/31";
        String compCode = psDBBean.getCompCode();
        String custId = psDBBean.getCustID();

        // 年次休暇付与日数, 年次休暇付与日
        AttendanceEndueTimeInfoDTO attendanceEndueTimeInfoDTO = this.selectEndueTimeInfo(dyyyymmdd, employeeId, preYearDay, nextYearDay, compCode, custId);
        //コメント
        AttendanceBookCommentDTO attendanceBookCommentDTO = this.selectComment(employeeId, year, yearLastDay, compCode, custId);
        //変える
        AttendanceBookHolidayInfoVO attendanceBookHolidayInfoVO = new AttendanceBookHolidayInfoVO(attendanceEndueTimeInfoDTO, attendanceBookCommentDTO);
        attendanceBookHolidayInfoVO.setDataTime(queryMonth);
        attendanceBookHolidayInfoVO.setEmployeeId(employeeId);
        return attendanceBookHolidayInfoVO;

    }


}
