package jp.smartcompany.job.modules.tmg_inp.noticeboard.logic;

import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.DraftNoticeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.DraftNoticeVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface INoticeBoardLogic {

    List<NoticeRangeDTO> getSendNoticeRangeList(HttpSession session);

    List<Map<String,String>> getValidReadEmpList(List<String> typeIds, HttpSession session);

    void addOrUpdateDraft(DraftNoticeDTO dto);

    String uploadImageUrl(MultipartFile file);

    PageUtil getSelfDraftNoticeList(Map<String,Object> params);

    void deleteDraft(List<Long> ids);

    DraftNoticeVO getDraftNoticeDetail(Long id);

}
