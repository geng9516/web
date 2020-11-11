const path = require('path');
const glob = require('glob');
// 多文件打包到多文件
const entries = function() {
    const entryFiles = glob.sync(path.join(__dirname, './templates/mobile/js/**.js'))
    const map = {}
    entryFiles.forEach(filePath => {
      const filename = filePath.match(/(?:\/)\w+\.js$/)[0]
      map[filename] = filePath
    })
    return map
}
module.exports = {
    mode: 'production',
    entry: entries(),
    output: {
        path: path.resolve(__dirname, './static/compliedjs'),
        filename: '[name]'
    },
    optimization: {
        minimize: true
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                use: 'babel-loader',
                exclude: '/node_modules/',
            }
        ]
    }
}