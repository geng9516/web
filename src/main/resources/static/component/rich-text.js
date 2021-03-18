const UPLOAD_ICON = '<i class="ivu-icon ivu-icon-md-cloud-upload" style="font-size: 18px;" aria-hidden="true"></i>'
const UNDO_ICON = '<i class="ivu-icon ivu-icon-ios-undo" style="font-size: 18px;" aria-hidden="true"></i>'
const REDO_ICON = '<i class="ivu-icon ivu-icon-ios-redo" style="font-size: 18px;" aria-hidden="true"></i>'
const RichText = {
    name: 'DrawerFooter',
    template: `<span>
    <div id="toolbar">
    <span class="ql-formats" style="margin-bottom:  -8px;">
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="太字" placement="top">
            <button class="ql-bold"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="斜体" placement="top">
            <button class="ql-italic"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="下線" placement="top">
            <button class="ql-underline"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="打ち消し線" placement="top">
            <button class="ql-strike"></button>
        </Tooltip>
    </span>
    <span class="ql-formats" style="margin-bottom:  -8px;">
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="番号付きリスト" placement="top">
            <button class="ql-list" value="ordered"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="リスト" placement="top">
            <button class="ql-list" value="bullet"></button>
        </Tooltip>
    </span>
    <span class="ql-formats" style="margin-bottom:  -8px;">
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="下付き文字" placement="top">
            <button class="ql-script" value="sub"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="上付き文字" placement="top">
            <button class="ql-script" value="super"></button>
        </Tooltip>
    </span>
    <span class="ql-formats" style="margin-bottom:  -8px;">
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="字上げ" placement="top">
            <button class="ql-indent" value="-1"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="字下げ" placement="top">
            <button class="ql-indent" value="+1"></button>
        </Tooltip>
    </span>
    <span class="ql-formats" style="margin-bottom:  -8px;">
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="フォーマットクリア" placement="top">
            <button class="ql-clean"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="元に戻す" placement="top">
            <button class="ql-undo" style="color: var(--grey);"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="やり直す" placement="top">
            <button class="ql-redo" style="color: var(--grey);"></button>
        </Tooltip>
    </span>
    <span class="ql-formats" style="margin-bottom:  -8px;">
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="フォントの色" placement="top">
            <select class="ql-color">
                <option value="rgb(0, 0, 0)" label="rgb(0, 0, 0)" />
                <option value="rgb(230, 0, 0)" label="rgb(230, 0, 0)" />
                <option value="rgb(255, 153, 0)" label="rgb(255, 153, 0)" />
                <option value="rgb(255, 255, 0)" label="rgb(255, 255, 0)" />
                <option value="rgb(0, 138, 0)" label="rgb(0, 138, 0)" />
                <option value="rgb(0, 102, 204)" label="rgb(0, 102, 204)" />
                <option value="rgb(153, 51, 255)" label="rgb(153, 51, 255)" />
                <option value="rgb(250, 204, 204)" label="rgb(250, 204, 204)" />
                <option value="rgb(255, 235, 204)" label="rgb(255, 235, 204)" />
                <option value="rgb(255, 255, 204)" label="rgb(255, 255, 204)" />
                <option value="rgb(204, 232, 204)" label="rgb(204, 232, 204)" />
                <option value="rgb(204, 224, 245)" label="rgb(204, 224, 245)" />
                <option value="rgb(235, 214, 255)" label="rgb(235, 214, 255)" />
                <option value="rgb(187, 187, 187)" label="rgb(187, 187, 187)" />
                <option value="rgb(240, 102, 102)" label="rgb(240, 102, 102)" />
                <option value="rgb(255, 194, 102)" label="rgb(255, 194, 102)" />
                <option value="rgb(255, 255, 102)" label="rgb(255, 255, 102)" />
                <option value="rgb(102, 185, 102)" label="rgb(102, 185, 102)" />
                <option value="rgb(102, 163, 224)" label="rgb(102, 163, 224)" />
                <option value="rgb(194, 133, 255)" label="rgb(194, 133, 255)" />
                <option value="rgb(136, 136, 136)" label="rgb(136, 136, 136)" />
                <option value="rgb(161, 0, 0)" label="rgb(161, 0, 0)" />
                <option value="rgb(178, 107, 0)" label="rgb(178, 107, 0)" />
                <option value="rgb(178, 178, 0)" label="rgb(178, 178, 0)" />
                <option value="rgb(0, 97, 0)" label="rgb(0, 97, 0)" />
                <option value="rgb(0, 71, 178)" label="rgb(0, 71, 178)" />
                <option value="rgb(107, 36, 178)" label="rgb(107, 36, 178)" />
                <option value="rgb(68, 68, 68)" label="rgb(68, 68, 68)" />
                <option value="rgb(92, 0, 0)" label="rgb(92, 0, 0)" />
                <option value="rgb(102, 61, 0)" label="rgb(102, 61, 0)" />
                <option value="rgb(102, 102, 0)" label="rgb(102, 102, 0)" />
                <option value="rgb(0, 55, 0)" label="rgb(0, 55, 0)" />
                <option value="rgb(0, 41, 102)" label="rgb(0, 41, 102)" />
                <option value="rgb(61, 20, 102)" label="rgb(61, 20, 102)" />
            </select>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="塗りつぶしの色" placement="top">
            <select class="ql-background" title="塗りつぶしの色">
                <option value="rgb(0, 0, 0)" label="rgb(0, 0, 0)" />
                <option value="rgb(230, 0, 0)" label="rgb(230, 0, 0)" />
                <option value="rgb(255, 153, 0)" label="rgb(255, 153, 0)" />
                <option value="rgb(255, 255, 0)" label="rgb(255, 255, 0)" />
                <option value="rgb(0, 138, 0)" label="rgb(0, 138, 0)" />
                <option value="rgb(0, 102, 204)" label="rgb(0, 102, 204)" />
                <option value="rgb(153, 51, 255)" label="rgb(153, 51, 255)" />
                <option value="rgb(255, 255, 255)" label="rgb(255, 255, 255)" />
                <option value="rgb(250, 204, 204)" label="rgb(250, 204, 204)" />
                <option value="rgb(255, 235, 204)" label="rgb(255, 235, 204)" />
                <option value="rgb(255, 255, 204)" label="rgb(255, 255, 204)" />
                <option value="rgb(204, 232, 204)" label="rgb(204, 232, 204)" />
                <option value="rgb(204, 224, 245)" label="rgb(204, 224, 245)" />
                <option value="rgb(235, 214, 255)" label="rgb(235, 214, 255)" />
                <option value="rgb(187, 187, 187)" label="rgb(187, 187, 187)" />
                <option value="rgb(240, 102, 102)" label="rgb(240, 102, 102)" />
                <option value="rgb(255, 194, 102)" label="rgb(255, 194, 102)" />
                <option value="rgb(255, 255, 102)" label="rgb(255, 255, 102)" />
                <option value="rgb(102, 185, 102)" label="rgb(102, 185, 102)" />
                <option value="rgb(102, 163, 224)" label="rgb(102, 163, 224)" />
                <option value="rgb(194, 133, 255)" label="rgb(194, 133, 255)" />
                <option value="rgb(136, 136, 136)" label="rgb(136, 136, 136)" />
                <option value="rgb(161, 0, 0)" label="rgb(161, 0, 0)" />
                <option value="rgb(178, 107, 0)" label="rgb(178, 107, 0)" />
                <option value="rgb(178, 178, 0)" label="rgb(178, 178, 0)" />
                <option value="rgb(0, 97, 0)" label="rgb(0, 97, 0)" />
                <option value="rgb(0, 71, 178)" label="rgb(0, 71, 178)" />
                <option value="rgb(107, 36, 178)" label="rgb(107, 36, 178)" />
                <option value="rgb(68, 68, 68)" label="rgb(68, 68, 68)" />
                <option value="rgb(92, 0, 0)" label="rgb(92, 0, 0)" />
                <option value="rgb(102, 61, 0)" label="rgb(102, 61, 0)" />
                <option value="rgb(102, 102, 0)" label="rgb(102, 102, 0)" />
                <option value="rgb(0, 55, 0)" label="rgb(0, 55, 0)" />
                <option value="rgb(0, 41, 102)" label="rgb(0, 41, 102)" />
                <option value="rgb(61, 20, 102)" label="rgb(61, 20, 102)" />
            </select>
        </Tooltip>
    </span>
    <span class="ql-formats" style="margin-bottom:  -8px;">
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="リンク" placement="top">
            <button class="ql-link"></button>
        </Tooltip>
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="画像" placement="top">
            <button class="ql-image"></button>
        </Tooltip>
        <Tooltip v-show="!disabledUpload" trigger="hover" :theme="toolTipTheme()" transfer content="アップロード" placement="top">
            <button class="ql-upload" style="color: var(--grey);"></button>
        </Tooltip>
    </span>
    <span class="ql-formats" style="margin-bottom:  -8px;">
        <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="フォント サイズ" placement="top">
            <select class="ql-size">
                <option value="14px"></option>
                <option value="16px"></option>
                <option value="18px"></option>
                <option value="22px"></option>
                <option value="26px"></option>
                <option value="30px"></option>
                <option value="36px"></option>
                <option value="42px"></option>
            </select>
        </Tooltip>
    </span>
    <span class="ql-formats">
        <Poptip v-model="emojiPop" placement="right-start" class="emoji-poptip">
            <Tooltip trigger="hover" :theme="toolTipTheme()" transfer content="絵文字" placement="top">
                <Button size="small" icon="md-happy" style="font-size: 18px;" class="ql-emoji">
                </Button>
            </Tooltip>
            <div class="tr" slot="content">
                <div v-for="(item,category,index) in SM_EMOJI" class="tl">
                    <div :class="index > 0 ? 'mt5 mb2': 'mb2'" style="font-size: 16px;">{{category}}
                    </div>
                    <div class="tc" style="display: flex;flex-wrap: wrap;overflow-y: hidden;">
                        <span v-for="(emoji, emojiName) in item" class="emoji"
                            @click="handleClickEmoji(emoji)">{{ emoji }}</span>
                    </div>
                </div>
            </div>
        </Poptip>
    </span>
</div>
<div ref="editor"></div>
<Upload :show-upload-list="false" multiple action="/" :before-upload="handleBeforeUpload"
style="display: none;">
<Button icon="ios-cloud-upload-outline" ref="uploadBtn"></Button>
</Upload>
<Upload :show-upload-list="false" action="/" :before-upload="handleBeforeUpload2" style="display: none;">
<Button icon="ios-cloud-upload-outline" ref="uploadImgBtn"></Button>
</Upload></span>` ,
    components: { Button: iview.Button, Tooltip: iview.Tooltip, Poptip: iview.Poptip,Upload: iview.Upload},
    props: {
        uploadFiles: {
            type: Array,
            default: []
        },
        disabledUpload: {
            type: Boolean,
            default: false
        },
    },
    data() {
        return {
            quill: {},
            emojiPop: false,
        }
    },
    mounted() {
        const Link = Quill.import("formats/link")
        var Embed = Quill.import('blots/embed');
        var icons = Quill.import('ui/icons')
        icons['upload'] = UPLOAD_ICON
        icons["undo"] = UNDO_ICON
        icons["redo"] = REDO_ICON

        // class FileBlot extends Link {  // 继承Link Blot
        //     static create(value) {
        //         let node = undefined
        //         if (value && !value.href) {  // 适应原本的Link Blot
        //             node = super.create(value);
        //         }
        //         else {  // 自定义Link Blot
        //             node = super.create(value.href);
        //             // node.setAttribute('download', value.innerText);  // 左键点击即下载
        //             node.innerText = value.innerText;
        //             node.download = value.innerText;
        //         }
        //         return node;
        //     }
        // }
        // FileBlot.blotName = 'link';
        // FileBlot.tagName = 'A';
        // Quill.register(FileBlot);
        class SPAN extends Embed {
            static create(paramValue) {
                let node = super.create();
                node.innerHTML = paramValue;
                return node;
            }

            static value(node) {
                return node.innerHTML;
            }
        }
        SPAN.blotName = 'span';
        SPAN.tagName = 'span';
        Quill.register(SPAN);

        const that = this
        this.quill = new Quill(this.$refs.editor, {
            modules: {
                imageResize: {
                    displayStyles: {
                        backgroundColor: 'black',
                        border: 'none',
                        color: 'white'
                    },
                    modules: ['Resize', 'DisplaySize', 'Toolbar']
                },
                toolbar: {
                    container: '#toolbar',
                    handlers: {
                        'image': function (value) {
                            if (value) {
                                that.$refs.uploadImgBtn.$el.click()
                            } else {
                                this.quill.format('image', false);
                            }
                        },
                        'upload': ((value) => {
                            if (value) {
                                that.$refs.uploadBtn.$el.click()
                            }
                        }),
                        'emoji': function (value) {},
                        redo() {
                            this.quill.history.redo()
                        },
                        undo() {
                            this.quill.history.undo()

                        }
                    }
                },
                history: {
                    'delay': 2000,
                    maxStack: 500,
                    'userOnly': true
                },
            },
            theme: 'snow'
        });
        // this.quill.enable(false)
        // this.quill.on('text-change', (delta, oldDelta, source) => {
        //     this.debugquill = this.quill.getContents()
        //     // this.debugquill = this.$refs.editor.children[0].innerHTML
        // })
        this.quill.on('selection-change', range => {
            if (!range) {
                let e = document.querySelector('.ql-tooltip,.ql-editing')
                if (e) {
                    let left = e.style.left
                    if (left.indexOf('-') === 0) {
                        e.style.left = 0
                    }
                }
            } else {

            }
        })
    },
    methods: {
        handleBeforeUpload(files) {
            // 上传附件
            let checkPassed = true
            if (this.uploadFiles.length >= 5) {
                this.$Notice.warning({
                    title: '注意',
                    desc: MSG.UPLOAD_CHECK.LENGTH, duration: 6.5
                })
                checkPassed = false
            }
            if (files.size / (1024 * 1024) > 20) {
                this.$Notice.warning({
                    title: '注意',
                    desc: MSG.UPLOAD_CHECK.SIZE_20M(files.name), duration: 6.5
                })
                checkPassed = false
            }

            const extensionIndex = files.name.lastIndexOf('.')
            let extensionCheck = false
            if(extensionIndex > -1) {
                if(files.name.slice(0, extensionIndex).length > 20){
                    extensionCheck = true
                }
            } else if(files.name.length > 20) {
                extensionCheck = true
            }
            if (extensionCheck) {
                this.$Notice.warning({
                    title: '注意',
                    desc: MSG.UPLOAD_CHECK.NAME_LENGTH(files.name), duration: 6.5
                })
                checkPassed = false
            }
            if (checkPassed) {
                this.$emit('add-files', files)
            }
            return false;
        },
        handleBeforeUpload2(files) {
            // 上传图片
            let checkPassed = true
            if (files.size / (1024 * 1024) > 20) {
                this.$Notice.warning({
                    desc: MSG.UPLOAD_CHECK.SIZE(files.name), duration: 6.5
                })
                checkPassed = false
            }
            const imgType = ["jpg", "jpeg", "png", "gif"]
            if (!imgType.includes(files.type.split('image/')[1])) {
                this.$Notice.warning({
                    desc: MSG.UPLOAD_CHECK.FORMAT(files.name), duration: 6.5
                })
                checkPassed = false
            }
            if (checkPassed) {
                this.http.uploadFiles('sys/noticeboard/upload/image', {
                    file: files,
                }).then(res => {
                    let length = this.quill.getSelection().index
                    this.quill.insertEmbed(length, 'image', `${BASE_URL}${res.data}`)
                    this.quill.setSelection(length + 1)
                })
            }
            return false
        },
        handleClickEmoji(emoji) {
            let length = this.quill.selection.savedRange.index
            this.quill.insertEmbed(length, 'span', emoji)
            this.quill.setSelection(length + 1)
            // this.emojiPop = false
        },
        getHtml() {
            return this.$refs.editor.children[0].innerHTML
        },
        setHtml(html) {
            this.$refs.editor.children[0].innerHTML = html
        },
        setText(text) {
            this.quill.setText(text)
        }
    }
}