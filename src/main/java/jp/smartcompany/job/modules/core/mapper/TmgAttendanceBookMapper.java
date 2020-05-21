package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAttendanceBookDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceBookDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceEndueTimeInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.MastGenericDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * [勤怠]出勤簿情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgAttendanceBookMapper extends BaseMapper<TmgAttendanceBookDO> {

    /**
     * ディフォルト表示時間を取得する
     *
     * @param params
     * @return
     */
    AttendanceDateInfoDTO selectDateInfo(HashMap<String, String> params);


    /**
     * 年次休暇付与日数と付与時間
     *
     * @param params
     * @return
     */
    AttendanceEndueTimeInfoDTO selectEndueTimeInfo(HashMap<String, String> params);


    /**
     * 出勤簿リスト
     *
     * @param params
     * @return
     */
    List<AttendanceBookDTO> selectAttendanceBookList(HashMap<String, Object> params);

    /**
     * 摘要編集
     *
     * @param params
     */
    void updateComment(HashMap<String, String> params);

    /**
     * コメント　検索
     *
     * @param params
     * @return
     */
    HashMap<String, String> selectComment(HashMap<String, String> params);

    /**
     * 出勤簿月単位集計項目 データ部取得クエリ構築
     *
     * @param params
     * @return
     */
    List<MastGenericDetailDTO> selectTotalDataQueryList(HashMap<String, Object> params);


}
