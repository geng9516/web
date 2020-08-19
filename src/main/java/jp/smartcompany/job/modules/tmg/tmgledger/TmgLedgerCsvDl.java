package jp.smartcompany.job.modules.tmg.tmgledger;

import jp.smartcompany.job.modules.core.util.PsDBBean;

/**
 * 帳票のCSVダウンロード機能を提供するインタフェースクラス
 *
 * @nsc-sci
 *
 */
public class TmgLedgerCsvDl {
    /**
     * パラメータ設定
     *
     * @param pPsDbBean PsDBBeanインスタンス
     * @param pParam    帳票出力パラメータクラス
     */
    public void setParam(PsDBBean pPsDbBean, TmgLedgerParam pParam){};

    /**
     * 帳票のCSVファイルをダウンロードします。
     *
     * @param  sFileName ダウンロードファイル名
     * @throws Exception
     */
    public void download(String sFileName) throws Exception {};
}
