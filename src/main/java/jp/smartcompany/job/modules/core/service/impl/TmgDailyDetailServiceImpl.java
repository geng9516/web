package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailDO;
import jp.smartcompany.job.modules.core.mapper.TmgDailyDetailMapper;
import jp.smartcompany.job.modules.core.service.ITmgDailyDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.ResultRest40tVo;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyDetailVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]日別情報詳細                    2007/02/23 予定実績を再統合。また、申請番号のカラ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgDailyDetailServiceImpl extends ServiceImpl<TmgDailyDetailMapper, TmgDailyDetailDO> implements ITmgDailyDetailService {


    /**
     * 日別詳細情報を取得する
     *
     * @param custID     顧客コード
     * @param compCode   法人コード
     * @param targetUser 対象者
     * @param day        対象日
     * @param language   言語
     * @param iMode      　Mode
     * @param bDel       　削除フラグ
     * @return List<DailyDetailVO>
     */
    @Override
    public List<DailyDetailVO> buildSQLForSelectDetail(String custID, String compCode, String targetUser, String day, String language, int iMode, boolean bDel) {
        Map<String, Object> map = MapUtil.newHashMap(7);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("day", day);
        map.put("language", language);
        map.put("iMode", iMode);
        map.put("bDel", bDel);

        return baseMapper.buildSQLForSelectDetail(map);
    }



    /**日別詳細情報より勤務予定時間外の休憩開始・終了時間を取得*/
    /**日別詳細情報より勤務予定時間外の休憩開始・終了時間を取得*/
    @Override
    public  List<ResultRest40tVo> selectResultRest40t(String custId, String compId, String baseDate, String sql){
        return baseMapper.selectResultRest40t( custId,  compId,  baseDate ,sql);
    }
    /**
     * 一括承認データを更新する
     *
     * @param custID        顧客コード
     * @param compCode      法人コード
     * @param loginUserCode 更新者
     * @param programId     　更新プログラムID
     * @param yyyymmdd      　対象日
     * @param empIdList     　更新職員リスト
     * @param notWorkId     　超過勤務
     * @return 更新件数
     */
    @Override
    public int buildSQLForUpdateTmgDailyDetail(String custID, String compCode, String loginUserCode, String programId, String yyyymmdd, List<String> empIdList, String notWorkId) {
        Map<String, Object> map = MapUtil.newHashMap(7);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("loginUserCode", loginUserCode);
        map.put("programId", programId);
        map.put("yyyymmdd", yyyymmdd);
        map.put("empIdList", empIdList);
        map.put("notWorkId", notWorkId);

        return baseMapper.buildSQLForUpdateTmgDailyDetail(map);

    }

}
