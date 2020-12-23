package jp.smartcompany.job.modules.tmg_inp.noticeboard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.mapper.HistBulletinBoardTempFileMapper;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.pojo.entity.HistBulletinBoardTempFileDO;
import jp.smartcompany.job.modules.tmg_inp.noticeboard.service.IHistBulletinBoardTempFileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistBulletinBoardTempFileServiceImpl  extends ServiceImpl<HistBulletinBoardTempFileMapper, HistBulletinBoardTempFileDO>
implements IHistBulletinBoardTempFileService {

    @Override
    public List<HistBulletinBoardTempFileDO> listFileById(Long boardId) {
        QueryWrapper<HistBulletinBoardTempFileDO> qw = SysUtil.query();
        qw.eq("HBT_ID_FK",boardId);
        return list(qw);
    }

}
