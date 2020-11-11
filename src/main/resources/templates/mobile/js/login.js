const login = new Vue({
    el: '.login-page',
    data() {
        return {
            login: {
                username: '',
                password: ''
            },
            loading: false,
            clocking: false,
            exiting: false,
            clockDisable: false,
            clockBtn: {
                clock: '出勤',
                exit: '退勤'
            },
            clockData: {
                startTime: '',
                endTime: ''
            },
            ruleValidate: {
                username: [
                    {
                        required: true,
                        message: '職員コードを入力してください',
                        trigger: 'blur'
                    }
                ],
                password: [
                    {
                        required: true,
                        message: 'パスワードを入力してください',
                        trigger: 'blur'
                    }
                ]
            },
            loading: false
        }
    },
    methods: {
        //undefined チェックする
        valid_ifnull(title, msg) {
            if (!msg) {
               //this.$Notice.warning({title: 'ログインチェック', desc: title + 'を入力してください'})
                this.$Message.warning(title + 'を入力してください');
                return false
            }
            return true
        },
        //form check
        formCheck() {
            var isPass = true
            if (!this.valid_ifnull('アカウント', this.login.username)) {
                isPass = false
                return
            }
            if (!this.valid_ifnull('パスワード', this.login.password)) {
                isPass = false
                return
            }
            return isPass
        },
        loging: Debounce(async function () {
            const isPass = this.formCheck()
            if (isPass) {
                this.loading = true
                this.clockDisable = true
                try {
                    // 采用ajax发送表单，事后跳转到请求地址，即可模拟form提交
                    await this.http.post('/login', this.login)
                    location.href = BASE_PATH
                } catch (error) {
                    this.loading = false
                    this.clockDisable = false
                }
            }
        }),
        //打刻
        async clock(pAction) {
            var isPass = this.formCheck()
            if (isPass) {
                if (pAction) {
                    if (pAction === "ACT_EXEC_OPEN") {
                        this.clocking = true
                    } else {
                        this.exiting = true
                    }
                    this.clockDisable = true
                    try {
                        const {data} = await this.http.postForm('/stamping', {username: this.login.username, password: this.login.password, pAction: pAction})
                        //console.log(data)
                        if (data) {
                            //チェック合格
                            if (data.resultCode === "0") {
                                //打刻成功(0)
                                if (pAction === "ACT_EXEC_OPEN") {
                                    this.clockData.startTime = data.clockTime
                                    this.clockBtn.clock = '出勤(' + data.clockTime + ')'
                                    //this.$Notice.info({title: '打刻しました', desc: data.resultMsg})
                                    this.$Message.success(data.resultMsg);
                                    /*this.$Modal.confirm({
                                               title: '操作確認',
                                               content: '打刻は終わりました、ホームページへ遷移しますか',
                                               okText: '遷移',
                                               cancelText: 'いいえ',
                                               onOk: async () => {
                                                   this.loging()
                                               },
                                               onCancel: () => {

                                               }
                                           })*/
                                } else {
                                    this.clockData.endTime = data.clockTime
                                    this.clockBtn.exit = '退勤(' + data.clockTime + ')'
                                    //this.$Notice.info({title: '打刻しました', desc: data.resultMsg})
                                    this.$Message.success(data.resultMsg);
                                }
                            } else {
                                //打刻失敗(10,20,30)
                                //this.$Notice.error({title: '打刻エラー', desc: data.resultMsg})
                                this.$Message.error(data.resultMsg);
                            }
                        } else {
                            //チェック不合格
                            //this.$Notice.warning({title: '打刻エラー', desc: "内部エラーが発生しました"})
                           this.$Message.error('内部エラーが発生しました');
                        }
                    } catch (error) {
                        this.clocking = false
                        this.exiting = false
                        this.clockDisable = false
                    }
                } else {
                    //this.$Notice.warning({title: '打刻エラー', desc: "パラメータが不正です"})
                    this.$Message.error("パラメータが不正です");
                }
                this.clocking = false
                this.exiting = false
                this.clockDisable = false
            }
        },
        handleBg() {
            // todo: 后端提供接口后需更改
            if(localStorage.getItem('APP_THEME') !== 'dark') return `background-image:url(${BASE_URL}/static/svg/trademark.svg)`
        },
        fixH5Height() {
            setTimeout(function () {
                let scrollHeight =
                    document.documentElement.scrollTop || document.body.scrollTop || 0
                window.scrollTo(0, Math.max(scrollHeight - 1, 0))
            }, 100)
        }
    }
})