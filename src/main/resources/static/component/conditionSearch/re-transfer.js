const ReTransfer = {
    name: 'ReTransfer',
    template: `<Row :gutter="8">
    <Col span="11">
      <div class="ivu-transfer-list width100" style="height: 500px;">
        <div class="ivu-transfer-list-header">
            <span class="ivu-transfer-list-header-title">{{ leftTitle }}</span>
        </div>
        <div class="ivu-transfer-list-body">
            <ul class="ivu-transfer-list-content">
                <li class="ivu-transfer-list-content-item conditionlist-transfer"
                    v-for="(item, i) of leftList" :key="item[leftKey]"
                    @click="handleClickedLeafLeaf(i)">
                    <Checkbox v-model="displayLeaftList[i]" :true-value="i"></Checkbox>
                    {{ item[leftLabel] }}
                </li>
                <li class="ivu-transfer-list-content-not-found"></li>
            </ul>
        </div>
    </div>
    </Col>
    <Col span="2" class="tc">
      <div class="ivu-transfer-operation" style="margin: 0; padding-top: 190px;">
        <slot name="top-btn" :left="displayLeaftList" :right="displayRightList"></slot>
        <Button type="primary" size="small" icon="ios-arrow-forward" ghost @click="addDisplayList" style="margin-bottom: 12px">{{ rightBtnText }}</Button>
        <Button type="primary" size="small" icon="ios-arrow-back" ghost @click="delDisplayList" style="margin-bottom: 12px">{{ leftBtnText }}</Button>
        <slot name="btm-btn" :left="displayLeaftList" :right="displayRightList"></slot>
      </div>
    </Col>
    <Col span="10">
      <div class="ivu-transfer-list width100" style="height: 500px;">
        <div class="ivu-transfer-list-header">
            <span class="ivu-transfer-list-header-title">{{ rightTitle }}</span>
        </div>
        <div class="ivu-transfer-list-body">
            <ul class="ivu-transfer-list-content">
                <li class="ivu-transfer-list-content-item conditionlist-transfer"
                    v-for="(item, i) of rightList" :key="item[rightKey]"
                    @click="handleClickedRightLeaf(i)">
                    <Checkbox v-model="displayRightList[i]" :true-value="i"></Checkbox>
                    {{ item[rightLabel] }}
                </li>
                <li class="ivu-transfer-list-content-not-found"></li>
            </ul>
        </div>
    </div>
    </Col>
  </Row>` ,
    components: {
        Button: iview.Button,
        Row: iview.Row,
        Col: iview.Col,
        Checkbox: iview.Checkbox,
    },
    props: {
        leftTitle: {
            type: String,
            default: '表示項目を選択してください'
        },
        rightTitle: {
            type: String,
            default: '選択された表示項目'
        },
        rightBtnText: {
            type: String,
            default: '追加'
        },
        rightBtnFn: {
            type: Function,
            default: e => e
        },
        leftBtnText: {
            type: String,
            default: '削除'
        },
        leftList: {
            type: Array,
            default: []
        },
        rightKey: {
            type: String,
            default: 'columnName'
        },
        leftKey: {
            type: String,
            default: 'columnName'
        },
        rightLabel: {
            type: String,
            default: 'columnFieldName'
        },
        leftLabel: {
            type: String,
            default: 'columnFieldName'
        },
    },
    data: function () {
        return {
            displayLeaftList: [],
            displayRightList: [],
            rightList: [],
        }
    },
    methods: {
        handleClickedLeafLeaf(i) {
            if (!this.displayLeaftList[i] && this.displayLeaftList[i] !== 0) this.$set(this.displayLeaftList, i, i)
            else this.$set(this.displayLeaftList, i, undefined)
        },
        handleClickedRightLeaf(i) {
            if (!this.displayRightList[i] && this.displayRightList[i] !== 0) this.$set(this.displayRightList, i, i)
            else this.$set(this.displayRightList, i, undefined)
        },
        addDisplayList() {
            let index = 0
            this.displayLeaftList.forEach(e => {
                if (e === 0 || e) {
                    const target = this.leftList.splice(e - index, 1)
                    this.rightList.push({...target[0], [this.rightLabel]: this.rightBtnFn(target[0][this.rightLabel])})
                    this.$set(this.displayLeaftList, e, undefined)
                    index += 1
                }
            })
        },
        delDisplayList() {
            let index = 0
            this.displayRightList.forEach(e => {
                if (e === 0 || e) {
                    this.$emit('to-left', ...this.rightList.splice(e - index, 1))
                    this.$set(this.displayRightList, e, undefined)
                    index += 1
                }
            })
        },
    }
}