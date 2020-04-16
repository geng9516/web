package jp.smartcompany.job.modules.base.service;

import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.base.BaseBean;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPluggableDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaoWenpeng
 */
@Service(BaseBean.Service.PLUGGABLE)
public  class PluggableService {

    /**
     * メソッド実行
     */
    @Transactional(rollbackFor = GlobalException.class)
    public Object execute(PluggableDTO pluggableDTO) {

        Object object = null;
        // TODO プラガブル取得処理
        List<TmgPluggableDO> pluggableBOList = new ArrayList<TmgPluggableDO>();

        try {
            for (TmgPluggableDO pluggableBO: pluggableBOList) {

                // 执行勤务service里的方法
                BaseExecute service = SpringUtil.getBean(pluggableBO.getTplCsparechar04());
                object = service.execute(pluggableDTO);
                if (object != null){
                    return object;
                }
            }
        }catch (GlobalException e){
            // error
            return  new GlobalException("error");
        }

        return object;
    }

    /**
     * メソッド実行
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void execute2(PluggableDTO pluggableDTO) {

        // TODO プラガブル取得処理
        List<TmgPluggableDO> pluggableBOList = new ArrayList<TmgPluggableDO>();

        try {
            for (TmgPluggableDO pluggableBO : pluggableBOList) {

                // 执行勤务service里的方法
                BaseExecute service = SpringUtil.getBean(pluggableBO.getTplCsparechar04());
                service.execute(pluggableDTO);
            }
        }catch (GlobalException e){
            // error
            throw new GlobalException("error");
        }
    }
}
