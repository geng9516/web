package jp.smartcompany.controller.personalinformation;

import cn.hutool.core.map.MapUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.logic.IConditionSearchLogic;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.option.*;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search.ConditionSettingDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IConditionSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 自由条件检索controller
 * @author Xiao Wenpeng
 */
@RestController
@RequestMapping("sys/conditionsearch")
@RequiredArgsConstructor
public class ConditionSearchController {

    private final IConditionSearchLogic conditionSearchLogic;
    private final IConditionSearchService conditionSearchService;

    /**
     * 获取数据库表名选择列表
     * http://localhost:6879/sys/conditionsearch/options/tbl
     */
    @GetMapping("options/tbl")
    public List<TableOptionDTO> getTableOptions() {
      return conditionSearchLogic.getTableOptions();
    }

    /**
     * 获取数据库中对应表的字段名选择列表
     * http://localhost:6879/sys/conditionsearch/options/col?table=TMG_V_CDS_EMPLOYEES
     */
    @GetMapping("options/col")
    public List<ColumnOptionDTO> getColumnOptions(@RequestParam String table) { return conditionSearchLogic.getColumnOptions(table); }

    /**
     * 表示するWhere句取得(簡易版)
     */
    @GetMapping("options/where")
    public List<ConditionWhereDTO> getWhereItems() {
        return conditionSearchService.selectWhereConditions(1L);
    }


    /**
     * 获取条件式定义数据库中可用的表
     * http://localhost:6879/sys/conditionsearch/defs/tbl
     */
    @GetMapping("defs/tbl")
    public List<TableQueryDefinitionOptionDTO> getTableOptionsForQueryDefinition() { return conditionSearchLogic.getTableForQueryDefinition(); }

    /**
     *  获取条件式定义数据库表下的列选择项
     *  http://localhost:6879/sys/conditionsearch/defs/col?table=HIST_V_CDS_DESIGNATION
     */
    @GetMapping("defs/col")
    public List<ColumnQueryDefinitionOptionDTO> getColumnForQueryDefinition(@RequestParam String table) { return conditionSearchLogic.getColumnForQueryDefinition(table); }

    /**
     * 查询参数说明：
     *  {
     *     "useQueryDefinition" true=利用条件式检索 false=利用条件项目检索
     *     "useCooperation": false // 検索結果データ読込 复选框
     *     "standardDateType": 1, // 1=指定日付時点のデータを検索する（通常はこちらを使用してください） 0=履歴も全て検索する
     *     "standardDate": "2021/02/08",   // 基準日関連
     *     "showMastCode": true, // マスタ参照項目を表示するときはコードも併せて表示する 复选框
     *     "mode": SCREEN=画面 CSV=CSV出力 TABLE=テーブル
     *     "pagerLinkDTO": { 分页参数
     *         "currentPage": 1,
     *         "pagerCondition": 20
     *     },
     *     "companyId": "01" // 法人code,
     *     "pagingEvent": pageCondition=每页显示条数发生了改变  currentPage=当前页发生了改变
     *     // 表示項目条件
     *     selectDtoList: [
     *        {
     *          hssId: IDカラム
     *          hssNsettingid:  設定ID
     *          hssNseq: 行番号
     *          hssCcolumn: カラムID
     *          columnName: プロパティ変数
     *        },
     *        ...
     *     ],
     *     // 条件項目
     *     whereDtoList: [
     *         {
                    mswId IDカラム
                    mswCcsutomerid 顧客コード
                    mswCtableid テーブルID
                    mswCcolumnid カラムID
                    mswNseq 並び順
                    mswCemployee 社員情報フラグ'
                    selectValue 選択値 [
                          {
                              hswCname 値名称
                              hswId IDカラム
                              hswNsettingid 設定ID
                              hswCtable テーブルID
                              hswCcolumn カラムID
                              hswCvalue 値
                              hswCuse 条件使用有無
                          }
                          ...
                    ]
                    use 使用可否
     *         }
     *         ...
     *     ],
     *     // 条件式項目
     *     queryConditionDtoList: [
     *         {
                   id ID
                   customerid 顧客コード
                   systemid システムコード
                   groupid グループコード
                   permissionid 定義コード
                   startdate 開始日
                   enddate 終了日
                   seq シーケンス番号
                   andor 論理演算子
                   openedparenthsis 開始カッコ
                   tableid テーブルID
                   mastertablename 選択済のマスタ区分
                   columnid カラムID
                   columnname カラム名(テーブル.カラム名)
                   typeofcolumn データ型
                   displayoperator 表示演算子
                   operator 演算子
                   companyid 法人コード
                   value 比較値
                   displayvalue 表示文字列
                   closedparenthsis 閉じカッコ
                   joinquery:  クエリ(結合式)
                   myflag: 自分のフラグ
                   delete:
                   settingid:
     *         }
     *         ...
     *     ],
     *     // ソート項目
     *     orderDtoList: [
     *         {
     *            hsoCcolumnId: プロパティ変数
                  mdCcolumnname  プロパティ変数
                  hsoId IDカラム
                  hsoNsettingid 設定ID
                  hsoNseq 行番号
                  hsoCcolumn カラムID
                  hsoCorder ソート順
              }
     *     ],
     *     // 共有範囲
     *     targetDtoList: [
              {
                groupId:,
                groupName:,
                hstId: IDカラム
                hstNsettingid: 設定ID
                hstCtargetsystem: 対象システム
                hstCtargetgroup 対象グループ
              }
     *     ],
     *     hseCcompanyselect, // 法人選択区分 1=全法人共通
     *     hseNdataId: // 連携データID,当mode为table模式时才使用到
     *     hseCusecooperation: 連携使用有無 1=useCooperation为true的情况
     *  }
     */
    @PostMapping("search")
    public Map<String,Object> search(@RequestBody ConditionSettingDTO settingDTO) {
        return conditionSearchLogic.search(settingDTO);
    }


    /**
     *
     * ======================
     *      编辑共有相关接口
     * ======================
     *
     */

    /**
     * 获取编辑共有前端显示需要的参数
     */
    @GetMapping("show/addOrUpdate")
    public Map<String,Object> showEdit(@RequestParam(required = false) Long settingId) {
        return conditionSearchService.showAddOrUpdate(settingId);
    }

    /**
     * 编辑共有
     */
    @PostMapping("addOrUpdate")
    public GlobalResponse editSettings(@RequestBody ConditionSettingDTO settingDTO) {
        return conditionSearchLogic.editSettings(settingDTO);
    }

    /**
     * 自由条件設定読込画面表示処理
     */
    @GetMapping("show/read")
    public List<CommonConditionVO> getConditionVoList() {
        return conditionSearchLogic.getConditionVoList();
    }


}
