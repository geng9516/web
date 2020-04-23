package jp.smartcompany.job.modules.base.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.base.BaseBean;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;

import jp.smartcompany.job.modules.core.pojo.entity.TmgGroupMemberDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPluggableDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPluggableRootDO;
import jp.smartcompany.job.modules.core.service.IMastEmployeesService;
import jp.smartcompany.job.modules.core.service.ITmgPluggableRootService;
import jp.smartcompany.job.modules.core.service.ITmgPluggableService;
import jp.smartcompany.job.modules.tmg.util.TmgPKG;
import jp.smartcompany.job.modules.tmg.util.Util;
import jp.smartcompany.job.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static jp.smartcompany.job.modules.tmg.util.TmgPKG.CS_WORKERTYPE_00;

/**
 * @author XiaoWenpeng
 */
@Service(BaseBean.Service.PLUGGABLE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PluggableService {

    /**
     * ITmgPluggableRootService
     */
    private final ITmgPluggableRootService iTmgPluggableRootService;
    /**
     * Util
     */
    private final Util util;
    /**
     * 対象者の身分
     */
    private final static String PLUGGABLE_TYPE_00 = "対象者の身分";

    /**
     * コード直接指定
     */
    private final static String PLUGGABLE_TYPE_01 = "コード直接指定";

    /**
     * 身分00以外共通
     */
    private final static String PLUGGABLE_TYPE_02 = "身分00以外共通";

    /**
     * ITmgPluggableService
     */
    private final ITmgPluggableService iTmgPluggableService;

    /**
     * メソッド実行
     */
    @Transactional(rollbackFor = GlobalException.class)
    public Object execute(PluggableDTO pluggableDTO) {

        // 勤怠種別
        String workType = this.getWorkType(pluggableDTO);
        if (StrUtil.hasEmpty(workType)) {
            return null;
        }
        // プラガブル取得処理
        List<TmgPluggableDO> pluggableBOList = iTmgPluggableService.listTmgPluggableDO(pluggableDTO.getCustomerId()
                , pluggableDTO.getCompanyId(), pluggableDTO.getYyyymmdd(), workType, pluggableDTO.getCphase());

        try {
            for (TmgPluggableDO pluggableBO : pluggableBOList) {

                // 执行勤务service里的方法
                BaseExecute service = SpringUtil.getBean(pluggableBO.getTplCsparechar04());
                Object object = service.execute(pluggableDTO);
                if (object != null) {
                    return object;
                }
            }
        } catch (GlobalException e) {
            // error
            return new GlobalException("error");
        }

        return null;
    }

    /**
     * メソッド実行
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void execute2(PluggableDTO pluggableDTO) {

        // 勤怠種別
        String workType = this.getWorkType(pluggableDTO);
        if (StrUtil.hasEmpty(workType)) {
            return;
        }
        // プラガブル取得処理
        List<TmgPluggableDO> pluggableBOList = iTmgPluggableService.listTmgPluggableDO(pluggableDTO.getCustomerId()
                , pluggableDTO.getCompanyId(), pluggableDTO.getYyyymmdd(), "*", pluggableDTO.getCphase());

        try {
            for (TmgPluggableDO pluggableBO : pluggableBOList) {

                // 执行勤务service里的方法
                BaseExecute service = SpringUtil.getBean(pluggableBO.getTplCsparechar04());
                service.execute(pluggableDTO);
            }
        } catch (GlobalException e) {
            // error
            throw new GlobalException("error");
        }
    }

    /**
     * 勤怠種別取得する
     * @param pluggableDTO 　PluggableDTO
     * @return 勤怠種別
     */
    private String getWorkType(PluggableDTO pluggableDTO) {

        String sWorkType = "";
        String pluggableType = PLUGGABLE_TYPE_00;

        List<TmgPluggableRootDO> workTypeList = iTmgPluggableRootService.getBaseMapper().selectList(
                SysUtil.<TmgPluggableRootDO>query()
                        .eq("tplr_ccustomerid", pluggableDTO.getCustomerId())
                        .eq("tplr_ccompanyid", pluggableDTO.getCompanyId())
                        .eq("tplr_cphase", pluggableDTO.getCphase())
                        .le("tplr_dstartdate", pluggableDTO.getYyyymmdd())
                        .ge("tplr_denddate", pluggableDTO.getYyyymmdd()
                        ));

        if (workTypeList.size() > 0) {
            pluggableType = workTypeList.get(0).getTplrCpluggabletype();
        }

        // コード直接指定
        if (PLUGGABLE_TYPE_01.equals(pluggableType)) {
            sWorkType = pluggableDTO.getWorkType();

            // 対象者の身分
        } else if (PLUGGABLE_TYPE_00.equals(pluggableType) && !StrUtil.hasEmpty(pluggableDTO.getEmployeeId())) {

            // 勤怠種別を取得
            sWorkType = util.tmgGetWorkerType(pluggableDTO.getCustomerId(), pluggableDTO.getCompanyId(), pluggableDTO.getEmployeeId(), pluggableDTO.getYyyymmdd());

            // 身分00以外共通
        } else if (PLUGGABLE_TYPE_02.equals(pluggableType) && !StrUtil.hasEmpty(pluggableDTO.getEmployeeId())) {
            // 勤怠種別を取得
            if (!TmgPKG.CS_WORKERTYPE_00.equals(util.tmgGetWorkerType(pluggableDTO.getCustomerId(), pluggableDTO.getCompanyId(), pluggableDTO.getEmployeeId(), pluggableDTO.getYyyymmdd()))) {
                sWorkType = "*";
            }
        } else {
            sWorkType = "*";
        }

        return sWorkType;
    }
}
