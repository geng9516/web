package jp.smartcompany.controller.tmg_inp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.logic.INoticeBoardLogic;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.DraftNoticeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.dto.NoticeRangeDTO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.vo.DraftNoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sys/noticeboard")
@RequiredArgsConstructor
@Validated
public class NoticeBoardController {

    private final INoticeBoardLogic noticeBoardLogic;

    /**
     * 查询可用于发送的人的范围type
     * @param session
     * @return
     */
    @GetMapping("rangelist")
    public List<NoticeRangeDTO> getNoticeRangeList(HttpSession session) {
       return noticeBoardLogic.getSendNoticeRangeList(session);
    }

    /**
     * 查询当前可用的发送用户组
     * @param typeIds
     * @param session
     * @return
     */
    // http://localhost:6879/sys/noticeboard/validemps?typeIds=02&typeIds=03&typeIds=05&typeIds=06&typeIds=07&typeIds=09&typeIds=10
    @GetMapping("validemps")
    public List<Map<String,String>> getValidReadEmpList(@RequestParam List<String> typeIds, HttpSession session) {
       return noticeBoardLogic.getValidReadEmpList(typeIds,session);
    }

    /**
     * 查询自己现有的草稿列表
     * @param params
     * @return
     */
    // http://localhost:6879/sys/noticeboard/draft/list
    @GetMapping("draft/list")
    public PageUtil getSelfDraftNoticeList(@RequestParam Map<String,Object> params) {
        return noticeBoardLogic.getSelfDraftNoticeList(params);
    }

    /**
     * 查询草稿详情
     * @param id 草稿id
     * @return
     */
    // http://localhost:6879/sys/noticeboard/draft/2
    @GetMapping("draft/{id:\\d+}")
    public DraftNoticeVO getDraftNoticeDetail(@PathVariable Long id) {
      return noticeBoardLogic.getDraftNoticeDetail(id);
    }

    /**
     * 添加或修改草稿
     * @param attachments
     * @param hbtId
     * @param hbtDdateofannouncement
     * @param hbtDdateofexpire
     * @param hbtCtitle
     * @param hbtCcontents
     * @param empRangeIds
     * @param hbtCheaddisp
     * @param hbtCfix
     * @return
     */
    @PostMapping("draft/addOrUpdate")
    public String addOrUpdateDraft(
            // 公告附件，最大支持五个附件
            @Size(max=5,message = "5点限りでアプロードできます") @RequestParam(required = false) List<MultipartFile> attachments,
            // 草稿id，不传为新增，传为保存
            Long hbtId,
            // 公告开始日期，默认为今天
            @RequestParam @JsonFormat(pattern = "yyyy/MM/dd") Date hbtDdateofannouncement,
            // 公告截止日期，不填默认永不截止
            @JsonFormat(pattern = "yyyy/MM/dd") Date hbtDdateofexpire,
            // 公告标题
            @RequestParam String hbtCtitle,
            // 公告内容
            @RequestParam String hbtCcontents,
            // 所选发送范围type，用逗号隔开
            @RequestParam String sendRangeTypes,
            // 可查看此公告的用户id，用逗号隔开
            @RequestParam String empRangeIds,
            // 1:置顶 0:不置顶 不填默认0
            @RequestParam(required = false,defaultValue = "0") String hbtCheaddisp,
            // 0:当作草稿存储 1:保存为正式公告 不填默认0
            @RequestParam(required = false,defaultValue = "0") String hbtCfix,
            // 修改时需要删除的附件对应id
            @RequestParam(required = false) String deleteAttachmentIdStr,
            // 是否发布此公告
            @RequestParam(required = false,defaultValue = "0") String isPublish
    ) {
        List<Long> deleteAttachmentIdList = CollUtil.newArrayList();
        if (StrUtil.isNotBlank(deleteAttachmentIdStr)) {
            deleteAttachmentIdList = Arrays.stream(deleteAttachmentIdStr.split(",")).map(Long::parseLong).collect(Collectors.toList());
        }
        DraftNoticeDTO dto = assembleNotice(attachments, hbtId, hbtDdateofannouncement, hbtDdateofexpire, hbtCtitle, hbtCcontents, hbtCheaddisp, hbtCfix, sendRangeTypes,empRangeIds,deleteAttachmentIdList,isPublish);
        noticeBoardLogic.addOrUpdateDraft(dto);
        return "下書き操作成功";
    }

    /**
     * 删除草稿，支持多条删除
     * @param ids 草稿id
     */
    // http://localhost:6879/sys/noticeboard/draft/list
    @PostMapping("draft/delete")
    public String deleteDraft(@RequestBody @NotEmpty List<Long> ids) {
        noticeBoardLogic.deleteDraft(ids);
        return "下書き削除成功";
    }

    /**
     * 富文本上传图片
     * @param file 上传图片
     * @return 图片地址
     */
    @PostMapping("upload/image")
    public String uploadRichTextImage(@RequestParam MultipartFile file) {
        return noticeBoardLogic.uploadImageUrl(file);
    }


    private DraftNoticeDTO assembleNotice(List<MultipartFile> attachments, Long hbtId, Date hbtDdateofannouncement,
                                          Date hbtDdateofexpire, String hbtCtitle, String hbtCcontents,
                                          String hbtCheaddisp, String hbtCfix,String sendRangeTypes,
                                          String empRangeIds,List<Long> deleteAttachmentIdList,String isPublish) {
        DraftNoticeDTO dto = new DraftNoticeDTO();
        dto.setAttachments(attachments);
        dto.setHbtCcontents(hbtCcontents);
        dto.setHbtCtitle(hbtCtitle);
        dto.setHbtCfix(hbtCfix);
        dto.setHbtCheaddisp(hbtCheaddisp);
        dto.setHbtDdateofannouncement(hbtDdateofannouncement);
        dto.setHbtDdateofexpire(hbtDdateofexpire);
        dto.setHbtId(hbtId);
        dto.setRangeTypes(sendRangeTypes);
        dto.setEmpRangeIds(empRangeIds);
        dto.setDeleteAttachmentIdList(deleteAttachmentIdList);
        dto.setIsPublish(isPublish);
        return dto;
    }

}
