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
const PopSelect = {
    name: 'PopSelect',
    template: `
    <div class="ivu-pop-popper" :style="poptipStyle" x-placement="right-start">
      <div class="ivu-poptip-content">
        <div class="ivu-poptip-arrow"></div>
        <div class="ivu-poptip-inner">
          <div class="ivu-poptip-body" style="max-height: 350px; overflow-y: auto;">
            <div class="ivu-poptip-body-content">
              <div class="ivu-poptip-body-content-inner">
                <!-- ＡＮＤ/ＯＲ -->
                <span v-show="poptipType === 'andor'">
                  <i-button @click="popClickAction('なし')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>なし</i-button>
                  <i-button @click="popClickAction('AND')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>AND</i-button>
                  <i-button @click="popClickAction('OR')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>OR</i-button>
                </span>
  
                <!-- （ -->
                <span v-show="poptipType === 'openedparenthsis'">
                  <i-button @click="popClickAction('なし')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>なし</i-button>
                  <i-button @click="popClickAction('(')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>(</i-button>
                  <i-button @click="popClickAction('((')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>((</i-button>
                  <i-button @click="popClickAction('(((')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>(((</i-button>
                </span>
  
                <!-- 項目 -->
                <span v-show="poptipType === 'columnname'">
                  <i-button v-for="(item,i) of queryconditionsList" :key="i" type="primary" ghost
                    @click="handleQueryconditions(item)" style="display:block;" size="small" class="mb5 tl" long>
                    {{ item.description }}</i-button>
                </span>
  
                <!-- 比較 -->
                <span v-show="poptipType === 'operator'">
                  <i-button @click="popClickAction(item.name, item.value)"
                    v-for="(item,i) of operatorType ? operatorList : operatorList2" type="primary" ghost
                    style="display:block" size="small" class="mb5" long>{{ item.name }}</i-button>
                </span>
  
                <!-- 値 -->
                <span v-show="poptipType === 'displayvalue'">
                  <!-- 为null时 自己输入
                  QHONKEN  0本務  兼務
                  QSECTION 弹出第二层抽屉选择所属
                  QPOST 役職マスタを選択してください。 弹出第二层抽屉选择役職マスタ
                  QPOSTNUM  役職順位マスタを選択してください。 没有
                  QZAITAI  在職 	退職
                  TMG_MANAGEFLG 管理対象外 管理対象 -->
                  <i-input class="mb5" v-if="!curdisplayPanel" v-model="updateValue.value"></i-input>
                  <i-button v-show="!curdisplayPanel" type="primary" ghost @click="updateInputValue"
                    style="display:block" size="small" class="mb5" long>決定</i-button>
                  <span v-show="curdisplayPanel === 'QHONKEN'">
                    <i-button @click="popClickAction('本務[0]', '0')" type="primary" ghost style="display:block"
                      size="small" class="mb5" long>本務[0]</i-button>
                    <i-button @click="popClickAction('兼務[1]', '1')" type="primary" ghost style="display:block"
                      size="small" class="mb5" long>兼務[1]</i-button>
                  </span>
  
                  <span v-show="curdisplayPanel === 'QZAITAI'">
                    <i-button @click="popClickAction('在職[0]', '0')" type="primary" ghost style="display:block"
                      size="small" class="mb5" long>在職[0]</i-button>
                    <i-button @click="popClickAction('退職[1]', '1')" type="primary" ghost style="display:block"
                      size="small" class="mb5" long>退職[1]</i-button>
                  </span>
  
                  <span v-show="curdisplayPanel === 'TMG_MANAGEFLG'">
                    <i-button @click="popClickAction('管理対象外[0]', '0')" type="primary" ghost style="display:block"
                      size="small" class="mb5" long>管理対象外[0]</i-button>
                    <i-button @click="popClickAction('管理対象[1]', '1')" type="primary" ghost style="display:block"
                      size="small" class="mb5" long>管理対象[1]</i-button>
                  </span>
                </span>
  
                <!-- ） -->
                <span v-show="poptipType === 'closedparenthsis'">
                  <i-button @click="popClickAction('なし')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>なし</i-button>
                  <i-button @click="popClickAction(')')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>)</i-button>
                  <i-button @click="popClickAction('))')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>))</i-button>
                  <i-button @click="popClickAction(')))')" type="primary" ghost style="display:block" size="small"
                    class="mb5" long>)))</i-button>
                </span>
                <i-button v-show="!hasSearchedQueryconditions || poptipType !== 'columnname'" @click="cancelpoptip"
                  style="display:block" size="small" class="mb5" long>キャンセル</i-button>
                <i-button v-show="hasSearchedQueryconditions && poptipType === 'columnname'"
                  @click="queryconditionsFallback" style="display:block" size="small" class="mb5" long>戻る</i-button>
  
                <Spin size="large" fix v-if="queryconditionsLoading"></Spin>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>` ,
    components: {
        Button: iview.Button,
        Spin: iview.Spin,
        Input: iview.Input
    },
    props: {
        queryData: {
            type: Array,
            default: [{ isAdd: true, addIndex: Date.now(), andor: '', openedparenthsis: '', columnname: '', operator: '', displayvalue: '', closedparenthsis: '', delete: false }]
        },
        clickedCell: {
            type: Object,
            default: { where: '', which: '' }
        },
        poptipType: {
            type: String,
            default: ''
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
    },
    data() {
        return {
            // curdisplayPanel: '',
            // poptipStyle: {
            //     display: 'none',
            //     position: 'absolute',
            //     'will-change': 'top, left',
            //     'z-index': '1001',
            //     left: '0',
            //     top: '0'
            // },
            // poptipType: '',
            queryconditionsLoading: false,
            queryconditionsList: [],
            queryconditionsList_bak: [],
            hasSearchedQueryconditions: false,
            // operatorType: false,
            updateValue: {}
        }
    },
    created() {
        this.getqueryconditions()
    },
    watch:{
        poptipType(newv) {
            console.log(newv)
        }
    },
    methods: {
        async getqueryconditions() {
            const { data } = await this.http.get('sys/conditionsearch/defs/tbl')
            this.queryconditionsList = data
            this.queryconditionsList_bak = [...data]
        },
        queryconditionsFallback() {
            if (this.hasSearchedQueryconditions) {
                this.queryconditionsList = this.queryconditionsList_bak
                this.hasSearchedQueryconditions = false
            }
        },
        updateInputValue() {
            // 将input的值更新到表格中
            this.queryData[this.clickedCell.where].displayvalue = this.updateValue.value
            this.queryData[this.clickedCell.where].value = this.updateValue.value
            this.poptipStyle.display = 'none'
            this.updateValue.value = ''
        },
        cancelpoptip() {
            this.poptipStyle.display = 'none'
            this.updateValue.value = ''
        },
        // 点击項目
        async handleQueryconditions(e) {

            if (this.hasSearchedQueryconditions) {
                this.$set(this.queryData[this.clickedCell.where], 'description', e.description)
                console.log('shie')
                console.log(this.queryData)
                // columnname 项目
                // displayvalue 值
                // 将项目的选中值更新到表格中
                //   更新选择得条件
                this.queryData[this.clickedCell.where].name = e.name
                this.queryData[this.clickedCell.where].type = e.type


                //   更新值的变换信息
                this.queryData[this.clickedCell.where].dialogType = e.dialogType
                this.curdisplayPanel = e.dialogType
                //   清空之前的值信息
                this.queryData[this.clickedCell.where].displayvalue = ''
                this.queryData[this.clickedCell.where].value = ''
                this.queryData[this.clickedCell.where].operator = ''
                this.poptipStyle.display = 'none'
                return
            }
            try {
                this.queryconditionsLoading = true
                const { data } = await this.http.get('sys/conditionsearch/defs/col', { table: e.name })
                this.hasSearchedQueryconditions = true
                this.queryconditionsList = data
            } catch (error) {
            } finally {
                this.queryconditionsLoading = false
            }
        },
        // 点击改变表格的值
        popClickAction(e, value2) {
            if (e === 'なし') {
                this.queryData[this.clickedCell.where][this.clickedCell.which] = ''
                if (this.clickedCell.which === 'openedparenthsis') this.queryData[this.clickedCell.where].closedparenthsis = ''
                if (this.clickedCell.which === 'closedparenthsis') this.queryData[this.clickedCell.where].openedparenthsis = ''
            } else {
                this.queryData[this.clickedCell.where][this.clickedCell.which] = e
                const paernthsis = {
                    '(': ')',
                    '((': '))',
                    '(((': ')))',
                    ')': '(',
                    '))': '((',
                    ')))': '(((',
                }
                if (this.clickedCell.which === 'displayvalue') this.queryData[this.clickedCell.where].value = value2
                // 这里和group不同，需要显示label
                if (this.clickedCell.which === 'operator') this.queryData[this.clickedCell.where].operator = e
                if (this.clickedCell.which === 'openedparenthsis') this.queryData[this.clickedCell.where].closedparenthsis = paernthsis[e]
                if (this.clickedCell.which === 'closedparenthsis') this.queryData[this.clickedCell.where].openedparenthsis = paernthsis[e]
            }
            this.poptipStyle.display = 'none'
        }
    }
}