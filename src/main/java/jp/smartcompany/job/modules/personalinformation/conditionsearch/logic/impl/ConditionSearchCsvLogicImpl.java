package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchCsvLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.csv.ConditionSearchCsvDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.csv.ConditionSearchResultDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConditionSearchCsvLogicImpl implements IConditionSearchCsvLogic {

    private final ScCacheUtil cacheUtil;
    private final IConditionSearchLogic conditionSearchLogic;

    @Override
    public List<ConditionSearchCsvDTO> showCsvDownload(ConditionSettingDTO settingDTO) {
      int maxLine = Integer.parseInt(cacheUtil.getSystemProperty(PROP_CSV_MAX_LINE));
      String csvFilePath = cacheUtil.getSystemProperty(PROP_CSV_FILE_PATH);
      // ファイル件数は0スタート
      int nFileCount = 1;
      // 終了フラグ
      boolean isEnd = true;

      List<ConditionSearchCsvDTO> csvDtoList = CollUtil.newArrayList();
      //検索結果件数
      Map<String,Object> map = conditionSearchLogic.search(settingDTO);
      //検索結果件数
      List<List<Object>> searchResult =(List<List<Object>>) map.get("result");
      List<String> titles = (List<String>)map.get("title");
      int nResultCount = searchResult.size();
      // 件数保持用
      int nTotalIndex = nResultCount;
      // ファイル数分だけループを行う。
      while(isEnd){
          ConditionSearchResultDTO wConditionSearchResultDto = new ConditionSearchResultDTO();
          String csvFileName = getCsvFileName(
                          SecurityUtil.getUserId(),
                          settingDTO.getHseCsettingname(),
                          nFileCount,
                          false);
          String csvFileNameForLink = getCsvFileName(
                  SecurityUtil.getUserId(),
                  settingDTO.getHseCsettingname(),
                  nFileCount, true);
          // 開始行取得
          int nFromIndex = 1 + (nFileCount - 1) * maxLine;
          // 初期化
          int nToIndex;
          // 二週目以降は何件目を見ているかもチェックする
          if(nResultCount < maxLine || (nTotalIndex <= maxLine && nFileCount != 1)){
              // 値を格納
              wConditionSearchResultDto.setResult(searchResult.subList(nFromIndex - 1,nResultCount));
              wConditionSearchResultDto.setTitle(titles);
//              wConditionSearchResultDto.setPagerLinkDto(oConditionSearchResultDto.getPagerLinkDto());
              // 取得件数が一ファイル最大件数分に満たない場合終了フラグを立てる。
              nToIndex = nResultCount;
              isEnd = false;
          }else{
              // 値を格納
              wConditionSearchResultDto.setResult(searchResult.subList(nFromIndex - 1,maxLine * nFileCount));
              wConditionSearchResultDto.setTitle(titles);
//              wConditionSearchResultDto.setPagerLinkDto(oConditionSearchResultDto.getPagerLinkDto());
              // 終了行取得
              nToIndex = (nFileCount) * maxLine;
              // 何件目まで見ているかを格納
              nTotalIndex = nTotalIndex - maxLine;
          }
          // CSV出力
          writeCSVFile(
                  wConditionSearchResultDto, getCsvFilePath(csvFilePath),csvFileNameForLink
          );
          // 取得件数が0件の場合は開始を0へ
          if (nResultCount == 0){
              nFromIndex = 0;
          }

          ConditionSearchCsvDTO conditionSearchCSVDto = new ConditionSearchCsvDTO();
          conditionSearchCSVDto.setFileName(csvFileName);
          conditionSearchCSVDto.setFilePath("/upload/"+csvFileNameForLink);
          conditionSearchCSVDto.setFromNum(nFromIndex);
          conditionSearchCSVDto.setToNum(nToIndex);
          csvDtoList.add(conditionSearchCSVDto);

          // ファイル件数インクリメント
          nFileCount++;
      }
      return csvDtoList;
    }

    /**
     * ファイル名称を取得
     */
    private String getCsvFileName(String userId, String psSettingName,int pnNum,boolean useEncryption){
        if(psSettingName == null){
            psSettingName = "";
        }
        StringBuilder sbFileName = new StringBuilder(psSettingName);
        sbFileName.append(WORD_PART);
        sbFileName.append(pnNum);
        if (useEncryption) {
            sbFileName.append("_");
            // ユーザIDをPsV4Util.getCryptPhotoNameで暗号化して付加する
            sbFileName.append(SysUtil.getCryptPhotoName(userId));
        }
        sbFileName.append(WORD_CSV);
        return sbFileName.toString();
    }

    /**
     * CSVファイルパス
     * @param psCSVFilePath CSVファイルパス
     * @return String
     */
    private String getCsvFilePath(String psCSVFilePath) {
        // セパレータの文字列を取得
        StringBuilder sbSeparator = new StringBuilder(System.getProperty("file.separator"));

        // エスケープ文字追加
        String sSeparator = sbSeparator.toString();
        sbSeparator.append("\\");
        String sEscapeSeparator = sbSeparator.toString();

        // CSVパスのセパレータ('/'or'\')置換
        psCSVFilePath = SysUtil.replaceString(
                psCSVFilePath,
                "/",
                sSeparator);
        // CSV出力パスを作成
        return psCSVFilePath;
    }

    /**
     * CSVファイルを出力します。
     *
     * @param poConditionSearchResultDto 検索結果
     * @param psFilePath CSV出力用のディレクトリのパス
     * @param psFileName CSVファイル名称
     * @return String
     */
    public void writeCSVFile(
            ConditionSearchResultDTO poConditionSearchResultDto,
            String psFilePath,
            String psFileName){
        PrintWriter oPrintWriter = null;
        try {
            // ディレクトリの生成
            File oDirectory = new File(psFilePath);
            // ディレクトリの作成
            if(!oDirectory.exists()){
                oDirectory.mkdirs();
            }
            // ファイルの生成
            File oFile = new File(oDirectory,psFileName);
            //ファイルが存在していなければ新しくファイルを作成
            if(!oFile.exists()){
                oFile.createNewFile();
            }
            oPrintWriter =
                    new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            new FileOutputStream(oFile.getAbsolutePath()),
                                            "MS932"
                                    )
                            )
                    );

            // タイトルをフォーマット
            String sTitleRow = toCSVFormatRow((ArrayList)poConditionSearchResultDto.getTitle());
            // 結果のリストを取得
            List<List<Object>> lResultList = poConditionSearchResultDto.getResult();

            // タイトルをファイルに書き出し
            oPrintWriter.println(sTitleRow);

            // レコード数分ループ
            for(int i = 0; i < lResultList.size();i++){
                String sRecordRow = toCSVFormatRow((ArrayList)lResultList.get(i));
                // レコードをファイルに書き出し
                oPrintWriter.println(sRecordRow);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        } finally {
            if (oPrintWriter != null) {
                oPrintWriter.close();
            }
        }
    }

    /**
     *  ArrayListをCSV形式へフォーマット
     */
    private String toCSVFormatRow(ArrayList plList) {
        // 結果格納領域
        StringBuilder sbResult = new StringBuilder();
        // 行追加
        for(int i = 0; i < plList.size(); i++){
            String sColumn = toCSVFormatColumn((String)plList.get(i));
            if(i != 0){
                // カンマ追加
                sbResult.append(",");
            }
            // 行追加
            sbResult.append(sColumn);
        }
        return sbResult.toString();
    }

    /**
     * String文字列をV4のCSV形式へフォーマット
     * @param psString
     */
    private String toCSVFormatColumn(String psString){

        if(psString == null){
            psString = "";
        }

        // "を置換
        psString = SysUtil.replaceString(
                psString,
                "\"",
                "\"\"");

        // 改行(\r\n)を(\r)置換
        psString = SysUtil.replaceString(psString, CR + LF, LF);

        // 結果格納領域
        StringBuilder sbResult = new StringBuilder();
        if (NumberUtil.isNumber(psString)){
            // 加\t防止数字过长csv自动将数字转换为科学计数法表示
          sbResult.append(psString).append("\t");
        } else {
          sbResult.append(psString);
        }
        // ダブルクォーテーションで囲む
        sbResult.insert(0,"\"");
        sbResult.append("\"");

        return sbResult.toString();
    }

}
