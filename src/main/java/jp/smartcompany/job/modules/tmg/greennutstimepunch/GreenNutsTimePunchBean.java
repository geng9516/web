package jp.smartcompany.job.modules.tmg.greennutstimepunch;

import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGreennutsEmployeesDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgGreennutsTplogDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import jp.smartcompany.job.modules.core.service.ITmgGreennutsEmployeesService;
import jp.smartcompany.job.modules.core.service.ITmgGreennutsTplogService;
import jp.smartcompany.job.modules.core.service.ITmgTimepunchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * greennuts打刻
 *
 * @author cui
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GreenNutsTimePunchBean {

    private final ITmgGreennutsTplogService iTmgGreennutsTplogService;
    private final ITmgTimepunchService iTmgTimepunchService;
    private final ITmgGreennutsEmployeesService iTmgGreennutsEmployeesService;

    public String timePunch(String data, int count, String no, int length) {
        //失败返回 エラーコード 1 異常 2 固定 01   サーバで検出したエラー詳細コード
        String errCode = "1201";
        //打刻数据大小=条数x每条大小
        if (data.length() == count * length) {
            for (int i = 0; i < count; i++) {
                //tplog打刻记录日志
                TmgGreennutsTplogDO tplog = new TmgGreennutsTplogDO();
                //每条打刻数据
                String tpdata = data.substring(i * length, (i + 1) * length);
                //スキャン日時
                String punchTime = tpdata.substring(28, 42);
                //当前时间yyyyMMddHHmmss格式
                LocalDateTime yyyyMMddHHmmss = LocalDateTime.parse(punchTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                //设置原始打刻数据
                tplog.setTgtlCtimepunchdata(tpdata);
                // IC カード ID 16 バイト
                tplog.setTgtlCiccardid(tpdata.substring(0, 16));
                //職員コード 10 バイト
                tplog.setTgtlCemployeeid(tpdata.substring(16, 26));
                // 出退勤区分 2 バイト
                tplog.setTgtlCtptypeid(tpdata.substring(26, 28));
                tplog.setTgtlCtptime(punchTime);
                //端末に登録されている端末製造番号
                tplog.setTgtlCmodifieruserid(no);
                //通过ICカード番号查询職員
                List<TmgGreennutsEmployeesDO> emplist = iTmgGreennutsEmployeesService.
                        getBaseMapper().
                        selectList(SysUtil.<TmgGreennutsEmployeesDO>query().
                                eq("TGE_CICCARDID", tplog.getTgtlCiccardid())
                                .le("TGE_DSTARTDATE", yyyyMMddHHmmss)
                                .ge("TGE_DENDDATE", yyyyMMddHHmmss));

                // ICカード番号→職員番号マスタの引き当てが1件の場合のみTMG_TIMEPUNCHへinsertする
                if (emplist.size() == 1) {
                    //emp员工
                    TmgGreennutsEmployeesDO emp = emplist.get(0);
                    //tp打刻记录
                    TmgTimepunchDO tp = new TmgTimepunchDO();
                    //设置顧客コード
                    tp.setTtpCcustomerid(emp.getTgeCcustomerid());
                    //设置法人コード
                    tp.setTtpCcompanyid(emp.getTgeCcompanyid());
                    //设置職員番号
                    tp.setTtpCemployeeid(emp.getTgeCemployeeid());
                    //端末に登録されている端末製造番号
                    tp.setTtpCmodifieruserid(no);
                    //设置更新プログラムid
                    tp.setTtpCmodifierprogramid("TmgGreenNutsTimepunchAction#timepunch");
                    //设置打刻時刻
                    tp.setTtpDtptime(Timestamp.valueOf(yyyyMMddHHmmss));
                    //设置打刻区分
                    tp.setTtpCtptypeid("TMG_TPTYPE|" + tplog.getTgtlCtptypeid());
                    //保存数据
                    iTmgTimepunchService.save(tp);
                    //0件の場合
                } else if (emplist.size() == 0) {
                    //设置备注
                    tplog.setTgtlCmemo("NO_DATA_FOUND : ICカード番号に該当する職員マスタが見つかりません");
                } else {
                    //2件以上の場合
                    //设置备注
                    tplog.setTgtlCmemo("TOO_MANY_ROWS : ICカード番号に該当する職員マスタが2件以上存在します");
                }
                //保存打刻日志记录
                iTmgGreennutsTplogService.save(tplog);
            }
            //成功返回 エラーコード 0 正常 2 固定 00 固定
            errCode = "0200";
        }
        return errCode;
    }
}
