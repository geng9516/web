const MSG = {
    ACTION:{
        CONFIRM_NOTICE_FC: opts => `${ opts ? '一時保存' : '投稿'}します。よろしいですか？`,
        DELECT_DRAFT: 'この下書を削除します。よろしいですか？',
        DELECT_NOTICE: 'この通知を削除します。よろしいですか？',
    },
    REQ_CHECK:{
        NOTICE_RANGE:'掲示範囲を入力してください',
        NOTICE_NAME:'件名を入力してください',
    },
    TIME_CHECK:{
        START_NOTICE: '掲示開始日を入力してください',
        START_END_NOTICE: '掲示期間(終了日)は(開始日)より後の日付を入力してください',
    },
    UPLOAD_CHECK:{
        LENGTH: '添付可能なファイル数が上限（5個）を超えました',
        SIZE_20M: msg => `ファイル「${msg}」のサイズが上限（20MBytes）を超えました`,
        FORMAT: msg => `ファイル「${msg}」のタイプがjpg、jpeg、png、gifではありません`,
    },
    SUCCESS:{
        NOTICE_FC: opts => opts ? '一時保存しました' : '投稿しました',
        DELECT:'削除しました'
    }
}