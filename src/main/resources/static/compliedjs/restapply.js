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
/******/ 	return __webpack_require__(__webpack_require__.s = 3);
/******/ })
/************************************************************************/
/******/ ({

/***/ 3:
/***/ (function(module, exports) {

function _toConsumableArray(arr) { return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _unsupportedIterableToArray(arr) || _nonIterableSpread(); }

function _nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }

function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }

function _iterableToArray(iter) { if (typeof Symbol !== "undefined" && Symbol.iterator in Object(iter)) return Array.from(iter); }

function _arrayWithoutHoles(arr) { if (Array.isArray(arr)) return _arrayLikeToArray(arr); }

function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) { arr2[i] = arr[i]; } return arr2; }

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

new Vue({
  el: '.restApply-warp',
  data: function data() {
    return {
      hasPassedTimeCheck: true,
      week: [{
        name: '月',
        value: 1
      }, {
        name: '火',
        value: 2
      }, {
        name: '水',
        value: 3
      }, {
        name: '木',
        value: 4
      }, {
        name: '金',
        value: 5
      }, {
        name: '土',
        value: 6
      }, {
        name: '日',
        value: 7
      }],
      curYear: new Date(),
      restInfoList: [],
      statusList: [],
      timerangeType: 1,
      loading: false,
      moreDataLoading: false,
      rotate: false,
      restInfoLoading: false,
      listLoading: false,
      applyHistoryListData: [],
      opts: {
        ntfTypeId: '',
        statusFlg: 0,
        year: 2020,
        psSite: Utils.getUrlParam(location.href, 'psSite'),
        page: 1
      },
      restApply: {
        useVacation: false,
        weekSelected: [],
        noreserved: 1,
        _dateList: '',
        _dateListRange: ['', ''],
        dateListRange: ['', ''],
        txtAddDate: null,
        txtTargetNumber: null
      },
      openDatePicker: false,
      applyHistoryListAmount: 0,
      restTypeListForReview: [],
      restTypeListForApply: [],
      selectedRestInfo: {},
      uploadFiles: [],
      deleteFiles: [],
      cacelBtnLoading: [],
      isReApplyIng: false
    };
  },
  watch: {
    // 重置曜日选择器
    'restApply.noreserved': function restApplyNoreserved(newValue) {
      if (newValue === 1) {
        return this.restApply.weekSelected = [];
      }
    },
    rotate: function rotate(newValue) {
      if (newValue) {
        this.restApply.restTypeId = '全て';
        return;
      } // 浏览历史时，将搜索的置空


      this.opts.ntfTypeId = '';

      if (!this.isReApplyIng && this.$refs.select) {
        return this.$refs.select.clearSingleSelect();
      }
    }
  },
  created: function created() {
    this.getRestTypeForApply();
    this.getRestTypeForReview();
    this.getRestInfo();
    this.getStatusList();
    this.getApplyHistory(true);
  },
  mounted: function mounted() {},
  computed: {
    flatRestTypeList: function flatRestTypeList() {
      var result = [];

      if (this.curRestTypeList.length > 0) {
        this.curRestTypeList.forEach(function (e) {
          return result = result.concat(e.ntfTypeValue);
        });
      }

      return result;
    },
    handleFrontCardClass: function handleFrontCardClass() {
      var result = 'card';

      if (this.rotate) {
        result = result.concat(' rotate-180');
      }

      var name = this.selectedRestInfo.ntfName;

      if (name && name.length > 25) {
        result = result.concat(' full-name');
      }

      return result;
    },
    curRestTypeList: function curRestTypeList() {
      if (this.rotate) return this.restTypeListForReview;else return this.restTypeListForApply;
    }
  },
  methods: {
    getRestTypeForApply: function getRestTypeForApply() {
      var _this = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
        var _yield$_this$http$get, data;

        return regeneratorRuntime.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                // 获得休假选择 申请用
                _this.loading = true;
                _context.prev = 1;
                _context.next = 4;
                return _this.http.get('sys/vapply/NtfTypeList', _this.opts);

              case 4:
                _yield$_this$http$get = _context.sent;
                data = _yield$_this$http$get.data;
                _this.restTypeListForApply = data;
                _context.next = 12;
                break;

              case 9:
                _context.prev = 9;
                _context.t0 = _context["catch"](1);
                return _context.abrupt("return");

              case 12:
                _context.prev = 12;
                _this.loading = false;
                return _context.finish(12);

              case 15:
              case "end":
                return _context.stop();
            }
          }
        }, _callee, null, [[1, 9, 12, 15]]);
      }))();
    },
    getRestTypeForReview: function getRestTypeForReview() {
      var _this2 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee2() {
        var _yield$_this2$http$ge, data;

        return regeneratorRuntime.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                // 获得休假选择 照会用
                _this2.loading = true;
                _context2.prev = 1;
                _context2.next = 4;
                return _this2.http.get('sys/vapply/NtfTypeDispList', _this2.opts);

              case 4:
                _yield$_this2$http$ge = _context2.sent;
                data = _yield$_this2$http$ge.data;
                _this2.restTypeListForReview = data;
                _context2.next = 12;
                break;

              case 9:
                _context2.prev = 9;
                _context2.t0 = _context2["catch"](1);
                return _context2.abrupt("return");

              case 12:
                _context2.prev = 12;
                _this2.loading = false;
                return _context2.finish(12);

              case 15:
              case "end":
                return _context2.stop();
            }
          }
        }, _callee2, null, [[1, 9, 12, 15]]);
      }))();
    },
    // 左边的年休残
    getRestInfo: function getRestInfo() {
      var _this3 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee3() {
        var _yield$_this3$http$ge, data;

        return regeneratorRuntime.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                _this3.restInfoLoading = true;
                _context3.prev = 1;
                _context3.next = 4;
                return _this3.http.get('sys/vapply/RestYearList', _this3.opts);

              case 4:
                _yield$_this3$http$ge = _context3.sent;
                data = _yield$_this3$http$ge.data;
                _this3.restInfoList = data;
                _context3.next = 12;
                break;

              case 9:
                _context3.prev = 9;
                _context3.t0 = _context3["catch"](1);
                return _context3.abrupt("return");

              case 12:
                _context3.prev = 12;
                _this3.restInfoLoading = false;
                return _context3.finish(12);

              case 15:
              case "end":
                return _context3.stop();
            }
          }
        }, _callee3, null, [[1, 9, 12, 15]]);
      }))();
    },
    getStatusList: function getStatusList() {
      var _this4 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee4() {
        var _yield$_this4$http$ge, data;

        return regeneratorRuntime.wrap(function _callee4$(_context4) {
          while (1) {
            switch (_context4.prev = _context4.next) {
              case 0:
                _context4.next = 2;
                return _this4.http.get('sys/vapply/StatusFlg', _this4.opts);

              case 2:
                _yield$_this4$http$ge = _context4.sent;
                data = _yield$_this4$http$ge.data;
                _this4.statusList = data;

              case 5:
              case "end":
                return _context4.stop();
            }
          }
        }, _callee4);
      }))();
    },
    handleDatePicker: function handleDatePicker(e) {
      this.opts.year = +e;
      this.openDatePicker = false;
      this.getApplyHistory();
    },
    getApplyHistory: Debounce( /*#__PURE__*/function () {
      var _ref = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee5(isInit) {
        var OPTS, _yield$this$http$get, data;

        return regeneratorRuntime.wrap(function _callee5$(_context5) {
          while (1) {
            switch (_context5.prev = _context5.next) {
              case 0:
                this.listLoading = true;
                this.opts.page = 1; // 获得历史申请数据

                _context5.prev = 2;
                // OPTS后端发送分离
                OPTS = _objectSpread({}, this.opts);

                if (this.opts.statusFlg === 0) {
                  OPTS.statusFlg = '';
                }

                _context5.next = 7;
                return this.http.get('sys/vapply/NotificationList', OPTS);

              case 7:
                _yield$this$http$get = _context5.sent;
                data = _yield$this$http$get.data;
                this.applyHistoryListData = data.list;
                this.applyHistoryListAmount = data.totalCount;
                if (!isInit) this.$Message.success('再表示完了');
                _context5.next = 17;
                break;

              case 14:
                _context5.prev = 14;
                _context5.t0 = _context5["catch"](2);
                return _context5.abrupt("return");

              case 17:
                _context5.prev = 17;
                this.loading = false;
                this.listLoading = false;
                return _context5.finish(17);

              case 21:
              case "end":
                return _context5.stop();
            }
          }
        }, _callee5, this, [[2, 14, 17, 21]]);
      }));

      return function (_x) {
        return _ref.apply(this, arguments);
      };
    }()),
    handleWeekSelect: function handleWeekSelect(e) {},
    handleRestType: function handleRestType(e) {
      if (!e) return;
      console.log(this.rotate);

      if (this.rotate) {
        this.opts.ntfTypeId = e === '全て' ? '' : e;
        this.getApplyHistory();
        return;
      } // 选择不同的休假时，判断显示不同的项目, 请空已选内容


      this.restApply = {
        useVacation: false,
        weekSelected: [],
        noreserved: 1,
        _dateList: '',
        _dateListRange: ['', ''],
        dateListRange: ['', ''],
        txtAddDate: null,
        txtTargetNumber: null
      };
      this.errorInfo = false;
      this.uploadFiles = [];
      this.hasPassedTimeCheck = true;
      this.selectedRestInfo = {};

      if (e === '全て') {
        this.restApply.restTypeId = '全て';
        return;
      }

      this.restApply.restTypeId = e;
      this.isReApplyIng = false;
      console.log(this.flatRestTypeList);
      this.selectedRestInfo = this.flatRestTypeList.find(function (t) {
        return t.ntfId === e;
      });
    },
    onRefresh: function onRefresh() {
      this.rotate = !this.rotate;
    },
    cancel: Debounce( /*#__PURE__*/function () {
      var _ref2 = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee6(e, i) {
        return regeneratorRuntime.wrap(function _callee6$(_context6) {
          while (1) {
            switch (_context6.prev = _context6.next) {
              case 0:
                // 自己取消后无论如何都应该再拉取一遍最新的数据
                this.$set(this.cacelBtnLoading, i, true);
                _context6.prev = 1;
                _context6.next = 4;
                return this.http.post('sys/vapply/EditWithdrop', {
                  action: 'ACT_EditApply_UWithdraw',
                  ntfNo: e.tntfCntfNo,
                  psSite: this.opts.psSite
                });

              case 4:
                this.$Message.success('取り消し完了');
                _context6.next = 10;
                break;

              case 7:
                _context6.prev = 7;
                _context6.t0 = _context6["catch"](1);
                return _context6.abrupt("return");

              case 10:
                _context6.prev = 10;
                this.$set(this.cacelBtnLoading, i, false);
                this.getApplyHistory();
                return _context6.finish(10);

              case 14:
              case "end":
                return _context6.stop();
            }
          }
        }, _callee6, this, [[1, 7, 10, 14]]);
      }));

      return function (_x2, _x3) {
        return _ref2.apply(this, arguments);
      };
    }(), 700),
    handleHistoryStatusChange: function handleHistoryStatusChange(e) {
      this.getApplyHistory();
    },
    apply: Debounce( /*#__PURE__*/function () {
      var _ref3 = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee7(isRestApply) {
        var _this5 = this;

        var week, _error;

        return regeneratorRuntime.wrap(function _callee7$(_context7) {
          while (1) {
            switch (_context7.prev = _context7.next) {
              case 0:
                // 申请完后的4件事： 成功消息，翻转卡片，解除loading，展示数据
                this.restApply.action = 'ACT_MakeApply_CAppl';

                if (isRestApply) {
                  this.restApply.action = 'ACT_ReMakeApply_CAppl';
                }

                this.restApply.typeNew = this.selectedRestInfo.ntfId; // 代理申请用，个人申请此处为空

                this.restApply.finalApprovalLevel = this.selectedRestInfo.finalApprovalLevel5;
                this.restApply.weekSelectedHelper = this.restApply.weekSelected.join();
                week = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
                week.forEach(function (e, i) {
                  if (_this5.restApply.weekSelectedHelper.indexOf("".concat(i + 1)) > -1) _this5.restApply[e] = '1';else _this5.restApply[e] = '0';
                });

                if (!this.selectedRestInfo.transfer) {
                  if (this.timerangeType === 2) {
                    this.restApply.begin = this.restApply.dateListRange[0];
                    this.restApply.end = this.restApply.dateListRange[1];
                  }

                  if (this.timerangeType === 1) {
                    this.restApply.begin = this.restApply.dateList;
                    this.restApply.end = this.restApply.dateList;
                  }
                }

                if (this.restApply.typeNew) {
                  _context7.next = 10;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '申請区分を選んでください',
                  duration: 6.5
                }));

              case 10:
                if (!(this.selectedRestInfo.confirmComment != '0' && !this.restApply.owncomment)) {
                  _context7.next = 12;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '申請事由を入力してください',
                  duration: 6.5
                }));

              case 12:
                if (!(!this.restApply.begin || !this.restApply.end)) {
                  _context7.next = 14;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '期間を選んでください',
                  duration: 6.5
                }));

              case 14:
                if (!(this.restApply.begin || this.restApply.end)) {
                  _context7.next = 21;
                  break;
                }

                if (!(this.restApply.begin && !this.restApply.end)) {
                  _context7.next = 17;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '開始期間を入力してください',
                  duration: 6.5
                }));

              case 17:
                if (!(!this.restApply.begin && this.restApply.end)) {
                  _context7.next = 19;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '終了期間を入力してください',
                  duration: 6.5
                }));

              case 19:
                if (!(!this.selectedRestInfo.transfer && this.restApply.begin && this.restApply.end && Utils.timeToMinute(this.restApply.begin) > Utils.timeToMinute(this.restApply.end))) {
                  _context7.next = 21;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '開始期間 ＜ 終了期間となるようにしてください',
                  duration: 6.5
                }));

              case 21:
                if (!this.selectedRestInfo.timeZone) {
                  _context7.next = 34;
                  break;
                }

                if (this.restApply.timezoneOpen) {
                  _context7.next = 26;
                  break;
                }

                this.errorInfo = true;
                this.errorMsg = '開始時間を入力してください';
                return _context7.abrupt("return");

              case 26:
                if (this.restApply.timezoneClose) {
                  _context7.next = 30;
                  break;
                }

                this.errorInfo = true;
                this.errorMsg = '終了時間を入力してください';
                return _context7.abrupt("return");

              case 30:
                if (!(!this.selectedRestInfo.transfer && this.restApply.timezoneOpen && this.restApply.timezoneOpen && Utils.timeToMinute(this.restApply.timezoneOpen) >= Utils.timeToMinute(this.restApply.timezoneClose))) {
                  _context7.next = 34;
                  break;
                }

                this.errorInfo = true;
                this.errorMsg = '開始時間 ＜ 終了時間となるようにしてください';
                return _context7.abrupt("return");

              case 34:
                if (this.hasPassedTimeCheck) {
                  _context7.next = 36;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '時刻をHH:MM形式で入力してください',
                  duration: 6.5
                }));

              case 36:
                if (!this.selectedRestInfo.workTime) {
                  _context7.next = 41;
                  break;
                }

                if (this.restApply.timeOpen) {
                  _context7.next = 39;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '始業後時刻を入力してください',
                  duration: 6.5
                }));

              case 39:
                if (this.restApply.timeClose) {
                  _context7.next = 41;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '終業前時刻を入力してください',
                  duration: 6.5
                }));

              case 41:
                if (!(this.selectedRestInfo.period && !this.restApply.txtPeriod)) {
                  _context7.next = 43;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '起算日を入力してください',
                  duration: 6.5
                }));

              case 43:
                if (!(this.selectedRestInfo.name && !this.restApply.txtName)) {
                  _context7.next = 45;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '氏名を入力してください',
                  duration: 6.5
                }));

              case 45:
                if (!(this.selectedRestInfo.relation && !this.restApply.txtRelation)) {
                  _context7.next = 47;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '続柄を入力してください',
                  duration: 6.5
                }));

              case 47:
                if (!(this.selectedRestInfo.targetNumber && !this.restApply.txtTargetNumber)) {
                  _context7.next = 49;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '対象の人数を入力してください',
                  duration: 6.5
                }));

              case 49:
                if (!(this.selectedRestInfo.birthday && !this.restApply.txtBirthday)) {
                  _context7.next = 51;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '生年月日を入力してください',
                  duration: 6.5
                }));

              case 51:
                if (!(this.selectedRestInfo.sickName && !this.restApply.txtSickName)) {
                  _context7.next = 53;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '傷病名を入力してください',
                  duration: 6.5
                }));

              case 53:
                if (!(this.selectedRestInfo.confirmComment != '0' && !this.restApply.owncomment)) {
                  _context7.next = 55;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '申請事由を入力してください',
                  duration: 6.5
                }));

              case 55:
                if (!(this.selectedRestInfo.confirmFile === '1' && !this.uploadFiles.length > 0)) {
                  _context7.next = 57;
                  break;
                }

                return _context7.abrupt("return", this.$Notice.warning({
                  title: '注意',
                  desc: '添付ファイルを入力してください',
                  duration: 6.5
                }));

              case 57:
                this.deleteFiles = _toConsumableArray(new Set(this.deleteFiles));
                _context7.prev = 58;
                this.loading = true;
                _context7.next = 62;
                return this.http.uploadFiles('sys/vapply/MakeApply', {
                  params: JSON.stringify(this.restApply),
                  uploadFiles: this.uploadFiles.filter(function (e) {
                    return !e.isDefault;
                  }),
                  deleteFiles: JSON.stringify(this.deleteFiles),
                  psSite: this.opts.psSite
                });

              case 62:
                this.restApply = {
                  useVacation: false,
                  restTypeId: '全て',
                  weekSelected: [],
                  noreserved: 1,
                  _dateList: '',
                  _dateListRange: ['', ''],
                  dateListRange: ['', ''],
                  txtAddDate: null,
                  txtTargetNumber: null
                };
                this.rotate = !this.rotate;
                this.isReApplyIng = false;
                _context7.next = 67;
                return this.getApplyHistory(true);

              case 67:
                this.$Message.success('申請完了');
                this.uploadFiles = [];
                _context7.next = 77;
                break;

              case 71:
                _context7.prev = 71;
                _context7.t0 = _context7["catch"](58);

                if (!(_context7.t0.config || _context7.t0.response && _context7.t0.response.status === 500)) {
                  _context7.next = 75;
                  break;
                }

                return _context7.abrupt("return");

              case 75:
                _error = JSON.parse(_context7.t0);
                return _context7.abrupt("return", this.$Modal.error({
                  title: '注意',
                  content: _error[Object.keys(_error)[0]][0].ERRMSG
                }));

              case 77:
                _context7.prev = 77;
                this.loading = false;
                this.deleteFiles = [];
                return _context7.finish(77);

              case 81:
              case "end":
                return _context7.stop();
            }
          }
        }, _callee7, this, [[58, 71, 77, 81]]);
      }));

      return function (_x4) {
        return _ref3.apply(this, arguments);
      };
    }()),
    handleApplyMarkShow: function handleApplyMarkShow(e) {
      // TMG_NTFSTATUS|0	取下
      if (e.indexOf('0') > -1) {
        return 'apply-status';
      } // TMG_NTFSTATUS|2	承認待


      if (e.indexOf('2') > -1) {
        return 'apply-status ing';
      } // TMG_NTFSTATUS|3	差戻


      if (e.indexOf('3') > -1) {
        return 'apply-status deny';
      } // TMG_NTFSTATUS|9	エラー


      if (e.indexOf('9') > -1) {
        return 'apply-status error';
      } // TMG_NTFSTATUS|5	承認済


      return 'apply-status ok';
    },
    handleTimelineDotShow: function handleTimelineDotShow(e) {
      // TMG_NTFSTATUS|0	取下
      if (e.indexOf('0') > -1) {
        return 'with-dot cancel';
      } // TMG_NTFSTATUS|2	承認待


      if (e.indexOf('2') > -1) {
        return 'with-dot ing';
      } // TMG_NTFSTATUS|3	差戻
      // TMG_NTFSTATUS|9	エラー


      if (e.indexOf('3') > -1 || e.indexOf('9') > -1) {
        return 'with-dot deny';
      } // TMG_NTFSTATUS|5	承認済


      return 'with-dot';
    },
    handleApplyDayShow: function handleApplyDayShow(begin, end) {
      if (begin === end) return begin;else return "".concat(begin, " \uFF5E ").concat(end);
    },
    // 按下背面的再申请按钮
    handleReApplybtn: function handleReApplybtn(item) {
      var _this6 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee8() {
        var _yield$_this6$http$ge, data;

        return regeneratorRuntime.wrap(function _callee8$(_context8) {
          while (1) {
            switch (_context8.prev = _context8.next) {
              case 0:
                _this6.deleteFiles = [];
                _this6.uploadFiles = [];
                _this6.rotate = !_this6.rotate;
                console.log(_this6.selectedRestInfo.transfer); // 时间部分

                console.log(item.remakeApply);

                _this6.$nextTick(function () {
                  _this6.handleRestType(item.remakeApply);

                  _this6.isReApplyIng = true;

                  if (!_this6.selectedRestInfo.transfer) {
                    // 是时间不是振休
                    // 是范围不是单一时间
                    if (item.tntfDbegin !== item.tntfDend) {
                      _this6.timerangeType = 2;
                      _this6.restApply._dateListRange = [item.tntfDbegin, item.tntfDend];
                      _this6.restApply.dateListRange = [item.tntfDbegin, item.tntfDend];
                    } else {
                      _this6.timerangeType = 1;
                      _this6.restApply._dateList = item.tntfDbegin;
                      _this6.restApply.dateList = item.tntfDbegin;
                    }
                  } else {
                    // 振休
                    console.log(item.tntfDbegin);
                    console.log(item.tntfDend);
                    _this6.restApply.begin = item.tntfDbegin;
                    _this6.restApply.end = item.tntfDend;
                  }
                }); // 申请区分


                _this6.restApply.restTypeId = item.remakeApply;
                _this6.restApply.ntfNo = item.tntfCntfNo;
                _this6.selectedRestInfo.finalApprovalLevel = item.finalApprovalLevel; // 文件

                _this6.uploadFiles = item.tmgNtfAttachedfileDoList.map(function (e) {
                  return {
                    name: e.tnafCfilename,
                    url: e.tnafFilepath,
                    type: e.tnafCfilename,
                    isDefault: true
                  };
                }); // comment

                _this6.restApply.owncomment = item.tntfCowncomment; // 再拿更多详细

                _context8.next = 13;
                return _this6.http.get('sys/vapply/NotificationDetail', {
                  ntfNo: item.tntfCntfNo
                });

              case 13:
                _yield$_this6$http$ge = _context8.sent;
                data = _yield$_this6$http$ge.data;
                _this6.restApply.ntfNo = item.tntfCntfNo; // 起算日

                _this6.restApply.txtPeriod = data.tntfDperiodDate; // 加算日

                _this6.restApply.txtAddDate = data.tntfNuapperAddition; // 時間帯

                _this6.$set(_this6.resetReApply, 'timezoneOpen', data.tntfNtimezoneOpenHhmi);

                _this6.$set(_this6.resetReApply, 'timezoneClose', data.tntfNtimezoneCloseHhmi); // 始业终业


                _this6.restApply.timeOpen = data.tntfNtimeOpen;
                _this6.restApply.timeClose = data.tntfNtimeClose; // 曜日

                _this6.restApply.noreserved = data.tntfNnoreserved;
                _this6.restApply.weekSelected = [data.tntfNmon === 1 ? 1 : 0,, data.tntfNtue === 1 ? 2 : 0,, data.tntfNwed === 1 ? 3 : 0,, data.tntfNthu === 1 ? 4 : 0,, data.tntfNfri === 1 ? 5 : 0,, data.tntfNsat === 1 ? 6 : 0,, data.tntfNsun === 1 ? 7 : 0]; // 氏名

                _this6.restApply.txtName = data.tntfCemployeeidName; // 続柄

                _this6.restApply.txtRelation = data.tntfCrelation; // 対象の人数

                _this6.restApply.txtTargetNumber = data.tntfNnumberOfTarget; // 生年月日

                _this6.restApply.txtBirthday = data.tntfDdateofbirth; // 伤病

                _this6.restApply.txtSickName = data.tntfCsickName;

              case 29:
              case "end":
                return _context8.stop();
            }
          }
        }, _callee8);
      }))();
    },
    resetReApply: function resetReApply() {
      this.loading = true;
      history.go(0);
    },
    handleBeforeUpload: function handleBeforeUpload(files) {
      // 返回false, 取消自带上传
      var checkPassed = true;

      if (this.uploadFiles.length >= 5) {
        this.$Notice.warning({
          title: '添付可能なファイル数が上限（5個）を超えました。'
        });
        checkPassed = false;
      }

      if (files.size / 1024 > 500) {
        this.$Notice.warning({
          title: 'Exceeding file size limit',
          desc: 'ファイル「' + files.name + '」のサイズが上限（500kBytes）を超えました。'
        });
        checkPassed = false;
      }

      if (checkPassed) {
        this.uploadFiles.push(files);
      }

      return false;
    },
    handleDelFile: function handleDelFile(file) {
      // 删除文件
      this.deleteFiles.push(file.name);
      this.uploadFiles = this.uploadFiles.filter(function (e) {
        return e.name !== file.name;
      });
    },
    handleTypeNameShow: function handleTypeNameShow(e) {
      var result = e.tntfCtype;

      if (e.tntfNtimezoneOpen) {
        result = result.concat(" (".concat(e.tntfNtimezoneOpen, " - ").concat(e.tntfNtimezoneClose, ")"));
      } // 此处有编译bug, 不能使用<号，且!也无效


      if (e.dayOfWeek.indexOf('なし') > -1) {
        return result;
      }

      result = result.concat(" ".concat(e.dayOfWeek));
      return result;
    },
    // 加载更多按钮
    handleMoreDataLoading: function handleMoreDataLoading() {
      var _this7 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee9() {
        var OPTS, _yield$_this7$http$ge, data;

        return regeneratorRuntime.wrap(function _callee9$(_context9) {
          while (1) {
            switch (_context9.prev = _context9.next) {
              case 0:
                _this7.opts.page += 1;
                _this7.moreDataLoading = true; // 获得历史申请数据

                _context9.prev = 2;
                // OPTS后端发送分离
                OPTS = _objectSpread({}, _this7.opts);

                if (_this7.opts.statusFlg === 0) {
                  OPTS.statusFlg = '';
                }

                _context9.next = 7;
                return _this7.http.get('sys/vapply/NotificationList', OPTS);

              case 7:
                _yield$_this7$http$ge = _context9.sent;
                data = _yield$_this7$http$ge.data;
                _this7.applyHistoryListData = _this7.applyHistoryListData.concat(data.list);

                _this7.$Message.success('再表示完了');

                _context9.next = 16;
                break;

              case 13:
                _context9.prev = 13;
                _context9.t0 = _context9["catch"](2);
                return _context9.abrupt("return");

              case 16:
                _context9.prev = 16;
                _this7.moreDataLoading = false;
                return _context9.finish(16);

              case 19:
              case "end":
                return _context9.stop();
            }
          }
        }, _callee9, null, [[2, 13, 16, 19]]);
      }))();
    },
    // 打开上传的文件
    applyCardDownloadFile: function applyCardDownloadFile(e) {
      if (e.url) {
        window.open("http:////".concat(e.url.slice(2)), '_blank');
      }
    },
    handleInputChange: function handleInputChange(i, object, value, el) {
      if (object) {
        this.hasPassedTimeCheck = Utils.checkTime(object, value, el);
      }
    }
  }
});

/***/ })

/******/ });