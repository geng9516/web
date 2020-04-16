package jp.smartcompany.job.modules.base.service;

import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;

/**
 * @author Nie Wanqun
 */
public abstract class BaseExecute {

    /**
     * プラガブル呼び出す用メソッド
     *
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
     public abstract Object execute(PluggableDTO pluggableDTO);

}
