package jp.smartcompany.job.modules.tmg_inp.noticeboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper.HistBulletinBoardFileMapper;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardFileDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardFileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistBulletinBoardFileServiceImpl extends ServiceImpl<HistBulletinBoardFileMapper, HistBulletinBoardFileDO>
implements IHistBulletinBoardFileService {

    @Override
    public List<HistBulletinBoardFileDO> listFileById(Long boardId) {
        QueryWrapper<HistBulletinBoardFileDO> qw = SysUtil.query();
        qw.eq("HB_ID_FK",boardId);
        return list(qw);
    }

}
