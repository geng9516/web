package jp.smartcompany.job.modules.tmg_inp.noticeboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardFileDO;

import java.util.List;

public interface IHistBulletinBoardFileService extends IService<HistBulletinBoardFileDO> {

    List<HistBulletinBoardFileDO> listFileById(Long boardId);

}
