package jp.smartcompany.job.modules.tmg.patternsetting.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author 陳毅力
 * @description
 * @objectSource
 * @date 2020/06/18
 **/
public class BufferedCSVReader extends BufferedReader {

    private final int nMaxLength = 4096;

    public BufferedCSVReader(Reader preaderIn) {
        super(preaderIn);
    }

    public BufferedCSVReader(Reader preaderIn, int pnSize) {
        super(preaderIn, pnSize);
    }

    @Override
    public String readLine() throws IOException {
        String sResult = "";
        String sLine = "";
        char[] sLine2 = new char['?'];
        for (; ; ) {
            mark(4096);

            sLine = super.readLine();
            if (sLine == null) {
                return null;
            }
            reset();

            read(sLine2, 0, sLine.length() + 1);
            if (sLine2[sLine.length()] != '\n') {
                break;
            }
            sResult = sResult + String.valueOf(sLine2, 0, sLine.length() + 1);
        }
        read();

        return sResult += sLine;
    }
}
