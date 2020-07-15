package jp.smartcompany.framework.component.logic;

import jp.smartcompany.framework.component.dto.QueryConditionDTO;

import java.util.ResourceBundle;

/**
 * @author Xiao Wenpeng
 *
 */
public interface QueryConditionValidatorLogic {

    /**
     * 定義情報チェック処理(条件式定義)<br>
     *
     * @return  int チェック結果
     */
    public int checkQueryCondition();

    /**
     * 名称マスタコード取得処理<br>
     * 指定された値がマスタコードであった場合、名称マスタコードを返却します。
     *
     * @param psValue   値(マスタコード)
     * @return  String  名称マスタコード
     */
    public String getMaseterId(String psValue);

    /**
     * 表示比較演算子取得処理<br>
     * 指定された値がマスタコードであった場合、表示用の比較演算子を返却します。
     *
     * @param psMaseterId   名称マスタコード
     * @param psOperator    設定済の比較演算子
     * @return  String  表示用比較演算子
     */
    public String getDispOperator(String psMaseterId, String psOperator);

    /**
     * 条件式定義コンポーネント用Validatorを生成する
     * @author isolyamada
     * @param   psCustId    顧客コード
     * @param   nBaseFlg    表示フラグ(0:グループ定義 1:検索対象範囲)
     * @return Validator
     */
//    public Validator createValidator(String psCustId, int nBaseFlg);

    /**
     * resourceBundle 設定する。
     *
     * @param resourceBundle を設定
     */
    public void setResourceBundle(ResourceBundle resourceBundle);

    /**
     * 条件式定義コンポーネント用Dtoの自動インジェクション
     *
     * @param queryConditionDto を設定
     */
    public void setQueryConditionDto(QueryConditionDTO queryConditionDto);


}
