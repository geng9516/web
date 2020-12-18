package jp.smartcompany.job.modules.tmg_setting.mailmanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jp.smartcompany.admin.usermanager.dto.UserManagerListDTO;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.*;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.logic.MailManagerLogic;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.bo.UpdateEmployMailBO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.TestSendMail;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto.UpdateMailDTO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.entity.EmployMailDO;
import jp.smartcompany.job.modules.tmg_setting.mailmanager.service.IEmployMailService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
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

@Service
@RequiredArgsConstructor
public class MailManagerLogicImpl implements MailManagerLogic {

    private final IEmployMailService employMailService;
    private final MailUtil mailUtil;

    private static final String XLSX = "xlsx";
    private static final String XLS = "xls";
    private static final String CSV = "csv";

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void uploadMailList(MultipartFile file) {
        XSSFWorkbook workbook = null;
        InputStream is;
        Map<String, UpdateEmployMailBO> updateMailList = MapUtil.newHashMap(true);
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
                    String empName = null;
                    String email = null;
                    for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                        XSSFCell cell = row.getCell(j);
                        String value = getCellValue(cell);
                        // 员工id
                        if (j == 0) {
                            empId = StrUtil.trim(value);
                            // email
                        } else if (j==1) {
                            empName = StrUtil.trim(value);
                        } else if (j == 2) {
                            email = StrUtil.trim(value);
                        }
                    }
                    if (StrUtil.isAllNotBlank(empId,empName,email)&& Validator.isEmail(email)) {
                        UpdateEmployMailBO mailBO = new UpdateEmployMailBO();
                        mailBO.setEmpName(empName);
                        mailBO.setEmail(email);
                        updateMailList.put(empId, mailBO);
                    }
                }
            } else if (StrUtil.equalsIgnoreCase(suffix,CSV)){
                CsvReader reader = CsvUtil.getReader();
                CsvData data = reader.read(uploadFile);
                List<CsvRow> rows = data.getRows();
                for (int i = 1; i < rows.size(); i++) {
                    CsvRow csvRow = rows.get(i);
                    List<String> rawList = csvRow.getRawList();
                    String empId = StrUtil.trim(rawList.get(0));
                    String empName = StrUtil.trim(rawList.get(1));
                    String email = StrUtil.trim(rawList.get(2));
                    if (StrUtil.isAllNotBlank(empId,empName,email) && Validator.isEmail(email)) {
                        UpdateEmployMailBO mailBO = new UpdateEmployMailBO();
                        mailBO.setEmpName(empName);
                        mailBO.setEmail(email);
                        updateMailList.put(empId, mailBO);
                    }
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
        // 更新邮箱信息
        if (CollUtil.isEmpty(updateMailList)) {
            throw new GlobalException("メールデータは入力されていません");
        }
        List<EmployMailDO> insertEmployMailList = CollUtil.newArrayList();
        String operator = SecurityUtil.getUserId();
        Date now = new Date();
        updateMailList.forEach((empId,mailBO) -> {
             String email = mailBO.getEmail();
             String empName = mailBO.getEmpName();
             QueryWrapper<EmployMailDO> qw = SysUtil.query();
             // 如果其他社员没有使用过此邮箱，则可以更新
             if(employMailService.count(qw.eq("tma_email",email).ne("tma_emp_id",empId))==0){
                 EmployMailDO saveEmployMailDO = new EmployMailDO();
                 saveEmployMailDO.setTmaEmail(email);
                 saveEmployMailDO.setTmaEmpId(empId);
                 saveEmployMailDO.setTmaEmpName(empName);
                 saveEmployMailDO.setTmaCreatedBy(operator);
                 saveEmployMailDO.setTmaUpdatedBy(operator);
                 saveEmployMailDO.setVersion(1L);
                 saveEmployMailDO.setTmaUpdateTime(now);
                 saveEmployMailDO.setTmaCreateTime(now);
                 insertEmployMailList.add(saveEmployMailDO);
            }
        });
        if (CollUtil.isEmpty(insertEmployMailList)) {
           throw new GlobalException("メールリストは空くにできません");
        }
        if (CollUtil.isNotEmpty(insertEmployMailList)){
           employMailService.saveBatch(insertEmployMailList);
        }
    }

    @Override
    public void exportXlsTemplate(HttpServletResponse response, String type){
        List<String> titles = CollUtil.newArrayList("社員番号","社員名", "メールアドレス");
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

    @Override
    public PageUtil getInvalidEmailEmpList(Map<String,Object> params) {
        IPage<MastEmployeesDO> pageQuery = new PageQuery<MastEmployeesDO>().getPage(params);
        IPage<MastEmployeesDO> result = employMailService.selectInvalidEmailEmpList(pageQuery);
        return new PageUtil(result);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void updateMailList(List<UpdateMailDTO> list) {
        String userId = SecurityUtil.getUserId();
        Date now = new Date();
        List<EmployMailDO> insertEmpList = CollUtil.newArrayList();
        List<EmployMailDO> updateEmpList = CollUtil.newArrayList();
        list.forEach(emp -> {
            String email=emp.getEmail();
            String empId = emp.getEmpId();
            QueryWrapper<EmployMailDO> qw = SysUtil.query();
            if (employMailService.count(qw.eq("tma_email",email).ne("tma_emp_id",empId))==0){
                QueryWrapper<EmployMailDO> existQw =SysUtil.query();
                EmployMailDO employMailDO = employMailService.getOne(existQw.eq("tma_emp_id",empId));
                if (employMailDO == null) {
                    EmployMailDO saveEmployMailDO = new EmployMailDO();
                    saveEmployMailDO.setTmaEmpName(emp.getEmpName());
                    saveEmployMailDO.setTmaEmpId(empId);
                    saveEmployMailDO.setTmaEmail(email);
                    saveEmployMailDO.setTmaCreateTime(now);
                    saveEmployMailDO.setTmaUpdateTime(now);
                    saveEmployMailDO.setTmaUpdatedBy(userId);
                    insertEmpList.add(saveEmployMailDO);
                } else {
                    employMailDO.setTmaEmail(email);
                    employMailDO.setTmaUpdateTime(now);
                    employMailDO.setTmaUpdatedBy(userId);
                    updateEmpList.add(employMailDO);
                }
            }
        });
        if (CollUtil.isNotEmpty(insertEmpList)){
            employMailService.saveBatch(insertEmpList);
        }
        if (CollUtil.isNotEmpty(updateEmpList)){
            employMailService.updateBatchById(updateEmpList);
        }
    }

    @Override
    public PageUtil searchForUpdateEmail(Map<String,Object> params, String keyword) {
        IPage<UserManagerListDTO> pageQuery = new PageQuery<UserManagerListDTO>().getPage(params);
        return new PageUtil(employMailService.searchEmpForUpdateMail(pageQuery,keyword));
    }

    @Override
    public void testSendMail(TestSendMail sendMail) {
        mailUtil.sendText(sendMail.getEmail(),"Test mail",sendMail.getContent());
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
