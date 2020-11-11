new Vue({
    el: '.mobile-warp',
    data() {
        return {
            defaultDate: '', //年月选择（Date）
            dispMonthlyList: [],       //年月リスト
            hatuReiName:'',
            hatuResTimeRange:'',
            today:Utils.formatDate(new Date(),'YYYY/MM/DD'),
            scheduleDataDTOList:[],
            yyyyMMdd:'',
            paidHolidayVO:{},
            selectScheduleInfo:{},
            loading: false,　       //ローディングアクション
            index: 0,
            contentScrollTop: 0,
            kanjiName: KANJINAME,　　 //ログインしたユーザの氏名
            loginEmployee: LOGIN_EMPOLYEE,  //ログインしたユーザの職員番号
            sectionName: SECTION_NAME,　 //ログインしたユーザの所属
            query: {
                employeeId: LOGIN_EMPOLYEE,　 //組織ツリーで選択した職員番号
                psSite: Utils.getUrlParam(location.href, 'psSite'),
                psApp: Utils.getUrlParam(location.href,'psApp'),
                txtBaseDate:'',
                txtEndDate:'',
            }
        }
    },
    async mounted() {
        this.selectScheduleDateList()
        window.addEventListener('scroll',this.handleScroll,{ passive: true })
    },
    beforeDestroy() {
        window.removeEventListener('scroll',this.handleScroll,{ passive: true })
    },
    computed: {
        tableHeadFixed() {
            if (this.contentScrollTop > 118) return true
            else return false
        },
        columns() {
            return [
        {
          title: '曜日',
          slot: 'week',
          width: 35,
          align: 'center'
        },
        {
          title: '日',
          slot: 'intDay',
          minWidth: 50,
          align: 'center'
        },
        {
          title: '区分',
          minWidth: 60,
          slot: 'workDivision',
          align: 'center'
        },
        {
          title: '始業',
          align: 'center',
          minWidth: 50,
          maxWidth: 60,
          slot: 'workStartFinal'
        },
        {
          title: '終業',
          minWidth: 50,
          maxWidth: 60,
          slot: 'workEndFinal',
          align: 'center'
        },
        {
          title: '休憩',
          className: 'checkin change-line',
          width: 60,
          slot: 'restTimeList',
          align: 'center'
        },
      ]
        }
    },
    methods: {
        async getSelectScheduleInfo() {
            this.loading=true
            try {
                const { data } = await this.http.get(`sys/schedule/selectScheduleInfo`, this.query)
                this.selectScheduleInfo=data
                this.paidHolidayVO=this.selectScheduleInfo.paidHolidayVO
                this.scheduleDataDTOList=this.selectScheduleInfo.scheduleDataDTOList
                this.yyyyMMdd=this.selectScheduleInfo.period
            } catch (error) {
                return
            } finally {
                this.loading = false
            }
        },
        async selectScheduleDateList() {
            try {
                const query = {
                    employeeId:this.query.employeeId,
                    psSite:this.query.psSite,
                    psApp:this.query.psApp,
                    baseDate:Utils.formatDate(new Date(),'YYYY-MM-DD')
                }
                const {data}=await  this.http.get(`sys/schedule/selectScheduleDateList`,query)
                this.dispMonthlyList = data
                this.defaultDate=this.dispMonthlyList[0].tda_dyyyymm
                this.query.txtBaseDate=this.dispMonthlyList[0].tda_firstDay
                this.query.txtEndDate=this.dispMonthlyList[0].tda_endDay
                await this.getSelectScheduleInfo()
                this.getHatuRei()
            } catch (error) {
                return
            }
        },
        async handleStartMonth(e) {
            let select=this.dispMonthlyList.find(item=> item.tda_dyyyymm==e )
            this.query.txtBaseDate=select.tda_firstDay
            this.query.txtEndDate=select.tda_endDay

            await this.getSelectScheduleInfo()
        },
        dayRed(e) {
            const dayOfWeek = e.holflgCalendar
            if (!dayOfWeek) {
                return
            }
            if (dayOfWeek.trim() != 'TMG_HOLFLG|0') {
                return 'blue'
            }
            if (e.tda_ccustomerid == this.today) {
                return 'brown'
            }
        },
        async getHatuRei() {
            try {
                let txtBaseDate= this.yyyyMMdd.split('～')[0].replace(/(.+?)\年(.+?)\月(.+)\日/,"$1/$2/$3")
                const query = {txtBaseDate:txtBaseDate}
                const { data } =  await this.http.get(`sys/schedule/hatuRei`, query)
                this.hatuReiName=data.name
                this.hatuResTimeRange=data.timerange
            } catch (error) {
                return
            }finally {

            }
        },

        handleScroll: Throttle(function(e) {
            this.contentScrollTop = e.target.documentElement.scrollTop || e.target.body.scrollTop
        }, 50)
    }
})