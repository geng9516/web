package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailCheckDO;
import jp.smartcompany.job.modules.core.mapper.TmgDailyDetailCheckMapper;
import jp.smartcompany.job.modules.core.service.ITmgDailyDetailCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.DetailCheckDto;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * [勤怠]エラーチェック用・日別情報詳細           2007/02/23元テーブルのレイアウト変更に伴い修正 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgDailyDetailCheckServiceImpl extends ServiceImpl<TmgDailyDetailCheckMapper, TmgDailyDetailCheckDO> implements ITmgDailyDetailCheckService {


    /**
     * 日別詳細情報チェック(申請関係)を追加する
     *
     * @param custID     顧客コード
     * @param compCode    法人コード
     * @param targetUser 対象者
     * @param day        基準日
     * @param userCode   ユーザコード
     * @param txtAction  アクション
     * @return int
     */
    @Override
    public int buildSQLForInsertDetailCheckEtc(String custID, String compCode, String targetUser, String day, String userCode, String txtAction) {

        Map<String, Object> map = MapUtil.newHashMap(6);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("day", day);
        map.put("userCode", userCode);
        map.put("txtAction", txtAction);

        return baseMapper.buildSQLForInsertDetailCheckEtc(map);
    }

    /**
     * 日別詳細情報チェック(画面非表示項目)を追加する
     *
     * @param custID     顧客コード
     * @param compCode    法人コード
     * @param targetUser 対象者
     * @param day        基準日
     * @param userCode   ユーザコード
     * @param txtAction  アクション
     * @param siteId     サイトID
     * @return int
     */
    @Override
    public int buildSQLForInsertDetailCheckNotDisp(String custID, String compCode, String targetUser, String day, String userCode, String txtAction, String siteId) {
        Map<String, Object> map = MapUtil.newHashMap(7);
        map.put("custID", custID);
        map.put("compCode", compCode);
        map.put("targetUser", targetUser);
        map.put("day", day);
        map.put("userCode", userCode);
        map.put("txtAction", txtAction);
        map.put("siteId", siteId);
        return baseMapper.buildSQLForInsertDetailCheckNotDisp(map);
    }
    /**
     *日別詳細情報チェックを追加する
     * @param detailCheckDto    DetailCheckDto
     * @return 件数
     */
    @Override
    public int buildSQLForInsertDetailCheck(DetailCheckDto detailCheckDto){
        return baseMapper.buildSQLForInsertDetailCheck(detailCheckDto);
    }



}
