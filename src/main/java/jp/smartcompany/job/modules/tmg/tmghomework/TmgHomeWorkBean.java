package jp.smartcompany.job.modules.tmg.tmghomework;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgHomeWorkDataDO;
import jp.smartcompany.job.modules.core.service.ITmgHomeWorkDataService;
import jp.smartcompany.job.modules.core.service.ITmgHomeWorkService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


/**
 * @author 顧成斌
 * @description 在宅勤務登録
 * @objectSource ps.c01.tmg.TmgHomeWork.TmgHomeWorkBean
 * @date 2020/11/20
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TmgHomeWorkBean {

    private final ITmgHomeWorkDataService iTmgHomeWorkDataService;
    private final ITmgHomeWorkService iTmgHomeWorkService;

    public List<HomeWorkVO>  selectHomeWorkData(PsDBBean psDBBean, String baseDate) {
        if (null == baseDate || "".equals(baseDate)) {
            //初期化
            baseDate = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        return iTmgHomeWorkService.selectHomeWork(psDBBean.getEmployeeCode(),baseDate);

    }

    public void updateHmoeWorkData(PsDBBean psDBBean, List<HomeWorkVO> homeWorkVO) {
        for (int i = 0; i < homeWorkVO.size(); i++) {
            TmgHomeWorkDataDO tmgHomeWorkDataDO = new TmgHomeWorkDataDO();
                if( "3".equals(homeWorkVO.get(i).getHwStatus())){

                    tmgHomeWorkDataDO.setHw_status("0");
                    tmgHomeWorkDataDO.setHw_homework("");
                    tmgHomeWorkDataDO.setHw_start("");
                    tmgHomeWorkDataDO.setHw_end("");
                    tmgHomeWorkDataDO.setHw_commute("");
                    tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkVO.get(i).getHwApplicationcomment());

                    QueryWrapper<TmgHomeWorkDataDO> queryWrapper = new QueryWrapper<TmgHomeWorkDataDO>();
                    queryWrapper.eq("hw_ccustomerid", psDBBean.getCustID());
                    queryWrapper.eq("hw_ccompanyid", psDBBean.getCompCode());
                    queryWrapper.eq("hw_cemployeeid", psDBBean.getEmployeeCode());
                    queryWrapper.eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate());

                    iTmgHomeWorkDataService.getBaseMapper().update(tmgHomeWorkDataDO,queryWrapper);

                    continue;
                }else if( "1".equals(homeWorkVO.get(i).getHwStatus()) || "0".equals(homeWorkVO.get(i).getHwStatus())){
                    iTmgHomeWorkDataService.getBaseMapper().delete(SysUtil.<TmgHomeWorkDataDO>query()
                            .eq("hw_ccustomerid", psDBBean.getCustID())
                            .eq("hw_ccompanyid", psDBBean.getCompCode())
                            .eq("hw_cemployeeid", psDBBean.getEmployeeCode())
                            .eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate()));

                    tmgHomeWorkDataDO.setHw_ccustomerid("01");
                    tmgHomeWorkDataDO.setHw_ccompanyid("01");
                    tmgHomeWorkDataDO.setHw_cemployeeid(psDBBean.getEmployeeCode());
                    tmgHomeWorkDataDO.setHw_applicationdate(homeWorkVO.get(i).getHwApplicationdate());
                    tmgHomeWorkDataDO.setHw_status(homeWorkVO.get(i).getHwStatus());
                    tmgHomeWorkDataDO.setHw_homework(homeWorkVO.get(i).getHwHomework());
                    tmgHomeWorkDataDO.setHw_start(homeWorkVO.get(i).getHwStart());
                    tmgHomeWorkDataDO.setHw_end(homeWorkVO.get(i).getHwEnd());
                    tmgHomeWorkDataDO.setHw_commute(homeWorkVO.get(i).getHwCommute());
                    tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkVO.get(i).getHwApplicationcomment());
                    iTmgHomeWorkDataService.getBaseMapper().insert(tmgHomeWorkDataDO);

                    continue;
                }else if(  "2".equals(homeWorkVO.get(i).getHwStatus()) || "4".equals(homeWorkVO.get(i).getHwStatus())){

                    tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkVO.get(i).getHwApplicationcomment());

                    QueryWrapper<TmgHomeWorkDataDO> queryWrapper = new QueryWrapper<TmgHomeWorkDataDO>();
                    queryWrapper.eq("hw_ccustomerid", psDBBean.getCustID());
                    queryWrapper.eq("hw_ccompanyid", psDBBean.getCompCode());
                    queryWrapper.eq("hw_cemployeeid", psDBBean.getEmployeeCode());
                    queryWrapper.eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate());

                    iTmgHomeWorkDataService.getBaseMapper().update(tmgHomeWorkDataDO,queryWrapper);
                    continue;
                }
        }
    }
}