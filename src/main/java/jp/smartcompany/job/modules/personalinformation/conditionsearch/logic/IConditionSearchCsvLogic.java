package jp.smartcompany.job.modules.personalinformation.conditionsearch.logic;

import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.csv.ConditionSearchCsvDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;

import java.util.List;

public interface IConditionSearchCsvLogic {

    String PROP_CSV_MAX_LINE = "JkCSVMaxLine";
    String PROP_CSV_FILE_PATH = "CSVFilePath";
    String WORD_PART = "Part";
    String WORD_CSV = ".csv";
    // 改行コードCR
    String CR = "\r";
    // 改行コードLF
    String LF = "\n";

    List<ConditionSearchCsvDTO> showCsvDownload(ConditionSettingDTO settingDTO);

}
