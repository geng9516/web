package jp.smartcompany.job.modules.tmg.greennutstimepunch;

import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGreennutsEmployeesDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGreennutsTplogDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.ITmgGreennutsEmployeesService;
import jp.smartcompany.job.modules.core.service.ITmgGreennutsTplogService;
import jp.smartcompany.job.modules.core.service.ITmgTimepunchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GreenNutsTimePunchBean {

    private final ITmgGreennutsTplogService iTmgGreennutsTplogService;
    private final ITmgTimepunchService iTmgTimepunchService;
    private final ITmgGreennutsEmployeesService iTmgGreennutsEmployeesService;

    public String timePunch(String data, int count, String no,int length) {
        String  errCode="1201";
        if(data.length() == count*length){
            for(int i = 0; i < count; i++){
                TmgGreennutsTplogDO tplog=new TmgGreennutsTplogDO();

                String tpdata = data.substring(i*length,(i+1)*length);
                String punchTime=tpdata.substring(28,42);

                LocalDateTime yyyyMMddHHmmss = LocalDateTime.parse(punchTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

                tplog.setTgtlCtimepunchdata(tpdata);
                tplog.setTgtlCiccardid(tpdata.substring(0,16));
                tplog.setTgtlCemployeeid(tpdata.substring(16,26));
                tplog.setTgtlCtptypeid(tpdata.substring(26,28));
                tplog.setTgtlCtptime(punchTime);
                tplog.setTgtlCmodifieruserid(no);

                List<TmgGreennutsEmployeesDO> emplist=iTmgGreennutsEmployeesService.
                        getBaseMapper().
                        selectList(SysUtil.<TmgGreennutsEmployeesDO>query().
                                eq("TGE_CICCARDID", tplog.getTgtlCiccardid())
                                .le("TGE_DSTARTDATE", yyyyMMddHHmmss)
                                .ge("TGE_DENDDATE", yyyyMMddHHmmss));

                if(emplist.size()==1){
                    TmgGreennutsEmployeesDO emp=emplist.get(0);
                    TmgTimepunchDO tp=new TmgTimepunchDO();
                    tp.setTtpCcustomerid(emp.getTgeCcustomerid());
                    tp.setTtpCcompanyid(emp.getTgeCcompanyid());
                    tp.setTtpCemployeeid(emp.getTgeCemployeeid());
                    tp.setTtpCmodifieruserid(no);
                    tp.setTtpCmodifierprogramid("TmgGreenNutsTimepunchAction#timepunch");
                    tp.setTtpDtptime(Timestamp.valueOf(yyyyMMddHHmmss));
                    tp.setTtpCtptypeid("TMG_TPTYPE|"+tplog.getTgtlCtptypeid());
                    iTmgTimepunchService.save(tp);
                }else if(emplist.size()==0){
                    tplog.setTgtlCmemo("NO_DATA_FOUND : ICカード番号に該当する職員マスタが見つかりません");
                }else {
                    tplog.setTgtlCmemo("TOO_MANY_ROWS : ICカード番号に該当する職員マスタが2件以上存在します");
                }
                iTmgGreennutsTplogService.save(tplog);
            }
            errCode="0200";
        }
        return errCode;
    }
}
