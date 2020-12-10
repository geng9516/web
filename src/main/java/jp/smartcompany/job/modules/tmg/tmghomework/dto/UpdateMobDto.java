package jp.smartcompany.job.modules.tmg.tmghomework.dto;

import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UpdateMobDto {
   private List<HomeWorkAdminVO> homeWorkAdminVO;
}
