/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 2);
/******/ })
/************************************************************************/
/******/ ({

/***/ 2:
/***/ (function(module, exports) {

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

var login = new Vue({
  el: '.login-page',
  data: function data() {
    return _defineProperty({
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
        username: [{
          required: true,
          message: '職員コードを入力してください',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: 'パスワードを入力してください',
          trigger: 'blur'
        }]
      }
    }, "loading", false);
  },
  methods: {
    //undefined チェックする
    valid_ifnull: function valid_ifnull(title, msg) {
      if (!msg) {
        //this.$Notice.warning({title: 'ログインチェック', desc: title + 'を入力してください'})
        this.$Message.warning(title + 'を入力してください');
        return false;
      }

      return true;
    },
    //form check
    formCheck: function formCheck() {
      var isPass = true;

      if (!this.valid_ifnull('アカウント', this.login.username)) {
        isPass = false;
        return;
      }

      if (!this.valid_ifnull('パスワード', this.login.password)) {
        isPass = false;
        return;
      }

      return isPass;
    },
    loging: Debounce( /*#__PURE__*/_asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
      var isPass;
      return regeneratorRuntime.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              isPass = this.formCheck();

              if (!isPass) {
                _context.next = 14;
                break;
              }

              this.loading = true;
              this.clockDisable = true;
              _context.prev = 4;
              _context.next = 7;
              return this.http.post('/login', this.login);

            case 7:
              location.href = BASE_PATH;
              _context.next = 14;
              break;

            case 10:
              _context.prev = 10;
              _context.t0 = _context["catch"](4);
              this.loading = false;
              this.clockDisable = false;

            case 14:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, this, [[4, 10]]);
    }))),
    //打刻
    clock: function clock(pAction) {
      var _this = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee2() {
        var isPass, _yield$_this$http$pos, data;

        return regeneratorRuntime.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                isPass = _this.formCheck();

                if (!isPass) {
                  _context2.next = 24;
                  break;
                }

                if (!pAction) {
                  _context2.next = 20;
                  break;
                }

                if (pAction === "ACT_EXEC_OPEN") {
                  _this.clocking = true;
                } else {
                  _this.exiting = true;
                }

                _this.clockDisable = true;
                _context2.prev = 5;
                _context2.next = 8;
                return _this.http.postForm('/stamping', {
                  username: _this.login.username,
                  password: _this.login.password,
                  pAction: pAction
                });

              case 8:
                _yield$_this$http$pos = _context2.sent;
                data = _yield$_this$http$pos.data;

                //console.log(data)
                if (data) {
                  //チェック合格
                  if (data.resultCode === "0") {
                    //打刻成功(0)
                    if (pAction === "ACT_EXEC_OPEN") {
                      _this.clockData.startTime = data.clockTime;
                      _this.clockBtn.clock = '出勤(' + data.clockTime + ')'; //this.$Notice.info({title: '打刻しました', desc: data.resultMsg})

                      _this.$Message.success(data.resultMsg);
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
                      _this.clockData.endTime = data.clockTime;
                      _this.clockBtn.exit = '退勤(' + data.clockTime + ')'; //this.$Notice.info({title: '打刻しました', desc: data.resultMsg})

                      _this.$Message.success(data.resultMsg);
                    }
                  } else {
                    //打刻失敗(10,20,30)
                    //this.$Notice.error({title: '打刻エラー', desc: data.resultMsg})
                    _this.$Message.error(data.resultMsg);
                  }
                } else {
                  //チェック不合格
                  //this.$Notice.warning({title: '打刻エラー', desc: "内部エラーが発生しました"})
                  _this.$Message.error('内部エラーが発生しました');
                }

                _context2.next = 18;
                break;

              case 13:
                _context2.prev = 13;
                _context2.t0 = _context2["catch"](5);
                _this.clocking = false;
                _this.exiting = false;
                _this.clockDisable = false;

              case 18:
                _context2.next = 21;
                break;

              case 20:
                //this.$Notice.warning({title: '打刻エラー', desc: "パラメータが不正です"})
                _this.$Message.error("パラメータが不正です");

              case 21:
                _this.clocking = false;
                _this.exiting = false;
                _this.clockDisable = false;

              case 24:
              case "end":
                return _context2.stop();
            }
          }
        }, _callee2, null, [[5, 13]]);
      }))();
    },
    handleBg: function handleBg() {
      // todo: 后端提供接口后需更改
      if (localStorage.getItem('APP_THEME') !== 'dark') return "background-image:url(".concat(BASE_URL, "/static/svg/trademark.svg)");
    },
    fixH5Height: function fixH5Height() {
      setTimeout(function () {
        var scrollHeight = document.documentElement.scrollTop || document.body.scrollTop || 0;
        window.scrollTo(0, Math.max(scrollHeight - 1, 0));
      }, 100);
    }
  }
});

/***/ })

/******/ });