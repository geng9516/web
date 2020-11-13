package jp.smartcompany.admin.groupappmanager.logic.impl;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerChangeDateDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionTableDTO;
import jp.smartcompany.admin.groupappmanager.form.GroupAppManagerUpdatePermsForm;
import jp.smartcompany.admin.groupappmanager.form.PermChangeItem;
import jp.smartcompany.admin.groupappmanager.logic.GroupAppManagerMainLogic;
import jp.smartcompany.admin.groupappmanager.vo.GroupAppManagerTableLayout;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SysDateUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupapppermissionDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiao Wenpeng
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupAppManagerMainLogicImpl implements GroupAppManagerMainLogic {

  /** セル背景色[設定なし] */
  private static final String BGCOLOR_EMPTY = "#f1f1ec";
  /** セル背景色[許可] */
  private static final String BGCOLOR_YES = "#ffffff";
  /** セル背景色[禁止] */
  private static final String BGCOLOR_NO = "#aab1ce";
  /** 種別 サイト */
  private static final String TYPE_SITE = "1";

  /** 種別 アプリケーション */
  private static final String TYPE_APP = "3";

  /** 法人選択区分 単一 */
  private static final String COMPANY_SEL_ONE = "one";

  /** 法人選択区分 共通 */
  private static final String COMPANY_SEL_ALL = "all";

  /** ON値 */
  private static final String ON = "1";

  /** OFF値 */
  private static final String OFF = "0";

  /** requestScopeに権限一覧を登録する際の名前 */
  public static final String REQ_SCOPE_NAME = "permissionTableDtoList";

  private final PsSearchCompanyUtil psSearchCompanyUtil;
  private final IMastGroupService iMastGroupService;
  private final IMastGroupapppermissionService iMastGroupapppermissionService;
  private final IMastApptreeService iMastApptreeService;
  private final IMastSystemService iMastSystemService;
  private final IMastCompanyService iMastCompanyService;
  private final TimedCache<String,Object> timedCache;

  @Override
  public GroupAppManagerTableLayout listPermsTable(
          String systemId, Date date,
          String groupId, String psSite,
          String psApp, String psLanguage,
          String customerId,String companyId,
          Boolean isAll
  ) {
    GroupAppManagerTableLayout layout = new GroupAppManagerTableLayout();
    List<GroupAppManagerGroupDTO> groupList = getGroupList(customerId, systemId, psLanguage,
            date, companyId,isAll);
    if (StrUtil.isNotBlank(groupId)) {
         groupList = groupList.stream().filter(item -> StrUtil.equals(groupId,item.getMgCgroupidPk())).collect(Collectors.toList());
    }

    List<String> permGroupIds = groupList.stream().map(GroupAppManagerGroupDTO::getMgCgroupidPk).collect(Collectors.toList());
    if (CollUtil.isEmpty(permGroupIds)){
      return null;
    }

    List<GroupAppManagerPermissionDTO> lPermissionList = iMastGroupapppermissionService.selectPermissionList(systemId,date,permGroupIds,psSite, psApp, psLanguage);
    List<GroupAppManagerGroupDTO> sortGroupIds = CollUtil.newArrayList();

//    for (int i = 0;i<permGroupIds.size();i++) {
//      String sortGroupId = lPermissionList.get(i).getMgpCgroupid();
//      for (GroupAppManagerGroupDTO groupAppManagerGroupDTO : groupList) {
//        if (StrUtil.equals(groupAppManagerGroupDTO.getMgCgroupidPk(),sortGroupId)) {
//          sortGroupIds.add(groupAppManagerGroupDTO);
//          break;
//        }
//      }
//    }

    groupList.forEach(groupItem -> {
      GroupAppManagerGroupDTO groupDTO = new GroupAppManagerGroupDTO();
      groupDTO.setCompanyNick(groupItem.getCompanyNick());
      groupDTO.setMgCgroupidPk(groupItem.getMgCgroupidPk());
      groupDTO.setMgCgroupdescription(groupItem.getMgCgroupdescription());
      groupDTO.setMgNweightage(groupItem.getMgNweightage());
      sortGroupIds.add(groupDTO);
    });

    // 権限一覧が取得できない場合はnullを返却
    if (CollUtil.isEmpty(lPermissionList)){
      return null;
    }
    layout.setTableHeader(sortGroupIds);
    // 画面表示のためのイレモノを取得
    List<GroupAppManagerPermissionTableDTO> lTable = getEmptyTable(lPermissionList, sortGroupIds, systemId);
    int nIndex = 0;
    // オブジェクトでのループ（縦方向）
    for (GroupAppManagerPermissionTableDTO groupAppManagerPermissionTableDTO : lTable) {
      List<GroupAppManagerPermissionDTO> lRecord = groupAppManagerPermissionTableDTO.getList();
      GroupAppManagerPermissionDTO oldDto = null;
      // オブジェクトのグループごとのループ（横方向）
      for (int j = 0; j < lRecord.size(); j++) {
        GroupAppManagerPermissionDTO permission = lRecord.get(j);
        // インデックスがListサイズを超えている場合
        if (!(nIndex < lPermissionList.size())) {
          break;
        }
        GroupAppManagerPermissionDTO dto = lPermissionList.get(nIndex);
        // グループIDが取得できない場合(MAST_GROUPAPPPERMISSIONにデータが無かった場合)
        if (dto.getMgpCgroupid() == null) {
          nIndex++;
          break;
        }
        // 前の処理と同じグループ&オブジェクトの場合(重複した歴があった場合)
        if (oldDto != null) {
          if (oldDto.getMgpCgroupid().equals(dto.getMgpCgroupid())
                  && oldDto.getMgpCobjectid().equals(dto.getMgpCobjectid())
                  && dto.getMgpCgroupid().equals(permission.getMgpCgroupid())
                  && dto.getMgpCobjectid().equals(permission.getMgpCobjectid())) {
            // 権限一覧のインデックスは進め、イレモノのループカウンタは進めない
            nIndex++;
            j--;
            continue;
          }
        }

        // グループIDが違い、オブジェクトIDが同じ場合(取得した権限一覧に含まれないグループの場合)
        if (!permission.getMgpCgroupid().equals(dto.getMgpCgroupid())
                && permission.getMgpCobjectid().equals(dto.getMgpCobjectid())) {
          continue;
        }

        // グループID、オブジェクトIDが同じ場合
        if (permission.getMgpCgroupid().equals(dto.getMgpCgroupid())
                && permission.getMgpCobjectid().equals(dto.getMgpCobjectid())) {

          // 権限値書き換え
          permission.setPermission(dto.getPermission());

          // 権限に対応したセルの背景色を設定
          if ("1".equals(dto.getPermission())) {
            permission.setBgColor(GroupAppManagerMainLogicImpl.BGCOLOR_YES);
          } else if ("2".equals(dto.getPermission())) {
            permission.setBgColor(GroupAppManagerMainLogicImpl.BGCOLOR_NO);
          }

          nIndex++;
        }

        oldDto = dto;
      }
    }
    layout.setTableBody(lTable);
    // 権限一覧をrequestScopeに登録
    timedCache.put(REQ_SCOPE_NAME+"_"+ContextUtil.getSession().getId(),lTable);
    getSearchDate(layout,systemId, date,  groupId);
    return layout;
  }

    /**
     * グループデータ設定処理<br>
     * グループコード、グループ名称、グループ一覧をDtoに設定する
     * @param customerId
     * @param systemId
     * @param language
     * @param searchDate
     * @param companyId
     * @return
     */
  @Override
  public List<GroupAppManagerGroupDTO> getGroupList(String customerId, String systemId, String language,
                           Date searchDate, String companyId,boolean isAll) {
    // 法人検索対象範囲取得
    List<String> searchCompList = psSearchCompanyUtil.getCompList(searchDate);
    List<GroupAppManagerGroupDTO> groupList= iMastGroupService.selectAppManagerGroup(
            customerId, systemId, language,
            searchDate, companyId, searchCompList
    );
    if (isAll) {
      for (GroupAppManagerGroupDTO groupAppManagerGroupDTO : groupList) {
          if (StrUtil.isNotBlank(groupAppManagerGroupDTO.getCompanyNick())){
            groupAppManagerGroupDTO.setMgCgroupdescription(
                    groupAppManagerGroupDTO.getMgCgroupdescription()
                            + "(" +groupAppManagerGroupDTO.getCompanyNick() + ")"
            );
          }
      }
    }
    return groupList;
  }

  /**
   * サイトデータ設定処理<br>
   * サイトID、サイト名称、サイト一覧をDtoに設定する
   *
   * @param psSystemId システムコード
   * @param psLanguage 言語区分
   * @return String
   */
  @Override
  public List<MastApptreeDO> getSiteList(String psSystemId, String psLanguage) {
    // サイト一覧取得
    return iMastApptreeService.selectSiteOrAppListByType(psSystemId,
            psLanguage, TYPE_SITE, null);
  }

  /**
   * アプリケーションデータ設定処理<br>
   * アプリケーションID、アプリケーション名称、アプリケーション一覧をDtoに設定する
   *
   * @param psSystemId システムコード
   * @param psLanguage 言語区分
   * @param psSiteId サイトID
   * @return String
   */
  @Override
  public List<MastApptreeDO> getAppList(String psSystemId, String psLanguage, String psSiteId) {
    // アプリケーション一覧取得
    return iMastApptreeService.selectSiteOrAppListByType(psSystemId, psLanguage, TYPE_APP, psSiteId);
  }

  /**
   * システムデータ設定処理<br>
   * システムコード、システム名称、システム一覧をDtoに設定する
   *
   * @param language 言語区分
   * @return String
   */
  @Override
  public List<MastSystemDO> getSystemList(String language) {
    return iMastSystemService.selectSystemList(language);
  }

  @Override
  public List<MastCompanyDO> getCompanyList(String custId,Date searchDate) {
     List<String> searchCompanyList = psSearchCompanyUtil.getCompList(searchDate);
    // 法人一覧取得
    return iMastCompanyService.selectCompanyList(
            custId, "ja", searchDate, searchCompanyList);
  }

  @Override
  @Transactional(rollbackFor = GlobalException.class)
  public String executeUpdate(GroupAppManagerUpdatePermsForm updatePerm) throws SQLException {
    HttpSession httpSession = ContextUtil.getSession();
    PsSession psSession = (PsSession)httpSession.getAttribute(Constant.PS_SESSION);
    String sessionId = httpSession.getId();
    // 画面表示のためのイレモノを取得
    List<GroupAppManagerPermissionTableDTO> lTable = (List<GroupAppManagerPermissionTableDTO>) timedCache.get(REQ_SCOPE_NAME+"_"+sessionId,false);
    if (CollUtil.isEmpty(lTable)) {
      throw new GlobalException("リストの有効期限が切れています、更新してもう一度お試しください");
    }

    // 今回改定日取得
    Date dStart=updatePerm.getChangeDate();
    DateTime now = DateUtil.date();
    if (dStart==null){
      dStart = SysDateUtil.of(now.year(),now.month()+1,now.dayOfMonth());
    }
    // 今回改定日の前日取得
    Date yesterday = DateUtil.offsetDay(dStart,-1);
    // 登録する終了日を取得
    Date dEnd = SysUtil.transStringToDate(PsConst.MAXDATE);

    // 更改原先table中的权限
    for (int i = 0; i < updatePerm.getPermList().size(); i++) {
      PermChangeItem permChangeItem = updatePerm.getPermList().get(i);
      int rowIndex = permChangeItem.getRowIndex();
      int colIndex = permChangeItem.getColIndex();
      String permission = permChangeItem.getPermission();
      lTable.get(rowIndex).getList().get(colIndex).setPermission(permission);
    }

    for (GroupAppManagerPermissionTableDTO groupAppManagerPermissionTableDTO : lTable) {
      List<GroupAppManagerPermissionDTO> lRecord = groupAppManagerPermissionTableDTO.getList();
      for (GroupAppManagerPermissionDTO record : lRecord) {
        // 画面から入力された権限(○×)に応じて、実行権限と実行許可を設定
        String sPermission = record.getPermission();
        if (StrUtil.equals("1", sPermission)) {
          // ○の場合
          record.setMgpCpermission(ON);
          record.setMgpCreject(OFF);
        } else if ("2".equals(sPermission)) {
          // ×の場合
          record.setMgpCpermission(ON);
          record.setMgpCreject(ON);
        } else {
          // 設定なしの場合
          record.setMgpCpermission(OFF);
          record.setMgpCreject(OFF);
        }
        record.setMgpDstartdate(dStart);
        record.setMgpDenddate(new Timestamp(dEnd.getTime()));
        // 今回改定日以降のレコードを削除
        iMastGroupapppermissionService.deleteAfter(record.getMgpCsystemid(),
                dStart, record.getMgpCgroupid(), record.getMgpCobjectid());
        // 現在有効なレコード取得
        List<MastGroupapppermissionDO> lEffective = iMastGroupapppermissionService.selectValidPermissions(record.getMgpCsystemid(), dStart, record
                .getMgpCgroupid(), record.getMgpCobjectid());
        // 有効なレコードが取得された場合
        if (CollUtil.isNotEmpty(lEffective)) {
          // 削除後、同じデータを終了日付のみ変更して登録する
          for (MastGroupapppermissionDO permissionDO : lEffective) {
            iMastGroupapppermissionService.removeById(permissionDO);
            permissionDO.setMgpDenddate(yesterday);
            iMastGroupapppermissionService.save(permissionDO);
          }
        }
        // 画面から編集されたレコードを登録する
        MastGroupapppermissionDO permissionDO = new MastGroupapppermissionDO();
        BeanUtil.copyProperties(record, permissionDO);
        permissionDO.setMgpDmodifieddate(now);
        permissionDO.setMgpCmodifieruserid(psSession.getLoginEmployee());
        iMastGroupapppermissionService.save(permissionDO);

        // 他システム上の同じObjectIdを持つレコードを削除
//        iMastGroupapppermissionService.deleteOtherSysObj(record.getMgpCsystemid(),
//                record.getMgpCobjectid());
      }

    }

    timedCache.remove(REQ_SCOPE_NAME+"_"+sessionId);
    // 刷新启动权限
    timedCache.remove(Constant.getSessionMenuId(httpSession.getId()));
    httpSession.removeAttribute(Constant.IS_APPROVER);
    return "権限を変更しました";
  }

  /**
   * 表示用のイレモノを生成する
   *
   * @param plPermission 権限一覧
   * @param groupIds グループ一覧
   * @param psSystemId システムコード
   * @return List
   */
  private List<GroupAppManagerPermissionTableDTO> getEmptyTable(
          List<GroupAppManagerPermissionDTO> plPermission,
          List<GroupAppManagerGroupDTO> groupIds, String psSystemId) {
    // 入れ物の準備
    List<GroupAppManagerPermissionTableDTO> lTable = CollUtil.newArrayList();
    String sObjIdTemp = "";

    for (int i = 0; i < plPermission.size(); i++) {

      GroupAppManagerPermissionDTO dto = plPermission.get(i);

      // オブジェクトIDが変わったら
      if (!sObjIdTemp.equals(dto.getMgpCobjectid())) {

        List<GroupAppManagerPermissionDTO> lRecord =CollUtil.newArrayList();
        // 表示グループ一覧でループ
        for (int j = 0; j <groupIds.size(); j++) {

          // １行分の権限データを設定
          GroupAppManagerPermissionDTO permissionDto = new GroupAppManagerPermissionDTO();
          permissionDto.setMgpCcompanyid(null);   // 法人コードはnull固定
          permissionDto.setMgpCsystemid(psSystemId);
          permissionDto.setMgpCgroupid(groupIds.get(j).getMgCgroupidPk());
          permissionDto.setMgpCobjectid(dto.getMgpCobjectid());
          permissionDto.setMgpCsite(dto.getMgpCsite());
          permissionDto.setMgpCapp(dto.getMgpCapp());
          permissionDto.setMgpCsubapp(dto.getMgpCsubapp());
          permissionDto.setMgpCbutton(dto.getMgpCbutton());
          permissionDto.setMgpCscreen(dto.getMgpCscreen());
          permissionDto.setObjectName(dto.getObjectName());
          permissionDto.setPermission("0");
          permissionDto.setType(dto.getType());
          permissionDto.setBgColor(GroupAppManagerMainLogicImpl.BGCOLOR_EMPTY);
          lRecord.add(permissionDto);
        }

        // １行分のデータを設定
        GroupAppManagerPermissionTableDTO table = new GroupAppManagerPermissionTableDTO();
        table.setList(lRecord);
        // グループ未設定且つアプリケーションが登録された場合の対応
        if (lRecord.size() != 0) {
          table.setObjectName(lRecord.get(0).getObjectName());
          table.setType(lRecord.get(0).getType());
        }
        // サブアプリ配下判定フラグをセット(画面/ボタンのみ)
        if ("5".equals(dto.getType()) || "6".equals(dto.getType())) {
          if (!dto.getMgpCobjectid().contains("__")) {
            table.setSubApp(true);
          }
        }
        lTable.add(table);

        sObjIdTemp = plPermission.get(i).getMgpCobjectid();

      }

    }

    return lTable;
  }

  /**
   * 検索日付取得処理
   *
   * @param systemId システムコード
   * @param changeDate 今回改定日
   * @param groupId グループID
   */
  private void getSearchDate(GroupAppManagerTableLayout layout,String systemId,  Date changeDate, String groupId) {
    // 改定日取得
    GroupAppManagerChangeDateDTO changeDateDTO = iMastGroupapppermissionService.selectDate(systemId, changeDate, groupId);
    Date beforeDate = changeDateDTO.getBeforedate();
    Date afterDate = changeDateDTO.getAfterdate();
    Date nowDate = changeDateDTO.getNowdate();
    Date latestDate = changeDateDTO.getLatestdate();
    layout.setBeforeDate(SysUtil.transDateToString(beforeDate));
    layout.setAfterDate(SysUtil.transDateToString(afterDate));
    layout.setChangeDate(SysUtil.transDateToString(changeDate));
    if (changeDateDTO.getNowdate() != null) {
      if (changeDateDTO.getLatestdate()!=null && DateUtil.isSameDay(nowDate,latestDate)) {
        layout.setFutureDate(null);
      } else {
        layout.setFutureDate(SysUtil.transDateToString(nowDate));
      }
    }
    layout.setLatestDate(SysUtil.transDateToString(latestDate));
  }


}
