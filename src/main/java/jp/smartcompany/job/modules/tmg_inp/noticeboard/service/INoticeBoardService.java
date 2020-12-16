package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface INoticeBoardService {

    List<NoticeRangeDTO> getSendNoticeRangeList(HttpSession session);

    List<String> getValidReadEmpList(List<String> typeIds,HttpSession session);
}
