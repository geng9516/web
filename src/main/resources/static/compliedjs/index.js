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
/******/ 	return __webpack_require__(__webpack_require__.s = 1);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */,
/* 1 */
/***/ (function(module, exports) {

function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }

function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }

function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }

function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) { arr2[i] = arr[i]; } return arr2; }

function _iterableToArrayLimit(arr, i) { if (typeof Symbol === "undefined" || !(Symbol.iterator in Object(arr))) return; var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"] != null) _i["return"](); } finally { if (_d) throw _e; } } return _arr; }

function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

new Vue({
  el: '.mobile-warp',
  data: function data() {
    var _ref;

    return _ref = {
      curDate: new Date(),
      hasPassedTimeCheck: false,
      monthlyLoading: false,
      dailyloading: false,
      selectDisabled: false,
      //年月選択可否制御
      loading: false,
      updateDailyLoading: false,
      dispMonthlyList: [],
      //年月リスト
      query: {
        txtAction: 'ACT_DISP_RMONTHLY',
        txtDYYYYMM: '',
        txtDYYYYMMDD: '',
        today: '',
        selHealthStatus: '',
        psSite: Utils.getUrlParam(location.href, 'psSite') || 'TMG_INP',
        psApp: Utils.getUrlParam(location.href, 'psApp') || 'TmgResults'
      },
      model1: '',
      columns: [{
        title: '曜日',
        slot: 'week',
        width: 45,
        align: 'center'
      }, {
        title: '日',
        slot: 'intDay',
        minwidth: 80,
        align: 'center'
      }, {
        title: '区分',
        slot: 'TDA_CWORKINGID_R_NAME',
        minwidth: 80,
        align: 'center'
      }, {
        title: '承認',
        slot: 'workConfirm',
        width: 45,
        align: 'center'
      }, {
        title: ' ',
        slot: 'action',
        align: 'center'
      }],
      // data目前用的线上某一份
      tableData: [],
      title: '',
      errorFlag: false,
      errorMsg: '',
      isShow: false,
      workingId: '',
      today: '',
      workingIdList: [],
      businessTrip: '',
      businessTripList: [],
      categoryNonduty: '',
      categoryNondutyName: '',
      categoryNondutyList: [],
      categoryOverhours: '',
      categoryOverhoursName: '',
      categoryOverhoursList: [],
      workTimeDisable: false,
      columnsWork: [{
        title: '勤務時間',
        align: 'center',
        key: 'workTime' //width:'30'

      }, {
        title: '始業',
        slot: 'workStart',
        align: 'center' //width:'35'

      }, {
        title: '終業',
        slot: 'workEnd',
        align: 'center' //width:'35'

      }],
      columnsNotWork: [{
        title: '内容',
        align: 'center',
        minWidth: '100',
        key: 'itemName'
      }, {
        title: '開始',
        minWidth: '90',
        slot: 'tdadNopen',
        align: 'center'
      }, {
        title: '終了',
        slot: 'tdadNclose',
        minWidth: '90',
        align: 'center'
      }, {
        title: '削除',
        slot: 'tdadNdeleteflg',
        minWidth: '60',
        align: 'center'
      }],
      detailNonDutyVOList: [],
      columnsOverWork: [{
        title: '内容',
        align: 'center',
        minWidth: '100',
        key: 'itemName'
      }, {
        title: '開始',
        minWidth: '90',
        slot: 'tdadNopen',
        align: 'center'
      }, {
        title: '終了',
        slot: 'tdadNclose',
        minWidth: '90',
        align: 'center'
      }, {
        title: '削除',
        slot: 'tdadNdeleteflg',
        minWidth: '60',
        align: 'center'
      }],
      detailOverhoursVOList: [],
      //table 用
      detailOverhoursVOListNormal: [],
      //请求 用
      dailyEdit: [],
      notWorkType: 1,
      dailyData: {},
      workData0: [],
      mgdCsparechar4VOList: [],
      bHoliday: false,
      bFixed: false,
      isEdiTableResult4Inp: false,
      isCommonDiscretionaryLabor: false,
      isDiscretion: false,
      //裁量労働制
      bOpen: false,
      bClose: false,
      // 承認者コメントの入力制御
      bApproved: false,
      index: 0,
      // 承認ステータス
      isConfirm: '',
      // 更新履歴の表示制御
      isLogShow: false
    }, _defineProperty(_ref, "updateDailyLoading", false), _defineProperty(_ref, "bDisable", false), _defineProperty(_ref, "overHourLimit", ''), _defineProperty(_ref, "otDaily", ''), _defineProperty(_ref, "targetForOverTime", ''), _defineProperty(_ref, "errMsg", []), _defineProperty(_ref, "bDispKinmujokyoKakunin", false), _defineProperty(_ref, "bDispKenkojotaiKakunin", false), _defineProperty(_ref, "bWorkChkStatus", false), _defineProperty(_ref, "bHealthChkStatus", false), _defineProperty(_ref, "kinmujokyoEnd", 0), _defineProperty(_ref, "workData", []), _defineProperty(_ref, "contentScrollTop", 0), _defineProperty(_ref, "tmgResultsDto", {
      txtAction: '',
      holiday: false,
      txtTdaNopenR: '',
      txtTdaNcloseR: '',
      workingId: '',
      selMgdCbusinessTrip: '',
      txtDYYYYMMDD: '',
      tdaCowncommentR: '',
      nonDutyList: [],
      overHoursList: [],
      hasAuthority: true,
      psSite: 'TMG_INP'
    }), _ref;
  },
  mounted: function mounted() {
    var _this = this;

    return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
      return regeneratorRuntime.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              _context.next = 2;
              return _this.getWorkDateList();

            case 2:
              _this.getTitleData();

              window.addEventListener('scroll', _this.handleScroll, {
                passive: true
              });

            case 4:
            case "end":
              return _context.stop();
          }
        }
      }, _callee);
    }))();
  },
  beforeDestroy: function beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll, {
      passive: true
    });
  },
  computed: {
    tableHeadFixed: function tableHeadFixed() {
      if (this.contentScrollTop > 118) return true;else return false;
    }
  },
  methods: {
    // 勤務年月一覧取得
    getWorkDateList: function getWorkDateList() {
      var _this2 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee2() {
        var _yield$_this2$http$ge, data;

        return regeneratorRuntime.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                _this2.query.txtAction = 'ACT_DISP_RMONTHLY';
                _context2.prev = 1;
                _context2.next = 4;
                return _this2.http.get('sys/tmgResults/workDateList', _this2.query);

              case 4:
                _yield$_this2$http$ge = _context2.sent;
                data = _yield$_this2$http$ge.data;
                _this2.query.today = data.today;
                _this2.dispMonthlyList = data.monthLy;

                if (data.monthLy.length > 0) {
                  _this2.model1 = _this2.dispMonthlyList[0].code;
                  _this2.query.txtDYYYYMM = _this2.model1; //年月リストを取得する時に、活性化にする

                  _this2.selectDisabled = true;
                } //年月リスト.lengthがゼロ場合、非活性化にする


                if (data.monthLy.length === 0) {
                  _this2.model1 = '';
                  _this2.selectDisabled = false;
                }

                _context2.next = 15;
                break;

              case 12:
                _context2.prev = 12;
                _context2.t0 = _context2["catch"](1);
                return _context2.abrupt("return");

              case 15:
              case "end":
                return _context2.stop();
            }
          }
        }, _callee2, null, [[1, 12]]);
      }))();
    },
    // 年月選択
    handleStartMonth: function handleStartMonth(e) {
      var _this3 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee3() {
        return regeneratorRuntime.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                _this3.query.txtAction = 'ACT_DISP_RMONTHLY';
                _this3.query.txtDYYYYMM = e;
                _this3.query.txtDYYYYMMDD = ''; // 就業実績 --月次・日次実績

                _context3.next = 5;
                return _this3.getTitleData();

              case 5:
              case "end":
                return _context3.stop();
            }
          }
        }, _callee3);
      }))();
    },
    // 就業実績のタイトルとデータを取得
    getTitleData: function getTitleData() {
      var _this4 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee4() {
        var _yield$_this4$http$ge, data, sysDate;

        return regeneratorRuntime.wrap(function _callee4$(_context4) {
          while (1) {
            switch (_context4.prev = _context4.next) {
              case 0:
                _this4.query.txtAction = 'ACT_EDITINP_RDAILY';
                _this4.tableData = [];
                _context4.prev = 2;
                _this4.monthlyLoading = true;
                _context4.next = 6;
                return _this4.http.get('sys/tmgResults/getTitleData', _this4.query);

              case 6:
                _yield$_this4$http$ge = _context4.sent;
                data = _yield$_this4$http$ge.data;
                sysDate = _yield$_this4$http$ge.sysDate;
                _this4.today = sysDate;
                _this4.tableData = data.dailyMapList;
                _context4.next = 16;
                break;

              case 13:
                _context4.prev = 13;
                _context4.t0 = _context4["catch"](2);
                return _context4.abrupt("return");

              case 16:
                _context4.prev = 16;
                _this4.monthlyLoading = false;
                return _context4.finish(16);

              case 19:
              case "end":
                return _context4.stop();
            }
          }
        }, _callee4, null, [[2, 13, 16, 19]]);
      }))();
    },
    //一覧から日次実績をクリックする
    showDay: function showDay(e) {
      var _this5 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee5() {
        return regeneratorRuntime.wrap(function _callee5$(_context5) {
          while (1) {
            switch (_context5.prev = _context5.next) {
              case 0:
                // 業登録画面表示
                _this5.isShow = true;
                _this5.dailyloading = true; // 就業登録画面項目クリアする

                _this5.categoryNonduty = '';
                _this5.categoryOverhours = '';
                _this5.dailyEdit = [];
                _this5.workData0 = [];
                _this5.detailNonDutyVOList = [];
                _this5.detailOverhoursVOList = [];
                _this5.detailOverhoursVOListNormal = [];
                _this5.title = '  ';
                _this5.query.txtDYYYYMMDD = e;
                _this5.query.txtAction = 'ACT_EDITINP_RDAILY';
                _this5.bFixed = false;
                _this5.bHoliday = false; // 業登録画面データ取得

                _context5.next = 16;
                return _this5.dailyDetail();

              case 16:
                _this5.dailyloading = false;

              case 17:
              case "end":
                return _context5.stop();
            }
          }
        }, _callee5);
      }))();
    },
    // 就業登録画面情報取得・設定する
    dailyDetail: function dailyDetail() {
      var _this6 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee6() {
        var _yield$_this6$http$ge, data, tempErrCode, limitOfBasedateVO;

        return regeneratorRuntime.wrap(function _callee6$(_context6) {
          while (1) {
            switch (_context6.prev = _context6.next) {
              case 0:
                if (_this6.$refs.select2) {
                  _this6.$refs.select2.clearSingleSelect();
                }

                if (_this6.$refs.select3) {
                  _this6.$refs.select3.clearSingleSelect();
                }

                if (_this6.$refs.select4) {
                  _this6.$refs.select4.clearSingleSelect();
                } // 画面情報取得


                _context6.next = 5;
                return _this6.http.get('sys/tmgResults/dailyDetail', _this6.query);

              case 5:
                _yield$_this6$http$ge = _context6.sent;
                data = _yield$_this6$http$ge.data;
                // 出張ドロップダウン
                _this6.businessTripList = data.businessTripList; // 非勤務ドロップダウン

                _this6.categoryNondutyList = data.categoryNondutyList;

                if (_this6.categoryNondutyList.length > 0) {
                  _this6.categoryNonduty = _this6.categoryNondutyList[0].itemCode;
                } // 超過勤務ドロップダウン


                _this6.categoryOverhoursList = data.categoryOverhoursList;

                if (_this6.categoryNondutyList.length > 0) {
                  _this6.categoryOverhours = _this6.categoryOverhoursList[0].itemCode;
                }

                _this6.mgdCsparechar4VOList = data.mgdCsparechar4VOList; // 詳細:欠勤離籍以外

                _this6.workData0 = data.dailyDetail0List; // 就業実績

                _this6.dailyEdit = data.dailyEditVO;

                if (('TMG_DATASTATUS|9' === _this6.dailyEdit.tdaCstatusflg || 'TMG_DATASTATUS|5' === _this6.dailyEdit.tdaCstatusflg) && '' !== _this6.dailyEdit.tdaCmodifieruseridR && '' !== _this6.dailyEdit.tdaDmodifieddateR) {
                  _this6.bApproved = true;
                } else {
                  _this6.bApproved = false;
                } // 登録ボタンとコメントのみ登録ボタンの表示制御


                _this6.bFixed = !data.isEditable;

                if (data.workIdList.length < 2) {
                  _this6.bHoliday = true;
                } //就業入力サイトでの就業実績編集機能を使用するか判定し値を返却します


                _this6.isEdiTableResult4Inp = data.isEdiTableResult4Inp;
                _this6.isCommonDiscretionaryLabor = data.isCommonDiscretionaryLabor;
                _this6.isDiscretion = data.isDiscretion;

                if (_this6.dailyEdit.tdaNopenR !== _this6.dailyEdit.tdaNopenTp) {
                  _this6.bOpen = true;
                } else {
                  _this6.bOpen = false;
                }

                if (_this6.dailyEdit.tdaNcloseR !== _this6.dailyEdit.tdaNcloseTp) {
                  _this6.bClose = true;
                } else {
                  _this6.bClose = false;
                }

                if (_this6.isEdiTableResult4Inp) {
                  _this6.dailyEdit.tdaNcloseR = _this6.dailyEdit.tdaNopenRHidden;
                  _this6.dailyEdit.tdaNcloseR = _this6.dailyEdit.tdaNcloseRHidden;
                }

                if (_this6.dailyEdit.tdaNopenTp === null) {
                  _this6.dailyEdit.tdaNopenTp = '---';
                }

                if (_this6.dailyEdit.tdaNcloseTp === null) {
                  _this6.dailyEdit.tdaNcloseTp = '---';
                }

                if (_this6.isDiscretion) {
                  _this6.dailyEdit.tdaNopenN = '---';
                  _this6.dailyEdit.tdaNcloseN = '---';
                }

                _this6.workingId = _this6.dailyEdit.tdaCworkingidR;
                _this6.businessTrip = _this6.dailyEdit.tdaCbusinesstripidR;
                _this6.title = _this6.dailyEdit.tdaDyyyymmddDy;

                if (_this6.dailyEdit.tdaCerrcode !== null) {
                  tempErrCode = JSON.parse(_this6.dailyEdit.tdaCerrcode);
                  Object.keys(tempErrCode).some(function (e) {
                    if (e === 'ERRTYPE_10') {
                      _this6.$Modal.info({
                        title: '注意',
                        content: tempErrCode.ERRTYPE_10[0].ERRMSG
                      });

                      return true;
                    }
                  });
                }

                _this6.workData = [{
                  workTime: '予定時間',
                  workStart: _this6.dailyEdit.tdaNopenN,
                  workEnd: _this6.dailyEdit.tdaNcloseN,
                  cellClassName: {
                    workTime: 'sub-title'
                  }
                }, {
                  workTime: '打刻',
                  workStart: _this6.dailyEdit.tdaNopenTp,
                  workEnd: _this6.dailyEdit.tdaNcloseTp,
                  cellClassName: {
                    workTime: 'sub-title'
                  }
                }, {
                  workTime: '就業時間',
                  workStart: _this6.dailyEdit.tdaNopenR,
                  workEnd: _this6.dailyEdit.tdaNcloseR,
                  isInput: true,
                  cellClassName: {
                    workTime: 'sub-title'
                  }
                }];

                _this6.workData0.forEach(function (e) {
                  _this6.workData = _this6.workData.concat({
                    workTime: e.tdadCnotworkName,
                    workStart: e.tdadNopenHhmi,
                    workEnd: e.tdadNcloseHhmi,
                    cellClassName: {
                      workTime: 'sub-title'
                    }
                  });
                });

                _this6.workData = _this6.workData.concat([{
                  workTime: '本人コメント',
                  isOwnComment: true,
                  cellClassName: {
                    workTime: 'like-title'
                  }
                }, {
                  workTime: '承認者コメント',
                  isBSComment: true,
                  cellClassName: {
                    workTime: 'like-title'
                  }
                }]); // 非勤務

                _this6.detailNonDutyVOList = data.detailNonDutyVOList; // 超過勤務

                _this6.detailOverhoursVOListNormal = data.detailOverhoursVOList;
                console.log(_this6.detailOverhoursVOListNormal);
                _this6.detailOverhoursVOList = _this6.handleOvertime(_this6.detailOverhoursVOListNormal, 0);
                console.log(_this6.detailOverhoursVOList); //

                limitOfBasedateVO = data.limitOfBasedateVO;
                _this6.overHourLimit = limitOfBasedateVO.dailyConvMinute;
                _this6.otDaily = limitOfBasedateVO.otDaily;
                _this6.targetForOverTime = data.targetForOverTime;

                _this6.changeWorkingID(_this6.workingId);

              case 44:
              case "end":
                return _context6.stop();
            }
          }
        }, _callee6);
      }))();
    },
    //超勤数据处理
    handleOvertime: function handleOvertime(data, flag) {
      console.log(data);
      var result = [];

      if (flag === 0) {
        //请求用到table用
        data.forEach(function (e) {
          result = result.concat({
            attributeUrl: e.attributeUrl,
            itemName: e.itemName,
            tdadCcode01: e.tdadCcode01,
            tdadCcode01Name: e.tdadCcode01Name,
            tdadCnotworkid: e.tdadCnotworkid,
            tdadCsparechar1: e.tdadCsparechar1,
            tdadCsparechar2: e.tdadCsparechar2,
            tdadCsparechar2Name: e.tdadCsparechar2Name,
            tdadNclose: e.tdadNclose,
            tdadNdeleteflg: e.tdadNdeleteflg,
            tdadNopen: e.tdadNopen,
            tdadNsparenum1: e.tdadNsparenum1,
            tdadSeq: e.tdadSeq
          }, {
            itemName: '用務内容',
            tdadNopen: e.tdadCsparechar1,
            tdadSeq: e.tdadSeq
          });
        });
      } else {
        //table用到请求用
        var num = 0;
        var comment = '';
        data.forEach(function (e) {
          if (num % 2 === 0) {
            result = result.concat({
              attributeUrl: e.attributeUrl,
              itemName: e.itemName,
              tdadCcode01: e.tdadCcode01,
              tdadCcode01Name: e.tdadCcode01Name,
              tdadCnotworkid: e.tdadCnotworkid,
              tdadCsparechar1: e.tdadCsparechar1,
              tdadCsparechar2: e.tdadCsparechar2,
              tdadCsparechar2Name: e.tdadCsparechar2Name,
              tdadNclose: e.tdadNclose,
              tdadNdeleteflg: e.tdadNdeleteflg,
              tdadNopen: e.tdadNopen,
              tdadNsparenum1: e.tdadNsparenum1,
              tdadSeq: e.tdadSeq
            });
            comment = '';
          } else {
            result[parseInt(num / 2)].tdadCsparechar1 = e.tdadNopen;
          }

          num = num + 1;
        });
      }

      return result;
    },
    handleSpan: function handleSpan(_ref2) {
      var row = _ref2.row,
          column = _ref2.column,
          rowIndex = _ref2.rowIndex,
          columnIndex = _ref2.columnIndex;

      if (rowIndex % 2 != 0) {
        if (columnIndex === 1) {
          return [1, 3];
        } else if (columnIndex > 2) {
          return [0, 0];
        }
      }
    },
    //コメントのみ登録
    updateInp: Debounce(function () {
      this.tmgResultsDto.txtAction = 'ACT_EDITINP_UCOMMENT';
      this.tmgResultsDto.holiday = this.bHoliday;
      this.tmgResultsDto.workingId = this.workingId;
      this.tmgResultsDto.txtDYYYYMMDD = this.query.txtDYYYYMMDD;
      this.tmgResultsDto.selMgdCbusinessTrip = this.businessTrip;
      this.tmgResultsDto.txtTdaNopenR = this.dailyEdit.tdaNopenR;
      this.tmgResultsDto.txtTdaNcloseR = this.dailyEdit.tdaNcloseR;
      this.tmgResultsDto.tdaCowncommentR = this.dailyEdit.tdaCowncommentR;

      if (this.tmgResultsDto.tdaCowncommentR !== null && this.getLengthB(this.tmgResultsDto.tdaCowncommentR) > 100) {
        this.$Notice.error({
          desc: "中断備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"
        });
        return false;
      }

      this.http.post('sys/tmgResults/updateInp', this.tmgResultsDto);
      this.isShow = false;
    }),
    //登録
    updateDaily: function updateDaily() {
      var _this7 = this;

      return _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee8() {
        var _this7$errorCheck, _this7$errorCheck2, check, msg;

        return regeneratorRuntime.wrap(function _callee8$(_context8) {
          while (1) {
            switch (_context8.prev = _context8.next) {
              case 0:
                _this7$errorCheck = _this7.errorCheck(_this7.workingId), _this7$errorCheck2 = _slicedToArray(_this7$errorCheck, 2), check = _this7$errorCheck2[0], msg = _this7$errorCheck2[1];

                if (check) {
                  _context8.next = 3;
                  break;
                }

                return _context8.abrupt("return");

              case 3:
                _this7.updateDailyLoading = true;
                console.log(1);
                _this7.tmgResultsDto.txtDYYYYMMDD = _this7.query.txtDYYYYMMDD;
                _this7.tmgResultsDto.txtAction = 'ACT_EDITINP_UDAILY';
                _this7.tmgResultsDto.workingId = _this7.workingId;
                _this7.tmgResultsDto.selMgdCbusinessTrip = _this7.businessTrip;
                _this7.tmgResultsDto.holiday = null;
                _this7.tmgResultsDto.txtTdaNopenR = '';
                _this7.tmgResultsDto.txtTdaNcloseR = '';
                _this7.tmgResultsDto.tdaCowncommentR = '';
                _this7.tmgResultsDto.hasAuthority = '';
                _this7.tmgResultsDto.holiday = _this7.bHoliday;
                _this7.tmgResultsDto.tdaCowncommentR = _this7.dailyEdit.tdaCowncommentR;

                if (!_this7.bDisable) {
                  if (!_this7.workData[2].workStart) {
                    _this7.tmgResultsDto.txtTdaNopenR = _this7.workData[0].workStart;
                  } else {
                    _this7.tmgResultsDto.txtTdaNopenR = _this7.workData[2].workStart;
                  }

                  if (!_this7.workData[2].workEnd) {
                    _this7.tmgResultsDto.txtTdaNcloseR = _this7.workData[0].workEnd;
                  } else {
                    _this7.tmgResultsDto.txtTdaNcloseR = _this7.workData[2].workEnd;
                  }

                  _this7.tmgResultsDto.nonDutyList = _this7.detailNonDutyVOList;
                  _this7.tmgResultsDto.overHoursList = _this7.detailOverhoursVOListNormal;

                  if (_this7.bFixed || _this7.bHoliday) {
                    _this7.tmgResultsDto.hasAuthority = false;
                  } else {
                    _this7.tmgResultsDto.hasAuthority = true;
                  }
                }

                _this7.$Modal.confirm({
                  inDrawer: true,
                  title: '注意',
                  content: msg || 'この内容で登録します。よろしいですか？',
                  width: 450,
                  okText: 'OK',
                  cancelText: 'キャンセル',
                  onOk: function () {
                    var _onOk = _asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee7() {
                      var _yield$_this7$http$po, _msg, errMsg20, _check;

                      return regeneratorRuntime.wrap(function _callee7$(_context7) {
                        while (1) {
                          switch (_context7.prev = _context7.next) {
                            case 0:
                              _context7.prev = 0;
                              _this7.updateDailyLoading = true;
                              _context7.next = 4;
                              return _this7.http.post('sys/tmgResults/updateDaily', _this7.tmgResultsDto);

                            case 4:
                              _yield$_this7$http$po = _context7.sent;
                              _msg = _yield$_this7$http$po.msg;

                              if (!(_msg === '0')) {
                                _context7.next = 11;
                                break;
                              }

                              _this7.updateDailyLoading = false;
                              _this7.isShow = false;

                              _this7.getTitleData();

                              return _context7.abrupt("return");

                            case 11:
                              _this7.errMsg = JSON.parse(_msg);
                              _check = Object.keys(_this7.errMsg).some(function (e) {
                                if (e === 'ERRTYPE_10') {
                                  _this7.$Notice.error({
                                    desc: _this7.errMsg.ERRTYPE_10[0].ERRMSG
                                  });

                                  return true;
                                }

                                if (e === 'ERRTYPE_20') {
                                  _this7.errMsg.ERRTYPE_20.forEach(function (e20) {
                                    errMsg20 = errMsg20 + e20.ERRMSG + '/n';
                                  });

                                  _this7.$Notice.error({
                                    desc: errMsg20
                                  });
                                }
                              });

                              if (!_check) {
                                _context7.next = 15;
                                break;
                              }

                              return _context7.abrupt("return", false);

                            case 15:
                              _context7.next = 20;
                              break;

                            case 17:
                              _context7.prev = 17;
                              _context7.t0 = _context7["catch"](0);
                              return _context7.abrupt("return");

                            case 20:
                              _context7.prev = 20;
                              _this7.updateDailyLoading = false;
                              return _context7.finish(20);

                            case 23:
                            case "end":
                              return _context7.stop();
                          }
                        }
                      }, _callee7, null, [[0, 17, 20, 23]]);
                    }));

                    function onOk() {
                      return _onOk.apply(this, arguments);
                    }

                    return onOk;
                  }()
                });

              case 18:
              case "end":
                return _context8.stop();
            }
          }
        }, _callee8);
      }))();
    },
    // 就業区分の切り替え表示制御
    changeWorkingID: function changeWorkingID(workingid) {
      if (!workingid) {
        return;
      }

      this.bDisable = false; // 休暇

      if (this.bHoliday) {
        this.bDisable = true;
        this.workTimeDisable = true; // 休暇以外
      } else {
        var mgdCsparechar4VO = this.mgdCsparechar4VOList.find(function (e) {
          return e.mgdCmastercode === workingid;
        }); // 出勤 or 休日出勤

        if (mgdCsparechar4VO.mgdCsparechar5 == '0') {
          this.bDisable = false;
          this.workTimeDisable = false; // 欠勤
        } else {
          this.bDisable = true;
          this.workTimeDisable = true;
        }
      }

      if (this.bDisable === true) {
        this.workData[2].workStart = '';
        this.workData[2].workEnd = '';
        this.detailNonDutyVOList = [];
        this.detailOverhoursVOList = [];
        this.detailOverhoursVOListNormal = [];
      }
    },
    time_check: function time_check(sTime) {
      return sTime.match(/^(([0-3]?[0-9])|([4][0-7])):([0-5]?[0-9])$/);
    },
    // エラーチェック
    errorCheck: function errorCheck(workingid) {
      var _this8 = this;

      var mgdCsparechar4VO = this.mgdCsparechar4VOList.find(function (e) {
        return e.mgdCmastercode === workingid;
      }); // 出勤 or 休日出勤

      if (mgdCsparechar4VO.mgdCsparechar5 != '0') {
        return [true];
      } // 修正出社時刻形式


      if (this.workData[2].workStart && !this.time_check(this.workData[2].workStart)) {
        this.$Notice.error({
          desc: "就業時間・始業は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"
        });
        return [false];
      } // 修正退社時刻形式


      if (this.workData[2].workEnd && !this.time_check(this.workData[2].workEnd)) {
        this.$Notice.error({
          desc: "就業時間・終業は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"
        });
        return [false];
      }

      if (this.dailyEdit.tdaCowncommentR !== null && this.getLengthB(this.dailyEdit.tdaCowncommentR) > 100) {
        this.$Notice.error({
          desc: "本人コメント考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"
        });
        return [false];
      } //非勤務


      var checkNonDuty = this.detailNonDutyVOList.some(function (e) {
        if (e.tdadNopen === null || !_this8.time_check(e.tdadNopen)) {
          _this8.$Notice.error({
            desc: "非勤務の開始時刻は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。時間の範囲は48:00以内で指定してください。"
          });

          return true;
        }

        if (e.tdadNclose === null || !_this8.time_check(e.tdadNclose)) {
          _this8.$Notice.error({
            desc: "非勤務の終了時刻は適切な時刻ではありません。(hh:mm)形式で時刻を入力してください。"
          });

          return true;
        }

        if (e.tdadCsparechar1 !== null && _this8.getLengthB(e.tdadCsparechar1) > 100) {
          _this8.$Notice.error({
            desc: "中断備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"
          });

          return true;
        }
      });
      if (checkNonDuty) return [false];
      this.detailOverhoursVOListNormal = this.handleOvertime(this.detailOverhoursVOList, 1); // 超過勤務

      var checkOverhour = this.detailOverhoursVOListNormal.some(function (e) {
        if (e.tdadCsparechar1 !== null && e.tdadCsparechar1.length > 100) {
          _this8.$Notice.error({
            desc: "超過勤務備考が設定値を超えています。全角・半角カナ50字以内、半角英数100字以内。"
          });

          return true;
        }
      });
      if (checkOverhour) return [false];
      var nOverTimeTotal = 0; // 超過勤務命令の明細部配下を行単位で取得

      this.detailOverhoursVOListNormal.forEach(function (e) {
        // 超過勤務命令の時間数を算出
        var sOpen = _this8.toMinuteFromHHcMI60(e.tdadNopen);

        var sClose = _this8.toMinuteFromHHcMI60(e.tdadNclose);

        nOverTimeTotal = nOverTimeTotal + (sClose - sOpen);
      }); // 超過勤務命令時間が入力されていて、超過勤務対象「無」の場合は警告メッセージを表示する

      if (nOverTimeTotal > 0 && this.targetForOverTime === "1") {
        // 警告メッセージの表示および、応答の確認
        return [true, '超過勤務対象外として設定されています。\n超過勤務命令を登録してもよろしいですか？'];
      } // 超過勤務命令時間数合計が登録された超勤限度時間を超える場合は警告メッセージを表示する


      if (nOverTimeTotal > this.overHourLimit) {
        // 警告メッセージの表示および、応答の確認
        return [true, '超過勤務命令の時間数が' + this.otDaily + '時間を超えています。\nこの超過勤務時間で登録してよろしいですか？'];
      }

      return [true];
    },
    toMinuteFromHHcMI60: function toMinuteFromHHcMI60(pHHMM) {
      // 何もパラメータが渡されなかった場合、そのまま返す
      if (pHHMM === '') {
        return '';
      }

      var retMinute;
      var nHour = new Number();
      var nMinute = new Number(); // 分に変換します

      nHour = eval(+(pHHMM.split(":")[0] * 60));
      nMinute = eval(+pHHMM.split(":")[1]);
      retMinute = eval(nHour + nMinute);
      return retMinute;
    },
    getLengthB: function getLengthB(moji) {
      var i,
          j,
          cnt = 0;
      var multiStr = new Array("×", "§", "¨", "°", "±", "´", "¶");

      for (i = 0; i < moji.length; i++) {
        for (j = 0; j < multiStr.length; j++) {
          if (multiStr[j].length == 0) {
            continue;
          }

          moji = moji.replace(multiStr[j], "00");
        }
      } // MAC OSの場合、改行コードが「\n」となり、Windowsの改行コード「\r\n」とバイト数が異なるので、


      moji = moji.split("\r\n").join("\n"); // 一旦「\r\n」 ⇒ 「\n」の変換を行い、改行コードを「\n」に合わせる。
      // バイト数を求めます。

      for (i = 0; i < moji.length; i++) {
        if (escape(moji.charAt(i)).length >= 4) cnt += 2;else cnt++;
      }

      return cnt;
    },
    addOverWork: function addOverWork() {
      var _this9 = this;

      var detailOverhoursVO = this.categoryOverhoursList.find(function (e) {
        return e.itemCode === _this9.categoryOverhours;
      });
      this.detailOverhoursVOList.push({
        attributeUrl: detailOverhoursVO.attributeUrl,
        tdadCnotworkid: detailOverhoursVO.itemCode,
        itemName: detailOverhoursVO.itemName,
        tdadNopen: '',
        tdadNclose: '',
        tdadNdeleteflg: '0',
        tdadSeq: Date.now(),
        tdadNsparenum1: '',
        tdadCcode01: '',
        tdadCcode01Name: '',
        tdadCsparechar2: '',
        tdadCsparechar2Name: ''
      }, {
        itemName: '用務内容',
        tdadNopen: '',
        tdadSeq: Date.now()
      });
    },
    addNotWork: function addNotWork() {
      var _this10 = this;

      var categoryNondutyVO = this.categoryNondutyList.find(function (e) {
        return e.itemCode === _this10.categoryNonduty;
      });
      this.detailNonDutyVOList.push({
        attributeUrl: categoryNondutyVO.attributeUrl,
        tdadCnotworkid: categoryNondutyVO.itemCode,
        itemName: categoryNondutyVO.itemName,
        tdadNdeleteflg: "0",
        tdadNopen: '',
        tdadNclose: '',
        tdadSeq: Date.now(),
        tdadCsparechar1: "\u4E2D\u65AD\u6642\u9593\u767B\u9332\uFF08\u643A\u5E2F\u7AEF\u672B\u3000".concat(Utils.formatDate(Date.now(), 'YYYY-MM-DD hh:ff:ss'), "\uFF09"),
        tdadNsparenum1: null
      });
    },
    delNotWork: function delNotWork(row) {
      this.detailNonDutyVOList = this.detailNonDutyVOList.filter(function (e) {
        return e.tdadSeq !== row.tdadSeq;
      });
    },
    delOverWork: function delOverWork(row) {
      this.detailOverhoursVOList = this.detailOverhoursVOList.filter(function (e) {
        return e.tdadSeq !== row.tdadSeq;
      });
    },
    dayRed: function dayRed(e, i) {
      var dayOfWeek = e.TMG_HOLFLG;

      if (i === 0) {
        if (e.TDA_DYYYYMMDD === this.today) {
          return 'brown cursor';
        }

        if (dayOfWeek.trim() === 'TMG_HOLFLG|0') {
          return 'cursor';
        }

        if (dayOfWeek.trim() != 'TMG_HOLFLG|0') {
          return 'blue cursor';
        }
      } else {
        if (e.TDA_DYYYYMMDD === this.today) {
          return 'brown';
        }

        if (dayOfWeek.trim() === 'TMG_HOLFLG|0') {
          return;
        }

        if (dayOfWeek.trim() != 'TMG_HOLFLG|0') {
          return 'blue';
        }
      }
    },
    handleInputChange: function handleInputChange(i, object, value, el) {
      if (object) {
        this.hasPassedTimeCheck = Utils.checkTime(object, value, el);
      }
    },
    handleClose: function handleClose() {
      this.errorFlag = false;
      this.isShow = false;
    },
    handleScroll: Throttle(function (e) {
      this.contentScrollTop = e.target.documentElement.scrollTop || e.target.body.scrollTop;
    }, 50),
    handleSpan4time: function handleSpan4time(_ref3) {
      var rowIndex = _ref3.rowIndex,
          columnIndex = _ref3.columnIndex;
      var len = this.workData.length;

      if (rowIndex === len - 2 && columnIndex === 1) {
        return [1, 2];
      } else if (rowIndex === len - 1 && columnIndex === 1) {
        return [1, 2];
      }
    }
  }
});

/***/ })
/******/ ]);