package jp.smartcompany.job.modules.tmg.attendanceBook;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.core.service.ITmgAttendanceBookService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.*;
import jp.smartcompany.job.modules.tmg.attendanceBook.vo.AttendanceBookHolidayInfoVO;
import jp.smartcompany.job.modules.tmg.attendanceBook.vo.AttendanceExistsVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private PsDBBean psDBBean;
    private TmgReferList _referList;
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

    private BeanMap map;
//    private int MONTH_DAY = 1;
    /**
     * 検索結果をJSONへマッピングする際のキー文字列(この文字列をキーとした連想配列が作成される)
     */
    public static final String[] DEFAULT_KEY_ARRAY = {
            "intDay", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve"};

    /**
     * 検索結果のキー文字列(この文字列をキーとした連想配列が作成される)
     */
    public static final String[] ATTENDANCEBOOK_KEY_ARRAY = {"dmm",
            "col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9", "col10", "col11",
            "tmaWorkcontent01", "tmaWorkcontent02", "tmaWorkcontent03", "tmaWorkcontent04",
            "tmaWorkcontent05", "tmaWorkcontent06", "tmaWorkcontent07", "tmaWorkcontent08",
            "tmaWorkcontent09", "tmaWorkcontent10", "tmaWorkcontent11", "tmaWorkcontent12",
            "tmaWorkcontent13", "tmaWorkcontent14", "tmaWorkcontent15", "tmaWorkcontent16",
            "tmaWorkcontent17", "tmaWorkcontent18", "tmaWorkcontent19", "tmaWorkcontent20",
            "tmaWorkcontent21", "tmaWorkcontent22", "tmaWorkcontent23", "tmaWorkcontent24",
            "tmaWorkcontent25", "tmaWorkcontent26", "tmaWorkcontent27", "tmaWorkcontent28",
            "tmaWorkcontent29", "tmaWorkcontent30", "tmaWorkcontent31"};

    /**
     * 画面から入力された実行条件を判定し設定します。
     *
     * @param year
     * @param month
     * @param employeeId
     * @param psDBBean
     */
    public void setExecuteParameters(String year, String month, String employeeId, PsDBBean psDBBean) {
        this.psDBBean = psDBBean;
        this.setReferList();
        if (null != employeeId && !"".equals(employeeId) && (null == psDBBean.getTargetUser() || "".equals(psDBBean.getTargetUser()))) {
            //目標対象
            psDBBean.setTargetUser(employeeId);
        }
    }

    /**
     * ディフォルト表示時間を取得する
     *
     * @param employeeId 34370889
     * @return
     */
    public AttendanceDateInfoDTO selectDateInfo(String employeeId) {
        //01
        String compCode = psDBBean.getCompCode();
        //01
        String custId = psDBBean.getCustID();
        //言語
        String language = psDBBean.getLanguage();
        //該当年月日
        String dyyyymmdd = DateUtil.format(new java.util.Date(), DYYYYMMDD);
        //該当年一日目
        String firstDayOfYear = DateUtil.format(DateUtil.beginOfYear(DateUtil.parse(dyyyymmdd)), DYYYYMMDD);
        // ディフォルト表示時間
        AttendanceDateInfoDTO result = iTmgAttendanceBookService.selectDateInfo(dyyyymmdd, firstDayOfYear, employeeId, compCode, custId, language);
        return result;
    }

    /**
     * 表示時間を取得する
     *
     * @param employeeId 34370889
     * @param year       2020
     * @return
     */
    public AttendanceDateInfoDTO selectDateInfo(String employeeId, String year, String month) {

        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("職員IDは空です");
            return null;
        }
        //該当年月日
        String dyyyymmdd = DateUtil.format(new java.util.Date(), DYYYYMMDD);
        //該当年一日目
        String firstDayOfYear = "";
        if (null != year && !"".equals(year)) {
            firstDayOfYear = year + "/01/01";
        } else {
            firstDayOfYear = DateUtil.format(DateUtil.beginOfYear(DateUtil.parse(dyyyymmdd)), DYYYYMMDD);
        }
        //該当月
        if (null == month || "".equals(month)) {
            month = DateUtil.thisMonth() + "";
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        //01
        String compCode = psDBBean.getCompCode();
        //01
        String custId = psDBBean.getCustID();
        //言語
        String language = psDBBean.getLanguage();
        //表示時間
        AttendanceDateInfoDTO result = iTmgAttendanceBookService.selectTargetDateInfo(dyyyymmdd, month, firstDayOfYear, employeeId, compCode, custId, language);

        return result;
    }

    /**
     * 汎用参照リストを生成します。
     *
     * @throws Exception 汎用参照リストの生成時
     */
    private void setReferList() {
        String sysDate = DateUtil.format(new Date(), "yyyy-MM-dd") + " 00:00:00";
        try {
            _referList = new TmgReferList(
                    psDBBean, BEAN_DESC,
                    (sysDate.substring(0, 10)).replaceAll("-", "/"),
                    TmgReferList.TREEVIEW_TYPE_EMP, true, true,
                    false, false, true
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        if (!this.isEnableEditField(year)) {
            logger.warn("権限がない");
            return false;
        }

        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId) || ObjectUtil.isNull(modifieruserId) || ObjectUtil.isEmpty(modifieruserId)) {
            logger.error("職員IDまたは更新者IDは空です");
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

        //該当年最後の日
        String yearLastDay = year + "/12/31";
        //01
        String compCode = psDBBean.getCompCode();
        //01
        String custId = psDBBean.getCustID();
        iTmgAttendanceBookService.updateComment(employeeId, modifieruserId, yearLastDay, comment, compCode, custId);
        return true;
    }

    /**
     * 権限処理　（コメント更新）
     *
     * @param year
     * @return
     */
    public boolean isEnableEditField(String year) {
        //該当年最後の日
        String targetDate = "";
        if (null != year && !"".equals(year)) {
            targetDate = year + "/12/31";
        }
        /**
         * 入力サイト：　TMG_INP
         * 管理サイト：　TMG_ADMIN
         *　承認サイト：　TMG_PERM
         */
        String siteId = psDBBean.getSiteId();
        //  String siteId = "TMG_ADMIN";
        if (null == siteId || "".equals(siteId)) {
            logger.warn("編集権限なし");
            return false;
        }
        /* String targetDate = getReqParm(REQ_TARGET_YEAR);*/
        String sysDate = DateUtil.format(new Date(), "yyyy/MM/dd");

        try {
            if (targetDate == null || targetDate.length() == 0) {
                targetDate = sysDate;
            } else if (0 <= stringToDate(targetDate).compareTo(new Date())) {
                targetDate = sysDate;
            }
            //権限チャック
            if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(siteId)
                    && _referList.hasAuthorityAtEmployee(
                    targetDate, _referList.getTargetEmployee()
                    , TmgUtil.Cs_AUTHORITY_NOTIFICATION
            )
            ) {
                logger.info("休暇・休出承認権限有り");
                return true;

            } else if (TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(siteId)) {
                logger.info("勤怠管理サイト権限有り");
                return true;

            } else {
                logger.warn("編集権限なし");
                return false;
            }
        } catch (Exception e) {
            logger.error("権限処理の際にエラーが発生しました。", e);
            return false;
        }
    }

    /**
     * String型の文字列をDate型に変換して返します。
     *
     * @param date
     * @return Date 日付
     */
    private Date stringToDate(String date) {
        Date d;
        try {
            d = new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return d;
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
        //コメント
        AttendanceBookCommentDTO attendanceBookCommentDTO = iTmgAttendanceBookService.selectComment(employeeId, yearLastDay, compCode, custId);
        if (null != attendanceBookCommentDTO) {
            //年次
            attendanceBookCommentDTO.setDyyyy(year);
            //職員
            attendanceBookCommentDTO.setEmployeeId(employeeId);
        } else {
            logger.error("コメントが空です");
        }

        return attendanceBookCommentDTO;
    }

    /**
     * 出勤簿月単位集計項目 データ部取得クエリ構築
     *
     * @return
     */
    private List<String> selectTotalDataQueryList() {
        //出勤簿月単位集計項目 データ部取得
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
    private List<AttendanceBookDTO> selectAttendanceBookDto(String employeeId, String year, String month) {
        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("職員IDは空です");
            return null;
        }
        //該当年
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        //該当月
        if (ObjectUtil.isNull(month) || ObjectUtil.isEmpty(month)) {
            month = DateUtil.thisMonth() + "";
        }
        if (month.length() == 1) {
            month = "0" + month;
        }

        //目標月　2020/05
        String queryMonth = year + "/" + month;
        String queryMonthDay = queryMonth + "/01";
        //2021/04/01
        String nextYearDay = DateUtil.format(DateUtil.parse(queryMonth, DYYYYMM).offset(DateField.MONTH, 11), DYYYYMM) + "/01";
        //01
        String compCode = psDBBean.getCompCode();
        //01
        String custId = psDBBean.getCustID();
        //出勤簿月単位集計項目 データ部
        List<String> results = this.selectTotalDataQueryList();
        //出勤簿リスト
        List<AttendanceBookDTO> attendanceBookDTOList = iTmgAttendanceBookService.selectAttendanceBookList(employeeId, queryMonthDay, nextYearDay, compCode, custId, results);

        return attendanceBookDTOList;
    }


    /**
     * 出勤簿リストDTO2VO
     *
     * @param employeeId 　　34370889
     * @param year       2020
     * @param month      04
     * @return
     */
    public List<LinkedHashMap<String, String>> selectAttendanceBookList(String employeeId, String year, String month) {
        //出勤簿リスト
        List<AttendanceBookDTO> attendanceBookDTOS = this.selectAttendanceBookDto(employeeId, year, month);
        List<LinkedHashMap<String, String>> resultListConverted = new ArrayList<LinkedHashMap<String, String>>();

        if (null == psDBBean) {
            logger.error("出勤簿リスト中で　psDBBean対象が空です！");
            return new ArrayList();
        }
        //出勤簿が存在かとうかチャック
        /*if (this.selectExistsAttendanceBookCheck(psDBBean.getCompCode(), psDBBean.getCustID(), employeeId, year, month)) {
            logger.info("職員「" + employeeId + "」の「" + year + "/" + month + "」の出勤簿がないです");
            return new ArrayList();
        }*/
        int MONTH_DAY = 1;
        for (int j = 0; j < 43; j++) {
            LinkedHashMap listHashMap = new LinkedHashMap();
            for (int i = 0; i < attendanceBookDTOS.size(); i++) {
                map = BeanMap.create(attendanceBookDTOS.get(i));
                switch (j) {
                    case 0:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "");
                        break;
                    case 1:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "合計時間数");
                        break;
                    case 2:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "年休取得 日");
                        break;
                    case 3:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "時");
                        break;
                    case 4:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "年休残 日");
                        break;
                    case 5:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "時");
                        break;
                    case 6:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "病休 日");
                        break;
                    case 7:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "時");
                        break;
                    case 8:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "特休 日");
                        break;
                    case 9:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "時");
                        break;
                    case 10:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "欠勤 日");
                        break;
                    case 11:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], "時");
                        break;
                    default:
                        listHashMap.put(DEFAULT_KEY_ARRAY[0], String.valueOf(MONTH_DAY++ / 12) + "日");
                }

                listHashMap.put(DEFAULT_KEY_ARRAY[i + 1], map.get(ATTENDANCEBOOK_KEY_ARRAY[j]));

            }
            resultListConverted.add(listHashMap);

        }

        List<LinkedHashMap<String, String>> resultListMerged = new ArrayList<LinkedHashMap<String, String>>();
        // 2行と3行、4行と5行・・・11行と12行をマージする
        for (int k = 0; k < resultListConverted.size(); k++) {
            switch (k) {
                case 2:
                    resultListMerged.add(listHashMapMerge(resultListConverted.get(k), resultListConverted.get(k + 1)));
                    break;
                case 3:
                    break;
                case 4:
                    resultListMerged.add(listHashMapMerge(resultListConverted.get(k), resultListConverted.get(k + 1)));
                    break;
                case 5:
                    break;
                case 6:
                    resultListMerged.add(listHashMapMerge(resultListConverted.get(k), resultListConverted.get(k + 1)));
                    break;
                case 7:
                    break;
                case 8:
                    resultListMerged.add(listHashMapMerge(resultListConverted.get(k), resultListConverted.get(k + 1)));
                    break;
                case 9:
                    break;
                case 10:
                    resultListMerged.add(listHashMapMerge(resultListConverted.get(k), resultListConverted.get(k + 1)));
                    break;
                case 11:
                    break;
                default:
                    resultListMerged.add(resultListConverted.get(k));
            }
        }
        return resultListMerged;
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
            logger.error("職員IDは空です");
            return null;
        }
        //該当年
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        //該当月
        if (ObjectUtil.isNull(month) || ObjectUtil.isEmpty(month)) {
            month = DateUtil.thisMonth() + "";
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        //目標月　ｙｙｙｙｍｍ
        String queryMonth = year + "/" + month;
        //目標月　yyyymmdd
        String queryMonthDay = queryMonth + "/01";
        String nextYearDay = DateUtil.format(DateUtil.parse(queryMonth, DYYYYMM).offset(DateField.MONTH, 11), DYYYYMM) + "/01";
        //01
        String compCode = psDBBean.getCompCode();
        //01
        String custId = psDBBean.getCustID();
        //出勤簿のヘッダ部情報（氏名、所属）等
        AttendanceBookEmpDTO attendanceBookEmpDTO = iTmgAttendanceBookService.selectEmployeesBasicInfo(employeeId, queryMonthDay, nextYearDay, compCode, custId);
        //目標日
        attendanceBookEmpDTO.setDyyyymmdd(queryMonthDay);
        //目標対象
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
            logger.error("職員IDは空です");
            return null;
        }
        //該当年
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        //該当月
        if (ObjectUtil.isNull(month) || ObjectUtil.isEmpty(month)) {
            month = DateUtil.thisMonth() + "";
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        //該当年月日
        String dyyyymmdd = DateUtil.format(new java.util.Date(), DYYYYMMDD);
        //目標月　ｙｙｙｙ/ｍｍ
        String queryMonth = year + "/" + month;
        //前月一日目2020/04/01
        String preYearDay = DateUtil.format(DateUtil.parse(queryMonth, DYYYYMM).offset(DateField.MONTH, -1), DYYYYMM) + "/01";
        //翌月一日目2021/04/01
        String nextYearDay = DateUtil.format(DateUtil.parse(queryMonth, DYYYYMM).offset(DateField.MONTH, 11), DYYYYMM) + "/01";
        //2020/12/31
        String yearLastDay = year + "/12/31";
        //01
        String compCode = psDBBean.getCompCode();
        //01
        String custId = psDBBean.getCustID();

        // 仕様変更で年次休暇付与日数, 年次休暇付与日が画面に表示されれないことにより、取得処理を行わない
        // 年次休暇付与日数, 年次休暇付与日
        // AttendanceEndueTimeInfoDTO attendanceEndueTimeInfoDTO = this.selectEndueTimeInfo(dyyyymmdd, employeeId, preYearDay, nextYearDay, compCode, custId);
        AttendanceEndueTimeInfoDTO attendanceEndueTimeInfoDTO = new AttendanceEndueTimeInfoDTO();

                //コメント
        AttendanceBookCommentDTO attendanceBookCommentDTO = this.selectComment(employeeId, year, yearLastDay, compCode, custId);
        //変える
        AttendanceBookHolidayInfoVO attendanceBookHolidayInfoVO = new AttendanceBookHolidayInfoVO(attendanceEndueTimeInfoDTO, attendanceBookCommentDTO);
        //データ時間
        attendanceBookHolidayInfoVO.setDataTime(queryMonth);
        //目標対象
        attendanceBookHolidayInfoVO.setEmployeeId(employeeId);
        return attendanceBookHolidayInfoVO;

    }

    /**
     * 行を列に変わる
     *
     * @param clazz
     * @param list
     * @param <T>
     * @return
     */
    private <T> List convert(Class<T> clazz, List<T> list) {
        List<ArrayList> result;
        Field[] fields = clazz.getDeclaredFields();
        result = new ArrayList<>(fields.length);
        for (int i = 0; i < fields.length; i++) {
           /* if (fields[i].getName().equals("tmaCcustomerid") || fields[i].getName().equals("tmaCcompanyid") || fields[i].getName().equals("tmaCemployeeid") || fields[i].getName().equals("dyyyy") || fields[i].getName().equals("dmm") || fields[i].getName().equals("tmaDyyyymm")) {
                continue;
            }*/
            result.add(new ArrayList());
        }
        for (T t : list) {
            for (int i = 0; i < fields.length; i++) {
              /*  if (fields[i].getName().equals("tmaCcustomerid") || fields[i].getName().equals("tmaCcompanyid") || fields[i].getName().equals("tmaCemployeeid") || fields[i].getName().equals("dyyyy") || fields[i].getName().equals("dmm") || fields[i].getName().equals("tmaDyyyymm")) {
                    continue;
                }*/

                ArrayList l = result.get(i);
                Field field = fields[i];
                field.setAccessible(true);
                try {
                    l.add(field.get(t));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.convertTarget(clazz, result);
    }

    private <T> List convertTarget(Class<T> clazz, List<ArrayList> list) {
        List<HashMap<String, List>> result = new ArrayList<HashMap<String, List>>();
        HashMap<String, List> data = null;
        Field[] fields = clazz.getDeclaredFields();
        int size = list.size();
        if (fields.length != size) {
            logger.error("target count not match");
            return null;
        }
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            data = new HashMap<String, List>();
            data.put(field.getName(), (List) list.get(i));
            result.add(data);
        }
        return result;
    }

    /**
     * 同じkeyであれば、listHashMap1.value+<br>+listHashMap2.value
     *
     * @param listHashMap1
     * @param listHashMap2
     * @return listHashMap
     */
    private LinkedHashMap listHashMapMerge(LinkedHashMap listHashMap1, LinkedHashMap listHashMap2) {
        LinkedHashMap listHashMap = new LinkedHashMap();
        for (int i = 0; i < 13; i++) {
            listHashMap.put(DEFAULT_KEY_ARRAY[i], listHashMap1.get(DEFAULT_KEY_ARRAY[i]) + "<br>" + listHashMap2.get(DEFAULT_KEY_ARRAY[i]));
        }
        return listHashMap;
    }

    /**
     * 対象職員の出勤簿情報が存在する年度情報を検索する
     *
     * @param employeeId
     * @param year
     * @return
     */
    public AttendanceExistsVO selectExistsAttendanceBook(String employeeId, String year) {

        String targetDate = "";

        if (null == employeeId || "".equals(employeeId)) {
            logger.warn("職員番号が空です");
            return null;
        }

        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        //目標時間
        targetDate = year + "/12/31";
        //該当最後の日
        String currentYear = DateUtil.thisYear() + "/12/31";
        //01
        String compCode = psDBBean.getCompCode();
        //01
        String custId = psDBBean.getCustID();
        //対象職員の出勤簿情報が存在する年度情報
        AttendanceExistsVO attendanceExistsVO = iTmgAttendanceBookService.selectExistsAttendanceBook(targetDate, employeeId, compCode, custId);
        //該当最後の日
        attendanceExistsVO.setCurrentYear(currentYear);

        return attendanceExistsVO;

    }

    /**
     * 出勤簿が存在かとうか
     *
     * @param compCode
     * @param custId
     * @param employeeId
     * @param year
     * @param month
     * @return
     */
    private boolean selectExistsAttendanceBookCheck(String compCode, String custId, String employeeId, String year, String month) {
        if (month.length() == 1) {
            month = "0" + month;
        }
        //基準日
        String baseDate = year + "/" + month + "/01";
        String flag = iTmgAttendanceBookService.selectExistsAttendanceBookCheck(baseDate, employeeId, compCode, custId);
        return "0".equals(flag);
    }


    /**
     * 月間実働時間
     *
     * @param sqlStr
     * @param employeeId
     * @param monthStartDate
     * @param monthEndDate
     * @param compCode
     * @param custId
     * @return
     */
    private List<String> selectWorkTime(String sqlStr, String employeeId, String monthStartDate, String monthEndDate, String compCode, String custId) {
        if (ObjectUtil.isEmpty(sqlStr) || ObjectUtil.isEmpty(employeeId) || ObjectUtil.isEmpty(monthStartDate) || ObjectUtil.isEmpty(monthEndDate) || ObjectUtil.isEmpty(compCode) || ObjectUtil.isEmpty(custId)) {
            logger.error("月間実働時間　パラメータが不正です");
            return null;
        }
        return iTmgAttendanceBookService.selectWorkTime(sqlStr, employeeId, monthStartDate, monthEndDate, compCode, custId);
    }

    /**
     * 月間実働時間
     *
     * @param psDBBean
     * @return
     */
    public String selectWorkTime(PsDBBean psDBBean) {
        if (null == psDBBean) {
            logger.error("PsDBBean が空です");
            return null;
        }
        //sql を取得する
        String sqlStr = "";
        //勤簿月単位集計項目 データ部取得
        List<MastGenericDetailDTO> mastGenericDetailDTOList = iTmgAttendanceBookService.selectTotalDataQueryList(Cs_MGD_DISPATTENDANCEITEMS, DISP_LINE_5);
        if (null != mastGenericDetailDTOList) {
            sqlStr = mastGenericDetailDTOList.get(0).getMgd_csparechar1();
        }
        //該当年月の一日目
        String monthStartDate = DateUtil.format(new Date(), "yyyy/MM") + "/01";
        //該当年月最後の日
        String monthEndDate = DateUtil.endOfMonth(new Date()).toString("yyyy/MM/dd");
        //月間実働時間
        List<String> results = this.selectWorkTime(sqlStr, psDBBean.getUserCode(), monthStartDate, monthEndDate, psDBBean.getCompCode(), psDBBean.getCustID());
        StringBuffer sb = new StringBuffer();
        if (null != results) {
            String workTime = results.get(0);
            //フォマードを変更する
            // 42.30  --> 42時30分
            sb.append(workTime.substring(0, workTime.indexOf("."))).append("時");
            String minute = workTime.substring(workTime.indexOf(".") + 1, workTime.length());
            sb.append(minute).append("分");
            logger.info("月間実働時間" + sb.toString());
            return sb.toString();
        } else {
            return "";
        }
    }

}
