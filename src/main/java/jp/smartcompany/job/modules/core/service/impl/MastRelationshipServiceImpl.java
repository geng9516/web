package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastRelationshipDO;
import jp.smartcompany.job.modules.core.mapper.MastRelationshipMapper;
import jp.smartcompany.job.modules.core.service.IMastRelationshipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysDateUtil;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * リレーションシップマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastRelationshipServiceImpl extends ServiceImpl<MastRelationshipMapper, MastRelationshipDO> implements IMastRelationshipService {

        @Override
        public List<MastRelationshipDO> selectEvaluationLevel(String psSystemId, int pnEvaluation, Date pdSearchDate) {
                Date d = SysDateUtil.of(1900,1,1);
                QueryWrapper<MastRelationshipDO> qw= SysUtil.query();
                qw.lt("MR_DCREATEDDATE", d)
                .and(w->
                        w.eq("MR_CDELFLAG",0)
                         .or()
                         .and(wr->
                                 wr.eq("MR_CDELFLAG",1)
                                         .gt("MR_DDELDATE",d)
                         )
                )
                .eq("MR_CDOMAINID_FK","01")
                .eq("MR_CLANGUAGE","ja")
                .eq("MR_CSYSTEMID", psSystemId)
                .and(pnEvaluation>0, w-> w.gt("MR_NEVALUATION",1).lt("MR_NEVALUATION",pnEvaluation))
                .orderByAsc("MR_NEVALUATION");
                return list(qw);
        }

}
