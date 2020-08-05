package jp.smartcompany.job.modules.tmg.tmgledger.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LedgerSheetVo {

    /**
     * 帳票識別ID
     */
    private  String mgdLedgerSheetId;
    /**
     * 帳票名
     */
    private  String mgdLedgerSheetName;
    /**
     * 出力タイプ（組織、個人）
     */
    private  String mgdOutputTargetType;
    /**
     * 出力タイプ（期間）
     */
    private  String mgdOutputTermType;
    /**
     * PDFダウンロード表示フラグ
     */
    private  String mgdPdfDlflg;
    /**
     * CSVダウンロード表示フラグ
     */
    private  String mgdCsvDlflg;
    /**
     * レコード順
     */
    private  String mgdSort;

}
