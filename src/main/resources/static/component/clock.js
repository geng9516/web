/**
 * 時刻获得
 * @param msec 毫秒数
 * @returns 格式化好的时间
 */
function getTime(msec) {
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
}
/**
 * 日付をフォーマット
 * @param {Date} date 日付
 * @param {String} format フォーマット
 */
function formatDate(date, format = 'YYYY-MM-DD hh:mm:ss') {
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
}
const clock_c = {
  name: 'clock',
  template: `<div v-if="timeView.dateMsg.length > 0" class="shadow">
  <div class="date-style">
    <span class="date mr15">{{ formatDate(timeView.dateMsg, 'yyyy年mm月dd日') }}</span>
    <span class="week">{{ mobileWeekHelper[timeView.week] }}</span>
  </div>
  <div class="hour-style">
    <span>{{ timeView.hourMsg.substring(0, 5) }}</span>
    <span style="font-size:35px;color: rgb(169,169,169)">{{ timeView.hourMsg.substring(6, 8) }}</span>
  </div>
</div>` ,
  props: {
    time: {
      type: Number,
      default: Date.now()
    }
  },
  data: function () {
    return {
      timemachine: {},
      timeView: {
        dateMsg: '',
        hourMsg: '',
        week: ''
      },
      weekHelper: [
        'checkIn.SUN',
        'checkIn.MON',
        'checkIn.TUE',
        'checkIn.WED',
        'checkIn.THU',
        'checkIn.FRi',
        'checkIn.SAT'
      ],
      mobileWeekHelper: ['日曜日', '月曜日', '火曜日', '水曜日', '木曜日', '金曜日', '土曜日']
    }
  },
  methods: {
    showTime() {
      // 手机部分
      // const Sever_Time = localStorage.getItem('SO_Mobile_Sever_Time')
      // const Local_Time = localStorage.getItem('SO_Mobile_Local_Time')
      // const time = Date.now() - Local_Time + +Sever_Time
      // const { dateMsg, hourMsg, week } = this.utils.getTime(time)
      this.$emit('updatetime', 1000)
      const { dateMsg, hourMsg, week } = getTime(this.time)
      this.timeView = { dateMsg, hourMsg, week }
      this.timemachine = setTimeout(this.showTime, 1000)
    },
    cleanClock() {
      clearTimeout(this.timemachine)
    }
  }
}