package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgNotificationDO;
import jp.smartcompany.job.modules.core.mapper.TmgNotification.TmgNotificationMapper;
import jp.smartcompany.job.modules.core.service.ITmgNotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ErrNtfDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationCheckOverhoursListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.ParamNotificationListDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.NotificationCheckOvertimeVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.NotificationDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.NotificationListVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]申請情報                      2007/01/31項目追加「申請日」「対象曜日：指定なし」 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgNotificationServiceImpl extends ServiceImpl<TmgNotificationMapper, TmgNotificationDO> implements ITmgNotificationService {


        /**
         * 休暇・休業申請を一覧を取得するSQLを返す
         *
         * @param params 　params
         * @return String パターン
         */
        @Override
        public List<NotificationListVo> selectNotificationList(ParamNotificationListDto params){
                List<NotificationListVo> notificationListVoList = baseMapper.selectNotificationList(params);
                return notificationListVoList;
        }
        /**
         * 休業申請を一覧COUNT
         *
         * @param params 　params
         * @return int パターン
         */
        @Override
        public int selectNotificationListCount(ParamNotificationListDto params){
                return baseMapper.selectNotificationListCount(params);
        }

        /**
         * 申請詳細を取得するSQLを返す
         *
         * @param params 　params
         * @return String パターン
         */
        @Override
        public NotificationDetailVo selectNotificationDetail(ParamNotificationListDto params){

                Map<String, Object> map = MapUtil.newHashMap(3);
                map.put("customerId", params.getCustId());
                map.put("companyId", params.getCompId());
                map.put("ntfNo", params.getNtfNo());
                map.put("lang", params.getLang());
                map.put("baseDate", params.getToday());
                map.put("datetimeFormat", "yyyy/MM/dd hh24:mi");
                map.put("dateFormat", "yyyy/MM/dd");
                NotificationDetailVo notificationDetailVo = baseMapper.selectNotificationDetail(map);
                return notificationDetailVo;
        }


        /**
         * シーケンス採番
         * @return String パターン
         */
        @Override
        public String selectNotificationSeq(){
                return  baseMapper.selectNotificationSeq();
        }


        /**
         * 申請区分略称を取得する
         * @return String
         */
        @Override
        public String selectNtfName(String custId,String compId,String ntfNo){
                return baseMapper.selectNtfName(custId,compId,ntfNo);
        }


        /**
         * 遡り期限を取得する
         */
        @Override
        public String selectBackLimit(String custId,String compId,String employeeId){
                return  baseMapper.selectBackLimit(custId,compId,employeeId);
        }


        /**承認後更新のSEQ*/
        @Override
        public int updateNotificationItem(ParamNotificationListDto params){
                return  baseMapper.updateNotificationItem(params);
        }

        @Override
        public List<String> getSelectNtfInfo(String yyyymmdd, String empId, String custID, String compCode){
                return  baseMapper.SelectNtfInfo( yyyymmdd,  empId,  custID, compCode);
        }

        /**
         * 休暇・休業申請を一覧を取得するSQLを返す
         *
         * @param params 　params
         * @return String パターン
         */
        @Override
        public List<NotificationCheckOvertimeVo> selectNotificationCheckList(ParamNotificationCheckOverhoursListDto params){
                List<NotificationCheckOvertimeVo> notificationListVoList = baseMapper.selectNotificationCheck(params);
                return notificationListVoList;
        }

        @Override
        public ErrNtfDto getNtfErrMsg(String ntfNo){
                return baseMapper.getNtfErrMsg(ntfNo);

        }

}
