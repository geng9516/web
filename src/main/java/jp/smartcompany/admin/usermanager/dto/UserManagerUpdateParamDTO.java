package jp.smartcompany.admin.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserManagerUpdateParamDTO {

  /**  */
  private String meCmail;
  /**  */
  private String maCaccount;
  /**  */
  private String originalPassword;

  private Long mapId; //MAP_ID
  private String mapCuserid; //MAP_CUSERID
  private Long mapNhistory; //MAP_NHISTORY
  private String mapCpassword; //MAP_CPASSWORD
  private Date mapDpwddate; //MAP_DPWDDATE
  private String mapCmodifieruserid; //MAP_CMODIFIERUSERID
  private Date mapDmodifieddate; //MAP_DMODIFIEDDATE

}
