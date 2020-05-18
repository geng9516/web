package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 *	id          Evaluator
 *  title       評価者情報格納クラス
 *              異動歴情報格納クラスの子クラスとして実装
 *              「評価レベル」「レポートラインタイプ」などの評価者情報を保持する
 * @author Xiao Wenpeng
 * 对应原代码中的Evaluator类：jp.co.scientia.framework.common.Evaluator
 *
 */
@ToString
@Getter
@Setter
public class EvaluatorBO extends DesignationBO {
    /** 評価レベル */
    private String evalLevel;

    /** レポートラインタイプ */
    private String reportLine;

    /** 判定区分(V3互換にて使用) */
    private String judgDiv;
}
