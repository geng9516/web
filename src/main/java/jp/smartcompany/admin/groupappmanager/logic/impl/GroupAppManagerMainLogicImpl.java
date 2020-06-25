package jp.smartcompany.admin.groupappmanager.logic.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerChangeDateDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerGroupDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionTableDTO;
import jp.smartcompany.admin.groupappmanager.logic.GroupAppManagerMainLogic;
import jp.smartcompany.admin.groupappmanager.vo.GroupAppManagerTableLayout;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import jp.smartcompany.job.modules.core.service.IMastGroupapppermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiao Wenpeng
 */
@Service
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

  private final PsSearchCompanyUtil psSearchCompanyUtil;
  private final IMastGroupService iMastGroupService;
  private final IMastGroupapppermissionService iMastGroupapppermissionService;

  @Override
  public GroupAppManagerTableLayout listPermsTable(
          String systemId, Date date,
          String groupId, String psSite,
          String psApp, String psLanguage,
          String customerId,String companyId,
          Boolean isAll
  ) {
    System.out.println(psSite);
    List<GroupAppManagerPermissionDTO> lPermissionList = iMastGroupapppermissionService.selectPermissionList(systemId,date,CollUtil.newArrayList(groupId),psSite, psApp, psLanguage);
    // 権限一覧が取得できない場合はnullを返却
    if (CollUtil.isEmpty(lPermissionList)){
      return null;
    }
    GroupAppManagerTableLayout layout = new GroupAppManagerTableLayout();
    List<GroupAppManagerGroupDTO> groupList = getGroupList(customerId, systemId, psLanguage,
            date, companyId,isAll);
    if (StrUtil.isNotBlank(groupId)) {
         groupList = groupList.stream().filter(item -> StrUtil.equals(groupId,item.getMgCgroupidPk())).collect(Collectors.toList());
    }
    layout.setTableHeader(groupList);
    // 画面表示のためのイレモノを取得
    List<GroupAppManagerPermissionTableDTO> lTable = this.getEmptyTable(lPermissionList,
            groupList, systemId);
    int nIndex =0;
    for (GroupAppManagerPermissionTableDTO groupAppManagerPermissionTableDTO : lTable) {
      List<GroupAppManagerPermissionDTO> lRecord = groupAppManagerPermissionTableDTO.getList();
      // オブジェクトのグループごとのループ（横方向）
      GroupAppManagerPermissionDTO oldDto = null;
      for (int j = 0; j < lRecord.size(); j++) {
        GroupAppManagerPermissionDTO permission = lRecord.get(j);
        if (nIndex >= lPermissionList.size()) {
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
    for (GroupAppManagerPermissionDTO dto : plPermission) {
      // オブジェクトIDが変わったら
      if (!sObjIdTemp.equals(dto.getMgpCobjectid())) {
        List<GroupAppManagerPermissionDTO> lRecord = CollUtil.newArrayList();
        // 表示グループ一覧でループ
        for (GroupAppManagerGroupDTO groupDTO:groupIds) {
          // １行分の権限データを設定
          GroupAppManagerPermissionDTO permissionDto = new GroupAppManagerPermissionDTO();
          permissionDto.setMgpCcompanyid(null);
          permissionDto.setMgpCsystemid(psSystemId);
          permissionDto.setMgpCgroupid(groupDTO.getMgCgroupidPk());
          permissionDto.setMgpCobjectid(dto.getMgpCobjectid());
          permissionDto.setMgpCsite(dto.getMgpCsite());
          permissionDto.setMgpCapp(dto.getMgpCapp());
          permissionDto.setMgpCsubapp(dto.getMgpCsubapp());
          permissionDto.setMgpCbutton(dto.getMgpCbutton());
          permissionDto.setMgpCscreen(dto.getMgpCscreen());
          permissionDto.setObjectName(dto.getObjectName());
          permissionDto.setPermission("0");
          permissionDto.setType(dto.getType());
          permissionDto.setBgColor(BGCOLOR_EMPTY);
          lRecord.add(permissionDto);
        }
        // １行分のデータを設定
        GroupAppManagerPermissionTableDTO table = new GroupAppManagerPermissionTableDTO();
        table.setList(lRecord);
        // グループ未設定且つアプリケーションが登録された場合の対応
        if (CollUtil.isNotEmpty(lRecord)) {
          table.setObjectName(lRecord.get(0).getObjectName());
          table.setType(lRecord.get(0).getType());
        }
        // サブアプリ配下判定フラグをセット(画面/ボタンのみ)
        if (StrUtil.equals("5",dto.getType()) || StrUtil.equals("6",dto.getType())) {
          if (!dto.getMgpCobjectid().contains("__")) {
            table.setSubApp(true);
          }
        }
        lTable.add(table);
        sObjIdTemp = dto.getMgpCobjectid();
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
  private void getSearchDate(String systemId,  Date changeDate, String groupId) {
    // 改定日取得
    GroupAppManagerChangeDateDTO changeDateDTO = iMastGroupapppermissionService.selectDate(systemId,changeDate,groupId);


  }

}
