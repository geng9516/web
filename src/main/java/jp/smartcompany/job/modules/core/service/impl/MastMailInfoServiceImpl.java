package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.MailUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.enums.MailType;
import jp.smartcompany.job.modules.core.mapper.MastMailinfo.MastMailInfoMapper;
import jp.smartcompany.job.modules.core.pojo.bo.SendChangePasswordMailBO;
import jp.smartcompany.job.modules.core.pojo.bo.SendMailBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastMailInfoDO;
import jp.smartcompany.job.modules.core.service.IMastMailInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MastMailInfoServiceImpl extends ServiceImpl<MastMailInfoMapper, MastMailInfoDO> implements IMastMailInfoService {

    private final MailUtil mailUtil;

    @Override
    public String sendMail(MailType mailType, SendMailBO sendData) {
        MastMailInfoDO mailTemplate = queryMailTemplate(mailType);
        log.info("邮箱模板:{}",mailTemplate);
        String title = mailTemplate.getMmCtitle();
        String content = mailTemplate.getMmCcontent() + "\r\n"+ mailTemplate.getMmCdesc();
        String to = sendData.getReceiver();
        if (mailType == MailType.PASSWORD_CHANGED) {
            if (sendData instanceof SendChangePasswordMailBO) {
                SendChangePasswordMailBO passwordMailBO = (SendChangePasswordMailBO) sendData;
                content = StrUtil.replace(content, "##0##", passwordMailBO.getPassword());
                content = StrUtil.replace(content, "##1##", passwordMailBO.getAccount());
            } else {
                throw new GlobalException("メールパラメータは正しくない");
            }
        } else {
            content = StrUtil.replace(content,"##0##",sendData.getEmpName());
        }
        mailUtil.sendMail(mailType,sendData.getSender(),sendData.getEmpId(),sendData.getStandardDate(),to,title,content);
        return "メールは発送されました";
    }

    private MastMailInfoDO queryMailTemplate(MailType sendType) {
        return getOne(SysUtil.<MastMailInfoDO>query().eq("mm_cid",sendType.getDesc()));
    }

}
