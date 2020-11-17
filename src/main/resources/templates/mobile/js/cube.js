const EVENT_CHANGE = 'change'
const EVENT_INPUT = 'input' // only used for v-model
const EVENT_PICKER_SHOW = 'picker-show'
const EVENT_PICKER_HIDE = 'picker-hide'
const EVENT_SELECT = 'select'
const EVENT_CANCEL = 'cancel'
const TYPE_LIST = ['year', 'month', 'date', 'hour', 'minute', 'second']
const DEFAULT_KEYS = {
  value: 'value',
  text: 'text',
  order: 'order'
}
const NATURE_BOUNDARY_MAP = {
  month: {
    natureMin: 1,
    natureMax: 12
  },
  date: {
    natureMin: 1,
    natureMax: 31
  },
  hour: {
    natureMin: 0,
    natureMax: 23
  },
  minute: {
    natureMin: 0,
    natureMax: 59
  },
  second: {
    natureMin: 0,
    natureMax: 59
  }
}

const DEFAULT_FORMAT = {
  year: 'YYYY',
  month: 'M',
  date: 'D',
  hour: 'hh',
  minute: 'mm',
  second: 'ss'
}

function findIndex(ary, fn) {
  if (ary.findIndex) {
    return ary.findIndex(fn)
  }
  /* istanbul ignore next */
  let index = -1
  /* istanbul ignore next */
  ary.some(function (item, i, ary) {
    const ret = fn.call(this, item, i, ary)
    if (ret) {
      index = i
      return ret
    }
  })
  /* istanbul ignore next */
  return index
}
const cubeSelect = {
  name: 'cube-select',
  template: `<div class="cube-select" :class="{ 'cube-select_active': active, 'cube-select_disabled': disabled }" @click="showPicker">
  <span v-if="selectedText" class="cube-select-text">{{ selectedText }}</span>
  <span v-else class="cube-select-placeholder">{{ _placeholder }}</span>
  <i class="cube-select-icon"></i>
</div>` ,
  data: function () {
    return {
      active: false
    }
  },
  props: {
    options: {
      type: Array,
      default() {
        /* istanbul ignore next */
        return []
      }
    },
    value: null,
    placeholder: {
      type: String,
      default: ''
    },
    autoPop: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    cancelTxt: {
      type: String,
      default: ''
    },
    confirmTxt: {
      type: String,
      default: ''
    }
  },
  computed: {
    adaptOptions() {
      return [this.options.map(item => {
        if (typeof item !== 'object') {
          item = {
            value: item,
            text: item
          }
        }
        return item
      })]
    },
    valueIndex() {
      const val = this.value
      const index = findIndex(this.adaptOptions[0], (item) => {
        return item.value === val
      })
      this.picker && this.picker.setData(this.adaptOptions, index !== -1 ? [index] : [0])

      return index
    },
    selectedIndex() {
      return this.valueIndex !== -1 ? [this.valueIndex] : [0]
    },
    selectedText() {
      return this.valueIndex !== -1 ? this.adaptOptions[0][this.valueIndex].text : ''
    },
    _placeholder() {
      return this.placeholder || 'selectText'
    },
    _title() {
      return this.title || 'entry time'
    },
    _cancelTxt() {
      return this.cancelTxt || 'cancel'
    },
    _confirmTxt() {
      return this.confirmTxt || 'Confirm'
    }
  },
  created() {
    this.picker = this.$createPicker({
      $props: {
        title: '_title',
        data: 'adaptOptions',
        selectedIndex: 'selectedIndex',
        cancelTxt: '_cancelTxt',
        confirmTxt: '_confirmTxt',
        zIndex:900
      },
      $events: {
        select: 'selectHandler',
        cancel: this.hided
      }
    })
    this.autoPop && this.showPicker()
  },
  methods: {
    showPicker() {
      if (this.disabled) {
        return
      }
      this.picker.show()
      this.active = true
      this.$emit(EVENT_PICKER_SHOW)
    },
    hided() {
      this.active = false
      this.$emit(EVENT_PICKER_HIDE)
    },
    selectHandler(selectedVal, selectedIndex, selectedText) {
      this.hided()
      if (selectedVal[0] !== this.value) {
        this.$emit(EVENT_INPUT, selectedVal[0])
        this.$emit(EVENT_CHANGE, selectedVal[0], selectedIndex[0], selectedText[0])
      }
    }
  }
}

const CascadePicker = {
  name: 'cube-cascade-picker',
  template: `<cube-picker
  ref="picker"
  v-model="isVisible"
  :data="pickerData"
  :selected-index="pickerSelectedIndex"
  :pending="pending"
  :title="title"
  :subtitle="subtitle"
  :z-index="zIndex"
  :cancel-txt="_cancelTxt"
  :confirm-txt="_confirmTxt"
  :swipe-time="swipeTime"
  :mask-closable="maskClosable"
  @select="_pickerSelect"
  @cancel="_pickerCancel"
  @change="_pickerChange">
</cube-picker>` ,
  model: {
    prop: 'visible',
    event: 'toggle'
  },
  props: {
    title: {
      type: String
    },
    subtitle: {
      type: String
    },
    cancelTxt: {
      type: String,
      default: ''
    },
    confirmTxt: {
      type: String,
      default: ''
    },
    swipeTime: {
      type: Number,
      default: 2500
    },
    maskClosable: {
      type: Boolean,
      default: true
    },
    async: {
      type: Boolean,
      default: false
    },
    data: {
      type: Array,
      default() {
        return []
      }
    },
    selectedIndex: {
      type: Array,
      default() {
        return []
      }
    },
    alias: {
      type: Object,
      default() {
        return {}
      }
    },
    visible: {
      type: Boolean,
      default: false
    },
    zIndex: {
      type: Number,
      default: 100
    },
    maskClosable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      cascadeData: this.data.slice(),
      pickerSelectedIndex: this.selectedIndex.slice(),
      pickerData: [],
      pending: false,
      isVisible: false
    }
  },
  watch: {
    isVisible(newVal) {
      this.$emit("toggle", newVal)
    },
    merge(newVal) {
      this.setData(newVal[0], newVal[1])
    }
  },
  mounted() {
    this.$watch('visible', (newVal, oldVal) => {
      if (newVal) {
        this.show()
      } else if (oldVal && !this._createAPI_reuse) {
        this.hide()
      }
    }, {
      immediate: true
    })
  },
  created() {
    this._updatePickerData()
  },
  computed: {
    _cancelTxt() {
      return this.cancelTxt || 'cancel'
    },
    _confirmTxt() {
      return this.confirmTxt || 'Confirm'
    },
    valueKey() {
      return this.alias.value || DEFAULT_KEYS.value
    },
    textKey() {
      return this.alias.text || DEFAULT_KEYS.text
    },
    orderKey() {
      return DEFAULT_KEYS.order
    },
    merge() {
      return [this.data, this.selectedIndex]
    }
  },
  methods: {
    show() {
      this.isVisible = true
    },
    hide() {
      this.isVisible = false
    },
    setData(data, selectedIndex = []) {
      this.pending = false
      this.cascadeData = data.slice()
      this.pickerSelectedIndex = selectedIndex.slice()
      this._updatePickerData()
    },
    _pickerSelect(selectedVal, selectedIndex, selectedText) {
      this.$emit(EVENT_SELECT, selectedVal, selectedIndex, selectedText)
    },
    _pickerCancel() {
      this.$emit(EVENT_CANCEL)
    },
    _pickerChange(i, newIndex) {
      if (newIndex !== this.pickerSelectedIndex[i]) {
        this.pickerSelectedIndex.splice(i, 1, newIndex)
        this.async
          ? (this.pending = i !== this.pickerData.length - 1)
          : this._updatePickerData(i + 1)
      }
      this.$emit(EVENT_CHANGE, i, newIndex)
    },
    _updatePickerData(fromColumn = 0) {
      let data = this.cascadeData
      let i = 0
      while (data) {
        if (i >= fromColumn) {
          let columnData = []
          data.forEach((item) => {
            columnData.push({
              value: item[this.valueKey],
              text: item[this.textKey],
              order: item[this.orderKey]
            })
          })
          this.pickerData[i] = columnData
          /* refillColumn could only be called after show() */
          this.pickerSelectedIndex[i] = fromColumn === 0
            ? (this.pickerSelectedIndex[i] < data.length ? this.pickerSelectedIndex[i] || 0 : 0)
            : this.$refs.picker.refillColumn(i, columnData)
        }
        data = data.length ? data[this.pickerSelectedIndex[i]].children : null

        i++
      }

      if (i < this.pickerData.length) {
        this.pickerData.splice(i, this.pickerData.length - i)
      }

      this.pickerData = this.pickerData.slice()
    }
  },
  components: {
    CubePicker: cube.picker
  }
}


function deepAssign(to, from) {
  for (let key in from) {
    if (!to[key] || typeof to[key] !== 'object') {
      to[key] = from[key]
    } else {
      deepAssign(to[key], from[key])
    }
  }
}
function pad(value) {
  return ('00' + value).substr(('' + value).length);
}
function formatType(type, format, value, regExpAttributes) {
  const regExpMap = {
    year: '(Y+)',
    month: '(M+)',
    date: '(D+)',
    hour: '(h+)',
    minute: '(m+)',
    second: '(s+)',
    quarter: '(q+)',
    millisecond: '(S)'
  }

  if (new RegExp(regExpMap[type], regExpAttributes).test(format)) {
    const replaceStr = type === 'year'
      ? value.toString().substr(4 - RegExp.$1.length)
      : (RegExp.$1.length === 1)
        ? value
        : pad(value)
    format = format.replace(RegExp.$1, replaceStr)
  }

  return format
}
function computeNatureMaxDay(month, year) {
  let natureMaxDay = 30
  if ([1, 3, 5, 7, 8, 10, 12].indexOf(month) > -1) {
    natureMaxDay = 31
  } else {
    if (month === 2) {
      natureMaxDay = !year || (!(year % 400) || (!(year % 4) && year % 100)) ? 29 : 28
    }
  }

  return natureMaxDay
}
function dateToArray(date) {
  return [date.getFullYear(), date.getMonth() + 1, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds()]
}
const DPicker = {
  name: 'DPicker',
  template: `<cube-cascade-picker
  v-model="isVisible"
  :data="data"
  :selected-index="selectedIndex"
  :title="title"
  :subtitle="subtitle"
  :cancel-txt="_cancelTxt"
  :confirm-txt="_confirmTxt"
  :swipe-time="swipeTime"
  :z-index="zIndex"
  :mask-closable="maskClosable"
  @select="_select"
  @cancel="_cancel"
  @change="_change">
</cube-cascade-picker>` ,
  data: function () {
    return {
      active: false
    }
  },
  model: {
    prop: 'visible',
    event: 'toggle'
  },
  props: {
    title: {
    type: String
  },
  subtitle: {
    type: String
  },
  cancelTxt: {
    type: String,
    default: ''
  },
  confirmTxt: {
    type: String,
    default: ''
  },
  swipeTime: {
    type: Number,
    default: 2500
  },
  maskClosable: {
    type: Boolean,
    default: true
  },
  zIndex: {
    type: Number,
    default: 100
  },
  maskClosable: {
    type: Boolean,
    default: false
  },
  visible: {
    type: Boolean,
    default: false
  },
  min: {
    type: [Date, Array],
    default() {
      return new Date(2010, 0, 1)
    }
  },
  max: {
    type: [Date, Array],
    default() {
      return new Date(2020, 11, 31)
    }
  },
  startColumn: {
    type: String,
    default() {
      return 'year'
    }
  },
  columnCount: {
    type: Number,
    default: 3
  },
  format: {
    type: Object,
    default() {
      return {}
    }
  },
  value: {
    type: [Date, Array],
    default() {
      return this.min
    }
  },
  columnOrder: {
    type: Array,
    default() {
      return ['year', 'month', 'date', 'hour', 'minute', 'second']
    }
  }
  },
  data() {
    return {
      isVisible: false
    }
  },
  watch: {
    isVisible(newVal) {
      this.$emit('toggle', newVal)
    }
  },
  mounted() {
    this.$watch('visible', (newVal, oldVal) => {
      if (newVal) {
        this.show()
      } else if (oldVal && !this._createAPI_reuse) {
        this.hide()
      }
    }, {
      immediate: true
    })
  },
  created() {
  },
  computed: {
    _cancelTxt() {
    return this.cancelTxt || 'cancel'
  },
  _confirmTxt() {
    return this.confirmTxt || 'Confirm'
  },
  formatConfig() {
    const formatConfig = Object.assign({}, DEFAULT_FORMAT)
    deepAssign(formatConfig, this.format)

    return formatConfig
  },
  natureRangeCache() {
    const natureRangeCache = {
      hour: [],
      minute: [],
      second: []
    }

    Object.keys(natureRangeCache).forEach((key) => {
      natureRangeCache[key] = this._range(key, NATURE_BOUNDARY_MAP[key].natureMin, NATURE_BOUNDARY_MAP[key].natureMax)
    })

    return natureRangeCache
  },
  startIndex() {
    const startIndex = TYPE_LIST.indexOf(this.startColumn)
    return startIndex < 0 ? 0 : startIndex
  },
  minArray() {
    return this.min instanceof Date
      ? dateToArray(this.min).slice(this.startIndex, this.startIndex + this.columnCount)
      : this.min
  },
  maxArray() {
    return this.max instanceof Date
      ? dateToArray(this.max).slice(this.startIndex, this.startIndex + this.columnCount)
      : this.max
  },
  valueArray() {
    return this.value instanceof Date
      ? dateToArray(this.value).slice(this.startIndex, this.startIndex + this.columnCount)
      : this.value
  },
  data() {
    const data = []
    this._generateData(this.startIndex, 0, data)
    return data
  },
  selectedIndex() {
    const selectedIndex = []
    let data = this.data
    let index

    for (let i = 0; i < this.columnCount && i < 6 - this.startIndex; i++) {
      index = findIndex(data, (item) => {
        return this.valueArray[i] && item.value === this.valueArray[i]
      })
      selectedIndex[i] = index !== -1 ? index : 0
      data = data[selectedIndex[i]] && data[selectedIndex[i]].children
    }

    return selectedIndex
  }
  },
  methods: {
    show() {
      this.isVisible = true
    },
    hide() {
      this.isVisible = false
    },
    _select(selectedVal, selectedIndex, selectedText) {
      this.$emit(EVENT_SELECT, this._arrayToDate(selectedVal), selectedVal, selectedText)
    },
    _cancel() {
      this.$emit(EVENT_CANCEL)
    },
    _change(i, newIndex) {
      this.$emit(EVENT_CHANGE, i, newIndex)
    },
    _generateData(i, count, item) {
      if (count === 0) {
        const min = i === 0 ? this.minArray[0] : Math.max(this.minArray[0], NATURE_BOUNDARY_MAP[TYPE_LIST[i]].natureMin)
        const max = i === 0 ? this.maxArray[0] : Math.min(this.maxArray[0], NATURE_BOUNDARY_MAP[TYPE_LIST[i]].natureMax)
        item.push.apply(item, this._range(TYPE_LIST[i], min, max, true, true))
      } else {
        if (i < 3 || item.isMin || item.isMax) {
          const natureMax = i === 2 ? computeNatureMaxDay(item.value, item.year) : NATURE_BOUNDARY_MAP[TYPE_LIST[i]].natureMax
          const min = item.isMin ? Math.max(this.minArray[count], NATURE_BOUNDARY_MAP[TYPE_LIST[i]].natureMin) : NATURE_BOUNDARY_MAP[TYPE_LIST[i]].natureMin
          const max = item.isMax ? Math.min(this.maxArray[count], natureMax) : natureMax

          const storageYear = i === 1 && this.startIndex === 0 && this.columnCount >= 3 && item.value
          item.children = this._range(TYPE_LIST[i], min, max, item.isMin, item.isMax, storageYear)
        } else {
          item.children = this.natureRangeCache[TYPE_LIST[i]]
        }
      }
      if (count < this.columnCount - 1 && i < 5) {
        (item.children || item).forEach(subItem => {
          (!subItem.children || subItem.isMin || subItem.isMax) && this._generateData(i + 1, count + 1, subItem)
        })
      }
    },
    _arrayToDate(selectedVal) {
      const args = []
      const defaultDateArray = dateToArray(new Date(0))

      for (let i = 0; i < 6; i++) {
        if (i < this.startIndex) {
          args[i] = defaultDateArray[i]
        } else if (i >= this.startIndex + this.columnCount) {
          args[i] = NATURE_BOUNDARY_MAP[TYPE_LIST[i]].natureMin
        } else {
          args[i] = selectedVal[i - this.startIndex]
        }
      }
      // Month need to subtract 1.
      args[1]--

      return new Date(...args)
    },
    _range(type, min, max, fatherIsMin, fatherIsMax, year = 0) {
      if (!this._rangeCache) {
        this._rangeCache = {}
      }
      const k = type + year + min + max + fatherIsMin + fatherIsMax
      if (this._rangeCache[k]) {
        return this._rangeCache[k]
      }
      const arr = []
      const format = this.formatConfig[type]
      for (let i = min; i <= max; i++) {
        const object = {
          text: formatType(type, format, i, 'i'),
          value: i,
          order: this.columnOrder.indexOf(type)
        }

        if (fatherIsMin && i === min) object.isMin = true
        if (fatherIsMax && i === max) object.isMax = true
        if (year) object.year = year

        arr.push(object)
      }
      this._rangeCache[k] = arr
      return arr
    }
  },
  components: {
    cubeCascadePicker: CascadePicker
  }
}
cube["create-api"](Vue, cube.picker, ['click'], true)
cube["create-api"](Vue, CascadePicker, ['click'], true)
cube["create-api"](Vue, DPicker, ['click'], true)
Vue.component('cube-select',cubeSelect)
cube.cascadePicker = CascadePicker
cube.dPicker = DPicker