const Clock = {
  name: 'clock',
  template: `<div v-if="timeView.dateMsg.length > 0" class="shadow">
  <div class="date-style">
    <span class="date mr15">{{ Utils.formatDate(timeView.dateMsg, 'yyyy年mm月dd日') }}</span>
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
      // const { dateMsg, hourMsg, week } = Utils.getTime(time)
      this.$emit('updatetime', 1000)
      const { dateMsg, hourMsg, week } = Utils.getTime(this.time)
      this.timeView = { dateMsg, hourMsg, week }
      this.timemachine = setTimeout(this.showTime, 1000)
    },
    cleanClock() {
      clearTimeout(this.timemachine)
    }
  }
}