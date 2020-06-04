package jp.smartcompany.job.modules.core.util.searchrange;

import jp.smartcompany.job.common.GlobalException;

import java.util.Enumeration;

public class CSVTokenizer implements Enumeration {

    public static final String copyright = "Copyright 1997 TAMURA Kent" + "\n" + "Copyright 1999 ANDOH Tomoharu";

    private String	source;				// CSV形式文字列
    private int		currentPosition;	// 次回呼び出し位置
    private int		maxPosition;		// 最終呼び出し位置（文字列長）

    /****************************************************************************
     * CSV形式のString lineを解析するCSVTokenizerのインスタンスを作成
     *
     * @param		psLine	CSV形式の文字列、改行コードを含まない。
     * @return	なし
     */
    public CSVTokenizer(String psLine) {

        // CSV形式文字列
        source = psLine;

        // 次回呼び出し位置
        currentPosition = 0;

        // 最終呼び出し位置（文字列長）
        maxPosition = psLine.length();

    }

    /****************************************************************************
     * 次のカンマがある位置を返す。
     * カンマが残っていない場合は nextComma() == maxPosition となる。
     * また最後の項目が空の場合も nextComma() == maxPosition となる。
     * ["]で囲まれている文字列が[,]を含んでいる場合は区切らず1つの文字列とする。
     *
     * @param		pnIndex	検索を開始する位置
     * @return	int		次のカンマがある位置。カンマがない場合は、文字列の長さの値となる。
     */
    private int nextComma(int pnIndex) {

        boolean inquote = false;

        while (pnIndex < maxPosition) {

            // 検索開始位置の１文字取得
            char ch = source.charAt(pnIndex);

            // カンマ
            if (!inquote && ch == ',') {
                break;
            }
            else if ('"' == ch) {
                inquote = !inquote; // ""の処理もこれでOK
            }

            pnIndex++;
        }

        return pnIndex;

    }

    /****************************************************************************
     * 含まれている項目の数を返す。
     *
     * @return int	含まれている項目の数
     */
    public int countTokens() {

        int i		= 0;
        int ret	= 1;

        while ((i = nextComma(i)) < maxPosition) {
            i++;
            ret++;
        }

        return ret;
    }

    /****************************************************************************
     * 次の項目の文字列を返す。
     *
     * @return String	次の項目
     * @exception
     * @throws
     */
    public String nextToken() {

        // ">=" では末尾の項目を正しく処理できない。
        // 末尾の項目が空（カンマで1行が終わる）場合、例外が発生して
        // しまうので。
        if (currentPosition > maxPosition) {
            throw new GlobalException(toString() + "#nextToken");
        }

        int st = currentPosition;

        currentPosition = nextComma(currentPosition);

        StringBuffer strb = new StringBuffer();

        while (st < currentPosition) {

            char ch = source.charAt(st++);

            // "の時
            if (ch == '"') {
                // "が単独で現れたときはスキップする
                if ((st < currentPosition) && (source.charAt(st) != '"')) {
                }
                // "がエスケープされているときは1つ書き出して１つスキップする
                // 「,"""",」のときに対応するために「currentPosition-1」とする
                else if ((st < currentPosition-1) && (source.charAt(st) == '"')) {
                    strb.append(ch);
                    st++;
                }
            }
            // "以外の時はそのまま書き出し
            else {
                strb.append(ch);
            }
        }
        currentPosition++;
        return new String(strb);
    }

    /****************************************************************************
     * <code>nextToken</code>メソッドと同じで、次の項目の文字列を返す。
     * ただし返値はString型ではなくObject型である。
     * java.util.Enumerationを実装しているためこのメソッドがある。
     *
     * @return Object	次の項目
     * @exception
     */
    @Override
    public Object nextElement() {
        return nextToken();
    }

    /****************************************************************************
     * <code>nextToken</code>メソッドと同じで、まだ項目が残っているかどうか調べる。
     * ただし返値はString型ではなくObject型である。
     * java.util.Enumerationを実装しているためこのメソッドがある。
     *
     * @return boolean まだ項目がのこっているならtrue
     */
    public boolean hasMoreTokens() {
        // "<=" でなく、"<" だと末尾の項目を正しく処理できない。
        return (nextComma(currentPosition) <= maxPosition);
    }

    /****************************************************************************
     * <code>hasMoreTokens</code>メソッドと同じで、まだ項目が残っているかどうか調べる。
     * java.util.Enumerationを実装しているため、このメソッドがある。
     *
     * @return boolean まだ項目がのこっているならtrue
     * @see	java.util.Enumeration
     */
    @Override
    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    /****************************************************************************
     * インスタンスの文字列表現を返す。
     *
     * @return String インスタンスの文字列表現。
     */
    @Override
    public String toString() {
        return "CSVTokenizer(\"" + source + "\")";
    }
}
