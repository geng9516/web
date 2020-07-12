package jp.smartcompany.admin.groupappmanager.logic.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
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
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.dbaccess.DbControllerLogic;
import jp.smartcompany.framework.util.PsSearchCompanyUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastCompanyDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupapppermissionDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
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
  private static final String REQ_SCOPE_NAME = "permissionTableDtoList";

  private final PsSearchCompanyUtil psSearchCompanyUtil;
  private final IMastGroupService iMastGroupService;
  private final IMastGroupapppermissionService iMastGroupapppermissionService;
  private final IMastApptreeService iMastApptreeService;
  private final IMastSystemService iMastSystemService;
  private final IMastCompanyService iMastCompanyService;
  private final DbControllerLogic dbControllerLogic;
  private final DataSource dataSource;

  @Override
  public GroupAppManagerTableLayout listPermsTable(
          String systemId, Date date,
          String groupId, String psSite,
          String psApp, String psLanguage,
          String customerId,String companyId,
          Boolean isAll,
          HttpSession httpSession
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
    for (int i = 0;i<permGroupIds.size();i++) {
      String sortGroupId = lPermissionList.get(i).getMgpCgroupid();
      for (GroupAppManagerGroupDTO groupAppManagerGroupDTO : groupList) {
        if (StrUtil.equals(groupAppManagerGroupDTO.getMgCgroupidPk(),sortGroupId)) {
          sortGroupIds.add(groupAppManagerGroupDTO);
          break;
        }
      }
    }

    // 権限一覧が取得できない場合はnullを返却
    if (CollUtil.isEmpty(lPermissionList)){
      return null;
    }
    layout.setTableHeader(sortGroupIds);
    // 画面表示のためのイレモノを取得
    List<GroupAppManagerPermissionTableDTO> lTable = getEmptyTable(lPermissionList, sortGroupIds, systemId);

    int nIndex = 0;
    // オブジェクトでのループ（縦方向）
    for (int i = 0; i < lTable.size(); i++) {
      List <GroupAppManagerPermissionDTO> lRecord = lTable.get(i).getList();
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
    httpSession.setAttribute(REQ_SCOPE_NAME,lTable);
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
  public String executeUpdate(HttpSession session, GroupAppManagerUpdatePermsForm updatePerm) throws ParseException, SQLException {
    PsSession psSession = (PsSession)ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
    // 画面表示のためのイレモノを取得
    List<GroupAppManagerPermissionTableDTO> lTable = (List<GroupAppManagerPermissionTableDTO>)session.getAttribute(REQ_SCOPE_NAME);
    if (CollUtil.isEmpty(lTable)) {
      throw new GlobalException("権限データが存在しない");
    }

    // 今回改定日取得
    Date dStart=updatePerm.getChangeDate();
    if (dStart==null){
      dStart =DateUtil.date();
    }
    // 今回改定日の前日取得
    Date yesterday = DateUtil.offsetDay(dStart,-1);
    // 登録する終了日を取得
    Date dEnd = SysUtil.transStringToDate("2222/12/31");

    // 更改原先table中的权限
    for (int i = 0; i < updatePerm.getPermList().size(); i++) {
      PermChangeItem permChangeItem = updatePerm.getPermList().get(i);
      int rowIndex = permChangeItem.getRowIndex();
      int colIndex = permChangeItem.getColIndex();
      String permission = permChangeItem.getPermission();
      lTable.get(rowIndex).getList().get(colIndex).setPermission(permission);
    }


    List<GroupAppManagerPermissionDTO> deleteAfterList = CollUtil.newArrayList();

    List<Long> deleteEffectiveIds = CollUtil.newArrayList();
    List<MastGroupapppermissionDO> insertEffectivePermList = CollUtil.newArrayList();

    List<MastGroupapppermissionDO> prepareInsertList = CollUtil.newArrayList();

    List<GroupAppManagerPermissionDTO> deleteOtherSysList = CollUtil.newArrayList();

    Connection conn =null;
    String strStart =SysUtil.transDateToString(dStart);
    try {
      conn = dataSource.getConnection();
      conn.setAutoCommit(false);

      for (GroupAppManagerPermissionTableDTO rowList : lTable) {
        List<GroupAppManagerPermissionDTO> colList = rowList.getList();
        for (GroupAppManagerPermissionDTO record : colList) {
          // 画面から入力された権限(○×)に応じて、実行権限と実行許可を設定
          String perm = record.getPermission();
          if (StrUtil.equals("1", perm)) {
            // ○の場合
            record.setMgpCpermission(ON);
            record.setMgpCreject(OFF);
          } else if (StrUtil.equals("2", perm)) {
            // ×の場合
            record.setMgpCpermission(ON);
            record.setMgpCreject(ON);
          } else {
            // 設定なしの場合
            record.setMgpCpermission(OFF);
            record.setMgpCreject(OFF);
          }
          record.setMgpDstartdate(dStart);
          record.setMgpDenddate(dEnd);
          // 今回改定日以降のレコードを削除
          deleteAfterList.add(record);
//        iMastGroupapppermissionService.deleteAfter(permission.getMgpCsystemid(),
//                SysUtil.transDateToString(dStart), permission.getMgpCgroupid(), permission.getMgpCobjectid());

          // 現在有効なレコード取得
          String sql = "SELECT MGP_ID, MGP_CCOMPANYID,MGP_CSYSTEMID,MGP_CGROUPID,MGP_COBJECTID,MGP_CSITE,MGP_CAPP,MGP_CSUBAPP,MGP_CBUTTON,MGP_CSCREEN,MGP_CPERMISSION,MGP_CREJECT,MGP_DSTARTDATE,MGP_DENDDATE FROM" +
                  " MAST_GROUPAPPPERMISSION WHERE MGP_CSYSTEMID = ? AND MGP_DSTARTDATE <= ? AND MGP_DENDDATE >= ? AND MGP_CGROUPID = ? ";
          Vector<Object> params = new Vector<>();
          params.add(record.getMgpCsystemid());
          params.add(strStart);
          params.add(strStart);
          params.add(record.getMgpCgroupid());
          if (StrUtil.isNotBlank(record.getMgpCobjectid())) {
            sql += "AND MGP_COBJECTID = ? ";
            params.add(record.getMgpCobjectid());
          }
          sql += "ORDER BY MGP_COBJECTID,MGP_DSTARTDATE";
          Vector<Vector<Object>> lEffective = dbControllerLogic.executeQuery(sql, params, conn);

          if (CollUtil.isNotEmpty(lEffective)) {
            if (lEffective.size()>1) {
              for (int i = 0; i < lEffective.size(); i++) {
                if (i!=0){
                  Vector<Object> fieldList = lEffective.get(i);
                  Long id = ((BigDecimal)fieldList.get(0)).longValue();
                  deleteEffectiveIds.add(id);
                  MastGroupapppermissionDO mastGroupapppermissionDO = new MastGroupapppermissionDO();
                  mastGroupapppermissionDO.setMgpId(id);
                  mastGroupapppermissionDO.setMgpCcompanyid((String)fieldList.get(1));
                  mastGroupapppermissionDO.setMgpCsystemid((String)fieldList.get(2));
                  mastGroupapppermissionDO.setMgpCgroupid((String)fieldList.get(3));
                  mastGroupapppermissionDO.setMgpCobjectid((String)fieldList.get(4));
                  mastGroupapppermissionDO.setMgpCsite((String)fieldList.get(5));
                  mastGroupapppermissionDO.setMgpCapp((String)fieldList.get(6));
                  mastGroupapppermissionDO.setMgpCsubapp((String)fieldList.get(7));
                  mastGroupapppermissionDO.setMgpCbutton((String)fieldList.get(8));
                  mastGroupapppermissionDO.setMgpCscreen((String)fieldList.get(9));
                  mastGroupapppermissionDO.setMgpCpermission((String)fieldList.get(10));
                  mastGroupapppermissionDO.setMgpCreject((String)fieldList.get(11));
                  mastGroupapppermissionDO.setMgpDstartdate((Timestamp)fieldList.get(12));
//                  Date endDate = (Timestamp)fieldList.get(13);
                  mastGroupapppermissionDO.setMgpDenddate(yesterday);
                  mastGroupapppermissionDO.setMgpCmodifieruserid(psSession.getLoginUser());
                  mastGroupapppermissionDO.setMgpDmodifieddate(DateUtil.date());
                  mastGroupapppermissionDO.setVersionno(1L);
                  insertEffectivePermList.add(mastGroupapppermissionDO);
                }
              }
            }
          }
//          List<MastGroupapppermissionDO> lEffective = iMastGroupapppermissionService.selectValidPermissions(
//                  record.getMgpCsystemid(), SysUtil.transDateToString(dStart), record.getMgpCgroupid(), record.getMgpCobjectid());

//          if (CollUtil.isNotEmpty(lEffective)) {
//            // 削除後、同じデータを終了日付のみ変更して登録する
//            for (MastGroupapppermissionDO mastGroupapppermissionDO : lEffective) {
//              deleteEffectiveIds.add(mastGroupapppermissionDO.getMgpId());
////            iMastGroupapppermissionService.removeById(mastGroupapppermissionDO);
//              mastGroupapppermissionDO.setMgpDenddate(yesterday);
//              insertEffectivePermList.add(mastGroupapppermissionDO);
////            iMastGroupapppermissionService.save(mastGroupapppermissionDO);
//            }
//          }
          // 画面から編集されたレコードを登録する
          MastGroupapppermissionDO permissionDO = new MastGroupapppermissionDO();
          BeanUtil.copyProperties(record, permissionDO);
          permissionDO.setVersionno(1L);
          prepareInsertList.add(permissionDO);
//        iMastGroupapppermissionService.save(permissionDO);
//        // 他システム上の同じObjectIdを持つレコードを削除

          deleteOtherSysList.add(record);

//          iMastGroupapppermissionService.deleteOtherSysObj(permission.getMgpCsystemid(),
//                  permission.getMgpCobjectid());
        }
      }
      log.debug("==============================");
      log.debug("数据准备完毕，开始权限修改");
      // ===================================删除数据开始================================
      // 今回改定日以降のレコードを削除
      int deleteAfterCount = 0;
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Start
      for (GroupAppManagerPermissionDTO item : deleteAfterList) {
        String sql = "DELETE FROM MAST_GROUPAPPPERMISSION WHERE MGP_CSYSTEMID = ? AND MGP_DSTARTDATE >= ? AND MGP_CGROUPID = ? ";
        int len = 3;
        if (StrUtil.isNotBlank(item.getMgpCobjectid())) {
          sql+="AND MGP_COBJECTID = ?";
          len = len+1;
        }
        if (len == 3) {
          deleteAfterCount += SqlExecutor.execute(conn, sql, item.getMgpCsystemid(), strStart,item.getMgpCgroupid());
        } else {
          deleteAfterCount += SqlExecutor.execute(conn, sql, item.getMgpCsystemid(), strStart,item.getMgpCgroupid(),item.getMgpCobjectid());
        }
      }
      log.debug("deleteAfter影响行数："+deleteAfterCount);
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> End
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Start
      int deleteEffectiveCount = 0;
      for (Long effectiveId: deleteEffectiveIds) {
        String sql = "DELETE FROM MAST_GROUPAPPPERMISSION WHERE MGP_ID = ?";
        deleteEffectiveCount += SqlExecutor.execute(conn, sql, effectiveId);
      }
      log.debug("deleteEffective影响行数："+deleteEffectiveCount);
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> End

      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Start
      int insertEffectiveCount = 0;
      for (MastGroupapppermissionDO item : insertEffectivePermList) {
        String insertSql = "INSERT INTO MAST_GROUPAPPPERMISSION (" +
                "MGP_ID, MGP_CCOMPANYID, MGP_CSYSTEMID, MGP_CGROUPID, MGP_COBJECTID, MGP_CSITE,MGP_CAPP, " +
                "MGP_CSUBAPP, MGP_CBUTTON, MGP_CSCREEN, MGP_CPERMISSION,MGP_CREJECT,MGP_DSTARTDATE, " +
                "MGP_DENDDATE, MGP_CMODIFIERUSERID, MGP_DMODIFIEDDATE, VERSIONNO) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         insertEffectiveCount += SqlExecutor.execute(conn,insertSql,
                 item.getMgpId(),item.getMgpCcompanyid(),item.getMgpCsystemid(),item.getMgpCgroupid(),
                 item.getMgpCobjectid(),item.getMgpCsite(),item.getMgpCapp(),item.getMgpCsubapp(),
                 item.getMgpCbutton(),item.getMgpCscreen(),item.getMgpCpermission(),item.getMgpCreject(),
                 SysUtil.transDateToString(item.getMgpDstartdate()),
                 SysUtil.transDateToString(item.getMgpDenddate()),item.getMgpCmodifieruserid(),
                 SysUtil.transDateToString(item.getMgpDmodifieddate()),item.getVersionno());
      }
      log.debug("insertEffectiveCount影响行数："+insertEffectiveCount);
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> End

      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Start
      int prepareInsertCount = 0;
      for (MastGroupapppermissionDO item : prepareInsertList) {
        String insertSql = "INSERT INTO MAST_GROUPAPPPERMISSION (" +
                "MGP_ID,MGP_CCOMPANYID, MGP_CSYSTEMID, MGP_CGROUPID, MGP_COBJECTID, MGP_CSITE,MGP_CAPP, " +
                "MGP_CSUBAPP, MGP_CBUTTON, MGP_CSCREEN, MGP_CPERMISSION,MGP_CREJECT,MGP_DSTARTDATE, " +
                "MGP_DENDDATE, MGP_CMODIFIERUSERID, MGP_DMODIFIEDDATE, VERSIONNO) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String nextValSeq = "SELECT MAST_GROUPAPPPERMISSION_SEQ.NEXTVAL FROM DUAL ";
        List<Entity> returnList  = SqlExecutor.query(conn,nextValSeq,new EntityListHandler());
        Long sequenceId = returnList.get(0).getNumber("NEXTVAL").longValue();
        prepareInsertCount += SqlExecutor.execute(conn,insertSql,
                sequenceId,item.getMgpCcompanyid(),item.getMgpCsystemid(),item.getMgpCgroupid(),
                item.getMgpCobjectid(),item.getMgpCsite(),item.getMgpCapp(),item.getMgpCsubapp(),
                item.getMgpCbutton(),item.getMgpCscreen(),item.getMgpCpermission(),item.getMgpCreject(),
                SysUtil.transDateToString(item.getMgpDstartdate()),
                SysUtil.transDateToString(item.getMgpDenddate()),item.getMgpCmodifieruserid(),
                SysUtil.transDateToString(item.getMgpDmodifieddate()),item.getVersionno());
      }
      log.debug("prepareInsertCount影响行数："+prepareInsertCount);
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> End

      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Start
      int deleteOtherSysCount = 0;
      for (GroupAppManagerPermissionDTO item : deleteOtherSysList) {
        String sql = "DELETE FROM MAST_GROUPAPPPERMISSION WHERE MGP_CSYSTEMID <> ? AND MGP_COBJECTID = ?";
        deleteOtherSysCount+= SqlExecutor.execute(conn, sql, item.getMgpCsystemid(), item.getMgpCobjectid());
      }
      log.debug("deleteOtherSysCount影响行数："+deleteOtherSysCount );
      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> End

      conn.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      conn.rollback();
      throw new GlobalException("query error");
    } finally {
       conn.setAutoCommit(true);
       if (conn!=null) {
         try {
           conn.close();
         }catch(SQLException e){
            e.printStackTrace();
         }
       }
    }

    return "権限の変更が成功しました";
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
          // 後処理
          permissionDto = null;
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
