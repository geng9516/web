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
        newObj[key] = typeof obj[key] === 'object' ? deepClone(obj[key]) : obj[key]
      }
    }
    return newObj
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
   *
   */
  convertTreeData(treeData = []) {
    return treeData.map(e => {
        const children = e.child || e.children
        if (children) {
            return {
                ...e.data,
                title: e.data.label,
                // 前两层级默认展开方便查看
                expand: e.data.level < 2 || !e.data.level,
                children: this.convertTreeData(children)
            }
        } else {
            return {
                ...e.data,
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

window.requestAnimFrame =
    window.requestAnimationFrame ||
    window.webkitRequestAnimationFrame ||
    window.mozRequestAnimationFrame ||
    window.oRequestAnimationFrame ||
    window.msRequestAnimationFrame ||
    function(callback) {
        window.setTimeout(callback, 1000 / 30);
    }
window.cancelAnimationFrame = window.cancelAnimationFrame || window.mozCancelAnimationFrame;