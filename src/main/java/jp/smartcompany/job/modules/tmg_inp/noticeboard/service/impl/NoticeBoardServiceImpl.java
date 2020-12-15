package jp.smartcompany.job.modules.tmg_inp.noticeboard.service.impl;

import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.INoticeBoardService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeBoardServiceImpl implements INoticeBoardService {

    // 获取能进行揭示板发布的group
    private List<LoginGroupBO> getPublishGroupList() {
        HttpSession session = ContextUtil.getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        return psSession.getLoginGroups().get("01").stream().filter(LoginGroupBO::getPublishing).collect(Collectors.toList());
    }

}
