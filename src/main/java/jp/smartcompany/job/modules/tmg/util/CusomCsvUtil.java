package jp.smartcompany.job.modules.tmg.util;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
/**
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CusomCsvUtil {

    private final HttpServletResponse response;

    public void writeCsv(String filename, List<List<Object>> rows) {
        writeCsv(filename, rows,null);
    }

    public void writeCsv(String filename, List<List<Object>> rows, String datePattern) {
        if (StrUtil.isBlank(datePattern)){
            datePattern = DatePattern.NORM_DATETIME_PATTERN;
        }
        setResponseHeader(filename);
        try {
            @Cleanup PrintWriter writer = response.getWriter();
            for(List<Object> row: rows) {
                StringBuilder excelRowData = new StringBuilder();
                for(Object col :row){
                    if (col instanceof Date) {
                        Date d = (Date)col;
                        excelRowData.append("\t");
                        excelRowData.append(DateUtil.format(d, datePattern));
                        excelRowData.append("\t");
                    } else {
                        excelRowData.append(col);
                    }
                    excelRowData.append(",");
                }
                String rowData=excelRowData.toString().substring(0,excelRowData.length()-1);
                writer.println(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setResponseHeader(String filename){
//        application/octetstream;charset=Shift_JIS
        String contentDisposition = StrUtil.join("","attachment;filename=\"",filename,".csv\"");
        response.setHeader("Content-Disposition", contentDisposition);
        response.setHeader("Content-type", "text/csv; charset=UTF-8");

    }

}
