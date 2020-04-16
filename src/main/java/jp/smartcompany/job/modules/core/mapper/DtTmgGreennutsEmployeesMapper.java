package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.DtTmgGreennutsEmployeesDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [勤怠]greennuts用：icカード→職員番号マッピング i/f用ワークテーブル                             : Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface DtTmgGreennutsEmployeesMapper extends BaseMapper<DtTmgGreennutsEmployeesDO> {

        }
