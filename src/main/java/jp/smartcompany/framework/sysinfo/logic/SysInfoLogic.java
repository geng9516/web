package jp.smartcompany.framework.sysinfo.logic;

import java.util.Date;
import java.util.List;

public interface SysInfoLogic {

    /**
     * <B>【上位組織取得（組織指定）】</B><BR>
     * 自組織の上位組織を<BR>
     * SQL用のカンマ区切りリストで取得する。
     *
     * @param  custID         検索対象顧客コード
     * @param  compCode       検索対象法人コード
     * @param  targetDept     検索対象組織コード
     * @param  searchDate     データ検索基準日
     * @return List < String >     上位組織リスト
     */
    List<String> getUpperSectionListDeptForSQL(String custID, String compCode, String targetDept, Date searchDate);

    /**
     * <B>【下位組織リスト取得（組織指定）】</B><BR>
     * 自組織の下位組織を<BR>
     * SQL用のカンマ区切りリストで取得する。
     *
     * @param  custID         検索対象顧客コード
     * @param  compCode       検索対象法人コード
     * @param  targetDept     検索対象組織コード
     * @param  searchDate     データ検索基準日
     * @return List < String >  下位組織リスト
     */
    List<String> getLowerSectionListDeptForSQL(String custID, String compCode, String targetDept, Date searchDate);

}
