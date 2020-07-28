package jp.smartcompany.framework.dialog.postselect.logic.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.framework.dialog.postselect.logic.PostGenericLogic;
import jp.smartcompany.framework.util.PsBuildTargetSql;
import jp.smartcompany.job.modules.core.pojo.entity.MastPostDO;
import jp.smartcompany.job.modules.core.service.IMastPostService;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class PostGenericLogicImpl implements PostGenericLogic {

  private final ScCacheUtil scCacheUtil;
  private final PsBuildTargetSql psBuildTargetSql;
  private final IMastPostService iMastPostService;

  /** キー：コード項目表示可否 */
  public static final String KEY_DISPCODEMASTERDLG = "DispCodeMasterDlg";

    /** 値：コード項目表示可 */
    public static final String VALUE_DISPCODEMASTERDLG_YES = "yes";

    /** 値：コード項目表示否 */
    public static final String VALUE_DISPCODEMASTERDLG_NO = "no";


    @Override
  public Map<String,Object> dispPost(String companyCode,String searchDate, boolean pbUseExistsQuery)  {
      Map<String,Object> maps = MapUtil.newHashMap();
      PsSession session = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
      // *****************************************
      // 初期処理
      // *****************************************
      String sCodeFlg = getDispCodeMasterDlg();
      // *****************************************
      // 役職マスタ検索処理
      // *****************************************

        //検索対象範囲のwhere句取得
        String sExists = "";
        if (pbUseExistsQuery) {
            sExists = psBuildTargetSql.getExistsQueryCompany("MAST_POST.MAP_CCOMPANYID_CK_FK");
        }
        List<MastPostDO> oEntity = iMastPostService.select(companyCode, searchDate, session.getLanguage(), session.getLoginCustomer(), sExists);
        maps.put("list",oEntity);
        // 昇順の場合または初期表示の場合
//        if (StrUtil.equals("0",psSortkbn) || StrUtil.isBlank(psSortkbn)) {
//            maps.put("asc",true);
//            maps.put("desc",false);
//        } else {
//            // 降順の場合
//            maps.put("asc",false);
//            maps.put("desc",true);
//        }
        // コードの場合または初期表示の場合
        // 昇順
//        if (StrUtil.equals("0",psAscDesckbn) || StrUtil.isBlank(psAscDesckbn)) {
//            maps.put("ascDescData","▼");
//        // 降順
//        } else {
//            maps.put("ascDescData","▲");
//        }
        // *****************************************
        // 画面表示
        // *****************************************
        // this.setScrrenInfo(sCodeFlg, psSelectMode);
//        if (StrUtil.equals("0",psSelectMode)) {
//            maps.put("gbModeFlg",false);
//        } else {
//            maps.put("gbModeFlg",true);
//        }
        if (VALUE_DISPCODEMASTERDLG_YES.equalsIgnoreCase(sCodeFlg)) {
            maps.put("gsCodeFlg",VALUE_DISPCODEMASTERDLG_YES);
        } else if (VALUE_DISPCODEMASTERDLG_NO.equalsIgnoreCase(sCodeFlg)) {
            maps.put("gsCodeFlg",VALUE_DISPCODEMASTERDLG_NO);
        }
//        if ("0".equals(psSelectMode) && VALUE_DISPCODEMASTERDLG_NO.equalsIgnoreCase(sCodeFlg)) {
//            maps.put("gnObjectCount",1);
//        } else if ("0".equals(psSelectMode) || VALUE_DISPCODEMASTERDLG_NO.equalsIgnoreCase(sCodeFlg)) {
//            maps.put("gnObjectCount",2);
//        } else {
//            maps.put("gnObjectCount",3);
//        }
        return maps;
  }

    /**
     * システムプロパティ（コード項目表示可否）取得処理
     *
     * @return sCodeFlg
     */
    private String getDispCodeMasterDlg() {
        String sCodeFlg = scCacheUtil.getSystemProperty(KEY_DISPCODEMASTERDLG);
        // 必須常駐変数
        if (sCodeFlg == null) {
            throw new GlobalException(KEY_DISPCODEMASTERDLG);
        }
        if (!VALUE_DISPCODEMASTERDLG_YES.equalsIgnoreCase(sCodeFlg) && !VALUE_DISPCODEMASTERDLG_NO.equalsIgnoreCase(sCodeFlg)) {
            throw new GlobalException(KEY_DISPCODEMASTERDLG);
        }
        return sCodeFlg;
    }

}
