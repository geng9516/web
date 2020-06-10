package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.ConfSyscontrolDO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * システムプロパティマスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IConfSyscontrolService extends IService<ConfSyscontrolDO> {


        /**
         * システムプロパティを取得する
         *
         * @param customerId     法人コード
         * @param propertyName    システムプロパティ名
         * @return String システムプロパティ値
         */
        String selectPropertyValue(String customerId, String propertyName);

        /**
         * 引数の条件でデータ取得できない場合は、顧客コード「00」でデータ取得
         *
         * @param customerId     法人コード
         * @param propertyName    システムプロパティ名
         * @return String システムプロパティ値
         */
        String selectPropertyValueNotFound(String customerId, String propertyName);

        /**
         * 读取SysControl全部的值
         * @return
         */
        List<ConfSyscontrolDO> getProperties();
}
