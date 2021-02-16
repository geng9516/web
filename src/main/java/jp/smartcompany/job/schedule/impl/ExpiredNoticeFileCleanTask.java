package jp.smartcompany.job.schedule.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardFileDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempFileDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardFileService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardTempFileService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardTempService;
import jp.smartcompany.job.schedule.ITask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务
 * 清理揭示板上传的已经过期的公告附件和富文本图片
 */
@Component
@RequiredArgsConstructor
public class ExpiredNoticeFileCleanTask implements ITask {

    private final IHistBulletinBoardService histBulletinBoardService;
    private final IHistBulletinBoardTempService histBulletinBoardTempService;
    private final IHistBulletinBoardFileService boardFileService;
    private final IHistBulletinBoardTempFileService boardTempFileService;

    /**
     * 每天凌晨四点执行
     */
    @Scheduled(cron = "0 0 4 * * ?")
    @Override
    public void execute() {
        QueryWrapper<HistBulletinBoardDO> bqw = SysUtil.query();
        Date now = DateUtil.date();
        bqw.lt("HB_DDATEOFEXPIRE",SysUtil.transDateToString(now)).select("HB_ID");
        List<Long> boardIdList = histBulletinBoardService.list(bqw).stream().map(HistBulletinBoardDO::getHbId).collect(Collectors.toList());

        QueryWrapper<HistBulletinBoardTempDO> btqw = SysUtil.query();
        btqw.lt("HBT_DDATEOFEXPIRE",SysUtil.transDateToString(now)).select("HBT_ID");
        List<Long> tempBoardIdList = histBulletinBoardTempService.list(btqw).stream().map(HistBulletinBoardTempDO::getHbtId).collect(Collectors.toList());

        if (CollUtil.isNotEmpty(boardIdList)) {
          // 对已经过期的存在物理硬盘上的文件做删除处理，并且将数据库里的记录字段valid设为false=无效
          List<HistBulletinBoardFileDO> fileList = boardFileService.listFileByIds(boardIdList);
          fileList.forEach(item -> {
              if (item.getHbfValid()) {
                  item.setHbfValid(false);
                  File preAvatarFile = new File(item.getHbfFileRealPath());
                  if (preAvatarFile.exists() && !preAvatarFile.delete()) {
                      throw new GlobalException("ファイル削除失敗");
                  }
              }
          });
          boardFileService.updateBatchById(fileList);
        }

        if (CollUtil.isNotEmpty(tempBoardIdList)) {
          // 对已经过期的存在物理硬盘上的文件做删除处理，并且将数据库里的记录字段valid设为false=无效
          List<HistBulletinBoardTempFileDO> tempFileList = boardTempFileService.listFileByIds(tempBoardIdList);
          tempFileList.forEach(item -> {
              if (item.getHbtfValid()) {
                  item.setHbtfValid(false);
                  File preAvatarFile = new File(item.getHbtfFileRealPath());
                  if (preAvatarFile.exists() && !preAvatarFile.delete()) {
                      throw new GlobalException("ファイル削除失敗");
                  }
              }
          });
          boardTempFileService.updateBatchById(tempFileList);
        }

    }

}
