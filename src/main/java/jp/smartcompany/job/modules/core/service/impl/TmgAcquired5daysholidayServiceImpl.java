package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgAcquired5daysholidayDO;
import jp.smartcompany.job.modules.core.mapper.TmgAcquired5daysholidayMapper;
import jp.smartcompany.job.modules.core.service.ITmgAcquired5daysholidayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.Acquired5DaysListVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 年5日時季指定取得情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgAcquired5daysholidayServiceImpl extends ServiceImpl<TmgAcquired5daysholidayMapper, TmgAcquired5daysholidayDO> implements ITmgAcquired5daysholidayService {

    /**
     * 一覧/編集画面検索用
     *
     * @param baseDate 基準日
     * @param empsql   　組織内職員取得sql
     * @param userCode 　対象sql
     * @return
     */
    @Override
    public List<Acquired5DaysListVO> buildSQLforList(String baseDate, String empsql, String userCode) {
            Map<String, Object> map = MapUtil.newHashMap(3);
            map.put("basedate", baseDate);
            map.put("empsql", empsql);
            map.put("userCode", userCode);

            return this.baseMapper.buildSQLforList(map);
    }
    /**
     *修正基準日チェック期間再計算
     *
     * @param txtUserCode 対象者
     * @param kijunbi　基準日
     * @param txtYear　基準日修正年
     * @param kijunbiEdit　修正基準日
     * @param userCode　更新者
     * @param programId　更新プログラムID
     * @param custID　顧客コード
     * @param compCode　法人コード
     */
    @Override
    public void buildSQLTmgAcquired5daykikanbi(String txtUserCode, String kijunbi, String txtYear, String kijunbiEdit, String userCode, String programId, String custID, String compCode){
        this.baseMapper.buildSQLTmgAcquired5daykikanbi(txtUserCode, kijunbi, txtYear, kijunbiEdit, userCode, programId, custID, compCode);
    }
}
