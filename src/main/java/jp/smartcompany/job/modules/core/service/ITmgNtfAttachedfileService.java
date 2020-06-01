package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgNtfAttachedfileDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 休暇休業申請添付ファイルテーブル 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgNtfAttachedfileService extends IService<TmgNtfAttachedfileDO> {
        /**添付ファイル一覧*/
        List<TmgNtfAttachedfileDO>  selectFileDisp(String custId,String compId,String ntfNo);
        }
