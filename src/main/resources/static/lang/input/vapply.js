const MSG = {
    ACTION:{
        APPLY: 'この内容で申請します。よろしいですか？',
        APPLY_AGAIN: 'この内容で再申請します。よろしいですか？',
        APPLY_CANCEL: 'この申請を取り下げます。よろしいですか？',
    },
    REQ_CHECK:{
        APPLY_TYPE:'申請区分を選んでください。',
        TIME_TRANGE:'期間を選んでください。',
        TIME_OPEN:'始業後時刻を入力してください。',
        TIME_CLOSE:'終業前時刻を入力してください。',
        TIME_PERIOD:'起算日を入力してください。',
        NAME:'氏名を入力してください。',
        RELATION:'続柄を入力してください。',
        TARGET_NUMBER:'対象の人数を入力してください。',
        BIRTHDAY:'生年月日を入力してください。',
        SICK_NAME:'傷病名を入力してください。',
        CONFIRM_COMMENT:'申請事由を入力してください。',
        FILE:'添付ファイルを追加してください。',
        WEEK:'曜日を指定してください。',
        OVERWORK_FC: (msg, msg2) => `下記の日付に超過勤務命令があります。\n${msg}\n${msg2}`
    },
    LENGTH:{
        COMMENT_INT: '中断備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。',
        COMMENT_SELF: '本人コメント考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。',
        COMMENT_OVER: '超過勤務備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。',
    },
    TIME_CHECK:{
        START:'開始時間を入力してください。',
        END: '終了時間を入力してください。',
        START_END: '対象時間(終了時間)は(開始時間)より後の時刻を入力してください。',
        START_RANGE:'開始期間を入力してください。',
        END_RANGE: '終了期間を入力してください。',
        START_END_RANGE: '対象期間(終了日)は(開始日)より後の日付を入力してください。',
        TIME: '時刻をHH:MM形式で入力してください。',
    },
    UPLOAD_CHECK:{
        LENGTH: '添付可能なファイル数が上限（5個）を超えました',
        SIZE_500K: msg => `ファイル「${msg}」のサイズが上限（500kBytes）を超えました`,
    },
    SUCCESS:{
        DELECT:'削除しました',
        REFREASH:'再表示完了',
        APPLY_CANCEL:'取り消し完了',
        APPLY:'申請完了',
    }
}