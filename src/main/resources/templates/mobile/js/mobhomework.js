new Vue({
  el: '.oconfirm-warp',
  data() {
    return {
      hwApplyShow: false,
      hwhomeworkShow: false,
      StatusList0: [
        {
          value: '1',
          text: '承認待',
        }
      ],
      StatusList: [
        {
          value: '1',
          text: '承認待',
        },
        {
          value: '3',
          text: '取下'
        }
      ],
      StatusList1: [
        {
          value: '2',
          text: '承認済'
        }
      ],
      StatusList2: [
        {
          value: '1',
          text: '承認待',
        },
        {
          value: '3',
          text: '取下'
        }
      ],
      StatusList3: [
        {
          value: '1',
          text: '承認待',
        },
        {
          value: '3',
          text: '取下'
        },
        {
          value: '4',
          text: '差戻'
        }
      ],
      homeworkList: [
        {
          value: '1',
          text: '終日'
        },
        {
          value: '2',
          text: '午前'
        },
        {
          value: '3',
          text: '午後'
        },
        {
          value: '4',
          text: '時間'
        }
      ],
      defaultDate: '', //年月选择（Date）
      dispMonthlyList: [],       //年月リスト
      HomeWorkVOList:[],
      HomeWorkVOListEditBack:[],
      HomeWorkVOUpdateList:[],
      HomeWorkVOUpdateListBack:[],
      yyyyMMdd:'',
      hwcworkingid:'',
      loading: false,　       //ローディングアクション
      hasPassedTimeCheck: false,
      contentScrollTop: 0,
      query: {
        employeeId: LOGIN_EMPOLYEE,　 //組織ツリーで選択した職員番号
        psSite: Utils.getUrlParam(location.href, 'psSite'),
        psApp: Utils.getUrlParam(location.href,'psApp'),
        txtBaseDate:'',
        txtEndDate:'',
        baseDate:''
      }
    }
  },
  async mounted() {
    this.selectScheduleDateList()
  },
  computed: {
    columns() {
      return [
        {
          title: '日',
          minWidth: 60,
          slot: 'tdaDd',
          align: 'center'
        },
        {
          title: '区分',
          minWidth: 60,
          slot: 'hwCworkingid',
          align: 'center'
        },
        {
          title: '状態',
          minWidth: 59,
          slot: 'hwStatus',
          align: 'center'
        },
        {
          title: '在宅\n勤務',
          minWidth: 55,
          slot: 'hwHomework',
          align: 'center'
        },
        {
          title: '時間\n詳細',
          minWidth: 55,
          slot: 'time',
          align: 'center'
        },
        {
          title: ' ',
          minWidth: 60,
          slot: 'apply',
          align: 'center'
        }
      ]
    },
    columns1() {
      return [
        {
          title: '状態',
          minWidth: 80,
          slot: 'hwstatus',
          align: 'center'
        },
        {
          title: '在宅勤務',
          minWidth: 80,
          slot: 'hwhomework',
          align: 'center'
        },
        {
          title: '通勤対象外',
          minWidth: 50,
          slot: 'hwCommute',
          align: 'center'
        },
      ]
    },
    columns2() {
      return [
        {
          title: '開始',
          minWidth: 90,
          slot: 'hwStart',
          align: 'center'
        },
        {
          title: '終了',
          minWidth: 90,
          slot: 'hwEnd',
          align: 'center'
        }
      ]
    },
    columns3() {
      return [
        {
          title: '申請コメント',
          minWidth: 200,
          slot: 'hwApplicationcomment'
        }
      ]
    },
    columns5() {
      return [
        {
          title: '承認コメント',
          minWidth: 200,
          slot: 'hwApprovalcomment',
          align: 'left'
        }
      ]
    },
    columns4() {
      return [
        {
          title: '申請日',
          minWidth: 50,
          slot: 'yymmdd'
        },
        {
          title: '区分',
          minWidth: 50,
          slot: 'hwcworkingid',
          align: 'center'
        }
      ]
    }
  },
  methods: {
    async getHomeWorkInfo() {
      this.loading=true
      try {
        const {data} = await this.http.get(`sys/homeWork/selectHomeWorkInfo`,this.query)
        this.HomeWorkVOList=data
        // this.HomeWorkVOListEditBack = {...data}
        this.HomeWorkVOListEditBack = JSON.parse(JSON.stringify(data));
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
        const {data}= await  this.http.get(`sys/schedule/selectScheduleDateList`,query)
        this.dispMonthlyList= data.map(e => {
          return {
            ...e,
            text:e.tda_dyyyymm,
            value:e.tda_dyyyymm
          }
        })
        this.defaultDate=this.dispMonthlyList[1].tda_dyyyymm
        this.query.txtBaseDate=this.dispMonthlyList[1].tda_firstDay
        await this.getHomeWorkInfo()
        this.getHatuRei()
      } catch (error) {
        return
      }
    },
    dayRed(e) {
      const dayOfWeek = e.mgdCsparechar
      if (!dayOfWeek) {
        return
      }
      if (dayOfWeek != '0') {
        return 'blue'
      }
    },
    async getHatuRei() {
      try {
        let txtBaseDate= this.yyyyMMdd.split('～')[0].replace(/(.+?)\年(.+?)\月(.+)\日/,"$1/$2/$3")
      } catch (error) {
        return
      }finally {

      }
    },
    handleInputChange(i,object,value,el) {
      // 填完后自动checkbox
      if(object) {
        this.hasPassedTimeCheck = Utils.checkTime(object, value,el)
      }
    },
    handleClose() {
      this.getHomeWorkInfo()
      this.hwApplyShow = false
    },
    time_check(sTime) {
      return sTime.match(/^(([0-3]?[0-9])|([4][0-7])):([0-5]?[0-9])$/);
    },
    editBack(i) {
      this.HomeWorkVOUpdateList[i].hwhomework = this.HomeWorkVOUpdateListBack.hwhomework
      this.HomeWorkVOUpdateList[i].hwStart = this.HomeWorkVOUpdateListBack.hwStart
      this.HomeWorkVOUpdateList[i].hwEnd = this.HomeWorkVOUpdateListBack.hwEnd
      this.HomeWorkVOUpdateList[i].hwCommute = this.HomeWorkVOUpdateListBack.hwCommute
    },
    editTime(i){
      if(this.HomeWorkVOUpdateList[i].hwhomework != '4'){
        this.HomeWorkVOUpdateList[i].hwStart = null
        this.HomeWorkVOUpdateList[i].hwEnd = null
        this.hwhomeworkShow=false
      }else{
        this.hwhomeworkShow=true
      }
    },
    //登陆处理
    async updateHomeWork() {
      let checkFlg=false
      for (let i = 0; i < this.HomeWorkVOUpdateList.length; i++) {
        if (this.HomeWorkVOUpdateList[i].hwstatus != null  && this.HomeWorkVOUpdateList[i].hwCommute == null){
          this.HomeWorkVOUpdateList[i].hwCommute="0";
        }
        if (this.HomeWorkVOUpdateList[i].hwstatus == null  && this.HomeWorkVOUpdateList[i].hwApplicationcomment != null){
          this.HomeWorkVOUpdateList[i].hwstatus="0";
        }
        let msday=this.HomeWorkVOUpdateList[i].hwApplicationdate + " : ";
        if (this.HomeWorkVOUpdateList[i].hwstatus === '1') {
          if( this.HomeWorkVOUpdateList[i].hwhomework === null){
            this.$Notice.error({desc: msday + "在宅勤務を入力してください。"})
            return checkFlg=true;
          }
        }
        if(this.HomeWorkVOUpdateList[i].hwhomework === '4' ){
          if (this.HomeWorkVOUpdateList[i].hwStart ==null ||  !this.time_check(this.HomeWorkVOUpdateList[i].hwStart)) {
            this.$Notice.error({desc: msday + "開始時間は適切な時刻ではありません。(hh:mm)形式で時刻を入力及び48:00以内の時刻で指定してください。"})
            return checkFlg=true;
          }
          if (this.HomeWorkVOUpdateList[i].hwEnd ==null ||  !this.time_check(this.HomeWorkVOUpdateList[i].hwEnd)) {
            this.$Notice.error({desc: msday + "終了時間は適切な時刻ではありません。(hh:mm)形式で時刻を入力及び48:00以内の時刻で指定してください。"})
            return checkFlg=true;
          }
          if(this.HomeWorkVOUpdateList[i].hwStart!='' && this.HomeWorkVOUpdateList[i].hwEnd!=''){
            let count=Utils.timeToMinute(this.HomeWorkVOUpdateList[i].hwEnd)-Utils.timeToMinute(this.HomeWorkVOUpdateList[i].hwStart)
            if(0>=count){
              this.$Notice.error({desc: msday + "開始時間は終了時間の前になるようにしてください。"})
              return checkFlg=true;
            }
          }
        }
      }
      console.log(this.HomeWorkVOUpdateList)
      if(checkFlg){
        return
      }
      this.$Modal.confirm({
        title: '注意',
        content: 'この編集内容で登録します。よろしいですか？',
        okText: 'OK',
        width: 480,
        cancelText: 'キャンセル',
        onOk: async () => {
          for (let y = 0; y < this.HomeWorkVOUpdateList.length; y++) {
            if(this.HomeWorkVOUpdateList[y].hwhomework === '4' ){
              this.HomeWorkVOUpdateList[y].hwStart = Utils.timeToMinute(this.HomeWorkVOUpdateList[y].hwStart);
              this.HomeWorkVOUpdateList[y].hwEnd = Utils.timeToMinute(this.HomeWorkVOUpdateList[y].hwEnd);
            }
          }
          let query={ homeWorkAdminVO:this.HomeWorkVOUpdateList }
          try{
            await this.http.post(`sys/homeWork/updateHmoeWorkMob`,query)
          }catch (e) {
            this.$Notice.warning({ title: '注意', desc: e, duration: 6.5 })
          }finally {
            this.getHomeWorkInfo()
            this.hwApplyShow = false
          }
        },
        onCancel: () => {
        }
      })
    },
    hwApplyShowFlg(e,e1){
      this.hwApplyShow = !e
      this.updateDay= Utils.formatDate(e,'YYYY年MM月DD日')
      this.query.baseDate= e1
      this.hwApplyListShow = true
      this.getHomeWorkListInfo()
    },
    async getHomeWorkListInfo() {
      try {
        const {data} = await this.http.get(`sys/homeWork/selectAdminHomeWorkmob`,this.query)
        this.HomeWorkVOUpdateList = [data]
        this.hwcworkingid = this.HomeWorkVOUpdateList[0].hwcworkingid
        if(this.HomeWorkVOUpdateList[0].hwhomework === '4'){
          this.hwhomeworkShow=true
        }
        this.HomeWorkVOUpdateListBack = JSON.parse(JSON.stringify(data));
      } catch (error) {
        return
      } finally {
      }
    },
    // 年月選択
    async handleStartMonth(e) {
      if(e){
        this.query.baseDate = this.dateFormat(e)
        this.getHomeWorkInfo()
      }

    },
    dateFormat(e) {
      //YYYY年MM月からYYYY/MM/01へ変換する
      let yearMMDD = e.replace("年","/")
      yearMMDD = yearMMDD.replace("月","/")
      yearMMDD = yearMMDD+"01"
      return  yearMMDD
    }
  }
})