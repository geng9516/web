package jp.smartcompany.framework.component.logic;

import jp.smartcompany.framework.component.dto.QueryConditionDTO;
import jp.smartcompany.framework.component.dto.QueryConditionRowDTO;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Xiao Wenpeng
 *
 */
public interface QueryConditionValidatorLogic {

    /**
     * 定義情報チェック処理(条件式定義)<br>
     * @param queryConditionList 現在、定義されている条件定義情報を取得する
     * @return  int チェック結果
     */
    int checkQueryCondition(List<QueryConditionRowDTO> queryConditionList);

    /**
     * 名称マスタコード取得処理<br>
     * 指定された値がマスタコードであった場合、名称マスタコードを返却します。
     *
     * @param psValue   値(マスタコード)
     * @return  String  名称マスタコード
     */
    String getMaseterId(String psValue);

    /**
     * 表示比較演算子取得処理<br>
     * 指定された値がマスタコードであった場合、表示用の比較演算子を返却します。
     *
     * @param psMaseterId   名称マスタコード
     * @param psOperator    設定済の比較演算子
     * @return  String  表示用比較演算子
     */
    String getDispOperator(String psMaseterId, String psOperator);

    /**
     * 条件式定義コンポーネント用Validatorを生成する
     * @author isolyamada
     * @param   psCustId    顧客コード
     * @param   nBaseFlg    表示フラグ(0:グループ定義 1:検索対象範囲)
     * @return Validator
     */
//    public Validator createValidator(String psCustId, int nBaseFlg);




}
