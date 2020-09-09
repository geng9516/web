const Utils = {
  /**
   * 時刻获得
   * @param msec 毫秒数
   * @returns 格式化好的时间
   */
  getTime: function (msec) {
    const time = new Date(msec)
    const year = time.getFullYear()
    const week = time.getDay()

    let month = time.getMonth() + 1
    let day = time.getDate()
    let hours = time.getHours()
    let minutes = time.getMinutes()
    let seconds = time.getSeconds()
    month = month < 10 ? '0' + month : month
    day = day < 10 ? '0' + day : day
    hours = hours < 10 ? '0' + hours : hours
    minutes = minutes < 10 ? '0' + minutes : minutes
    seconds = seconds < 10 ? '0' + seconds : seconds

    const dateMsg = `${year}-${month}-${day}`
    const hourMsg = `${hours}:${minutes}:${seconds}`
    return { dateMsg, hourMsg, week }
  },
  /**
   * 日付をフォーマット
   * @param {Date} date 日付
   * @param {String} format フォーマット
   */
  formatDate: function (date, format = 'YYYY-MM-DD hh:mm:ss') {
    if (typeof date !== Date) {
      date = new Date(date)
    }

    let result = format.replace(/YYYY/gi, date.getFullYear())
    result = result.replace(/MM/gi, ('0' + (date.getMonth() + 1)).slice(-2))
    result = result.replace(/DD/gi, ('0' + date.getDate()).slice(-2))
    result = result.replace(/hh/gi, ('0' + date.getHours()).slice(-2))
    result = result.replace(/mm/gi, ('0' + date.getMinutes()).slice(-2))
    result = result.replace(/ss/gi, ('0' + date.getSeconds()).slice(-2))

    result = result.replace(/M/gi, date.getMonth() + 1)
    result = result.replace(/D/gi, date.getDate())
    result = result.replace(/h/gi, date.getHours())
    result = result.replace(/m/gi, date.getMinutes())
    result = result.replace(/s/gi, date.getSeconds())

    return result
  },
  /**
   * timeToMinute
   * @param time 当前的选项的value
   * @returns Number 要发送的分钟数
   */
  timeToMinute: function (time) {
    if (time.indexOf(':') > -1) {
      return +time.split(':')[0] * 60 + +time.split(':')[1]
    }
    return time.replace(/(^[0-9]{1,2})([0-9]{2}$)/g, (match, $1, $2) => +$1 * 60 + +$2)
  },
  /**
   * 深拷贝
   * @param obj
   * @returns {Function}
   */
  deepClone: function (obj) {
    if (obj === null || typeof obj !== 'object') return obj
    let newObj = obj instanceof Array ? [] : {}
    for (let key in obj) {
      if (obj.hasOwnProperty(key)) {
        newObj[key] = typeof obj[key] === 'object' ? this.deepClone(obj[key]) : obj[key]
      }
    }
    return newObj
  },
  /**
   * 时间check和自动转换
   * 715 -> 7:15, 1005 -> 10:05 自动加冒号
   * 360 -> 4:00, 1487 -> 15:27  分钟正确进位小时
   * 使用方法:　在你的on-blur / on-change 的时候传入更新用value的对应对象引用, 键名, $el
   *          你可以保存该返回值, 在对应文件中点击更新按钮时再次弹出提醒
   * @param obj 对象引用
   * @param name 属性键名
   * @param el element
   * @returns 是否通过了时间check, false为未通过
   */
  checkTime(object, name, el) {
    if (!object[name]) {
      return true
    }
    const regExp1 = /^[0-9]{1,2}:[0-9]{2}$/g
    const regExp2 = /^[0-9]{3,4}$/g
    if (regExp1.test(object[name]) || regExp2.test(object[name])) {
      // 兼容三位和四位的输入
      const minutes = this.timeToMinute(object[name])
      // 统一格式化为变成HH:MM
      const handleTime = Vue.filter('handleTime')

      object[name] = handleTime(+minutes)
      return true
    }
    // el.focus() 取消focus
    Vue.prototype.$Notice.error({
      title: '注意',
      desc: '時刻をHH:MM形式で入力してください',
      duration: 6.5
    })
    return false
  },
  /**
   * 将不规范的JSON规范化
   * 给与键名双引号，键值单引号转双引号
   * @param data 递归的数据
   *
   */
  convertToData: function (data) {
    if (!data) return
    return JSON.parse(data.replace(/([a-z]+)(?=:)/gi, '\"$1\"').replace(/'/g, '\"'))
  },
  /**
   * 通用转化所属选择器data
   * @param treeData 标准树形数据
   * @param fristDisable 是否禁用第一项
   *
   */
  convertTreeData(treeData = [], fristDisable) {
    return treeData.map((e, i) => {
      const children = e.child || e.children
      if (children) {
        return {
          ...e.data,
          title: e.data.label,
          // 前两层级默认展开方便查看
          expand: e.data.level < 2 || !e.data.level,
          //是否禁用第一项
          disabled: i === 0 && fristDisable,
          children: this.convertTreeData(children)
        }
      } else {
        return {
          ...e.data,
          disabled: i === 0 && fristDisable,
          title: e.data.label
        }
      }
    })
  },
  /**
   * プラス・マイナス月份
   * @param {date} date
   * @returns {Function}
   */
  addMonths: function (date, months) {
    let d = date.getDate()
    date.setMonth(date.getMonth() + +months)
    if (date.getDate() !== d) {
      date.setDate(0)
    }
    return new Date(date)
  },
  minusMonths: function (date, months) {
    let d = date.getMonth()
    date.setMonth(date.getMonth() - months)
    if (date.getMonth() === d) {
      date.setDate(0)
    }
    return new Date(date)
  },
  /**
   * 下载文件
   * @param data repsonse
   * @param filename filename
   * @param type Blob type
   */
  downloadFile: function (data, filename, type) {
    let file = {}
    if (type.indexOf('pdf') > -1) {
      file = new Blob([data], { type: type })
    } else {
      file = new Blob([`\ufeff${data}`], { type: type })
    }
    if (window.navigator.msSaveOrOpenBlob) {
      // IE10+
      window.navigator.msSaveOrOpenBlob(file, filename)
    } else {
      // Others
      let a = document.createElement('a')
      const url = URL.createObjectURL(file)
      a.href = url
      a.download = filename
      a.style.display = 'none'
      document.body.appendChild(a)
      a.click()
      setTimeout(function () {
        document.body.removeChild(a)
        window.URL.revokeObjectURL(url)
      }, 0)
    }
  },
  /**
   * 检测时间段是否重叠,为true重叠
   * 用于日期时，务必转成yyyymm 或 yyyymmdd 的格式
   * @param StartA 8:00
   * @param EndA   12:00
   * @param StartB 9:00
   * @param EndB   10:00
   * @returns {boolean}
   */
  timeOverlap: function (StartA, EndA, StartB, EndB) {
    if (StartA.indexOf(':') > -1) {
      return Math.max(this.timeToMinute(StartA), this.timeToMinute(StartB)) < Math.min(this.timeToMinute(EndA), this.timeToMinute(EndB))
    }
    return Math.max(+StartA, +StartB,) < Math.min(+EndA, +EndB)
  },
  /**
   * 检测多个时间段是否重叠,为true重叠
   * 如果是单个的请使用timeOverlap
   * @param timeArray [{open:'12:00',close:'12:10'},{open:'12:00',close:'12:01'},{open:'12:09',close:'12:10'}]
   * @param formkey   时间开始字段的键名
   * @param endkey 时间结束字段的键名
   * @param errorMsg 错误信息
   * @returns {boolean}
   */
  checkTimeOverlap: function (timeArray, formkey = 'open', endkey = 'close', errorMsg = '時間が重複しています。') {
    let checkArr = []
    timeArray.forEach(e => {
      const timeArray = this.getNumArray(this.timeToMinute(e[formkey]), this.timeToMinute(e.close[endkey]))
      const timeArraySliced = this.getNumArray(this.timeToMinute(e[formkey]), this.timeToMinute(e.close[endkey])).slice(1, -1)
      if (timeArraySliced.length === 0) {
        console.log(timeArray)
        timeArray.forEach(e => {
          if (checkArr.includes(e)) {
            checkArr = checkArr.concat(e)
          }
        })
      } else {
        checkArr = checkArr.concat(timeArraySliced)
      }
    })
    const checkArr2 = new Set([...checkArr])
    if (checkArr.length !== checkArr2.size) {
      Vue.prototype.$Notice.warning({ title: '注意', desc: errorMsg, duration: 6.5 })
      return true
    }
    return false
  }
  /**
   * 检测多个日期段是否重叠,为true重叠
   * 你需要在这之前check 其中之一为空值和end小于start的情况
   * @param dateArray [{start: Date,end:Date},{start:Date,end:Date}]
   * @returns {boolean}
   */
  multipleDateRangeOverlaps: function (dateArray) {
    if (dateArray.length === 1) return false
    let _dateArray = []
    dateArray.forEach(e => {
      // 排除两个都为空值
      if (e.start && e.end) {
        _dateArray = _dateArray.concat(this.formatDate(e.start, 'yyyymmdd'))
        _dateArray = _dateArray.concat(this.formatDate(e.end, 'yyyymmdd'))
      }
    })
    // 被去重了就说明已经有重复的了
    if (_dateArray.length !== new Set([..._dateArray]).size) return true

    // 检查所有项目
    for (let i = 0; i < _dateArray.length - 2; i += 2) {
      for (let j = i + 2; j < _dateArray.length; j += 2) {
        if (
          this.timeOverlap(
            _dateArray[i], _dateArray[i + 1],
            _dateArray[j], _dateArray[j + 1]
          )
        ) return true;
      }
    }
    return false;
  },
  /**
   * 获取一个自增的数字数组，如输入1，4 获得[1,2,3,4]
   * @param {start, end} date
   * @returns {Array}
   */
  getNumArray: function (start, end) {
    return Array(end - start + 1)
      .fill(0)
      .map((v, i) => i + start)
  },
  /**
   * 获取指定URL参数
   * @param {sUrl, sKey} string
   * @returns {value}
   */
  getUrlParam(sUrl, sKey) {
    let result, Oparam = {};
    sUrl.replace(/[\?&]?(\w+)=(\w+)/g, function ($0, $1, $2) {
      Oparam[$1] === void 0 ? Oparam[$1] = $2 : Oparam[$1] = [].concat(Oparam[$1], $2);
    });
    sKey === void 0 || sKey === '' ? result = Oparam : result = Oparam[sKey] || '';
    return result;
  },
  /**
   * 向下找到所有的子组件
   * @param  context 当前的组件，也就是传入 this
   * @param  componentName 要找的组件的 name
   * @returns 返回的是一个数组，包含了所有找到的组件实例
   */
  findChildrens(context, componentName) {
    return context.$children.reduce((components, child) => {
      if (child.$options.name === componentName) components.push(child)
      const foundChilds = findChildrens(child, componentName)
      return components.concat(foundChilds)
    }, [])
  }
}
// 为了简便写法，不和Utils放一起了
/**
 * 防抖 (只执行最后一次触发)
 * @param fn
 * @param delay
 * @returns {Function}
 */
const Debounce = (fn, t) => {
  let delay = t || 500
  let timer
  return function () {
    let args = arguments
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(() => {
      timer = null
      fn.apply(this, args)
    }, delay)
  }
}
/**
 * 节流
 * @param fn
 * @param interval
 * @returns {Function}
 */
const Throttle = (fn, t) => {
  let last
  let timer
  let interval = t || 500
  return function () {
    let args = arguments
    let now = +new Date()
    if (last && now - last < interval) {
      clearTimeout(timer)
      timer = setTimeout(() => {
        last = now
        fn.apply(this, args)
      }, interval)
    } else {
      last = now
      fn.apply(this, args)
    }
  }
}

// 语言日文
iview.lang('ja-JP');

// 全局配置iview
// Vue.prototype.$IVIEW.transfer = true
// Vue.prototype.$IVIEW.capture = false

window.requestAnimFrame =
  window.requestAnimationFrame ||
  window.webkitRequestAnimationFrame ||
  window.mozRequestAnimationFrame ||
  window.oRequestAnimationFrame ||
  window.msRequestAnimationFrame ||
  function (callback) {
    window.setTimeout(callback, 1000 / 30);
  }
window.cancelAnimationFrame = window.cancelAnimationFrame || window.mozCancelAnimationFrame;