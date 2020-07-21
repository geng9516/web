// 分钟数转为小时数，支持负数
Vue.filter('handleTime', function (e) {
  if (!e) {
    return ''
  }
  const E = Math.abs(e)
  if (E >= 6000) {
    const result = `0${(E / 60) ^ 0}`.slice(-3) + ':' + ('0' + (E % 60)).slice(-2)
    return e < 0 ? `-${result}` : result
  }
  const result = `0${(E / 60) ^ 0}`.slice(-2) + ':' + ('0' + (E % 60)).slice(-2)
  return e < 0 ? `-${result}` : result
})

// 给天数和时间数字增加精度
Vue.filter('addNumDeci', function (e, isDay) {
  if (!e) {
    if (!isDay) return '0.00'
    return '0.0'
  }
  if (!isDay) return parseFloat(e).toFixed(2)
  else return parseFloat(e).toFixed(1)
})

const TURN_LINE_STR = "<br>"
const WHITE_SPACE_STR = "&nbsp;"
// 换行过滤器
Vue.filter('turnLine', function (e) {
  if(!e) return
  return e.replace(/(\<br\/?\>)+/g, '\n')
})

Vue.filter('whiteSpace', function (e) {
  if(!e) return
  return e.replace(/(&nbsp;)+/g, '')
})