package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempFileDO;

import java.util.List;

public interface IHistBulletinBoardTempFileService extends IService<HistBulletinBoardTempFileDO> {

    List<HistBulletinBoardTempFileDO> listFileById(Long boardId);

}
