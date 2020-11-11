# 前提
``` scss
没有node.js 请下载最新node.js https://nodejs.org/en/
在/resources 路径下 敲npm i

本项目中需要特别注意scss编译，手机端编译，和hack方式的应用svg-sprite
以下分别展开介绍
```

## 手机端编译
``` scss
开发时，应该讲js黏贴回html中，以便debug, 修改完后在删掉，复制到js文件夹对应文件中
然后在/resources路径下 敲 npm run build, 待compliedjs生成好后再一并提交
```

## SCSS
``` scss
请参照 \resources\static\css\README.md
```

## svg-sprite
``` scss
图标是通过svg-sprite-loader生成的html片段组成html应用在本项目中
resources\templates\layout\icons.html  // 菜单icon
resources\templates\layout\logos.html  // 首页和header的logo

现在仅需要维护，即往相应html中按照其他的格式一样的，增加一个svg即可
```