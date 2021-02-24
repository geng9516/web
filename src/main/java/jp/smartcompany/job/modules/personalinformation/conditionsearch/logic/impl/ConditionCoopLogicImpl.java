package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionCoopLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.bo.SqlBO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.CooperationDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchCoopDetailService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchCoopService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.util.ConditionSearchSqlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ConditionCoopLogicImpl implements IConditionCoopLogic {

    private final IHistSearchCoopService histSearchCoopService;
    private final ConditionSearchSqlBuilder sqlBuilder;
    private final IConditionSearchLogic conditionSearchLogic;
    private final IHistSearchCoopDetailService histSearchCoopDetailService;

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void saveCoop(ConditionSettingDTO settingDTO) {
        CooperationDTO dto = settingDTO.getCoopDTO();
        Boolean exists = histSearchCoopService.selectSameDataName(dto.getHscCdataname());
        if (exists) {
            throw new GlobalException("名称が重複している");
        }
        HistSearchCoopDO histSearchCoopDO = new HistSearchCoopDO();
        BeanUtil.copyProperties(dto,histSearchCoopDO);

        String loginUserId = SecurityUtil.getUserId();
        Date now = DateUtil.date();
        histSearchCoopDO.setHscCcustomerid("01");
        histSearchCoopDO.setHscCuserid(loginUserId);
        histSearchCoopDO.setHscDmodifieddate(now);
        Long oDataId = histSearchCoopService.selectSeq();
        histSearchCoopDO.setHscNdataId(oDataId);
        histSearchCoopService.save(histSearchCoopDO);

        settingDTO.setHseNdataId(oDataId);
        SqlBO sqlBO = sqlBuilder.createSql(settingDTO);
        String searchSql = conditionSearchLogic.getSearchSql(settingDTO,sqlBO);
        histSearchCoopDetailService.deleteDetail(oDataId);
        // ORDER BY句は外す
        String[] selectSql = searchSql.split("ORDER");
        if (selectSql.length>0) {
            histSearchCoopDetailService.saveQuery(selectSql[0]);
        } else {
            throw new GlobalException("クエリ結果が存在しません");
        }
    }

}
