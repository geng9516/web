package jp.smartcompany.framework.sysboot;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.auth.entity.AppAuthJudgmentEntity;
import jp.smartcompany.framework.sysboot.dto.AppAuthJudgmentDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupDO;
import jp.smartcompany.job.modules.core.service.IMastApptreeService;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;

/**
 * アプリケーション起動権限判定常駐変数キャッシュクラス
 * @author Xiao Wenpeng
 */
@Slf4j
public class AppAuthJudgmentCache {

    private final Map< String, List<AppAuthJudgmentDTO>> gAppAuthJudgmentInfo = MapUtil.newConcurrentHashMap();

    /**
     * 情報取得.
     * @param psKey1 キー名1
     * @return アプリケーション設定Bean
     */
    public List<AppAuthJudgmentDTO> getAppAuthJudgmentCache(String psKey1) {
        if (psKey1 == null) {
            return null;
        }
        return gAppAuthJudgmentInfo.get(psKey1);
    }

    public void loadAppAuthJudgment(){
        LRUCache<Object,Object> lruCache = SpringUtil.getBean("scCache");
        IMastGroupService iMastGroupService = SpringUtil.getBean("mastGroupServiceImpl");
        List<MastGroupDO> groupList = iMastGroupService.list(SysUtil.<MastGroupDO>query().select("MG_CSYSTEMID_CK_FK","MG_CGROUPID_PK"));
        // 権限判定が必要な画面オブジェクトの一覧を取得
        IMastApptreeService iMastApptreeService = SpringUtil.getBean("mastApptreeServiceImpl");
        List<AppAuthJudgmentEntity> lAppAuthJudgmentDtoList = iMastApptreeService.selectAppTreePermission();
        StringBuilder sb = new StringBuilder();
        Map<String, AppAuthJudgmentEntity> objectIdMapMaster = MapUtil.newHashMap();
        for (AppAuthJudgmentEntity d : lAppAuthJudgmentDtoList) {
            objectIdMapMaster.put(d.getMtrCobjectid(), d);
        }
        for (MastGroupDO mastGroupDO : groupList) {
            // 初期化
            sb.delete(0, sb.length());
            // システム
            sb.append(mastGroupDO.getMgCsystemidCkFk());
            sb.append("_");
            // グループID
            sb.append(mastGroupDO.getMgCgroupidPk());
            // 初期化
            String sLanguage = null;
            // 保存用取得
            // グループの画面オブジェクトに対する権限を取得する
            List<AppAuthJudgmentEntity> lAppAuthJudgmentDtoListBase =
                    iMastApptreeService.selectGroupPermission(
                            mastGroupDO.getMgCsystemidCkFk(),
                            mastGroupDO.getMgCgroupidPk()
                    );
            Map<String, AppAuthJudgmentEntity> objectIdMapBase = MapUtil.newHashMap();
            for (AppAuthJudgmentEntity d : lAppAuthJudgmentDtoListBase) {
                objectIdMapBase.put(d.getMtrCobjectid(), d);
            }
            // 言語分登録
            for (int i=0; i < 5; i++){
                // 判定
                if(i == 0 ){
                    sLanguage = "ja";
                }else if(i == 1){
                    sLanguage = "en";
                }else if(i == 2){
                    sLanguage = "ch";
                }else if(i == 3){
                    sLanguage = "01";
                }else {
                    sLanguage = "02";
                }
                sb.append("_");
                // リレーションIO
                sb.append(sLanguage);
                List<AppAuthJudgmentDTO> lAppAuthJudgmentDTOList = CollUtil.newArrayList();

                for (AppAuthJudgmentEntity appAuthJudgmentEntity : lAppAuthJudgmentDtoList) {
                    // 画面オブジェクトのインスタンスをコピーして
                    // そこにグループの権限を設定する
                    AppAuthJudgmentDTO c = new AppAuthJudgmentDTO(appAuthJudgmentEntity);
                    lAppAuthJudgmentDTOList.add(c);
                    AppAuthJudgmentEntity perm = objectIdMapBase.get(c.getMtrCobjectid());
                    if (perm != null) {
                        c.setMgpCreject(perm.getMgpCreject());
                        c.setMgpCpermission(perm.getMgpCpermission());
                    }
                    AppAuthJudgmentEntity site = objectIdMapMaster.get(c.getMtrCsiteid());
                    switch (sLanguage) {
                        case "ja":
                            c.setMtrObjname(c.getMtrCobjnameja());
                            c.setMtrSitecaption(site.getMtrCsitecaptionja());
                            break;
                        case "en":
                            c.setMtrObjname(c.getMtrCobjnameen());
                            c.setMtrSitecaption(site.getMtrCsitecaptionen());
                            break;
                        case "ch":
                            c.setMtrObjname(c.getMtrCobjnamech());
                            c.setMtrSitecaption(site.getMtrCsitecaptionch());
                            break;
                        case "01":
                            c.setMtrObjname(c.getMtrCobjname01());
                            c.setMtrSitecaption(site.getMtrCsitecaption01());
                            break;
                        case "02":
                            c.setMtrObjname(c.getMtrCobjname02());
                            c.setMtrSitecaption(site.getMtrCsitecaption02());
                            break;
                        default:
                            break;
                    }
                }
                // 保存
                gAppAuthJudgmentInfo.put(sb.toString(), lAppAuthJudgmentDTOList);
                // 前の言語区分を削除
                sb.delete(sb.length() - 3, sb.length());
            }
        }
//        log.info("【アプリケーション起動権限判定:{}】",gAppAuthJudgmentInfo);
        lruCache.put(ScCacheUtil.APP_AUTH_JUDGMENT_CACHE, gAppAuthJudgmentInfo);
    }
}
