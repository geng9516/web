package jp.smartcompany.controller.tmg_inp;

import jp.smartcompany.job.modules.tmg_inp.noticeboard.logic.INoticeBoardLogic;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.DraftNoticeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/noticeboard")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final INoticeBoardLogic noticeBoardLogic;

    @GetMapping("rangelist")
    public List<NoticeRangeDTO> getNoticeRangeList(HttpSession session) {
       return noticeBoardLogic.getSendNoticeRangeList(session);
    }

    // http://localhost:6879/sys/noticeboard/validemps?typeIds=02&typeIds=03&typeIds=05&typeIds=06&typeIds=07&typeIds=09&typeIds=10
    @GetMapping("validemps")
    public List<Map<String,String>> getValidReadEmpList(@RequestParam List<String> typeIds, HttpSession session) {
       return noticeBoardLogic.getValidReadEmpList(typeIds,session);
    }

    @PostMapping("draft/addOrUpdate")
    public String addOrUpdateDraft(
            @RequestParam(value="files",required = false) List<MultipartFile> attachments,
            @Valid @RequestParam DraftNoticeDTO dto
                                   ) {
        System.out.println(attachments);
        System.out.println(dto);
//        noticeBoardLogic.addOrUpdateDraft(dto,attachments);
        return "下書き操作成功";
    }

}
