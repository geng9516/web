const DrawerFooter = {
    name: 'DrawerFooter',
    template: `<div class="width100" style=" position: absolute; bottom: 0; left: 0; border-top: 1px solid #e8e8e8; padding: 10px 16px; text-align: right; background: #fff;">
    <Button style="margin-right: 8px" @click="handleCancel">{{ cancelText }}</Button>
    <Button type="primary" @click="handleOk">{{ okText }}</Button>
</div>` ,
    components: { Button: iview.Button },
    props:{
        okText:{
            type: String,
            default: '登録'
        },
        cancelText:{
            type: String,
            default: 'キャンセル'
        }
    },
    data: function () {
        return {
        }
    },
    methods: {
        handleOk() {
            this.$emit('ok')
        },
        handleCancel() {
            this.$emit('cancel')
        }
    }
}