/*==============================================================================
 *   @system	Progress@Site
 *   @version	4.0
 *   @since		JDK1.5
 *   @id		AppAuthJudgmentEntity
 *   @title		アプリケーション起動権限判定のEntityクラス
 *   @author	RB CHIBA
 *   @create	2007/06/05
 *				更新日     更新者     更新内容
 *   @update     
 *   @remark     
 =============================================================================*/

package jp.smartcompany.framework.auth.entity;

import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * アプリケーション起動権限判定の検索SQL用Entityクラス<br>.
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class AppAuthJudgmentEntity extends MastApptreeDO {

	/** グループ別アプリケーション権限マスタ.システムID .*/
	private String mgpCsystemid;     //MGP_CSYSTEMID

	/** グループ別アプリケーション権限マスタ.グループコード .*/
	private String mgpCgroupid;    //MGP_CGROUPID

	/** グループ別アプリケーション権限マスタ.実行権限 .*/
	private String mgpCpermission; //MGP_CPERMISSION

	/** グループ別アプリケーション権限マスタ.実行拒否判定 .*/
	private String mgpCreject;     //MGP_CREJECT

	/** アプリケーション設定マスタ.オブジェクト名：言語対応 .*/
	private String mtrObjname;     //MTR_OBJNAME

	/** アプリケーション設定マスタ.サイト説明文：言語対応 .*/
	private String mtrSitecaption; //MTR_SITECAPTION


}
