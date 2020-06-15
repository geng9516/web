package jp.smartcompany.framework.dbaccess;

/**
 * @author Xiao Wenpeng
 */
public interface BehaviorApplyLogic {

    /**
     * <B>【マスキング判定処理】</B><BR>
     * データ項目（カラム単位）の表示権限の有無を取得します。
     *
     * @param	String	システムコード
     * @param	String	ドメインコード
     * @param	String	リレーションコード
     * @param	String	カラムコード
     * @return	boolean	カラム単位の表示権限可否
     */
    boolean isMask(String psSystemID, String psDomainID,
                          String psRelationID, String psColumnId) ;

}
