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
        <slot name="top-btn" :left="displayLeaftList" :right="displayRightList" :rightList="rightList"></slot>
        <Button type="primary" size="small" icon="ios-arrow-forward" ghost @click="addDisplayList" style="margin-bottom: 12px">{{ rightBtnText }}</Button>
        <Button type="primary" size="small" icon="ios-arrow-back" ghost @click="delDisplayList" style="margin-bottom: 12px">{{ leftBtnText }}</Button>
        <slot name="btm-btn" :left="displayLeaftList" :right="displayRightList" rightList="rightList"></slot>
      </div>
    </Col>
    <Col span="10">
      <div class="ivu-transfer-list width100" style="height: 500px;">
        <div class="ivu-transfer-list-header" style="display:flex">
            <span class="ivu-transfer-list-header-title">{{ rightTitle }}</span>
            <span style="flex: 1;"></span>
            <span class="mr10">ドラッグ機能:  </span><i-switch v-model="isDraggable" size="large">
            <span slot="open">ON</span>
            <span slot="close">OFF</span>
          </i-switch>
        </div>
        <div class="ivu-transfer-list-body">
            <draggable class="ivu-transfer-list-content" v-model="rightList" v-bind="dragOptions" @start="onDragStart" @end="onDragEnd" :disabled="!isDraggable" forceFallback>
            <transition-group type="transition" :name="!drag ? 'flip-list' : null">
                <li class="ivu-transfer-list-content-item conditionlist-transfer"
                    v-for="(item, i) of rightList" :key="item[rightKey]"
                    @click="handleClickedRightLeaf(i)">
                    <Checkbox v-model="displayRightList[i]" :true-value="i"></Checkbox>
                    {{ item[rightShowLabel] }}
                </li>
                <li class="ivu-transfer-list-content-not-found"></li>
            </transition-group>
            </draggable>
        </div>
    </div>
    </Col>
  </Row>` ,
    components: {
        Button: iview.Button,
        Row: iview.Row,
        Col: iview.Col,
        Checkbox: iview.Checkbox,
        draggable: vuedraggable
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
        // 用于右边框的显示，避免rightLabel被重复污染
        rightShowLabel: {
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
            isDraggable: true,
            drag: false,
            dragOptions() {
                return {
                    animation: 200,
                    group: "sort",
                    ghostClass: "ghost"
                };
            }
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
                    this.rightList.push({ ...target[0], [this.rightShowLabel]: this.rightBtnFn(target[0][this.rightLabel]) })
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
        onDragStart(e) {
            this.drag = true
        },
        onDragEnd(e) {
            // 将被drag歪掉的check的复位
            if(e.oldIndex !== e.newIndex) {
                if(this.displayRightList[e.oldIndex] || this.displayRightList[e.oldIndex] === 0) {
                    this.$set(this.displayRightList, e.newIndex, e.newIndex)
                    this.$set(this.displayRightList, e.oldIndex, undefined)
                }
            }
            this.drag = false
        }
    }
}