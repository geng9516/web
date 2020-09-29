package jp.smartcompany.controller;

import jp.smartcompany.boot.annotation.IgnoreResponseSerializable;
import jp.smartcompany.job.modules.tmg.greennutstimepunch.GreenNutsTimePunchBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GreenNutsTimepunchController {
    private static  final  int TRDATA_RECORD_LENGTH=42;
    private static final String CRLF = "\r\n";


    @Autowired
    private GreenNutsTimePunchBean greenNutsTimePunchBean;


    /**
     * ICカード対応タイムレコーダー『Green Nuts』用 端末認証アクション
     * 対応ページ名：McSrvs01
     * @return
     */
    @PostMapping(value="McSrvs01",produces = MediaType.TEXT_PLAIN_VALUE)
    @IgnoreResponseSerializable
    public String greenNutsAuthenticate()  {
        //errCode エラーコード 0 正常 1 固定 00 固定
        String errCode="0100";
        //memo 設置場所メモ
        String memo="hoge";
        String[] args = {errCode, String.valueOf(memo.length()), memo};
        String res=buildRes(args);
        StringBuilder content=new StringBuilder();
        //“Reply-Length: (電文長のバイト数)”を応答電文に出力する必要があります。
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();


    }

    /**
     * ICカード対応タイムレコーダー『Green Nuts』用 完了アクション
     * 対応ページ名：McSrvs02
     * @return
     */
    @PostMapping(value="McSrvs02",produces = MediaType.TEXT_PLAIN_VALUE)
    @IgnoreResponseSerializable
    public String greenNutsFinish()  {
        //errCode エラーコード 0 正常 Z 固定 00 固定
        String errCode="0Z00";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
        //“Reply-Length: (電文長のバイト数)”を応答電文に出力する必要があります。
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();
    }

    /**
     * ICカード対応タイムレコーダー『Green Nuts』用 出退勤データ受信アクション
     * 対応ページ名：McSrvs03
     * @return
     */
    @PostMapping(value="McSrvs03",produces = MediaType.TEXT_PLAIN_VALUE)
    @IgnoreResponseSerializable
    public String greenNutsTimepunch(@RequestParam("TRDATA") String data, @RequestParam("TRCOUNT")int count, @RequestParam("SERIALNO") String no, HttpServletResponse response) throws IOException {
        //失败返回 エラーコード 1 異常 2 固定 02   サーバで検出したエラー詳細コード
        String errCode="1202";
        try{
            //count 出退勤データ件数。
            //data  レコード
            //no 端末に登録されている端末製造番号
            errCode=greenNutsTimePunchBean.timePunch(data,count,no,TRDATA_RECORD_LENGTH);
        }catch (Exception e){
        }
        StringBuilder content=new StringBuilder();

        String[] args = {errCode};
        String res=buildRes(args);
        //“Reply-Length: (電文長のバイト数)”を応答電文に出力する必要があります。
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();


    }


    /**
     * ICカード対応タイムレコーダー『Green Nuts』用 社員マスタアクション
     * 対応ページ名：McSrvs04
     * @return
     */
    @PostMapping(value="McSrvs04",produces = MediaType.TEXT_PLAIN_VALUE)
    @IgnoreResponseSerializable
    public String greenNutsMastemployees()  {
        //errCode エラーコード 1 異常 7 固定 42  サーバに社員マスタファイルがない
        String errCode="1742";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
        //“Reply-Length: (電文長のバイト数)”を応答電文に出力する必要があります。
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();
    }

    /**
     * ICカード対応タイムレコーダー『Green Nuts』用 時刻あわせアクション
     * 対応ページ名：McSrvs05
     * @return
     */
    @PostMapping(value="McSrvs05",produces = MediaType.TEXT_PLAIN_VALUE)
    @IgnoreResponseSerializable
    public String greenNutsSetSystime()  {
        //errCode エラーコード 0 正常  3 固定 42  固定
        String errCode="0300";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
        //“Reply-Length: (電文長のバイト数)”を応答電文に出力する必要があります。
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();
    }


    /**
     * ICカード対応タイムレコーダー『Green Nuts』用 ICカード登録アクション
     * 対応ページ名：McSrvs06
     * @return
     */
    @PostMapping(value="McSrvs06",produces = MediaType.TEXT_PLAIN_VALUE)
    @IgnoreResponseSerializable
    public String greenNutsRegistIcCard()  {
        //errCode エラーコード 1 異常  4 固定 01 サーバで検出したエラー詳細コード
        String errCode="1401";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
        //“Reply-Length: (電文長のバイト数)”を応答電文に出力する必要があります。
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();
    }


    /**
     * ICカード対応タイムレコーダー『Green Nuts』用 PGダウンロードアクション
     * 対応ページ名：McSrvs07
     * @return
     */
    @PostMapping(value="McSrvs07",produces = MediaType.TEXT_PLAIN_VALUE)
    @IgnoreResponseSerializable
    public String greenNutsDownloadPG()  {
        //errCode エラーコード 1 異常  5 固定 42 サーバに PG ファイルがない
        String errCode="1542";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
        //“Reply-Length: (電文長のバイト数)”を応答電文に出力する必要があります。
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();
    }



    /**
     * ICカード対応タイムレコーダー『Green Nuts』用 CRC送信要求アクション
     * 対応ページ名：McSrvs08
     * @return
     */
    @PostMapping(value="McSrvs08",produces = MediaType.TEXT_PLAIN_VALUE)
    @IgnoreResponseSerializable
    public String  greenNutsSendCRC()  {
        //errCode エラーコード 1 異常  6 固定  01 サーバで検出したエラー詳細コード
        String errCode="1601";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
        //“Reply-Length: (電文長のバイト数)”を応答電文に出力する必要があります。
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();
    }







    private String buildRes(String[] args){
        StringBuffer res = new StringBuffer();
        //応答電文のオブジェクトボディには、単純に全ての項目毎に CRLF を付与します。
        for(int i = 0; i < args.length; i++){
            res.append(args[i]+CRLF);
        }
        return res.toString();
    }

}