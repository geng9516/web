var APP_THEME = localStorage.getItem('APP_THEME') || 'default'
var THEME_CONFIG  = {
  default: {
    //basic color
    '--grey': 'rgb(104, 109, 116)',
    '--white': 'rgb(255, 255, 255)',
    '--brown': 'rgb(175, 121, 2)',
    '--red': 'rgb(252, 13, 27)',
    '--theme-blue': 'rgb(48, 118, 242)',
    '--green': 'rgb(82, 196, 26)',
    '--border-grey': 'rgb(234, 235, 236)',
    // login
    '--login-svg-fill': 'var(--orange)',
    '--login-left-part-bg': 'var(--orange-g)',
    '--login-right-part-bg': 'var(--orange25)',
    '--login-panel-bg': 'rgb(255,255,255)',
    '--theme-mark': 'var(--orange60)',
    '--filter-svg-icon': 'grayscale(1)',
    '--filter-svg-icon-hover': 'invert(100%) sepia() saturate(23) hue-rotate(0deg)',
    '--content-bg': 'white',
    '--dept-select': 'var(--orange)',
    '--sider-background': 'var(--orange60)',
    '--sider-background-actived': 'var(--orange25)',
    '--sider-menu-background': 'var(--orange25)',
    '--sider-menu-p': 'rgb(81, 90, 110)',
    '--sider-menu-p-hover': 'var(--orange)',
    '--sider-menu-p-active': 'var(--orange)',
    '--sider-menu-icon-bg': 'linear-gradient(-45deg, rgb(255, 242, 234), rgb(255, 234, 217))',
    '--sider-menu-title-background': 'var(--orange50)',
    '--sider-menu-item-border-top': 'var(--orange)',
    '--sider-menu-item-border-bottom': 'var(--dark-red)',
    '--form-label': 'var(--orange60)',
    '--table-head': 'var(--orange60)',
    '--table-head-border': 'var(--orange50)',
    '--table-border': 'rgb(230, 230, 230)',
    '--table-body-bg': 'white',
    '--table-td-stripe': 'rgb(248,248,249)',
    '--table-td-sat': 'var(--orange25)',
    '--table-td-hover-bg': 'var(--light-gold)',
    '--table-tr-hover': 'rgb(81,91,110)',
    '--table-checkin': 'rgb(175, 102, 10)',
    '--table-checkin-bg': 'rgb(255, 254, 234)',
    '--table-subtitle': 'var(--orange75)',
    '--table-subtitle-bg': 'var(--orange25)',
    '--spin': 'var(--orange)',
    '--spin-bg': 'rgba(255, 255, 255, .7)',
    '--tree-hover': 'rgb(255,213, 185)',
    '--tree-select': 'rgb(255,213, 185)',
    '--tree-bg': 'var(--orange25)',
    '--tree-border': 'rgb(249, 233, 224)',
    '--primary-info': 'var(--brown-text)',
    '--primary-info-border': '1px solid var(--olive)',
    '--primary-info-bg': 'rgba(255, 252, 189, 0.7)',
    '--success-info': 'var(--text-green)',
    '--success-info-bg': 'var(--green25)',
    '--gold-info':'var(--brown-text)',
    '--gold-info-bg':'var(--light-gold)',
    '--error-info-bg': 'var(--light-pink)',
    '--error-info-border': 'rgba(237, 64, 20, 0.12)',
    '--row-label-primary': 'rgb(171, 78, 16)',
    '--row-label-primary-border': 'rgb(220, 200, 187)',
    // header
    '--header': 'rgb(175, 102, 10)',
    '--header-border': 'rgb(224, 215, 12)',
    '--header-bg': 'rgb(255, 254, 234)',
    // 打卡页
    '--card-title': 'var(--orange)',
    '--card-date-bg': 'linear-gradient(90deg, rgb(255, 157, 93), rgb(255, 102, 0))',
    '--card-plan-bg': 'var(--orange60)',
    '--card-title-bg': 'white',
    '--card-boxshadow': '0 0 1px rgba(0, 0, 0, 0.2), 0 2px 4px rgba(0, 0, 0, 0.1)',
    '--card-plan-title-border-b': 'var(--orange60)',
    '--real-schedule': 'var(--orange)',
    '--real-schedule-bg': 'rgba(253, 124, 40,.17)',
    '--real-schedule-overwork': 'rgb(173, 24, 24)',
    '--real-schedule-overwork-bg': 'rgba(253, 124, 40,.4)',
    '--offwork-btn-border': 'rgb(230,222,201)',
    '--offwork-btn': 'white',
    '--offwork-btn-bg': 'linear-gradient(90deg, rgb(254, 222, 117), rgb(255, 186, 0))',
    // 按钮
    '--primary-btn': 'white',
    '--primary-border': 'var(--orange)',
    '--primary-background': 'linear-gradient(90deg, rgb(255,157,93), rgb(255,102,0))',
    '--primary-hover': 'linear-gradient(90deg, rgb(253, 150, 81), rgb(243, 97, 0))',
    '--primary-active': 'linear-gradient(90deg, rgb(248, 130, 51), rgb(228, 91, 0))',
    '--ghost-primary-border': 'var(--primary-border)',
    '--ghost-primary-color': 'var(--orange)',
    '--ghost-primary-hover': 'linear-gradient(-45deg, var(--orange25), var(--white))',
    '--ghost-primary-active': 'linear-gradient(-45deg, var(--orange25), var(--white))',
    '--disabled-btn': '#c5c8ce',
    '--disabled-btn-bg': 'white',
    '--disabled-btn-border': '#dcdee2',
    '--disabled-selection': '#f3f3f3',
    // 小标题样式
    '--titlenorm-border-t': 'none',
    '--titlenorm-border-b': 'var(--orange75)',
    '--titlenorm-bg': 'unset',
    '--titlenorm-i': 'unset',
    // checkbox
    '--checkbox-border': 'var(--orange25)',
    '--checkbox-bg': 'var(--orange75)',
    '--disabled-checkbox-bg':'linear-gradient(-45deg, rgb(193,193,193), rgb(217,217,217))',
    '--disabled-checkbox-border':'rgb(165, 165, 165)',
    // mobile login
    '--mobile-login-btn': 'linear-gradient(90deg, rgba(255,157,93, .7), rgb(255,102,0, .7))',
    '--mobile-nav-bg':'var(--table-head)',
    '--mobile-other-btn-bg':'rgba(255, 255, 255, .6)',
    '--mobile-input-btn-bg':'rgba(255, 255, 255, .6)',
    // footer
    '--footer-bg': '#eaebec',
    // reset
    '--input-border-grey': 'rgb(220, 222, 226)',
    '--login-title-border-b': 'rgb(211, 211, 211)',
    '--login-title-label': 'rgb(128, 134, 149)',
    '--input-border': 'var(--input-border-grey)',
    '--input-bg': 'white',
    '--place-holder': 'rgb(194,194,194)',
    '--ghost-background': 'rgb(255, 255, 255)',
    '--btn-border': '#dcdee2',
    '--modal-bg': 'white',
    '--label-monthSum': 'var(--border-grey)',
    '--light-input-border-grey': 'rgb(230, 230, 230)',
    '--label-monthSum-bg': 'white',
    '--selection-input-bg':'white',
    '--dept-select-border-left':'0',

    '--input-number-disabled-border':'#e3e5e8',
    '--input-number-disabled-bg':'var(--light-input-border-grey)',
    // calendar
    '--calendar-empty-bg':'rgb(245, 247, 249)',
    '--calendar-sat-bg':'var(--primary-info-bg)',
    '--calendar-sun-bg':'var(--error-info-bg)',
    '--rest-apply-self-info-bg':'rgba(255, 250, 240,0.35)',
    '--blue-or-dark':'var(--theme-blue)',
    '--blue-or-dark-bg':'var(--light-blue)',
    '--apply-status-dot-shadow':'0 0 0 4px rgb(241, 241, 241)',
    '--dept-search-bg':'#f8f8f9',
    '--over-time-level-2':'rgb(253, 201, 197)',
    '--over-time-level-3':'rgb(255, 124, 114)',
    '--over-time-level-4':'rgb(171, 14, 1)',
    '--over-time-level-5':'rgb(88, 9, 2)',
    '--mask-bg':'rgba(55,55,55,.6)',
    '--upload-list-file-hover-bg':'#f3f3f3',

  },
  classical: {
    //basic color
    '--grey': 'rgb(104, 109, 116)',
    '--white': 'rgb(255, 255, 255)',
    '--brown': 'rgb(175, 121, 2)',
    '--red': 'rgb(252, 13, 27)',
    '--theme-blue': 'rgb(48, 118, 242)',
    '--green': 'rgb(82, 196, 26)',
    '--border-grey': 'rgb(234, 235, 236)',
    // login
    '--login-svg-fill': 'var(--theme-blue)',
    '--login-left-part-bg': 'radial-gradient(rgb(135, 177, 252), rgb(48, 118, 242))',
    '--login-right-part-bg': 'var(--blue15)',
    '--login-panel-bg': 'rgb(255,255,255)',
    '--theme-mark': 'var(--theme-blue)',
    '--filter-svg-icon': 'grayscale(1)',
    '--filter-svg-icon-hover': 'none',
    '--content-bg': 'white',
    '--dept-select': 'var(--theme-blue)',
    '--sider-background': 'var(--theme-blue)',
    '--sider-background-actived': 'rgb(240, 250, 255)',
    '--sider-menu-background': 'rgb(240,250,255)',
    '--sider-menu-p': 'rgb(81, 90, 110)',
    '--sider-menu-p-hover': 'var(--theme-blue)',
    '--sider-menu-p-active': 'var(--theme-blue)',
    '--sider-menu-icon-bg': 'rgba(236, 239, 241, .7)',
    '--sider-menu-title-background': 'rgb(125, 169, 247)',
    '--sider-menu-item-border-top': 'var(--primary)',
    '--sider-menu-item-border-bottom': 'var(--text-green)',
    '--form-label': 'rgb(103, 168, 255)',
    '--table-head': 'rgb(110, 165, 238)',
    '--table-head-border': 'var(--blue25)',
    '--table-border': 'rgb(230, 230, 230)',
    '--table-body-bg': 'white',
    '--table-td-stripe': 'rgb(248,248,249)',
    '--table-td-sat': 'var(--blue25)',
    '--table-td-hover-bg': 'rgb(232, 247, 233)',
    '--table-tr-hover': 'rgb(81,91,110)',
    '--table-checkin': 'var(--table-th-checkin)',
    '--table-checkin-bg': 'var(--light-blue)',
    '--table-subtitle': 'var(--theme-blue)',
    '--table-subtitle-bg': 'var(--light-blue-l)',
    '--spin': 'var(--theme-blue)',
    '--spin-bg': 'rgba(255, 255, 255, .9)',
    '--tree-hover': 'rgb(213, 232, 252)',
    '--tree-select': 'rgb(234, 244, 254)',
    '--tree-bg': 'var(--light-grey)',
    '--tree-border': 'var(--blue25)',
    '--primary-info': 'var(--primary)',
    '--primary-info-bg': 'rgb(240, 250, 255)',
    '--primary-info-border': '1px solid rgb(171, 220, 255)',
    '--success-info': 'var(--text-green)',
    '--success-info-bg': 'var(--green25)',
    '--gold-info':'var(--brown-text)',
    '--gold-info-bg':'var(--light-gold)',
    '--error-info-bg': 'var(--light-pink)',
    '--error-info-border': 'rgba(237, 64, 20, 0.12)',
    '--row-label-primary': 'var(--primary)',
    '--row-label-primary-border': 'rgb(53, 173, 255)',
    // header
    '--header': 'rgb(10, 91, 176)',
    '--header-border': 'rgb(171, 220, 255)',
    '--header-bg': 'rgb(240, 250, 255)',
    // 打卡页
    '--card-title': 'var(--primary)',
    '--card-date-bg': 'linear-gradient(90deg, rgb(50, 182, 255), rgb(66, 118, 255))',
    '--card-plan-bg': 'var(--label-blue)',
    '--card-title-bg': 'white',
    '--card-boxshadow': '0 0 1px rgba(0, 0, 0, 0.2), 0 2px 4px rgba(0, 0, 0, 0.1)',
    '--card-plan-title-border-b': 'var(--blue25)',
    '--real-schedule': 'var(--primary)',
    '--real-schedule-bg': 'rgba(45, 140, 240,.17)',
    '--real-schedule-overwork': 'rgb(12, 72, 135)',
    '--real-schedule-overwork-bg': 'rgba(45, 140, 240,.4)',
    '--offwork-btn-border': 'var(--ghost-primary-border)',
    '--offwork-btn': 'var(--primary)',
    '--offwork-btn-bg': 'white',
    // 按钮
    '--primary-btn': 'white',
    '--primary-border': 'rgb(53, 173, 255)',
    '--primary-background': 'linear-gradient(90deg, rgb(50, 182, 255), rgb(66, 118, 255))',
    '--primary-hover': 'linear-gradient(90deg, rgb(72, 191, 255), rgb(84, 133, 255))',
    '--primary-active': 'linear-gradient(90deg, rgb(33, 167, 240), rgb(36, 95, 245))',
    '--ghost-primary-border': 'var(--primary-border)',
    '--ghost-primary-color': 'var(--primary)',
    '--ghost-primary-hover': 'linear-gradient(-45deg, var(--ghost-hover), var(--white))',
    '--ghost-primary-active': 'linear-gradient(-45deg, var(--ghost-hover), var(--white))',
    '--disabled-btn': '#c5c8ce',
    '--disabled-btn-bg': 'white',
    '--disabled-btn-border': '#dcdee2',
    '--disabled-selection': '#f3f3f3',
    // 小标题样式
    '--titlenorm-border-t': '1px solid var(--form-title-border)',
    '--titlenorm-border-b': 'var(--form-title-border)',
    '--titlenorm-bg': 'var(--table-td-hover-bg)',
    '--titlenorm-i': 'rgb(91, 208, 41)',
    // checkbox
    '--checkbox-border': 'var(--primary)',
    '--checkbox-bg': 'var(--primary)',
    '--disabled-checkbox-bg':'linear-gradient(-45deg, rgb(193,193,193), rgb(217,217,217))',
    '--disabled-checkbox-border':'rgb(165, 165, 165)',
    // mobile login
    '--mobile-login-btn': 'linear-gradient(90deg, rgba(50, 182, 255, .7), rgb(66, 118, 255, .7))',
    '--mobile-nav-bg':'var(--table-head)',
    '--mobile-other-btn-bg':'rgba(255, 255, 255, .6)',
    '--mobile-input-btn-bg':'rgba(255, 255, 255, .6)',
    // footer
    '--footer-bg': '#eaebec',
    // reset
    '--input-border-grey': 'rgb(220, 222, 226)',
    '--login-title-border-b': 'rgb(211, 211, 211)',
    '--login-title-label': 'rgb(128, 134, 149)',
    '--input-border': 'var(--input-border-grey)',
    '--input-bg': 'white',
    '--place-holder': 'rgb(194,194,194)',
    '--ghost-background': 'rgb(255, 255, 255)',
    '--btn-border': '#dcdee2',
    '--modal-bg': 'white',
    '--label-monthSum': 'var(--border-grey)',
    '--light-input-border-grey': 'rgb(230, 230, 230)',
    '--label-monthSum-bg': 'white',
    '--selection-input-bg':'white',
    '--dept-select-border-left':'0',
    '--input-number-disabled-border':'#e3e5e8',
    '--input-number-disabled-bg':'#f3f3f3',
    // calendar
    '--calendar-empty-bg':'rgb(245, 247, 249)',
    '--calendar-sat-bg':'var(--primary-info-bg)',
    '--calendar-sun-bg':'var(--error-info-bg)',
    '--rest-apply-self-info-bg':'rgba(240,250,255,0.349)',
    '--blue-or-dark':'var(--theme-blue)',
    '--blue-or-dark-bg':'var(--light-blue)',
    '--apply-status-dot-shadow':'0 0 0 4px rgb(241, 241, 241)',
    '--dept-search-bg':'#f8f8f9',
    '--over-time-level-2':'rgb(253, 201, 197)',
    '--over-time-level-3':'rgb(255, 124, 114)',
    '--over-time-level-4':'rgb(171, 14, 1)',
    '--over-time-level-5':'rgb(88, 9, 2)',
    '--mask-bg':'rgba(55,55,55,.6)',
    '--upload-list-file-hover-bg':'#f3f3f3',
  },
  dark: {
    //basic color
    '--grey': 'rgb(235, 235, 235)',
    '--white': 'rgb(235,235,235)',
    '--brown': 'rgb(255, 215, 127)',
    '--red': 'rgb(255, 98, 106)',
    '--theme-blue': 'rgb(159, 206, 255)',
    '--green': 'rgb(82, 196, 26)',
    '--border-grey': 'rgb(92, 87, 87)',
    // login
    '--login-svg-fill': 'rgb(235, 235, 235)',
    '--login-left-part-bg': 'rgb(42,44,49)',
    '--login-right-part-bg': 'rgb(42,44,49)',
    '--login-panel-bg': 'rgb(54,57,63)',
    '--theme-mark': 'rgb(113, 112, 112)',
    '--filter-svg-icon': 'grayscale(1) brightness(1.1) contrast(1.3)',
    '--filter-svg-icon-hover': 'none',
    '--content-bg': 'rgb(42,44,49)',
    '--dept-select': 'var(--theme-blue)',
    '--sider-background': 'rgb(54,57,63)',
    '--sider-background-actived': 'rgb(46,46,46)',
    '--sider-menu-background': 'rgb(46,46,46)',
    '--sider-menu-p': 'rgb(235, 235, 235)',
    '--sider-menu-p-hover': 'rgb(235, 235, 235)',
    '--sider-menu-p-active': 'rgb(87, 88, 91)',
    '--sider-menu-icon-bg': 'rgba(88, 88, 88, .7)',
    '--sider-menu-title-background': 'rgb(58, 58, 58)',
    '--sider-menu-item-border-top': 'rgb(130,130,130)',
    '--sider-menu-item-border-bottom': 'rgb(190, 190, 190)',
    '--form-label': 'rgb(75, 75, 75)',
    '--table-head': 'rgb(75, 75, 75)',
    '--table-head-border': 'rgb(92, 87, 87)',
    '--table-border': 'rgb(92, 87, 87)',
    '--table-body-bg': 'rgb(42,44,49)',
    '--table-td-stripe': 'rgb(42,44,49)',
    '--table-td-sat': 'var(--blue25)',
    '--table-td-hover-bg': 'rgb(220,220, 220,0.25)',
    '--table-tr-hover': 'rgb(235,235,235)',
    '--table-checkin': 'var(--primary-info)',
    '--table-checkin-bg': 'var(--primary-info-bg)',
    '--table-subtitle': 'rgb(108, 108, 108)',
    '--table-subtitle-bg': 'rgb(201, 201, 201)',
    '--spin': 'var(--theme-blue)',
    '--spin-bg': 'rgba(51, 51, 51,0.9)',
    '--tree-hover': 'rgb(104, 104, 104)',
    '--tree-select': 'rgba(209, 209, 211, 0.46)',
    '--tree-bg': 'var(--table-head)',
    '--tree-border': 'rgb(96, 96, 96)',
    '--primary-info': 'rgb(168, 210, 255)',
    '--primary-info-bg': 'rgb(71, 159, 210,.2)',
    '--success-info': 'rgb(168, 255, 174)',
    '--success-info-bg': 'rgba(71, 210, 90, 0.2)',
    '--gold-info':'rgb(252, 241, 154)',
    '--gold-info-bg':'rgba(180, 175, 134 ,0.4)',
    '--error-info-bg': 'rgba(246, 123, 97,0.2)',
    '--error-info-border': 'transparent',
    '--primary-info-border': 'transparent',
    '--row-label-primary': 'var(--primary)',
    '--row-label-primary-border': 'rgb(53, 173, 255)',
    // header
    '--header': 'rgb(235, 235, 235)',
    '--header-border': 'transparent',
    '--header-bg': 'rgb(72, 72, 72)',
    // 打卡页
    '--card-title': 'var(--primary)',
    '--card-date-bg': 'rgb(54, 57, 63)',
    '--card-plan-bg': 'rgb(54, 57, 63)',
    '--card-title-bg': 'rgb(54, 57, 63)',
    '--card-boxshadow': '0 2px 10px 0 rgba(0,0,0,.2)',
    '--card-plan-title-border-b': 'rgb(0,0,0,0.3)',
    '--real-schedule': 'rgb(113, 180, 251)',
    '--real-schedule-bg': 'rgba(45, 140, 240,.17)',
    '--real-schedule-overwork': 'rgb(113, 180, 251)',
    '--real-schedule-overwork-bg': 'rgba(35, 72, 146,.4)',
    '--offwork-btn-border': 'transparent',
    '--offwork-btn': '#c8e1ff',
    '--offwork-btn-bg': 'rgb(41, 71, 94)',
    // 按钮
    '--primary-btn': '#c8e1ff',
    '--primary-border': 'transparent',
    '--primary-background': 'rgb(41, 71, 94)',
    '--primary-hover': 'rgb(39, 77, 106)',
    '--primary-active': 'linear-gradient(90deg, rgb(33, 167, 240), rgb(36, 95, 245))',
    '--ghost-primary-border': 'rgb(53, 173, 255)',
    '--ghost-primary-color': 'rgb(53, 173, 255)',
    '--ghost-primary-hover': 'rgb(65, 69, 76)',
    '--ghost-primary-active': 'linear-gradient(-45deg, var(--ghost-hover), var(--white))',
    '--disabled-btn': '#606060',
    '--disabled-btn-bg': 'var(--content-bg)',
    '--disabled-btn-border': '#3c3c3d',
    '--disabled-selection': 'var(--content-bg)',
    // 小标题样式
    '--titlenorm-border-t': 'none',
    '--titlenorm-border-b': 'transparent',
    '--titlenorm-bg': 'rgba(71, 210, 90, 0.2)',
    '--titlenorm-i': 'rgb(91, 208, 41)',
    // checkbox
    '--checkbox-border': 'rgb(136, 136, 136)',
    '--checkbox-bg': 'rgb(98,98, 98)',
    '--disabled-checkbox-bg':'linear-gradient(-45deg, #333333, #333333)',
    '--disabled-checkbox-border':'#464646',
    // mobile login
    '--mobile-login-btn': 'rgb(43, 64, 104)',
    '--mobile-nav-bg':'rgb(32, 31, 31)',
    '--mobile-other-btn-bg':'var(--ghost-background)',
    '--mobile-input-btn-bg':'var(--selection-input-bg)',

    // footer
    '--footer-bg': 'rgb(54, 54, 54)',
    // dark
    '--input-border-grey': 'rgb(92, 87, 87)',
    '--login-title-border-b': 'rgb(92, 87, 87)',
    '--login-title-label': 'rgb(142, 146, 151)',
    '--input-border': 'rgb(0,0,0,0.3)',
    '--input-bg': 'rgb(0,0,0,0.1)',
    '--place-holder': 'rgba(148,148,148)',
    '--ghost-background': 'rgb(54,57,63)',
    '--btn-border': 'rgb(138, 138, 138)',
    '--modal-bg': 'rgb(42,44,49)',
    '--label-monthSum': 'rgb(72, 72, 72)',
    '--light-input-border-grey': 'rgb(144, 143, 143)',
    '--label-monthSum-bg': 'rgb(55, 54, 54)',
    '--selection-input-bg':'rgba(101, 101, 101, 0.2)',
    '--dept-select-border-left':'1px dashed var(--dept-select)',
    '--input-number-disabled-border':'rgb(79,79, 79)',
    '--input-number-disabled-bg':'rgba(73, 73, 73, 0.55)',
    // calendar
    '--calendar-empty-bg':'rgb(36, 36, 36)',
    '--calendar-sat-bg':'rgba(13,70,89,0.2',
    '--calendar-sun-bg':'rgba(89,26,13,0.2)',
    '--rest-apply-self-info-bg':'var(--content-bg)',
    '--blue-or-dark':'var(--primary-info)',
    '--blue-or-dark-bg':'var(--primary-info-bg)',
    '--apply-status-dot-shadow':'0 0 0 3px rgb(65, 65, 65)',
    '--dept-search-bg':'var(--content-bg)',
    '--over-time-level-2':'rgba(244, 116, 88, 0.52)',
    '--over-time-level-3':'#92231a',
    '--over-time-level-4':'#480904',
    '--over-time-level-5':'#210301',
    '--mask-bg':'rgba(99, 99, 99,0.91)',
    '--upload-list-file-hover-bg':'#3b3a3a',
  }
}

var Changetheme = function(e = APP_THEME, context) {
  if(context) context.curTheme = e
  if(e) {
    localStorage.setItem('APP_THEME', e)
    Object.keys(THEME_CONFIG[e]).forEach(t=>{
      document.documentElement.style.setProperty(t,THEME_CONFIG[e][t])
    })
  }
}

var toolTipTheme = function() {
  return APP_THEME === 'dark' ? 'light' : 'dark'
}
Changetheme()