var APP_THEME = localStorage.getItem('APP_THEME') || 'default'
var THEME_CONFIG  = {
  orange: {
    '--login-svg-fill': 'var(--orange)',
    '--login-left-part-bg': 'var(--orange-g)',
    '--login-right-part-bg': 'var(--orange25)',
    '--theme-mark': 'var(--orange60)',
    '--filter-svg-icon': 'invert(100%) sepia() saturate(23) hue-rotate(0deg)',
    '--dept-select': 'var(--orange)',
    '--sider-background': 'var(--orange60)',
    '--sider-background-actived': 'var(--orange25)',
    '--sider-memu-background': 'var(--orange25)',
    '--sider-memu-icon-color': 'var(--orange)',
    '--sider-memu-icon-bg': 'linear-gradient(-45deg, rgb(255, 242, 234), rgb(255, 234, 217))',
    '--sider-memu-title-background': 'var(--orange50)',
    '--sider-memu-item-border-top': 'var(--orange)',
    '--sider-memu-item-border-bottom': 'var(--dark-red)',
    '--form-label': 'var(--orange60)',
    '--table-head': 'var(--orange60)',
    '--table-head-border': 'var(--orange50)',
    '--table-td-sat': 'var(--orange25)',
    '--table-td-hover': 'var(--light-gold)',
    '--table-checkin': 'rgb(175, 102, 10)',
    '--table-checkin-bg': 'rgb(255, 254, 234)',
    '--spin': 'var(--orange)',
    '--spin-bg': 'rgba(255, 255, 255, .7)',
    '--tree-hover': 'rgb(255,213, 185)',
    '--tree-select': 'rgb(255,213, 185)',
    '--tree-bg': 'var(--orange25)',
    '--tree-border': 'rgb(249, 233, 224)',
    '--primary-info': 'var(--brown-text)',
    '--primary-info-border': '1px solid var(--olive)',
    '--primary-info-bg': 'var(--gold50)',
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
    '--card-plan-title-border-b': 'var(--orange60)',
    '--real-schedule': 'var(--orange)',
    '--real-schedule-bg': 'rgba(253, 124, 40,.17)',
    '--offwork-btn-border': 'rgb(230,222,201)',
    '--offwork-btn': 'white',
    '--offwork-btn-bg': 'linear-gradient(90deg, rgb(254, 222, 117), rgb(255, 186, 0))',
    // 按钮
    '--primary-border': 'var(--orange)',
    '--primary-background': 'linear-gradient(90deg, rgb(255,157,93), rgb(255,102,0))',
    '--primary-hover': 'linear-gradient(90deg, rgb(253, 150, 81), rgb(243, 97, 0))',
    '--primary-active': 'linear-gradient(90deg, rgb(248, 130, 51), rgb(228, 91, 0))',
    '--ghost-primary-color': 'var(--orange)',
    '--ghost-primary-hover': 'linear-gradient(-45deg, var(--orange25), var(--white))',
    '--ghost-primary-active': 'linear-gradient(-45deg, var(--orange25), var(--white))',
    // 小标题样式
    '--titlenorm-border-t': 'none',
    '--titlenorm-border-b': 'var(--orange75)',
    '--titlenorm-bg': 'unset',
    '--titlenorm-i': 'unset',
    // checkbox
    '--checkbox-border': 'var(--orange25)',
    '--checkbox-bg': 'var(--orange75)',
    // mobile login
    '--mobile-login-btn': 'linear-gradient(90deg, rgba(255,157,93, .7), rgb(255,102,0, .7))',

  },
  blue: {
    '--login-svg-fill': 'var(--theme-blue)',
    '--login-left-part-bg': 'radial-gradient(rgb(135 177 252), rgb(48, 118, 242))',
    '--login-right-part-bg': 'var(--blue15)',
    '--theme-mark': 'var(--theme-blue)',
    '--filter-svg-icon': 'none',
    '--dept-select': 'var(--theme-blue)',
    '--sider-background': 'var(--theme-blue)',
    '--sider-background-actived': 'rgb(240, 250, 255)',
    '--sider-memu-background': 'rgb(240,250,255)',
    '--sider-memu-icon-color': 'var(--theme-blue)',
    '--sider-memu-icon-bg': 'rgba(236, 239, 241, .7)',
    '--sider-memu-title-background': 'rgb(125, 169, 247)',
    '--sider-memu-item-border-top': 'var(--primary)',
    '--sider-memu-item-border-bottom': 'var(--text-green)',
    '--form-label': 'rgb(103, 168, 255)',
    '--table-head': 'rgb(110, 165, 238)',
    '--table-head-border': 'var(--blue25)',
    '--table-td-sat': 'var(--blue25)',
    '--table-td-hover': 'rgb(232, 247, 233)',
    '--table-checkin': 'var(--table-th-checkin)',
    '--table-checkin-bg': 'var(--light-blue)',
    '--spin': 'var(--theme-blue)',
    '--spin-bg': 'rgba(255, 255, 255, .9)',
    '--tree-hover': 'rgb(213, 232, 252)',
    '--tree-select': 'rgb(234, 244, 254)',
    '--tree-bg': 'var(--light-grey)',
    '--tree-border': 'var(--blue25)',
    '--primary-info': 'var(--primary)',
    '--primary-info-bg': 'rgb(240, 250, 255)',
    '--primary-info-border': '1px solid rgb(171, 220, 255)',
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
    '--card-plan-title-border-b': 'var(--blue25)',
    '--real-schedule': 'var(--primary)',
    '--real-schedule-bg': 'rgba(45, 140, 240,.17)',
    '--offwork-btn-border': 'var(--ghost-primary-border)',
    '--offwork-btn': 'var(--primary)',
    '--offwork-btn-bg': 'white',
    // 按钮
    '--primary-border': 'rgb(53, 173, 255)',
    '--primary-background': 'linear-gradient(90deg, rgb(50, 182, 255), rgb(66, 118, 255))',
    '--primary-hover': 'linear-gradient(90deg, rgb(72, 191, 255), rgb(84, 133, 255))',
    '--primary-active': 'linear-gradient(90deg, rgb(33, 167, 240), rgb(36, 95, 245))',
    '--ghost-primary-color': 'var(--primary)',
    '--ghost-primary-hover': 'linear-gradient(-45deg, var(--ghost-hover), var(--white))',
    '--ghost-primary-active': 'linear-gradient(-45deg, var(--ghost-hover), var(--white))',
    // 小标题样式
    '--titlenorm-border-t': '1px solid var(--form-title-border)',
    '--titlenorm-border-b': 'var(--form-title-border)',
    '--titlenorm-bg': 'var(--table-td-hover)',
    '--titlenorm-i': 'rgb(91, 208, 41)',
    // checkbox
    '--checkbox-border': 'var(--primary)',
    '--checkbox-bg': 'var(--primary)',
    // mobile login
    '--mobile-login-btn': 'linear-gradient(90deg, rgba(50, 182, 255, .7), rgb(66, 118, 255, .7))',
  }
}

var Changetheme = function(e = APP_THEME) {
  if(e === 'default') {
    localStorage.setItem('APP_THEME', e)
    Object.keys(THEME_CONFIG.orange).forEach(e=>{
      document.documentElement.style.setProperty(e,THEME_CONFIG.orange[e])
    })
  }
  if(e=== 'classical') {
    localStorage.setItem('APP_THEME', e)
    Object.keys(THEME_CONFIG.blue).forEach(e=>{
      document.documentElement.style.setProperty(e,THEME_CONFIG.blue[e])
    })
  }
}

var toolTipTheme = function(theme) {
  return APP_THEME === 'dark' ? 'dark' : 'light'
}
Changetheme()