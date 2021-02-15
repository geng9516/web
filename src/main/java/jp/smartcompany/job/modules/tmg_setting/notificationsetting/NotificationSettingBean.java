package jp.smartcompany.job.modules.tmg_setting.notificationsetting;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.TypeChildrenVo;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.dto.MgdDto;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.GroupVo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.NtfDispVo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.vo.RELVo;
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
//      String = "APP_文字2"   TMG_NTFTYPE|3072
//      String = "APP_文字2"   TMG_APPROVAL_LEVEL|1



    //TMG_NTFTYPEGROUP
//    String = "REL_文字1"    平均勤務時間・年休残時間の換算区分
//    String = "REL_文字2"    消化時間の換算区分
//    String = "REL_文字3"    '営業日or暦日区分
//    String = "REL_文字4"    '連続チェック有無（0：無、1：有）
//    String = "REL_数字1"     年度開始月
//    String = "REL_数字2"     '同一とみなす起算日の範囲 （null：無、0：有）
//    String = "REL_数字3"     '消化時間に端数がある場合の換算区分



    //TMG_NTFGROUP
//    String = "REL_数字1"     'ソート順
    //亲区分
    private void insertNTFGROUP(List<MgdDto> ntfGroupDtoList){

        List<MastGenericDetailDO> mgdDoList =new ArrayList<>();
        List<Integer> existList = checkExist(ntfGroupDtoList);
        mgdDoList= setBaseMGD(ntfGroupDtoList,"TMG_NTFGROUP");
        //todo：排序
        mgdDoList.stream().sorted(Comparator.comparing(MastGenericDetailDO::getMgdNsparenum1)).collect(Collectors.toList());

        List<MastGenericDetailDO> finalMgdDoList = mgdDoList;
        Stream.iterate(0, i -> i + 1).limit(mgdDoList.size()).forEach(i -> {
            if(existList.get(i).equals(0)){
                iMastGenericDetailService.getBaseMapper().insert(finalMgdDoList.get(i));
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
    private List<MastGenericDetailDO> setBaseMGD(List<MgdDto> mgdDolist , String groupId){
        List<MastGenericDetailDO> mgdDoList =new ArrayList<>();

        PsSession psSession=(PsSession) httpSession.getAttribute(Constant.PS_SESSION);

        //详细id list获取
        String[] detailId=getDetailId(groupId,mgdDolist.size());
        int i = 0;
        for(MgdDto mgdDto:mgdDolist){
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
    private List<Integer> checkExist(List<MgdDto> mgdDtoList){
        List<Integer> existList =new ArrayList<>();
        for(MgdDto mgdDto :mgdDtoList ){
            existList.add(iMastGenericDetailService.existMgdDesc(mgdDto.getNtfName(),mgdDto.getStartDate(),mgdDto.getEndDate()));
        }
        return existList;
    }



    //显示画面type
    private TypeChildrenVo num2TypeHandle(String typeId){
        if (StrUtil.hasBlank(typeId)){
            return null;
        }
        int num = Integer.valueOf(typeId);
        String viewflg="";
        for(int i=0;i<14;i++){
            viewflg  += (num%2);
            num = num/2;
        }
        TypeChildrenVo tc = new TypeChildrenVo();
        if (viewflg.length()==14){
            byte[] bytes;
            bytes=viewflg.getBytes();
            if (bytes[0]=='1'){ tc.setTransfer(true); }//振替先・元
            if (bytes[1]=='1'){ tc.setTimeZone(true); }//時間帯
            if (bytes[2]=='1'){ tc.setWorkTime(true); }//始業・終業
            if (bytes[3]=='1'){ tc.setSickName(true); }//傷病名
            if (bytes[4]=='1'){ tc.setSickApply(true); }//労災申請有無
            if (bytes[5]=='1'){ tc.setPeriod(true); }//起算日
            if (bytes[6]=='1'){ tc.setAddDate(true); }//加算日数
            if (bytes[7]=='1'){ tc.setLabel(true); }//勤務時間ラベル
            if (bytes[8]=='1'){ tc.setRestTime(true); }//休憩時間
            if (bytes[9]=='1'){ tc.setName(true); }//氏名
            if (bytes[10]=='1'){ tc.setRelation(true); }//続柄
            if (bytes[11]=='1'){ tc.setBirthday(true); }//生年月日
            if (bytes[12]=='1'){ tc.setDaysOfWeek(true); }//曜日
            if (bytes[13]=='1'){ tc.setTargetNumber(true); }//対象者の人数
        }
        return tc;
    }

    //显示画面type转换
    private String type2NumHandle(TypeChildrenVo type){
        double num = 0;
        if(type.isTransfer()){num = num + Math.pow(2, 0);}
        if(type.isTimeZone()){num = num + Math.pow(2, 1);}
        if(type.isWorkTime()){num = num + Math.pow(2, 2);}
        if(type.isSickName()){num = num + Math.pow(2, 3);}
        if(type.isSickApply()){num = num + Math.pow(2, 4);}
        if(type.isPeriod()){num = num + Math.pow(2, 5);}
        if(type.isAddDate()){num = num + Math.pow(2, 6);}
        if(type.isLabel()){num = num + Math.pow(2, 7);}
        if(type.isRestTime()){num = num + Math.pow(2, 8);}
        if(type.isName()){num = num + Math.pow(2, 9);}
        if(type.isRelation()){num = num + Math.pow(2, 10);}
        if(type.isBirthday()){num = num + Math.pow(2, 11);}
        if(type.isDaysOfWeek()){num = num + Math.pow(2, 12);}
        if(type.isTargetNumber()){num = num + Math.pow(2, 13);}
        return  String.valueOf(num);
    }

    //获取排序 重新排序用
    private Long[] getSortList(int num){
        Long[] sortList = new Long[num];
        for(int i = 0 ;i < num ; i++ ){
            sortList[i] = Long.valueOf(i*10);
        }
        return sortList;
    }


    //去空格 全角处理
    private static String w2f(String src) {
        if (StrUtil.hasBlank(src)) {
            return src;
        }
        char SBC_CHAR_START = 65281; // 全角！
        char SBC_CHAR_END = 65374; // 全角～
        char SBC_SPACE = 12288; // 全角空格 12288
        char DBC_SPACE = ' '; // 半角空格
        int CONVERT_STEP = 65248; // 全角半角转换间隔

        StringBuilder buf = new StringBuilder(src.length()+1);
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格
                buf.append(DBC_SPACE);
            } else { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }


    //获取所有主区分
    private List<GroupVo> getNTFGroup(){
        return iMastGenericDetailService.getNTFGroup(TmgUtil.getSysdate());
    }

    //获取主页面所有内容
    private List<NtfDispVo> getNtfDisp(String ntfGroup,String sysdate){
        List<NtfDispVo> ntfDispVoList=iMastGenericDetailService.getNtfTypeDetail( ntfGroup, sysdate);

        List<NtfDispVo> ntfDispVos=new ArrayList<>();

        for (NtfDispVo vo:ntfDispVoList) {
            if(!ntfDispVos.stream().filter(m->m.getNtfTypeId().equals(vo.getNtfTypeId())).findAny().isPresent()){
                //去重
                ntfDispVos.add(vo);
            }else{
                for(int i=0;i<ntfDispVos.size();i++){
                    if(ntfDispVos.get(i).getNtfTypeId().equals(vo.getNtfTypeId())){
                        RELVo relVo=new RELVo();
                        //RELATION


                        ntfDispVos.get(i).getWorkTypeInfo().add(relVo);
                    }
                }
            }

        }






        return  ntfDispVoList;
    }


}
