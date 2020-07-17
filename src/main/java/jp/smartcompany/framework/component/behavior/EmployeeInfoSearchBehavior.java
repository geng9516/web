/**==============================================================================
 *	system      Progress@Site
 *	version     Ver4.1
 *  since       JDK1.5
 *	id          EmployeeInfoSearchBehavior
 *  title       社員情報検索Behaviorクラス
 *
 *	author      國井
 *	create      2008/03/26
 *				更新日		更新者			更新内容
 *	update      
 *	remark      
 =============================================================================*/

package jp.smartcompany.framework.component.behavior;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 社員情報検索Behaviorクラス
 * 
 * @author 國井
 */
@Getter
@Setter
@ToString
public class EmployeeInfoSearchBehavior implements java.io.Serializable {

	private static final long serialVersionUID = 6159404077548880068L;
	/** 異動歴"HIST_DESIGNATION"社員番号 */
	private boolean hdCemployeeidCk; // HD_CEMPLOYEEID_CK
	
	/** 基本情報"MAST_EMPLOYEES"社員番号 */
	private boolean meCemployeeidCk; // ME_CEMPLOYEEID_CK

	/** FUNCTION 社員氏名 */
	private boolean empName; // EMP_NAME

	/** FUNCTION 組織名称 */
	private boolean sectionName; // SECTION_NAME

	/** FUNCTION 役職名称 */
	private boolean postName; // POST_NAME

	/** FUNCTION 法人名称 */
	private boolean compName; // COMP_NAME
	
	/** FUNCTION 組織略称 */
	private boolean sectionNick;

	/** FUNCTION 役職略称 */
	private boolean postNick;

	/** FUNCTION 法人略称 */
	private boolean companyNick;

	/** 社員基本情報 MAST_EMPLOYEES"カナ名称" */
	private boolean meCkananame; // ME_CKANANAME
	
	/** 社員基本情報 ME_CIFSTILLEMPLOYEDID"在職・退職" */
	private boolean meCifstillemployedid; // ME_CIFSTILLEMPLOYEDID 2009/10/27 K.Monden 追加

}
