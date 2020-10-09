package jp.smartcompany.admin.component.logic.impl;

import jp.smartcompany.admin.component.dto.BaseSectionRowListDTO;
import jp.smartcompany.admin.component.logic.BaseSectionLogic;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupbasesectionDO;
import org.springframework.stereotype.Service;

@Service
public class BaseSectionLogicImpl implements BaseSectionLogic {

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ基点組織マスタ)<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。
     *
     * @param poDto             基点組織選択用Dto
     * @return MastGroupEntity  更新用Entity
     */
    @Override
    public MastGroupbasesectionDO insertBaseSection(BaseSectionRowListDTO poDto) {
        // 入れ替え用Entity
        MastGroupbasesectionDO oInsert = new MastGroupbasesectionDO();
        oInsert.setMgbsId(poDto.getMgbsId());
        oInsert.setMgbsCcustomerid(poDto.getMgbsCcustomerid());
        oInsert.setMgbsCsystemid(poDto.getMgbsCsystemid());
        oInsert.setMgbsCgroupid(poDto.getMgbsCgroupid());
        oInsert.setMgbsDstartdate(poDto.getMgbsDstartdate());
        if (poDto.getMgbsDenddate()==null) {
            oInsert.setMgbsDenddate(SysUtil.getMaxDateObject());
        } else {
            oInsert.setMgbsDenddate(poDto.getMgbsDenddate());
        }
        oInsert.setMgbsCcompanyid(poDto.getMgbsCcompanyid());
        oInsert.setMgbsCsectionid(poDto.getMgbsCsectionid());
        oInsert.setMgbsClayeredsectionid(poDto.getMgbsClayeredsectionid());
        oInsert.setMgbsCbeloworsingle(poDto.getMgbsCbeloworsingle());
        return oInsert;
    }

}
