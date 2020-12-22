package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.MailUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.enums.MailType;
import jp.smartcompany.job.modules.core.mapper.MastMailinfo.MastMailInfoMapper;
import jp.smartcompany.job.modules.core.pojo.bo.SendMailBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastMailInfoDO;
import jp.smartcompany.job.modules.core.service.IMastCompanyService;
import jp.smartcompany.job.modules.core.service.IMastMailInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MastMailInfoServiceImpl extends ServiceImpl<MastMailInfoMapper, MastMailInfoDO> implements IMastMailInfoService {

    private final MailUtil mailUtil;
    private final IMastCompanyService companyService;
    @Value("${info.siteUrl}")
    private String siteUrl;

    @Override
    public void sendMail(MailType mailType, SendMailBO sendData) {
        if (StrUtil.isBlank(sendData.getToAddress())){
            throw new GlobalException("メールアドレスは空にできません");
        }
        MastMailInfoDO mailTemplateInfo = queryMailTemplate(mailType);
        String title = mailTemplateInfo.getMmCtitle();
        String content = assembleMailContent(mailType,mailTemplateInfo.getMmCcontent(),sendData.getExtraContent());

        mailUtil.sendMail(mailType,mailTemplateInfo.getMmCaddress(),sendData.getEmpId(),sendData.getStandardDate(),sendData.getToAddress(),title,content);
    }

    @Override
    public MastMailInfoDO queryMailTemplate(MailType sendType) {
        return getOne(SysUtil.<MastMailInfoDO>query().eq("mm_cid",sendType.getDesc()));
    }


    public static final String KEY_ACCOUNT =  "account";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EMPLOY_NAME = "employName";
    public static final String KEY_MONTH = "month";
    public static final String KEY_HOURS = "hours";
    public static final String KEY_MANAGER = "manager";
    public static final String KEY_WORK_DAY = "workDay";

    private String assembleMailContent(MailType type, String mailTemplate, Map<String,Object> extraData) {
        String result;

        String account = null;
        String password = null;
        String employName = null;
        String message = null;
        String month = null;
        String hours = null;
        String manager = null;
        String workDay = null;
        if (Objects.nonNull(extraData)) {
            account = (String) extraData.get(KEY_ACCOUNT);
            password = (String) extraData.get(KEY_PASSWORD);
            employName = (String) extraData.get(KEY_EMPLOY_NAME);
            message = (String) extraData.get(KEY_MESSAGE);
            month = (String) extraData.get(KEY_MONTH);
            hours = (String) extraData.get(KEY_HOURS);
            manager = (String) extraData.get(KEY_MANAGER);
            workDay = (String) extraData.get(KEY_WORK_DAY);
        }

        switch (type) {
            case CREATE_ACCOUNT_FOR_USER:
                if (StrUtil.isBlank(account)|| StrUtil.isBlank(password)){
                    throw new GlobalException("アカウントまたはパスワードは空にできません");
                }
                result = mailTemplate.replaceAll("##0##",employName);
                result = result.replaceAll("##1##",account);
                result = result.replaceAll("##2##",password);
                result = result.replaceAll("##3##",siteUrl);
                break;
            case CREATE_ACCOUNT_FOR_ADMIN:
                result = mailTemplate.replaceAll("##0##",employName);
                break;
            case GROUP_CHECK:
                String companyName = companyService.getCompanyName(SysUtil.transDateToString(DateUtil.date()));
                result = mailTemplate.replaceAll("##0##", companyName);
                break;
            case PS_GENERAL_INTERFACE:
                result = mailTemplate;
                break;
            case PASSWORD_CHANGED:
                if (StrUtil.isBlank(account)|| StrUtil.isBlank(password)){
                    throw new GlobalException("アカウントまたはパスワードは空にできません");
                }
                result = mailTemplate.replaceAll("##0##",account);
                result = result.replaceAll("##1##",password);
                break;
            case UN_APPROVAL:
                result = mailTemplate.replaceAll("##0##",message);
                break;
            case TMG_NTF_AWAIT_APPROVAL:
            case TMG_NTF_APPROVED:
            case TMG_NTF_REJECTED:
            case TMG_NTF_CANCELED:
                result = mailTemplate.replaceAll("##0##",employName);
                result = result.replaceAll("##1##",message);
                break;
            case TMG_TDA_UNAPPR_WEEKLY:
            case TMG_TDA_UNAPPR_MONTHLY:
            case TMG_TMO_UNAPPR_MONTHLY:
                result = mailTemplate.replaceAll("##0##",employName);
                result = result.replaceAll("##2##",message);
                break;
            case TMG_OT_MONTHLY_EMP_01:
            case TMG_OT_MONTHLY_EMP_02:
            case TMG_OT_MONTHLY_EMP_03:
            case TMG_OT_MONTHLY_EMP_04:
            case TMG_OT_MONTHLY_EMP_05:
                if (StrUtil.isBlank(month)|| StrUtil.isBlank(hours)){
                    throw new GlobalException("月間または時間数は空にできません");
                }
                result = mailTemplate.replaceAll("##0##",employName);
                result = result.replaceAll("##1##",month);
                result = result.replaceAll("##2##",hours);
                break;
            case TMG_OT_MONTHLY_MGR_01:
            case TMG_OT_MONTHLY_MGR_02:
            case TMG_OT_MONTHLY_MGR_03:
            case TMG_OT_MONTHLY_MGR_04:
            case TMG_OT_MONTHLY_MGR_05:
                if (StrUtil.isBlank(month)|| StrUtil.isBlank(hours)){
                    throw new GlobalException("月間または時間数は空にできません");
                }
                result = mailTemplate.replaceAll("##0##",employName);
                result = result.replaceAll("##1##",month);
                result = result.replaceAll("##2##",hours);
                result = result.replaceAll("##3##",message);
                break;
            case TMG_RESULTS_INPUT_OT:
                result = mailTemplate.replaceAll("##0##",employName);
                result = result.replaceAll("##1##",manager);
                result = result.replaceAll("##2##",workDay);
                result = result.replaceAll("##3##",message);
                break;
            default:
                throw new GlobalException("メールタイプは存在しません");
        }
        return result;
    }

}
