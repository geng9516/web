package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNtfAttachedfileDO;
import jp.smartcompany.job.modules.core.mapper.TmgNtfAttachedfile.TmgNtfAttachedfileMapper;
import jp.smartcompany.job.modules.core.service.ITmgNtfAttachedfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 休暇休業申請添付ファイルテーブル 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgNtfAttachedfileServiceImpl extends ServiceImpl<TmgNtfAttachedfileMapper, TmgNtfAttachedfileDO> implements ITmgNtfAttachedfileService {


        /**添付ファイル一覧*/
        @Override
        public List<TmgNtfAttachedfileDO> selectFileDisp(String custId, String compId, String ntfNo){
                return baseMapper.selectFileDisp(custId,compId,ntfNo);
        }

        /**seq*/
        @Override
        public String  selectSeq(String custId,String compId,String ntfNo){
                return baseMapper.selectSeq(custId,compId,ntfNo);
        }

        }
