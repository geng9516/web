package jp.smartcompany.job.modules.tmg.tmghomework;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgHomeWorkDataDO;
import jp.smartcompany.job.modules.core.service.ITmgHomeWorkDataService;
import jp.smartcompany.job.modules.core.service.ITmgHomeWorkService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminListVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkAdminVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkMonthVO;
import jp.smartcompany.job.modules.tmg.tmghomework.vo.HomeWorkVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


/**
 * @author 顧成斌
 * @description 在宅勤務登録
 * @objectSource ps.c01.tmg.TmgHomeWork.TmgHomeWorkBean
 * @date 2020/11/20
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TmgHomeWorkBean {

    public TmgReferList referList = null;
    private final ITmgHomeWorkDataService iTmgHomeWorkDataService;
    private final ITmgHomeWorkService iTmgHomeWorkService;

    public List<HomeWorkVO>  selectHomeWorkData(PsDBBean psDBBean, String baseDate) {
        if (null == baseDate || "".equals(baseDate)) {
            //初期化
            baseDate = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        return iTmgHomeWorkService.selectHomeWork(psDBBean.getTargetUser(),baseDate);
    }

    public List<HomeWorkAdminVO>  selectAdminHomeWorkInfo(PsDBBean psDBBean, String baseDate) throws Exception {
        if (null == baseDate || "".equals(baseDate)) {
            //初期化
            baseDate = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        //汎用参照コンポーネント。
        referList = new TmgReferList(psDBBean, "TmgHomeWork", baseDate, TmgReferList.TREEVIEW_TYPE_LIST,
                true, true, false, false, true
        );
        return iTmgHomeWorkService.selectAdminHomeWork(referList.buildSQLForSelectEmployees(),baseDate);

    }

    public List<HomeWorkAdminListVO> selectAdminHomeWorkList(PsDBBean psDBBean, String baseDate) throws Exception {
        if (null == baseDate || "".equals(baseDate)) {
            //初期化
            baseDate = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        //汎用参照コンポーネント。
        referList = new TmgReferList(psDBBean, "TmgHomeWork", baseDate, TmgReferList.TREEVIEW_TYPE_LIST,
                true, true, false, false, true
        );

        List<HomeWorkAdminListVO> list = CollUtil.newArrayList();
        List<HomeWorkAdminVO> homeWorkAdminVO = iTmgHomeWorkService.selectAdminHomeWork(referList.buildSQLForSelectEmployees(),baseDate);
        for(int i=0; i < homeWorkAdminVO.size();) {
            HomeWorkAdminListVO homeWorkAdminList = new HomeWorkAdminListVO();
            int y;
            homeWorkAdminList.setEmpid(homeWorkAdminVO.get(i).getEMPID());
            homeWorkAdminList.setEmpname(homeWorkAdminVO.get(i).getEmpname());

            homeWorkAdminList.setHwstatusflg1(homeWorkAdminVO.get(i).getHwstatus());
            homeWorkAdminList.setHwstatusflg2(homeWorkAdminVO.get(i+1).getHwstatus());
            homeWorkAdminList.setHwstatusflg3(homeWorkAdminVO.get(i+2).getHwstatus());
            homeWorkAdminList.setHwstatusflg4(homeWorkAdminVO.get(i+3).getHwstatus());
            homeWorkAdminList.setHwstatusflg5(homeWorkAdminVO.get(i+4).getHwstatus());
            homeWorkAdminList.setHwstatusflg6(homeWorkAdminVO.get(i+5).getHwstatus());
            homeWorkAdminList.setHwstatusflg7(homeWorkAdminVO.get(i+6).getHwstatus());
            homeWorkAdminList.setHwstatusflg8(homeWorkAdminVO.get(i+7).getHwstatus());
            homeWorkAdminList.setHwstatusflg9(homeWorkAdminVO.get(i+8).getHwstatus());
            homeWorkAdminList.setHwstatusflg10(homeWorkAdminVO.get(i+9).getHwstatus());
            homeWorkAdminList.setHwstatusflg11(homeWorkAdminVO.get(i+10).getHwstatus());
            homeWorkAdminList.setHwstatusflg12(homeWorkAdminVO.get(i+11).getHwstatus());
            homeWorkAdminList.setHwstatusflg13(homeWorkAdminVO.get(i+12).getHwstatus());
            homeWorkAdminList.setHwstatusflg14(homeWorkAdminVO.get(i+13).getHwstatus());
            homeWorkAdminList.setHwstatusflg15(homeWorkAdminVO.get(i+14).getHwstatus());
            homeWorkAdminList.setHwstatusflg16(homeWorkAdminVO.get(i+15).getHwstatus());
            homeWorkAdminList.setHwstatusflg17(homeWorkAdminVO.get(i+16).getHwstatus());
            homeWorkAdminList.setHwstatusflg18(homeWorkAdminVO.get(i+17).getHwstatus());
            homeWorkAdminList.setHwstatusflg19(homeWorkAdminVO.get(i+18).getHwstatus());
            homeWorkAdminList.setHwstatusflg20(homeWorkAdminVO.get(i+19).getHwstatus());
            homeWorkAdminList.setHwstatusflg21(homeWorkAdminVO.get(i+20).getHwstatus());
            homeWorkAdminList.setHwstatusflg22(homeWorkAdminVO.get(i+21).getHwstatus());
            homeWorkAdminList.setHwstatusflg23(homeWorkAdminVO.get(i+22).getHwstatus());
            homeWorkAdminList.setHwstatusflg24(homeWorkAdminVO.get(i+23).getHwstatus());
            homeWorkAdminList.setHwstatusflg25(homeWorkAdminVO.get(i+24).getHwstatus());
            homeWorkAdminList.setHwstatusflg26(homeWorkAdminVO.get(i+25).getHwstatus());
            homeWorkAdminList.setHwstatusflg27(homeWorkAdminVO.get(i+26).getHwstatus());
            homeWorkAdminList.setHwstatusflg28(homeWorkAdminVO.get(i+27).getHwstatus());

            homeWorkAdminList.setHwhomeworkflg1(homeWorkFlg(homeWorkAdminVO.get(i).getHwhomework(),homeWorkAdminVO.get(i).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg2(homeWorkFlg(homeWorkAdminVO.get(i+1).getHwhomework(),homeWorkAdminVO.get(i+1).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg3(homeWorkFlg(homeWorkAdminVO.get(i+2).getHwhomework(),homeWorkAdminVO.get(i+2).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg4(homeWorkFlg(homeWorkAdminVO.get(i+3).getHwhomework(),homeWorkAdminVO.get(i+3).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg5(homeWorkFlg(homeWorkAdminVO.get(i+4).getHwhomework(),homeWorkAdminVO.get(i+4).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg6(homeWorkFlg(homeWorkAdminVO.get(i+5).getHwhomework(),homeWorkAdminVO.get(i+5).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg7(homeWorkFlg(homeWorkAdminVO.get(i+6).getHwhomework(),homeWorkAdminVO.get(i+6).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg8(homeWorkFlg(homeWorkAdminVO.get(i+7).getHwhomework(),homeWorkAdminVO.get(i+7).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg9(homeWorkFlg(homeWorkAdminVO.get(i+8).getHwhomework(),homeWorkAdminVO.get(i+8).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg10(homeWorkFlg(homeWorkAdminVO.get(i+9).getHwhomework(),homeWorkAdminVO.get(i+9).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg11(homeWorkFlg(homeWorkAdminVO.get(i+10).getHwhomework(),homeWorkAdminVO.get(i+10).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg12(homeWorkFlg(homeWorkAdminVO.get(i+11).getHwhomework(),homeWorkAdminVO.get(i+11).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg13(homeWorkFlg(homeWorkAdminVO.get(i+12).getHwhomework(),homeWorkAdminVO.get(i+12).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg14(homeWorkFlg(homeWorkAdminVO.get(i+13).getHwhomework(),homeWorkAdminVO.get(i+13).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg15(homeWorkFlg(homeWorkAdminVO.get(i+14).getHwhomework(),homeWorkAdminVO.get(i+14).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg16(homeWorkFlg(homeWorkAdminVO.get(i+15).getHwhomework(),homeWorkAdminVO.get(i+15).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg17(homeWorkFlg(homeWorkAdminVO.get(i+16).getHwhomework(),homeWorkAdminVO.get(i+16).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg18(homeWorkFlg(homeWorkAdminVO.get(i+17).getHwhomework(),homeWorkAdminVO.get(i+17).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg19(homeWorkFlg(homeWorkAdminVO.get(i+18).getHwhomework(),homeWorkAdminVO.get(i+18).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg20(homeWorkFlg(homeWorkAdminVO.get(i+19).getHwhomework(),homeWorkAdminVO.get(i+19).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg21(homeWorkFlg(homeWorkAdminVO.get(i+20).getHwhomework(),homeWorkAdminVO.get(i+20).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg22(homeWorkFlg(homeWorkAdminVO.get(i+21).getHwhomework(),homeWorkAdminVO.get(i+21).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg23(homeWorkFlg(homeWorkAdminVO.get(i+22).getHwhomework(),homeWorkAdminVO.get(i+22).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg24(homeWorkFlg(homeWorkAdminVO.get(i+23).getHwhomework(),homeWorkAdminVO.get(i+23).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg25(homeWorkFlg(homeWorkAdminVO.get(i+24).getHwhomework(),homeWorkAdminVO.get(i+24).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg26(homeWorkFlg(homeWorkAdminVO.get(i+25).getHwhomework(),homeWorkAdminVO.get(i+25).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg27(homeWorkFlg(homeWorkAdminVO.get(i+26).getHwhomework(),homeWorkAdminVO.get(i+26).getHwtime()));
            homeWorkAdminList.setHwhomeworkflg28(homeWorkFlg(homeWorkAdminVO.get(i+27).getHwhomework(),homeWorkAdminVO.get(i+27).getHwtime()));
            y=28;
            if (i + 27==homeWorkAdminVO.size()){
                break;
            }
            if(homeWorkAdminVO.get(i + 27).getEMPID().equals(homeWorkAdminVO.get(i + 28).getEMPID())){
                y=29;
                homeWorkAdminList.setHwstatusflg29(homeWorkAdminVO.get(i+28).getHwstatus());
                homeWorkAdminList.setHwhomeworkflg29(homeWorkFlg(homeWorkAdminVO.get(i+28).getHwhomework(),homeWorkAdminVO.get(i+28).getHwtime()));
                if (i + 28==homeWorkAdminVO.size()){
                    break;
                }
            }

            if(homeWorkAdminVO.get(i + 28).getEMPID().equals(homeWorkAdminVO.get(i + 29).getEMPID())){
                y=30;
                homeWorkAdminList.setHwstatusflg30(homeWorkAdminVO.get(i+29).getHwstatus());
                homeWorkAdminList.setHwhomeworkflg30(homeWorkFlg(homeWorkAdminVO.get(i+29).getHwhomework(),homeWorkAdminVO.get(i+29).getHwtime()));
                if (i + 29==homeWorkAdminVO.size()){
                    break;
                }
            }
            if (i + 30==homeWorkAdminVO.size()){
                break;
            }
            if(homeWorkAdminVO.get(i + 29).getEMPID().equals(homeWorkAdminVO.get(i + 30).getEMPID())){
                y=31;
                homeWorkAdminList.setHwstatusflg31(homeWorkAdminVO.get(i+30).getHwstatus());
                homeWorkAdminList.setHwhomeworkflg31(homeWorkFlg(homeWorkAdminVO.get(i+30).getHwhomework(),homeWorkAdminVO.get(i+30).getHwtime()));
            }
            list.add(homeWorkAdminList);
            i+=y;
        }

        return list;

    }

    public List<HomeWorkAdminVO>  selectAdminHomeWorkUpdateList(PsDBBean psDBBean, String baseDate)  throws Exception {
            if (null == baseDate || "".equals(baseDate)) {
                //初期化
                baseDate = DateUtil.format(new Date(), "yyyy/MM/dd");
            }
            //汎用参照コンポーネント。
            referList = new TmgReferList(psDBBean, "TmgHomeWork", baseDate, TmgReferList.TREEVIEW_TYPE_LIST,
                    true, true, false, false, true
            );

        return iTmgHomeWorkService.selectAdminHomeWorkUpdateList(referList.buildSQLForSelectEmployees(),baseDate);

        }

    public List<HomeWorkMonthVO>  selectAdminHomeWorkMonthList(PsDBBean psDBBean, String baseDate)  throws Exception {
        if (null == baseDate || "".equals(baseDate)) {
            //初期化
            baseDate = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        //汎用参照コンポーネント。
        referList = new TmgReferList(psDBBean, "TmgHomeWork", baseDate, TmgReferList.TREEVIEW_TYPE_LIST,
                true, true, false, false, true
        );

        return iTmgHomeWorkService.selectAdminHomeWorkMonthList(referList.buildSQLForSelectEmployees(),baseDate);
    }

    public void updateHmoeWorkData(PsDBBean psDBBean, List<HomeWorkVO> homeWorkVO) {
        for (int i = 0; i < homeWorkVO.size(); i++) {
            TmgHomeWorkDataDO tmgHomeWorkDataDO = new TmgHomeWorkDataDO();
                if( "3".equals(homeWorkVO.get(i).getHwStatus())){

                    tmgHomeWorkDataDO.setHw_status("0");
                    tmgHomeWorkDataDO.setHw_homework("");
                    tmgHomeWorkDataDO.setHw_start("");
                    tmgHomeWorkDataDO.setHw_end("");
                    tmgHomeWorkDataDO.setHw_commute("");
                    tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkVO.get(i).getHwApplicationcomment());

                    QueryWrapper<TmgHomeWorkDataDO> queryWrapper = new QueryWrapper<TmgHomeWorkDataDO>();
                    queryWrapper.eq("hw_ccustomerid", psDBBean.getCustID());
                    queryWrapper.eq("hw_ccompanyid", psDBBean.getCompCode());
                    queryWrapper.eq("hw_cemployeeid", homeWorkVO.get(i).getEmpid());
                    queryWrapper.eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate());

                    iTmgHomeWorkDataService.getBaseMapper().update(tmgHomeWorkDataDO,queryWrapper);

                    continue;
                }else if( "1".equals(homeWorkVO.get(i).getHwStatus()) || "0".equals(homeWorkVO.get(i).getHwStatus())){
                    iTmgHomeWorkDataService.getBaseMapper().delete(SysUtil.<TmgHomeWorkDataDO>query()
                            .eq("hw_ccustomerid", psDBBean.getCustID())
                            .eq("hw_ccompanyid", psDBBean.getCompCode())
                            .eq("hw_cemployeeid", homeWorkVO.get(i).getEmpid())
                            .eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate()));

                    tmgHomeWorkDataDO.setHw_ccustomerid("01");
                    tmgHomeWorkDataDO.setHw_ccompanyid("01");
                    tmgHomeWorkDataDO.setHw_cemployeeid(homeWorkVO.get(i).getEmpid());
                    tmgHomeWorkDataDO.setHw_applicationdate(homeWorkVO.get(i).getHwApplicationdate());
                    tmgHomeWorkDataDO.setHw_status(homeWorkVO.get(i).getHwStatus());
                    tmgHomeWorkDataDO.setHw_homework(homeWorkVO.get(i).getHwHomework());
                    tmgHomeWorkDataDO.setHw_start(homeWorkVO.get(i).getHwStart());
                    tmgHomeWorkDataDO.setHw_end(homeWorkVO.get(i).getHwEnd());
                    tmgHomeWorkDataDO.setHw_commute(homeWorkVO.get(i).getHwCommute());
                    tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkVO.get(i).getHwApplicationcomment());
                    iTmgHomeWorkDataService.getBaseMapper().insert(tmgHomeWorkDataDO);

                    continue;
                }else if(  "2".equals(homeWorkVO.get(i).getHwStatus()) || "4".equals(homeWorkVO.get(i).getHwStatus())){

                    tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkVO.get(i).getHwApplicationcomment());

                    QueryWrapper<TmgHomeWorkDataDO> queryWrapper = new QueryWrapper<TmgHomeWorkDataDO>();
                    queryWrapper.eq("hw_ccustomerid", psDBBean.getCustID());
                    queryWrapper.eq("hw_ccompanyid", psDBBean.getCompCode());
                    queryWrapper.eq("hw_cemployeeid", homeWorkVO.get(i).getEmpid());
                    queryWrapper.eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate());

                    iTmgHomeWorkDataService.getBaseMapper().update(tmgHomeWorkDataDO,queryWrapper);
                    continue;
                }
        }
    }
    public void updateHmoeWorkAdminDataList(PsDBBean psDBBean, List<HomeWorkAdminVO> homeWorkAdminVO) {
        for (int i = 0; i < homeWorkAdminVO.size(); i++) {
            TmgHomeWorkDataDO tmgHomeWorkDataDO = new TmgHomeWorkDataDO();
            if( "0".equals(homeWorkAdminVO.get(i).getHwstatus())){
                iTmgHomeWorkDataService.getBaseMapper().delete(SysUtil.<TmgHomeWorkDataDO>query()
                        .eq("hw_ccustomerid", psDBBean.getCustID())
                        .eq("hw_ccompanyid", psDBBean.getCompCode())
                        .eq("hw_cemployeeid", homeWorkAdminVO.get(i).getEMPID())
                        .eq("hw_applicationdate", homeWorkAdminVO.get(i).getTdaday()));

                tmgHomeWorkDataDO.setHw_ccustomerid(psDBBean.getCustID());
                tmgHomeWorkDataDO.setHw_ccompanyid(psDBBean.getCompCode());
                tmgHomeWorkDataDO.setHw_cemployeeid(homeWorkAdminVO.get(i).getEMPID());
                tmgHomeWorkDataDO.setHw_applicationdate(homeWorkAdminVO.get(i).getTdaday());
                tmgHomeWorkDataDO.setHw_status("0");
                tmgHomeWorkDataDO.setHw_homework("");
                tmgHomeWorkDataDO.setHw_start("");
                tmgHomeWorkDataDO.setHw_end("");
                tmgHomeWorkDataDO.setHw_commute("");
                tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkAdminVO.get(i).getHwApplicationcomment());
                tmgHomeWorkDataDO.setHw_approvalcomment(homeWorkAdminVO.get(i).getHwApprovalcomment());

                iTmgHomeWorkDataService.getBaseMapper().insert(tmgHomeWorkDataDO);

                continue;
            }else if( "2".equals(homeWorkAdminVO.get(i).getHwstatus())){
                iTmgHomeWorkDataService.getBaseMapper().delete(SysUtil.<TmgHomeWorkDataDO>query()
                        .eq("hw_ccustomerid", psDBBean.getCustID())
                        .eq("hw_ccompanyid", psDBBean.getCompCode())
                        .eq("hw_cemployeeid", homeWorkAdminVO.get(i).getEMPID())
                        .eq("hw_applicationdate", homeWorkAdminVO.get(i).getTdaday()));

                tmgHomeWorkDataDO.setHw_ccustomerid(psDBBean.getCustID());
                tmgHomeWorkDataDO.setHw_ccompanyid(psDBBean.getCompCode());
                tmgHomeWorkDataDO.setHw_cemployeeid(homeWorkAdminVO.get(i).getEMPID());
                tmgHomeWorkDataDO.setHw_applicationdate(homeWorkAdminVO.get(i).getTdaday());
                tmgHomeWorkDataDO.setHw_status(homeWorkAdminVO.get(i).getHwstatus());
                tmgHomeWorkDataDO.setHw_homework(homeWorkAdminVO.get(i).getHwhomework());
                tmgHomeWorkDataDO.setHw_start(homeWorkAdminVO.get(i).getHwStart());
                tmgHomeWorkDataDO.setHw_end(homeWorkAdminVO.get(i).getHwEnd());
                tmgHomeWorkDataDO.setHw_commute(homeWorkAdminVO.get(i).getHwCommute());
                tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkAdminVO.get(i).getHwApplicationcomment());
                tmgHomeWorkDataDO.setHw_approvalcomment(homeWorkAdminVO.get(i).getHwApprovalcomment());

                iTmgHomeWorkDataService.getBaseMapper().insert(tmgHomeWorkDataDO);

                continue;
            }else if( "4".equals(homeWorkAdminVO.get(i).getHwstatus())){
                tmgHomeWorkDataDO.setHw_status(homeWorkAdminVO.get(i).getHwstatus());
                if (!"".equals(homeWorkAdminVO.get(i).getHwApprovalcomment()) && homeWorkAdminVO.get(i).getHwApprovalcomment() !=null){
                    tmgHomeWorkDataDO.setHw_approvalcomment(homeWorkAdminVO.get(i).getHwApprovalcomment());
                }
                QueryWrapper<TmgHomeWorkDataDO> queryWrapper = new QueryWrapper<TmgHomeWorkDataDO>();
                queryWrapper.eq("hw_ccustomerid", psDBBean.getCustID());
                queryWrapper.eq("hw_ccompanyid", psDBBean.getCompCode());
                queryWrapper.eq("hw_cemployeeid", homeWorkAdminVO.get(i).getEMPID());
                queryWrapper.eq("hw_applicationdate", homeWorkAdminVO.get(i).getTdaday());

                iTmgHomeWorkDataService.getBaseMapper().update(tmgHomeWorkDataDO,queryWrapper);
                continue;
            }else if( "1".equals(homeWorkAdminVO.get(i).getHwstatus())){
                if (!"".equals(homeWorkAdminVO.get(i).getHwApprovalcomment()) && homeWorkAdminVO.get(i).getHwApprovalcomment() !=null){
                    tmgHomeWorkDataDO.setHw_approvalcomment(homeWorkAdminVO.get(i).getHwApprovalcomment());
                }else {
                    continue;
                }
                QueryWrapper<TmgHomeWorkDataDO> queryWrapper = new QueryWrapper<TmgHomeWorkDataDO>();
                queryWrapper.eq("hw_ccustomerid", psDBBean.getCustID());
                queryWrapper.eq("hw_ccompanyid", psDBBean.getCompCode());
                queryWrapper.eq("hw_cemployeeid", homeWorkAdminVO.get(i).getEMPID());
                queryWrapper.eq("hw_applicationdate", homeWorkAdminVO.get(i).getTdaday());

                iTmgHomeWorkDataService.getBaseMapper().update(tmgHomeWorkDataDO,queryWrapper);
                continue;
            }
        }
    }
    public void updateHmoeWorkAdminData(PsDBBean psDBBean, List<HomeWorkVO> homeWorkVO) {
        for (int i = 0; i < homeWorkVO.size(); i++) {
            TmgHomeWorkDataDO tmgHomeWorkDataDO = new TmgHomeWorkDataDO();
            if( "0".equals(homeWorkVO.get(i).getHwStatus())){
                iTmgHomeWorkDataService.getBaseMapper().delete(SysUtil.<TmgHomeWorkDataDO>query()
                        .eq("hw_ccustomerid", psDBBean.getCustID())
                        .eq("hw_ccompanyid", psDBBean.getCompCode())
                        .eq("hw_cemployeeid", homeWorkVO.get(i).getEmpid())
                        .eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate()));

                tmgHomeWorkDataDO.setHw_ccustomerid("01");
                tmgHomeWorkDataDO.setHw_ccompanyid("01");
                tmgHomeWorkDataDO.setHw_cemployeeid(homeWorkVO.get(i).getEmpid());
                tmgHomeWorkDataDO.setHw_applicationdate(homeWorkVO.get(i).getHwApplicationdate());
                tmgHomeWorkDataDO.setHw_status("0");
                tmgHomeWorkDataDO.setHw_homework("");
                tmgHomeWorkDataDO.setHw_start("");
                tmgHomeWorkDataDO.setHw_end("");
                tmgHomeWorkDataDO.setHw_commute("");
                tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkVO.get(i).getHwApplicationcomment());
                tmgHomeWorkDataDO.setHw_approvalcomment(homeWorkVO.get(i).getHwApprovalcomment());

                iTmgHomeWorkDataService.getBaseMapper().insert(tmgHomeWorkDataDO);

                continue;
            }else if( "2".equals(homeWorkVO.get(i).getHwStatus())){
                iTmgHomeWorkDataService.getBaseMapper().delete(SysUtil.<TmgHomeWorkDataDO>query()
                        .eq("hw_ccustomerid", psDBBean.getCustID())
                        .eq("hw_ccompanyid", psDBBean.getCompCode())
                        .eq("hw_cemployeeid", homeWorkVO.get(i).getEmpid())
                        .eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate()));

                tmgHomeWorkDataDO.setHw_ccustomerid("01");
                tmgHomeWorkDataDO.setHw_ccompanyid("01");
                tmgHomeWorkDataDO.setHw_cemployeeid(homeWorkVO.get(i).getEmpid());
                tmgHomeWorkDataDO.setHw_applicationdate(homeWorkVO.get(i).getHwApplicationdate());
                tmgHomeWorkDataDO.setHw_status(homeWorkVO.get(i).getHwStatus());
                tmgHomeWorkDataDO.setHw_homework(homeWorkVO.get(i).getHwHomework());
                tmgHomeWorkDataDO.setHw_start(homeWorkVO.get(i).getHwStart());
                tmgHomeWorkDataDO.setHw_end(homeWorkVO.get(i).getHwEnd());
                tmgHomeWorkDataDO.setHw_commute(homeWorkVO.get(i).getHwCommute());
                tmgHomeWorkDataDO.setHw_applicationcomment(homeWorkVO.get(i).getHwApplicationcomment());
                tmgHomeWorkDataDO.setHw_approvalcomment(homeWorkVO.get(i).getHwApprovalcomment());

                iTmgHomeWorkDataService.getBaseMapper().insert(tmgHomeWorkDataDO);

                continue;
            }else if( "4".equals(homeWorkVO.get(i).getHwStatus())){
                tmgHomeWorkDataDO.setHw_status(homeWorkVO.get(i).getHwStatus());
                if (!"".equals(homeWorkVO.get(i).getHwApprovalcomment()) && homeWorkVO.get(i).getHwApprovalcomment() !=null){
                    tmgHomeWorkDataDO.setHw_approvalcomment(homeWorkVO.get(i).getHwApprovalcomment());
                }
                QueryWrapper<TmgHomeWorkDataDO> queryWrapper = new QueryWrapper<TmgHomeWorkDataDO>();
                queryWrapper.eq("hw_ccustomerid", psDBBean.getCustID());
                queryWrapper.eq("hw_ccompanyid", psDBBean.getCompCode());
                queryWrapper.eq("hw_cemployeeid", homeWorkVO.get(i).getEmpid());
                queryWrapper.eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate());

                iTmgHomeWorkDataService.getBaseMapper().update(tmgHomeWorkDataDO,queryWrapper);
                continue;
            }else if( "1".equals(homeWorkVO.get(i).getHwStatus())){
                if (!"".equals(homeWorkVO.get(i).getHwApprovalcomment()) && homeWorkVO.get(i).getHwApprovalcomment() !=null){
                    tmgHomeWorkDataDO.setHw_approvalcomment(homeWorkVO.get(i).getHwApprovalcomment());
                }else {
                    continue;
                }
                QueryWrapper<TmgHomeWorkDataDO> queryWrapper = new QueryWrapper<TmgHomeWorkDataDO>();
                queryWrapper.eq("hw_ccustomerid", psDBBean.getCustID());
                queryWrapper.eq("hw_ccompanyid", psDBBean.getCompCode());
                queryWrapper.eq("hw_cemployeeid", homeWorkVO.get(i).getEmpid());
                queryWrapper.eq("hw_applicationdate", homeWorkVO.get(i).getHwApplicationdate());

                iTmgHomeWorkDataService.getBaseMapper().update(tmgHomeWorkDataDO,queryWrapper);
                continue;
            }
        }
    }
    public String homeWorkFlg(String homework, String hwtime) {
        String homeworkFlg = "";
        if("1".equals(homework)){
            homeworkFlg = "終日";
        }else if ("2".equals(homework)){
            homeworkFlg = "午前";
        }else if ("3".equals(homework)){
            homeworkFlg = "午後";
        }else if ("4".equals(homework)){
            homeworkFlg = hwtime;
        }
        return homeworkFlg;
    }
}