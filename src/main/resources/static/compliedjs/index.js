!function(t){var e={};function r(a){if(e[a])return e[a].exports;var i=e[a]={i:a,l:!1,exports:{}};return t[a].call(i.exports,i,i.exports,r),i.l=!0,i.exports}r.m=t,r.c=e,r.d=function(t,e,a){r.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:a})},r.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},r.t=function(t,e){if(1&e&&(t=r(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var a=Object.create(null);if(r.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var i in t)r.d(a,i,function(e){return t[e]}.bind(null,i));return a},r.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return r.d(e,"a",e),e},r.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},r.p="",r(r.s=2)}({2:function(module,exports){function _slicedToArray(t,e){return _arrayWithHoles(t)||_iterableToArrayLimit(t,e)||_unsupportedIterableToArray(t,e)||_nonIterableRest()}function _nonIterableRest(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}function _unsupportedIterableToArray(t,e){if(t){if("string"==typeof t)return _arrayLikeToArray(t,e);var r=Object.prototype.toString.call(t).slice(8,-1);return"Object"===r&&t.constructor&&(r=t.constructor.name),"Map"===r||"Set"===r?Array.from(t):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?_arrayLikeToArray(t,e):void 0}}function _arrayLikeToArray(t,e){(null==e||e>t.length)&&(e=t.length);for(var r=0,a=new Array(e);r<e;r++)a[r]=t[r];return a}function _iterableToArrayLimit(t,e){if("undefined"!=typeof Symbol&&Symbol.iterator in Object(t)){var r=[],a=!0,i=!1,o=void 0;try{for(var n,s=t[Symbol.iterator]();!(a=(n=s.next()).done)&&(r.push(n.value),!e||r.length!==e);a=!0);}catch(t){i=!0,o=t}finally{try{a||null==s.return||s.return()}finally{if(i)throw o}}return r}}function _arrayWithHoles(t){if(Array.isArray(t))return t}function asyncGeneratorStep(t,e,r,a,i,o,n){try{var s=t[o](n),d=s.value}catch(t){return void r(t)}s.done?e(d):Promise.resolve(d).then(a,i)}function _asyncToGenerator(t){return function(){var e=this,r=arguments;return new Promise((function(a,i){var o=t.apply(e,r);function n(t){asyncGeneratorStep(o,a,i,n,s,"next",t)}function s(t){asyncGeneratorStep(o,a,i,n,s,"throw",t)}n(void 0)}))}}function _defineProperty(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}new Vue({el:".mobile-warp",data:function(){var t;return _defineProperty(t={curDate:new Date,hasPassedTimeCheck:!1,monthlyLoading:!1,dailyloading:!1,selectDisabled:!1,loading:!1,updateDailyLoading:!1,dispMonthlyList:[],query:{txtAction:"ACT_DISP_RMONTHLY",txtDYYYYMM:"",txtDYYYYMMDD:"",today:"",selHealthStatus:"",psSite:Utils.getUrlParam(location.href,"psSite")||"TMG_INP",psApp:Utils.getUrlParam(location.href,"psApp")||"TmgResults"},model1:"",columns:[{title:"曜日",slot:"week",width:45,align:"center"},{title:"日",slot:"intDay",minwidth:80,align:"center"},{title:"区分",slot:"TDA_CWORKINGID_R_NAME",minwidth:80,align:"center"},{title:"承認",slot:"workConfirm",width:45,align:"center"},{title:" ",slot:"action",align:"center"}],tableData:[],title:"",errorFlag:!1,errorMsg:"",isShow:!1,workingId:"",today:"",workingIdList:[],businessTrip:"",businessTripList:[],categoryNonduty:"",categoryNondutyName:"",categoryNondutyList:[],categoryOverhours:"",categoryOverhoursName:"",categoryOverhoursList:[],workTimeDisable:!1,columnsWork:[{title:"勤務時間",align:"center",key:"workTime"},{title:"始業",slot:"workStart",align:"center"},{title:"終業",slot:"workEnd",align:"center"}],columnsNotWork:[{title:"内容",align:"center",minWidth:"100",key:"itemName"},{title:"開始",minWidth:"90",slot:"tdadNopen",align:"center"},{title:"終了",slot:"tdadNclose",minWidth:"90",align:"center"},{title:"削除",slot:"tdadNdeleteflg",minWidth:"60",align:"center"}],detailNonDutyVOList:[],columnsOverWork:[{title:"内容",align:"center",minWidth:"100",key:"itemName"},{title:"開始",minWidth:"90",slot:"tdadNopen",align:"center"},{title:"終了",slot:"tdadNclose",minWidth:"90",align:"center"},{title:"削除",slot:"tdadNdeleteflg",minWidth:"60",align:"center"}],detailOverhoursVOList:[],detailOverhoursVOListNormal:[],dailyEdit:[],notWorkType:1,dailyData:{},workData0:[],mgdCsparechar4VOList:[],bHoliday:!1,bFixed:!1,isEdiTableResult4Inp:!1,isCommonDiscretionaryLabor:!1,isDiscretion:!1,bOpen:!1,bClose:!1,bApproved:!1,index:0,isConfirm:"",isLogShow:!1},"updateDailyLoading",!1),_defineProperty(t,"bDisable",!1),_defineProperty(t,"overHourLimit",""),_defineProperty(t,"otDaily",""),_defineProperty(t,"targetForOverTime",""),_defineProperty(t,"errMsg",[]),_defineProperty(t,"bDispKinmujokyoKakunin",!1),_defineProperty(t,"bDispKenkojotaiKakunin",!1),_defineProperty(t,"bWorkChkStatus",!1),_defineProperty(t,"bHealthChkStatus",!1),_defineProperty(t,"kinmujokyoEnd",0),_defineProperty(t,"workData",[]),_defineProperty(t,"contentScrollTop",0),_defineProperty(t,"tmgResultsDto",{txtAction:"",holiday:!1,txtTdaNopenR:"",txtTdaNcloseR:"",workingId:"",selMgdCbusinessTrip:"",txtDYYYYMMDD:"",tdaCowncommentR:"",nonDutyList:[],overHoursList:[],hasAuthority:!0,psSite:"TMG_INP"}),t},mounted:function(){var t=this;return _asyncToGenerator(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,t.getWorkDateList();case 2:t.getTitleData(),window.addEventListener("scroll",t.handleScroll,{passive:!0});case 4:case"end":return e.stop()}}),e)})))()},beforeDestroy:function(){window.removeEventListener("scroll",this.handleScroll,{passive:!0})},computed:{tableHeadFixed:function(){return this.contentScrollTop>118}},methods:{getWorkDateList:function(){var t=this;return _asyncToGenerator(regeneratorRuntime.mark((function e(){var r,a;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return t.query.txtAction="ACT_DISP_RMONTHLY",e.prev=1,e.next=4,t.http.get("sys/tmgResults/workDateList",t.query);case 4:r=e.sent,a=r.data,t.query.today=a.today,t.dispMonthlyList=a.monthLy,a.monthLy.length>0&&(t.model1=t.dispMonthlyList[0].code,t.query.txtDYYYYMM=t.model1,t.selectDisabled=!0),0===a.monthLy.length&&(t.model1="",t.selectDisabled=!1),e.next=15;break;case 12:return e.prev=12,e.t0=e.catch(1),e.abrupt("return");case 15:case"end":return e.stop()}}),e,null,[[1,12]])})))()},handleStartMonth:function(t){var e=this;return _asyncToGenerator(regeneratorRuntime.mark((function r(){return regeneratorRuntime.wrap((function(r){for(;;)switch(r.prev=r.next){case 0:return e.query.txtAction="ACT_DISP_RMONTHLY",e.query.txtDYYYYMM=t,e.query.txtDYYYYMMDD="",r.next=5,e.getTitleData();case 5:case"end":return r.stop()}}),r)})))()},getTitleData:function(){var t=this;return _asyncToGenerator(regeneratorRuntime.mark((function e(){var r,a,i;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return t.query.txtAction="ACT_EDITINP_RDAILY",t.tableData=[],e.prev=2,t.monthlyLoading=!0,e.next=6,t.http.get("sys/tmgResults/getTitleData",t.query);case 6:r=e.sent,a=r.data,i=r.sysDate,t.today=i,t.tableData=a.dailyMapList,e.next=16;break;case 13:return e.prev=13,e.t0=e.catch(2),e.abrupt("return");case 16:return e.prev=16,t.monthlyLoading=!1,e.finish(16);case 19:case"end":return e.stop()}}),e,null,[[2,13,16,19]])})))()},showDay:function(t){var e=this;return _asyncToGenerator(regeneratorRuntime.mark((function r(){return regeneratorRuntime.wrap((function(r){for(;;)switch(r.prev=r.next){case 0:return e.isShow=!0,e.dailyloading=!0,e.categoryNonduty="",e.categoryOverhours="",e.dailyEdit=[],e.workData0=[],e.detailNonDutyVOList=[],e.detailOverhoursVOList=[],e.detailOverhoursVOListNormal=[],e.title="  ",e.query.txtDYYYYMMDD=t,e.query.txtAction="ACT_EDITINP_RDAILY",e.bFixed=!1,e.bHoliday=!1,r.next=16,e.dailyDetail();case 16:e.dailyloading=!1;case 17:case"end":return r.stop()}}),r)})))()},dailyDetail:function(){var t=this;return _asyncToGenerator(regeneratorRuntime.mark((function e(){var r,a,i,o;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return t.$refs.select2&&t.$refs.select2.clearSingleSelect(),t.$refs.select3&&t.$refs.select3.clearSingleSelect(),t.$refs.select4&&t.$refs.select4.clearSingleSelect(),e.next=5,t.http.get("sys/tmgResults/dailyDetail",t.query);case 5:r=e.sent,a=r.data,t.businessTripList=a.businessTripList,t.categoryNondutyList=a.categoryNondutyList,t.categoryNondutyList.length>0&&(t.categoryNonduty=t.categoryNondutyList[0].itemCode),t.categoryOverhoursList=a.categoryOverhoursList,t.categoryNondutyList.length>0&&(t.categoryOverhours=t.categoryOverhoursList[0].itemCode),t.mgdCsparechar4VOList=a.mgdCsparechar4VOList,t.workData0=a.dailyDetail0List,t.dailyEdit=a.dailyEditVO,"TMG_DATASTATUS|9"!==t.dailyEdit.tdaCstatusflg&&"TMG_DATASTATUS|5"!==t.dailyEdit.tdaCstatusflg||""===t.dailyEdit.tdaCmodifieruseridR||""===t.dailyEdit.tdaDmodifieddateR?t.bApproved=!1:t.bApproved=!0,t.bFixed=!a.isEditable,a.workIdList.length<2&&(t.bHoliday=!0),t.isEdiTableResult4Inp=a.isEdiTableResult4Inp,t.isCommonDiscretionaryLabor=a.isCommonDiscretionaryLabor,t.isDiscretion=a.isDiscretion,t.dailyEdit.tdaNopenR!==t.dailyEdit.tdaNopenTp?t.bOpen=!0:t.bOpen=!1,t.dailyEdit.tdaNcloseR!==t.dailyEdit.tdaNcloseTp?t.bClose=!0:t.bClose=!1,t.isEdiTableResult4Inp&&(t.dailyEdit.tdaNcloseR=t.dailyEdit.tdaNopenRHidden,t.dailyEdit.tdaNcloseR=t.dailyEdit.tdaNcloseRHidden),null===t.dailyEdit.tdaNopenTp&&(t.dailyEdit.tdaNopenTp="---"),null===t.dailyEdit.tdaNcloseTp&&(t.dailyEdit.tdaNcloseTp="---"),t.isDiscretion&&(t.dailyEdit.tdaNopenN="---",t.dailyEdit.tdaNcloseN="---"),t.workingId=t.dailyEdit.tdaCworkingidR,t.businessTrip=t.dailyEdit.tdaCbusinesstripidR,t.title=t.dailyEdit.tdaDyyyymmddDy,null!==t.dailyEdit.tdaCerrcode&&(i=JSON.parse(t.dailyEdit.tdaCerrcode),Object.keys(i).some((function(e){if("ERRTYPE_10"===e)return t.$Modal.info({title:"注意",content:i.ERRTYPE_10[0].ERRMSG}),!0}))),t.workData=[{workTime:"予定時間",workStart:t.dailyEdit.tdaNopenN,workEnd:t.dailyEdit.tdaNcloseN,cellClassName:{workTime:"sub-title"}},{workTime:"打刻",workStart:t.dailyEdit.tdaNopenTp,workEnd:t.dailyEdit.tdaNcloseTp,cellClassName:{workTime:"sub-title"}},{workTime:"就業時間",workStart:t.dailyEdit.tdaNopenR,workEnd:t.dailyEdit.tdaNcloseR,isInput:!0,cellClassName:{workTime:"sub-title"}}],t.workData0.forEach((function(e){t.workData=t.workData.concat({workTime:e.tdadCnotworkName,workStart:e.tdadNopenHhmi,workEnd:e.tdadNcloseHhmi,cellClassName:{workTime:"sub-title"}})})),t.workData=t.workData.concat([{workTime:"本人コメント",isOwnComment:!0,cellClassName:{workTime:"like-title"}},{workTime:"承認者コメント",isBSComment:!0,cellClassName:{workTime:"like-title"}}]),t.detailNonDutyVOList=a.detailNonDutyVOList,t.detailOverhoursVOListNormal=a.detailOverhoursVOList,console.log(t.detailOverhoursVOListNormal),t.detailOverhoursVOList=t.handleOvertime(t.detailOverhoursVOListNormal,0),console.log(t.detailOverhoursVOList),o=a.limitOfBasedateVO,t.overHourLimit=o.dailyConvMinute,t.otDaily=o.otDaily,t.targetForOverTime=a.targetForOverTime,t.changeWorkingID(t.workingId);case 44:case"end":return e.stop()}}),e)})))()},handleOvertime:function(t,e){console.log(t);var r=[];if(0===e)t.forEach((function(t){r=r.concat({attributeUrl:t.attributeUrl,itemName:t.itemName,tdadCcode01:t.tdadCcode01,tdadCcode01Name:t.tdadCcode01Name,tdadCnotworkid:t.tdadCnotworkid,tdadCsparechar1:t.tdadCsparechar1,tdadCsparechar2:t.tdadCsparechar2,tdadCsparechar2Name:t.tdadCsparechar2Name,tdadNclose:t.tdadNclose,tdadNdeleteflg:t.tdadNdeleteflg,tdadNopen:t.tdadNopen,tdadNsparenum1:t.tdadNsparenum1,tdadSeq:t.tdadSeq},{itemName:"用務内容",tdadNopen:t.tdadCsparechar1,tdadSeq:t.tdadSeq})}));else{var a=0;t.forEach((function(t){a%2==0?(r=r.concat({attributeUrl:t.attributeUrl,itemName:t.itemName,tdadCcode01:t.tdadCcode01,tdadCcode01Name:t.tdadCcode01Name,tdadCnotworkid:t.tdadCnotworkid,tdadCsparechar1:t.tdadCsparechar1,tdadCsparechar2:t.tdadCsparechar2,tdadCsparechar2Name:t.tdadCsparechar2Name,tdadNclose:t.tdadNclose,tdadNdeleteflg:t.tdadNdeleteflg,tdadNopen:t.tdadNopen,tdadNsparenum1:t.tdadNsparenum1,tdadSeq:t.tdadSeq}),""):r[parseInt(a/2)].tdadCsparechar1=t.tdadNopen,a+=1}))}return r},handleSpan:function(t){t.row,t.column;var e=t.rowIndex,r=t.columnIndex;if(e%2!=0){if(1===r)return[1,3];if(r>2)return[0,0]}},updateInp:Debounce((function(){if(this.tmgResultsDto.txtAction="ACT_EDITINP_UCOMMENT",this.tmgResultsDto.holiday=this.bHoliday,this.tmgResultsDto.workingId=this.workingId,this.tmgResultsDto.txtDYYYYMMDD=this.query.txtDYYYYMMDD,this.tmgResultsDto.selMgdCbusinessTrip=this.businessTrip,this.tmgResultsDto.txtTdaNopenR=this.dailyEdit.tdaNopenR,this.tmgResultsDto.txtTdaNcloseR=this.dailyEdit.tdaNcloseR,this.tmgResultsDto.tdaCowncommentR=this.dailyEdit.tdaCowncommentR,null!==this.tmgResultsDto.tdaCowncommentR&&this.getLengthB(this.tmgResultsDto.tdaCowncommentR)>100)return this.$Notice.error({desc:"中断備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"}),!1;this.http.post("sys/tmgResults/updateInp",this.tmgResultsDto),this.isShow=!1})),updateDaily:function(){var t=this;return _asyncToGenerator(regeneratorRuntime.mark((function e(){var r,a,i,o;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(r=t.errorCheck(t.workingId),a=_slicedToArray(r,2),i=a[0],o=a[1],i){e.next=3;break}return e.abrupt("return");case 3:t.updateDailyLoading=!0,console.log(1),t.tmgResultsDto.txtDYYYYMMDD=t.query.txtDYYYYMMDD,t.tmgResultsDto.txtAction="ACT_EDITINP_UDAILY",t.tmgResultsDto.workingId=t.workingId,t.tmgResultsDto.selMgdCbusinessTrip=t.businessTrip,t.tmgResultsDto.holiday=null,t.tmgResultsDto.txtTdaNopenR="",t.tmgResultsDto.txtTdaNcloseR="",t.tmgResultsDto.tdaCowncommentR="",t.tmgResultsDto.hasAuthority="",t.tmgResultsDto.holiday=t.bHoliday,t.tmgResultsDto.tdaCowncommentR=t.dailyEdit.tdaCowncommentR,t.bDisable||(t.workData[2].workStart?t.tmgResultsDto.txtTdaNopenR=t.workData[2].workStart:t.tmgResultsDto.txtTdaNopenR=t.workData[0].workStart,t.workData[2].workEnd?t.tmgResultsDto.txtTdaNcloseR=t.workData[2].workEnd:t.tmgResultsDto.txtTdaNcloseR=t.workData[0].workEnd,t.tmgResultsDto.nonDutyList=t.detailNonDutyVOList,t.tmgResultsDto.overHoursList=t.detailOverhoursVOListNormal,t.bFixed||t.bHoliday?t.tmgResultsDto.hasAuthority=!1:t.tmgResultsDto.hasAuthority=!0),t.$Modal.confirm({inDrawer:!0,title:"注意",content:o||"この内容で登録します。よろしいですか？",width:450,okText:"OK",cancelText:"キャンセル",onOk:function(){var e=_asyncToGenerator(regeneratorRuntime.mark((function e(){var r,a,i;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,t.updateDailyLoading=!0,e.next=4,t.http.post("sys/tmgResults/updateDaily",t.tmgResultsDto);case 4:if(r=e.sent,"0"!==(a=r.msg)){e.next=11;break}return t.updateDailyLoading=!1,t.isShow=!1,t.getTitleData(),e.abrupt("return");case 11:if(t.errMsg=JSON.parse(a),!Object.keys(t.errMsg).some((function(e){if("ERRTYPE_10"===e)return t.$Notice.error({desc:t.errMsg.ERRTYPE_10[0].ERRMSG}),!0;"ERRTYPE_20"===e&&(t.errMsg.ERRTYPE_20.forEach((function(t){i=i+t.ERRMSG+"/n"})),t.$Notice.error({desc:i}))}))){e.next=15;break}return e.abrupt("return",!1);case 15:e.next=20;break;case 17:return e.prev=17,e.t0=e.catch(0),e.abrupt("return");case 20:return e.prev=20,t.updateDailyLoading=!1,e.finish(20);case 23:case"end":return e.stop()}}),e,null,[[0,17,20,23]])})));return function(){return e.apply(this,arguments)}}()});case 18:case"end":return e.stop()}}),e)})))()},changeWorkingID:function(t){if(t){if(this.bDisable=!1,this.bHoliday)this.bDisable=!0,this.workTimeDisable=!0;else"0"==this.mgdCsparechar4VOList.find((function(e){return e.mgdCmastercode===t})).mgdCsparechar5?(this.bDisable=!1,this.workTimeDisable=!1):(this.bDisable=!0,this.workTimeDisable=!0);!0===this.bDisable&&(this.workData[2].workStart="",this.workData[2].workEnd="",this.detailNonDutyVOList=[],this.detailOverhoursVOList=[],this.detailOverhoursVOListNormal=[])}},time_check:function(t){return t.match(/^(([0-3]?[0-9])|([4][0-7])):([0-5]?[0-9])$/)},errorCheck:function(t){var e=this;if("0"!=this.mgdCsparechar4VOList.find((function(e){return e.mgdCmastercode===t})).mgdCsparechar5)return[!0];if(this.workData[2].workStart&&!this.time_check(this.workData[2].workStart))return this.$Notice.error({desc:"就業時間・始業は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"}),[!1];if(this.workData[2].workEnd&&!this.time_check(this.workData[2].workEnd))return this.$Notice.error({desc:"就業時間・終業は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"}),[!1];if(null!==this.dailyEdit.tdaCowncommentR&&this.getLengthB(this.dailyEdit.tdaCowncommentR)>100)return this.$Notice.error({desc:"本人コメント考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"}),[!1];if(this.detailNonDutyVOList.some((function(t){return null!==t.tdadNopen&&e.time_check(t.tdadNopen)?null!==t.tdadNclose&&e.time_check(t.tdadNclose)?null!==t.tdadCsparechar1&&e.getLengthB(t.tdadCsparechar1)>100?(e.$Notice.error({desc:"中断備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"}),!0):void 0:(e.$Notice.error({desc:"非勤務の終了時刻は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"}),!0):(e.$Notice.error({desc:"非勤務の開始時刻は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。時間の範囲は48:00以内で指定してください。"}),!0)})))return[!1];if(this.detailOverhoursVOListNormal=this.handleOvertime(this.detailOverhoursVOList,1),this.detailOverhoursVOListNormal.some((function(t){if(null!==t.tdadCsparechar1&&t.tdadCsparechar1.length>100)return e.$Notice.error({desc:"超過勤務備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"}),!0})))return[!1];var r=0;return this.detailOverhoursVOListNormal.forEach((function(t){var a=e.toMinuteFromHHcMI60(t.tdadNopen),i=e.toMinuteFromHHcMI60(t.tdadNclose);r+=i-a})),r>0&&"1"===this.targetForOverTime?[!0,"超過勤務対象外として設定されています。\n超過勤務命令を登録してもよろしいですか？"]:r>this.overHourLimit?[!0,"超過勤務命令の時間数が"+this.otDaily+"時間を超えています。\nこの超過勤務時間で登録してよろしいですか？"]:[!0]},toMinuteFromHHcMI60:function toMinuteFromHHcMI60(pHHMM){if(""===pHHMM)return"";var retMinute,nHour=new Number,nMinute=new Number;return nHour=eval(60*pHHMM.split(":")[0]),nMinute=eval(+pHHMM.split(":")[1]),retMinute=eval(nHour+nMinute),retMinute},getLengthB:function(t){var e,r,a=0,i=new Array("×","§","¨","°","±","´","¶");for(e=0;e<t.length;e++)for(r=0;r<i.length;r++)0!=i[r].length&&(t=t.replace(i[r],"00"));for(t=t.split("\r\n").join("\n"),e=0;e<t.length;e++)escape(t.charAt(e)).length>=4?a+=2:a++;return a},addOverWork:function(){var t=this,e=this.categoryOverhoursList.find((function(e){return e.itemCode===t.categoryOverhours}));this.detailOverhoursVOList.push({attributeUrl:e.attributeUrl,tdadCnotworkid:e.itemCode,itemName:e.itemName,tdadNopen:"",tdadNclose:"",tdadNdeleteflg:"0",tdadSeq:Date.now(),tdadNsparenum1:"",tdadCcode01:"",tdadCcode01Name:"",tdadCsparechar2:"",tdadCsparechar2Name:""},{itemName:"用務内容",tdadNopen:"",tdadSeq:Date.now()})},addNotWork:function(){var t=this,e=this.categoryNondutyList.find((function(e){return e.itemCode===t.categoryNonduty}));this.detailNonDutyVOList.push({attributeUrl:e.attributeUrl,tdadCnotworkid:e.itemCode,itemName:e.itemName,tdadNdeleteflg:"0",tdadNopen:"",tdadNclose:"",tdadSeq:Date.now(),tdadCsparechar1:"中断時間登録（携帯端末　".concat(Utils.formatDate(Date.now(),"YYYY-MM-DD hh:ff:ss"),"）"),tdadNsparenum1:null})},delNotWork:function(t){this.detailNonDutyVOList=this.detailNonDutyVOList.filter((function(e){return e.tdadSeq!==t.tdadSeq}))},delOverWork:function(t){this.detailOverhoursVOList=this.detailOverhoursVOList.filter((function(e){return e.tdadSeq!==t.tdadSeq}))},dayRed:function(t,e){var r=t.TMG_HOLFLG;if(0===e){if(t.TDA_DYYYYMMDD===this.today)return"brown cursor";if("TMG_HOLFLG|0"===r.trim())return"cursor";if("TMG_HOLFLG|0"!=r.trim())return"blue cursor"}else{if(t.TDA_DYYYYMMDD===this.today)return"brown";if("TMG_HOLFLG|0"===r.trim())return;if("TMG_HOLFLG|0"!=r.trim())return"blue"}},handleInputChange:function(t,e,r,a){e&&(this.hasPassedTimeCheck=Utils.checkTime(e,r,a))},handleClose:function(){this.errorFlag=!1,this.isShow=!1},handleScroll:Throttle((function(t){this.contentScrollTop=t.target.documentElement.scrollTop||t.target.body.scrollTop}),50),handleSpan4time:function(t){var e=t.rowIndex,r=t.columnIndex,a=this.workData.length;return e===a-2&&1===r||e===a-1&&1===r?[1,2]:void 0}}})}});