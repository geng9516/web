package jp.smartcompany.framework.dialog.postselect.logic;

import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

public interface PostGenericLogic {

    /**
     * 役職、名称マスタ選択ダイアログ表示処理（役職用）
     *
     * @param companyCode 法人コード
     * @param searchDate 基準日
     * @param pbUseExistsQuery 検索対象範囲を掛けるかどうか(true:かける)
     * @throws ParseException 変換例外
     */
    Map<String,Object> dispPost(String companyCode,String searchDate, boolean pbUseExistsQuery) throws ParseException;

}
