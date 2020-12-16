package jp.smartcompany.controller.tmg_inp;

import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.INoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("sys/noticeboard")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final INoticeBoardService noticeBoardService;

    @GetMapping("rangelist")
    public List<NoticeRangeDTO> getNoticeRangeList(HttpSession session) {
       return noticeBoardService.getSendNoticeRangeList(session);
    }

    // http://localhost:6879/sys/noticeboard/validemps?typeIds=02&typeIds=03&typeIds=05&typeIds=06&typeIds=07&typeIds=09&typeIds=10
    @GetMapping("validemps")
    public List<String> getValidReadEmpList(@RequestParam List<String> typeIds,HttpSession session) {
       return noticeBoardService.getValidReadEmpList(typeIds,session);
    }



}
