!function(t){var e={};function r(n){if(e[n])return e[n].exports;var o=e[n]={i:n,l:!1,exports:{}};return t[n].call(o.exports,o,o.exports,r),o.l=!0,o.exports}r.m=t,r.c=e,r.d=function(t,e,n){r.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:n})},r.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},r.t=function(t,e){if(1&e&&(t=r(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var n=Object.create(null);if(r.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var o in t)r.d(n,o,function(e){return t[e]}.bind(null,o));return n},r.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return r.d(e,"a",e),e},r.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},r.p="",r(r.s=4)}({4:function(t,e){function r(t,e){var r=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),r.push.apply(r,n)}return r}function n(t){for(var e=1;e<arguments.length;e++){var n=null!=arguments[e]?arguments[e]:{};e%2?r(Object(n),!0).forEach((function(e){o(t,e,n[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(n)):r(Object(n)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(n,e))}))}return t}function o(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function i(t,e,r,n,o,i,a){try{var s=t[i](a),u=s.value}catch(t){return void r(t)}s.done?e(u):Promise.resolve(u).then(n,o)}function a(t){return function(){var e=this,r=arguments;return new Promise((function(n,o){var a=t.apply(e,r);function s(t){i(a,n,o,s,u,"next",t)}function u(t){i(a,n,o,s,u,"throw",t)}s(void 0)}))}}new Vue({el:".oconfirm-warp",data:function(){return{hwApplyShow:!1,hwhomeworkShow:!1,StatusList0:[{value:"1",text:"承認待"}],StatusList:[{value:"0",text:"なし"},{value:"1",text:"承認待"},{value:"3",text:"取下"}],StatusList1:[{value:"2",text:"承認済"}],StatusList2:[{value:"1",text:"承認待"},{value:"3",text:"取下"}],StatusList3:[{value:"1",text:"承認待"},{value:"3",text:"取下"},{value:"4",text:"差戻"}],homeworkList:[{value:"1",text:"終日"},{value:"2",text:"午前"},{value:"3",text:"午後"},{value:"4",text:"時間"}],defaultDate:"",dispMonthlyList:[],HomeWorkVOList:[],HomeWorkVOListEditBack:[],HomeWorkVOUpdateList:[],HomeWorkVOUpdateListBack:[],yyyyMMdd:"",hwcworkingid:"",loading:!1,hasPassedTimeCheck:!1,contentScrollTop:0,today:"",query:{employeeId:LOGIN_EMPOLYEE,psSite:Utils.getUrlParam(location.href,"psSite"),psApp:Utils.getUrlParam(location.href,"psApp"),txtBaseDate:"",txtEndDate:"",baseDate:""}}},mounted:function(){var t=this;return a(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:t.selectScheduleDateList();case 1:case"end":return e.stop()}}),e)})))()},computed:{columns:function(){return[{title:"日",minWidth:60,slot:"tdaDd",align:"center"},{title:"区分",minWidth:60,slot:"hwCworkingid",align:"center"},{title:"状態",minWidth:59,slot:"hwStatus",align:"center"},{title:"在宅\n勤務",minWidth:55,slot:"hwHomework",align:"center"},{title:"時間\n詳細",minWidth:55,slot:"time",align:"center"},{title:" ",minWidth:60,slot:"apply",align:"center"}]},columns1:function(){return[{title:"状態",minWidth:80,slot:"hwstatus",align:"center"},{title:"在宅勤務",minWidth:80,slot:"hwhomework",align:"center"},{title:"通勤対象外",minWidth:50,slot:"hwCommute",align:"center"}]},columns2:function(){return[{title:"開始",minWidth:90,slot:"hwStart",align:"center"},{title:"終了",minWidth:90,slot:"hwEnd",align:"center"}]},columns3:function(){return[{title:"申請コメント",minWidth:200,slot:"hwApplicationcomment"}]},columns5:function(){return[{title:"承認コメント",minWidth:200,slot:"hwApprovalcomment",align:"left"}]},columns4:function(){return[{title:"申請日",minWidth:50,slot:"yymmdd"},{title:"区分",minWidth:50,slot:"hwcworkingid",align:"center"}]}},methods:{getHomeWorkInfo:function(){var t=this;return a(regeneratorRuntime.mark((function e(){var r,n;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return t.loading=!0,e.prev=1,e.next=4,t.http.get("sys/homeWork/selectHomeWorkInfo",t.query);case 4:r=e.sent,n=r.data,t.HomeWorkVOList=n,t.HomeWorkVOListEditBack=JSON.parse(JSON.stringify(n)),e.next=13;break;case 10:return e.prev=10,e.t0=e.catch(1),e.abrupt("return");case 13:return e.prev=13,t.loading=!1,e.finish(13);case 16:case"end":return e.stop()}}),e,null,[[1,10,13,16]])})))()},selectScheduleDateList:function(){var t=this;return a(regeneratorRuntime.mark((function e(){var r,o,i,a;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,r={employeeId:t.query.employeeId,psSite:t.query.psSite,psApp:t.query.psApp,baseDate:Utils.formatDate(new Date,"YYYY-MM-DD")},e.next=4,t.http.get("sys/schedule/selectScheduleDateList",r);case 4:return o=e.sent,i=o.data,a=o.sysDate,t.today=a,t.dispMonthlyList=i.map((function(t){return n(n({},t),{},{text:t.tda_dyyyymm,value:t.tda_dyyyymm})})),t.defaultDate=t.dispMonthlyList[1].tda_dyyyymm,t.query.txtBaseDate=t.dispMonthlyList[1].tda_firstDay,e.next=13,t.getHomeWorkInfo();case 13:t.getHatuRei(),e.next=19;break;case 16:return e.prev=16,e.t0=e.catch(0),e.abrupt("return");case 19:case"end":return e.stop()}}),e,null,[[0,16]])})))()},dayRed:function(t){var e=t.mgdCsparechar;if(e)return t.hwApplicationdate===this.today?"brown":"0"!=e?"blue":void 0},getHatuRei:function(){var t=this;return a(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:e.prev=0,t.yyyyMMdd.split("～")[0].replace(/(.+?)\年(.+?)\月(.+)\日/,"$1/$2/$3"),e.next=7;break;case 4:return e.prev=4,e.t0=e.catch(0),e.abrupt("return");case 7:return e.prev=7,e.finish(7);case 9:case"end":return e.stop()}}),e,null,[[0,4,7,9]])})))()},handleInputChange:function(t,e,r,n){e&&(this.hasPassedTimeCheck=Utils.checkTime(e,r,n))},handleClose:function(){this.getHomeWorkInfo(),this.hwApplyShow=!1},time_check:function(t){return t.match(/^(([0-3]?[0-9])|([4][0-7])):([0-5]?[0-9])$/)},editBack:function(t){this.HomeWorkVOUpdateList[t].hwhomework=this.HomeWorkVOUpdateListBack.hwhomework,this.HomeWorkVOUpdateList[t].hwStart=this.HomeWorkVOUpdateListBack.hwStart,this.HomeWorkVOUpdateList[t].hwEnd=this.HomeWorkVOUpdateListBack.hwEnd,this.HomeWorkVOUpdateList[t].hwCommute=this.HomeWorkVOUpdateListBack.hwCommute},editTime:function(t){"4"!=this.HomeWorkVOUpdateList[t].hwhomework?(this.HomeWorkVOUpdateList[t].hwStart=null,this.HomeWorkVOUpdateList[t].hwEnd=null,this.hwhomeworkShow=!1):this.hwhomeworkShow=!0},updateHomeWork:function(){var t=this;return a(regeneratorRuntime.mark((function e(){var r,n,o;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:r=!1,n=0;case 2:if(!(n<t.HomeWorkVOUpdateList.length)){e.next=25;break}if(null!=t.HomeWorkVOUpdateList[n].hwstatus&&null==t.HomeWorkVOUpdateList[n].hwCommute&&(t.HomeWorkVOUpdateList[n].hwCommute="0"),null==t.HomeWorkVOUpdateList[n].hwstatus&&null!=t.HomeWorkVOUpdateList[n].hwApplicationcomment&&(t.HomeWorkVOUpdateList[n].hwstatus="0"),o=t.HomeWorkVOUpdateList[n].hwApplicationdate+" : ","1"!==t.HomeWorkVOUpdateList[n].hwstatus){e.next=10;break}if(null!==t.HomeWorkVOUpdateList[n].hwhomework){e.next=10;break}return t.$Notice.error({desc:o+"在宅勤務を入力してください。"}),e.abrupt("return",r=!0);case 10:if("4"!==t.HomeWorkVOUpdateList[n].hwhomework){e.next=22;break}if(null!=t.HomeWorkVOUpdateList[n].hwStart&&t.time_check(t.HomeWorkVOUpdateList[n].hwStart)){e.next=14;break}return t.$Notice.error({desc:o+"開始時間は適切な時刻ではありません。(hh:mm)形式で時刻を入力及び48:00以内の時刻で指定してください。"}),e.abrupt("return",r=!0);case 14:if(null!=t.HomeWorkVOUpdateList[n].hwEnd&&t.time_check(t.HomeWorkVOUpdateList[n].hwEnd)){e.next=17;break}return t.$Notice.error({desc:o+"終了時間は適切な時刻ではありません。(hh:mm)形式で時刻を入力及び48:00以内の時刻で指定してください。"}),e.abrupt("return",r=!0);case 17:if(""==t.HomeWorkVOUpdateList[n].hwStart||""==t.HomeWorkVOUpdateList[n].hwEnd){e.next=22;break}if(!(0>=Utils.timeToMinute(t.HomeWorkVOUpdateList[n].hwEnd)-Utils.timeToMinute(t.HomeWorkVOUpdateList[n].hwStart))){e.next=22;break}return t.$Notice.error({desc:o+"開始時間は終了時間の前になるようにしてください。"}),e.abrupt("return",r=!0);case 22:n++,e.next=2;break;case 25:if(console.log(t.HomeWorkVOUpdateList),!r){e.next=28;break}return e.abrupt("return");case 28:t.$Modal.confirm({title:"注意",content:"この編集内容で登録します。よろしいですか？",okText:"OK",width:480,cancelText:"キャンセル",onOk:function(){var e=a(regeneratorRuntime.mark((function e(){var r,n;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:for(r=0;r<t.HomeWorkVOUpdateList.length;r++)"4"===t.HomeWorkVOUpdateList[r].hwhomework&&(t.HomeWorkVOUpdateList[r].hwStart=Utils.timeToMinute(t.HomeWorkVOUpdateList[r].hwStart),t.HomeWorkVOUpdateList[r].hwEnd=Utils.timeToMinute(t.HomeWorkVOUpdateList[r].hwEnd));return n={homeWorkAdminVO:t.HomeWorkVOUpdateList},e.prev=2,e.next=5,t.http.post("sys/homeWork/updateHmoeWorkMob",n);case 5:e.next=10;break;case 7:e.prev=7,e.t0=e.catch(2),t.$Notice.warning({title:"注意",desc:e.t0,duration:6.5});case 10:return e.prev=10,t.getHomeWorkInfo(),t.hwApplyShow=!1,e.finish(10);case 14:case"end":return e.stop()}}),e,null,[[2,7,10,14]])})));return function(){return e.apply(this,arguments)}}(),onCancel:function(){}});case 29:case"end":return e.stop()}}),e)})))()},hwApplyShowFlg:function(t,e){this.hwApplyShow=!t,this.updateDay=Utils.formatDate(t,"YYYY年MM月DD日"),this.query.baseDate=e,this.hwApplyListShow=!0,this.getHomeWorkListInfo()},getHomeWorkListInfo:function(){var t=this;return a(regeneratorRuntime.mark((function e(){var r,n;return regeneratorRuntime.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,t.http.get("sys/homeWork/selectAdminHomeWorkmob",t.query);case 3:r=e.sent,n=r.data,t.HomeWorkVOUpdateList=[n],t.hwcworkingid=t.HomeWorkVOUpdateList[0].hwcworkingid,"4"===t.HomeWorkVOUpdateList[0].hwhomework&&(t.hwhomeworkShow=!0),t.HomeWorkVOUpdateListBack=JSON.parse(JSON.stringify(n)),e.next=14;break;case 11:return e.prev=11,e.t0=e.catch(0),e.abrupt("return");case 14:return e.prev=14,e.finish(14);case 16:case"end":return e.stop()}}),e,null,[[0,11,14,16]])})))()},handleStartMonth:function(t){var e=this;return a(regeneratorRuntime.mark((function r(){return regeneratorRuntime.wrap((function(r){for(;;)switch(r.prev=r.next){case 0:t&&(e.query.baseDate=e.dateFormat(t),e.getHomeWorkInfo());case 1:case"end":return r.stop()}}),r)})))()},dateFormat:function(t){var e=t.replace("年","/");return e=e.replace("月","/"),e+="01"}}})}});