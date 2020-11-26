package jp.smartcompany.job.modules.tmg_setting.genericmanager.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.PageQuery;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.service.IMastCompanyService;
import jp.smartcompany.job.modules.core.service.IMastGenericCategoryService;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.service.IMastGenericService;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.dto.GenericHistoryDetailDTO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.mapper.GenericManagerMapper;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.dto.UpdateDetailDTO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.service.IGenericManagerService;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.vo.CategoryGenericDetailItemVO;
import jp.smartcompany.job.modules.tmg_setting.genericmanager.pojo.vo.CategoryGenericDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenericManagerServiceImpl extends ServiceImpl<GenericManagerMapper, MastGenericDO>
implements IGenericManagerService {

    private static final String FLAG_VALUE_1 = "1";
    private final IMastGenericDetailService mastGenericDetailService;
    private final IMastGenericService mastGenericService;
    private final IMastGenericCategoryService mastGenericCategoryService;
    private final IMastCompanyService mastCompanyService;

    @Override
    public List<CategoryGenericDetailVO> listCategoryGenericDetail(String categoryId) {
        return  baseMapper.listCategoryGenericDetail(categoryId);
    }

    @Override
    public Map<String,Object> getGenericDetailList(Map<String,Object> conditions) {
        Date searchDate = (Date)conditions.get("searchDate");
        Date now = DateUtil.date();
        if (searchDate == null) {
            searchDate = now;
        }
        String groupId = (String)conditions.get("groupId");
        if (StrUtil.isBlank(groupId)) {
            throw new GlobalException("groupIdは空にできません");
        }
        String historyType = (String)conditions.get("historyType");
        String standardDate = SysUtil.transDateToString(searchDate);
        IPage<CategoryGenericDetailItemVO> page = new PageQuery<CategoryGenericDetailItemVO>().getPage(conditions);
        IPage<CategoryGenericDetailItemVO> itemListPage = baseMapper.listGenericDetailByGroupId(page,standardDate,groupId);
        PageUtil listPage = new PageUtil(itemListPage);
        Date revisionDate = null;
        Date prevRevisionDate = null;
        Date nextRevisionDate = null;
        Date validRevisionDate;
        List<GenericHistoryDetailDTO> pastList = CollUtil.newArrayList();
        List<GenericHistoryDetailDTO> futureList =CollUtil.newArrayList();
        // 履歴作成区分が履歴作成の場合に改定日、無効明細情報取得
        if (StrUtil.isNotBlank(historyType) && StrUtil.equals(FLAG_VALUE_1,historyType)) {
            // 検索対象日で有効な改定日取得
            revisionDate = baseMapper.selectRevisionDate(groupId,standardDate);
            Date queryDate = searchDate;
            if (revisionDate!=null){
                queryDate = revisionDate;
            }
            String strQueryDate = SysUtil.transDateToString(queryDate);
            prevRevisionDate = baseMapper.selectPrevRevisionDate(groupId,strQueryDate);
            nextRevisionDate = baseMapper.selectNextRevisionDate(groupId,strQueryDate, PsConst.MAXDATE);
            // システム日付で有効な改定日取得
            String strNow = SysUtil.transDateToString(now);
            validRevisionDate =  baseMapper.selectRevisionDate(groupId,strNow);
            if (revisionDate == null && validRevisionDate !=null) {
                revisionDate = SysUtil.transStringToDate("1900/01/01");
            }
            // 無効名称マスタ明細(過去)取得
            pastList = baseMapper.selectPastDetailList(groupId,standardDate);
            // 無効名称マスタ明細(未来)取得
            futureList = baseMapper.selectFutureDetailList(groupId,standardDate);
        }
        return MapUtil.<String,Object>builder()
                .put("listPage",listPage)
                .put("revisionDate",revisionDate)
                .put("prevRevisionDate",prevRevisionDate)
                .put("nextRevisionDate",nextRevisionDate)
                .put("pastList",pastList)
                .put("futureList",futureList)
                .build();
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public String deleteSelectedDetails(List<Long> ids) {
        List<String> groupIds = mastGenericDetailService.listByIds(ids).stream().map(MastGenericDetailDO::getMgdCgenericgroupid).collect(Collectors.toList());
        QueryWrapper<MastGenericDO> qw = SysUtil.query();
        qw.in("mgCgenericgroupidCk",groupIds).eq("mg_cifeditable","0");
        if (count(qw)>0) {
            throw new GlobalException("削除できません");
        }
        mastGenericDetailService.removeByIds(ids);
        return "削除成功";
    }

    @Override
    public Map<String,Object> getGenericDetail(String categoryId,String groupId,Date searchDate,String detailId) {
       Date now = DateUtil.date();
       if (searchDate == null) {
           searchDate = now;
       }
       String categoryName = mastGenericCategoryService.getNameByCateId(categoryId);
       String strSearchDate = SysUtil.transDateToString(searchDate);
       MastGenericDO generic = mastGenericService.getByGroupId(groupId);
       String companyName = mastCompanyService.getCompanyName(strSearchDate);
       // 新規追加処理
       Date maxEndDate = null;
       MastGenericDetailDO detailDO;
       if (StrUtil.isBlank(detailId)) {
           detailDO = getNewDetailDto();
           detailDO.setMgdCgenericgroupid(generic.getMgCgenericgroupidCk());
          if (StrUtil.equals(FLAG_VALUE_1,generic.getMgCifhistorical())) {
               maxEndDate = SysUtil.getMaxDateObject();
           }
       // 更新処理
       } else {
           detailDO = mastGenericDetailService.getGenericDetail(groupId,detailId,strSearchDate);
           if (StrUtil.equals(FLAG_VALUE_1,generic.getMgCifhistorical())) {
               maxEndDate = mastGenericDetailService.getMaxEndDate(groupId,detailId,strSearchDate);
               if (maxEndDate == null) {
                   maxEndDate = SysUtil.getMaxDateObject();
               }
           }
       }
       return MapUtil.<String,Object>builder()
               .put("categoryName",categoryName)
               .put("group",generic)
               .put("companyName",companyName)
               .put("detail",detailDO)
               .put("maxEndDate",maxEndDate)
               .build();
    }

    @Override
    public void execute(UpdateDetailDTO info) {

    }

    /**
     * 新規登録用明細データDTO取得<br/>
     * 新規登録で使用する明細データDTOを生成し、返却します
     * @author Y.Sato
     * @return 明細データDTO
     */
    private MastGenericDetailDO getNewDetailDto() {
        MastGenericDetailDO oDto = new MastGenericDetailDO();
        oDto.setMgdDstartCk(SysUtil.getMinDateObject());
        oDto.setMgdDend(null);
        oDto.setMgdCcompanyidCkFk("01");
        return oDto;
    }

}
