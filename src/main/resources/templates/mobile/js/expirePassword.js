const login = new Vue({
    el: '.login-page',
    data() {
        return {
            login: {
                username: Utils.getUrlParam(location.href, 'username')
            },
            loading: false,
        }
    },
    mounted() {
        this.$Modal.warning({ title: '注意', content: 'このパスワードは有効期限を過ぎました。新しいパスワードを登録してください'})
    },
    methods: {
        loging: Debounce(async function () {
            if (!this.login.oldPassword) {
                return this.$Notice.warning({ title: '注意', desc: '現在のパスワードを入力してください', duration: 6.5 })
            }
            if (!this.login.newPassword) {
                return this.$Notice.warning({ title: '注意', desc: '変更後のパスワードを入力してください', duration: 6.5 })
            }
            if (!this.login.repeatPassword) {
                return this.$Notice.warning({ title: '注意', desc: '変更後のパスワード(確認用)を入力してください', duration: 6.5 })
            }
            if(!/^[a-z0-9A-Z]+$/.test(this.login.newPassword) || !/^[a-z0-9A-Z]+$/.test(this.login.repeatPassword)) {
                return this.$Notice.warning({ title: '注意', desc: 'パスワードに使用できる文字は英数字のみです', duration: 6.5 })
            }
            if (this.login.newPassword !== this.login.repeatPassword) {
                return this.$Notice.warning({ title: '注意', desc: '新パスワード(確認用)には、新パスワードと同じパスワードを入力してください。', duration: 6.5 })
            }
            this.loading = true
            try {
                await this.http.post('/changeExpirePassword', this.login)
                location.href = BASE_PATH
            } catch (error) {
                this.loading = false
                return this.$Notice.error({ title: '注意', desc: error, duration: 6.5 })
            }

        }),
        fixH5Height() {
            setTimeout(function () {
                let scrollHeight =
                    document.documentElement.scrollTop || document.body.scrollTop || 0
                window.scrollTo(0, Math.max(scrollHeight - 1, 0))
            }, 100)
        }
    }
})