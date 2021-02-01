package jp.smartcompany.job.modules.tmg_setting.notificationsetting;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.mgdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


/**
 * 休暇マスター設定
 *
 *
 * @author Wang Ziyue
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationSettingBean {


    private final IMastGenericDetailService iMastGenericDetailService;

    private final HttpSession httpSession;


    //TMG_NTFTYPE
//    String = "TYPE_文字2"   '更新区分
//    String = "TYPE_文字3"   '申請事由必須入力
//    String = "TYPE_文字5"    '申請可能サイト
//    String = "TYPE_数字1"    '取得最小単位
//    String = "TYPE_数字2"     '表示項目タイプ
//    String = "TYPE_数字3"    '部分休業合算上限
//    String = "TYPE_数字4"    '部分休業サブタイプ
//    String = "TYPE_数字5"    '申請反映実行順


    //TMG_NTFTYPE2
//    String = "TYPE2_文字1"    '親区分
//    String = "TYPE2_文字2"    '申請区分
//    String = "TYPE2_文字3"    '注釈文言
//    String = "TYPE2_文字4"    'グルーピングコード
//    String = "TYPE2_数字1"   '表示順
//    String = "TYPE2_数字2"   '添付ファイル必須有無
//    String = "TYPE2_数字3"  '申請期間タイプ
//    String = "TYPE2_数字4"  'IMARTスケジュール連携対象有


    //TMG_NTF_RELATION
//    String = "REL_文字2"
//    String = "REL_文字3"    '週の所定労働日数(日(以下)
//    String = "REL_文字4"    '継続勤務期間(月未満)
//    String = "REL_文字5"    '労働契約期間(月未満)
//    String = "REL_数字1"     '使用有無
//    String = "REL_数字3"     '取得上限値
//    String = "REL_数字4"     '取得可能回数
//    String = "REL_数字5"     '取得可能期


    //TMG_APPROVAL_LEVEL
//      String = "REL_文字2"
//      String = "APP_文字2"



    //TMG_NTFTYPEGROUP
//    String = "REL_文字1"    平均勤務時間・年休残時間の換算区分
//    String = "REL_文字2"    消化時間の換算区分
//    String = "REL_文字3"    '営業日or暦日区分
//    String = "REL_文字4"    '連続チェック有無（0：無、1：有）
//    String = "REL_文字5"     年度開始月
//    String = "REL_数字1"     '同一とみなす起算日の範囲 （null：無、0：有）
//    String = "REL_数字3"     '消化時間に端数がある場合の換算区分



    //TMG_NTFGROUP
//    String = "REL_数字1"     'ソート順
    //亲区分
    private void insertNTFGROUP(List<mgdDto> ntfGroupDtoList){

        List<MastGenericDetailDO> mgdDoList =new ArrayList<>();
        List<Integer> existList = checkExist(ntfGroupDtoList);
        mgdDoList= setBaseMGD(ntfGroupDtoList,"TMG_NTFGROUP");
        //todo：排序
        mgdDoList.stream().sorted(Comparator.comparing(MastGenericDetailDO::getMgdNsparenum1)).collect(Collectors.toList());

        int index = 0;
        mgdDoList.stream().forEach(e->{
            if(existList.get(index).equals(0)){
                iMastGenericDetailService.getBaseMapper().insert(e);
            }else{
                //todo重复
            }
        });
    }

    //TMG_NTFHOLRESTTYPE
//    String = "REL_文字1"    代表申請区分
//    String = "REL_文字2"    申請種類グルーピングコード
//    String = "REL_文字3"    ファンクションID

//    String = "REL_数字1"     'ソート順

    //TMG_NTFCHECK
//    String = "REL_文字1"    プログラム名
//    String = "REL_文字2"    申請種類 ntftype
//    String = "REL_文字3"    '申請種類グルーピングコード todo
//    String = "REL_数字1"     '取得上限値
//    String = "REL_数字2"     '取得単位
//    String = "REL_数字3"     '予定勤務時間下限値 todo








    /**共通MGD 内容设置
     * @param mgdDolist
     * @param groupId group类别
     * @return
     */
    private List<MastGenericDetailDO> setBaseMGD(List<mgdDto> mgdDolist , String groupId){
        List<MastGenericDetailDO> mgdDoList =new ArrayList<>();

        PsSession psSession=(PsSession) httpSession.getAttribute(Constant.PS_SESSION);

        //详细id list获取
        String[] detailId=getDetailId(groupId,mgdDolist.size());
        int i = 0;
        for(mgdDto mgdDto:mgdDolist){
            Date start;
            Date end;
            //默认时间设置
            if(StrUtil.hasBlank(mgdDto.getStartDate())){
                start = TmgUtil.minDate;
            }else{
                start=DateUtil.parseDateTime(mgdDto.getStartDate());
            }
            if(StrUtil.hasBlank(mgdDto.getEndDate())){
                end=TmgUtil.maxDate;
            }else{
                end=DateUtil.parseDateTime(mgdDto.getEndDate());
            }

            MastGenericDetailDO mgdDo=new MastGenericDetailDO();
            mgdDo.setMgdCcustomerid(TmgUtil.Cs_CUSTID);
            mgdDo.setMgdCcompanyidCkFk(TmgUtil.Cs_COMPID);
            mgdDo.setMgdId(Long.parseLong(iMastGenericDetailService.getMgdSeq()));

            //登陆者信息与有效期
            mgdDo.setMgdClanguageCk(psSession.getLanguage());
            mgdDo.setMgdDstartCk(start);
            mgdDo.setMgdDend(end);
            mgdDo.setMgdCmodifieruserid(psSession.getLoginAccount());
            mgdDo.setMgdDmodifieddate(DateTime.now());

            //详细id设置
            mgdDo.setMgdCgenericgroupid(groupId);
            mgdDo.setMgdCmastercode(groupId+ "|"+detailId[i]);
            mgdDo.setMgdCgenericdetailidCk(detailId[i]);


            //文言设置
            mgdDo.setMgdCgenericdetaildesc(mgdDto.getNtfName());
            mgdDo.setMgdCgenericdetaildescja(mgdDto.getNtfName());

            //附属字段设置
            mgdDo.setMgdCsparechar1(mgdDto.getChart1());
            mgdDo.setMgdCsparechar2(mgdDto.getChart2());
            mgdDo.setMgdCsparechar3(mgdDto.getChart3());
            mgdDo.setMgdCsparechar4(mgdDto.getChart4());
            mgdDo.setMgdCsparechar5(mgdDto.getChart5());

            mgdDo.setMgdNsparenum1(mgdDto.getNum1());
            mgdDo.setMgdNsparenum2(mgdDto.getNum2());
            mgdDo.setMgdNsparenum3(mgdDto.getNum3());
            mgdDo.setMgdNsparenum4(mgdDto.getNum4());
            mgdDo.setMgdNsparenum5(mgdDto.getNum5());

            i++;
            mgdDoList.add(mgdDo);
        }
        return mgdDoList;
    }


    //获取最新的详细Id list
    private String[] getDetailId(String groupId,int num){
        String detailId;
        do{
            detailId= iMastGenericDetailService.getMgdDetailId(groupId);

        }while (iMastGenericDetailService.existMgdMastCode(groupId+"|"+detailId)>1);

        String[] detailIdList = new String[num];
        for(int i = 0 ;i < num ; i++ ){
            detailIdList[i] = detailId + i*10;
        }
        return detailIdList;
    }


    //检查有效期与名字重复
    private List<Integer> checkExist(List<mgdDto> mgdDtoList){
        List<Integer> existList =new ArrayList<>();
        for(mgdDto mgdDto :mgdDtoList ){
            existList.add(iMastGenericDetailService.existMgdDesc(mgdDto.getNtfName(),mgdDto.getStartDate(),mgdDto.getEndDate()));
        }
        return existList;
    }


}
