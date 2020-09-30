// 全局混入的限制1970/01/01 08:00 < date < SYS_DATE下个月的每一天都可选
Vue.mixin({
    data () {
      return {
        startLimit: {
          disabledDate: date => {
            const firstDay = new Date(SYS_DATE).setDate(1)
            return date.valueOf() < -3600000 || date.valueOf() > Utils.addMonths(firstDay, 2).getTime() - 1000 * 60 * 60 * 24
          }
        }
      }
    }
})