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
import jp.smartcompany.job.asynctask.SendMailTask;
import jp.smartcompany.job.modules.core.enums.MailType;
import jp.smartcompany.job.modules.core.pojo.entity.MastEmployeesDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastMailInfoDO;
import jp.smartcompany.job.modules.core.service.IMastMailInfoService;
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
    private final IMastMailInfoService mailInfoService;
    private final SendMailTask sendMailTask;
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
                throw new GlobalException("xlsx???xls???csv???????????????????????????????????????????????????");
            }
            is = file.getInputStream();

            if (StrUtil.equalsAnyIgnoreCase(suffix,XLSX,XLS)) {
                workbook = new XSSFWorkbook(is);
                is.close();
                //???????????????
                XSSFSheet sheet = workbook.getSheetAt(0);
                //?????????
                int totalRows = sheet.getPhysicalNumberOfRows();
                for (int i = 1; i < totalRows; i++) {
                    XSSFRow row = sheet.getRow(i);
                    String empId = null;
                    String empName = null;
                    String email = null;
                    for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                        XSSFCell cell = row.getCell(j);
                        String value = getCellValue(cell);
                        // ??????id
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
                throw new GlobalException("???????????????????????????????????????????????????["+suffix+"]");
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
        // ??????????????????
        if (CollUtil.isEmpty(updateMailList)) {
            throw new GlobalException("????????????????????????????????????????????????");
        }
        List<EmployMailDO> insertEmployMailList = CollUtil.newArrayList();
        String operator = SecurityUtil.getUserId();
        Date now = new Date();
        updateMailList.forEach((empId,mailBO) -> {
             String email = mailBO.getEmail();
             String empName = mailBO.getEmpName();
             QueryWrapper<EmployMailDO> qw = SysUtil.query();
             // ????????????????????????????????????????????????????????????
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
           throw new GlobalException("?????????????????????????????????????????????");
        }
        if (CollUtil.isNotEmpty(insertEmployMailList)){
           employMailService.saveBatch(insertEmployMailList);
        }
    }

    @Override
    public void exportXlsTemplate(HttpServletResponse response, String type){
        List<String> titles = CollUtil.newArrayList("????????????","?????????", "?????????????????????");
        Date now = new Date();
        String filenamePrefix = "???????????????-"+now.getTime();

        if (StrUtil.equalsAnyIgnoreCase(type,"xls","xlsx")) {
            //?????????????????????
            HSSFWorkbook workbook = new HSSFWorkbook();
            //??????????????????
            HSSFSheet sheet = workbook.createSheet();

            HSSFCellStyle titleBorder = workbook.createCellStyle();
            // ????????????
            titleBorder.setAlignment(HorizontalAlignment.CENTER);
            // ????????????
            HSSFFont setBold = workbook.createFont();
            setBold.setBold(true);
            titleBorder.setFont(setBold);

            //??????????????????????????????15?????????
            sheet.setDefaultColumnWidth((short) 18);
            //???????????????????????????????????????
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < titles.size(); i++) {
                //?????????
                HSSFCell cell = row.createCell(i);
                //?????????????????????String
                cell.setCellType(CellType.STRING);
                cell.setCellStyle(titleBorder);
                cell.setCellValue(transCellType(titles.get(i)));
            }
            // ??????excel????????????
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
            // CSV??????????????????
            String colSeparator = ",";
            // CSV??????????????????
//            String colRn = "\r\n";
            byte[] bom ={(byte) 0xEF,(byte) 0xBB,(byte) 0xBF};
            StringBuilder strBuilder = new StringBuilder();
            for (String aColNamesArr : titles) {
                strBuilder.append(aColNamesArr).append(colSeparator);
            }
            // ??????csv????????????
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
            throw new GlobalException("???????????????????????????????????????????????????");
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
        for (UpdateMailDTO emp : list) {
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
            } else {
               throw new GlobalException("?????????["+email+"]???????????????????????????????????????");
            }
        }
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
        MastMailInfoDO mastMailInfoDO =mailInfoService.queryMailTemplate(MailType.TEST_SEND);
        sendMailTask.sendTestMail(sendMail.getEmail(),mastMailInfoDO.getMmCtitle(),sendMail.getContent(),mastMailInfoDO.getMmCaddress());
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

    //???????????????
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
        // ?????????????????????
        switch (cell.getCellType()) {
            case NUMERIC: // ??????
                if (DateUtil.isCellDateFormatted(cell)) {// ?????????????????????????????????
                    SimpleDateFormat sdf = null;
                    // ??????short???
                    if (cell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("?????????????????????????????????");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 0) {//??????????????????
                    cell.setCellType(CellType.STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            case STRING: // ?????????
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: // ??????
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: // ??????
                cellValue = null;
                break;
            case ERROR: // ??????
                cellValue = "???????????????";
                break;
            default:
                cellValue = "??????????????????";
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
