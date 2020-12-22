package jp.smartcompany.job.modules.tmg_inp.noticeboard.logic;

import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeBoardDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface INoticeBoardLogic {

    List<NoticeRangeDTO> getSendNoticeRangeList(HttpSession session);

    List<Map<String,String>> getValidReadEmpList(List<String> typeIds, HttpSession session);

    void addNotice(NoticeBoardDTO dto);

}
