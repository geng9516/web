package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * パスワードマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastPasswordService extends IService<MastPasswordDO> {

        /**
         * 根据用户id和明码获取密码设定日期
         * @param userId
         * @param password
         * @return
         */
        List<MastPasswordDO> getUpdateDateByUsernamePassword(String userId,String password);

        /**
         * パスワード変更処理（パスワードマスタ）検索処理.
         * @param  username ユーザＩＤ
         * @param  passwordHisCnt パスワード履歴保持件数
         * @return パスワードマスタ検索結果
         */
        List<MastPasswordDO> selectSinglePassword(String username, String passwordHisCnt);

}
