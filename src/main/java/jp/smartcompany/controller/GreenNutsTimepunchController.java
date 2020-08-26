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
        String errCode="0100";
        String memo="hoge";

        String[] args = {errCode, String.valueOf(memo.length()), memo};
        String res=buildRes(args);
        StringBuilder content=new StringBuilder();
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
        String errCode="0Z00";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
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
        String errCode="1202";
        try{
            errCode=greenNutsTimePunchBean.timePunch(data,count,no,TRDATA_RECORD_LENGTH);
        }catch (Exception e){
            //
        }
        StringBuilder content=new StringBuilder();

        String[] args = {errCode};
        String res=buildRes(args);
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
        String errCode="1742";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
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
        String errCode="0300";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
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
        String errCode="1401";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
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
        String errCode="1542";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
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
        String errCode="1601";
        StringBuilder content=new StringBuilder();
        String[] args = {errCode};
        String res=buildRes(args);
        content.append("Reply-Length: ").append(res.length()).append(CRLF).append(res).append(CRLF);
        return content.toString();
    }







    private String buildRes(String[] args){
        StringBuffer res = new StringBuffer();

        for(int i = 0; i < args.length; i++){
            res.append(args[i]+CRLF);
        }
        return res.toString();
    }

}