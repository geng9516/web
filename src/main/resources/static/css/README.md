# CSS 开发参考

[TOC]


## **1. 整体介绍**

```js
// 为了避免刀耕火种，采用自动生成浏览器兼容前缀和scss编译后压缩
👍 请下载`vscode` 的 live scss compiler (集成了`autoprefixer`)
   或  `webstorm` 的 `autoprefixer` 插件
😂 开始愉快的样式编程吧

下面是vscode的settings
  "liveSassCompile.settings.formats":[
        {
            "format": "compressed",
            "extensionName": ".min.css",
            "savePath": "/web-work/src/main/resources/static/css/pages" //编译后输出的路径，可以根据正在编辑的改
        },
    ],
  "liveSassCompile.settings.autoprefix": [
      "> 1%",
      "last 2 versions"
  ],
   "liveSassCompile.settings.generateMap": false,

```




### **1.2 结构**

```scss
|-components // 公用组件css
|-pages // 各页面单独的css
|-scss // scss 灵活调整输出目录，方便开发
```

## **2. 注意事项**

### 2.1 颜色建议使用rgb 或 hsl

```js
👍      rgb(251,146,140) rgba(251,146,140,.9)

👎      rgb(251,146,140) rgb(254,160,170)

👎👎    #FB928C #FEA0AA

// 不能相似颜色只是深浅不一而用不一样的
```

### 2.3 大小单位尽量选择rem

```js
👍   rem是相对根元素html的大小，易于控制

🙂　 px 不具备适应性

👎  em是相对与父级的大小，难以控制　
```



### 2.4 宽高选择vw vh，calc()

```js
👍   vw vh 只在根部及外层元素中使用，内层元素尽量不设置宽度，通过padding来使得页面适应性更好

🙂　 calc() 需要精确计算的部分
🙂   % 百分比其实是运算单位，也可以接受

👎  em是相对与父级的大小，难以控制　
```



### 2.5 避免不必要的div 和grid布局

```js
无意义的div和row col 会加深html，不利于DOM渲染，并且
1000行的html 和500行html维护的代价时不同的

👍 采用flex布局，兼容好，不破化页面流动性

👎  float 造成父元素坍陷，还需要cleanboth
```
