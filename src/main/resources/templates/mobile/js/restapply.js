new Vue({
   el: '.restApply-warp',
   data() {
      return {
         hasPassedTimeCheck: true,
         week: [
            { name: '月', value: 1 },
            { name: '火', value: 2 },
            { name: '水', value: 3 },
            { name: '木', value: 4 },
            { name: '金', value: 5 },
            { name: '土', value: 6 },
            { name: '日', value: 7 }
         ],
         curYear: new Date().getFullYear(),
         restInfoList: [],
         statusList: [],
         timerangeType: 1,
         loading: false,
         disableReapply: false,
         moreDataLoading: false,
         rotate: false,
         restInfoLoading: false,
         listLoading: false,
         applyHistoryListData: [],
         opts: {
            ntfTypeId: '',
            statusFlg: 0,
            year: 2020,
            psSite: Utils.getUrlParam(location.href, 'psSite'),
            page: 1,
         },
         restApply: {
            useVacation: false,
            weekSelected: [],
            noreserved: 1,
            dateList: '',
            dateListRange:['', ''],
            txtAddDate:null,
            txtTargetNumber:null
         },
         applyHistoryListAmount: 0,
         restTypeListForReview: [],
         restTypeListForApply: [],
         selectedRestInfo: {},
         uploadFiles: [],
         deleteFiles: [],
         cacelBtnLoading: [],
         isReApplyIng: false
      }
   },
   watch: {
      // 重置曜日选择器
      'restApply.noreserved'(newValue) {
         if (newValue === 1) {
            return (this.restApply.weekSelected = [])
         }
      },
      rotate(newValue) {
         if (newValue) {
            this.restApply.restTypeId = '全て'
            return
         }
         // 浏览历史时，将搜索的置空
         this.opts.ntfTypeId = ''
         if (!this.isReApplyIng && this.$refs.select) {
            return this.$refs.select.clearSingleSelect()
         }
      }
   },
   created() {
      this.getRestTypeForApply()
      this.getRestTypeForReview()
      this.getRestInfo()
      this.getStatusList()
      this.getApplyHistory(true)
   },
   mounted() {
   },
   computed: {
      handleFrontCardClass() {
         let result = 'card'
         if (this.rotate) {
            result = result.concat(' rotate-180')
         }
         const name = this.selectedRestInfo.ntfName
         if (name && name.length > 25) {
            result = result.concat(' full-name')
         }
         return result
      },
      curRestTypeList() {
         if (this.rotate) return this.restTypeListForReview
         else return this.restTypeListForApply
      }
   },
   methods: {
     showDatePicker(key,key2) {
        if (!this.datePicker) {
           this.datePicker = cube.dPicker.$create({
           title: '日付',
           min: new Date(`${new Date().getFullYear() - 20}/1/1`),
           max: new Date(`${new Date().getFullYear() + 30}/12/31`),
           value: new Date(),
           zIndex: 900,
        })
           this.datePicker.$on('select',
            (e) => {
               if(typeof key2  ===  'number') {
                  this.$set(this.restApply[key], key2, Utils.formatDate(e, 'yyyy/MM/dd'))
               } else {
                 this.$set(this.restApply, key, Utils.formatDate(e, 'yyyy/MM/dd'))
               }
               this.datePicker.remove()
               this.datePicker = null
            })
        }
           this.datePicker.show()
     },
     showYearSelector(key) {
        if (!this.yearSelector) {
           const numArray = Utils.getNumArray(1970, 2100)
           const index = numArray.findIndex(e=> e === this.curYear)
           console.log(index)
           const column1 = Utils.getNumArray(1970, 2100).map(e=> {
              return {
                 value:e,
                 text:e
              }
           })
           this.yearSelector = cube.picker.$create({
           title: '年',
           selectedIndex: [index],
           data: [column1],
           zIndex: 900,
        })
           this.yearSelector.$on('select',
            (e) => {
               this.opts.year = e[0]
               this.curYear = e[0]
               this.getApplyHistory()
               this.yearSelector.remove()
               this.yearSelector = null
            })
        }
           this.yearSelector.show()
     },
      async getRestTypeForApply() {
         // 获得休假选择 申请用
         this.loading = true
         try {
            const { data } = await this.http.get('sys/vapply/NtfTypeList', this.opts)
            // 手机端对应数据, 为了全量显示，这里只摘取第二层级
            let result = []
            data.forEach(e=>{
              result = result.concat(...e.ntfTypeValue.map(t=> {
                 return {
                    ...t,
                    value:t.ntfId,
                    text: t.ntfName
                 }
              }))
            })
            this.restTypeListForApply = result
         } catch (error) {
            return
         } finally {
            this.loading = false
         }
         // 获得历史申请数据
         // this.getApplyHistory()
      },
      async getRestTypeForReview() {
         // 获得休假选择 照会用
         this.loading = true
         try {
            const { data } = await this.http.get('sys/vapply/NtfTypeDispList', this.opts)
            this.restTypeListForReview = data
         } catch (error) {
            return
         } finally {
            this.loading = false
         }
         // 获得历史申请数据
         // this.getApplyHistory()
      },
      // 左边的年休残
      async getRestInfo() {
         this.restInfoLoading = true
         try {
            const { data } = await this.http.get('sys/vapply/RestYearList', this.opts)
            this.restInfoList = data
         } catch (error) {
            return
         } finally {
            this.restInfoLoading = false
         }
      },
      async getStatusList() {
         const { data } = await this.http.get('sys/vapply/StatusFlg', this.opts)
         this.statusList = [{ value:0, text: '全て'}].concat(data.map(e=> {
            return {
              value: e.stutasId,
              text:e.stutasName
            }
         }))
      },
      getApplyHistory: Debounce(async function (isInit) {
         this.listLoading = true
         this.opts.page = 1
         // 获得历史申请数据
         try {
            // OPTS后端发送分离
            let OPTS = { ...this.opts }
            if (this.opts.statusFlg === 0) {
               OPTS.statusFlg = ''
            }
            const { data } = await this.http.get('sys/vapply/NotificationList', OPTS)
            this.applyHistoryListData = data.list
            this.applyHistoryListAmount = data.totalCount
            if (!isInit) this.$Message.success('再表示完了')
         } catch (error) {
            return
         } finally {
            this.loading = false
            this.listLoading = false
         }
      }),
      handleWeekSelect(e) {
      },
      handleRestType(e) {
         if (!e) return
         if (this.rotate) {
            this.opts.ntfTypeId = e === '全て' ? '' : e
            this.getApplyHistory()
            return
         }
         // 选择不同的休假时，判断显示不同的项目, 请空已选内容
         this.restApply = {
            useVacation: false,
            weekSelected: [],
            noreserved: 1,
            dateList: '',
            dateListRange: ['', ''],
            txtAddDate:null,
            txtTargetNumber:null
         }
         this.errorInfo = false
         this.uploadFiles = []
         this.hasPassedTimeCheck = true
         this.selectedRestInfo = {}
         if (e === '全て') {
            this.restApply.restTypeId = '全て'
            return
         }
         this.restApply.restTypeId = e
         this.isReApplyIng = false
         this.selectedRestInfo = this.restTypeListForApply.find(t => t.ntfId === e)
      },
      onRefresh() {
         this.rotate = !this.rotate
      },
      cancel: Debounce(async function (e, i) {
         // 自己取消后无论如何都应该再拉取一遍最新的数据
         this.$set(this.cacelBtnLoading, i, true)
         try {
            await this.http.post('sys/vapply/EditWithdrop', {
               action: 'ACT_EditApply_UWithdraw',
               ntfNo: e.tntfCntfNo,
               psSite: this.opts.psSite
            })
            this.$Message.success('取り消し完了')
         } catch (error) {
            return
         } finally {
            this.$set(this.cacelBtnLoading, i, false)
            this.getApplyHistory()
         }
      }, 700),
      handleHistoryStatusChange(e) {
         this.getApplyHistory()
      },
      apply: Debounce(async function (isRestApply) {
         // 申请完后的4件事： 成功消息，翻转卡片，解除loading，展示数据
         this.restApply.action = 'ACT_MakeApply_CAppl'
         if (isRestApply) {
            this.restApply.action = 'ACT_ReMakeApply_CAppl'
         }
         this.restApply.typeNew = this.selectedRestInfo.ntfId
         // 代理申请用，个人申请此处为空
         this.restApply.finalApprovalLevel = this.selectedRestInfo.finalApprovalLevel5
         this.restApply.weekSelectedHelper = this.restApply.weekSelected.join()
         const week = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun']
         week.forEach((e, i) => {
            if (this.restApply.weekSelectedHelper.indexOf(`${i + 1}`) > -1) this.restApply[e] = '1'
            else this.restApply[e] = '0'
         })
         if (!this.selectedRestInfo.transfer) {
            if (this.timerangeType === 2) {
               this.restApply.begin = this.restApply.dateListRange[0]
               this.restApply.end = this.restApply.dateListRange[1]
            }
            if (this.timerangeType === 1) {
               this.restApply.begin = this.restApply.dateList
               this.restApply.end = this.restApply.dateList
            }
         }
         if (!this.restApply.typeNew) {
            return this.$Notice.warning({ title: '注意', desc: '申請区分を選んでください', duration: 6.5 })
         }
         if (this.selectedRestInfo.confirmComment != '0' && !this.restApply.owncomment) {
            return this.$Notice.warning({ title: '注意', desc: '申請事由を入力してください', duration: 6.5 })
         }

         if (!this.restApply.begin || !this.restApply.end) {
            return this.$Notice.warning({ title: '注意', desc: '期間を選んでください', duration: 6.5 })
         }
         if (this.restApply.begin || this.restApply.end) {
            if (this.restApply.begin && !this.restApply.end) {
               return this.$Notice.warning({ title: '注意', desc: '開始期間を入力してください', duration: 6.5 })
            }
            if (!this.restApply.begin && this.restApply.end) {
               return this.$Notice.warning({ title: '注意', desc: '終了期間を入力してください', duration: 6.5 })
            }
            if (!this.selectedRestInfo.transfer && this.restApply.begin && this.restApply.end && Utils.timeToMinute(this.restApply.begin) > Utils.timeToMinute(this.restApply.end)) {
               return this.$Notice.warning({ title: '注意', desc: '対象期間(終了日)は(開始日)より後の日付を入力してください', duration: 6.5 })
            }
         }
         if(this.selectedRestInfo.timeZone) {
            if(!this.restApply.timezoneOpen) {
               return this.$Notice.warning({ title: '注意', desc: '開始期間を入力してください', duration: 6.5 })
            }
            if(!this.restApply.timezoneClose) {
               return this.$Notice.warning({ title: '注意', desc: '終了期間を入力してください', duration: 6.5 })
            }
            if(!this.selectedRestInfo.transfer && this.restApply.timezoneOpen && this.restApply.timezoneOpen && Utils.timeToMinute(this.restApply.timezoneOpen) >= Utils.timeToMinute(this.restApply.timezoneClose)) {
               return this.$Notice.warning({ title: '注意', desc: '対象期間(終了日)は(開始日)より後の日付を入力してください', duration: 6.5 })
            }
         }
         if (!this.hasPassedTimeCheck) {
            return this.$Notice.warning({ title: '注意', desc: '時刻をHH:MM形式で入力してください', duration: 6.5 })
         }
         if(this.selectedRestInfo.workTime) {
            if (!this.restApply.timeOpen) {
               return this.$Notice.warning({ title: '注意', desc: '始業後時刻を入力してください', duration: 6.5 })

            }
            if (!this.restApply.timeClose) {
               return this.$Notice.warning({ title: '注意', desc: '終業前時刻を入力してください', duration: 6.5 })
            }
         }
         if (this.selectedRestInfo.period && !this.restApply.txtPeriod) {
            return this.$Notice.warning({ title: '注意', desc: '起算日を入力してください', duration: 6.5 })
         }
         if (this.selectedRestInfo.name && !this.restApply.txtName) {
            return this.$Notice.warning({ title: '注意', desc: '氏名を入力してください', duration: 6.5 })
         }
         if (this.selectedRestInfo.relation && !this.restApply.txtRelation) {
            return this.$Notice.warning({ title: '注意', desc: '続柄を入力してください', duration: 6.5 })
         }
         if (this.selectedRestInfo.targetNumber && !this.restApply.txtTargetNumber) {
            return this.$Notice.warning({ title: '注意', desc: '対象の人数を入力してください', duration: 6.5 })
         }
         if (this.selectedRestInfo.birthday && !this.restApply.txtBirthday) {
            return this.$Notice.warning({ title: '注意', desc: '生年月日を入力してください', duration: 6.5 })
         }
         if (this.selectedRestInfo.sickName && !this.restApply.txtSickName) {
            return this.$Notice.warning({ title: '注意', desc: '傷病名を入力してください', duration: 6.5 })
         }
         if (this.selectedRestInfo.confirmComment !='0' && !this.restApply.owncomment) {
            return this.$Notice.warning({ title: '注意', desc: '申請事由を入力してください', duration: 6.5 })
         }
         if (this.selectedRestInfo.confirmFile === '1' && !this.uploadFiles.length>0) {
            return this.$Notice.warning({ title: '注意', desc: '添付ファイルを入力してください', duration: 6.5 })
         }
         this.deleteFiles = [...new Set(this.deleteFiles)]
         try {
            this.loading = true
            await this.http.uploadFiles('sys/vapply/MakeApply', {
               params: JSON.stringify(this.restApply),
               uploadFiles: this.uploadFiles.filter(e => !e.isDefault),
               deleteFiles: JSON.stringify(this.deleteFiles),
               psSite: this.opts.psSite
            })
            this.restApply = {
               useVacation: false,
               restTypeId: '全て',
               weekSelected: [],
               noreserved: 1,
               dateList: '',
               dateListRange:['', ''],
               txtAddDate:null,
               txtTargetNumber:null
            }
            this.rotate = !this.rotate
            this.isReApplyIng = false
            await this.getApplyHistory(true)
            await this.getRestInfo()
            this.$Message.success('申請完了')
            this.uploadFiles = []
         } catch (error) {
            if (error.config || error.response && error.response.status === 500) return
            const _error = JSON.parse(error)
            return this.$Modal.error({ title: '注意', content: _error[Object.keys(_error)[0]][0].ERRMSG })
         } finally {
            this.loading = false
            this.deleteFiles = []
         }
      }),
      handleApplyMarkShow(e) {
         // TMG_NTFSTATUS|0	取下
         if (e.indexOf('0') > -1) {
            return 'apply-status'
         }

         // TMG_NTFSTATUS|2	承認待
         if (e.indexOf('2') > -1) {
            return 'apply-status ing'
         }

         // TMG_NTFSTATUS|3	差戻
         if (e.indexOf('3') > -1) {
            return 'apply-status deny'
         }
         // TMG_NTFSTATUS|9	エラー
         if (e.indexOf('9') > -1) {
            return 'apply-status error'
         }

         // TMG_NTFSTATUS|5	承認済
         return 'apply-status ok'
      },
      handleTimelineDotShow(e) {
         // TMG_NTFSTATUS|0	取下
         if (e.indexOf('0') > -1) {
            return 'with-dot cancel'
         }

         // TMG_NTFSTATUS|2	承認待
         if (e.indexOf('2') > -1) {
            return 'with-dot ing'
         }

         // TMG_NTFSTATUS|3	差戻
         // TMG_NTFSTATUS|9	エラー
         if (e.indexOf('3') > -1 || e.indexOf('9') > -1) {
            return 'with-dot deny'
         }

         // TMG_NTFSTATUS|5	承認済
         return 'with-dot'
      },
      handleApplyDayShow(begin, end) {
         if (begin === end) return begin
         else return `${begin} ～ ${end}`
      },
      // 按下背面的再申请按钮
      async handleReApplybtn(item) {

         this.deleteFiles = []
         this.uploadFiles = []
         this.rotate = !this.rotate

         console.log(this.selectedRestInfo.transfer)
         // 时间部分
         console.log(item.remakeApply)
         this.$nextTick(() => {
            this.handleRestType(item.remakeApply)
            this.isReApplyIng = true
            if (!this.selectedRestInfo.transfer) {
               // 是时间不是振休
               // 是范围不是单一时间
               if (item.tntfDbegin !== item.tntfDend) {
                  this.timerangeType = 2
                  this.restApply.dateListRange = [item.tntfDbegin, item.tntfDend]
               } else {
                  this.timerangeType = 1
                  this.restApply.dateList = item.tntfDbegin
               }
            } else {
               // 振休
               console.log(item.tntfDbegin)
               console.log(item.tntfDend)
               this.restApply.begin = item.tntfDbegin
               this.restApply.end = item.tntfDend
            }
         })

         // 申请区分
         this.restApply.restTypeId = item.remakeApply
         this.restApply.ntfNo = item.tntfCntfNo
         this.selectedRestInfo.finalApprovalLevel = item.finalApprovalLevel
         // 文件
         this.uploadFiles = item.tmgNtfAttachedfileDoList.map(e => {
            return {
               name: e.tnafCfilename,
               url: e.tnafFilepath,
               type: e.tnafCfilename,
               isDefault: true
            }
         })

         // comment
         this.restApply.owncomment = item.tntfCowncomment

         // 再拿更多详细
         const { data } = await this.http.get('sys/vapply/NotificationDetail', { ntfNo: item.tntfCntfNo })

         this.restApply.ntfNo=item.tntfCntfNo
         // 起算日
         this.restApply.txtPeriod = data.tntfDperiodDate

         // 加算日
         this.restApply.txtAddDate = data.tntfNuapperAddition

         // 時間帯
         this.$set(this.resetReApply, 'timezoneOpen', data.tntfNtimezoneOpenHhmi)
         this.$set(this.resetReApply, 'timezoneClose', data.tntfNtimezoneCloseHhmi)

         // 始业终业
         this.restApply.timeOpen = data.tntfNtimeOpen
         this.restApply.timeClose = data.tntfNtimeClose

         // 曜日
         this.restApply.noreserved = data.tntfNnoreserved
         this.restApply.weekSelected = [
            data.tntfNmon === 1 ? 1 : 0, ,
            data.tntfNtue === 1 ? 2 : 0, ,
            data.tntfNwed === 1 ? 3 : 0, ,
            data.tntfNthu === 1 ? 4 : 0, ,
            data.tntfNfri === 1 ? 5 : 0, ,
            data.tntfNsat === 1 ? 6 : 0, ,
            data.tntfNsun === 1 ? 7 : 0,
         ]

         // 氏名
         this.restApply.txtName = data.tntfCemployeeidName

         // 続柄
         this.restApply.txtRelation = data.tntfCrelation

         // 対象の人数
         this.restApply.txtTargetNumber = data.tntfNnumberOfTarget

         // 生年月日
         this.restApply.txtBirthday = data.tntfDdateofbirth

         // 伤病
         this.restApply.txtSickName = data.tntfCsickName
      },
      resetReApply() {
         this.disableReapply = true
         history.go(0)
      },
      handleBeforeUpload(files) {
         // 返回false, 取消自带上传
         let checkPassed = true
         if (this.uploadFiles.length >= 5) {
            this.$Notice.warning({
               title: '添付可能なファイル数が上限（5個）を超えました。'
            })
            checkPassed = false
         }
         if (files.size / 1024 > 500) {
            this.$Notice.warning({
               title: 'Exceeding file size limit',
               desc: 'ファイル「' + files.name + '」のサイズが上限（500kBytes）を超えました。'
            })
            checkPassed = false
         }
         if (checkPassed) {
            this.uploadFiles.push(files)
         }
         return false;
      },
      handleDelFile(file) {
         // 删除文件
         this.deleteFiles.push(file.name)
         this.uploadFiles = this.uploadFiles.filter(e => e.name !== file.name)
      },
      handleTypeNameShow(e) {
         let result = e.tntfCtype
         if (e.tntfNtimezoneOpen) {
            result = result.concat(` (${e.tntfNtimezoneOpen} - ${e.tntfNtimezoneClose})`)
         }
         // 此处有编译bug, 不能使用<号，且!也无效
         if (e.dayOfWeek.indexOf('なし') > -1) {
            return result
         }
         result = result.concat(` ${e.dayOfWeek}`)
         return result
      },
      // 加载更多按钮
      async handleMoreDataLoading() {
         this.opts.page += 1
         this.moreDataLoading = true
         // 获得历史申请数据
         try {
            // OPTS后端发送分离
            let OPTS = { ...this.opts }
            if (this.opts.statusFlg === 0) {
               OPTS.statusFlg = ''
            }
            const { data } = await this.http.get('sys/vapply/NotificationList', OPTS)
            this.applyHistoryListData = this.applyHistoryListData.concat(data.list)
            this.$Message.success('再表示完了')
         } catch (error) {
            return
         } finally {
            this.moreDataLoading = false
         }
      },
      // 打开上传的文件
      applyCardDownloadFile(e) {
         if (e.url) {
            window.open(`http:////${e.url.slice(2)}`, '_blank')
         }
      },
      handleInputChange(i, object, value, el) {
         if (object) {
            this.hasPassedTimeCheck = Utils.checkTime(object, value, el)
         }
      },
   }
})