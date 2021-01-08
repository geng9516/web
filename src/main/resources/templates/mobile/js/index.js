new Vue({
    el: '.mobile-warp',
    data() {
      return {
        curDate: new Date(),
        hasPassedTimeCheck: false,
        monthlyLoading: false,
        dailyloading: false,
        selectDisabled: false,   　//年月選択可否制御
        loading: false,
        updateDailyLoading: false,
        dispMonthlyList: [],       //年月リスト
        query: {
          txtAction: 'ACT_DISP_RMONTHLY',
          txtDYYYYMM: '',
          txtDYYYYMMDD: '',
          today: '',
          selHealthStatus: '',
          psSite: Utils.getUrlParam(location.href, 'psSite') || 'TMG_INP',
          psApp: Utils.getUrlParam(location.href, 'psApp') || 'TmgResults'
        },
        model1:'',
        columns: [
          {
            title: '曜日',
            slot: 'week',
            width: 45,
            align: 'center'
          },
          {
            title: '日',
            slot: 'intDay',
            minwidth: 80,
            align: 'center'
          },
          {
            title: '区分',
            slot: 'TDA_CWORKINGID_R_NAME',
            minwidth: 80,
            align: 'center'
          },
          {
            title: '承認',
            slot: 'workConfirm',
            width: 45,
            align: 'center'
          },
          {
            title: ' ',
            slot: 'action',
            align: 'center'
          }
        ],
        // data目前用的线上某一份
        tableData:[],
        title: '',
        errorFlag: false,
        errorMsg: '',
        isShow: false,
        workingId: '',
        today: '',
        workingIdList: [],
        businessTrip: '',
        businessTripList: [],
        categoryNonduty: '',
        categoryNondutyName: '',
        categoryNondutyList: [],
        categoryOverhours: '',
        categoryOverhoursName: '',
        categoryOverhoursList: [],
        workTimeDisable: false,
        columnsWork: [
          {
            title: '勤務時間',
            align: 'center',
            key: 'workTime',
            //width:'30'
          },
          {
            title: '始業',
            slot: 'workStart',
            align: 'center',
            //width:'35'
          },
          {
            title: '終業',
            slot: 'workEnd',
            align: 'center',
            //width:'35'
          }
        ],
        columnsNotWork: [
          {
            title: '内容',
            align: 'center',
            width: '100',
            key: 'itemName'
          },
          {
            title: '開始',
            slot: 'tdadNopen',
            align: 'center'
          },
          {
            title: '終了',
            slot: 'tdadNclose',
            align: 'center'
          },
          {
            title: '削除',
            slot: 'tdadNdeleteflg',
            width: '60',
            align: 'center'
          }
        ],
        detailNonDutyVOList: [],
        columnsOverWork: [
          {
            title: '内容',
            align: 'center',
            width: '100',
            key: 'itemName'
          },
          {
            title: '開始',
            slot: 'tdadNopen',
            align: 'center'
          },
          {
            title: '終了',
            slot: 'tdadNclose',
            align: 'center'
          },
          {
            title: '削除',
            slot: 'tdadNdeleteflg',
            width: '60',
            align: 'center'
          }
        ],
        detailOverhoursVOList: [], //table 用
        detailOverhoursVOListNormal: [], //请求 用
        dailyEdit: [],
        notWorkType: 1,
        dailyData: {},
        workData0: [],
        mgdCsparechar4VOList: [],
        bHoliday: false,
        bFixed: false,
        isEdiTableResult4Inp: false,
        isCommonDiscretionaryLabor: false,
        isDiscretion: false,//裁量労働制
        bOpen: false,
        bClose: false,
        // 承認者コメントの入力制御
        bApproved: false,

        index: 0,
        // 承認ステータス
        isConfirm: '',
        // 更新履歴の表示制御
        isLogShow: false,
        updateDailyLoading: false,
        // 就業区分切替の表示制御
        bDisable: false,
        overHourLimit: '',
        otDaily: '',
        targetForOverTime: '',
        errMsg: [],
        bDispKinmujokyoKakunin: false,
        bDispKenkojotaiKakunin: false,
        bWorkChkStatus: false,
        bHealthChkStatus: false,
        kinmujokyoEnd: 0,
        workData: [],
        contentScrollTop: 0,
        tmgResultsDto: {
          txtAction: '',
          holiday: false,
          txtTdaNopenR: '',
          txtTdaNcloseR: '',
          workingId: '',
          selMgdCbusinessTrip: '',
          txtDYYYYMMDD: '',
          tdaCowncommentR: '',
          nonDutyList: [],
          overHoursList: [],
          hasAuthority: true,
          psSite: 'TMG_INP',
        },
      }
    },
    async mounted() {
      await this.getWorkDateList()
      this.getTitleData()
      window.addEventListener('scroll', this.handleScroll, {passive: true})
    },
    beforeDestroy() {
      window.removeEventListener('scroll', this.handleScroll, { passive: true })
    },
    computed: {
      tableHeadFixed() {
        if (this.contentScrollTop > 118) return true
        else return false
      },
    },
    methods: {
      // 勤務年月一覧取得
      async getWorkDateList() {
        this.query.txtAction = 'ACT_DISP_RMONTHLY'
        try {
          const {data} = await this.http.get('sys/tmgResults/workDateList', this.query)

          this.query.today = data.today
          this.dispMonthlyList = data.monthLy.map(e=> {
            return {
              ...e,
              text: e.val,
              value: e.code
            }
          })
          if (data.monthLy.length > 0) {
            this.model1 = this.dispMonthlyList[0].code
            this.query.txtDYYYYMM = this.model1
            //年月リストを取得する時に、活性化にする
            this.selectDisabled = true
          }
          //年月リスト.lengthがゼロ場合、非活性化にする
          if (data.monthLy.length === 0) {
            this.model1 = ''
            this.selectDisabled = false
          }
        } catch (error) {
          return
        }
      },
      // 年月選択
      async handleStartMonth(e) {
        this.query.txtAction = 'ACT_DISP_RMONTHLY'
        this.query.txtDYYYYMM = e
        this.query.txtDYYYYMMDD = ''
        // 就業実績 --月次・日次実績
        await this.getTitleData()

      },

      // 就業実績のタイトルとデータを取得
      async getTitleData() {
        this.query.txtAction = 'ACT_EDITINP_RDAILY'
        this.tableData = []
        try {
          this.monthlyLoading=true
          const {data,sysDate} = await this.http.get('sys/tmgResults/getTitleData', this.query)
          this.today=sysDate
          this.tableData = data.dailyMapList
        } catch (error) {
          return
        }finally {
          this.monthlyLoading= false
        }
      },

      //一覧から日次実績をクリックする
      async showDay(e) {
        // 業登録画面表示
        this.isShow = true
        this.dailyloading = true
        // 就業登録画面項目クリアする
        this.categoryNonduty = ''
        this.categoryOverhours = ''
        this.dailyEdit = []
        this.workData0 = []
        this.detailNonDutyVOList = []
        this.detailOverhoursVOList = []
        this.detailOverhoursVOListNormal = []
        this.title = '  '
        this.query.txtDYYYYMMDD = e
        this.query.txtAction = 'ACT_EDITINP_RDAILY'
        this.bFixed = false
        this.bHoliday = false
        // 業登録画面データ取得
        await this.dailyDetail()
        this.dailyloading = false
      },

      // 就業登録画面情報取得・設定する
      async dailyDetail() {
        if(this.$refs.select2){
          this.$refs.select2.clearSingleSelect()
        }
        if(this.$refs.select3){
          this.$refs.select3.clearSingleSelect()
        }
        if(this.$refs.select4){
          this.$refs.select4.clearSingleSelect()
        }
        // 画面情報取得
        const { data } = await this.http.get('sys/tmgResults/dailyDetail', this.query)

        // 出張ドロップダウン
        this.businessTripList = data.businessTripList.map(e=> {
          return {
            ...e,
            text: e.mgdCgenericdetaildesc,
            value:e.mgdCmastercode
          }
        })

        // 非勤務ドロップダウン
        this.categoryNondutyList = data.categoryNondutyList.map(e=> {
          return {
            ...e,
            text: e.itemName,
            value:e.itemCode
          }
        })
        if(this.categoryNondutyList.length>0){
          this.categoryNonduty = this.categoryNondutyList[0].itemCode
        }

        // 超過勤務ドロップダウン
        this.categoryOverhoursList = data.categoryOverhoursList.map(e=> {
          return {
            ...e,
            text: e.itemName,
            value:e.itemCode
          }
        })
        if(this.categoryNondutyList.length>0){
          this.categoryOverhours = this.categoryOverhoursList[0].itemCode
        }
        this.mgdCsparechar4VOList = data.mgdCsparechar4VOList

        // 詳細:欠勤離籍以外
        this.workData0 = data.dailyDetail0List

        // 就業実績
        this.dailyEdit = data.dailyEditVO

        if (('TMG_DATASTATUS|9' === this.dailyEdit.tdaCstatusflg || 'TMG_DATASTATUS|5' === this.dailyEdit.tdaCstatusflg)
          && '' !== this.dailyEdit.tdaCmodifieruseridR
          && '' !== this.dailyEdit.tdaDmodifieddateR
        ) {
          this.bApproved = true
        } else {
          this.bApproved = false
        }

        // 登録ボタンとコメントのみ登録ボタンの表示制御
        this.bFixed = !data.isEditable
        if (data.workIdList.length < 2) {
          this.bHoliday = true
        }

        //就業入力サイトでの就業実績編集機能を使用するか判定し値を返却します
        this.isEdiTableResult4Inp = data.isEdiTableResult4Inp

        this.isCommonDiscretionaryLabor = data.isCommonDiscretionaryLabor
        this.isDiscretion = data.isDiscretion

        if (this.dailyEdit.tdaNopenR !== this.dailyEdit.tdaNopenTp) {
          this.bOpen = true
        } else {
          this.bOpen = false
        }
        if (this.dailyEdit.tdaNcloseR !== this.dailyEdit.tdaNcloseTp) {
          this.bClose = true
        } else {
          this.bClose = false
        }

        if (this.isEdiTableResult4Inp) {
          this.dailyEdit.tdaNcloseR = this.dailyEdit.tdaNopenRHidden
          this.dailyEdit.tdaNcloseR = this.dailyEdit.tdaNcloseRHidden
        }

        if (this.dailyEdit.tdaNopenTp === null) {
          this.dailyEdit.tdaNopenTp = '---'
        }
        if (this.dailyEdit.tdaNcloseTp === null) {
          this.dailyEdit.tdaNcloseTp = '---'
        }
        if (this.isDiscretion) {
          this.dailyEdit.tdaNopenN = '---'
          this.dailyEdit.tdaNcloseN = '---'
        }

        this.workingId = this.dailyEdit.tdaCworkingidR
        this.businessTrip = this.dailyEdit.tdaCbusinesstripidR
        this.title = this.dailyEdit.tdaDyyyymmddDy

        if (this.dailyEdit.tdaCerrcode) {

          let tempErrCode = JSON.parse(this.dailyEdit.tdaCerrcode)
          Object.keys(tempErrCode).some(e => {

            if (e === 'ERRTYPE_10') {

              this.$Modal.info({ title: '注意', content: tempErrCode.ERRTYPE_10[0].ERRMSG })
              return true
            }
          })

        }
        this.workData = [
          {
            workTime: '予定時間',
            workStart: this.dailyEdit.tdaNopenN,
            workEnd: this.dailyEdit.tdaNcloseN,
            cellClassName: {
              workTime: 'sub-title'
            }
          },
          {
            workTime: '打刻',
            workStart: this.dailyEdit.tdaNopenTp,
            workEnd: this.dailyEdit.tdaNcloseTp,
            cellClassName: {
              workTime: 'sub-title'
            }
          },
          {
            workTime: '就業時間',
            workStart: this.dailyEdit.tdaNopenR,
            workEnd: this.dailyEdit.tdaNcloseR,
            isInput: true,
            cellClassName: {
              workTime: 'sub-title'
            }
          }]
        this.workData0.forEach(e => {
          this.workData = this.workData.concat(
            {
              workTime: e.tdadCnotworkName,
              workStart: e.tdadNopenHhmi,
              workEnd: e.tdadNcloseHhmi,
              cellClassName: {
                workTime: 'sub-title'
              }
            }
          )
        })
        this.workData = this.workData.concat([
          {
            workTime: '本人コメント',
            isOwnComment: true,
            cellClassName: {
              workTime: 'like-title'
            }
          },
          {
            workTime: '承認者コメント',
            isBSComment: true,
            cellClassName: {
              workTime: 'like-title'
            }
          }])
        // 非勤務
        this.detailNonDutyVOList = data.detailNonDutyVOList
        // 超過勤務

        this.detailOverhoursVOListNormal = data.detailOverhoursVOList
        console.log(this.detailOverhoursVOListNormal)
        this.detailOverhoursVOList = this.handleOvertime(this.detailOverhoursVOListNormal,0)
        console.log(this.detailOverhoursVOList)

        //
        let limitOfBasedateVO = data.limitOfBasedateVO
        this.overHourLimit = limitOfBasedateVO.dailyConvMinute
        this.otDaily = limitOfBasedateVO.otDaily
        this.targetForOverTime = data.targetForOverTime
        this.changeWorkingID(this.workingId)
      },

      //超勤数据处理
      handleOvertime(data,flag){
        console.log(data)
        let result=[]
        if(flag===0){
          //请求用到table用
          data.forEach(e => {
            result = result.concat(
                            {
                              attributeUrl: e.attributeUrl,
                              itemName: e.itemName,
                              tdadCcode01: e.tdadCcode01,
                              tdadCcode01Name: e.tdadCcode01Name,
                              tdadCnotworkid: e.tdadCnotworkid,
                              tdadCsparechar1: e.tdadCsparechar1,
                              tdadCsparechar2: e.tdadCsparechar2,
                              tdadCsparechar2Name: e.tdadCsparechar2Name,
                              tdadNclose: e.tdadNclose,
                              tdadNdeleteflg: e.tdadNdeleteflg,
                              tdadNopen: e.tdadNopen,
                              tdadNsparenum1: e.tdadNsparenum1,
                              tdadSeq: e.tdadSeq,
                            },
                            {
                              itemName: '用務内容',
                              tdadNopen: e.tdadCsparechar1,
                              tdadSeq:e.tdadSeq
                            }
                    )
          }
          )
        }else{
          //table用到请求用
          let num=0
          let comment=''
          data.forEach(e => {
                    if(num%2===0){
                      result = result.concat(
                              {
                                attributeUrl: e.attributeUrl,
                                itemName: e.itemName,
                                tdadCcode01: e.tdadCcode01,
                                tdadCcode01Name: e.tdadCcode01Name,
                                tdadCnotworkid: e.tdadCnotworkid,
                                tdadCsparechar1: e.tdadCsparechar1,
                                tdadCsparechar2: e.tdadCsparechar2,
                                tdadCsparechar2Name: e.tdadCsparechar2Name,
                                tdadNclose: e.tdadNclose,
                                tdadNdeleteflg: e.tdadNdeleteflg,
                                tdadNopen: e.tdadNopen,
                                tdadNsparenum1: e.tdadNsparenum1,
                                tdadSeq: e.tdadSeq,
                              },
                      )
                      comment=''
                    }else{
                      result[parseInt(num/2)].tdadCsparechar1=e.tdadNopen
                    }
                    num=num+1
                  }
          )
        }
        return result
      },



      handleSpan ({ row, column, rowIndex, columnIndex }) {
        if (rowIndex%2 != 0) {
          if(columnIndex === 1){
            return [1, 3];
          }else if (columnIndex >2) {
            return [0, 0]
          }
        }
      },

      //コメントのみ登録
      updateInp: Debounce(function () {
        this.tmgResultsDto.txtAction = 'ACT_EDITINP_UCOMMENT'
        this.tmgResultsDto.holiday = this.bHoliday
        this.tmgResultsDto.workingId = this.workingId
        this.tmgResultsDto.txtDYYYYMMDD = this.query.txtDYYYYMMDD
        this.tmgResultsDto.selMgdCbusinessTrip = this.businessTrip
        this.tmgResultsDto.txtTdaNopenR = this.dailyEdit.tdaNopenR
        this.tmgResultsDto.txtTdaNcloseR = this.dailyEdit.tdaNcloseR
        this.tmgResultsDto.tdaCowncommentR = this.dailyEdit.tdaCowncommentR

        if (this.tmgResultsDto.tdaCowncommentR !== null && this.getLengthB(this.tmgResultsDto.tdaCowncommentR) > 100) {
          this.$Notice.error({ desc: "中断備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。" })
          return false
        }
        this.http.post('sys/tmgResults/updateInp', this.tmgResultsDto)
        this.isShow = false
      }),
      //登録
      async updateDaily() {
        const [check, msg] = this.errorCheck(this.workingId)
        if (!check) {
          return
        }
        this.updateDailyLoading = true
        console.log(1)
        this.tmgResultsDto.txtDYYYYMMDD = this.query.txtDYYYYMMDD
        this.tmgResultsDto.txtAction = 'ACT_EDITINP_UDAILY'

        this.tmgResultsDto.workingId = this.workingId
        this.tmgResultsDto.selMgdCbusinessTrip = this.businessTrip
        this.tmgResultsDto.holiday = null
        this.tmgResultsDto.txtTdaNopenR = ''
        this.tmgResultsDto.txtTdaNcloseR = ''
        this.tmgResultsDto.tdaCowncommentR = ''
        this.tmgResultsDto.hasAuthority = ''
        this.tmgResultsDto.holiday = this.bHoliday
        this.tmgResultsDto.tdaCowncommentR = this.dailyEdit.tdaCowncommentR
        if (!this.bDisable) {
          if (!this.workData[2].workStart ) {
            this.tmgResultsDto.txtTdaNopenR = this.workData[0].workStart
          }else{
            this.tmgResultsDto.txtTdaNopenR = this.workData[2].workStart
          }

          if (!this.workData[2].workEnd ) {
            this.tmgResultsDto.txtTdaNcloseR = this.workData[0].workEnd
          }else{
            this.tmgResultsDto.txtTdaNcloseR = this.workData[2].workEnd
          }
          this.tmgResultsDto.nonDutyList = this.detailNonDutyVOList
          this.tmgResultsDto.overHoursList =this.detailOverhoursVOListNormal
          if (this.bFixed || this.bHoliday) {
            this.tmgResultsDto.hasAuthority = false
          } else {
            this.tmgResultsDto.hasAuthority = true
          }
        }
        this.$Modal.confirm({
          inDrawer: true,
          title: '注意',
          content: msg || 'この内容で登録します。よろしいですか？',
          width: 450,
          okText: 'OK',
          cancelText: 'キャンセル',
          onOk: async () => {
            try {
              this.updateDailyLoading = true
              const { msg } = await this.http.post('sys/tmgResults/updateDaily', this.tmgResultsDto)
              if (msg === '0') {
                this.updateDailyLoading = false
                this.isShow = false
                this.getTitleData()
                return
              }
              this.errMsg = JSON.parse(msg)
              let errMsg20
              let check = Object.keys(this.errMsg).some(e => {
                if (e === 'ERRTYPE_10') {
                  this.$Notice.error({ desc: this.errMsg.ERRTYPE_10[0].ERRMSG })
                  return true
                }
                if (e === 'ERRTYPE_20') {
                  this.errMsg.ERRTYPE_20.forEach(e20 => {
                    errMsg20 = errMsg20 + e20.ERRMSG + '/n'
                  })
                  this.$Notice.error({ desc: errMsg20 })
                }

              })
              if (check) {
                return false
              }
            } catch (error) {
              return
            } finally {
              this.updateDailyLoading = false
            }
          }
        })
      },
      // 就業区分の切り替え表示制御
      changeWorkingID(workingid) {
        if (!workingid) {
          return
        }
        this.bDisable = false
        // 休暇
        if (this.bHoliday) {
          this.bDisable = true
          this.workTimeDisable = true
          // 休暇以外
        } else {
          let mgdCsparechar4VO = this.mgdCsparechar4VOList.find(e => e.mgdCmastercode === workingid)
          // 出勤 or 休日出勤
          if (mgdCsparechar4VO.mgdCsparechar5 == '0') {
            this.bDisable = false
            this.workTimeDisable = false
            // 欠勤
          } else {
            this.bDisable = true;
            this.workTimeDisable = true
          }
        }
        if (this.bDisable === true) {
          this.workData[2].workStart = ''
          this.workData[2].workEnd = ''
          this.detailNonDutyVOList = []
          this.detailOverhoursVOList = []
          this.detailOverhoursVOListNormal = []
        }
      },
      time_check(sTime) {
        return sTime.match(/^(([0-3]?[0-9])|([4][0-7])):([0-5]?[0-9])$/);
      },
      // エラーチェック
      errorCheck(workingid) {
        let mgdCsparechar4VO = this.mgdCsparechar4VOList.find(e => e.mgdCmastercode === workingid)
        // 出勤 or 休日出勤
        if (mgdCsparechar4VO.mgdCsparechar5 != '0') {
          return [true];
        }
        // 修正出社時刻形式
        if (this.workData[2].workStart && !this.time_check(this.workData[2].workStart)) {
          this.$Notice.error({desc: "就業時間・始業は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"})
          return [false];
        }

        // 修正退社時刻形式

        if (this.workData[2].workEnd && !this.time_check(this.workData[2].workEnd)) {
          this.$Notice.error({desc: "就業時間・終業は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"})
          return [false];
        }

        if (this.dailyEdit.tdaCowncommentR !== null && this.getLengthB(this.dailyEdit.tdaCowncommentR) > 100) {
          this.$Notice.error({desc: "本人コメント考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"})
          return [false];
        }

        //非勤務
        const checkNonDuty = this.detailNonDutyVOList.some(e => {

          if (e.tdadNopen ===null ||  !this.time_check(e.tdadNopen)) {
            this.$Notice.error({desc: "非勤務の開始時刻は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。時間の範囲は48:00以内で指定してください。"})
            return true;
          }
          if (e.tdadNclose ===null || !this.time_check(e.tdadNclose)) {
            this.$Notice.error({desc: "非勤務の終了時刻は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"})
            return true;
          }
          if (e.tdadCsparechar1 !== null && this.getLengthB(e.tdadCsparechar1) > 100) {
            this.$Notice.error({desc: "中断備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"})
            return true
          }
        })
        if (checkNonDuty) return [false]
        this.detailOverhoursVOListNormal= this.handleOvertime(this.detailOverhoursVOList,1)
        // 超過勤務
        const checkOverhour = this.detailOverhoursVOListNormal.some(e => {

          if (e.tdadCsparechar1 !== null && e.tdadCsparechar1.length > 100) {
            this.$Notice.error({desc: "超過勤務備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"})
            return true
          }
        })
        if (checkOverhour) return [false]
        let nOverTimeTotal = 0;
        // 超過勤務命令の明細部配下を行単位で取得
        this.detailOverhoursVOListNormal.forEach(e => {
          // 超過勤務命令の時間数を算出
          let sOpen = this.toMinuteFromHHcMI60(e.tdadNopen);
          let sClose = this.toMinuteFromHHcMI60(e.tdadNclose);
          nOverTimeTotal = nOverTimeTotal + (sClose - sOpen);
        })
        // 超過勤務命令時間が入力されていて、超過勤務対象「無」の場合は警告メッセージを表示する
        if (nOverTimeTotal > 0 && this.targetForOverTime === "1") {
          // 警告メッセージの表示および、応答の確認
          return [true, '超過勤務対象外として設定されています。\n超過勤務命令を登録してもよろしいですか？']
        }
        // 超過勤務命令時間数合計が登録された超勤限度時間を超える場合は警告メッセージを表示する
        if (nOverTimeTotal > this.overHourLimit) {
          // 警告メッセージの表示および、応答の確認
          return [true, '超過勤務命令の時間数が' +this.otDaily +'時間を超えています。\nこの超過勤務時間で登録してよろしいですか？']
        }
        return [true];
      },
      toMinuteFromHHcMI60(pHHMM) {
        // 何もパラメータが渡されなかった場合、そのまま返す
        if (pHHMM === '') {
          return '';
        }
        let retMinute;
        let nHour = new Number();
        let nMinute = new Number();
        // 分に変換します
        nHour = eval(+(pHHMM.split(":")[0] * 60));
        nMinute = eval(+(pHHMM.split(":")[1]));
        retMinute = eval(nHour + nMinute);
        return retMinute;
      },
      getLengthB(moji) {
        let i, j, cnt = 0;
        let multiStr = new Array("×", "§", "¨", "°", "±", "´", "¶");
        for (i = 0; i < moji.length; i++) {
          for (j = 0; j < multiStr.length; j++) {
            if (multiStr[j].length == 0) {
              continue;
            }
            moji = moji.replace(multiStr[j], "00");
          }
        }
        // MAC OSの場合、改行コードが「\n」となり、Windowsの改行コード「\r\n」とバイト数が異なるので、
        moji = moji.split("\r\n").join("\n"); // 一旦「\r\n」 ⇒ 「\n」の変換を行い、改行コードを「\n」に合わせる。
        // バイト数を求めます。
        for (i = 0; i < moji.length; i++)
          if (escape(moji.charAt(i)).length >= 4) cnt += 2; else cnt++;
        return cnt;
      },

      addOverWork() {
        let detailOverhoursVO = this.categoryOverhoursList.find(e => e.itemCode === this.categoryOverhours)

        this.detailOverhoursVOList.push({
          attributeUrl: detailOverhoursVO.attributeUrl,
          tdadCnotworkid: detailOverhoursVO.itemCode,
          itemName: detailOverhoursVO.itemName,
          tdadNopen: '',
          tdadNclose: '',
          tdadNdeleteflg: '0',
          tdadSeq: Date.now(),
          tdadNsparenum1: '',
          tdadCcode01: '',
          tdadCcode01Name: '',
          tdadCsparechar2: '',
          tdadCsparechar2Name: ''
        },{
          itemName: '用務内容',
          tdadNopen: '',
          tdadSeq: Date.now(),
        })
      },
      addNotWork() {
        let categoryNondutyVO = this.categoryNondutyList.find(e => e.itemCode === this.categoryNonduty)
        this.detailNonDutyVOList.push({
          attributeUrl: categoryNondutyVO.attributeUrl,
          tdadCnotworkid: categoryNondutyVO.itemCode,
          itemName: categoryNondutyVO.itemName,
          tdadNdeleteflg: "0",
          tdadNopen: '',
          tdadNclose: '',
          tdadSeq: Date.now(),
          tdadCsparechar1: `中断時間登録（携帯端末　${Utils.formatDate(Date.now(),'YYYY-MM-DD hh:ff:ss')}）`,
          tdadNsparenum1: null
        })

      },

      delNotWork(row) {
        this.detailNonDutyVOList = this.detailNonDutyVOList.filter(e => e.tdadSeq !== row.tdadSeq)
      },
      delOverWork(row) {
        this.detailOverhoursVOList = this.detailOverhoursVOList.filter(e => e.tdadSeq !== row.tdadSeq)
      },

      dayRed(e, i) {
        const dayOfWeek = e.TMG_HOLFLG
        if (i === 0) {
          if (e.TDA_DYYYYMMDD === this.today) {
            return 'brown cursor'
          }

          if (dayOfWeek.trim() === 'TMG_HOLFLG|0') {
            return 'cursor'
          }
          if (dayOfWeek.trim() != 'TMG_HOLFLG|0') {
            return 'blue cursor'
          }

        } else {
          if (e.TDA_DYYYYMMDD === this.today) {
            return 'brown'
          }
          if (dayOfWeek.trim() === 'TMG_HOLFLG|0') {
            return
          }
          if (dayOfWeek.trim() != 'TMG_HOLFLG|0') {
            return 'blue'
          }

        }
      },
      handleInputChange(i, object, value, el) {
        if (object) {
          this.hasPassedTimeCheck = Utils.checkTime(object, value, el)
        }
      },
      handleClose() {
        this.errorFlag = false
        this.isShow = false
      },
      handleScroll: Throttle(function (e) {
        this.contentScrollTop = e.target.documentElement.scrollTop || e.target.body.scrollTop
      }, 50),
      handleSpan4time({ rowIndex, columnIndex }) {
        const len = this.workData.length
        if (rowIndex === len - 2 && columnIndex === 1) {
          return [1, 2]
        } else if (rowIndex === len - 1 && columnIndex === 1) {
          return [1, 2]
        }
      }
    }
  })