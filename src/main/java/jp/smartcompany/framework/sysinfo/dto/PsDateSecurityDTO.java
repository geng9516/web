package jp.smartcompany.framework.sysinfo.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PsDateSecurityDTO implements Serializable {

  private static final long serialVersionUID = 6910170574918009725L;

  /** オブジェクトID */
  private String mtrCobjectid; // MTR_COBJECTID
  /** サイトID */
  private String mtrCsiteid; // MTR_CSITEID
  /** アプリケーションID */
  private String mtrCappid; // MTR_CAPPID
  /** 相対過去対象 */
  private String mdsStart; // MDS_NASPECT_START
  /** 相対未来対象 */
  private String mdsEnd;	 // MDS_NASPECT_END
  /** 指定した日付が参照可能かどうか */
  private boolean mdsIsReferableAt = false;

}
