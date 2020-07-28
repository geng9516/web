package jp.smartcompany.admin.component.logic;

import jp.smartcompany.admin.component.dto.BaseSectionRowListDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupbasesectionDO;

public interface BaseSectionLogic {

    /**
     * DB新規登録(更新)処理用のEntity作成処理(グループ基点組織マスタ)<br>
     * 定義情報をDBへ反映させるためのEntityを作成する。
     *
     * @param poDto             基点組織選択用Dto
     * @return MastGroupEntity  更新用Entity
     */
    MastGroupbasesectionDO insertBaseSection(BaseSectionRowListDTO poDto);
}
