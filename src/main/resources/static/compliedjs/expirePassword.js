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
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

function asyncGeneratorStep(gen, resolve, reject, _next, _throw, key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(_next, _throw); } }

function _asyncToGenerator(fn) { return function () { var self = this, args = arguments; return new Promise(function (resolve, reject) { var gen = fn.apply(self, args); function _next(value) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "next", value); } function _throw(err) { asyncGeneratorStep(gen, resolve, reject, _next, _throw, "throw", err); } _next(undefined); }); }; }

var login = new Vue({
  el: '.login-page',
  data: function data() {
    return {
      login: {
        username: Utils.getUrlParam(location.href, 'username')
      },
      loading: false
    };
  },
  mounted: function mounted() {
    this.$Modal.warning({
      title: '注意',
      content: 'このパスワードは有効期限を過ぎました。新しいパスワードを登録してください'
    });
  },
  methods: {
    loging: Debounce( /*#__PURE__*/_asyncToGenerator( /*#__PURE__*/regeneratorRuntime.mark(function _callee() {
      return regeneratorRuntime.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              if (this.login.oldPassword) {
                _context.next = 2;
                break;
              }

              return _context.abrupt("return", this.$Notice.warning({
                title: '注意',
                desc: '現在のパスワードを入力してください',
                duration: 6.5
              }));

            case 2:
              if (this.login.newPassword) {
                _context.next = 4;
                break;
              }

              return _context.abrupt("return", this.$Notice.warning({
                title: '注意',
                desc: '変更後のパスワードを入力してください',
                duration: 6.5
              }));

            case 4:
              if (this.login.repeatPassword) {
                _context.next = 6;
                break;
              }

              return _context.abrupt("return", this.$Notice.warning({
                title: '注意',
                desc: '変更後のパスワード(確認用)を入力してください',
                duration: 6.5
              }));

            case 6:
              if (!(!/^[a-z0-9A-Z]+$/.test(this.login.newPassword) || !/^[a-z0-9A-Z]+$/.test(this.login.repeatPassword))) {
                _context.next = 8;
                break;
              }

              return _context.abrupt("return", this.$Notice.warning({
                title: '注意',
                desc: 'パスワードに使用できる文字は英数字のみです',
                duration: 6.5
              }));

            case 8:
              if (!(this.login.newPassword !== this.login.repeatPassword)) {
                _context.next = 10;
                break;
              }

              return _context.abrupt("return", this.$Notice.warning({
                title: '注意',
                desc: '新パスワード(確認用)には、新パスワードと同じパスワードを入力してください。',
                duration: 6.5
              }));

            case 10:
              this.loading = true;
              _context.prev = 11;
              _context.next = 14;
              return this.http.post('/changeExpirePassword', this.login);

            case 14:
              location.href = BASE_PATH;
              _context.next = 21;
              break;

            case 17:
              _context.prev = 17;
              _context.t0 = _context["catch"](11);
              this.loading = false;
              return _context.abrupt("return", this.$Notice.error({
                title: '注意',
                desc: _context.t0,
                duration: 6.5
              }));

            case 21:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, this, [[11, 17]]);
    }))),
    fixH5Height: function fixH5Height() {
      setTimeout(function () {
        var scrollHeight = document.documentElement.scrollTop || document.body.scrollTop || 0;
        window.scrollTo(0, Math.max(scrollHeight - 1, 0));
      }, 100);
    }
  }
});

/***/ })
/******/ ]);