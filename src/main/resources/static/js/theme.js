var APP_THEME = localStorage.getItem('APP_THEME') || 'デフォルト'
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
    // 按钮
    '--primary-border': 'var(--orange)',
    '--primary-background': 'linear-gradient(90deg, rgb(255,157,93), rgb(255,102,0))',
    '--primary-hover': 'linear-gradient(90deg, rgb(253, 150, 81), rgb(243, 97, 0))',
    '--primary-active': 'linear-gradient(90deg, rgb(248, 130, 51), rgb(228, 91, 0))',
    '--ghost-primary-color': 'var(--orange)',
    '--ghost-primary-hover': 'linear-gradient(-45deg, var(--orange25), var(--white))',
    '--ghost-primary-active': 'linear-gradient(-45deg, var(--orange25), var(--white))',
    // checkbox
    '--checkbox-border': 'var(--orange25)',
    '--checkbox-bg': 'var(--orange75)',

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
    // 按钮
    '--primary-border': 'rgb(53, 173, 255)',
    '--primary-background': 'linear-gradient(90deg, rgb(50, 182, 255), rgb(66, 118, 255))',
    '--primary-hover': 'linear-gradient(90deg, rgb(72, 191, 255), rgb(84, 133, 255))',
    '--primary-active': 'linear-gradient(90deg, rgb(33, 167, 240), rgb(36, 95, 245))',
    '--ghost-primary-color': 'var(--primary)',
    '--ghost-primary-hover': 'linear-gradient(-45deg, var(--ghost-hover), var(--white))',
    '--ghost-primary-active': 'linear-gradient(-45deg, var(--ghost-hover), var(--white))',
    // checkbox
    '--checkbox-border': 'var(--primary)',
    '--checkbox-bg': 'var(--primary-border)',
  }
}

var Changetheme = function(e = APP_THEME) {
  if(e === 'デフォルト') {
    localStorage.setItem('APP_THEME', e)
    Object.keys(THEME_CONFIG.orange).forEach(e=>{
      document.documentElement.style.setProperty(e,THEME_CONFIG.orange[e])
    })
  }
  if(e=== 'クラシック') {
    localStorage.setItem('APP_THEME', e)
    Object.keys(THEME_CONFIG.blue).forEach(e=>{
      document.documentElement.style.setProperty(e,THEME_CONFIG.blue[e])
    })
  }
}
Changetheme()