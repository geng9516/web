package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgLiquidationDailyMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyDO;
import jp.smartcompany.job.modules.core.service.ITmgliquidationDailyService;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationDailyDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.MonthSumTimeDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDailyInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TmgLiquidationDailyServiceImpl extends ServiceImpl<TmgLiquidationDailyMapper, TmgLiquidationDailyDO> implements ITmgliquidationDailyService {
    @Override
    public List<String> getMonthList(String empId, String startDate, String endDate){
        return baseMapper.getMonthList( empId, startDate, endDate);
    }

    @Override
    public List<LiquidationDailyDto> getMonthInfo(String empId, String yyyymm){
        return baseMapper.getMonthInfo( empId,yyyymm);
    }

    @Override
    public void execTLDDInsert(String empId, String startDate, String endDate, String userCode, String custID, String compCode){
        baseMapper.execTLDDInsert( empId,  startDate,  endDate,  userCode,  custID,  compCode);
    }


    @Override
    public List<MonthSumTimeDto> getMonthSumTime(String empId, String startDate, String endDate,String custID, String compCode){
        return baseMapper.getMonthSumTime( empId,  startDate,  endDate,custID,compCode);
    }

    @Override
    public int checkLiquidationDaily(String custID, String compCode, String empid, String yyyymmdd) {
        return  baseMapper.checkLiquidationDaily(  custID,  compCode,  empid,  yyyymmdd);
    }


    @Override
    public List<LiquidationDailyInfoVo> selectDailyInfo(String custID, String compCode, String empId, String yyyymm){
        return  baseMapper.selectDailyInfo(  custID,  compCode,  empId,  yyyymm);
    }

    @Override
    public void execTDAInsert(String empId, String startDate, String endDate, String userCode, String custID, String compCode){
        baseMapper.execTDAInsert( empId,  startDate,  endDate, userCode, custID, compCode);
    }
}
