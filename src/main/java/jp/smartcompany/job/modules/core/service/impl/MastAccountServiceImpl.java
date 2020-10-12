package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.admin.usermanager.dto.UserManagerDTO;
import jp.smartcompany.admin.usermanager.dto.UserManagerUpdateParamDTO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.mapper.MastAccount.MastAccountMapper;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * アカウントマスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastAccountServiceImpl extends ServiceImpl<MastAccountMapper, MastAccountDO> implements IMastAccountService {

        @Override
        public MastAccountDO getByUsername(String username) {
                Date now = DateUtil.date();
                QueryWrapper<MastAccountDO> qw = SysUtil.query();
                qw.eq("ma_caccount", username)
                  .le("ma_dstart", now)
                  .ge("ma_dend",now);
                List<MastAccountDO> accountList = list(qw);
                if (CollUtil.isEmpty(accountList)){
                   return null;
                }
                return accountList.get(0);
        }

        @Override
        public LoginAccountBO getAccountInfo(String username) {
              List<LoginAccountBO> loginAccountList = baseMapper.selectAccountInfo(username);
              if (CollUtil.isEmpty(loginAccountList)){
                      return null;
              }
              return loginAccountList.get(0);
        }


        /* =====================
                用户管理模块相关sql 开始
           ===================
         */
        @Override
        public List<UserManagerDTO> selectStartCheckAccount(
                String customerId,
                String userid,
                String account) {
           return baseMapper.selectStartCheckAccount(
                   customerId,
                   userid,
                   account);
        }

        @Override
        public List<UserManagerDTO> selectPersonalCheckAccountOld(String customerId, String account) {
           QueryWrapper<MastAccountDO> qw = SysUtil.query();
           qw.eq("MA_CCUSTOMERID",customerId)
             .eq("MA_CACCOUNT",account)
                   .lt("MA_DEND", DateUtil.date());
           return list(qw).stream().map(accountDO -> {
             UserManagerDTO dto = new UserManagerDTO();
             dto.setMaCuserid(accountDO.getMaCuserid());
             dto.setMaCaccount(accountDO.getMaCaccount());
             return dto;
           }).collect(Collectors.toList());
        }

        @Override
        public UserManagerDTO selectPersonalCheckUserid(String userId) {
          QueryWrapper<MastAccountDO> qw = SysUtil.query();
          qw.eq("MA_CUSERID",userId);
          MastAccountDO account = list(qw).get(0);
          UserManagerDTO userManagerDTO = new UserManagerDTO();
          BeanUtil.copyProperties(account,userManagerDTO);
          return userManagerDTO;
        }

      /**
       * 更新情報取得クエリ
       * @param  pwdDate 日付
       * @param sCryptPassword パスワード
       * @param userId ログインユーザID
       * @param sUpdatePassword パスワード（元）
       * @param customerId 顧客コード
       * @param userIds ユーザID（複数配列）
       * @param companyList 法人検索対象範囲
       * @return List
       */
        @Override
        public List<UserManagerUpdateParamDTO> selectPasswordForUpdateInfo(
          String pwdDate,
          String sCryptPassword,
          String userId,
          String sUpdatePassword,
          String customerId,
          List<String> userIds,
          List<String> companyList) {
            return baseMapper.selectPasswordForUpdateInfo(pwdDate, sCryptPassword
                    , userId
                    , sUpdatePassword
                    , customerId
                    , userIds
                    , companyList);
        }

        @Override
        public List<UserManagerDTO> selectPasswordList(
            String custId,
            String language,
            List<String> userIds,
            Integer searchType,
            List<String> companyList
        ) {
            return baseMapper.selectPasswordList(custId,language,userIds,searchType,companyList);
        }
        /* =====================
                用户管理模块相关sql 结束
           ===================
         */
}
