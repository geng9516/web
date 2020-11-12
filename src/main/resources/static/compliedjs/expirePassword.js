!function(t){var e={};function n(r){if(e[r])return e[r].exports;var o=e[r]={i:r,l:!1,exports:{}};return t[r].call(o.exports,o,o.exports,n),o.l=!0,o.exports}n.m=t,n.c=e,n.d=function(t,e,r){n.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:r})},n.r=function(t){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},n.t=function(t,e){if(1&e&&(t=n(t)),8&e)return t;if(4&e&&"object"==typeof t&&t&&t.__esModule)return t;var r=Object.create(null);if(n.r(r),Object.defineProperty(r,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var o in t)n.d(r,o,function(e){return t[e]}.bind(null,o));return r},n.n=function(t){var e=t&&t.__esModule?function(){return t.default}:function(){return t};return n.d(e,"a",e),e},n.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},n.p="",n(n.s=0)}([function(t,e){function n(t,e,n,r,o,i,a){try{var u=t[i](a),s=u.value}catch(t){return void n(t)}u.done?e(s):Promise.resolve(s).then(r,o)}function r(t){return function(){var e=this,r=arguments;return new Promise((function(o,i){var a=t.apply(e,r);function u(t){n(a,o,i,u,s,"next",t)}function s(t){n(a,o,i,u,s,"throw",t)}u(void 0)}))}}new Vue({el:".login-page",data:function(){return{login:{username:Utils.getUrlParam(location.href,"username")},loading:!1}},mounted:function(){this.$Modal.warning({title:"注意",content:"このパスワードは有効期限を過ぎました。新しいパスワードを登録してください"})},methods:{loging:Debounce(r(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){for(;;)switch(t.prev=t.next){case 0:if(this.login.oldPassword){t.next=2;break}return t.abrupt("return",this.$Notice.warning({title:"注意",desc:"現在のパスワードを入力してください",duration:6.5}));case 2:if(this.login.newPassword){t.next=4;break}return t.abrupt("return",this.$Notice.warning({title:"注意",desc:"変更後のパスワードを入力してください",duration:6.5}));case 4:if(this.login.repeatPassword){t.next=6;break}return t.abrupt("return",this.$Notice.warning({title:"注意",desc:"変更後のパスワード(確認用)を入力してください",duration:6.5}));case 6:if(/^[a-z0-9A-Z]+$/.test(this.login.newPassword)&&/^[a-z0-9A-Z]+$/.test(this.login.repeatPassword)){t.next=8;break}return t.abrupt("return",this.$Notice.warning({title:"注意",desc:"パスワードに使用できる文字は英数字のみです",duration:6.5}));case 8:if(this.login.newPassword===this.login.repeatPassword){t.next=10;break}return t.abrupt("return",this.$Notice.warning({title:"注意",desc:"新パスワード(確認用)には、新パスワードと同じパスワードを入力してください。",duration:6.5}));case 10:return this.loading=!0,t.prev=11,t.next=14,this.http.post("/changeExpirePassword",this.login);case 14:location.href=BASE_PATH,t.next=21;break;case 17:return t.prev=17,t.t0=t.catch(11),this.loading=!1,t.abrupt("return",this.$Notice.error({title:"注意",desc:t.t0,duration:6.5}));case 21:case"end":return t.stop()}}),t,this,[[11,17]])})))),fixH5Height:function(){setTimeout((function(){var t=document.documentElement.scrollTop||document.body.scrollTop||0;window.scrollTo(0,Math.max(t-1,0))}),100)}}})}]);