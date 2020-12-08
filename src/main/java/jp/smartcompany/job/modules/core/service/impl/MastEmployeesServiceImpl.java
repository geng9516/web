package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.admin.usermanager.dto.PersonalInfoDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.auth.entity.LoginControlEntity;
import jp.smartcompany.framework.compatible.entity.V3CompatiblePostEntity;
import jp.smartcompany.framework.component.dto.EmployInfoSearchDTO;
import jp.smartcompany.framework.component.entity.EmployeeInfoSearchEntity;
import jp.smartcompany.job.modules.core.mapper.MastEmployees.MastEmployeesMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.tmg.empattrsetting.vo.EmployMentWithMEVo;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 基本情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastEmployeesServiceImpl extends ServiceImpl<MastEmployeesMapper, MastEmployeesDO> implements IMastEmployeesService {

    /**
     * MAST_EMPLOYEESの採用日を取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 職員番号
     * @param yyyymmdd   基準日
     * @return Date 採用日
     */
    @Override
    public Date selectBegindateWork(String customerId, String companyId, String employeeId, Date yyyymmdd) {

        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("customerId", customerId);
        map.put("companyId", companyId);
        map.put("employeeId", employeeId);
        map.put("yyyymmdd", yyyymmdd);

        return baseMapper.selectBegindateWork(map);
    }

    @Override
    public List<PaidHolidayInitVO> listPaidHolidayInit(String empSql) {
        return baseMapper.selectPaidHolidayInit(empSql);
    }

    @Override
    public  List<String> selectUserIdList(String psCustid,
                                          String psCompid,
                                          String psLoginUserId,
                                          Date psDate) {
        QueryWrapper<MastEmployeesDO> qw = SysUtil.query();
        qw.eq("ME_CCUSTOMERID_CK",psCustid)
          .eq("ME_CCOMPANYID",psCompid)
          .eq("ME_CEMPLOYEEID_CK",psLoginUserId)
                .le("ME_DSTARTDATE",psDate)
                .ge("ME_DENDDATE",psDate);
        return list(qw).stream().map(MastEmployeesDO::getMeCuserid).collect(Collectors.toList());
    }

    @Override
    public List<MastEmployeesDO> selectEmployByLoginUserId(String psCustid,
                                                           String psCompid,
                                                           String psLoginUserId,
                                                           String psDate) {
        QueryWrapper<MastEmployeesDO> qw = SysUtil.query();
        qw.eq("ME_CCUSTOMERID_CK",psCustid)
                .eq("ME_CCOMPANYID",psCompid)
                .eq("ME_CEMPLOYEEID_CK",psLoginUserId)
                .le("ME_DSTARTDATE",psDate)
                .ge("ME_DENDDATE",psDate);
        return list(qw);
    }

    /**
     * サイトIDを判定し更新対象の職員番号
     *
     * @param siteId   サイトID
     * @param yyyymmdd 　対象日
     * @param empsql   　対象職員取得SQL
     * @param empIds   　画面チェックした職員番号リスト
     * @return
     */
    @Override
    public List<String> selectEmpIdListForTmgDaily(String siteId, String yyyymmdd, String empsql, String[] empIds) {
        Map<String, Object> map = MapUtil.newHashMap(4);
        map.put("siteId", siteId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("empsql", empsql);
        map.put("empIds", empIds);
        return baseMapper.selectEmpIdListForTmgDaily(map);
    }

    @Override
    public int selectRelationEx(String sCust,String sLoginUser,String sTargetUser,String sSystem,String sDesig,
                                       String sDate, String psBase1, String psBase2, String psBase3,
                                       String psBase4, String psBase5, String psBase6,
                                       String psBase7, String psBase8) {
        if (StrUtil.isBlank(sCust)){
            sCust = "01";
        }
        if (StrUtil.isBlank(sLoginUser)){
            sLoginUser = "111";
        }
        if (StrUtil.isBlank(sTargetUser)) {
            sTargetUser = "222";
        }
        if (StrUtil.isBlank(sSystem)){
            sSystem = "system";
        }
        return baseMapper.selectRelationEx(sCust,sLoginUser,sTargetUser,sSystem,sDesig,sDate,psBase1,psBase2,psBase3,
                psBase4,psBase5,psBase6,psBase7,psBase8);
    }

    @Override
    public int selectRelation(String sCust,String sLoginUser,String sTargetUser,String sSystem,String sDesig,
                                String sDate) {
        if (StrUtil.isBlank(sCust)){
            sCust = "01";
        }
        if (StrUtil.isBlank(sLoginUser)){
            sLoginUser = "111";
        }
        if (StrUtil.isBlank(sTargetUser)) {
            sTargetUser = "222";
        }
        if (StrUtil.isBlank(sSystem)){
            sSystem = "system";
        }
        return baseMapper.selectRelation(sCust,sLoginUser,sTargetUser,sSystem,sDesig,sDate);
    }

    @Override
    public List<LoginControlEntity> selectUserInfoByDate(String loginUser, String language, String psDate) {
        return baseMapper.selectUserInfoByDate(loginUser,language,psDate);
    }

    @Override
    public int selectOrgRelation(String sCust, String sLoginUser,String sTargetComp,
                                 String sTargetSec,String sSystem,String non,String sDate) {
        if (StrUtil.isBlank(sCust)){
            sCust="01";
        }
        if(StrUtil.isBlank(sLoginUser)){
            sLoginUser = "111";
        }
        if (StrUtil.isBlank(sTargetComp)){
            sTargetComp = "222";
        }
        if (StrUtil.isBlank(sTargetSec)){
            sTargetSec = "222";
        }
        if (StrUtil.isBlank(sSystem)){
            sSystem = "system";
        }
        return baseMapper.selectOrgRelation(sCust,
                sLoginUser, sTargetComp, sTargetSec, sSystem, null, sDate);
    }

    @Override
    public List<V3CompatiblePostEntity> getVersion3SectionChief(
            String sCustid,
            String sCompid,
            String sDeptid,
            String sDate,
            String sPostid,
            boolean bIncludeactual
    ) {
        return baseMapper.getVersion3SectionChief(sCustid,sCompid,sDeptid,sDate,sPostid,bIncludeactual);
    }

    /**
     * 発令上の勤務開始日取得用SQL取得メソッド
     */
    @Override
    public EmployMentWithMEVo selectDateofemploymentWithME(String custId, String compId, String empId){
        return baseMapper.selectDateofemploymentWithME( custId,  compId,  empId);
    }


    /**
     * ================社员检索(Group定义中社员检索弹窗用)===========
     */
    @Override
    public List<EmployeeInfoSearchEntity> selectEmployeeInfoUserIDList(EmployInfoSearchDTO searchDTO) {
        return baseMapper.selectEmployeeInfoUserIDList(searchDTO);
    }

    @Override
    public  List<EmployeeInfoSearchEntity> selectEmployeeInfoUserIDListAdd(EmployInfoSearchDTO searchDTO) {
        return baseMapper.selectEmployeeInfoUserIDListAdd(searchDTO);
    }

    @Override
    public List<EmployeeInfoSearchEntity> selectEmployeeInfoList(
            String searchDate,
            String language,
            String designation,
            String sEmpInfoUserIDList,
            String sCompNick,
            String sSectionNick,
            String sPostNick,
            String loginUser,
            String systemId
    ) {
        return baseMapper.selectEmployeeInfoList(searchDate,language,designation,sEmpInfoUserIDList,sCompNick,sSectionNick,sPostNick,loginUser,systemId);
    }


    // -=========================== 用户管理相关sql开始============================//
    @Override
    public IPage<UserManagerListDTO> selectMainAllList(IPage<UserManagerListDTO> page, String custId, String language, String companyId, List<String> companyList) {
        return baseMapper.selectMainAllList(page, custId, language, companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainLockoutList(IPage<UserManagerListDTO> page, String custId, String language, String companyId, List<String> companyList) {
        return baseMapper.selectMainLockoutList(page, custId, language, companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainAdminList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainAdminList(page,custId,language,companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainEmpNameList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList,String empName) {
        return baseMapper.selectMainEmpNameList(page,custId,language,companyId,companyList,empName);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainSectionList(IPage<UserManagerListDTO> page,String custId,String language,List<String> companyList,
                                                           String sectionCompanyId,String sectionId) {
        return baseMapper.selectMainSectionList(page,custId,language,companyList,sectionCompanyId,sectionId);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainEmpIdList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList,String empId) {
        return baseMapper.selectMainEmpIdList(page,custId,language,companyId,companyList,empId);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainAfterRetireList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainAfterRetireList(page,custId,language, companyId, companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainAfterJoinList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainAfterJoinList(page, custId,language, companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainBeforeJoinList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainBeforeJoinList(page,custId,language,companyId,companyList);
    }

    @Override
    public IPage<UserManagerListDTO> selectMainValidList(IPage<UserManagerListDTO> page,String custId,String language,String companyId,List<String> companyList) {
        return baseMapper.selectMainValidList(page,custId,language,companyId,companyList);
    }

    @Override
    public List<UserManagerDTO> selectStartList(String custId, List<String> userIds,

                                                String language, Integer searchType,
                                                List<String> companyList) {
        return baseMapper.selectStartList(custId,userIds,language,searchType,companyList);
    }

    @Override
    public List<UserManagerDTO> selectEndList(String custId, List<String> userIds,
                                              String language, Integer searchType,
                                              List<String> companyList) {
         return baseMapper.selectEndList(custId,userIds, language, searchType, companyList);
    }

    @Override
    public UserManagerDTO selectPersonalName(String custId, String userId, String language,  List<String> companyList) {
         return baseMapper.selectPersonalName(custId,userId,language, companyList);
    }

    @Override
    public PersonalInfoDTO selectPersonalInfo(Date baseDate, String custId, String language, String userId) {
         String sBaseDate = SysUtil.transDateToString(baseDate);
         return baseMapper.selectPersonalInfo(custId,userId,language,sBaseDate);
    }
    /*========================= 用户管理相关sql结束 ==================================*/


    private static final String XLSX = "xlsx";
    private static final String XLS = "xls";
    private static final String CSV = "csv";

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void uploadMailList(MultipartFile file) {
        XSSFWorkbook workbook = null;
        InputStream is;
        Map<String,String> updateMailList = MapUtil.newHashMap(true);
        try {
            File uploadFile = multipartFileToFile(file);
            String suffix = FileUtil.getSuffix(uploadFile);
            if(!StrUtil.equalsAny(suffix,XLSX,CSV,XLS)) {
                throw new GlobalException("xlsx、xls、csv形式のファイルのみをサポートします");
            }
            is = file.getInputStream();

            if (StrUtil.equalsAnyIgnoreCase(suffix,XLSX,XLS)) {
                workbook = new XSSFWorkbook(is);
                is.close();
                //工作表对象
                XSSFSheet sheet = workbook.getSheetAt(0);
                //总行数
                int totalRows = sheet.getPhysicalNumberOfRows();
                for (int i = 1; i < totalRows; i++) {
                    XSSFRow row = sheet.getRow(i);
                    String empId = null;
                    String email = null;
                    for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                        XSSFCell cell = row.getCell(j);
                        String value = getCellValue(cell);
                        // 员工id
                        if (j == 0) {
                            empId = StrUtil.trim(value);
                        // email
                        } else if (j == 1) {
                            email = StrUtil.trim(value);
                        }
                    }
                    updateMailList.put(empId, email);
                }
            } else if (StrUtil.equalsIgnoreCase(suffix,CSV)){
                CsvReader reader = CsvUtil.getReader();
                CsvData data = reader.read(uploadFile);
                List<CsvRow> rows = data.getRows();
                for (int i = 1; i < rows.size(); i++) {
                    CsvRow csvRow = rows.get(i);
                    List<String> rawList = csvRow.getRawList();
                    String empId = StrUtil.trim(rawList.get(0));
                    String email = StrUtil.trim(rawList.get(1));
                    updateMailList.put(empId,email);
                }
            } else {
                throw new GlobalException("サポートされていないファイルタイプ["+suffix+"]");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        } finally {
            try {
                if (workbook!=null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(updateMailList);
    }

    @Override
    public void exportXlsTemplate(HttpServletResponse response,String type){
        List<String> titles = CollUtil.newArrayList("アカウントIDまたは社員番号", "メールアドレス");
        Date now = new Date();
        String filenamePrefix = "メール登録-"+now.getTime();

        if (StrUtil.equalsAnyIgnoreCase(type,"xls","xlsx")) {
            //声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            //生成一个表格
            HSSFSheet sheet = workbook.createSheet();

            HSSFCellStyle titleBorder = workbook.createCellStyle();
            // 文字居中
            titleBorder.setAlignment(HorizontalAlignment.CENTER);
            // 粗体显示
            HSSFFont setBold = workbook.createFont();
            setBold.setBold(true);
            titleBorder.setFont(setBold);

            //设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 18);
            //循环字段名数组，创建标题行
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < titles.size(); i++) {
                //创建列
                HSSFCell cell = row.createCell(i);
                //设置单元类型为String
                cell.setCellType(CellType.STRING);
                cell.setCellStyle(titleBorder);
                cell.setCellValue(transCellType(titles.get(i)));
            }
            // 输出excel填写模板
            try {
                OutputStream os = response.getOutputStream();
                String filename = new String((filenamePrefix + ".xls").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setHeader("Pragma", "public");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Cache-Control", "max-age=30");
                workbook.write(response.getOutputStream());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        } else if (StrUtil.equalsIgnoreCase(type,"csv")) {
            // CSV文件列分隔符
            String colSeparator = ",";
            // CSV文件列分隔符
//            String colRn = "\r\n";
            byte[] bom ={(byte) 0xEF,(byte) 0xBB,(byte) 0xBF};
            StringBuilder strBuilder = new StringBuilder();
            for (String aColNamesArr : titles) {
                strBuilder.append(aColNamesArr).append(colSeparator);
            }
            // 输出csv填写模板
            try {
                String filename = new String((filenamePrefix + ".csv").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setHeader("Pragma", "public");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Cache-Control", "max-age=30");
                PrintWriter writer = response.getWriter();
                writer.write((new String(bom, StandardCharsets.UTF_8)));
                writer.write(strBuilder.toString());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        } else {
            throw new GlobalException("他の種類のテンプレートはありません");
        }
    }

    private static File multipartFileToFile(MultipartFile file) throws IOException {
        File toFile = null;
        if (file.getSize() > 0) {
            InputStream ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead ;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCellValue(XSSFCell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: // 数字
                if (DateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    // 验证short值
                    if (cell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日付フォーマットエラー");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                    cell.setCellType(CellType.STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            case STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: // 空值
                cellValue = null;
                break;
            case ERROR: // 故障
                cellValue = "不正な文字";
                break;
            default:
                cellValue = "不明なタイプ";
                break;
        }
        return cellValue;
    }

    private static String transCellType(Object value){
        String str;
        if (value instanceof Date){
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            str = sdf.format(date);
        }else{
            str = String.valueOf(value);
            if (StrUtil.equals(str,"null")){
                str = "";
            }
        }
        return str;
    }


}
