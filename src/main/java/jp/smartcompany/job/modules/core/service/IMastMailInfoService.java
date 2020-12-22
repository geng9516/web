package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.enums.MailType;
import jp.smartcompany.job.modules.core.pojo.bo.SendMailBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastMailInfoDO;

public interface IMastMailInfoService extends IService<MastMailInfoDO> {

    void sendMail(MailType mailType, SendMailBO sendMailBO);

    MastMailInfoDO queryMailTemplate(MailType sendType);
}
