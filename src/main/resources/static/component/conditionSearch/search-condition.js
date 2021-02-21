var operatorList = [
    { name: '＝', value: '=' },
    { name: '≠', value: '<>' },
    { name: '≧', value: '>=' },
    { name: '≦', value: '<=' },
    { name: '＞', value: '>' },
    { name: '＜', value: '<' },
    { name: '空白のみ', value: 'ISNULL' },
    { name: '空白以外', value: 'ISNOTNULL' },
    { name: '次が含まれる', value: 'CONTAINS' },
    { name: '次で始まる', value: 'STARTS_WITH' },
    { name: '次で終わる', value: 'ENDS_WITH' },
  ]
  var operatorList2 = [
    { name: '＝', value: '=' },
    { name: '≠', value: '<>' },
    { name: '≧', value: '>=' },
    { name: '≦', value: '<=' },
    { name: '＞', value: '>' },
    { name: '＜', value: '<' },
    { name: '空白のみ', value: 'ISNULL' },
    { name: '空白以外', value: 'ISNOTNULL' },
  ]
const SearchCondition = {
    name: 'SearchCondition',
    template: `<span><div style="display: flex;" class="mb5">
    <i-button type="primary" size="small" ghost @click="isByConditionMethod = !isByConditionMethod" class="mt10">{{ isByConditionMethod ? '条件を項目選択で指定する ->' : '条件を条件式で指定する ->'}}
    </i-button>
    <div style="flex: 1;"></div>
      <div v-show="isByConditionMethod">
        <i-button type="error" icon="md-trash" ghost @click="conditionDeleteAll">全行を削除
        </i-button>
        <i-button type="success" icon="md-add" ghost @click="conditionAddTop">1行追加
        </i-button>
      </div>
  </div>
  <i-table v-show="isByConditionMethod" class="mt5 no-padding" border :row-class-name="()=>'select-col'" :columns="queryColumns"
  @on-cell-click="handleCellClickForAction" :disabled-hover="true" :data="queryData" no-data-text=""
  ref="table">
  <template slot-scope="{ row }" slot="andor">{{ queryData[row._index].andor || '　'  }}</template>

  <template slot-scope="{ row }"
    slot="openedparenthsis">{{ queryData[row._index].openedparenthsis || '　' }}</template>

  <template slot-scope="{ row }"
    slot="columnname">{{ queryData[row._index].description || '　' }}</template>

  <template slot-scope="{ row }" slot="operator">{{ queryData[row._index].operator || '　' }}</template>

  <template slot-scope="{ row }"
    slot="displayvalue">{{ queryData[row._index].displayvalue || '　' }}</template>

  <template slot-scope="{ row }"
    slot="closedparenthsis">{{ queryData[row._index].closedparenthsis || '　' }}</template>
  <template slot-scope="{ row }" slot="action">
    <Poptip v-model="lightDelBtnVisible3[row._index]" placement="left" transfer>
      <div class="operateBtn" slot="content">
        <a @click="cancelconditionDelete(row._index)">いいえ</a>
        <a @click="conditionDelete(row, row._index)">はい</a>
      </div>
      <i-button type="error" ghost size="small" style="width: 83px;text-align: left;">削除
      </i-button>
    </Poptip>
    <i-button type="success" class="mt5" ghost icon="md-arrow-down" size="small"
      @click="conditionAdd(row._index)" long>1行追加
    </i-button>
  </template>
</i-table>
<i-table v-show="!isByConditionMethod" class="mt5 no-padding" border :row-class-name="()=>'select-col'" :columns="queryColumns2"
:disabled-hover="true" :data="queryData2" no-data-text=""　ref="table">
<template slot-scope="{ row }"slot="use">
  <Checkbox v-model="row.isChecked"></Checkbox>
</template>

<template slot-scope="{ row }" slot="columnname">
  <i-button type="primary" class="mt5 tl" ghost size="small"
    @click="handleClickQueryWhere(row)" long>{{ row.columnDescription }}
  </i-button>
</template>
<template slot-scope="{ row }" slot="displayvalue">
  <span v-if="queryData2[row._index].selectValue">{{ queryData2[row._index].selectValue.map(e=> e.name).join() }}</span>
  <span v-if="queryData2[row._index].postFlag">
    <radio-group class="tl" v-model="queryData2[row._index].postCode">
      <Radio label="1">本務</Radio>
      <Radio label="0">兼務</Radio>
    </radio-group>
  </span>
  <span v-if="queryData2[row._index].inWorkFlag">
    <radio-group class="tl" v-model="queryData2[row._index].atWorkCode">
      <Radio label="1">在職</Radio>
      <Radio label="0">退職</Radio>
    </radio-group>
  </span>
</template>
<template slot-scope="{ row }" slot="action">
  <i-button type="primary" class="mt5" ghost size="small"
    @click="handleCleanQueryWhere(row)" long>クリア
  </i-button>
</template>
</i-table>
<!--  条件式による定義 选择group和 役職 start-->
<Drawer class="tc" placement="right" :width="curdisplayPanel === 'searchDept' ? '100' : '700'" title="該当条件選択" :mask-closable="false"
v-model="showGroupOrPositionDrawer">
<span v-show="curdisplayPanel === 'QPOST'">
  <div class="titlenorm mb5">
    <Icon type="logo-buffer"></Icon>
    役職
  </div>
  <Alert class="primary-info tl" style="margin-bottom: 5px;">
    <div>※ 役職マスタを選択してください。</div>
    <div class="mt2">&emsp;&nbsp;検索結果件数 {{ positonData.length }}件</div>
  </Alert>
  <div class="tr mb5" v-if="selectTreeType === 'post'">
    <i-button type="primary" ghost @click="addPostTree">決定</i-button>
  </div>
  <i-table class="mb5" border :row-class-name="()=>'select-col'" :columns="positonColumns"
    :disabled-hover="true" :data="positonData" no-data-text="" ref="postTable">
    <template slot-scope="{ row }" slot="mapCpostidCk">
      <span class="mr10 pt2 pb2 pl10 pr10 cursor row-label-primary" @click="uptdatePositonValue(row)"
        v-if="selectTreeType !== 'post'">{{ row.mapCpostidCk }}</span>
      <span v-else>{{ row.mapCpostidCk }}</span>
    </template>

    <template slot-scope="{ row }" slot="mapCpostname">
      <span class="mr10 pt2 pb2 pl10 pr10 cursor row-label-primary" @click="uptdatePositonValue(row)"
        v-if="selectTreeType !== 'post'">{{ row.mapCpostname }}</span>
      <span v-else>{{ row.mapCpostname }}</span>
    </template>
  </i-table>
</span>

<Row :gutter="8">
  <i-col :span="curdisplayPanel === 'QSECTION'|| curdisplayPanel === 'base' ? 24 : 8"
    v-show="curdisplayPanel === 'QSECTION' || curdisplayPanel === 'searchDept'|| curdisplayPanel === 'base'">
    <div class="titlenorm mb5">
      <Icon type="logo-buffer"></Icon>
      所属
    </div>
    <Row class="tl mb5" :gutter="8">
      <i-col span="10">
        <i-input class="mar like-select" placeholder="検索" v-model="searchDept"
          @keyup.enter.native="handleSearchDept">
        </i-input>
      </i-col>
      <i-col span="8" class="no-border-radius">
        <i-button long type="primary" icon="md-search" ghost @click="handleSearchDept">検索
        </i-button>
      </i-col>
      <i-col span="4" offset="2" class="no-border-radius" v-if="curdisplayPanel === 'base'">
        <i-button long type="primary" ghost @click="handleSelectDept">決定
        </i-button>
      </i-col>
    </Row>
    <!-- 所属选择树 -->
    <Tree class="tree mt2" :data="treeData" @on-select-change="handleClickLeaf" @on-check-change="handleClickCheckTree" empty-text="所属データなし" :render="renderSearchTree" ref="selectTree" :show-checkbox="curdisplayPanel === 'base'" :check-strictly="curdisplayPanel === 'base'" :check-directly="curdisplayPanel === 'base'"></Tree>
  </i-col>
  <i-col :span="curdisplayPanel === 'searchEmp' ? 24 : 16"
    v-show="curdisplayPanel === 'searchEmp' || curdisplayPanel === 'searchDept'">
    <div class="titlenorm mb5">
      <Icon type="logo-buffer"></Icon>
      社員検索
    </div>
    <Row class="tl mb5" :gutter="8">
      <i-col span="10">
        <i-input class="mar like-select" placeholder="社員の絞込み検索" v-model="searchEmpt"
          @keyup.enter.native="handleSearchEmpt">
        </i-input>
      </i-col>
      <i-col span="6" class="no-border-radius">
        <i-button long type="primary" icon="md-search" ghost :loading="empSearchLoading" @click="handleSearchEmpt()">
          検索
        </i-button>
      </i-col>
    </Row>
    <div class="tr mb5">
      <i-button type="primary" ghost @click="addSearchEmp">決定</i-button>
    </div>
    <i-table class="mb5" border :row-class-name="()=>'select-col'" :columns="searchEmpColumns"
      :disabled-hover="true" :data="searchEmpData" :loading="empSearchLoading" no-data-text="" ref="empTable">
    </i-table>
  </i-col>
</Row>
<Spin size="large" fix v-if="empSearchLoading"></Spin>
</Drawer>
    </span>` ,
    components: {
        Button: iview.Button,
        Row: iview.Row,
        Col: iview.Col,
        Checkbox: iview.Checkbox,
        Poptip: iview.Poptip,
        RadioGroup: iview.RadioGroup,
        Radio: iview.Radio,
        Drawer: iview.Drawer,
        Alert: iview.Alert,
        Tree: iview.Tree,
        Spin: iview.Spin,
        Table: iview.Table,
    },
    props: {
        poptipType: {
            type: String,
            default: 'displayvalue'
        },
        curdisplayPanel: {
            type: String,
            default: ''
        },
        operatorType: {
            type: Boolean,
            default: false
        },
        poptipStyle: {
            type: Object,
            default: {
                display: 'none',
                position: 'absolute',
                'will-change': 'top, left',
                'z-index': '1001',
                left: '0',
                top: '0'
            }
        },
        queryData: {
            type: Array,
            default: [{ isAdd: true, addIndex: Date.now(), andor: '', openedparenthsis: '', columnname: '', operator: '', displayvalue: '', closedparenthsis: '', delete: false }]
        },
        clickedCell: {
            type: Object,
            default: { where: '', which: '' }
        },
    },
    data() {
        return {
            isByConditionMethod: true,
            queryColumns: [
                {
                    title: 'ＡＮＤ/ＯＲ',
                    slot: 'andor',
                    align: 'center',
                    width: 100,
                },
                {
                    title: '（',
                    width: 70,
                    align: 'center',
                    slot: 'openedparenthsis',
                },
                {
                    title: '項目',
                    slot: 'columnname',
                },
                {
                    title: '比較',
                    width: 120,
                    align: 'center',
                    slot: 'operator',
                },
                {
                    title: '値',
                    slot: 'displayvalue',
                },
                {
                    title: '）',
                    width: 70,
                    align: 'center',
                    slot: 'closedparenthsis',
                },
                {
                    title: ' ',
                    width: 100,
                    slot: 'action',
                },
            ],
            queryColumns2: [
                {
                    title: '使用',
                    slot: 'use',
                    align: 'center',
                    width: 100,
                },
                {
                    title: '条件項目（・・・が）',
                    slot: 'columnname',
                    width: 300,
                },
                {
                    title: '条件の値（以下のいずれかに該当する）',
                    minWidth: 300,
                    slot: 'displayvalue',
                },
                {
                    title: ' ',
                    width: 100,
                    slot: 'action',
                },
            ],
            // queryData: [{ isAdd: true, addIndex: Date.now(), andor: '', openedparenthsis: '', columnname: '', operator: '', displayvalue: '', closedparenthsis: '', delete: false }],
            lightDelBtnVisible3: [],
            queryData2: [],
            queryconditionsList: [],
            queryconditionsList_bak: [],
            hasSearchedQueryconditions: false,
            queryconditionsLoading: false,
            showGroupOrPositionDrawer: false,
            positonColumns: [
                {
                    title: 'コード',
                    width: 120,
                    slot: 'mapCpostidCk',
                },
                {
                    title: '名称',
                    slot: 'mapCpostname',
                },
            ],
            positonData: [],
            searchEmpColumns: [
                {
                    title: '社員番号',
                    width: 120,
                    key: 'meCuserid',
                },
                {
                    title: '氏名',
                    key: 'empName',
                },
                {
                    title: '所属',
                    key: 'sectionName',
                },
                {
                    title: '役職',
                    key: 'postName',
                },
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
            ],
            selectTreeType: '',
            searchDept: '',
            searchEmpt: '',
            treeData: [],
            searchEmpData: [],
            empSearchLoading: false,
            updateValue: {}
        }
    },
    created() {
        this.getQueryWhereList()
    },
    methods: {
        async getQueryWhereList() {
            const { data } = await this.http.get('sys/conditionsearch/options/where')
            this.queryData2 = data
        },
        conditionDeleteAll() {
            this.$Modal.confirm({
                inDrawer: true,
                title: '全行を削除します。よろしいですか？',
                okText: 'OK',
                cancelText: 'キャンセル',
                onOk: () => {
                    this.deleteQueryData = this.queryData.filter(e => !e.isAdd)
                    this.queryData = []
                }
            })
            this.poptipStyle.display = 'none'
        },
        conditionAddTop() {
            // 条件式表格顶部加一行
            this.queryData.unshift({ isAdd: true, addIndex: Date.now(), andor: '', openedparenthsis: '', columnname: '', operator: '', displayvalue: '', closedparenthsis: '', delete: false })
            this.poptipStyle.display = 'none'
        },
        conditionAdd(i) {
            // 条件式表格中间加一行
            this.queryData.splice(i + 1, 0, { isAdd: true, addIndex: Date.now(), andor: '', openedparenthsis: '', columnname: '', operator: '', displayvalue: '', closedparenthsis: '', delete: false })
            this.poptipStyle.display = 'none'
        },
        cancelconditionDelete(i) {
            this.$set(this.lightDelBtnVisible3, i, false)
        },
        conditionDelete(row, i) {
            if (row.isAdd) this.queryData = this.queryData.filter(e => e.addIndex !== row.addIndex)
            else this.queryData = this.queryData.filter(e => e.id !== row.id)
            this.$set(this.lightDelBtnVisible3, i, false)
        },
        async handleCellClickForAction(row, column, data, event) {
            // debugger
            this.$emit('close-poptip')
            if (column.slot === 'action') {
                return
            }
            this.$emit('query-fallback')
            // this.poptipType = column.slot
            this.$emit('change-poptip-type',column.slot)
            this.$emit('set-curdisplay-panel',row.dialogType)
            this.$emit('change-operator-type',row.type ? row.type === 'VARCHAR2' || row.type === 'NVARCHAR2' ? true : false : false)
            // 储存单击格的位置信息
            // this.clickedCell = {
            //     where: row._index,
            //     which: column.slot
            // }
            this.$emit('set-clickcell',{
                where: row._index,
                which: column.slot
            })
            this.searchDept = ''
            console.log(this.poptipType)
            if (column.slot === 'displayvalue') {
                console.log(row.dialogType)
                if (row.dialogType === 'QPOST') {
                    this.showGroupOrPositionDrawer = true
                    const selectionIndex = this.positonColumns.findIndex(e => e.type === 'selection')
                    if (selectionIndex > -1) {
                        this.positonColumns.splice(selectionIndex, 1)
                    }
                    this.selectTreeType = ''
                    const { data } = await this.http.get(`sys/tree/posts?psSite=${this.psSite}`)
                    this.positonData = data.list
                    return
                }
                if (row.dialogType === 'QSECTION') {
                    this.showGroupOrPositionDrawer = true
                    const { data } = await this.http.get(`sys/tree/orgs?psSite=${this.psSite}&psApp=${this.psApp}`)
                    this.treeData = this.convertTreeData(data)
                    this.treeData[0].expand = true
                    return
                }
            }
            const elemLocation = event.target.offsetParent.getBoundingClientRect()
            this.poptipStyle.display = 'block'
            // this.$emit('open-poptip')
            this.poptipStyle.left = `${elemLocation.x}px`
            this.poptipStyle.top = `${elemLocation.y}px`
        },
        handleClickQueryWhere(row) {
            this.$emit('set-curdisplay-panel',row.dialogType)
            this.$emit('change-poptip-type','displayvalue')
            // 储存单击格的位置信息
            // this.clickedCell = {
            //     where: row._index,
            //     which: 'displayvalue'
            // }
            this.$emit('set-clickcell',{
                where: row._index,
                which: 'displayvalue'
            })
            if (row.dialogFlag === 'QZAITAI') {
                // 在職 退職
                this.$set(this.queryData2[this.clickedCell.where], 'postFlag', true)
                this.$set(this.queryData2[this.clickedCell.where], 'postCode', '1')
            }
            if (row.dialogFlag === 'QHONKEN') {
                // 本務 兼務
                this.$set(this.queryData2[this.clickedCell.where], 'inWorkFlag', true)
                this.$set(this.queryData2[this.clickedCell.where], 'atWorkCode', '1')
            }
            if (row.dialogFlag === 'QPOST') {
                // 役職マスタ
                this.openPostTree()
            }
            if (row.dialogFlag === 'QSECTION') {
                // 弹出第二层抽屉选择所属
                this.openSectionTree()
            }
            if (!row.dialogFlag) {
                // 搜职员
                this.openEmpTree()
            }
        },
        handleCleanQueryWhere(row) {
            // 清空条件项目
            if (row.dialogFlag === 'QZAITAI') {
                // 在職 退職
                this.$set(this.queryData2[row._index], 'postFlag', false)
                this.$set(this.queryData2[row._index], 'postCode', '')
            }
            if (row.dialogFlag === 'QHONKEN') {
                // 本務 兼務
                this.$set(this.queryData2[row._index], 'inWorkFlag', false)
                this.$set(this.queryData2[row._index], 'atWorkCode', '')
            }
            if (row.dialogFlag === 'QPOST' || row.dialogFlag === 'QSECTION' || !row.dialogFlag) {
                // 役職マスタ
                this.$set(this.queryData2[row._index], 'selectValue', [])
            }
        },
        async openPostTree() {
            this.searchDept = ''
            this.showGroupOrPositionDrawer = true
            this.selectTreeType = 'post'
            if (!this.positonColumns.some(e => e.type === 'selection')) this.positonColumns = this.positonColumns.concat({ type: 'selection', width: 60, align: 'center' })
            const { data } = await this.http.get(`sys/tree/posts?psSite=${this.psSite}`)
            // 点击役職定義回显
            const targetArray = this.queryData2[this.clickedCell.where].selectValue
            let IDs = targetArray && targetArray.map(e => e.hswCvalue) || []
            this.positonData = data.list.map(e => {
                return {
                    ...e,
                    _checked: IDs.includes(e.mapCpostidCk)
                }
            })
        },
        async openSectionTree() {
            this.searchDept = ''
            this.showGroupOrPositionDrawer = true
            this.$emit('set-curdisplay-panel', 'base')
            this.selectTreeType = 'section'
            // this.selectedBaseTreeData = []
            const { data } = await this.http.get(`sys/tree/orgs?psSite=${this.psSite}&psApp=${this.psApp}`)
            // 点击追加組織・役職選択による定義回显
            const targetArray = this.queryData2[this.clickedCell.where].selectValue
            let IDs = targetArray && targetArray.map(e => e.hswCvalue) || []
            this.treeData = this.convertTreeData(data, IDs, 'sectionId')
        },
        openEmpTree() {
            this.searchDept = ''
            this.$emit('set-curdisplay-panel', 'searchEmp')
            this.showGroupOrPositionDrawer = true
        },
        async handleSearchEmpt() {
            try {
                this.empSearchLoading = true
                const { data } = await this.http.get(`sys/groupmanager/empsearch?psSite=${this.psSite}`, { searchWord: this.searchEmpt, type: 1 })
                this.checkDefaultEmpt(data)
            } catch (error) {
            } finally {
                this.empSearchLoading = false
            }
        },
        checkDefaultEmpt(data) {
            // 点开后将默认勾选勾上
            const targetArray = this.queryData2[this.clickedCell.where].selectValue
            let IDs = targetArray && targetArray.map(e => e.hswCvalue) || []
            console.log(IDs)
            this.searchEmpData = data.map(e => {
                return { ...e, _checked: IDs.includes(e.meCemployeeidCk) }
            })
        },
        handleSelectDept() {
            this.$set(this.queryData2[this.clickedCell.where], 'selectValue', this.$refs.selectTree.getCheckedNodes().map(e => {
                return {
                    id: e.moId,
                    name: ` ${e.sectionName}(${e.sectionId})`,
                    hswCvalue: e.sectionId,
                }
            }))
            this.showGroupOrPositionDrawer = false
        },
        addPostTree() {
            this.$set(this.queryData2[this.clickedCell.where], 'selectValue', this.$refs.postTable.getSelection().map(e => {
                return {
                    id: e.mapId,
                    name: ` ${e.mapCpostname}(${e.mapCpostidCk})`,
                    hswCvalue: e.mapCpostidCk,
                }
            }))
            this.showGroupOrPositionDrawer = false
        },
        // 将选择中的社员加到社員を指定表格里
        addSearchEmp() {
            this.$set(this.queryData2[this.clickedCell.where], 'selectValue', this.$refs.empTable.getSelection().map(e => {
                return {
                    id: e.meId,
                    name: ` ${e.empName}(${e.meCemployeeidCk})`,
                    hswCvalue: e.meCemployeeidCk,
                }
            }))
            this.showGroupOrPositionDrawer = false
        },
        // 可以传入一个selectedItemArray, 来回显已被选择的值
        convertTreeData(treeData, selectedItemArray, selectedItemkeyName) {
            if (!treeData) {
                this.$Message.error({
                    background: true,
                    closable: true,
                    duration: 6.5,
                    content: '参照できる組織図が存在しません。'
                });
                return []
            }
            return treeData.map((e, i) => {
                if (e.children) {
                    return {
                        ...e,
                        title: e.sectionName,
                        expand: true,
                        checked: selectedItemArray && selectedItemArray.includes(e[selectedItemkeyName]),
                        children: this.convertTreeData(e.children, selectedItemArray, selectedItemkeyName)
                    }
                } else {
                    return {
                        ...e,
                        title: e.sectionName,
                        checked: selectedItemArray && selectedItemArray.includes(e[selectedItemkeyName])
                    }
                }
            })
        },
        handleSearchDept: Debounce(function () {
            this.sectionInTreeData(this.treeData)
            this.treeData = Utils.deepClone(this.treeData)
        }),
        sectionInTreeData(data, father) {
            const _this = this
            // 这里再有已勾选项时会消失，所以只应该打上记号
            data.forEach((e, i) => {
                e.searchWords = ''
                if (e.title.indexOf(this.searchDept) > -1) {
                    if (father) father.expand = true
                    e.searchWords = this.searchDept
                }
                if (e.children && e.children.length > 0) {
                    _this.sectionInTreeData(e.children, e);
                }
            })
        },
        renderSearchTree(h, { root, node, data }) {
            const searchWords = data.searchWords
            if (searchWords && data.title.indexOf(searchWords) > -1) {
                const title = data.title.split(searchWords)
                return h('span', {
                    style: {
                        display: 'inline-block',
                        width: '100%'
                    }
                }, [
                    h('span', title[0]),
                    h('span', {
                        style: {
                            color: 'var(--row-label-primary)',
                            fontWeight: 'bold'
                        },
                    }, searchWords),
                    h('span', title[1])
                ])
            }
            return h('span', data.title)
        },
        handleClickLeaf(e) {
            if (!e[0]) return
            // if (this.curdisplayPanel === 'searchDept') {
            //   try {
            //     this.empSearchLoading = true
            //     const { data } = await this.http.get(`sys/groupmanager/empsearch?psSite=${this.psSite}`, { targetDept: e[0].sectionId, type: 2 })
            //     this.searchEmpData = data.map(e => {
            //       // 点开后将默认勾选勾上
            //       if (this.emptData.find(t => t.employeeId === e.meCuserid)) {
            //         return { ...e, _checked: true }
            //       } else {
            //         return e
            //       }
            //     })
            //   } catch (error) {
            //   } finally {
            //     this.empSearchLoading = false
            //   }
            //   return
            // }
            this.queryData[this.clickedCell.where][this.clickedCell.which] = `${e[0].sectionName}[${e[0].sectionId}]`
            this.showGroupOrPositionDrawer = false
        },
        handleClickCheckTree(e, current) {
            if (!e[0]) return
            // 如果有子节点，则遍历影响
            if (current.children && current.children.length > 0) {
                this.treeCheckHack(current.children, current.checked)
            }
        },
        // 使得check-strictly的情况下， 勾选/取消 checkbox父也会遍历全部子集
        treeCheckHack(treeNode, checked) {
            let _this = this
            treeNode.forEach((e, i) => {
                _this.$set(e, 'checked', checked)
                if (e.children && e.children.length > 0) {
                    _this.treeCheckHack(e.children, checked);

                }
            })
        },
        uptdatePositonValue(e) {
            //mapCpostname  mapCpostidCk
            if (this.selectTreeType === 'post') {
                return
            } else {
                this.queryData[this.clickedCell.where].displayvalue = `${e.mapCpostname}[${e.mapCpostidCk}]`
            }
            this.showGroupOrPositionDrawer = false
        },
    }
}