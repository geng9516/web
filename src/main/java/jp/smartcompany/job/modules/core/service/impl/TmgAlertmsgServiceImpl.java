package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAlertmsgDO;
import jp.smartcompany.job.modules.core.mapper.TmgAlertmsg.TmgAlertmsgMapper;
import jp.smartcompany.job.modules.core.service.ITmgAlertmsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.AlertVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [勤怠]アラートメッセージ格納テーブル 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgAlertmsgServiceImpl extends ServiceImpl<TmgAlertmsgMapper, TmgAlertmsgDO> implements ITmgAlertmsgService {



        /**
         * 集計時の問題(アラート)の表示内容を取得するクエリを返却します
         */
        @Override
        public List<AlertVo> selectAlert(String cust, String comp, String secid, String dyyyymm, String lang, int numStart, int numEnd){
                return baseMapper.selectAlert(cust, comp, secid, dyyyymm, lang, numStart, numEnd);
        }
        }
